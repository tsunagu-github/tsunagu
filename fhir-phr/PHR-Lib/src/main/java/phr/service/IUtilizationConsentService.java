/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;

import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.UtilizationConsentEntity;

/**
 *
 */
public interface IUtilizationConsentService {
	
	/**
	 * PHR_IDで活用同意一覧テーブルからレコードを取得
	 * @param phr_id
	 * @param type
	 * @return list
	 */
	public List<UtilizationConsentEntity> findByPhrId(String phr_id, String type) throws Throwable;
	
	/**
	 * PHR_IDで活用同意一覧テーブルからレコードを取得（降順）
	 * @param phr_id
	 * @param type
	 * @return list
	 */
	public List<UtilizationConsentEntity> findByPhrIdDesc(String phr_id, String type) throws Throwable;
	
	/**
	 * 活用同意一覧テーブルを更新する
	 * @param phr_id
	 * @param studyId
	 * @param responseStatus
	 * @param subjectId
	 * @return rowCount
	 */
	public int updateRecord(String phr_id, String studyId, String responseStatus, String subjectId) throws Throwable;

	/**
	 * PHR_IDから患者情報を取得する
	 * @param phr_id
	 * @return entity
	 */
	public PatientEntity getPatient(String phr_id) throws Throwable;

    
    /**
     * 研究名と状態にて患者情報を取得
     * @param studyName
     * @param responseStatusList
     * @return
     * @throws Throwable
     */
    public List<UtilizationConsentEntity> findByStudyNameResponseStatus(String studyName, List<String> responseStatusList) throws Throwable;
    
    /**
     * 活用同意一覧テーブルの通知日を更新し、NewArrivalFlg,Invalidを0にする
     * @param phr_id
     * @param studyId
     * @param responseStatus
     * @return rowCount
     */
    public int updateRecord(String phr_id, String studyId, String SubjectId) throws Throwable;
}
