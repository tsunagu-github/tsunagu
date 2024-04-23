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
public class DoctorInfoDto {
    
    //医師・薬剤師氏名
    private String name;
    
    //医師・薬剤師連絡先
    private String telNo;
    
    //レコード作成者
    private String recoder;

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
     * @return the recoder
     */
    public String getRecoder() {
        return recoder;
    }

    /**
     * @param recoder the recoder to set
     */
    public void setRecoder(String recoder) {
        this.recoder = recoder;
    }
 
    public DoctorInfoDto setDoctorInfo(String line){
        DoctorInfoDto dto = new DoctorInfoDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setName(elements[1]);
        if(size >= 3) dto.setTelNo(elements[2]);
        if(size >= 4) dto.setRecoder(elements[3]);
        
        return dto;
    }
}
