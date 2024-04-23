/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Date;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.service.impl.SelfCheckService.SelfCheckResult;

/**
 *
 * @author chiba
 */
public interface ISelfCheckService {

    InsurerPatientEntity getInsurerPatient( String phrId )throws Throwable;
    SelfCheckResult getSelfCheckList( String phrId, Date startDate, Date endDate ) throws Throwable;
    ObservationEntity getObservation( ObservationEntity targetEntity )throws Throwable;
    SelfCheckResult deleteObservation( ObservationEntity targetEntity ) throws Throwable;
    
}
