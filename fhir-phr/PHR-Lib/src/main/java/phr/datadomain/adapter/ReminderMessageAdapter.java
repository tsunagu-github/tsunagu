/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダメッセージのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/13
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ReminderMessageEntity;

/**
 * リマインダメッセージのデータオブジェクトです。
 */
public class ReminderMessageAdapter extends ReminderMessageAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderMessageAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ReminderMessageAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 通知有効なリマインダメッセージを抽出する
     * @return
     * @throws Throwable 
     */
    public List<ReminderMessageEntity> getPushMessageList() throws Throwable {

        logger.trace("Start");
        try {
            DataAccessObject dao = new DataAccessObject(connection);

            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            sql.append("  ReminderMessage.ReminderTypeId, ");
            sql.append("  ReminderMessage.ReminderLevel ");
            sql.append("from ");
            sql.append("  ReminderMaster ");
            sql.append("    inner join ");
            sql.append("  ReminderMessage ");
            sql.append("    on ");
            sql.append("      ReminderMaster.ReminderTypeId = ReminderMessage.ReminderTypeId ");
            sql.append("where ");
            sql.append("      ReminderMaster.NotificationFlg = 1 ");
            sql.append("  and ReminderMessage.Title <> '' ");
            dao.setSql(sql.toString());

            dao.clearBindParam();
            ArrayList resultList;

            try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {

                try (ResultSet dataTable = preparedStatement.executeQuery()) {
                    if (dataTable == null) {
                        return null;
                    }
                    resultList = new ArrayList();
                    
                    while (dataTable.next()) {
                        
                        ReminderMessageEntity entity = new ReminderMessageEntity();
                        entity.setReminderTypeId(AbstractEntity.getString(dataTable, "ReminderTypeId"));
                        entity.setReminderLevel(AbstractEntity.getInt(dataTable, "ReminderLevel"));
                        resultList.add(entity);
                        
                    }
                }
            } finally {
                dao.clearBindParam();
            }
            return resultList;
        } catch (Throwable th) {
            logger.error("", th);
            throw th;
        } finally {
            logger.trace("End");
        }
    }

    /**
     * リマインダ種別IDにてリマインダメッセージの情報を削除します。
     * @param reminderTypeId
     * @return
     * @throws Throwable
     */
    public int deleteByReminderTypeId(String reminderTypeId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "delete from ReminderMessage where ReminderTypeId = ?";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, reminderTypeId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * リマインダー種別IDにてリマインダメッセージを検索します。
     * @param reminderTypeId
     * @return
     * @throws Throwable 
     */
    public List<ReminderMessageEntity> findByReminderTypeId(String reminderTypeId) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ReminderTypeId = ?";
        sql += " order by ReminderLevel";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, reminderTypeId);

        List<ReminderMessageEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            list.add(ReminderMessageEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
    
    /**
     * 同意通知のメッセージを抽出する
     * @return
     * @throws Throwable 
     */
    public ReminderMessageEntity getConsentNotificationControllerMessage() throws Throwable {

        logger.trace("Start");
        try {
            DataAccessObject dao = new DataAccessObject(connection);

            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            sql.append("  ReminderMessage.ReminderTypeId, ");
            sql.append("  ReminderMessage.ReminderLevel ");
            sql.append("from ");
            sql.append("  ReminderMaster ");
            sql.append("    inner join ");
            sql.append("  ReminderMessage ");
            sql.append("    on ");
            sql.append("      ReminderMaster.ReminderTypeId = ReminderMessage.ReminderTypeId ");
            sql.append("where ");
            sql.append("      ReminderMaster.NotificationFlg = 1 ");
            sql.append("  and ReminderMessage.Title <> '' ");
            sql.append("  and remindermessage.ReminderTypeId = 'B0000001'");
            dao.setSql(sql.toString());

            dao.clearBindParam();
            ReminderMessageEntity entity = new ReminderMessageEntity();

            try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {

                try (ResultSet dataTable = preparedStatement.executeQuery()) {
                    if (dataTable == null) {
                        return null;
                    }
                    
                    while (dataTable.next()) {
                        
//                        ReminderMessageEntity entity = new ReminderMessageEntity();
                        entity.setReminderTypeId(AbstractEntity.getString(dataTable, "ReminderTypeId"));
                        entity.setReminderLevel(AbstractEntity.getInt(dataTable, "ReminderLevel"));
//                        result.add(entity);
                        
                    }
                }
            } finally {
                dao.clearBindParam();
            }
            return entity;
        } catch (Throwable th) {
            logger.error("", th);
            throw th;
        } finally {
            logger.trace("End");
        }
    }

}
