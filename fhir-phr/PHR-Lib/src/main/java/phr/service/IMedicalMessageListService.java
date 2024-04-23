/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Date;
import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface IMedicalMessageListService {
    
    /**
     * メッセージ一覧の検索を行う
     * @param phrId
     * @param startDate
     * @param endDate
     * @param readFlg
     * @return 
     * @throws java.lang.Throwable 
     */
    //List<CommunicationReceiverEntity> messageSearch(String phrId, Date startDate, Date endDate, Boolean readFlg)  throws Throwable;
      public List<CommunicationEntity> messageSearch(String patient,String medicalCd, Date startDate, Date endDate, Boolean readFlg) throws Throwable;
    
}
