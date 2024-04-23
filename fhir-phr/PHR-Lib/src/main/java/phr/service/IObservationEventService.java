/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationEventEntity;

/**
 *
 * @author KISNOTE011
 */
public interface IObservationEventService {
    /**
     * 対象年度の検査結果から基準日の直近の値を検索する
     * @param iYear 対象年度
     * @param phrid 対象のPHRID
     * @param basedate 対象の基準日
     * @param viewId
     * @return
     * @throws Throwable 
     */
    List<ObservationEventEntity> searchObsevationByOrderNo(int iYear , String phrid,Timestamp basedate , Integer viewId) throws Throwable;
    /**
     *  対象年度の検査結果から基準日の直近の値を検索する
     * @param iYear
     * @param phrid
     * @param basedate
     * @param dataInputTypeCd
     * @param viewId
     * @return
     * @throws Throwable 
     */
    List<ObservationEventEntity> searchObsevationByOrderNo(int iYear , String phrid,Timestamp basedate, Integer dataInputTypeCd , Integer viewId) throws Throwable;
    
    /**
     * 疾病種別のKeyValueペアのリストを取得する
     * @return
     * @throws Throwable 
     */
    List<DiseaseTypeEntity> searchDiseaseType(int viewId) throws Throwable;

    
    /**
     * 年度、保険者番号で項目疾病情報のMapを取得する
     *    ([疾病種別CD] = [項目IDリスト])
     * @param year
     * @param phrid
     * @param dataInputTypeCd
     * @return
     * @throws Throwable 
     */
    Map<Integer, List<String>> searchObservationDefinitionDiseaseForMap(int year, String phrid, Integer dataInputTypeCd) throws Throwable;
    
    /**
     * 年度、保険者番号で項目疾病情報のMapを取得する
     *    ([疾病種別CD] = [項目IDリスト])
     * @param viewid
     * @param dataInputTypeCd
     * @return
     * @throws Throwable 
     */
    Map<Integer, List<String>> searchObservationDefinitionDiseaseForMap(int viewId, Integer dataInputTypeCd) throws Throwable;    
    
    /**
     * 保険者番号、年度にて管理項目種別を検索します。
     * @param insurerNo
     * @param year
     * @return
     * @throws Throwable
     */
    List<ObservationDefinitionTypeEntity> searchObservationDefinitionType(String insurerNo, int year) throws Throwable;  

    /**
     * 利用者番号、年度にて管理項目種別を検索します。
     * @param insurerNo
     * @param phrId     * 
     * @param viewId
     * @return
     * @throws Throwable
     */
    List<ObservationDefinitionTypeEntity> searchObservationDefinitionType(String insurerNo,String phrId, int viewId) throws Throwable;
    
    /**
     *  基準日から検査結果の直近未来日を検索
     * @param phrid
     * @param basedate
     * @param searchType
     * @return
     * @throws Throwable 
     */
    List<ObservationEventEntity> searchObsevationByFutureDay(String phrid,Timestamp basedate,int searchType) throws Throwable;
    
    /**
     * viewIdリストを取得する
     * @return
     * @throws Throwable 
     */
    List<InsurerViewDefinitionEntity> getViewIdList(int year, String phrid) throws Throwable;
    
    /**
     * 検査結果のEnum値を設定する。
     * @param observationList
     * @return
     * @throws Throwable 
     */
    List<ObservationEventEntity> setObservationEnumValue(List<ObservationEventEntity> observationList, int viewId) throws Throwable;
    
    /**
     * PHR_IDからEventIDの一覧を検索します。
     * @param phrId
     * @return
     * @throws Throwable 
     */
    Map<String, String> getEventIdandDateList(String phrId) throws Throwable;
    
    /**
     * PHR_IDから全検査タブのEventIDの一覧を検索します。
     * @param phrId
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> searchObservationEventList(String phrId) throws Throwable;
    /**
     * PHR_IDから全検査タブのEventIDの一覧を検索します。
     * @param phrId
     * @return
     * @throws Throwable
     */
    List<String> searchObservationEventId(String phrId) throws Throwable;
    
    /**
     * PHR_IDからObservationEventEntityリストを取得します。
     * @param phrId
     * @return
     * @throws Throwable 
     */
    public List<ObservationEventEntity> findByPhrId(String phrId) throws Throwable;
    
    /**
     * PHR_IDとDataInputTyepCdの値でObservationEventIdリストを取得
     * @param phr_id
     * @param dataInputTypeCd
     * @return list
     * @throws Throwable 
     */
    public List<String> findByPhridAndDatainputtypecd(String phr_id, String dataInputTypeCd) throws Throwable;
    
    /**
     * ObservationDefinitionIdとenumValueからenumNameの値を取得
     * @param observationEventId
     * @param enumValue
     * @param viewId
     * @return enumName
     * @throws Throwable 
     */
    public String findByIdAndEnum(String observationDefinitionId, String enumValue, int viewId) throws Throwable;
}
