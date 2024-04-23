/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Date;
import java.util.List;
import phr.datadomain.entity.CommunicationEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface IMessageListService {
    
    /**
     * メッセージ一覧の検索を行う
     * @param phrId
     * @param startDate
     * @param endDate
     * @param readFlg
     * @param insurerNo
     * @return 
     * @throws java.lang.Throwable 
     */
    //List<CommunicationReceiverEntity> messageSearch(String phrId, Date startDate, Date endDate, Boolean readFlg)  throws Throwable;
    List<CommunicationEntity> messageSearch(String phrId, Date startDate, Date endDate, Boolean readFlg, String insurerNo)  throws Throwable;
    
}
