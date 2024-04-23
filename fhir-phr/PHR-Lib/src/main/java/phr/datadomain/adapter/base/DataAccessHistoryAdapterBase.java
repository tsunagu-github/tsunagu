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
package phr.datadomain.adapter.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.entity.DataAccessHistoryEntity;

/**
 * システム連携実行履歴情報のデータオブジェクトです。
 */
public abstract class DataAccessHistoryAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(DataAccessHistoryAdapterBase.class);
    private static Logger logger = Logger.getLogger(DataAccessHistoryAdapterBase.class);

    /**
     * データベーステンプレート
     */
    @Autowired
    @Qualifier("jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DataAccessHistoryAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    DataAccessHistory.ProcessHistoryId As ProcessHistoryId  \r\n");
        sql.append("    , DataAccessHistory.DataCooperationSystemId As DataCooperationSystemId  \r\n");
        sql.append("    , DataAccessHistory.StartDateTime As StartDateTime  \r\n");
        sql.append("    , DataAccessHistory.EndDateTime As EndDateTime  \r\n");
        sql.append("    , DataAccessHistory.Status As Status  \r\n");
        sql.append("    , DataAccessHistory.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , DataAccessHistory.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from DataAccessHistory \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into DataAccessHistory \r\n");
        sql.append("(ProcessHistoryId, DataCooperationSystemId, StartDateTime, EndDateTime, Status, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update DataAccessHistory set \r\n");
        sql.append("DataCooperationSystemId = ?, StartDateTime = ?, EndDateTime = ?, Status = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where ProcessHistoryId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  DataAccessHistory \r\n");
        sql.append("where ProcessHistoryId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(DataAccessHistoryEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getProcessHistoryId()
                , entity.getDataCooperationSystemId()
                , entity.getStartDateTime()
                , entity.getEndDateTime()
                , entity.getStatus()
            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(DataAccessHistoryEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getDataCooperationSystemId()
                , entity.getStartDateTime()
                , entity.getEndDateTime()
                , entity.getStatus()
                , entity.getProcessHistoryId()
                , entity.getUpdateDateTime()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてシステム連携実行履歴情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(DataAccessHistoryEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getProcessHistoryId(),
                entity.getUpdateDateTime()
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにてシステム連携実行履歴情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public DataAccessHistoryEntity findByPrimaryKey(int key1) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where DataAccessHistory.ProcessHistoryId = ?";

        DataAccessHistoryEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1 }, new RowMapper<DataAccessHistoryEntity>() {

                public DataAccessHistoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return DataAccessHistoryEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
