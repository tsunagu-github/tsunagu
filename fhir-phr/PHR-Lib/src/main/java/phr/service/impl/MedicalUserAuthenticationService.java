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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import kis.inc.ssmix2storagemaker.utility.TypeUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.HPKIAccountAdapter;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import phr.datadomain.adapter.OneTimeIdInfoAdapter;
import phr.datadomain.entity.HPKIAccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.OneTimeIdInfoEntity;
import phr.service.IMedicalUserAuthenticationService;
import phr.utility.AesUtility;

/**
 * ユーザ認証を行うサービスクラス
 *
 * @author daisuke
 */
public class MedicalUserAuthenticationService implements IMedicalUserAuthenticationService{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalUserAuthenticationService.class);

    /**
    * AesUtility
    */
   private static AesUtility aesUtility = null;
   
    /**
     * 保険者Userの認証を行う
     *
     * @param loingId
     * @param password
     * @return
     */
    public HPKIAccountEntity authenticationInsurerUser(String loingId,String medicalOrganizationCd, String password) throws Throwable {
//        public MedicalOrganizationEntity authenticationInsurerUser(String loingId, String password) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();

//            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
//            MedicalOrganizationEntity entity = adapter.findByPrimaryKey(loingId);
            
            String key = PhrConfig.getSystemConfigProperty(SystemConfigConst.PASSWORD_ENCRIPT_KEY);
            aesUtility = new AesUtility(key);
            // passwordの暗号化
            String convertedPass = aesUtility.encrypt(password);
            
            HPKIAccountAdapter adapter = new HPKIAccountAdapter(dao.getConnection());
            HPKIAccountEntity entity = adapter.findByPrimaryKey(loingId, convertedPass);

            // 存在のチェック
            if (entity == null) {
                logger.debug("End");
                return entity;
            }

            // パスワードのチェック
            if (!medicalOrganizationCd.equals(entity.getMedicalOrganizationCd())) {
                logger.debug("End");
                return null;
            }

            // 有効期限のチェック（初期パスワードのユーザ以外）
//            if (!entity.isInitPassword()) {
                Long pwPeriod = Long.parseLong(PhrConfig.getConfigProperty(ConfigConst.PASSWORD_VALIDITY_PERIOD));
                Date dtPeriod = new Date(new Date().getTime() + (pwPeriod * 24 * 60 * 60 * 1000));    //今日に有効日数を加算（日数をミリ秒に変換し）
                if (entity.getLastLoginDateTime() == null || entity.getLastLoginDateTime().after(dtPeriod)) {
////                    //期間終了、パスワード変更画面を表示させる
//                    entity.setInitPassword(true);
                    logger.debug("End");
                    return null;
                }
//                logger.debug("End");
//                return null;
//            }

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
     * 医療機関の認証を行う
     *
     * @param loingId
     * @param password
     * @return
     */
    @Override
    public OneTimeIdInfoEntity authenticationNonHPKIUser(String loingId, String password) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();

            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            MedicalOrganizationEntity entity = adapter.findByPrimaryKey(loingId);

            // 存在のチェック
            if (entity == null) {
                logger.debug("End");
                return null;
            }

            // パスワードのチェック
            if (!password.equals(entity.getDecriptPassword())) {
                logger.debug("End");
                return null;
            }

            // 有効期限のチェック（初期パスワードのユーザ以外）
            if (!entity.isInitPassword()) {
                Long pwPeriod = Long.parseLong(PhrConfig.getConfigProperty(ConfigConst.PASSWORD_VALIDITY_PERIOD));
                Date dtPeriod = new Date(new Date().getTime() + (pwPeriod * 24 * 60 * 60 * 1000));    //今日に有効日数を加算（日数をミリ秒に変換し）
                if (entity.getPasswordExpirationDate() == null
                        || entity.getPasswordExpirationDate().after(dtPeriod)) {
                    //期間終了、パスワード変更画面を表示させる
                    entity.setInitPassword(true);
                }
                logger.debug("End");
            }
            OneTimeIdInfoEntity newEntity = createOneTimeIdInfo(dao, null, entity.getMedicalOrganizationCd(), null);

            return newEntity;
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
     * HPKI医師の認証を行う
     *
     * @param commonName
     * @param serialNo
     * @return
     * @throws java.lang.Throwable
     */
    @Override
    public OneTimeIdInfoEntity authenticationHPKIUser(String commonName, String serialNo) throws Throwable {
        DataAccessObject dao = null;            
        try {
            logger.debug("Start");
            dao = new DataAccessObject();

            HPKIAccountAdapter adapter = new HPKIAccountAdapter(dao.getConnection());
            HPKIAccountEntity entity = adapter.findByPrimaryKey(commonName, serialNo);
            if (entity == null) {
                logger.debug("-------------------------------------------------------------");
                logger.debug("-- 該当アカウント情報無し");
                logger.debug("-------------------------------------------------------------");
                logger.debug("CommonName:" + commonName);
                logger.debug("SerialNo  :" + serialNo);
                logger.debug("-------------------------------------------------------------");
                return null;
            }
            OneTimeIdInfoEntity newEntity = createOneTimeIdInfo(dao, entity.getInsurerNo(), entity.getMedicalOrganizationCd(), commonName);

            return newEntity;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.debug("End");
        }
    }
    /**
     * ワンタイムIDの認証を行う
     *
     * @param oneTimeId
     * @return
     * @throws java.lang.Throwable
     */
    @Override
    public MedicalOrganizationEntity authenticationOneTimeId(String oneTimeId) throws Throwable {
        DataAccessObject dao = null;            
        try {
            logger.debug("Start");
            dao = new DataAccessObject();

            OneTimeIdInfoAdapter adapter = new OneTimeIdInfoAdapter(dao.getConnection());
            OneTimeIdInfoEntity entity = adapter.findByPrimaryKey(oneTimeId);
            if (entity == null) {
                logger.debug("-------------------------------------------------------------");
                logger.debug("-- 該当ワンタイムID無し");
                logger.debug("-------------------------------------------------------------");
                logger.debug("OneTimeId:" + oneTimeId);
                logger.debug("-------------------------------------------------------------");
                return null;
            }
            
            // 有効期限のチェック
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            logger.debug("NOW:"  + TypeUtility.format(now, "yyyy/MM/dd hh:mm:ss.SSS"));
            logger.debug("DBD:" + TypeUtility.format(entity.getExpirationDate(), "yyyy/MM/dd hh:mm:ss.SSS"));
            if (entity.getExpirationDate().getTime() < now.getTime()) {
                logger.debug("-------------------------------------------------------------");
                logger.debug("-- 該当ワンタイムID有効期限切れ");
                logger.debug("-------------------------------------------------------------");
                logger.debug("OneTimeId:" + oneTimeId);
                logger.debug("-------------------------------------------------------------");
                return null;
            }
            
            MedicalOrganizationAdapter medicalOrganizationAdapter = new MedicalOrganizationAdapter(dao.getConnection());
            MedicalOrganizationEntity medicalOrganizationEntity = medicalOrganizationAdapter.findByPrimaryKey(entity.getMedicalOrganizationCd());
            medicalOrganizationEntity.setAccountName(entity.getCommonName());
            return medicalOrganizationEntity;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.debug("End");
        }
    }    
    /**
     * ワンタイムID情報を作成する
     * @param dao
     * @param insurerNo
     * @param medicalOrganizationCd
     * @param commonName
     * @return
     * @throws Throwable 
     */
    private OneTimeIdInfoEntity createOneTimeIdInfo( DataAccessObject dao, String insurerNo, String medicalOrganizationCd, String commonName) throws Throwable {
        OneTimeIdInfoAdapter oneTimeIdInfoAdapter = new OneTimeIdInfoAdapter(dao.getConnection());

        // UUIDでワンタイムIDを生成
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        uniqueId = uniqueId.replaceAll("-", "");
        uniqueId = uniqueId.replaceAll("\\/", "l");
        
        // 10秒間有効
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);


        OneTimeIdInfoEntity newEntity = new OneTimeIdInfoEntity();
        newEntity.setOneTimeId(uniqueId);
        newEntity.setCommonName(commonName);
        newEntity.setInsurerNo(insurerNo);
        newEntity.setMedicalOrganizationCd(medicalOrganizationCd);
        newEntity.setExpirationDate(new Timestamp(calendar.getTime().getTime()));

        logger.debug("NOW:"  + TypeUtility.format(calendar.getTime(), "yyyy/MM/dd hh:mm:ss.SSS"));
        logger.debug("DBD:" + TypeUtility.format(newEntity.getExpirationDate(), "yyyy/MM/dd hh:mm:ss.SSS"));

        dao.beginTransaction();
        oneTimeIdInfoAdapter.insert(newEntity);
        dao.commitTransaction();
        return newEntity;
    }
}
