/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：連携医療機関システム情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.MedicalOrganizationSystemEntity;

/**
 * 連携医療機関システム情報のデータオブジェクトです。
 */
public abstract class MedicalOrganizationSystemEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(MedicalOrganizationSystemEntityBase.class);
    private static Logger logger = Logger.getLogger(MedicalOrganizationSystemEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public MedicalOrganizationSystemEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* データ連携システムID */
    protected int dataCooperationSystemId = 0;
    /* 医療機関コード */
    protected String medicalOrganizationCd = null;
    /* システム区分 */
    protected String systemTypeId = null;
    /* FHIRサーバ基底URL */
    protected String fhirServerBaseUrl = null;
    /* 認可サーバURL */
    protected String authorizationServerUrl = null;
    /* クライアントID */
    protected String clientId = null;
    /* クライアントシークレット */
    protected String clientSecret = null;
    /* 備考 */
    protected String note = null;
    /* 無効フラグ */
    protected boolean invalid = false;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  MedicalOrganizationSystemEntity
     */
    public static MedicalOrganizationSystemEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  MedicalOrganizationSystemEntity
     */
    public static MedicalOrganizationSystemEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.debug("Start");
        MedicalOrganizationSystemEntity entity = new MedicalOrganizationSystemEntity();
        entity.setDataCooperationSystemId(getInt(dataRow, "DataCooperationSystemId"));
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setSystemTypeId(getString(dataRow, "SystemTypeId"));
        entity.setFhirServerBaseUrl(getString(dataRow, "FhirServerBaseUrl"));
        entity.setAuthorizationServerUrl(getString(dataRow, "AuthorizationServerUrl"));
        entity.setClientId(getString(dataRow, "ClientId"));
        entity.setClientSecret(getString(dataRow, "ClientSecret"));
        entity.setNote(getString(dataRow, "Note"));
        entity.setInvalid(getBoolean(dataRow, "Invalid"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("データ連携システムID    ：" + entity.getDataCooperationSystemId());
            logger.debug("医療機関コード          ：" + entity.getMedicalOrganizationCd());
            logger.debug("システム区分            ：" + entity.getSystemTypeId());
            logger.debug("FHIRサーバ基底URL       ：" + entity.getFhirServerBaseUrl());
            logger.debug("認可サーバURL           ：" + entity.getAuthorizationServerUrl());
            logger.debug("クライアントID          ：" + entity.getClientId());
            logger.debug("クライアントシークレット：" + entity.getClientSecret());
            logger.debug("備考                    ：" + entity.getNote());
            logger.debug("無効フラグ              ：" + entity.isInvalid());
            logger.debug("作成日時                ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時            ：" + entity.getUpdateDateTime());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * データ連携システムIDを取得する
     *
     * @return
     */
    public int getDataCooperationSystemId() {
        return this.dataCooperationSystemId;
    }
    /**
     * データ連携システムIDを設定する
     *
     * @param value
     */
    public void setDataCooperationSystemId(int value) {
        this.dataCooperationSystemId = value;
    }

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
     * システム区分を取得する
     *
     * @return
     */
    public String getSystemTypeId() {
        return this.systemTypeId;
    }
    /**
     * システム区分を設定する
     *
     * @param value
     */
    public void setSystemTypeId(String value) {
        this.systemTypeId = value;
    }

    /**
     * FHIRサーバ基底URLを取得する
     *
     * @return
     */
    public String getFhirServerBaseUrl() {
        return this.fhirServerBaseUrl;
    }
    /**
     * FHIRサーバ基底URLを設定する
     *
     * @param value
     */
    public void setFhirServerBaseUrl(String value) {
        this.fhirServerBaseUrl = value;
    }

    /**
     * 認可サーバURLを取得する
     *
     * @return
     */
    public String getAuthorizationServerUrl() {
        return this.authorizationServerUrl;
    }
    /**
     * 認可サーバURLを設定する
     *
     * @param value
     */
    public void setAuthorizationServerUrl(String value) {
        this.authorizationServerUrl = value;
    }

    /**
     * クライアントIDを取得する
     *
     * @return
     */
    public String getClientId() {
        return this.clientId;
    }
    /**
     * クライアントIDを設定する
     *
     * @param value
     */
    public void setClientId(String value) {
        this.clientId = value;
    }

    /**
     * クライアントシークレットを取得する
     *
     * @return
     */
    public String getClientSecret() {
        return this.clientSecret;
    }
    /**
     * クライアントシークレットを設定する
     *
     * @param value
     */
    public void setClientSecret(String value) {
        this.clientSecret = value;
    }

    /**
     * 備考を取得する
     *
     * @return
     */
    public String getNote() {
        return this.note;
    }
    /**
     * 備考を設定する
     *
     * @param value
     */
    public void setNote(String value) {
        this.note = value;
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

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "dataCooperationSystemId=" + dataCooperationSystemId + 
                ", medicalOrganizationCd=" + medicalOrganizationCd + 
                ", systemTypeId=" + systemTypeId + 
                ", fhirServerBaseUrl=" + fhirServerBaseUrl + 
                ", authorizationServerUrl=" + authorizationServerUrl + 
                ", clientId=" + clientId + 
                ", clientSecret=" + clientSecret + 
                ", note=" + note + 
                ", invalid=" + invalid + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
