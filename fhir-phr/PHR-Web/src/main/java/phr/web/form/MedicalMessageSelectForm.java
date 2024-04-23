/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import phr.datadomain.entity.CommunicationEntity;
import phr.service.impl.MessageSelectService.MedInfoResult;

/**
 *
 * @author kis.o-note-003
 */
public class MedicalMessageSelectForm extends AbstractForm {

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
    
    /*
    * 病院名称
    */
    private String name;

    /*
    * 患者ID
    */
    private String patientId;
    
    /**
     * 患者氏名
     */
    private String patientName;
    
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
    private CommunicationEntity messageDetail;
    public CommunicationEntity getMessageDetail() {
        return messageDetail;
    }
    public void setMessageDetail(CommunicationEntity messageDetail) {
        this.messageDetail = messageDetail;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return the patientName
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * @param patientName the patientName to set
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
        
    
}
