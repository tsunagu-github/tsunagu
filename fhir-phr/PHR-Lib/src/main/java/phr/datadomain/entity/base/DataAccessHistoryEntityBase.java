/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：システム連携実行履歴情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.DataAccessHistoryEntity;

/**
 * システム連携実行履歴情報のデータオブジェクトです。
 */
public abstract class DataAccessHistoryEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(DataAccessHistoryEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DataAccessHistoryEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 実行履歴Id */
    protected int processHistoryId = 0;
    /* データ連携システムID */
    protected int dataCooperationSystemId = 0;
    /* 開始日時 */
    protected Timestamp startDateTime = null;
    /* 終了日時 */
    protected Timestamp endDateTime = null;
    /* ステータス */
    protected String status = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DataAccessHistoryEntity
     */
    public static DataAccessHistoryEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DataAccessHistoryEntity
     */
    public static DataAccessHistoryEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        DataAccessHistoryEntity entity = new DataAccessHistoryEntity();
        entity.setProcessHistoryId(getInt(dataRow, "ProcessHistoryId"));
        entity.setDataCooperationSystemId(getInt(dataRow, "DataCooperationSystemId"));
        entity.setStartDateTime(getDateTime(dataRow, "StartDateTime"));
        entity.setEndDateTime(getDateTime(dataRow, "EndDateTime"));
        entity.setStatus(getString(dataRow, "Status"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("実行履歴Id          ：" + entity.getProcessHistoryId());
            logger.debug("データ連携システムID：" + entity.getDataCooperationSystemId());
            logger.debug("開始日時            ：" + entity.getStartDateTime());
            logger.debug("終了日時            ：" + entity.getEndDateTime());
            logger.debug("ステータス          ：" + entity.getStatus());
            logger.debug("作成日時            ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時        ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 実行履歴Idを取得する
     *
     * @return
     */
    public int getProcessHistoryId() {
        return this.processHistoryId;
    }
    /**
     * 実行履歴Idを設定する
     *
     * @param value
     */
    public void setProcessHistoryId(int value) {
        this.processHistoryId = value;
    }

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
     * 開始日時を取得する
     *
     * @return
     */
    public Timestamp getStartDateTime() {
        return this.startDateTime;
    }
    /**
     * 開始日時を設定する
     *
     * @param value
     */
    public void setStartDateTime(Timestamp value) {
        this.startDateTime = value;
    }

    /**
     * 終了日時を取得する
     *
     * @return
     */
    public Timestamp getEndDateTime() {
        return this.endDateTime;
    }
    /**
     * 終了日時を設定する
     *
     * @param value
     */
    public void setEndDateTime(Timestamp value) {
        this.endDateTime = value;
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
                "processHistoryId=" + processHistoryId + 
                ", dataCooperationSystemId=" + dataCooperationSystemId + 
                ", startDateTime=" + startDateTime + 
                ", endDateTime=" + endDateTime + 
                ", status=" + status + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
