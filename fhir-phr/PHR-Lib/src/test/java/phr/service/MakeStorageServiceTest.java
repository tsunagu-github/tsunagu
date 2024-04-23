/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import phr.datadomain.entity.ObservationEntity;
import phr.service.impl.AlertSetService;
import phr.service.impl.MakeStorageService;

/**
 *
 * @author daisuke
 */
public class MakeStorageServiceTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MakeStorageServiceTest.class);
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    @Test
    public void MakeStorageServiceTest() throws Throwable {
        
        IMakeStorageService service = new MakeStorageService();
        service.makeSTDStorage("1234567-000000", "/opt/strage/STD");
        
        
        
    }
}
