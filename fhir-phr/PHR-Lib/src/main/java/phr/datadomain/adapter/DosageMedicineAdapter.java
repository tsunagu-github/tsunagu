/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：薬剤情報のデータオブジェクト
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
import phr.datadomain.entity.DosageMedicineEntity;

/**
 * 薬剤情報のデータオブジェクトです。
 */
public class DosageMedicineAdapter extends DosageMedicineAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicineAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageMedicineAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     *指定した調剤IDの調剤薬リストを取得します。
     * @param dosageid
     * @param seq
     * @param recipeNo
     * @return List<NonPrescriptionDrugsEntity>
     * @throws Throwable
     */
    public List<DosageMedicineEntity> findByDosageid(String dosageid, int Seq,int recipeNo) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "where DosegeMedicine.DosageId = ? \r\n";
        sql += " and DosegeMedicine.Seq = ?\r\n";
        sql += " and DosegeMedicine.RecipeNo = ?\r\n";
 
//        sql += "where DosageMedicine.DosageId = ? \r\n";
//        sql += " and DosageMedicine.Seq = ?\r\n";
//        sql += " and DosageMedicine.RecipeNo = ?\r\n";
 
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageid);
        preparedStatement.setInt(2, Seq);
        preparedStatement.setInt(3, recipeNo);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        
        List<DosageMedicineEntity> entityList = new ArrayList();
        while( dataTable.next() ) {
           DosageMedicineEntity entity = DosageMedicineEntity.setData(dataTable);
           entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }

    /**
     * DosageIdの値にて薬剤情報の情報を削除します。
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
     * 対象のRecipeNoに対応する薬剤が何件登録されているか、件数を返します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int getCountByRecipeNo(String dosageId,int seq,int recipeNo) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "select count(DosegeMedicine.MedicineSeq) as count from DosegeMedicine where DosegeMedicine.DosageId = ? AND DosegeMedicine.Seq = ? AND DosegeMedicine.RecipeNo = ? \r\n";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);
        preparedStatement.setInt(2, seq);
        preparedStatement.setInt(3, recipeNo);
        
        int count = 0;
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return -1;
                }
                while( dataTable.next() ) {
                    count = AbstractEntity.getInt(dataTable, "count");
                }
            }
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return count;
    }       

    /**
     *dosageIDにて調剤薬リストを取得します。
     * @param dosageid
     * @return List<NonPrescriptionDrugsEntity>
     * @throws Throwable
     */
    public List<DosageMedicineEntity> findByDosageIdOnly(String dosageid) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "where DosegeMedicine.DosageId = ? \r\n";
 //        sql += "where DosageMedicine.DosageId = ? \r\n";
 
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageid);
 
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        
        List<DosageMedicineEntity> entityList = new ArrayList();
        while( dataTable.next() ) {
           DosageMedicineEntity entity = DosageMedicineEntity.setData(dataTable);
           entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }

    /**
     * DosageIdでDosageMedicineテーブルからレコードを削除します。
     * @param dosageId
     * @return rowCount
     * @throws Throwable
     */
    public int deleteDosageMedicineRecord(String dosageId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  DosegeMedicine \r\n");
        sb.append("where DosegeMedicine.DosageId = ? \r\n");
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
