/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関患者関連情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/10/06
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
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import java.sql.PreparedStatement;
import java.sql.Types;
import phr.datadomain.DataAccessObject;
import phr.utility.TypeUtility;

import phr.datadomain.entity.MedicalOrganizationPatientEntity;

/**
 * 医療機関患者関連情報のデータオブジェクトです。
 */
public abstract class MedicalOrganizationPatientAdapterBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(MedicalOrganizationPatientAdapterBase.class);
    private static Logger logger = Logger.getLogger(MedicalOrganizationPatientAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public MedicalOrganizationPatientAdapterBase(Connection conn)
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
        sql.append("    MedicalOrganizationPatient.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , MedicalOrganizationPatient.PHR_ID As PHR_ID  \r\n");
        sql.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sql.append("    , MedicalOrganizationPatient.AgreesToShare As AgreesToShare  \r\n");
        sql.append("from MedicalOrganizationPatient \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into MedicalOrganizationPatient \r\n");
        sql.append("(MedicalOrganizationCd, PHR_ID, PatientId, AgreesToShare) \r\n");
        sql.append("values (?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update MedicalOrganizationPatient set \r\n");
        sql.append("PatientId = ?, AgreesToShare = ? \r\n");
        sql.append("where MedicalOrganizationCd = ? AND PHR_ID = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  MedicalOrganizationPatient \r\n");
        sql.append("where MedicalOrganizationCd = ? AND PHR_ID = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(MedicalOrganizationPatientEntity entity) throws Throwable  
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
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPHR_ID());
            }
            if (entity.getPatientId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getPatientId());
            }
            preparedStatement.setBoolean(4, entity.isAgreesToShare());

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
    public int update(MedicalOrganizationPatientEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getPatientId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getPatientId());
            }
            preparedStatement.setBoolean(2, entity.isAgreesToShare());
            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getMedicalOrganizationCd());
            }
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getPHR_ID());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて医療機関患者関連情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(MedicalOrganizationPatientEntity entity) throws Throwable 
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
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPHR_ID());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて医療機関患者関連情報を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public MedicalOrganizationPatientEntity findByPrimaryKey(String key1, String key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where MedicalOrganizationPatient.MedicalOrganizationCd = ?";
        sql += "   and MedicalOrganizationPatient.PHR_ID = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);

        MedicalOrganizationPatientEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = MedicalOrganizationPatientEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
