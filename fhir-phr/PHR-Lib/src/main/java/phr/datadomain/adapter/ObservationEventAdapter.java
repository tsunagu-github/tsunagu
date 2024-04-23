
/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getDateTime;
import static phr.datadomain.AbstractEntity.getInt;
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.UtilizationConsentEntity;

/**
 * 検査結果情報のデータオブジェクトです。
 */
public class ObservationEventAdapter extends ObservationEventAdapterBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationEventAdapter.class);
    private static Logger logger = Logger.getLogger(ObservationEventAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationEventAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * 検査結果IDを採番する
     * @return
     * @throws Throwable 
     */
    public static String numberingObservationEventId() throws Throwable {
        String dosageId = "0000000000";
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            String sql = "update Seq_ObservationEventId set ObservationEventId=LAST_INSERT_ID(ObservationEventId+1)";
            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            int rowCount = preparedStatement.executeUpdate();
            String sql2 = "SELECT LAST_INSERT_ID() as SeqId";
            preparedStatement.close();

            dao.setSql(sql2);
            dao.clearBindParam();
            PreparedStatement preparedStatement2 = dao.getPreparedStatement();
            ResultSet dataTable = preparedStatement2.executeQuery();
            if (dataTable == null) {
                return null;
            }

            while (dataTable.next()) {
                long id = AbstractEntity.getLong(dataTable, "SeqId");
                dosageId = String.format("%010d", id);
            }

            // dosageId = '0000000000'の時
            if (dosageId.equals("0000000000")) {
                String sql3 = "insert into seq_ObservationEventId value ('0000000000')";
                dao.setSql(sql3);
                dao.clearBindParam();
                PreparedStatement preparedStatement3 = dao.getPreparedStatement();
                preparedStatement3.executeUpdate();
            }

            dao.clearBindParam();
            dataTable.close();
            preparedStatement2.close();
            dao.commitTransaction();
            return dosageId;
        } catch (ClassNotFoundException | SQLException ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error(ex.toString(), ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.debug("End");
        }
    }

    /**
     * PHR_ID、検査日にて検査結果を検索します。
     * @param phrId
     * @param endDate
     * @param startDate
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> findByIdAndDate( String phrId, Date startDate, Date endDate, String insurerNo) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = this.getSelectedSqlAddAlert();
        int addCnt = 0;
        java.sql.Date sqlStartDate = null;
        java.sql.Date sqlEndDate = null;

        sql += " and ObservationEvent.InsurerNo = ?";

        if (phrId != "" && phrId != null) {
            sql += " and ObservationEvent.PHR_ID = ?";
            addCnt++;
        }
        if (startDate != null) {
            sql += " and ObservationEvent.ExaminationDate >= ?";
            addCnt++;

            sqlStartDate = new java.sql.Date(startDate.getTime());
        }
        if (endDate != null) {
            sql += " and ObservationEvent.ExaminationDate <= ?";
            addCnt++;

            sqlEndDate = new java.sql.Date(endDate.getTime());
        }

        sql += " order by left(ObservationEvent.ExaminationDate,10) DESC, ObservationEvent.PHR_ID DESC, Observation.ObservationDefinitionId ASC";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        if (phrId != null && phrId != "") {
            preparedStatement.setString(2, phrId);
            if (sqlStartDate != null) {
                preparedStatement.setDate(3, sqlStartDate);
            }
        } else {
            if (sqlStartDate != null) {
                preparedStatement.setDate(2, sqlStartDate);
            }
        }
        if (sqlEndDate != null) {
            switch (addCnt) {
            case 1:
                preparedStatement.setDate(2, sqlEndDate);
                break;
            case 2:
                preparedStatement.setDate(3, sqlEndDate);
                break;
            case 3:
                preparedStatement.setDate(4, sqlEndDate);
                break;
            default:
                break;
            }
        }

        List<ObservationEventEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationEventEntity entity = ObservationEventEntity.setDataPatient(dataTable);
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return ret;
    }

    /**
     * ユーザIDと検査NOで検査結果を検索します。
     * @param phrid
     * @param orderNo
     * @return 
     * @throws java.lang.Throwable 
     */
    public ObservationEventEntity findByIdAndOrderNo(String phrid , String orderNo) throws Throwable
    {
        ObservationEventEntity entity = null;
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationEvent.PHR_ID = ?";
        sql += " and ObservationEvent.OrderNo = ?";

        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrid);
        preparedStatement.setString(2, orderNo);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationEventEntity.setData(dataTable);
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;

    }

    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSqlAddAlert() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationEvent.ObservationEventId As ObservationEventId  \r\n");
        sql.append("    , ObservationEvent.DataInputTypeCd As DataInputTypeCd  \r\n");
        sql.append("    , ObservationEvent.PHR_ID As PHR_ID  \r\n");
        sql.append("    , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        sql.append("    , ObservationEvent.Year As Year  \r\n");
        sql.append("    , ObservationEvent.InsurerNo As InsurerNo  \r\n");
        sql.append("    , ObservationEvent.LaboratoryCd As LaboratoryCd  \r\n");
        sql.append("    , ObservationEvent.OrderNo As OrderNo  \r\n");
        sql.append("    , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , ObservationEvent.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , ObservationEvent.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , Patient.FamilyName As FamilyName  \r\n");
        sql.append("    , Patient.GivenName As GivenName  \r\n");
        sql.append("    , Patient.FamilyKana As FamilyKana  \r\n");
        sql.append("    , Patient.GivenKana As GivenKana  \r\n");
        sql.append("    , Observation.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , Observation.AlertFlg As AlertFlg  \r\n");
        sql.append("    , ObservationDefinition.ObservationDefinitionName As ObservationDefinitionName  \r\n");
        sql.append("from ObservationEvent,  Patient, Observation, ObservationDefinition \r\n");
        sql.append("where  Observation.AlertFlg = 1 \r\n");
        sql.append("and  ObservationEvent.PHR_ID = Patient.PHR_ID \r\n");
        sql.append("and  ObservationEvent.ObservationEventId = Observation.ObservationEventId \r\n");
        sql.append("and  Observation.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \r\n");

        return sql.toString();
    }

    /**
     * 対象年度の検査結果から基準日の直近の値を検索(全部取得)
     * @param iYear
     * @param phrid
     * @param basedate
     * @return 
     * @throws java.lang.Throwable 
     */
    public List<ObservationEventEntity> findByMostRecent(int iYear , String phrid, Timestamp basedate , int viewId) throws Throwable
    {
        return findByMostRecent(iYear, phrid,basedate,-1 , viewId);
    }

    /**
     * 対象年度の検査結果から基準日の直近の値を検索（データ入力種別指定）
     * @param iYear
     * @param phrid
     * @param basedate
     * @param DataInputTypeCd データ入力種別（1:検査結果、2:家庭測定、3:健診検査、4:健診問診、5:健診診察、-1:全部）
     * @return 
     * @throws java.lang.Throwable 
     */
    public List<ObservationEventEntity> findByMostRecent(int iYear , String phrid, Timestamp basedate, int DataInputTypeCd , int viewId) throws Throwable
    {
        logger.debug("Start");
        List<ObservationEventEntity> list = new ArrayList<>();
        DataAccessObject dao = new DataAccessObject(connection);
        String sql=makeSqlMostRecent(DataInputTypeCd);

        dao.setSql(sql);
        dao.clearBindParam();
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            //パラメータの日付情報作成
            Date getDate = new Date(basedate.getTime());
            Calendar cal_date = Calendar.getInstance();
            cal_date.setTime(getDate);
            cal_date.add(Calendar.YEAR, -1);    //１年前を取得開始日とする
            Timestamp getTimeStamp = new Timestamp(cal_date.getTimeInMillis());

            int count = 1;

            Timestamp nowTimeStamp = new Timestamp(System.currentTimeMillis());
            Date nowDate = new Date(nowTimeStamp.getTime());
            int iNewYear;
            cal_date.setTime(nowDate);
            iNewYear=cal_date.get(Calendar.YEAR);//年
            if(cal_date.get(Calendar.MONTH) <= 3){
                iNewYear = iNewYear - 1;
            }
            if(DataInputTypeCd==1 || DataInputTypeCd==-1){
                preparedStatement.setInt(count, viewId);
                count++;
                preparedStatement.setString(count, phrid);
                count++;
                preparedStatement.setString(count, phrid);
                count++;
                preparedStatement.setInt(count, iYear);
                count++;
                preparedStatement.setTimestamp(count, basedate);
                count++;
                preparedStatement.setInt(count, viewId);
                count++;
                preparedStatement.setString(count, phrid);
                count++;
                preparedStatement.setInt(count, iNewYear);
                count++;
                preparedStatement.setTimestamp(count, nowTimeStamp);
                count++;
            }
            switch (DataInputTypeCd) {
            case 3:
                preparedStatement.setString(1, phrid);
                preparedStatement.setTimestamp(2, basedate);
                preparedStatement.setInt(count, 3);
                count++;
                break;
            case 4:
                preparedStatement.setString(count, phrid);
                count++;
                preparedStatement.setTimestamp(count, basedate);
                count++;
                preparedStatement.setInt(count, 4);
                count++;
                break;
            case 5:
                preparedStatement.setString(count, phrid);
                count++;
                preparedStatement.setTimestamp(count, basedate);
                count++;
                preparedStatement.setInt(count, 5);
                count++;
                break;
            case 2:
                preparedStatement.setString(count, phrid);
                count++;
//                preparedStatement.setInt(count, iYear);
//                count++;
//                preparedStatement.setTimestamp(count, getTimeStamp);
//                count++;
                preparedStatement.setTimestamp(count, basedate);
                count++;
                break;
            case 1:
                break;
            default:
                preparedStatement.setString(count, phrid);
                count++;
                preparedStatement.setTimestamp(count, basedate);
                count++;                  
                preparedStatement.setString(count, phrid);
                count++;
//                preparedStatement.setInt(count, iYear);
//                count++;
//                preparedStatement.setTimestamp(count, getTimeStamp);
//                count++;
                preparedStatement.setTimestamp(count, basedate);
                count++;
                break;
            }

            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return null;
                }

                int j =0;
                int tmpTypeCd=0;
                String tmpEventId="";
                while( dataTable.next() ) {
                    ObservationEventEntity tmpEntity = ObservationEventEntity.setData(dataTable);
                    tmpEntity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
                    String enumStr=AbstractEntity.getString(dataTable, "EnumName");
                    if(enumStr==null){
                        tmpEntity.setObservationValue(AbstractEntity.getString(dataTable, "Value"));
                    }else{
                        tmpEntity.setObservationValue(enumStr);
                    }
                    if(AbstractEntity.getNullInt(dataTable, "OutRangeTypeCd")==null){
                        tmpEntity.setOutRangeTypeCd(-1);
                    }else{
                        tmpEntity.setOutRangeTypeCd(AbstractEntity.getNullInt(dataTable, "OutRangeTypeCd"));
                    }
                    tmpEntity.setUnitValue(AbstractEntity.getString(dataTable, "UnitValue"));
                    tmpEntity.setUpdateDateTimeA(AbstractEntity.getDateTime(dataTable, "UpdateDateTimeA"));
                    if(AbstractEntity.getNullInt(dataTable, "AlertLevelCd")==null){
                        tmpEntity.setAlertLevelCd(9); //その他「9」を設定
                    }else{
                        tmpEntity.setAlertLevelCd(AbstractEntity.getNullInt(dataTable, "AlertLevelCd"));
                    }
                    tmpEntity.setDisplayName(AbstractEntity.getString(dataTable, "DisplayName"));
                    tmpEntity.setNewExaminationDate(AbstractEntity.getDateTime(dataTable, "NewExaminationDate"));
                    if(AbstractEntity.getNullInt(dataTable, "ReminderTypeCd")==null){
                        tmpEntity.setReminderTypeCd(99); //なし「99」を設定
                    }else{
                        tmpEntity.setReminderTypeCd(AbstractEntity.getNullInt(dataTable, "ReminderTypeCd"));
                    }
                    tmpEntity.setObservationEventId(AbstractEntity.getString(dataTable, "ObservationEventId"));
                    //                    tmpEntity.setDispNo(AbstractEntity.getNullInt(dataTable, "no"));
                    //画面設定用に連番を付番
                    //（結果はソートされてくるので、getObservationDefinitionIdが変わったらインクリメント）
                    if(tmpEntity.getDataInputTypeCd()>=3 && tmpEntity.getDataInputTypeCd()<=5){
                        if(tmpEntity.getDataInputTypeCd()!=tmpTypeCd){
                            //DataInputTypeCdが初回と変わった時は初期値設定
                            j=1;
                        }else if(!tmpEntity.getObservationEventId().equals(tmpEventId)){
                            //ObservationEventIdが変わると連番カウントアップ(DataInputTypeCdは同一)
                            j+=1;
                        }
                        tmpEntity.setDispNo(j);

                        //比較の為の前回設定情報として保持
                        tmpTypeCd=tmpEntity.getDataInputTypeCd();
                        tmpEventId=tmpEntity.getObservationEventId();
                    }else{
                        tmpEntity.setDispNo(0);
                    }
                    list.add(tmpEntity);
                }

                dao.clearBindParam();
            }
        }
        logger.debug("End");
        return list;
    }
    public String makeSqlMostRecent(int DataInputTypeCd) throws Throwable
    {
        StringBuilder sql = new StringBuilder();
        if(DataInputTypeCd==1 || DataInputTypeCd==-1){
            // kensakekka
            sql.append(" ( \r\n");
            sql.append(" SELECT  \r\n");
            sql.append(" tblB.ObservationEventId As ObservationEventId  \r\n");
            sql.append(" , tblE.DataInputTypeCd As DataInputTypeCd  \r\n");
            sql.append(" , tblD.PHR_ID As PHR_ID  \r\n");
            sql.append(" , tblB.ExaminationDate As ExaminationDate  \r\n");
            sql.append(" , ObservationEvent.Year As Year  \r\n");
            sql.append(" , ObservationEvent.InsurerNo As InsurerNo  \r\n");
            sql.append(" , ObservationEvent.LaboratoryCd As LaboratoryCd  \r\n");
            sql.append(" , ObservationEvent.OrderNo As OrderNo  \r\n");
            sql.append(" , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
            sql.append(" , ObservationEvent.CreateDateTime As CreateDateTime  \r\n");
            sql.append(" , ObservationEvent.UpdateDateTime As UpdateDateTime  \r\n");
            sql.append(" , ObservationEvent.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg  \r\n");
            sql.append(" , tblD.ObservationDefinitionId As ObservationDefinitionId  \r\n");
            sql.append(" , Observation.Value As Value  \r\n");
            sql.append(" , Observation.OutRangeTypeCd As OutRangeTypeCd  \r\n");
//            sql.append(" , ObservationDefinitionInsurer.UnitValue As UnitValue  \r\n");
            sql.append(" , Observation.Unit As UnitValue  \r\n");
            sql.append(" , Observation.UpdateDateTime As UpdateDateTimeA   \r\n");
            sql.append(" , ObservationAlert.AlertLevelCd As AlertLevelCd  \r\n");
            sql.append(" , ObservationDefinition.DisplayName As DisplayName  \r\n");
            sql.append(" , ObservationDefinitionEnumValue.EnumName As EnumName  \r\n");
            sql.append(" , tblC.NewExaminationDate As NewExaminationDate  \r\n");
            sql.append(" , tblA.ReminderTypeCd As ReminderTypeCd  \r\n");
            sql.append(" FROM  \r\n");
            sql.append(" (  \r\n");
            sql.append(" SELECT  \r\n");
            sql.append(" ObservationDefinitionInsurer.ViewId  \r\n");
            sql.append(" , ObservationDefinitionInsurer.ObservationDefinitionId  \r\n");
            sql.append(" , InsurerPatient.PHR_ID  \r\n");
            sql.append(" FROM InsurerPatient  \r\n");
            sql.append(" inner join ObservationDefinitionInsurer  \r\n");
            sql.append(" on ObservationDefinitionInsurer.ViewId = ?  \r\n");
            sql.append(" and InsurerPatient.PHR_ID = ? \r\n");
            sql.append(" group by ObservationDefinitionInsurer.ViewId, ObservationDefinitionInsurer.ObservationDefinitionId  \r\n");
            sql.append(" , InsurerPatient.PHR_ID  \r\n");
            sql.append(" ) As tblD  \r\n");
            sql.append(" inner join ObservationDefinition  \r\n");
            sql.append(" on ObservationDefinition.ObservationDefinitionId = tblD.ObservationDefinitionId  \r\n");
            sql.append(" left join (  \r\n");
            sql.append(" SELECT  \r\n");
            sql.append(" ObservationEvent.DataInputTypeCd  \r\n");
            sql.append(" , ObservationEvent.ObservationEventId  \r\n");
            sql.append(" , ObservationEvent.PHR_ID  \r\n");
            sql.append(" , MAX(ObservationEvent.ExaminationDate) ExaminationDate  \r\n");
            sql.append(" , Observation.ObservationDefinitionId  \r\n");
            sql.append(" FROM  ObservationEvent  \r\n");
            sql.append(" inner join Observation  \r\n");
            sql.append(" on ObservationEvent.ObservationEventId=Observation.ObservationEventId  \r\n");
            sql.append(" and ObservationEvent.DataInputTypeCd=1  \r\n");
            sql.append(" WHERE ObservationEvent.PHR_ID = ? \r\n");
            sql.append(" and ObservationEvent.Year <= ? \r\n");
            sql.append(" and ObservationEvent.ExaminationDate <= ? \r\n");
            sql.append(" GROUP BY ObservationEvent.DataInputTypeCd  \r\n");
            sql.append(" ,ObservationEvent.PHR_ID  \r\n");
            sql.append(" , Observation.ObservationDefinitionId  \r\n");

            // add start 2021/09/10
            sql.append(" , ObservationEvent.ObservationEventId  \r\n");
            // add end 2021/09/10

            sql.append(" ) as tblB  \r\n");
            sql.append(" on tblB.PHR_ID = tblD.PHR_ID  \r\n");
            sql.append(" and tblB.ObservationDefinitionId = tblD.ObservationDefinitionId  \r\n");
            sql.append(" left join ObservationEvent  \r\n");
            sql.append(" on ObservationEvent.ExaminationDate = tblB.ExaminationDate  \r\n");
            sql.append(" and ObservationEvent.DataInputTypeCd = tblB.DataInputTypeCd  \r\n");
            sql.append(" and ObservationEvent.PHR_ID = tblD.PHR_ID  \r\n");
            sql.append(" left join Observation  \r\n");
            sql.append(" on Observation.ObservationEventId=ObservationEvent.ObservationEventId  \r\n");
            sql.append(" and Observation.ObservationDefinitionId=tblD.ObservationDefinitionId  \r\n");
            sql.append(" left join ObservationAlert  \r\n");
            sql.append(" on ObservationEvent.ObservationEventId=ObservationAlert.ObservationEventId  \r\n");
            sql.append(" and tblD.ObservationDefinitionId = ObservationAlert.ObservationDefinitionId \r\n");
            sql.append(" and tblD.ViewId = ObservationAlert.ViewId \r\n");
            sql.append(" left join ObservationDefinitionEnumValue  \r\n");
            sql.append(" on ObservationDefinitionEnumValue.ViewId= tblD.ViewId  \r\n");
            sql.append(" and ObservationDefinitionEnumValue.ObservationDefinitionId=tblD.ObservationDefinitionId  \r\n");
            sql.append(" and ObservationDefinitionEnumValue.EnumValue=Observation.Value  \r\n");
            sql.append(" left join (  \r\n");
            sql.append(" select  \r\n");
            sql.append(" ObservationDefinitionInsurer.ViewId  \r\n");
            sql.append(" , ObservationDefinitionInsurer.ObservationDefinitionId  \r\n");
            sql.append(" , min(ObservationDefinitionInsurer.ReminderTypeCd) As ReminderTypeCd  \r\n");
            sql.append(" FROM  ObservationEvent  \r\n");
            sql.append(" inner join Observation  \r\n");
            sql.append(" on ObservationEvent.ObservationEventId=Observation.ObservationEventId  \r\n");
            sql.append(" and ObservationEvent.DataInputTypeCd=1  \r\n");
            sql.append(" left join ObservationDefinitionInsurer  \r\n");
            sql.append(" on ObservationDefinitionInsurer.ViewId= ?  \r\n");
            sql.append(" and ObservationDefinitionInsurer.ObservationDefinitionId=Observation.ObservationDefinitionId  \r\n");
            sql.append(" GROUP BY ObservationDefinitionInsurer.ViewId  \r\n");
            sql.append(" , ObservationDefinitionInsurer.ObservationDefinitionId  \r\n");
            sql.append(" ) As tblA  \r\n");
            sql.append(" on tblA.ViewId = tblD.ViewId  \r\n");
            sql.append(" and tblA.ObservationDefinitionId = tblD.ObservationDefinitionId  \r\n");
            sql.append(" inner join (  \r\n");
            sql.append(" select  \r\n");
            sql.append(" ObservationDefinitionType.InsurerNo  \r\n");
            sql.append(" , ObservationDefinitionType.ObservationDefinitionId  \r\n");
            sql.append(" , ObservationDefinitionType.DataInputTypeCd  \r\n");
            sql.append(" from ObservationDefinitionType  \r\n");
            sql.append(" where ObservationDefinitionType.DataInputTypeCd=1  \r\n");
            sql.append(" group by ObservationDefinitionType.InsurerNo, ObservationDefinitionType.ObservationDefinitionId  \r\n");
            sql.append(" , ObservationDefinitionType.DataInputTypeCd  \r\n");
            sql.append(" ) As tblE  \r\n");
            sql.append(" on tblE.ObservationDefinitionId=tblD.ObservationDefinitionId  \r\n");
            sql.append(" left join (  \r\n");
            sql.append(" SELECT  \r\n");
            sql.append(" ObservationEvent.DataInputTypeCd  \r\n");
            sql.append(" , ObservationEvent.PHR_ID  \r\n");
            sql.append(" , MAX(ObservationEvent.ExaminationDate) NewExaminationDate  \r\n");
            sql.append(" , Observation.ObservationDefinitionId  \r\n");
            sql.append(" FROM  ObservationEvent  \r\n");
            sql.append(" inner join Observation  \r\n");
            sql.append(" on ObservationEvent.ObservationEventId=Observation.ObservationEventId  \r\n");
            sql.append(" and ObservationEvent.DataInputTypeCd=1  \r\n");
            sql.append(" WHERE ObservationEvent.PHR_ID = ? \r\n");
            sql.append(" and ObservationEvent.Year <= ? \r\n");
            sql.append(" and ObservationEvent.ExaminationDate <= ? \r\n");
            sql.append(" GROUP BY ObservationEvent.DataInputTypeCd  \r\n");
            sql.append(" ,ObservationEvent.PHR_ID  \r\n");
            sql.append(" , Observation.ObservationDefinitionId  \r\n");
            sql.append(" ) as tblC  \r\n");
            sql.append(" on tblC.DataInputTypeCd = tblE.DataInputTypeCd  \r\n");
            sql.append(" and tblC.PHR_ID = tblD.PHR_ID  \r\n");
            sql.append(" and tblC.ObservationDefinitionId = tblD.ObservationDefinitionId  \r\n");
            sql.append(" left join ObservationDefinitionInsurer  \r\n");
            sql.append(" on ObservationDefinitionInsurer.ViewId= tblD.ViewId  \r\n");
            sql.append(" and ObservationDefinitionInsurer.ObservationDefinitionId=tblD.ObservationDefinitionId  \r\n");
            sql.append(" left join (  \r\n");
            sql.append(" select ObservationDefinitionInsurer.SortNo  \r\n");
            sql.append(" , ObservationDefinitionInsurer.ViewId  \r\n");
            sql.append(" , ObservationDefinitionInsurer.ObservationDefinitionId  \r\n");
            sql.append(" from ObservationDefinitionInsurer  \r\n");
            sql.append(" group by ObservationDefinitionInsurer.ViewId, ObservationDefinitionInsurer.ObservationDefinitionId  \r\n");
            sql.append(" , ObservationDefinitionInsurer.SortNo  \r\n");
            sql.append(" ) As tblF  \r\n");
            sql.append(" on tblF.ViewId= tblD.ViewId  \r\n");
            sql.append(" and tblF.ObservationDefinitionId=tblD.ObservationDefinitionId  \r\n");
            sql.append(" order by tblF.SortNo  \r\n");
            sql.append(" ) \r\n");
        }
        if(DataInputTypeCd==-1){
            sql.append(" union all \r\n");
        }
        if(DataInputTypeCd==3 || DataInputTypeCd==4 || DataInputTypeCd==5 || DataInputTypeCd==-1){
            //特定健診取得
            //ViewIDは１で固定
            sql.append(" ( \r\n");
            sql.append(" SELECT  \r\n");
            sql.append(" distinct  \r\n");
            sql.append(" ObservationEvent.ObservationEventId As ObservationEventId  \r\n");
            sql.append(" , ObservationDefinitionType.DataInputTypeCd As DataInputTypeCd  \r\n");
            sql.append(" , tblB.PHR_ID As PHR_ID  \r\n");
            sql.append(" , tblB.ExaminationDate As ExaminationDate  \r\n");
            sql.append(" , ObservationEvent.Year As Year  \r\n");
            sql.append(" , tblB.InsurerNo As InsurerNo  \r\n");
            sql.append(" , ObservationEvent.LaboratoryCd As LaboratoryCd  \r\n");
            sql.append(" , ObservationEvent.OrderNo As OrderNo  \r\n");
            sql.append(" , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
            sql.append(" , ObservationEvent.CreateDateTime As CreateDateTime  \r\n");
            sql.append(" , ObservationEvent.UpdateDateTime As UpdateDateTime  \r\n");
            sql.append(" , ObservationEvent.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg  \r\n");
            sql.append(" , tblB.ObservationDefinitionId  \r\n");
//            sql.append(" , Observation.Value  \r\n");
            sql.append(" , case  \r\n");
            sql.append(" when ObservationDefinitionEnumValue.EnumName is null  \r\n");
            sql.append("   then Observation.Value  \r\n");
            sql.append(" else ObservationDefinitionEnumValue.EnumName  \r\n");
            sql.append(" end As Value  \r\n");
            sql.append(" , Observation.OutRangeTypeCd  \r\n");
//            sql.append(" , ObservationDefinitionType.UnitValue  \r\n");
            sql.append(" , Observation.Unit As UnitValue  \r\n");
            sql.append(" , Observation.UpdateDateTime As UpdateDateTimeA  \r\n");
            sql.append(" , tblB.AlertLevelCd as AlertLevelCd  \r\n");
            sql.append(" , ObservationDefinition.DisplayName as DisplayName  \r\n");
            sql.append(" , ObservationDefinitionEnumValue.EnumName  \r\n");
            sql.append(" , null As NewExaminationDate  \r\n");
            sql.append(" , null As ReminderTypeCd  \r\n");
            sql.append(" FROM (  \r\n");
            sql.append("  SELECT  \r\n");
            sql.append(" ObservationEvent.PHR_ID  \r\n");
            sql.append(" , ObservationEvent.ExaminationDate  \r\n");
            sql.append(" , ObservationEvent.Year  \r\n");
            sql.append(" , ObservationEvent.InsurerNo  \r\n");
            sql.append(" , ObservationEvent.ObservationEventId  \r\n");
            sql.append(" , Observation.ObservationDefinitionId \r\n");
//            sql.append(" , ObservationAlert.AlertLevelCd \r\n");
            sql.append(" , MAX(ObservationAlert.AlertLevelCd) as AlertLevelCd \r\n");
            sql.append(" FROM  ObservationEvent  \r\n");
            sql.append("left join Observation  \r\n");
            sql.append(" on ObservationEvent.ObservationEventId=Observation.ObservationEventId  \r\n");
            sql.append(" inner join ObservationDefinitionType  \r\n");
            sql.append(" on ObservationDefinitionType.ObservationDefinitionId = Observation.ObservationDefinitionId  \r\n");
            sql.append(" and ObservationDefinitionType.InsurerNo = ObservationEvent.InsurerNo  \r\n");
            sql.append(" left join ObservationAlert  \r\n");
            sql.append(" on ObservationAlert.ObservationDefinitionId = Observation.ObservationDefinitionId  \r\n");
            sql.append(" and ObservationAlert.ObservationEventId = Observation.ObservationEventId  \r\n");
//            sql.append("  and ObservationDefinitionType.InsurerNo = ObservationEvent.InsurerNo \r\n");
            sql.append(" WHERE ObservationEvent.PHR_ID = ? \r\n");
            sql.append(" and ObservationEvent.ExaminationDate <= ? \r\n");
//            sql.append(" and ObservationEvent.DataInputTypeCd = 3  \r\n");
            if(DataInputTypeCd==-1){
                sql.append(" and ObservationEvent.DataInputTypeCd IN (3,4,5)  \r\n");
            } else {
                sql.append(" and ObservationEvent.DataInputTypeCd = 3  \r\n");
            }
            sql.append(" GROUP BY ObservationEvent.ObservationEventId  \r\n");
            sql.append(" , ObservationEvent.ExaminationDate  \r\n");
            sql.append(" , ObservationEvent.PHR_ID  \r\n");
            sql.append(" , ObservationEvent.Year  \r\n");
            sql.append(" , ObservationEvent.InsurerNo  \r\n");
            sql.append(" , Observation.ObservationDefinitionId  \r\n");

            // add start 2021/09/10
//            sql.append(" , ObservationAlert.AlertLevelCd   \r\n");
            // add end 2021/09/10

            sql.append("order by ObservationEvent.ExaminationDate Desc, ObservationEvent.ObservationEventId Desc  \r\n");
            sql.append(" ) as tblB  \r\n");
            sql.append(" inner join ObservationDefinition  \r\n");
            sql.append(" on ObservationDefinition.ObservationDefinitionId = tblB.ObservationDefinitionId  \r\n");
            sql.append(" left join ObservationEvent  \r\n");
            sql.append(" on ObservationEvent.PHR_ID = tblB.PHR_ID  \r\n");
            sql.append(" and ObservationEvent.ExaminationDate = tblB.ExaminationDate  \r\n");
            sql.append(" and ObservationEvent.Year = tblB.Year  \r\n");
            sql.append(" and ObservationEvent.ObservationEventId = tblB.ObservationEventId  \r\n");
//            sql.append(" and ObservationEvent.DataInputTypeCd = 3  \r\n");
            if(DataInputTypeCd==-1){
                sql.append(" and ObservationEvent.DataInputTypeCd IN (3,4,5)  \r\n");
            } else {
                sql.append(" and ObservationEvent.DataInputTypeCd = 3  \r\n");
            }
            sql.append(" left join Observation  \r\n");
            sql.append(" on ObservationEvent.ObservationEventId=Observation.ObservationEventId  \r\n");
            sql.append(" and tblB.ObservationDefinitionId=Observation.ObservationDefinitionId  \r\n");
            sql.append(" left join ObservationDefinitionEnumValue  \r\n");
//            sql.append(" on ObservationDefinitionEnumValue.ViewId= 1 \r\n");
//            sql.append(" and ObservationDefinitionEnumValue.ObservationDefinitionId=Observation.ObservationDefinitionId  \r\n");
            sql.append(" on ObservationDefinitionEnumValue.ObservationDefinitionId=Observation.ObservationDefinitionId  \r\n");
            sql.append(" and ObservationDefinitionEnumValue.EnumValue=Observation.Value  \r\n");
            sql.append(" inner join ObservationDefinitionType  \r\n");
            sql.append(" on ObservationDefinitionType.ObservationDefinitionId = tblB.ObservationDefinitionId  \r\n");
            sql.append("and ObservationDefinitionType.InsurerNo = tblB.InsurerNo  \r\n");
            sql.append(" and ObservationDefinitionType.DataInputTypeCd >= 3 \r\n");
            if(DataInputTypeCd==-1){
                sql.append(" and ObservationDefinitionType.DataInputTypeCd IN (3,4,5) \r\n");   //パラメータ
            }else{
                sql.append(" and ObservationDefinitionType.DataInputTypeCd IN (?) \r\n");   //パラメータ
            }

            sql.append(")\r\n");
        }
        if(DataInputTypeCd==-1){
            sql.append(" union all \r\n");
        }
        if(DataInputTypeCd==2 || DataInputTypeCd==-1){
            //家庭測定取得
            //ViewIDは2で固定
            sql.append(" (  \r\n");
            sql.append(" SELECT  \r\n");
            sql.append(" ObservationEvent.ObservationEventId As ObservationEventId  \r\n");
            sql.append(" , ObservationEvent.DataInputTypeCd As DataInputTypeCd  \r\n");
            sql.append(" , ObservationEvent.PHR_ID As PHR_ID  \r\n");
            sql.append(" , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
            sql.append(" , ObservationEvent.Year As Year  \r\n");
            sql.append(" , ObservationEvent.InsurerNo As InsurerNo  \r\n");
            sql.append(" , ObservationEvent.LaboratoryCd As LaboratoryCd  \r\n");
            sql.append(" , ObservationEvent.OrderNo As OrderNo  \r\n");
            sql.append(" , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
            sql.append(" , ObservationEvent.CreateDateTime As CreateDateTime  \r\n");
            sql.append(" , ObservationEvent.UpdateDateTime As UpdateDateTime  \r\n");
            sql.append(" , ObservationEvent.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg  \r\n");
            sql.append(" , Observation.ObservationDefinitionId  \r\n");
            sql.append(" , Observation.Value  \r\n");
            sql.append(" , Observation.OutRangeTypeCd  \r\n");
//            sql.append(" , ObservationDefinitionInsurer.UnitValue  \r\n");
            sql.append(" , Observation.Unit As UnitValue  \r\n");
            sql.append(" , Observation.UpdateDateTime As UpdateDateTimeA   \r\n");
            sql.append(" , ObservationAlert.AlertLevelCd as AlertLevelCd  \r\n");
            sql.append(" , ObservationDefinition.DisplayName as DisplayName  \r\n");
            sql.append(" , ObservationDefinitionEnumValue.EnumName  \r\n");
            sql.append(" , null As NewExaminationDate  \r\n");
            sql.append(" , null As ReminderTypeCd  \r\n");
            sql.append(" FROM  ObservationEvent  \r\n");
            sql.append(" inner join Observation  \r\n");
            sql.append("on ObservationEvent.ObservationEventId=Observation.ObservationEventId  \r\n");
            sql.append(" and ObservationEvent.DataInputTypeCd=2  \r\n");
            sql.append(" and ObservationEvent.PHR_ID = ? \r\n");
//            sql.append("and ObservationEvent.Year <= ? \r\n");
//            sql.append(" and ObservationEvent.ExaminationDate >= ? \r\n");
            sql.append(" and ObservationEvent.ExaminationDate <= ? \r\n");
            sql.append(" left join ObservationDefinitionInsurer  \r\n");
            sql.append(" on ObservationDefinitionInsurer.ViewId= 2  \r\n");
            sql.append(" and ObservationDefinitionInsurer.ObservationDefinitionId=Observation.ObservationDefinitionId   \r\n");
            sql.append(" inner join ObservationDefinition  \r\n");
            sql.append(" on ObservationDefinition.ObservationDefinitionId = Observation.ObservationDefinitionId  \r\n");
            sql.append(" left join ObservationDefinitionEnumValue  \r\n");
            sql.append(" on ObservationDefinitionEnumValue.ViewId= 2  \r\n");
            sql.append(" and ObservationDefinitionEnumValue.ObservationDefinitionId=Observation.ObservationDefinitionId   \r\n");
            sql.append(" and ObservationDefinitionEnumValue.EnumValue=Observation.Value  \r\n");
            sql.append(" left join ObservationAlert  \r\n");
            sql.append(" on ObservationAlert.ViewId = 2 \r\n");
            sql.append(" and ObservationAlert.ObservationDefinitionId=Observation.ObservationDefinitionId  \r\n");
            sql.append(" and ObservationAlert.ObservationEventId=Observation.ObservationEventId  \r\n");
            sql.append(" order by ObservationEvent.ExaminationDate Desc, ObservationDefinitionInsurer.SortNo  \r\n");
            sql.append(" )  \r\n");

        }
        if(DataInputTypeCd==-1){
            // add start 2021/09/10
            //            sql.append(" order by DataInputTypeCd, ExaminationDate Desc, ObservationEventId Desc, ObservationDefinitionId \r\n");
            sql.append(" order by DataInputTypeCd, ExaminationDate, ObservationEventId Desc, ObservationDefinitionId \r\n");
            // add end 2021/09/10
        }
        return sql.toString();
    }

    /**
     * <pre>検査結果情報登録処理</pre>
     * @param entity
     * @return
     * @throws Throwable 
     */
    public int InsertObservationEvent(ObservationEventEntity entity) throws Throwable{

        DataAccessObject dao = new DataAccessObject(connection);
        dao.beginTransaction();
        String sql = getInsertSql();

        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        Date date = new Date(entity.getExaminationDate().getTime());
        preparedStatement.setString(1,entity.getObservationEventId());   // 検査結果ID
        preparedStatement.setInt(2,entity.getDataInputTypeCd());     // データ入力種別
        preparedStatement.setString(3,entity.getPHR_ID());  // PHR_ID
        preparedStatement.setTimestamp(4,entity.getExaminationDate());  // 検査日
        preparedStatement.setInt(5,entity.getYear());   // 対象年度
        preparedStatement.setString(6,entity.getInsurerNo());     // 保険者No
        preparedStatement.setString(7,entity.getLaboratoryCd());  // 検査会社コード
        preparedStatement.setString(8, entity.getOrderNo());  // 検査オーダNO
        preparedStatement.setString(9, entity.getMedicalOrganizationCd());  // 医療機関コード

        // 登録処理
        int i =  preparedStatement.executeUpdate();
        preparedStatement.close();

        return i;
    }

    /**
     * PHR_ID、検査日にて自己測定結果を検索します。
     * @param phrId
     * @param startDate
     * @param endDate
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> selectPhrIdAndObservationId( String phrId, Date startDate, Date endDate ) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        int addCnt = 0;
        java.sql.Date sqlStartDate = null;
        java.sql.Date sqlEndDate = null;

        sql += "where ObservationEvent.DataInputTypeCd=2 and  ObservationEvent.PHR_ID=? ";

        if (startDate != null) {
            sql += " and ObservationEvent.ExaminationDate >= ?";
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
            sql += " and ObservationEvent.ExaminationDate <= ?";
            addCnt++;

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(endDate);
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.SECOND, 0);
            calEnd.set(Calendar.MILLISECOND, 0);
            sqlEndDate = new java.sql.Date(calEnd.getTimeInMillis());
        }

        sql += " order by ObservationEvent.ExaminationDate DESC";

        System.out.println(sql);

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phrId);
        if (sqlStartDate != null) {
            preparedStatement.setDate(2, sqlStartDate);
        }
        if (sqlEndDate != null) {
            if( addCnt == 1 ){
                preparedStatement.setDate(2, sqlEndDate);
            }else{
                preparedStatement.setDate(3, sqlEndDate);
            }
        }

        List<ObservationEventEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationEventEntity entity = ObservationEventEntity.setData(dataTable);
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return ret;
    }

    /**
     * PHR_ID、健診実施日にて特定健診を検索します。
     * @param phrId
     * @param endDate
     * @param startDate
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> findCheckup( String phrId, Date date, String insurerNo) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = this.getSelectedSql();
        int addCnt = 0;
        java.sql.Date sqlDate = null;

        sqlDate = new java.sql.Date(date.getTime());

        sql += " where ObservationEvent.InsurerNo = ?";
        sql += " and ObservationEvent.PHR_ID = ?";
        sql += " and ObservationEvent.ExaminationDate = ?";
        sql += " and ObservationEvent.DataInputTypeCd = 3";


        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        preparedStatement.setString(2, phrId);
        preparedStatement.setDate(3, sqlDate);

        List<ObservationEventEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationEventEntity entity = ObservationEventEntity.setData(dataTable);
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return ret;
    }


    /**
     * 基準日から検査結果の直近日を検索（未来[0]過去[1]条件指定）
     * @param phrid
     * @param basedate
     * @param searchType
     * @return 
     * @throws java.lang.Throwable 
     */
    public List<ObservationEventEntity> findByFutureDay(String phrid, Timestamp basedate,int searchType) throws Throwable
    {
        logger.debug("Start");
        List<ObservationEventEntity> list = new ArrayList<>();
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
//        sql.append(" SELECT \r\n");
//        if(searchType==0){
//            sql.append(" min(ObservationEvent.ExaminationDate) as ExaminationDate \r\n");
//        }else{
//            sql.append(" max(ObservationEvent.ExaminationDate) as ExaminationDate \r\n");
//        }
//        sql.append(" , ObservationEvent.DataInputTypeCd \r\n");
//        sql.append(" from ObservationEvent \r\n");
//        sql.append(" left outer join Observation  \r\n");
//        sql.append("   on ObservationEvent.ObservationEventId = Observation.ObservationEventId  \r\n");
//        sql.append(" where ObservationEvent.PHR_ID=? \r\n");
//        sql.append(" and ObservationEvent.DataInputTypeCd IN (1,3) \r\n");
//        if(searchType==0){
//            sql.append(" and ObservationEvent.ExaminationDate>? \r\n");
//        }else{
//            sql.append(" and ObservationEvent.ExaminationDate<? \r\n");
//        }
//        sql.append(" and ObservationEvent.DiseaseManagementTargetFlg = 1  \r\n");
//        sql.append(" and Observation.DiseaseManagementTargetFlg = 1  \r\n");
//        sql.append(" group by ObservationEvent.DataInputTypeCd \r\n");
//        sql.append(" order by ObservationEvent.DataInputTypeCd Asc \r\n");
        
        sql.append("(SELECT \r\n");
        if(searchType==0){
            sql.append("    min(ObservationEvent.ExaminationDate) as ExaminationDate \r\n");
        }else{
            sql.append("    max(ObservationEvent.ExaminationDate) as ExaminationDate \r\n");
        }
        sql.append("    , ObservationEvent.DataInputTypeCd  \r\n");
        sql.append("  from \r\n");
        sql.append("    ObservationEvent  \r\n");
        sql.append("    left outer join Observation  \r\n");
        sql.append("      on ObservationEvent.ObservationEventId = Observation.ObservationEventId  \r\n");
        sql.append("  where \r\n");
        sql.append("    ObservationEvent.PHR_ID = ? \r\n");
        sql.append("    and ObservationEvent.DataInputTypeCd = 1 \r\n");
        if(searchType==0){
            sql.append(" and ObservationEvent.ExaminationDate>? \r\n");
        }else{
            sql.append(" and ObservationEvent.ExaminationDate<? \r\n");
        }
        sql.append("    and ObservationEvent.DiseaseManagementTargetFlg = 1  \r\n");
        sql.append("    and Observation.DiseaseManagementTargetFlg = 1  \r\n");
        sql.append("  group by ObservationEvent.DataInputTypeCd)  \r\n");
        sql.append("union all (  \r\n");
        sql.append("  SELECT \r\n");
        if(searchType==0){
            sql.append("    min(ObservationEvent.ExaminationDate) as ExaminationDate \r\n");
        }else{
            sql.append("    max(ObservationEvent.ExaminationDate) as ExaminationDate \r\n");
        }
        sql.append("    , ObservationEvent.DataInputTypeCd  \r\n");
        sql.append("  from \r\n");
        sql.append("    ObservationEvent  \r\n");
        sql.append("    left outer join Observation  \r\n");
        sql.append("      on ObservationEvent.ObservationEventId = Observation.ObservationEventId  \r\n");
        sql.append("  where \r\n");
        sql.append("    ObservationEvent.PHR_ID = ? \r\n");
        sql.append("    and ObservationEvent.DataInputTypeCd = 3  \r\n");
        if(searchType==0){
            sql.append("    and ObservationEvent.ExaminationDate>? \r\n");
        }else{
            sql.append("    and ObservationEvent.ExaminationDate<? \r\n");
        }
        sql.append("  group by ObservationEvent.DataInputTypeCd)  \r\n");
        sql.append("order by DataInputTypeCd Asc \r\n");

        dao.setSql(sql.toString());
        dao.clearBindParam();
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            preparedStatement.setString(1, phrid);
            preparedStatement.setTimestamp(2, basedate);
            preparedStatement.setString(3, phrid);
            preparedStatement.setTimestamp(4, basedate);

            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return null;
                }

                while( dataTable.next() ) {
                    ObservationEventEntity tmpEntity = new ObservationEventEntity();
                    tmpEntity.setExaminationDate(AbstractEntity.getDateTime(dataTable, "ExaminationDate"));
                    tmpEntity.setDataInputTypeCd(AbstractEntity.getNullInt(dataTable, "DataInputTypeCd"));
                    list.add(tmpEntity);
                }

                dao.clearBindParam();
            }
        }
        logger.debug("End");
        return list;
    }

    /**
     * PHR_ID、医療機関コード、検査日にて検査登録期間ごとの検査日を検索します。
     * @param phrId
     * @param startDate
     * @param endDate
     * @param medicalOrganizationCd
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> selectObsetvationEventReminder(String phrId, Date startDate, Date endDate, String medicalOrganizationCd) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select  \r\n");
        sb.append("    ObservationEvent.ObservationEventId As ObservationEventId  \r\n");
        sb.append("    , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        //sb.append("    , ObservationDefinitionRange.ReminderTypeCd As ReminderTypeCd  \r\n");
        sb.append("    , ObservationEvent.CreateDateTime As CreateDateTime  \r\n");
        sb.append("    , ObservationEvent.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("from ObservationEvent \r\n");
        sb.append("inner join Observation \r\n");
        sb.append("on ObservationEvent.ObservationEventId = Observation.ObservationEventId \r\n");
        //sb.append("inner join ObservationDefinitionRange \r\n");
        //sb.append("on ObservationEvent.InsurerNo = ObservationDefinitionRange.InsurerNo \r\n");
        //sb.append("and ObservationEvent.Year = ObservationDefinitionRange.Year \r\n");
        //sb.append("and Observation.ObservationDefinitionId = ObservationDefinitionRange.ObservationDefinitionId \r\n");
        sb.append("inner join InsurerDiseaseType \r\n");
        sb.append("on Observation.ObservationDefinitionId = InsurerDiseaseType.ObservationDefinitionId \r\n");

        String  sql = sb.toString();
        java.sql.Date sqlStartDate = null;
        java.sql.Date sqlEndDate = null;

//        sql += " where ObservationEvent.DataInputTypeCd = 1 \r\n";
        sql += " where ObservationEvent.DataInputTypeCd in (1, 3) \r\n";
        sql += " and ObservationEvent.PHR_ID = ? \r\n";
        sql += " and ObservationEvent.MedicalOrganizationCd = ? \r\n";
//        sql += " and ObservationEvent.DiseaseManagementTargetFlg = 1 \r\n";

        if (startDate != null) {
            sql += " and ObservationEvent.ExaminationDate >= ? ";
            sqlStartDate = new java.sql.Date(startDate.getTime());
        }
        if (endDate != null) {
            sql += " and ObservationEvent.ExaminationDate <= ? ";
            sqlEndDate = new java.sql.Date(endDate.getTime());
        }

        //sql += " group by ObservationEvent.ExaminationDate, ObservationEvent.ObservationEventId, ObservationDefinitionRange.ReminderTypeCd";
        //sql += " order by ObservationEvent.ExaminationDate DESC, ObservationEvent.ObservationEventId, ObservationDefinitionRange.ReminderTypeCd";
        sql += " group by ObservationEvent.ExaminationDate, ObservationEvent.ObservationEventId";
        sql += " order by ObservationEvent.ExaminationDate DESC, ObservationEvent.ObservationEventId";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        int i = 3;
        preparedStatement.setString(1, phrId);
        preparedStatement.setString(2, medicalOrganizationCd);
        if (sqlStartDate != null) {
            preparedStatement.setDate(i, sqlStartDate);
            i+=1;
        }
        if (sqlEndDate != null) {
            preparedStatement.setDate(i, sqlEndDate);
        }

        List<ObservationEventEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationEventEntity entity = new ObservationEventEntity();
            entity.setObservationEventId(getString(dataTable, "ObservationEventId"));
            entity.setExaminationDate(getDateTime(dataTable, "ExaminationDate"));
            //entity.setReminderTypeCd(getInt(dataTable, "ReminderTypeCd"));
            entity.setCreateDateTime(getDateTime(dataTable, "CreateDateTime"));
            entity.setUpdateDateTime(getDateTime(dataTable, "UpdateDateTime"));

            if (logger.isDebugEnabled())
            {
                logger.debug("検査ID         　　 ：" + entity.getObservationEventId());
                logger.debug("検査日         　　 ：" + entity.getExaminationDate());
                //logger.debug("リマインダータイプCD：" + entity.getReminderTypeCd());
                logger.debug("作成日時      　　  ：" + entity.getCreateDateTime());
                logger.debug("最終更新日時    　　：" + entity.getUpdateDateTime());
            }

            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return ret;
    }

    /**
     * ObservationEventIdで検査項目結果の検索
     * @param observationdefinitionId
     * @return 検査項目結果一覧
     * @throws Throwable 
     */
    public ObservationEventEntity findLatestValue(String phrid , String observationdefinitionId,Timestamp date) throws Throwable
    {
        List<ObservationEntity> resList;
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sqlsb = new StringBuilder();
        sqlsb.append("select \r\n");
        sqlsb.append("    ObservationEvent.ObservationEventId As ObservationEventId \r\n");
        sqlsb.append("    , ObservationEvent.DataInputTypeCd As DataInputTypeCd \r\n");
        sqlsb.append("    , ObservationEvent.PHR_ID As PHR_ID \r\n");
        sqlsb.append("    , ObservationEvent.ExaminationDate As ExaminationDate \r\n");
        sqlsb.append("    , ObservationEvent.Year As Year \r\n");
        sqlsb.append("    , ObservationEvent.InsurerNo As InsurerNo \r\n");
        sqlsb.append("    , ObservationEvent.LaboratoryCd As LaboratoryCd \r\n");
        sqlsb.append("    , ObservationEvent.OrderNo As OrderNo \r\n");
        sqlsb.append("    , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd \r\n");
        sqlsb.append("    , ObservationEvent.CreateDateTime As CreateDateTime \r\n");
        sqlsb.append("    , ObservationEvent.UpdateDateTime As UpdateDateTime \r\n");
        sqlsb.append("    , Observation.ObservationDefinitionId As ObservationDefinitionId \r\n");
        sqlsb.append("    , Observation.Value As Value \r\n");
        sqlsb.append("from ObservationEvent \r\n");
        sqlsb.append("inner join Observation \r\n");
        sqlsb.append("  on Observation.ObservationEventId = ObservationEvent.ObservationEventId \r\n");

        String sql = sqlsb.toString();
        sql += " where ObservationEvent.PHR_ID = ? ";
        sql += "  and Observation.ObservationDefinitionId = ? ";
        sql += "  and ObservationEvent.ExaminationDate <= ?";
        sql += "  order by ObservationEvent.ExaminationDate DESC";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrid);
        preparedStatement.setString(2, observationdefinitionId);
        preparedStatement.setTimestamp(3, date);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        ObservationEventEntity entity = null;
        while (dataTable.next()) {
            entity = ObservationEventEntity.setData(dataTable);
            entity.setObservationDefinitionId(AbstractEntity.getString(dataTable, "ObservationDefinitionId"));
            entity.setObservationValue(AbstractEntity.getString(dataTable, "Value"));

            if(entity != null ) break;
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }

    /**
     * PHR_IDからEventList検査日を検索します。
     * @param phrId
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> getEventList(String phrId) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = this.getSelectedSql();
        int addCnt = 0;
        java.sql.Date sqlStartDate = null;
        java.sql.Date sqlEndDate = null;

        if (phrId != "" && phrId != null) {
            sql += " where ObservationEvent.PHR_ID = ?";
            //            sql += " and ObservationEvent.LaboratoryCd is null ";
            sql += " and( ObservationEvent.DataInputTypeCd = 1";
            sql += " or ObservationEvent.DataInputTypeCd = 2 )";
            addCnt++;
        }

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phrId);

        List<ObservationEventEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationEventEntity entity = ObservationEventEntity.setData(dataTable);
            ret.add(entity);
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return ret;
    }

    /**
     * PHR_IDからEventIDと健診日の一覧を検索します。
     * @param phrId
     * @return
     * @throws Throwable
     */
    public Map<String, String> getEventIdandDateList(String phrId, String insurerNo) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);

        StringBuilder sqlsb = new StringBuilder();
        sqlsb.append("select \r\n");
        sqlsb.append("    ObservationEvent.ObservationEventId As ObservationEventId \r\n");
        sqlsb.append("    , ObservationEvent.ExaminationDate As ExaminationDate \r\n");
        sqlsb.append("from ObservationEvent \r\n");
        sqlsb.append(" where ObservationEvent.PHR_ID = ? \r\n");
        sqlsb.append(" and ObservationEvent.InsurerNo = ? \r\n");
        sqlsb.append(" and ObservationEvent.DataInputTypeCd = 3 \r\n");       

        dao.setSql(sqlsb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phrId);
        preparedStatement.setString(2, insurerNo);

        Map<String, String> ret = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String strDate = "";
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            String oeventID = dataTable.getString("ObservationEventId");
            Date exdate = dataTable.getDate("ExaminationDate");
            strDate = sdf.format(exdate);
            ret.put(strDate, oeventID);
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return ret;
    }

    /**
     * 検査依頼IDにて検査結果情報を検索します。
     * @param key
     * @return
     * @throws Throwable
     */
    public ObservationEventEntity findByOrderNo(String key) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        //        String sql = getSelectedSql();
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationEvent.ObservationEventId As ObservationEventId  \r\n");
        sql.append("    , ObservationEvent.DataInputTypeCd As DataInputTypeCd  \r\n");
        sql.append("    , ObservationEvent.PHR_ID As PHR_ID  \r\n");
        sql.append("    , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        sql.append("    , ObservationEvent.Year As Year  \r\n");
        sql.append("    , ObservationEvent.InsurerNo As InsurerNo  \r\n");
        sql.append("    , ObservationEvent.LaboratoryCd As LaboratoryCd  \r\n");
        sql.append("    , ObservationEvent.OrderNo As OrderNo  \r\n");
        sql.append("    , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , ObservationEvent.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , ObservationEvent.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , ObservationEvent.DiseaseManagementTargetFlg As DiseaseManagementTargetFlg  \r\n");
        sql.append("from ObservationEvent \r\n");
        sql.append("where ObservationEvent.OrderNo = ? \r\n");
        //        sql += " where ObservationEvent.OrderNo = ?";

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key);

        ObservationEventEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationEventEntity.setData(dataTable);
        }
        dao.commitTransaction();
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationEventEntity entity) throws Throwable  
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationEvent \r\n");
        sql.append("(ObservationEventId, DataInputTypeCd, PHR_ID, ExaminationDate, Year, InsurerNo, LaboratoryCd, OrderNo, MedicalOrganizationCd, DiseaseManagementTargetFlg, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        if (entity.getObservationEventId() == null ) {
            preparedStatement.setNull(1, Types.VARCHAR );
        } else {
            preparedStatement.setString(1, entity.getObservationEventId());
        }
        preparedStatement.setInt(2, entity.getDataInputTypeCd());
        if (entity.getPHR_ID() == null ) {
            preparedStatement.setNull(3, Types.VARCHAR );
        } else {
            preparedStatement.setString(3, entity.getPHR_ID());
        }
        preparedStatement.setTimestamp(4, entity.getExaminationDate());
        preparedStatement.setInt(5, entity.getYear());
        if (entity.getInsurerNo() == null ) {
            preparedStatement.setNull(6, Types.VARCHAR );
        } else {
            preparedStatement.setString(6, entity.getInsurerNo());
        }
        if (entity.getLaboratoryCd() == null ) {
            preparedStatement.setNull(7, Types.VARCHAR );
        } else {
            preparedStatement.setString(7, entity.getLaboratoryCd());
        }
        if (entity.getOrderNo() == null ) {
            preparedStatement.setNull(8, Types.VARCHAR );
        } else {
            preparedStatement.setString(8, entity.getOrderNo());
        }
        if (entity.getMedicalOrganizationCd() == null ) {
            preparedStatement.setNull(9, Types.VARCHAR );
        } else {
            preparedStatement.setString(9, entity.getMedicalOrganizationCd());
        }
        preparedStatement.setBoolean(10, entity.isDiseaseManagementTargetFlg());

        int rowCount = preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();

        logger.debug("End");
        return rowCount;
    }

    /**
     * 同一の検査依頼に1つでも疾病管理項目を含んでいる場合は疾病管理対象フラグを更新する
     * @throws SQLException 
     * 
     */
    public void updateDiseaseManagementTargetFlg(String id) throws SQLException {
    	logger.debug("Start");
    	
    	DataAccessObject dao = new DataAccessObject(connection);
    	StringBuilder sql = new StringBuilder();
        sql.append("update ObservationEvent set \r\n");
        sql.append("DiseaseManagementTargetFlg = true, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where ObservationEventId = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
        dao.commitTransaction();
        dao.clearBindParam();
        preparedStatement.close();
    	
    	logger.debug("End");
    }


    /**
     * PHR_IDからEventIDの一覧を検索します。
     * @param phrId
     * @return
     */
    public List<String> findObservationEventId(String phrId) throws Throwable{
        {
            logger.debug("Start");

            DataAccessObject dao = new DataAccessObject(connection);
            StringBuilder sqlsb = new StringBuilder();
            sqlsb.append("  select \r\n");
            sqlsb.append("      table1.ObservationEventId  \r\n");
            sqlsb.append("      , table1.DataInputTypeCd  \r\n");
            sqlsb.append("    from \r\n");
            sqlsb.append("      (  \r\n");
            sqlsb.append("        select \r\n");
            sqlsb.append("          ObservationEvent.ObservationEventId as ObservationEventId \r\n");
            sqlsb.append("          , ObservationEvent.ExaminationDate as ExaminationDate  \r\n");
            sqlsb.append("          , ObservationEvent.DataInputTypeCd as DataInputTypeCd  \r\n");
            sqlsb.append("        from \r\n");
            sqlsb.append("          ObservationEvent  \r\n");
            sqlsb.append("          left outer join Observation  \r\n");
            sqlsb.append("            on ObservationEvent.ObservationEventId = Observation.ObservationEventId  \r\n");
            sqlsb.append("        where \r\n");
            sqlsb.append("          ObservationEvent.PHR_ID = ?  \r\n");
            sqlsb.append("          and ObservationEvent.DataInputTypeCd in (1, 2, 3)  \r\n");
            sqlsb.append("      ) as table1  \r\n");
            sqlsb.append("    group by \r\n");
            sqlsb.append("      table1.ObservationEventId \r\n");
            sqlsb.append("      , table1.ExaminationDate  \r\n");
            sqlsb.append("      , table1.DataInputTypeCd  \r\n");
            sqlsb.append("    order by \r\n");
            sqlsb.append("      table1.ExaminationDate desc \r\n");
            sqlsb.append("      , table1.ObservationEventId desc \r\n");

            dao.setSql(sqlsb.toString());

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            preparedStatement.setString(1, phrId);

            List<String> ret = new ArrayList<>();

            ResultSet dataTable = preparedStatement.executeQuery();
            if (dataTable == null)
            {
                return null;
            }

            while( dataTable.next() ) {
                ret.add(getString(dataTable, "ObservationEventId"));
            }

            dao.clearBindParam();
            dataTable.close();
            preparedStatement.close();
            
            logger.debug("End");
            return ret;
        }
    }

    /**
     * PHR_IDからObservationEventのリストを検索します。
     * @param phrId
     * @return
     */
    public List<ObservationEventEntity> findByPhrId(String phrId) throws Throwable{
        {
            logger.debug("Start");

            DataAccessObject dao = new DataAccessObject(connection);
            StringBuilder sqlsb = new StringBuilder();
            sqlsb.append("select * \r\n");
            sqlsb.append("from ObservationEvent \r\n");
            sqlsb.append("where ObservationEvent.PHR_ID = ? \r\n");
            sqlsb.append("order by ExaminationDate desc, ObservationEventId desc \r\n");

            dao.setSql(sqlsb.toString());

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            preparedStatement.setString(1, phrId);

            List<ObservationEventEntity> ret = new ArrayList<>();

            ResultSet dataTable = preparedStatement.executeQuery();
            if (dataTable == null)
            {
                return null;
            }

            while( dataTable.next() ) {
                ObservationEventEntity entity = ObservationEventEntity.setData(dataTable);
                ret.add(entity);
            }

            dao.clearBindParam();
            dataTable.close();
            preparedStatement.close();

            logger.trace("End");
            return ret;
        }
    }
    
    /**
     * PHR_IDからEventIDの一覧を検索します。
     * @param phrId
     * @return
     */
    public List<ObservationEventEntity> findObservationEventList(String phrId) throws Throwable{
        {
            logger.trace("Start");

            DataAccessObject dao = new DataAccessObject(connection);
            StringBuilder sqlsb = new StringBuilder();
            sqlsb.append("  select \r\n");
            sqlsb.append("      table1.ObservationEventId  \r\n");
            sqlsb.append("      , table1.DataInputTypeCd  \r\n");
            sqlsb.append("    from \r\n");
            sqlsb.append("      (  \r\n");
            sqlsb.append("        select \r\n");
            sqlsb.append("          ObservationEvent.ObservationEventId as ObservationEventId \r\n");
            sqlsb.append("          , ObservationEvent.ExaminationDate as ExaminationDate  \r\n");
            sqlsb.append("          , ObservationEvent.DataInputTypeCd as DataInputTypeCd  \r\n");
            sqlsb.append("        from \r\n");
            sqlsb.append("          ObservationEvent  \r\n");
            sqlsb.append("          left outer join Observation  \r\n");
            sqlsb.append("            on ObservationEvent.ObservationEventId = Observation.ObservationEventId  \r\n");
            sqlsb.append("        where \r\n");
            sqlsb.append("          ObservationEvent.PHR_ID = ?  \r\n");
//            sqlsb.append("          and ObservationEvent.DataInputTypeCd in (1, 2, 3)  \r\n");
            sqlsb.append("          and ObservationEvent.DataInputTypeCd in (1, 2, 3, 4, 5)  \r\n");
            sqlsb.append("      ) as table1  \r\n");
            sqlsb.append("    group by \r\n");
            sqlsb.append("      table1.ObservationEventId \r\n");
            sqlsb.append("      , table1.ExaminationDate  \r\n");
            sqlsb.append("      , table1.DataInputTypeCd  \r\n");
            sqlsb.append("    order by \r\n");
            sqlsb.append("      table1.ExaminationDate desc \r\n");
            sqlsb.append("      , table1.ObservationEventId desc \r\n");

            dao.setSql(sqlsb.toString());

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            preparedStatement.setString(1, phrId);

            List<ObservationEventEntity> ret = new ArrayList<>();

            ResultSet dataTable = preparedStatement.executeQuery();
            if (dataTable == null)
            {
                return null;
            }

            while( dataTable.next() ) {
                ObservationEventEntity entity = new ObservationEventEntity();
                entity.setObservationEventId(getString(dataTable, "ObservationEventId"));
                entity.setDataInputTypeCd(getInt(dataTable, "DataInputTypeCd"));
                ret.add(entity);
            }

            dao.clearBindParam();
            dataTable.close();
            preparedStatement.close();
            
            logger.debug("End");
            return ret;
        }
    }

    /**
     * PHR_IDとDataInputTyepCdの値でレコードを取得
     * @param phr_id
     * @param dataInputTypeCd
     * @return list
     * @throws Throwable 
     */
    public List<ObservationEventEntity> findByPhridAndDatainputtypecd(String phr_id, String dataInputTypeCd) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sqlsb = new StringBuilder();
        sqlsb.append("select * \r\n");
        sqlsb.append("from ObservationEvent \r\n");
        sqlsb.append("where ObservationEvent.PHR_ID = ? \r\n");
        sqlsb.append("and ObservationEvent.DataInputTypeCd = ? \r\n");

        dao.setSql(sqlsb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        preparedStatement.setString(2, dataInputTypeCd);

        List<ObservationEventEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationEventEntity entity = ObservationEventEntity.setData(dataTable);
            ret.add(entity);
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return ret;
    }

    /**
     * PHR_IDとExaminationDateの値でレコードを取得
     * @param phr_id
     * @param date
     * @return list
     * @throws Throwable 
     */
    public List<ObservationEventEntity> getObservationEventId(String phr_id, String date) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sqlsb = new StringBuilder();
        sqlsb.append("select * \r\n");
        sqlsb.append("from ObservationEvent \r\n");
        sqlsb.append("where ObservationEvent.PHR_ID = ? \r\n");
        sqlsb.append("and ObservationEvent.ExaminationDate = ? \r\n");

        dao.setSql(sqlsb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        preparedStatement.setString(2, date);

        List<ObservationEventEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationEventEntity entity = ObservationEventEntity.setData(dataTable);
            ret.add(entity);
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return ret;
    }

    /**
     * observationEventIdでレコードを削除
     * @param observationEventId
     * @return 
     * @throws Throwable 
     */
    public void deleteById(String observationEventId) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ObservationEvent \r\n");
        sql.append("where ObservationEvent.ObservationEventId = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, observationEventId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
    }

    /**
     * ユーザIDと検査NOで検査結果を検索します。
     * @param observationEventId
     * @return entity
     * @throws java.lang.Throwable 
     */
    public ObservationEventEntity findById(String observationEventId) throws Throwable
    {
        ObservationEventEntity entity = null;
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationEvent.ObservationEventId = ?";

        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationEventId);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationEventEntity.setData(dataTable);
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;

    }

    /**
     * PHR_IDにてObservationEventの有効なレコードのリストを取得
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<ObservationEventEntity> getObservationEventIdList(String phr_id) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationEvent.PHR_ID = ?";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        
        List<ObservationEventEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(ObservationEventEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDでObservationEventテーブルからレコードを削除します。
     * @param phr_id
     * @return rowCount
     * @throws Throwable
     */
    public int deleteObservationEventRecordByPhrId(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  ObservationEvent \r\n");
        sb.append("where ObservationEvent.PHR_ID = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phr_id);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
