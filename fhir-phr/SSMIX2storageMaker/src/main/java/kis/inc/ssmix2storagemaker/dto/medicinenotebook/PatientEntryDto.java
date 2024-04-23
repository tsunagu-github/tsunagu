/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 患者等記入レコード
 */
public class PatientEntryDto {
    
    //患者等記入情報
    private String entryRecord;
    
    //入力年月日
    private String entryDate;

    /**
     * @return the entryRecord
     */
    public String getEntryRecord() {
        return entryRecord;
    }

    /**
     * @param entryRecord the entryRecord to set
     */
    public void setEntryRecord(String entryRecord) {
        this.entryRecord = entryRecord;
    }

    /**
     * @return the entryDate
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    
    public PatientEntryDto setPatientEntry(String line){
        PatientEntryDto dto = new PatientEntryDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setEntryRecord(elements[1]);
        if(size >= 3) dto.setEntryDate(elements[2]);
        
        return dto;
    }
    
}
