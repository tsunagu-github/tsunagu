/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kis-note
 * 薬品情報
 */
public class DrugInfoDto {
    
    //薬品レコード
    private DrugRecordDto drugRecordDto;
    
    //薬品補足レコード
//    private DrugSupplementalDto drugSupplementalDto;
    private List<DrugSupplementalDto> drugSupplementalDtoList;
    
    //薬品服用注意レコード
//    private DrugCautionDto drugCautionDto;
    private List<DrugCautionDto> drugCautionDtoList;

    /**
     * @return the drugRecordDto
     */
    public DrugRecordDto getDrugRecordDto() {
        return drugRecordDto;
    }

    /**
     * @param drugRecordDto the drugRecordDto to set
     */
    public void setDrugRecordDto(DrugRecordDto drugRecordDto) {
        this.drugRecordDto = drugRecordDto;
    }

    /**
     * @return the drugSupplementalDto
     */
//    public DrugSupplementalDto getDrugSupplementalDto() {
    public DrugSupplementalDto getDrugSupplementalDto(int i) {
        return drugSupplementalDtoList.get(i);
//        return drugSupplementalDto;
    }

    /**
     * @param drugSupplementalDto the drugSupplementalDto to set
     */
    public void setDrugSupplementalDto(DrugSupplementalDto drugSupplementalDto) {
        if(drugSupplementalDtoList == null){
            drugSupplementalDtoList = new ArrayList();
        }
        drugSupplementalDtoList.add(drugSupplementalDto);        
//        this.drugSupplementalDto = drugSupplementalDto;
    }

    /**
     * @return the drugCautionDto
     */
//    public DrugCautionDto getDrugCautionDto() {
    public DrugCautionDto getDrugCautionDto(int i) {
        return drugCautionDtoList.get(i);
//        return drugCautionDto;
    }

    /**
     * @param drugCautionDto the drugCautionDto to set
     */
    public void setDrugCautionDto(DrugCautionDto drugCautionDto) {
        if(drugCautionDtoList == null){
            drugCautionDtoList = new ArrayList();
        }
        
        drugCautionDtoList.add(drugCautionDto);
//        this.drugCautionDto = drugCautionDto;
    }

    /**
     * @return the drugSupplementalDtoList
     */
    public List<DrugSupplementalDto> getDrugSupplementalDtoList() {
        return drugSupplementalDtoList;
    }

    /**
     * @param drugSupplementalDtoList the drugSupplementalDtoList to set
     */
    public void setDrugSupplementalDtoList(List<DrugSupplementalDto> drugSupplementalDtoList) {
        this.drugSupplementalDtoList = drugSupplementalDtoList;
    }

    /**
     * @return the drugCautionDtoList
     */
    public List<DrugCautionDto> getDrugCautionDtoList() {
        return drugCautionDtoList;
    }

    /**
     * @param drugCautionDtoList the drugCautionDtoList to set
     */
    public void setDrugCautionDtoList(List<DrugCautionDto> drugCautionDtoList) {
        this.drugCautionDtoList = drugCautionDtoList;
    }
    
}
