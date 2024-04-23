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
import phr.service.impl.AutoCalcService;
import phr.service.impl.ObservationEventService;

/**
 *
 * @author daisuke
 */
public class AutoCaluTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AutoCaluTest.class);
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    @Test
    public void AutoSrtServiceTest() throws Throwable {
        
        IAutoCalcService service = new AutoCalcService();
        
        ObservationEntity entity = new ObservationEntity();
        entity.setValue("1.2");
        entity.setObservationDefinitionId("0000000008");
        
        List<ObservationEntity> list = new ArrayList<ObservationEntity>();
        list.add(entity);
        
        try {
            List<ObservationEntity> rentity = service.autoCalcSet("1234567-000000","1234567", 2016,Timestamp.valueOf("2016-10-10 12:00:00"), list);
            if(rentity != null && rentity.size() > 0){
                for(ObservationEntity result : rentity){
                    System.out.println(result.getObservationDefinitionId());
                    System.out.println(result.getValue());
                }
            }
        } catch (Throwable ex) {
            logger.error("", ex);
            //throw ex;
        }
        
        
    }
    
        @Test
    public void AlertTest() throws Throwable {
        try{
            File data = new File("/opt/phr/data.csv");
            FileReader fr = new FileReader(data);
            BufferedReader br = new BufferedReader(fr);

            List<ObservationEntity> list = new ArrayList<ObservationEntity>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] lines = line.split(",");
                ObservationEntity entity = new ObservationEntity();
                entity.setValue(lines[1]);
                entity.setObservationDefinitionId(lines[0]);
                list.add(entity);
             }

            IAlertSetService service = new AlertSetService();

            try {
                List<ObservationEntity> rentity = service.alertSet("1234567-000000","1234567", 2016, list,null);
                if(rentity != null && rentity.size() > 0){
                    for(ObservationEntity result : rentity){
                    }
                }
            } catch (Throwable ex) {
                logger.error("", ex);
                throw ex;
            }
        }catch(Exception e){
            
        }
        
    }

}
