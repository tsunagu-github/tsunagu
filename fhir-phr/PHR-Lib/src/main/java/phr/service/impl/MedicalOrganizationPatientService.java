package phr.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.service.IMedicalOrganizationPatientService;

/**
 * 
 * @author kisvdi017
 *
 */
public class MedicalOrganizationPatientService implements IMedicalOrganizationPatientService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalOrganizationPatientService.class);

    /**
     * 対象患者の閲覧同意の確認
     */
    @Override
    public MedicalOrganizationPatientEntity findByPrimaryKey(String medicalOrganizationCd, String phrId) throws Throwable {
        logger.debug("Start");
        MedicalOrganizationPatientEntity entity = null;

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            MedicalOrganizationPatientAdapter adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
            entity = adapter.findByPrimaryKey(medicalOrganizationCd, phrId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        logger.debug("End");
        return entity;
    }

}
