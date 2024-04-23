/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface IMessageReplyService {
    /**
     * CommunicationIdから送信先患者情報を取得する
     * @param CommunicationId
     * @return
     * @throws Throwable 
     */
    public PatientEntity getPatient(String CommunicationId) throws Throwable;
  
    public boolean submitMessage(CommunicationEntity pentity , List<CommunicationReceiverEntity> rList) throws Throwable;
    
    public String getInsurerPatientInfo(String phrId) throws Throwable;
    
    public List<MedicalOrganizationPatientEntity> getMedicalList(String phrId) throws Throwable;
    
    /**
     * 保険者名の取得を行う
     * @param insurerNo
     * @return entity
     * @throws java.lang.Throwable 
     */
    public String insurerSerarch(String insurerNo) throws Throwable;
}
