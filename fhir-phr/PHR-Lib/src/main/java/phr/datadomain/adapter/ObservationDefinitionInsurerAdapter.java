/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者管理項目情報のデータオブジェクト
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.datadomain.entity.ObservationLabelEntity;

/**
 * 保険者管理項目情報のデータオブジェクトです。
 */
public class ObservationDefinitionInsurerAdapter extends ObservationDefinitionInsurerAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionInsurerAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionInsurerAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * 保険者管理項目情報の中の自動計算項目を検索します。
     * @param insureNo
     * @param year
     * @return
     * @throws Throwable
     */
    public List<String> findActoCalc(String insureNo,int year) throws Throwable {
        
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "";
        sql += " select distinct \n";
        sql += "     ObservationDefinitionInsurer.ObservationDefinitionId \n";
        sql += " from ObservationDefinitionInsurer \n";
        sql += " inner join InsurerViewDefinition on \n";
        sql += "     InsurerViewDefinition.ViewId = ObservationDefinitionInsurer.ViewId \n";
        sql += " where InsurerViewDefinition.InsurerNo = ? \n";
        sql += "   and InsurerViewDefinition.StartYear <= ? \n";
        sql += "   and (InsurerViewDefinition.EndYear >= ? or InsurerViewDefinition.EndYear is null) \n";
        sql += "   and ObservationDefinitionInsurer.DataTypeCd = ? \n";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insureNo);
        preparedStatement.setInt(2, year);
        preparedStatement.setInt(3, year);
        preparedStatement.setString(4, "4");

        List<String> result = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            String id =dataTable.getString("ObservationDefinitionId".toUpperCase());
            result.add(id);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return result;
    }

    /**
     * ビューIDにて保険者管理項目情報を検索します。
     * @param viewId
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionEntity> findByViewId(int viewId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("  t_od.* ");
        sql.append("from ");
        sql.append("  ObservationDefinitionInsurer t_odi ");
        sql.append("    inner join ");
        sql.append("  ObservationDefinition t_od ");
        sql.append("    on ");
        sql.append("      t_od.ObservationDefinitionId = t_odi.ObservationDefinitionId ");
        sql.append("where ");
        sql.append("  t_odi.ViewId = ? ");
        sql.append("order by ");
        sql.append("  t_odi.SortNo ");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);

        List<ObservationDefinitionEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            list.add(ObservationDefinitionEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }

    /**
     * ビューIDにて有効な保険者管理項目情報を検索します。
     * @param viewId
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionEntity> findEnabledByViewId(int viewId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct ");
        sql.append("  t_od.* ");
        sql.append("from ");
        sql.append("  ObservationDefinitionInsurer t_odi ");
        sql.append("    inner join ");
        sql.append("  ObservationDefinition t_od ");
        sql.append("    on ");
        sql.append("      t_od.ObservationDefinitionId = t_odi.ObservationDefinitionId ");
        sql.append("    inner join ");
        sql.append("  DiseaseType t_dt ");
        sql.append("    on ");
        sql.append("      t_dt.ViewId = t_odi.ViewId ");
        sql.append("    inner join ");
        sql.append("  InsurerDiseaseType t_idt ");
        sql.append("    on ");
        sql.append("          t_idt.ViewId = t_odi.ViewId ");
        sql.append("      and t_idt.ObservationDefinitionId = t_odi.ObservationDefinitionId ");
        sql.append("      and t_idt.DiseaseTypeCd = t_dt.DiseaseTypeCd ");
        sql.append("where ");
        sql.append("  t_odi.ViewId = ? ");
        sql.append("order by ");
//        sql.append("  t_odi.SortNo ");
        sql.append("  t_od.SortNo ");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);

        List<ObservationDefinitionEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            list.add(ObservationDefinitionEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }

    /**
     * 主キーにて順序を更新します。
     * @param viewId
     * @param observationDefinitionId
     * @param sortNo
     * @return
     * @throws Throwable
     */
    public int updateSortNo(int viewId, String observationDefinitionId, int sortNo) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "update ObservationDefinitionInsurer set SortNo = ? " +
                "where ViewId = ? and ObservationDefinitionId = ?";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setInt(1, sortNo);
        preparedStatement.setInt(2, viewId);
        preparedStatement.setString(3, observationDefinitionId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * ビューIDにて保険者管理項目情報と関連リマインダを検索します。
     * @param viewId
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionInsurerEntity> findObservationRemindersByViewId(int viewId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("  t_odi.*, ");
        sql.append("  t_od.DisplayName, ");
        sql.append("  group_concat( ");
        sql.append("    case ");
        sql.append("      when t_rm_ri.ReminderTypeId is null then ");
        sql.append("        '' ");
        sql.append("      else ");
        sql.append("        concat(concat(t_rm_ri.ReminderTitle, '：'), t_rm_ri.ReminderTypeName) ");
        sql.append("    end ");
        sql.append("    order by ");
        sql.append("      t_rm_ri.ReminderTitle, ");
        sql.append("      t_rm_ri.ReminderTypeName ");
        sql.append("    separator ");
        sql.append("      '\\n') Reminders ");
        sql.append("from ");
        sql.append("  ObservationDefinitionInsurer t_odi ");
        sql.append("    inner join ");
        sql.append("  ObservationDefinition t_od ");
        sql.append("    on ");
        sql.append("      t_od.ObservationDefinitionId = t_odi.ObservationDefinitionId ");
        sql.append("    left outer join ");
        sql.append("  ( ");
        sql.append("    select ");
        sql.append("      t_rm.ReminderTypeId, ");
        sql.append("      t_rm.ViewId, ");
        sql.append("      t_rm.ReminderTitle, ");
        sql.append("      t_ri.ObservationDefinitionId, ");
        sql.append("      t_rt.Name ReminderTypeName ");
        sql.append("    from ");
        sql.append("      ReminderMaster t_rm ");
        sql.append("        inner join ");
        sql.append("      ReminderItem t_ri ");
        sql.append("        on ");
        sql.append("          t_ri.ReminderTypeId = t_rm.ReminderTypeId ");
        sql.append("        inner join ");
        sql.append("      ReminderType t_rt ");
        sql.append("        on ");
        sql.append("          t_rt.ReminderTypeCd = t_rm.ReminderTypeCd ");
        sql.append("  ) t_rm_ri ");
        sql.append("    on ");
        sql.append("          t_rm_ri.ViewId = t_odi.ViewId ");
        sql.append("      and t_rm_ri.ObservationDefinitionId = t_odi.ObservationDefinitionId ");
        sql.append("where ");
        sql.append("  t_odi.ViewId = ? ");
        sql.append("group by ");
        sql.append("  t_odi.ObservationDefinitionId, ");
        sql.append("  t_od.DisplayName, ");
        sql.append("  t_odi.UnitValue, ");
        sql.append("  t_odi.MinReferenceValue, ");
        sql.append("  t_odi.MaxReferenceValue ");
        sql.append("order by ");
        sql.append("  t_odi.SortNo, ");
        sql.append("  t_odi.ObservationDefinitionId ");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);

        List<ObservationDefinitionInsurerEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationDefinitionInsurerEntity entity =
                    ObservationDefinitionInsurerEntity.setData(dataTable);
            entity.setDisplayName(
                    AbstractEntity.getString(dataTable, "DisplayName"));
            entity.setReminders(
                    AbstractEntity.getString(dataTable, "Reminders"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
    
   /**
     * 患者指定画面用に検査結果、家庭測定、特定健診の項目リストを取得する。
     * リストには管理疾病の情報も付加する
     * @param viewId
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionInsurerEntity> findItemList(int viewId) throws Throwable {
        
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append(" select ");
        sb.append("     ObservationDefinitionInsurer.* ");
        sb.append("     , InsurerDiseaseType.DiseaseTypeCd ");
        sb.append("     , ObservationDefinition.DisplayName As DisplayName ");
        sb.append("     , ObservationDefinitionInsurer.DefaultJLAC10 As DefaultJLAC10 ");
        sb.append(" from ObservationDefinitionInsurer  ");
        sb.append("  left join InsurerDiseaseType on ");
        sb.append("        ObservationDefinitionInsurer.ViewId = InsurerDiseaseType.ViewId ");
        sb.append("    and ObservationDefinitionInsurer.ObservationDefinitionId = InsurerDiseaseType.ObservationDefinitionId  ");
        sb.append("  inner join ObservationDefinition on ");
        sb.append("        ObservationDefinitionInsurer.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId ");
        
        String sql = sb.toString();
        sql += " where ObservationDefinitionInsurer.ViewId = ?";
        sql += " ORDER BY ObservationDefinitionInsurer.SortNo ASC";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);

        List<ObservationDefinitionInsurerEntity> resultList = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        ObservationDefinitionInsurerEntity entity = null;
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionInsurerEntity.setData(dataTable);
            entity.setDiseaseTypeCd(AbstractEntity.getInt(dataTable, "DiseaseTypeCd"));
            entity.setDisplayName(AbstractEntity.getString(dataTable, "DisplayName"));
            resultList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return resultList;
    }        
}
