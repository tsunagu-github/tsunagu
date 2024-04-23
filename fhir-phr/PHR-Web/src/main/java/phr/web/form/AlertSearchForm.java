/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import phr.datadomain.entity.ObservationAlertEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * アラート一覧画面フォーム
 * 
 * @author KISO-NOTE-005
 */
public class AlertSearchForm extends AbstractForm {
    
    /*
     * 検索条件(phrID)
     */
    private String phrId;
    /*
     * 検索条件(検査日（FROM）)
     */
    private String startDate;
    /*
     * 検索条件(検査日（TO）)
     */
    private String endDate;
    /*
     * アラート一覧検索結果リスト
     */
    private List<ObservationAlertEntity> alertList;
    /*
     * 患者情報
     */
    private PatientEntity patientInfo;
    /*
     * アラート一覧検索結果リスト(表示用)
     */
    private ArrayList<HashMap<String, Object>> showAlertList;
    
    /*
     * 検索条件(phrID)
     * @return 
     */
    public String getPhrId() {
        return phrId;
    }
    public void setPhrId(String phrId) {
        this.phrId = phrId;
    }
    
    /*
     * 検索条件(検査日（FROM）)
     * @return 
     */
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    /*
     * 検索条件(検査日（TO）)
     * @return 
     */
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    /*
     * アラート一覧検索結果リスト
     * @return 
     */
    public List<ObservationAlertEntity> getAlertList() 
    {
        return alertList;
    }

    public void setAlertList(List<ObservationAlertEntity> alertList) 
    {
        this.alertList = alertList;
    }
    
    /*
     * アラート一覧検索結果リスト
     * @return 
     */
    public PatientEntity getPatientInfo() 
    {
        return patientInfo;
    }

    public void setPatientInfo(PatientEntity patientInfo) 
    {
        this.patientInfo = patientInfo;
    }
    
    /*
     * アラート一覧検索結果リスト(表示用)
     * @return 
     */
    public ArrayList<HashMap<String, Object>> getShowAlertList() 
    {
        return showAlertList;
    }

    public void setShowAlertList(ArrayList<HashMap<String, Object>> showAlertList) 
    {
        this.showAlertList = showAlertList;
    }
}
