/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：手帳メモのデータオブジェクト
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.DosageNoteEntity;

/**
 * 手帳メモのデータオブジェクトです。
 */
public class DosageNoteAdapter extends DosageNoteAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageNoteAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageNoteAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     *dosageIdの値にて手帳メモの情報を削除します。
     * @param entity
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

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }
    
    /**
     * DosageIdにて手帳メモを検索します。
     * @param dosageId
     * @return
     * @throws Throwable
     */
    public List<DosageNoteEntity> findByDosageId(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosageNote.DosageId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);

        List<DosageNoteEntity> noteList = new ArrayList();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
           DosageNoteEntity entity = DosageNoteEntity.setData(dataTable);
           noteList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return noteList;
    }    

    /**
     * DosageIdでDosageNoteテーブルからレコードを削除します。
     * @param dosageId
     * @return rowCount
     * @throws Throwable
     */
    public int deleteDosageNoteRecord(String dosageId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  DosageNote \r\n");
        sb.append("where DosageNote.DosageId = ? \r\n");
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
