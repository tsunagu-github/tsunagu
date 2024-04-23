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
import java.sql.Timestamp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.adapter.Seq_ObservationImportHistoryIdAdapter;
import phr.datadomain.entity.Seq_UtilizationConsentIdEntity;

/**
 * 活用同意一覧 IDのデータオブジェクトです。
 */
public abstract class Seq_UtilizationConsentIdAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(Seq_UtilizationConsentIdAdapterBase.class);

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
    public Seq_UtilizationConsentIdAdapterBase() {
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
    public Seq_UtilizationConsentIdAdapterBase(Connection conn)
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
        sql.append("    Seq_UtilizationConsentId.UtilizationConsentId As UtilizationConsentId  \r\n");
        sql.append("from Seq_UtilizationConsentId \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Seq_UtilizationConsentId \r\n");
        sql.append("(UtilizationConsentId) \r\n");
        sql.append("values (?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update Seq_UtilizationConsentId set \r\n");
        sql.append(" \r\n");
        sql.append("where UtilizationConsentId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  Seq_UtilizationConsentId \r\n");
        sql.append("where UtilizationConsentId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(Seq_UtilizationConsentIdEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        String seqSql = "select nextval('○')";
        Long seqId = jdbcTemplate.queryForObject(seqSql, Long.class);
        if (seqId != null) {
            entity.setUtilizationConsentId(seqId);
        } else {
            new NullPointerException("検査結果取得履歴 IDの採番に失敗しました。【○】");
        }
        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getUtilizationConsentId()
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
    public int update(Seq_UtilizationConsentIdEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getUtilizationConsentId()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて活用同意一覧 IDの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(Seq_UtilizationConsentIdEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getUtilizationConsentId(),
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて活用同意一覧 IDを検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public Seq_UtilizationConsentIdEntity findByPrimaryKey(long key1) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where Seq_UtilizationConsentId.UtilizationConsentId = ?";

        Seq_UtilizationConsentIdEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1 }, new RowMapper<Seq_UtilizationConsentIdEntity>() {

                public Seq_UtilizationConsentIdEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return Seq_UtilizationConsentIdEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
