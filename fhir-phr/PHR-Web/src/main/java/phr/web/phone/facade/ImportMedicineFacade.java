/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：おくすりインポートクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/07
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.ImportMedicineComparator;
import phr.datadomain.entity.RecordCreatorTypeCd;
import phr.datadomain.entity.SeparatorComparator;
import phr.datadomain.entity.SeparatorInfoEntity;
import phr.dto.DosageEntitySetDto;
import phr.dto.MedicineFormatRecordNo;
import phr.dto.MedicineImportUnitDto;
import phr.dto.MedicineQRVersion;
import phr.service.IDosageImportService;
import phr.service.IImportMedicineSetService;
import phr.service.impl.DosageImportService;
import phr.service.impl.ImportMedicineSetService;
import phr.web.phone.dto.MedicineImportDto;
import phr.web.phone.dto.RequestMedicineImportDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseMedicineUpdateDto;

/**
 *
 * @author kis-note-027_user
 */
public class ImportMedicineFacade extends PhoneFacade{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ImportMedicineFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * インジェクション：ユーザ認証サービス
     */
//    @Autowired
//    @Qualifier("DosageImportService")
    private IDosageImportService dosageImportService = new DosageImportService();
    
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        //IDosageImportService service = new DosageImportService();
        ResponseBaseDto responseDto = new ResponseMedicineUpdateDto();
        IImportMedicineSetService setService = new ImportMedicineSetService();
        
        RequestMedicineImportDto requestDto = gson.fromJson(json, RequestMedicineImportDto.class);
        List<MedicineImportDto> importText = requestDto.getImportText();
        
        String phrId = requestDto.getPhrId();
        DosageEntitySetDto setDto = new DosageEntitySetDto();
        String dosageId=null;

        //CLRFで分割する
        String[] recordString;
        
        
        try{
            
            //分割の有無その他確認
            boolean isSeparated = false;
            String sepDosageId = "";
            long separatorId = 0L;
            
           //
            if(importText.size()>1){
                //準備としてimportTextをリストに入れなおす
                List<MedicineImportUnitDto> importList = new ArrayList(); 
                for(MedicineImportDto importDto:importText){
                    MedicineImportUnitDto unitDto = new MedicineImportUnitDto();
                    unitDto.setShootOrder(importDto.getShootingOrder());
                    unitDto.setImportText(importDto.getImportText());
                    importList.add(unitDto);
                }
                
                //1番目とセパレータ確認
                int verRecordCount = 0;//バージョン情報数
                String verNo = null;//バージョン情報
                int separatorCount = 0;//セパレータレコード数
                int recordSepCount = 0;//セパレータレコードが持つ分割の全件数                
                int separatorIdcount = 0;//セパレータIdの数（2以上はエラーとして返す）                
                for(MedicineImportUnitDto unitDto:importList){
                    recordString = unitDto.getImportTextSplit();
                    for(int i= 0;i<recordString.length;i++){
                        String[] recordSet = recordString[i].split(",",-1);
                        String recordno  = recordSet[0];

                        //1番目を特定する(1番目が複数ある場合はエラーを返す)
                        if(recordno.contains(MedicineQRVersion.FIXEDVALUE.getText())){
                            verRecordCount = verRecordCount +1;
                            verNo = recordno;
                            //unitDto.setUnitOrder(1);
                        }else if(i==0){
                        //レコードNoで始まっているか確認                            
                            unitDto.setStartRecoreNO(checkStartLine(recordno,recordSet.length,verNo));
                        }
                        //セパレータ(911)レコードがあるか
                       if(recordno.equals(MedicineFormatRecordNo.SEPARATOR.getId())){
                            String SepaRatorIdStr = recordSet[1];
                            separatorCount = separatorCount + 1;
                            unitDto.setSeparatorRecord(true);
                            if(separatorId==0L){
                                separatorId = Long.parseLong(SepaRatorIdStr);
                                recordSepCount = Integer.parseInt(recordSet[2]);
                                separatorIdcount = 1;
                            }else{
                                Long tempSepId = Long.parseLong(SepaRatorIdStr);
                                if(tempSepId!=separatorId){
                                    separatorIdcount = separatorIdcount+1;
                                    break;
                                }
                            }
                       }else //レシピ番号を取得しておく
                           if(recordno.equals(MedicineFormatRecordNo.MEDICINE.getId()) || recordno.equals(MedicineFormatRecordNo.RECIPE.getId())){
                               if(unitDto.getStartRecipeNo()==0){
                                   unitDto.setStartRecipeNo(Integer.parseInt(recordSet[1]));
                                   unitDto.setEndRecipeNo(Integer.parseInt(recordSet[1]));
                               }else{
                                   unitDto.setEndRecipeNo(Integer.parseInt(recordSet[1]));
                               }
                           
                       } 
                    }
                    if(verRecordCount>1 ||separatorIdcount>1){
                        break;
                    }
                }
                if(verRecordCount>1 ||separatorIdcount>1){
                    responseDto.setStatus(ResponseBaseDto.ERROR);
                    responseDto.setMessage("複数セットのお薬情報が検出されました。1セットずつでの取り込みをお願いいたします");                    
                }
                
                //結合順のソート
                Collections.sort(importList, new ImportMedicineComparator());
     
                //セパレータセットの処理
                if(separatorIdcount==1){
                    //全部ある
                    if(separatorCount==recordSepCount){
                        //分割数と一致
                        if(dosageId==null){
                            dosageId = dosageImportService.getNewDosageId();
                        }
                        List<SeparatorInfoEntity> seplist=new ArrayList();
                        if(separatorCount==importList.size()){  
                            for(MedicineImportUnitDto sepDto:importList){
                                seplist.add(setSeparatorInfofromText(sepDto.getImportTextSplit(),dosageId,sepDto.getImportText()));
                            }                        
                        }else{//分割数と一致しない                          
                            StringBuilder sepText = new StringBuilder();
                            for(int i=0;i<importList.size();i++){
                                if(importList.get(i).isSeparatorRecord()){
                                    String contentText = sepText.toString();
                                    seplist.add(setSeparatorInfofromText(importList.get(i).getImportTextSplit(),dosageId,contentText));
                                    sepText.setLength(0);
                                }else{
                                    if(i>0){
                                        sepText.append("\r\n");
                                    }
                                    sepText.append(importList.get(i).getImportText());
                                }
                            }   
                        }
                        Collections.sort(seplist, new SeparatorComparator());
                        StringBuilder importTextsep = new StringBuilder();
                        for(int k=0;k<seplist.size();k++){
                            if(k>0){
                                importTextsep.append("\r\n");
                            }
                            importTextsep.append(seplist.get(k).getSeparatText());
                        }
                        recordString = null;
                        recordString = importTextsep.toString().split("\r\n");                         
                    }else {//Separatorの一部                        
                        //保存済みのデータの有無の確認
                            List<SeparatorInfoEntity> seplistfromDB = dosageImportService.getSeparatorInfo(separatorId);
                            if(seplistfromDB==null){
                                seplistfromDB=new ArrayList();
                                if(dosageId==null){
                                    dosageId = dosageImportService.getNewDosageId();
                                }
                            }else{
                                dosageId = seplistfromDB.get(0).getDosageId();
                            }
                            //取得データを分割ごとにテキスト追加
                            List<SeparatorInfoEntity> seplistforSave = new ArrayList();
                            StringBuilder sepText = new StringBuilder();
                            for(int i=0;i<importList.size();i++){
                                if(importList.get(i).isSeparatorRecord()){
                                    String contentText = sepText.toString();
                                    seplistforSave.add(setSeparatorInfofromText(importList.get(i).getImportTextSplit(),dosageId,contentText));
                                    sepText.setLength(0);
                                }else{
                                    if(i>0){
                                        sepText.append("\r\n");
                                    }
                                    sepText.append(importList.get(i).getImportText());
                                }
                            }
                            //重複データでないか確認
                            for(SeparatorInfoEntity dupcheck:seplistforSave){
                                for(SeparatorInfoEntity savedData:seplistfromDB){
                                    if(savedData.getSeparateNo()==dupcheck.getSeparateNo()){
                                       responseDto.setStatus(ResponseBaseDto.SUCCESS);
                                       responseDto.setMessage("取得済みのデータでした。次のデータを取込んでください。");
                                       return responseDto;
                                    }
                                }
                            }
                            if((seplistfromDB.size()+seplistforSave.size())==recordSepCount){
                                for(SeparatorInfoEntity ent:seplistforSave){
                                    seplistfromDB.add(ent);
                                }
                                Collections.sort(seplistfromDB, new SeparatorComparator());
                                StringBuilder importTextsep = new StringBuilder();
                                for(int k=0;k<seplistfromDB.size();k++){
                                    if(k>0){
                                        importTextsep.append("\r\n");
                                    }
                                    importTextsep.append(seplistfromDB.get(k).getSeparatText());
                                }
                                recordString = null;
                                recordString = importTextsep.toString().split("\r\n");                                 
                            }else{
                                //データベースへの登録1(セパレータレコードがあり、すべてを取得していない)
                                int sepresult=0;
                                if(seplistfromDB==null||seplistfromDB.isEmpty()){
                                   setDto.setDosage(setSepDosageEntity(dosageId,phrId)); 
                                }
                                for(SeparatorInfoEntity ent:seplistforSave){
                                   setDto.setSeparatorInfo(setSeparatorInfo(ent.getSeparatorId(),dosageId,ent.getSeparateNo(),ent.getSeparateCount(),ent.getSeparatText()));
                                   sepresult = dosageImportService.setDosage(setDto);
                                }
                               if(sepresult>0){
                                   responseDto.setStatus(ResponseBaseDto.SUCCESS);
                                   responseDto.setMessage("分割データを保存いたしました。次のデータを取込んでください。");
                               }else{
                                    responseDto.setStatus(ResponseBaseDto.ERROR);
                                    responseDto.setMessage("お薬の登録中にエラーが発生しました");           
                               }
                               return responseDto;
                            }
                    }
                }else{//Separatorはない
                    StringBuilder importTextsep = new StringBuilder();
                    for(int k=0;k<importList.size();k++){
                        if(k>0){
                            if(importList.get(k).isStartRecoreNO()){
                               importTextsep.append("\r\n");
                            }
                        }
                        importTextsep.append(importList.get(k).getImportText());
                    }
                    recordString = null;
                    recordString = importTextsep.toString().split("\r\n");
                }

                //本登録
                if(dosageId==null){
                    dosageId = dosageImportService.getNewDosageId();
                }                
                //setDto = getSetDto(recordString,phrId,dosageId);
                setDto = setService.getImportMedicineSet(recordString, phrId, dosageId);
            }else if(importText.size()==1){
                recordString= importText.get(0).getImportText().split("\r\n");

                for(int i=0;i<recordString.length;i++){
                    //カンマで分割する
                    String[] recordSet = recordString[i].split(",",-1);
                    String recordNo = recordSet[0];
                    if(recordNo.equals(MedicineFormatRecordNo.SEPARATOR.getId())){
                        isSeparated = true;
                        //セパレータ情報取得
                        String SepaRatorIdStr = recordSet[1];
                        separatorId = Long.parseLong(SepaRatorIdStr);
                        int sepNo = Integer.parseInt(recordSet[3]);//分割の何番目か
                        int sepCount = Integer.parseInt(recordSet[2]);//分割の全件数                    
                        List<SeparatorInfoEntity> seplist = dosageImportService.getSeparatorInfo(separatorId);
                        
                            //重複データでないか確認                           
                        for(SeparatorInfoEntity savedData:seplist){
                            if(savedData.getSeparateNo()==sepNo){
                               responseDto.setStatus(ResponseBaseDto.SUCCESS);
                               responseDto.setMessage("取得済みのデータでした。次のデータを取込んでください。");
                               return responseDto;
                            }
                        }                        
                        //データの結合または保存
                        if(!seplist.isEmpty() && (seplist.size() + 1)==sepCount){
                           dosageId = seplist.get(0).getDosageId();
                           sepDosageId = seplist.get(0).getDosageId();
                           seplist.add(setSeparatorInfo(separatorId,dosageId,sepNo,sepCount,importText.get(0).getImportText()));
                            Collections.sort(seplist, new SeparatorComparator());
                            StringBuilder importTextsep = new StringBuilder();
                            for(int k=0;k<seplist.size();k++){
                                if(k>0){
                                    importTextsep.append("\r\n");
                                }
                                importTextsep.append(seplist.get(k).getSeparatText());
                            }
                            recordString = null;
                            recordString = importTextsep.toString().split("\r\n");
                            break;
                        }else{
                            if(seplist.isEmpty()){
                                dosageId = dosageImportService.getNewDosageId();
                                setDto.setDosage(setSepDosageEntity(dosageId,phrId));
                            }else{
                               dosageId = seplist.get(0).getDosageId();
                            }

                           setDto.setSeparatorInfo(setSeparatorInfo(separatorId,dosageId,sepNo,sepCount,importText.get(0).getImportText()));
                           int sepresult = dosageImportService.setDosage(setDto);
                           if(sepresult>0){
                               responseDto.setStatus(ResponseBaseDto.SUCCESS);
                               responseDto.setMessage("分割データを保存いたしました。次のデータを取込んでください。");
                           }else{
                                responseDto.setStatus(ResponseBaseDto.ERROR);
                                responseDto.setMessage("お薬の登録中にエラーが発生しました");           
                           }
                           return responseDto;                       
                        }
                    }
                }

                //本登録
                if(!isSeparated){
                    dosageId = dosageImportService.getNewDosageId();
                  
                }
                //setDto = getSetDto(recordString,phrId,dosageId);
                setDto = setService.getImportMedicineSet(recordString, phrId, dosageId);
            }else{
                responseDto.setStatus(ResponseBaseDto.ERROR);
                responseDto.setMessage("登録情報を取得できませんでした。再度取り込みをお願いいたします。");
                return responseDto;
            }
            
          if(setDto==null){
            responseDto.setStatus(ResponseBaseDto.ERROR);
            responseDto.setMessage("取得データが「JAHIS 電子版お薬手帳データフォーマット」形式ではありませんでした。再度取り込みを実行するか、手入力での登録をお願いいたします。");              
          }else{
               int result = dosageImportService.setDosage(setDto);
               if(isSeparated){
                   int delresult = dosageImportService.deleteSeparatorInfo(separatorId, sepDosageId);
                   result = result + delresult;
               }

               if(result>0){
                   responseDto.setStatus(ResponseBaseDto.SUCCESS);
               }else{
                    responseDto.setStatus(ResponseBaseDto.ERROR);
                    responseDto.setMessage("お薬の登録ができませんでした。");           
               }            
          }

            return responseDto;
        }catch (Throwable ex){
            logger.error("", ex);
            responseDto.setStatus(ResponseBaseDto.ERROR);
            responseDto.setMessage("適切に情報が読み取れませんでした。再度取込みを実行するか、手入力での登録をお願いいたします。");
            
            return responseDto;
        }
    }

    
    private DosageEntity setSepDosageEntity(String dosageId,String phrId) throws ParseException{
        DosageEntity entity = new DosageEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(0);
        entity.setPHR_ID(phrId);
        entity.setDosageDate(new Date());
        entity.setRecordCreatorType(RecordCreatorTypeCd.OTHER.getId());
        return entity;
    }

    
    private SeparatorInfoEntity setSeparatorInfo(Long separatorId,String dosageId,int sepNo,int sepCount,String sepText){
        SeparatorInfoEntity entity = new SeparatorInfoEntity();
        entity.setSeparatorId(separatorId);
        entity.setDosageId(dosageId);
        entity.setSeparateNo(sepNo);//分割の何番目
        entity.setSeparateCount(sepCount);//分割数
        entity.setSeparatText(sepText);
        return entity;
    }
     
    private SeparatorInfoEntity setSeparatorInfofromText(String[] importTextsplit,String dosageId,String sepText){
        Long separatorId = 0L;
        int sepNo = 0;
        int sepCount = 0;      
        for(String recText:importTextsplit){
            String[] recordSet = recText.split(",",-1);
            String recordno  = recordSet[0];
            if(recordno.equals(MedicineFormatRecordNo.SEPARATOR.getId())){
                String SepaRatorIdStr = recordSet[1];
                separatorId = Long.parseLong(SepaRatorIdStr);
                sepNo = Integer.parseInt(recordSet[3]);//分割の何番目か
                sepCount = Integer.parseInt(recordSet[2]);//分割の全件数  
            }
        }
        return setSeparatorInfo(separatorId,dosageId,sepNo,sepCount,sepText);
    }
        
    private boolean checklastLine(String importText,String verNo){
        boolean isFullRecord = true;
        if(verNo.equals(MedicineQRVersion.VERSION1_0)){
            
        }else if(verNo.equals(MedicineQRVersion.VERSION1_1)){
            
        }else if(verNo.equals(MedicineQRVersion.VERSION2_0)){
            
        }else if(verNo.equals(MedicineQRVersion.VERSION2_1)){
            
        }else if(verNo.equals(MedicineQRVersion.VERSION2_2)){
            
        }else if(verNo.equals(MedicineQRVersion.VERSION2_3)){
            
        }else if(verNo.equals(MedicineQRVersion.VERSION2_4)){
            
        }
        
        return isFullRecord;
    }
    
    private boolean checkStartLine(String recordNo,int recordcount,String verNo){
        boolean isStartRecNo = false;

        if(recordNo.equals(MedicineFormatRecordNo.DOSAGEHEAD)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==10){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==11){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.SPINST)){
            if(verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.NONPRESC)){
            if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==5){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGENOTE)){
            if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGE)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0) || verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==2){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.PHARMACY)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0)){
                if(recordcount==5){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==8){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==9){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.PHARMACIST)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0)){
                if(recordcount==2){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.MEDORGAN)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0) || verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==5){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==6){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.DOCTOR)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0) || verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.MEDICINE)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0) || verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==7){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==8){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.MEDADD)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0) || verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.MEDATTENT)){
            if(verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.RECIPE)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0) || verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==8){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==9){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.RECIPEADD)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0) || verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.RECATTENT)){
            if(verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGEATTENT)){
            if(verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==2){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.ORGANPROVINFO)){
            if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==4){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.UNUSEDDRUGINFO)){
            if(verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.REMARK)){
            if(verNo.equals(MedicineQRVersion.VERSION1_0) || verNo.equals(MedicineQRVersion.VERSION1_1)){
                if(recordcount==2){
                    isStartRecNo = true;
                }
            }else if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.PATINPUT)){
            if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==3){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.PARMACEUTIST)){
            if(verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==7){
                    isStartRecNo = true;
                }
            }
        }else if(recordNo.equals(MedicineFormatRecordNo.SEPARATOR)){
            if(verNo.equals(MedicineQRVersion.VERSION2_0) || verNo.equals(MedicineQRVersion.VERSION2_1) 
            		|| verNo.equals(MedicineQRVersion.VERSION2_2) || verNo.equals(MedicineQRVersion.VERSION2_3)
            		|| verNo.equals(MedicineQRVersion.VERSION2_4)){
                if(recordcount==11){
                    isStartRecNo = true;
                }
            }
        }

        return isStartRecNo;
    }

}

//    private DosageEntitySetDto getSetDto(String[] recordString,String phrId,String dosageId) throws Throwable{
//        DosageEntitySetDto setDto = new DosageEntitySetDto();
//                //DosageIdを取得する
//        //String dosageId = "";
//        String nonpreDosageId = "";
//        //各Seqを設定する
//        int pSISeq = 0;
//        int nopreSeq = 0;
//        int dNoteSeq = 0;
//        int dosageSeq = 0;
//        int medicineSeq = 0;
//        int medAddSeq = 0;
//        int medAttentSeq = 0;
//        int recAttentSeq = 0;
//        int recAddSeq = 0;
//        int dosageAttentSeq = 0;
//        int provSeq = 0;
//        int remarkSeq = 0;
//        int patInputSeq = 0;
//        int parmaCeutistSeq = 0;
//        //バージョン情報
//        String QRver = "";
//            for(int i=0;i<recordString.length;i++){
//                //カンマで分割する
//                String[] recordSet = recordString[i].split(",",-1);
//                String recordNo = recordSet[0];
//
//                if(recordNo.equals(MedicineFormatRecordNo.DOSAGEHEAD.getId())){
//                    //ヘッダ情報
//                    //setDto.setDosageHead(setHead(recordSet,QRver,dosageId));
//                    setDto.setDosageHeadListItem(setHead(recordSet,QRver,dosageId));
//                    //setDto.getDosageHead().setDosageId(dosageId);
//                }else if(recordNo.equals(MedicineFormatRecordNo.SPINST.getId())){
//                    // 患者特記情報
//                    pSISeq = pSISeq+1;
//                    setDto.setPatientSpecialInstruction(setPSInstruction(recordSet,dosageId,pSISeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.NONPRESC.getId())){
//                    //市販薬情報
//                    nopreSeq = nopreSeq + 1;
//                    //if(nonpreDosageId.isEmpty()){
//                        //市販薬用dosageId取得
//                        nonpreDosageId = dosageImportService.getNewDosageId();
//                        //DosagHead作成
//                        //setDto.setDosageHeadNonpre(setNonpreHead(setDto.getDosageHead(),nonpreDosageId));
//                        setDto.setDosageHeadNonpreListItem(setNonpreHead(setDto.getDosageHeadListItem(0),nonpreDosageId));
//                    //}
//                    setDto.setNonPrescriptionDrugs(setNonpre(recordSet,nonpreDosageId,nopreSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGENOTE.getId())){
//                    //手帳メモ情報
//                    dNoteSeq = dNoteSeq + 1;
//                    setDto.setDosageNote(setDosageNote(recordSet,dosageId,dNoteSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGE.getId())){
//                    //調剤情報
//                    if(setDto.getDosageList().isEmpty()){
//                        dosageSeq = 1;                        
//                    }else{
//                        dosageId = dosageImportService.getNewDosageId();
//                        pSISeq = 0;
//                        nopreSeq = 0;
//                        dNoteSeq = 0;
//                        dosageSeq = 1;
//                        medicineSeq = 0;
//                        medAddSeq = 0;
//                        medAttentSeq = 0;
//                        recAttentSeq = 0;
//                        recAddSeq = 0;
//                        dosageAttentSeq = 0;
//                        provSeq = 0;
//                        remarkSeq = 0;
//                        patInputSeq = 0;
//                        parmaCeutistSeq = 0;
//                        
//                        setDto.setDosageHeadListItem(setNonpreHead(setDto.getDosageHeadListItem(0),dosageId));
//                    }
//                    DosageEntity dEnt = setDosageEntity(recordSet,dosageId,dosageSeq,phrId); 
//                    setDto.setDosage(dEnt);
//                }else if(recordNo.equals(MedicineFormatRecordNo.PHARMACY.getId())){
//                    //薬局情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    if(setDto.getMedicalOrganizationList().size()>0){
//                        if(checkhospitalDosageSeq(setDto.getMedicalOrganizationList(),dosageId,DosageTypeCd.DOSAGE,dosageSeq)){
//                            dosageSeq = dosageSeq+1;
//                            for(DosageEntity dEnt:setDto.getDosageList()){
//                                if(dEnt.getSeq()==(dosageSeq-1)){
//                                    DosageEntity dosageent = setNewDosageEntity(dEnt,dosageId,dosageSeq,phrId); 
//                                    setDto.setDosage(dosageent);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                    setDto.setMedicalOrganization(setPharmacy(recordSet,dosageId,dosageSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.PHARMACIST.getId())){
//                    //薬剤師情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    if(setDto.getDosageDoctorList().size()>0){
//                        if(checkDoctorDosageSeq(setDto.getDosageDoctorList(),dosageId,DosageTypeCd.DOSAGE,dosageSeq)){
//                            dosageSeq = dosageSeq+1;
//                            for(DosageEntity dEnt:setDto.getDosageList()){
//                                if(dEnt.getSeq()==(dosageSeq-1)){
//                                    DosageEntity dosageent = setNewDosageEntity(dEnt,dosageId,dosageSeq,phrId);                                     
//                                    setDto.setDosage(dosageent);
//                                    break;
//                                }
//                            }
//                            for(DosageMedicalOrganizationEntity copypharm:setDto.getMedicalOrganizationList()){
//                                if(copypharm.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
//                                    if(copypharm.getSeq()==(dosageSeq-1)){
//                                        DosageMedicalOrganizationEntity phaEnt = setNewHospital(copypharm,dosageSeq);
//                                        setDto.setMedicalOrganization(phaEnt);
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    setDto.setDosageDoctor(setPharmacist(recordSet,dosageId,dosageSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.MEDORGAN.getId())){
//                    //医療機関情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    if(setDto.getMedicalOrganizationList().size()>1){
//                        if(checkhospitalDosageSeq(setDto.getMedicalOrganizationList(),dosageId,DosageTypeCd.RECIPE,dosageSeq)){
//                            dosageSeq = dosageSeq+1;
//                            for(DosageEntity dEnt:setDto.getDosageList()){
//                                if(dEnt.getSeq()==(dosageSeq-1)){
//                                    DosageEntity dosageent = setNewDosageEntity(dEnt,dosageId,dosageSeq,phrId);                                     
//                                    setDto.setDosage(dosageent);
//                                    break;
//                                }
//                            }
//                            for(DosageMedicalOrganizationEntity copypharm:setDto.getMedicalOrganizationList()){
//                                if(copypharm.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
//                                    if(copypharm.getSeq()==(dosageSeq-1)){
//                                        DosageMedicalOrganizationEntity phaEnt = setNewHospital(copypharm,dosageSeq);
//                                        setDto.setMedicalOrganization(phaEnt);
//                                        break;
//                                    }
//                                }
//                            }
//                            if(!setDto.getDosageDoctorList().isEmpty()){
//                                for(DosageDoctorEntity copydoc:setDto.getDosageDoctorList()){
//                                    if(copydoc.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
//                                        if(copydoc.getSeq()==(dosageSeq-1)){
//                                            DosageDoctorEntity docEnt = setNewDoctor(copydoc,dosageSeq);
//                                            setDto.setDosageDoctor(docEnt);
//                                            break;
//                                        }
//                                    }
//                                }         
//                            }
//                        }
//                    }
//                    setDto.setMedicalOrganization(setHospital(recordSet,dosageId,dosageSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.DOCTOR.getId())){
//                    //医師情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    if(setDto.getDosageDoctorList().size()>1){
//                        if(checkDoctorDosageSeq(setDto.getDosageDoctorList(),dosageId,DosageTypeCd.RECIPE,dosageSeq)){
//                            dosageSeq = dosageSeq+1;
//                            for(DosageEntity dEnt:setDto.getDosageList()){
//                                if(dEnt.getSeq()==(dosageSeq-1)){
//                                    DosageEntity dosageent = setNewDosageEntity(dEnt,dosageId,dosageSeq,phrId);                                     
//                                    setDto.setDosage(dosageent);
//                                    break;
//                                }
//                            }
//                            for(DosageMedicalOrganizationEntity copypharm:setDto.getMedicalOrganizationList()){
//                                if(copypharm.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
//                                    if(copypharm.getSeq()==(dosageSeq-1)){
//                                        DosageMedicalOrganizationEntity phaEnt = setNewHospital(copypharm,dosageSeq);
//                                        setDto.setMedicalOrganization(phaEnt);
//                                        break;
//                                    }
//                                }
//                            }
//                            for(DosageMedicalOrganizationEntity copypharm:setDto.getMedicalOrganizationList()){
//                                if(copypharm.getDosageTypeCd().equals(DosageTypeCd.RECIPE.getId())){
//                                    if(copypharm.getSeq()==(dosageSeq-1)){
//                                        DosageMedicalOrganizationEntity hospEnt = setNewHospital(copypharm,dosageSeq);
//                                        setDto.setMedicalOrganization(hospEnt);
//                                        break;
//                                    }
//                                }
//                            }
//                            if(!setDto.getDosageDoctorList().isEmpty()){
//                                for(DosageDoctorEntity copydoc:setDto.getDosageDoctorList()){
//                                    if(copydoc.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
//                                        if(copydoc.getSeq()==(dosageSeq-1)){
//                                            DosageDoctorEntity docEnt = setNewDoctor(copydoc,dosageSeq);
//                                            setDto.setDosageDoctor(docEnt);
//                                            break;
//                                        }
//                                    }
//                                }         
//                            }                            
//                        }
//                    }
//                    setDto.setDosageDoctor(setDoctor(recordSet,dosageId,dosageSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.MEDICINE.getId())){
//                    //薬剤情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }                    
//                    medicineSeq = medicineSeq + 1;
//                    setDto.setDosageMedicine(setMedicine(recordSet,dosageId,dosageSeq,medicineSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.MEDADD.getId())){
//                    //薬剤補足情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    medAddSeq = medAddSeq + 1;
//                    setDto.setMedicineAddition(setMedAddition(recordSet,dosageId,dosageSeq,medicineSeq,medAddSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.MEDATTENT.getId())){
//                    //薬品服用注意情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }                    
//                    medAttentSeq = medAttentSeq + 1;
//                    setDto.setMedicineAttention(setMedAttent(recordSet,dosageId,dosageSeq,medicineSeq,medAttentSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.RECIPE.getId())){
//                    //レシピ情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }                    
//                    setDto.setDosageRecipe(setRecipe(recordSet,dosageId,dosageSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.RECIPEADD.getId())){
//                    //用法補足情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }                    
//                    recAddSeq = recAddSeq + 1;
//                    setDto.setDosageRecipeAdd(setRecipeAdd(recordSet,dosageId,dosageSeq,recAddSeq));                    
//                }else if(recordNo.equals(MedicineFormatRecordNo.RECATTENT.getId())){
//                    //処方服用注意情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }                    
//                    recAttentSeq = recAttentSeq + 1;
//                    setDto.setRecipeAttention(setRecipeAttent(recordSet,dosageId,dosageSeq,recAttentSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGEATTENT.getId())){
//                    //服用注意情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    dosageAttentSeq = dosageAttentSeq +1;
//                    //int dosageSeqforDA = 0;
//                    //for(int l=0;l<dosageSeq;l++){
//                    //    dosageSeqforDA = dosageSeqforDA+1;
//                        setDto.setDosageAttention(setDosageAttent(recordSet,dosageId,dosageSeq,dosageAttentSeq));
//                    //    setDto.setDosageAttention(setDosageAttent(recordSet,dosageId,dosageSeqforDA,dosageAttentSeq));
//                    //}
//                }else if(recordNo.equals(MedicineFormatRecordNo.ORGANPROVINFO.getId())){
//                    //医療機関等提供情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    provSeq = provSeq +1;
//                    //int dosageSeqforPR = 0;
//                    //for(int l=0;l<dosageSeq;l++){
//                    //    dosageSeqforPR = dosageSeqforPR+1;
//                        setDto.setOrganProvisionInfo(setDosageOrganInfo(recordSet,dosageId,dosageSeq,provSeq));
//                        //setDto.setOrganProvisionInfo(setDosageOrganInfo(recordSet,dosageId,dosageSeqforPR,provSeq));                        
//                    //}
//                }else if(recordNo.equals(MedicineFormatRecordNo.REMARK.getId())){
//                    //備考情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    remarkSeq = remarkSeq + 1;
//                    //int dosageSeqforRM = 0;
//                    //for(int l=0;l<dosageSeq;l++){
//                    //    dosageSeqforRM = dosageSeqforRM+1;
//                        setDto.setDosageRemark(setDosageRemark(recordSet,dosageId,dosageSeq,remarkSeq));
//                        //setDto.setDosageRemark(setDosageRemark(recordSet,dosageId,dosageSeqforRM,remarkSeq));
//                    //}
//                }else if(recordNo.equals(MedicineFormatRecordNo.PATINPUT.getId())){
//                    //患者等記入情報
//                    if(dosageSeq==0){
//                        dosageSeq=1;
//                    }
//                    patInputSeq = patInputSeq +1;
//                    //int dosageSeqforPI =0;
//                    //for(int l=0;l<dosageSeq;l++){
//                    //    dosageSeqforPI= dosageSeqforPI+1;
//                        setDto.setPatientInput(setPatientInput(recordSet,dosageId,dosageSeq,patInputSeq));
//                        //setDto.setPatientInput(setPatientInput(recordSet,dosageId,dosageSeqforPI,patInputSeq));
//                    //}
//                }else if(recordNo.equals(MedicineFormatRecordNo.PARMACEUTIST.getId())){
//                    //かかりつけ薬剤師情報
//                    parmaCeutistSeq = parmaCeutistSeq + 1;
//                    setDto.setPharmaceutist(setPharmaceutist(recordSet,dosageId,parmaCeutistSeq));
//                }else if(recordNo.equals(MedicineFormatRecordNo.SEPARATOR.getId())){
//
//                }else if(i==0){
//                    QRver = recordNo;
//                    if(!recordNo.contains(MedicineQRVersion.FIXEDVALUE.getText())){
//                        return null;
//                    }
//                }
//            }
//            
//            //市販薬用Dosage作成
//            //if(setDto.getDosageHeadNonpre()==null){
//            if(setDto.getDosageHeadNonpreList().isEmpty()){    
//                //Dosageがない場合
//                if(setDto.getDosageList().isEmpty()){
//                        DosageEntity tmpEnt = new DosageEntity();
//                        tmpEnt.setDosageId(dosageId);
//                        if(dosageSeq==0){
//                            dosageSeq=1;
//                        }                    
//                        tmpEnt.setSeq(dosageSeq);
//                        tmpEnt.setDosageDate(new Date());
//                        tmpEnt.setPHR_ID(phrId);
//                        tmpEnt.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//                        setDto.setDosage(tmpEnt);
//                }                
//            }else{
//                for(NonPrescriptionDrugsEntity nDrugEnt:setDto.getNonPrescriptionDrugsList()){
//                    DosageEntity nonpreEnt = new DosageEntity();
//                    //nonpreEnt.setDosageId(nonpreDosageId);
//                    nonpreEnt.setDosageId(nDrugEnt.getDosageId());
//                    nonpreEnt.setSeq(nDrugEnt.getSeq());
//                    if(nDrugEnt.getStartDate()!=null){
//                        nonpreEnt.setDosageDate(nDrugEnt.getStartDate());
//                    }else if(nDrugEnt.getEndDate()!=null){
//                        nonpreEnt.setDosageDate(nDrugEnt.getEndDate());
//                    }else{
//                        nonpreEnt.setDosageDate(new Date());
//                    }
//                    nonpreEnt.setPHR_ID(phrId);
//                    nonpreEnt.setRecordCreatorType(nDrugEnt.getRecordCreatorType());
//                    setDto.setDosage(nonpreEnt);
//                }
//            }
//
//            //dosage,parmaceutist,dosageNote,patientSpecialInstructionを持たないDosageHeadは削除
//            //if(setDto.getDosageHead()!=null){
//            if(!setDto.getDosageHeadList().isEmpty()){    
//            if(setDto.getPharmaceutistList().isEmpty()){
//                if(setDto.getDosageNoteList().isEmpty()){
//                    if(setDto.getPatientSpecialInstructionList().isEmpty()){
//                        int dcount = 0;
//                        for(DosageEntity checkDosage:setDto.getDosageList()){
//                            for(DosageHeadEntity headent:setDto.getDosageHeadList()){
//                                if(checkDosage.getDosageId().equals(headent.getDosageId())){
//                                    dcount = dcount+1;
//                                }
//                            }
//                        }
//                        if(dcount==0){
//                            setDto.setDosageHeadList(null);
//                        }
//                    }
//                }
//            }
//            }
//                //Dosageに日付が入っていなかった場合
//                for(DosageEntity entity:setDto.getDosageList()){
//                    if(entity.getDosageDate()==null){
//                        if(setDto.getNonPrescriptionDrugsList().isEmpty()){
//                        //しょうがないので今日の日付
//                                entity.setDosageDate(new Date());
//                        }else{
//                            //市販薬がある場合
//                            for(NonPrescriptionDrugsEntity nmEnt:setDto.getNonPrescriptionDrugsList()){
//                                boolean getDate = false;
//                                if(nmEnt.getStartDate()!=null){
//                                    //(開始日)
//                                    getDate = true;
//                                    entity.setDosageDate(nmEnt.getStartDate());
//                                }else if(nmEnt.getEndDate()!=null){
//                                    //(終了日)
//                                    getDate = true;
//                                    entity.setDosageDate(nmEnt.getEndDate());                            
//                                }
//
//                                if(!getDate){
//                                    //しょうがないので今日の日付
//                                    entity.setDosageDate(new Date());    
//                                }                
//                            }
//                        }
//
//                    }
//                }        
//
//        return setDto;
//    }
    
    
//    private DosageHeadEntity setHead(String[] recordSet,String QRver,String dosageId) throws ParseException{
//        DosageHeadEntity entity = new DosageHeadEntity();
//        entity.setQRVersion(QRver);
//        entity.setDosageId(dosageId);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                   entity.setPatientName(recordSet[1]);
//                   break;
//                case 2:
//                    entity.setSexCd(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setBirthDate(StrToDate(recordSet[3]));
//                    break;
//                case 4:
//                    entity.setZipCode(recordSet[4]);
//                    break;
//                case 5:
//                    entity.setAddressLine(recordSet[5]);
//                    break;
//                case 6:
//                    entity.setTelNo(recordSet[6]);
//                    break;
//                case 7:
//                    entity.setEmergencyContact(recordSet[7]);
//                    break;
//                case 8:
//                    entity.setBloodType(recordSet[8]);
//                    break;
//                case 9:
//                    entity.setWeight(recordSet[9]);
//                    break;
//                case 10:
//                    entity.setNameInKana(recordSet[10]);
//                    break;
//            }
//        }
//        return entity;
//    }
//
//    private DosageHeadEntity setNonpreHead(DosageHeadEntity orgHead,String dosageId) throws ParseException{
//        DosageHeadEntity entity = new DosageHeadEntity();
//        entity.setDosageId(dosageId);
//        entity.setQRVersion(orgHead.getQRVersion());
//        if(orgHead.getPatientName()!=null){
//            entity.setPatientName(orgHead.getPatientName());
//        }
//        if(orgHead.getSexCd()!=null){
//            entity.setSexCd(orgHead.getSexCd());
//        }
//        if(orgHead.getBirthDate()!=null){
//            entity.setBirthDate(orgHead.getBirthDate());
//        }
//        if(orgHead.getZipCode()!=null){
//            entity.setZipCode(orgHead.getZipCode());
//        }
//        if(orgHead.getAddressLine()!=null){
//            entity.setAddressLine(orgHead.getAddressLine());            
//        }
//        if(orgHead.getTelNo()!=null){
//            entity.setTelNo(orgHead.getTelNo());
//        }
//        if(orgHead.getEmergencyContact()!=null){
//            entity.setEmergencyContact(orgHead.getEmergencyContact());
//        }
//        if(orgHead.getBloodType()!=null){
//            entity.setBloodType(orgHead.getBloodType());
//        }
//        if(orgHead.getWeight()!=null){
//            entity.setWeight(orgHead.getWeight());
//        }
//        if(orgHead.getNameInKana()!=null){
//            entity.setNameInKana(orgHead.getNameInKana());
//        }
//        return entity;
//    }    
//    
//    private PatientSpecialInstructionEntity setPSInstruction(String[] recordSet,String dosageId,int pSISeq){
//        PatientSpecialInstructionEntity entity = new PatientSpecialInstructionEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(pSISeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setTypeCd(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setNoteValue(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private NonPrescriptionDrugsEntity setNonpre(String[] recordSet,String dosageId,int nopreSeq) throws ParseException{
//        NonPrescriptionDrugsEntity entity = new NonPrescriptionDrugsEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(nopreSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setMedicineName(recordSet[1]); 
//                    break;
//                case 2:
//                    entity.setStartDate(StrToDate(recordSet[2]));
//                    break;
//                case 3:
//                    entity.setEndDate(StrToDate(recordSet[3]));
//                    break;
//                case 4:
//                    entity.setRecordCreatorType(recordSet[4]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosageNoteEntity setDosageNote(String[] recordSet,String dosageId,int dNoteSeq) throws ParseException{
//        DosageNoteEntity entity = new DosageNoteEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dNoteSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setNoteValue(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setInputDate(StrToDate(recordSet[2]));
//                    break;
//                case 3:
//                    entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosageEntity setDosageEntity(String[] recordSet,String dosageId,int dosageSeq,String phrId) throws ParseException{
//        DosageEntity entity = new DosageEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setPHR_ID(phrId);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setDosageDate(StrToDate(recordSet[1]));
//                    break;
//                case 2:
//                    entity.setRecordCreatorType(recordSet[2]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//
//    private DosageEntity setNewDosageEntity(DosageEntity orgEntity,String dosageId,int dosageSeq,String phrId){
//        DosageEntity entity = new DosageEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setDosageDate(orgEntity.getDosageDate());
//        entity.setPHR_ID(phrId);
//        entity.setRecordCreatorType(orgEntity.getRecordCreatorType());
//        return entity;
//    }    
//    

//      
//    private DosageMedicalOrganizationEntity setPharmacy(String[] recordSet,String dosageId, int dosageSeq){
//        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);  
//        entity.setDosageTypeCd(DosageTypeCd.DOSAGE.getId());
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setMedicalOrganizationName(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setPrefectureCd(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setOrganizationType(recordSet[3]);
//                    break;
//                case 4:
//                    entity.setMedicalOrganizationCd(recordSet[4]);
//                    break;
//                case 5:
//                    entity.setZipCode(recordSet[5]);
//                    break;
//                case 6:
//                    entity.setAddressLine(recordSet[6]);
//                    break;
//                case 7:
//                    entity.setTelNo(recordSet[7]);
//                    break;
//                case 8:
//                    entity.setRecordCreatorType(recordSet[8]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//
//    private DosageDoctorEntity setPharmacist(String[] recordSet,String dosageId,int dosageSeq){
//        DosageDoctorEntity entity = new DosageDoctorEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setDosageTypeCd(DosageTypeCd.DOSAGE.getId());
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setDoctorName(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setContactAddress(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
////        entity.setDepartmentName();
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosageMedicalOrganizationEntity setHospital(String[] recordSet,String dosageId, int dosageSeq){
//        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);  
//        entity.setDosageTypeCd(DosageTypeCd.RECIPE.getId());
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setMedicalOrganizationName(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setPrefectureCd(recordSet[2]);        
//                    break;
//                case 3:
//                    entity.setOrganizationType(recordSet[3]);
//                    break;
//                case 4:
//                    entity.setMedicalOrganizationCd(recordSet[4]);
//                    break;
//                case 5:
//                    entity.setRecordCreatorType(recordSet[5]);
//                    break;
//            }
//        }
//        //entity.setZipCode(recordSet[5]);
//        //entity.setAddressLine(recordSet[6]);
//        //entity.setTelNo(recordSet[7]);
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosageMedicalOrganizationEntity setNewHospital(DosageMedicalOrganizationEntity oldHospital, int dosageSeq){
//        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
//        entity.setDosageId(oldHospital.getDosageId());
//        entity.setSeq(dosageSeq);  
//        entity.setDosageTypeCd(oldHospital.getDosageTypeCd());
//        if(oldHospital.getMedicalOrganizationName()!=null){
//            entity.setMedicalOrganizationName(oldHospital.getMedicalOrganizationName());
//        }
//        if(oldHospital.getMedicalOrganizationCd()!=null){
//        entity.setMedicalOrganizationCd(oldHospital.getMedicalOrganizationCd());
//        }
//        if(oldHospital.getOrganizationType()!=null){
//        entity.setOrganizationType(oldHospital.getOrganizationType());
//        }
//        if(oldHospital.getPrefectureCd()!=null){
//            entity.setPrefectureCd(oldHospital.getPrefectureCd());
//        }
//        if(oldHospital.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
//            if(oldHospital.getZipCode()!=null){
//                entity.setZipCode(oldHospital.getZipCode());
//            }
//            if(oldHospital.getAddressLine()!=null){
//                entity.setAddressLine(oldHospital.getAddressLine());
//            }
//            if(oldHospital.getTelNo()!=null){
//                entity.setTelNo(oldHospital.getTelNo());
//            }
//        }
//        entity.setRecordCreatorType(oldHospital.getRecordCreatorType());
//        
//        return entity;
//    }
//    
//    private DosageDoctorEntity setDoctor(String[] recordSet,String dosageId,int dosageSeq){
//        DosageDoctorEntity entity = new DosageDoctorEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setDosageTypeCd(DosageTypeCd.RECIPE.getId());
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setDoctorName(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setDepartmentName(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        //        entity.setContactAddress();
//        return entity;
//    }
//    
//    private DosageDoctorEntity setNewDoctor(DosageDoctorEntity orgDoc,int dosageSeq){
//        DosageDoctorEntity entity = new DosageDoctorEntity();
//        entity.setDosageId(orgDoc.getDosageId());
//        entity.setSeq(dosageSeq);
//        entity.setDosageTypeCd(orgDoc.getDosageTypeCd());
//        if(orgDoc.getDoctorName()!=null){
//            entity.setDoctorName(orgDoc.getDoctorName());
//        }
//        if(orgDoc.getRecordCreatorType()!=null){
//            entity.setRecordCreatorType(orgDoc.getRecordCreatorType());
//        }
//        if(orgDoc.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
//            if(orgDoc.getContactAddress()!=null){
//                entity.setContactAddress(orgDoc.getContactAddress());
//            }
//        }else{
//            if(orgDoc.getDepartmentName()!=null){
//                entity.setDepartmentName(orgDoc.getDepartmentName());
//            }
//        }
//        return entity;
//    }    
//
//    private DosageMedicineEntity setMedicine(String[] recordSet,String dosageId,int dosageSeq,int medicineSeq){
//        DosageMedicineEntity entity = new DosageMedicineEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setMedicineSeq(medicineSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
//                    break;
//                case 2:
//                    entity.setMedicineName(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setAmount(recordSet[3]);
//                    break;
//                case 4:
//                    entity.setUnitName(recordSet[4]);
//                    break;
//                case 5:
//                    entity.setMedicineCdType(recordSet[5]);
//                    break;
//                case 6:
//                    entity.setMedicineCd(recordSet[6]);
//                    break;
//                case 7:
//                    entity.setRecordCreatorType(recordSet[7]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosageMedicineAdditionEntity setMedAddition(String[] recordSet,String dosageId,int dosageSeq,int medicineSeq,int medAddSeq){
//        DosageMedicineAdditionEntity entity = new DosageMedicineAdditionEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setMedicineSeq(medicineSeq);
//        entity.setAdditionSeq(medAddSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
//                    break;
//                case 2:
//                    entity.setAdditionText(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//
//    private DosageMedicineAttentionEntity setMedAttent(String[] recordSet,String dosageId,int dosageSeq,int medicineSeq,int medAttentSeq){
//        DosageMedicineAttentionEntity entity = new DosageMedicineAttentionEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setMedicineSeq(medicineSeq);
//        entity.setAttentionSeq(medAttentSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
//                    break;
//                case 2:
//                    entity.setAttentionText(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosageRecipeEntity setRecipe(String[] recordSet,String dosageId,int dosageSeq){
//        DosageRecipeEntity entity = new DosageRecipeEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
//                    break;
//                case 2:
//                    entity.setUsageName(recordSet[2]);
//                    break;
//                case 3:        
//                    entity.setDosageQuantity(Integer.parseInt(recordSet[3]));
//                    break;
//                case 4:
//                    entity.setDosageUnit(recordSet[4]);
//                    break;
//                case 5:
//                    entity.setDosageForms(recordSet[5]);
//                    break;
//                case 6:
//                    entity.setUsageCdType(recordSet[6]);
//                    break;
//                case 7:
//                    entity.setUsageCd(recordSet[7]);
//                    break;
//                case 8:
//                    entity.setRecordCreatorType(recordSet[8]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//
//    private DosageRecipeAdditionEntity setRecipeAdd(String[] recordSet,String dosageId,int dosageSeq,int recAddSeq){
//         DosageRecipeAdditionEntity entity = new  DosageRecipeAdditionEntity();
//         entity.setDosageId(dosageId);
//         entity.setSeq(dosageSeq);
//         entity.setRecipeAdditionSeq(recAddSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:         
//                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
//                    break;
//                case 2:
//                     entity.setAdditionText(recordSet[2]);
//                    break;
//                case 3:
//                     entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosageRecipeAttentionEntity setRecipeAttent(String[] recordSet,String dosageId,int dosageSeq,int recAttenetSeq){
//         DosageRecipeAttentionEntity entity = new  DosageRecipeAttentionEntity();
//         entity.setDosageId(dosageId);
//         entity.setSeq(dosageSeq);
//         entity.setAttentionSeq(recAttenetSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:         
//                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
//                    break;
//                case 2:
//                     entity.setAttentionText(recordSet[2]);
//                    break;
//                case 3:
//                     entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosageAttentionEntity setDosageAttent(String[] recordSet,String dosageId,int dosageSeq,int dosageAttentSeq){
//         DosageAttentionEntity entity = new  DosageAttentionEntity();
//         entity.setDosageId(dosageId);
//         entity.setSeq(dosageSeq);
//         entity.setAttentionSeq(dosageAttentSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setAttentionText(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setRecordCreatorType(recordSet[2]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//         return entity;
//    }    
//
//    private DosageOrganProvisionInfoEntity setDosageOrganInfo(String[] recordSet,String dosageId,int dosageSeq,int provSeq){
//        DosageOrganProvisionInfoEntity entity = new  DosageOrganProvisionInfoEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setProvisionSeq(provSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setProvisionText(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setProvisionType(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setRecordCreatorType(recordSet[3]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }   
//
//    private DosageRemarkEntity setDosageRemark(String[] recordSet,String dosageId,int dosageSeq,int remarkSeq){
//        DosageRemarkEntity entity = new  DosageRemarkEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setRemarkSeq(remarkSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:        
//                    entity.setRemarkText(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setRecordCreatorType(recordSet[2]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }
//    
//    private DosagePatientInputEntity setPatientInput(String[] recordSet,String dosageId,int dosageSeq,int patInputSeq) throws ParseException{
//        DosagePatientInputEntity entity = new  DosagePatientInputEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(dosageSeq);
//        entity.setInputSeq(patInputSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setInputText(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setInputDate(StrToDate(recordSet[2]));
//                    break;
//            }
//        }
//        return entity;
//    }    
//
//    private PharmaceutistEntity setPharmaceutist(String[] recordSet,String dosageId,int parmaCeutistSeq) throws ParseException{
//        PharmaceutistEntity entity = new PharmaceutistEntity();
//        entity.setDosageId(dosageId);
//        entity.setSeq(parmaCeutistSeq);
//        for(int i=0;i<recordSet.length;i++){
//            switch(i){
//                case 1:
//                    entity.setPharmaceutistName(recordSet[1]);
//                    break;
//                case 2:
//                    entity.setPharmacy(recordSet[2]);
//                    break;
//                case 3:
//                    entity.setContactAddress(recordSet[3]);
//                    break;
//                case 4:
//                    entity.setStartDate(StrToDate(recordSet[4]));
//                    break;
//                case 5:
//                    entity.setEndDate(StrToDate(recordSet[5]));
//                    break;
//                case 6:
//                    entity.setRecordCreatorType(recordSet[6]);
//                    break;
//            }
//        }
//        if(entity.getRecordCreatorType()==null){
//            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
//        }
//        return entity;
//    }

//    private Date StrToDate(String dateStr) throws ParseException{
//        Date returnDate = null;
//        Locale loc = new Locale("jp","jp","jp");
//        //Calendar cal = Calendar.getInstance(loc);
//        DateFormat form = new SimpleDateFormat("yyyyMMdd",loc);
//        if(dateStr.length()==8){
//            //DateFormat form = new SimpleDateFormat("yyyyMMdd",loc);
//            returnDate = form.parse(dateStr);
//        }else if(dateStr.length()==7){
//            //DateFormat jForm = new SimpleDateFormat("GyyMMdd",loc);
//            //returnDate = jForm.parse(dateStr);
//            String eraName = dateStr.substring(0,1);
//            int jpYear = Integer.parseInt(dateStr.substring(1,3));
//            String monthday = dateStr.substring(3);
//            int year = 0;
//            if(eraName.equals("M")){
//               year = 1867 + jpYear; 
//            }else if(eraName.equals("T")){
//                year = 1911 + jpYear;
//            }else if(eraName.equals("S")){
//                year = 1925 + jpYear;
//            }else if(eraName.equals("H")){
//                year = 1988 + jpYear;
//            }
//            String yearStr = Integer.toString(year);
//            String fulldateStr = yearStr + monthday;
//            returnDate = form.parse(fulldateStr);
//        }
//        return returnDate;
//    }
//    
//    private boolean checkhospitalDosageSeq(List<DosageMedicalOrganizationEntity> hospitallist,String dosageId,DosageTypeCd targetType,int dosageSeq){
//        for(DosageMedicalOrganizationEntity entity:hospitallist){
//            if(entity.getDosageId().equals(dosageId)){
//            if(entity.getDosageTypeCd().equals(targetType.getId())){
//                if(entity.getSeq()==dosageSeq){
//                   return true; 
//                }
//            }
//            }
//        }
//        return false;
//    }    
//
//    private boolean checkDoctorDosageSeq(List<DosageDoctorEntity> doctorlist,String dosageId,DosageTypeCd targetType,int dosageSeq){
//        for(DosageDoctorEntity entity:doctorlist){
//            if(entity.getDosageId().equals(dosageId)){
//            if(entity.getDosageTypeCd().equals(targetType.getId())){
//                if(entity.getSeq()==dosageSeq){
//                   return true; 
//                }
//            }
//            }
//        }
//        return false;
//    }