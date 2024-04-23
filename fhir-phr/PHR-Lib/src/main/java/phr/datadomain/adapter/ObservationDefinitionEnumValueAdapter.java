/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目列挙値情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationDefinitionEnumValueEntity;

/**
 * 管理項目列挙値情報のデータオブジェクトです。
 */
public class ObservationDefinitionEnumValueAdapter extends ObservationDefinitionEnumValueAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionEnumValueAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionEnumValueAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブザベーションIDにて管理項目列挙値情報を検索します。
     * @param year
     * @param observationId
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionEnumValueEntity> findByObservationId( String observationId, String insurerNo) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "   Where ObservationDefinitionEnumValue.ObservationDefinitionId = ?";
//        sql += "   and ObservationDefinitionEnumValue.InsurerNo = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationId);
//        preparedStatement.setString(2, insurerNo);

        List<ObservationDefinitionEnumValueEntity> entityList = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationDefinitionEnumValueEntity entity = ObservationDefinitionEnumValueEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }    
    
    public List<ObservationDefinitionEnumValueEntity> findByViewId( int viewId ) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
//        sql += "   Where ObservationDefinitionEnumValue.viewId = ?";
        sql += "   Where ObservationDefinitionEnumValue.InputValueFlg = 1";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
//        preparedStatement.setInt(1, viewId);
//        preparedStatement.setString(2, insurerNo);

        List<ObservationDefinitionEnumValueEntity> entityList = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationDefinitionEnumValueEntity entity = ObservationDefinitionEnumValueEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }    

    /**
     * ObservationEventIdとenumValueからenumNameの値を取得
     * @param observationEventId
     * @param enumValue
     * @param viewId
     * @return entity
     * @throws Throwable 
     */
    public ObservationDefinitionEnumValueEntity findByIdAndEnum(String observationDefinitionId, String enumValue, int viewId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "   where ObservationDefinitionEnumValue.ViewId = ?";
        sql += "   and ObservationDefinitionEnumValue.ObservationDefinitionId = ?";
        sql += "   and ObservationDefinitionEnumValue.EnumValue = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);
        preparedStatement.setString(2, observationDefinitionId);
        preparedStatement.setString(3, enumValue);

        ObservationDefinitionEnumValueEntity entity = new ObservationDefinitionEnumValueEntity();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionEnumValueEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
