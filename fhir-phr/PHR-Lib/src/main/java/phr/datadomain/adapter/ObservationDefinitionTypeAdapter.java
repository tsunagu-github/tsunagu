/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
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
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;

/**
 * 管理項目種別のデータオブジェクトです。
 */
public class ObservationDefinitionTypeAdapter extends ObservationDefinitionTypeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionTypeAdapter.class);    
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionTypeAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * 保険者番号、年度にて管理項目種別を検索します。
     * @param insurerNo
     * @param viewID
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionTypeEntity> findByInsurerNoYear(String insurerNo, int viewId, int dataInputTypeCd) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql = sql.replaceFirst("select  \r\n", "select DISTINCT \r\n");
        sql = sql.replaceFirst("from ObservationDefinitionType \r\n", " , ObservationDefinition.DisplayName as DisplayName \r\n");
        sql += " , ObservationDefinitionType.UnitValue as UnitValue \r\n";
        sql += " , ObservationDefinitionInsurer.SortNo \r\n";
        sql += " from ObservationDefinitionType \r\n";
        sql += " inner join ObservationDefinitionInsurer on ";
//        sql += "     ObservationDefinitionInsurer.InsurerNo = ObservationDefinitionType.InsurerNo ";
//        sql += "     and ObservationDefinitionInsurer.Year = ObservationDefinitionType.Year ";
        sql += "      ObservationDefinitionInsurer.ObservationDefinitionId = ObservationDefinitionType.ObservationDefinitionId ";
        sql += "      and ObservationDefinitionInsurer.ViewId = ? ";
        sql += " inner join ObservationDefinition on ";
        sql += "     ObservationDefinition.ObservationDefinitionId = ObservationDefinitionType.ObservationDefinitionId ";
        sql += " where ObservationDefinitionType.InsurerNo = ?";
//viewId?        sql += "   and ObservationDefinitionType.Year = ?";
        sql += "   and ObservationDefinitionType.DataInputTypeCd = ?";
        sql += " order by ObservationDefinitionInsurer.SortNo ASC";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);
        preparedStatement.setString(2, insurerNo);
        preparedStatement.setInt(3, dataInputTypeCd);


        List<ObservationDefinitionTypeEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationDefinitionTypeEntity entity = ObservationDefinitionTypeEntity.setData(dataTable);
             entity.setDisplayName(getString(dataTable, "DisplayName"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
    
    /**
     * 保険者番号、年度から検体検査及び家庭測定のリストを取得する
     * @param insurerNo
     * @param year
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionTypeEntity> findByInsurerList(String insurerNo, Integer typeCd) throws Throwable{
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql ="select \r\n";
        sql +="    ObservationDefinitionType.InsurerNo As InsurerNo  \r\n";
        sql +="    , ObservationDefinitionType.ObservationDefinitionId As ObservationDefinitionId  \r\n";
        sql +="    , ObservationDefinitionType.DataInputTypeCd As DataInputTypeCd  \r\n";
        sql +="    , ObservationDefinition.DisplayName As DisplayName \r\n";
        sql +="    , ObservationDefinition.SortNo As SortNo \r\n";
        sql +="    , CONCAT(left(ObservationDefinitionJlac10.JLAC10, 5),'000000000000') As JLAC10 \r\n";
        sql += "   , ObservationDefinitionType.UnitValue as UnitValue \r\n";
        sql +="from ObservationDefinitionType \r\n";
        sql +="inner join ObservationDefinition on \r\n";
        sql +="  ObservationDefinitionType.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId\r\n";
        sql +="left join ObservationDefinitionJlac10 on \r\n";
        sql +="  ObservationDefinition.ObservationDefinitionId = ObservationDefinitionJlac10.ObservationDefinitionId\r\n";        
        sql +="where \r\n";
        sql +="   ObservationDefinitionType.InsurerNo = ? \r\n";
        sql +="   and ObservationDefinitionType.DataInputTypeCd = ? \r\n";
        sql +="  ORDER BY ObservationDefinition.SortNo ASC \r\n";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        preparedStatement.setInt(2, typeCd);


        List<ObservationDefinitionTypeEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationDefinitionTypeEntity entity = ObservationDefinitionTypeEntity.setData(dataTable);
            entity.setDisplayName(AbstractEntity.getString(dataTable, "DisplayName"));
            entity.setSortNo(AbstractEntity.getInt(dataTable, "SortNo"));
            entity.setJlac10(AbstractEntity.getString(dataTable, "JLAC10"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;     
    }

    /**
     * IDとデータ種別で抽出
     * @param observationDefinitionId
     * @param dataInputTypeCd
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionTypeEntity findById(String observationDefinitionId, int dataInputTypeCd) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append(" select * from ObservationDefinitionType \r\n");
        sb.append(" where ObservationDefinitionType.ObservationDefinitionId = ? \r\n");
        sb.append("   and ObservationDefinitionType.DataInputTypeCd = ? \r\n");
        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationDefinitionId);
        preparedStatement.setInt(2, dataInputTypeCd);

        ObservationDefinitionTypeEntity entity = new ObservationDefinitionTypeEntity();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionTypeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }
}
