/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：バックアップリストアイベントのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/12/22
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

import phr.datadomain.entity.BackupRestoreEventEntity;

/**
 * バックアップリストアイベントのデータオブジェクトです。
 */
public abstract class BackupRestoreEventAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreEventAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public BackupRestoreEventAdapterBase(Connection conn)
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
        sql.append("    BackupRestoreEvent.BackupRestoreEventId As BackupRestoreEventId  \r\n");
        sql.append("    , BackupRestoreEvent.RequestInsurerNo As RequestInsurerNo  \r\n");
        sql.append("    , BackupRestoreEvent.RequestPHR_ID As RequestPHR_ID  \r\n");
        sql.append("    , BackupRestoreEvent.BackupRestoreActionTypeCd As BackupRestoreActionTypeCd  \r\n");
        sql.append("    , BackupRestoreEvent.BackupRestoreStatusCd As BackupRestoreStatusCd  \r\n");
        sql.append("    , BackupRestoreEvent.Password As Password  \r\n");
        sql.append("    , BackupRestoreEvent.RestorePhrAssociationNo As RestorePhrAssociationNo  \r\n");
        sql.append("    , BackupRestoreEvent.RestoreProjectNo As RestoreProjectNo  \r\n");
        sql.append("    , BackupRestoreEvent.RestorePHR_ID As RestorePHR_ID  \r\n");
        sql.append("    , BackupRestoreEvent.ReceiptNo As ReceiptNo  \r\n");
        sql.append("    , BackupRestoreEvent.ErrorMessage As ErrorMessage  \r\n");
        sql.append("    , BackupRestoreEvent.StartDateTiem As StartDateTiem  \r\n");
        sql.append("    , BackupRestoreEvent.EndDateTime As EndDateTime  \r\n");
        sql.append("    , BackupRestoreEvent.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , BackupRestoreEvent.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from BackupRestoreEvent \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into BackupRestoreEvent \r\n");
        sql.append("(BackupRestoreEventId, RequestInsurerNo, RequestPHR_ID, BackupRestoreActionTypeCd, BackupRestoreStatusCd, Password, RestorePhrAssociationNo, RestoreProjectNo, RestorePHR_ID, ReceiptNo, ErrorMessage, StartDateTiem, EndDateTime, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update BackupRestoreEvent set \r\n");
        sql.append("RequestInsurerNo = ?, RequestPHR_ID = ?, BackupRestoreActionTypeCd = ?, BackupRestoreStatusCd = ?, Password = ?, RestorePhrAssociationNo = ?, RestoreProjectNo = ?, RestorePHR_ID = ?, ReceiptNo = ?, ErrorMessage = ?, StartDateTiem = ?, EndDateTime = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where BackupRestoreEventId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  BackupRestoreEvent \r\n");
        sql.append("where BackupRestoreEventId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(BackupRestoreEventEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getBackupRestoreEventId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getBackupRestoreEventId());
            }
            if (entity.getRequestInsurerNo() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getRequestInsurerNo());
            }
            if (entity.getRequestPHR_ID() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getRequestPHR_ID());
            }
            preparedStatement.setInt(4, entity.getBackupRestoreActionTypeCd());
            preparedStatement.setInt(5, entity.getBackupRestoreStatusCd());
            if (entity.getPassword() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getPassword());
            }
            if (entity.getRestorePhrAssociationNo() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getRestorePhrAssociationNo());
            }
            if (entity.getRestoreProjectNo() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getRestoreProjectNo());
            }
            if (entity.getRestorePHR_ID() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getRestorePHR_ID());
            }
            if (entity.getReceiptNo() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getReceiptNo());
            }
            if (entity.getErrorMessage() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getErrorMessage());
            }
            preparedStatement.setTimestamp(12, entity.getStartDateTiem());
            preparedStatement.setTimestamp(13, entity.getEndDateTime());

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
    public int update(BackupRestoreEventEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getRequestInsurerNo() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getRequestInsurerNo());
            }
            if (entity.getRequestPHR_ID() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getRequestPHR_ID());
            }
            preparedStatement.setInt(3, entity.getBackupRestoreActionTypeCd());
            preparedStatement.setInt(4, entity.getBackupRestoreStatusCd());
            if (entity.getPassword() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getPassword());
            }
            if (entity.getRestorePhrAssociationNo() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getRestorePhrAssociationNo());
            }
            if (entity.getRestoreProjectNo() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getRestoreProjectNo());
            }
            if (entity.getRestorePHR_ID() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getRestorePHR_ID());
            }
            if (entity.getReceiptNo() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getReceiptNo());
            }
            if (entity.getErrorMessage() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getErrorMessage());
            }
            preparedStatement.setTimestamp(11, entity.getStartDateTiem());
            preparedStatement.setTimestamp(12, entity.getEndDateTime());
            if (entity.getBackupRestoreEventId() == null ) {
                preparedStatement.setNull(13, Types.VARCHAR );
            } else {
                preparedStatement.setString(13, entity.getBackupRestoreEventId());
            }
            preparedStatement.setTimestamp(14, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてバックアップリストアイベントの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(BackupRestoreEventEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getBackupRestoreEventId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getBackupRestoreEventId());
            }
            preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてバックアップリストアイベントを検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public BackupRestoreEventEntity findByPrimaryKey(String key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where BackupRestoreEvent.BackupRestoreEventId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        BackupRestoreEventEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = BackupRestoreEventEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
