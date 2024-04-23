/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import java.util.List;
import phr.datadomain.entity.ObservationEntity;

/**
 *
 * @author kis-note
 */
public interface IAutoCalcService {
    
     /**
     * 検査結果から自動計算を実施する
     * @param insureNo
     * @param year
     * @param observationList
     * @return 
     * @throws java.lang.Throwable 
     */
    List<ObservationEntity> autoCalcSet(String phrid ,String insureNo, int year , Timestamp date , List<ObservationEntity> observationList)  throws Throwable;

}
