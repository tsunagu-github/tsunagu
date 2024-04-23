/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.MedicineNoteBoookDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.ObservationDefinitionTypeAdapter;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.RecordCreatorTypeCd;
import phr.datadomain.entity.SeparatorComparator;
import phr.datadomain.entity.SeparatorInfoEntity;
import phr.dto.DosageEntitySetDto;
import phr.dto.MedicineFormatRecordNo;
import phr.service.IDosageImportService;
import phr.service.IImportMedicineSetService;
import phr.service.IObservationEntryService;
import phr.service.IRegistModelMedicineNoteBookService;


/**
 *
 * アラート一覧画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class RegistModelMedicineNoteBookService implements IRegistModelMedicineNoteBookService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RegistModelMedicineNoteBookService.class);
    
    /**
     * 検索用Dtoクラス
     * ObservationDefinitionIDと登録タイプをいれる
     */
    private class RegistDto{
        private ObservationEventEntity event;
        private List<ObservationEntity> obList;

        /**
         * @return the event
         */
        public ObservationEventEntity getEvent() {
            return event;
        }

        /**
         * @param event the event to set
         */
        public void setEvent(ObservationEventEntity event) {
            this.event = event;
        }

        /**
         * @return the obList
         */
        public List<ObservationEntity> getObList() {
            return obList;
        }

        /**
         * @param obList the obList to set
         */
        public void setObList(List<ObservationEntity> obList) {
            this.obList = obList;
        }
        
    }
    /**
     * 検索用Dtoクラス
     * ObservationDefinitionIDと登録タイプをいれる
     */
    private class CodeDto{
        private String id;
        private int type;

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the type
         */
        public int getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(int type) {
            this.type = type;
        }
    }
    
    /**
     * リストア時のお薬手帳情報の登録処理
     * @param phrid
     * @param dto
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean modelMNB(String phrid, MedicineNoteBoookDto dto) throws Throwable {
        logger.debug("おくすり1件の登録開始");

        String insurerNo = getInsurerNo(phrid);
        if(insurerNo == null){
            logger.debug("登録されていないため終了");
            return false;
        }

        //処理年度はすべてリストア時の年度とする。
        String year  = yearCH();
        logger.debug("対象年度 : " + year);
        
//        List<ObservationDefinitionTypeEntity> typeList = getTypeList(insurerNo , year);
//        Map<String , CodeDto> codeMap = makeCodeMap(typeList);
        IDosageImportService dosageImportService = new DosageImportService();
        IImportMedicineSetService setService = new ImportMedicineSetService();
        //boolean notTargetFlg = false;
        String dosageId = null;
        String sepDosageId = null;
        Long separatorId = 0l;
        DosageEntitySetDto setDto = new DosageEntitySetDto();
        boolean isSeparated = false;
        
        //原文情報を取得
        String orgMessage = dto.getOriginalText();
        String[] recordString= orgMessage.split("\r\n");

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
                        //notTargetFlg = true;
                        return false;
                    }
                }                        
                //データの結合または保存
                if(!seplist.isEmpty() && (seplist.size() + 1)==sepCount){
                    dosageId = seplist.get(0).getDosageId();
                    sepDosageId = seplist.get(0).getDosageId();
                    seplist.add(setSeparatorInfo(separatorId,dosageId,sepNo,sepCount,orgMessage));
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
                        setDto.setDosage(setSepDosageEntity(dosageId,phrid));
                    }else{
                       dosageId = seplist.get(0).getDosageId();
                    }

                   setDto.setSeparatorInfo(setSeparatorInfo(separatorId,dosageId,sepNo,sepCount,orgMessage));
                   int sepresult = dosageImportService.setDosage(setDto);
                   if(sepresult>0){
                       logger.debug("分割データを保存いたしました。");
                   }else{
                        logger.debug("分割データの登録中にエラーが発生しました");           
                   }
                   return true;                       
                }
            }
        }

        //本登録
        if(!isSeparated){
            dosageId = dosageImportService.getNewDosageId();
        }
        //setDto = getSetDto(recordString,phrId,dosageId);
        setDto = setService.getImportMedicineSet(recordString, phrid, dosageId);

        if(setDto==null){
            logger.debug("取得データが「JAHIS 電子版お薬手帳データフォーマット」形式ではありませんでした:" + orgMessage);              
        }else{
            int result = dosageImportService.setDosage(setDto);
            if(isSeparated){
                int delresult = dosageImportService.deleteSeparatorInfo(separatorId, sepDosageId);
                result = result + delresult;
            }

            if(result<=0){
                logger.debug("お薬の登録ができませんでした。");
                return false;
            }            
        }                
        
        logger.debug("おくすり1件の登録終了");
        return true;
    }

    private String getInsurerNo(String phrid) throws SQLException, ClassNotFoundException {
        DataAccessObject dao = new DataAccessObject();
        InsurerPatientEntity entity = null;
    	try{
            InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
            dao.beginTransaction();
            
            entity = adapter.findByPhrId(phrid);

     	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	} catch (Throwable ex) {
            Logger.getLogger(RegistModelMedicineNoteBookService.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

        logger.trace("end");
        return entity.getInsurerNo();
    }

    private String yearCH() {
        SimpleDateFormat sdf_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        Date date = new Date();
        
        String year = sdf_y.format(date);
        String month = sdf_m.format(date);
        month = month.replace("0","");
        
        Integer month_i = Integer.parseInt(month);
        Integer year_i = Integer.parseInt(year);
        if(month_i <= 3){
            year_i = year_i - 1;
        }
        
        return year_i.toString();
    }
    
    private List<ObservationDefinitionTypeEntity> getTypeList(String insurerNo, String year) throws SQLException, ClassNotFoundException {
        DataAccessObject dao = new DataAccessObject();
        List<ObservationDefinitionTypeEntity> entityList =new ArrayList<>();
        List<ObservationDefinitionTypeEntity> entityList2 = new ArrayList<>();
    	try{
            ObservationDefinitionTypeAdapter adapter = new ObservationDefinitionTypeAdapter(dao.getConnection());
            dao.beginTransaction();
            
            entityList = adapter.findByInsurerList(insurerNo, 1);
            entityList2 = adapter.findByInsurerList(insurerNo, 3);

            if(entityList2.size() > 0){
                for(ObservationDefinitionTypeEntity entity : entityList2){
                    entityList.add(entity);
                }
            }
     	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	} catch (Throwable ex) {
            Logger.getLogger(RegistModelMedicineNoteBookService.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

        logger.trace("end");
        return entityList;       
    }
    
    
    private Map<String, CodeDto> makeCodeMap(List<ObservationDefinitionTypeEntity> typeList) {
        logger.debug("Map作成開始");
        Map<String , CodeDto> codeMap = new HashMap<String , CodeDto>();
        
        for(ObservationDefinitionTypeEntity entity : typeList){
            String jlac10 = entity.getJlac10();
            CodeDto dto = new CodeDto();
            dto.setId(entity.getObservationDefinitionId());
            dto.setType(entity.getDataInputTypeCd());
            codeMap.put(jlac10, dto);
        }
        
        logger.debug("Map作成終了");
        return codeMap;
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

    private DosageEntity setSepDosageEntity(String dosageId,String phrId) throws ParseException{
        DosageEntity entity = new DosageEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(0);
        entity.setPHR_ID(phrId);
        entity.setDosageDate(new Date());
        entity.setRecordCreatorType(RecordCreatorTypeCd.OTHER.getId());
        return entity;
    }    
}
