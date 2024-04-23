/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.UtilizationConsentEntity;

/**
 *
 * @author daisuke
 */
public interface IPatientManagementService {

    /**
     * 利用者情報を取得する
     * @param phrId
     * @return 
     * @throws Throwable 
     */
    PatientEntity getPatient(String phrId) throws Throwable;
    /**
     * PHRIDを採番する
     * @param entity
     * @return 
     * @throws Throwable
     */
    String createPatient(PatientEntity entity) throws Throwable;
    /**
     * 利用者情報を更新する
     * @param entity
     * @throws Throwable 
     */
    void updatePatient(PatientEntity entity) throws Throwable;
    
    /**
     * 指定の保険者と対応する患者のPHRIDリストを取得する。
     * @param insurerNo
     * @return
     * @throws Throwable 
     */
    List<String> getInsurerPatientList(String insurerNo) throws Throwable;
    
    /**
     * PHR-ID一覧に対応する患者一覧を取得する。
     * @param phrId
     * @return
     * @throws Throwable 
     */
    List<PatientEntity> getPatientListByIdList(List<String> phrId) throws Throwable;
    
    /**
     * 保険者ごとの条件に対応する患者一覧を習得する。
     * @param phrId
     * @param familyName
     * @param givenName
     * @param familyKana
     * @param givenKana
     * @return
     * @throws Throwable 
     */
    List<PatientEntity> getPatientList(String insurerNo, String phrId, String familyName, String givenName, String familyKana,String givenKana) throws Throwable;
    
    List<PatientEntity> findPatientListByIdList(List<String> phrIdList) throws Throwable;
    
    /**
     * 有効な研究情報レコードを取得する
     * @return list
     * @throws Throwable
     */
    public List<StudyInformationEntity> getStudyInformation() throws Throwable;
    
//    /**
//     * 登録するための活用同意一覧レコードのリストを作成する
//     * @param phr_id
//     * @param studyInformationEntityList
//     * @return utilizationConsentEntityList
//     * @throws Throwable
//     */
//    public List<UtilizationConsentEntity> createList(String phr_id, List<StudyInformationEntity> studyInformationEntityList) throws Throwable;
    
    /**
     * 活用同意一覧レコードのリストをDBに登録する
     * @param list
     * @return rowCount
     * @throws Throwable
     */
    public int insertList(List<UtilizationConsentEntity> list) throws Throwable;
}
