/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import jp.kis_inc.core.utility.TypeUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;
import phr.utility.AesUtility;

/**
 * 医療機関情報のデータオブジェクトです。
 */
public class MedicalOrganizationEntity extends MedicalOrganizationEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalOrganizationEntity.class);

     /**
     * AesUtility
     */
    private static AesUtility aesUtility = null;
    
    /**k
     * 更新フラグ
     */
    private boolean updateFlg;
    /**
     * アカウント名称
     */
    private String accountName = null;
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public MedicalOrganizationEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

        /**
     * 平文のパスワードを取得する
     * @return 
     */
    public String getDecriptPassword() throws Throwable {
        try {
            if (aesUtility == null) {
                String key = PhrConfig.getSystemConfigProperty(SystemConfigConst.PASSWORD_ENCRIPT_KEY);
                aesUtility = new AesUtility(key);
            }
            
            if (this.password == null)
                return null;
            
            return aesUtility.decrypt(this.password);
        } catch (Throwable ex) {
            logger.error("復号に失敗しました", ex);
            throw ex;
        }
    }
    
    /**
     * 平文のパスワードを暗号化し設定する
     * @param password
     * @throws Throwable 
     */
    public void setDecriptPassword(String password) throws Throwable {
        if (aesUtility == null) {
            String key = PhrConfig.getSystemConfigProperty(SystemConfigConst.PASSWORD_ENCRIPT_KEY);
            aesUtility = new AesUtility(key);
        }
        
        if (password == null)
            this.password = password;
        else {
            try {
                this.password = aesUtility.encrypt(password);
            } catch (Throwable ex) {
                logger.error("暗号化に失敗しました", ex);
                throw ex;
            }
        }
    }

    /**
     * @return the updateFlg
     */
    public boolean isUpdateFlg() {
        return updateFlg;
    }

    /**
     * @param updateFlg the updateFlg to set
     */
    public void setUpdateFlg(boolean updateFlg) {
        this.updateFlg = updateFlg;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        if (TypeUtility.isNullOrEmpty(accountName))
            return this.medicalOrganizationName;
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    
}
