/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ定義のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2019/05/09
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

import phr.datadomain.entity.ChartDefinitionEntity;

/**
 * グラフ定義のデータオブジェクトです。
 */
public abstract class ChartDefinitionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartDefinitionAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ChartDefinitionAdapterBase(Connection conn)
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
        sql.append("    ChartDefinition.ChartDefinitionId As ChartDefinitionId  \r\n");
        sql.append("    , ChartDefinition.ChartDefinitionName As ChartDefinitionName  \r\n");
        sql.append("    , ChartDefinition.ViewCount As ViewCount  \r\n");
        sql.append("    , ChartDefinition.DataInputTypeCd As DataInputTypeCd  \r\n");
        sql.append("    , ChartDefinition.InsurerNo As InsurerNo  \r\n");
        sql.append("    , ChartDefinition.PHR_ID As PHR_ID  \r\n");
        sql.append("    , ChartDefinition.CommonFlg As CommonFlg  \r\n");
        sql.append("    , ChartDefinition.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , ChartDefinition.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from ChartDefinition \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ChartDefinition \r\n");
        sql.append("(ChartDefinitionId, ChartDefinitionName, ViewCount, DataInputTypeCd, InsurerNo, PHR_ID, CommonFlg, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ChartDefinition set \r\n");
        sql.append("ChartDefinitionName = ?, ViewCount = ?, DataInputTypeCd = ?, InsurerNo = ?, PHR_ID = ?, CommonFlg = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where ChartDefinitionId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ChartDefinition \r\n");
        sql.append("where ChartDefinitionId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ChartDefinitionEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            preparedStatement.setLong(1, entity.getChartDefinitionId());
            if (entity.getChartDefinitionName() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getChartDefinitionName());
            }
            preparedStatement.setInt(3, entity.getViewCount());
            preparedStatement.setInt(4, entity.getDataInputTypeCd());
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getInsurerNo());
            }
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getPHR_ID());
            }
            preparedStatement.setInt(7, entity.getCommonFlg());

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
    public int update(ChartDefinitionEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getChartDefinitionName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getChartDefinitionName());
            }
            preparedStatement.setInt(2, entity.getViewCount());
            preparedStatement.setInt(3, entity.getDataInputTypeCd());
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getInsurerNo());
            }
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getPHR_ID());
            }
            preparedStatement.setInt(6, entity.getCommonFlg());
            preparedStatement.setLong(7, entity.getChartDefinitionId());
            preparedStatement.setTimestamp(8, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてグラフ定義の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ChartDefinitionEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setLong(1, entity.getChartDefinitionId());
            preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてグラフ定義を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public ChartDefinitionEntity findByPrimaryKey(long key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ChartDefinition.ChartDefinitionId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, key1);

        ChartDefinitionEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ChartDefinitionEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
