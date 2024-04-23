/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：一般用医薬品のデータオブジェクト
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
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;


/**
 * 一般用医薬品のデータオブジェクトです。
 */
public class NonPrescriptionDrugsAdapter extends NonPrescriptionDrugsAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NonPrescriptionDrugsAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public NonPrescriptionDrugsAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     *指定した調剤IDの市販薬リストを取得します。
     * @param dosageid
     * @param seq
     * @return List<NonPrescriptionDrugsEntity>
     * @throws Throwable
     */
    public List<NonPrescriptionDrugsEntity> findByDosageid(String dosageid) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "where NonPrescriptionDrugs.DosageId = ? \r\n";
    //    sql += " and NonPrescriptionDrugs.Seq = ?\r\n";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageid);
    //    preparedStatement.setInt(2, Seq);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        
        List<NonPrescriptionDrugsEntity> entityList = new ArrayList();
        while( dataTable.next() ) {
           NonPrescriptionDrugsEntity entity = NonPrescriptionDrugsEntity.setData(dataTable);
           entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }

    /**
     * dosageIdにて一般用医薬品の情報を削除します。
     * @param dosageId
     * @return
     * @throws Throwable
     */
    public int deleteByDosageId(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sqlorg = getDeleteSql();
        int idx = sqlorg.indexOf("AND");
        String sql = sqlorg.substring(0, idx);
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);
     //   preparedStatement.setInt(2, seq);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }    

    /**
     * DosageIdでNonPrescriptionDrugsテーブルからレコードを削除します。
     * @param dosageId
     * @return rowCount
     * @throws Throwable
     */
    public int deleteNonPrescriptionDrugsRecord(String dosageId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  NonPrescriptionDrugs \r\n");
        sb.append("where NonPrescriptionDrugs.DosageId = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, dosageId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
