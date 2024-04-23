/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査項目結果情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.datadomain.adapter;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.kis_inc.core.utility.TypeUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getDateTime;
import static phr.datadomain.AbstractEntity.getNullDouble;
import static phr.datadomain.AbstractEntity.getNullBoolean;
import static phr.datadomain.AbstractEntity.getInt;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.ObservationLabelEntity;
import phr.datadomain.entity.ReminderTargetPhrIdItemEntity;

/**
 * 検査項目結果情報のデータオブジェクトです。
 */
public class ObservationAdapter extends ObservationAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationAdapter.class);
    private static Logger logger = Logger.getLogger(ObservationAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public ObservationAdapter(Connection conn) {
        super(conn);
    }

    /**
     * ObservationEventIdで検査項目結果の検索
     *
     * @param observationEventId
     * @return 検査項目結果一覧
     * @throws Throwable
     */
    public List<ObservationEntity> findByObservationEventId(String observationEventId) throws Throwable {
        logger.debug("Start");
        List<ObservationEntity> resList;
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
//        String sql = getSelectedSql();
        
        sb.append("  select \r\n");
        sb.append("    Observation.ObservationEventId As ObservationEventId \r\n");
        sb.append("    , Observation.ObservationDefinitionId As ObservationDefinitionId \r\n");
        sb.append("    , Observation.JLAC10 As JLAC10 \r\n");
        sb.append("    , Observation.Value As Value \r\n");
        sb.append("    , Observation.OutRangeTypeCd As OutRangeTypeCd \r\n");
        sb.append("    , case \r\n");
        sb.append("      when Observation.MinReferenceValue is null \r\n");
        sb.append("      and Observation.MaxReferenceValue is null \r\n");
        sb.append("        then ObservationDefinitionInsurer.MinReferenceValue \r\n");
        sb.append("      else Observation.MinReferenceValue \r\n");
        sb.append("      end As MinReferenceValue \r\n");
        sb.append("    , case \r\n");
        sb.append("      when Observation.MaxReferenceValue is null \r\n");
        sb.append("      and Observation.MaxReferenceValue is null \r\n");
        sb.append("        then ObservationDefinitionInsurer.MaxReferenceValue \r\n");
        sb.append("      else Observation.MaxReferenceValue \r\n");
        sb.append("      end As MaxReferenceValue \r\n");
        sb.append("    , Observation.CreateDateTime As CreateDateTime \r\n");
        sb.append("    , Observation.UpdateDateTime As UpdateDateTime \r\n");
        sb.append("    , Observation.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg \r\n");
        sb.append("    , Observation.DiseaseManagementTargetFlg As flag \r\n");
        sb.append("    , Observation.Unit As Unit \r\n");
        sb.append("  from \r\n");
        sb.append("    Observation \r\n");
        sb.append("    left outer join ObservationDefinitionInsurer \r\n");
        sb.append("      on Observation.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId \r\n");
        sb.append("  where \r\n");
        sb.append("    Observation.ObservationEventId = ? \r\n");
        sb.append("    and ObservationDefinitionInsurer.ViewId >= 100 \r\n");
        sb.append("  order by \r\n");
        sb.append("    Observation.CreateDateTime desc \r\n");

        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationEventId);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        resList = new ArrayList<>();
        while (dataTable.next()) {
            ObservationEntity entity = ObservationEntity.setData(dataTable);
//            entity.setFlag(getNullBoolean(dataTable, "flag"));
            entity.setFlag(true);
            resList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resList;
    }

    /* -------------------------------------------------------------------------------------- */
    /**
     * <pre>検査結果情報登録処理</pre>
     *
     * @param map
     * @return
     * @throws Throwable
     */
    public int InsertObservation(ObservationEntity entity) throws Throwable {

        DataAccessObject dao = new DataAccessObject(connection);
        dao.beginTransaction();
        String sql = getInsertSql();

        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, entity.getObservationEventId());   // 検査結果ID
        preparedStatement.setString(2, entity.getObservationDefinitionId());     // 項目ID
        preparedStatement.setString(3, entity.getJLAC10());  // JLAC10コード
        preparedStatement.setString(4, entity.getValue());   // 検査結果値
        preparedStatement.setInt(5, entity.getOutRangeTypeCd());     // 範囲外種別コード
        preparedStatement.setDouble(6, entity.getMinReferenceValue());  // 基準値下限
        preparedStatement.setDouble(7, entity.getMaxReferenceValue());  // 基準値上限
//        preparedStatement.setDouble(8,entity.getAlertLevelCd());  // アラートレベルコード
//        preparedStatement.setBoolean(9,entity.isAlertFlg());  // アラートレベルフラグ

        // 登録処理
        int i = preparedStatement.executeUpdate();
        preparedStatement.close();

        return i;
    }

    /**
     * 指定した検査項目の数日間のMaxとMinは取得する
     *
     * @param phrid
     * @param date
     * @param targetDay　対象に数字、月か日の判別はtypeにて行う
     * @param targetid
     * @param type 1:日　2:月
     * @param observationEventId
     * @return 検査項目結果一覧
     * @throws Throwable
     */
    public String findByAlertTarget(String phrid, String targetid, String targetDay, Integer type, Timestamp date) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);

        StringBuilder sb = new StringBuilder();
        sb.append(" select ");
        sb.append(" Observation.Value as value , ");
        sb.append(" ObservationEvent.ExaminationDate as cdate  ");
        sb.append(" from ");
        sb.append(" Observation ");
        sb.append("  inner join ");
        sb.append(" ObservationEvent ");
        sb.append(" where ");

        String sql = sb.toString();
        sql += " Observation.ObservationDefinitionId = ?";
        sql += " and Observation.ObservationEventId = ObservationEvent.ObservationEventId";
        if (type == 1) {
            sql += " and ObservationEvent.ExaminationDate > DATE_ADD(?, INTERVAL ? DAY)";
        } else if (type == 2) {
            sql += " and ObservationEvent.ExaminationDate > DATE_ADD(?, INTERVAL ? MONTH)";
        } else {
            return null;
        }
        sql += " and ObservationEvent.PHR_ID = ?";
        sql += "  Order by cdate desc ";
        sql += "  limit 1 ";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, targetid);
        java.sql.Date cdate = new java.sql.Date(date.getTime());

        preparedStatement.setDate(2, cdate);
        preparedStatement.setString(3, targetDay);
        preparedStatement.setString(4, phrid);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        String target = null;
        while (dataTable.next()) {
            target = AbstractEntity.getString(dataTable, "value");

        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return target;

    }

    /**
     * 指定した検査項目の数日間のMaxとMinは取得する
     *
     * @param observationEventId
     * @return 検査項目結果一覧
     * @throws Throwable
     */
    public List<ObservationEntity> getObservationList(String evetid, String insurerNo) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);

        StringBuilder sb = new StringBuilder();
        sb.append("select  \r\n");
        sb.append("    Observation.ObservationEventId As ObservationEventId  \r\n");
        sb.append("    , Observation.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sb.append("    , Observation.JLAC10 As JLAC10  \r\n");
        sb.append("    , Observation.Value As Value  \r\n");
        sb.append("    , Observation.OutRangeTypeCd As OutRangeTypeCd  \r\n");
        sb.append("    , Observation.MinReferenceValue As MinReferenceValue  \r\n");
        sb.append("    , Observation.MaxReferenceValue As MaxReferenceValue  \r\n");
//        sb.append("    , Observation.AlertLevelCd As AlertLevelCd  \r\n");
//        sb.append("    , Observation.AlertFlg As AlertFlg  \r\n");
        sb.append("    , Observation.CreateDateTime As CreateDateTime  \r\n");
        sb.append("    , Observation.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("    , ObservationDefinition.ObservationDefinitionName \r\n");
        sb.append("    , ObservationDefinition.DisplayName \r\n");
        sb.append("    , ObservationDefinitionJlac10.JLAC10 as D_JLAC10 \r\n");
        sb.append("    , ObservationDefinitionInsurer.UnitValue \r\n");
        sb.append(" from Observation \r\n");
        sb.append(" inner join ObservationDefinition on \r\n");
        sb.append("    ObservationDefinition.ObservationDefinitionId = Observation.ObservationDefinitionId \r\n");
        sb.append(" inner join ObservationDefinitionJlac10 on \r\n");
        sb.append("    Observation.ObservationDefinitionId = ObservationDefinitionJlac10.ObservationDefinitionId \r\n");
        sb.append(" inner join ObservationDefinitionInsurer on  \r\n");
        sb.append("    Observation.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId \r\n");

        String sql = sb.toString();
        sql += " where ";
        sql += " Observation.ObservationEventId = ?";
        //    sql += " and ObservationDefinitionInsurer.InsurerNo = ?";
        sql += " GROUP BY ";
        sql += "  ObservationDefinitionId ";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, evetid);
        //    preparedStatement.setString(2, insurerNo);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        List<ObservationEntity> resultList = new ArrayList<>();

        while (dataTable.next()) {
            ObservationEntity entity = ObservationEntity.setData(dataTable);
            entity.setObservationDefinitionName(AbstractEntity.getString(dataTable, "ObservationDefinitionName"));
            entity.setDisplayName(AbstractEntity.getString(dataTable, "DisplayName"));
            entity.setD_JLAC10(AbstractEntity.getString(dataTable, "D_JLAC10"));
            entity.setUnitValue(AbstractEntity.getString(dataTable, "UnitValue"));
            resultList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resultList;

    }

    /**
     * 最終検査日から指定日数以上超過しているPHR-ID・検査情報・最終検査日を抽出する（PatientレコードのDiseaseManagement=trueのもの）
     *
     * @param reminderTypeId リマインダ種別ID
     * @param reminderLevel リマインダレベル
     * @return
     * @throws Throwable
     */
    public List<ReminderTargetPhrIdItemEntity> findExpiredData(String reminderTypeId, int reminderLevel) throws Throwable {

        logger.debug("Start");
        try {
            DataAccessObject dao = new DataAccessObject(connection);

            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            sql.append("  t_2.* ");
            sql.append("from ");
            sql.append("  ( ");
            sql.append("    select ");
            sql.append("      PHR_ID, ");
            sql.append("      ObservationDefinitionId, ");
            sql.append("      max(ExaminationDate) LastExaminationDate ");
            sql.append("    from ");
            sql.append("      ( ");
            sql.append("        select ");
            sql.append("          t_oe_ri.PHR_ID, ");
            sql.append("          t_oe_ri.ObservationDefinitionId, ");
            sql.append("          case ");
            sql.append("            when t_o.ObservationDefinitionId is null then null ");
            sql.append("            else t_oe_ri.ExaminationDate ");
            sql.append("          end ExaminationDate ");
            sql.append("        from ");
            sql.append("          ( ");
            sql.append("            select ");
            sql.append("              t_oe_1.*, ");
            sql.append("              t_ri.ObservationDefinitionId ");
            sql.append("            from ");
            sql.append("              ( ");
            sql.append("                select ");
            sql.append("                  ? ReminderTypeId, ");
            sql.append("                  t_oe.ObservationEventId, ");
            sql.append("                  t_oe.ExaminationDate, ");
            sql.append("                  t_p.PHR_ID ");
            sql.append("                from ");
            sql.append("                  Patient t_p ");
            sql.append("                    left outer join ");
            sql.append("                  ObservationEvent t_oe ");
            sql.append("                    on ");
            sql.append("                          t_oe.PHR_ID = t_p.PHR_ID ");
            sql.append("                      and t_oe.DataInputTypeCd = ( ");
            sql.append("                        select ");
            sql.append("                          min(ObservationDefinitionType.DataInputTypeCd) ");
            sql.append("                        from ");
            sql.append("                          ReminderMaster ");
            sql.append("                            inner join ");
            sql.append("                          InsurerViewDefinition ");
            sql.append("                            on ");
            sql.append("                              InsurerViewDefinition.ViewId = ReminderMaster.ViewId ");
            sql.append("                            inner join ");
            sql.append("                          ObservationDefinitionInsurer ");
            sql.append("                            on ");
            sql.append("                              ObservationDefinitionInsurer.ViewId = InsurerViewDefinition.ViewId ");
            sql.append("                            inner join ");
            sql.append("                          ObservationDefinitionType ");
            sql.append("                            on ");
            sql.append("                                  ObservationDefinitionType.InsurerNo = InsurerViewDefinition.InsurerNo ");
            sql.append("                              and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId ");
            sql.append("                        where ");
            sql.append("                          ReminderMaster.ReminderTypeId = ? ");
            sql.append("                      ) ");
            sql.append("                where ");
            sql.append("                  exists ( ");
            sql.append("                    select ");
            sql.append("                      1 ");
            sql.append("                    from ");
            sql.append("                      ReminderMaster, ");
            sql.append("                      ReminderKind, ");
            sql.append("                      DiseaseType, ");
            sql.append("                      Observation, ");
            sql.append("                      ObservationEvent ");
            sql.append("                    where ");
            sql.append("                          ReminderMaster.ReminderTypeId = ? ");
            sql.append("                      and ReminderKind.ReminderTypeId = ReminderMaster.ReminderTypeId ");
            sql.append("                      and DiseaseType.ViewId = ReminderMaster.ViewId ");
            sql.append("                      and ReminderKind.DiseaseTypeCd = DiseaseType.DiseaseTypeCd ");
            sql.append("                      and Observation.ObservationDefinitionId = DiseaseType.ObservationDefinitionId ");
            sql.append("                      and ObservationEvent.ObservationEventId = Observation.ObservationEventId ");
            sql.append("                      and ObservationEvent.DataInputTypeCd = '1' ");
            sql.append("                      and t_p.PHR_ID = ObservationEvent.PHR_ID ");
            sql.append("                      and t_p.DiseaseManagement = true ");
            sql.append("                  ) ");
            sql.append("                  and not exists ( ");
            sql.append("                    select ");
            sql.append("                      1 ");
            sql.append("                    from ");
            sql.append("                      ReminderPushedList ");
            sql.append("                    where ");
            sql.append("                          ReminderPushedList.ReminderTypeId = ? ");
            sql.append("                      and ReminderPushedList.ReminderLevel = ? ");
            sql.append("                      and ReminderPushedList.RemoveBanDate > curdate() ");
            sql.append("                      and t_p.PHR_ID = ReminderPushedList.PHR_ID ");
            sql.append("                  ) ");
            sql.append("              ) t_oe_1 ");
            sql.append("                left outer join ");
            sql.append("              ReminderItem t_ri ");
            sql.append("                on ");
            sql.append("                  t_ri.ReminderTypeId = t_oe_1.ReminderTypeId ");
            sql.append("          ) t_oe_ri ");
            sql.append("            left outer join ");
            sql.append("          Observation t_o ");
            sql.append("            on ");
            sql.append("                  t_o.ObservationEventId = t_oe_ri.ObservationEventId ");
            sql.append("              and t_o.ObservationDefinitionId = t_oe_ri.ObservationDefinitionId ");
            sql.append("      ) t_1 ");
            sql.append("    group by ");
            sql.append("      t_1.PHR_ID, ");
            sql.append("      t_1.ObservationDefinitionId ");
            sql.append("  ) t_2 ");
            sql.append("    inner join ");
            sql.append("  ReminderMessage t_rmes ");
            sql.append("    on ");
            sql.append("          t_rmes.ReminderTypeId = ? ");
            sql.append("      and t_rmes.ReminderLevel = ? ");
            sql.append("    inner join ");
            sql.append("  ReminderMaster t_rm ");
            sql.append("    on ");
            sql.append("      t_rm.ReminderTypeId = ? ");
            sql.append("where ");
            sql.append("      t_rm.SendType = '0' ");
            sql.append("  and ( ");
            sql.append("        t_2.LastExaminationDate is null ");
            sql.append("    and t_rmes.ReminderLevel = 1 ");
            sql.append("      or ");
            sql.append("            t_2.LastExaminationDate is not null ");
            sql.append("        and curdate() > (t_2.LastExaminationDate + interval t_rmes.SendPeriod day) ");
            sql.append("        and not exists ( ");
            sql.append("          select ");
            sql.append("            1 ");
            sql.append("          from ");
            sql.append("            ReminderMessage ");
            sql.append("          where ");
            sql.append("                ReminderMessage.ReminderTypeId = ? ");
            sql.append("            and ReminderMessage.ReminderLevel > ? ");
            sql.append("            and ReminderMessage.Title <> '' ");
            sql.append("            and curdate() > (t_2.LastExaminationDate + interval ReminderMessage.SendPeriod day) ");
            sql.append("        ) ");
            sql.append("  ) ");
            sql.append("    or ");
            sql.append("      t_rm.SendType = '1' ");
            sql.append("  and t_rmes.SendMonth = DATE_FORMAT(curdate(), '%c') ");
            dao.setSql(sql.toString());

            dao.clearBindParam();
            ArrayList resultList;

            try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {

                preparedStatement.setString(1, reminderTypeId);
                preparedStatement.setString(2, reminderTypeId);
                preparedStatement.setString(3, reminderTypeId);
                preparedStatement.setString(4, reminderTypeId);
                preparedStatement.setInt(5, reminderLevel);
                preparedStatement.setString(6, reminderTypeId);
                preparedStatement.setInt(7, reminderLevel);
                preparedStatement.setString(8, reminderTypeId);
                preparedStatement.setString(9, reminderTypeId);
                preparedStatement.setInt(10, reminderLevel);
                try (ResultSet dataTable = preparedStatement.executeQuery()) {
                    if (dataTable == null) {
                        return null;
                    }
                    resultList = new ArrayList();

                    while (dataTable.next()) {

                        ReminderTargetPhrIdItemEntity entity = new ReminderTargetPhrIdItemEntity();
                        entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
                        entity.setPhrId(AbstractEntity.getString(dataTable, "PHR_ID"));
                        entity.setLastExaminationDate(AbstractEntity.getDate(dataTable, "LastExaminationDate"));
                        resultList.add(entity);

                    }
                }
            } finally {
                dao.clearBindParam();
            }
            return resultList;
        } catch (Throwable th) {
            logger.error(th.toString(), th);
            throw th;
        } finally {
            logger.debug("End");
        }
    }

    /**
     * グラフIDにてグラフの情報を取得する
     *
     * @param phrId
     * @param chartDefinitionId
     * @param targetDate
     * @return
     * @throws Throwable
     */
    public List<ObservationEntity> findByChartDefinitionId(String phrId, long chartDefinitionId, Date targetDate) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);

        StringBuilder sb = new StringBuilder();
        sb.append("select \r\n");
        sb.append("    ObservationEvent.ExaminationDate \r\n");
        sb.append("    , ObservationEvent.ObservationEventId \r\n");
        sb.append("    , ChartObservationDefinition.ObservationDefinitionId \r\n");
        sb.append("    , Observation.Value \r\n");
        sb.append("from  \r\n");
        sb.append("    ChartDefinition \r\n");
        sb.append("    inner join ChartObservationDefinition on \r\n");
        sb.append("        ChartObservationDefinition.ChartDefinitionId = ChartDefinition.ChartDefinitionId \r\n");
        sb.append("    inner join ObservationEvent on \r\n");
        sb.append("        ObservationEvent.PHR_ID = ? \r\n");
        sb.append("    inner join Observation on \r\n");
        sb.append("        Observation.ObservationEventId = ObservationEvent.ObservationEventId \r\n");
        sb.append("        and Observation.ObservationDefinitionId = ChartObservationDefinition.ObservationDefinitionId \r\n");
        sb.append("where \r\n");
        sb.append("    ChartDefinition.ChartDefinitionId = ? \r\n");
        if (targetDate != null) {
            sb.append("    and ObservationEvent.ExaminationDate <= cast(? as datetime) \r\n");
        }
        sb.append("order by \r\n");
        sb.append("    ObservationEvent.ExaminationDate desc \r\n");
        sb.append("    , Observation.ObservationDefinitionId \r\n");

        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);
        preparedStatement.setLong(2, chartDefinitionId);
        if (targetDate != null) {
            String date = TypeUtility.format(targetDate, "yyyy/MM/dd HH:mm");
            preparedStatement.setString(3, date);
        }

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        List<ObservationEntity> resultList = new ArrayList<>();

        while (dataTable.next()) {
            ObservationEntity entity = new ObservationEntity();
            entity.setObservationEventId(AbstractEntity.getString(dataTable, "ObservationEventId"));
            entity.setExaminationDate(getDateTime(dataTable, "ExaminationDate"));
            entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
            entity.setValue(AbstractEntity.getString(dataTable, "Value"));
            resultList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resultList;

    }

    /**
     * 開始日、終了日を元に自己測定の結果リストを取得する
     *
     * @param phrId
     * @param startDate
     * @param endDate
     * @return
     * @throws Throwable
     */
    public List<ObservationEntity> findSelfCheckList(String phrId, Date startDate, Date endDate) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);

        String baseSql = getSelectedSql();
        StringBuilder sb = new StringBuilder(baseSql.replaceAll("from Observation \r\n", ""));
        sb.append("    , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        sb.append("from Observation \r\n");
        sb.append("    inner join ObservationEvent on \r\n");
        sb.append("        ObservationEvent.ObservationEventId = Observation.ObservationEventId \r\n");
        sb.append("where \r\n");
        sb.append("    ObservationEvent.DataInputTypeCd = 2 \r\n");
        sb.append("    and  ObservationEvent.PHR_ID = ? \r\n");

        int addCnt = 0;
        java.sql.Date sqlStartDate = null;
        java.sql.Date sqlEndDate = null;

        if (startDate != null) {
            sb.append("    and ObservationEvent.ExaminationDate >= ? \r\n");
            addCnt++;
            Calendar calStart = Calendar.getInstance();
            calStart.setTime(startDate);
            calStart.set(Calendar.HOUR_OF_DAY, 0);
            calStart.set(Calendar.MINUTE, 0);
            calStart.set(Calendar.SECOND, 0);
            calStart.set(Calendar.MILLISECOND, 0);
            sqlStartDate = new java.sql.Date(calStart.getTimeInMillis());
        }
        if (endDate != null) {
            sb.append("    and ObservationEvent.ExaminationDate <= ? \r\n");
            addCnt++;

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(endDate);
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.SECOND, 0);
            calEnd.set(Calendar.MILLISECOND, 0);
            sqlEndDate = new java.sql.Date(calEnd.getTimeInMillis());
        }
        sb.append("order by ObservationEvent.ExaminationDate DESC \r\n");
        sb.append("    , Observation.ObservationEventId \r\n");

        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phrId);
        if (sqlStartDate != null) {
            preparedStatement.setDate(2, sqlStartDate);
        }
        if (sqlEndDate != null) {
            if (addCnt == 1) {
                preparedStatement.setDate(2, sqlEndDate);
            } else {
                preparedStatement.setDate(3, sqlEndDate);
            }
        }
        
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        List<ObservationEntity> resultList = new ArrayList<>();

        while (dataTable.next()) {
            ObservationEntity entity = ObservationEntity.setData(dataTable);
            entity.setExaminationDate(getDateTime(dataTable, "ExaminationDate"));
            resultList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resultList;

    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationEntity entity) throws Throwable  
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Observation \r\n");
        sql.append("(ObservationEventId, ObservationDefinitionId, JLAC10, Value, OutRangeTypeCd, MinReferenceValue, MaxReferenceValue, DiseaseManagementTargetFlg, Unit, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getObservationEventId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getObservationEventId());
            }
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getObservationDefinitionId());
            }
            if (entity.getJLAC10() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getJLAC10());
            }
            if (entity.getValue() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getValue());
            }
            if (entity.getOutRangeTypeCd() == null ) {
                preparedStatement.setNull(5, Types.INTEGER );
            } else {
                preparedStatement.setInt(5, entity.getOutRangeTypeCd().intValue());
            }
            if (entity.getMinReferenceValue() == null ) {
                preparedStatement.setNull(6, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(6, entity.getMinReferenceValue().doubleValue());
            }
            if (entity.getMaxReferenceValue() == null ) {
                preparedStatement.setNull(7, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(7, entity.getMaxReferenceValue().doubleValue());
            }
            preparedStatement.setBoolean(8, entity.isDiseaseManagementTargetFlg());
            if (entity.getUnit() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getUnit());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();

        logger.debug("End");
        return rowCount;
    }

    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(ObservationEntity entity) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("update Observation set \r\n");
        sql.append("JLAC10 = ?, Value = ?, OutRangeTypeCd = ?, MinReferenceValue = ?, MaxReferenceValue = ?, DiseaseManagementTargetFlg = ?, Unit = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where ObservationEventId = ? AND ObservationDefinitionId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getJLAC10() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getJLAC10());
            }
            if (entity.getValue() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getValue());
            }
            if (entity.getOutRangeTypeCd() == null ) {
                preparedStatement.setNull(3, Types.INTEGER );
            } else {
                preparedStatement.setInt(3, entity.getOutRangeTypeCd().intValue());
            }
            if (entity.getMinReferenceValue() == null ) {
                preparedStatement.setNull(4, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(4, entity.getMinReferenceValue().doubleValue());
            }
            if (entity.getMaxReferenceValue() == null ) {
                preparedStatement.setNull(5, Types.DOUBLE );
            } else {
                preparedStatement.setDouble(5, entity.getMaxReferenceValue().doubleValue());
            }
            preparedStatement.setBoolean(6, entity.isDiseaseManagementTargetFlg());
            if (entity.getUnit() == null ) {
                preparedStatement.setNull(7, Types.DOUBLE );
            } else {
                preparedStatement.setString(7, entity.getUnit());
            }
            if (entity.getObservationEventId() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getObservationEventId());
            }
            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getObservationDefinitionId());
            }
            preparedStatement.setTimestamp(10, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }
    
    /**
     * 測定・全検査結果のデータを取得（患者View）
     * @param observationEventId
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> searchAllByEventId(String observationEventId) throws Throwable {
        logger.debug("Start");
        List<ObservationEventEntity> resList;
        DataAccessObject dao = new DataAccessObject(connection);

        StringBuilder sb = new StringBuilder();
        sb.append("select \r\n");
        sb.append("    * \r\n");
        sb.append("from  \r\n");
        sb.append("    ( \r\n");
        sb.append("    select \r\n");
        sb.append("        Observation.ObservationEventId As ObservationEventId \r\n");
        sb.append("        , ObservationDefinitionInsurer.ViewId \r\n");
        sb.append("        , ObservationEvent.PHR_ID As PHR_ID \r\n");
        sb.append("        , ObservationEvent.ExaminationDate As ExaminationDate \r\n");
        sb.append("        , ObservationEvent.Year As Year \r\n");
        sb.append("        , ObservationEvent.InsurerNo As InsurerNo \r\n");
        sb.append("        , ObservationEvent.LaboratoryCd As LaboratoryCd \r\n");
        sb.append("        , ObservationEvent.OrderNo As OrderNo \r\n");
        sb.append("        , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd \r\n");
        sb.append("        , ObservationEvent.CreateDateTime As CreateDateTime \r\n");
        sb.append("        , ObservationEvent.UpdateDateTime As UpdateDateTime \r\n");
        sb.append("        , ObservationEvent.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg \r\n");
        sb.append("        , Observation.ObservationDefinitionId As ObservationDefinitionId \r\n");
        sb.append("        , Observation.JLAC10 As JLAC10 \r\n");
        sb.append("        , Observation.Value As Value \r\n");
        sb.append("        , Observation.OutRangeTypeCd As OutRangeTypeCd \r\n");
        sb.append("        , Observation.Unit As Unit \r\n");
        sb.append("        , LEFT(Observation.JLAC10, 5) As JLAC10AnalyteCode \r\n");
        sb.append("        , Jlac10AnalyteCode.SortNo As SortNo \r\n");
        sb.append("        , Jlac10AnalyteCode.AnalyteName1 As Jlac10AnalyteName \r\n");
        sb.append("        , Jlac11AnalyteCode.AnalyteName As Jlac11AnalyteName \r\n");
        sb.append("        , case \r\n");
        sb.append("          when Observation.MinReferenceValue is null \r\n");
        sb.append("          and Observation.MaxReferenceValue is null \r\n");
        sb.append("            then ObservationDefinitionInsurer.MinReferenceValue \r\n");
        sb.append("          else Observation.MinReferenceValue  \r\n");
        sb.append("          end As MinReferenceValue \r\n");
        sb.append("        , case \r\n");
        sb.append("          when Observation.MaxReferenceValue is null \r\n");
        sb.append("        and Observation.MaxReferenceValue is null \r\n");
        sb.append("          then ObservationDefinitionInsurer.MaxReferenceValue \r\n");
        sb.append("        else Observation.MaxReferenceValue  \r\n");
        sb.append("        end As MaxReferenceValue \r\n");
//        sb.append("        , ObservationDefinitionInsurer.UnitValue As UnitValue \r\n");
        sb.append("        , Observation.UpdateDateTime As UpdateDateTimeA \r\n");
        sb.append("        , ObservationDefinition.DisplayName As DisplayName \r\n");
        sb.append("        , ObservationDefinitionEnumValue.EnumName As EnumName \r\n");
        sb.append("        , ObservationEvent.ExaminationDate As NewExaminationDate \r\n");
        sb.append("        , ObservationDefinitionInsurer.ReminderTypeCd As ReminderTypeCd \r\n");
        sb.append("        , case ObservationEvent.DataInputTypeCd  \r\n");
        sb.append("          when 1 then - 3  \r\n");
        sb.append("          when 2 then - 4   \r\n");
        sb.append("          else - 5  \r\n");
        sb.append("          end as DataInputTypeCd   \r\n");
        sb.append("    from  \r\n");
        sb.append("        Observation \r\n");
//        sb.append("        inner join Jlac10AnalyteCode \r\n");
        sb.append("        left join Jlac10AnalyteCode \r\n");
        sb.append("        on LEFT(Observation.JLAC10, 5) = Jlac10AnalyteCode.AnalyteType \r\n");
        sb.append("        left join Jlac11AnalyteCode \r\n");
        sb.append("        on LEFT(Observation.JLAC10, 5) = Jlac11AnalyteCode.AnalyteType \r\n");
        sb.append("        inner join ObservationEvent \r\n");
        sb.append("            on Observation.ObservationEventId = ObservationEvent.ObservationEventId \r\n");
        sb.append("        left join ObservationDefinitionInsurer \r\n");
        sb.append("            on ObservationDefinitionInsurer.ObservationDefinitionId = Observation.ObservationDefinitionId \r\n");
        sb.append("            and ObservationDefinitionInsurer.ViewId = 6 \r\n");
        sb.append("        left join ObservationDefinition \r\n");
        sb.append("            on ObservationDefinition.ObservationDefinitionId = Observation.ObservationDefinitionId \r\n");
        sb.append("        left join ObservationDefinitionEnumValue \r\n");
        sb.append("            on ObservationDefinitionEnumValue.ViewId = ObservationDefinitionInsurer.ViewId \r\n");
        sb.append("            and ObservationDefinitionEnumValue.ObservationDefinitionId = Observation.ObservationDefinitionId \r\n");
        sb.append("            and ObservationDefinitionEnumValue.EnumValue = Observation.Value \r\n");
        sb.append("    where \r\n");
        sb.append("        Observation.ObservationEventId = ? \r\n");
        sb.append("    order by \r\n");
        sb.append("        ObservationDefinitionInsurer.SortNo \r\n");
        sb.append("    ) as table1 \r\n");
        sb.append("    left join ObservationAlert \r\n");
        sb.append("        on table1.ObservationEventId = ObservationAlert.ObservationEventId \r\n");
        sb.append("        and table1.ObservationDefinitionId = ObservationAlert.ObservationDefinitionId \r\n");
        sb.append("order by SortNo \r\n");

        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationEventId);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        resList = new ArrayList<>();
        while (dataTable.next()) {
            ObservationEventEntity entity = ObservationEventEntity.setData(dataTable);
            entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionName(AbstractEntity.getString(dataTable, "DisplayName"));
            if (entity.getObservationDefinitionName() == null) {
                if (AbstractEntity.getString(dataTable, "Jlac10AnalyteName") != null) {
                    entity.setObservationDefinitionName(AbstractEntity.getString(dataTable, "Jlac10AnalyteName"));
                } else if (AbstractEntity.getString(dataTable, "Jlac11AnalyteName") != null) {
                    entity.setObservationDefinitionName(AbstractEntity.getString(dataTable, "Jlac11AnalyteName"));
                }
            }
            entity.setJlac10(AbstractEntity.getString(dataTable, "JLAC10"));
            String enumStr=AbstractEntity.getString(dataTable, "EnumName");
            if(enumStr==null){
                entity.setObservationValue(AbstractEntity.getString(dataTable, "Value"));
            }else{
                entity.setObservationValue(enumStr);
            }
            if(AbstractEntity.getNullInt(dataTable, "OutRangeTypeCd")==null){
                entity.setOutRangeTypeCd(-1);
            }else{
                entity.setOutRangeTypeCd(AbstractEntity.getNullInt(dataTable, "OutRangeTypeCd"));
            }
//            entity.setUnitValue(AbstractEntity.getString(dataTable, "UnitValue"));
            entity.setUnitValue(AbstractEntity.getString(dataTable, "Unit"));
            entity.setUpdateDateTimeA(AbstractEntity.getDateTime(dataTable, "UpdateDateTimeA"));
            if(AbstractEntity.getNullInt(dataTable, "AlertLevelCd")==null){
                entity.setAlertLevelCd(9); //その他「9」を設定
            }else{
                entity.setAlertLevelCd(AbstractEntity.getNullInt(dataTable, "AlertLevelCd"));
            }
            entity.setDisplayName(AbstractEntity.getString(dataTable, "DisplayName"));
            entity.setNewExaminationDate(AbstractEntity.getDateTime(dataTable, "NewExaminationDate"));
            if(AbstractEntity.getNullInt(dataTable, "ReminderTypeCd")==null){
                entity.setReminderTypeCd(99); //なし「99」を設定
            }else{
                entity.setReminderTypeCd(AbstractEntity.getNullInt(dataTable, "ReminderTypeCd"));
            }
            entity.setObservationEventId(AbstractEntity.getString(dataTable, "ObservationEventId"));
            
            entity.setMaxReferenceValue(getNullDouble(dataTable, "MaxReferenceValue"));
            entity.setMinReferenceValue(getNullDouble(dataTable, "MinReferenceValue"));
            entity.setjlac10AnalyteCode(AbstractEntity.getString(dataTable, "JLAC10AnalyteCode"));
            entity.setSortNo(AbstractEntity.getInt(dataTable, "SortNo"));
            
            resList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resList;
    }

    /**
     * 測定・全検査結果（自己測定・健診結果）のデータを取得（患者View）
     * @param observationEventId
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> searchAllByEventIdForSelfCheck(String observationEventId) throws Throwable {
        logger.debug("Start");
        List<ObservationEventEntity> resList;
        DataAccessObject dao = new DataAccessObject(connection);

        StringBuilder sb = new StringBuilder();
        sb.append("select \r\n");
        sb.append("    * \r\n");
        sb.append("from  \r\n");
        sb.append("    ( \r\n");
        sb.append("    select \r\n");
        sb.append("        Observation.ObservationEventId As ObservationEventId \r\n");
        sb.append("        , ObservationDefinitionInsurer.ViewId \r\n");
        sb.append("        , ObservationEvent.PHR_ID As PHR_ID \r\n");
        sb.append("        , ObservationEvent.ExaminationDate As ExaminationDate \r\n");
        sb.append("        , ObservationEvent.Year As Year \r\n");
        sb.append("        , ObservationEvent.InsurerNo As InsurerNo \r\n");
        sb.append("        , ObservationEvent.LaboratoryCd As LaboratoryCd \r\n");
        sb.append("        , ObservationEvent.OrderNo As OrderNo \r\n");
        sb.append("        , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd \r\n");
        sb.append("        , ObservationEvent.CreateDateTime As CreateDateTime \r\n");
        sb.append("        , ObservationEvent.UpdateDateTime As UpdateDateTime \r\n");
        sb.append("        , ObservationEvent.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg \r\n");
        sb.append("        , Observation.ObservationDefinitionId As ObservationDefinitionId \r\n");
        sb.append("        , Observation.Value As Value \r\n");
        sb.append("        , Observation.OutRangeTypeCd As OutRangeTypeCd \r\n");
        sb.append("        , Observation.Unit As Unit \r\n");
        sb.append("        , ObservationDefinition.SortNo As SortNo \r\n");
        sb.append("        , case \r\n");
        sb.append("          when Observation.MinReferenceValue is null \r\n");
        sb.append("          and Observation.MaxReferenceValue is null \r\n");
        sb.append("            then ObservationDefinitionInsurer.MinReferenceValue \r\n");
        sb.append("          else Observation.MinReferenceValue  \r\n");
        sb.append("          end As MinReferenceValue \r\n");
        sb.append("        , case \r\n");
        sb.append("          when Observation.MaxReferenceValue is null \r\n");
        sb.append("        and Observation.MaxReferenceValue is null \r\n");
        sb.append("          then ObservationDefinitionInsurer.MaxReferenceValue \r\n");
        sb.append("        else Observation.MaxReferenceValue  \r\n");
        sb.append("        end As MaxReferenceValue \r\n");
//        sb.append("        , ObservationDefinitionInsurer.UnitValue As UnitValue \r\n");
        sb.append("        , Observation.UpdateDateTime As UpdateDateTimeA \r\n");
        sb.append("        , ObservationDefinition.DisplayName As DisplayName \r\n");
        sb.append("        , ObservationDefinitionEnumValue.EnumName As EnumName \r\n");
        sb.append("        , ObservationEvent.ExaminationDate As NewExaminationDate \r\n");
        sb.append("        , ObservationDefinitionInsurer.ReminderTypeCd As ReminderTypeCd \r\n");
        sb.append("        , case ObservationEvent.DataInputTypeCd  \r\n");
        sb.append("          when 1 then - 3  \r\n");
        sb.append("          when 2 then - 4   \r\n");
        sb.append("          else - 5  \r\n");
        sb.append("          end as DataInputTypeCd   \r\n");
        sb.append("    from  \r\n");
        sb.append("        Observation \r\n");
        sb.append("        inner join ObservationEvent \r\n");
        sb.append("            on Observation.ObservationEventId = ObservationEvent.ObservationEventId \r\n");
        sb.append("        left join ObservationDefinitionInsurer \r\n");
        sb.append("            on ObservationDefinitionInsurer.ObservationDefinitionId = Observation.ObservationDefinitionId \r\n");
        sb.append("            and ObservationDefinitionInsurer.ViewId = 6 \r\n");
        sb.append("        left join ObservationDefinition \r\n");
        sb.append("            on ObservationDefinition.ObservationDefinitionId = Observation.ObservationDefinitionId \r\n");
        sb.append("        left join ObservationDefinitionEnumValue \r\n");
        sb.append("            on ObservationDefinitionEnumValue.ViewId = ObservationDefinitionInsurer.ViewId \r\n");
        sb.append("            and ObservationDefinitionEnumValue.ObservationDefinitionId = Observation.ObservationDefinitionId \r\n");
        sb.append("            and ObservationDefinitionEnumValue.EnumValue = Observation.Value \r\n");
        sb.append("    where \r\n");
        sb.append("        Observation.ObservationEventId = ? \r\n");
        sb.append("    order by \r\n");
        sb.append("        ObservationDefinitionInsurer.SortNo \r\n");
        sb.append("    ) as table1 \r\n");
        sb.append("    left join ObservationAlert \r\n");
        sb.append("        on table1.ObservationEventId = ObservationAlert.ObservationEventId \r\n");
        sb.append("        and table1.ObservationDefinitionId = ObservationAlert.ObservationDefinitionId \r\n");
        sb.append("        and ObservationAlert.ViewId > 1 \r\n");
        sb.append("order by SortNo \r\n");

        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationEventId);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        resList = new ArrayList<>();
        while (dataTable.next()) {
            ObservationEventEntity entity = ObservationEventEntity.setData(dataTable);
            entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationDefinitionName(AbstractEntity.getString(dataTable, "DisplayName"));
            String enumStr = AbstractEntity.getString(dataTable, "EnumName");
            if(enumStr==null){
                entity.setObservationValue(AbstractEntity.getString(dataTable, "Value"));
            }else{
                entity.setObservationValue(enumStr);
            }
            if(AbstractEntity.getNullInt(dataTable, "OutRangeTypeCd")==null){
                entity.setOutRangeTypeCd(-1);
            }else{
                entity.setOutRangeTypeCd(AbstractEntity.getNullInt(dataTable, "OutRangeTypeCd"));
            }
//            entity.setUnitValue(AbstractEntity.getString(dataTable, "UnitValue"));
            entity.setUnitValue(AbstractEntity.getString(dataTable, "Unit"));
            entity.setUpdateDateTimeA(AbstractEntity.getDateTime(dataTable, "UpdateDateTimeA"));
            if(AbstractEntity.getNullInt(dataTable, "AlertLevelCd")==null){
                entity.setAlertLevelCd(9); //その他「9」を設定
            }else{
                entity.setAlertLevelCd(AbstractEntity.getNullInt(dataTable, "AlertLevelCd"));
            }
            entity.setDisplayName(AbstractEntity.getString(dataTable, "DisplayName"));
            entity.setNewExaminationDate(AbstractEntity.getDateTime(dataTable, "NewExaminationDate"));
            if(AbstractEntity.getNullInt(dataTable, "ReminderTypeCd")==null){
                entity.setReminderTypeCd(99); //なし「99」を設定
            }else{
                entity.setReminderTypeCd(AbstractEntity.getNullInt(dataTable, "ReminderTypeCd"));
            }
            entity.setObservationEventId(AbstractEntity.getString(dataTable, "ObservationEventId"));
            
            entity.setMaxReferenceValue(getNullDouble(dataTable, "MaxReferenceValue"));
            entity.setMinReferenceValue(getNullDouble(dataTable, "MinReferenceValue"));
            entity.setSortNo(AbstractEntity.getNullInt(dataTable, "SortNo"));
            
            resList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resList;
    }

    /**
     * ObservationEventIdに紐づくobservationリソースをすべて取得する(自己測定、特定検診)
     * @param observationEventId
     * @return
     * @throws Throwable 
     */
    public List<ObservationEntity> getObservation23ByObservationEventId(String observationEventId) throws Throwable {
        List<ObservationEntity> list = new ArrayList<ObservationEntity>();
        DataAccessObject dao = new DataAccessObject(connection);

        StringBuilder sb = new StringBuilder();
        sb.append("  select \r\n");
        sb.append("    Observation.ObservationEventId As ObservationEventId \r\n");
        sb.append("    , Observation.ObservationDefinitionId As ObservationDefinitionId \r\n");
        sb.append("    , case  \r\n");
        sb.append("      when ObservationDefinitionEnumValue.EnumName is null  \r\n");
        sb.append("        then Observation.Value  \r\n");
        sb.append("      else ObservationDefinitionEnumValue.EnumName  \r\n");
        sb.append("      end As Value \r\n");
        sb.append("    , Observation.MinReferenceValue As MinReferenceValue1 \r\n");
        sb.append("    , Observation.MaxReferenceValue As MaxReferenceValue1 \r\n");
        sb.append("    , Observation.Unit As Unit1 \r\n");
        sb.append(", case  \r\n");
        sb.append("  when Observation.OutRangeTypeCd is null  \r\n");
        sb.append("    then '-1'  \r\n");
        sb.append("  else Observation.OutRangeTypeCd  \r\n");
        sb.append("  end As OutRangeTypeCd \r\n");
        sb.append(", ObservationEvent.ExaminationDate As ExaminationDate \r\n");
        sb.append(", ObservationEvent.DataInputTypeCd As DataInputTypeCd \r\n");
        sb.append(", ObservationDefinition.DisplayName As DisplayName \r\n");
        sb.append(", case  \r\n");
        sb.append("  when PhrReferenceRange.MinReferenceValue is null  \r\n");
        sb.append("  and PhrReferenceRange.MaxReferenceValue is null  \r\n");
        sb.append("    then case (  \r\n");
        sb.append("      select \r\n");
        sb.append("        Patient.SexCd  \r\n");
        sb.append("      from \r\n");
        sb.append("        Patient  \r\n");
        sb.append("      where \r\n");
        sb.append("        Patient.PHR_ID = ObservationEvent.PHR_ID \r\n");
        sb.append("   )  \r\n");
        sb.append("    when 'M' then PhrReferenceRange.MaleMinReferenceValue  \r\n");
        sb.append("    when 'F' then PhrReferenceRange.FemaleMinReferenceValue  \r\n");
        sb.append("    end  \r\n");
        sb.append("  else PhrReferenceRange.MinReferenceValue  \r\n");
        sb.append("  end As MinReferenceValue2 \r\n");
        sb.append(", case  \r\n");
        sb.append("  when PhrReferenceRange.MinReferenceValue is null  \r\n");
        sb.append("  and PhrReferenceRange.MaxReferenceValue is null  \r\n");
        sb.append("    then case (  \r\n");
        sb.append("      select \r\n");
        sb.append("        Patient.SexCd  \r\n");
        sb.append("      from \r\n");
        sb.append("        Patient  \r\n");
        sb.append("      where \r\n");
        sb.append("        Patient.PHR_ID = ObservationEvent.PHR_ID \r\n");
        sb.append("    )  \r\n");
        sb.append("    when 'M' then PhrReferenceRange.MaleMaxReferenceValue  \r\n");
        sb.append("    when 'F' then PhrReferenceRange.FemaleMaxReferenceValue  \r\n");
        sb.append("        end  \r\n");
        sb.append("      else PhrReferenceRange.MaxReferenceValue  \r\n");
        sb.append("      end As MaxReferenceValue2 \r\n");
        sb.append("    , PhrReferenceRange.Unit As Unit2 \r\n");
        sb.append("    , PhrReferenceRange.ReferenceEnumName As ReferenceEnumName  \r\n");
        sb.append("    , MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName   \r\n");
        sb.append("  from \r\n");
        sb.append("    Observation  \r\n");
        sb.append("    inner join ObservationEvent  \r\n");
        sb.append("      on Observation.ObservationEventId = ObservationEvent.ObservationEventId  \r\n");
        sb.append("    inner join ObservationDefinition  \r\n");
        sb.append("      on ObservationDefinition.ObservationDefinitionId = Observation.ObservationDefinitionId  \r\n");
        sb.append("    left outer join PhrReferenceRange  \r\n");
        sb.append("      on PhrReferenceRange.ObservationDefinitionId = Observation.ObservationDefinitionId  \r\n");
        sb.append("    left outer join ObservationDefinitionEnumValue  \r\n");
        sb.append("      on ObservationDefinitionEnumValue.ViewId = 6  \r\n");
        sb.append("      and ObservationDefinitionEnumValue.ObservationDefinitionId = Observation.ObservationDefinitionId  \r\n");
        sb.append("      and ObservationDefinitionEnumValue.EnumValue = Observation.Value  \r\n");
        sb.append("    left outer join ObservationDefinitionInsurer  \r\n");
        sb.append("      on ObservationDefinitionInsurer.ObservationDefinitionId = Observation.ObservationDefinitionId  \r\n");
        sb.append("      and ObservationDefinitionInsurer.ViewId in (6) \r\n");
        sb.append("    left outer join MedicalOrganization   \r\n");
        sb.append("      on MedicalOrganization.MedicalOrganizationCd = ObservationEvent.MedicalOrganizationCd   \r\n");
        sb.append("  where \r\n");
        sb.append("    Observation.ObservationEventId = ? \r\n");
        sb.append("  order by \r\n");
        sb.append("  ObservationDefinition.SortNo \r\n");
        
        dao.setSql(sb.toString());
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationEventId);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            ObservationEntity entity = new ObservationEntity();
            entity.setObservationEventId(AbstractEntity.getString(dataTable, "ObservationEventId"));
            entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
            entity.setValue(AbstractEntity.getString(dataTable, "Value"));
            entity.setMaxReferenceValue1(getNullDouble(dataTable, "MaxReferenceValue1"));
            entity.setMinReferenceValue1(getNullDouble(dataTable, "MinReferenceValue1"));
            entity.setUnit1(AbstractEntity.getString(dataTable, "Unit1"));
            entity.setOutRangeTypeCd(AbstractEntity.getNullInt(dataTable, "OutRangeTypeCd"));
            entity.setExaminationDate(getDateTime(dataTable, "ExaminationDate"));
            entity.setDataInputTypeCd(getInt(dataTable, "DataInputTypeCd"));
            entity.setObservationDefinitionName(AbstractEntity.getString(dataTable, "DisplayName"));
            entity.setMaxReferenceValue2(getNullDouble(dataTable, "MaxReferenceValue2"));
            entity.setMinReferenceValue2(getNullDouble(dataTable, "MinReferenceValue2"));
            entity.setUnit2(AbstractEntity.getString(dataTable, "Unit2"));
            entity.setReferenceEnumName(AbstractEntity.getString(dataTable, "ReferenceEnumName"));
            entity.setMedicalOrganizationName(AbstractEntity.getString(dataTable, "MedicalOrganizationName"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
    
    /**
     * ObservationEventIdに紐づくobservationリソースをすべて取得する(自己管理)
     * @param observationEventId
     * @return
     * @throws Throwable 
     */
    public List<ObservationEntity> getObservation1ByObservationEventId(String observationEventId) throws Throwable {
        List<ObservationEntity> list = new ArrayList<ObservationEntity>();
        DataAccessObject dao = new DataAccessObject(connection);

        String sql = getAllDiseaseSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationEventId);
        preparedStatement.setString(2, observationEventId);
        preparedStatement.setString(3, observationEventId);
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            ObservationEntity entity = new ObservationEntity();
            entity.setObservationEventId(AbstractEntity.getString(dataTable, "ObservationEventId"));
            entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
            entity.setValue(AbstractEntity.getString(dataTable, "Value"));
            entity.setMaxReferenceValue1(getNullDouble(dataTable, "MaxReferenceValue1"));
            entity.setMinReferenceValue1(getNullDouble(dataTable, "MinReferenceValue1"));
            entity.setUnit1(AbstractEntity.getString(dataTable, "Unit1"));
            entity.setOutRangeTypeCd(AbstractEntity.getNullInt(dataTable, "OutRangeTypeCd"));
            entity.setExaminationDate(getDateTime(dataTable, "ExaminationDate"));
            entity.setDataInputTypeCd(getInt(dataTable, "DataInputTypeCd"));
            entity.setObservationDefinitionName(AbstractEntity.getString(dataTable, "DisplayName"));
            entity.setMaxReferenceValue2(getNullDouble(dataTable, "MaxReferenceValue2"));
            entity.setMinReferenceValue2(getNullDouble(dataTable, "MinReferenceValue2"));
            entity.setUnit2(AbstractEntity.getString(dataTable, "Unit2"));
            entity.setReferenceEnumName(AbstractEntity.getString(dataTable, "ReferenceEnumName"));
            entity.setMedicalOrganizationName(AbstractEntity.getString(dataTable, "MedicalOrganizationName"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
    
    /**
     * 全検査タブの青ヘッダーのSQL
     * @return
     */
    private String getAllDiseaseSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("  (   \r\n");
        sql.append("        select DISTINCT  \r\n");
        sql.append("          Jlac10AnalyteCode.SortNo As SortNo ,   \r\n");
        sql.append("          case   \r\n");
        sql.append("            when   \r\n");
        sql.append("          left (Observation.JLAC10, 5) is null   \r\n");
        sql.append("            then table1.JLAC10   \r\n");
        sql.append("          else   \r\n");
        sql.append("          left (Observation.JLAC10, 5)   \r\n");
        sql.append("          end As JLAC10  \r\n");
        sql.append("          , table1.JLAC11 As JLAC11  \r\n");
        sql.append("          , Jlac10AnalyteCode.HospitalLabResultTargetFlg As JLAC10Flg  \r\n");
        sql.append("          , Jlac11AnalyteCode.HospitalLabResultTargetFlg As JLAC11Flg  \r\n");
        sql.append("          , Observation.ObservationEventId As ObservationEventId  \r\n");
        sql.append("          , Observation.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("          , case   \r\n");
        sql.append("          when ObservationDefinitionEnumValue.EnumName is null   \r\n");
        sql.append("            then Observation.Value   \r\n");
        sql.append("          else ObservationDefinitionEnumValue.EnumName   \r\n");
        sql.append("          end As Value  \r\n");
        sql.append("          , Observation.MinReferenceValue As MinReferenceValue1  \r\n");
        sql.append("          , Observation.MaxReferenceValue As MaxReferenceValue1  \r\n");
        sql.append("          , Observation.Unit As Unit1  \r\n");
        sql.append("      , case   \r\n");
        sql.append("      when Observation.OutRangeTypeCd is null   \r\n");
        sql.append("        then '-1'   \r\n");
        sql.append("      else Observation.OutRangeTypeCd   \r\n");
        sql.append("      end As OutRangeTypeCd  \r\n");
        sql.append("      , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        sql.append("      , ObservationEvent.DataInputTypeCd As DataInputTypeCd  \r\n");
        sql.append("      , ObservationDefinition.DisplayName As DisplayName  \r\n");
        sql.append("      , case   \r\n");
        sql.append("        when CommonReferenceRange.CommonLowerLimit is null   \r\n");
        sql.append("        and CommonReferenceRange.CommonUpperLimit is null   \r\n");
        sql.append("          then case (select Patient.SexCd from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID)   \r\n");
        sql.append("          when 'M' then CommonReferenceRange.MaleLowerLimit   \r\n");
        sql.append("          when 'F' then CommonReferenceRange.FemaleLowerLimit   \r\n");
        sql.append("          end   \r\n");
        sql.append("        else CommonReferenceRange.CommonLowerLimit   \r\n");
        sql.append("        end As MinReferenceValue2  \r\n");
        sql.append("      , case   \r\n");
        sql.append("        when CommonReferenceRange.CommonLowerLimit is null   \r\n");
        sql.append("        and CommonReferenceRange.CommonUpperLimit is null   \r\n");
        sql.append("          then case (select Patient.SexCd from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID)   \r\n");
        sql.append("          when 'M' then CommonReferenceRange.MaleUpperLimit   \r\n");
        sql.append("          when 'F' then CommonReferenceRange.FemaleUpperLimit   \r\n");
        sql.append("          end   \r\n");
        sql.append("        else CommonReferenceRange.CommonUpperLimit   \r\n");
        sql.append("        end As MaxReferenceValue2  \r\n");
        sql.append("      , CommonReferenceRange.Unit As Unit2  \r\n");
        sql.append("      , null As ReferenceEnumName   \r\n");
        sql.append("      , MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName   \r\n");
        sql.append("    from  \r\n");
        sql.append("      Observation   \r\n");
        sql.append("      inner join ObservationEvent   \r\n");
        sql.append("        on Observation.ObservationEventId = ObservationEvent.ObservationEventId   \r\n");
        sql.append("      inner join ObservationDefinition   \r\n");
        sql.append("        on ObservationDefinition.ObservationDefinitionId = Observation.ObservationDefinitionId   \r\n");
        sql.append("      left outer join ObservationDefinitionEnumValue   \r\n");
        sql.append("        on ObservationDefinitionEnumValue.ViewId = 6   \r\n");
        sql.append("        and ObservationDefinitionEnumValue.ObservationDefinitionId = Observation.ObservationDefinitionId   \r\n");
        sql.append("        and ObservationDefinitionEnumValue.EnumValue = Observation.Value   \r\n");
        sql.append("      left outer join (select ObservationDefinitionId , max(left (ObservationDefinitionJlac10.JLAC10, 5)) As JLAC10 , max(left (ObservationDefinitionJlac10.JLAC11, 5)) As JLAC11 from ObservationDefinitionJlac10 group by ObservationDefinitionId) As table1   \r\n");
        sql.append("        on table1.ObservationDefinitionId = Observation.ObservationDefinitionId   \r\n");
        sql.append("      left outer join CommonReferenceRange   \r\n");
        sql.append("        on CommonReferenceRange.JLAC10AnalyteCode = table1.JLAC10   \r\n");
        sql.append("        and CommonReferenceRange.ReferenceRangeType in (0, (select case when Patient.SexCd = 'M' then 1 else 2 end from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID))   \r\n");
        sql.append("      left outer join Jlac10AnalyteCode   \r\n");
        sql.append("        on Jlac10AnalyteCode.AnalyteType = table1.JLAC10   \r\n");
        sql.append("      left outer join Jlac11AnalyteCode   \r\n");
        sql.append("        on Jlac11AnalyteCode.AnalyteType = table1.JLAC11   \r\n");
        sql.append("      left outer join MedicalOrganization   \r\n");
        sql.append("        on MedicalOrganization.MedicalOrganizationCd = ObservationEvent.MedicalOrganizationCd   \r\n");
        sql.append("    where  \r\n");
        sql.append("      Observation.ObservationEventId = ?  \r\n");
        sql.append("      and not (Jlac10AnalyteCode.HospitalLabResultTargetFlg = 0 and Jlac11AnalyteCode.HospitalLabResultTargetFlg = 0)   \r\n");
        sql.append("      and not (Jlac10AnalyteCode.HospitalLabResultTargetFlg = 0 and Jlac11AnalyteCode.HospitalLabResultTargetFlg is null)  \r\n");
        sql.append("  )   \r\n");
        sql.append("  union all (   \r\n");
        sql.append("    select DISTINCT  \r\n");
        sql.append("      Jlac10AnalyteCode.SortNo As SortNo ,   \r\n");
        sql.append("      Jlac10AnalyteCode.AnalyteType As JLAC10  \r\n");
        sql.append("      , Jlac11AnalyteCode.AnalyteType As JLAC11  \r\n");
        sql.append("      , Jlac10AnalyteCode.HospitalLabResultTargetFlg As JLAC10Flg  \r\n");
        sql.append("      , Jlac11AnalyteCode.HospitalLabResultTargetFlg As JLAC11Flg  \r\n");
        sql.append("      , Observation.ObservationEventId As ObservationEventId  \r\n");
        sql.append("      , Observation.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("      , Observation.Value As Value  \r\n");
        sql.append("      , Observation.MinReferenceValue As MinReferenceValue1  \r\n");
        sql.append("      , Observation.MaxReferenceValue As MaxReferenceValue1  \r\n");
        sql.append("      , Observation.Unit As Unit1  \r\n");
        sql.append("      , case   \r\n");
        sql.append("        when Observation.OutRangeTypeCd is null   \r\n");
        sql.append("          then '-1'   \r\n");
        sql.append("        else Observation.OutRangeTypeCd   \r\n");
        sql.append("        end As OutRangeTypeCd  \r\n");
        sql.append("      , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        sql.append("      , ObservationEvent.DataInputTypeCd As DataInputTypeCd  \r\n");
        sql.append("      , Jlac10AnalyteCode.AnalyteName1 As DisplayName  \r\n");
        sql.append("      , case   \r\n");
        sql.append("        when CommonReferenceRange.CommonLowerLimit is null   \r\n");
        sql.append("        and CommonReferenceRange.CommonUpperLimit is null   \r\n");
        sql.append("          then case (select Patient.SexCd from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID)   \r\n");
        sql.append("          when 'M' then CommonReferenceRange.MaleLowerLimit   \r\n");
        sql.append("          when 'F' then CommonReferenceRange.FemaleLowerLimit   \r\n");
        sql.append("          end   \r\n");
        sql.append("        else CommonReferenceRange.CommonLowerLimit   \r\n");
        sql.append("        end As MinReferenceValue2  \r\n");
        sql.append("      , case   \r\n");
        sql.append("        when CommonReferenceRange.CommonLowerLimit is null   \r\n");
        sql.append("        and CommonReferenceRange.CommonUpperLimit is null   \r\n");
        sql.append("          then case (select Patient.SexCd from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID)   \r\n");
        sql.append("          when 'M' then CommonReferenceRange.MaleUpperLimit   \r\n");
        sql.append("          when 'F' then CommonReferenceRange.FemaleUpperLimit   \r\n");
        sql.append("          end   \r\n");
        sql.append("        else CommonReferenceRange.CommonUpperLimit   \r\n");
        sql.append("        end As MaxReferenceValue2  \r\n");
        sql.append("      , CommonReferenceRange.Unit As Unit2  \r\n");
        sql.append("      , null As ReferenceEnumName   \r\n");
        sql.append("      , MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName   \r\n");
        sql.append("    from  \r\n");
        sql.append("      Observation   \r\n");
        sql.append("      inner join ObservationEvent   \r\n");
        sql.append("        on Observation.ObservationEventId = ObservationEvent.ObservationEventId   \r\n");
        sql.append("      inner join Jlac10AnalyteCode   \r\n");
        sql.append("        on Jlac10AnalyteCode.AnalyteType = left(Observation.ObservationDefinitionId, 5)   \r\n");
        sql.append("      left outer join CommonReferenceRange   \r\n");
        sql.append("        on CommonReferenceRange.JLAC10AnalyteCode = Jlac10AnalyteCode.AnalyteType   \r\n");
        sql.append("        and CommonReferenceRange.ReferenceRangeType in (0, (select case when Patient.SexCd = 'M' then 1 else 2 end from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID))   \r\n");
        sql.append("      left outer join Jlac11AnalyteCode   \r\n");
        sql.append("        on Jlac11AnalyteCode.JLAC10AnalyteCode = Jlac10AnalyteCode.AnalyteType   \r\n");
        sql.append("      left outer join MedicalOrganization   \r\n");
        sql.append("        on MedicalOrganization.MedicalOrganizationCd = ObservationEvent.MedicalOrganizationCd   \r\n");
        sql.append("    where  \r\n");
        sql.append("      Observation.ObservationEventId = ?  \r\n");
        sql.append("      and not (Jlac10AnalyteCode.HospitalLabResultTargetFlg = 0 and Jlac11AnalyteCode.HospitalLabResultTargetFlg = 0)   \r\n");
        sql.append("      and not (Jlac10AnalyteCode.HospitalLabResultTargetFlg = 0 and Jlac11AnalyteCode.HospitalLabResultTargetFlg is null)  \r\n");
        sql.append("  )   \r\n");
        sql.append("  union all (   \r\n");
        sql.append("    select DISTINCT  \r\n");
        sql.append("      Jlac10AnalyteCode.SortNo As SortNo ,   \r\n");
        sql.append("      Jlac10AnalyteCode.AnalyteType As JLAC10  \r\n");
        sql.append("      , Jlac11AnalyteCode.AnalyteType As JLAC11  \r\n");
        sql.append("      , Jlac10AnalyteCode.HospitalLabResultTargetFlg As JLAC10Flg  \r\n");
        sql.append("      , Jlac11AnalyteCode.HospitalLabResultTargetFlg As JLAC11Flg  \r\n");
        sql.append("      , Observation.ObservationEventId As ObservationEventId  \r\n");
        sql.append("      , Observation.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("      , Observation.Value As Value  \r\n");
        sql.append("      , Observation.MinReferenceValue As MinReferenceValue1  \r\n");
        sql.append("      , Observation.MaxReferenceValue As MaxReferenceValue1  \r\n");
        sql.append("      , Observation.Unit As Unit1  \r\n");
        sql.append("      , case   \r\n");
        sql.append("        when Observation.OutRangeTypeCd is null   \r\n");
        sql.append("          then '-1'   \r\n");
        sql.append("        else Observation.OutRangeTypeCd   \r\n");
        sql.append("        end As OutRangeTypeCd  \r\n");
        sql.append("      , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        sql.append("      , ObservationEvent.DataInputTypeCd As DataInputTypeCd  \r\n");
        sql.append("      , Jlac11AnalyteCode.AnalyteName As DisplayName  \r\n");
        sql.append("      , case   \r\n");
        sql.append("        when CommonReferenceRange.CommonLowerLimit is null   \r\n");
        sql.append("        and CommonReferenceRange.CommonUpperLimit is null   \r\n");
        sql.append("          then case (select Patient.SexCd from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID)   \r\n");
        sql.append("          when 'M' then CommonReferenceRange.MaleLowerLimit   \r\n");
        sql.append("          when 'F' then CommonReferenceRange.FemaleLowerLimit   \r\n");
        sql.append("          end   \r\n");
        sql.append("        else CommonReferenceRange.CommonLowerLimit   \r\n");
        sql.append("        end As MinReferenceValue2  \r\n");
        sql.append("      , case   \r\n");
        sql.append("        when CommonReferenceRange.CommonLowerLimit is null   \r\n");
        sql.append("        and CommonReferenceRange.CommonUpperLimit is null   \r\n");
        sql.append("          then case (select Patient.SexCd from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID)   \r\n");
        sql.append("          when 'M' then CommonReferenceRange.MaleUpperLimit   \r\n");
        sql.append("          when 'F' then CommonReferenceRange.FemaleUpperLimit   \r\n");
        sql.append("          end   \r\n");
        sql.append("        else CommonReferenceRange.CommonUpperLimit   \r\n");
        sql.append("        end As MaxReferenceValue2  \r\n");
        sql.append("      , CommonReferenceRange.Unit As Unit2  \r\n");
        sql.append("      , null As ReferenceEnumName   \r\n");
        sql.append("      , MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName   \r\n");
        sql.append("    from  \r\n");
        sql.append("      Observation   \r\n");
        sql.append("      inner join ObservationEvent   \r\n");
        sql.append("        on Observation.ObservationEventId = ObservationEvent.ObservationEventId   \r\n");
        sql.append("      inner join Jlac11AnalyteCode   \r\n");
        sql.append("        on Jlac11AnalyteCode.AnalyteType = left(Observation.ObservationDefinitionId, 5)   \r\n");
        sql.append("      left outer join Jlac10AnalyteCode   \r\n");
        sql.append("        on Jlac10AnalyteCode.AnalyteType = Jlac11AnalyteCode.JLAC10AnalyteCode   \r\n");
        sql.append("      left outer join CommonReferenceRange   \r\n");
        sql.append("        on CommonReferenceRange.JLAC10AnalyteCode = Jlac10AnalyteCode.AnalyteType   \r\n");
        sql.append("        and CommonReferenceRange.ReferenceRangeType in (0, (select case when Patient.SexCd = 'M' then 1 else 2 end from Patient where Patient.PHR_ID = ObservationEvent.PHR_ID))   \r\n");
        sql.append("      left outer join MedicalOrganization   \r\n");
        sql.append("        on MedicalOrganization.MedicalOrganizationCd = ObservationEvent.MedicalOrganizationCd   \r\n");
        sql.append("    where  \r\n");
        sql.append("      Observation.ObservationEventId = ?  \r\n");
        sql.append("      and not (Jlac10AnalyteCode.HospitalLabResultTargetFlg = 0 and Jlac11AnalyteCode.HospitalLabResultTargetFlg = 0)   \r\n");
        sql.append("      and not (Jlac10AnalyteCode.HospitalLabResultTargetFlg is null and Jlac11AnalyteCode.HospitalLabResultTargetFlg = 0)  \r\n");
        sql.append("  )   \r\n");
//        sql.append("  order by JLAC10, JLAC11  \r\n");
        sql.append("  order by SortNo  \r\n");

        return sql.toString();
    }

    /**
     * 過去の疾病発病を取得
     * @param phrid
     * @param examinationDate
     * @return
     * @throws SQLException 
     */
    public List<Integer> getDiseaseType(String phrid, Timestamp examinationDate) throws SQLException {
        List<Integer> list = new ArrayList<Integer>();
        DataAccessObject dao = new DataAccessObject(connection);

        StringBuilder sql = new StringBuilder();
        sql.append("  (   \r\n");
        sql.append("    select distinct  \r\n");
        sql.append("      diseasetype.DiseaseTypeCd As DiseaseTypeCd   \r\n");
        sql.append("    from  \r\n");
        sql.append("      observation   \r\n");
        sql.append("      inner join observationevent   \r\n");
        sql.append("        on observation.ObservationEventId = observationevent.ObservationEventId   \r\n");
        sql.append("      inner join diseasetype   \r\n");
        sql.append("        on diseasetype.ObservationDefinitionId = observation.ObservationDefinitionId   \r\n");
        sql.append("    where  \r\n");
        sql.append("      observationevent.DataInputTypeCd = 1   \r\n");
        sql.append("      and observationevent.PHR_ID = ?  \r\n");
        sql.append("      and observation.ObservationDefinitionId in (0000000011, 0000000015, 0000000019, 0000000021)   \r\n");
        sql.append("      and observation.DiseaseManagementTargetFlg = true   \r\n");
        sql.append("      and observationevent.DiseaseManagementTargetFlg = true   \r\n");
        sql.append("      and observationevent.ExaminationDate <= ?  \r\n");
        sql.append("  )   \r\n");
        sql.append("  union all (   \r\n");
        sql.append("    select distinct  \r\n");
        sql.append("      diseasetype.DiseaseTypeCd As DiseaseTypeCd   \r\n");
        sql.append("    from  \r\n");
        sql.append("      observation   \r\n");
        sql.append("      inner join observationevent   \r\n");
        sql.append("        on observation.ObservationEventId = observationevent.ObservationEventId   \r\n");
        sql.append("      inner join diseasetype   \r\n");
        sql.append("        on diseasetype.ObservationDefinitionId = observation.ObservationDefinitionId   \r\n");
        sql.append("    where  \r\n");
        sql.append("      observationevent.DataInputTypeCd = 1   \r\n");
        sql.append("      and observationevent.PHR_ID = ?  \r\n");
        sql.append("      and observation.ObservationDefinitionId = 0000000020   \r\n");
        sql.append("      and observation.Value in (1, 2)   \r\n");
        sql.append("      and observation.DiseaseManagementTargetFlg = true   \r\n");
        sql.append("      and observationevent.DiseaseManagementTargetFlg = true   \r\n");
        sql.append("      and observationevent.ExaminationDate <= ?  \r\n");
        sql.append("  )   \r\n");
        sql.append("  union all (   \r\n");
        sql.append("    select distinct  \r\n");
        sql.append("      diseasetype.DiseaseTypeCd As DiseaseTypeCd   \r\n");
        sql.append("    from  \r\n");
        sql.append("      observation   \r\n");
        sql.append("      inner join observationevent   \r\n");
        sql.append("        on observation.ObservationEventId = observationevent.ObservationEventId   \r\n");
        sql.append("      inner join diseasetype   \r\n");
        sql.append("        on diseasetype.ObservationDefinitionId = observation.ObservationDefinitionId   \r\n");
        sql.append("    where  \r\n");
        sql.append("      observationevent.DataInputTypeCd = 1   \r\n");
        sql.append("      and observationevent.PHR_ID = ?  \r\n");
        sql.append("      and observation.ObservationDefinitionId = 0000000103   \r\n");
        sql.append("      and observation.Value in (1, 2, 3, 4)   \r\n");
        sql.append("      and observation.DiseaseManagementTargetFlg = true   \r\n");
        sql.append("      and observationevent.DiseaseManagementTargetFlg = true   \r\n");
        sql.append("      and observationevent.ExaminationDate <= ?  \r\n");
        sql.append("  )   \r\n");
        sql.append("  order by DiseaseTypeCd  \r\n");
        dao.setSql(sql.toString());
        
        java.sql.Date date = new java.sql.Date(examinationDate.getTime());
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrid);
        preparedStatement.setDate(2, date);
        preparedStatement.setString(3, phrid);
        preparedStatement.setDate(4, date);
        preparedStatement.setString(5, phrid);
        preparedStatement.setDate(6, date);
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            list.add(AbstractEntity.getInt(dataTable, "DiseaseTypeCd"));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }

    /**
     * ObservationEventIdの値で検査結果レコードを削除
     * @param id
     * @return
     * @throws Throwable
     */
    public int deleteObservationRecord(String id) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("delete from Observation \r\n");
        sql.append("where Observation.ObservationEventId = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, id);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();

        logger.debug("End");
        return rowCount;
    }

    /**
     * ObservationEventIdの値で検査結果レコードを取得
     * @param id
     * @return
     * @throws Throwable
     */
    public List<ObservationEntity> findById(String id) throws Throwable {
        logger.debug("Start");
        List<ObservationEntity> list = new ArrayList<>();
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from Observation \r\n");
        sql.append("where Observation.ObservationEventId = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, id);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        list = new ArrayList<>();
        while (dataTable.next()) {
            ObservationEntity entity = ObservationEntity.setData(dataTable);
            list.add(entity);
        }
        dao.clearBindParam();
        preparedStatement.close();

        logger.debug("End");
        return list;
    }

    /**
     * ObservationEventIdで検査項目結果の検索（自己測定結果）
     *
     * @param observationEventId
     * @return 検査項目結果一覧
     * @throws Throwable
     */
    public List<ObservationEntity> findByObservationEventIdForSelfCheck(String observationEventId) throws Throwable {
        logger.debug("Start");
        List<ObservationEntity> resList;
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "where Observation.ObservationEventId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationEventId);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        resList = new ArrayList<>();
        while (dataTable.next()) {
            ObservationEntity entity = ObservationEntity.setData(dataTable);
            entity.setFlag(getNullBoolean(dataTable, "DiseaseManagementTargetFlg"));
            resList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return resList;
    }
}
