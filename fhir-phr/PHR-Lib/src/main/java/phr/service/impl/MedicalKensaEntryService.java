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
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalKensaEntryService;

/**
 *
 * @author KISO-NOTE-005
 */
public class MedicalKensaEntryService implements IMedicalKensaEntryService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalKensaEntryService.class);
    
    /**
     * 患者情報の取得を行う
     * @param patientId
     * @param familyKame
     * @param givenKana
     * @param birthDate
     * @param sexCd
     * @param MedicalOrganizationCd
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public List<PatientEntity> patientSearch(String patientId, String familyKame, String givenKana, String birthDate, String sexCd, String MedicalOrganizationCd) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            List<PatientEntity> entity = adapter.selectMedicalPatientByInput(patientId, familyKame, givenKana, birthDate, sexCd, MedicalOrganizationCd);
            
            
            
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
     * 患者IDの存在確認を行う
     * @param patientId
     * @param MedicalOrganizationCd
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public MedicalOrganizationPatientEntity idSearch(String patientId, String MedicalOrganizationCd) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            MedicalOrganizationPatientAdapter adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
            MedicalOrganizationPatientEntity entity = adapter.findByPatientIdAndMedicalCd(patientId, MedicalOrganizationCd);

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
