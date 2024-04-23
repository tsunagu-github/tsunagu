package phr.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DosageRecipeAdapter;
import phr.datadomain.entity.DosageRecipeEntity;
import phr.service.IDosageRecipeService;

/**
 *
 * @author iwaasa
 */
public class DosageRecipeService implements IDosageRecipeService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageRecipeService.class);

    @Override
    public List<DosageRecipeEntity> searchRecipeByDosageId(String dosageId, int seq) throws Throwable {
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
             DosageRecipeAdapter adapter = new  DosageRecipeAdapter(dao.getConnection());
            List<DosageRecipeEntity> recipeList = adapter.findByDosageid(dosageId, seq);

            logger.debug("End");
            return recipeList;
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
