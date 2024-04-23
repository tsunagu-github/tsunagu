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
import phr.datadomain.entity.AnswerStatusEntity;

/**
 * 回答情報のデータオブジェクトです。
 */
public abstract class AnswerStatusEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
	private static final Log logger = LogFactory.getLog(AnswerStatusEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public AnswerStatusEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* PHR_ID */
    protected String pHR_ID = null;
    /* 研究ID */
    protected String studyId = null;
    /* 項目ID */
    protected String subjectId = null;
    /* 確認項目ID */
    protected String checkListId = null;
    /* ステータス */
    protected boolean status = false;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AnswerStatusEntity
     */
    public static AnswerStatusEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AnswerStatusEntity
     */
    public static AnswerStatusEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        AnswerStatusEntity entity = new AnswerStatusEntity();
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setStudyId(getString(dataRow, "StudyId"));
        entity.setSubjectId(getString(dataRow, "SubjectId"));
        entity.setCheckListId(getString(dataRow, "CheckListId"));
        entity.setStatus(getBoolean(dataRow, "Status"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("PHR_ID      ：" + entity.getPHR_ID());
            logger.debug("研究ID      ：" + entity.getStudyId());
            logger.debug("項目ID      ：" + entity.getSubjectId());
            logger.debug("確認項目ID  ：" + entity.getCheckListId());
            logger.debug("ステータス  ：" + entity.isStatus());
            logger.debug("作成日時    ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * 確認項目IDを取得する
     *
     * @return
     */
    public String getCheckListId() {
        return this.checkListId;
    }
    /**
     * 確認項目IDを設定する
     *
     * @param value
     */
    public void setCheckListId(String value) {
        this.checkListId = value;
    }

    /**
     * ステータスを取得する
     *
     * @return
     */
    public boolean isStatus() {
        return this.status;
    }
    /**
     * ステータスを設定する
     *
     * @param value
     */
    public void setStatus(boolean value) {
        this.status = value;
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
                "pHR_ID=" + pHR_ID + 
                ", studyId=" + studyId + 
                ", subjectId=" + subjectId + 
                ", checkListId=" + checkListId + 
                ", status=" + status + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
