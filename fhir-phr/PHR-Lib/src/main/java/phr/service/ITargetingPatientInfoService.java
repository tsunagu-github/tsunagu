/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationLabelEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author KISNOTE011
 */
public interface ITargetingPatientInfoService {
    /**
     * PHRIDから患者情報を取得する
     * @param phrid
     * @return
     * @throws Throwable 
     */
    public PatientEntity getPatientInfo(String phrid)throws Throwable;
   
    /**
     * PHRIDから保険者患者関連情報の取得
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    public String getInsurerPatientInfo(String phrId) throws Throwable;   
    
    /**
     * Viewリストを取得する
     *
     * @return
     * @throws Throwable
     */
    public List<InsurerViewDefinitionEntity> getViewList(String insurerNo) throws Throwable;

    /**
     * 管理疾病リストを取得する
     *
     * @return
     * @throws Throwable
     */
    public List<DiseaseTypeEntity> getDiseaseTypeList(int viewId) throws Throwable;    
    
    /**
     * Viewの項目リストを取得する
     *
     * @param viewId
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionInsurerEntity> getitemList(int viewId) throws Throwable;    

    /**
     * 家庭測定や特定健診の項目リストを取得する
     *
     * @param insurerNo
     * @param type
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionTypeEntity> getitemTypeList(String insurerNo , int type) throws Throwable;       
}
