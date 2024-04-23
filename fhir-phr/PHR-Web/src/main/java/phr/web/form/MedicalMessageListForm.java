/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.Date;
import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;


/**
 *メッセージ一覧画面Form
 * @author kis.o-note-003
 */
public class MedicalMessageListForm extends AbstractForm {
    
    /**
     * 検索画面のpatientID
     */
    private String patientId;
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    /**
     * 検索画面の対象期間(From)
     */
    private String startDate;
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    /**
     * 検索画面の対象期間(To)
     */
    private String endDate;
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    /**
     * 検索画面の未読チェックボックス
     */
    private Boolean readFlg;
	public Boolean getReadFlg() {
        return readFlg;
    }

    public void setReadFlg(Boolean readFlg) {
        this.readFlg = readFlg;
    }
    
    /**
     * メッセージ一覧検索結果リスト(send)
     */
    private List<CommunicationEntity> messageList;
    public List<CommunicationEntity> getMessageList() 
    {
    return messageList;
    }

    public void setMessageList(List<CommunicationEntity> messageList) 
    {
    this.messageList = messageList;
    }




    
}
