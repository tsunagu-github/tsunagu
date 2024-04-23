/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：薬品服用注意情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.DosageMedicineAttentionEntity;


/**
 * 薬品服用注意情報のデータオブジェクトです。
 */
public class DosageMedicineAttentionAdapter extends DosageMedicineAttentionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicineAttentionAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageMedicineAttentionAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * doageIdの値にて薬品服用注意情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
//    public int deleteByDosageId(String dosageId,int seq) throws Throwable
    public int deleteByDosageId(String dosageId) throws Throwable
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sqlorg = getDeleteSql();
        int idx = sqlorg.indexOf("AND");
        String sql = sqlorg.substring(0, idx);
//        sql += " AND Seq = ? \r\n";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);
//        preparedStatement.setInt(2, seq);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * dosageIdにて薬品服用注意情報を検索します。
     * @param dosageId
     * @return
     * @throws Throwable
     */
    public List<DosageMedicineAttentionEntity> findByDosageId(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosegeMedicineAttention.DosageId = ?";
//        sql += " where DosageMedicineAttention.DosageId = ?";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);

        List<DosageMedicineAttentionEntity> entlist = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            DosageMedicineAttentionEntity entity = DosageMedicineAttentionEntity.setData(dataTable);
            entlist.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entlist;
    }

    /**
     * DosageIdでDosageMedicineAttentionテーブルからレコードを削除します。
     * @param dosageId
     * @return rowCount
     * @throws Throwable
     */
    public int deleteDosageMedicineAttentionRecord(String dosageId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  DosegeMedicineAttention \r\n");
        sb.append("where DosegeMedicineAttention.DosageId = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, dosageId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
