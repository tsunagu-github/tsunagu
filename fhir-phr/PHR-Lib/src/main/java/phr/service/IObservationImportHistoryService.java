/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.net.URL;
import java.util.List;

import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;

import phr.datadomain.entity.MedicalOrganizationSystemEntity;
import phr.datadomain.entity.ObservationImportHistoryEntity;

/**
 *
 */
public interface IObservationImportHistoryService {
	/**
	 * 検査結果取得履歴情報の登録
	 * @throws Throwable 
	 */
//	String insertObservationImportHistory() throws Throwable;
	ObservationImportHistoryEntity insertObservationImportHistory(MedicalOrganizationSystemEntity param) throws Throwable;
	
	/**
	 * 検査結果取得履歴情報レコード取得処理
	 * @return entity
	 * @throws Throwable 
	 */
//	ObservationImportHistoryEntity getObservationImportHistoryRecord() throws Throwable;
	ObservationImportHistoryEntity getObservationImportHistoryRecord(ObservationImportHistoryEntity entity) throws Throwable;
	
	/**
	 * ServiceRequestリソース取得処理
	 * @param entity
	 * @param param
	 * @return serviceRequest
	 * @throws Throwable 
	 */
	public List<ServiceRequest> searchServiceRequestResource(ObservationImportHistoryEntity entity, MedicalOrganizationSystemEntity param) throws Throwable;
	
	/**
	 * 論理ID取得処理
	 * @param list
	 * @return logicalId
	 * @throws Throwable 
	 */
	public List<String> getLogicalId(List<ServiceRequest> list) throws Throwable;
	
	/**
	 * Patientリソース取得処理
	 * @param logicalId
	 * @param param
	 * @return patient
	 * @throws Throwable 
	 */
	public Patient readPatientResource(String logicalId, MedicalOrganizationSystemEntity param) throws Throwable;
	
	/**
	 * Organizationリソース取得処理
	 * @param patient
	 * @param param
	 * @return organization
	 * @throws Throwable
	 */
	public Organization readOrganizationResource(Patient patient, MedicalOrganizationSystemEntity param) throws Throwable;
	
	/**
	 * Observationリソース取得処理
	 * @param serviceRequest
	 * @param param
	 * @return observation
	 * @throws Throwable
	 */
	public List<Observation> searchObservationResource(List<ServiceRequest> serviceRequestList, MedicalOrganizationSystemEntity param) throws Throwable;
	
	/**
	 * 検査結果情報登録処理
	 * @param patientList
	 * @param importHIstoryId
	 * @param param
	 */
	public String register(List<Patient> patientList, String importHIstoryId, MedicalOrganizationSystemEntity param) throws Throwable;
	
	/**
	 * 検査結果取得履歴情報更新処理
	 * @param importHIstoryId
	 * @param status
	 * @throws Throwable
	 */
	public void update(String importHIstoryId, String status) throws Throwable;
	
	/**
	 * FRUCtoSサーバへのリクエスト処理
	 * @param url
	 * @param accessToken
	 * @return result
	 * @throws Throwable
	 */
	public String getRequest(URL url, String accessToken) throws Throwable;
	
	/**
	 * エラーリストをテキストファイルとして出力する
	 * @param importHistoryId
	 * @param errorList
	 */
	public void createFile(String importHistoryId, List<String> errorList);
}
