/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

import java.util.List;

/**
 *
 * @author kis-note
 * 調剤情報
 */
public class MedicineInfoDto {
     //調剤等年月日レコード
    private DispensingDateDto dispensingDateDto;
    
    //調剤医療機関等レコード
    private MedicalOrgInfoDto medicalInfoDto;
    
    //調剤医師・薬剤師レコード
    private DoctorInfoDto doctorIndoDto;
    
    //処方医療機関レコード
    private PrescriptionMedicalOrgDto p_medicalOrgDto;
    
    //処方医師情報
    private List<PrescriotionInfoDto> prescriptionInfoList;

    /**
     * @return the dispensingDateDto
     */
    public DispensingDateDto getDispensingDateDto() {
        return dispensingDateDto;
    }

    /**
     * @param dispensingDateDto the dispensingDateDto to set
     */
    public void setDispensingDateDto(DispensingDateDto dispensingDateDto) {
        this.dispensingDateDto = dispensingDateDto;
    }

    /**
     * @return the medicalInfoDto
     */
    public MedicalOrgInfoDto getMedicalInfoDto() {
        return medicalInfoDto;
    }

    /**
     * @param medicalInfoDto the medicalInfoDto to set
     */
    public void setMedicalInfoDto(MedicalOrgInfoDto medicalInfoDto) {
        this.medicalInfoDto = medicalInfoDto;
    }

    /**
     * @return the doctorIndoDto
     */
    public DoctorInfoDto getDoctorIndoDto() {
        return doctorIndoDto;
    }

    /**
     * @param doctorIndoDto the doctorIndoDto to set
     */
    public void setDoctorIndoDto(DoctorInfoDto doctorIndoDto) {
        this.doctorIndoDto = doctorIndoDto;
    }

    /**
     * @return the p_medicalOrgDto
     */
    public PrescriptionMedicalOrgDto getP_medicalOrgDto() {
        return p_medicalOrgDto;
    }

    /**
     * @param p_medicalOrgDto the p_medicalOrgDto to set
     */
    public void setP_medicalOrgDto(PrescriptionMedicalOrgDto p_medicalOrgDto) {
        this.p_medicalOrgDto = p_medicalOrgDto;
    }

    /**
     * @return the prescriptionInfoList
     */
    public List<PrescriotionInfoDto> getPrescriptionInfoList() {
        return prescriptionInfoList;
    }

    /**
     * @param prescriptionInfoList the prescriptionInfoList to set
     */
    public void setPrescriptionInfoList(List<PrescriotionInfoDto> prescriptionInfoList) {
        this.prescriptionInfoList = prescriptionInfoList;
    }
   
}
