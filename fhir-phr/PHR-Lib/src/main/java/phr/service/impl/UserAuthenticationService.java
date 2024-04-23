/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：ユーザ認証を行うサービスクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.AccountAdapter;
import phr.datadomain.adapter.AppAccessLogAdapter;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.AppAccessLogEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IUserAuthenticationService;

/**
 * ユーザ認証を行うサービスクラス
 *
 * @author daisuke
 */
public class UserAuthenticationService implements IUserAuthenticationService {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UserAuthenticationService.class);

    /**
     * 保険者Userの認証を行う
     *
     * @param loingId
     * @param password
     * @return
     */
    @Override
    public AccountEntity authenticationInsurerUser(String loingId, String password) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();

            AccountAdapter adapter = new AccountAdapter(dao.getConnection());
            AccountEntity entity = adapter.findByLoginId(loingId);

            // 存在のチェック
            if (entity == null) {
                logger.debug("End");
                return entity;
            }

            // パスワードのチェック
            if (!password.equals(entity.getDecriptPassword())) {
                logger.debug("End");
                return null;
            }

            // 無効のチェック
            if (entity.isInvalid()) {
                logger.debug("End");
                return null;
            }

            // 有効期限のチェック（初期パスワードのユーザ以外）
            if (!entity.isInitPassword()) {
                Long pwPeriod = Long.parseLong(PhrConfig.getConfigProperty(ConfigConst.PASSWORD_VALIDITY_PERIOD));
                Date dtPeriod = new Date((new Date().getTime() - (pwPeriod * 24 * 60 * 60 * 1000)));    //今日に有効日数を減算（日数をミリ秒に変換し）
                if (entity.getPasswordExpirationDate() == null
                        || entity.getPasswordExpirationDate().before(dtPeriod)){
                    //期間終了、パスワード変更画面を表示させる
                    entity.setInitPassword(true);
                }
                logger.debug("End");
//                return null;
            }

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
     * 入力パスワードがDBのパスワードと一致するか確認
     *
     * @param entity　確認対象情報
     * @param currentPassWord　確認対象パスワード文字列
     * @return true 一致する,false 一致しない
     * @throws Throwable
     */
    @Override
    public boolean chkCurrentPass(AccountEntity entity, String currentPassWord) throws Throwable {
        logger.debug("Start");

        boolean rtnPassEncrypt = false;
        DataAccessObject dao = new DataAccessObject();
        try{
            AccountAdapter adapter = new AccountAdapter(dao.getConnection());
            AccountEntity dbEntity = adapter.findByLoginId(entity.getLoginId());

            //パスワードの入力確認
            if (dbEntity != null && dbEntity.getDecriptPassword().equals(currentPassWord)) {
                rtnPassEncrypt = true;
            }
        }catch(Exception e){
            logger.error(e);
            throw e;
    	}finally{
            try{
                dao.close();
            }catch(Exception e){
                logger.debug(e);
            }
    	}

        logger.debug("end");
        return rtnPassEncrypt;
    }

    /**
     * 最終ログイン日時を更新
     *
     * @param entity　更新対象ユーザ情報
     * @throws Throwable
     */
    @Override
    public void updateLastloginDateTime(AccountEntity entity) throws Throwable {
        logger.debug("Start");

        DataAccessObject dao = new DataAccessObject();
        try {
            AccountAdapter adapter = new AccountAdapter(dao.getConnection());
            dao.beginTransaction();
            entity.setLastLoginDateTime(new Timestamp(new Date().getTime()));
            adapter.update(entity);
            dao.commitTransaction();
        } catch (Exception ex) {
            dao.rollbackTransaction();
            logger.error(ex);
            throw ex;
    	}finally{
            try{
                dao.close();
            }catch(Exception e){
                logger.debug(e);
            }
        }

        logger.debug("end");
    }

    /**
     * 利用者の認証を行う
     *
     * @param phrId
     * @param timestamp
     * @param keyValue
     * @param tokenId
     * @return
     * @throws Throwable
     */
    @Override
    public PatientEntity authenticationPatient(String phrId, String timestamp, String keyValue, String tokenId) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();

            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            PatientEntity entity = adapter.findByPrimaryKey(phrId);

            // 存在のチェック
            if (entity == null) {
                logger.debug("End 存在のチェックFalse[" + phrId + "]");
                return entity;
            }

            InsurerPatientAdapter insurerPatientAdapter = new InsurerPatientAdapter(dao.getConnection());
            InsurerPatientEntity insurerPatientEntity = insurerPatientAdapter.findByPhrId(phrId);
            if (insurerPatientEntity == null) {
                logger.debug("End 被被験者チェックFalse[" + phrId + "]");
                return null;
            }

            // 保険者Noを設定しておく
            entity.setInsurerNo(insurerPatientEntity.getInsurerNo());

            // Hash値を計算して比較する
            String source = timestamp + entity.getKyeId();
            String hash = getHashedStirng(source);
            if (!hash.equals(keyValue)) {
                logger.debug("End Key NG");
                return null;
            }

            // Push用トークン登録有無を設定しておく
            entity.setTokenRegistered(entity.getTokenId() != null);

            // tokenIdが送られてきた場合、Update
            if(tokenId != null && !tokenId.isEmpty()) {
                try {
                    dao.beginTransaction();
                    int success = adapter.updatePatientToken(phrId, tokenId);
                    dao.commitTransaction();
                    if(success > 0) entity.setTokenRegistered(true);
                } catch (Throwable ex) {
                    if (dao != null)
                        dao.rollbackTransaction();
                    logger.error("", ex);
                    //throw ex;
                }
            }

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
     * ハッシュ文字列を返します。
     *
     * @param normalString 文字列
     * @return ハッシュ化した文字列
     * @throws java.lang.Exception
     */
    public static String getHashedStirng(String normalString) throws Exception {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
            throw new Exception(e);
        }
        md.reset();
        md.update(normalString.getBytes());
        byte[] hash = md.digest();

        // ハッシュを16進数文字列に変換
        StringBuilder sb = new StringBuilder();
        int cnt = hash.length;
        for (int i = 0; i < cnt; i++) {
            sb.append(Integer.toHexString((hash[i] >> 4) & 0x0F));
            sb.append(Integer.toHexString(hash[i] & 0x0F));
        }
        return sb.toString();
    }
    
     /**
     * 入力パスワードがDBのパスワードと一致するか確認(医療機関）
     *
     * @param entity　確認対象情報
     * @param currentPassWord　確認対象パスワード文字列
     * @return true 一致する,false 一致しない
     * @throws Throwable
     */
    @Override
    public boolean chkCurrentPassMedical(MedicalOrganizationEntity entity, String currentPassWord) throws Throwable {
        logger.debug("Start");

        boolean rtnPassEncrypt = false;
        DataAccessObject dao = new DataAccessObject();
        try{
            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            MedicalOrganizationEntity dbEntity = adapter.findByPrimaryKey(entity.getMedicalOrganizationCd());

            //パスワードの入力確認
            if (dbEntity != null && dbEntity.getDecriptPassword().equals(currentPassWord)) {
                rtnPassEncrypt = true;
            }
        }catch(Exception e){
            logger.error(e);
            throw e;
    	}finally{
            try{
                dao.close();
            }catch(Exception e){
                logger.debug(e);
            }
    	}

        logger.debug("end");
        return rtnPassEncrypt;
    }

    /**
     * アプリ起動時にサーバに存在確認のリクエストを投げる
     * @param phr_id
     * @return rowCount
     */
    public int insertRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        int rowCount = 0;
        DataAccessObject dao = new DataAccessObject();
        AppAccessLogAdapter adapter = new AppAccessLogAdapter(dao.getConnection());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        // まず、Appアクセスログテーブルから最大のSeq_Idを取得する
        AppAccessLogEntity entity = new AppAccessLogEntity();
        entity = adapter.searchRecord();
        int seq_Id = 0;
        if (entity != null && entity.getPHR_ID() != null) {
            seq_Id = entity.getSeq_Id() + 1;
        }
        
        AppAccessLogEntity insertEntity = new AppAccessLogEntity();
        insertEntity.setSeq_Id(seq_Id);
        insertEntity.setPHR_ID(phr_id);
        insertEntity.setAccessDateTime(timestamp);
        rowCount = adapter.insertRecord(insertEntity);
        
        logger.debug("End");
        return rowCount;
    }

    /**
     * Appアクセスログテーブルから該当利用者のアクセス日時が最新のレコードを取得
     * @param phr_id
     * @return rowCount
     */
    public AppAccessLogEntity findByPhrId(String phr_id) throws Throwable {
        logger.debug("Start");
        AppAccessLogEntity entity = new AppAccessLogEntity();
        
        try {
            DataAccessObject dao = new DataAccessObject();
            AppAccessLogAdapter adapter = new AppAccessLogAdapter(dao.getConnection());
            entity = adapter.findByPhrId(phr_id);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
        
        logger.debug("End");
        return entity;
    }
}
