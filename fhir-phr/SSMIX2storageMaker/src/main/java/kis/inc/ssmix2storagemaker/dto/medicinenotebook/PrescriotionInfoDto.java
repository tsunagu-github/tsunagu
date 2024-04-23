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
 * 処方医師情報
 */
public class PrescriotionInfoDto {
    
    //処方医師レコード
    private PrescriotionDoctorDto p_DoctorDto;
    
    //RP情報
    private List<RPInfoDto> rpInfoList;
    
    //服用注意レコード
//    private DosingCautionDto dosingCautionDto;
    private List<DosingCautionDto> dosingCautionDtoList;
    
    //医療機関等提供情報レコード
//    private OfferRecordDto offerRecordDto;
    private List<OfferRecordDto> offerRecordDtoList;
    
    //備考レコード
//    private RemarksDto remarksDto;
    private List<RemarksDto> remarksDtoList;
    
    //患者等記入情報
//    private PatientEntryDto patientEntryDto;
    private List<PatientEntryDto> patientEntryDtoList;

    /**
     * @return the p_DoctorDto
     */
    public PrescriotionDoctorDto getP_DoctorDto() {
        return p_DoctorDto;
    }

    /**
     * @param p_DoctorDto the p_DoctorDto to set
     */
    public void setP_DoctorDto(PrescriotionDoctorDto p_DoctorDto) {
        this.p_DoctorDto = p_DoctorDto;
    }

    /**
     * @return the rpInfoList
     */
    public List<RPInfoDto> getRpInfoList() {
        if(rpInfoList == null){
            rpInfoList = new ArrayList();
        }
        return rpInfoList;
    }

    /**
     * @param rpInfoList the rpInfoList to set
     */
    public void setRpInfoList(List<RPInfoDto> rpInfoList) {
        this.rpInfoList = rpInfoList;
    }

    /**
     * @return the dosingCautionDto
     */
//    public DosingCautionDto getDosingCautionDto() {
    public DosingCautionDto getDosingCautionDto(int i) {
        return dosingCautionDtoList.get(i);
//        return dosingCautionDto;
    }

    /**
     * @param dosingCautionDto the dosingCautionDto to set
     */
    public void setDosingCautionDto(DosingCautionDto dosingCautionDto) {
        if(dosingCautionDtoList == null){
            dosingCautionDtoList = new ArrayList();
        }
        dosingCautionDtoList.add(dosingCautionDto);
//        this.dosingCautionDto = dosingCautionDto;
    }

    /**
     * @return the offerRecordDto
     */
//    public OfferRecordDto getOfferRecordDto() {
    public OfferRecordDto getOfferRecordDto(int i) {
        return offerRecordDtoList.get(i);
//        return offerRecordDto;
    }

    /**
     * @param offerRecordDto the offerRecordDto to set
     */
    public void setOfferRecordDto(OfferRecordDto offerRecordDto) {
//        this.offerRecordDto = offerRecordDto;
        if(offerRecordDtoList == null){
            offerRecordDtoList = new ArrayList();
        }
        offerRecordDtoList.add(offerRecordDto);
    }

    /**
     * @return the remarksDto
     */
//    public RemarksDto getRemarksDto() {
    public RemarksDto getRemarksDto(int i) {
        return remarksDtoList.get(i);
//        return remarksDto;
    }

    /**
     * @param remarksDto the remarksDto to set
     */
    public void setRemarksDto(RemarksDto remarksDto) {
        if(remarksDtoList == null){
            remarksDtoList = new ArrayList();
        }
        remarksDtoList.add(remarksDto);
//        this.remarksDto = remarksDto;
    }

    /**
     * @return the patientEntryDto
     */
//    public PatientEntryDto getPatientEntryDto() {
    public PatientEntryDto getPatientEntryDto(int i) {
        return patientEntryDtoList.get(i);
//        return patientEntryDto;
    }

    /**
     * @param patientEntryDto the patientEntryDto to set
     */
    public void setPatientEntryDto(PatientEntryDto patientEntryDto) {
        if(patientEntryDtoList == null){
            patientEntryDtoList = new ArrayList();
        }
        patientEntryDtoList.add(patientEntryDto);
//        this.patientEntryDto = patientEntryDto;
    }

    /**
     * @return the dosingCautionDtoList
     */
    public List<DosingCautionDto> getDosingCautionDtoList() {
        return dosingCautionDtoList;
    }

    /**
     * @param dosingCautionDtoList the dosingCautionDtoList to set
     */
    public void setDosingCautionDtoList(List<DosingCautionDto> dosingCautionDtoList) {
        this.dosingCautionDtoList = dosingCautionDtoList;
    }

    /**
     * @return the offerRecordDtoList
     */
    public List<OfferRecordDto> getOfferRecordDtoList() {
        return offerRecordDtoList;
    }

    /**
     * @param offerRecordDtoList the offerRecordDtoList to set
     */
    public void setOfferRecordDtoList(List<OfferRecordDto> offerRecordDtoList) {
        this.offerRecordDtoList = offerRecordDtoList;
    }

    /**
     * @return the remarksDtoList
     */
    public List<RemarksDto> getRemarksDtoList() {
        return remarksDtoList;
    }

    /**
     * @param remarksDtoList the remarksDtoList to set
     */
    public void setRemarksDtoList(List<RemarksDto> remarksDtoList) {
        this.remarksDtoList = remarksDtoList;
    }

    /**
     * @return the patientEntryDtoList
     */
    public List<PatientEntryDto> getPatientEntryDtoList() {
        return patientEntryDtoList;
    }

    /**
     * @param patientEntryDtoList the patientEntryDtoList to set
     */
    public void setPatientEntryDtoList(List<PatientEntryDto> patientEntryDtoList) {
        this.patientEntryDtoList = patientEntryDtoList;
    }
    
}
