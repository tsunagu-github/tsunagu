/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：JLAC11測定物情報のデータオブジェクト
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

import phr.datadomain.entity.JLAC11AnalyteCodeEntity;

/**
 * JLAC11測定物情報のデータオブジェクトです。
 */
public abstract class JLAC11AnalyteCodeAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(JLAC11AnalyteCodeAdapterBase.class);
    private static Logger logger = Logger.getLogger(JLAC11AnalyteCodeAdapterBase.class);

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
    public JLAC11AnalyteCodeAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    JLAC11AnalyteCode.AnalyteCode As AnalyteCode  \r\n");
        sql.append("    , JLAC11AnalyteCode.AnalyteType As AnalyteType  \r\n");
        sql.append("    , JLAC11AnalyteCode.AnalyteName As AnalyteName  \r\n");
        sql.append("    , JLAC11AnalyteCode.JLAC10AnalyteCode As JLAC10AnalyteCode  \r\n");
        sql.append("    , JLAC11AnalyteCode.Note As Note  \r\n");
        sql.append("    , JLAC11AnalyteCode.HospitalLabResultTargetFlg As HospitalLabResultTargetFlg  \r\n");
        sql.append("    , JLAC11AnalyteCode.GraphTargetFllg As GraphTargetFllg  \r\n");
        sql.append("    , JLAC11AnalyteCode.JLAC11Version As JLAC11Version  \r\n");
        sql.append("    , JLAC11AnalyteCode.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , JLAC11AnalyteCode.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from JLAC11AnalyteCode \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into JLAC11AnalyteCode \r\n");
        sql.append("(AnalyteCode, AnalyteType, AnalyteName, JLAC10AnalyteCode, Note, HospitalLabResultTargetFlg, GraphTargetFllg, JLAC11Version, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update JLAC11AnalyteCode set \r\n");
        sql.append("AnalyteCode = ?, AnalyteName = ?, JLAC10AnalyteCode = ?, Note = ?, HospitalLabResultTargetFlg = ?, GraphTargetFllg = ?, JLAC11Version = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where AnalyteType = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  JLAC11AnalyteCode \r\n");
        sql.append("where AnalyteType = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(JLAC11AnalyteCodeEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getAnalyteCode()
                , entity.getAnalyteType()
                , entity.getAnalyteName()
                , entity.getJLAC10AnalyteCode()
                , entity.getNote()
                , entity.isHospitalLabResultTargetFlg()
                , entity.isGraphTargetFllg()
                , entity.getJLAC11Version()
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
    public int update(JLAC11AnalyteCodeEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getAnalyteCode()
                , entity.getAnalyteName()
                , entity.getJLAC10AnalyteCode()
                , entity.getNote()
                , entity.isHospitalLabResultTargetFlg()
                , entity.isGraphTargetFllg()
                , entity.getJLAC11Version()
                , entity.getAnalyteType()
                , entity.getUpdateDateTime()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてJLAC11測定物情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(JLAC11AnalyteCodeEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getAnalyteType(),
                entity.getUpdateDateTime()
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにてJLAC11測定物情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public JLAC11AnalyteCodeEntity findByPrimaryKey(String key1) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where JLAC11AnalyteCode.AnalyteType = ?";

        JLAC11AnalyteCodeEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1 }, new RowMapper<JLAC11AnalyteCodeEntity>() {

                public JLAC11AnalyteCodeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return JLAC11AnalyteCodeEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
