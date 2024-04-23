/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アクセスログのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/10/07
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

import phr.datadomain.entity.AccessLogsEntity;

/**
 * アクセスログのデータオブジェクトです。
 */
public abstract class AccessLogsAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccessLogsAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public AccessLogsAdapterBase(Connection conn)
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
        sql.append("    AccessLogs.AccessDatetime As AccessDatetime  \r\n");
        sql.append("    , AccessLogs.AccessDatetimeSeq As AccessDatetimeSeq  \r\n");
        sql.append("    , AccessLogs.AccessUser As AccessUser  \r\n");
        sql.append("    , AccessLogs.AccessUserName As AccessUserName  \r\n");
        sql.append("    , AccessLogs.SiteType As SiteType  \r\n");
        sql.append("    , AccessLogs.ActionId As ActionId  \r\n");
        sql.append("    , AccessLogs.ActionName As ActionName  \r\n");
        sql.append("    , AccessLogs.ScreenCd As ScreenCd  \r\n");
        sql.append("    , AccessLogs.ScreenName As ScreenName  \r\n");
        sql.append("    , AccessLogs.Url As Url  \r\n");
        sql.append("    , AccessLogs.Refer As Refer  \r\n");
        sql.append("    , AccessLogs.RequestMethod As RequestMethod  \r\n");
        sql.append("    , AccessLogs.RemoteAddress As RemoteAddress  \r\n");
        sql.append("    , AccessLogs.RemoteHost As RemoteHost  \r\n");
        sql.append("    , AccessLogs.Agent As Agent  \r\n");
        sql.append("    , AccessLogs.ReferPhrid As ReferPhrid  \r\n");
        sql.append("from AccessLogs \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into AccessLogs \r\n");
        sql.append("(AccessDatetime, AccessDatetimeSeq, AccessUser, AccessUserName, SiteType, ActionId, ActionName, ScreenCd, ScreenName, Url, Refer, RequestMethod, RemoteAddress, RemoteHost, Agent, ReferPhrid) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update AccessLogs set \r\n");
        sql.append("AccessDatetime = ?, AccessUserName = ?, ActionName = ?, ScreenCd = ?, ScreenName = ?, Url = ?, Refer = ?, RequestMethod = ?, RemoteAddress = ?, RemoteHost = ?, Agent = ?, ReferPhrid = ? \r\n");
        sql.append("where AccessDatetimeSeq = ? AND AccessUser = ? AND SiteType = ? AND ActionId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  AccessLogs \r\n");
        sql.append("where AccessDatetimeSeq = ? AND AccessUser = ? AND SiteType = ? AND ActionId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(AccessLogsEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            preparedStatement.setTimestamp(1, entity.getAccessDatetime());
            preparedStatement.setLong(2, entity.getAccessDatetimeSeq());
            if (entity.getAccessUser() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getAccessUser());
            }
            if (entity.getAccessUserName() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getAccessUserName());
            }
            preparedStatement.setInt(5, entity.getSiteType());
            if (entity.getActionId() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getActionId());
            }
            if (entity.getActionName() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getActionName());
            }
            if (entity.getScreenCd() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getScreenCd());
            }
            if (entity.getScreenName() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getScreenName());
            }
            if (entity.getUrl() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getUrl());
            }
            if (entity.getRefer() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getRefer());
            }
            if (entity.getRequestMethod() == null ) {
                preparedStatement.setNull(12, Types.VARCHAR );
            } else {
                preparedStatement.setString(12, entity.getRequestMethod());
            }
            if (entity.getRemoteAddress() == null ) {
                preparedStatement.setNull(13, Types.VARCHAR );
            } else {
                preparedStatement.setString(13, entity.getRemoteAddress());
            }
            if (entity.getRemoteHost() == null ) {
                preparedStatement.setNull(14, Types.VARCHAR );
            } else {
                preparedStatement.setString(14, entity.getRemoteHost());
            }
            if (entity.getAgent() == null ) {
                preparedStatement.setNull(15, Types.VARCHAR );
            } else {
                preparedStatement.setString(15, entity.getAgent());
            }
            if (entity.getReferPhrid() == null ) {
                preparedStatement.setNull(16, Types.VARCHAR );
            } else {
                preparedStatement.setString(16, entity.getReferPhrid());
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
    public int update(AccessLogsEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setTimestamp(1, entity.getAccessDatetime());
            if (entity.getAccessUserName() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getAccessUserName());
            }
            if (entity.getActionName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getActionName());
            }
            if (entity.getScreenCd() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getScreenCd());
            }
            if (entity.getScreenName() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getScreenName());
            }
            if (entity.getUrl() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getUrl());
            }
            if (entity.getRefer() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getRefer());
            }
            if (entity.getRequestMethod() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getRequestMethod());
            }
            if (entity.getRemoteAddress() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getRemoteAddress());
            }
            if (entity.getRemoteHost() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getRemoteHost());
            }
            if (entity.getAgent() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getAgent());
            }
            if (entity.getReferPhrid() == null ) {
                preparedStatement.setNull(12, Types.VARCHAR );
            } else {
                preparedStatement.setString(12, entity.getReferPhrid());
            }
            preparedStatement.setLong(13, entity.getAccessDatetimeSeq());
            if (entity.getAccessUser() == null ) {
                preparedStatement.setNull(14, Types.VARCHAR );
            } else {
                preparedStatement.setString(14, entity.getAccessUser());
            }
            preparedStatement.setInt(15, entity.getSiteType());
            if (entity.getActionId() == null ) {
                preparedStatement.setNull(16, Types.VARCHAR );
            } else {
                preparedStatement.setString(16, entity.getActionId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてアクセスログの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(AccessLogsEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setLong(1, entity.getAccessDatetimeSeq());
            if (entity.getAccessUser() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getAccessUser());
            }
            preparedStatement.setInt(3, entity.getSiteType());
            if (entity.getActionId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getActionId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてアクセスログを検索します。
     * @param key1
     * @param key2
     * @param key3
     * @param key4
     * @return
     * @throws Throwable
     */
    public AccessLogsEntity findByPrimaryKey(long key1, String key2, int key3, String key4) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where AccessLogs.AccessDatetimeSeq = ?";
        sql += "   and AccessLogs.AccessUser = ?";
        sql += "   and AccessLogs.SiteType = ?";
        sql += "   and AccessLogs.ActionId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setInt(3, key3);
        preparedStatement.setString(4, key4);

        AccessLogsEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = AccessLogsEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
