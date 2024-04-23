/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

import kis.inc.ssmix2storagemaker.Enums.MedicinePrefactureCdEnums;

/**
 *
 * @author kis-note
 */
public class MedicalOrgInfoDto {
    
    //医療機関等名称
    private String name;
    
    //医療機関等都道府県コード
    private  String prefactureCd;
    
    //医療機関等都道府県
    private String prefactureName;
    
    //医療機関等点数表
    private String pointType;
    
    //医療機関等コード
    private String medicalOrdCd;
    
    //医療機関等郵便番号
    private String zipCode;
    
    //医療機関等住所
    private String address;
    
    //医療機関等電話番号
    private String telNo;
    
    //レコード作成者
    private String recorder;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the prefactureCd
     */
    public String getPrefactureCd() {
        return prefactureCd;
    }

    /**
     * @param prefactureCd the prefactureCd to set
     */
    public void setPrefactureCd(String prefactureCd) {
        this.prefactureCd = prefactureCd;
    }

    /**
     * @return the prefactureName
     */
    public String getPrefactureName() {
        return prefactureName;
    }

    /**
     * @param prefactureName the prefactureName to set
     */
    public void setPrefactureName(String prefactureName) {
        this.prefactureName = prefactureName;
    }

    /**
     * @return the pointType
     */
    public String getPointType() {
        return pointType;
    }

    /**
     * @param pointType the pointType to set
     */
    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    /**
     * @return the medicalOrdCd
     */
    public String getMedicalOrdCd() {
        return medicalOrdCd;
    }

    /**
     * @param medicalOrdCd the medicalOrdCd to set
     */
    public void setMedicalOrdCd(String medicalOrdCd) {
        this.medicalOrdCd = medicalOrdCd;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
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
    
    public MedicalOrgInfoDto serMedicalOrgInfo(String line){
        MedicalOrgInfoDto dto = new MedicalOrgInfoDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setName(elements[1]);
        if(size >= 3){
            dto.setPrefactureCd(elements[2]);
            String p_name = MedicinePrefactureCdEnums.selectName(elements[2]);
            dto.setPrefactureName(p_name);
        }
        if(size >= 4) dto.setPointType(elements[3]);
        if(size >= 5) dto.setMedicalOrdCd(elements[4]);
        if(size >= 6) dto.setZipCode(elements[5]);
        if(size >= 7) dto.setAddress(elements[6]);
        if(size >= 8) dto.setTelNo(elements[7]);
        if(size >= 9) dto.setRecorder(elements[8]);
        
        return dto;
    }
}
