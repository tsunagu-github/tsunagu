/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医師薬剤師情報のデータオブジェクト
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

import phr.datadomain.entity.DosageDoctorEntity;

/**
 * 医師薬剤師情報のデータオブジェクトです。
 */
public abstract class DosageDoctorAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageDoctorAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageDoctorAdapterBase(Connection conn)
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
        sql.append("    DosageDoctor.DosageId As DosageId  \r\n");
        sql.append("    , DosageDoctor.Seq As Seq  \r\n");
        sql.append("    , DosageDoctor.DosageTypeCd As DosageTypeCd  \r\n");
        sql.append("    , DosageDoctor.DoctorName As DoctorName  \r\n");
        sql.append("    , DosageDoctor.ContactAddress As ContactAddress  \r\n");
        sql.append("    , DosageDoctor.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("    , DosageDoctor.DepartmentName As DepartmentName  \r\n");
        sql.append("from DosageDoctor \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into DosageDoctor \r\n");
        sql.append("(DosageId, Seq, DosageTypeCd, DoctorName, ContactAddress, RecordCreatorType, DepartmentName) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update DosageDoctor set \r\n");
        sql.append(" DoctorName = ?, ContactAddress = ?, RecordCreatorType = ?, DepartmentName = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? And DosageTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  DosageDoctor \r\n");
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
    public int insert(DosageDoctorEntity entity) throws Throwable  
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
            if (entity.getDosageTypeCd() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getDosageTypeCd());
            }
            if (entity.getDoctorName() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getDoctorName());
            }
            if (entity.getContactAddress() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getContactAddress());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getRecordCreatorType());
            }
            if (entity.getDepartmentName() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getDepartmentName());
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
    public int update(DosageDoctorEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getDoctorName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getDoctorName());
            }
            if (entity.getContactAddress() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getContactAddress());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getRecordCreatorType());
            }
            if (entity.getDepartmentName() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getDepartmentName());
            }
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getDosageId());
            }
            preparedStatement.setInt(6, entity.getSeq());
            preparedStatement.setString(7, entity.getDosageTypeCd());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて医師薬剤師情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(DosageDoctorEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        sql += "   and DosageDoctor.DosageTypeCd = ?";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getDosageId());
            }
            preparedStatement.setInt(2, entity.getSeq());
            preparedStatement.setString(3, entity.getDosageTypeCd());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて医師薬剤師情報を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public DosageDoctorEntity findByPrimaryKey(String key1, int key2,String key3) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosageDoctor.DosageId = ?";
        sql += "   and DosageDoctor.Seq = ?";
        sql += "   and DosageDoctor.DosageTypeCd = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);
        preparedStatement.setString(3, key3);

        DosageDoctorEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = DosageDoctorEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
