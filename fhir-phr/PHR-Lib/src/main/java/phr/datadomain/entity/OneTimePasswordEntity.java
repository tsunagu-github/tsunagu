/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ引継ぎ情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author chiba
 */
public class OneTimePasswordEntity {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OneTimePasswordEntity.class);

    protected String oneTimePassword = null;
    protected String expirationDate = null;
    protected String oneTimePasswordResultCd = null;

    
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public OneTimePasswordEntity()
    {
    }
    
    public String getOneTimePassword() {
        return this.oneTimePassword;
    }
    public String getExpirationDate() {
        return this.expirationDate;
    }
    public String getOneTimePasswordResultCd() {
        return this.oneTimePasswordResultCd;
    }

}
