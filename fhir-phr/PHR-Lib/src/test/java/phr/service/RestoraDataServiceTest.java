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
import phr.service.impl.RestoraDataService;

/**
 *
 * @author daisuke
 */
public class RestoraDataServiceTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RestoraDataServiceTest.class);
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    @Test
    public void RestoraDataServiceTest() throws Throwable {
        
        IRestoraDataService service = new RestoraDataService();
               
        File file = new File("F:/opt/phr/backup/1234567-000012.zip");
        try {
             service.ececuteRestoraData(file,"1234567-000000","1234567-000012");
        } catch (Throwable ex) {
            logger.error("", ex);
            //throw ex;
        }
        
        
    }
    
}
