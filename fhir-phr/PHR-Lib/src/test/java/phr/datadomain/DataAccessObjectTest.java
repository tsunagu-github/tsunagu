/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.adapter.DosageHeadAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;

/**
 *
 * @author daisuke
 */
public class DataAccessObjectTest {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataAccessObjectTest.class);
    
    public DataAccessObjectTest() {
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
    public void getConnectionTest() {
        try {
            logger.debug("Start");
            Connection conn = DataAccessObject.createConnection();
            conn.close();
            logger.debug("End");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccessObjectTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void numberingDosageIdTest() {
        try {
            //String Id = DosageHeadAdapter.numberingDosageId();
            //logger.debug(Id);
        } catch (Throwable ex) {
            Logger.getLogger(DataAccessObjectTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void numberingObservationEventIdTest() {
        try {
            //String Id = ObservationEventAdapter.numberingObservationEventId();
            //logger.debug(Id);
        } catch (Throwable ex) {
            Logger.getLogger(DataAccessObjectTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void numberingCommunicationIdTest() {
        try {
            //String Id = CommunicationAdapter.numberingCommunicationId();
            //logger.debug(Id);
        } catch (Throwable ex) {
            Logger.getLogger(DataAccessObjectTest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
