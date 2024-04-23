/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 用法補足レコード
 */
public class DoesSupplementalDto {
    
    //RP番号
    private String rpNum;
    
    //用法補足情報
    private String supplement;
    
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
     * @return the supplement
     */
    public String getSupplement() {
        return supplement;
    }

    /**
     * @param supplement the supplement to set
     */
    public void setSupplement(String supplement) {
        this.supplement = supplement;
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
    
    public DoesSupplementalDto setDoesSupplemental(String line){
        DoesSupplementalDto dto = new DoesSupplementalDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setRpNum(elements[1]);
        if(size >= 3) dto.setSupplement(elements[2]);
        if(size >= 4) dto.setRecorder(elements[3]);
        
        return dto;
    }
}
