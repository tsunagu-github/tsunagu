/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：研究情報のデータオブジェクト
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
import phr.datadomain.entity.StudyInformationEntity;

/**
 * 研究情報のデータオブジェクトです。
 */
public abstract class StudyInformationEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StudyInformationEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public StudyInformationEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 研究ID */
    protected String studyId = null;
    /* 研究名称 */
    protected String studyName = null;
    /* 説明 */
    protected String explanation = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyInformationEntity
     */
    public static StudyInformationEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  StudyInformationEntity
     */
    public static StudyInformationEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        StudyInformationEntity entity = new StudyInformationEntity();
        entity.setStudyId(getString(dataRow, "StudyId"));
        entity.setStudyName(getString(dataRow, "StudyName"));
        entity.setExplanation(getString(dataRow, "Explanation"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("研究ID      ：" + entity.getStudyId());
            logger.debug("研究名称    ：" + entity.getStudyName());
            logger.debug("説明        ：" + entity.getExplanation());
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

    /**
     * 説明を取得する
     *
     * @return
     */
    public String getExplanation() {
        return this.explanation;
    }
    /**
     * 説明を設定する
     *
     * @param value
     */
    public void setExplanation(String value) {
        this.explanation = value;
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
                ", studyName=" + studyName + 
                ", explanation=" + explanation + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
