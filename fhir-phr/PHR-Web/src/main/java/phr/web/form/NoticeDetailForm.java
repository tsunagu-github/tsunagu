/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import phr.datadomain.entity.CommunicationEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public class NoticeDetailForm extends AbstractForm {
    /**
     * パラメーター(communicationId)
     */
//    @NotEmpty(message = "{login.password}の{NotEmpty.message}")
    private String communicationId;
    public String getCommunicationId() {
        return communicationId;
    }
    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }
    
    /**
     * お知らせ検索結果
     */
    private CommunicationEntity noticeDetailList;
    public CommunicationEntity getNoticeDetailList() 
    {
    return noticeDetailList;
    }

    public void setNoticeDetailList(CommunicationEntity noticeDetailList) 
    {
    this.noticeDetailList = noticeDetailList;
    }
    
    /**
     * アカウント名
     */
    private String accountName;
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    /**
     * 保険者名
     */
    private String insurerName;
    public String getInsurerName() {
        return insurerName;
    }
    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }
    
    /**
    *　完了フラグ
    */
    private boolean status;
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
