/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ項目定義のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2019/05/09
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getLong;
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ChartDefinitionEntity;
import phr.datadomain.entity.ChartObservationDefinitionEntity;

/**
 * グラフ項目定義のデータオブジェクトです。
 */
public class ChartObservationDefinitionAdapter extends ChartObservationDefinitionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartObservationDefinitionAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ChartObservationDefinitionAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * グラフ定義IDでグラフ項目情報を削除する
     * @param chartDefinitionId
     * @return
     * @throws SQLException 
     */
    public int deleteByChartDefinitionId(long chartDefinitionId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ChartObservationDefinition \r\n");
        sql.append("where ChartDefinitionId = ? \r\n");
        DataAccessObject dao = new DataAccessObject(connection);
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, chartDefinitionId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();     
        
        return rowCount;
    }
    /**
     * グラフ項目定義を取得する（定義編集用）更新
     * @param insurerNo
     * @param chartDefinitionId
     * @param dataInputType
     * @return
     * @throws SQLException 
     */
    public List<ChartObservationDefinitionEntity> searchChartObservationDefinition(String insurerNo, Long chartDefinitionId, Integer dataInputType) throws SQLException {
        
        List<ChartObservationDefinitionEntity> list =  new ArrayList<>();
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct \n");
        sb.append("    ObservationDefinition.ObservationDefinitionId \n");
        sb.append("    , ObservationDefinition.DisplayName \n");
        sb.append("    , ObservationDefinitionInsurer.UnitValue \n");
        sb.append("    , ChartObservationDefinition.ChartDefinitionId \n");
        sb.append("    , ChartObservationDefinition.LineColor \n");
        sb.append("from \n");
        sb.append("    ObservationDefinition \n");
        sb.append("    inner join ObservationDefinitionInsurer on \n");
        sb.append("        ObservationDefinitionInsurer.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("        and ObservationDefinitionInsurer.DataTypeCd = 2 \n");
        sb.append("    inner join InsurerViewDefinition on \n");
        sb.append("        InsurerViewDefinition.ViewId = ObservationDefinitionInsurer.ViewId \n");
        sb.append("    inner join ObservationDefinitionType on \n");
        sb.append("        ObservationDefinitionType.InsurerNo = InsurerViewDefinition.InsurerNo \n");
        sb.append("        and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("    left outer join ChartObservationDefinition on \n");
        sb.append("	ChartObservationDefinition.ChartDefinitionId = ? \n");
        sb.append("	and ChartObservationDefinition.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("where  \n");
        sb.append("    InsurerViewDefinition.InsurerNo = ? \n");
        sb.append(" and ChartObservationDefinition.ChartDefinitionId = ? \n");
//        sb.append("    and ObservationDefinitionType.DataInputTypeCd = ? \n");
//        sb.append("order by ObservationDefinitionInsurer.SortNo \n");
        
        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, chartDefinitionId);
        preparedStatement.setString(2, insurerNo);
//        preparedStatement.setInt(3, dataInputType);
        preparedStatement.setLong(3, chartDefinitionId);
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return list;
        }
        
        while( dataTable.next() ) {
            ChartObservationDefinitionEntity entity = new ChartObservationDefinitionEntity();
            entity.setChartDefinitionId(getLong(dataTable, "ChartDefinitionId"));
            entity.setObservationDefinitionId(getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionName(getString(dataTable, "DisplayName"));
            entity.setUnitValue(getString(dataTable, "UnitValue"));
            entity.setLineColor(getString(dataTable, "LineColor"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;

    }
    /**
     * グラフ項目定義を取得する（定義編集用）新規
     * @param insurerNo
     * @param chartDefinitionId
     * @param dataInputType
     * @return
     * @throws SQLException 
     */
    public List<ChartObservationDefinitionEntity> searchNewChartObservationDefinition(String insurerNo, Integer dataInputType) throws SQLException {
        
        List<ChartObservationDefinitionEntity> list =  new ArrayList<>();
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct \n");
        sb.append("    ObservationDefinition.ObservationDefinitionId \n");
        sb.append("    , ObservationDefinition.DisplayName \n");
        sb.append("    , ObservationDefinitionInsurer.UnitValue \n");
        sb.append("from \n");
        sb.append("    ObservationDefinition \n");
        sb.append("    inner join ObservationDefinitionInsurer on \n");
        sb.append("        ObservationDefinitionInsurer.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("        and ObservationDefinitionInsurer.DataTypeCd = 2 \n");
        sb.append("    inner join InsurerViewDefinition on \n");
        sb.append("        InsurerViewDefinition.ViewId = ObservationDefinitionInsurer.ViewId \n");
        sb.append("    inner join ObservationDefinitionType on \n");
        sb.append("        ObservationDefinitionType.InsurerNo = InsurerViewDefinition.InsurerNo \n");
        sb.append("        and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("where  \n");
        sb.append("    InsurerViewDefinition.InsurerNo = ? \n");
        sb.append("    and ObservationDefinitionType.DataInputTypeCd = ? \n");
//        sb.append("order by ObservationDefinitionInsurer.SortNo \n");
        
        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        preparedStatement.setInt(2, dataInputType);
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return list;
        }
        
        while( dataTable.next() ) {
            ChartObservationDefinitionEntity entity = new ChartObservationDefinitionEntity();
            entity.setObservationDefinitionId(getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionName(getString(dataTable, "DisplayName"));
            entity.setUnitValue(getString(dataTable, "UnitValue"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;

    }
    /**
     * グラフ定義を取得する（グラフ表示用）
     * @param chartDefinitionId
     * @return
     * @throws SQLException
     * @throws Throwable 
     */
    public List<ChartObservationDefinitionEntity> getChartDefinition(long chartDefinitionId) throws SQLException, Throwable {
        List<ChartObservationDefinitionEntity> list =  new ArrayList<>();
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct \n");
        sb.append("    ObservationDefinition.ObservationDefinitionId \n");
        sb.append("    , ObservationDefinition.DisplayName \n");
        sb.append("    , ObservationDefinitionInsurer.UnitValue \n");
        sb.append("    , ChartObservationDefinition.ChartDefinitionId \n");
        sb.append("    , ChartObservationDefinition.LineColor \n");
        sb.append("from  \n");
        sb.append("    ChartDefinition \n");
        sb.append("    inner join ChartObservationDefinition on \n");
        sb.append("        ChartObservationDefinition.ChartDefinitionId = ChartDefinition.ChartDefinitionId \n");
        sb.append("    inner join ObservationDefinition on \n");
        sb.append("        ObservationDefinition.ObservationDefinitionId = ChartObservationDefinition.ObservationDefinitionId \n");
        sb.append("    inner join ObservationDefinitionInsurer on \n");
        sb.append("        ObservationDefinitionInsurer.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("where \n");
        sb.append("    ChartDefinition.ChartDefinitionId = ? \n");
        sb.append("order by \n");
        sb.append("    ObservationDefinition.ObservationDefinitionId \n");
        
        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, chartDefinitionId);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return list;
        }
        
        while( dataTable.next() ) {
            ChartObservationDefinitionEntity entity = new ChartObservationDefinitionEntity();
            entity.setChartDefinitionId(getLong(dataTable, "ChartDefinitionId"));
            entity.setObservationDefinitionId(getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionName(getString(dataTable, "DisplayName"));
            entity.setUnitValue(getString(dataTable, "UnitValue"));
            entity.setLineColor(getString(dataTable, "LineColor"));

            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;        
    }

    /**
     * グラフ項目定義を全て取得する（定義編集用）新規
     * @param insurerNo
     * @param chartDefinitionId
     * @return
     * @throws SQLException 
     */
    public List<ChartObservationDefinitionEntity> searchAllChartObservationDefinition(String insurerNo) throws SQLException {
        
        List<ChartObservationDefinitionEntity> list =  new ArrayList<>();
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct \n");
        sb.append("    ObservationDefinition.ObservationDefinitionId \n");
        sb.append("    , ObservationDefinition.DisplayName \n");
        sb.append("    , ObservationDefinitionInsurer.UnitValue \n");
        sb.append("    , ObservationDefinitionType.DataInputTypeCd \n");
        sb.append("from \n");
        sb.append("    ObservationDefinition \n");
        sb.append("    inner join ObservationDefinitionInsurer on \n");
        sb.append("        ObservationDefinitionInsurer.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("        and ObservationDefinitionInsurer.DataTypeCd = 2 \n");
        sb.append("    inner join InsurerViewDefinition on \n");
        sb.append("        InsurerViewDefinition.ViewId = ObservationDefinitionInsurer.ViewId \n");
        sb.append("    inner join ObservationDefinitionType on \n");
        sb.append("        ObservationDefinitionType.InsurerNo = InsurerViewDefinition.InsurerNo \n");
        sb.append("        and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("where  \n");
        sb.append("    InsurerViewDefinition.InsurerNo = ? \n");

        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return list;
        }
        
        while( dataTable.next() ) {
            ChartObservationDefinitionEntity entity = new ChartObservationDefinitionEntity();
            entity.setObservationDefinitionId(getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionName(getString(dataTable, "DisplayName"));
            entity.setUnitValue(getString(dataTable, "UnitValue"));
            entity.setDataInputTypeCd(getString(dataTable, "DataInputTypeCd"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;

    }

    /**
     * グラフ項目定義を"1"、"2"、"3"以外の全て取得する（定義編集用）新規
     * @param insurerNo
     * @param chartDefinitionId
     * @return
     * @throws SQLException 
     */
    public List<ChartObservationDefinitionEntity> searchChartObservationDefinition(String insurerNo) throws SQLException {
        
        List<ChartObservationDefinitionEntity> list =  new ArrayList<>();
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select distinct \n");
        sb.append("    ObservationDefinition.ObservationDefinitionId \n");
        sb.append("    , ObservationDefinition.DisplayName \n");
        sb.append("    , ObservationDefinitionInsurer.UnitValue \n");
        sb.append("from \n");
        sb.append("    ObservationDefinition \n");
        sb.append("    inner join ObservationDefinitionInsurer on \n");
        sb.append("        ObservationDefinitionInsurer.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("        and ObservationDefinitionInsurer.DataTypeCd = 2 \n");
        sb.append("    inner join InsurerViewDefinition on \n");
        sb.append("        InsurerViewDefinition.ViewId = ObservationDefinitionInsurer.ViewId \n");
        sb.append("    inner join ObservationDefinitionType on \n");
        sb.append("        ObservationDefinitionType.InsurerNo = InsurerViewDefinition.InsurerNo \n");
        sb.append("        and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \n");
        sb.append("where  \n");
        sb.append("    InsurerViewDefinition.InsurerNo = ? \n");
        sb.append("    and ObservationDefinitionType.DataInputTypeCd != '1' \n");
        sb.append("    and ObservationDefinitionType.DataInputTypeCd != '2' \n");
        sb.append("    and ObservationDefinitionType.DataInputTypeCd != '3' \n");

        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return list;
        }
        
        while( dataTable.next() ) {
            ChartObservationDefinitionEntity entity = new ChartObservationDefinitionEntity();
            entity.setObservationDefinitionId(getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionName(getString(dataTable, "DisplayName"));
            entity.setUnitValue(getString(dataTable, "UnitValue"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;

    }
}
