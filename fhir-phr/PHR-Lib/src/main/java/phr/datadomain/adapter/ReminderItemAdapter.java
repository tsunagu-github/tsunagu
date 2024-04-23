/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダ項目のデータオブジェクト
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
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ReminderItemEntity;

/**
 * リマインダ項目のデータオブジェクトです。
 */
public class ReminderItemAdapter extends ReminderItemAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderItemAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ReminderItemAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * リマインダ種別IDにてリマインダ項目の情報を削除します。
     * @param reminderTypeId
     * @return
     * @throws Throwable
     */
    public int deleteByReminderTypeId(String reminderTypeId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "delete from ReminderItem where ReminderTypeId = ?";
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
     * リマインダ種別IDにてリマインダ項目を検索します。
     * @param reminderTypeId
     * @return
     * @throws Throwable
     */
    public List<ReminderItemEntity> findByReminderTypeId(String reminderTypeId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ReminderTypeId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, reminderTypeId);

        List<ReminderItemEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            list.add(ReminderItemEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }

}
