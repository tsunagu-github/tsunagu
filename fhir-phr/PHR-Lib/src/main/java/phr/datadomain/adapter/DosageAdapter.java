/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：調剤情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.DosageDoctorEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.DosageMedicalOrganizationEntity;
import phr.datadomain.entity.DosageMedicineEntity;
import phr.datadomain.entity.DosageRecipeEntity;
import phr.datadomain.entity.DosageRemarkEntity;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;
import phr.datadomain.entity.PharmaceutistEntity;
import phr.datadomain.entity.UtilizationConsentEntity;
import phr.utility.TypeUtility;

/**
 * 調剤情報のデータオブジェクトです。
 */
public class DosageAdapter extends DosageAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * PHRIDから直近年度のおくすり情報を検索します。
     * @param phrid
     * @param basedate
     * @return 
     * @throws java.lang.Throwable 
     */
    public List<DosageEntity> findByIdPhrid(String phrid,Date basedate) throws Throwable
    {
        logger.trace("Start");

        List<DosageEntity> entity = new ArrayList<>();
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append(" select \r\n");
        sql.append(" Dosage.DosageId As DosageId \r\n");
        sql.append(" , Dosage.Seq As Seq \r\n");
        sql.append(" , Dosage.DosageDate As DosageDate \r\n");
        sql.append(" , Dosage.PHR_ID As PHR_ID \r\n");
        sql.append(" , Dosage.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append(" , pharmacy.MedicalOrganizationName As Pharmacy \r\n");
        sql.append(" , medicalorganization.MedicalOrganizationName As MedicalOrganizationName \r\n");
        sql.append(" from Dosage \r\n");
        sql.append(" left join DosageMedicalOrganization As pharmacy\r\n");
        sql.append(" on Dosage.DosageId=pharmacy.DosageId \r\n");
        sql.append(" and Dosage.Seq=pharmacy.Seq \r\n");
        sql.append(" and pharmacy.DosageTypeCd=1 \r\n");
        sql.append(" left join DosageMedicalOrganization As medicalorganization\r\n");
        sql.append(" on Dosage.DosageId=medicalorganization.DosageId \r\n");
        sql.append(" and Dosage.Seq=medicalorganization.Seq \r\n");
        sql.append(" and medicalorganization.DosageTypeCd=2 \r\n");       
        sql.append(" where Dosage.PHR_ID = ? \r\n");
        sql.append(" and Dosage.DosageDate >= ? \r\n");
        sql.append(" and Dosage.DosageDate <= ? \r\n");
        sql.append(" order by Dosage.DosageDate Desc, Dosage.DosageId \r\n");

        //sql.append(" , Parmaceutist.Pharmacy As Pharmacy \r\n");
        //sql.append(" left join Parmaceutist \r\n");
        //sql.append(" on Dosage.DosageId=Parmaceutist.DosageId \r\n");
        //sql.append(" and Dosage.Seq=Parmaceutist.Seq \r\n");
        
        dao.setSql(sql.toString());
        dao.clearBindParam();
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            //パラメータの日付情報作成
            Calendar cal_date = Calendar.getInstance();
            cal_date.setTime(basedate);
            cal_date.add(Calendar.YEAR, -1);    //１年前を取得開始日とする
//            int iNewYear=cal_date.get(Calendar.YEAR);//年
//            if(cal_date.get(Calendar.MONTH) <= 3){
//                iNewYear = iNewYear - 1;
//            }
//            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//            java.util.Date date = df.parse(String.valueOf(iNewYear)+"/04/01");

            preparedStatement.setString(1, phrid);
            preparedStatement.setDate(2, new Date(cal_date.getTimeInMillis()));
            preparedStatement.setDate(3, basedate);
            
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return null;
                }
                
                while( dataTable.next() ) {
                    DosageEntity tmpEntity=DosageEntity.setData(dataTable);
                    String tmpStr=AbstractEntity.getString(dataTable, "Pharmacy");
                    if(TypeUtility.isNullOrEmpty(tmpStr)){
                    }else {
                        tmpEntity.setPharmacy(tmpStr);
                    }              
                    entity.add(tmpEntity);
                }
            }
        }
        dao.clearBindParam();
        
        logger.trace("End");
        return entity;
        
    }
 
    /**
     * DosageIdの値にて調剤情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
//    public int deleteByDosageId(String dosageId,int seq) throws Throwable 
    public int deleteByDosageId(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sqlorg = getDeleteSql();
        int idx = sqlorg.indexOf("AND");
        String sql = sqlorg.substring(0, idx);
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);
//        preparedStatement.setInt(2, seq);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }    
    /**
     * DosageIdの値にて調剤情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deleteByDosageId(String dosageId,int seq) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);
        preparedStatement.setInt(2, seq);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }
    
    /**
     * DosageId,Seq,PHRIDからおくすりの詳細リストを検索します。
     * @param dosageId
     * @param seq
     * @param phrid
     * @return 
     * @throws java.lang.Throwable 
     */
    public List<DosageEntity> findByDosageIdSeqPhrid(String dosageId,String seq,String phrid) throws Throwable
    {
        logger.trace("Start");

        List<DosageEntity> entity = new ArrayList<>();
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append(" select \r\n");
        sql.append(" Dosage.DosageId As DosageId \r\n");
        sql.append(" , Dosage.Seq As Seq \r\n");
        sql.append(" , Dosage.DosageDate As DosageDate \r\n");
        sql.append(" , Dosage.PHR_ID As PHR_ID \r\n");
        sql.append(" , Pharma.MedicalOrganizationName As Pharmacy \r\n");
        sql.append(" , PharmaDoc.DoctorName As PharmaceutistName \r\n");
        sql.append(" , DosageMedicalOrganization.MedicalOrganizationCd As MedicalOrganizationCd \r\n");
        sql.append(" , DosageMedicalOrganization.MedicalOrganizationName As MedicalOrganizationName \r\n");
        sql.append(" , DosageMedicalOrganization.DosageTypeCd As DosageTypeCd \r\n");
        sql.append(" , DosageDoctor.DoctorName As DoctorName \r\n");
        sql.append(" , DosageRecipe.RecipeNo As RecipeNo \r\n");
        sql.append(" , DosageRecipe.UsageName As UsageName \r\n");
        sql.append(" , DosageRecipe.DosageQuantity As DosageQuantity \r\n");
        sql.append(" , DosageRecipe.DosageUnit As DosageUnit \r\n");
        sql.append(" , DosageRecipe.DosageForms As DosageForms \r\n");
        sql.append(" , DosegeMedicine.MedicineSeq As MedicineSeq \r\n");
        sql.append(" , DosegeMedicine.MedicineName \r\n");
        sql.append(" , DosegeMedicine.Amount \r\n");
        sql.append(" , DosegeMedicine.UnitName \r\n");
        sql.append(" , DosegeMedicine.MedicineCdType \r\n");
        sql.append(" , DosegeMedicine.MedicineCd \r\n");
        sql.append(" , DosegeMedicine.RecordCreatorType \r\n");
        sql.append(" , NonPrescriptionDrugs.MedicineName As NonPreMedicineName \r\n");
        sql.append(" , NonPrescriptionDrugs.RecordCreatorType As NonPreRecordCreatorType \r\n");
        sql.append(" , DosageRemark.RemarkText \r\n");
        sql.append(" from Dosage \r\n");
        sql.append(" left join DosageMedicalOrganization As Pharma \r\n");
        sql.append(" on Dosage.DosageId=Pharma.DosageId \r\n");
        sql.append(" and Dosage.Seq=Pharma.Seq \r\n");
        sql.append(" and Pharma.DosageTypeCd=1 \r\n");
        sql.append(" left join DosageDoctor As PharmaDoc \r\n");
        sql.append(" on Dosage.DosageId=PharmaDoc.DosageId \r\n");
        sql.append(" and Dosage.Seq=PharmaDoc.Seq \r\n");
        sql.append(" and Pharma.DosageTypeCd = PharmaDoc.DosageTypeCd \r\n");
        sql.append(" left join DosageRecipe \r\n");
        sql.append(" on Dosage.DosageId=DosageRecipe.DosageId \r\n");
        sql.append(" and Dosage.Seq=DosageRecipe.Seq \r\n");
        sql.append(" left join DosegeMedicine \r\n");
        sql.append(" on Dosage.DosageId=DosegeMedicine.DosageId \r\n");
        sql.append(" and Dosage.Seq=DosegeMedicine.Seq \r\n");
        sql.append(" and DosageRecipe.RecipeNo=DosegeMedicine.RecipeNo \r\n");
        sql.append(" left join DosageMedicalOrganization \r\n");
        sql.append(" on Dosage.DosageId = DosageMedicalOrganization.DosageId \r\n");
        sql.append(" and Dosage.Seq = DosageMedicalOrganization.Seq \r\n");
        sql.append(" and DosageMedicalOrganization.DosageTypeCd=2 \r\n");
        sql.append(" left join NonPrescriptionDrugs \r\n");
        sql.append(" on Dosage.DosageId = NonPrescriptionDrugs.DosageId \r\n");
        sql.append(" and Dosage.Seq = NonPrescriptionDrugs.Seq \r\n");
        sql.append(" left join DosageDoctor \r\n");
        sql.append(" on Dosage.DosageId = DosageDoctor.DosageId \r\n");
        sql.append(" and Dosage.Seq = DosageDoctor.Seq \r\n");
        sql.append(" and DosageMedicalOrganization.DosageTypeCd = DosageDoctor.DosageTypeCd \r\n");
        sql.append(" left join DosageRemark \r\n");
        sql.append(" on Dosage.DosageId = DosageRemark.DosageId \r\n");
        sql.append(" and Dosage.Seq = DosageRemark.Seq \r\n");
        sql.append(" where Dosage.PHR_ID = ? \r\n");
        sql.append(" and Dosage.DosageId = ? \r\n");
        sql.append(" and Dosage.Seq = ? \r\n");
        sql.append(" order by Dosage.DosageDate Desc, Dosage.DosageId \r\n");
        sql.append(" , Dosage.Seq, DosegeMedicine.RecipeNo, DosegeMedicine.MedicineSeq \r\n");
        
        dao.setSql(sql.toString());
        dao.clearBindParam();
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            preparedStatement.setString(1, phrid);
            preparedStatement.setString(2, dosageId);
            preparedStatement.setInt(3, Integer.parseInt(seq));
            
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return null;
                }
                
                while( dataTable.next() ) {
                    DosageEntity tmpEntity=DosageEntity.setData(dataTable);
                    
                    PharmaceutistEntity pharmaceutist=new PharmaceutistEntity();
                    pharmaceutist.setPharmacy(AbstractEntity.getString(dataTable, "Pharmacy"));
                    pharmaceutist.setPharmaceutistName(AbstractEntity.getString(dataTable, "PharmaceutistName"));
                    tmpEntity.setPharmaceutistEntity(pharmaceutist);
                    
                    DosageMedicalOrganizationEntity dosageMedicalOrganization=new DosageMedicalOrganizationEntity();
                    dosageMedicalOrganization.setMedicalOrganizationCd(AbstractEntity.getString(dataTable, "MedicalOrganizationCd"));
                    dosageMedicalOrganization.setMedicalOrganizationName(AbstractEntity.getString(dataTable, "MedicalOrganizationName"));
                    dosageMedicalOrganization.setDosageTypeCd(AbstractEntity.getString(dataTable, "DosageTypeCd"));
                    tmpEntity.setDosageMedicalOrganizationEntity(dosageMedicalOrganization);
                    
                    DosageDoctorEntity dosageDoctor=new DosageDoctorEntity();
                    dosageDoctor.setDoctorName(AbstractEntity.getString(dataTable, "DoctorName"));
                    tmpEntity.setDosageDoctorEntity(dosageDoctor);
                    
                    DosageRecipeEntity dosageRecipe=new DosageRecipeEntity();
                    if(AbstractEntity.getNullInt(dataTable, "RecipeNo")==null){
                        dosageRecipe.setRecipeNo(-1);
                    }else{
                        dosageRecipe.setRecipeNo(AbstractEntity.getNullInt(dataTable, "RecipeNo"));
                    }
                    dosageRecipe.setUsageName(AbstractEntity.getString(dataTable, "UsageName"));
                    if(AbstractEntity.getNullInt(dataTable, "DosageQuantity")==null){
                        dosageRecipe.setDosageQuantity(-1);
                    }else{
                        dosageRecipe.setDosageQuantity(AbstractEntity.getNullInt(dataTable, "DosageQuantity"));
                    }
                    dosageRecipe.setDosageUnit(AbstractEntity.getString(dataTable, "DosageUnit"));
                    dosageRecipe.setDosageForms(AbstractEntity.getString(dataTable, "DosageForms"));
                    tmpEntity.setDosageRecipeEntity(dosageRecipe);
                    
                    DosageMedicineEntity dosegemedicine=new DosageMedicineEntity();
                    if(AbstractEntity.getNullInt(dataTable, "MedicineSeq")==null){
                        dosegemedicine.setMedicineSeq(-1);
                    }else{
                        dosegemedicine.setMedicineSeq(AbstractEntity.getNullInt(dataTable, "MedicineSeq"));
                    }
                    dosegemedicine.setMedicineName(AbstractEntity.getString(dataTable, "MedicineName"));
                    dosegemedicine.setAmount(AbstractEntity.getString(dataTable, "Amount"));
                    dosegemedicine.setUnitName(AbstractEntity.getString(dataTable, "UnitName"));
                    dosegemedicine.setMedicineCdType(AbstractEntity.getString(dataTable, "MedicineCdType"));
                    dosegemedicine.setMedicineCd(AbstractEntity.getString(dataTable, "MedicineCd"));
                    dosegemedicine.setRecordCreatorType(AbstractEntity.getString(dataTable, "RecordCreatorType"));
                    tmpEntity.setDosageMedicineEntity(dosegemedicine);
                    
                    NonPrescriptionDrugsEntity nonPrescriptionDrugs=new NonPrescriptionDrugsEntity();
                    nonPrescriptionDrugs.setMedicineName(AbstractEntity.getString(dataTable, "NonPreMedicineName"));
                    nonPrescriptionDrugs.setRecordCreatorType(AbstractEntity.getString(dataTable, "NonPreRecordCreatorType"));
                    tmpEntity.setNonPrescriptionDrugsEntity(nonPrescriptionDrugs);

                    DosageRemarkEntity dosegeReamark=new DosageRemarkEntity();
                    dosegeReamark.setRemarkText(AbstractEntity.getString(dataTable, "RemarkText"));
                    tmpEntity.setDosageRemarkEntity(dosegeReamark);

                    entity.add(tmpEntity);
                }
            }
        }
        dao.clearBindParam();
        
        logger.trace("End");
        return entity;
        
    }
    
   /**
     * 対象のDosageIdが何件登録されているか、件数を返します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int getDosageIdCount(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "select count(Dosage.DosageId) as count from Dosage where Dosage.DosageId = ? \r\n";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);
        
        int count = 0;
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return -1;
                }
                while( dataTable.next() ) {
                    count = AbstractEntity.getInt(dataTable, "count");
                }
            }
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return count;
    }
    
   /**
     * 対象のPHRIDに対応するDosageIdのリストを返します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public List<String> getDosageIdList(String phrId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "select DosageId As DosageId from Dosage where Dosage.PHR_ID = ? \r\n";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);
        
        List<String> idList = new ArrayList();
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null)
                {
                    return idList;
                }
                while( dataTable.next() ) {
                    idList.add(AbstractEntity.getString(dataTable, "DosageId"));
                }
            }
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return idList;
    }

    /**
     * DosageIdにて調剤情報を検索します。
     * @param dosageId
     * @return
     * @throws Throwable
     */
    public List<DosageEntity> findByDosageId(String dosageId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where Dosage.DosageId = ?";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);

        List<DosageEntity> entList = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            DosageEntity entity = DosageEntity.setData(dataTable);
            entList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entList;
    }    

    /**
     * PHRIDからおくすり情報を検索します。
     * @param phrid
     * @return list
     * @throws java.lang.Throwable 
     */
    public List<DosageEntity> findByPhrid(String phrid) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "where Dosage.PHR_ID = ?";
        
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrid);
        
        List<DosageEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(DosageEntity.setData(dataTable));
        }
        
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDでDosageテーブルからレコードを削除します。
     * @param phr_id
     * @return rowCount
     * @throws Throwable
     */
    public int deleteDosageRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  Dosage \r\n");
        sb.append("where Dosage.PHR_ID = ? \r\n");
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
