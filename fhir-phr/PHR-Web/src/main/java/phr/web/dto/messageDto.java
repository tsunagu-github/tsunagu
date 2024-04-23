/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

import java.util.List;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;

/**
 *
 * @author kis-note
 */
public class messageDto {
    
    /*
    * 戻るFlg
    * false 新規作成
    * true 戻る
    */
    private boolean returnflg;
    /*
    * 共通
    * PHR ID
    */
    private String phrid;
    
    /*
    * 送信者が医療機関の場合に使用
    * 患者 ID
    */
    private String patientId;

    /*
    * 共通
    * 患者name
    */
    private String name;
    
    /*
    * 共通
    * 送信者flg
    */
    private boolean typeflg;
    
    /*
    * 医療機関が送信者の時に使用
    * 送信病院名称
    */
    private String medicalname;

    /*
    * 医療機関が送信者の時に使用
    * 送信病院Cd
    */
    private String medicalCd;

    /*
    * 医療機関が送信者の時に使用
    * 保険者フラグ
    */
    private boolean insureFlg;
    
    /*
    * 保険者が送信者のときに使用
    * 送信医療機関一覧
    */
    private List<MedicalOrganizationPatientEntity> medicalLsit;
    /*
    * 共通
    * 返信タイトル
    */
    private String subject;
    
    /*
    * 共通
    * 返信内容
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
     * @return the insureFlg
     */
    public boolean isInsureFlg() {
        return insureFlg;
    }

    /**
     * @param insureFlg the insureFlg to set
     */
    public void setInsureFlg(boolean insureFlg) {
        this.insureFlg = insureFlg;
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
     * @return the medicalname
     */
    public String getMedicalname() {
        return medicalname;
    }

    /**
     * @param medicalname the medicalname to set
     */
    public void setMedicalname(String medicalname) {
        this.medicalname = medicalname;
    }

    /**
     * @return the medicalCd
     */
    public String getMedicalCd() {
        return medicalCd;
    }

    /**
     * @param medicalCd the medicalCd to set
     */
    public void setMedicalCd(String medicalCd) {
        this.medicalCd = medicalCd;
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
     * @return the medicalLsit
     */
    public List<MedicalOrganizationPatientEntity> getMedicalLsit() {
        return medicalLsit;
    }

    /**
     * @param medicalLsit the medicalLsit to set
     */
    public void setMedicalLsit(List<MedicalOrganizationPatientEntity> medicalLsit) {
        this.medicalLsit = medicalLsit;
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
     * @return the returnflg
     */
    public boolean isReturnflg() {
        return returnflg;
    }

    /**
     * @param returnflg the returnflg to set
     */
    public void setReturnflg(boolean returnflg) {
        this.returnflg = returnflg;
    }
    
}
