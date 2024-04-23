/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Date;
import java.util.List;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public interface IMedicalKensaPatientService {
    /**
     * 患者情報の検索を行う
     * @param patientId
     * @param MedicalOrganizationCd
     * @return 
     * @throws java.lang.Throwable 
     */
    PatientEntity patientSearch(String patientId, String MedicalOrganizationCd) throws Throwable;
    
    /**
     * 対象患者の検査結果検索を行う
     * @param phrId
     * @param startDate
     * @param endDate
     * @param medicalOrganizationCd
     * @return 
     * @throws java.lang.Throwable 
     */
    List<ObservationEventEntity> observationEventSearch(String phrId, Date startDate, Date endDate, String medicalOrganizationCd) throws Throwable;
    
}
