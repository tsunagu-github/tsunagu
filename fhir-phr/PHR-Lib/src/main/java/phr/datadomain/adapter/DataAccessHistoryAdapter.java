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
package phr.datadomain.adapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.DataAccessHistoryEntity;
import phr.datadomain.entity.JLAC10AnalyteCodeEntity;
import phr.datadomain.entity.MedicalOrganizationSystemEntity;

/**
 * システム連携実行履歴情報のデータオブジェクトです。
 */
@Repository
public class DataAccessHistoryAdapter extends DataAccessHistoryAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(DataAccessHistoryAdapter.class);
    private static Logger logger = Logger.getLogger(DataAccessHistoryAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DataAccessHistoryAdapter() {
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * エラー情報を出力
     * @param historyId
     * @param id
     * @param javaSqlDate
     * @throws Throwable
     */
    public void insertError(String historyId, String id, java.sql.Date javaSqlDate) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject();
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        preparedStatement.setString(1, historyId);
        preparedStatement.setString(2, id);
        preparedStatement.setDate(3, javaSqlDate);
        preparedStatement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setString(5, "9");

        preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
    }

    /**
     * 実行履歴IDが最大のものを取得
     * @param id
     * @param javaSqlDate
     * @throws Throwable
     */
    public String getHistoryId() throws Throwable 
    {
        logger.debug("Start");
        String historyId = null;
        DataAccessObject dao = new DataAccessObject();
        String sql = getSelectedSql();
        sql += " order by ProcessHistoryId desc limit 1";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        DataAccessHistoryEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = DataAccessHistoryEntity.setData(dataTable);
        }

        if (entity != null) {
            historyId = String.format("%09d", entity.getProcessHistoryId() + 1);
        } else {
            historyId = "000000000";
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return historyId;
    }
}
