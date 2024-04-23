/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import phr.datadomain.adapter.DataTypeAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.entity.DataTypeEntity;
import phr.datadomain.entity.ObservationEventEntity;

/**
 *
 * @author daisuke
 */
public class ObservationEventAdapterTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationEventAdapterTest.class);
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Test
    public void findByMostRecentTest() {
        int year = 2016;
        String phrId = "1234567-000000";
        Timestamp tm =   new Timestamp(System.currentTimeMillis());
        int viewId = 10;
        try {
            Connection conn = DataAccessObject.createConnection();
            ObservationEventAdapter adapter = new ObservationEventAdapter(conn);
            
            List<ObservationEventEntity> list = adapter.findByMostRecent(year, phrId, tm , viewId);
            
            for (ObservationEventEntity entity : list) {
                logger.debug(gson.toJson(entity));
            }
        } catch (Throwable ex) {
            Logger.getLogger(DataTypeAdapterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
