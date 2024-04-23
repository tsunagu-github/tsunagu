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
public class GenericDrugDto {
    
    //薬品名称
    private String medicineName;
    
    //服薬開始年月日
    private String startDate;
    
    //服薬終了年月日
    private String endDate;
    
    //レコード作成者
    private String recorder;

    /**
     * @return the medicineName
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * @param medicineName the medicineName to set
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
    
    public GenericDrugDto setGeneric(String line){
        GenericDrugDto dto = new GenericDrugDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setMedicineName(elements[1]);
        if(size >= 3) dto.setStartDate(elements[2]);
        if(size >= 4) dto.setEndDate(elements[3]);
        if(size >= 5) dto.setRecorder(elements[4]);
        
        return dto;
    }
}
