/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author kis.o-note-003
 */
public class UserManageDto {
    
    /* アカウントID */
    private String accountId = null;
    /* ログインID */
    private String loginId = null;
    /* パスワード */
    private String password = null;
    /* アカウント名称 */
    private String name = null;
    /* アカウント種別 */
    private int accoutTypeCd = 0;
    /* 保険者番号 */
    private String insurerNo = null;
    /* 保険者名称 */
    private String insurerName = null;
    /* 初期パスワード有無 */
    private boolean initPassword = false;
    /* 無効フラグ */
    private boolean invalid = false;
    /* パスワード有効期限 */
    private Date passwordExpirationDate = null;
    /* 最終ログイン日時 */
    private Timestamp lastLoginDateTime = null;
    /* 作成アカウントID */
    private String createAccoutId = null;
    /* 作成日時 */
    private Timestamp createDateTime = null;
    /* 最終更新アカウントID */
    private String updateAccoutId = null;
    /* 最終更新日時 */
    private Timestamp updateDateTime = null;
    
    /* -------------------------------------------------------------------------------------- */

    /**
     * アカウントIDを取得する
     *
     * @return
     */
    public String getAccountId() {
        return this.accountId;
    }
    /**
     * アカウントIDを設定する
     *
     * @param value
     */
    public void setAccountId(String value) {
        this.accountId = value;
    }

    /**
     * ログインIDを取得する
     *
     * @return
     */
    public String getLoginId() {
        return this.loginId;
    }
    /**
     * ログインIDを設定する
     *
     * @param value
     */
    public void setLoginId(String value) {
        this.loginId = value;
    }

    /**
     * パスワードを取得する
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * パスワードを設定する
     *
     * @param value
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * アカウント名称を取得する
     *
     * @return
     */
    public String getName() {
        return this.name;
    }
    /**
     * アカウント名称を設定する
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * アカウント種別を取得する
     *
     * @return
     */
    public int getAccoutTypeCd() {
        return this.accoutTypeCd;
    }
    /**
     * アカウント種別を設定する
     *
     * @param value
     */
    public void setAccoutTypeCd(int value) {
        this.accoutTypeCd = value;
    }

    /**
     * 保険者番号を取得する
     *
     * @return
     */
    public String getInsurerNo() {
        return this.insurerNo;
    }
    /**
     * 保険者番号を設定する
     *
     * @param value
     */
    public void setInsurerNo(String value) {
        this.insurerNo = value;
    }

    /**
     * 初期パスワード有無を取得する
     *
     * @return
     */
    public boolean isInitPassword() {
        return this.initPassword;
    }
    /**
     * 初期パスワード有無を設定する
     *
     * @param value
     */
    public void setInitPassword(boolean value) {
        this.initPassword = value;
    }

    /**
     * 無効フラグを取得する
     *
     * @return
     */
    public boolean isInvalid() {
        return this.invalid;
    }
    /**
     * 無効フラグを設定する
     *
     * @param value
     */
    public void setInvalid(boolean value) {
        this.invalid = value;
    }

    /**
     * パスワード有効期限を取得する
     *
     * @return
     */
    public Date getPasswordExpirationDate() {
        return this.passwordExpirationDate;
    }
    /**
     * パスワード有効期限を設定する
     *
     * @param value
     */
    public void setPasswordExpirationDate(Date value) {
        this.passwordExpirationDate = value;
    }

    /**
     * 最終ログイン日時を取得する
     *
     * @return
     */
    public Timestamp getLastLoginDateTime() {
        return this.lastLoginDateTime;
    }
    /**
     * 最終ログイン日時を設定する
     *
     * @param value
     */
    public void setLastLoginDateTime(Timestamp value) {
        this.lastLoginDateTime = value;
    }

    /**
     * 作成アカウントIDを取得する
     *
     * @return
     */
    public String getCreateAccoutId() {
        return this.createAccoutId;
    }
    /**
     * 作成アカウントIDを設定する
     *
     * @param value
     */
    public void setCreateAccoutId(String value) {
        this.createAccoutId = value;
    }

    /**
     * 作成日時を取得する
     *
     * @return
     */
    public Timestamp getCreateDateTime() {
        return this.createDateTime;
    }
    /**
     * 作成日時を設定する
     *
     * @param value
     */
    public void setCreateDateTime(Timestamp value) {
        this.createDateTime = value;
    }

    /**
     * 最終更新アカウントIDを取得する
     *
     * @return
     */
    public String getUpdateAccoutId() {
        return this.updateAccoutId;
    }
    /**
     * 最終更新アカウントIDを設定する
     *
     * @param value
     */
    public void setUpdateAccoutId(String value) {
        this.updateAccoutId = value;
    }

    /**
     * 最終更新日時を取得する
     *
     * @return
     */
    public Timestamp getUpdateDateTime() {
        return this.updateDateTime;
    }
    /**
     * 最終更新日時を設定する
     *
     * @param value
     */
    public void setUpdateDateTime(Timestamp value) {
        this.updateDateTime = value;
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

}
