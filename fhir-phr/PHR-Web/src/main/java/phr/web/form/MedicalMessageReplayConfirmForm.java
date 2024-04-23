/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

/**
 *
 * @author kis.o-note-003
 */
public class MedicalMessageReplayConfirmForm extends AbstractForm {
  
    
    /*
    * 送信タイトル
    */
    private String title;
    
    /*
    * 送信本文
    */
    private String bodytext;
    
    /*
    *　完了フラグ
    */
    private boolean status;
    

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
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the insureflg to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
            
}
