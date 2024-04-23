/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：調剤ヘッダ情報のデータオブジェクト
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

import phr.datadomain.entity.DosageHeadEntity;

/**
 * 調剤ヘッダ情報のデータオブジェクトです。
 */
public abstract class DosageHeadAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageHeadAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageHeadAdapterBase(Connection conn)
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
        sql.append("    DosageHead.DosageId As DosageId  \r\n");
        sql.append("    , DosageHead.QRVersion As QRVersion  \r\n");
        sql.append("    , DosageHead.PatientName As PatientName  \r\n");
        sql.append("    , DosageHead.SexCd As SexCd  \r\n");
        sql.append("    , DosageHead.BirthDate As BirthDate  \r\n");
        sql.append("    , DosageHead.ZipCode As ZipCode  \r\n");
        sql.append("    , DosageHead.AddressLine As AddressLine  \r\n");
        sql.append("    , DosageHead.TelNo As TelNo  \r\n");
        sql.append("    , DosageHead.EmergencyContact As EmergencyContact  \r\n");
        sql.append("    , DosageHead.BloodType As BloodType  \r\n");
        sql.append("    , DosageHead.Weight As Weight  \r\n");
        sql.append("    , DosageHead.NameInKana As NameInKana  \r\n");
        sql.append("from DosageHead \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into DosageHead \r\n");
        sql.append("(DosageId, QRVersion, PatientName, SexCd, BirthDate, ZipCode, AddressLine, TelNo, EmergencyContact, BloodType, Weight, NameInKana) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update DosageHead set \r\n");
        sql.append("QRVersion = ?, PatientName = ?, SexCd = ?, BirthDate = ?, ZipCode = ?, AddressLine = ?, TelNo = ?, EmergencyContact = ?, BloodType = ?, Weight = ?, NameInKana = ? \r\n");
        sql.append("where DosageId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  DosageHead \r\n");
        sql.append("where DosageId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(DosageHeadEntity entity) throws Throwable  
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
            if (entity.getQRVersion() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getQRVersion());
            }
            if (entity.getPatientName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getPatientName());
            }
            if (entity.getSexCd() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getSexCd());
            }
            if (entity.getBirthDate() == null ) {
                preparedStatement.setNull(5, Types.DATE );
            } else {
                preparedStatement.setDate(5, TypeUtility.convertDate(entity.getBirthDate()));
            }
            if (entity.getZipCode() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getZipCode());
            }
            if (entity.getAddressLine() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getAddressLine());
            }
            if (entity.getTelNo() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getTelNo());
            }
            if (entity.getEmergencyContact() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getEmergencyContact());
            }
            if (entity.getBloodType() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getBloodType());
            }
            if (entity.getWeight() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getWeight());
            }
            if (entity.getNameInKana() == null ) {
                preparedStatement.setNull(12, Types.VARCHAR );
            } else {
                preparedStatement.setString(12, entity.getNameInKana());
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
    public int update(DosageHeadEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getQRVersion() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getQRVersion());
            }
            if (entity.getPatientName() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPatientName());
            }
            if (entity.getSexCd() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getSexCd());
            }
            if (entity.getBirthDate() == null ) {
                preparedStatement.setNull(4, Types.DATE );
            } else {
                preparedStatement.setDate(4, TypeUtility.convertDate(entity.getBirthDate()));
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
            if (entity.getEmergencyContact() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getEmergencyContact());
            }
            if (entity.getBloodType() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getBloodType());
            }
            if (entity.getWeight() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getWeight());
            }
            if (entity.getNameInKana() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getNameInKana());
            }
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(12, Types.VARCHAR );
            } else {
                preparedStatement.setString(12, entity.getDosageId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて調剤ヘッダ情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(DosageHeadEntity entity) throws Throwable 
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

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて調剤ヘッダ情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public DosageHeadEntity findByPrimaryKey(String key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosageHead.DosageId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        DosageHeadEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = DosageHeadEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
