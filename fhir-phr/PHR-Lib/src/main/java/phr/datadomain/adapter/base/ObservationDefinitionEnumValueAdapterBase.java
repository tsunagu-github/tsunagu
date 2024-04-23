/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目列挙値情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/16
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

import phr.datadomain.entity.ObservationDefinitionEnumValueEntity;

/**
 * 管理項目列挙値情報のデータオブジェクトです。
 */
public abstract class ObservationDefinitionEnumValueAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionEnumValueAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionEnumValueAdapterBase(Connection conn)
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
        sql.append("    ObservationDefinitionEnumValue.ViewId As ViewId  \r\n");
        sql.append("    , ObservationDefinitionEnumValue.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , ObservationDefinitionEnumValue.EnumId As EnumId  \r\n");
        sql.append("    , ObservationDefinitionEnumValue.EnumName As EnumName  \r\n");
        sql.append("    , ObservationDefinitionEnumValue.EnumValue As EnumValue  \r\n");
        sql.append("from ObservationDefinitionEnumValue \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationDefinitionEnumValue \r\n");
        sql.append("(ViewId, ObservationDefinitionId, EnumId, EnumName, EnumValue) \r\n");
        sql.append("values (?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ObservationDefinitionEnumValue set \r\n");
        sql.append("EnumName = ?, EnumValue = ? \r\n");
        sql.append("where ViewId = ? AND ObservationDefinitionId = ? AND EnumId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ObservationDefinitionEnumValue \r\n");
        sql.append("where ViewId = ? AND ObservationDefinitionId = ? AND EnumId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationDefinitionEnumValueEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            preparedStatement.setInt(1, entity.getViewId());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(3, entity.getEnumId());
            if (entity.getEnumName() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getEnumName());
            }
            if (entity.getEnumValue() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getEnumValue());
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
    public int update(ObservationDefinitionEnumValueEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getEnumName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getEnumName());
            }
            if (entity.getEnumValue() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getEnumValue());
            }
            preparedStatement.setInt(3, entity.getViewId());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(5, entity.getEnumId());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて管理項目列挙値情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationDefinitionEnumValueEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getViewId());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(3, entity.getEnumId());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて管理項目列挙値情報を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionEnumValueEntity findByPrimaryKey(int key1, String key2, int key3) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionEnumValue.ViewId = ?";
        sql += "   and ObservationDefinitionEnumValue.ObservationDefinitionId = ?";
        sql += "   and ObservationDefinitionEnumValue.EnumId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setInt(3, key3);

        ObservationDefinitionEnumValueEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionEnumValueEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
