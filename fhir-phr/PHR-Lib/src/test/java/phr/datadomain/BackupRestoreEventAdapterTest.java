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
import phr.datadomain.adapter.BackupRestoreEventAdapter;

/**
 *
 * @author daisuke
 */
public class BackupRestoreEventAdapterTest {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreEventAdapterTest.class);
    
    public BackupRestoreEventAdapterTest() {
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
    public void numberingBackupRestoreEventId() {
        try {
            logger.debug("Start");
            
            String id = BackupRestoreEventAdapter.numberingBackupRestoreEventId();

            logger.debug(id);
            logger.debug("End");
        } catch (Throwable ex) {
            Logger.getLogger(DataAccessObjectTest.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}
