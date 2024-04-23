/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：連携医療機関システム情報のデータオブジェクト
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

import phr.datadomain.entity.MedicalOrganizationSystemEntity;

/**
 * 連携医療機関システム情報のデータオブジェクトです。
 */
public abstract class MedicalOrganizationSystemAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(MedicalOrganizationSystemAdapterBase.class);
	private static Logger logger = Logger.getLogger(MedicalOrganizationSystemAdapterBase.class);

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
    public MedicalOrganizationSystemAdapterBase() {
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    MedicalOrganizationSystem.DataCooperationSystemId As DataCooperationSystemId  \r\n");
        sql.append("    , MedicalOrganizationSystem.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , MedicalOrganizationSystem.SystemTypeId As SystemTypeId  \r\n");
        sql.append("    , MedicalOrganizationSystem.FhirServerBaseUrl As FhirServerBaseUrl  \r\n");
        sql.append("    , MedicalOrganizationSystem.AuthorizationServerUrl As AuthorizationServerUrl  \r\n");
        sql.append("    , MedicalOrganizationSystem.ClientId As ClientId  \r\n");
        sql.append("    , MedicalOrganizationSystem.ClientSecret As ClientSecret  \r\n");
        sql.append("    , MedicalOrganizationSystem.Note As Note  \r\n");
        sql.append("    , MedicalOrganizationSystem.Invalid As Invalid  \r\n");
        sql.append("    , MedicalOrganizationSystem.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , MedicalOrganizationSystem.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from MedicalOrganizationSystem \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into MedicalOrganizationSystem \r\n");
        sql.append("(DataCooperationSystemId, MedicalOrganizationCd, SystemTypeId, FhirServerBaseUrl, AuthorizationServerUrl, ClientId, ClientSecret, Note, Invalid, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update MedicalOrganizationSystem set \r\n");
        sql.append("MedicalOrganizationCd = ?, SystemTypeId = ?, FhirServerBaseUrl = ?, AuthorizationServerUrl = ?, ClientId = ?, ClientSecret = ?, Note = ?, Invalid = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where DataCooperationSystemId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  MedicalOrganizationSystem \r\n");
        sql.append("where DataCooperationSystemId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(MedicalOrganizationSystemEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getInsertSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getDataCooperationSystemId()
                , entity.getMedicalOrganizationCd()
                , entity.getSystemTypeId()
                , entity.getFhirServerBaseUrl()
                , entity.getAuthorizationServerUrl()
                , entity.getClientId()
                , entity.getClientSecret()
                , entity.getNote()
                , entity.isInvalid()
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
    public int update(MedicalOrganizationSystemEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getUpdateSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getMedicalOrganizationCd()
                , entity.getSystemTypeId()
                , entity.getFhirServerBaseUrl()
                , entity.getAuthorizationServerUrl()
                , entity.getClientId()
                , entity.getClientSecret()
                , entity.getNote()
                , entity.isInvalid()
                , entity.getDataCooperationSystemId()
                , entity.getUpdateDateTime()

            }
        );
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて連携医療機関システム情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(MedicalOrganizationSystemEntity entity) throws Throwable {
        logger.debug("Start");
        String sql = getDeleteSql();

        int rowCount = jdbcTemplate.update(sql, 
            new Object[] { 
                entity.getDataCooperationSystemId(),
                entity.getUpdateDateTime()
            }
        );
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて連携医療機関システム情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public MedicalOrganizationSystemEntity findByPrimaryKey(int key1) throws Throwable { 
        logger.debug("Start");
        String sql = getSelectedSql();
        sql += " where MedicalOrganizationSystem.DataCooperationSystemId = ?";

        MedicalOrganizationSystemEntity entity = null;
        try {
            entity = jdbcTemplate.queryForObject(sql, new Object[] { key1 }, new RowMapper<MedicalOrganizationSystemEntity>() {

                public MedicalOrganizationSystemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return MedicalOrganizationSystemEntity.setData(rs, false);
                }

            });

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {}
        logger.debug("End");
        return entity;
    }
}
