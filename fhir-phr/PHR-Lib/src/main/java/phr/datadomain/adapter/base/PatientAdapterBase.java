/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/19
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

import phr.datadomain.entity.PatientEntity;

/**
 * 患者情報のデータオブジェクトです。
 */
public abstract class PatientAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PatientAdapterBase(Connection conn)
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
        sql.append("    Patient.PHR_ID As PHR_ID  \r\n");
        sql.append("    , Patient.FamilyName As FamilyName  \r\n");
        sql.append("    , Patient.GivenName As GivenName  \r\n");
        sql.append("    , Patient.FamilyKana As FamilyKana  \r\n");
        sql.append("    , Patient.GivenKana As GivenKana  \r\n");
        sql.append("    , Patient.BirthDate As BirthDate  \r\n");
        sql.append("    , Patient.SexCd As SexCd  \r\n");
        sql.append("    , Patient.ZipCode As ZipCode  \r\n");
        sql.append("    , Patient.PrefectureCd As PrefectureCd  \r\n");
        sql.append("    , Patient.AddressLine As AddressLine  \r\n");
        sql.append("    , Patient.BuildingName As BuildingName  \r\n");
        sql.append("    , Patient.TelNo As TelNo  \r\n");
        sql.append("    , Patient.OtherContactNo As OtherContactNo  \r\n");
        sql.append("    , Patient.EmailAddress As EmailAddress  \r\n");
        sql.append("    , Patient.KyeId As KyeId  \r\n");
        sql.append("    , Patient.tokenId As tokenId  \r\n");
        sql.append("    , Patient.DiseaseManagement As DiseaseManagement  \r\n");
        sql.append("    , Patient.DynamicConsentFlg As DynamicConsentFlg  \r\n");
        sql.append("    , Patient.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , Patient.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from Patient \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Patient \r\n");
        sql.append("(PHR_ID, FamilyName, GivenName, FamilyKana, GivenKana, BirthDate, SexCd, ZipCode, PrefectureCd, AddressLine, BuildingName, TelNo, OtherContactNo, EmailAddress, KyeId, tokenId, DiseaseManagement, DynamicConsentFlg, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update Patient set \r\n");
        sql.append("FamilyName = ?, GivenName = ?, FamilyKana = ?, GivenKana = ?, BirthDate = ?, SexCd = ?, ZipCode = ?, PrefectureCd = ?, AddressLine = ?, BuildingName = ?, TelNo = ?, OtherContactNo = ?, EmailAddress = ?, KyeId = ?, tokenId = ?, DiseaseManagement = ?, DynamicConsentFlg = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where PHR_ID = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  Patient \r\n");
        sql.append("where PHR_ID = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(PatientEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getPHR_ID());
            }
            if (entity.getFamilyName() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getFamilyName());
            }
            if (entity.getGivenName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getGivenName());
            }
            if (entity.getFamilyKana() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getFamilyKana());
            }
            if (entity.getGivenKana() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getGivenKana());
            }
            if (entity.getBirthDate() == null ) {
                preparedStatement.setNull(6, Types.DATE );
            } else {
                preparedStatement.setDate(6, TypeUtility.convertDate(entity.getBirthDate()));
            }
            if (entity.getSexCd() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getSexCd());
            }
            if (entity.getZipCode() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getZipCode());
            }
            if (entity.getPrefectureCd() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getPrefectureCd());
            }
            if (entity.getAddressLine() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getAddressLine());
            }
            if (entity.getBuildingName() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getBuildingName());
            }
            if (entity.getTelNo() == null ) {
                preparedStatement.setNull(12, Types.VARCHAR );
            } else {
                preparedStatement.setString(12, entity.getTelNo());
            }
            if (entity.getOtherContactNo() == null ) {
                preparedStatement.setNull(13, Types.VARCHAR );
            } else {
                preparedStatement.setString(13, entity.getOtherContactNo());
            }
            if (entity.getEmailAddress() == null ) {
                preparedStatement.setNull(14, Types.VARCHAR );
            } else {
                preparedStatement.setString(14, entity.getEmailAddress());
            }
            if (entity.getKyeId() == null ) {
                preparedStatement.setNull(15, Types.VARCHAR );
            } else {
                preparedStatement.setString(15, entity.getKyeId());
            }
            if (entity.getTokenId() == null ) {
                preparedStatement.setNull(16, Types.VARCHAR );
            } else {
                preparedStatement.setString(16, entity.getTokenId());
            }
            preparedStatement.setBoolean(17, entity.isDiseaseManagement());
            preparedStatement.setBoolean(18, entity.isDynamicConsentFlg());

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
    public int update(PatientEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getFamilyName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getFamilyName());
            }
            if (entity.getGivenName() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getGivenName());
            }
            if (entity.getFamilyKana() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getFamilyKana());
            }
            if (entity.getGivenKana() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getGivenKana());
            }
            if (entity.getBirthDate() == null ) {
                preparedStatement.setNull(5, Types.DATE );
            } else {
                preparedStatement.setDate(5, TypeUtility.convertDate(entity.getBirthDate()));
            }
            if (entity.getSexCd() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getSexCd());
            }
            if (entity.getZipCode() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getZipCode());
            }
            if (entity.getPrefectureCd() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getPrefectureCd());
            }
            if (entity.getAddressLine() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getAddressLine());
            }
            if (entity.getBuildingName() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getBuildingName());
            }
            if (entity.getTelNo() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getTelNo());
            }
            if (entity.getOtherContactNo() == null ) {
                preparedStatement.setNull(12, Types.VARCHAR );
            } else {
                preparedStatement.setString(12, entity.getOtherContactNo());
            }
            if (entity.getEmailAddress() == null ) {
                preparedStatement.setNull(13, Types.VARCHAR );
            } else {
                preparedStatement.setString(13, entity.getEmailAddress());
            }
            if (entity.getKyeId() == null ) {
                preparedStatement.setNull(14, Types.VARCHAR );
            } else {
                preparedStatement.setString(14, entity.getKyeId());
            }
            if (entity.getTokenId() == null ) {
                preparedStatement.setNull(15, Types.VARCHAR );
            } else {
                preparedStatement.setString(15, entity.getTokenId());
            }
            preparedStatement.setBoolean(16, entity.isDiseaseManagement());
            preparedStatement.setBoolean(17,  entity.isDynamicConsentFlg());
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(18, Types.VARCHAR );
            } else {
                preparedStatement.setString(18, entity.getPHR_ID());
            }
            preparedStatement.setTimestamp(19, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて患者情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(PatientEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getPHR_ID());
            }
            preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて患者情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public PatientEntity findByPrimaryKey(String key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where Patient.PHR_ID = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        PatientEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = PatientEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
