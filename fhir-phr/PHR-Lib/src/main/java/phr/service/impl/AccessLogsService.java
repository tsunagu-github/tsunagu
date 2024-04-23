/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.AccessLogsAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.AccessLogsEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IAccessLogsService;

/**
 *
 * @author KISNOTE011
 */
public class AccessLogsService implements IAccessLogsService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccessLogsService.class);

    /**
     * アクセスログの登録
     * @param accessLog
     * @return
     * @throws Throwable 
     */
    @Override
    public AccessLogResultCd insertAccessLog(AccessLogsEntity accessLog) throws Throwable{
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            AccessLogsAdapter adapter = new AccessLogsAdapter(dao.getConnection());
            dao.beginTransaction();
            int iRet = adapter.insert(accessLog);
            dao.commitTransaction();

            logger.debug("End");
            return AccessLogResultCd.SUCCCESS;
        } catch (Throwable ex) {
            logger.error("", ex);
            if(dao!=null){dao.rollbackTransaction();}
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
        return AccessLogResultCd.SQLFAIL;
    }

    /**
     * Phoneアクセス者名取得
     * @param phrid
     * @return
     * @throws Throwable 
     */
    @Override
    public PatientEntity searchAccessUserName(String phrid) throws Throwable{
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            PatientEntity entity = adapter.findByPrimaryKey(phrid);

            logger.debug("End");
            return entity;
        } catch (Throwable ex) {
            logger.error("", ex);
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
        return null;
    }

    /**
     *  結果コード 
     */
    public enum AccessLogResultCd {
        SUCCCESS,
        DATA_NOT_FOUND,
        SQLFAIL;
    }
}
