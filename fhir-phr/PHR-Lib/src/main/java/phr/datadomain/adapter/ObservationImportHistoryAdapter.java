/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果取得履歴情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/04/26
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationImportHistoryEntity;
import phr.datadomain.entity.PatientEntity;
import phr.utility.TypeUtility;

/**
 * 検査結果取得履歴情報のデータオブジェクトです。
 */
@Repository
public class ObservationImportHistoryAdapter extends ObservationImportHistoryAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationImportHistoryAdapter.class);
    private static Logger logger = Logger.getLogger(ObservationImportHistoryAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationImportHistoryAdapter() {
    }
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * コンストラクタ
     */
    public ObservationImportHistoryAdapter(Connection conn)
    {
        super(conn);
    }
    
    /**
     * 検査結果取得履歴情報の取得
     * @return
     * @throws SQLException 
     * @throws Throwable
     */
    public ObservationImportHistoryEntity getObservationHistory(ObservationImportHistoryEntity en) throws SQLException {
    	logger.debug("Start");
    	
    	DataAccessObject dao = new DataAccessObject(connection);
    	
    	// 処理ステータスが「1:正常終了」もしくは「2:正常終了エラーあり」の値を持ち、かつ、
    	// 指定されたデータ連携システムIDを持つ検査結果履歴取得IDが最大のものを取得
    	StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationImportHistory.ImportHistoryId As ImportHistoryId  \r\n");
        sql.append("    , ObservationImportHistory.DataCooperationSystemId As DataCooperationSystemId  \r\n");
        sql.append("    , ObservationImportHistory.ExecutionDate As ExecutionDate  \r\n");
        sql.append("    , ObservationImportHistory.Status As Status  \r\n");
        sql.append("    , ObservationImportHistory.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , ObservationImportHistory.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from ObservationImportHistory \r\n");
        sql.append(" where ObservationImportHistory.Status IN ('1', '2')");
        sql.append(" and ObservationImportHistory.DataCooperationSystemId = ?");
        sql.append(" order by ImportHistoryId desc");
        sql.append(" limit 1");
//        String sql = getSelectedSql();
//        sql += " where ObservationImportHistory.Status IN ('1', '2')";
//        sql += " order by ImportHistoryId desc"; 
//        sql += " limit 1"; 
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, en.getDataCooperationSystemId());
        
        ObservationImportHistoryEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationImportHistoryEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }
    
    /**
     * 作成日が最新の検査結果取得履歴情報を取得
     * @return
     * @throws Throwable
     */
    public ObservationImportHistoryEntity getLatestRecord() throws Throwable { 
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject();
        String sql = getSelectedSql();
        sql += " order by createDateTime desc";
        sql += " limit 1";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        ObservationImportHistoryEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationImportHistoryEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }
    
    /**
     * importHistoryIdで検査結果取得履歴情報を取得
     * @param importHistoryId
     * @return
     * @throws Throwable
     */
    public ObservationImportHistoryEntity getRecord(String importHistoryId) throws Throwable { 
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject();
//        String sql = getSelectedSql();
//        sql += " where importHistoryId = ? ";
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationImportHistory.ImportHistoryId As ImportHistoryId  \r\n");
        sql.append("    , ObservationImportHistory.ExecutionDate As ExecutionDate  \r\n");
        sql.append("    , ObservationImportHistory.Status As Status  \r\n");
        sql.append("    , ObservationImportHistory.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , ObservationImportHistory.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , ObservationImportHistory.DataCooperationSystemId As DataCooperationSystemId  \r\n");
        sql.append("from ObservationImportHistory \r\n");
        sql.append("where importHistoryId = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, importHistoryId);
        
        ObservationImportHistoryEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationImportHistoryEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationImportHistoryEntity entity) throws Throwable {
    	logger.debug("Start");

        DataAccessObject dao = new DataAccessObject();
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationImportHistory \r\n");
        sql.append("(ImportHistoryId, DataCooperationSystemId, ExecutionDate, Status, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

	        if (entity.getImportHistoryId() == null) {
	            preparedStatement.setNull(1, Types.VARCHAR );
	        } else {
	            preparedStatement.setString(1, entity.getImportHistoryId());
	        }
	        if (entity.getDataCooperationSystemId() == null) {
	            preparedStatement.setNull(2, Types.VARCHAR );
	        } else {
	            preparedStatement.setString(2, entity.getDataCooperationSystemId());
	        }
            if (entity.getExecutionDate() == null) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getExecutionDate().toString());
            }
            if (entity.getStatus() == null) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getStatus());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();

        logger.debug("End");
        return rowCount;
    }

    /**
     * 検査結果取得履歴情報を更新
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(ObservationImportHistoryEntity entity) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject();

        StringBuilder sb = new StringBuilder();
        sb.append("update ObservationImportHistory set \r\n");
        sb.append("Status = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sb.append("where ImportHistoryId = ? \r\n");
        String sql = sb.toString();
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, entity.getStatus());
        preparedStatement.setString(2, entity.getImportHistoryId());

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();
        
        logger.debug("End");
        return rowCount;
    }    
}
