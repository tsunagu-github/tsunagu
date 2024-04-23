/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダメッセージのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/23
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

import phr.datadomain.entity.ReminderMessageEntity;

/**
 * リマインダメッセージのデータオブジェクトです。
 */
public abstract class ReminderMessageAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderMessageAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ReminderMessageAdapterBase(Connection conn)
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
        sql.append("    ReminderMessage.ReminderTypeId As ReminderTypeId  \r\n");
        sql.append("    , ReminderMessage.ReminderLevel As ReminderLevel  \r\n");
        sql.append("    , ReminderMessage.SendMessage As SendMessage  \r\n");
        sql.append("    , ReminderMessage.Title As Title  \r\n");
        sql.append("    , ReminderMessage.SendPeriod As SendPeriod  \r\n");
        sql.append("    , ReminderMessage.SendMonth As SendMonth  \r\n");
        sql.append("    , ReminderMessage.RemoveBanDays As RemoveBanDays  \r\n");
        sql.append("from ReminderMessage \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ReminderMessage \r\n");
        sql.append("(ReminderTypeId, ReminderLevel, SendMessage, Title, SendPeriod, SendMonth, RemoveBanDays) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ReminderMessage set \r\n");
        sql.append("SendMessage = ?, Title = ?, SendPeriod = ?, SendMonth = ?, RemoveBanDays = ? \r\n");
        sql.append("where ReminderTypeId = ? AND ReminderLevel = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ReminderMessage \r\n");
        sql.append("where ReminderTypeId = ? AND ReminderLevel = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ReminderMessageEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getReminderTypeId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getReminderTypeId());
            }
            preparedStatement.setInt(2, entity.getReminderLevel());
            if (entity.getSendMessage() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getSendMessage());
            }
            if (entity.getTitle() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getTitle());
            }
            if (entity.getSendPeriod() == null ) {
                preparedStatement.setNull(5, Types.INTEGER );
            } else {
                preparedStatement.setInt(5, entity.getSendPeriod().intValue());
            }
            if (entity.getSendMonth() == null ) {
                preparedStatement.setNull(6, Types.INTEGER );
            } else {
                preparedStatement.setInt(6, entity.getSendMonth().intValue());
            }
            preparedStatement.setInt(7, entity.getRemoveBanDays());

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
    public int update(ReminderMessageEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getSendMessage() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getSendMessage());
            }
            if (entity.getTitle() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getTitle());
            }
            if (entity.getSendPeriod() == null ) {
                preparedStatement.setNull(3, Types.INTEGER );
            } else {
                preparedStatement.setInt(3, entity.getSendPeriod().intValue());
            }
            if (entity.getSendMonth() == null ) {
                preparedStatement.setNull(4, Types.INTEGER );
            } else {
                preparedStatement.setInt(4, entity.getSendMonth().intValue());
            }
            preparedStatement.setInt(5, entity.getRemoveBanDays());
            if (entity.getReminderTypeId() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getReminderTypeId());
            }
            preparedStatement.setInt(7, entity.getReminderLevel());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてリマインダメッセージの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ReminderMessageEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getReminderTypeId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getReminderTypeId());
            }
            preparedStatement.setInt(2, entity.getReminderLevel());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてリマインダメッセージを検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public ReminderMessageEntity findByPrimaryKey(String key1, int key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ReminderMessage.ReminderTypeId = ?";
        sql += "   and ReminderMessage.ReminderLevel = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);

        ReminderMessageEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ReminderMessageEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
