/*
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目JLAC10のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.datadomain.adapter;

import static phr.datadomain.AbstractEntity.getString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ChartObservationDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionJlac10Entity;

/**
 * 管理項目JLAC10のデータオブジェクトです。
 */
public class ObservationDefinitionJlac10Adapter extends ObservationDefinitionJlac10AdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationDefinitionJlac10Adapter.class);
    private static Logger logger = Logger.getLogger(ObservationDefinitionJlac10Adapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public ObservationDefinitionJlac10Adapter(Connection conn) {
        super(conn);
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 全データを取得
     * @return
     * @throws Throwable 
     */
    public List<ObservationDefinitionJlac10Entity> findByInsurerNoYearInputType(String insurerNo , int year) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        /*
        sql += " inner join ObservationDefinitionType on ";
        sql += "     ObservationDefinitionType.ObservationDefinitionId = ObservationDefinitionJlac10.ObservationDefinitionId ";
        sql += " where ObservationDefinitionType.InsurerNo = ?";
        sql += "   and ObservationDefinitionType.Year = ?";
        sql += "   and ObservationDefinitionType.DataInputTypeCd = 1";
        */
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        //preparedStatement.setString(1, insurerNo);
        //preparedStatement.setInt(2, year);

        List<ObservationDefinitionJlac10Entity> resList = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        resList = new ArrayList<>();

        while (dataTable.next()) {
            ObservationDefinitionJlac10Entity entity = ObservationDefinitionJlac10Entity.setData(dataTable);
            resList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resList;

    }
    /**
     * <pre>JLAC10コードより項目IDを取得する。
     * @param insurerNo
     * @param year
     * @param code //JLAC10コード
     * @return  // 項目ID
     * @throws Throwable 
     */
    public String getObservationDefinitionId(String insurerNo,String year,String code) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        /*
        sql += "inner join ObservationDefinitionType on "; 
	sql += "ObservationDefinitionType.InsurerNo = ? ";
	sql += "and ObservationDefinitionType.year = ? ";
	sql += "and ObservationDefinitionType.DataInputTypeCd >= ? ";
	sql += "and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinitionJlac10.ObservationDefinitionId";
        */
        sql += " where ObservationDefinitionJlac10.JLAC10 = ?";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        //preparedStatement.setString(1, insurerNo);
        //preparedStatement.setInt(2, Integer.parseInt(year));
        //preparedStatement.setInt(3, 3);
        //preparedStatement.setString(4, code);
        preparedStatement.setString(1, code);

        ObservationDefinitionJlac10Entity entity = null;
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            entity = ObservationDefinitionJlac10Entity.setData(dataTable);
        }
        
        String ObservationDefinitionId = entity.getObservationDefinitionId();
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return ObservationDefinitionId;
    }

    /**
     * <pre>対象保険者、年度よりJLAC10コードと項目IDのMapを取得する
     * @param insurerNo
     * @param year
     * @return  // 項目ID
     * @throws Throwable 
     */
    public Map<String,String> getJLACMap(String insurerNo) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        /*
        sql += "inner join ObservationDefinitionType on "; 
	sql += "ObservationDefinitionType.InsurerNo = ? ";
	sql += "and ObservationDefinitionType.DataInputTypeCd >= ? ";
	sql += "and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinitionJlac10.ObservationDefinitionId";
        */
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        //preparedStatement.setString(1, insurerNo);
        //preparedStatement.setInt(2, 3);

        ObservationDefinitionJlac10Entity entity = null;
        Map<String , String > resultMap = new HashMap<String,String>();
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            entity = ObservationDefinitionJlac10Entity.setData(dataTable);
            resultMap.put(entity.getJLAC10(), entity.getObservationDefinitionId());
        }
        
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resultMap;
    }
    
    /**
     * 主キーにて管理項目JLAC10を検索する
     * @param key
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionJlac10Entity findByPrimaryKey(String key) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionJlac10.ObservationDefinitionId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key);

        ObservationDefinitionJlac10Entity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionJlac10Entity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }

    /**
     * 管理項目JLAC10と一致するものを検索する
     * @param code
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionJlac10Entity findJLAC10(String code, int i) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationDefinitionJlac10.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , ObservationDefinitionJlac10.JLAC10 As JLAC10  \r\n");
        sql.append("from ObservationDefinitionJlac10 \r\n");
        sql.append("where ObservationDefinitionJlac10.JLAC10 = ?");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, code);

        ObservationDefinitionJlac10Entity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionJlac10Entity.setData(dataTable, i);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }

    /**
     * 管理項目JLAC11と一致するものを検索する
     * @param code
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionJlac10Entity findJLAC11(String code, int i) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationDefinitionJlac10.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , ObservationDefinitionJlac10.JLAC11 As JLAC11  \r\n");
        sql.append("from ObservationDefinitionJlac10 \r\n");
        sql.append("where ObservationDefinitionJlac10.JLAC11 = ?");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, code);

        ObservationDefinitionJlac10Entity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionJlac10Entity.setData(dataTable, i);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
    
    /**
     * 主キーにて管理項目JLAC10・JLAC11を全て取得する
     * @param insurerNo
     * @param chartDefinitionId
     * @return
     * @throws SQLException 
     */
    public List<ObservationDefinitionJlac10Entity> findByPrimaryKeyGetAll(String key) throws SQLException {
        
        List<ObservationDefinitionJlac10Entity> list =  new ArrayList<>();
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionJlac10.ObservationDefinitionId = ?";
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key);
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return list;
        }
        
        while( dataTable.next() ) {
                ObservationDefinitionJlac10Entity entity = new ObservationDefinitionJlac10Entity();
            entity.setObservationDefinitionId(getString(dataTable, "ObservationDefinitionId"));
            entity.setJLAC10(getString(dataTable, "JLAC10"));
            entity.setJLAC11(getString(dataTable, "JLAC11"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
}
