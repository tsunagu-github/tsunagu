/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者管理項目情報のデータオブジェクト
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

import phr.datadomain.entity.ObservationDefinitionInsurerEntity;

/**
 * 保険者管理項目情報のデータオブジェクトです。
 */
public abstract class ObservationDefinitionInsurerAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionInsurerAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionInsurerAdapterBase(Connection conn)
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
        sql.append("    ObservationDefinitionInsurer.ViewId As ViewId  \r\n");
        sql.append("    , ObservationDefinitionInsurer.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , ObservationDefinitionInsurer.ReminderTypeCd As ReminderTypeCd  \r\n");
        sql.append("    , ObservationDefinitionInsurer.SortNo As SortNo  \r\n");
        sql.append("    , ObservationDefinitionInsurer.UnitValue As UnitValue  \r\n");
        sql.append("    , ObservationDefinitionInsurer.DataTypeCd As DataTypeCd  \r\n");
        sql.append("    , ObservationDefinitionInsurer.MinReferenceValue As MinReferenceValue  \r\n");
        sql.append("    , ObservationDefinitionInsurer.MaxReferenceValue As MaxReferenceValue  \r\n");
        sql.append("    , ObservationDefinitionInsurer.DefaultJLAC10 As DefaultJLAC10  \r\n");
        sql.append("from ObservationDefinitionInsurer \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationDefinitionInsurer \r\n");
        sql.append("(ViewId, ObservationDefinitionId, ReminderTypeCd, SortNo, UnitValue, DataTypeCd, MinReferenceValue, MaxReferenceValue, DefaultJLAC10) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ObservationDefinitionInsurer set \r\n");
        sql.append("ReminderTypeCd = ?, SortNo = ?, UnitValue = ?, DataTypeCd = ?, MinReferenceValue = ?, MaxReferenceValue = ?, DefaultJLAC10 = ? \r\n");
        sql.append("where ViewId = ? AND ObservationDefinitionId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ObservationDefinitionInsurer \r\n");
        sql.append("where ViewId = ? AND ObservationDefinitionId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationDefinitionInsurerEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getViewId());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getObservationDefinitionId());
            }
            if (entity.getReminderTypeCd() == null ) {
                preparedStatement.setNull(3, Types.INTEGER );
            } else {
                preparedStatement.setInt(3, entity.getReminderTypeCd().intValue());
            }
            if (entity.getSortNo() == null ) {
                preparedStatement.setNull(4, Types.INTEGER );
            } else {
                preparedStatement.setInt(4, entity.getSortNo().intValue());
            }
            if (entity.getUnitValue() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getUnitValue());
            }
            if (entity.getDataTypeCd() == null ) {
                preparedStatement.setNull(6, Types.INTEGER );
            } else {
                preparedStatement.setInt(6, entity.getDataTypeCd().intValue());
            }
            if (entity.getMinReferenceValue() == null ) {
                preparedStatement.setNull(7, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(7, entity.getMinReferenceValue().doubleValue());
            }
            if (entity.getMaxReferenceValue() == null ) {
                preparedStatement.setNull(8, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(8, entity.getMaxReferenceValue().doubleValue());
            }
            if (entity.getDefaultJLAC10() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getDefaultJLAC10());
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
    public int update(ObservationDefinitionInsurerEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getReminderTypeCd() == null ) {
                preparedStatement.setNull(1, Types.INTEGER );
            } else {
                preparedStatement.setInt(1, entity.getReminderTypeCd().intValue());
            }
            if (entity.getSortNo() == null ) {
                preparedStatement.setNull(2, Types.INTEGER );
            } else {
                preparedStatement.setInt(2, entity.getSortNo().intValue());
            }
            if (entity.getUnitValue() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getUnitValue());
            }
            if (entity.getDataTypeCd() == null ) {
                preparedStatement.setNull(4, Types.INTEGER );
            } else {
                preparedStatement.setInt(4, entity.getDataTypeCd().intValue());
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
            if (entity.getDefaultJLAC10() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getDefaultJLAC10());
            }
            preparedStatement.setInt(8, entity.getViewId());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getObservationDefinitionId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて保険者管理項目情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationDefinitionInsurerEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getViewId());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getObservationDefinitionId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて保険者管理項目情報を検索します。
     * @param key1
     * @param key1
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionInsurerEntity findByPrimaryKey(int key1, String key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionInsurer.ViewId = ?";
        sql += "   and ObservationDefinitionInsurer.ObservationDefinitionId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, key1);
        preparedStatement.setString(2, key2);

        ObservationDefinitionInsurerEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionInsurerEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
