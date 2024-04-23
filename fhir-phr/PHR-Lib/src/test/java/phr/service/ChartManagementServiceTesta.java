/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.ChartDefinitionEntity;
import phr.datadomain.entity.ChartObservationDefinitionEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.service.impl.ChartManagementService;

/**
 *
 * @author daisuke
 */
public class ChartManagementServiceTesta {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartManagementServiceTesta.class);

    public ChartManagementServiceTesta() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void getChartListTest() throws ClassNotFoundException, SQLException, Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject();
        ChartManagementService service = new ChartManagementService();
        
        List<ChartDefinitionEntity> list = service.getChartList("1234567-000006");
        for (ChartDefinitionEntity entity : list) {
            logger.debug(entity.getChartDefinitionName());
        }
        
    }
    
    @Test
    public void getChartDefinition() throws ClassNotFoundException, SQLException, Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject();
        ChartManagementService service = new ChartManagementService();
        
        long id = 1;
        ChartDefinitionEntity entity = service.getChartDefinition(id, 2);
        
       logger.debug(entity.getChartDefinitionName()); 
       for (ChartObservationDefinitionEntity obEntity : entity.getChartObservationDefinitionList()) {
           logger.debug(obEntity.getObservationDefinitionName());
       }
    }
    
    @Test
    public void getViewChartDataTest1() throws ClassNotFoundException, SQLException, Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject();
        ChartManagementService service = new ChartManagementService();
        
        long id = 1;
        Map<String, Map<ChartObservationDefinitionEntity, ObservationEntity>> chartDataMap = service.getViewChartData("1234567-000006", id, null);
        
       logger.debug(chartDataMap.size()); 
       for (Map.Entry<String,  Map<ChartObservationDefinitionEntity, ObservationEntity>> e: chartDataMap.entrySet()) {
           logger.debug(e.getKey());
       }
    }
}
