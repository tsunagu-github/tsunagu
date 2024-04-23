/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
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

import phr.datadomain.entity.MedicalOrganizationEntity;

/**
 * 医療機関情報のデータオブジェクトです。
 */
public abstract class MedicalOrganizationAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalOrganizationAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public MedicalOrganizationAdapterBase(Connection conn)
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
        sql.append("    MedicalOrganization.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName  \r\n");
        sql.append("    , MedicalOrganization.ZipCode As ZipCode  \r\n");
        sql.append("    , MedicalOrganization.Address As Address  \r\n");
        sql.append("    , MedicalOrganization.TelNo As TelNo  \r\n");
        sql.append("    , MedicalOrganization.Password As Password  \r\n");
        sql.append("    , MedicalOrganization.InitPassword As InitPassword  \r\n");
        sql.append("    , MedicalOrganization.PasswordExpirationDate As PasswordExpirationDate  \r\n");
        sql.append("from MedicalOrganization \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into MedicalOrganization \r\n");
        sql.append("(MedicalOrganizationCd, MedicalOrganizationName, ZipCode, Address, TelNo, Password, InitPassword, PasswordExpirationDate) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update MedicalOrganization set \r\n");
        sql.append("MedicalOrganizationName = ?, ZipCode = ?, Address = ?, TelNo = ?, Password = ?, InitPassword = ?, PasswordExpirationDate = ? \r\n");
        sql.append("where MedicalOrganizationCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  MedicalOrganization \r\n");
        sql.append("where MedicalOrganizationCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(MedicalOrganizationEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getMedicalOrganizationCd());
            }
            if (entity.getMedicalOrganizationName() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getMedicalOrganizationName());
            }
            if (entity.getZipCode() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getZipCode());
            }
            if (entity.getAddress() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getAddress());
            }
            if (entity.getTelNo() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getTelNo());
            }
            if (entity.getPassword() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getPassword());
            }
            preparedStatement.setBoolean(7, entity.isInitPassword());
            if (entity.getPasswordExpirationDate() == null ) {
                preparedStatement.setNull(8, Types.DATE );
            } else {
                preparedStatement.setDate(8, TypeUtility.convertDate(entity.getPasswordExpirationDate()));
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
    public int update(MedicalOrganizationEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getMedicalOrganizationName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getMedicalOrganizationName());
            }
            if (entity.getZipCode() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getZipCode());
            }
            if (entity.getAddress() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getAddress());
            }
            if (entity.getTelNo() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getTelNo());
            }
            if (entity.getPassword() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getPassword());
            }
            preparedStatement.setBoolean(6, entity.isInitPassword());
            if (entity.getPasswordExpirationDate() == null ) {
                preparedStatement.setNull(7, Types.DATE );
            } else {
                preparedStatement.setDate(7, TypeUtility.convertDate(entity.getPasswordExpirationDate()));
            }
            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getMedicalOrganizationCd());
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
    public int delete(MedicalOrganizationEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getMedicalOrganizationCd());
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
     * @return
     * @throws Throwable
     */
    public MedicalOrganizationEntity findByPrimaryKey(String key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where MedicalOrganization.MedicalOrganizationCd = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        MedicalOrganizationEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = MedicalOrganizationEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
