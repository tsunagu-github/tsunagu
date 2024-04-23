/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.Facade.ModelToStorageFacade;
import kis.inc.ssmix2storagemaker.Facade.StorageToModelFacade;
import kis.inc.ssmix2storagemaker.dto.ADT00.ADT00BaseDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OrderDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IN1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.SPMDto;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMakeStorageService;
import phr.service.IMergeStorageService;
import phr.ssmix.maker.EVNMaker;
import phr.ssmix.maker.IN1Maker;
import phr.ssmix.maker.MSHMaker;
import phr.ssmix.maker.OBRMaker;
import phr.ssmix.maker.OBXMaker;
import phr.ssmix.maker.ORCMaker;
import phr.ssmix.maker.PIDMaker;
import phr.ssmix.maker.PV1Maker;
import phr.ssmix.maker.SPMMaker;

/**
 *
 * アラート一覧画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class MergeStorageService implements IMergeStorageService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MergeStorageService.class);

    /**
     * 
    **/
    @Override
    public boolean mergeExecute(String phrid , String rootPath) throws Throwable {
        logger.debug("マージ処理開始");
        File stdDir = new File(rootPath);
        if(!stdDir.getName().equals("STD")){
            logger.debug("標準化ストレージのルートパスではありません");
            return false;
        }
        
        String path = makeRootPath(rootPath, phrid);

        File nodate = new File(path);
        File[] types = nodate.listFiles();
        Map<String , String > deleateMap = new HashMap<>();
        for(File type : types){
            String dataType = type.getName();
            switch(dataType){
                case "ADT-00":
                    File[] adt00s = type.listFiles();
                    if(adt00s.length < 2){
                        logger.debug("複数ファイルがないので次へ");
                        break;
                    }
                    deleateMap = margeADT00(phrid , adt00s , rootPath);
                    break;
            }
        }
        deleate(deleateMap);
        logger.debug("マージ処理終了");
        return true;
    }

    private String makeRootPath(String rootPath, String phrid) {
        logger.debug("ルートパスの作成開始");
        
        if(phrid == null){
            logger.debug("PHRID指定がないためpathの直下で対応");
            return rootPath;
        }
        
        if(phrid.length() <= 5){
            logger.debug("患者IDの文字数が足りません");
            return rootPath;
        }
        
        String ftid = phrid.substring(0, 3);
        String sdid = phrid.substring(3, 6);

        String path = rootPath;
        
        path = path + "/"  + ftid + "/" + sdid + "/" + phrid + "/-";
        
        logger.debug("targetPath: " + rootPath);
        
        File file = new File(rootPath);
        if(!file.exists()) file.mkdirs();
        
        logger.debug("ルートパスの作成終了");

        return path;

    }

    private Map<String , String > margeADT00(String phrid , File[] adt00s , String rootPath) throws IOException {
        logger.debug("ADT-00のマージ処理開始");
        Map<String , Map<String , ADT00BaseDto>> adt00Map = new TreeMap<>();
        StorageToModelFacade stm = new StorageToModelFacade();
        
        ADT00BaseDto mainDto = new ADT00BaseDto();

        List<String> pathList = new ArrayList<>();
        String mainPath = "";
        Map<String , String > pathMap = new HashMap<>();
       
        for(File adt00 : adt00s){
            String filename = adt00.getName();
            if(!filename.endsWith("_1")) continue;

            ADT00BaseDto dto = (ADT00BaseDto)stm.exChangeToModel("ADT-00", adt00);
            //ファイル名から患者IDを取得する
            //元と新規が完全一致の場合は、PatientEntityからCreateDateを取得してそこまでみないと作成した
            //メッセージの区別がつかない
            String[] elemnts = filename.split("_");
            String id = elemnts[0];
            String mdate = elemnts[4];
            
            //BaseDtoの設定
            if(id.equals(phrid)){
                dto.setCdate(mdate);
                mainDto = dto;
                mainPath = adt00.getPath();
            }else{
                Map<String , ADT00BaseDto> subMap = new HashMap<>();
                if(adt00Map.containsKey(mdate)){
                    subMap = adt00Map.get(mdate);
                }
                subMap.put(id, dto);
                adt00Map.put(mdate, subMap);
                pathList.add(adt00.getPath());
            }
        }
        
        //マージ処理
        //マージするのはOBXとAL1のフィールドのみとする。
        //addの作成はトランザクション順位行う
        if(adt00Map.size() > 0){
            for(String cdate : adt00Map.keySet()){
                Map<String , ADT00BaseDto> subMap = adt00Map.get(cdate);

                for(String pid : subMap.keySet()){
                    ADT00BaseDto dto = subMap.get(pid);
                    if(dto.getObxList() != null) mainDto.setObxList(dto.getObxList());
                    if(dto.getAl1List() != null) mainDto.setAl1List(dto.getAl1List());
                }
            }
        

            //ADT-00に反映
            ModelToStorageFacade mts = new ModelToStorageFacade();
            try {
                mts.exChangeToStorage(mainDto, rootPath, phrid, "-", "0000000000");
            } catch (ParseException ex) {
                Logger.getLogger(MergeStorageService.class.getName()).log(Level.SEVERE, null, ex);
                logger.debug("" ,ex);
            }

            //ファイルのコンディションフラグを２にする
            for(File adt00 : adt00s){
                String filename = adt00.getName();
                if(!filename.endsWith("_1")) continue;

                String old_path = adt00.getPath();
                if(old_path.equals(mainPath)) continue;
                String old_name = filename;
                String new_path = adt00.getParent();

                String[] elements = old_name.split("_");
                String new_name = makeFileName(elements);

                File target = new File(new_path + "/" + new_name);
                pathMap.put(adt00.getPath(), target.getPath());
            }
            
        }
        logger.debug("ADT-00のマージ処理終了");
        return pathMap;
    }

    private String makeFileName(String[] elements) {
        
        //コンディションフラグを２にする
        elements[6] = "2";
        
        String name = elements[0] + "_" + elements[1] + "_" + elements[2] + "_" + elements[3] + "_" + elements[4] + "_" + elements[5] + "_" + elements[6];
        
        return name;
    }

    private void deleate(Map<String, String> deleateMap) {
        
        for(String old_path : deleateMap.keySet()){
            File oldFile = new File(old_path);
            String new_path = deleateMap.get(old_path);
            
            File newFile = new File(new_path);
                
            try {
                 Files.copy(oldFile.toPath(), newFile.toPath());
                 oldFile.delete();
            } catch (IOException ex) {
                Logger.getLogger(MergeStorageService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
       
}
