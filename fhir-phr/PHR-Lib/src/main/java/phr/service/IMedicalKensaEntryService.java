/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public interface IMedicalKensaEntryService {
    /**
     * 患者情報の検索を行う
     * @param phrId
     * @param startDate
     * @param endDate
     * @return 
     * @throws java.lang.Throwable 
     */
    List<PatientEntity> patientSearch(String patientId, String familyKame, String givenKana, String birthDate, String sexCd, String MedicalOrganizationCd) throws Throwable;
    
    /**
     * 患者IDの存在確認を行う
     * @param patientId
     * @param MedicalOrganizationCd
     * @return 
     * @throws java.lang.Throwable 
     */
    MedicalOrganizationPatientEntity idSearch(String patientId, String MedicalOrganizationCd) throws Throwable;
    
}
