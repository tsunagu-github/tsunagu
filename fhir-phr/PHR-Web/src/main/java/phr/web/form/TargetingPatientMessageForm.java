/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.service.impl.MessageSelectService.MedInfoResult;

/**
 *
 * @author kis.o-note-003
 */
public class TargetingPatientMessageForm extends AbstractForm {
  
    

    /*
    * 送信先PHRID
    */
    private String phrid;
    
    /*
    * 送信先患者名称
    */
    private String name;
    
    /*
    * 送信者flg
    * true :保険者
    * false ：医療機関
    */
    private boolean typeflg;
    
    /*
    * 送信先病院一覧
    */
    private List<MedicalOrganizationPatientEntity> medicalList;

    /*
    * 送信先病院(初期設定）
    */
    private List<MedicalOrganizationPatientEntity> hospitallist;
    
    /*
    * 保険者送信flg
    */
    private boolean insureflg;

    /*
    * 送信タイトル
    */
    private String subject;
    
    /*
    * 送信本文
    */
    private String bodytext;

    /**
     * @return the phrid
     */
    public String getPhrid() {
        return phrid;
    }

    /**
     * @param phrid the phrid to set
     */
    public void setPhrid(String phrid) {
        this.phrid = phrid;
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
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the bodytext
     */
    public String getBodytext() {
        return bodytext;
    }

    /**
     * @param bodytext the bodytext to set
     */
    public void setBodytext(String bodytext) {
        this.bodytext = bodytext;
    }

    /**
     * @return the insureflg
     */
    public boolean isInsureflg() {
        return insureflg;
    }

    /**
     * @param insureflg the insureflg to set
     */
    public void setInsureflg(boolean insureflg) {
        this.insureflg = insureflg;
    }

    /**
     * @return the typeflg
     */
    public boolean isTypeflg() {
        return typeflg;
    }

    /**
     * @param typeflg the typeflg to set
     */
    public void setTypeflg(boolean typeflg) {
        this.typeflg = typeflg;
    }

    /**
     * @param hospitallist the hospitallist to set
     */
    public void setHospitallist(String hospitallist) {
        this.setHospitallist(hospitallist);
    }

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
