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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.impl.AlertSetService;
import phr.service.impl.AlertSpecificService;
import phr.service.impl.ObservationEventService;

/**
 *
 * @author daisuke
 */
public class AlertSpecificTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AlertSpecificTest.class);
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    @Test
    public void AlertSpecificTest() throws Throwable {
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date birthDate = sdf.parse("19570223");
        Timestamp date = Timestamp.valueOf("2016-2-23 12:00:00");
        
        String birth = sdf.format(birthDate);
        String cdate = sdf.format(date.getTime());

        int i_birth = Integer.parseInt(birth);
        int i_cdate = Integer.parseInt(cdate);
        
        int age = (i_cdate - i_birth)/10000;
        
        logger.debug(age);
        
        
    }

}
