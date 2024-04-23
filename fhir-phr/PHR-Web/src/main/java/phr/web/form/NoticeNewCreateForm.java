/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

/**
 *
 * @author KISO-NOTE-005
 */
public class NoticeNewCreateForm extends AbstractForm {
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
     * 登録内容
     * 題名
     */
    private String subject;
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    /**
     * 登録内容
     * 本文
     */
    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
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
