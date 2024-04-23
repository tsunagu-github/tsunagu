/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：研究詳細情報のデータオブジェクト
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
import phr.datadomain.entity.StudyDetailedInformationEntity;

/**
 * 研究詳細情報のデータオブジェクトです。
 */
public abstract class StudyDetailedInformationEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StudyDetailedInformationEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public StudyDetailedInformationEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* SeqID */
    protected String seqId = null;
    /* 研究ID */
    protected String studyId = null;
    /* 項目ID */
    protected String subjectId = null;
    /* 確認項目 */
    protected String checkList = null;
    /* ステータス */
    protected String status = null;
    /* ソート番号 */
    protected String sortNo = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyDetailedInformationEntity
     */
    public static StudyDetailedInformationEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyDetailedInformationEntity
     */
    public static StudyDetailedInformationEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        StudyDetailedInformationEntity entity = new StudyDetailedInformationEntity();
        entity.setSeqId(getString(dataRow, "SeqId"));
        entity.setStudyId(getString(dataRow, "StudyId"));
        entity.setSubjectId(getString(dataRow, "SubjectId"));
        entity.setCheckList(getString(dataRow, "CheckList"));
        entity.setStatus(getString(dataRow, "Status"));
        entity.setSortNo(getString(dataRow, "SortNo"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("SeqID       ：" + entity.getSeqId());
            logger.debug("研究ID      ：" + entity.getStudyId());
            logger.debug("項目ID      ：" + entity.getSubjectId());
            logger.debug("確認項目    ：" + entity.getCheckList());
            logger.debug("ステータス  ：" + entity.getStatus());
            logger.debug("ソート番号  ：" + entity.getSortNo());
            logger.debug("作成日時    ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * SeqIDを取得する
     *
     * @return
     */
    public String getSeqId() {
        return this.seqId;
    }
    /**
     * SeqIDを設定する
     *
     * @param value
     */
    public void setSeqId(String value) {
        this.seqId = value;
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
     * 確認項目を取得する
     *
     * @return
     */
    public String getCheckList() {
        return this.checkList;
    }
    /**
     * 確認項目を設定する
     *
     * @param value
     */
    public void setCheckList(String value) {
        this.checkList = value;
    }

    /**
     * ステータスを取得する
     *
     * @return
     */
    public String getStatus() {
        return this.status;
    }
    /**
     * ステータスを設定する
     *
     * @param value
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * ソート番号を取得する
     *
     * @return
     */
    public String getSortNo() {
        return this.sortNo;
    }
    /**
     * ソート番号を設定する
     *
     * @param value
     */
    public void setSortNo(String value) {
        this.sortNo = value;
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
                "seqId=" + seqId + 
                ", studyId=" + studyId + 
                ", subjectId=" + subjectId + 
                ", checkList=" + checkList + 
                ", status=" + status + 
                ", sortNo=" + sortNo + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
