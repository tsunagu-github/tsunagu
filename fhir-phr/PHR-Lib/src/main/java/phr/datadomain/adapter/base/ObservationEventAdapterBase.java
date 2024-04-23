/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果情報のデータオブジェクト
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

import phr.datadomain.entity.ObservationEventEntity;

/**
 * 検査結果情報のデータオブジェクトです。
 */
public abstract class ObservationEventAdapterBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationEventAdapterBase.class);
    private static Logger logger = Logger.getLogger(ObservationEventAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationEventAdapterBase(Connection conn)
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
        sql.append("    ObservationEvent.ObservationEventId As ObservationEventId  \r\n");
        sql.append("    , ObservationEvent.DataInputTypeCd As DataInputTypeCd  \r\n");
        sql.append("    , ObservationEvent.PHR_ID As PHR_ID  \r\n");
        sql.append("    , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        sql.append("    , ObservationEvent.Year As Year  \r\n");
        sql.append("    , ObservationEvent.InsurerNo As InsurerNo  \r\n");
        sql.append("    , ObservationEvent.LaboratoryCd As LaboratoryCd  \r\n");
        sql.append("    , ObservationEvent.OrderNo As OrderNo  \r\n");
        sql.append("    , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , ObservationEvent.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , ObservationEvent.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , ObservationEvent.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg  \r\n");
        sql.append("from ObservationEvent \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationEvent \r\n");
        sql.append("(ObservationEventId, DataInputTypeCd, PHR_ID, ExaminationDate, Year, InsurerNo, LaboratoryCd, OrderNo, MedicalOrganizationCd, CreateDateTime, UpdateDateTime, DiseaseManagementTargetFlg) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ObservationEvent set \r\n");
        sql.append("DataInputTypeCd = ?, PHR_ID = ?, ExaminationDate = ?, Year = ?, InsurerNo = ?, LaboratoryCd = ?, OrderNo = ?, MedicalOrganizationCd = ?, UpdateDateTime = CURRENT_TIMESTAMP, DiseaseManagementTargetFlg = ? \r\n");
        sql.append("where ObservationEventId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ObservationEvent \r\n");
        sql.append("where ObservationEventId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationEventEntity entity) throws Throwable  
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getObservationEventId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getObservationEventId());
            }
            preparedStatement.setInt(2, entity.getDataInputTypeCd());
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getPHR_ID());
            }
            preparedStatement.setTimestamp(4, entity.getExaminationDate());
            preparedStatement.setInt(5, entity.getYear());
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getInsurerNo());
            }
            if (entity.getLaboratoryCd() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getLaboratoryCd());
            }
            if (entity.getOrderNo() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getOrderNo());
            }
            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getMedicalOrganizationCd());
            }
            preparedStatement.setBoolean(10, entity.isDiseaseManagementTargetFlg());

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();

        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(ObservationEventEntity entity) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getDataInputTypeCd());
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPHR_ID());
            }
            preparedStatement.setTimestamp(3, entity.getExaminationDate());
            preparedStatement.setInt(4, entity.getYear());
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getInsurerNo());
            }
            if (entity.getLaboratoryCd() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getLaboratoryCd());
            }
            if (entity.getOrderNo() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getOrderNo());
            }
            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getMedicalOrganizationCd());
            }
            preparedStatement.setBoolean(9, entity.isDiseaseManagementTargetFlg());
            if (entity.getObservationEventId() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getObservationEventId());
            }
            preparedStatement.setTimestamp(11, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて検査結果情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationEventEntity entity) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getObservationEventId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getObservationEventId());
            }
            preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて検査結果情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public ObservationEventEntity findByPrimaryKey(String key1) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationEvent.ObservationEventId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        ObservationEventEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationEventEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
}
