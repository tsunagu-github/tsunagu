/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import phr.web.dto.MedicalKensaEntryDto;

/**
 *
 * @author KISO-NOTE-005
 */
public class MedicalKensaPatientForm extends AbstractForm {
    
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
     * パラメータ-検査期間From
     */
    private String startDate;
    public String getStartDate() 
    {
        return startDate;
    }

    public void setStartDate(String startDate) 
    {
        this.startDate = startDate;
    }
    /*
     * パラメータ-検査期間To
     */
    private String endDate;
    public String getEndDate() 
    {
        return endDate;
    }

    public void setEndDate(String endDate) 
    {
        this.endDate = endDate;
    }
    
    /*
     * 患者情報
     */
    private MedicalKensaEntryDto patientList;
    public MedicalKensaEntryDto getPatientList() 
    {
        return patientList;
    }

    public void setPatientList(MedicalKensaEntryDto patientList) 
    {
        this.patientList = patientList;
    }
    
    /*
     * 検査検索結果一覧
     */
    private ArrayList<HashMap<String, Object>> observationEventList;
    public ArrayList<HashMap<String, Object>> getObservationEventList() 
    {
        return observationEventList;
    }

    public void setObservationEventList(ArrayList<HashMap<String, Object>> observationEventList) 
    {
        this.observationEventList = observationEventList;
    }
}
