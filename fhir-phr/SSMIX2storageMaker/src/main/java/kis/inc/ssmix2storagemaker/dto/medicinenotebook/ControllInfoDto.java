/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 制御情報
 */
public class ControllInfoDto {
    //データ固有ID
    private String id;
    
    //分割数
    private String divisionNum;
    
    //データ連番
    private String dataNum;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the divisionNum
     */
    public String getDivisionNum() {
        return divisionNum;
    }

    /**
     * @param divisionNum the divisionNum to set
     */
    public void setDivisionNum(String divisionNum) {
        this.divisionNum = divisionNum;
    }

    /**
     * @return the dataNum
     */
    public String getDataNum() {
        return dataNum;
    }

    /**
     * @param dataNum the dataNum to set
     */
    public void setDataNum(String dataNum) {
        this.dataNum = dataNum;
    }
    
    public ControllInfoDto setControllInfo(String line){
        ControllInfoDto dto = new ControllInfoDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setId(elements[1]);
        if(size >= 3) dto.setDivisionNum(elements[2]);
        if(size >= 4) dto.setDataNum(elements[3]);
        
        return dto;
    }
    
}
