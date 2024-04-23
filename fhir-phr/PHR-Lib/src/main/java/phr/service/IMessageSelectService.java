/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface IMessageSelectService {
    /**
     * メッセージ詳細の取得を行う
     * @param CommunicationId
     * @return
     * @throws Throwable 
     */
    public CommunicationEntity getMessageDetail(String CommunicationId) throws Throwable;
    
    /**
     * 保険者名の検索を行う
     * @param insurerNo
     * @return
     * @throws Throwable 
     */
    public String insurerSerarch(String insurerNo) throws Throwable;
    
    /**
     * PHRIDの検索を行う
     * @param comId
     * @return
     * @throws Throwable 
     */
    public String phrIdSerarch(String comId) throws Throwable;
    
    /**
     * 未読を既読に変更する
     * @param CommunicationId
     * @param insurerNo
     * @return
     * @throws Throwable 
     */
    public boolean changeReadFlg(String CommunicationId,String insurerNo) throws Throwable;
    
    /**
     * 送信した医療機関を検索する
     * @param comId
     * @return
     * @throws Throwable 
     */
    public List<CommunicationReceiverEntity> getMedInfo(String comId) throws Throwable;
    
}
