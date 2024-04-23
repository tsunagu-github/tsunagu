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
public class MedicalKensaEntryForm extends AbstractForm {
    
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
     * パラメータ-カナ氏名_姓
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
     * パラメータ-カナ氏名_名
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
     * パラメータ-生年月日
     */
    private String birthDate;
    public String getBirthDate() 
    {
        return birthDate;
    }

    public void setBirthDate(String birthDate) 
    {
        this.birthDate = birthDate;
    }
    /*
     * パラメータ-性別
     */
    private String sex;
    public String getSex() 
    {
        return sex;
    }

    public void setSex(String sex) 
    {
        this.sex = sex;
    }
    
    /*
     * 患者情報
     */
    private ArrayList<HashMap<String, Object>> patientList;
    public ArrayList<HashMap<String, Object>> getPatientList() 
    {
        return patientList;
    }

    public void setPatientList(ArrayList<HashMap<String, Object>> patientList) 
    {
        this.patientList = patientList;
    }
}
