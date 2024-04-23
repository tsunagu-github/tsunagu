/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.impl.AlertSearchService;

/**
 *
 * @author KISO-NOTE-005
 */
public interface IAlertSetService {
    
    /**
     * 検査結果のアラート判別を実施する
     * @param insureNo
     * @param year
     * @param observationList
     * @return 
     * @throws java.lang.Throwable 
     */
    List<ObservationEntity> alertSet(String phtid ,String insureNo, Integer viewId , List<ObservationEntity> observationList, Timestamp date)  throws Throwable;
    
     /**
     * 特定健診結果のアラート判別を実施する
     * @param insureNo
     * @param year
     * @param observationList
     * @return 
     * @throws java.lang.Throwable 
     */
    List<ObservationEntity> checkupalertSet(String phtid ,String insureNo , List<ObservationEntity> observationList, Timestamp date)  throws Throwable;

}
