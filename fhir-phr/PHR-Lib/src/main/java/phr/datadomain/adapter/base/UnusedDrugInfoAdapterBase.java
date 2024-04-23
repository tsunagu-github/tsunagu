/********************************************************************************
 * システム名      ：phr
 * コンポーネント名：残薬確認情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/28
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter.base;

import java.sql.ResultSet;
import java.sql.SQLException; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.entity.UnusedDrugInfoEntity;

/**
 * 残薬確認情報のデータオブジェクトです。
 */
public abstract class UnusedDrugInfoAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(UnusedDrugInfoAdapterBase.class);

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
    public UnusedDrugInfoAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    UnusedDrugInfo.DosageId As DosageId  \r\n");
        sql.append("    , UnusedDrugInfo.Seq As Seq  \r\n");
        sql.append("    , UnusedDrugInfo.UnusedDrugs As UnusedDrugs  \r\n");
        sql.append("    , UnusedDrugInfo.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("from UnusedDrugInfo \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into UnusedDrugInfo \r\n");
        sql.append("(DosageId, Seq, UnusedDrugs, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update UnusedDrugInfo set \r\n");
        sql.append("UnusedDrugs = ?, RecordCreatorType = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  UnusedDrugInfo \r\n");
        sql.append("where DosageId = ? AND Seq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(UnusedDrugInfoEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getDosageId()
                , entity.getSeq()
                , entity.getUnusedDrugs()
                , entity.getRecordCreatorType()
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
    public int update(UnusedDrugInfoEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getUnusedDrugs()
                , entity.getRecordCreatorType()
                , entity.getDosageId()
                , entity.getSeq()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて残薬確認情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(UnusedDrugInfoEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getDosageId(),
                entity.getSeq(),
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて残薬確認情報を検索します。
     * @param key1
     * @param key1
     * @return
     * @throws Throwable
     */
    public UnusedDrugInfoEntity findByPrimaryKey(String key1, int key2) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where UnusedDrugInfo.DosageId = ?";
        sql += "   and UnusedDrugInfo.Seq = ?";

        UnusedDrugInfoEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1, key2 }, new RowMapper<UnusedDrugInfoEntity>() {

                public UnusedDrugInfoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return UnusedDrugInfoEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
