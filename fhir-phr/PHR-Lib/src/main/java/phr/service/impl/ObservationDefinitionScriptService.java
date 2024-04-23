/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.AccessLogsAdapter;
import phr.datadomain.adapter.ObservationDefinitionScriptAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.AccessLogsEntity;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IObservationDefinitionScriptService;

/**
 *
 * @author KISNOTE011
 */
public class ObservationDefinitionScriptService implements IObservationDefinitionScriptService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionScriptService.class);

    /**
     * ObservationDefinitionIdとDiseaseTypeCdでレコードを取得する
     * @param observationDefinitionId
     * @param diseaseTypeCd
     * @return
     * @throws Throwable 
     */
    public ObservationDefinitionScriptEntity findByIdAndDiseasetypecd(String observationDefinitionId, int diseaseTypeCd) throws Throwable{
        logger.debug("Start");
        
        ObservationDefinitionScriptEntity entity = new ObservationDefinitionScriptEntity();
        DataAccessObject dao = null;

        try {
            dao = new DataAccessObject();
            ObservationDefinitionScriptAdapter adapter = new ObservationDefinitionScriptAdapter(dao.getConnection());
            dao.beginTransaction();
            entity = adapter.findByIdAndDiseasetypecd(observationDefinitionId, diseaseTypeCd, 7);
            dao.commitTransaction();
        } catch (Throwable ex) {
            logger.error("", ex);
            if(dao!=null){dao.rollbackTransaction();}
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
        
        logger.debug("End");
        return entity;
    }

}
