/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public class OneTimePassForm extends AbstractForm {
    
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
     * 権限の有無 権限有=true,無し=false
     */
    private boolean auth = false;
    public boolean isAuth() 
    {
        return auth;
    }

    public void setAuth(boolean auth) 
    {
        this.auth = auth;
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
     * 患者情報
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

    /*
     *ワンタイムパスワード 
     */
    private String oneTimePass;
    public String getOneTimePass() 
    {
        return oneTimePass;
    }

    public void setOneTimePass(String oneTimePass) 
    {
        this.oneTimePass = oneTimePass;
    }
    /*
     *ワンタイムパスワードフラグ
     */
    private boolean passwordFlg;
    public boolean getPasswordFlg() 
    {
        return passwordFlg;
    }

    public void setPasswordFlg(boolean passwordFlg) 
    {
        this.passwordFlg = passwordFlg;
    }    
    
    /*
     *有効期限 
     */
    private String expirationDate;
    public String getExpirationDate() 
    {
        return expirationDate;
    }

    public void setExpirationDate(String exDate) 
    {
        this.expirationDate = exDate;
    }
    
}
