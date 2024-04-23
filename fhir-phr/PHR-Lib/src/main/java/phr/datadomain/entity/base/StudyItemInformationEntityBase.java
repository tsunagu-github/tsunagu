/********************************************************************************
 * システム名      ：MInCS for ePRO
 * コンポーネント名：研究項目情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/06/06
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
import phr.datadomain.entity.StudyItemInformationEntity;

/**
 * 研究項目情報のデータオブジェクトです。
 */
public abstract class StudyItemInformationEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StudyItemInformationEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public StudyItemInformationEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 研究ID */
    protected String studyId = null;
    /* 項目ID */
    protected String subjectId = null;
    /* 項目説明 */
    protected String subject = null;
    /* 同意種別 */
    protected String consentType = null;
    /* URL */
    protected String url = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyItemInformationEntity
     */
    public static StudyItemInformationEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyItemInformationEntity
     */
    public static StudyItemInformationEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        StudyItemInformationEntity entity = new StudyItemInformationEntity();
        entity.setStudyId(getString(dataRow, "StudyId"));
        entity.setSubjectId(getString(dataRow, "SubjectId"));
        entity.setSubject(getString(dataRow, "Subject"));
        entity.setConsentType(getString(dataRow, "ConsentType"));
        entity.setUrl(getString(dataRow, "Url"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("研究ID      ：" + entity.getStudyId());
            logger.debug("項目ID      ：" + entity.getSubjectId());
            logger.debug("項目説明    ：" + entity.getSubject());
            logger.debug("同意種別    ：" + entity.getConsentType());
            logger.debug("URL         ：" + entity.getUrl());
            logger.debug("作成日時    ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * 項目説明を取得する
     *
     * @return
     */
    public String getSubject() {
        return this.subject;
    }
    /**
     * 項目説明を設定する
     *
     * @param value
     */
    public void setSubject(String value) {
        this.subject = value;
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
     * URLを取得する
     *
     * @return
     */
    public String getUrl() {
        return this.url;
    }
    /**
     * URLを設定する
     *
     * @param value
     */
    public void setUrl(String value) {
        this.url = value;
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
                "studyId=" + studyId + 
                ", subjectId=" + subjectId + 
                ", subject=" + subject + 
                ", consentType=" + consentType + 
                ", url=" + url + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
