/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者View定義情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/16
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.InsurerViewDefinitionAdapterBase;

//import static phr.datadomain.adapter.base.InsurerPatientAdapterBase.getSelectedSql;
import phr.utility.TypeUtility;

import phr.datadomain.entity.InsurerViewDefinitionEntity;

/**
 * 保険者View定義情報のデータオブジェクトです。
 */
public class InsurerViewDefinitionAdapter extends InsurerViewDefinitionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerViewDefinitionAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public InsurerViewDefinitionAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    public List<InsurerViewDefinitionEntity> findViewList(String insurerNo) throws SQLException, Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where InsurerViewDefinition.InsurerNo = ?";
        sql += "and (InsurerViewDefinition.ViewId >= 100 ";
        sql += "or   InsurerViewDefinition.ViewId = 0) ";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        
        List<InsurerViewDefinitionEntity> entityList = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            InsurerViewDefinitionEntity entity = InsurerViewDefinitionEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }

    public List<InsurerViewDefinitionEntity> findAll() throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        List<InsurerViewDefinitionEntity> entityList = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            InsurerViewDefinitionEntity entity = InsurerViewDefinitionEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }

    public List<InsurerViewDefinitionEntity> findViewListbyInsureAndYear(String insurerNo,int year) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where InsurerViewDefinition.InsurerNo = ?";
        sql += "and (InsurerViewDefinition.StartYear <= ? ";
        sql += "and InsurerViewDefinition.EndYear >= ? )";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        preparedStatement.setInt(2, year);
        preparedStatement.setInt(3, year);
        
        List<InsurerViewDefinitionEntity> entityList = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            InsurerViewDefinitionEntity entity = InsurerViewDefinitionEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;        
    }

}
