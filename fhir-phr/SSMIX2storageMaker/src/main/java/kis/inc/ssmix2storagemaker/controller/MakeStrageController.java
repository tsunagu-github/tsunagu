/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.Service.SaveOML11Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class MakeStrageController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MakeStrageController.class);
    
     /**
     * ロギングコンポーネント
     */
    private List<String> messageList;

    /**
     * @return the messageList
     */
    public List<String> getMessageList() {
        return messageList;
    }
    
    /**
     * 
     * 保存対象ファイルを対象患者に保存する
     * @param patientId 患者を識別するID
     * @param date　日付
     * @param file　保存対象ファイル
     * @param type　データ種別
     * @return 
     */
    public boolean makeStrage(String patientId , String date , String rootPath , File file , String type){
        logger.debug("Start");
        
        if(rootPath == null){
            messageList.add("標準ストレージのルートパスが設定されていません。");
            logger.debug("標準ストレージのルートパスが設定されていません。");
            return false;
        } 
        logger.debug("ルートパス ： " + rootPath);
        
        String pass1 = patientId.substring(0, 3);
        String pass2 = patientId.substring(3, 6);
        
        String patientPass = rootPath + "/" + pass1 + "/" + pass2 + "/" + patientId;
        logger.debug("患者のルートパス ： " + patientPass);
        
        String targetPass = patientPass + "/" + date + "/" + type ;
        logger.debug("保存対象パス ： " + patientPass);
        
        File targetPath = new File(targetPass);
        if(!targetPath.exists()) targetPath.mkdirs();
            
        try {
            File target = new File(targetPass,file.getName());
            Files.copy(file.toPath(), target.toPath());
        } catch (IOException ex) {
            Logger.getLogger(MakeStrageController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        
        logger.debug("End");
        return true;
    }
   
}
