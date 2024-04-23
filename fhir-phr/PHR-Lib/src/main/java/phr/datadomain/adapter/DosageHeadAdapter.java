/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：調剤ヘッダ情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationDefinitionEntity;

/**
 * 調剤ヘッダ情報のデータオブジェクトです。
 */
public class DosageHeadAdapter extends DosageHeadAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageHeadAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public DosageHeadAdapter(Connection conn) {
        super(conn);
    }

    /* -------------------------------------------------------------------------------------- */
    /**
     * 調剤IDを採番する
     *
     * @return
     * @throws Throwable
     */
    public static String numberingDosageId() throws Throwable {
        String dosageId = "0000000000";
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            String sql = "update Seq_DosageId set DosageId=LAST_INSERT_ID(DosageId+1)";
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
                dosageId = String.format("%010d", id);
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement2.close();
            dao.commitTransaction();
            return dosageId;
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
     * DosageIdにて調剤ヘッダ情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deletebyDosageId(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.trace("End");
        return rowCount;
    }    
    
    
}
