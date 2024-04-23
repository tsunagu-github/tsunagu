/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：連携システムデータ取得タイミング情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.DataAccessTimingEntity;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.execute.DataAccessExecute;

/**
 * 連携システムデータ取得タイミング情報のデータオブジェクトです。
 */
@Repository
public class DataAccessTimingAdapter extends DataAccessTimingAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(DataAccessTimingAdapter.class);
	private static Logger logger = Logger.getLogger(DataAccessTimingAdapter.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DataAccessTimingAdapter() {
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DataAccessTimingAdapter(Connection conn)
    {
    	connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */

     /**
     * 全レコードを取得
     * @return　list
     * @throws Throwable
     */
    public List<DataAccessTimingEntity> getRecords() throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject();
        String sql = getSelectedSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        List<DataAccessTimingEntity> entityList = new ArrayList<DataAccessTimingEntity>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entityList.add(DataAccessTimingEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entityList;
    }
}
