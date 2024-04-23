/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：Phoneアクセスログのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2018/04/03
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

import phr.datadomain.entity.PhoneAccessLogsEntity;

/**
 * Phoneアクセスログのデータオブジェクトです。
 */
public abstract class PhoneAccessLogsAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhoneAccessLogsAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PhoneAccessLogsAdapterBase(Connection conn)
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
        sql.append("    PhoneAccessLogs.AccessDatetimeSeq As AccessDatetimeSeq  \r\n");
        sql.append("    , PhoneAccessLogs.AccessDatetime As AccessDatetime  \r\n");
        sql.append("    , PhoneAccessLogs.PhrId As PhrId  \r\n");
        sql.append("    , PhoneAccessLogs.ActionId As ActionId  \r\n");
        sql.append("    , PhoneAccessLogs.ActionName As ActionName  \r\n");
        sql.append("    , PhoneAccessLogs.Url As Url  \r\n");
        sql.append("    , PhoneAccessLogs.Refer As Refer  \r\n");
        sql.append("    , PhoneAccessLogs.RequestMethod As RequestMethod  \r\n");
        sql.append("    , PhoneAccessLogs.RemoteAddress As RemoteAddress  \r\n");
        sql.append("    , PhoneAccessLogs.RemoteHost As RemoteHost  \r\n");
        sql.append("    , PhoneAccessLogs.Agent As Agent  \r\n");
        sql.append("    , PhoneAccessLogs.ReferPhrid As ReferPhrid  \r\n");
        sql.append("from PhoneAccessLogs \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into PhoneAccessLogs \r\n");
        sql.append("(AccessDatetimeSeq, AccessDatetime, PhrId, ActionId, ActionName, Url, Refer, RequestMethod, RemoteAddress, RemoteHost, Agent, ReferPhrid) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update PhoneAccessLogs set \r\n");
        sql.append("AccessDatetime = ?, ActionId = ?, ActionName = ?, Url = ?, Refer = ?, RequestMethod = ?, RemoteAddress = ?, RemoteHost = ?, Agent = ?, ReferPhrid = ? \r\n");
        sql.append("where AccessDatetimeSeq = ? AND PhrId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  PhoneAccessLogs \r\n");
        sql.append("where AccessDatetimeSeq = ? AND PhrId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(PhoneAccessLogsEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            preparedStatement.setLong(1, entity.getAccessDatetimeSeq());
            preparedStatement.setTimestamp(2, entity.getAccessDatetime());
            if (entity.getPhrId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getPhrId());
            }
            if (entity.getActionId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getActionId());
            }
            if (entity.getActionName() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getActionName());
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
    public int update(PhoneAccessLogsEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setTimestamp(1, entity.getAccessDatetime());
            if (entity.getActionId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getActionId());
            }
            if (entity.getActionName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getActionName());
            }
            if (entity.getUrl() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getUrl());
            }
            if (entity.getRefer() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getRefer());
            }
            if (entity.getRequestMethod() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getRequestMethod());
            }
            if (entity.getRemoteAddress() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getRemoteAddress());
            }
            if (entity.getRemoteHost() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getRemoteHost());
            }
            if (entity.getAgent() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getAgent());
            }
            if (entity.getReferPhrid() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getReferPhrid());
            }
            preparedStatement.setLong(11, entity.getAccessDatetimeSeq());
            if (entity.getPhrId() == null ) {
                preparedStatement.setNull(12, Types.VARCHAR );
            } else {
                preparedStatement.setString(12, entity.getPhrId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてPhoneアクセスログの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(PhoneAccessLogsEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setLong(1, entity.getAccessDatetimeSeq());
            if (entity.getPhrId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPhrId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてPhoneアクセスログを検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public PhoneAccessLogsEntity findByPrimaryKey(long key1, String key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where PhoneAccessLogs.AccessDatetimeSeq = ?";
        sql += "   and PhoneAccessLogs.PhrId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, key1);
        preparedStatement.setString(2, key2);

        PhoneAccessLogsEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = PhoneAccessLogsEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
