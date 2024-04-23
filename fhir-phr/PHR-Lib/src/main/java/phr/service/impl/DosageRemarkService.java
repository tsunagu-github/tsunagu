/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DosageRemarkAdapter;
import phr.datadomain.entity.DosageRemarkEntity;

import phr.service.IDosageRemarkService;

/**
 *
 * @author kis-note-027_user
 */
public class DosageRemarkService implements IDosageRemarkService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageRemarkService.class);

    @Override
    public DosageRemarkEntity getDosageRemark(String dosageID, int seq, int remarkSeq) throws Throwable{
        
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            DosageRemarkAdapter adapter = new DosageRemarkAdapter(dao.getConnection());
            DosageRemarkEntity remark = adapter.findByPrimaryKey(dosageID, seq,remarkSeq);

            logger.debug("End");
            return remark;
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
