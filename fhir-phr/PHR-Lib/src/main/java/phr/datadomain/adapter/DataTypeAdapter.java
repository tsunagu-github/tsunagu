/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.DataTypeEntity;

/**
 * データ種別のデータオブジェクトです。
 */
public class DataTypeAdapter extends DataTypeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataTypeAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DataTypeAdapter(Connection conn)
    {
        super(conn);
    }
    /**
     * 全件を取得する
     * @return
     * @throws Throwable
     */
    public List<DataTypeEntity> findByAll() throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " order by DataType.DataTypeCd";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        List<DataTypeEntity> list = new ArrayList<DataTypeEntity>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return list;
        }

        while( dataTable.next() ) {
            DataTypeEntity entity = DataTypeEntity.setData(dataTable);
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;       
    }
    /* -------------------------------------------------------------------------------------- */

}
