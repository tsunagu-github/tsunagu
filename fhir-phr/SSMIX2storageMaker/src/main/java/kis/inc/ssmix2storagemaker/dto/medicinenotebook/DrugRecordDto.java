/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 薬品レコード
 */
public class DrugRecordDto {
    
    //RP番号
    private String rpNum;
    
    //薬品名称
    private String mName;
    
    //用量
    private String dose;
    
    //単位名
    private String unitName;
    
    //薬品コード種別
    private String drugCodeType;
    
    //薬品コード
    private String drugCode;
    
    //レコード作成者
    private String recorder;

    /**
     * @return the rpNum
     */
    public String getRpNum() {
        return rpNum;
    }

    /**
     * @param rpNum the rpNum to set
     */
    public void setRpNum(String rpNum) {
        this.rpNum = rpNum;
    }

    /**
     * @return the mName
     */
    public String getmName() {
        return mName;
    }

    /**
     * @param mName the mName to set
     */
    public void setmName(String mName) {
        this.mName = mName;
    }

    /**
     * @return the dose
     */
    public String getDose() {
        return dose;
    }

    /**
     * @param dose the dose to set
     */
    public void setDose(String dose) {
        this.dose = dose;
    }

    /**
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * @return the drugCodeType
     */
    public String getDrugCodeType() {
        return drugCodeType;
    }

    /**
     * @param drugCodeType the drugCodeType to set
     */
    public void setDrugCodeType(String drugCodeType) {
        this.drugCodeType = drugCodeType;
    }

    /**
     * @return the drugCode
     */
    public String getDrugCode() {
        return drugCode;
    }

    /**
     * @param drugCode the drugCode to set
     */
    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    /**
     * @return the recorder
     */
    public String getRecorder() {
        return recorder;
    }

    /**
     * @param recorder the recorder to set
     */
    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }
    
    public DrugRecordDto setDrugRecord(String line){
        DrugRecordDto dto = new DrugRecordDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2)dto.setRpNum(elements[1]);
        if(size >= 3)dto.setmName(elements[2]);
        if(size >= 4)dto.setDose(elements[3]);
        if(size >= 5)dto.setUnitName(elements[4]);
        if(size >= 6)dto.setDrugCodeType(elements[5]);
        if(size >= 7)dto.setDrugCode(elements[6]);
        if(size >= 8)dto.setRecorder(elements[7]);
        
        return dto;
    }
}
