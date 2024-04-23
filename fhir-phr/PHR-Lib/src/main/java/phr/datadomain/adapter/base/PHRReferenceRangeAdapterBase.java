/********************************************************************************
 * システム名      ：MInCS for PHR
 * コンポーネント名：PHR推奨設定シートのデータオブジェクト
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
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.PHRReferenceRangeEntity;

/**
 * PHR推奨設定シートのデータオブジェクトです。
 */
public abstract class PHRReferenceRangeAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(PHRReferenceRangeAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PHRReferenceRangeAdapterBase(Connection conn)
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
        sql.append("    PHRReferenceRange.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , PHRReferenceRange.ObservationDefinitionName As ObservationDefinitionName  \r\n");
        sql.append("    , PHRReferenceRange.Unit As Unit  \r\n");
        sql.append("    , PHRReferenceRange.UnitCode As UnitCode  \r\n");
        sql.append("    , PHRReferenceRange.MinReferenceValue As MinReferenceValue  \r\n");
        sql.append("    , PHRReferenceRange.MaxReferenceValue As MaxReferenceValue  \r\n");
        sql.append("    , PHRReferenceRange.MaleMinReferenceValue As MaleMinReferenceValue  \r\n");
        sql.append("    , PHRReferenceRange.MaleMaxReferenceValue As MaleMaxReferenceValue  \r\n");
        sql.append("    , PHRReferenceRange.FemaleMinReferenceValue As FemaleMinReferenceValue  \r\n");
        sql.append("    , PHRReferenceRange.FemaleMaxReferenceValue As FemaleMaxReferenceValue  \r\n");
        sql.append("    , PHRReferenceRange.ReferenceEnumName As ReferenceEnumName  \r\n");
        sql.append("    , PHRReferenceRange.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , PHRReferenceRange.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from PHRReferenceRange \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into PHRReferenceRange \r\n");
        sql.append("(ObservationDefinitionId, ObservationDefinitionName, Unit, UnitCode, MinReferenceValue, MaxReferenceValue, MaleMinReferenceValue, MaleMaxReferenceValue, FemaleMinReferenceValue, FemaleMaxReferenceValue, ReferenceEnumName, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update PHRReferenceRange set \r\n");
        sql.append("ObservationDefinitionName = ?, Unit = ?, UnitCode = ?, MinReferenceValue = ?, MaxReferenceValue = ?, MaleMinReferenceValue = ?, MaleMaxReferenceValue = ?, FemaleMinReferenceValue = ?, FemaleMaxReferenceValue = ?, ReferenceEnumName = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where ObservationDefinitionId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  PHRReferenceRange \r\n");
        sql.append("where ObservationDefinitionId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(PHRReferenceRangeEntity entity) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        if (entity.getObservationDefinitionId() == null ) {
            preparedStatement.setNull(1, Types.VARCHAR );
        } else {
            preparedStatement.setString(1, entity.getObservationDefinitionId());
        }
        if (entity.getObservationDefinitionName() == null ) {
            preparedStatement.setNull(2, Types.VARCHAR );
        } else {
            preparedStatement.setString(2, entity.getObservationDefinitionName());
        }
        if (entity.getUnit() == null ) {
            preparedStatement.setNull(3, Types.VARCHAR );
        } else {
            preparedStatement.setString(3, entity.getUnit());
        }
        if (entity.getUnitCode() == null ) {
            preparedStatement.setNull(4, Types.VARCHAR );
        } else {
            preparedStatement.setString(4, entity.getUnitCode());
        }
        if (entity.getMinReferenceValue() == null ) {
            preparedStatement.setNull(5, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(5, entity.getMinReferenceValue().doubleValue());
        }
        if (entity.getMaxReferenceValue() == null ) {
            preparedStatement.setNull(6, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(6, entity.getMaxReferenceValue().doubleValue());
        }
        if (entity.getMaleMinReferenceValue() == null ) {
            preparedStatement.setNull(7, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(7, entity.getMaleMinReferenceValue().doubleValue());
        }
        if (entity.getMaleMaxReferenceValue() == null ) {
            preparedStatement.setNull(8, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(8, entity.getMaleMaxReferenceValue().doubleValue());
        }
        if (entity.getFemaleMinReferenceValue() == null ) {
            preparedStatement.setNull(9, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(9, entity.getFemaleMinReferenceValue().doubleValue());
        }
        if (entity.getFemaleMaxReferenceValue() == null ) {
            preparedStatement.setNull(10, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(10, entity.getFemaleMaxReferenceValue().doubleValue());
        }
        if (entity.getReferenceEnumName() == null ) {
            preparedStatement.setNull(11, Types.VARCHAR );
        } else {
            preparedStatement.setString(11, entity.getReferenceEnumName());
        }
        
        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
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
    public int update(PHRReferenceRangeEntity entity) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        if (entity.getObservationDefinitionId() == null ) {
            preparedStatement.setNull(1, Types.VARCHAR );
        } else {
            preparedStatement.setString(1, entity.getObservationDefinitionId());
        }
        if (entity.getObservationDefinitionName() == null ) {
            preparedStatement.setNull(2, Types.VARCHAR );
        } else {
            preparedStatement.setString(2, entity.getObservationDefinitionName());
        }
        if (entity.getUnit() == null ) {
            preparedStatement.setNull(3, Types.VARCHAR );
        } else {
            preparedStatement.setString(3, entity.getUnit());
        }
        if (entity.getUnitCode() == null ) {
            preparedStatement.setNull(4, Types.VARCHAR );
        } else {
            preparedStatement.setString(4, entity.getUnitCode());
        }
        if (entity.getMinReferenceValue() == null ) {
            preparedStatement.setNull(5, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(5, entity.getMinReferenceValue().doubleValue());
        }
        if (entity.getMaxReferenceValue() == null ) {
            preparedStatement.setNull(6, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(6, entity.getMaxReferenceValue().doubleValue());
        }
        if (entity.getMaleMinReferenceValue() == null ) {
            preparedStatement.setNull(7, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(7, entity.getMaleMinReferenceValue().doubleValue());
        }
        if (entity.getMaleMaxReferenceValue() == null ) {
            preparedStatement.setNull(8, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(8, entity.getMaleMaxReferenceValue().doubleValue());
        }
        if (entity.getFemaleMinReferenceValue() == null ) {
            preparedStatement.setNull(9, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(9, entity.getFemaleMinReferenceValue().doubleValue());
        }
        if (entity.getFemaleMaxReferenceValue() == null ) {
            preparedStatement.setNull(10, Types.DOUBLE );
        } else {
            preparedStatement.setDouble(10, entity.getFemaleMaxReferenceValue().doubleValue());
        }
        if (entity.getReferenceEnumName() == null ) {
            preparedStatement.setNull(11, Types.VARCHAR );
        } else {
            preparedStatement.setString(11, entity.getReferenceEnumName());
        }
        preparedStatement.setTimestamp(12, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてPHR推奨設定シートの情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(PHRReferenceRangeEntity entity) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        if (entity.getObservationDefinitionId() == null ) {
            preparedStatement.setNull(1, Types.VARCHAR );
        } else {
            preparedStatement.setString(1, entity.getObservationDefinitionId());
        }
        preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてPHR推奨設定シートを検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public PHRReferenceRangeEntity findByPrimaryKey(String key1) throws Throwable { 
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where PHRReferenceRange.ObservationDefinitionId = ?";

        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        
        PHRReferenceRangeEntity entity = null;
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = PHRReferenceRangeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
