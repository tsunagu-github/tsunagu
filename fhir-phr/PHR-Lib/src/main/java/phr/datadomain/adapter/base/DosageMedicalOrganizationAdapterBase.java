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

import phr.datadomain.entity.DosageMedicalOrganizationEntity;

/**
 * 医療機関情報のデータオブジェクトです。
 */
public abstract class DosageMedicalOrganizationAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicalOrganizationAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageMedicalOrganizationAdapterBase(Connection conn)
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
        sql.append("    DosageMedicalOrganization.DosageId As DosageId  \r\n");
        sql.append("    , DosageMedicalOrganization.Seq As Seq  \r\n");
        sql.append("    , DosageMedicalOrganization.DosageTypeCd As DosageTypeCd  \r\n");
        sql.append("    , DosageMedicalOrganization.OrganizationType As OrganizationType  \r\n");
        sql.append("    , DosageMedicalOrganization.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , DosageMedicalOrganization.MedicalOrganizationName As MedicalOrganizationName  \r\n");
        sql.append("    , DosageMedicalOrganization.PrefectureCd As PrefectureCd  \r\n");
        sql.append("    , DosageMedicalOrganization.ZipCode As ZipCode  \r\n");
        sql.append("    , DosageMedicalOrganization.AddressLine As AddressLine  \r\n");
        sql.append("    , DosageMedicalOrganization.TelNo As TelNo  \r\n");
        sql.append("    , DosageMedicalOrganization.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("from DosageMedicalOrganization \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into DosageMedicalOrganization \r\n");
        sql.append("(DosageId, Seq, DosageTypeCd, OrganizationType, MedicalOrganizationCd, MedicalOrganizationName, PrefectureCd, ZipCode, AddressLine, TelNo, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update DosageMedicalOrganization set \r\n");
        sql.append("OrganizationType = ?, MedicalOrganizationCd = ?, MedicalOrganizationName = ?, PrefectureCd = ?, ZipCode = ?, AddressLine = ?, TelNo = ?, RecordCreatorType = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? AND DosageTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  DosageMedicalOrganization \r\n");
        sql.append("where DosageId = ? AND Seq = ? AND DosageTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(DosageMedicalOrganizationEntity entity) throws Throwable  
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
            if (entity.getOrganizationType() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getOrganizationType());
            }
            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getMedicalOrganizationCd());
            }
            if (entity.getMedicalOrganizationName() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getMedicalOrganizationName());
            }
            if (entity.getPrefectureCd() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getPrefectureCd());
            }
            if (entity.getZipCode() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getZipCode());
            }
            if (entity.getAddressLine() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getAddressLine());
            }
            if (entity.getTelNo() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getTelNo());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getRecordCreatorType());
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
    public int update(DosageMedicalOrganizationEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getOrganizationType() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getOrganizationType());
            }
            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getMedicalOrganizationCd());
            }
            if (entity.getMedicalOrganizationName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getMedicalOrganizationName());
            }
            if (entity.getPrefectureCd() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getPrefectureCd());
            }
            if (entity.getZipCode() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getZipCode());
            }
            if (entity.getAddressLine() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getAddressLine());
            }
            if (entity.getTelNo() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getTelNo());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getRecordCreatorType());
            }
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getDosageId());
            }
            preparedStatement.setInt(10, entity.getSeq());
            if (entity.getDosageTypeCd() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getDosageTypeCd());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて医療機関情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(DosageMedicalOrganizationEntity entity) throws Throwable 
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
            if (entity.getDosageTypeCd() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getDosageTypeCd());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて医療機関情報を検索します。
     * @param key1
     * @param key1
     * @param key1
     * @return
     * @throws Throwable
     */
    public DosageMedicalOrganizationEntity findByPrimaryKey(String key1, int key2, String key3) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosageMedicalOrganization.DosageId = ?";
        sql += "   and DosageMedicalOrganization.Seq = ?";
        sql += "   and DosageMedicalOrganization.DosageTypeCd = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);
        preparedStatement.setString(3, key3);

        DosageMedicalOrganizationEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = DosageMedicalOrganizationEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
