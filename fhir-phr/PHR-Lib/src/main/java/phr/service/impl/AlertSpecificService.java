/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.ViewPasswordAdapter;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.ViewPasswordEntity;
import phr.service.IAlertSearchService;
import phr.service.IAlertSpecificService;

/**
 *
 * アラート一覧画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class AlertSpecificService implements IAlertSpecificService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AlertSpecificService.class);
    
    /**
     * 家庭体重における基準日から10日以内の最小と最大の取得する
     * @param phtid
     * @param phrId
     * @param startDate
     * @param endDate
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public String periodValue(String phtid ,ObservationEntity observation, Timestamp cdate , String days , Integer type) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            String result = adapter.findByAlertTarget(phtid, observation.getObservationDefinitionId() , days , type , cdate);
            
            logger.debug("End");
            return result;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
}
