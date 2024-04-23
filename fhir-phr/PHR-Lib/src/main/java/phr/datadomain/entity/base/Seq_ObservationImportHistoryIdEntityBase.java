/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果取得履歴 IDのデータオブジェクト
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
import phr.datadomain.entity.Seq_ObservationImportHistoryIdEntity;

/**
 * 検査結果取得履歴 IDのデータオブジェクトです。
 */
public abstract class Seq_ObservationImportHistoryIdEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(Seq_ObservationImportHistoryIdEntityBase.class);
    private static Logger logger = Logger.getLogger(Seq_ObservationImportHistoryIdEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public Seq_ObservationImportHistoryIdEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査結果取得履歴 ID */
    protected long importHistoryId = 0;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  Seq_ObservationImportHistoryIdEntity
     */
    public static Seq_ObservationImportHistoryIdEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  Seq_ObservationImportHistoryIdEntity
     */
    public static Seq_ObservationImportHistoryIdEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.debug("Start");
        Seq_ObservationImportHistoryIdEntity entity = new Seq_ObservationImportHistoryIdEntity();
        entity.setImportHistoryId(getLong(dataRow, "ImportHistoryId"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果取得履歴 ID：" + entity.getImportHistoryId());
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
    public long getImportHistoryId() {
        return this.importHistoryId;
    }
    /**
     * 検査結果取得履歴 IDを設定する
     *
     * @param value
     */
    public void setImportHistoryId(long value) {
        this.importHistoryId = value;
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
                " }\r\n";
    }
}
