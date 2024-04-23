/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者採番情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.InsurerNumberingEntity;
import phr.datadomain.entity.InsurerPatientEntity;

/**
 * 保険者採番情報のデータオブジェクトです。
 */
public class InsurerNumberingAdapter extends InsurerNumberingAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerNumberingAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public InsurerNumberingAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * 保険者番号にてPHR IDを採番します
     * @param insurerNo
     * @return
     * @throws Throwable
     */
    public String numberingPhrId(String insurerNo) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where InsurerNumbering.InsurerNo = ?";
        sql += " for update";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);

        InsurerNumberingEntity entity = null;
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = InsurerNumberingEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        if (entity == null || entity.getPHR_ID() == null) {
            return null;
        }
        
        int phrId = entity.getPHR_ID();
        phrId++;
        entity.setPHR_ID(phrId);
        
        int rowCnt = this.update(entity);
        if (rowCnt == 0)
            return null;
        
        String returnOhrId = String.format("%06d", phrId);
        logger.trace("End");
        return returnOhrId;
    }
}
