/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 備考レコード
 */
public class RemarksDto {
    
    //備考情報
    private String remarks;

    //レコード作成者
    private String recorder;

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    
    public RemarksDto setRemarksDto(String line){
        RemarksDto dto = new RemarksDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setRemarks(elements[1]);
        if(size >= 3) dto.setRecorder(elements[2]);
                
        return dto;
    }
}
