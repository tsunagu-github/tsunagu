/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DiseaseTypeAdapter;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.ObservationDefinitionInsurerAdapter;
import phr.datadomain.adapter.ObservationDefinitionTypeAdapter;
import phr.datadomain.adapter.ObsevationLabelAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.InsurerViewDefinitionAdapter;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationLabelEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.ITargetingPatientInfoService;

/**
 *
 * @author KISNOTE011
 */
public class TargetingPatientInfoService implements ITargetingPatientInfoService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UserAuthenticationService.class);

    /*
    * 日付処理
    */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");    /**

    /*** 患者情報を取得します。
     * @param phrid     取得対象のPHRID
     * @throws Throwable
     */
    @Override
    public PatientEntity getPatientInfo(String phrid) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject();
        PatientEntity rtnPatientEntity = null;
    	try{
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            dao.beginTransaction();
            PatientEntity account = adapter.findByPrimaryKey(phrid);

            rtnPatientEntity = account;
    	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	}finally{
            if (dao != null) {
                dao.close();
            }
        }

        logger.trace("end");
        return rtnPatientEntity;
    }
    
    /*
    * 年度から検査項目を取得します。
    */

    public List<ObservationLabelEntity> getObservationLabel(String insurerno ,int year) throws Throwable {
        
        DataAccessObject dao = new DataAccessObject();
        List<ObservationLabelEntity> observationList = null;
    	try{
            ObsevationLabelAdapter adapter = new ObsevationLabelAdapter(dao.getConnection());
            dao.beginTransaction();
            
            observationList = adapter.findByLabel(insurerno,year);

     	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	}finally{
            if (dao != null) {
                dao.close();
            }
        }

        logger.trace("end");
        return observationList;
    }

     /**
     * PHRIDから保険者患者関連情報の取得
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    public String getInsurerPatientInfo(String phrId) throws Throwable {
        InsurerPatientEntity entity = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
            entity = adapter.findByPhrId(phrId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return entity.getInsurerNo();
    }
    
    /**
     * Viewリストを取得する
     * 全検査を追加するので注意
     * 
     * @return
     * @throws Throwable
     */
    public List<InsurerViewDefinitionEntity> getViewList(String insurerNo) throws Throwable{
        List<InsurerViewDefinitionEntity> entityList = new ArrayList<>();
        DataAccessObject dao = null;
        
        try {
            dao = new DataAccessObject();
            InsurerViewDefinitionAdapter adapter = new InsurerViewDefinitionAdapter(dao.getConnection());
            entityList = adapter.findViewList(insurerNo);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        return entityList;
    }

    @Override
    public List<DiseaseTypeEntity> getDiseaseTypeList(int viewId) throws Throwable {
        List<DiseaseTypeEntity> entityList = new ArrayList<>();
        DataAccessObject dao = null;
        
        try {
            dao = new DataAccessObject();
            DiseaseTypeAdapter adapter = new DiseaseTypeAdapter(dao.getConnection());
            entityList = adapter.findTypeList(viewId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
                
        return entityList;   
    }

    @Override
    public List<ObservationDefinitionInsurerEntity> getitemList(int viewId) throws Throwable {
        List<ObservationDefinitionInsurerEntity> entityList = new ArrayList<>();
        DataAccessObject dao = null;
        
        try {
            dao = new DataAccessObject();
            ObservationDefinitionInsurerAdapter adapter = new ObservationDefinitionInsurerAdapter(dao.getConnection());
            entityList = adapter.findItemList(viewId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
                
        return entityList;       
    }

    @Override
    public List<ObservationDefinitionTypeEntity> getitemTypeList(String insurerNo, int type) throws Throwable {
        List<ObservationDefinitionTypeEntity> entityList = new ArrayList<>();
        DataAccessObject dao = null;
        
        try {
            dao = new DataAccessObject();
            ObservationDefinitionTypeAdapter adapter = new ObservationDefinitionTypeAdapter(dao.getConnection());
            List<ObservationDefinitionTypeEntity> returnList = adapter.findByInsurerList(insurerNo , type);
            //リストの重複を排除
            entityList = distinctList(returnList);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
                
        return entityList;       
    }
    
    private List<ObservationDefinitionTypeEntity> distinctList(List<ObservationDefinitionTypeEntity> getlist){
        List<ObservationDefinitionTypeEntity> entityList = new ArrayList<>();
        for(int i = 0;i<getlist.size();i++){
            boolean flg = true;
            String id = getlist.get(i).getObservationDefinitionId();
            for(ObservationDefinitionTypeEntity entity:entityList){
                if(entity.getObservationDefinitionId().equals(id)){
                    flg = false;
                }
            }
            if(flg){
                entityList.add(getlist.get(i));
            }
        }
        return entityList;
    }
}
