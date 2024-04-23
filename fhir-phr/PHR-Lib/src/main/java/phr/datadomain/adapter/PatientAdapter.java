/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者情報のデータオブジェクト
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
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getString;
import static phr.datadomain.AbstractEntity.getBoolean;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.PatientEntity;

/**
 * 患者情報のデータオブジェクトです。
 */
public class PatientAdapter extends PatientAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PatientAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

     /**
     * CommunicationIDからメッセージを受診したアカウント情報を検索します。
     * @param communicationId
     * @return
     * @throws Throwable
     */
    public PatientEntity findByComId(String communicationId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " inner join CommunicationReceiver on ";
        sql += "	CommunicationReceiver.PHR_ID = Patient.PHR_ID ";
        sql += " where CommunicationReceiver.CommunicationId = ? ";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, communicationId);

        PatientEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = PatientEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }

     /**
     * phrIDから保険者番号を含めて患者情報を取得する。
     * @param communicationId
     * @return
     * @throws Throwable
     */
    public PatientEntity findInsurePatient(String phrId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        String sqlorg = getSelectedSql();
        int idx = sqlorg.indexOf("from");
        String sql1 = sqlorg.substring(0, idx);        
        sql.append(sql1);
//        sql.append("select  \r\n");
//        sql.append("    Patient.PHR_ID As PHR_ID  \r\n");
//        sql.append("    , Patient.FamilyName As FamilyName  \r\n");
//        sql.append("    , Patient.GivenName As GivenName  \r\n");
//        sql.append("    , Patient.FamilyKana As FamilyKana  \r\n");
//        sql.append("    , Patient.GivenKana As GivenKana  \r\n");
//        sql.append("    , Patient.BirthDate As BirthDate  \r\n");
//        sql.append("    , Patient.SexCd As SexCd  \r\n");
//        sql.append("    , Patient.ZipCode As ZipCode  \r\n");
//        sql.append("    , Patient.PrefectureCd As PrefectureCd  \r\n");
//        sql.append("    , Patient.AddressLine As AddressLine  \r\n");
//        sql.append("    , Patient.BuildingName As BuildingName  \r\n");
//        sql.append("    , Patient.TelNo As TelNo  \r\n");
//        sql.append("    , Patient.OtherContactNo As OtherContactNo  \r\n");
//        sql.append("    , Patient.EmailAddress As EmailAddress  \r\n");
//        sql.append("    , Patient.KyeId As KyeId  \r\n");
//        sql.append("    , Patient.DiseaseManagement As DiseaseManagement  \r\n");
//        sql.append("    , Patient.CreateDateTime As CreateDateTime  \r\n");
//        sql.append("    , Patient.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , InsurerPatient.InsurerNo As InsurerNo  \r\n");
        sql.append("from Patient \r\n");

        String sqls = sql.toString();
        sqls += " inner join InsurerPatient on ";
        sqls += "	InsurerPatient.PHR_ID = Patient.PHR_ID ";
        sqls += " where Patient.PHR_ID = ? ";
        
        dao.setSql(sqls);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);

        PatientEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = PatientEntity.setData(dataTable);
            entity.setInsurerNo(AbstractEntity.getString(dataTable, "InsurerNo"));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
    
    /**
     * MedicalOrganizationCdとphrIdより患者情報を検索します。
     * @param phrId
     * @param medicalOrganizationCd
     * @return
     * @throws Throwable
     */
    public PatientEntity selectMedicalPatient(String patientId, String medicalOrganizationCd) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        String sqlorg = getSelectedSql();
        int idx = sqlorg.indexOf("from");
        String sql1 = sqlorg.substring(0, idx);        
        sb.append(sql1);
//        sb.append("select  \r\n");
//        sb.append("    Patient.PHR_ID As PHR_ID  \r\n");
//        sb.append("    , Patient.FamilyName As FamilyName  \r\n");
//        sb.append("    , Patient.GivenName As GivenName  \r\n");
//        sb.append("    , Patient.FamilyKana As FamilyKana  \r\n");
//        sb.append("    , Patient.GivenKana As GivenKana  \r\n");
//        sb.append("    , Patient.BirthDate As BirthDate  \r\n");
//        sb.append("    , Patient.SexCd As SexCd  \r\n");
//        sb.append("    , Patient.ZipCode As ZipCode  \r\n");
//        sb.append("    , Patient.PrefectureCd As PrefectureCd  \r\n");
//        sb.append("    , Patient.AddressLine As AddressLine  \r\n");
//        sb.append("    , Patient.BuildingName As BuildingName  \r\n");
//        sb.append("    , Patient.TelNo As TelNo  \r\n");
//        sb.append("    , Patient.OtherContactNo As OtherContactNo  \r\n");
//        sb.append("    , Patient.EmailAddress As EmailAddress  \r\n");
//        sb.append("    , Patient.KyeId As KyeId  \r\n");
//        sb.append("    , Patient.DiseaseManagement As DiseaseManagement  \r\n");
//        sb.append("    , Patient.CreateDateTime As CreateDateTime  \r\n");
//        sb.append("    , Patient.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("    , MedicalOrganizationPatient.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sb.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sb.append("from Patient \r\n");
        sb.append("inner join MedicalOrganizationPatient \r\n");
        sb.append("on Patient.PHR_ID = MedicalOrganizationPatient.PHR_ID \r\n");
        String sql = sb.toString();
        sql += " where MedicalOrganizationPatient.PatientId = ? ";
        sql += " and MedicalOrganizationPatient.MedicalOrganizationCd = ? ";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, patientId);
        preparedStatement.setString(2, medicalOrganizationCd);
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        
        PatientEntity entity = new PatientEntity();
        while( dataTable.next() ) {
            entity = PatientEntity.setData(dataTable);
            entity.setMedicalOrganizationCd(getString(dataTable, "MedicalOrganizationCd"));
            entity.setPatientId(getString(dataTable, "PatientId"));
        }
        
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
    
    /**
     * MedicalOrganizationCdと入力条件より患者情報を検索します。
     * @param patientId
     * @param familyKana
     * @param givenKana
     * @param birthDate
     * @param sexCd
     * @param medicalOrganizationCd
     * @return
     * @throws Throwable
     */
    public List<PatientEntity> selectMedicalPatientByInput(String patientId, String familyKana, String givenKana, String birthDate, String sexCd, String medicalOrganizationCd) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        String sqlorg = getSelectedSql();
        int idx = sqlorg.indexOf("from");
        String sql1 = sqlorg.substring(0, idx);        
        sb.append(sql1);
//        sb.append("select  \r\n");
//        sb.append("    Patient.PHR_ID As PHR_ID  \r\n");
//        sb.append("    , Patient.FamilyName As FamilyName  \r\n");
//        sb.append("    , Patient.GivenName As GivenName  \r\n");
//        sb.append("    , Patient.FamilyKana As FamilyKana  \r\n");
//        sb.append("    , Patient.GivenKana As GivenKana  \r\n");
//        sb.append("    , Patient.BirthDate As BirthDate  \r\n");
//        sb.append("    , Patient.SexCd As SexCd  \r\n");
//        sb.append("    , Patient.ZipCode As ZipCode  \r\n");
//        sb.append("    , Patient.PrefectureCd As PrefectureCd  \r\n");
//        sb.append("    , Patient.AddressLine As AddressLine  \r\n");
//        sb.append("    , Patient.BuildingName As BuildingName  \r\n");
//        sb.append("    , Patient.TelNo As TelNo  \r\n");
//        sb.append("    , Patient.OtherContactNo As OtherContactNo  \r\n");
//        sb.append("    , Patient.EmailAddress As EmailAddress  \r\n");
//        sb.append("    , Patient.KyeId As KyeId  \r\n");
//        sb.append("    , Patient.DiseaseManagement As DiseaseManagement  \r\n");
//        sb.append("    , Patient.CreateDateTime As CreateDateTime  \r\n");
//        sb.append("    , Patient.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("    , MedicalOrganizationPatient.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sb.append("    , MedicalOrganizationPatient.PatientId As PatientId  \r\n");
        sb.append("from Patient \r\n");
        sb.append("inner join MedicalOrganizationPatient \r\n");
        sb.append("on Patient.PHR_ID = MedicalOrganizationPatient.PHR_ID \r\n");
        String sql = sb.toString();
        
        sql += " where MedicalOrganizationPatient.MedicalOrganizationCd = ? ";
        if (!patientId.isEmpty()) {
            sql += " and MedicalOrganizationPatient.PatientId = ? ";
        }
        if (!familyKana.isEmpty()) {
            sql += " and Patient.FamilyKana like ? ";
        }
        if (!givenKana.isEmpty()) {
            sql += " and Patient.GivenKana like ? ";
        }
        if (!birthDate.isEmpty()) {
            sql += " and Patient.BirthDate = ? ";
        }
        if (sexCd != null) {
            sql += " and Patient.SexCd = ? ";
        }
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        int i = 2;
        
        preparedStatement.setString(1, medicalOrganizationCd);
        if (!patientId.isEmpty()) {
            preparedStatement.setString(i, patientId);
            i+=1;
        }
        if (!familyKana.isEmpty()) {
            preparedStatement.setString(i, "%%"+familyKana+"%%");
            i+=1;
        }
        if (!givenKana.isEmpty()) {
            preparedStatement.setString(i, "%%"+givenKana+"%%");
            i+=1;
        }
        if (!birthDate.isEmpty()) {
            preparedStatement.setString(i, birthDate);
            i+=1;
        }
        if (sexCd != null) {
            preparedStatement.setString(i, sexCd);
        }

        List<PatientEntity> entityList = new ArrayList<PatientEntity>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            PatientEntity entity = PatientEntity.setData(dataTable);
            entity.setMedicalOrganizationCd(getString(dataTable, "MedicalOrganizationCd"));
            entity.setPatientId(getString(dataTable, "PatientId"));
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }

     /**
     * phrIDリストから患者情報を取得し、トークンマップにして返却する。
     * @param phrIdList
     * @return
     * @throws Throwable
     */
    public Map findPatientTokenMap(List<String> phrIdList) throws Throwable 
    {
        logger.trace("Start");
        Map tokenMap = new LinkedHashMap();
        if(phrIdList == null || phrIdList.isEmpty()) return tokenMap;

        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    Patient.PHR_ID As PHR_ID  \r\n");
        sql.append("    , Patient.tokenId As tokenId  \r\n");
        sql.append("from Patient \r\n");

        String sqls = sql.toString();
        sqls += " where Patient.tokenId is not null ";
        if (phrIdList.size() == 1) {
            sqls += " and Patient.PHR_ID = ? ";
        } else {
            sqls += " and Patient.PHR_ID in (?";
            for(int i = 1; i < phrIdList.size(); i++) {
                sqls += ", ?";
            }
            sqls += ")";
        }
        
        dao.setSql(sqls);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        if (phrIdList.size() == 1) {
            preparedStatement.setString(1, phrIdList.get(0));
        } else {
            int x = 1;
            for(String str : phrIdList) {
                preparedStatement.setString(x++, str);
            }
        }

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while(dataTable.next()) {
            tokenMap.put(getString(dataTable, "PHR_ID"), getString(dataTable, "tokenId"));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return tokenMap;
    }
    
     /**
     * 対象患者のトークンを更新する。
     * @param phrid
     * @param newToken
     * @return
     * @throws Throwable
     */
    public int updatePatientToken(String phrid, String newToken) throws Throwable 
    {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("update Patient set \r\n");
        sb.append("  tokenId = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sb.append("  where PHR_ID = ? \r\n");
        String sql= sb.toString();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        if (newToken == null ) {
            preparedStatement.setNull(1, Types.VARCHAR );
        } else {
            preparedStatement.setString(1, newToken);
        }
        preparedStatement.setString(2, phrid);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();

        logger.trace("End");
        return rowCount;
    }
    
     /**
     * phrIDリストから患者リストを取得する。
     * @param phrIdList
     * @return
     * @throws Throwable
     */
    public  List<PatientEntity> findPatientListByIdList(List<String> phrIdList) throws Throwable 
    {
        logger.trace("Start");
        List<PatientEntity> entityList = new ArrayList<PatientEntity>();
        
        if(phrIdList == null || phrIdList.isEmpty()) return entityList;

        DataAccessObject dao = new DataAccessObject(connection);
        
        StringBuilder sql = new StringBuilder();
        sql.append(getSelectedSql());
        sql.append(" Where Patient.PHR_ID in (? ");
        for(int i = 1; i < phrIdList.size(); i++) {
                sql.append(", ?");
        }
        sql.append(")");
        dao.setSql(sql.toString());
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        for(int i = 0; i < phrIdList.size(); i++) {
            preparedStatement.setString(i+1, phrIdList.get(i));
        }
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            PatientEntity entity = PatientEntity.setData(dataTable);
            entityList.add(entity);           
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }    
    
     /**
     * 条件から患者リストを取得する。
     * @param phrIdList
     * @return
     * @throws Throwable
     */
    public  List<PatientEntity> findPatientListByIdOrNames(String insurerNo, String phrId, String familyName, String givenName, String familyKana, String givenKana) throws Throwable 
    {
        logger.trace("Start");
        
//        if(insurerNo == null || insurerNo.isEmpty()){
//            return null;
//        }

        DataAccessObject dao = new DataAccessObject(connection);
        
        StringBuilder sql = new StringBuilder();
//        sql.append(getSelectedSql());
        sql.append("select  \r\n");
        sql.append("    Patient.PHR_ID As PHR_ID  \r\n");
        sql.append("    , Patient.FamilyName As FamilyName  \r\n");
        sql.append("    , Patient.GivenName As GivenName  \r\n");
        sql.append("    , Patient.FamilyKana As FamilyKana  \r\n");
        sql.append("    , Patient.GivenKana As GivenKana  \r\n");
        sql.append("    , Patient.BirthDate As BirthDate  \r\n");
        sql.append("    , Patient.SexCd As SexCd  \r\n");
        sql.append("    , Patient.ZipCode As ZipCode  \r\n");
        sql.append("    , Patient.PrefectureCd As PrefectureCd  \r\n");
        sql.append("    , Patient.AddressLine As AddressLine  \r\n");
        sql.append("    , Patient.BuildingName As BuildingName  \r\n");
        sql.append("    , Patient.TelNo As TelNo  \r\n");
        sql.append("    , Patient.OtherContactNo As OtherContactNo  \r\n");
        sql.append("    , Patient.EmailAddress As EmailAddress  \r\n");
        sql.append("    , Patient.KyeId As KyeId  \r\n");
        sql.append("    , Patient.tokenId As tokenId  \r\n");
        sql.append("    , Patient.DiseaseManagement As DiseaseManagement  \r\n");
        sql.append("    , Patient.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , Patient.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , MedicalOrganizationPatient.AgreesToShare As AgreesToShare  \r\n");
        sql.append("    , patient.DynamicConsentFlg As DynamicConsentFlg  \r\n");
        sql.append("from Patient \r\n");
        //sql.append("WHERE Patient.PHR_Id IN (SELECT InsurerPatient.PHR_ID FROM InsurerPatient Where InsurerPatient.InsurerNo = ?)");
//        sql.append("INNER JOIN InsurerPatient ON Patient.PHR_Id = InsurerPatient.PHR_ID AND InsurerPatient.InsurerNo = ?");
        sql.append("INNER JOIN MedicalOrganizationPatient ON Patient.PHR_Id = MedicalOrganizationPatient.PHR_ID AND MedicalOrganizationPatient.MedicalOrganizationCd = ?");
        
        boolean whereFlg = false;
        if (phrId != null && !phrId.isEmpty()) {
            sql.append(" WHERE Patient.PHR_Id = ? ");
            whereFlg= true;
        }
        if (familyName != null && !familyName.isEmpty()) {
            if(whereFlg){
                sql.append(" or Patient.FamilyName like ? ");
            }else{
                sql.append(" WHERE Patient.FamilyName like ? ");            
                whereFlg= true;
            }
        }
        if (givenName != null && !givenName.isEmpty()){
            if(whereFlg){
                sql.append(" or Patient.GivenName like ? ");            
            }else{
                sql.append(" WHERE Patient.GivenName like ? ");            
                whereFlg= true;
            }
        }        
        if (familyKana != null && !familyKana.isEmpty()) {
            if(whereFlg){
                sql.append(" or Patient.FamilyKana like ? ");
            }else{
                sql.append(" WHERE Patient.FamilyKana like ? ");            
                whereFlg= true;
            }
        }
        if (givenKana != null && !givenKana.isEmpty()) {
            if(whereFlg){
                sql.append(" or Patient.GivenKana like ? ");
            }else{
                sql.append(" WHERE Patient.GivenKana like ? ");            
                whereFlg= true;
            }
        }
        
        dao.setSql(sql.toString());
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, insurerNo);
//        preparedStatement.setString(2, insurerNo);
        if(whereFlg){
            int i = 2;
            if (phrId != null && !phrId.isEmpty()) {
                preparedStatement.setString(i, phrId);
                i+=1;
            }
            if (familyName != null && !familyName.isEmpty()) {
                preparedStatement.setString(i, "%%"+familyName+"%%");
                i+=1;
            }
            if (givenName != null && !givenName.isEmpty()) {
                preparedStatement.setString(i, "%%"+givenName+"%%");
                i+=1;
            }
            if (familyKana != null && !familyKana.isEmpty()) {
                preparedStatement.setString(i, "%%"+familyKana+"%%");
                i+=1;
            }
            if (givenKana != null && !givenKana.isEmpty()) {
                preparedStatement.setString(i, "%%"+givenKana+"%%");
                i+=1;
            }
        }
       
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        List<PatientEntity> entityList = new ArrayList<PatientEntity>();
        while( dataTable.next() ) {
            PatientEntity entity = PatientEntity.setData(dataTable);
            entity.setAgreesToShare(getBoolean(dataTable, "AgreesToShare"));
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }    

    /**
     * PHR_IDで患者情報の情報を削除します。
     * @param phr_id
     * @return
     * @throws Throwable
     */
    public int deleteByPhrId(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += "delete from Patient";
        sql += " where Patient.PHR_ID = ?";
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

    /**
     * 該当のPHR_IDのダイナミックコンセント利用停止を行います。
     * @param phr_id
     * @return rowCount
     * @throws Throwable
     */
    public int updateByPhrId(String phr_id) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += "update Patient set ";
        sql += " Patient.DynamicConsentFlg = false ";
        sql += " where Patient.PHR_ID = ?";
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

//    /**
//     * 指定された論理ID（ユーザ論理ID）に一致するユーザ情報を取得
//     * 
//     * @param patientLogicalIds
//     * @return
//     * @throws Throwable
//     */
//    public List<PatientEntity> getUserInfoList(List<String> patientPhrIdList) throws Throwable {
//        logger.debug("Start");
//
//        DataAccessObject dao = new DataAccessObject(connection);
//        String selectedSql = getSelectedSql();
//        List<Object> params = new ArrayList<>();
//        int types[] = new int[patientPhrIdList.size()];
//        StringBuilder sql = new StringBuilder();
//        sql.append("select  \r\n");
//        sql.append("    Patient.PHR_ID As PHR_ID  \\r\\n\"  \r\n");
//        sql.append("from Patient \r\n");
//        sql.append("where    \r\n");
//        if (patientPhrIdList.size() == 1) {
//            sql.append("  UserInfo.UserLogicalId = ?   \r\n");
//            params.add(patientPhrIdList.get(0));
//            types[0] = Types.VARCHAR;
//        } else {
//            sql.append("  UserInfo.UserLogicalId in (?   \r\n");
//            params.add(patientPhrIdList.get(0));
//            types[0] = Types.VARCHAR;
//            for(int i = 1; i < patientPhrIdList.size(); i++) {
//                sql.append("  , ?  \r\n");
//                params.add(patientPhrIdList.get(i));
//                types[i] = Types.VARCHAR;
//            }
//            sql.append("  )  \r\n");
//        }
//
//        dao.setSql(sql.toString());
//        dao.clearBindParam();
//        PreparedStatement preparedStatement = dao.getPreparedStatement();
//
//        List<PatientEntity> entityList = new ArrayList<PatientEntity>();
//        for (String phrId : patientPhrIdList) {
//            preparedStatement.setString(1, phrId);
//           
//            ResultSet dataTable = preparedStatement.executeQuery();
//            if (dataTable == null)
//            {
//                return null;
//            }
//            
//            while( dataTable.next() ) {
//                PatientEntity entity = PatientEntity.setData(dataTable);
//                entity.setAgreesToShare(getBoolean(dataTable, "AgreesToShare"));
//                entityList.add(entity);
//            }
//            dao.clearBindParam();
//            dataTable.close();
//            preparedStatement.close();
//            logger.trace("End");
//        }
//        return entityList;
//    }
    
//    /**
//    * phrIDリストから患者情報を取得し、トークンマップにして返却する。
//    * @param phrIdList
//    * @return
//    * @throws Throwable
//    */
//    public List<PatientEntity> getUserInfoList(List<String> phrIdList) throws Throwable {
//       logger.trace("Start");
//       List<PatientEntity> entityList = new ArrayList<PatientEntity>();
//       if(phrIdList == null || phrIdList.isEmpty()) return entityList;
//
//       DataAccessObject dao = new DataAccessObject(connection);
//       StringBuilder sql = new StringBuilder();
//       sql.append("select  \r\n");
//       sql.append("    Patient.PHR_ID As PHR_ID  \r\n");
//       sql.append("    , Patient.tokenId As tokenId  \r\n");
//       sql.append("from Patient \r\n");
//
//       String sqls = sql.toString();
//       sqls += " where Patient.tokenId is not null ";
//       if (phrIdList.size() == 1) {
//           sqls += " and Patient.PHR_ID = ? ";
//       } else {
//           sqls += " and Patient.PHR_ID in (?";
//           for(int i = 1; i < phrIdList.size(); i++) {
//               sqls += ", ?";
//           }
//           sqls += ")";
//       }
//       
//       dao.setSql(sqls);
//
//       dao.clearBindParam();
//       PreparedStatement preparedStatement = dao.getPreparedStatement();
//       
//       if (phrIdList.size() == 1) {
//           preparedStatement.setString(1, phrIdList.get(0));
//       } else {
//           int x = 1;
//           for(String str : phrIdList) {
//               preparedStatement.setString(x++, str);
//           }
//       }
//
//       ResultSet dataTable = preparedStatement.executeQuery();
//       if (dataTable == null)
//       {
//           return null;
//       }
//
//       while(dataTable.next()) {
//           entityList.put(getString(dataTable, "PHR_ID"), getString(dataTable, "tokenId"));
//       }
//       dao.clearBindParam();
//       dataTable.close();
//       preparedStatement.close();
//       logger.trace("End");
//       return entityList;
//   }
    
}
