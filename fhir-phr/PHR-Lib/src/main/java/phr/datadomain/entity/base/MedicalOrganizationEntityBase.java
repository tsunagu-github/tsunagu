/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関情報のデータオブジェクト
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
import phr.datadomain.entity.MedicalOrganizationEntity;

/**
 * 医療機関情報のデータオブジェクトです。
 */
public abstract class MedicalOrganizationEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalOrganizationEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public MedicalOrganizationEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 医療機関コード */
    protected String medicalOrganizationCd = null;
    /* 医療機関名称 */
    protected String medicalOrganizationName = null;
    /* 郵便番号 */
    protected String zipCode = null;
    /* 住所 */
    protected String address = null;
    /* 電話番号 */
    protected String telNo = null;
    /* パスワード */
    protected String password = null;
    /* 初期パスワード有無 */
    protected boolean initPassword = false;
    /* パスワード有効期限 */
    protected Date passwordExpirationDate = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  MedicalOrganizationEntity
     */
    public static MedicalOrganizationEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  MedicalOrganizationEntity
     */
    public static MedicalOrganizationEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        MedicalOrganizationEntity entity = new MedicalOrganizationEntity();
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setMedicalOrganizationName(getString(dataRow, "MedicalOrganizationName"));
        entity.setZipCode(getString(dataRow, "ZipCode"));
        entity.setAddress(getString(dataRow, "Address"));
        entity.setTelNo(getString(dataRow, "TelNo"));
        entity.setPassword(getString(dataRow, "Password"));
        entity.setInitPassword(getBoolean(dataRow, "InitPassword"));
        entity.setPasswordExpirationDate(getDate(dataRow, "PasswordExpirationDate"));

        if (logger.isDebugEnabled())
        {
            logger.debug("医療機関コード    ：" + entity.getMedicalOrganizationCd());
            logger.debug("医療機関名称      ：" + entity.getMedicalOrganizationName());
            logger.debug("郵便番号          ：" + entity.getZipCode());
            logger.debug("住所              ：" + entity.getAddress());
            logger.debug("電話番号          ：" + entity.getTelNo());
            logger.debug("パスワード        ：" + entity.getPassword());
            logger.debug("初期パスワード有無：" + entity.isInitPassword());
            logger.debug("パスワード有効期限：" + entity.getPasswordExpirationDate());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 医療機関コードを取得する
     *
     * @return
     */
    public String getMedicalOrganizationCd() {
        return this.medicalOrganizationCd;
    }
    /**
     * 医療機関コードを設定する
     *
     * @param value
     */
    public void setMedicalOrganizationCd(String value) {
        this.medicalOrganizationCd = value;
    }

    /**
     * 医療機関名称を取得する
     *
     * @return
     */
    public String getMedicalOrganizationName() {
        return this.medicalOrganizationName;
    }
    /**
     * 医療機関名称を設定する
     *
     * @param value
     */
    public void setMedicalOrganizationName(String value) {
        this.medicalOrganizationName = value;
    }

    /**
     * 郵便番号を取得する
     *
     * @return
     */
    public String getZipCode() {
        return this.zipCode;
    }
    /**
     * 郵便番号を設定する
     *
     * @param value
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    /**
     * 住所を取得する
     *
     * @return
     */
    public String getAddress() {
        return this.address;
    }
    /**
     * 住所を設定する
     *
     * @param value
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * 電話番号を取得する
     *
     * @return
     */
    public String getTelNo() {
        return this.telNo;
    }
    /**
     * 電話番号を設定する
     *
     * @param value
     */
    public void setTelNo(String value) {
        this.telNo = value;
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

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "edicalOrganizationCd=" + medicalOrganizationCd + 
                ", edicalOrganizationName=" + medicalOrganizationName + 
                ", ipCode=" + zipCode + 
                ", ddress=" + address + 
                ", elNo=" + telNo + 
                ", assword=" + password + 
                ", nitPassword=" + initPassword + 
                ", asswordExpirationDate=" + passwordExpirationDate + 
                " }\r\n";
    }
}
