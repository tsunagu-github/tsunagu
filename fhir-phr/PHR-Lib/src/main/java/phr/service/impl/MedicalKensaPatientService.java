/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalKensaPatientService;

/**
 *
 * @author KISO-NOTE-005
 */
public class MedicalKensaPatientService implements IMedicalKensaPatientService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalKensaPatientService.class);
    
    /**
     * 患者情報の取得を行う
     * @param MedicalOrganizationCd
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public PatientEntity patientSearch(String patientId, String MedicalOrganizationCd) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            PatientEntity entity = adapter.selectMedicalPatient(patientId, MedicalOrganizationCd);

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
     * 患者情報の取得を行う
     * @param phrId
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public List<ObservationEventEntity> observationEventSearch(String phrId, Date startDate, Date endDate, String medicalOrganizationCd) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
            List<ObservationEventEntity> entity = adapter.selectObsetvationEventReminder(phrId, startDate, endDate, medicalOrganizationCd);

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
