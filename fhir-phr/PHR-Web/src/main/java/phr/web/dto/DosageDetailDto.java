/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

/**
 *
 * @author KISNOTE011
 */
public class DosageDetailDto {
    /* 調剤ID */
    protected String dosageId = null;
    public String getDosageId() {
        return this.dosageId;
    }
    public void setDosageId(String value) {
        this.dosageId = value;
    }

    /* 調剤番号 */
    protected int seq = 0;
    public int getSeq() {
        return this.seq;
    }
    public void setSeq(int value) {
        this.seq = value;
    }

    /* 調剤等年月日 */
    protected String dosageDate = null;
    public String getDosageDate() {
        return this.dosageDate;
    }
    public void setDosageDate(String value) {
        this.dosageDate = value;
    }

    /* PHRID */
    protected String pHR_ID = null;
    public String getPHR_ID() {
        return this.pHR_ID;
    }
    public void setPHR_ID(String value) {
        this.pHR_ID = value;
    }

    /* かかりつけ薬剤師氏名 */
    protected String pharmaceutistName = null;
    public String getPharmaceutistName() {
        return this.pharmaceutistName;
    }
    public void setPharmaceutistName(String value) {
        this.pharmaceutistName = value;
    }

    /* 勤務先薬局名称 */
    protected String pharmacy = null;
    public String getPharmacy() {
        return this.pharmacy;
    }
    public void setPharmacy(String value) {
        this.pharmacy = value;
    }

    /* 処方調剤 */
    protected String dosageTypeCd = null;
    public String getDosageTypeCd() {
        return this.dosageTypeCd;
    }
    public void setDosageTypeCd(String value) {
        this.dosageTypeCd = value;
    }

    /* 医療機関等コード */
    protected String medicalOrganizationCd = null;
    public String getMedicalOrganizationCd() {
        return this.medicalOrganizationCd;
    }
    public void setMedicalOrganizationCd(String value) {
        this.medicalOrganizationCd = value;
    }

    /* 医療機関等名称 */
    protected String medicalOrganizationName = null;
    public String getMedicalOrganizationName() {
        return this.medicalOrganizationName;
    }
    public void setMedicalOrganizationName(String value) {
        this.medicalOrganizationName = value;
    }

    /* 医師･薬剤師氏名 */
    protected String doctorName = null;
    public String getDoctorName() {
        return this.doctorName;
    }
    public void setDoctorName(String value) {
        this.doctorName = value;
    }

    /* 処方備考 */
    protected String dosageRemark = null;
    public String getDosageRemark() {
        return this.dosageRemark;
    }
    public void setDosageRemark(String value) {
        this.dosageRemark = value;
    }

    /* 処方番号 */
    protected int recipeNo = 0;
    public int getRecipeNo() {
        return this.recipeNo;
    }
    public void setRecipeNo(int value) {
        this.recipeNo = value;
    }

    /* 用法名称 */
    protected String usageName = null;
    public String getUsageName() {
        return this.usageName;
    }
    public void setUsageName(String value) {
        this.usageName = value;
    }

    /* 調剤数量 */
    protected Integer dosageQuantity = null;
    public Integer getDosageQuantity() {
        return this.dosageQuantity;
    }
    public void setDosageQuantity(Integer value) {
        this.dosageQuantity = value;
    }

    /* 調剤単位 */
    protected String dosageUnit = null;
    public String getDosageUnit() {
        return this.dosageUnit;
    }
    public void setDosageUnit(String value) {
        this.dosageUnit = value;
    }

    /* 剤型コード */
    protected String dosageForms = null;
    public String getDosageForms() {
        return this.dosageForms;
    }
    public void setDosageForms(String value) {
        this.dosageForms = value;
    }

    /* 薬剤番号 */
    protected int medicineSeq = 0;
    public int getMedicineSeq() {
        return this.medicineSeq;
    }
    public void setMedicineSeq(int value) {
        this.medicineSeq = value;
    }

    /* 薬品名称 */
    protected String medicineName = null;
    public String getMedicineName() {
        return this.medicineName;
    }
    public void setMedicineName(String value) {
        this.medicineName = value;
    }

    /* 用量 */
    protected String amount = null;
    public String getAmount() {
        return this.amount;
    }
    public void setAmount(String value) {
        this.amount = value;
    }

    /* 単位名 */
    protected String unitName = null;
    public String getUnitName() {
        return this.unitName;
    }
    public void setUnitName(String value) {
        this.unitName = value;
    }

    /* 薬品コード種別 */
    protected String medicineCdType = null;
    public String getMedicineCdType() {
        return this.medicineCdType;
    }
    public void setMedicineCdType(String value) {
        this.medicineCdType = value;
    }

    /* 薬品コード */
    protected String medicineCd = null;
    public String getMedicineCd() {
        return this.medicineCd;
    }
    public void setMedicineCd(String value) {
        this.medicineCd = value;
    }

    /* レコード作成者 */
    protected String recordCreatorType = null;
    public String getRecordCreatorType() {
        return this.recordCreatorType;
    }
    public void setRecordCreatorType(String value) {
        this.recordCreatorType = value;
    }

    /* 一般薬品名症 */
    protected String nonPreMedicineName = null;
    public String getNonPreMedicineName() {
        return this.nonPreMedicineName;
    }
    public void setNonPreMedicineName(String value) {
        this.nonPreMedicineName = value;
    }

    /* 一般薬レコード作成者 */
    protected String nonPreRecordCreatorType = null;
    public String getNonPreRecordCreatorType() {
        return this.nonPreRecordCreatorType;
    }
    public void setNonPreRecordCreatorType(String value) {
        this.nonPreRecordCreatorType = value;
    }

    // 左ボタンフラグ
    protected String leftFlg;
    public String getLeftFlg(){
        return this.leftFlg;
    }
    public void setLeftFlg(String leftFlg){
        this.leftFlg=leftFlg;
    }

    // 右ボタンフラグ
    protected String rightFlg;
    public String getRightFlg(){
        return this.rightFlg;
    }
    public void setRightFlg(String rightFlg){
        this.rightFlg=rightFlg;
    }
    
}
