/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：共用基準範囲のデータオブジェクト
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

import phr.datadomain.entity.CommonReferenceRangeEntity;

/**
 * 共用基準範囲のデータオブジェクトです。
 */
public abstract class CommonReferenceRangeAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(CommonReferenceRangeAdapterBase.class);
    private static Logger logger = Logger.getLogger(CommonReferenceRangeAdapterBase.class);

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
    public CommonReferenceRangeAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    CommonReferenceRange.JLAC10AnalyteCode As JLAC10AnalyteCode  \r\n");
        sql.append("    , CommonReferenceRange.JLAC11AnalyteCode As JLAC11AnalyteCode  \r\n");
        sql.append("    , CommonReferenceRange.ObservationDefinitionName As ObservationDefinitionName  \r\n");
        sql.append("    , CommonReferenceRange.ObservationDefinitionShortName As ObservationDefinitionShortName  \r\n");
        sql.append("    , CommonReferenceRange.Unit As Unit  \r\n");
        sql.append("    , CommonReferenceRange.UnitCode As UnitCode  \r\n");
        sql.append("    , CommonReferenceRange.ReferenceRangeType As ReferenceRangeType  \r\n");
        sql.append("    , CommonReferenceRange.CommonLowerLimit As CommonLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.CommonUpperLimit As CommonUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.MaleLowerLimit As MaleLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.MaleUpperLimit As MaleUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.FemaleLowerLimit As FemaleLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.FemaleUpperLimit As FemaleUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , CommonReferenceRange.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from CommonReferenceRange \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into CommonReferenceRange \r\n");
        sql.append("(JLAC10AnalyteCode, JLAC11AnalyteCode, ObservationDefinitionName, ObservationDefinitionShortName, Unit, UnitCode, ReferenceRangeType, CommonLowerLimit, CommonUpperLimit, MaleLowerLimit, MaleUpperLimit, FemaleLowerLimit, FemaleUpperLimit, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update CommonReferenceRange set \r\n");
        sql.append("JLAC11AnalyteCode = ?, ObservationDefinitionName = ?, ObservationDefinitionShortName = ?, Unit = ?, UnitCode = ?, ReferenceRangeType = ?, CommonLowerLimit = ?, CommonUpperLimit = ?, MaleLowerLimit = ?, MaleUpperLimit = ?, FemaleLowerLimit = ?, FemaleUpperLimit = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where JLAC10AnalyteCode = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  CommonReferenceRange \r\n");
        sql.append("where JLAC10AnalyteCode = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(CommonReferenceRangeEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getJLAC10AnalyteCode()
                , entity.getJLAC11AnalyteCode()
                , entity.getObservationDefinitionName()
                , entity.getObservationDefinitionShortName()
                , entity.getUnit()
                , entity.getUnitCode()
                , entity.getReferenceRangeType()
                , entity.getCommonLowerLimit()
                , entity.getCommonUpperLimit()
                , entity.getMaleLowerLimit()
                , entity.getMaleUpperLimit()
                , entity.getFemaleLowerLimit()
                , entity.getFemaleUpperLimit()
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
    public int update(CommonReferenceRangeEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getJLAC11AnalyteCode()
                , entity.getObservationDefinitionName()
                , entity.getObservationDefinitionShortName()
                , entity.getUnit()
                , entity.getUnitCode()
                , entity.getReferenceRangeType()
                , entity.getCommonLowerLimit()
                , entity.getCommonUpperLimit()
                , entity.getMaleLowerLimit()
                , entity.getMaleUpperLimit()
                , entity.getFemaleLowerLimit()
                , entity.getFemaleUpperLimit()
                , entity.getJLAC10AnalyteCode()
                , entity.getUpdateDateTime()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて共用基準範囲の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(CommonReferenceRangeEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getJLAC10AnalyteCode(),
                entity.getUpdateDateTime()
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて共用基準範囲を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public CommonReferenceRangeEntity findByPrimaryKey(String key1) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where CommonReferenceRange.JLAC10AnalyteCode = ?";

        CommonReferenceRangeEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1 }, new RowMapper<CommonReferenceRangeEntity>() {

                public CommonReferenceRangeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return CommonReferenceRangeEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
