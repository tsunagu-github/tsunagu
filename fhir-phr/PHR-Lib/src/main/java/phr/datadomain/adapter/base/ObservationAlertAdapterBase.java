/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査項目結果アラート情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/25
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

import phr.datadomain.entity.ObservationAlertEntity;

/**
 * 検査項目結果アラート情報のデータオブジェクトです。
 */
public abstract class ObservationAlertAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationAlertAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationAlertAdapterBase(Connection conn)
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
        sql.append("    ObservationAlert.ObservationEventId As ObservationEventId  \r\n");
        sql.append("    , ObservationAlert.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , ObservationAlert.ViewId As ViewId  \r\n");
        sql.append("    , ObservationAlert.AlertLevelCd As AlertLevelCd  \r\n");
        sql.append("    , ObservationAlert.AlertFlg As AlertFlg  \r\n");
        sql.append("    , ObservationAlert.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , ObservationAlert.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from ObservationAlert \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationAlert \r\n");
        sql.append("(ObservationEventId, ObservationDefinitionId, ViewId, AlertLevelCd, AlertFlg, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ObservationAlert set \r\n");
        sql.append("AlertLevelCd = ?, AlertFlg = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where ObservationEventId = ? AND ObservationDefinitionId = ? AND ViewId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ObservationAlert \r\n");
        sql.append("where ObservationEventId = ? AND ObservationDefinitionId = ? AND ViewId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationAlertEntity entity) throws Throwable  
    {
        logger.trace("Start");
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
            preparedStatement.setInt(3, entity.getViewId());
            if (entity.getAlertLevelCd() == null ) {
                preparedStatement.setNull(4, Types.INTEGER );
            } else {
                preparedStatement.setInt(4, entity.getAlertLevelCd().intValue());
            }
            preparedStatement.setBoolean(5, entity.isAlertFlg());

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
    public int update(ObservationAlertEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "";
        if(entity.getUpdateDateTime()==null){
            StringBuilder sqlOrg = new StringBuilder();
            sqlOrg.append(getUpdateSql());
            int startidx = sqlOrg.indexOf("and");
            int lastidx = sqlOrg.length();
            sqlOrg.delete(startidx,lastidx);
            sql = sqlOrg.toString();
        }else{
            sql = getUpdateSql();
        }        

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getAlertLevelCd() == null ) {
                preparedStatement.setNull(1, Types.INTEGER );
            } else {
                preparedStatement.setInt(1, entity.getAlertLevelCd().intValue());
            }
            preparedStatement.setBoolean(2, entity.isAlertFlg());
            if (entity.getObservationEventId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getObservationEventId());
            }
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(5, entity.getViewId());
            if(entity.getUpdateDateTime()==null){
                
            }else{
                preparedStatement.setTimestamp(6, entity.getUpdateDateTime());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて検査項目結果アラート情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationAlertEntity entity) throws Throwable 
    {
        logger.trace("Start");
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
            preparedStatement.setInt(3, entity.getViewId());
            preparedStatement.setTimestamp(4, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて検査項目結果アラート情報を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @return
     * @throws Throwable
     */
    public ObservationAlertEntity findByPrimaryKey(String key1, String key2, int key3) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationAlert.ObservationEventId = ?";
        sql += "   and ObservationAlert.ObservationDefinitionId = ?";
        sql += "   and ObservationAlert.ViewId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setInt(3, key3);

        ObservationAlertEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationAlertEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
