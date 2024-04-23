/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

/**
 *
 * @author chiba
 */
public class CooperationListDto {
    
    private String medicalAgencyCode = "";
    private String patientId ="";
    private String isValid = "";
    private String message = "";
    private boolean agreesToShare;

    public String getMedicalAgencyCode(){
        return medicalAgencyCode;
    }
    public void setMedicalAgencyCode( String value ){
        this.medicalAgencyCode = value;
    }
    
    public String getPatientId(){
        return patientId;
    }
    public void setPatientId( String value ){
        this.patientId = value;
    }

    public String getIsValid(){
        return isValid;
    }
    public void setIsValid( String value ){
        this.isValid = value;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage( String value ){
        this.message = value;
    }
	public boolean isAgreesToShare() {
		return agreesToShare;
	}
	public void setAgreesToShare(boolean agreesToShare) {
		this.agreesToShare = agreesToShare;
	}
}
