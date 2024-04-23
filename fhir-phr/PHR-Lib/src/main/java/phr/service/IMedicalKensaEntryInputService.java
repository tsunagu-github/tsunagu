/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.datadomain.entity.ObservationEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public interface IMedicalKensaEntryInputService {
    /**
     * 患者情報の検索を行う
     * @param insurerNo
     * @param Year
     * @param string 
     * @return 
     * @throws java.lang.Throwable 
     */
    List<ObservationDefinitionRangeEntity> observationReminderSearch(String insurerNo, int Year, String sexCd) throws Throwable;
    
    /**
     * PHRIDから保険者情報の取得を行う
     * @param phrid
     * @return 
     * @throws java.lang.Throwable 
     */
    InsurerPatientEntity insurerSearch(String phrid) throws Throwable;
    
    /**
     * 入力値の異常値チェック行う
     * @param value
     * @param InsurerNo
     * @param ObservationDeifinitionId
     * @param DiseaseTypeCd
     * @param Year
     * @return 
     * @throws java.lang.Throwable 
     */
    ObservationDefinitionRangeEntity inputCheck(String value, String InsurerNo, int Year, String ObservationDeifinitionId, String DiseaseTypeCd) throws Throwable;
    
    /**
     * ObservationDefinitionIDの一覧から対応するObservationDefinitionEntityリスト
     * @param idList
     * @return
     * @throws Throwable 
     */
    List<ObservationDefinitionEntity> getObservationDefinition(List<String> idList) throws Throwable;
    
    /**
     * observationEventIdに対応するObservationEntryの一覧を取得する
     * @param observationEventId
     * @return
     * @throws Throwable 
     */
    List<ObservationEntity> getObservationEntryList(String observationEventId) throws Throwable;
    
}
