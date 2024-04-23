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

import phr.datadomain.entity.DosageMedicineEntity;

/**
 * 薬剤情報のデータオブジェクトです。
 */
public abstract class DosageMedicineAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicineAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageMedicineAdapterBase(Connection conn)
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
        sql.append("    DosegeMedicine.DosageId As DosageId  \r\n");
        sql.append("    , DosegeMedicine.Seq As Seq  \r\n");
        sql.append("    , DosegeMedicine.RecipeNo As RecipeNo  \r\n");
        sql.append("    , DosegeMedicine.MedicineSeq As MedicineSeq  \r\n");
        sql.append("    , DosegeMedicine.MedicineName As MedicineName  \r\n");
        sql.append("    , DosegeMedicine.Amount As Amount  \r\n");
        sql.append("    , DosegeMedicine.UnitName As UnitName  \r\n");
        sql.append("    , DosegeMedicine.MedicineCdType As MedicineCdType  \r\n");
        sql.append("    , DosegeMedicine.MedicineCd As MedicineCd  \r\n");
        sql.append("    , DosegeMedicine.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("from DosegeMedicine \r\n");
        return sql.toString();
        
//        StringBuilder sql = new StringBuilder();
//        sql.append("select  \r\n");
//        sql.append("    DosageMedicine.DosageId As DosageId  \r\n");
//        sql.append("    , DosageMedicine.Seq As Seq  \r\n");
//        sql.append("    , DosageMedicine.RecipeNo As RecipeNo  \r\n");
//        sql.append("    , DosageMedicine.MedicineSeq As MedicineSeq  \r\n");
//        sql.append("    , DosageMedicine.MedicineName As MedicineName  \r\n");
//        sql.append("    , DosageMedicine.Amount As Amount  \r\n");
//        sql.append("    , DosageMedicine.UnitName As UnitName  \r\n");
//        sql.append("    , DosageMedicine.MedicineCdType As MedicineCdType  \r\n");
//        sql.append("    , DosageMedicine.MedicineCd As MedicineCd  \r\n");
//        sql.append("    , DosageMedicine.RecordCreatorType As RecordCreatorType  \r\n");
//        sql.append("from DosageMedicine \r\n");
//        return sql.toString();

    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into DosegeMedicine \r\n");
        sql.append("(DosageId, Seq, RecipeNo, MedicineSeq, MedicineName, Amount, UnitName, MedicineCdType, MedicineCd, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

//    protected static String getInsertSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("insert into DosageMedicine \r\n");
//        sql.append("(DosageId, Seq, RecipeNo, MedicineSeq, MedicineName, Amount, UnitName, MedicineCdType, MedicineCd, RecordCreatorType) \r\n");
//        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \r\n");
//        return sql.toString();
//    }    
    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update DosegeMedicine set \r\n");
        sql.append("MedicineName = ?, Amount = ?, UnitName = ?, MedicineCdType = ?, MedicineCd = ?, RecordCreatorType = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? AND RecipeNo = ? AND MedicineSeq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

//    protected static String getUpdateSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("update DosageMedicine set \r\n");
//        sql.append("MedicineName = ?, Amount = ?, UnitName = ?, MedicineCdType = ?, MedicineCd = ?, RecordCreatorType = ? \r\n");
//        sql.append("where DosageId = ? AND Seq = ? AND RecipeNo = ? AND MedicineSeq = ? \r\n");
//        sql.append(" \r\n");
//        return sql.toString();
//    }    
    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  DosegeMedicine \r\n");
        sql.append("where DosageId = ? AND Seq = ? AND RecipeNo = ? AND MedicineSeq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

//    protected static String getDeleteSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("delete from  DosageMedicine \r\n");
//        sql.append("where DosageId = ? AND Seq = ? AND RecipeNo = ? AND MedicineSeq = ? \r\n");
//        sql.append(" \r\n");
//        return sql.toString();
//    }
    
    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(DosageMedicineEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getDosageId());
            }
            preparedStatement.setInt(2, entity.getSeq());
            preparedStatement.setInt(3, entity.getRecipeNo());
            preparedStatement.setInt(4, entity.getMedicineSeq());
            if (entity.getMedicineName() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getMedicineName());
            }
            if (entity.getAmount() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getAmount());
            }
            if (entity.getUnitName() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getUnitName());
            }
            if (entity.getMedicineCdType() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getMedicineCdType());
            }
            if (entity.getMedicineCd() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getMedicineCd());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getRecordCreatorType());
            }

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
    public int update(DosageMedicineEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getMedicineName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getMedicineName());
            }
            if (entity.getAmount() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getAmount());
            }
            if (entity.getUnitName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getUnitName());
            }
            if (entity.getMedicineCdType() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getMedicineCdType());
            }
            if (entity.getMedicineCd() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getMedicineCd());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getRecordCreatorType());
            }
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getDosageId());
            }
            preparedStatement.setInt(8, entity.getSeq());
            preparedStatement.setInt(9, entity.getRecipeNo());
            preparedStatement.setInt(10, entity.getMedicineSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて薬剤情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(DosageMedicineEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getDosageId());
            }
            preparedStatement.setInt(2, entity.getSeq());
            preparedStatement.setInt(3, entity.getRecipeNo());
            preparedStatement.setInt(4, entity.getMedicineSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて薬剤情報を検索します。
     * @param key1
     * @param key1
     * @param key1
     * @param key1
     * @return
     * @throws Throwable
     */
    public DosageMedicineEntity findByPrimaryKey(String key1, int key2, int key3, int key4) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosegeMedicine.DosageId = ?";
        sql += "   and DosegeMedicine.Seq = ?";
        sql += "   and DosegeMedicine.RecipeNo = ?";
        sql += "   and DosegeMedicine.MedicineSeq = ?";

//        sql += " where DosageMedicine.DosageId = ?";
//        sql += "   and DosageMedicine.Seq = ?";
//        sql += "   and DosageMedicine.RecipeNo = ?";
//        sql += "   and DosageMedicine.MedicineSeq = ?";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);
        preparedStatement.setInt(3, key3);
        preparedStatement.setInt(4, key4);

        DosageMedicineEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = DosageMedicineEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
