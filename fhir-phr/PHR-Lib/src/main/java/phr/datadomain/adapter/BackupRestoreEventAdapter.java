/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：バックアップリストアイベントのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/12/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.BackupRestoreActionTypeEntity;
import phr.datadomain.entity.BackupRestoreEventEntity;
import phr.datadomain.entity.BackupRestoreStatusEntity;

/**
 * バックアップリストアイベントのデータオブジェクトです。
 */
public class BackupRestoreEventAdapter extends BackupRestoreEventAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreEventAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public BackupRestoreEventAdapter(Connection conn) {
        super(conn);
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 指定した利用者の最後に実施したバックアップ情報を取得する
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    public BackupRestoreEventEntity findByLastBackupEntity(String phrId) throws Throwable {
        logger.trace("Start");
        try {
            DataAccessObject dao = new DataAccessObject(connection);
            String sql = getSelectedSql();
            sql += " where BackupRestoreEvent.RequestPHR_ID = ?";
            sql += "   and BackupRestoreEvent.BackupRestoreActionTypeCd = ?";
            sql += "   and BackupRestoreEvent.BackupRestoreStatusCd = ?";
            sql += "  order by EndDateTime DESC";

            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            preparedStatement.setString(1, phrId);
            preparedStatement.setInt(2, BackupRestoreActionTypeEntity.ACTION_BACKUP);
            preparedStatement.setInt(3, BackupRestoreStatusEntity.STATUS_COMPLETED);

            BackupRestoreEventEntity entity = null;

            ResultSet dataTable = preparedStatement.executeQuery();
            if (dataTable == null) {
                return null;
            }

            while (dataTable.next()) {
                entity = BackupRestoreEventEntity.setData(dataTable);
                break;
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement.close();
            return entity;
        } catch (Throwable th) {
            logger.error("", th);
            throw th;
        } finally {
            logger.trace("End");
        }
    }

    /**
     * アクションCDとステータスCDにてバックアップイベント情報を検索する
     *
     * @param actionTypeCd
     * @param statusCd
     * @return
     * @throws Throwable
     */
    public List<BackupRestoreEventEntity> findByStatus(int actionTypeCd, int statusCd) throws Throwable {
        logger.trace("Start");
        try {
            List<BackupRestoreEventEntity> list = new ArrayList<>();
            DataAccessObject dao = new DataAccessObject(connection);
            String sql = getSelectedSql();
            sql += " where BackupRestoreEvent.BackupRestoreStatusCd = ?";
            sql += "   and BackupRestoreEvent.BackupRestoreActionTypeCd = ?";

            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            preparedStatement.setInt(1, statusCd);
            preparedStatement.setInt(2, actionTypeCd);

            ResultSet dataTable = preparedStatement.executeQuery();
            if (dataTable == null) {
                return null;
            }

            while (dataTable.next()) {
                BackupRestoreEventEntity entity = BackupRestoreEventEntity.setData(dataTable);
                list.add(entity);
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement.close();
            return list;
        } catch (Throwable th) {
            logger.error("", th);
            throw th;
        } finally {
            logger.trace("End");
        }
    }

    /**
     * イベントIDを採番する
     *
     * @return
     * @throws Throwable
     */
    public static String numberingBackupRestoreEventId() throws Throwable {
        String backupRestoreEventId = "0000000000";
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            {
                String sql = "update Seq_BackupRestoreEventId set BackupRestoreEventId=LAST_INSERT_ID(BackupRestoreEventId+1)";
                dao.setSql(sql);

                dao.clearBindParam();
                PreparedStatement preparedStatement = dao.getPreparedStatement();
                int rowCount = preparedStatement.executeUpdate();
                preparedStatement.close();
            }

            {
                String sql = "SELECT LAST_INSERT_ID() as SeqId";
                dao.setSql(sql);
                dao.clearBindParam();

                PreparedStatement preparedStatement = dao.getPreparedStatement();
                ResultSet dataTable = preparedStatement.executeQuery();
                if (dataTable == null) {
                    return null;
                }

                while (dataTable.next()) {
                    long id = AbstractEntity.getLong(dataTable, "SeqId");
                    backupRestoreEventId = String.format("%010d", id);
                }
                dao.clearBindParam();
                dataTable.close();
                preparedStatement.close();
            }
            dao.commitTransaction();
            return backupRestoreEventId;
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }

    /**
     * buckupRestoreEventIdの値にてバックアップリストアイベント情報の情報を削除します。
     *
     * @param buckupRestoreEventId
     * @return
     * @throws Throwable
     */
    public int deleteByBackupRestoreEventId(String backupRestoreEventId) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sqlorg = getDeleteSql();
        int idx = sqlorg.indexOf("and");
        String sql = sqlorg.substring(0, idx);
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, backupRestoreEventId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * phrIDにてバックアップイベント情報を検索する
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    public List<BackupRestoreEventEntity> findByphrId(String phrId) throws Throwable {
        logger.trace("Start");
        try {
            List<BackupRestoreEventEntity> list = new ArrayList<>();
            DataAccessObject dao = new DataAccessObject(connection);
            String sql = getSelectedSql();
            sql += " where BackupRestoreEvent.RequestPHR_ID = ?";

            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            preparedStatement.setString(1, phrId);

            ResultSet dataTable = preparedStatement.executeQuery();
            if (dataTable == null) {
                return null;
            }

            while (dataTable.next()) {
                BackupRestoreEventEntity entity = BackupRestoreEventEntity.setData(dataTable);
                list.add(entity);
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement.close();
            return list;
        } catch (Throwable th) {
            logger.error("", th);
            throw th;
        } finally {
            logger.trace("End");
        }
    }

    /**
     * バックアップイベントを無効にする
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    public int invalidBackupEvent(String phrId) throws Throwable {
        logger.trace("Start");
        try {
            DataAccessObject dao = new DataAccessObject(connection);
            String sql = "update BackupRestoreEvent set";
            sql += " BackupRestoreEvent.BackupRestoreStatusCd = ?";
            sql += " where BackupRestoreEvent.RequestPHR_ID = ?";
            sql += "   and BackupRestoreEvent.BackupRestoreActionTypeCd = ?";
            sql += "   and BackupRestoreEvent.BackupRestoreStatusCd = ?";

            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            preparedStatement.setInt(1, BackupRestoreStatusEntity.STATUS_INVALID);
            preparedStatement.setString(2, phrId);
            preparedStatement.setInt(3, BackupRestoreActionTypeEntity.ACTION_BACKUP);
            preparedStatement.setInt(4, BackupRestoreStatusEntity.STATUS_COMPLETED);

            int count = preparedStatement.executeUpdate();

            return count;
        } catch (Throwable th) {
            logger.error("", th);
            throw th;
        } finally {
            logger.trace("End");
        }
    }
}
