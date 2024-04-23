/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関患者関連情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;

/**
 * 医療機関患者関連情報のデータオブジェクトです。
 */
public class MedicalOrganizationPatientAdapter extends MedicalOrganizationPatientAdapterBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(MedicalOrganizationPatientAdapter.class);
    private static Logger logger = Logger.getLogger(MedicalOrganizationPatientAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public MedicalOrganizationPatientAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 患者コードと病院コードから医療機関関連情報の取得
     * @param patientId
     * @param MedicalCd
     * @return
     * @throws SQLException
     * @throws Throwable 
     */
    public MedicalOrganizationPatientEntity findByPatientIdAndMedicalCd(String patientId , String MedicalCd) throws SQLException, Throwable         
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    MedicalOrganizationPatient.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , MedicalOrganizationPatient.PHR_ID As PHR_ID  \r\n");
        sql.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sql.append("    , MedicalOrganizationPatient.AgreesToShare As AgreesToShare  \r\n");
        sql.append("from MedicalOrganizationPatient \r\n");
        sql.append(" where MedicalOrganizationPatient.MedicalOrganizationCd = ? \r\n");
        sql.append("   and MedicalOrganizationPatient.PatientId = ? \r\n");
         dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, MedicalCd);
        preparedStatement.setString(2, patientId);
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
 
    /**
     * 患者コードと病院コードから医療機関関連情報の取得
     * @param patientId
     * @param MedicalCd
     * @return
     * @throws SQLException
     * @throws Throwable 
     */
    public List<MedicalOrganizationPatientEntity> findByPatientIdAndMedicalCdAll(String patientId , String MedicalCd) throws SQLException, Throwable         
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    MedicalOrganizationPatient.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , MedicalOrganizationPatient.PHR_ID As PHR_ID  \r\n");
        sql.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sql.append("    , MedicalOrganizationPatient.AgreesToShare As AgreesToShare  \r\n");
        sql.append("from MedicalOrganizationPatient \r\n");
        sql.append(" where MedicalOrganizationPatient.MedicalOrganizationCd = ? \r\n");
        sql.append("   and MedicalOrganizationPatient.PatientId = ? \r\n");
         dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, MedicalCd);
        preparedStatement.setString(2, patientId);
       List<MedicalOrganizationPatientEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            MedicalOrganizationPatientEntity entity = MedicalOrganizationPatientEntity.setData(dataTable);
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }   
    /**
     * PHR_IDにて保険者患者関連情報を検索します。
     * @param phrId
     * @return
     * @throws Throwable
     */
    public List<MedicalOrganizationPatientEntity> selectByPhrId(String phrId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    MedicalOrganizationPatient.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , MedicalOrganizationPatient.PHR_ID As PHR_ID  \r\n");
        sql.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sql.append("    , MedicalOrganizationPatient.AgreesToShare As AgreesToShare  \r\n");
        sql.append("from MedicalOrganizationPatient \r\n");
        sql.append(" where MedicalOrganizationPatient.PHR_ID = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);
        
//       MedicalOrganizationPatientEntity entity = null;
        List<MedicalOrganizationPatientEntity> entity = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        
        
        while( dataTable.next() ) {
            entity.add( MedicalOrganizationPatientEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
    /* -------------------------------------------------------------------------------------- */

    /*
    * phrIDから関連付けられている医療機関のリストを取得する。
    */
    public List<MedicalOrganizationPatientEntity> getMedicalList(String phrId) throws SQLException, Throwable{
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    MedicalOrganizationPatient.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , MedicalOrganizationPatient.PHR_ID As PHR_ID  \r\n");
        sql.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sql.append("    , MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName  \r\n");
        sql.append("from MedicalOrganizationPatient  \r\n");
        sql.append("inner join MedicalOrganization on  \r\n");
        sql.append("	MedicalOrganization.MedicalOrganizationCd = MedicalOrganizationPatient.MedicalOrganizationCd  \r\n");
        sql.append("where MedicalOrganizationPatient.PHR_ID = ?  \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);
        
       MedicalOrganizationPatientEntity entity = null;
       List<MedicalOrganizationPatientEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = MedicalOrganizationPatientEntity.setData(dataTable);
            entity.setMedicalOrganizationName(AbstractEntity.getString(dataTable, "MedicalOrganizationName"));

            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;

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
        StringBuilder sql = new StringBuilder();
        sql.append("insert into MedicalOrganizationPatient \r\n");
        sql.append("(MedicalOrganizationCd, PHR_ID, PatientId, AgreesToShare) \r\n");
        sql.append("values (?, ?, ?, ?) \r\n");
        dao.setSql(sql.toString());

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
            preparedStatement.setBoolean(4, entity.getAgreesToShare());

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
        StringBuilder sql = new StringBuilder();
        sql.append("update MedicalOrganizationPatient set \r\n");
        sql.append("PatientId = ?, AgreesToShare = ? \r\n");
        sql.append("where MedicalOrganizationCd = ? AND PHR_ID = ? \r\n");
        sql.append(" \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getPatientId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getPatientId());
            }
            if (entity.getAgreesToShare()) {
                preparedStatement.setString(2, "1");
            } else {
            	preparedStatement.setString(2, "0");
            }
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
     * PHR_IDでMedicalOrganizationPatientテーブルからレコードを削除します。
     * @param phr_id
     * @return rowCount
     * @throws Throwable
     */
    public int deleteMedicalOrganizationPatientRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  MedicalOrganizationPatient \r\n");
        sb.append("where MedicalOrganizationPatient.PHR_ID = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phr_id);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
