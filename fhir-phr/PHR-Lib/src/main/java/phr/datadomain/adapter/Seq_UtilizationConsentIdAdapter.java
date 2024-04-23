/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果取得履歴 IDのデータオブジェクト
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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.Seq_UtilizationConsentIdEntity;

/**
 * 活用同意一覧 IDのデータオブジェクトです。
 */
@Repository
public class Seq_UtilizationConsentIdAdapter extends Seq_UtilizationConsentIdAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(Seq_UtilizationConsentIdAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public Seq_UtilizationConsentIdAdapter() {
    }
    public Seq_UtilizationConsentIdAdapter(Connection conn)
    {
        connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 活用同意一覧 IDを採番する
     * @return
     * @throws Throwable 
     */
    public static String numberingUtilizationConsentId() throws Throwable {
    	logger.debug("Start");
        
        String utilizationConsentId = "0000000";
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            String sql = "update Seq_UtilizationConsentId set UtilizationConsentId=LAST_INSERT_ID(UtilizationConsentId+1)";
            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            int rowCount = preparedStatement.executeUpdate();
            String sql2 = "SELECT LAST_INSERT_ID() as SeqId";
            dao.setSql(sql2);
            dao.clearBindParam();
            preparedStatement.close();
            
            PreparedStatement preparedStatement2 = dao.getPreparedStatement();
            ResultSet dataTable = preparedStatement2.executeQuery();
            if (dataTable == null) {
                return null;
            }

            while (dataTable.next()) {
                long id = AbstractEntity.getLong(dataTable, "SeqId");
                utilizationConsentId = String.format("%010d", id);
            }
            
            dao.clearBindParam();
            dataTable.close();
            preparedStatement2.close();
            dao.commitTransaction();
            return utilizationConsentId;
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error(ex.toString(), ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.debug("End");
        }
    }
    
    /**
     * utilizationConsentId=0の時のみSeq_UtilizationConsentIdテーブルに登録
     * @param entity
     * @return
     * @throws Throwable
     */
    public static int insertUtilizationConsentId(Seq_UtilizationConsentIdEntity e) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject();
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, String.valueOf(e.getUtilizationConsentId()));

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();
        
        logger.debug("End");
        return rowCount;
    } 
}
