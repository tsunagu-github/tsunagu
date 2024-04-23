/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：JLAC10分析物情報のデータオブジェクト
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

import phr.datadomain.entity.JLAC10AnalyteCodeEntity;

/**
 * JLAC10分析物情報のデータオブジェクトです。
 */
public abstract class JLAC10AnalyteCodeAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(JLAC10AnalyteCodeAdapterBase.class);
    private static Logger logger = Logger.getLogger(JLAC10AnalyteCodeAdapterBase.class);

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
    public JLAC10AnalyteCodeAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    JLAC10AnalyteCode.AnalyteCode As AnalyteCode  \r\n");
        sql.append("    , JLAC10AnalyteCode.AnalyteType As AnalyteType  \r\n");
        sql.append("    , JLAC10AnalyteCode.AnalyteName1 As AnalyteName1  \r\n");
        sql.append("    , JLAC10AnalyteCode.AnalyteName2 As AnalyteName2  \r\n");
        sql.append("    , JLAC10AnalyteCode.Note As Note  \r\n");
        sql.append("    , JLAC10AnalyteCode.HospitalLabResultTargetFlg As HospitalLabResultTargetFlg  \r\n");
        sql.append("    , JLAC10AnalyteCode.GraphTargetFllg As GraphTargetFllg  \r\n");
        sql.append("    , JLAC10AnalyteCode.JLAC10Version As JLAC10Version  \r\n");
        sql.append("    , JLAC10AnalyteCode.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , JLAC10AnalyteCode.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , JLAC10AnalyteCode.SortNo As SortNo  \r\n");
        sql.append("from JLAC10AnalyteCode \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into JLAC10AnalyteCode \r\n");
        sql.append("(AnalyteCode, AnalyteType, AnalyteName1, AnalyteName2, Note, HospitalLabResultTargetFlg, GraphTargetFllg, JLAC10Version, CreateDateTime, UpdateDateTime, SortNo) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update JLAC10AnalyteCode set \r\n");
        sql.append("AnalyteCode = ?, AnalyteName1 = ?, AnalyteName2 = ?, Note = ?, HospitalLabResultTargetFlg = ?, GraphTargetFllg = ?, JLAC10Version = ?, UpdateDateTime = CURRENT_TIMESTAMP, SortNo = ? \r\n");
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
        sql.append("delete from  JLAC10AnalyteCode \r\n");
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
    public int insert(JLAC10AnalyteCodeEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getAnalyteCode()
                , entity.getAnalyteType()
                , entity.getAnalyteName1()
                , entity.getAnalyteName2()
                , entity.getNote()
                , entity.isHospitalLabResultTargetFlg()
                , entity.isGraphTargetFllg()
                , entity.getJLAC10Version()
                , entity.getJLAC10Version()
                , entity.getSortNo()
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
    public int update(JLAC10AnalyteCodeEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getAnalyteCode()
                , entity.getAnalyteName1()
                , entity.getAnalyteName2()
                , entity.getNote()
                , entity.isHospitalLabResultTargetFlg()
                , entity.isGraphTargetFllg()
                , entity.getJLAC10Version()
                , entity.getAnalyteType()
                , entity.getUpdateDateTime()
                , entity.getSortNo()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてJLAC10分析物情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(JLAC10AnalyteCodeEntity entity) throws Throwable {
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
     * 主キーにてJLAC10分析物情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public JLAC10AnalyteCodeEntity findByPrimaryKey(String key1) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where JLAC10AnalyteCode.AnalyteType = ?";

        JLAC10AnalyteCodeEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1 }, new RowMapper<JLAC10AnalyteCodeEntity>() {

                public JLAC10AnalyteCodeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return JLAC10AnalyteCodeEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
