/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * パスワード変更Form
 * @author KISNOTE011
 */
public class PasswordChangeForm extends AbstractForm {
    /**
     * serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
        
    /**
     * ユーザーID
     */
    @NotEmpty(message = "{passwordChange.userid}：{org.hibernate.validator.constraints.NotEmpty.message}")
    private String userId;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 現在パスワード
     */
    @NotEmpty(message = "{passwordChange.currenntPassword}の{NotEmpty.message}")
    private String currenntPassword;
    public String getCurrenntPassword() {
        return currenntPassword;
    }
    public void setCurrenntPassword(String currenntPassword) {
        this.currenntPassword = currenntPassword;
    }
    
    /**
     * パスワード
     */
    @NotEmpty(message = "{passwordChange.newPassword}の{NotEmpty.message}")
    private String newPassword;
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    /**
     *新しいパスワード(確認)
     */
   @NotEmpty(message = "{passwordChange.confirmPassword}の{NotEmpty.message}")
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
