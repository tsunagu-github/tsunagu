/********************************************************************************
 * システム名      ：phr
 * コンポーネント名：JLAC11単位コード情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/12/08
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
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.JLAC11UnitCodeEntity;
import phr.datadomain.entity.ObservationEntity;

/**
 * JLAC11単位コード情報のデータオブジェクトです。
 */
public abstract class JLAC11UnitCodeAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = Logger.getLogger(JLAC11UnitCodeAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public JLAC11UnitCodeAdapterBase(Connection conn)
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
        sql.append("    JLAC11UnitCode.JLAC11UnitCode As JLAC11UnitCode  \r\n");
        sql.append("    , JLAC11UnitCode.Unit As Unit  \r\n");
        sql.append("    , JLAC11UnitCode.Note As Note  \r\n");
        sql.append("    , JLAC11UnitCode.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , JLAC11UnitCode.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from JLAC11UnitCode \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into JLAC11UnitCode \r\n");
        sql.append("(JLAC11UnitCode, Unit, Note, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update JLAC11UnitCode set \r\n");
        sql.append("Unit = ?, Note = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where JLAC11UnitCode = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  JLAC11UnitCode \r\n");
        sql.append("where JLAC11UnitCode = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(JLAC11UnitCodeEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getJLAC11UnitCode() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getJLAC11UnitCode());
            }
            if (entity.getUnit() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getUnit());
            }
            if (entity.getNote() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getNote());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();

        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(JLAC11UnitCodeEntity entity) throws Throwable {
    	logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getUnit() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getUnit());
            }
            if (entity.getNote() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getNote());
            }
            preparedStatement.setString(3, entity.getJLAC11UnitCode());
            preparedStatement.setTimestamp(4, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてJLAC11単位コード情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(JLAC11UnitCodeEntity entity) throws Throwable {
    	logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getJLAC11UnitCode() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getJLAC11UnitCode() );
            }
            preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにてJLAC11単位コード情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public JLAC11UnitCodeEntity findByPrimaryKey(String key1) throws Throwable { 
    	logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where JLAC11UnitCode.JLAC11UnitCode = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        JLAC11UnitCodeEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = JLAC11UnitCodeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
}
