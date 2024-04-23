/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：お薬情報　対象調剤の薬剤師および医師情報項目のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.sql.ResultSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.entity.DosageProviderEntity;
import phr.datadomain.entity.DosageTypeCd;


/**
 *
 * @author iwaasa
 */
public abstract class DosageProviderEntityBase extends AbstractEntity{
   /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageProviderEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public  DosageProviderEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */
    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤ID */
    protected String dosageId = null;
    /* 調剤番号 */
    protected int seq = 0;
    
    /* 調剤薬局 */
    protected String pharmacy = null;
    /* 薬剤師名 */
    protected String pharmacistName = null;
    /* 処方医療機関 */
    protected String medicalOrganization = null;
    /* 処方診療科 */
    protected String departmentName = null;
    /* 処方医師名 */
    protected String doctorName = null;
    /* -------------------------------------------------------------------------------------- */
    
        /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static DosageProviderEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static DosageProviderEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageProviderEntity entity = new DosageProviderEntity();

        while( dataRow.next() ) {
            String typeCd = getString(dataRow,"DosageTypeCd");
            if(typeCd.equals(DosageTypeCd.DOSAGE.getId())){
                //調剤
                entity.setPharmacy(getString(dataRow, "MedicalOrganizationName"));
                entity.setPharmacistName(getString(dataRow, "DoctorName"));
            }else if(typeCd.equals(DosageTypeCd.RECIPE.getId())){
                //処方
                entity.setMedicalOrganization(getString(dataRow, "MedicalOrganizationName"));
                entity.setDoctorName(getString(dataRow, "DoctorName"));
                entity.setDepartmentName(getString(dataRow, "DepartmentName"));
            }
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("調剤薬局     ：" + entity.getPharmacy());
            logger.debug("薬剤師名    ：" + entity.getPharmacistName());
            logger.debug("処方医療機関       ：" + entity.getMedicalOrganization());
            logger.debug("処方診療科       ：" + entity.getDepartmentName());
            logger.debug("処方医師名       ：" + entity.getDoctorName());
        }
        logger.trace("End");
        return entity;
    }
    

    /**
     * @return the dosageId
     */
    public String getDosageId() {
        return dosageId;
    }

    /**
     * @param dosageId the dosageId to set
     */
    public void setDosageId(String dosageId) {
        this.dosageId = dosageId;
    }

    /**
     * @return the seq
     */
    public int getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * @return the pharmacy
     */
    public String getPharmacy() {
        return pharmacy;
    }

    /**
     * @param pharmacy the pharmacy to set
     */
    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    /**
     * @return the pharmacistName
     */
    public String getPharmacistName() {
        return pharmacistName;
    }

    /**
     * @param pharmacistName the pharmacistName to set
     */
    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    /**
     * @return the medicalOrganization
     */
    public String getMedicalOrganization() {
        return medicalOrganization;
    }

    /**
     * @param medicalOrganization the medicalOrganization to set
     */
    public void setMedicalOrganization(String medicalOrganization) {
        this.medicalOrganization = medicalOrganization;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the doctorName
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * @param doctorName the doctorName to set
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
     
    
    
}
