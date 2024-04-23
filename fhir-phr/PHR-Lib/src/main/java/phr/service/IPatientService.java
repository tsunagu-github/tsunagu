/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author KISNOTE011
 */
public interface IPatientService {

	/**
     * 該当患者のレコードを取得する
     * @param phr_id
     * @return
     * @throws Throwable 
     */
    public PatientEntity findById(String phr_id) throws Throwable;

}
