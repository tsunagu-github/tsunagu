/********************************************************************************
 * システム名      ：phr
 * コンポーネント名：検査項目結果情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/12/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.ObservationEntity;

/**
 * 検査項目結果情報のデータオブジェクトです。
 */
public abstract class ObservationAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = Logger.getLogger(ObservationAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationAdapterBase(Connection conn)
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
        sql.append("    Observation.ObservationEventId As ObservationEventId  \r\n");
        sql.append("    , Observation.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , Observation.JLAC10 As JLAC10  \r\n");
        sql.append("    , Observation.Value As Value  \r\n");
        sql.append("    , Observation.OutRangeTypeCd As OutRangeTypeCd  \r\n");
        sql.append("    , Observation.MinReferenceValue As MinReferenceValue  \r\n");
        sql.append("    , Observation.MaxReferenceValue As MaxReferenceValue  \r\n");
        sql.append("    , Observation.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , Observation.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , Observation.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg  \r\n");
        sql.append("    , Observation.Unit As Unit  \r\n");
        sql.append("from Observation \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Observation \r\n");
        sql.append("(ObservationEventId, ObservationDefinitionId, JLAC10, Value, OutRangeTypeCd, MinReferenceValue, MaxReferenceValue, CreateDateTime, UpdateDateTime, DiseaseManagementTargetFlg, Unit) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update Observation set \r\n");
        sql.append("JLAC10 = ?, Value = ?, OutRangeTypeCd = ?, MinReferenceValue = ?, MaxReferenceValue = ?, UpdateDateTime = CURRENT_TIMESTAMP, DiseaseManagementTargetFlg = ?, Unit = ? \r\n");
        sql.append("where ObservationEventId = ? AND ObservationDefinitionId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  Observation \r\n");
        sql.append("where ObservationEventId = ? AND ObservationDefinitionId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationEntity entity) throws Throwable  
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
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getObservationDefinitionId());
            }
            if (entity.getJLAC10() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getJLAC10());
            }
            if (entity.getValue() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getValue());
            }
            if (entity.getOutRangeTypeCd() == null ) {
                preparedStatement.setNull(5, Types.INTEGER );
            } else {
                preparedStatement.setInt(5, entity.getOutRangeTypeCd().intValue());
            }
            if (entity.getMinReferenceValue() == null ) {
                preparedStatement.setNull(6, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(6, entity.getMinReferenceValue().doubleValue());
            }
            if (entity.getMaxReferenceValue() == null ) {
                preparedStatement.setNull(7, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(7, entity.getMaxReferenceValue().doubleValue());
            }
            preparedStatement.setBoolean(8, entity.isDiseaseManagementTargetFlg());
            if (entity.getUnit() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getUnit());
            }

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
    public int update(ObservationEntity entity) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getJLAC10() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getJLAC10());
            }
            if (entity.getValue() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getValue());
            }
            if (entity.getOutRangeTypeCd() == null ) {
                preparedStatement.setNull(3, Types.INTEGER );
            } else {
                preparedStatement.setInt(3, entity.getOutRangeTypeCd().intValue());
            }
            if (entity.getMinReferenceValue() == null ) {
                preparedStatement.setNull(4, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(4, entity.getMinReferenceValue().doubleValue());
            }
            if (entity.getMaxReferenceValue() == null ) {
                preparedStatement.setNull(5, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(5, entity.getMaxReferenceValue().doubleValue());
            }
            preparedStatement.setBoolean(6, entity.isDiseaseManagementTargetFlg());
            if (entity.getUnit() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getUnit());
            }
            if (entity.getObservationEventId() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getObservationEventId());
            }
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getObservationDefinitionId());
            }
            preparedStatement.setTimestamp(10, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて検査項目結果情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationEntity entity) throws Throwable 
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
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getObservationDefinitionId());
            }
            preparedStatement.setTimestamp(3, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて検査項目結果情報を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public ObservationEntity findByPrimaryKey(String key1, String key2) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where Observation.ObservationEventId = ?";
        sql += "   and Observation.ObservationDefinitionId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);

        ObservationEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
}
