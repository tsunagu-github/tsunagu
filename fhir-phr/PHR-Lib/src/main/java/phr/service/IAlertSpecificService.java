/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.impl.AlertSearchService;

/**
 *
 * @author KISO-NOTE-005
 */
public interface IAlertSpecificService {
    
    /**
     * 検査結果のアラート判別を実施する
     * @param phtid
     * @param observation
     * @param cdate
     * @param days
     * @param type
     * @return 
     * @throws java.lang.Throwable 
     */
    String periodValue(String phtid ,ObservationEntity observation, Timestamp cdate , String days , Integer type) throws Throwable;
    
}
