/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author kis-note
 */
public interface IAlertExecuteService {
    /**
     * 抽出を行うメインサービス
     * @param
     * @return 
     * @return 
     * @return 
     * @throws Throwable 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	Integer getAlert(ObservationDefinitionScriptEntity entity, ObservationEntity target,PatientEntity pentity , Timestamp date) throws Throwable;    
}
