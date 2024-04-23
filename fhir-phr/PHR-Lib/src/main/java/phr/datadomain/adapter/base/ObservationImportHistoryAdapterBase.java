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
package phr.datadomain.adapter.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.ObservationImportHistoryEntity;

/**
 * 検査結果取得履歴情報のデータオブジェクトです。
 */
public abstract class ObservationImportHistoryAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationImportHistoryAdapterBase.class);
    private static Logger logger = Logger.getLogger(ObservationImportHistoryAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;
    
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
    public ObservationImportHistoryAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * コンストラクタ
     */
    public ObservationImportHistoryAdapterBase(Connection conn)
    {
    	connection = conn;
    }

    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationImportHistory.ImportHistoryId As ImportHistoryId  \r\n");
        sql.append("    , ObservationImportHistory.ExecutionDate As ExecutionDate  \r\n");
        sql.append("    , ObservationImportHistory.Status As Status  \r\n");
        sql.append("    , ObservationImportHistory.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , ObservationImportHistory.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from ObservationImportHistory \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationImportHistory \r\n");
        sql.append("(ImportHistoryId, ExecutionDate, Status, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ObservationImportHistory set \r\n");
        sql.append("ExecutionDate = ?, Status = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where ImportHistoryId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ObservationImportHistory \r\n");
        sql.append("where ImportHistoryId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationImportHistoryEntity entity) throws Throwable {
    	logger.debug("Start");

        DataAccessObject dao = new DataAccessObject();
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

	        if (entity.getImportHistoryId() == null) {
	            preparedStatement.setNull(1, Types.VARCHAR );
	        } else {
	            preparedStatement.setString(1, entity.getImportHistoryId());
	        }
            if (entity.getExecutionDate() == null) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getExecutionDate().toString());
            }
            if (entity.getStatus() == null) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getStatus());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();

        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(ObservationImportHistoryEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getExecutionDate()
                , entity.getStatus()
                , entity.getImportHistoryId()
                , entity.getUpdateDateTime()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて検査結果取得履歴情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationImportHistoryEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getImportHistoryId(),
                entity.getUpdateDateTime()
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて検査結果取得履歴情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public ObservationImportHistoryEntity findByPrimaryKey(String key1) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where ObservationImportHistory.ImportHistoryId = ?";

        ObservationImportHistoryEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1 }, new RowMapper<ObservationImportHistoryEntity>() {

                public ObservationImportHistoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return ObservationImportHistoryEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
