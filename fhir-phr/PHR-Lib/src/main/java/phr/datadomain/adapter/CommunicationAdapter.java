/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション情報(送信)のデータオブジェクト
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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.CommunicationEntity;
import phr.utility.TypeUtility;

/**
 * コミュニケーション情報(送信)のデータオブジェクトです。
 */
public class CommunicationAdapter extends CommunicationAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public CommunicationAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * コミュニケーションIDを採番する
     * @return
     * @throws Throwable 
     */
    public static String numberingCommunicationId() throws Throwable {
        String dosageId = "000000000000000";
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            String sql = "update Seq_CommunicationId set CommunicationId=LAST_INSERT_ID(CommunicationId+1)";
            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            int rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
            
            String sql2 = "SELECT LAST_INSERT_ID() as SeqId";
            dao.setSql(sql2);
            dao.clearBindParam();
            
            PreparedStatement preparedStatement2 = dao.getPreparedStatement();
            ResultSet dataTable = preparedStatement2.executeQuery();
            if (dataTable == null) {
                return null;
            }

            while (dataTable.next()) {
                long id = AbstractEntity.getLong(dataTable, "SeqId");
                dosageId = String.format("%015d", id);
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement2.close();
            dao.commitTransaction();
            return dosageId;
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }
    /**
     * 送信日にてコミュニケーション情報(送信)を検索します。
     * @param insurerNo
     * @param startDate
     * @param endDate
     * @return
     * @throws Throwable
     */
    public List<CommunicationEntity> findByFromToDate(String insurerNo, Date startDate, Date endDate) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        int addCnt = 0;
        
        java.sql.Date sqlStartDate = null;
        java.sql.Date sqlEndDate = null;
        
        sql += " where Communication.CommunicationTypeCd = '1'";
        sql += " and Communication.SendInsurerNo = ?";
                
        if(startDate != null ){
            sql += " and Communication.CreateDateTime >= ?";
            addCnt++;
            
            sqlStartDate = new java.sql.Date(startDate.getTime());
        }
        if(endDate != null) 
        {
            sql += " and Communication.CreateDateTime <= ?";
            addCnt++;
            
            sqlEndDate = new java.sql.Date(endDate.getTime());
        }
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        if (sqlStartDate != null) {
            preparedStatement.setDate(2, sqlStartDate);
        }
        if (sqlEndDate != null) {
            if (addCnt == 1) {
                preparedStatement.setDate(2, sqlEndDate);
            } else if (addCnt == 2) {
                preparedStatement.setDate(3, sqlEndDate);
            }
            
        }
        List<CommunicationEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            CommunicationEntity entity = CommunicationEntity.setData(dataTable);
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;
    }
    
    /**
     * PHR_ID 送信日 未読/既読フラグ 保険者Noにてメッセージ一覧を検索します。
     * @param phrId
     * @param endDate
     * @param startDate
     * @param readFlg
     * @param insurerNo
     * @return
     * @throws Throwable
     */
    public List<CommunicationEntity> findByPkDateFlgInsurer(String phrId, Date startDate, Date endDate, Boolean readFlg, String insurerNo) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select  \r\n");
        sb.append("    Communication.CommunicationId As CommunicationId  \r\n");
        sb.append("    , Communication.CommunicationTypeCd As CommunicationTypeCd  \r\n");
        sb.append("    , Communication.SendPHRID As SendPHRID  \r\n");
        sb.append("    , Communication.SendInsurerNo As SendInsurerNo  \r\n");
        sb.append("    , Communication.SendAccountId As SendAccountId  \r\n");
        sb.append("    , Communication.SendMedicalOrganizationCd As SendMedicalOrganizationCd  \r\n");
        sb.append("    , Communication.SenderName As SenderName  \r\n");
        sb.append("    , Communication.Subject As Subject  \r\n");
        sb.append("    , Communication.BodyText As BodyText  \r\n");
        sb.append("    , Communication.CreateDateTime As CreateDateTime  \r\n");
        sb.append("    , Communication.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("    , CommunicationReceiver.ReadFlg As ReadFlg  \r\n");
        sb.append("from Communication, CommunicationReceiver \r\n");
        sb.append("where Communication.CommunicationId = CommunicationReceiver.CommunicationId \r\n");
        sb.append(" and COALESCE( Communication.SendAccountId, ' ' ) != 'PushService' ");
        
        String sql = sb.toString();
        
        sql += " and Communication.CommunicationTypeCd = 2";
        
        if (readFlg == false) {
            sql += " and CommunicationReceiver.InsurerNo = ?";
        } else {
            sql += " and (CommunicationReceiver.InsurerNo = ? or (Communication.SendInsurerNo = ? and CommunicationReceiver.Seq = 0))";
        }
        
        int addCnt = 0;
        
        if (phrId != "" && phrId != null) {
            sql += " and Communication.SendPHRID = ?";
            addCnt++;
        }
        if (startDate != null) {
            sql += " and Communication.CreateDateTime >= ?";
            addCnt++;
        }
        if (endDate != null) {
            sql += " and Communication.CreateDateTime <= ?";
            addCnt++;
        }
        if (readFlg == false) {
            sql += " and CommunicationReceiver.ReadFlg = ?";
            addCnt++;
        }
        sql += " order by Communication.CreateDateTime Desc ";

        dao.setSql(sql);
        
        java.sql.Date sqlStartDate = null; 
        java.sql.Date sqlEndDate = null;
        
        if (startDate != null) {
            sqlStartDate = new java.sql.Date(startDate.getTime());
        }
        if (endDate != null) {
            sqlEndDate = new java.sql.Date(endDate.getTime());
        }

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        if (readFlg == false) {
            if (phrId != "" && phrId != null) {
                preparedStatement.setString(2, phrId);
                if (startDate != null) {
                    preparedStatement.setDate(3, sqlStartDate);
                    if (endDate != null) {
                        preparedStatement.setDate(4, sqlEndDate);
                    }
                } else {
                    if (endDate != null) {
                        preparedStatement.setDate(3, sqlEndDate);
                    }
                }
            } else {
                if (startDate != null) {
                    preparedStatement.setDate(2, sqlStartDate);
                    if (endDate != null) {
                        preparedStatement.setDate(3, sqlEndDate);
                    }
                } else {
                    if (endDate != null) {
                        preparedStatement.setDate(2, sqlEndDate);
                    }
                }
            }
            switch (addCnt) {
                case 1:
                    preparedStatement.setBoolean(2, readFlg);
                    break;
                case 2:
                    preparedStatement.setBoolean(3, readFlg);
                    break;
                case 3:
                    preparedStatement.setBoolean(4, readFlg);
                    break;
                case 4:
                    preparedStatement.setBoolean(5, readFlg);
                    break;
                default:
                    break;
            }
        } else {
            preparedStatement.setString(2, insurerNo);
        
            if (phrId != "" && phrId != null) {
                preparedStatement.setString(3, phrId);
                if (startDate != null) {
                    preparedStatement.setDate(4, sqlStartDate);
                    if (endDate != null) {
                        preparedStatement.setDate(5, sqlEndDate);
                    }
                } else {
                    if (endDate != null) {
                        preparedStatement.setDate(4, sqlEndDate);
                    }
                }
            } else {
                if (startDate != null) {
                    preparedStatement.setDate(3, sqlStartDate);
                    if (endDate != null) {
                        preparedStatement.setDate(4, sqlEndDate);
                    }
                } else {
                    if (endDate != null) {
                        preparedStatement.setDate(3, sqlEndDate);
                    }
                }
            }
        }
        
        sql += " order by Communication.CreateDateTime DESC, Communication.CommunicationId DESC";
        
        List<CommunicationEntity> ret = new ArrayList<>();

        logger.debug( preparedStatement );
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            CommunicationEntity entity = CommunicationEntity.setData(dataTable);
            entity.setReadFlg(AbstractEntity.getBoolean(dataTable, "ReadFlg"));
            if(entity.getSendInsurerNo() != null && entity.getSendInsurerNo().trim().length() > 0){
                entity.setSenderStatus(1);
            }else if(entity.getSendMedicalOrganizationCd() != null && entity.getSendMedicalOrganizationCd().trim().length() > 0){
                entity.setSenderStatus(2);
            }else{
                entity.setSenderStatus(3);
            }
            
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;
    }
    
    /**
     * CommunicationIdをキーにおしらせを削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deleteByCommunicationId(CommunicationEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  Communication \r\n");
        sb.append("where CommunicationId = ? \r\n");
        String sql= sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        if (entity.getCommunicationId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getCommunicationId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }
    
    /**
     * 医療機関宛てのメッセージ一覧を検索します。
     * @param patientId
     * @param endDate
     * @param startDate
     * @param readFlg
     * @return
     * @throws Throwable
     */
    public List<CommunicationEntity> searchMedicalMeg(String patientId, String medicalCd, Date startDate, Date endDate, Boolean readFlg) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select  \r\n");
        sb.append("    Communication.CommunicationId As CommunicationId  \r\n");
        sb.append("    , Communication.CommunicationTypeCd As CommunicationTypeCd  \r\n");
        sb.append("    , Communication.SendPHRID As SendPHRID  \r\n");
        sb.append("    , Communication.SendInsurerNo As SendInsurerNo  \r\n");
        sb.append("    , Communication.SendAccountId As SendAccountId  \r\n");
        sb.append("    , Communication.SendMedicalOrganizationCd As SendMedicalOrganizationCd  \r\n");
        sb.append("    , Communication.SenderName As SenderName  \r\n");
        sb.append("    , Communication.Subject As Subject  \r\n");
        sb.append("    , Communication.BodyText As BodyText  \r\n");
        sb.append("    , Communication.CreateDateTime As CreateDateTime  \r\n");
        sb.append("    , Communication.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("    , CommunicationReceiver.ReadFlg As ReadFlg  \r\n");
        sb.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sb.append("from Communication  \r\n");
        sb.append("inner join CommunicationReceiver on \r\n");
        sb.append("	CommunicationReceiver.CommunicationId = Communication.CommunicationId  \r\n");
        sb.append("inner join MedicalOrganizationPatient on  \r\n");
        
        String sql = sb.toString();
        
        sql += "MedicalOrganizationPatient.MedicalOrganizationCd = ?";
                
        sql += "where Communication.CommunicationTypeCd = ?";
        sql += "and ((Communication.SendMedicalOrganizationCd = ?";
        sql += "and CommunicationReceiver.Seq = 0)";
        sql += "or CommunicationReceiver.MedicalOrganizationCd = ? )";
        sql += " and (MedicalOrganizationPatient.PHR_ID = Communication.SendPHRID";
        sql += " or MedicalOrganizationPatient.PHR_ID = CommunicationReceiver.PHR_ID)";

        if (patientId != "" && patientId != null) {
            sql += " and MedicalOrganizationPatient.PatientId = ?";
            sql += " and Communication.SendPHRID > 0 ";
        }
        if (startDate != null) {
            sql += " and Communication.CreateDateTime >= ?";
        }
        if (endDate != null) {
            sql += " and Communication.CreateDateTime <= ?";
        }
        if (readFlg == false) {
            sql += " and CommunicationReceiver.MedicalOrganizationCd = ?";
            sql += " and CommunicationReceiver.ReadFlg = ?";
        }
        sql += " ORDER BY Communication.UpdateDateTime Desc";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, medicalCd);
        preparedStatement.setString(2, "2");
        preparedStatement.setString(3, medicalCd);
        preparedStatement.setString(4, medicalCd);
        int count = 5;
        if (patientId != "" && patientId != null) {
            preparedStatement.setString(count, patientId);
            count++;
        }
        if (startDate != null) {
            java.sql.Date start = new java.sql.Date(startDate.getTime());           
            preparedStatement.setDate(count, start);
            count++;
        }
        if (endDate != null) {
            java.sql.Date end = new java.sql.Date(endDate.getTime());           
            preparedStatement.setDate(count, end);
            count++;
        }
        if (readFlg == false) {
            preparedStatement.setString(count, medicalCd);
            count++;
            preparedStatement.setString(count, "0");
            count++;
        }
        
        List<CommunicationEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            CommunicationEntity entity = CommunicationEntity.setData(dataTable);
            entity.setReadFlg(AbstractEntity.getBoolean(dataTable, "ReadFlg"));
            entity.setPatientId(AbstractEntity.getString(dataTable, "PatientId"));
             
            if(entity.getSendInsurerNo() != null && entity.getSendInsurerNo().trim().length() > 0){
                entity.setSenderStatus(1);
            }else if(entity.getSendMedicalOrganizationCd() != null && entity.getSendMedicalOrganizationCd().trim().length() > 0){
                entity.setSenderStatus(2);
            }else{
                entity.setSenderStatus(3);
            }
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;
    }
    
    /**
     * PHRIDからおしらせ及びメッセージを検索します。（複数件直近情報）
     * @param phrid
     * @param sendOrganizationCd
     * @param basedate 基準日
     * @param targetFlg 検索ターゲット
     * @return
     * @throws Throwable
     */
    public List<CommunicationEntity> findByPhridOrganization(String phrid,String sendOrganizationCd,Timestamp basedate, int targetFlg) throws Throwable 
    {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT \r\n");
        sql.append(" Communication.CommunicationId \r\n");
        sql.append(" , Communication.CommunicationTypeCd \r\n");
        sql.append(" , Communication.SendPHRID \r\n");
        sql.append(" , Communication.SendInsurerNo \r\n");
        sql.append(" , Communication.SendAccountId \r\n");
        sql.append(" , Communication.SendMedicalOrganizationCd \r\n");
        sql.append(" , Communication.SenderName \r\n");
        sql.append(" , Communication.Subject \r\n");
        sql.append(" , Communication.BodyText \r\n");
        sql.append(" , Communication.UpdateDateTime \r\n");
        sql.append(" , Communication.CreateDateTime \r\n");
        sql.append(" , CommunicationReceiver.Seq \r\n");
        sql.append(" , CommunicationReceiver.ReadFlg \r\n");
        sql.append(" , Insurer.InsurerName \r\n");
        sql.append(" , MedicalOrganization.MedicalOrganizationName \r\n");
        sql.append(" FROM Communication \r\n");
        sql.append(" inner join CommunicationReceiver \r\n");
        sql.append(" on Communication.CommunicationId = CommunicationReceiver.CommunicationId \r\n");
        sql.append(" left join Insurer \r\n");
        sql.append(" on Communication.SendInsurerNo = Insurer.InsurerNo \r\n");
        sql.append(" left join MedicalOrganization \r\n");
        sql.append(" on Communication.SendMedicalOrganizationCd = MedicalOrganization.MedicalOrganizationCd \r\n");
        sql.append(" WHERE ((Communication.SendPHRID = ? \r\n");
        if(targetFlg==CommunicationEntity.TargetFlgEnum.INSURER.getIntValue()){
            sql.append(" and CommunicationReceiver.InsurerNo = ?) \r\n");
            sql.append(" or (Communication.SendInsurerNo = ? \r\n");
            sql.append(" and CommunicationReceiver.PHR_ID = ?)) \r\n");
        }else if(targetFlg==CommunicationEntity.TargetFlgEnum.MEDICAL.getIntValue()){
            sql.append(" and CommunicationReceiver.MedicalOrganizationCd = ?) \r\n");
            sql.append(" or (Communication.SendMedicalOrganizationCd = ? \r\n");
            sql.append(" and CommunicationReceiver.PHR_ID = ?)) \r\n");
        }else{
            sql.append(" or CommunicationReceiver.PHR_ID = ?)) \r\n");
        }
        sql.append(" and Communication.CommunicationTypeCd IN (1,2) \r\n");
        sql.append(" and Communication.UpdateDateTime >= ? \r\n");
        sql.append(" and Communication.UpdateDateTime <= ? \r\n");
        sql.append(" ORDER BY Communication.UpdateDateTime Desc, CommunicationReceiver.Seq \r\n");

        dao.setSql(sql.toString());
        dao.clearBindParam();
        List<CommunicationEntity> entities;
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            //パラメータの日付情報作成
            Date getDate = new Date(basedate.getTime());
            Calendar cal_date = Calendar.getInstance();
            cal_date.setTime(getDate);
            cal_date.add(Calendar.YEAR, -1);    //１年前を取得開始日とする
            Timestamp getTimeStamp = new Timestamp(cal_date.getTimeInMillis());

            preparedStatement.setString(1, phrid);
            if(targetFlg==CommunicationEntity.TargetFlgEnum.INSURER.getIntValue()
               || targetFlg==CommunicationEntity.TargetFlgEnum.MEDICAL.getIntValue()){
                preparedStatement.setString(2, sendOrganizationCd);
                preparedStatement.setString(3, sendOrganizationCd);
                preparedStatement.setString(4, phrid);
                preparedStatement.setTimestamp(5, getTimeStamp);
                preparedStatement.setTimestamp(6, basedate);
            }else{
                preparedStatement.setString(2, phrid);
                preparedStatement.setTimestamp(3, getTimeStamp);
                preparedStatement.setTimestamp(4, basedate);
            }
            entities = new ArrayList<>();
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return null;
                }   while(dataTable.next()){
                    CommunicationEntity entity = CommunicationEntity.setData(dataTable);
                    entity.setSeqNo(AbstractEntity.getNullInt(dataTable, "Seq"));
                    entity.setReadFlg(AbstractEntity.getBoolean(dataTable, "ReadFlg"));
                    String getName;
                    if(targetFlg==CommunicationEntity.TargetFlgEnum.INSURER.getIntValue()){
                        getName = AbstractEntity.getString(dataTable, "InsurerName");
                    }else if(targetFlg==CommunicationEntity.TargetFlgEnum.MEDICAL.getIntValue()){
                        getName = AbstractEntity.getString(dataTable, "MedicalOrganizationName");
                    }else{
                        getName = AbstractEntity.getString(dataTable, "InsurerName");
                        if(TypeUtility.isNullOrEmpty(getName)){
                            getName = AbstractEntity.getString(dataTable, "MedicalOrganizationName");
                        }
                        if(TypeUtility.isNullOrEmpty(getName)){
                            getName = "";
                        }
                    }
                    entity.setSendOrganizationName(getName);
                    entities.add(entity);
                }
            }
            dao.clearBindParam();
        }
        
        logger.trace("End");
        return entities;
    }
    
    /**
     * 医療機関宛てのメッセージ一覧を検索します。
     * @param patientId
     * @param endDate
     * @param startDate
     * @param readFlg
     * @return
     * @throws Throwable
     */
    public CommunicationEntity findmedicalCom(String medicalCd,String communicationId) throws Throwable{
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select  \r\n");
        sb.append("    Communication.CommunicationId As CommunicationId  \r\n");
        sb.append("    , Communication.CommunicationTypeCd As CommunicationTypeCd  \r\n");
        sb.append("    , Communication.SendPHRID As SendPHRID  \r\n");
        sb.append("    , Communication.SendInsurerNo As SendInsurerNo  \r\n");
        sb.append("    , Communication.SendAccountId As SendAccountId  \r\n");
        sb.append("    , Communication.SendMedicalOrganizationCd As SendMedicalOrganizationCd  \r\n");
        sb.append("    , Communication.SenderName As SenderName  \r\n");
        sb.append("    , Communication.Subject As Subject  \r\n");
        sb.append("    , Communication.BodyText As BodyText  \r\n");
        sb.append("    , Communication.CreateDateTime As CreateDateTime  \r\n");
        sb.append("    , Communication.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sb.append("from Communication  \r\n");
        sb.append("inner join MedicalOrganizationPatient on  \r\n");
        
        String sql = sb.toString();
        
        sql += "MedicalOrganizationPatient.MedicalOrganizationCd = ?";
        sql += "where Communication.CommunicationId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, medicalCd);
        preparedStatement.setString(2, communicationId);
        
        CommunicationEntity ret = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            CommunicationEntity entity = CommunicationEntity.setData(dataTable);
            entity.setPatientId(AbstractEntity.getString(dataTable, "PatientId"));
             
            if(entity.getSendInsurerNo() != null && entity.getSendInsurerNo().trim().length() > 0){
                entity.setSenderStatus(1);
            }else if(entity.getSendMedicalOrganizationCd() != null && entity.getSendMedicalOrganizationCd().trim().length() > 0){
                entity.setSenderStatus(2);
            }else{
                entity.setSenderStatus(3);
            }
            ret = entity;
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;

    }
    
    /**
     * コミュニケーションID,Seqからおしらせ及びメッセージを検索する
     * @param commuid
     * @param Seq
     * @return
     * @throws Throwable
     */
    public List<CommunicationEntity> findByCommuidAndSeq(String commuid,int Seq) throws Throwable 
    {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT \r\n");
        sql.append(" Communication.CommunicationId \r\n");
        sql.append(" , Communication.CommunicationTypeCd \r\n");
        sql.append(" , Communication.SendPHRID \r\n");
        sql.append(" , Communication.SendInsurerNo \r\n");
        sql.append(" , Communication.SendAccountId \r\n");
        sql.append(" , Communication.SendMedicalOrganizationCd \r\n");
        sql.append(" , Communication.SenderName \r\n");
        sql.append(" , Communication.Subject \r\n");
        sql.append(" , Communication.BodyText \r\n");
        sql.append(" , Communication.UpdateDateTime \r\n");
        sql.append(" , Communication.CreateDateTime \r\n");
        sql.append(" , CommunicationReceiver.Seq \r\n");
        sql.append(" , CommunicationReceiver.ReadFlg \r\n");
        sql.append(" , CommunicationReceiver.PHR_ID \r\n");
        sql.append(" , CommunicationReceiver.InsurerNo \r\n");
        sql.append(" , CommunicationReceiver.MedicalOrganizationCd \r\n");
        sql.append(" , CommunicationReceiver.UpdateDateTime As RecUpdateDateTime \r\n");
        sql.append(" , Insurer.InsurerName \r\n");
        sql.append(" , MedicalOrganization.MedicalOrganizationName \r\n");
        sql.append(" FROM Communication \r\n");
        sql.append(" inner join CommunicationReceiver \r\n");
        sql.append(" on Communication.CommunicationId = CommunicationReceiver.CommunicationId \r\n");
        sql.append(" left join Insurer \r\n");
        sql.append(" on Communication.SendInsurerNo = Insurer.InsurerNo \r\n");
        sql.append(" left join MedicalOrganization \r\n");
        sql.append(" on Communication.SendMedicalOrganizationCd = MedicalOrganization.MedicalOrganizationCd \r\n");
        sql.append(" WHERE Communication.CommunicationId = ? \r\n");
        sql.append(" and CommunicationReceiver.Seq = ? \r\n");

        dao.setSql(sql.toString());
        dao.clearBindParam();
        List<CommunicationEntity> entities;
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            preparedStatement.setString(1, commuid);
            preparedStatement.setInt(2, Seq);

            entities = new ArrayList<>();
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return null;
                }
                
                while(dataTable.next()){
                    CommunicationEntity entity = CommunicationEntity.setData(dataTable);
                    entity.setSeqNo(AbstractEntity.getNullInt(dataTable, "Seq"));
                    entity.setReadFlg(AbstractEntity.getBoolean(dataTable, "ReadFlg"));
                    String getName;
                    getName = AbstractEntity.getString(dataTable, "InsurerName");
                    if(TypeUtility.isNullOrEmpty(getName)){
//                        getName = AbstractEntity.getString(dataTable, "MedicalOrganizationName");
//                        if(TypeUtility.isNullOrEmpty(getName)){
                            getName = "";
//                        }
                    }
                    entity.setSendOrganizationName(getName);
                    entity.setPhrid(AbstractEntity.getString(dataTable, "PHR_ID"));
                    entity.setInsurerNo(AbstractEntity.getString(dataTable, "InsurerNo"));
                    entity.setMedicalOrganizationCd(AbstractEntity.getString(dataTable, "MedicalOrganizationCd"));
                    entity.setRecUpdateDateTime(AbstractEntity.getDateTime(dataTable, "RecUpdateDateTime"));
                    entities.add(entity);
                }
            }
            dao.clearBindParam();
        }
        
        logger.trace("End");
        return entities;
    }
    
    /**
     * メッセージの詳細情報を検索します。
     * @param communicationId
     * @return
     * @throws Throwable
     */
    public CommunicationEntity findMessageDetail(String communicationId) throws Throwable{
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select  \r\n");
        sb.append("    Communication.CommunicationId As CommunicationId  \r\n");
        sb.append("    , Communication.CommunicationTypeCd As CommunicationTypeCd  \r\n");
        sb.append("    , Communication.SendPHRID As SendPHRID  \r\n");
        sb.append("    , Communication.SendInsurerNo As SendInsurerNo  \r\n");
        sb.append("    , Communication.SendAccountId As SendAccountId  \r\n");
        sb.append("    , Communication.SendMedicalOrganizationCd As SendMedicalOrganizationCd  \r\n");
        sb.append("    , Communication.SenderName As SenderName  \r\n");
        sb.append("    , Communication.Subject As Subject  \r\n");
        sb.append("    , Communication.BodyText As BodyText  \r\n");
        sb.append("    , Communication.CreateDateTime As CreateDateTime  \r\n");
        sb.append("    , Communication.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("    , CommunicationReceiver.PHR_ID As recPHR_ID  \r\n");
        sb.append("    , Patient.FamilyName  As recFamilyName  \r\n");
        sb.append("    , Patient.GivenName  As recGivenName  \r\n");
        sb.append("from Communication  \r\n");
        sb.append("left outer join CommunicationReceiver on  \r\n");
        sb.append("Communication.CommunicationId = CommunicationReceiver.CommunicationId \r\n");
        sb.append("and CommunicationReceiver.PHR_ID <> '' and CommunicationReceiver.PHR_ID is not null \r\n");
        sb.append("left outer join Patient on  \r\n");
        sb.append("CommunicationReceiver.PHR_ID = Patient.PHR_ID \r\n");
        
        String sql = sb.toString();
        
        sql += " where Communication.CommunicationId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, communicationId);
        
        CommunicationEntity ret = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            CommunicationEntity entity = CommunicationEntity.setData(dataTable);
            entity.setRecPhrId(getString(dataTable, "recPHR_ID"));
            entity.setRecPhrName(getString(dataTable, "recFamilyName") + getString(dataTable, "recGivenName"));
            ret = entity;
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;

    }

    /**
     * （患者View向け）PHRIDから受診＆送信済みの、おしらせ及びメッセージを検索します。
     * @param phrid
     * @param basedate 基準日
     * @return
     * @throws Throwable
     */
    public List<CommunicationEntity> findByPhridForPatient(String phrid, Timestamp basedate) throws Throwable 
    {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT \r\n");
        sql.append(" Communication.CommunicationId \r\n");
        sql.append(" , Communication.CommunicationTypeCd \r\n");
        sql.append(" , Communication.SendPHRID \r\n");
        sql.append(" , Communication.SendInsurerNo \r\n");
        sql.append(" , Communication.SendAccountId \r\n");
        sql.append(" , Communication.SendMedicalOrganizationCd \r\n");
        sql.append(" , Communication.SenderName \r\n");
        sql.append(" , Communication.Subject \r\n");
        sql.append(" , Communication.BodyText \r\n");
        sql.append(" , Communication.UpdateDateTime \r\n");
        sql.append(" , Communication.CreateDateTime \r\n");
        sql.append(" , CommunicationReceiver.Seq \r\n");
        sql.append(" , CommunicationReceiver.ReadFlg \r\n");
        sql.append(" , Insurer.InsurerName \r\n");
        sql.append(" , MedicalOrganization.MedicalOrganizationName \r\n");
        sql.append(" FROM Communication \r\n");
        sql.append(" inner join CommunicationReceiver \r\n");
        sql.append(" on Communication.CommunicationId = CommunicationReceiver.CommunicationId \r\n");
        sql.append(" left join Insurer \r\n");
        sql.append(" on Communication.SendInsurerNo = Insurer.InsurerNo \r\n");
        sql.append(" left join MedicalOrganization \r\n");
        sql.append(" on Communication.SendMedicalOrganizationCd = MedicalOrganization.MedicalOrganizationCd \r\n");
        sql.append(" WHERE (Communication.SendPHRID = ? \r\n");
        sql.append(" or CommunicationReceiver.PHR_ID = ?) \r\n");
        sql.append(" and Communication.UpdateDateTime >= ? \r\n");
        sql.append(" and Communication.UpdateDateTime <= ? \r\n");
        sql.append(" and COALESCE( Communication.SendMedicalOrganizationCd, '' ) ='' \r\n");
        sql.append(" and \r\n");
        sql.append(" ( \r\n");
        sql.append("    ( COALESCE( Communication.SendPHRID,'' ) = '' and COALESCE( Communication.SendInsurerNo,'' ) != '' and COALESCE( Communication.SendAccountId, '' ) != '' )  \r\n");
        sql.append("    or \r\n");
        sql.append("    ( COALESCE( Communication.SendPHRID,'' ) != '' and COALESCE( Communication.SendInsurerNo, '' ) = '' and COALESCE( Communication.SendAccountId, '' ) = '' )  \r\n");
        sql.append(" ) \r\n");
        sql.append(" ORDER BY Communication.UpdateDateTime Desc, CommunicationReceiver.Seq \r\n");

        dao.setSql(sql.toString());
        dao.clearBindParam();
        List<CommunicationEntity> entities;
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            //パラメータの日付情報作成
            Date getDate = new Date(basedate.getTime());
            Calendar cal_date = Calendar.getInstance();
            cal_date.setTime(getDate);
            cal_date.add(Calendar.YEAR, -1);    //１年前を取得開始日とする
            Timestamp getTimeStamp = new Timestamp(cal_date.getTimeInMillis());

            preparedStatement.setString(1, phrid);
            preparedStatement.setString(2, phrid);
            preparedStatement.setTimestamp(3, getTimeStamp);
            preparedStatement.setTimestamp(4, basedate);

            entities = new ArrayList<>();
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return null;
                }   while(dataTable.next()){
                    CommunicationEntity entity = CommunicationEntity.setData(dataTable);
                    entity.setSeqNo(AbstractEntity.getNullInt(dataTable, "Seq"));
                    entity.setReadFlg(AbstractEntity.getBoolean(dataTable, "ReadFlg"));
                    String getName;
                        getName = AbstractEntity.getString(dataTable, "InsurerName");
                        if(TypeUtility.isNullOrEmpty(getName)){
                            getName = AbstractEntity.getString(dataTable, "MedicalOrganizationName");
                        }
                        if(TypeUtility.isNullOrEmpty(getName)){
                            getName = "";
                        }

                    entity.setSendOrganizationName(getName);
                    entities.add(entity);
                }
                dataTable.close();
            }
            dao.clearBindParam();
            preparedStatement.close();
        }
        logger.trace("End");
        return entities;
    }

    /**
     * PHR_IDでCommunicationテーブルからレコードを削除します
     * @param phr_id
     * @return rowCount
     * @throws Throwable
     */
    public int deleteCommunicationRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  Communication \r\n");
        sb.append("where Communication.SendPHRID = ? \r\n");
        String sql= sb.toString();

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
