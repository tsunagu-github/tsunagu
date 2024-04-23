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
public class DispensingDateDto {
    
    //調剤年月日
    private String dispensingDate;
    
    //レコード作成者
    private String recorder;

    /**
     * @return the dispensingDate
     */
    public String getDispensingDate() {
        return dispensingDate;
    }

    /**
     * @param dispensingDate the dispensingDate to set
     */
    public void setDispensingDate(String dispensingDate) {
        this.dispensingDate = dispensingDate;
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
    
    public DispensingDateDto setDispensing(String line){
        DispensingDateDto dto = new DispensingDateDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setDispensingDate(elements[1]);
        if(size >= 3) dto.setRecorder(elements[2]);
        
        return dto;
    }
}
