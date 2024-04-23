/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：連携システムデータ取得タイミング情報のデータオブジェクト
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

import phr.datadomain.entity.DataAccessTimingEntity;
import phr.execute.DataAccessExecute;

/**
 * 連携システムデータ取得タイミング情報のデータオブジェクトです。
 */
public abstract class DataAccessTimingAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(DataAccessTimingAdapterBase.class);
    private static Logger logger = Logger.getLogger(DataAccessTimingAdapterBase.class);

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
    public DataAccessTimingAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    DataAccessTiming.DataCooperationSystemId As DataCooperationSystemId  \r\n");
        sql.append("    , DataAccessTiming.Seq As Seq  \r\n");
        sql.append("    , DataAccessTiming.DataAccessTime As DataAccessTime  \r\n");
        sql.append("    , DataAccessTiming.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , DataAccessTiming.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from DataAccessTiming \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into DataAccessTiming \r\n");
        sql.append("(DataCooperationSystemId, Seq, DataAccessTime, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update DataAccessTiming set \r\n");
        sql.append("DataAccessTime = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where DataCooperationSystemId = ? AND Seq = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  DataAccessTiming \r\n");
        sql.append("where DataCooperationSystemId = ? AND Seq = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(DataAccessTimingEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getDataCooperationSystemId()
                , entity.getSeq()
                , entity.getDataAccessTime()
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
    public int update(DataAccessTimingEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getDataAccessTime()
                , entity.getDataCooperationSystemId()
                , entity.getSeq()
                , entity.getUpdateDateTime()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて連携システムデータ取得タイミング情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(DataAccessTimingEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getDataCooperationSystemId(),
                entity.getSeq(),
                entity.getUpdateDateTime()
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて連携システムデータ取得タイミング情報を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public DataAccessTimingEntity findByPrimaryKey(int key1, int key2) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where DataAccessTiming.DataCooperationSystemId = ?";
        sql += "   and DataAccessTiming.Seq = ?";

        DataAccessTimingEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1, key2 }, new RowMapper<DataAccessTimingEntity>() {

                public DataAccessTimingEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return DataAccessTimingEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
