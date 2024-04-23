/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダプッシュ完了リストのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/13
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

import phr.datadomain.entity.ReminderPushedListEntity;

/**
 * リマインダプッシュ完了リストのデータオブジェクトです。
 */
public abstract class ReminderPushedListAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderPushedListAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ReminderPushedListAdapterBase(Connection conn)
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
        sql.append("    ReminderPushedList.PHR_ID As PHR_ID  \r\n");
        sql.append("    , ReminderPushedList.ReminderTypeId As ReminderTypeId  \r\n");
        sql.append("    , ReminderPushedList.ReminderLevel As ReminderLevel  \r\n");
        sql.append("    , ReminderPushedList.PushDate As PushDate  \r\n");
        sql.append("    , ReminderPushedList.RemoveBanDate As RemoveBanDate  \r\n");
        sql.append("from ReminderPushedList \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ReminderPushedList \r\n");
        sql.append("(PHR_ID, ReminderTypeId, ReminderLevel, PushDate, RemoveBanDate) \r\n");
        sql.append("values (?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ReminderPushedList set \r\n");
        sql.append("PushDate = ?, RemoveBanDate = ? \r\n");
        sql.append("where PHR_ID = ? AND ReminderTypeId = ? AND ReminderLevel = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ReminderPushedList \r\n");
        sql.append("where PHR_ID = ? AND ReminderTypeId = ? AND ReminderLevel = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ReminderPushedListEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getPHR_ID());
            }
            if (entity.getReminderTypeId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getReminderTypeId());
            }
            preparedStatement.setInt(3, entity.getReminderLevel());
            if (entity.getPushDate() == null ) {
                preparedStatement.setNull(4, Types.DATE );
            } else {
                preparedStatement.setDate(4, TypeUtility.convertDate(entity.getPushDate()));
            }
            if (entity.getRemoveBanDate() == null ) {
                preparedStatement.setNull(5, Types.DATE );
            } else {
                preparedStatement.setDate(5, TypeUtility.convertDate(entity.getRemoveBanDate()));
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
    public int update(ReminderPushedListEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getPushDate() == null ) {
                preparedStatement.setNull(1, Types.DATE );
            } else {
                preparedStatement.setDate(1, TypeUtility.convertDate(entity.getPushDate()));
            }
            if (entity.getRemoveBanDate() == null ) {
                preparedStatement.setNull(2, Types.DATE );
            } else {
                preparedStatement.setDate(2, TypeUtility.convertDate(entity.getRemoveBanDate()));
            }
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getPHR_ID());
            }
            if (entity.getReminderTypeId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getReminderTypeId());
            }
            preparedStatement.setInt(5, entity.getReminderLevel());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてリマインダプッシュ完了リストの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ReminderPushedListEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getPHR_ID());
            }
            if (entity.getReminderTypeId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getReminderTypeId());
            }
            preparedStatement.setInt(3, entity.getReminderLevel());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてリマインダプッシュ完了リストを検索します。
     * @param key1
     * @param key2
     * @param key3
     * @return
     * @throws Throwable
     */
    public ReminderPushedListEntity findByPrimaryKey(String key1, String key2, int key3) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ReminderPushedList.PHR_ID = ?";
        sql += "   and ReminderPushedList.ReminderTypeId = ?";
        sql += "   and ReminderPushedList.ReminderLevel = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setInt(3, key3);

        ReminderPushedListEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ReminderPushedListEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
