/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.service.impl.MessageSelectService.MedInfoResult;

/**
 *
 * @author kis.o-note-003
 */
public class MedicalMessageReplayForm extends AbstractForm {
  
    
    /*
    * 病院名称
    */
    private String name;
    
    /*
    * 送信先PHRID
    */
    private String phrid;
    
    /*
    * 送信先患者名称
    */
    private String phrname;
    
    /**
     * 返信メッセージ詳細表示リスト
     */
    private CommunicationEntity messageDetail;

    /*
    * 送信タイトル
    */
    private String title;
    
    /*
    * 送信本文
    */
    private String bodytext;
    
    /*
    * 保険者送信フラグ
    */
    private boolean insureflg;
    
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
     * @return the phrname
     */
    public String getPhrname() {
        return phrname;
    }

    /**
     * @param phrname the phrname to set
     */
    public void setPhrname(String phrname) {
        this.phrname = phrname;
    }
            
}
