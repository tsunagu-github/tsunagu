/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アカウント情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;
import phr.utility.AesUtility;

/**
 * アカウント情報のデータオブジェクトです。
 */
public class AccountEntity extends AccountEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccountEntity.class);

    /**
     * AesUtility
     */
    private static AesUtility aesUtility = null;
    
    /**
     * 保険者名称
     */
    private String insurerName;
    
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public AccountEntity()
    {
    }
    
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
    /* -------------------------------------------------------------------------------------- */

    /**
     * @return the insurerName
     */
    public String getInsurerName() {
        return insurerName;
    }

    /**
     * @param insurerName the insurerName to set
     */
    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }

}
