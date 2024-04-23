/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 医療機関等提供情報レコード
 */
public class OfferRecordDto {
    
    //内容
    private String comments;
    
    //提供情報種別
    private String offerType;
    
    //レコード作成者
    private String recorder;

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

    /**
     * @return the offerType
     */
    public String getOfferType() {
        return offerType;
    }

    /**
     * @param offerType the offerType to set
     */
    public void setOfferType(String offerType) {
        this.offerType = offerType;
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
    
    public OfferRecordDto setOfferRecord(String line){
        OfferRecordDto dto = new OfferRecordDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setComments(elements[1]);
        if(size >= 3) dto.setOfferType(elements[2]);
        if(size >= 4) dto.setRecorder(elements[3]);
        
        return dto;
    }
    
}
