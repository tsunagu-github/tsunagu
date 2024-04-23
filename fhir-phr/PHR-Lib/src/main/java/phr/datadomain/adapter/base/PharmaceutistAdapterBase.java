/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：かかりつけ薬剤師情報のデータオブジェクト
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

import phr.datadomain.entity.PharmaceutistEntity;

/**
 * かかりつけ薬剤師情報のデータオブジェクトです。
 */
public abstract class PharmaceutistAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PharmaceutistAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PharmaceutistAdapterBase(Connection conn)
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
        sql.append("    Parmaceutist.DosageId As DosageId  \r\n");
        sql.append("    , Parmaceutist.Seq As Seq  \r\n");
        sql.append("    , Parmaceutist.ParmaceutistName As PharmaceutistName  \r\n");
        sql.append("    , Parmaceutist.Pharmacy As Pharmacy  \r\n");
        sql.append("    , Parmaceutist.ContactAddress As ContactAddress  \r\n");
        sql.append("    , Parmaceutist.StartDate As StartDate  \r\n");
        sql.append("    , Parmaceutist.EndDate As EndDate  \r\n");
        sql.append("    , Parmaceutist.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("from Parmaceutist \r\n");
        return sql.toString();
    }

//    protected static String getSelectedSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("select  \r\n");
//        sql.append("    pharmaceutist.DosageId As DosageId  \r\n");
//        sql.append("    , pharmaceutist.Seq As Seq  \r\n");
//        sql.append("    , pharmaceutist.PharmaceutistName As PharmaceutistName  \r\n");
//        sql.append("    , pharmaceutist.Pharmacｙ As Pharmacｙ  \r\n");
//        sql.append("    , pharmaceutist.ContactAddress As ContactAddress  \r\n");
//        sql.append("    , pharmaceutist.StartDate As StartDate  \r\n");
//        sql.append("    , pharmaceutist.EndDate As EndDate  \r\n");
//        sql.append("    , pharmaceutist.RecordCreatorType As RecordCreatorType  \r\n");
//        sql.append("from pharmaceutist \r\n");
//        return sql.toString();
//    }
    
    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Parmaceutist \r\n");
        sql.append("(DosageId, Seq, ParmaceutistName, Pharmacy, ContactAddress, StartDate, EndDate, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

//    protected static String getInsertSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("insert into pharmaceutist \r\n");
//        sql.append("(DosageId, Seq, PharmaceutistName, Pharmacｙ, ContactAddress, StartDate, EndDate, RecordCreatorType) \r\n");
//        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?) \r\n");
//        return sql.toString();
//    }
    
    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update Parmaceutist set \r\n");
        sql.append("ParmaceutistName = ?, Pharmacy = ?, ContactAddress = ?, StartDate = ?, EndDate = ?, RecordCreatorType = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

//    protected static String getUpdateSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("update pharmaceutist set \r\n");
//        sql.append("PharmaceutistName = ?, Pharmacｙ = ?, ContactAddress = ?, StartDate = ?, EndDate = ?, RecordCreatorType = ? \r\n");
//        sql.append("where DosageId = ? AND Seq = ? \r\n");
//        sql.append(" \r\n");
//        return sql.toString();
//    }
//    
    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  Parmaceutist \r\n");
        sql.append("where DosageId = ? AND Seq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

//    protected static String getDeleteSql() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("delete from  pharmaceutist \r\n");
//        sql.append("where DosageId = ? AND Seq = ? \r\n");
//        sql.append(" \r\n");
//        return sql.toString();
//    }

    
    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(PharmaceutistEntity entity) throws Throwable  
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
            if (entity.getPharmaceutistName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getPharmaceutistName());
            }
            if (entity.getPharmacy() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getPharmacy());
            }
            if (entity.getContactAddress() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getContactAddress());
            }
            if (entity.getStartDate() == null ) {
                preparedStatement.setNull(6, Types.DATE );
            } else {
                preparedStatement.setDate(6, TypeUtility.convertDate(entity.getStartDate()));
            }
            if (entity.getEndDate() == null ) {
                preparedStatement.setNull(7, Types.DATE );
            } else {
                preparedStatement.setDate(7, TypeUtility.convertDate(entity.getEndDate()));
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getRecordCreatorType());
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
    public int update(PharmaceutistEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getPharmaceutistName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getPharmaceutistName());
            }
            if (entity.getPharmacy() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPharmacy());
            }
            if (entity.getContactAddress() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getContactAddress());
            }
            if (entity.getStartDate() == null ) {
                preparedStatement.setNull(4, Types.DATE );
            } else {
                preparedStatement.setDate(4, TypeUtility.convertDate(entity.getStartDate()));
            }
            if (entity.getEndDate() == null ) {
                preparedStatement.setNull(5, Types.DATE );
            } else {
                preparedStatement.setDate(5, TypeUtility.convertDate(entity.getEndDate()));
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

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてかかりつけ薬剤師情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(PharmaceutistEntity entity) throws Throwable 
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

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてかかりつけ薬剤師情報を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public PharmaceutistEntity findByPrimaryKey(String key1, int key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where Parmaceutist.DosageId = ?";
        sql += "   and Parmaceutist.Seq = ?";

//        sql += " where pharmaceutist.DosageId = ?";
//        sql += "   and pharmaceutist.Seq = ?";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);

        PharmaceutistEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = PharmaceutistEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
