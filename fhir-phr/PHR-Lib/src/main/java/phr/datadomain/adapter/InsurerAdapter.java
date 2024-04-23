/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者情報のデータオブジェクト
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.InsurerEntity;

/**
 * 保険者情報のデータオブジェクトです。
 */
public class InsurerAdapter extends InsurerAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public InsurerAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * PHR_IDにて、加入している保険者を検索します。
     * @param phrId
     * @return
     * @throws Throwable
     */
    public InsurerEntity findByPatientPhrId(String phrId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " inner join InsurerPatient on ";
        sql += "	Insurer.InsurerNo = InsurerPatient.InsurerNo ";
        sql += " where InsurerPatient.PHR_ID = ? ";
        sql += " and Insurer.Invalid = ?";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);
        preparedStatement.setBoolean(2, false);

        InsurerEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = InsurerEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
    /* -------------------------------------------------------------------------------------- */

    /*
    保険者情報を全取得
    */
    public List<InsurerEntity> getInsurerList() throws SQLException, Throwable{
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    Insurer.InsurerNo As InsurerNo  \r\n");
        sql.append("    , Insurer.InsurerName As InsurerName  \r\n");
        sql.append("    , Insurer.Invalid As Invalid  \r\n");
        sql.append("from Insurer  \r\n");
        sql.append(" where Insurer.Invalid = 0 \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        InsurerEntity entity = null;
        List<InsurerEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = InsurerEntity.setData(dataTable);
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;

    }
    
    /*
    保険者情報を全取得
    */
    public InsurerEntity getInsurer(String insureNo) throws SQLException, Throwable{
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where Insurer.InsurerNo = ? ";
        sql += " and Insurer.Invalid = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insureNo);
        preparedStatement.setBoolean(2, false);
        
        InsurerEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = InsurerEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;

    }
}
