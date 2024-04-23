/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public class AlertPatientInfoForm extends AbstractForm {
    /*
    * 対象患者のPHR　ID
    */
    private String phrid;
    
    /*
    * 対象患者の氏名
    */
    private String name;
    
    /*
    * 対象患者のカナ氏名
    */
    private String kananame;
    
    /*
    * 対象患者の生年月日 
    */
    private String birthday;
    
    /*
    * 対象患者の性別
    */
    private String sex;
    
    /*
    * 対象患者の郵便番号
    */
    private String zipcode;

    /*
    * 対象患者の住所
    */
    private String address;
    
    /*
    * 対象患者の電話番号
    */
    private String telnumber;
    /*
     * アラート患者情報
     */
    private PatientEntity alertPatientInfo;
    
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
     * @return the kananame
     */
    public String getKananame() {
        return kananame;
    }

    /**
     * @param kananame the kananame to set
     */
    public void setKananame(String kananame) {
        this.kananame = kananame;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sexCd to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the adress
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param adress the adress to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the telnumber
     */
    public String getTelnumber() {
        return telnumber;
    }

    /**
     * @param telnumber the telnumber to set
     */
    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    /**
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode the zipcode to set
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public PatientEntity getAlertPatientInfo() {
        return alertPatientInfo;
    }
    public void setAlertPatientInfo(PatientEntity alertPatientInfo) {
        this.alertPatientInfo = alertPatientInfo;
    }
}
