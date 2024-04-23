/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.datadomain.entity.InsurerEntity;

/**
 *
 * @author kis.o-note-003
 */
public class UserManageEditForm extends AbstractForm {
    
    /**
     * アカウントID
     */
    private String accountId;

    /**
     * ログインID
     */
    private String loginId;

    /**
     * 氏名
     */
    private String userName;

    /**
     * 所属保険者コード
     */
    private String insurerNo;

    /**
     * 所属保険者名称
     */
    private String insurerName;

    /**
     * アカウント種別
     */
    private int accountType;
    
    /**
     * ワンタイムパスワード発行権限
     */
    private boolean oneTimePassAuth;    

    /**
     * パスワード変更フラグ
     */
    private boolean changeflg;

    /**
     * パスワード
     */
    private String password;

    /**
     * パスワード(確認)
     */
    private String cPassword;

    /**
     * ステータス
     */
    private Boolean invalidFlg;

    /**
     * 新規登録or修正フラグ（修正 = true）
     */
    private Boolean editFlg;

    /**
     * 登録完了フラグ
     */
    private Boolean registFlg;

    /**
     * 保険者リスト
     */
    private List<InsurerEntity> insurerList;
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getInsurerNo() {
        return insurerNo;
    }
    public void setInsurerNo(String insurerNo) {
        this.insurerNo = insurerNo;
    }
    
    public int getAccountType() {
        return accountType;
    }
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getcPassword() {
        return cPassword;
    }
    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }
    
    public Boolean getInvalidFlg() {
        return invalidFlg;
    }
    public void setInvalidFlg(Boolean invalidFlg) {
        this.invalidFlg = invalidFlg;
    }
    
    public Boolean getEditFlg() {
        return editFlg;
    }
    public void setEditFlg(Boolean editFlg) {
        this.editFlg = editFlg;
    }
    
    public Boolean getRegistFlg() {
        return registFlg;
    }
    public void setRegistFlg(Boolean registFlg) {
        this.registFlg = registFlg;
    }
    
    public List<InsurerEntity> getInsurerList() {
        return insurerList;
    }
    public void setInsurerList(List<InsurerEntity> insurerList) {
        this.insurerList = insurerList;
    }

    /**
     * @return the changeflg
     */
    public boolean isChangeflg() {
        return changeflg;
    }

    /**
     * @param changeflg the changeflg to set
     */
    public void setChangeflg(boolean changeflg) {
        this.changeflg = changeflg;
    }

    /**
     * @return the insurerName
     */
    public String getInsurerName() {
        return insurerName;
    }

    /**
     * @param insurerName the insurerName to set
     */
    public void setInsurerName(String insurerName) {
        this.insurerName = insurerName;
    }
    
        /**
     * @return the oneTimePassAuth
     */
    public boolean getOneTimePassAuth() {
        return oneTimePassAuth;
    }

    /**
     * @param oneTimePassAuth the oneTimePassAuth to set
     */
    public void setOneTimePassAuth(boolean oneTimePassAuth) {
        this.oneTimePassAuth = oneTimePassAuth;
    }
}
