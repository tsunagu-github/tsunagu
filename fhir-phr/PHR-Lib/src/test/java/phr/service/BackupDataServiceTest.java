/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import phr.service.impl.BackupDataService;

/**
 *
 * @author daisuke
 */
public class BackupDataServiceTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupDataServiceTest.class);
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    @Test
    public void BackupDataServiceTest() throws Throwable {
        
        IBackupDataService service = new BackupDataService();
               
        try {
            File result = service.makeBackupData("1234567-000000");
        } catch (Throwable ex) {
            logger.error("", ex);
            //throw ex;
        }
        
        
    }
    
}
