/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 処方服用注意レコード
 */
public class PrescriptionCautionDto {
    
    //RP番号
    private String rpNum;
    
    //内容
    private String comments;

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

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public PrescriptionCautionDto setPrescriptionCaution(String line){
        PrescriptionCautionDto dto = new PrescriptionCautionDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setRpNum(elements[1]);
        if(size >= 3) dto.setComments(elements[2]);
        if(size >= 4) dto.setRecorder(elements[3]);
        
        return dto;
    }
}
