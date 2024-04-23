/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 用法レコード
 */
public class DoseRecordDto {
    
    //RP番号
    private String rpNum;
            
    //用法名称
    private String doseName;
    
    //調剤数量
    private String quantity;
    
    //調剤単位
    private String dispensingUnit;
    
    //調型コード
    private String dispensingCode;
    
    //用法コード種別
    private String doseCodeType;
    
    //用法コード
    private String doseCode;
    
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
     * @return the doseName
     */
    public String getDoseName() {
        return doseName;
    }

    /**
     * @param doseName the doseName to set
     */
    public void setDoseName(String doseName) {
        this.doseName = doseName;
    }

    /**
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the dispensingUnit
     */
    public String getDispensingUnit() {
        return dispensingUnit;
    }

    /**
     * @param dispensingUnit the dispensingUnit to set
     */
    public void setDispensingUnit(String dispensingUnit) {
        this.dispensingUnit = dispensingUnit;
    }

    /**
     * @return the dispensingCode
     */
    public String getDispensingCode() {
        return dispensingCode;
    }

    /**
     * @param dispensingCode the dispensingCode to set
     */
    public void setDispensingCode(String dispensingCode) {
        this.dispensingCode = dispensingCode;
    }

    /**
     * @return the doseCodeType
     */
    public String getDoseCodeType() {
        return doseCodeType;
    }

    /**
     * @param doseCodeType the doseCodeType to set
     */
    public void setDoseCodeType(String doseCodeType) {
        this.doseCodeType = doseCodeType;
    }

    /**
     * @return the doseCode
     */
    public String getDoseCode() {
        return doseCode;
    }

    /**
     * @param doseCode the doseCode to set
     */
    public void setDoseCode(String doseCode) {
        this.doseCode = doseCode;
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
    
    public DoseRecordDto setDoseRecord(String line){
        DoseRecordDto dto = new DoseRecordDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setRpNum(elements[1]);
        if(size >= 3) dto.setDoseName(elements[2]);
        if(size >= 4) dto.setQuantity(elements[3]);
        if(size >= 5) dto.setDispensingUnit(elements[4]);
        if(size >= 6) dto.setDispensingCode(elements[5]);
        if(size >= 7) dto.setDoseCodeType(elements[6]);
        if(size >= 8) dto.setDoseCode(elements[7]);
        if(size >= 9) dto.setRecorder(elements[8]);
        
        return dto;
    }
}
