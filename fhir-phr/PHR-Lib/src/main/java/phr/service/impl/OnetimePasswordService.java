/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ViewPasswordAdapter;
import phr.datadomain.entity.ViewPasswordEntity;
import phr.service.IOnetimePasswordService;

/**
 *
 * @author kis-note-025
 */
public class OnetimePasswordService implements IOnetimePasswordService {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OnetimePasswordService.class);

    //ワンタイムパスワード長
    private static final int PASS_LENGTH = 6;
    //パスワード候補文字
    private static final String PASS_CANDIDATE_STRING = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

//<editor-fold defaultstate="collapsed" desc="createOneTimePassword">
    /**
     * ワンタイムパスワードを発行する。
     *
     * @param PHRID
     * @return
     * @throws Throwable
     */
    @Override
    public OneTimePasswordResult createOneTimePassword(String PHRID) throws Throwable {
        logger.debug("Start");

        int retryCnt = 1000;
        String password;
        DataAccessObject dao = null;
        ViewPasswordEntity entity = null;
        OneTimePasswordResult res = new OneTimePasswordResult();
        for (int i = 0; i < retryCnt; i++) {
            password = createRandomString();
            logger.debug("password:" + password);

            try {
                dao = new DataAccessObject();
                ViewPasswordAdapter adapter = new ViewPasswordAdapter(dao.getConnection());
                entity = adapter.findByPrimaryKey(password);

            } catch (Throwable ex) {
                //エラーが発生した場合はエラーを通知する。
                logger.error("", ex);
                res.setResultCd(OnetimePasswordResultCd.SQLFAIL);
                break;
            } finally {
                if (dao != null) {
                    dao.close();
                }
            }

            //該当のパスワードが存在しない場合
            if (entity == null) {
                try {
                    //登録用のEntityを作成
                    ViewPasswordEntity ve = new ViewPasswordEntity();
                    ve.setPHR_ID(PHRID);
                    ve.setPassword(password);
                    Calendar cl = Calendar.getInstance();
                    cl.add(Calendar.DAY_OF_MONTH, 1);
                    cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                    cl.set(Calendar.MILLISECOND, 0);
                    Timestamp ts = new Timestamp(cl.getTimeInMillis());
                    ve.setExpirationDate(ts);

                    dao = new DataAccessObject();
                    ViewPasswordAdapter adapter = new ViewPasswordAdapter(dao.getConnection());

                    dao.beginTransaction();	// トランザクション開始
                    adapter.insert(ve);
                    dao.commitTransaction();	// コミット

                    //結果の設定
                    res.setPassword((ve.getPassword()));
                    res.setExpirationDate(ve.getExpirationDate());
                    res.setResultCd(OnetimePasswordResultCd.SUCCCESS);
                    break;
                } catch (Throwable ex) {
                    //エラーが発生した場合はエラーを通知する。
                    logger.error("", ex);
                    res.setResultCd(OnetimePasswordResultCd.SQLFAIL);
                    break;
                } finally {
                    if (dao != null) {
                        dao.close();
                    }
                }
            } else {
                logger.info(password + " は既に存在する。再度作成して確認");
            }
        }
        return res;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="deleteExpirationPassword">
    /**
     * 期限切れパスワードの削除
     *
     * @throws Throwable
     */
    @Override
    public void deleteExpirationPassword() throws Throwable {

        Calendar cl = Calendar.getInstance();
        cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cl.set(Calendar.MILLISECOND, 0);
        Timestamp ts = new Timestamp(cl.getTimeInMillis());
        DataAccessObject dao = null;

        try {
            dao = new DataAccessObject();
            ViewPasswordAdapter adapter = new ViewPasswordAdapter(dao.getConnection());
            dao.beginTransaction();	// トランザクション開始
            adapter.DeleteViewPasswordByExpirationDate(ts);
            dao.commitTransaction();	// コミット
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("", ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="deletePassword">
    /**
     *
     * @return @throws java.lang.Throwable
     */
    @Override
    public OneTimePasswordResult deletePassword(String Password) throws Throwable {
        OneTimePasswordResult res = new OneTimePasswordResult();
        res.setResultCd(OnetimePasswordResultCd.SQLFAIL);

        DataAccessObject dao = null;
        ViewPasswordEntity entity = null;

        try {
            dao = new DataAccessObject();
            ViewPasswordAdapter adapter = new ViewPasswordAdapter(dao.getConnection());
            entity = adapter.findByPrimaryKey(Password);
        } catch (ClassNotFoundException | SQLException ex) {
            //エラーが発生した場合はエラーを通知する。
            logger.error("", ex);
            res.setResultCd(OnetimePasswordResultCd.SQLFAIL);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        if (entity != null) {
            try {
                dao = new DataAccessObject();
                ViewPasswordAdapter adapter = new ViewPasswordAdapter(dao.getConnection());
                dao.beginTransaction();	// トランザクション開始
                adapter.delete(entity);
                dao.commitTransaction();	// コミット
                res.setExpirationDate(entity.getExpirationDate());
                res.setPassword(entity.getPassword());
                res.setResultCd(OnetimePasswordResultCd.SUCCCESS);
            } catch (ClassNotFoundException | SQLException ex) {
                logger.error("", ex);
                res.setResultCd(OnetimePasswordResultCd.SQLFAIL);
            } finally {
                if (dao != null) {
                    dao.close();
                }
            }
        } else {
            res.setResultCd(OnetimePasswordResultCd.DATA_NOT_FOUND);
        }

        return res;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="searchPassword">
    @Override
    public OneTimePasswordResult searchPassword(String Password,String insurerId,String MedicalOrgCd) throws Throwable {
        logger.debug("Start");
        ViewPasswordEntity entity;
        OneTimePasswordResult res = new OneTimePasswordResult();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ViewPasswordAdapter adapter = new ViewPasswordAdapter(dao.getConnection());
            entity = adapter.getAliveViewPasswordByPHR_ID(Password,insurerId,MedicalOrgCd);
            if (entity != null) {
                res.setPHR_ID(entity.getPHR_ID());
                res.setPassword(entity.getPassword());
                res.setExpirationDate(entity.getExpirationDate());
                res.setResultCd(OnetimePasswordResultCd.SUCCCESS);
            } else {
                res.setResultCd(OnetimePasswordResultCd.DATA_NOT_FOUND);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            res.setResultCd(OnetimePasswordResultCd.SQLFAIL);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return res;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Private">

    /**
     * 構成されたランダムパスワードの作成
     *
     * @return　ランダム文字列
     */
    private String createRandomString() {
        Random rnd = new Random();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < PASS_LENGTH; i++) {
            int val = rnd.nextInt(PASS_CANDIDATE_STRING.length());
            buf.append(PASS_CANDIDATE_STRING.charAt(val));
        }
        return buf.toString();
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="ワンタイムパスワード操作結果クラス">
    /**
     * ワンタイム用パスワード結果クラス
     */
    public class OneTimePasswordResult {

        String password = null;
        String phrid = null;
        Timestamp expirationDate = null;
        String name = null;
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

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="OnetimePasswordResultCdEnum">
    
    public enum OnetimePasswordResultCd {
        SUCCCESS,
        DATA_NOT_FOUND,
        SQLFAIL;
    }
    
//</editor-fold>
}
