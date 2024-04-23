/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface IMedicalMessageReplayService {
    /**
     * CommunicationIdから送信先患者情報を取得する
     * @param CommunicationId
     * @return
     * @throws Throwable 
     */
    public PatientEntity getPatient(String CommunicationId) throws Throwable;
  
    public boolean submitMessage(CommunicationEntity pentity , List<CommunicationReceiverEntity> rList) throws Throwable;
    
    public String getInsurerPatientInfo(String phrId) throws Throwable;
    /**
     * 患者IDを取得する
     * @param phrid
     * @param MedicalOrganizationCd
     * @return
     * @throws Throwable
     */
    public String getPatientId(String phrid , String MedicalOrganizationCd) throws Throwable;
}
