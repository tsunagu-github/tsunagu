/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.entity.MedicalOrganizationSystemEntity;
import phr.datadomain.entity.ObservationImportHistoryEntity;
import phr.service.IFHIRCooperationService;

/**
 *
 */
public class FHIRCooperationService implements IFHIRCooperationService {

//	private static final Log logger = LogFactory.getLog(FHIRCooperationService.class);
	private static Logger logger = Logger.getLogger(FHIRCooperationService.class);

//	private static final String FHIR_SERVER_BASE_URL = PhrConfig.getConfigProperty(ConfigConst.FHIR_SERVER_BASE_URL);

	/**
	 * PHR-FHIR検査結果連携機能
	 * @throws Throwable 
	 */
	@Override
	public void cooperationFunction(MedicalOrganizationSystemEntity param) {
		
		logger.debug("Start");
		
		ObservationImportHistoryService oi = new ObservationImportHistoryService();
		ObservationImportHistoryEntity en = new ObservationImportHistoryEntity();
//		String importHIstoryId = null;
		// PHR-FHIR検査結果連携機能
		try {
//			importHIstoryId = oi.insertObservationImportHistory();
//			en = oi.insertObservationImportHistory();
			en = oi.insertObservationImportHistory(param);
		} catch (Throwable e) {
			// TODO 自動生成された catch ブロック
			logger.error(e.toString(), e);
			System.exit(-1);
		}
		String importHIstoryId = en.getImportHistoryId();
		
		// 検査結果取得履歴情報レコード取得処理
		List<ServiceRequest> serviceRequestList = new ArrayList<>();
		List<String> logicalId = new ArrayList<>();
		Patient patient = null;
		List<Patient> patientList = new ArrayList<>();
		Organization organization = null;
		List<Organization> organizationList = new ArrayList<>();
		List<Observation> observationList = new ArrayList<>();
		try {
//			ObservationImportHistoryEntity entity = oi.getObservationImportHistoryRecord();
			ObservationImportHistoryEntity entity = oi.getObservationImportHistoryRecord(en);
			// ServiceRequestリソース取得処理
			serviceRequestList = oi.searchServiceRequestResource(entity, param);
			// 論理ID取得処理
			logicalId = oi.getLogicalId(serviceRequestList);
			// 論理IDごとのPatient、Organizationリソースを取得する
			for (int i = 0; i < logicalId.size(); i++) {
				patient = null;
				organization = null;
				patient = oi.readPatientResource(logicalId.get(i), param);
				patientList.add(patient);
				organization = oi.readOrganizationResource(patient, param);
				organizationList.add(organization);
			}
			// ServiceRequestリソースに含まれるすべてのObservationリソースを取得する
			observationList = oi.searchObservationResource(serviceRequestList, param);
		} catch (Throwable e) {
			// TODO 自動生成された catch ブロック
			logger.error(e.toString(), e);
			System.exit(-1);
		}
		
		// Observationリソースが1件も取得できなかった場合は更新処理を実行し処理を終了
		if (observationList.isEmpty() || observationList == null) {
            logger.info("Observationリソースが存在しません。");
			try {
				oi.update(importHIstoryId, "2");
			} catch (Throwable e) {
				// TODO 自動生成された catch ブロック
				logger.error(e.toString(), e);
				System.exit(-1);
			}
		} else {
			// 検査結果情報登録処理
			String status = null;
			try {
				status = oi.register(patientList, importHIstoryId, param);
			} catch (Throwable e) {
				logger.error(e);
				
				// 検査結果取得履歴情報更新処理（処理ステータス「9:異常終了」の時）
				status = "9";
				try {
					oi.update(importHIstoryId, status);
				} catch (Throwable e1) {
					// TODO 自動生成された catch ブロック
					logger.error(e.toString(), e);
					System.exit(-1);
				}
//				logger.error(e.toString(), e);
//				System.exit(-1);
			}
			// 検査結果取得履歴情報更新処理
			if (status != null) {
				try {
					oi.update(importHIstoryId, status);
				} catch (Throwable e) {
					// TODO 自動生成された catch ブロック
					logger.error(e.toString(), e);
					System.exit(-1);
				}
			}
		}
	logger.debug("End");
	}

}
