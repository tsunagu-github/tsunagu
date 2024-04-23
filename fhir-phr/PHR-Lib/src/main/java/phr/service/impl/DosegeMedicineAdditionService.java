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
import phr.datadomain.adapter.DosageMedicineAdditionAdapter;
import phr.datadomain.entity.DosageMedicineAdditionEntity;
import phr.service.IDosegeMedicineAddition;

/**
 *
 * @author kis-note-027_user
 */
public class DosegeMedicineAdditionService implements IDosegeMedicineAddition{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosegeMedicineAdditionService.class);

    @Override
    public DosageMedicineAdditionEntity getMedicineAddition(String dosageID, int seq, int recipeNo, int medicineSeq) throws Throwable{
        
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            DosageMedicineAdditionAdapter adapter = new DosageMedicineAdditionAdapter(dao.getConnection());
            DosageMedicineAdditionEntity medicineAdd = adapter.findByPrimaryKey(dosageID, seq, recipeNo,medicineSeq,1);

            logger.debug("End");
            return medicineAdd;
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
