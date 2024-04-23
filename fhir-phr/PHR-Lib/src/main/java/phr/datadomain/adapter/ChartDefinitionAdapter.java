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
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ChartDefinitionEntity;

/**
 * グラフ定義のデータオブジェクトです。
 */
public class ChartDefinitionAdapter extends ChartDefinitionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartDefinitionAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ChartDefinitionAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * コミュニケーションIDを採番する
     * @return
     * @throws Throwable 
     */
    public static long numberingChartDefinitionId() throws Throwable {
        long id = -1;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            
            String sql = "update Seq_ChartDefinitionId set ChartDefinitionId=LAST_INSERT_ID(ChartDefinitionId+1)";
            dao.setSql(sql);
            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            int rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
            
            String sql2 = "SELECT LAST_INSERT_ID() as SeqId";
            dao.setSql(sql2);
            dao.clearBindParam();
            
            PreparedStatement preparedStatement2 = dao.getPreparedStatement();
            ResultSet dataTable = preparedStatement2.executeQuery();
            if (dataTable == null) {
                return id;
            }

            while (dataTable.next()) {
                id = AbstractEntity.getLong(dataTable, "SeqId");
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement2.close();
            dao.commitTransaction();
            
            if (id == 0 || id == 1) {
//                String sql3 = "update Seq_ChartDefinitionId set ChartDefinitionId = '5'";
                String sql3 = "insert into  Seq_ChartDefinitionId value ('5')";
                dao.setSql(sql3);
                dao.clearBindParam();
                PreparedStatement preparedStatement3 = dao.getPreparedStatement();
                int rowCount2 = preparedStatement3.executeUpdate();
                preparedStatement3.close();
                
                dao.setSql(sql);
                dao.clearBindParam();
                PreparedStatement preparedStatement4 = dao.getPreparedStatement();
                int rowCount3 = preparedStatement4.executeUpdate();
                preparedStatement4.close();
                
                dao.setSql(sql2);
                dao.clearBindParam();
                PreparedStatement preparedStatement5 = dao.getPreparedStatement();
                ResultSet dataTable2 = preparedStatement5.executeQuery();
                if (dataTable2 == null) {
                    return id;
                }

                while (dataTable2.next()) {
                    id = AbstractEntity.getLong(dataTable2, "SeqId");
                }
                dao.clearBindParam();
                dataTable2.close();
                preparedStatement5.close();
                dao.commitTransaction();
            }
            return id;
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }

    /**
     * グラフリストを取得する
     * @param insurerNo
     * @param phrId
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<ChartDefinitionEntity> getChartList(String insurerNo, String phrId) throws SQLException, Throwable {
        List<ChartDefinitionEntity> list = new ArrayList<>();
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ChartDefinition.PHR_ID = ? ";
        sql += " or (ChartDefinition.CommonFlg = 1 and ChartDefinition.InsurerNo = ? ) ";
        sql += " order by ChartDefinitionId ";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);
        preparedStatement.setString(2, insurerNo);
       
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ChartDefinitionEntity entity = ChartDefinitionEntity.setData(dataTable);
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;

    }

}
