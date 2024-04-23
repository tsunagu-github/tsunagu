package phr.web.phone.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author iwaasa
 */
public class MedicineDosageDto {
    //調剤ID
    private String dosageId=null;
    //調剤日
//    private Date dosageDate = null;
    private String dosageDate = null;
    //薬局名
    private String pharmacy = null;
    //薬剤師名
    private String pharmacistName = null;
    //備考
    private List<String> dosageRemark = null;
    //患者記入情報
    private List<DosagePatientInputEntityDto> patientInput = null;    
    //服用注意情報
    private List<String> dosageAttention = null;
    //レコード作成者（医療関係者：1、患者等：2、その他：8、不明：9）
    private String recordCreatorType = null;
    //調剤リスト
    private List<DosageDataDto> dosageDataList = null;

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
     * @return the dosageRemark
     */
    public List<String> getDosageRemark() {
        return dosageRemark;
    }

    /**
     * @param dosageRemark the dosageRemark to set
     */
    public void setDosageRemark(List<String> dosageRemark) {
        this.dosageRemark = dosageRemark;
    }

    /**
     * @return the recordCreatorType
     */
    public String getRecordCreatorType() {
        return recordCreatorType;
    }

    /**
     * @param recordCreatorType the recordCreatorType to set
     */
    public void setRecordCreatorType(String recordCreatorType) {
        this.recordCreatorType = recordCreatorType;
    }

    /**
     * @return the patientInput
     */
    public List<DosagePatientInputEntityDto> getPatientInput() {
        return patientInput;
    }

    /**
     * @param patientInput the patientInput to set
     */
    public void setPatientInput(List<DosagePatientInputEntityDto> patientInput) {
        this.patientInput = patientInput;
    }

    /**
     * @return the dosageAttention
     */
    public List<String> getDosageAttention() {
        return dosageAttention;
    }

    /**
     * @param dosageAttention the dosageAttention to set
     */
    public void setDosageAttention(List<String> dosageAttention) {
        this.dosageAttention = dosageAttention;
    }

    /**
     * @return the dosageDataList
     */
    public List<DosageDataDto> getDosageDataList() {
        return dosageDataList;
    }

    /**
     * @param dosageDataList the dosageDataList to set
     */
    public void setDosageDataList(List<DosageDataDto> dosageDataList) {
        this.dosageDataList = dosageDataList;
    }    
    
//    //調剤ID
//    private String dosageId=null;
//    //調剤番号
//    private int seq = 0;
//    //調剤日
////    private Date dosageDate = null;
//    private String dosageDate = null;
//    //薬局名
//    private String pharmacy = null;
//    //薬剤師名
//    private String pharmacistName = null;
//    //医療機関名
//    private String medicalOrganization = null;
//    //診療科名
//    private String departmentName = null;
//    //医師名
//    private String doctorName = null;
//    //備考
//    private String dosageRemark = null; 
//    //取得元
//    private String recordCreatorType = null;
//    //薬品リスト
//    private List<MedicineListDto> medicineList;
//
//    /**
//     * @return the dosageId
//     */
//    public String getDosageId() {
//        return dosageId;
//    }
//
//    /**
//     * @param dosageId the dosageId to set
//     */
//    public void setDosageId(String dosageId) {
//        this.dosageId = dosageId;
//    }
//
//    /**
//     * @return the seq
//     */
//    public int getSeq() {
//        return seq;
//    }
//
//    /**
//     * @param seq the seq to set
//     */
//    public void setSeq(int seq) {
//        this.seq = seq;
//    }
//
//    /**
//     * @return the dosageDate
//     */
//    public String getDosageDate() {
//        return dosageDate;
//    }
//
//    /**
//     * @param dosageDate the dosageDate to set
//     */
//    public void setDosageDate(String dosageDate) {
//        this.dosageDate = dosageDate;
//    }
//
//    /**
//     * @return the pharmacy
//     */
//    public String getPharmacy() {
//        return pharmacy;
//    }
//
//    /**
//     * @param pharmacy the pharmacy to set
//     */
//    public void setPharmacy(String pharmacy) {
//        this.pharmacy = pharmacy;
//    }
//
//    /**
//     * @return the pharmacistName
//     */
//    public String getPharmacistName() {
//        return pharmacistName;
//    }
//
//    /**
//     * @param pharmacistName the pharmacistName to set
//     */
//    public void setPharmacistName(String pharmacistName) {
//        this.pharmacistName = pharmacistName;
//    }
//
//    /**
//     * @return the medicalOrganization
//     */
//    public String getMedicalOrganization() {
//        return medicalOrganization;
//    }
//
//    /**
//     * @param medicalOrganization the medicalOrganization to set
//     */
//    public void setMedicalOrganization(String medicalOrganization) {
//        this.medicalOrganization = medicalOrganization;
//    }
//
//    /**
//     * @return the departmentName
//     */
//    public String getDepartmentName() {
//        return departmentName;
//    }
//
//    /**
//     * @param departmentName the departmentName to set
//     */
//    public void setDepartmentName(String departmentName) {
//        this.departmentName = departmentName;
//    }
//
//    /**
//     * @return the doctorName
//     */
//    public String getDoctorName() {
//        return doctorName;
//    }
//
//    /**
//     * @param doctorName the doctorName to set
//     */
//    public void setDoctorName(String doctorName) {
//        this.doctorName = doctorName;
//    }
//
//    /**
//     * @return the medicineList
//     */
//    public List<MedicineListDto> getMedicineList() {
//        return medicineList;
//    }
//
//    /**
//     * @param medicineList the medicineList to set
//     */
//    public void setMedicineList(List<MedicineListDto> medicineList) {
//        this.medicineList = medicineList;
//    }
//
//    /**
//     * @return the dosageRemark
//     */
//    public String getDosageRemark() {
//        return dosageRemark;
//    }
//
//    /**
//     * @param dosageRemark the dosageRemark to set
//     */
//    public void setDosageRemark(String dosageRemark) {
//        this.dosageRemark = dosageRemark;
//    }
//
//    /**
//     * @return the recordCreatorType
//     */
//    public String getRecordCreatorType() {
//        return recordCreatorType;
//    }
//
//    /**
//     * @param recordCreatorType the recordCreatorType to set
//     */
//    public void setRecordCreatorType(String recordCreatorType) {
//        this.recordCreatorType = recordCreatorType;
//    }
    
    

}
