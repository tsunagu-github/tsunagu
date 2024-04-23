/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import phr.config.PhrConfigTest;
import phr.datadomain.adapter.DataTypeAdapter;
import phr.datadomain.entity.DataTypeEntity;

/**
 *
 * @author daisuke
 */
public class DataTypeAdapterTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataTypeAdapterTest.class);  
    public DataTypeAdapterTest() {
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
    public void findByAllTest() {
        try {
            Connection conn = DataAccessObject.createConnection();
            DataTypeAdapter adapter = new DataTypeAdapter(conn);
            
            List<DataTypeEntity> list = adapter.findByAll();
            
            for (DataTypeEntity entity : list) {
                logger.debug(entity.getDataTypeCd() + ":" + entity.getName());
            }
            
        } catch (Throwable ex) {
            Logger.getLogger(DataTypeAdapterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
