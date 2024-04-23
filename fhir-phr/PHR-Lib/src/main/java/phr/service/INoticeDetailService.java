/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.CommunicationEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public interface INoticeDetailService {
    
    /**
     * おしらせ情報の詳細検索を行う
     * @param communicationID
     * @return 
     * @throws java.lang.Throwable 
     */
    CommunicationEntity noticeDetailSearch(String communicationID)  throws Throwable;
    
    /**
     * 保険者名の検索を行う
     * @param insurerNo
     * @return 
     * @throws java.lang.Throwable 
     */
    String insurerSerarch(String insurerNo)  throws Throwable;
    
    /**
     * 選択したおしらせの削除を行う 
     * @param entity
     * @throws java.lang.Throwable 
     */
    void noticeDelete(String communicationId)  throws Throwable;
    
}
