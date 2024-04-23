/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;
import  java.util.ArrayList;

public class MedicalCreateEditForm extends AbstractForm {
    /**
     * serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    
    /**
     *  医療機関CD
     */
    private String medicalCd;

    /**
     * 医療機関名称
     */
    private String medicalName;

    /**
     * 郵便番号
     */
    private String zipCode;

    /**
     * 所在地
     */
    private String address;

    /**
     * 電話番号
     */
    private String telNo;

    /**
     * パスワード
     */
    private String password;

    /**
     * パスワード（確認）
     */
    private String initPassword;

    /**
     * パスワード（確認）
     */
    private boolean changeFlg;

    /**
     * 新規登録or修正フラグ（1：修正）
     */
    private boolean editFlg;

    
    public String getZipCode(){
        return  zipCode;
    }
    
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
    
    public String getAddress(){
        return  address;
    }
    
    public void setAddress(String address){
        this.address = address;
    }

    public String getTelNo(){
        return  telNo;
    }
    
    public void setTelNo(String telNo){
        this.telNo = telNo;
    }

    public String getPassword(){
        return  password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getInitPassword(){
        return  initPassword;
    }
    
    public void setInitPassword(String initPassword){
        this.initPassword = initPassword;
    }
    
    public boolean isEditFlg(){
        return  editFlg;
    }
    
    public void setEditFlg(boolean editFlg){
        this.editFlg = editFlg;
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
     * @return the medicalName
     */
    public String getMedicalName() {
        return medicalName;
    }

    /**
     * @param medicalName the medicalName to set
     */
    public void setMedicalName(String medicalName) {
        this.medicalName = medicalName;
    }

    /**
     * @return the changeFlg
     */
    public boolean isChangeFlg() {
        return changeFlg;
    }

    /**
     * @param changeFlg the changeFlg to set
     */
    public void setChangeFlg(boolean changeFlg) {
        this.changeFlg = changeFlg;
    }
}
