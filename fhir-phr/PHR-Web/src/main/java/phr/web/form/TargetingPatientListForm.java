package phr.web.form;

import java.util.List;

import phr.datadomain.entity.PatientEntity;

/**
 * 
 * @author kisvdi017
 *
 */
public class TargetingPatientListForm extends AbstractForm {
    
    /*
     * パラメータ-患者ID
     */
    private String patientId;
    public String getPatientId() 
    {
        return patientId;
    }

    public void setPatientId(String patientId) 
    {
        this.patientId = patientId;
    }
    
    /*
     * パラメータ-患者姓
     */
    private String familyName;
    public String getFamilyName() 
    {
        return familyName;
    }

    public void setFamilyName(String familyName) 
    {
        this.familyName = familyName;
    }
    /*
     * パラメータ-患者名
     */
    private String givenName;
    public String getGivenName() 
    {
        return givenName;
    }

    public void setGivenName(String givenName) 
    {
        this.givenName = givenName;
    }
    /*
     * パラメータ-患者カナ姓
     */
    private String familyKana;
    public String getFamilyKana() 
    {
        return familyKana;
    }

    public void setFamilyKana(String familyKana) 
    {
        this.familyKana = familyKana;
    }
    /*
     * パラメータ-患者カナ名
     */
    private String givenKana;
    public String getGivenKana() 
    {
        return givenKana;
    }

    public void setGivenKana(String givenKana) 
    {
        this.givenKana = givenKana;
    }
    
    /*
     * 患者情報
     */
    private List<PatientEntity> patientInfo;
    public List<PatientEntity> getPatientInfo() 
    {
        return patientInfo;
    }

    public void setPatientInfo(List<PatientEntity> patientInfo) 
    {
        this.patientInfo = patientInfo;
    }
    
    /*
     * 患者情報有無
     */
    private boolean patientFlg;
    public boolean getPatientFlg() 
    {
        return patientFlg;
    }

    public void setPatientFlg(boolean patientFlg) 
    {
        this.patientFlg = patientFlg;
    }    

}
