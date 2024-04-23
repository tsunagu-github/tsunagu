/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果取得履歴情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/04/26
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.ObservationImportHistoryEntity;

/**
 * 検査結果取得履歴情報のデータオブジェクトです。
 */
public abstract class ObservationImportHistoryEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationImportHistoryEntityBase.class);
    private static Logger logger = Logger.getLogger(ObservationImportHistoryEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationImportHistoryEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査結果取得履歴 ID */
    protected String importHistoryId = null;
    /* 実行日時 */
    protected Timestamp executionDate = null;
    /* 処理ステータス */
    protected String status = null;
    /* 登録日時 */
    protected Timestamp createDateTime = null;
    /* 更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationImportHistoryEntity
     */
    public static ObservationImportHistoryEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationImportHistoryEntity
     */
    public static ObservationImportHistoryEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.debug("Start");
        ObservationImportHistoryEntity entity = new ObservationImportHistoryEntity();
        entity.setImportHistoryId(getString(dataRow, "ImportHistoryId"));
        entity.setExecutionDate(getDateTime(dataRow, "ExecutionDate"));
        entity.setStatus(getString(dataRow, "Status"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果取得履歴 ID：" + entity.getImportHistoryId());
            logger.debug("実行日時           ：" + entity.getExecutionDate());
            logger.debug("処理ステータス     ：" + entity.getStatus());
            logger.debug("登録日時           ：" + entity.getCreateDateTime());
            logger.debug("更新日時           ：" + entity.getUpdateDateTime());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 検査結果取得履歴 IDを取得する
     *
     * @return
     */
    public String getImportHistoryId() {
        return this.importHistoryId;
    }
    /**
     * 検査結果取得履歴 IDを設定する
     *
     * @param value
     */
    public void setImportHistoryId(String value) {
        this.importHistoryId = value;
    }

    /**
     * 実行日時を取得する
     *
     * @return
     */
    public Timestamp getExecutionDate() {
        return this.executionDate;
    }
    /**
     * 実行日時を設定する
     *
     * @param value
     */
    public void setExecutionDate(Timestamp value) {
        this.executionDate = value;
    }

    /**
     * 処理ステータスを取得する
     *
     * @return
     */
    public String getStatus() {
        return this.status;
    }
    /**
     * 処理ステータスを設定する
     *
     * @param value
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * 登録日時を取得する
     *
     * @return
     */
    public Timestamp getCreateDateTime() {
        return this.createDateTime;
    }
    /**
     * 登録日時を設定する
     *
     * @param value
     */
    public void setCreateDateTime(Timestamp value) {
        this.createDateTime = value;
    }

    /**
     * 更新日時を取得する
     *
     * @return
     */
    public Timestamp getUpdateDateTime() {
        return this.updateDateTime;
    }
    /**
     * 更新日時を設定する
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
                "importHistoryId=" + importHistoryId + 
                ", executionDate=" + executionDate + 
                ", status=" + status + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
