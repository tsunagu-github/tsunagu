/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationDefinitionRangeAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IMedicalKensaEntryConfirmService;
import phr.service.IMedicalKensaEntryInputService;

/**
 *
 * @author KISO-NOTE-005
 */
public class MedicalKensaEntryConfirmService implements IMedicalKensaEntryConfirmService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalKensaEntryConfirmService.class);
    
    /**
     * 検査一覧の取得を行う
     * @param insurerNo
     * @param Year
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public List<ObservationDefinitionRangeEntity> observationReminderSearch(String insurerNo, int Year, String sexCd)  throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            ObservationDefinitionRangeAdapter adapter = new ObservationDefinitionRangeAdapter(dao.getConnection());
            List<ObservationDefinitionRangeEntity> entity = adapter.SearchObservationDefinitionId(insurerNo, Year, sexCd);

            return entity;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    /**
     * PHRIDより保険者情報の取得を行う
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public InsurerPatientEntity insurerSearch(String phrid) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
            InsurerPatientEntity entity = adapter.findByPhrId(phrid);

            return entity;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    @Override
    public int updateObservationEventAndObservation(List<ObservationEntity> obEntitylist, ObservationEventEntity evEntity) throws Throwable {
         DataAccessObject dao = null;
         int result = 0;
        try {
            dao = new DataAccessObject();
            ObservationAdapter oAdapter = new ObservationAdapter(dao.getConnection());
            ObservationEventAdapter eAdapter = new ObservationEventAdapter(dao.getConnection());

            // 自動計算する
            AutoCalcService autoCalcService = new AutoCalcService();  
            obEntitylist = autoCalcService.autoCalcSet(evEntity.getPHR_ID(), evEntity.getInsurerNo(), evEntity.getYear(), evEntity.getExaminationDate(), obEntitylist);
            
            dao.beginTransaction();	// トランザクション開始
            List<ObservationEntity> insertEntList = new ArrayList();
            if(evEntity.getObservationEventId()==null || evEntity.getObservationEventId().isEmpty()){
                //新規登録
                String eventId = ObservationEventAdapter.numberingObservationEventId();
                evEntity.setObservationEventId(eventId);
                result = eAdapter.insert(evEntity);
                
                for (ObservationEntity res : obEntitylist) {
                    res.setObservationEventId(eventId);
                    result = oAdapter.insert(res);
                    insertEntList.add(res);
                }
                
            }else{
                //更新
                ObservationEventEntity savedEv = eAdapter.findByPrimaryKey(evEntity.getObservationEventId());
                evEntity.setUpdateDateTime(savedEv.getUpdateDateTime());
                result = eAdapter.update(evEntity);
                List<ObservationEntity> savedList = oAdapter.findByObservationEventId(evEntity.getObservationEventId());
                boolean saved = false;
                for (ObservationEntity res :  obEntitylist) {
                    saved = false;
                    for(ObservationEntity checkEnt :  savedList){
                        if(checkEnt.getObservationDefinitionId().equals(res.getObservationDefinitionId())){
                            saved = true;
                            res.setUpdateDateTime(checkEnt.getUpdateDateTime());
                            break;
                        }
                    }
                    if(saved){
                        result = result + oAdapter.update(res);
                    }else{
                        result = result + oAdapter.insert(res);
                        insertEntList.add(res);
                    }
                }  
            }
                //アラーム設定
                AlertSetService alarmService = new AlertSetService();
                alarmService.alertSet(evEntity.getPHR_ID(), evEntity.getInsurerNo(), null, obEntitylist, evEntity.getExaminationDate());
                
            dao.commitTransaction();
            
        } catch (Throwable ex) {
            dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return result;
    }

    @Override
    public ObservationEventEntity getObservationEvent(String observationEventId) throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
            ObservationEventEntity entity = adapter.findByPrimaryKey(observationEventId);
            return entity;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    @Override
    public ObservationEntity getObservation(String observationEventId, String observationDefinitionId) throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            ObservationEntity entity = adapter.findByPrimaryKey(observationEventId, observationDefinitionId);
            return entity;
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
