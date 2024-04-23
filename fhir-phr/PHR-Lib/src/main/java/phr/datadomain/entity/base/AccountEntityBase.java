/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アカウント情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.AccountEntity;

/**
 * アカウント情報のデータオブジェクトです。
 */
public abstract class AccountEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccountEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public AccountEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* アカウントID */
    protected String accountId = null;
    /* ログインID */
    protected String loginId = null;
    /* パスワード */
    protected String password = null;
    /* アカウント名称 */
    protected String name = null;
    /* アカウント種別 */
    protected int accoutTypeCd = 0;
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 初期パスワード有無 */
    protected boolean initPassword = false;
    /* 無効フラグ */
    protected boolean invalid = false;
    /* パスワード有効期限 */
    protected Date passwordExpirationDate = null;
    /* 最終ログイン日時 */
    protected Timestamp lastLoginDateTime = null;
    /* 作成アカウントID */
    protected String createAccoutId = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新アカウントID */
    protected String updateAccoutId = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* ワンタイムパスワード発行権限 */
    protected boolean oneTimePassAuth = false;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AccountEntity
     */
    public static AccountEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AccountEntity
     */
    public static AccountEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        AccountEntity entity = new AccountEntity();
        entity.setAccountId(getString(dataRow, "AccountId"));
        entity.setLoginId(getString(dataRow, "LoginId"));
        entity.setPassword(getString(dataRow, "Password"));
        entity.setName(getString(dataRow, "Name"));
        entity.setAccoutTypeCd(getInt(dataRow, "AccoutTypeCd"));
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setInitPassword(getBoolean(dataRow, "InitPassword"));
        entity.setInvalid(getBoolean(dataRow, "Invalid"));
        entity.setPasswordExpirationDate(getDate(dataRow, "PasswordExpirationDate"));
        entity.setLastLoginDateTime(getDateTime(dataRow, "LastLoginDateTime"));
        entity.setCreateAccoutId(getString(dataRow, "CreateAccoutId"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateAccoutId(getString(dataRow, "UpdateAccoutId"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));
        entity.setOneTimePassAuth(getBoolean(dataRow, "OneTimePassAuth"));

        if (logger.isDebugEnabled())
        {
            logger.debug("アカウントID        ：" + entity.getAccountId());
            logger.debug("ログインID          ：" + entity.getLoginId());
            logger.debug("パスワード          ：" + entity.getPassword());
            logger.debug("アカウント名称      ：" + entity.getName());
            logger.debug("アカウント種別      ：" + entity.getAccoutTypeCd());
            logger.debug("保険者番号          ：" + entity.getInsurerNo());
            logger.debug("初期パスワード有無  ：" + entity.isInitPassword());
            logger.debug("無効フラグ          ：" + entity.isInvalid());
            logger.debug("パスワード有効期限  ：" + entity.getPasswordExpirationDate());
            logger.debug("最終ログイン日時    ：" + entity.getLastLoginDateTime());
            logger.debug("作成アカウントID    ：" + entity.getCreateAccoutId());
            logger.debug("作成日時            ：" + entity.getCreateDateTime());
            logger.debug("最終更新アカウントID：" + entity.getUpdateAccoutId());
            logger.debug("最終更新日時        ：" + entity.getUpdateDateTime());
            logger.debug("ワンタイムパス発行権限：" + entity.getOneTimePassAuth());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * アカウントIDを取得する
     *
     * @return
     */
    public String getAccountId() {
        return this.accountId;
    }
    /**
     * アカウントIDを設定する
     *
     * @param value
     */
    public void setAccountId(String value) {
        this.accountId = value;
    }

    /**
     * ログインIDを取得する
     *
     * @return
     */
    public String getLoginId() {
        return this.loginId;
    }
    /**
     * ログインIDを設定する
     *
     * @param value
     */
    public void setLoginId(String value) {
        this.loginId = value;
    }

    /**
     * パスワードを取得する
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * パスワードを設定する
     *
     * @param value
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * アカウント名称を取得する
     *
     * @return
     */
    public String getName() {
        return this.name;
    }
    /**
     * アカウント名称を設定する
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * アカウント種別を取得する
     *
     * @return
     */
    public int getAccoutTypeCd() {
        return this.accoutTypeCd;
    }
    /**
     * アカウント種別を設定する
     *
     * @param value
     */
    public void setAccoutTypeCd(int value) {
        this.accoutTypeCd = value;
    }

    /**
     * 保険者番号を取得する
     *
     * @return
     */
    public String getInsurerNo() {
        return this.insurerNo;
    }
    /**
     * 保険者番号を設定する
     *
     * @param value
     */
    public void setInsurerNo(String value) {
        this.insurerNo = value;
    }

    /**
     * 初期パスワード有無を取得する
     *
     * @return
     */
    public boolean isInitPassword() {
        return this.initPassword;
    }
    /**
     * 初期パスワード有無を設定する
     *
     * @param value
     */
    public void setInitPassword(boolean value) {
        this.initPassword = value;
    }

    /**
     * 無効フラグを取得する
     *
     * @return
     */
    public boolean isInvalid() {
        return this.invalid;
    }
    /**
     * 無効フラグを設定する
     *
     * @param value
     */
    public void setInvalid(boolean value) {
        this.invalid = value;
    }

    /**
     * パスワード有効期限を取得する
     *
     * @return
     */
    public Date getPasswordExpirationDate() {
        return this.passwordExpirationDate;
    }
    /**
     * パスワード有効期限を設定する
     *
     * @param value
     */
    public void setPasswordExpirationDate(Date value) {
        this.passwordExpirationDate = value;
    }

    /**
     * 最終ログイン日時を取得する
     *
     * @return
     */
    public Timestamp getLastLoginDateTime() {
        return this.lastLoginDateTime;
    }
    /**
     * 最終ログイン日時を設定する
     *
     * @param value
     */
    public void setLastLoginDateTime(Timestamp value) {
        this.lastLoginDateTime = value;
    }

    /**
     * 作成アカウントIDを取得する
     *
     * @return
     */
    public String getCreateAccoutId() {
        return this.createAccoutId;
    }
    /**
     * 作成アカウントIDを設定する
     *
     * @param value
     */
    public void setCreateAccoutId(String value) {
        this.createAccoutId = value;
    }

    /**
     * 作成日時を取得する
     *
     * @return
     */
    public Timestamp getCreateDateTime() {
        return this.createDateTime;
    }
    /**
     * 作成日時を設定する
     *
     * @param value
     */
    public void setCreateDateTime(Timestamp value) {
        this.createDateTime = value;
    }

    /**
     * 最終更新アカウントIDを取得する
     *
     * @return
     */
    public String getUpdateAccoutId() {
        return this.updateAccoutId;
    }
    /**
     * 最終更新アカウントIDを設定する
     *
     * @param value
     */
    public void setUpdateAccoutId(String value) {
        this.updateAccoutId = value;
    }

    /**
     * 最終更新日時を取得する
     *
     * @return
     */
    public Timestamp getUpdateDateTime() {
        return this.updateDateTime;
    }
    /**
     * 最終更新日時を設定する
     *
     * @param value
     */
    public void setUpdateDateTime(Timestamp value) {
        this.updateDateTime = value;
    }
    
    /**
     * ワンタイムパスワード発行権限を取得する
     *
     * @return
     */
    public boolean getOneTimePassAuth() {
        return this.oneTimePassAuth;
    }
    /**
     * 最終更新アカウントIDを設定する
     *
     * @param value
     */
    public void setOneTimePassAuth(boolean value) {
        this.oneTimePassAuth = value;
    }    
    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "accountId=" + accountId + 
                ", loginId=" + loginId + 
                ", password=" + password + 
                ", name=" + name + 
                ", accoutTypeCd=" + accoutTypeCd + 
                ", insurerNo=" + insurerNo + 
                ", initPassword=" + initPassword + 
                ", invalid=" + invalid + 
                ", passwordExpirationDate=" + passwordExpirationDate + 
                ", lastLoginDateTime=" + lastLoginDateTime + 
                ", createAccoutId=" + createAccoutId + 
                ", createDateTime=" + createDateTime + 
                ", updateAccoutId=" + updateAccoutId + 
                ", updateDateTime=" + updateDateTime + 
                ", oneTimePassAuth=" + oneTimePassAuth +                 
                " }\r\n";
    }
}
