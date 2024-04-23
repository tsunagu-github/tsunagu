/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 */
public class MedicinePatientDto {
    
    //患者氏名
    private String name;
    
    //患者性別
    private String sex;
    
    //患者生年月日
    private String birthDay;
    
    //患者郵便番号
    private String zipCode;
    
    //患者住所
    private String address;
    
    //患者電話番号
    private String telNo;
    
    //緊急連絡先
    private String emTelNo;
    
    //血液型
    private String bloddType;
    
    //体重
    private String weight;
    
    //患者氏名カナ
    private String nameKana;

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
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the birthDay
     */
    public String getBirthDay() {
        return birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
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
     * @return the emTelNo
     */
    public String getEmTelNo() {
        return emTelNo;
    }

    /**
     * @param emTelNo the emTelNo to set
     */
    public void setEmTelNo(String emTelNo) {
        this.emTelNo = emTelNo;
    }

    /**
     * @return the bloddType
     */
    public String getBloddType() {
        return bloddType;
    }

    /**
     * @param bloddType the bloddType to set
     */
    public void setBloddType(String bloddType) {
        this.bloddType = bloddType;
    }

    /**
     * @return the weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * @return the nameKana
     */
    public String getNameKana() {
        return nameKana;
    }

    /**
     * @param nameKana the nameKana to set
     */
    public void setNameKana(String nameKana) {
        this.nameKana = nameKana;
    }
    
    public MedicinePatientDto setPatient(String line){
        MedicinePatientDto dto = new MedicinePatientDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2)dto.setName(elements[1]);
        if(size >= 3)dto.setSex(elements[2]);
        if(size >= 4)dto.setBirthDay(elements[3]);
        if(size >= 5)dto.setZipCode(elements[4]);
        if(size >= 6)dto.setAddress(elements[5]);
        if(size >= 7)dto.setTelNo(elements[6]);
        if(size >= 8)dto.setEmTelNo(elements[7]);
        if(size >= 9)dto.setBloddType(elements[8]);
        if(size >= 10)dto.setWeight(elements[9]);
        if(size >= 11)dto.setNameKana(elements[10]);
        
        return dto;
    }
    
}
