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
 * RP情報
 */
public class RPInfoDto {
    
    //薬品情報
    private List<DrugInfoDto> drugInfoList;
    
    //用法レコード
    private DoseRecordDto doseRecordDto;
    
    //用法補足レコード
//    private DoesSupplementalDto doesSupplementalDto;
    private List<DoesSupplementalDto> doesSupplementalDtoList;
    
    //処方服用注意レコード
//    private PrescriptionCautionDto prescriptionCautionDto;
    private List<PrescriptionCautionDto> prescriptionCautionDtoList;

    /**
     * @return the drugInfoList
     */
    public List<DrugInfoDto> getDrugInfoList() {
        return drugInfoList;
    }

    /**
     * @param drugInfoList the drugInfoList to set
     */
    public void setDrugInfoList(List<DrugInfoDto> drugInfoList) {
        this.drugInfoList = drugInfoList;
    }

    /**
     * @return the doseRecordDto
     */
    public DoseRecordDto getDoseRecordDto() {
        return doseRecordDto;
    }

    /**
     * @param doseRecordDto the doseRecordDto to set
     */
    public void setDoseRecordDto(DoseRecordDto doseRecordDto) {
        this.doseRecordDto = doseRecordDto;
    }

    /**
     * @return the doesSupplementalDto
     */
//    public DoesSupplementalDto getDoesSupplementalDto() {
    public DoesSupplementalDto getDoesSupplementalDto(int i) {
        return doesSupplementalDtoList.get(i);
//        return doesSupplementalDto;
    }

    /**
     * @param doesSupplementalDto the doesSupplementalDto to set
     */
    public void setDoesSupplementalDto(DoesSupplementalDto doesSupplementalDto) {
        if(doesSupplementalDtoList == null){
            doesSupplementalDtoList = new ArrayList();
        }        
        doesSupplementalDtoList.add(doesSupplementalDto);
//        this.doesSupplementalDto = doesSupplementalDto;
    }

    /**
     * @return the prescriptionCautionDto
     */
//    public PrescriptionCautionDto getPrescriptionCautionDto() {
    public PrescriptionCautionDto getPrescriptionCautionDto(int i) {
        return prescriptionCautionDtoList.get(i);
//        return prescriptionCautionDto;
    }

    /**
     * @param prescriptionCautionDto the prescriptionCautionDto to set
     */
    public void setPrescriptionCautionDto(PrescriptionCautionDto prescriptionCautionDto) {
        if(prescriptionCautionDtoList == null){
            prescriptionCautionDtoList = new ArrayList();
        }        
        prescriptionCautionDtoList.add(prescriptionCautionDto);
//        this.prescriptionCautionDto = prescriptionCautionDto;        
    }

    /**
     * @return the doesSupplementalDtoList
     */
    public List<DoesSupplementalDto> getDoesSupplementalDtoList() {
        return doesSupplementalDtoList;
    }

    /**
     * @param doesSupplementalDtoList the doesSupplementalDtoList to set
     */
    public void setDoesSupplementalDtoList(List<DoesSupplementalDto> doesSupplementalDtoList) {
        this.doesSupplementalDtoList = doesSupplementalDtoList;
    }

    /**
     * @return the prescriptionCautionDtoList
     */
    public List<PrescriptionCautionDto> getPrescriptionCautionDtoList() {
        return prescriptionCautionDtoList;
    }

    /**
     * @param prescriptionCautionDtoList the prescriptionCautionDtoList to set
     */
    public void setPrescriptionCautionDtoList(List<PrescriptionCautionDto> prescriptionCautionDtoList) {
        this.prescriptionCautionDtoList = prescriptionCautionDtoList;
    }
    
    
}
