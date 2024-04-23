/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 薬品服用注意レコード
 */
public class DrugCautionDto {

    //RP番号
    private String rpNum;
    
    //内容
    private String coments;
    
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
    
    public DrugCautionDto setDrugCaution(String line){
        DrugCautionDto dto = new DrugCautionDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2)dto.setRpNum(elements[1]);
        if(size >= 3)dto.setComents(elements[2]);
        if(size >= 4)dto.setRecorder(elements[3]);
        
        return dto;
    }
}
