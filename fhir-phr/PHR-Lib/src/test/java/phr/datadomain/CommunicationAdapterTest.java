/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.entity.CommunicationEntity;

/**
 *
 * @author kis-note
 */
public class CommunicationAdapterTest {
    
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationAdapterTest.class);  
    
    @Test
    public void medicalTest() throws Throwable{
         DataAccessObject dao = null;

         String patient = null;
         String medicalCd = "1111111111";
         Date startDate = null;
         Date endDate = null;
         boolean readFlg = true;
         
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            List<CommunicationEntity> entity = adapter.searchMedicalMeg(patient,medicalCd, startDate, endDate, readFlg);
            
            System.out.println(entity.size());
            
        } catch (Throwable ex) {
            System.out.println(ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
   }
    
}
