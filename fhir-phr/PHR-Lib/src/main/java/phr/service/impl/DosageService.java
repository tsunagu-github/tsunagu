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
import phr.datadomain.adapter.DosageAdapter;
import phr.datadomain.entity.DosageEntity;
import phr.service.IDosageService;

/**
 *
 * @author KISNOTE011
 */
public class DosageService implements IDosageService {
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AlertSearchService.class);

    /**
     * 対象年度の検査結果から基準日の直近の値を検索する
     * @param phrid 対象のPHRID
     * @param basedate 対象の基準日
     * @return
     * @throws Throwable 
     */
    @Override
    public List<DosageEntity> searchDosageByPhrid(String phrid,java.sql.Date basedate) throws Throwable{
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            DosageAdapter adapter = new DosageAdapter(dao.getConnection());
            List<DosageEntity> entity = adapter.findByIdPhrid(phrid, basedate);

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
    
    /**
     * DosageId,Seq,PHRIDからおくすりの詳細リストを検索する
     * @param dosageId 対象の調剤ID
     * @param seq 対象の調剤番号
     * @param phrid 対象のPHRID
     * @return
     * @throws Throwable 
     */
    @Override
    public List<DosageEntity> searchDosageByDosageIdSeqPhrid(String dosageId,String seq,String phrid) throws Throwable{
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            DosageAdapter adapter = new DosageAdapter(dao.getConnection());
            List<DosageEntity> entity = adapter.findByDosageIdSeqPhrid(dosageId,seq, phrid);

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
