/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.web.dto.MedicalKensaEntryDto;

/**
 *
 * @author KISO-NOTE-005
 */
public class MedicalKensaEntryConfirmForm extends AbstractForm {
    
    /*
     * 検査日
     */
    private String startDate;
    public String getStartDate() 
    {
        return startDate;
    }

    public void setStartDate(String startDate) 
    {
        this.startDate = startDate;
    }
    
    /*
     * 検査登録期間
     */
    private String selectPeriod;
    public String getSelectPeriod() 
    {
        return selectPeriod;
    }

    public void setSelectPeriod(String selectPeriod) 
    {
        this.selectPeriod = selectPeriod;
    }
    
    /*
     * 検査コード
     */
    private String[] defid;
    public String[] getDefid() 
    {
        return defid;
    }

    public void setDefid(String[] defid) 
    {
        this.defid = defid;
    }
    
    /*
     * 結果値
     */
    private String[] value;
    public String[] getValue() 
    {
        return value;
    }

    public void setValue(String[] value) 
    {
        this.value = value;
    }
    
    /*
     * 基準値下
     */
    private String[] minRefValue;
    public String[] getMinRefValue() 
    {
        return minRefValue;
    }

    public void setMinRefValue(String[] minRefValue) 
    {
        this.minRefValue = minRefValue;
    }
    
    /*
     * 基準値上
     */
    private String[] maxRefValue;
    public String[] getMaxRefValue() 
    {
        return maxRefValue;
    }

    public void setMaxRefValue(String[] maxRefValue) 
    {
        this.maxRefValue = maxRefValue;
    }
    
    /*
     * 患者情報
     */
    private MedicalKensaEntryDto patientList;
    public MedicalKensaEntryDto getPatientList() 
    {
        return patientList;
    }

    public void setPatientList(MedicalKensaEntryDto patientList) 
    {
        this.patientList = patientList;
    }
    
    /*
     * 検査検索結果一覧
     */
    private ArrayList<HashMap<String, Object>> observationEventList;
    public ArrayList<HashMap<String, Object>> getObservationEventList() 
    {
        return observationEventList;
    }

    public void setObservationEventList(ArrayList<HashMap<String, Object>> observationEventList) 
    {
        this.observationEventList = observationEventList;
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
    
    /*
    * 検査結果登録入力値リスト
    */
    private List<HashMap<Object, Object>> inputList;
    public List<HashMap<Object, Object>> getInputList() {
        return inputList;
    }
    
    public void setInputList(List<HashMap<Object, Object>> inputList) {
        this.inputList = inputList;
    }
}