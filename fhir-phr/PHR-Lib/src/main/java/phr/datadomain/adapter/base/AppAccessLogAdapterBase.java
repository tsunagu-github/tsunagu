/********************************************************************************
 * システム名      ：MInCS for ePRO
 * コンポーネント名：Appアクセスログのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/05/31
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.AnswerStatusEntity;
import phr.datadomain.entity.AppAccessLogEntity;

/**
 * Appアクセスログのデータオブジェクトです。
 */
public abstract class AppAccessLogAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(AppAccessLogAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public AppAccessLogAdapterBase(Connection conn)
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
        sql.append("    AppAccessLog.Seq_Id As Seq_Id  \r\n");
        sql.append("    , AppAccessLog.PHR_ID As PHR_ID  \r\n");
        sql.append("    , AppAccessLog.AccessDateTime As AccessDateTime  \r\n");
        sql.append("from AppAccessLog \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into AppAccessLog \r\n");
        sql.append("(Seq_Id, PHR_ID, AccessDateTime) \r\n");
        sql.append("values (?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update AppAccessLog set \r\n");
        sql.append("AccessDateTime = ? \r\n");
        sql.append("where Seq_Id = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  AppAccessLog \r\n");
        sql.append("where Seq_Id = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(AppAccessLogEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getSeq_Id() < 0 ) {
                preparedStatement.setNull(1, Types.INTEGER );
            } else {
                preparedStatement.setInt(1, entity.getSeq_Id());
            }
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPHR_ID());
            }
            if (entity.getAccessDateTime() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getAccessDateTime().toString());
            }

        int rowCount = preparedStatement.executeUpdate();
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
    public int update(AppAccessLogEntity entity) throws Throwable {
        logger.debug("Start");

        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getAccessDateTime() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getAccessDateTime().toString());
            }
            if (entity.getSeq_Id() < 0 ) {
                preparedStatement.setNull(2, Types.INTEGER );
            } else {
                preparedStatement.setInt(2, entity.getSeq_Id());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();

        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてAppアクセスログの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(AppAccessLogEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getSeq_Id() < 0 ) {
                preparedStatement.setNull(1, Types.INTEGER );
            } else {
                preparedStatement.setInt(1, entity.getSeq_Id());
            }
            
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにてAppアクセスログを検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public AppAccessLogEntity findByPrimaryKey(String key1) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where AppAccessLog.Seq_Id = ?";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        
        AppAccessLogEntity entity = null;
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            entity = AppAccessLogEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
}
