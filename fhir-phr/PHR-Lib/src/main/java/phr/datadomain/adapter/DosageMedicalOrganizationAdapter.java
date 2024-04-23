/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関情報のデータオブジェクト
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
import phr.datadomain.entity.DosageMedicalOrganizationEntity;

/**
 * 医療機関情報のデータオブジェクトです。
 */
public class DosageMedicalOrganizationAdapter extends DosageMedicalOrganizationAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicalOrganizationAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageMedicalOrganizationAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * DosageIdの値にて医療機関情報の情報を削除します。
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
     * dosageIdにて医療機関情報を検索します。
     * @param dosageId
     * @return
     * @throws Throwable
     */
    public List<DosageMedicalOrganizationEntity> findByDosageId(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosageMedicalOrganization.DosageId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);

        List<DosageMedicalOrganizationEntity> entlist = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            DosageMedicalOrganizationEntity entity = DosageMedicalOrganizationEntity.setData(dataTable);
            entlist.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entlist;
    }    

    /**
     * DosageIdでDosageMedicalOrganizationテーブルからレコードを削除します。
     * @param dosageId
     * @return rowCount
     * @throws Throwable
     */
    public int deleteDosageMedicalOrganizationRecord(String dosageId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  DosageMedicalOrganization \r\n");
        sb.append("where DosageMedicalOrganization.DosageId = ? \r\n");
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
