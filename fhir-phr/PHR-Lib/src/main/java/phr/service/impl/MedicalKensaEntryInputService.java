/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationDefinitionAdapter;
import phr.datadomain.adapter.ObservationDefinitionRangeAdapter;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.service.IMedicalKensaEntryInputService;

/**
 *
 * @author KISO-NOTE-005
 */
public class MedicalKensaEntryInputService implements IMedicalKensaEntryInputService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalKensaEntryInputService.class);
    
    /**
     * 検査一覧の取得を行う
     * @param insurerNo
     * @param Year
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public List<ObservationDefinitionRangeEntity> observationReminderSearch(String insurerNo, int Year, String sexCd) throws Throwable {
        
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
    
    /**
     * 入力値の異常値チェック行う
     * @param value
     * @param InsurerNo
     * @param ObservationDeifinitionId
     * @param DiseaseTypeCd
     * @param Year
     * @return 
     * @throws java.lang.Throwable 
     */
    @Override
    public ObservationDefinitionRangeEntity inputCheck(String value, String InsurerNo, int Year, String ObservationDeifinitionId, String DiseaseTypeCd) throws Throwable{
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            ObservationDefinitionRangeAdapter adapter = new ObservationDefinitionRangeAdapter(dao.getConnection());
            ObservationDefinitionRangeEntity entity = adapter.checkInput(value, InsurerNo, Year, ObservationDeifinitionId, DiseaseTypeCd);

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
    public List<ObservationDefinitionEntity> getObservationDefinition(List<String> idList) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            ObservationDefinitionAdapter adapter = new ObservationDefinitionAdapter(dao.getConnection());
            List<ObservationDefinitionEntity> entity = adapter.getEntityList(idList);

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
    public List<ObservationEntity> getObservationEntryList(String observationEventId) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            List<ObservationEntity> entity = adapter.findByObservationEventId(observationEventId);

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
