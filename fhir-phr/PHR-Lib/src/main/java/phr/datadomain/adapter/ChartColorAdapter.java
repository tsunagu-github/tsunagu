/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ色情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2019/05/16
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ChartColorEntity;

/**
 * グラフ色情報のデータオブジェクトです。
 */
public class ChartColorAdapter extends ChartColorAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartColorAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public ChartColorAdapter(Connection conn) {
        super(conn);
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 全件取得する(リスト形式)
     * @return
     * @throws SQLException
     * @throws Throwable 
     */
    public List<ChartColorEntity> findAll() throws SQLException, Throwable {
        List<ChartColorEntity> list = new ArrayList<>();
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }
        while (dataTable.next()) {
            ChartColorEntity entity = ChartColorEntity.setData(dataTable);
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
    /**
     * 全件取得する(MAP形式)
     * @return
     * @throws SQLException
     * @throws Throwable 
     */
    public Map<String, ChartColorEntity> findAllForMap() throws SQLException, Throwable {
        Map<String, ChartColorEntity> map = new LinkedHashMap<>();
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }
        while (dataTable.next()) {
            ChartColorEntity entity = ChartColorEntity.setData(dataTable);
            map.put(entity.getColorCd(), entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return map;
    }
}
