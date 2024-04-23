/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.service.impl.MessageSelectService.MedInfoResult;

/**
 *
 * @author kis.o-note-003
 */
public class MessageSelectForm extends AbstractForm {

    /**
     * パラメータ(communicationId)
     */
    private String communicationId;
    public String getCommunicationId() {
        return communicationId;
    }
    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }
    
    /**
     * PHR ID取得
     */
    private String phrId;
    public String getPhrId() {
        return phrId;
    }
    public void setPhrId(String phrId) {
        this.phrId = phrId;
    }
    
    /**
     * メッセージ詳細表示リスト
     */
    private CommunicationEntity messageDetailList;
    public CommunicationEntity getMessageDetailList() {
        return messageDetailList;
    }
    public void setMessageDetailList(CommunicationEntity messageDetailList) {
        this.messageDetailList = messageDetailList;
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
     * 送信先医療機関リスト
     */
    private List<CommunicationReceiverEntity> medList;
    public List<CommunicationReceiverEntity> getMedList() {
        return medList;
    }
    public void setMedList(List<CommunicationReceiverEntity> medList) {
        this.medList = medList;
    }
}
