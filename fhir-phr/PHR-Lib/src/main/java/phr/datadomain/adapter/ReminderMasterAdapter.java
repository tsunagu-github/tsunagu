/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダマスタのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/13
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ReminderMasterEntity;

/**
 * リマインダマスタのデータオブジェクトです。
 */
public class ReminderMasterAdapter extends ReminderMasterAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderMasterAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ReminderMasterAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 年度にてリマインダマスタを検索します。
     * @param year
     * @return
     * @throws Throwable
     */
    public List<ReminderMasterEntity> findByYear(int year) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("  t_rm.*, ");
        sql.append("  t_ivd.ViewName, ");
        sql.append("  t_rt.Name ReminderTypeName ");
        sql.append("from ");
        sql.append("  InsurerViewDefinition t_ivd ");
        sql.append("    inner join ");
        sql.append("  ReminderMaster t_rm ");
        sql.append("    on ");
        sql.append("      t_rm.ViewId = t_ivd.ViewId ");
        sql.append("    inner join ");
        sql.append("  ReminderType t_rt ");
        sql.append("    on ");
        sql.append("      t_rt.ReminderTypeCd = t_rm.ReminderTypeCd ");
        sql.append("where ");
        sql.append("  ? between t_ivd.StartYear and t_ivd.EndYear ");
        sql.append("order by ");
        sql.append("  t_ivd.ViewName, ");
        sql.append("  t_rm.ReminderTitle ");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, year);

        List<ReminderMasterEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ReminderMasterEntity entity = ReminderMasterEntity.setData(dataTable);
            entity.setViewName(AbstractEntity.getString(dataTable, "ViewName"));
            entity.setReminderTypeName(AbstractEntity.getString(dataTable, "ReminderTypeName"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }

    /**
     * リマインダマスタのIDの最大値を検索します。
     * @return
     * @throws Throwable
     */
    public String findMaxId() throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("  max(ReminderTypeId) ReminderTypeId ");
        sql.append("from ");
        sql.append("  ReminderMaster ");
        sql.append("where ");
        sql.append("  ReminderTypeId like 'A%' ");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        String id = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            id = AbstractEntity.getString(dataTable, "ReminderTypeId");
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return id;
    }

    /**
     * ビューIDと項目IDリストにてリマインダマスタを検索します。
     * @param viewId
     * @param observationDefinitionIdList
     * @return
     * @throws Throwable
     */
    public List<ReminderMasterEntity> findByViewIdObservation(int viewId, List<String> observationDefinitionIdList) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("  ReminderMaster.*, ");
        sql.append("  ObservationDefinition.ObservationDefinitionId, ");
        sql.append("  ObservationDefinition.DisplayName ");
        sql.append("from ");
        sql.append("  ReminderMaster ");
        sql.append("    inner join ");
        sql.append("  ReminderItem ");
        sql.append("    on ");
        sql.append("      ReminderItem.ReminderTypeId = ReminderMaster.ReminderTypeId ");
        sql.append("    inner join ");
        sql.append("  ObservationDefinition ");
        sql.append("    on ");
        sql.append("      ObservationDefinition.ObservationDefinitionId = ReminderItem.ObservationDefinitionId ");
        sql.append("where ");
        sql.append("      ReminderMaster.ViewId = ? ");
        if (observationDefinitionIdList != null && !observationDefinitionIdList.isEmpty()) {
            String questions = observationDefinitionIdList
                    .stream()
                    .map(e -> { return "?";})
                    .collect(Collectors.joining(", "));
            sql.append("  and ReminderItem.ObservationDefinitionId in (")
                    .append(questions).append(") ");
        }

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);
        if (observationDefinitionIdList != null) {
            int i = 2;
            for (String observationDefinitionId : observationDefinitionIdList) {
                preparedStatement.setString(i++, observationDefinitionId);
            }
        }

        List<ReminderMasterEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ReminderMasterEntity entity = ReminderMasterEntity.setData(dataTable);
            entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionDisplayName(AbstractEntity.getString(dataTable, "DisplayName"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }

    /**
     * ワーニング対象を検索します。
     * @param viewId
     * @param insurerDiseaseTypeDel
     * @param insurerDiseaseTypeNew
     * @return
     * @throws Throwable
     */
    public List<ReminderMasterEntity> findWarningTarget(
            int viewId,
            List<String> insurerDiseaseTypeDel,
            List<String> insurerDiseaseTypeNew) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("  t_rm.*, ");
        sql.append("  t_od.ObservationDefinitionId, ");
        sql.append("  t_od.DisplayName ");
        sql.append("from ");
        sql.append("  ReminderMaster t_rm ");
        sql.append("    inner join ");
        sql.append("  ReminderItem t_ri ");
        sql.append("    on ");
        sql.append("      t_ri.ReminderTypeId = t_rm.ReminderTypeId ");
        sql.append("    inner join ");
        sql.append("  ObservationDefinition t_od ");
        sql.append("    on ");
        sql.append("      t_od.ObservationDefinitionId = t_ri.ObservationDefinitionId ");
        sql.append("where ");
        sql.append("      t_rm.ViewId = ? ");
        sql.append("  and t_rm.SendType = '0' ");
        sql.append("  and exists( ");
        sql.append("    select ");
        sql.append("      1 ");
        sql.append("    from ");
        sql.append("      ReminderMaster ");
        sql.append("        inner join ");
        sql.append("      ReminderKind ");
        sql.append("        on ");
        sql.append("          ReminderKind.ReminderTypeId = ReminderMaster.ReminderTypeId ");
        sql.append("        inner join ");
        sql.append("      InsurerDiseaseType ");
        sql.append("        on ");
        sql.append("              InsurerDiseaseType.ViewId = ReminderMaster.ViewId ");
        sql.append("          and InsurerDiseaseType.DiseaseTypeCd = ReminderKind.DiseaseTypeCd ");
        sql.append("    where ");
        sql.append("          ReminderMaster.ReminderTypeId = t_rm.ReminderTypeId ");
        sql.append("      and InsurerDiseaseType.ObservationDefinitionId = t_ri.ObservationDefinitionId ");
        sql.append("  ) ");
        if (insurerDiseaseTypeDel != null && !insurerDiseaseTypeDel.isEmpty()) {
            sql.append("  and not exists( ");
            sql.append("    select ");
            sql.append("      1 ");
            sql.append("    from ");
            sql.append("      ReminderMaster ");
            sql.append("        inner join ");
            sql.append("      ReminderKind ");
            sql.append("        on ");
            sql.append("          ReminderKind.ReminderTypeId = ReminderMaster.ReminderTypeId ");
            sql.append("        inner join ");
            sql.append("      InsurerDiseaseType ");
            sql.append("        on ");
            sql.append("              InsurerDiseaseType.ViewId = ReminderMaster.ViewId ");
            sql.append("          and InsurerDiseaseType.DiseaseTypeCd = ReminderKind.DiseaseTypeCd ");
            sql.append("    where ");
            sql.append("          ReminderMaster.ReminderTypeId = t_rm.ReminderTypeId ");
            sql.append("      and InsurerDiseaseType.ObservationDefinitionId = t_ri.ObservationDefinitionId ");
            String questions = insurerDiseaseTypeDel
                    .stream()
                    .map(e -> { return "(?, ?)";})
                    .collect(Collectors.joining(", "));
            sql.append("      and (InsurerDiseaseType.ObservationDefinitionId, InsurerDiseaseType.DiseaseTypeCd) not in (")
                    .append(questions).append(") ");
            sql.append("  ) ");
        }
        if (insurerDiseaseTypeNew != null && !insurerDiseaseTypeNew.isEmpty()) {
            sql.append("  and not exists( ");
            sql.append("    select ");
            sql.append("      1 ");
            sql.append("    from ");
            sql.append("      ReminderMaster ");
            sql.append("        inner join ");
            sql.append("      ReminderKind ");
            sql.append("        on ");
            sql.append("          ReminderKind.ReminderTypeId = ReminderMaster.ReminderTypeId ");
            sql.append("    where ");
            sql.append("          ReminderMaster.ReminderTypeId = t_rm.ReminderTypeId ");
            String questions = insurerDiseaseTypeNew
                    .stream()
                    .map(e -> { return "(?, ?)";})
                    .collect(Collectors.joining(", "));
            sql.append("      and (t_ri.ObservationDefinitionId, ReminderKind.DiseaseTypeCd) in (")
                    .append(questions).append(") ");
            sql.append("  ) ");
        }

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);
        int i = 2;
        if (insurerDiseaseTypeDel != null) {
            for (String pair : insurerDiseaseTypeDel) {
                String[] splitted = pair.split("-");
                preparedStatement.setString(i++, splitted[0]);
                preparedStatement.setInt(i++, Integer.valueOf(splitted[1]));
            }
        }
        if (insurerDiseaseTypeNew != null) {
            for (String pair : insurerDiseaseTypeNew) {
                String[] splitted = pair.split("-");
                preparedStatement.setString(i++, splitted[0]);
                preparedStatement.setInt(i++, Integer.valueOf(splitted[1]));
            }
        }

        List<ReminderMasterEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ReminderMasterEntity entity = ReminderMasterEntity.setData(dataTable);
            entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionDisplayName(AbstractEntity.getString(dataTable, "DisplayName"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }

}
