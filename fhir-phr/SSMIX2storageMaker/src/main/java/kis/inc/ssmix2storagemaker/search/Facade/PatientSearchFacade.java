/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.search.Facade;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.Enums.DataTypeEnum;
import kis.inc.ssmix2storagemaker.Facade.StorageToModelFacade;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchResultDto;

/**
 *
 * @author kis-note
 */
public class PatientSearchFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientSearchFacade.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public List<SSMIXSearchResultDto> search(SSMIXSearchDto searchDto){
        logger.debug("Start");
        List<SSMIXSearchResultDto> resultList = new ArrayList<>();

        String patientId = searchDto.getPatientId();
        logger.debug("検索対象患者ID : " + patientId);

        //検索対象のデータ種別の取得
        List<String> typeList = getTypeList(searchDto.getDataType());

        for(String type : typeList){
            logger.debug("検索対象のデータ種別 :" + type);
            SSMIXSearchResultDto resultDto = new SSMIXSearchResultDto();
            String path ="";
            if(DataTypeEnum.selectType(type).equals("STD")){
                path = searchDto.getStdRootPath();
            }else{
                path = searchDto.getExRootPath();
            }

            String baseDate = searchDto.getBaseDate();
            Integer searchDirection = searchDto.getSearchDirection();
            Integer count = searchDto.getCount();
            Integer datecount = searchDto.getDatecount();

            String ftid = patientId.substring(0, 3);
            String sdid = patientId.substring(3, 6);

            path = path + "/" + ftid + "/" + sdid + "/" + patientId;

            if(!DataTypeEnum.selectFlg(type)){
                baseDate = "-";
                count = 1;
            }

            resultDto = execute(path , type , patientId , baseDate , searchDirection , count , datecount);
            resultList.add(resultDto);
        }


        logger.debug("End");
        return resultList;
    }

    public List<SSMIXSearchResultDto> search2(SSMIXSearchDto searchDto){
        logger.debug("Start");
        List<SSMIXSearchResultDto> resultList = new ArrayList<>();

        String patientId = searchDto.getPatientId();
        logger.debug("検索対象患者ID : " + patientId);

        //検索対象のデータ種別の取得
        List<String> typeList = getTypeList(searchDto.getDataType());

        for(String type : typeList){
            logger.debug("検索対象のデータ種別 :" + type);
            SSMIXSearchResultDto resultDto = new SSMIXSearchResultDto();
            String path ="";
            if(DataTypeEnum.selectType(type).equals("STD")){
                path = searchDto.getStdRootPath();
            }else{
                path = searchDto.getExRootPath();
            }

            String baseDate = searchDto.getBaseDate();
            Integer searchDirection = searchDto.getSearchDirection();
            Integer count = searchDto.getCount();
            Integer datecount = searchDto.getDatecount();

            String ftid = patientId.substring(0, 3);
            String sdid = patientId.substring(3, 6);

            path = path + "/" + ftid + "/" + sdid + "/" + patientId;

            if(!DataTypeEnum.selectFlg(type)){
                baseDate = "-";
                count = 1;
            }

            resultDto = execute2(path , type , patientId , baseDate , searchDirection , count , datecount);
            resultList.add(resultDto);
        }


        logger.debug("End");
        return resultList;
    }

    public ModelDto search3(File searchFile, String type){
        logger.debug("Start");
        logger.debug(searchFile);

        ModelDto modelDto = execute3(searchFile , type);

        logger.debug("End");
        return modelDto;
    }

    private List<String> getTypeList(String dataType) {
        logger.debug("検索対象のデータ種別のリスト化開始");
        List<String> typeList = new ArrayList<String>();

        switch(dataType){
            case "STD":
                typeList = DataTypeEnum.selectSType(dataType);
                break;
            case "EXT":
                typeList = DataTypeEnum.selectSType(dataType);
                break;
            case "ALL":
                typeList = DataTypeEnum.selectSType("STD");
                List<String> typeEXTList = DataTypeEnum.selectSType("EXT");
                for(String ext : typeEXTList){
                    typeList.add(ext);
                }
                break;
            default :
                typeList.add(dataType);
        }
        logger.debug("検索対象のデータ種別のリスト化終了");

        return typeList;
    }

    private SSMIXSearchResultDto execute(String path, String type, String patientId, String baseDate, Integer searchDirection, Integer count , Integer datecount) {
        logger.debug("検索開始");
        boolean dateFlg = true;
        File base = new File(path);

        //存在しない場合は終了
        if(!base.exists()) return null;

        //診療日からMapを作成する
        File[] targets = base.listFiles();
        Map<String , File> cdateMap = dateCH(targets , baseDate , searchDirection , datecount);

        //処理を行う順番のリストを作成
        List<File> fileList = targetList(cdateMap ,searchDirection);

        //リストの順番に対象のデータ種別の有無を確認して検索を行い、対象ファイルを取得する。
        List<File> searchList = null;
        if(DataTypeEnum.selectType(type).equals("STD")){
           searchList = searchFile(fileList , type , count);
        }else{
            searchList = searchExtFile(fileList , type , count);
        }
        //対象ファイルリストからモデルを作成
        List<ModelDto> modelList = new ArrayList<>();
        for(File search : searchList){
            StorageToModelFacade stmf = new StorageToModelFacade();
            logger.debug(search.getPath());
            try {
                ModelDto dto = new ModelDto();
                dto = stmf.exChangeToModel(type, search);
                modelList.add(dto);
            } catch (IOException ex) {
                Logger.getLogger(PatientSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        SSMIXSearchResultDto resultDto = new SSMIXSearchResultDto();
        resultDto.setPatientId(patientId);
        resultDto.setModelList(modelList);

        logger.debug("検索終了");
        return resultDto;

    }

    private SSMIXSearchResultDto execute2(String path, String type, String patientId, String baseDate, Integer searchDirection, Integer count , Integer datecount) {
        logger.debug("検索開始");

        File base = new File(path);

        //存在しない場合は終了
        if(!base.exists()) return null;

        //診療日からMapを作成する
        File file = new File(path + File.separator + baseDate);
        File[] targets = {file};
        Map<String , File> cdateMap = dateCH(targets , baseDate , searchDirection , datecount);

        //処理を行う順番のリストを作成
        List<File> fileList = targetList(cdateMap ,searchDirection);

        //リストの順番に対象のデータ種別の有無を確認して検索を行い、対象ファイルを取得する。
        List<File> searchList = null;
        if(DataTypeEnum.selectType(type).equals("STD")){
            searchList = searchFile(fileList , type , count);
        }else{
            searchList = searchExtFile(fileList , type , count);
        }
        Collections.sort(searchList);

        //対象ファイルリストからモデルを作成
        List<ModelDto> modelList = new ArrayList<>();
        for(File search : searchList){
            StorageToModelFacade stmf = new StorageToModelFacade();
            logger.debug(search.getPath());
            try {
                ModelDto dto = new ModelDto();
                dto = stmf.exChangeToModel(type, search);
                modelList.add(dto);
            } catch (IOException ex) {
                Logger.getLogger(PatientSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        SSMIXSearchResultDto resultDto = new SSMIXSearchResultDto();
        resultDto.setPatientId(patientId);
        resultDto.setModelList(modelList);

        logger.debug("検索終了");
        return resultDto;

    }

    private ModelDto execute3(File searchFile, String type) {
        logger.debug("検索開始");

        ModelDto dto = null;

        //存在しない場合は終了
        if(Objects.isNull(searchFile) || Objects.isNull(type)) return dto;

        //対象ファイルリストからモデルを作成
        StorageToModelFacade stmf = new StorageToModelFacade();
        try {
            dto = stmf.exChangeToModel(type, searchFile);
        } catch (IOException ex) {
            Logger.getLogger(PatientSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        logger.debug("検索終了");
        return dto;

    }

    private Map<String, File> dateCH(File[] targets, String baseDate, Integer searchDirection , Integer datecount) {
        logger.debug("診療日のMap作成開始");
        Map<String, File> cdateMap = new TreeMap<>();

        for(File target : targets){
            String cdate = target.getName();
            if(baseDate.equals("-")){
                if(cdate.equals("-")){
                    cdateMap.put(cdate, target);
                    break;
                }
            }else{
                if(cdate.equals("-")) continue;

                //過去検索
                if(searchDirection == 1){
                    if(Integer.parseInt(baseDate) >= Integer.parseInt(cdate))
                        if(datecount != null && !dateCal(baseDate , cdate , datecount)){
                            continue;
                        }else{
                            cdateMap.put(cdate, target);
                        }
                }else{
                    if(Integer.parseInt(baseDate) <= Integer.parseInt(cdate))
                        if(datecount != null && !dateCal(cdate , baseDate , datecount)){
                            continue;
                        }else{
                            cdateMap.put(cdate, target);
                        }
                }
            }
        }
        logger.debug("診療日のMap作成終了");
        return cdateMap;
    }

    private List<File> targetList(Map<String, File> cdateMap, Integer searchDirection) {
        logger.debug("処理リストの作成開始");
        List<String> cdateList = new ArrayList<>();

        for(String key : cdateMap.keySet()){
            cdateList.add(key);
        }

        List<File> resultList = new ArrayList<>();
        //過去検索
        if(searchDirection == 1){
            for(int n=cdateList.size()-1; n >= 0 ; n-- ){
                String target = cdateList.get(n);
                resultList.add(cdateMap.get(target));
                logger.debug("診療日 : " + target);
            }
        }else{
            //未来検索
            for(int n=0; n < cdateList.size() ; n++ ){
                String target = cdateList.get(n);
                resultList.add(cdateMap.get(target));
                logger.debug("診療日 : " + target);
            }
        }

        logger.debug("処理リストの作成終了");
        return resultList;
    }

    private List<File> searchFile(List<File> fileList, String type, Integer count) {
       logger.debug("対象ファイル検索の開始");
       Integer getcount = 0;

       List<File> resultList = new ArrayList<>();

       for(File target : fileList){
           String path = target.getPath() + "/" + type;
           File typeDir = new File(path);

           //対象のデータ種別がない場合は次へ
           if(!typeDir.exists()) continue;

           logger.debug("対象のデータ種別あり path : "  + path);

           File[] ssmixs = typeDir.listFiles();

           for(File ssmix : ssmixs){
               String name = ssmix.getName();
               logger.debug(name);
               //コンディションフラグが１のものだけを取得する
               if(!name.endsWith("_1")) continue;

               resultList.add(ssmix);
               getcount++;

               if(count == getcount) break;

           }
           if(count == getcount) break;
       }

       logger.debug("対象ファイル検索の終了");
       return resultList;
    }


    private List<File> searchExtFile(List<File> fileList, String type, Integer count) {
       logger.debug("対象ファイル検索の開始");
       Integer getcount = 0;

       List<File> resultList = new ArrayList<>();

       for(File target : fileList){
           String path = target.getPath() + "/" + type;
           File typeDir = new File(path);

           //対象のデータ種別がない場合は次へ
           if(!typeDir.exists()) continue;

           logger.debug("対象のデータ種別あり path : "  + path);

           File[] ssmixs = typeDir.listFiles();

           for(File ssmix : ssmixs){
               String name = ssmix.getName();
               logger.debug(name);
               //コンディションフラグが１のものだけを取得する
               //if(!name.endsWith("_1")) continue;

               resultList.add(ssmix);
               getcount++;

               if(count == getcount) break;

           }
           if(count == getcount) break;
       }

       logger.debug("対象ファイル検索の終了");
       return resultList;
    }

    /**
     * 日数計算
     * @param baseDate
     * @param cdate
     * @param datecount
     * @return
     */
    private boolean dateCal(String baseDate, String cdate, Integer datecount) {
        logger.debug("日数計算開始");

        try {
            Date b_date = sdf.parse(baseDate);
            Date c_date = sdf.parse(cdate);

            long l_b = b_date.getTime();
            long l_c = c_date.getTime();
            long ondateTime = 1000 * 60 * 60 * 24;
            long dif_Date = (l_b - l_c)/ondateTime;

            if(dif_Date > datecount ) return false;

        } catch (ParseException ex) {
            Logger.getLogger(PatientSearchFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        logger.debug("日数計算終了");
        return true;
    }


}
