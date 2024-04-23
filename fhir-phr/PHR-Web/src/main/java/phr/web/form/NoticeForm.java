/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.Date;
import java.util.List;
import phr.datadomain.entity.CommunicationEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public class NoticeForm extends AbstractForm {
    /**
     * 検索条件(受信日（FROM）)
     */
//    @NotEmpty(message = "{login.password}の{NotEmpty.message}")
    private String startDate;
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    /**
     * 検索条件(受信日（TO）)
     */
    private String endDate;
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    /**
     * おしらせ検索結果リスト
     */
    private List<CommunicationEntity> noticeList;
    public List<CommunicationEntity> getNoticeList() 
    {
    return noticeList;
    }

    public void setNoticeList(List<CommunicationEntity> noticeList) 
    {
    this.noticeList = noticeList;
    }
    

}
