/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.MedicalOrganizationSystemEntity;

/**
 *
 */
public interface IFHIRCooperationService {
	/**
	 * PHR-FHIR検査結果連携機能
	 * @param param
	 * @return 
	 * @throws Throwable 
	 */
	void cooperationFunction(MedicalOrganizationSystemEntity param) throws Throwable;
	
}
