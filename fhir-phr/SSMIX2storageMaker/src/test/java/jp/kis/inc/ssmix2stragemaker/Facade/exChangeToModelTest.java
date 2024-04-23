/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.kis.inc.ssmix2stragemaker.Facade;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.Facade.ModelToStorageFacade;
import kis.inc.ssmix2storagemaker.Facade.StorageToModelFacade;
import kis.inc.ssmix2storagemaker.dto.ADT00.ADT00BaseDto;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author kis-note
 */
public class exChangeToModelTest {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(exChangeToModelTest.class);
    
    /**
     * OML11のテスト
     */
    @Test
    public void excute(){
        logger.debug("Test Start");
        
        String path = "/opt/strage/test/OML-11/000035526_-_OML-11_999999999999999_ 20140111135649314_-_1";
        String type ="OML-11";
        File file = new File(path);
           
        StorageToModelFacade service = new StorageToModelFacade();
        try {
            ModelDto model = service.exChangeToModel(type, file);
            
            OML11BaseDto result = (OML11BaseDto) model;
            
            //テスト結果
            Gson gson = new Gson();
            String json = gson.toJson(result);
            logger.debug(json);
            
            logger.debug("write test開始");
            ModelToStorageFacade service2 = new ModelToStorageFacade();
            
            String tagetpath = "/opt/strage/output/STD";
            try {
                service2.exChangeToStorage(model, tagetpath, "9999999-000001", "20161201","00000000001");
            } catch (ParseException ex) {
                Logger.getLogger(exChangeToModelTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(exChangeToModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    /**
     * ADT00のテスト
     */
    @Test
    public void excuteADT00(){
        logger.debug("Test Start");
        
        String path = "/opt/strage/test/ADT-00/000035526_-_ADT-00_999999999999999_20141119135649314_-_1";
        String type ="ADT-00";
        File file = new File(path);
           
        StorageToModelFacade service = new StorageToModelFacade();
        try {
            ModelDto model = service.exChangeToModel(type, file);
            
            ADT00BaseDto result = (ADT00BaseDto) model;
            
            //テスト結果
            Gson gson = new Gson();
            String json = gson.toJson(result);
            logger.debug(json);
            
            logger.debug("write test開始");
            ModelToStorageFacade service2 = new ModelToStorageFacade();
            
            String tagetpath = "/opt/strage/output/STD";
            try {
                service2.exChangeToStorage(model, tagetpath, "9999999-000001", "20161201","00000000001");
            } catch (ParseException ex) {
                Logger.getLogger(exChangeToModelTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(exChangeToModelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
}
