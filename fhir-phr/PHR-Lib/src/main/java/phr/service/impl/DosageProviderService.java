
package phr.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DosageProviderAdapter;
import phr.datadomain.entity.DosageProviderEntity;
import phr.service.IDosageProviderService;

/**
 *
 * @author kis-note-027_user
 */
public class DosageProviderService implements IDosageProviderService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageProviderService.class);

    @Override
    public DosageProviderEntity searchDosageProviderByDosageid(String dosageid, int Seq) throws Throwable {
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            DosageProviderAdapter adapter = new DosageProviderAdapter(dao.getConnection());
            DosageProviderEntity entity = adapter.findByDosageid(dosageid, Seq);

            logger.debug("End");
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
