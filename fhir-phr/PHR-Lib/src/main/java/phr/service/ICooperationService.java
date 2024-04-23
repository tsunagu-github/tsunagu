/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.service.impl.CooperationService.CooperationResult;

/**
 *
 * @author chiba
 */
public interface ICooperationService {
    CooperationResult getCooperationList( String phrId ) throws Throwable;

    CooperationResult createCooperation( MedicalOrganizationPatientEntity targetEntity ) throws Throwable;
    CooperationResult deleteCooperation( MedicalOrganizationPatientEntity targetEntity ) throws Throwable;
    CooperationResult updateCooperation( MedicalOrganizationPatientEntity targetEntity ) throws Throwable;    
    MedicalOrganizationPatientEntity getCooperation( String phrId,String medicalCd ) throws Throwable;
    /**
     * 対象患者IDのチェック
     * @param medicalCd
     * @param phrId
     * @param patientId
     * @return
     * @throws Throwable 
     */
    boolean isDuplication(String medicalCd, String phrId, String patientId) throws Throwable;
}
