/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;

/**
 *
 * @author kis.o-note-003
 */
public class MessageReplayForm extends AbstractForm {
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
     * メッセージ詳細表示リスト
     */
    private CommunicationEntity messageDetail;
    public CommunicationEntity getMessageDetail() {
        return messageDetail;
    }
    public void setMessageDetail(CommunicationEntity messageDetail) {
        this.messageDetail = messageDetail;
    }
    
    /*
    * 送信先病院一覧
    */
    private List<MedicalOrganizationPatientEntity> medicalList;
    /**
     * @return the medicalList
     */
    public List<MedicalOrganizationPatientEntity> getMedicalList() {
        return medicalList;
    }

    /**
     * @param medicalList the medicalList to set
     */
    public void setMedicalList(List<MedicalOrganizationPatientEntity> medicalList) {
        this.medicalList = medicalList;
    }

    /**
     * 送信先PHRID
     */
    private String phrid;
    public String getPhrid() {
        return phrid;
    }
    public void setPhrid(String phrid) {
        this.phrid = phrid;
    }
    
    /**
    * 送信先患者名称
    */
    private String phrname;
    public String getPhrname() {
        return phrname;
    }
    public void setPhrname(String phrname) {
        this.phrname = phrname;
    }
    
    /**
     * 送信先医療機関CD
     */
    private String medicalorganizationcd;
    public String getMedicalorganizationcd() {
        return medicalorganizationcd;
    }
    public void setMedicalorganizationcd(String medicalorganizationcd) {
        this.medicalorganizationcd = medicalorganizationcd;
    }
    
    /**
    * 送信先医療機関名称
    */
    private String medname;
    public String getMedname() {
        return medname;
    }
    public void setMedname(String medname) {
        this.medname = medname;
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
     * 送信タイトル
     */
    private String subject;
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    /**
     * 送信本文
     */
    private String bodytext;
    public String getBodytext() {
        return bodytext;
    }
    public void setBodytext(String bodytext) {
        this.bodytext = bodytext;
    }
    
    /**
     * 保険者名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /*
    * 送信タイトル
    */
    private String title;
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /*
    * 送信先病院(初期設定）
    */
    private List<MedicalOrganizationPatientEntity> hospitallist;
    /**
     * @param hospitallist the hospitallist to set
     */
    public void setHospitallist(List<MedicalOrganizationPatientEntity> hospitallist) {
        this.hospitallist = hospitallist;
    }

    /**
     * @return the hospitallist
     */
    public List<MedicalOrganizationPatientEntity> getHospitallist() {
        return hospitallist;
    }
}
