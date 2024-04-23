/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.HashMap;
import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerPatientEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public interface INoticeNewCreateService {
    
    /**
     * おしらせ情報の新規登録を行う
     * @param communicationID
     * @return 
     * @throws java.lang.Throwable 
     */
    CommunicationEntity noticeNewInsert(String communicationID)  throws Throwable;
    
    /**
     * 保険者名の検索を行う
     * @param insurerNo
     * @return 
     * @throws java.lang.Throwable 
     */
    String insurerSerarch(String insurerNo)  throws Throwable;
    
    /**
     * CommunicationIdのmaxの検索を行う
     * @return 
     * @throws java.lang.Throwable 
     */
    String maxCommunicationIdSearch()  throws Throwable;
    
    /**
     * おしらせ情報(Communication)の登録を行う 
     * @param entityCommunication
     * @throws java.lang.Throwable 
     */
    void insertCommunication(CommunicationEntity entityCommunication) throws Throwable;
    
        /**
     * おしらせ情報(CommunicationReceiver)の登録を行う 
     * @param entityComRecList
     * @throws java.lang.Throwable 
     */
    void insertCommunicationReceiver(List<CommunicationReceiverEntity> entityComRecList) throws Throwable;
    
    /**
     * 保険者患者の検索を行う
     * @param communicationID
     * @return 
     * @throws java.lang.Throwable 
     */
    List<InsurerPatientEntity> serchPhrId(String communicationID)  throws Throwable;
}
