
package phr.web.phone.dto;

import java.util.List;

/**
 *
 * @author kis-note-027_user
 */
public class MedicineEntryItemDto {
    /** 調剤Id*/
    private String dosageId = null;
    /** 調剤日*/
    private String dosageDate = null;
    /** 調剤番号*/
    private String seq=null;
    /** 薬局名*/
    private String pharmacy = null;
    /** 薬剤師名*/
    private String pharmacistName = null;
    /** 処方病院名*/
    private String medicalOrganization = null;
    /** 診療科名*/
    private String departmentName =null;
    /** 医師名*/
    private String doctorName=null;
    /** 備考*/
    private String dosageRemark=null;    
    /** 市販薬リスト*/
    private List<nonpreMedicineDto> nonpre;
    /** 調剤薬リスト*/
    private List<dosageMedicineDto> medicines;

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
     * @return the dosageDate
     */
    public String getDosageDate() {
        return dosageDate;
    }

    /**
     * @param dosageDate the dosageDate to set
     */
    public void setDosageDate(String dosageDate) {
        this.dosageDate = dosageDate;
    }

    /**
     * @return the seq
     */
    public String getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(String seq) {
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

    /**
     * @return the nonpreList
     */
    public List<nonpreMedicineDto> getNonpre() {
        return nonpre;
    }

    /**
     * @param nonpreList the nonpreList to set
     */
    public void setNonpre(List<nonpreMedicineDto> nonpre) {
        this.nonpre = nonpre;
    }

    /**
     * @return the medicineList
     */
    public List<dosageMedicineDto> getMedicines() {
        return medicines;
    }

    /**
     * @param medicineList the medicineList to set
     */
    public void setMedicines(List<dosageMedicineDto> medicines) {
        this.medicines = medicines;
    }

    /**
     * @return the dosageRemark
     */
    public String getDosageRemark() {
        return dosageRemark;
    }

    /**
     * @param dosageRemark the dosageRemark to set
     */
    public void setDosageRemark(String dosageRemark) {
        this.dosageRemark = dosageRemark;
    }
    
    
}
