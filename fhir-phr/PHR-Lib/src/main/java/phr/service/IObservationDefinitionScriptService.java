/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.ObservationDefinitionScriptEntity;

/**
 *
 * @author KISNOTE011
 */
public interface IObservationDefinitionScriptService {

	/**
     * ObservationDefinitionIdとDiseaseTypeCdでレコードを取得する
     * @param observationDefinitionId
     * @param diseaseTypeCd
     * @return
     * @throws Throwable 
     */
    public ObservationDefinitionScriptEntity findByIdAndDiseasetypecd(String observationDefinitionId, int diseaseTypeCd) throws Throwable;

}
