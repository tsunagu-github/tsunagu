/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：範囲外種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter.base;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import java.sql.PreparedStatement;
import java.sql.Types;
import phr.datadomain.DataAccessObject;
import phr.utility.TypeUtility;

import phr.datadomain.entity.OutRangeTypeEntity;

/**
 * 範囲外種別のデータオブジェクトです。
 */
public abstract class OutRangeTypeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OutRangeTypeAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public OutRangeTypeAdapterBase(Connection conn)
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
        sql.append("    OutRangeType.OutRangeTypeCd As OutRangeTypeCd  \r\n");
        sql.append("    , OutRangeType.Name As Name  \r\n");
        sql.append("from OutRangeType \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into OutRangeType \r\n");
        sql.append("(OutRangeTypeCd, Name) \r\n");
        sql.append("values (?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update OutRangeType set \r\n");
        sql.append("Name = ? \r\n");
        sql.append("where OutRangeTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  OutRangeType \r\n");
        sql.append("where OutRangeTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(OutRangeTypeEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            preparedStatement.setInt(1, entity.getOutRangeTypeCd());
            if (entity.getName() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getName());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();

        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(OutRangeTypeEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getName());
            }
            preparedStatement.setInt(2, entity.getOutRangeTypeCd());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて範囲外種別の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(OutRangeTypeEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getOutRangeTypeCd());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて範囲外種別を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public OutRangeTypeEntity findByPrimaryKey(int key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where OutRangeType.OutRangeTypeCd = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, key1);

        OutRangeTypeEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = OutRangeTypeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
