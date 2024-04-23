/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import jp.kis_inc.csvconverter.src.dto.ConvertDto;
import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.dto.ConvertPhrIdDto;
import phr.dto.ObservationEntryDto;
//import phr.datadomain.entity.ObservationDefinitionRangeEntity;

/**
 *
 * @author kis.o-note-002
 */
public interface ISpecificMedicalCheakUpFormService {
    
    /**
     * <pre>特定健診結果登録処理
     * @param list
     * @param resultdto
     * @return
     * @throws Throwable 
     */
    int InsertObservationAndObservationEvent (List<ResultObservationDto> list , ConvertPhrIdDto headerPhrId) throws Throwable;

    /**
     * <pre>項目ID取得処理</pre>
     * @param code
     * @return
     * @throws Throwable 
     */
    public String getObservationDefinitionId(String insurerNo,String year,String code) throws Throwable;

    /*** 患者情報の有無を取得します。
     * @param phrid     取得対象のPHRID
     * @throws Throwable
    */
    public PatientEntity getPatientInfo(String phrid) throws Throwable;

    public Map<String, String> getjlacmap(String insurerNo)throws Throwable;
    
    /*
    * 過去に登録済みかの確認
    */
    public List<ObservationEventEntity> confirmCheckup(String phrid , String insurerNo, String date) throws Throwable;
    
    /*
    * 登録ずみの特定健診結果を削除する
    */
    public boolean deleateCheckup(List<ObservationEventEntity> eventList) throws Throwable;
    
    /**
     * 検査結果の登録・削除
     * @param dtoList
     * @param eventList
     * @return 
     * @throws java.lang.Throwable 
     */
    boolean deleteInsertObservationList(List<ObservationEntryDto> dtoList, List<ObservationEventEntity> eventList) throws Throwable;
    
    /**
     * ObservationEventIdを取得（ObservationEvent）
     * @param phr_id
     * @param date
     * @return
     * @throws Throwable 
     */
    public List<String> getObservationEventId(String phr_id, String date) throws Throwable;
    
    /**
     * observationEventIdでレコードを削除（Observation）
     * @param observationEventId
     * @return 
     * @throws Throwable 
     */
    public void deleteObservationById(String observationEventId) throws Throwable;
}
