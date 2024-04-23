/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;

/**
 *
 * @author KISO-NOTE-005
 */
public interface IMedicalKensaEntryConfirmService {
    /**
     * 患者情報の検索を行う
     * @param insurerNo
     * @param Year
     * @return 
     * @throws java.lang.Throwable 
     */
    List<ObservationDefinitionRangeEntity> observationReminderSearch(String insurerNo, int Year, String sexCd)  throws Throwable;
    
    /**
     * PHRIDから保険者情報の取得を行う
     * @param phrid
     * @return 
     * @throws java.lang.Throwable 
     */
    InsurerPatientEntity insurerSearch(String phrid) throws Throwable;
    
    /**
     * 検査データの登録を行う
     * @param obEntitylist
     * @param evEntity
     * @return
     * @throws Throwable 
     */
    int updateObservationEventAndObservation(List<ObservationEntity> obEntitylist, ObservationEventEntity evEntity) throws Throwable;

    /**
     * ObservationEventIdに対応するObservationEventtを取得する
     * @param observationEventId
     * @return
     * @throws Throwable
     */
    ObservationEventEntity getObservationEvent(String observationEventId) throws Throwable;

    /**
     * ObservationEventId、ObservationDefinitionIdに対応するObservationを取得する
     * @param observationEventId
     * @param observationDefinitionId
     * @return
     * @throws Throwable
     */
    ObservationEntity getObservation(String observationEventId, String observationDefinitionId) throws Throwable;
}
