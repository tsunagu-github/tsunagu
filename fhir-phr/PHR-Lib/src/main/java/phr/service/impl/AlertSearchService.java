/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationAlertAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.ViewPasswordAdapter;
import phr.datadomain.entity.ObservationAlertEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.ViewPasswordEntity;
import phr.service.IAlertSearchService;

/**
 *
 * アラート一覧画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class AlertSearchService implements IAlertSearchService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AlertSearchService.class);
    
    /**
     * アラート対象者情報の取得を行う
     * @param insurerNo
     * @param phrId
     * @param startDate
     * @param endDate
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public List<ObservationAlertEntity> alertSearch(String insurerNo, String phrId, Timestamp startDate, Timestamp endDate) throws Throwable {
        
        DataAccessObject dao = null;
        List<ObservationAlertEntity> entityList = null;
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            ObservationAlertAdapter adapter = new ObservationAlertAdapter(dao.getConnection());
            entityList = adapter.findByAlertList(insurerNo, phrId, startDate, endDate);
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

        return entityList;
    }
    
    /**
     * 患者情報の取得を行う
     * @param phrId
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public PatientEntity patientInfoSearch(String phrId) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            PatientEntity entity = adapter.findByPrimaryKey(phrId);
            
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
    
    /*** ワンタイムパスワードの検索を行います。
     * @throws Throwable
     */    
    @Override
    public AlertSearchService.OneTimePasswordResult searchPassword(String Password,String insurerId,String MedicalOrgCd) throws Throwable {
        logger.debug("Start");
        ViewPasswordEntity entity;
        AlertSearchService.OneTimePasswordResult res = new AlertSearchService.OneTimePasswordResult();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ViewPasswordAdapter adapter = new ViewPasswordAdapter(dao.getConnection());
            entity = adapter.getAliveViewPasswordByPHR_ID(Password,insurerId,MedicalOrgCd);
            if (entity != null) {
                res.setPHR_ID(entity.getPHR_ID());
                res.setPassword(entity.getPassword());
                res.setExpirationDate(entity.getExpirationDate());
                res.setResultCd(AlertSearchService.OnetimePasswordResultCd.SUCCCESS);
            } else {
                res.setResultCd(AlertSearchService.OnetimePasswordResultCd.DATA_NOT_FOUND);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            res.setResultCd(AlertSearchService.OnetimePasswordResultCd.SQLFAIL);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return res;
    }
    
        public class OneTimePasswordResult {

        String password = null;
        String phrid = null;
        Timestamp expirationDate = null;
        OnetimePasswordResultCd ResultCd;

        /**
         * PHRIDの設定
         *
         * @param value
         */
        public void setPHR_ID(String value) {
            this.phrid = value;
        }

        /**
         * PHRIDの取得
         *
         * @return
         */
        public String getPHR_ID() {
            return this.phrid;
        }

        public OneTimePasswordResult() {
        }

        /**
         * パスワードの設定
         *
         * @param value
         */
        public void setPassword(String value) {
            this.password = value;
        }

        /**
         * パスワードの取得
         *
         * @return 参照用パスワード取得
         */
        public String getPassword() {
            return this.password;
        }

        /**
         * パスワードの設定
         *
         * @param value
         */
        public void setExpirationDate(Timestamp value) {
            this.expirationDate = value;
        }

        /**
         * パスワードの取得
         *
         * @return 参照用パスワード取得
         */
        public Timestamp getExpirationDate() {
            return this.expirationDate;
        }

        /**
         * 更新結果の設定
         *
         * @param value
         */
        public void setResultCd(OnetimePasswordResultCd value) {
            this.ResultCd = value;
        }

        /**
         * 更新結果の取得
         *
         * @return 更新結果
         */
        public OnetimePasswordResultCd getResultCd() {
            return this.ResultCd;
        }

    }

    public enum OnetimePasswordResultCd {
        SUCCCESS,
        DATA_NOT_FOUND,
        SQLFAIL;
    }

}
