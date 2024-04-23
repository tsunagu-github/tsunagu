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
public class MedicinePatientMentionDto {
    
    //患者特記種別
    private String mentionType;
    
    //患者特記内容
    private String mention;
    
    //レコード作成者
    private String recoder;

    /**
     * @return the mentionType
     */
    public String getMentionType() {
        return mentionType;
    }

    /**
     * @param mentionType the mentionType to set
     */
    public void setMentionType(String mentionType) {
        this.mentionType = mentionType;
    }

    /**
     * @return the mention
     */
    public String getMention() {
        return mention;
    }

    /**
     * @param mention the mention to set
     */
    public void setMention(String mention) {
        this.mention = mention;
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
    
    public MedicinePatientMentionDto setPatientMendtion(String line){
        MedicinePatientMentionDto dto = new MedicinePatientMentionDto();
        String[]elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2)dto.setMentionType(elements[1]);
        if(size >= 3)dto.setMention(elements[2]);
        if(size >= 4)dto.setRecoder(elements[3]);

        return dto;
    }

}
