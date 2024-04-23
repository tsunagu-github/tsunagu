/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダマスタのデータオブジェクト
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

import phr.datadomain.entity.ReminderMasterEntity;

/**
 * リマインダマスタのデータオブジェクトです。
 */
public abstract class ReminderMasterAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderMasterAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ReminderMasterAdapterBase(Connection conn)
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
        sql.append("    ReminderMaster.ReminderTypeId As ReminderTypeId  \r\n");
        sql.append("    , ReminderMaster.ViewId As ViewId  \r\n");
        sql.append("    , ReminderMaster.ReminderTypeCd As ReminderTypeCd  \r\n");
        sql.append("    , ReminderMaster.ReminderTitle As ReminderTitle  \r\n");
        sql.append("    , ReminderMaster.ReminderKindCd As ReminderKindCd  \r\n");
        sql.append("    , ReminderMaster.NotificationFlg As NotificationFlg  \r\n");
        sql.append("    , ReminderMaster.SendType As SendType  \r\n");
        sql.append("from ReminderMaster \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ReminderMaster \r\n");
        sql.append("(ReminderTypeId, ViewId, ReminderTypeCd, ReminderTitle, ReminderKindCd, NotificationFlg, SendType) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ReminderMaster set \r\n");
        sql.append("ViewId = ?, ReminderTypeCd = ?, ReminderTitle = ?, ReminderKindCd = ?, NotificationFlg = ?, SendType = ? \r\n");
        sql.append("where ReminderTypeId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ReminderMaster \r\n");
        sql.append("where ReminderTypeId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ReminderMasterEntity entity) throws Throwable  
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
            preparedStatement.setInt(2, entity.getViewId());
            preparedStatement.setInt(3, entity.getReminderTypeCd());
            if (entity.getReminderTitle() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getReminderTitle());
            }
            if (entity.getReminderKindCd() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getReminderKindCd());
            }
            preparedStatement.setInt(6, entity.getNotificationFlg());
            if (entity.getSendType() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getSendType());
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
    public int update(ReminderMasterEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getViewId());
            preparedStatement.setInt(2, entity.getReminderTypeCd());
            if (entity.getReminderTitle() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getReminderTitle());
            }
            if (entity.getReminderKindCd() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getReminderKindCd());
            }
            preparedStatement.setInt(5, entity.getNotificationFlg());
            if (entity.getSendType() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getSendType());
            }
            if (entity.getReminderTypeId() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getReminderTypeId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてリマインダマスタの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ReminderMasterEntity entity) throws Throwable 
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

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてリマインダマスタを検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public ReminderMasterEntity findByPrimaryKey(String key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ReminderMaster.ReminderTypeId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        ReminderMasterEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ReminderMasterEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
