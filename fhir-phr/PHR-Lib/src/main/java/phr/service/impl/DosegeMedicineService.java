
package phr.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DosageMedicineAdapter;
import phr.datadomain.entity.DosageMedicineEntity;
import phr.service.IDosegeMedicineService;

/**
 *
 * @author iwaasa
 */
public class DosegeMedicineService implements IDosegeMedicineService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosegeMedicineService.class);

    @Override
    public List<DosageMedicineEntity> searchMedicineByDosageId(String dosageId, int seq, int recipeNo) throws Throwable {
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            DosageMedicineAdapter adapter = new DosageMedicineAdapter(dao.getConnection());
            List<DosageMedicineEntity> medicineList = adapter.findByDosageid(dosageId, seq, recipeNo);

            logger.debug("End");
            return medicineList;
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
