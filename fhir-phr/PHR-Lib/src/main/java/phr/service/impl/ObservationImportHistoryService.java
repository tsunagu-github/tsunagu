/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.elementmodel.Manager.FhirFormat;
import org.hl7.fhir.r4.formats.IParser;
import org.hl7.fhir.r4.formats.ParserFactory;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.StringType;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.CommonReferenceRangeAdapter;
import phr.datadomain.adapter.DiseaseTypeAdapter;
import phr.datadomain.adapter.InsurerDiseaseTypeAdapter;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.JLAC10AnalyteCodeAdapter;
import phr.datadomain.adapter.JLAC11AnalyteCodeAdapter;
import phr.datadomain.adapter.JLAC11UnitCodeAdapter;
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationDefinitionJlac10Adapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.adapter.ObservationImportHistoryAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.Seq_ObservationImportHistoryIdAdapter;
import phr.datadomain.entity.CommonReferenceRangeEntity;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerDiseaseTypeEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.JLAC10AnalyteCodeEntity;
import phr.datadomain.entity.JLAC11AnalyteCodeEntity;
import phr.datadomain.entity.JLAC11UnitCodeEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.MedicalOrganizationSystemEntity;
import phr.datadomain.entity.ObservationDefinitionJlac10Entity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.ObservationImportHistoryEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.Seq_ObservationImportHistoryIdEntity;
import phr.service.IAlertSetService;
import phr.service.IObservationImportHistoryService;

/**
 *
 */
public class ObservationImportHistoryService implements IObservationImportHistoryService {

//	private static final Log logger = LogFactory.getLog(ObservationImportHistoryService.class);
	private static Logger logger = Logger.getLogger(ObservationImportHistoryService.class);

//	private static final String FHIR_SERVER_BASE_URL = PhrConfig.getConfigProperty(ConfigConst.FHIR_SERVER_BASE_URL);

	/**
	 * 検査結果取得履歴情報の登録
	 * @param executionDate
	 * @param status
	 * @return
	 * @throws Throwable 
	 */
//	@Override
//	public String insertObservationImportHistory() throws Throwable{
	public ObservationImportHistoryEntity insertObservationImportHistory(MedicalOrganizationSystemEntity param) throws Throwable{
		logger.debug("Start");
		
		ObservationImportHistoryEntity entity = new ObservationImportHistoryEntity();
		String importHistoryId = Seq_ObservationImportHistoryIdAdapter.numberingObservationImportHistoryId();
		Timestamp executionDate = new Timestamp(System.currentTimeMillis());
		String status = "0";
		entity.setImportHistoryId(importHistoryId);
		entity.setExecutionDate(executionDate);
		entity.setStatus(status);
		entity.setDataCooperationSystemId(String.format("%09d", param.getDataCooperationSystemId()));
		
		ObservationImportHistoryAdapter adapter = new ObservationImportHistoryAdapter();
		adapter.insert(entity);
		
		// importHistoryId=0の時のみSeq_ObservationImportHistoryIdテーブルに登録
		if (importHistoryId.equals("000000000000000")) {
			Seq_ObservationImportHistoryIdEntity e = new Seq_ObservationImportHistoryIdEntity();
			e.setImportHistoryId(Long.valueOf(importHistoryId));
			Seq_ObservationImportHistoryIdAdapter.insertImportHistoryId(e);
		}
		
		logger.debug("End");
//		return importHistoryId;
		return entity;
	}
	
	/**
	 * 検査結果取得履歴情報レコード取得処理
	 * @return entity
	 * @throws Throwable 
	 */
	@Override
	public ObservationImportHistoryEntity getObservationImportHistoryRecord(ObservationImportHistoryEntity en) throws Throwable{
		logger.debug("Start");
		
		DataAccessObject dao = null;
		try {
			dao = new DataAccessObject();
			ObservationImportHistoryAdapter adapter = new ObservationImportHistoryAdapter(dao.getConnection());
//			ObservationImportHistoryEntity entity = adapter.getObservationHistory();
			ObservationImportHistoryEntity entity = adapter.getObservationHistory(en);
			return entity;
		} catch (Throwable ex) {
			logger.error(ex.toString(), ex);
			throw ex;
		} finally {
			if (dao != null) {
				dao.close();
			}
			logger.debug("End");
		}
	}

	/**
	 * アクセストークンの取得処理
	 * @param param
	 * @return accessToken
	 * @throws Throwable 
	 */
	public String getAccessToken(MedicalOrganizationSystemEntity param) throws Throwable{
		logger.debug("Start");
		String accessToken = null;
		
		String url = param.getAuthorizationServerUrl();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("client_id", param.getClientId()));
		parameters.add(new BasicNameValuePair("client_secret", param.getClientSecret()));
		parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
		
		logger.debug("url: " + url);
		logger.debug("client_id: " + param.getClientId());
		logger.debug("client_secret: " + param.getClientSecret());
		logger.debug("grant_type: client_credentials");
		
		HttpEntity entity = new UrlEncodedFormEntity(parameters);
		request.setEntity(entity);
		request.setHeader("Host", "keycloak:8090");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		logger.debug("Host: keycloak:8090");
		logger.debug("Content-Type: application/x-www-form-urlencoded");
		
		CloseableHttpResponse response = httpClient.execute(request);
		
		logger.debug("HTTP_STATUS: " + response.getStatusLine().getStatusCode());
		
		String result = null;
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		response.close();
		
//		URL url = new URL(param.getAuthorizationServerUrl());
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setDoInput(true);
//		connection.setDoOutput(true);
//		connection.setUseCaches(false);
//		connection.setRequestMethod("POST");
//		
//		String parameters = "grant_type=client_credentials"
//						+ "&client_id=" + param.getClientId()
//						+ "&client_secret=" + param.getClientSecret();
//		PrintWriter pr = new PrintWriter(connection.getOutputStream());
//		pr.print(parameters);
//		pr.close();
//		
//		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//		String str = null;
//		String line;
//		while ((line = br.readLine()) != null) {
//			str += line;
//		}
//		
		// アクセストークンのみを抽出
		String[] ret = result.split(",");
//		String[] ret = str.split(",");
		String targetStr = null;
		for (String target : ret) {
			if (target.contains("access_token")) {
				targetStr = target;
			}
		}
		String[] splitStr = targetStr.split(":");
		int size = splitStr[1].length();
		accessToken = splitStr[1].substring(1, size-1);
		
		logger.debug("accessToken: " + accessToken);
		
		logger.debug("End");
		return accessToken;
	}

	/**
	 * ServiceRequestリソース取得処理
	 * @return list
	 * @throws Throwable 
	 */
	public List<ServiceRequest> searchServiceRequestResource(ObservationImportHistoryEntity entity, MedicalOrganizationSystemEntity param) throws Throwable{
		logger.debug("Start");
		
		// アクセストークンの取得処理
		String accessToken = this.getAccessToken(param);
		
		String ret = null;
		URL url = null;
//		String baseUrl = FHIR_SERVER_BASE_URL + "/ServiceRequest";
		String serverUrl = param.getFhirServerBaseUrl() + "/ServiceRequest";
		
		if (entity != null) {
			if (entity.getExecutionDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String date = sdf.format(entity.getExecutionDate());
//				url = new URL(baseUrl + "?_lastUpdated=gt" + date.replace(" ", "T"));
				url = new URL(serverUrl + "?_lastUpdated=gt" + date.replace(" ", "T") + "&_count=" + Integer.MAX_VALUE);
			}
		} else {
			url = new URL(serverUrl + "?_count=" + Integer.MAX_VALUE);
		}

		logger.debug("url: " + url);

		ret = this.getRequest(url, accessToken);
		
		
		// 検索結果をパースする
		List<ServiceRequest> list = new ArrayList<>();
		if (ret != null) {
			IParser parser = ParserFactory.parser(FhirFormat.JSON);
			IBaseResource resource = parser.parse(ret.toString());
			Bundle bundle = (Bundle) resource;
			logger.info("Bundle.total: " + bundle.getTotal());
			List<Bundle.BundleEntryComponent> entry = bundle.getEntry(); 
			for (int i = 0; i < entry.size(); i++) {
				list.add((ServiceRequest) entry.get(i).getResource());
			}
		}
		
		logger.debug("End");
		return list;
	}
	
	/**
	 * 論理ID取得処理
	 * @return result
	 * @throws Throwable 
	 */
	public List<String> getLogicalId(List<ServiceRequest> list) throws Throwable{
		logger.debug("Start");
		
		// ServiceRequestリソースのsubjectプロパティに設定されたPatientリソースの論理 IDを取得する
		List<String> logicalId = new ArrayList<>();
		Set<String> linkedHashSet = new LinkedHashSet<String>();
		for (int i = 0; i < list.size(); i ++) {
			linkedHashSet.add(list.get(i).getSubject().getReference());
		}
		for (String id : linkedHashSet) {
			logicalId.add(id.substring(8));
		}
		
		logger.debug("End");
		return logicalId;
	}
	
	/**
	 * Patientリソース取得処理
	 * @param
	 * @return
	 * @throws Throwable
	 */
	public Patient readPatientResource(String logicalId, MedicalOrganizationSystemEntity param) throws Throwable{
		logger.debug("Start");
		
		// アクセストークンの取得処理
		String accessToken = this.getAccessToken(param);
		
		Patient patient = null;
		
		// 論理IDを用いてPatientリソースを取得する
		String ret = null;
//		String baseUrl = FHIR_SERVER_BASE_URL + "/Patient";
		String serverUrl = param.getFhirServerBaseUrl() + "/Patient";
//		URL url = new URL(baseUrl + "/" + logicalId);
		URL url = new URL(serverUrl + "/" + logicalId);

		ret = this.getRequest(url, accessToken);
		
		logger.debug("url: " + url);
				
		// 検索結果をパースする
		if (ret != null) {
			IParser parser = ParserFactory.parser(FhirFormat.JSON);
			patient = (Patient) parser.parse(ret.toString());
		}
		
		logger.debug("End");
		return patient;
	}
	
	/**
	 * Organizationリソース取得処理
	 * @param patient
	 * @return organization
	 * @throws Throwable
	 */
	public Organization readOrganizationResource(Patient patient, MedicalOrganizationSystemEntity param) throws Throwable{
		logger.debug("Start");
		
		// アクセストークンの取得処理
		String accessToken = this.getAccessToken(param);
		
		Organization organization = null;
		
		// PatientリソースからOrganizationリソースの論理IDを取得する
		String logicalId = new String();
		logicalId = patient.getManagingOrganization().getReference().substring(13);
		
		// 論理IDを用いてOrganizationリソースを取得する
		String ret = null;
//		String baseUrl = FHIR_SERVER_BASE_URL + "/Organization";
		String serverUrl = param.getFhirServerBaseUrl() + "/Organization";
//		URL url = new URL(baseUrl + "/" + logicalId);
		URL url = new URL(serverUrl + "/" + logicalId);

		ret = this.getRequest(url, accessToken);
		
		logger.debug("url: " + url);
		
		// 検索結果をパースする
		if (ret != null) {
			IParser parser = ParserFactory.parser(FhirFormat.JSON);
			organization = (Organization) parser.parse(ret.toString());
		}
		
		logger.debug("End");
		return organization;
	}
	
	/**
	 * Observationリソース取得処理
	 * @param serviceRequest
	 * @return observation
	 * @throws Throwable
	 */
	public List<Observation> searchObservationResource(List<ServiceRequest> serviceRequestList, MedicalOrganizationSystemEntity param) throws Throwable{
		logger.debug("Start");
		
		// アクセストークンの取得処理
		String accessToken = this.getAccessToken(param);
		
		List<Observation> observationList = new ArrayList<>();
		
		// ServiceRequestリソースのIDのみを抽出する
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < serviceRequestList.size(); i++) {
			idList.add("ServiceRequest/" + serviceRequestList.get(i).getId());
		}
		
		// IDの数だけObservationリソースに対してSEARCHインタラクションを実行する
		for (int ii = 0; ii < idList.size(); ii++) {
			String ret = null;
//			String baseUrl = FHIR_SERVER_BASE_URL + "/Observation?based-on=";
			String serverUrl = param.getFhirServerBaseUrl() + "/Observation?based-on=";
//			URL url = new URL(baseUrl + idList.get(ii));
			URL url = new URL(serverUrl + idList.get(ii));

			ret = this.getRequest(url, accessToken);
			
			logger.debug("url: " + url);
			
			// 検索結果をパースする
			if (ret != null) {
				IParser parser = ParserFactory.parser(FhirFormat.JSON);
				IBaseResource resource = parser.parse(ret.toString());
				Bundle bundle = (Bundle) resource;
				logger.debug("Bundle.total: " + bundle.getTotal());
				List<Bundle.BundleEntryComponent> entry = bundle.getEntry(); 
				for (int iii = 0; iii < entry.size(); iii++) {
					observationList.add((Observation) entry.get(iii).getResource());
				}
			}
		}
		
		logger.debug("End");
		return observationList;
	}
	
	/**
	 * 検査結果情報登録処理
	 * @param patientList
	 */
	public String register(List<Patient> list, String importHIstoryId, MedicalOrganizationSystemEntity param) throws Throwable{
		logger.debug("Start");
		
		// 処理ステータス
		String status = "1";
		// エラーリスト
		List<String> errorList = new ArrayList<>();
		
		// PatientリソースからPatient.idが重複しないPatientリソースのリストを作成する
		List<Patient> patientList = new ArrayList<>();
		Set<Patient> linkedHashSet = new LinkedHashSet<Patient>();
		for (int i = 0; i < list.size(); i ++) {
			linkedHashSet.add(list.get(i));
		}
		for (Patient p : linkedHashSet) {
			patientList.add(p);
		}
		
		// 各Patientリソース毎に処理を行う
		for (Patient patient : patientList) {
			
			// 患者ID
			String patientId = null;
			String patSystem = PhrConfig.getConfigProperty(ConfigConst.PATIENT_IDENTIFIER_SYSTEM);
			if (!patient.getIdentifier().isEmpty()) {
				for (int i = 0; i < patient.getIdentifier().size(); i++) {
					if (patient.getIdentifier().get(i).getSystem().startsWith(patSystem)) {
						patientId = patient.getIdentifier().get(i).getValue();
						break;
					}
				}
				// 患者IDが取得できない場合はエラーとして次のPatientリソースの処理へ進む
				if (patientId == null) {
					status = "2";
					errorList.add("[" + importHIstoryId + "]" + "患者IDが取得できません。" + 
							"Patient.id = " + patient.getId() + "、idntifier = #{" + patSystem + "}|{value}");
					logger.error("[" + importHIstoryId + "]" + "患者IDが取得できません。" + 
							"Patient.id = " + patient.getId() + "、idntifier = #{" + patSystem + "}|{value}");
					continue;
				}
			} else {
				// 患者IDが取得できない場合はエラーとして次のPatientリソースの処理へ進む
				status = "2";
				errorList.add("[" + importHIstoryId + "]" + "患者IDが存在しません。Patient.id = " + 
						patient.getId() + "、idntifier = #{" + patSystem + "}|{value}");
				logger.error("[" + importHIstoryId + "]" + "患者IDが存在しません。Patient.id = " + 
						patient.getId() + "、idntifier = #{" + patSystem + "}|{value}");
				continue;
			}
			
			// 医療機関情報(Organizationリソースの取得)
			String logicalId = null;
			logicalId = patient.getManagingOrganization().getReference().substring(13);
			String ret = null;
//			String baseUrl = FHIR_SERVER_BASE_URL + "/Organization/";
			String serverUrl = param.getFhirServerBaseUrl() + "/Organization/";
//			URL url = new URL(baseUrl + logicalId);
			URL url = new URL(serverUrl + logicalId);
			// アクセストークンの取得処理
			String accessToken = this.getAccessToken(param);
			ret = this.getRequest(url, accessToken);
			Organization organization = null;
			if (!ret.isEmpty() || ret != null) {
				IParser parser = ParserFactory.parser(FhirFormat.JSON);
				organization = (Organization) parser.parse(ret.toString());
			}
			if (organization == null) {
				status = "2";
				errorList.add("[" + importHIstoryId + "]" + "医療機関情報が取得できません。Patient.id = " +
						patient.getId() + "、Organization.id = " + logicalId);
				logger.error("[" + importHIstoryId + "]" + "医療機関情報が取得できません。Patient.id = " +
						patient.getId() + "、Organization.id = " + logicalId);
				continue;
			}
			
			// 医療機関コード
			String medicalOrganizationCd = null;
			if (!organization.getIdentifier().isEmpty()) {
				String orgSystem = PhrConfig.getConfigProperty(ConfigConst.ORGANIZATION_IDENTIFIER_SYSTEM);
				for (int i = 0; i < organization.getIdentifier().size(); i++) {
					if (organization.getIdentifier().get(i).getSystem().startsWith(orgSystem)) {
						medicalOrganizationCd = organization.getIdentifier().get(i).getValue();
						break;
					}
				}
			}
			
			// 医療機関患者関連情報レコードの取得
			DataAccessObject dao = null;
			dao = new DataAccessObject();
			MedicalOrganizationPatientAdapter medicalOrganizationPatientAdapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
			MedicalOrganizationPatientEntity medicalOrganizationPatientEntity = new MedicalOrganizationPatientEntity();
			medicalOrganizationPatientEntity = medicalOrganizationPatientAdapter.findByPatientIdAndMedicalCd(patientId, medicalOrganizationCd);
			if (medicalOrganizationPatientEntity == null) {
				status = "2";
				errorList.add("[" + importHIstoryId + "]" + "該当の患者が登録されていません。Patient.id = " +
						patient.getId() + "、identifier = " + patientId + "、Organization.id = " + 
						organization.getId() + "、identifier = " + medicalOrganizationCd);
				logger.error("[" + importHIstoryId + "]" + "該当の患者が登録されていません。Patient.id = " +
						patient.getId() + "、identifier = " + patientId + "、Organization.id = " + 
						organization.getId() + "、identifier = " + medicalOrganizationCd);
				continue;
			}
			
			// PHR患者ID
			String phr_Id = null;
			phr_Id = medicalOrganizationPatientEntity.getPHR_ID();
			
			// 患者情報レコードを取得
			PatientAdapter patAdapter = new PatientAdapter(dao.getConnection());
			PatientEntity patEntity = new PatientEntity();
			patEntity = patAdapter.findByPrimaryKey(phr_Id);
			// 性別 （男性：1、女性：2）
			String sexCd = null;
			if (patEntity.getSexCd().equals("M")) {
				sexCd = "1";
			} else if (patEntity.getSexCd().equals("F")) {
				sexCd = "2";
			}
			
			// 保険者患者関連情報レコードの取得
			InsurerPatientAdapter insurerPatientAdapter = new InsurerPatientAdapter(dao.getConnection());
			InsurerPatientEntity insurerPatientEntity = new InsurerPatientEntity();
			insurerPatientEntity = insurerPatientAdapter.findByPhrId(phr_Id);
			String insurerNo = null;
			if (insurerPatientEntity == null) {
				status = "2";
				errorList.add("[" + importHIstoryId + "]" + "該当の患者が保険者に登録されていません。PHR患者ID = " + phr_Id);
				logger.error("[" + importHIstoryId + "]" + "該当の患者が保険者に登録されていません。PHR患者ID = " + phr_Id);
				continue;
			} else {
				insurerNo = insurerPatientEntity.getInsurerNo();
			}
			
			// 検査依頼情報(ServiceRequestリソースの取得)
			String patId = null;
			patId = patient.getId();
			String result = null;
//			String baseUrl2 = FHIR_SERVER_BASE_URL + "/ServiceRequest?subject=Patient/";
			String serverUrl2 = param.getFhirServerBaseUrl() + "/ServiceRequest?subject=Patient/";
//			URL url2 = new URL(baseUrl2 + patId);
			URL url2 = new URL(serverUrl2 + patId + "&_count=" + Integer.MAX_VALUE);
			// アクセストークンの取得処理
			String accessToken2 = this.getAccessToken(param);
			logger.debug("url: " + url2);
			result = this.getRequest(url2, accessToken2);
			List<ServiceRequest> serviceRequestList = new ArrayList<>();
			if (result != null) {
				IParser parser = ParserFactory.parser(FhirFormat.JSON);
				IBaseResource resource = parser.parse(result.toString());
				Bundle bundle = (Bundle) resource;
				logger.info("Bundle.total: " + bundle.getTotal());
				List<Bundle.BundleEntryComponent> entry = bundle.getEntry(); 
				for (int i = 0; i < entry.size(); i++) {
					serviceRequestList.add((ServiceRequest) entry.get(i).getResource());
				}
			}
			
			// 各ServiceRequestリソース毎に処理を行う
			for (ServiceRequest serviceRequest : serviceRequestList) {
				
				// 検査依頼ID
				String examRequestId = null;
				if (!serviceRequest.getIdentifier().isEmpty()) {
					String srSystem = PhrConfig.getConfigProperty(ConfigConst.SERVICEREQUEST_IDENTIFIER_SYSTEM);
					for (int i = 0; i < serviceRequest.getIdentifier().size(); i++) {
						if (serviceRequest.getIdentifier().get(i).getSystem().startsWith(srSystem)) {
							examRequestId = serviceRequest.getIdentifier().get(i).getValue();
                            int lastIndex = examRequestId.lastIndexOf("-");
                            if (lastIndex > 0) {
                                examRequestId = examRequestId.substring(lastIndex + 1);
                            }
                            logger.debug("OrderNo: " + examRequestId);
							break;
						}
					}
				}
				
/*
				// 検査日
				String examDate = null;
				Timestamp ts = null;
				if (!serviceRequest.getOccurrence().isEmpty()) {
					DateTimeType d = new DateTimeType();
					d = serviceRequest.getOccurrenceDateTimeType();
					examDate = d.getAsV3().substring(0,4) + "/" + d.getAsV3().substring(4, 6) + "/" + d.getAsV3().substring(6, 8);
//					examDate = d.getYear().toString() + "/" + d.getMonth().toString() + "/" + d.getDay().toString();
					ts = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(examDate).getTime());
				}
*/
				
				// 検査結果情報(Observationリソースの取得)
				String srId = null;
				srId = serviceRequest.getId();
				String result2 = null;
//				String baseUrl3 = FHIR_SERVER_BASE_URL + "/Observation??based-on=ServiceRequest/";
				String serverUrl3 = param.getFhirServerBaseUrl() + "/Observation??based-on=ServiceRequest/";
//				URL url3 = new URL(baseUrl3 + srId);
				URL url3 = new URL(serverUrl3 + srId);
				// アクセストークンの取得処理
				String accessToken3 = this.getAccessToken(param);
				result2 = this.getRequest(url3, accessToken3);
				List<Observation> observationList = new ArrayList<>();
				if (result2 != null) {
					IParser parser = ParserFactory.parser(FhirFormat.JSON);
					IBaseResource resource = parser.parse(result2.toString());
					Bundle bundle = (Bundle) resource;
					List<Bundle.BundleEntryComponent> entry = bundle.getEntry(); 
					for (int i = 0; i < entry.size(); i++) {
						observationList.add((Observation) entry.get(i).getResource());
					}
				}
				
				// 取得した検査項目IDが検査項目マスタに存在するかどうか
				int count = 0;
				int sum = observationList.size();
				
				// 新規登録用のカウント
				int insertCount = observationList.size();
				int ic = 0;
				List<ObservationEntity> insertCountEntity = new ArrayList<>();
				ObservationEventEntity insertEventEntity = new ObservationEventEntity();
                String examDate = null;
                Timestamp ts = null;
				// 各Observationリソース毎に処理を行う
				for (Observation observation : observationList) {
                    // 検査日
                    if (examDate == null) {
                        DateTimeType d = new DateTimeType();
                        d = observation.getEffectiveDateTimeType();
                        if (d != null && d.hasValue()) {
                            examDate = d.getAsV3().substring(0, 4) + "/" + d.getAsV3().substring(4, 6) + "/" + d.getAsV3().substring(6, 8);
                            ts = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(examDate).getTime());
                        }
                    }

					// 検査項目ID
					String examCode = null;
					examCode = observation.getCode().getCoding().get(0).getCode();
					ObservationDefinitionJlac10Adapter observationDefinitionJlac10Adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
					boolean hospitalTargetFlg = false;
					String observationDefinitionId = null;
					boolean diseaseManagementFlg = false;
					// 検査結果項目IDがJLAC10かもしくはJLAC11かで処理を分岐
					ObservationDefinitionJlac10Entity j10e = new ObservationDefinitionJlac10Entity();
					j10e = observationDefinitionJlac10Adapter.findJLAC10(examCode, 10);
					if (j10e != null) {
						String analyteType = j10e.getJLAC10().substring(0, 5);
						JLAC10AnalyteCodeAdapter jLAC10AnalyteCodeAdapter = new JLAC10AnalyteCodeAdapter(dao.getConnection());
						JLAC10AnalyteCodeEntity jLAC10AnalyteCodeEntity = new JLAC10AnalyteCodeEntity();
						jLAC10AnalyteCodeEntity = jLAC10AnalyteCodeAdapter.findRecord(analyteType);
						if (jLAC10AnalyteCodeEntity != null) {
							hospitalTargetFlg = jLAC10AnalyteCodeEntity.isHospitalLabResultTargetFlg();
						}
						// 病院検査結果表示対象項目かどうかで処理を分岐
						if (hospitalTargetFlg) {
							observationDefinitionId = j10e.getObservationDefinitionId();
//							DiseaseTypeAdapter diseaseTypeAdapter = new DiseaseTypeAdapter(dao.getConnection());
//							List<DiseaseTypeEntity> diseaseTypeList = new ArrayList<>();
//							diseaseTypeList = diseaseTypeAdapter.findByObservationDefinitionId(observationDefinitionId);
//							if (diseaseTypeList != null && !diseaseTypeList.isEmpty()) {
//								diseaseManagementFlg = true;
//							}
							InsurerDiseaseTypeAdapter insurerDiseaseTypeAdapter = new InsurerDiseaseTypeAdapter(dao.getConnection());
							List<InsurerDiseaseTypeEntity> insurerDiseaseTypeList = new ArrayList<>();
							insurerDiseaseTypeList = insurerDiseaseTypeAdapter.findByObservationDefinitionId(observationDefinitionId);
							if (insurerDiseaseTypeList != null && !insurerDiseaseTypeList.isEmpty()) {
								diseaseManagementFlg = true;
							}
						} else {
							continue;
						}
					}
					ObservationDefinitionJlac10Entity j11e = new ObservationDefinitionJlac10Entity();
					j11e = observationDefinitionJlac10Adapter.findJLAC11(examCode, 11);
					if (j11e != null) {
						String analyteType = j11e.getJLAC11().substring(0, 5);
						JLAC11AnalyteCodeAdapter jLAC11AnalyteCodeAdapter = new JLAC11AnalyteCodeAdapter(dao.getConnection());
						JLAC11AnalyteCodeEntity jLAC11AnalyteCodeEntity = new JLAC11AnalyteCodeEntity();
						jLAC11AnalyteCodeEntity = jLAC11AnalyteCodeAdapter.findRecord(analyteType);
						if (jLAC11AnalyteCodeEntity != null) {
							hospitalTargetFlg = jLAC11AnalyteCodeEntity.isHospitalLabResultTargetFlg();
						}
						// 病院検査結果表示対象項目かどうかで処理を分岐
						if (hospitalTargetFlg) {
							observationDefinitionId = j11e.getObservationDefinitionId();
//							DiseaseTypeAdapter diseaseTypeAdapter = new DiseaseTypeAdapter(dao.getConnection());
//							List<DiseaseTypeEntity> diseaseTypeList = new ArrayList<>();
//							diseaseTypeList = diseaseTypeAdapter.findByObservationDefinitionId(observationDefinitionId);
//							if (diseaseTypeList != null && !diseaseTypeList.isEmpty()) {
//								diseaseManagementFlg = true;
//							}
							InsurerDiseaseTypeAdapter insurerDiseaseTypeAdapter = new InsurerDiseaseTypeAdapter(dao.getConnection());
							List<InsurerDiseaseTypeEntity> insurerDiseaseTypeList = new ArrayList<>();
							insurerDiseaseTypeList = insurerDiseaseTypeAdapter.findByObservationDefinitionId(observationDefinitionId);
							if (insurerDiseaseTypeList != null && !insurerDiseaseTypeList.isEmpty()) {
								diseaseManagementFlg = true;
							}
						} else {
							continue;
						}
					}
					
					// 検査結果値
					String value = null;
					String unit = null;
					if (observation.getValue() != null) {
						Object ob = observation.getValue().getClass();
						if (ob.equals(CodeableConcept.class)) {
							value = observation.getValueCodeableConcept().getCoding().get(0).getCode();
						} else if (ob.equals(Quantity.class)) {
							value = observation.getValueQuantity().getValue().toString();
							if (observation.getValueQuantity().getUnit() != null) {
								unit = observation.getValueQuantity().getUnit();
							} else if (observation.getValueQuantity().getUnit() == null && observation.getValueQuantity().getCode() != null) {
								unit = observation.getValueQuantity().getCode();
							}
							// 検査項目IDがJLAC11で単位コードが未指定の場合、JLAC11の結果単位コードを設定
							if (j11e != null && unit == null) {
								String jlac11UnitCode = null;
								jlac11UnitCode = j11e.getJLAC11().substring(j11e.getJLAC11().length() - 2);
								JLAC11UnitCodeAdapter jlac11UnitCodeAdapter = new JLAC11UnitCodeAdapter(dao.getConnection());
								JLAC11UnitCodeEntity jlac11UnitCodeEntity = new JLAC11UnitCodeEntity();
								jlac11UnitCodeEntity = jlac11UnitCodeAdapter.findByPrimaryKey(jlac11UnitCode);
								if (jlac11UnitCodeEntity != null) {
									if (!jlac11UnitCodeEntity.getJLAC11UnitCode().equals("01")) {
										unit = jlac11UnitCodeEntity.getUnit();
									}
								}
							}
						} else if (ob.equals(StringType.class)) {
							value = observation.getValueStringType().toString();
						}
					}
					logger.debug("Observation.value = " + value);
					
					// 基準上限値・下限値
					String low = null;
					String high = null;
					if (!observation.getReferenceRange().isEmpty()) {
//						if (!observation.getReferenceRange().isEmpty()) {
							if (!observation.getReferenceRange().get(0).getLow().isEmpty()) {
								low = observation.getReferenceRange().get(0).getLow().getValue().toString();
							}
							if (!observation.getReferenceRange().get(0).getHigh().isEmpty()) {
								high = observation.getReferenceRange().get(0).getHigh().getValue().toString();
							}
//						}
					} else {
						// Observation.referenceRangeが未設定の場合、共用基準範囲マスタから取得
						CommonReferenceRangeAdapter commonReferenceRangeAdapter = new CommonReferenceRangeAdapter(dao.getConnection());
						List<CommonReferenceRangeEntity> commonReferenceRangeEntity = new ArrayList<>();
						if (j10e != null) {
							// 検査項目IDがJLAC10コードの場合
							String analyteCode = j10e.getJLAC10().substring(0, 5);
							commonReferenceRangeEntity = commonReferenceRangeAdapter.findAllList(analyteCode);
							if (!commonReferenceRangeEntity.isEmpty()) {
								// 基準値区分の判定
								for (CommonReferenceRangeEntity c : commonReferenceRangeEntity) {
									if (c.getReferenceRangeType().equals(sexCd)) {
										if (sexCd.equals("1")) {
											low = c.getMaleLowerLimit().toString();
											high = c.getMaleUpperLimit().toString();
										} else if (sexCd.equals("2")) {
											low = c.getFemaleLowerLimit().toString();
											high = c.getFemaleUpperLimit().toString();
										}
									}
								}
								// 男女別の基準値が無ければ共用基準値を適用
								if (commonReferenceRangeEntity.get(0).getReferenceRangeType().equals("0")) {
									if (commonReferenceRangeEntity.get(0).getCommonLowerLimit() != null) {
										low = commonReferenceRangeEntity.get(0).getCommonLowerLimit().toString();
									}
									if (commonReferenceRangeEntity.get(0).getCommonUpperLimit() != null) {
										high = commonReferenceRangeEntity.get(0).getCommonUpperLimit().toString();
									}
								}
							}
						}
						if (j11e != null) {
							// 検査項目IDがJLAC11コードの場合
							String analyte11Code = j11e.getJLAC11().substring(0, 5);
							// JLAC10分析物コードを取得
							JLAC11AnalyteCodeAdapter jLAC11AnalyteCodeAdapter = new JLAC11AnalyteCodeAdapter(dao.getConnection());
							JLAC11AnalyteCodeEntity jLAC11AnalyteCodeEntity = new JLAC11AnalyteCodeEntity();
							jLAC11AnalyteCodeEntity = jLAC11AnalyteCodeAdapter.findRecord(analyte11Code);
							String analyte10Code = null;
							if (jLAC11AnalyteCodeEntity != null) {
								analyte10Code = jLAC11AnalyteCodeEntity.getJLAC10AnalyteCode();
							}
							commonReferenceRangeEntity = commonReferenceRangeAdapter.findAllList(analyte10Code);
							if (!commonReferenceRangeEntity.isEmpty()) {
								// 基準値区分の判定
								for (CommonReferenceRangeEntity c : commonReferenceRangeEntity) {
									if (c.getReferenceRangeType().equals(sexCd)) {
										if (sexCd.equals("1")) {
											low = c.getMaleLowerLimit().toString();
											high = c.getMaleUpperLimit().toString();
										} else if (sexCd.equals("2")) {
											low = c.getFemaleLowerLimit().toString();
											high = c.getFemaleUpperLimit().toString();
										}
									}
								}
								// 男女別の基準値が無ければ共用基準値を適用
								if (commonReferenceRangeEntity.get(0).getReferenceRangeType().equals("0")) {
									if (commonReferenceRangeEntity.get(0).getCommonLowerLimit() != null) {
										low = commonReferenceRangeEntity.get(0).getCommonLowerLimit().toString();
									}
									if (commonReferenceRangeEntity.get(0).getCommonUpperLimit() != null) {
										high = commonReferenceRangeEntity.get(0).getCommonUpperLimit().toString();
									}
								}
							}
						}
					}
					
					// 検査項目IDが管理項目情報テーブルに存在するかをチェック
//					ObservationDefinitionAdapter observationDefinitionAdapter = new ObservationDefinitionAdapter(dao.getConnection());
//					ObservationDefinitionEntity observationDefinitionEntity = new ObservationDefinitionEntity();
//					observationDefinitionEntity = observationDefinitionAdapter.findByPrimaryKey(examCode);
//					if (observationDefinitionEntity == null) {
//						status = "2";
//						count++;
//						errorList.add("[" + importHIstoryId + "]" + "検査項目が取得できない検査が存在します。Patient.id = " +
//								patient.getId() + "、ServiceRequest.id = " + serviceRequest.getId() +
//								"、Observation.id = " + observation.getId());
//						logger.error("[" + importHIstoryId + "]" + "検査項目が取得できない検査が存在します。Patient.id = " +
//								patient.getId() + "、ServiceRequest.id = " + serviceRequest.getId() +
//								"、Observation.id = " + observation.getId());
//						//同一のServiceRequestリソースに含まれるすべての検査項目が存在しない場合
//						if (count == sum) {
//							errorList.add("[" + importHIstoryId + "]" + "検査オーダに含まれるすべての検査項目が取得できません。Patient.id = " +
//									patient.getId() + "、ServiceRequest.id = " + serviceRequest.getId());
//							logger.error("[" + importHIstoryId + "]" + "検査オーダに含まれるすべての検査項目が取得できません。Patient.id = " +
//									patient.getId() + "、ServiceRequest.id = " + serviceRequest.getId());
//						}
//						continue;
//					}
					
					// 検査項目IDの頭5桁がJLAC10分析物マスタおよびJLAC11測定物マスタに含まれるかチェック
//					if (j10e != null || j11e != null) {
					String code = examCode.substring(0, 5);
					// JLAC10分析物マスタに含まれるか確認
					JLAC10AnalyteCodeAdapter jLAC10AnalyteCodeAdapter = new JLAC10AnalyteCodeAdapter(dao.getConnection());
					JLAC10AnalyteCodeEntity jLAC10AnalyteCodeEntity = new JLAC10AnalyteCodeEntity();
					jLAC10AnalyteCodeEntity = jLAC10AnalyteCodeAdapter.findRecord(code);
					// JLAC11分析物マスタに含まれるか確認
					JLAC11AnalyteCodeAdapter jLAC11AnalyteCodeAdapter = new JLAC11AnalyteCodeAdapter(dao.getConnection());
					JLAC11AnalyteCodeEntity jLAC11AnalyteCodeEntity = new JLAC11AnalyteCodeEntity();
					jLAC11AnalyteCodeEntity = jLAC11AnalyteCodeAdapter.findRecord(code);
					if (jLAC10AnalyteCodeEntity == null && jLAC11AnalyteCodeEntity == null) {
						status = "2";
						ic++;
						count++;
						errorList.add("[" + importHIstoryId + "]" + "検査項目が取得できない検査が存在します。Patient.id = " +
								patient.getId() + "、ServiceRequest.id = " + serviceRequest.getId() +
								"、Observation.id = " + observation.getId());
						logger.error("[" + importHIstoryId + "]" + "検査項目が取得できない検査が存在します。Patient.id = " +
								patient.getId() + "、ServiceRequest.id = " + serviceRequest.getId() +
								"、Observation.id = " + observation.getId());
						//同一のServiceRequestリソースに含まれるすべての検査項目が存在しない場合
						if (count == sum) {
							errorList.add("[" + importHIstoryId + "]" + "検査オーダに含まれるすべての検査項目が取得できません。Patient.id = " +
									patient.getId() + "、ServiceRequest.id = " + serviceRequest.getId());
							logger.error("[" + importHIstoryId + "]" + "検査オーダに含まれるすべての検査項目が取得できません。Patient.id = " +
									patient.getId() + "、ServiceRequest.id = " + serviceRequest.getId());
						}
						// Observationリソースのうち1つでもエラーが含まれている場合の新規登録処理
						List<ObservationEntity> insertAlertEntity = new ArrayList<>();
						AutoCalcService insertAutoCalcService = new AutoCalcService();
						ObservationEntryService ies = new ObservationEntryService();
						if (insertCount == ic) {
							// アラート設定
							insertAlertEntity = insertAutoCalcService.autoCalcSet(insertEventEntity.getPHR_ID(), 
									insertEventEntity.getInsurerNo(), insertEventEntity.getYear(), 
									insertEventEntity.getExaminationDate(), insertCountEntity);
							// 新規登録処理
							ies.insertObservationAndObservationEvent(insertEventEntity, insertAlertEntity);
						}
						continue;
					}
//					}
					
					
					// 検査項目IDからJLAC10コードを取得
					String jlac10 = null;
//					ObservationDefinitionJlac10Adapter observationDefinitionJlac10Adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
//					ObservationDefinitionJlac10Entity observationDefinitionJlac10Entity = new ObservationDefinitionJlac10Entity();
//					observationDefinitionJlac10Entity = observationDefinitionJlac10Adapter.findByPrimaryKey(examCode);
//					if (observationDefinitionJlac10Entity != null) {
//						jlac10 = observationDefinitionJlac10Entity.getJLAC10();
//					}
					if (j10e != null) {
						jlac10 = examCode;
						examCode = j10e.getObservationDefinitionId();
					} else if (j11e != null) {
						jlac10 = j11e.getJLAC10();
						examCode = j11e.getObservationDefinitionId();
                    } else {
                        jlac10 = examCode;
                    }
					
					// 更新・登録用entity
					ObservationEventEntity eventEntity = new ObservationEventEntity();
					ObservationEntity entity = new ObservationEntity();
					
					//Alertの設定
					AutoCalcService autoCalcService = new AutoCalcService();
					List<ObservationEntity> alertEntity = new ArrayList<>();
					List<ObservationEntity> insertEntity = new ArrayList<>();
					List<ObservationEntity> updateEntity = new ArrayList<>();
					ObservationEntryService es = new ObservationEntryService();
					
					// 検査結果情報テーブルからレコードを取得
					ObservationEventAdapter observationEventAdapter = new ObservationEventAdapter(dao.getConnection());
					ObservationAdapter observationAdapter = new ObservationAdapter(dao.getConnection());
					ObservationEventEntity observationEventEntity = new ObservationEventEntity();
					ObservationEntity observationEntity = new ObservationEntity();
					observationEventEntity = observationEventAdapter.findByOrderNo(examRequestId);
					if (observationEventEntity != null) {
						// 検査項目結果情報テーブルからレコードを取得
						observationEntity = observationAdapter.findByPrimaryKey(observationEventEntity.getObservationEventId(), examCode);
						// 同一の検査依頼に疾病管理項目を含んでいるかを確認
						if (diseaseManagementFlg) {
							if (!observationEventEntity.isDiseaseManagementTargetFlg()) {
								observationEventAdapter.updateDiseaseManagementTargetFlg(observationEventEntity.getObservationEventId());
							}
						}
						if (observationEntity != null) {
							// 検査項目結果情報レコードの更新情報を作成
							entity.setObservationEventId(observationEntity.getObservationEventId());
							entity.setObservationDefinitionId(examCode);
							entity.setJLAC10(jlac10);
							if (value != null) {
								entity.setValue(value);
							}
							if (low != null) {
								entity.setMinReferenceValue(Double.parseDouble(low));
							}
							if (high != null) {
								entity.setMaxReferenceValue(Double.parseDouble(high));
							}
							entity.setUpdateDateTime(observationEntity.getUpdateDateTime());
							entity.setDiseaseManagementTargetFlg(diseaseManagementFlg);
							entity.setUnit(unit);
							// アラート設定
							updateEntity.add(entity);
							alertEntity = autoCalcService.autoCalcSet(observationEventEntity.getPHR_ID(), 
									observationEventEntity.getInsurerNo(), observationEventEntity.getYear(), 
									observationEventEntity.getExaminationDate(), updateEntity);
							// 更新処理
							es.updateObservationAndObservationEvent(updateEntity, insertEntity);
							
						} else {
							// 検査項目結果情報レコードの新規登録情報を作成
							entity.setObservationEventId(observationEventEntity.getObservationEventId());
							entity.setObservationDefinitionId(examCode);
							entity.setJLAC10(jlac10);
							if (value != null) {
								entity.setValue(value);
							}
							if (low != null) {
								entity.setMinReferenceValue(Double.parseDouble(low));
							}
							if (high != null) {
								entity.setMaxReferenceValue(Double.parseDouble(high));
							}
							entity.setDiseaseManagementTargetFlg(diseaseManagementFlg);
							entity.setUnit(unit);
							// アラート設定
							insertEntity.add(entity);
							alertEntity = autoCalcService.autoCalcSet(observationEventEntity.getPHR_ID(), 
									observationEventEntity.getInsurerNo(), observationEventEntity.getYear(), 
									observationEventEntity.getExaminationDate(), insertEntity);
							IAlertSetService a_service = new AlertSetService();
							a_service.alertSet(observationEventEntity.getPHR_ID(), observationEventEntity.getInsurerNo(), null,
									insertEntity, observationEventEntity.getExaminationDate()); 
							// 新規登録処理
							es.updateObservationAndObservationEvent(updateEntity, insertEntity);
						}
					} else {
						// 全ての検査結果を新規情報として新規登録情報を作成
						// ObservationEventテーブルに登録する新規情報を作成
						eventEntity.setDataInputTypeCd(1);
						eventEntity.setPHR_ID(phr_Id);
						eventEntity.setExaminationDate(ts);
						if (examDate != null) {
							eventEntity.setYear(Integer.parseInt(examDate.substring(0, 4)));
						}
						eventEntity.setInsurerNo(insurerNo);
						if (examRequestId != null) {
							eventEntity.setOrderNo(examRequestId);
						}
						if (medicalOrganizationCd != null) {
							eventEntity.setMedicalOrganizationCd(medicalOrganizationCd);
						}
						if (diseaseManagementFlg) {
							eventEntity.setDiseaseManagementTargetFlg(diseaseManagementFlg);
						}
						// エラーが存在する場合用のObservationEventテーブルに登録する新規情報を作成
						insertEventEntity.setDataInputTypeCd(1);
						insertEventEntity.setPHR_ID(phr_Id);
						insertEventEntity.setExaminationDate(ts);
						if (examDate != null) {
							insertEventEntity.setYear(Integer.parseInt(examDate.substring(0, 4)));
						}
						insertEventEntity.setInsurerNo(insurerNo);
						if (examRequestId != null) {
							insertEventEntity.setOrderNo(examRequestId);
						}
						if (medicalOrganizationCd != null) {
							insertEventEntity.setMedicalOrganizationCd(medicalOrganizationCd);
						}
						if (diseaseManagementFlg) {
							insertEventEntity.setDiseaseManagementTargetFlg(diseaseManagementFlg);
						}
						
						// Observationテーブルに登録する新規情報を作成
						entity.setObservationDefinitionId(examCode);
						if (jlac10 != null) {
							entity.setJLAC10(jlac10);
						}
						if (value != null) {
							entity.setValue(value);
						}
						if (low != null) {
							entity.setMinReferenceValue(Double.valueOf(low));
						}
						if (high != null) {
							entity.setMaxReferenceValue(Double.valueOf(high));
						}
						entity.setDiseaseManagementTargetFlg(diseaseManagementFlg);
						entity.setUnit(unit);
						insertCountEntity.add(entity);
						ic++;
						if (insertCount == ic) {
							// アラート設定
							alertEntity = autoCalcService.autoCalcSet(eventEntity.getPHR_ID(), 
									eventEntity.getInsurerNo(), eventEntity.getYear(), 
									eventEntity.getExaminationDate(), insertCountEntity);
							// 新規登録処理
							es.insertObservationAndObservationEvent(eventEntity, alertEntity);
						}
					}
				}
			}
		}
		// エラーリストが空でない場合、テキストファイルとして出力
		if (!errorList.isEmpty()) {
			this.createFile(importHIstoryId, errorList);
		}
		
		logger.debug("End");
		return status;
	}
	
	/**
	 * 検査結果取得履歴情報更新処理
	 * @throws Throwable
	 */
	public void update(String importHIstoryId, String status) throws Throwable{
		logger.debug("Start");
		
		// 検査結果取得履歴情報テーブルから該当レコードを取得
		ObservationImportHistoryEntity entity = new ObservationImportHistoryEntity();
		ObservationImportHistoryAdapter adapter = new ObservationImportHistoryAdapter();
		entity = adapter.getRecord(importHIstoryId);
		
		// ステータスをセットして更新処理
		entity.setStatus(status);
		adapter.update(entity);
		
		logger.debug("End");
	}
	
	/**
	 * FRUCtoSサーバへのリクエスト処理
	 * @param url
	 * @return result
	 * @throws Throwable
	 */
	public String getRequest(URL url, String accessToken) throws Throwable{
		String result = new String();
		logger.debug("Start");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Bearer " + accessToken);
		connection.connect();
		
		logger.debug("RequestMethod: GET");
		logger.debug("Authorization: Bearer " + accessToken);

		int responseCode = connection.getResponseCode();
		logger.debug("HTTP_STATUS: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream in = connection.getInputStream();
			String encoding = connection.getContentEncoding();
			if (null == encoding) {
				encoding = "UTF-8";
				}

			StringBuffer ret = new StringBuffer();
			final InputStreamReader inReader = new InputStreamReader(in, encoding);
			final BufferedReader bufReader = new BufferedReader(inReader);

			String line = null;
			while ((line = bufReader.readLine()) != null) {
				ret.append(line);
				ret.append("\r\n");
			}

			bufReader.close();
			inReader.close();
			in.close();
			result = ret.toString();
		}
		
		logger.debug("End");
		return result;
	}
	
	/**
	 * エラーリストをテキストファイルとして出力する
	 * @param importHistoryId
	 */
	public void createFile(String importHistoryId, List<String> errorList) {
		logger.debug("Start");
		
		// カレントディレクトリのパス
		Path p1 = Paths.get("");
		Path p2 = p1.toAbsolutePath();
		
		// 保存先ディレクトリの作成
		File optDir = new File(p2 + "/opt");
		if (optDir.exists() == false) {
			optDir.mkdir();
		}
		File dataDir = new File(optDir + "/data");
		if (dataDir.exists() == false) {
			dataDir.mkdir();
		}
		File errorDir = new File(dataDir + "/error");
		if (errorDir.exists() == false) {
			errorDir.mkdir();
		}
		
		// テキストファイル名
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		String fileName = "ObservationResultImportErrorList_" + dtformat.format(date) + "_" + importHistoryId.substring(9, 15);
		
		// テキストファイル作成
		File file = new File(errorDir + "/" + fileName + ".txt");
		try {
			FileWriter filewriter = new FileWriter(file);
			for (String s : errorList) {
				filewriter.write(s);
				filewriter.write("\r\n");
			}
			filewriter.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			logger.error(e.toString(), e);
			e.printStackTrace();
		}
		
		logger.debug("End");
	}
}
