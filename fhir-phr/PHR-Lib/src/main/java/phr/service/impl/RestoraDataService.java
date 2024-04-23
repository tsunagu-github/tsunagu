/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.controller.SSMIXSearchController;
import kis.inc.ssmix2storagemaker.dto.CHECKUP.CheckUpXMLDto;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.MedicineNoteBoookDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchResponseDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchResultDto;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.service.ICheckUpBackUPRestoreService;
import phr.service.IMakeStorageService;
import phr.service.IRegistModelMedicineNoteBookService;
import phr.service.IRegistModelOML11Service;
import phr.service.IRestoraDataService;


/**
 *
 * アラート一覧画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class RestoraDataService implements IRestoraDataService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RestoraDataService.class);
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    /**
     * リストア用のzipを展開、DBに登録するサービス
     * 
     * @param phrid
     * @return
     * @throws Throwable 
     */
    public boolean ececuteRestoraData(File zipFile ,String old_phrid , String phrid) throws Throwable{
        logger.debug("Start");
        //最初に現在のDBからストレージを作成する
        String id = zipFile.getName();
        id = id.replace(".zip", "");
        
        if(!id.equals(phrid)) return false;

        //作成用のID作成
        //後にバックアップの処理IDなどに変えた方がよいが今はuuidとする・
        String uniqueId = null;
        UUID uuid = UUID.randomUUID();
        uniqueId = uuid.toString();
        uniqueId = uniqueId.replaceAll("-", "");
        
        String savePath = PhrConfig.getConfigProperty(ConfigConst.BACKUP_STORAGE);
        logger.debug("savePath : " + savePath);        
        
        String targetPath = savePath +  File.separator + uniqueId ;
        
//        logger.debug("標準化ストレージをDBを作成開始");
//        IMakeStorageService service = new MakeStorageService();
//        service.makeSTDStorage(id, targetPath + File.separator + "STD");
//        service.makeEXTStorage(id, targetPath + File.separator + "EXT");
//        logger.debug("標準化ストレージをDBを作成終了");
        
        
        String name = zipFile.getName();
        
        if(!(name.endsWith("zip") || name.endsWith("ZIP"))){
            logger.debug("zipファイルでないため終了");
            return false;
        }

        String zipPath = savePath +  File.separator +  uniqueId +  File.separator + name;
        String basePath = savePath +  File.separator +  uniqueId +  File.separator + old_phrid;
        
        logger.debug("展開対象zipPath : " + zipPath);
        
        File baseFile = new File(basePath);
        baseFile.mkdirs();

        File targetFile = new File(zipPath);
        Files.copy(zipFile.toPath(), targetFile.toPath());
        
        //展開処理
        unzip(targetFile , basePath);
      
        //id移行処理
        idChange(targetPath + File.separator + "STD", phrid , basePath , old_phrid);
        idChange(targetPath + File.separator + "EXT", phrid , basePath , old_phrid);
        
        //展開したzipファイルは削除する
        deleteStorage( new File(basePath));
        targetFile.delete();
                
        //標準化ストレージのデータ登録処理
         
        SSMIXSearchDto dto = new SSMIXSearchDto();
        
        dto.setPatientId(phrid);
        dto.setSearchType(1);
        dto.setStdRootPath(savePath +  File.separator +  uniqueId +  File.separator + "STD");
        dto.setDataType("STD");
        Date date = new Date();
        String sd_date = sdf.format(date);
        
        dto.setBaseDate(sd_date);
        dto.setSearchDirection(1);
        dto.setCount(9999);
        SSMIXSearchController search = new SSMIXSearchController();
        SSMIXSearchResponseDto result = search.search(dto);

        //登録用Entity作成
        //ADT-00の情報は登録は実施しない
        //理由はPHRIDを採番されている時点で最新のADT-00の情報が登録済みなため
        //別途マージを実施する必要がある。
        if(!result.isStatus()) return false;
        
        List<SSMIXSearchResultDto> searchList = result.getResultList();
       
        //拡張ストレージの結果取得
        dto.setExRootPath(savePath +  File.separator +  uniqueId +  File.separator + "EXT");
        dto.setDataType("EXT");
        SSMIXSearchResponseDto extResult = search.search(dto);
        if(!extResult.isStatus()) return false;        
        List<SSMIXSearchResultDto> extSearchList = extResult.getResultList();
        
        //標準ストレージの登録処理
        for(SSMIXSearchResultDto targetDto : searchList){
            String pid = targetDto.getPatientId();
            List<ModelDto> models = targetDto.getModelList();
            
            //model毎に登録を実施する。
            for(ModelDto model : models){
                registModel(model , phrid );
            }
        }
        
        //拡張ストレージのデータ登録処理
        //お薬手帳
        for(SSMIXSearchResultDto exTargetDto : extSearchList){
            if(exTargetDto != null && exTargetDto.getPatientId() != null){
                String pid = exTargetDto.getPatientId();
                List<ModelDto> models = exTargetDto.getModelList();

                //model毎に登録を実施する。
                for(ModelDto model : models){
                    registModel(model , phrid );
                }                
            }
        }
        
        logger.debug("ストレージの保存パスに変更");
        coptSTDStorage(savePath +  File.separator +  uniqueId +  File.separator + "STD" , phrid);
        
        logger.debug("一時作成ストレージの削除");
        File dFile = new File(savePath + File.separator + uniqueId);
        deleteStorage(dFile);
        dFile.delete();
        logger.debug("End");
        return true;
    }



    private void unzip(File baseFile , String baseDirPath ) throws FileNotFoundException, IOException {

        InputStream is = new FileInputStream(baseFile);

        ZipArchiveInputStream archive;

        archive = new ZipArchiveInputStream(is, "JIS", true);

        ZipArchiveEntry entry;
        String base = baseDirPath;
        while ((entry = archive.getNextZipEntry()) != null) {
                base = base +  File.separator + entry.getName();
                logger.debug("base : " + base);
                File file = new File(base);
                if (entry.isDirectory()) {
                        logger.debug("ディレクトリ作成");
                        file.mkdirs();
                } else {
                        logger.debug("ファイル作成");
                        if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                        }
                        OutputStream out = new FileOutputStream(file);
                        IOUtils.copy(archive, out);
                        out.close();
                        base = baseDirPath;
                }
        }

        archive.close();
        is.close();
    }

    private void idChange(String targetPath, String phrid, String basePath, String old_phrid) {
       logger.debug("ID変換処理を開始");
       File base = new File(basePath);
       File[] bases = base.listFiles();
       
       String pass1 = phrid.substring(0, 3);
       String pass2 = phrid.substring(3, 6);
       
       String savePath = targetPath +  File.separator + pass1 +  File.separator + pass2 +  File.separator + phrid;
       File save = new File(savePath);
       String targetType = targetPath.substring(targetPath.length() - 3, targetPath.length());
       //患者IDの先頭
       for(File b1 : bases){
           //
           String fileType = b1.getName();
           if(!fileType.equals(targetType)){
               continue;
           }
           File[] sdpat = b1.listFiles();
           
           //患者IDの２番目
           for(File b2 : sdpat){
               File[] patients = b2.listFiles();
               
               //患者ID
               for(File patient : patients){
                   File[] cdates = patient.listFiles();
                   
                   for(File cdate : cdates){
                        try {
                            copyExecute(cdate , save);
                        } catch (IOException ex) {
                            Logger.getLogger(RestoraDataService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }
               }
           }
       }
       
//       deleteStorage(base);
//       base.delete();
       logger.debug("ID変換処理を終了");
    }

    private void copyExecute(File target, File saveDir) throws IOException {
        File[] targetDirs = target.listFiles();
        
        for(File file : targetDirs){
            if(file.isDirectory()){
                String base = file.getName();
                String savePath = saveDir.getPath() +  File.separator + base;
                File save = new File(savePath);
                save.mkdirs();
                
                copyExecute(file , save);
            }else{
                String name = file.getName();
                File save = new File(saveDir.getPath() +  File.separator + name);
                Files.copy(file.toPath(), save.toPath());
            }
        }

    }

    private void deleteStorage(File target) {
    
        if (!target.exists()) {
            return;
        }

        if (target.isFile()) {
            target.delete();
            return;
        }
        if (target.isDirectory()) {
            File[] files = target.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteStorage(files[i]);
            }
            target.delete();
        }

    }    

    private void registModel(ModelDto model , String phrid) {
        logger.debug("Start");
        String className = model.getClass().getSimpleName();
        
        logger.debug("登録対象モデル : " + className);
        
        switch(className){
            case "ADT00BaseDto":
                logger.debug("ADT-00のため登録は実施しない");
                break;
            case "OML11BaseDto":
                IRegistModelOML11Service regist = new RegistModelOML11Service();
                OML11BaseDto target = (OML11BaseDto) model;
                try {
                    regist.modelOML11(phrid, target);
                } catch (Throwable ex) {
                    Logger.getLogger(RestoraDataService.class.getName()).log(Level.SEVERE, null, ex);
                    logger.error("", ex);
                }
                break;
            case "MedicineNoteBoookDto":
                IRegistModelMedicineNoteBookService medRegist = new RegistModelMedicineNoteBookService();
                try {
                    medRegist.modelMNB(phrid, (MedicineNoteBoookDto)model);
                } catch (Throwable ex) {
                    Logger.getLogger(RestoraDataService.class.getName()).log(Level.SEVERE, null, ex);
                    logger.error("", ex);
                }
                break;
            case "CheckUpXMLDto":
                ICheckUpBackUPRestoreService chuRegist= new CheckUpBackUPRestoreService();
                try {
                    chuRegist.setRestoreCheckUpXML(phrid, (CheckUpXMLDto)model);
                } catch (Throwable ex) {
                    Logger.getLogger(RestoraDataService.class.getName()).log(Level.SEVERE, null, ex);
                    logger.error("", ex);
                }
                
                break;
            default :
                logger.debug("対応したデータ型ではありません");
                
        }
        logger.debug("End");
    }

    private void coptSTDStorage(String rootPath, String phrid) {
 
        String pass1 = phrid.substring(0, 3);
        String pass2 = phrid.substring(3, 6);

        String targetPath = rootPath +  File.separator + pass1 +  File.separator + pass2 +  File.separator + phrid;

        logger.debug("targetPath : " + targetPath);
        File target = new File(targetPath);

        String savePath = PhrConfig.getConfigProperty(ConfigConst.STORAGE_PATH) + File.separator + "STD";
        //過去に別事業者などからのバックアップを受け取ったことがあるかの確認
        //ない場合は終了
        //連続で処理されたらもしかしたら先頭に何か必要かもしれない
        String savetargetPath = savePath + File.separator + pass1 + File.separator + pass2 + File.separator + phrid;

        File saveDir = new File(savetargetPath);

        saveDir.mkdirs();
        if (!target.exists()) {
            return;
        }
        logger.debug("コピー開始");

        try {
            copyExecute(target, saveDir);
        } catch (IOException ex) {
            Logger.getLogger(RestoraDataService.class.getName()).log(Level.SEVERE, null, ex);
        }

        logger.debug("コピー終了");

    }
}
