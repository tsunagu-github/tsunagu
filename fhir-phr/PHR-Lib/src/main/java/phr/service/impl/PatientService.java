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
import phr.service.IPatientService;

/**
 *
 * @author KISNOTE011
 */
public class PatientService implements IPatientService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientService.class);

    /**
     * 該当患者のレコードを取得する
     * @param phr_id
     * @return
     * @throws Throwable 
     */
    public PatientEntity findById(String phr_id) throws Throwable{
        logger.debug("Start");
        
        PatientEntity entity = new PatientEntity();
        DataAccessObject dao = null;

        try {
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            dao.beginTransaction();
            entity = adapter.findByPrimaryKey(phr_id);
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
