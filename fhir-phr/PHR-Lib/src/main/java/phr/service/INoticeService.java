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
 * @author KISO-NOTE-005
 */
public interface INoticeService {
    
    /**
     * おしらせの検索を行う
     * @param startDate
     * @param endDate
     * @param insurerNo
     * @return 
     * @throws java.lang.Throwable 
     */
    List<CommunicationEntity> noticeSearch(Date startDate, Date endDate, String insurerNo)  throws Throwable;
}
