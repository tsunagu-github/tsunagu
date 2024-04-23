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
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ReminderPushedListEntity;
import phr.utility.TypeUtility;

/**
 * リマインダプッシュ完了リストのデータオブジェクトです。
 */
public class ReminderPushedListAdapter extends ReminderPushedListAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderPushedListAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ReminderPushedListAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * リマインダ種別IDにてリマインダプッシュ完了リストの情報を削除します。
     * @param reminderTypeId
     * @return
     * @throws Throwable
     */
    public int deleteByReminderTypeId(String reminderTypeId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "delete from ReminderPushedList where ReminderTypeId = ?";
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
     * プッシュ解禁日を更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int updateRemoveBanDate(ReminderPushedListEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("update ReminderPushedList set \r\n");
        sql.append("RemoveBanDate = ? \r\n");
        sql.append("where PHR_ID = ? AND ReminderTypeId = ? AND ReminderLevel = ? \r\n");
        sql.append(" \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setDate(1, TypeUtility.convertDate(entity.getRemoveBanDate()));
        preparedStatement.setString(2, entity.getPHR_ID());
        preparedStatement.setString(3, entity.getReminderTypeId());
        preparedStatement.setInt(4, entity.getReminderLevel());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * PHR_IDでReminderPushedListテーブルからレコードを削除します。
     * @param phr_id
     * @return rowCount
     * @throws Throwable
     */
    public int deleteReminderPushedListRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  ReminderPushedList \r\n");
        sb.append("where ReminderPushedList.PHR_ID = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phr_id);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
