/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：用法補足情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/10/17
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
import phr.datadomain.entity.SeparatorInfoEntity;

/**
 * 用法補足情報のデータオブジェクトです。
 */
public class SeparatorInfoAdapter extends SeparatorInfoAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SeparatorInfoAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public SeparatorInfoAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * dosageIdの値にてセパレータ情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deleteByDosageId(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sqlorg = getDeleteSql();
        int idx = sqlorg.indexOf("where");
        String sql = sqlorg.substring(0, idx);
        sql += "where SeparatorInfo.DosageId = ? \r\n";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }
    
    
    /**
     * separatorIdの値にてセパレータ情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deleteBySeparatorId(Long separatorId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sqlorg = getDeleteSql();
        int idx = sqlorg.indexOf("where");
        String sql = sqlorg.substring(0, idx);
        sql += "where SeparatorInfo.SeparatorId = ? \r\n";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, separatorId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }
    
        
    /**
     * セパレータIDにてセパレータ情報を検索します。
     * @param separatorId
     * @return
     * @throws Throwable
     */
    public List<SeparatorInfoEntity> findBySeparatorId(long separatorId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where SeparatorInfo.SeparatorId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, separatorId);

        List<SeparatorInfoEntity> entitylist = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            SeparatorInfoEntity entity = SeparatorInfoEntity.setData(dataTable);
            entitylist.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entitylist;
    }
 
    
   /**
     * 対象のSeparatorInfoが何件登録されているか、件数を返します。
     * @param separatorId
     * @return
     * @throws Throwable
     */
    public int getSeparatorInfoCount(Long separatorId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "select count(SeparatorInfo.SeparatorId) as count from SeparatorInfo where SeparatorInfo.SeparatorId = ? \r\n";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, separatorId);
        
        int count = 0;
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return -1;
                }
                while( dataTable.next() ) {
                    count = AbstractEntity.getInt(dataTable, "count");
                }
            }
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return count;
    }    

    /**
     * DosageIdでSeparatorInfoテーブルからレコードを削除します。
     * @param dosageId
     * @return rowCount
     * @throws Throwable
     */
    public int deleteSeparatorInfoRecord(String dosageId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  SeparatorInfo \r\n");
        sb.append("where SeparatorInfo.DosageId = ? \r\n");
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
