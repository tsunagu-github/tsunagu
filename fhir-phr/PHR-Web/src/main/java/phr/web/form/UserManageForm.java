/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.sql.Date;
import java.util.List;
import phr.datadomain.entity.AccountEntity;

/**
 *
 * @author kis.o-note-003
 */
public class UserManageForm extends AbstractForm {
    
    /**
     * ログインID
     */
    private String loginId;
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    
    /**
     * 氏名
     */
    private String userName;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * パスワード有効期限
     */
    private Date passDate;
    public Date getPassDate() {
        return passDate;
    }
    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }
    
    /**
     * 今日の日付
     */
    private Date todayDate;
    public Date getTodayDate() {
        return todayDate;
    }
    public void setTodayDate(Date todayDate) {
        this.todayDate = todayDate;
    }
    
    /**
     * 期限切れ表示/非表示フラグ
     */
    private Boolean passFlg;
    public Boolean getPassFlg() {
        return passFlg;
    }
    public void setPassFlg(Boolean passFlg) {
        this.passFlg = passFlg;
    }
    
    /**
     * ステータスフラグ
     */
    private Boolean validFlg;
    public Boolean getValidFlg() {
        return validFlg;
    }
    public void setValidFlg(Boolean validFlg) {
        this.validFlg = validFlg;
    }
    
    /**
     * ログイン中の保険者No
     */
    private String loginNo;
    public String getLoginNo() {
        return loginNo;
    }
    public void setLoginNo(String loginNo) {
        this.loginNo = loginNo;
    }
    
    /**
     * ユーザ検索結果リスト
     */
    private List<AccountEntity> userList;
    public List<AccountEntity> getUserList() {
        return userList;
    }
    public void setUserList(List<AccountEntity> userList) {
        this.userList = userList;
    }
}
