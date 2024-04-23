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
package phr.datadomain.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.base.*;

/**
 * 研究項目情報のデータオブジェクトです。
 */
public class StudyItemInformationEntity extends StudyItemInformationEntityBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StudyItemInformationEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public StudyItemInformationEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */

    /* 研究名称 */
    protected String studyName = null;
    /* チェックリスト */
    protected String checkList = null;
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
        entity.setStudyName(getString(dataRow, "StudyName"));
        entity.setSubject(getString(dataRow, "Subject"));
        entity.setConsentType(getString(dataRow, "ConsentType"));
        entity.setUrl(getString(dataRow, "Url"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("研究ID      ：" + entity.getStudyId());
            logger.debug("項目ID      ：" + entity.getSubjectId());
            logger.debug("研究名称           ：" + entity.getStudyName());
            logger.debug("項目説明    ：" + entity.getSubject());
            logger.debug("同意種別    ：" + entity.getConsentType());
            logger.debug("URL         ：" + entity.getUrl());
            logger.debug("作成日時    ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyItemInformationEntity
     */
    public static StudyItemInformationEntity setDat(ResultSet dataRow, boolean isChildTable) throws SQLException {
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
    /**
     * 研究名称を取得する
     *
     * @return
     */
    public String getStudyName() {
        return this.studyName;
    }
    /**
     * 研究名称を設定する
     *
     * @param value
     */
    public void setStudyName(String value) {
        this.studyName = value;
    }
    
    public String getCheckList() {
        return checkList;
    }

    public void setCheckList(String checkList) {
        this.checkList = checkList;
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
                ", studyName=" + studyName + 
                ", subject=" + subject + 
                ", consentType=" + consentType + 
                ", url=" + url + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
