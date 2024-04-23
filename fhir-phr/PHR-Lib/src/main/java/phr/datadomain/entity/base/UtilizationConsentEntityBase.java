/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：活用同意一覧情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/04/13
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.UtilizationConsentEntity;

/**
 * 活用同意一覧情報のデータオブジェクトです。
 */
public abstract class UtilizationConsentEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UtilizationConsentEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public UtilizationConsentEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 活用同意一覧ID */
    protected String utilizationConsentId = null;
    /* PHR_ID */
    protected String pHR_ID = null;
    /* 研究ID */
    protected String studyId = null;
    /* 項目ID */
    protected String subjectId = null;
    /* 同意種別 */
    protected String consentType = null;
    /* 回答ステータス */
    protected String responseStatus = null;
    /* 新着フラグ */
    protected boolean newArrivalFlg = false;
    /* 無効フラグ */
    protected String invalid = null;
    /* 通知日 */
    protected Timestamp notificationDate = null;
    /* 回答更新日 */
    protected Timestamp responseDate = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  UtilizationConsentEntity
     */
    public static UtilizationConsentEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  UtilizationConsentEntity
     */
    public static UtilizationConsentEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        UtilizationConsentEntity entity = new UtilizationConsentEntity();
        entity.setUtilizationConsentId(getString(dataRow, "UtilizationConsentId"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setStudyId(getString(dataRow, "StudyId"));
        entity.setSubjectId(getString(dataRow, "SubjectId"));
        entity.setConsentType(getString(dataRow, "ConsentType"));
        entity.setResponseStatus(getString(dataRow, "ResponseStatus"));
        entity.setNewArrivalFlg(getBoolean(dataRow, "NewArrivalFlg"));
        entity.setInvalid(getString(dataRow, "Invalid"));
        entity.setNotificationDate(getDateTime(dataRow, "NotificationDate"));
        entity.setResponseDate(getDateTime(dataRow, "ResponseDate"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("活用同意一覧ID：" + entity.getUtilizationConsentId());
            logger.debug("PHR_ID        ：" + entity.getPHR_ID());
            logger.debug("研究ID        ：" + entity.getStudyId());
            logger.debug("項目ID        ：" + entity.getSubjectId());
            logger.debug("同意種別      ：" + entity.getConsentType());
            logger.debug("回答ステータス：" + entity.getResponseStatus());
            logger.debug("新着フラグ    ：" + entity.isNewArrivalFlg());
            logger.debug("無効フラグ    ：" + entity.getInvalid());
            logger.debug("通知日        ：" + entity.getNotificationDate());
            logger.debug("回答更新日      ：" + entity.getResponseDate());
            logger.debug("作成日時      ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時  ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 活用同意一覧IDを取得する
     *
     * @return
     */
    public String getUtilizationConsentId() {
        return this.utilizationConsentId;
    }
    /**
     * 活用同意一覧IDを設定する
     *
     * @param value
     */
    public void setUtilizationConsentId(String value) {
        this.utilizationConsentId = value;
    }

    /**
     * PHR_IDを取得する
     *
     * @return
     */
    public String getPHR_ID() {
        return this.pHR_ID;
    }
    /**
     * PHR_IDを設定する
     *
     * @param value
     */
    public void setPHR_ID(String value) {
        this.pHR_ID = value;
    }

    /**
     * 研究IDを取得する
     *
     * @return
     */
    public String getStudyId() {
        return this.studyId;
    }
    /**
     * 研究IDを設定する
     *
     * @param value
     */
    public void setStudyId(String value) {
        this.studyId = value;
    }

    /**
     * 項目IDを取得する
     *
     * @return
     */
    public String getSubjectId() {
        return this.subjectId;
    }
    /**
     * 項目IDを設定する
     *
     * @param value
     */
    public void setSubjectId(String value) {
        this.subjectId = value;
    }

    /**
     * 同意種別を取得する
     *
     * @return
     */
    public String getConsentType() {
        return this.consentType;
    }
    /**
     * 同意種別を設定する
     *
     * @param value
     */
    public void setConsentType(String value) {
        this.consentType = value;
    }

    /**
     * 回答ステータスを取得する
     *
     * @return
     */
    public String getResponseStatus() {
        return this.responseStatus;
    }
    /**
     * 回答ステータスを設定する
     *
     * @param value
     */
    public void setResponseStatus(String value) {
        this.responseStatus = value;
    }

    /**
     * 新着フラグを取得する
     *
     * @return
     */
    public boolean isNewArrivalFlg() {
        return this.newArrivalFlg;
    }
    /**
     * 新着フラグを設定する
     *
     * @param value
     */
    public void setNewArrivalFlg(boolean value) {
        this.newArrivalFlg = value;
    }

    /**
     * 無効フラグを取得する
     *
     * @return
     */
    public String getInvalid() {
        return this.invalid;
    }
    /**
     * 無効フラグを設定する
     *
     * @param value
     */
    public void setInvalid(String value) {
        this.invalid = value;
    }

    /**
     * 通知日を取得する
     *
     * @return
     */
    public Timestamp getNotificationDate() {
        return this.notificationDate;
    }
    /**
     * 通知日を設定する
     *
     * @param value
     */
    public void setNotificationDate(Timestamp value) {
        this.notificationDate = value;
    }

    /**
     * 通知日を取得する
     *
     * @return
     */
    public Timestamp getResponseDate() {
        return this.responseDate;
    }
    /**
     * 通知日を設定する
     *
     * @param value
     */
    public void setResponseDate(Timestamp value) {
        this.responseDate = value;
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
                "utilizationConsentId=" + utilizationConsentId + 
                ", pHR_ID=" + pHR_ID + 
                ", studyId=" + studyId + 
                ", subjectId=" + subjectId + 
                ", consentType=" + consentType + 
                ", responseStatus=" + responseStatus + 
                ", newArrivalFlg=" + newArrivalFlg + 
                ", invalid=" + invalid + 
                ", notificationDate=" + notificationDate + 
                ", responseDate=" + responseDate + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
