/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：薬剤補足情報のデータオブジェクト
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

import phr.datadomain.entity.DosageMedicineAdditionEntity;

/**
 * 薬剤補足情報のデータオブジェクトです。
 */
public abstract class DosageMedicineAdditionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicineAdditionAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageMedicineAdditionAdapterBase(Connection conn)
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
        sql.append("    DosegeMedicineAddition.DosageId As DosageId  \r\n");
        sql.append("    , DosegeMedicineAddition.Seq As Seq  \r\n");
        sql.append("    , DosegeMedicineAddition.RecipeNo As RecipeNo  \r\n");
        sql.append("    , DosegeMedicineAddition.MedicineSeq As MedicineSeq  \r\n");
        sql.append("    , DosegeMedicineAddition.AdditionSeq As AdditionSeq  \r\n");
        sql.append("    , DosegeMedicineAddition.AdditionText As AdditionText  \r\n");
        sql.append("    , DosegeMedicineAddition.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("from DosegeMedicineAddition \r\n");
        return sql.toString();
    }

//    protected static String getSelectedSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("select  \r\n");
//        sql.append("    DosageMedicineAddition.DosageId As DosageId  \r\n");
//        sql.append("    , DosageMedicineAddition.Seq As Seq  \r\n");
//        sql.append("    , DosageMedicineAddition.RecipeNo As RecipeNo  \r\n");
//        sql.append("    , DosageMedicineAddition.MedicineSeq As MedicineSeq  \r\n");
//        sql.append("    , DosageMedicineAddition.AdditionSeq As AdditionSeq  \r\n");
//        sql.append("    , DosageMedicineAddition.AdditionText As AdditionText  \r\n");
//        sql.append("    , DosageMedicineAddition.RecordCreatorType As RecordCreatorType  \r\n");
//        sql.append("from DosageMedicineAddition \r\n");
//        return sql.toString();
//    }    
    
    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into DosegeMedicineAddition \r\n");
        sql.append("(DosageId, Seq, RecipeNo, MedicineSeq, AdditionSeq, AdditionText, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

//    protected static String getInsertSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("insert into DosageMedicineAddition \r\n");
//        sql.append("(DosageId, Seq, RecipeNo, MedicineSeq, AdditionSeq, AdditionText, RecordCreatorType) \r\n");
//        sql.append("values (?, ?, ?, ?, ?, ?, ?) \r\n");
//        return sql.toString();
//    }    
    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update DosegeMedicineAddition set \r\n");
        sql.append("AdditionText = ?, RecordCreatorType = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? AND RecipeNo = ? AND MedicineSeq = ? AND AdditionSeq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

//    protected static String getUpdateSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("update DosageMedicineAddition set \r\n");
//        sql.append("AdditionText = ?, RecordCreatorType = ? \r\n");
//        sql.append("where DosageId = ? AND Seq = ? AND RecipeNo = ? AND MedicineSeq = ? AND AdditionSeq = ? \r\n");
//        sql.append(" \r\n");
//        return sql.toString();
//    }    
    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  DosegeMedicineAddition \r\n");
        sql.append("where DosageId = ? AND Seq = ? AND RecipeNo = ? AND MedicineSeq = ? AND AdditionSeq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

//    protected static String getDeleteSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("delete from  DosageMedicineAddition \r\n");
//        sql.append("where DosageId = ? AND Seq = ? AND RecipeNo = ? AND MedicineSeq = ? AND AdditionSeq = ? \r\n");
//        sql.append(" \r\n");
//        return sql.toString();
//    }
    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(DosageMedicineAdditionEntity entity) throws Throwable  
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
            preparedStatement.setInt(5, entity.getAdditionSeq());
            if (entity.getAdditionText() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getAdditionText());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getRecordCreatorType());
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
    public int update(DosageMedicineAdditionEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getAdditionText() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getAdditionText());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getRecordCreatorType());
            }
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getDosageId());
            }
            preparedStatement.setInt(4, entity.getSeq());
            preparedStatement.setInt(5, entity.getRecipeNo());
            preparedStatement.setInt(6, entity.getMedicineSeq());
            preparedStatement.setInt(7, entity.getAdditionSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて薬剤補足情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(DosageMedicineAdditionEntity entity) throws Throwable 
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
            preparedStatement.setInt(5, entity.getAdditionSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて薬剤補足情報を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @param key4
     * @param key5
     * @return
     * @throws Throwable
     */
    public DosageMedicineAdditionEntity findByPrimaryKey(String key1, int key2, int key3, int key4, int key5) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosegeMedicineAddition.DosageId = ?";
        sql += "   and DosegeMedicineAddition.Seq = ?";
        sql += "   and DosegeMedicineAddition.RecipeNo = ?";
        sql += "   and DosegeMedicineAddition.MedicineSeq = ?";
        sql += "   and DosegeMedicineAddition.AdditionSeq = ?";

//        sql += " where DosageMedicineAddition.DosageId = ?";
//        sql += "   and DosageMedicineAddition.Seq = ?";
//        sql += "   and DosageMedicineAddition.RecipeNo = ?";
//        sql += "   and DosageMedicineAddition.MedicineSeq = ?";
//        sql += "   and DosageMedicineAddition.AdditionSeq = ?";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);
        preparedStatement.setInt(3, key3);
        preparedStatement.setInt(4, key4);
        preparedStatement.setInt(5, key5);

        DosageMedicineAdditionEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = DosageMedicineAdditionEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
