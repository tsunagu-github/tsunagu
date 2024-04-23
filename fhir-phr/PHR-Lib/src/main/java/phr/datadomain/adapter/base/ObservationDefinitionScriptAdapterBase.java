/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ入力種別のデータオブジェクト
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
import phr.datadomain.AbstractEntity;
import java.sql.PreparedStatement;
import java.sql.Types;
import phr.datadomain.DataAccessObject;
import phr.utility.TypeUtility;

import phr.datadomain.entity.ObservationDefinitionScriptEntity;

/**
 * データ入力種別のデータオブジェクトです。
 */
public abstract class ObservationDefinitionScriptAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionScriptAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionScriptAdapterBase(Connection conn)
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
        sql.append("    ObservationDefinitionScript.ViewId As ViewId  \r\n");
        sql.append("    , ObservationDefinitionScript.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , ObservationDefinitionScript.ScriptTypeId As ScriptTypeId  \r\n");
        sql.append("    , ObservationDefinitionScript.AlertLevelScript As AlertLevelScript  \r\n");
        sql.append("    , ObservationDefinitionScript.AlertFlgScript As AlertFlgScript  \r\n");
        sql.append("    , ObservationDefinitionScript.CompareDate As CompareDate  \r\n");
        sql.append("    , ObservationDefinitionScript.UpperLimit As UpperLimit  \r\n");
        sql.append("    , ObservationDefinitionScript.LowerLimit As LowerLimit  \r\n");
        sql.append("    , ObservationDefinitionScript.DiseaseTypeCd As DiseaseTypeCd  \r\n");
        sql.append("from ObservationDefinitionScript \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationDefinitionScript \r\n");
        sql.append("(ViewId, ObservationDefinitionId, ScriptTypeId, AlertLevelScript, AlertFlgScript, CompareDate, UpperLimit, LowerLimit, DiseaseTypeCd) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ObservationDefinitionScript set \r\n");
        sql.append("ScriptTypeId = ?, AlertLevelScript = ?, AlertFlgScript = ?, CompareDate = ?, UpperLimit = ?, LowerLimit = ?, DiseaseTypeCd = ? \r\n");
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
        sql.append("delete from  ObservationDefinitionScript \r\n");
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
    public int insert(ObservationDefinitionScriptEntity entity) throws Throwable  
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
            preparedStatement.setInt(3, entity.getScriptTypeId());
            if (entity.getAlertLevelScript() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getAlertLevelScript());
            }
            if (entity.getAlertFlgScript() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getAlertFlgScript());
            }
            if (entity.getCompareDate() == null ) {
                preparedStatement.setNull(6, Types.INTEGER );
            } else {
                preparedStatement.setInt(6, entity.getCompareDate().intValue());
            }
            if (entity.getUpperLimit() == null ) {
                preparedStatement.setNull(7, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(7, entity.getUpperLimit().doubleValue());
            }
            if (entity.getLowerLimit() == null ) {
                preparedStatement.setNull(8, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(8, entity.getLowerLimit().doubleValue());
            }
            if (entity.getDiseaseTypeCd() == null ) {
                preparedStatement.setNull(9, Types.INTEGER );
            } else {
                preparedStatement.setInt(9, entity.getDiseaseTypeCd().intValue());
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
    public int update(ObservationDefinitionScriptEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getScriptTypeId());
            if (entity.getAlertLevelScript() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getAlertLevelScript());
            }
            if (entity.getAlertFlgScript() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getAlertFlgScript());
            }
            if (entity.getCompareDate() == null ) {
                preparedStatement.setNull(4, Types.INTEGER );
            } else {
                preparedStatement.setInt(4, entity.getCompareDate().intValue());
            }
            if (entity.getUpperLimit() == null ) {
                preparedStatement.setNull(5, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(5, entity.getUpperLimit().doubleValue());
            }
            if (entity.getLowerLimit() == null ) {
                preparedStatement.setNull(6, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(6, entity.getLowerLimit().doubleValue());
            }
            if (entity.getDiseaseTypeCd() == null ) {
                preparedStatement.setNull(7, Types.INTEGER );
            } else {
                preparedStatement.setInt(7, entity.getDiseaseTypeCd().intValue());
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
     * オブジェクトの値にてデータ入力種別の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationDefinitionScriptEntity entity) throws Throwable 
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
     * 主キーにてデータ入力種別を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionScriptEntity findByPrimaryKey(int key1, String key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionScript.ViewId = ?";
        sql += "   and ObservationDefinitionScript.ObservationDefinitionId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, key1);
        preparedStatement.setString(2, key2);

        ObservationDefinitionScriptEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionScriptEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
