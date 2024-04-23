/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pht.web.phone.facade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import phr.web.phone.facade.FrailResultFacade;

/**
 *
 * @author suzuki
 */
public class FrailResultFacadeTest {
           /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(FrailResultFacadeTest.class);
    
        private static FrailResultFacade facade;

    
    @BeforeClass
    public static void setUpClass() {
        facade = new FrailResultFacade();
    }
    
    @AfterClass
    public static void tearDownClass() {
    } 
    
    /**
     * Test of sendFCMMessage method, of class FCMNotificationService.
     */
    @Test
    public void test() throws Throwable {
        String Json = "{\"phrId\":\"1234567-000043\",\"action\":\"FrailSummaryAction\"}";
       facade.execute(Json);
    }
}
