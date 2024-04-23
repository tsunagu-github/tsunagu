/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.web.dto.DiseaseDto;
import phr.web.dto.DisplayDto;
import phr.web.dto.KensinDto;
import phr.web.dto.ObservationDto;

/**
 *
 * 対象者情報Form
 * @author KISNOTE011
 */
public class TargetingPatientInfoForm extends AbstractForm {
    /**
     * serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    
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
    * 基準日
    */
    private String basedate;

    /*
    * Viewリスト
    */
    private List<InsurerViewDefinitionEntity> viewList;
    
    //選択View
    private int selectView;
    
    /*
    * 検査項目リスト
    */
    private List<ObservationDefinitionInsurerEntity> observationList;

   /*
    * 健診検査項目リスト
    */
    private List<ObservationDefinitionTypeEntity> kensinOList;
    
   /*
    * 健診問診項目リスト
    */
    private List<ObservationDefinitionTypeEntity> kensinQList;

   /*
    * 健診診察項目リスト
    */
    private List<ObservationDefinitionTypeEntity> kensinSList;

    /*
    * 検査項目リスト
    */
    private List<DisplayDto> displayList;

    /*
    * 検査項目表示条件リスト
    */
    private List<DisplayDto> conditionList;

    /*
    * 疾患ラベル  
    */
    private List<DiseaseTypeEntity> diseaseList;
    
    /*
    * 家庭測定名称リスト  
    */
    private List<ObservationDefinitionTypeEntity> kateiList;

     /*
      * 全検査結果項目リスト(自己管理)
      */
      private List<ObservationDefinitionInsurerEntity> allDiseaseListOrderByJlac10;
    
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

    /**
     * @return the basedate
     */
    public String getBasedate() {
        return basedate;
    }

    /**
     * @param basedate the basedate to set
     */
    public void setBasedate(String basedate) {
        this.basedate = basedate;
    }

    /**
     * @return the observationList
     */
    public List<ObservationDefinitionInsurerEntity> getObservationList() {
        return observationList;
    }

    /**
     * @param observationList the observationList to set
     */
    public void setObservationList(List<ObservationDefinitionInsurerEntity> observationList) {
        this.observationList = observationList;
    }

    /**
     * @return the displayList
     */
    public List<DisplayDto> getDisplayList() {
        return displayList;
    }

    /**
     * @param displayList the displayList to set
     */
    public void setDisplayList(List<DisplayDto> displayList) {
        this.displayList = displayList;
    }

    /**
     * @return the diseaseList
     */
    public List<DiseaseTypeEntity> getDiseaseList() {
        return diseaseList;
    }

    /**
     * @param diseaseList the diseaseList to set
     */
    public void setDiseaseList(List<DiseaseTypeEntity> diseaseList) {
        this.diseaseList = diseaseList;
    }

    /**
     * @return the conditionList
     */
    public List<DisplayDto> getConditionList() {
        return conditionList;
    }

    /**
     * @param conditionList the conditionList to set
     */
    public void setConditionList(List<DisplayDto> conditionList) {
        this.conditionList = conditionList;
    }

    /**
     * @return the kensinOList
     */
    public List<ObservationDefinitionTypeEntity> getKensinOList() {
        return kensinOList;
    }

    /**
     * @param kensinOList the kensinOList to set
     */
    public void setKensinOList(List<ObservationDefinitionTypeEntity> kensinOList) {
        this.kensinOList = kensinOList;
    }

    /**
     * @return the kensinQList
     */
    public List<ObservationDefinitionTypeEntity> getKensinQList() {
        return kensinQList;
    }

    /**
     * @param kensinQList the kensinQList to set
     */
    public void setKensinQList(List<ObservationDefinitionTypeEntity> kensinQList) {
        this.kensinQList = kensinQList;
    }

    /**
     * @return the kensinSList
     */
    public List<ObservationDefinitionTypeEntity> getKensinSList() {
        return kensinSList;
    }

    /**
     * @param kensinSList the kensinSList to set
     */
    public void setKensinSList(List<ObservationDefinitionTypeEntity> kensinSList) {
        this.kensinSList = kensinSList;
    }

    /**
     * @return the kateiList
     */
    public List<ObservationDefinitionTypeEntity> getKateiList() {
        return kateiList;
    }

    /**
     * @param kateiList the kateiList to set
     */
    public void setKateiList(List<ObservationDefinitionTypeEntity> kateiList) {
        this.kateiList = kateiList;
    }

    /**
     * @return the viewList
     */
    public List<InsurerViewDefinitionEntity> getViewList() {
        return viewList;
    }

    /**
     * @param viewList the viewList to set
     */
    public void setViewList(List<InsurerViewDefinitionEntity> viewList) {
        this.viewList = viewList;
    }

    /**
     * @return the selectView
     */
    public int getSelectView() {
        return selectView;
    }

    /**
     * @param selectView the selectView to set
     */
    public void setSelectView(int selectView) {
        this.selectView = selectView;
    }

    public List<ObservationDefinitionInsurerEntity> getAllDiseaseListOrderByJlac10() {
        return allDiseaseListOrderByJlac10;
    }

    public void setAllDiseaseListOrderByJlac10(List<ObservationDefinitionInsurerEntity> allListJlac10) {
        this.allDiseaseListOrderByJlac10 = allListJlac10;
    }
    
}
