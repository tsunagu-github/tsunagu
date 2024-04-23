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
package phr.datadomain.adapter.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException; 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.entity.Seq_ObservationImportHistoryIdEntity;

/**
 * 検査結果取得履歴 IDのデータオブジェクトです。
 */
public abstract class Seq_ObservationImportHistoryIdAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(Seq_ObservationImportHistoryIdAdapterBase.class);
    private static Logger logger = Logger.getLogger(Seq_ObservationImportHistoryIdAdapterBase.class);

    /**
     * データベーステンプレート
     */
    @Autowired
    @Qualifier("jdbcTemplate")
    protected static JdbcTemplate jdbcTemplate;
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public Seq_ObservationImportHistoryIdAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * データベースコネクション
     */
    protected static Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public Seq_ObservationImportHistoryIdAdapterBase(Connection conn)
    {
        connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    Seq_ObservationImportHistoryId.ImportHistoryId As ImportHistoryId  \r\n");
        sql.append("from Seq_ObservationImportHistoryId \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Seq_ObservationImportHistoryId \r\n");
        sql.append("(ImportHistoryId) \r\n");
        sql.append("values (?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update Seq_ObservationImportHistoryId set \r\n");
        sql.append(" \r\n");
        sql.append("where ImportHistoryId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  Seq_ObservationImportHistoryId \r\n");
        sql.append("where ImportHistoryId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public static int insert(Seq_ObservationImportHistoryIdEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        String seqSql = "select nextval('○')";
        Long seqId = jdbcTemplate.queryForObject(seqSql, Long.class);
        if (seqId != null) {
            entity.setImportHistoryId(seqId);
        } else {
            new NullPointerException("検査結果取得履歴 IDの採番に失敗しました。【○】");
        }
        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getImportHistoryId()
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
    public int update(Seq_ObservationImportHistoryIdEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getImportHistoryId()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて検査結果取得履歴 IDの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(Seq_ObservationImportHistoryIdEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getImportHistoryId(),
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて検査結果取得履歴 IDを検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public Seq_ObservationImportHistoryIdEntity findByPrimaryKey(long key1) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where Seq_ObservationImportHistoryId.ImportHistoryId = ?";

        Seq_ObservationImportHistoryIdEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1 }, new RowMapper<Seq_ObservationImportHistoryIdEntity>() {

                public Seq_ObservationImportHistoryIdEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return Seq_ObservationImportHistoryIdEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
