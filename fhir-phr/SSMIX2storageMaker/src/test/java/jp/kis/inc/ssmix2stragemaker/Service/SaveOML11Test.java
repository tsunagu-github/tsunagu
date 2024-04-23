/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.kis.inc.ssmix2stragemaker.Service;

import java.io.File;
import kis.inc.ssmix2storagemaker.Service.SaveOML11Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author kis-note
 */
public class SaveOML11Test {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SaveOML11Test.class);
    
    @Test
    public void excute(){
        logger.debug("Test Start");
        
        String path = "/opt/strage/test/";
        String tpath = "/opt/strage/STD";
        String type ="OML-11";
        
        String targetPath = path + type;
        
        File targetFolder = new File(targetPath);
        
        File[] targets = targetFolder.listFiles();
        
        for(File target : targets){
            String patientId = "1234567-0000001";
            String date = "20161221";
            
            SaveOML11Service service = new SaveOML11Service();
            service.setRootPath(tpath);
            service.saveOML11(patientId, date, target);
            
        }
        
    }
    
    
}
