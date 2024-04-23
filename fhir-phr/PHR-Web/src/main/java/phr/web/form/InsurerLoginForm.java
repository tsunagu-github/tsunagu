/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * 保険者ログインForm
 * @author KISNOTE011
 */
public class InsurerLoginForm extends AbstractForm {
    /**
     * serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    
    /**
     * ログインID
     */
    @NotEmpty(message = "{login.loginId}の{NotEmpty.message}")
    private String userId;
	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * パスワード
     */
    @NotEmpty(message = "{login.password}の{NotEmpty.message}")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * ログインサイト
     */
    private String site;
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
}
