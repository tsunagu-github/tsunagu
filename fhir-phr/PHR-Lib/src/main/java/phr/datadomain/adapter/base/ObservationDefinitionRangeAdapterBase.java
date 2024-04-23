/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目範囲情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/11/04
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

import phr.datadomain.entity.ObservationDefinitionRangeEntity;

/**
 * 管理項目範囲情報のデータオブジェクトです。
 */
public abstract class ObservationDefinitionRangeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionRangeAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionRangeAdapterBase(Connection conn)
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
        sql.append("    ObservationDefinitionRange.InsurerNo As InsurerNo  \r\n");
        sql.append("    , ObservationDefinitionRange.Year As Year  \r\n");
        sql.append("    , ObservationDefinitionRange.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , ObservationDefinitionRange.DiseaseTypeCd As DiseaseTypeCd  \r\n");
        sql.append("    , ObservationDefinitionRange.ReminderTypeCd As ReminderTypeCd  \r\n");
        sql.append("    , ObservationDefinitionRange.RangeTypeCd As RangeTypeCd  \r\n");
        sql.append("    , ObservationDefinitionRange.MinInput As MinInput  \r\n");
        sql.append("    , ObservationDefinitionRange.MaxInput As MaxInput  \r\n");
        sql.append("from ObservationDefinitionRange \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationDefinitionRange \r\n");
        sql.append("(InsurerNo, Year, ObservationDefinitionId, DiseaseTypeCd, ReminderTypeCd, RangeTypeCd, MinInput, MaxInput) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ObservationDefinitionRange set \r\n");
        sql.append("ReminderTypeCd = ?, RangeTypeCd = ?, MinInput = ?, MaxInput = ? \r\n");
        sql.append("where InsurerNo = ? AND Year = ? AND ObservationDefinitionId = ? AND DiseaseTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ObservationDefinitionRange \r\n");
        sql.append("where InsurerNo = ? AND Year = ? AND ObservationDefinitionId = ? AND DiseaseTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationDefinitionRangeEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getInsurerNo());
            }
            preparedStatement.setInt(2, entity.getYear());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(4, entity.getDiseaseTypeCd());
            if (entity.getReminderTypeCd() == null ) {
                preparedStatement.setNull(5, Types.INTEGER );
            } else {
                preparedStatement.setInt(5, entity.getReminderTypeCd().intValue());
            }
            preparedStatement.setInt(6, entity.getRangeTypeCd());
            if (entity.getMinInput() == null ) {
                preparedStatement.setNull(7, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(7, entity.getMinInput().doubleValue());
            }
            if (entity.getMaxInput() == null ) {
                preparedStatement.setNull(8, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(8, entity.getMaxInput().doubleValue());
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
    public int update(ObservationDefinitionRangeEntity entity) throws Throwable 
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
            preparedStatement.setInt(2, entity.getRangeTypeCd());
            if (entity.getMinInput() == null ) {
                preparedStatement.setNull(3, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(3, entity.getMinInput().doubleValue());
            }
            if (entity.getMaxInput() == null ) {
                preparedStatement.setNull(4, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(4, entity.getMaxInput().doubleValue());
            }
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getInsurerNo());
            }
            preparedStatement.setInt(6, entity.getYear());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(8, entity.getDiseaseTypeCd());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて管理項目範囲情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationDefinitionRangeEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getInsurerNo());
            }
            preparedStatement.setInt(2, entity.getYear());
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(4, entity.getDiseaseTypeCd());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて管理項目範囲情報を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @param key4
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionRangeEntity findByPrimaryKey(String key1, int key2, String key3, int key4) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionRange.InsurerNo = ?";
        sql += "   and ObservationDefinitionRange.Year = ?";
        sql += "   and ObservationDefinitionRange.ObservationDefinitionId = ?";
        sql += "   and ObservationDefinitionRange.DiseaseTypeCd = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);
        preparedStatement.setString(3, key3);
        preparedStatement.setInt(4, key4);

        ObservationDefinitionRangeEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionRangeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
