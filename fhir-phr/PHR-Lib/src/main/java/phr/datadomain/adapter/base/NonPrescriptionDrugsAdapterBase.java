/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：一般用医薬品のデータオブジェクト
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

import phr.datadomain.entity.NonPrescriptionDrugsEntity;

/**
 * 一般用医薬品のデータオブジェクトです。
 */
public abstract class NonPrescriptionDrugsAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NonPrescriptionDrugsAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public NonPrescriptionDrugsAdapterBase(Connection conn)
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
        sql.append("    NonPrescriptionDrugs.DosageId As DosageId  \r\n");
        sql.append("    , NonPrescriptionDrugs.Seq As Seq  \r\n");
        sql.append("    , NonPrescriptionDrugs.MedicineName As MedicineName  \r\n");
        sql.append("    , NonPrescriptionDrugs.StartDate As StartDate  \r\n");
        sql.append("    , NonPrescriptionDrugs.EndDate As EndDate  \r\n");
        sql.append("    , NonPrescriptionDrugs.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("from NonPrescriptionDrugs \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into NonPrescriptionDrugs \r\n");
        sql.append("(DosageId, Seq, MedicineName, StartDate, EndDate, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update NonPrescriptionDrugs set \r\n");
        sql.append("MedicineName = ?, StartDate = ?, EndDate = ?, RecordCreatorType = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  NonPrescriptionDrugs \r\n");
        sql.append("where DosageId = ? AND Seq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(NonPrescriptionDrugsEntity entity) throws Throwable  
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
            if (entity.getMedicineName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getMedicineName());
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
    public int update(NonPrescriptionDrugsEntity entity) throws Throwable 
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
            if (entity.getStartDate() == null ) {
                preparedStatement.setNull(2, Types.DATE );
            } else {
                preparedStatement.setDate(2, TypeUtility.convertDate(entity.getStartDate()));
            }
            if (entity.getEndDate() == null ) {
                preparedStatement.setNull(3, Types.DATE );
            } else {
                preparedStatement.setDate(3, TypeUtility.convertDate(entity.getEndDate()));
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getRecordCreatorType());
            }
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getDosageId());
            }
            preparedStatement.setInt(6, entity.getSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて一般用医薬品の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(NonPrescriptionDrugsEntity entity) throws Throwable 
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
     * 主キーにて一般用医薬品を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public NonPrescriptionDrugsEntity findByPrimaryKey(String key1, int key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where NonPrescriptionDrugs.DosageId = ?";
        sql += "   and NonPrescriptionDrugs.Seq = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);

        NonPrescriptionDrugsEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = NonPrescriptionDrugsEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
