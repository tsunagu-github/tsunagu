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
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 検査結果取得履歴情報のデータオブジェクトです。
 */
public class ObservationImportHistoryEntity extends ObservationImportHistoryEntityBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationImportHistoryEntity.class);
    private static Logger logger = Logger.getLogger(ObservationImportHistoryEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationImportHistoryEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* データ連携システムID */
    protected String dataCooperationSystemId = null;
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
        entity.setDataCooperationSystemId(getString(dataRow, "DataCooperationSystemId"));
        entity.setExecutionDate(getDateTime(dataRow, "ExecutionDate"));
        entity.setStatus(getString(dataRow, "Status"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果取得履歴 ID：" + entity.getImportHistoryId());
            logger.debug("データ連携システム ID：" + entity.getDataCooperationSystemId());
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
    public String getDataCooperationSystemId() {
        return this.dataCooperationSystemId;
    }
    /**
     * 検査結果取得履歴 IDを設定する
     *
     * @param value
     */
    public void setDataCooperationSystemId(String value) {
        this.dataCooperationSystemId = value;
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
                "dataCooperationSystemId=" + dataCooperationSystemId + 
                ", executionDate=" + executionDate + 
                ", status=" + status + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
