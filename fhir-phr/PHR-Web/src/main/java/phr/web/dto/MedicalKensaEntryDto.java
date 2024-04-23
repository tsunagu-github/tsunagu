/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

import java.util.HashMap;
import java.util.List;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;

/**
 *
 * @author kis-note
 */
public class MedicalKensaEntryDto {
    
    /*
    * 共通
    * PHR ID
    */
    private String phrid;
    
    /*
    * 送信者が医療機関の場合に使用
    * 患者 ID
    */
    private String patientId;

    /*
    * 共通
    * 患者name
    */
    private String name;
    
    /*
    * 共通
    * 患者kana
    */
    private String kana;
    
    /*
    * 共通
    * 患者birth
    */
    private String birth;
    
    /*
    * 共通
    * 患者sex
    */
    private String sex;
    
    /*
    * 共通
    * 医療機関CD
    */
    private String medcd;
     
    /*
    * 検査結果登録入力値リスト
    */
    private List<HashMap<Object, Object>> inputList;
    
    /*
    * 検査ID
    */
    private String defid;
    
    /*
    * 結果値
    */
    private String value;
    
    /*
    * 基準値↓
    */
    private Double min;
    
    /*
    * 基準値↑
    */
    private Double max;
    
    /*
    * 検査名
    */
    private String defname;
    
    /*
    * 単位
    */
    private String unitvalue;
    
    /*
    * 列挙ID
    */
    private String enumid;
    
    /*
    * 列挙値
    */
    private String enumvalue;
    
    /*
    * 列挙名
    */
    private String enumname;
    
    /*
    * 列挙リスト
    */
    private List<HashMap> enumList;
    
    /*
     * 検査日
     */
    private String startDate;
    
    /*
     * 検査登録期間
     */
    private String selectPeriod;
    
    /*
    *オブザベーションイベントID
    */
    private String observationEventId;
    
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
     * @return the patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    /**
     * @return the kana
     */
    public String getkana() {
        return kana;
    }
    /**
     * @param kana the kana to set
     */
    public void setKana(String kana) {
        this.kana = kana;
    }
    
    /**
     * @return the birth
     */
    public String getBirth() {
        return birth;
    }
    /**
     * @param birth the birth to set
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }
    
    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }
    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    /**
     * @return the medcd
     */
    public String getMedcd() {
        return medcd;
    }
    /**
     * @param medcd the medcd to set
     */
    public void setMedcd(String medcd) {
        this.medcd = medcd;
    }
    
    /**
     * @return the inputList
     */
    public List<HashMap<Object, Object>> getInputList() {
        return inputList;
    }
    /**
     * @param inputList the inputList to set
     */
    public void setInputList(List<HashMap<Object, Object>> inputList) {
        this.inputList = inputList;
    }
    
    /*
     * 検査一覧初回のみ
     */
    private List<ObservationDefinitionRangeEntity> kensaListFirst;
    public List<ObservationDefinitionRangeEntity> getKensaListFirst() 
    {
        return kensaListFirst;
    }

    public void setKensaListFirst(List<ObservationDefinitionRangeEntity> kensaListFirst) 
    {
        this.kensaListFirst = kensaListFirst;
    }
    
    /*
     * 検査一覧1ヶ月
     */
    private List<ObservationDefinitionRangeEntity> kensaListOne;
    public List<ObservationDefinitionRangeEntity> getKensaListOne() 
    {
        return kensaListOne;
    }

    public void setKensaListOne(List<ObservationDefinitionRangeEntity> kensaListOne) 
    {
        this.kensaListOne = kensaListOne;
    }
    
    /*
     * 検査一覧3ヶ月
     */
    private List<ObservationDefinitionRangeEntity> kensaListThree;
    public List<ObservationDefinitionRangeEntity> getKensaListThree() 
    {
        return kensaListThree;
    }

    public void setKensaListThree(List<ObservationDefinitionRangeEntity> kensaListThree) 
    {
        this.kensaListThree = kensaListThree;
    }
    
    /*
     * 検査一覧年度
     */
    private List<ObservationDefinitionRangeEntity> kensaListYears;
    public List<ObservationDefinitionRangeEntity> getKensaListYears() 
    {
        return kensaListYears;
    }

    public void setKensaListYears(List<ObservationDefinitionRangeEntity> kensaListYears) 
    {
        this.kensaListYears = kensaListYears;
    }
    
    /*
     * 検査一覧なし
     */
    private List<ObservationDefinitionRangeEntity> kensaListNone;
    public List<ObservationDefinitionRangeEntity> getKensaListNone() 
    {
        return kensaListNone;
    }

    public void setKensaListNone(List<ObservationDefinitionRangeEntity> kensaListNone) 
    {
        this.kensaListNone = kensaListNone;
    }
    
    /**
     * @return the defid
     */
    public String getDefid() {
        return defid;
    }
    /**
     * @param defid the defid to set
     */
    public void setDefid(String defid) {
        this.defid = defid;
    }
    
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * @return the min
     */
    public Double getMin() {
        return min;
    }
    /**
     * @param min the min to set
     */
    public void setMin(Double min) {
        this.min = min;
    }
    
    /**
     * @return the max
     */
    public Double getMax() {
        return max;
    }
    /**
     * @param max the max to set
     */
    public void setMax(Double max) {
        this.max = max;
    }
 
    /**
     * 検査名称を取得する
     *
     * @return
     */
    public String getDefname() {
        return this.defname;
    }
    /**
     * 検査名称を設定する
     *
     * @param defname
     */
    public void setDefname(String defname) {
        this.defname = defname;
    }
    
    /**
     * 単位を取得する
     *
     * @return
     */
    public String getUnitvalue() {
        return this.unitvalue;
    }
    /**
     * 検査名称を設定する
     *
     * @param unitvalue
     */
    public void setUnitvalue(String unitvalue) {
        this.unitvalue = unitvalue;
    }
    
    /**
     * 列挙IDを取得する
     *
     * @return
     */
    public String getEnumid() {
        return this.enumid;
    }
    /**
     * 列挙IDを設定する
     *
     * @param enumid
     */
    public void setEnumid(String enumid) {
        this.enumid = enumid;
    }

    /**
     * 列挙名称を取得する
     *
     * @return
     */
    public String getEnumname() {
        return this.enumname;
    }
    /**
     * 列挙名称を設定する
     *
     * @param enumname
     */
    public void setEnumname(String enumname) {
        this.enumname = enumname;
    }

    /**
     * 列挙値を取得する
     *
     * @return
     */
    public String getEnumvalue() {
        return this.enumvalue;
    }
    /**
     * 列挙値を設定する
     *
     * @param enumvalue
     */
    public void setEnumvalue(String enumvalue) {
        this.enumvalue = enumvalue;
    }
    
    /**
     * 列挙リストを取得する
     *
     * @return
     */
    public List<HashMap> getEnumList() {
        return this.enumList;
    }
    /**
     * 列挙リストを設定する
     *
     * @param value
     */
    public void setEnumList(List<HashMap> value) {
        this.enumList = value;
    }
    
    /**
     * 検査日を取得する
     *
     * @return
     */
    public String getStartDate() 
    {
        return startDate;
    }
    /**
     * 検査日を設定する
     *
     * @param startDate
     */
    public void setStartDate(String startDate) 
    {
        this.startDate = startDate;
    }
    
    /**
     * 検査登録期間を取得する
     *
     * @return
     */
    public String getSelectPeriod() 
    {
        return selectPeriod;
    }
    /**
     * 検査登録期間を設定する
     *
     * @param selectPeriod
     */
    public void setSelectPeriod(String selectPeriod) 
    {
        this.selectPeriod = selectPeriod;
    }
    /**
     * オブザベーションイベントIDを取得する
     *
     * @return
     */
    public String getObservationEventId() 
    {
        return observationEventId;
    }
    /**
     * オブザベーションイベントIDを設定する
     *
     * @param observationEventId
     */
    public void setObservationEventId(String observationEventId) 
    {
        this.observationEventId = observationEventId;
    }    
}
