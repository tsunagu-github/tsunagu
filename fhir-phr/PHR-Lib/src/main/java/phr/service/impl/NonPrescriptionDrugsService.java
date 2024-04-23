
package phr.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.NonPrescriptionDrugsAdapter;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;
import phr.service.INonPrescriptionDrugsService;

/**
 *
 * @author iwaasa
 */
public class NonPrescriptionDrugsService implements INonPrescriptionDrugsService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NonPrescriptionDrugsService.class);

    @Override
    public List<NonPrescriptionDrugsEntity> searchMedicineByDosageId(String dosageId) throws Throwable {
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            NonPrescriptionDrugsAdapter adapter = new NonPrescriptionDrugsAdapter(dao.getConnection());
            List<NonPrescriptionDrugsEntity> nonpreList = adapter.findByDosageid(dosageId);

            logger.debug("End");
            return nonpreList;
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