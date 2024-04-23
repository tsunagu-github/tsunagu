/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.DosageAttentionEntity;
import phr.datadomain.entity.ObservationLabelEntity;

/**
 *
 * @author kis-note
 */
public class ObsevationLabelAdapter {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObsevationLabelAdapter.class);
  /* -------------------------------------------------------------------------------------- */
     /**
     * データベースコネクション
     */
    protected Connection connection = null;
    /**
     * コンストラクタ
     * @param conn
     */
    public ObsevationLabelAdapter(Connection conn)
    {
        connection = conn;
    }
    
       /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationDefinitionInsurer.ObservationDefinitionId  \r\n");
        sql.append("    ,ObservationDefinition.ObservationDefinitionName  \r\n");
        sql.append("    ,ObservationDefinition.DisplayName  \r\n");
        sql.append("    ,ObservationDefinitionDisease.DiseaseTypeCd  \r\n");
        sql.append("    ,ObservationDefinitionType.DataInputTypeCd  \r\n");
        sql.append("	from ObservationDefinitionInsurer \r\n");
        sql.append("	inner join ObservationDefinition on \r\n");
        sql.append("	ObservationDefinition.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId \r\n");
        sql.append("	inner join ObservationDefinitionDisease on \r\n");
        sql.append("	ObservationDefinitionDisease.InsurerNo = ObservationDefinitionInsurer.InsurerNo \r\n");
        sql.append("	and ObservationDefinitionDisease.Year = ObservationDefinitionInsurer.Year \r\n");
        sql.append("	and ObservationDefinitionDisease.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId \r\n");
        sql.append("	inner join ObservationDefinitionType on \r\n");
        sql.append("	ObservationDefinitionType.InsurerNo = ObservationDefinitionInsurer.InsurerNo \r\n");
        sql.append("	and ObservationDefinitionType.Year = ObservationDefinitionInsurer.Year \r\n");
        sql.append("	and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId \r\n");
        return sql.toString();
    }
        
    /**
     * 保健者番号、年度から検査項目の一覧を取得します。
     * @param key1
     * @param key2
     * @param key3
     * @return
     * @throws Throwable
     */
    public List<ObservationLabelEntity> findByLabel(String insurerno, int year) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionInsurer.InsurerNo = ?";
        sql += "   and ObservationDefinitionInsurer.Year = ?";
        sql += "     ORDER BY SortNo ASC";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerno);
        preparedStatement.setInt(2, year);

        List<ObservationLabelEntity> entitylist = new ArrayList<ObservationLabelEntity>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entitylist.add(ObservationLabelEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entitylist;
    }
}
