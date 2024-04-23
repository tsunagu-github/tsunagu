/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import phr.datadomain.entity.ObservationAlertEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.impl.AlertSearchService;

/**
 *
 * @author KISO-NOTE-005
 */
public interface IAlertSearchService {
    
    /**
     * アラート一覧の検索を行う
     * @param phrId
     * @param startDate
     * @param endDate
     * @return 
     * @throws java.lang.Throwable 
     */
    public List<ObservationAlertEntity> alertSearch(String insurerNo, String phrId, Timestamp startDate, Timestamp endDate)  throws Throwable;
    
    /**
     * 患者の検索を行う
     * @param phrId
     * @return 
     * @throws java.lang.Throwable 
     */
    PatientEntity patientInfoSearch(String phrId)  throws Throwable;
    
    /**
     * ワンタイムパスワード検索
     * @param Password　検索対象パスワード
     * @param insurerId 検索対象保険者番号（条件としない場合null）
     * @param MedicalOrgCd 検索対象医療機関CD（条件としない場合null）
     * @return 
     * @throws Throwable 
     */
    public AlertSearchService.OneTimePasswordResult searchPassword(String Password,String insurerId,String MedicalOrgCd) throws Throwable;
    
}
