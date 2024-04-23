/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 服用注意レコード
 */
public class DosingCautionDto {
    
    //内容
    private String coments;
    
    //レコード作成者
    private String recorder;

    /**
     * @return the coments
     */
    public String getComents() {
        return coments;
    }

    /**
     * @param coments the coments to set
     */
    public void setComents(String coments) {
        this.coments = coments;
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
    
    public DosingCautionDto setDosingCaution(String line){
        DosingCautionDto dto = new DosingCautionDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2)dto.setComents(elements[1]);
        if(size >= 3)dto.setRecorder(elements[2]);
        
        return dto;
    }
}
