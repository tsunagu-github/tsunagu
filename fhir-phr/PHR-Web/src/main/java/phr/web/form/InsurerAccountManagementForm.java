/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.datadomain.entity.AccountEntity;

/**
 *
 * @author KISNOTE011
 */
public class InsurerAccountManagementForm extends AbstractForm {
    /**
     * serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * ユーザ管理画面一覧用リスト
     */
    private List<AccountEntity> accountList;
    public List<AccountEntity> getAccountList() {
            return accountList;
    }
    public void setAccountList(List<AccountEntity> accountList) {
            this.accountList = accountList;
    }

    /** 検索_ユーザID */
    private String userIdStr;
    public String getUserIdStr() {
        return userIdStr;
    }
    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    /** 検索_ユーザ名 */
    private String userNameStr;
    public String getUserNameStr() {
        return userNameStr;
    }
    public void setUserNameStr(String userNameStr) {
        this.userNameStr = userNameStr;
    }
    
    /** 検索_無効ユーザ表示 */
    private boolean showInvalidUser;
    public boolean getShowInvalidUser() {
        return showInvalidUser;
    }
    public void setShowInvalidUser(boolean showInvalidUser) {
        this.showInvalidUser = showInvalidUser;
    }

    /** 選択したリストのINDEX */
    private String index;
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }

    /** 所属施設(現状未使用、後で必要そうなので) */
    private String facilityCode;
    public String getFacilityCode(){
        return facilityCode;
    }
    public void setFacilityCode(String facilityCode){
        this.facilityCode = facilityCode;
    }
    private String facilityName;
    public String getFacilityName(){
        return facilityName;
    }
    public void setFacilityName(String facilityName){
        this.facilityName = facilityName;
    }
}
