/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DiseaseTypeAdapter;
import phr.datadomain.adapter.InsurerDiseaseTypeAdapter;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.ObservationDefinitionEnumValueAdapter;
import phr.datadomain.adapter.ObservationDefinitionTypeAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.adapter.InsurerViewDefinitionAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerDiseaseTypeEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionEnumValueEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IObservationEventService;

/**
 *
 * @author KISNOTE011
 */
public class ObservationEventService implements IObservationEventService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UserAuthenticationService.class);

    /**
     *  対象年度の検査結果から基準日の直近の値を検索する
     * @param iYear
     * @param phrid
     * @param basedate
     * @param viewId
     * @return
     * @throws Throwable 
     */
    @Override
    public List<ObservationEventEntity> searchObsevationByOrderNo(int iYear , String phrid,Timestamp basedate , Integer viewId) throws Throwable{
        return this.searchObsevationByOrderNo(iYear, phrid, basedate, -1 , viewId);
    }

    /**
     *  対象年度の検査結果から基準日の直近の値を検索する
     * @param iYear
     * @param phrid
     * @param basedate
     * @param dataInputTypeCd
     * @return
     * @throws Throwable 
     */
    @Override
    public List<ObservationEventEntity> searchObsevationByOrderNo(int iYear , String phrid,Timestamp basedate, Integer dataInputTypeCd , Integer viewId) throws Throwable {
        List<ObservationEventEntity> entities = new ArrayList<>();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            entities = adapter.findByMostRecent(iYear, phrid, basedate, viewId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        return entities;
    }
    /**
     * 疾病種別のKeyValueペアのリストを取得する
     * @return
     * @throws Throwable 
     */
    @Override
    public List<DiseaseTypeEntity> searchDiseaseType(int viewId) throws Throwable {
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            DiseaseTypeAdapter diseaseTypeAdapter = new DiseaseTypeAdapter(dao.getConnection());
            List<DiseaseTypeEntity> typeList = diseaseTypeAdapter.findTypeList(viewId);

            return typeList;
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }       
    }
   
    /**
     * 年度、保険者番号で項目疾病情報のMapを取得する
     *    ([疾病種別CD] = [項目IDリスト])
     * @param year
     * @param phrid
     * @param dataInputTypeCd
     * @return
     * @throws Throwable 
     */
    @Override
    public Map<Integer, List<String>> searchObservationDefinitionDiseaseForMap(int year, String phrid, Integer dataInputTypeCd) throws Throwable {
        DataAccessObject dao = null;
        try {
             dao = new DataAccessObject();

            InsurerPatientAdapter insurerPatientAdapter = new InsurerPatientAdapter(dao.getConnection());
            //ObservationDefinitionDiseaseAdapter observationDefinitionDiseaseAdapter = new ObservationDefinitionDiseaseAdapter(dao.getConnection());
            InsurerViewDefinitionAdapter insureViewDefAdapter = new InsurerViewDefinitionAdapter(dao.getConnection());
            InsurerDiseaseTypeAdapter diseaseTypeAdapter = new InsurerDiseaseTypeAdapter(dao.getConnection()); 
            
            InsurerPatientEntity insurerPatientEntity = insurerPatientAdapter.findByPhrId(phrid);
            
            //疾病タイプ(DiseaseTypeCd)ごとの管理項目取得
//            List<ObservationDefinitionDiseaseEntity> list = 
//                    observationDefinitionDiseaseAdapter.findByInsurerNoYear(insurerPatientEntity.getInsurerNo(), year);
            List<InsurerViewDefinitionEntity> viewDefList = insureViewDefAdapter.findViewListbyInsureAndYear(insurerPatientEntity.getInsurerNo(),year);
            List<Integer> viewIdList = new ArrayList();
            for(InsurerViewDefinitionEntity viewDefEnt:viewDefList){
                viewIdList.add(viewDefEnt.getViewId());
            }
            List<InsurerDiseaseTypeEntity> list = diseaseTypeAdapter.findDiseaseTypeListByViewIdList(viewIdList);

            if (dataInputTypeCd != null) {
                list = list.stream().filter(
                                         x -> x.getDataInputTypeCd() == dataInputTypeCd
                                    ).collect(Collectors.toList());
            }
            // Mapにする
            Map<Integer, List<String>> map = new LinkedHashMap<>();
//            for (ObservationDefinitionDiseaseEntity entity : list) {
            for (InsurerDiseaseTypeEntity entity : list) {    
                if (!map.containsKey(entity.getDiseaseTypeCd()))
                    map.put(entity.getDiseaseTypeCd(), new ArrayList<>());
                
                List<String> mapList =  map.get(entity.getDiseaseTypeCd());
                mapList.add(entity.getObservationDefinitionId());
                
            }
            // 共通を他の疾病にマージする
            Map<Integer, List<String>> diseaseMap = new LinkedHashMap<>();
            List<String> comList = map.get(0);
            if(comList!=null){
                diseaseMap.put(0, comList);
            }
            int i = 1;
            while(true) {
                if (!map.containsKey(i))
                    break;
                List<String> diseaseList = new ArrayList<>();
                List<String> mapList = map.get(i);
                if(comList!=null){
                    diseaseList.addAll(comList);
                }
                diseaseList.addAll(mapList);
                diseaseMap.put(i, diseaseList);
                i++;
            }
            return diseaseMap;

        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
    }
    
    @Override
    public Map<Integer, List<String>> searchObservationDefinitionDiseaseForMap(int viewId, Integer dataInputTypeCd) throws Throwable {
        DataAccessObject dao = null;
        try {
             dao = new DataAccessObject();

            InsurerDiseaseTypeAdapter diseaseTypeAdapter = new InsurerDiseaseTypeAdapter(dao.getConnection()); 

            
            //疾病タイプ(DiseaseTypeCd)ごとの管理項目取得
            List<InsurerDiseaseTypeEntity> list = diseaseTypeAdapter.findDiseaseTypeListByViewId(viewId);

            if (dataInputTypeCd != null) {
                list = list.stream().filter(
                                         x -> x.getDataInputTypeCd() == dataInputTypeCd
                                    ).collect(Collectors.toList());
            }
            // Mapにする
            Map<Integer, List<String>> map = new LinkedHashMap<>();
//            for (ObservationDefinitionDiseaseEntity entity : list) {
            for (InsurerDiseaseTypeEntity entity : list) {    
                if (!map.containsKey(entity.getDiseaseTypeCd()))
                    map.put(entity.getDiseaseTypeCd(), new ArrayList<>());
                
                List<String> mapList =  map.get(entity.getDiseaseTypeCd());
                mapList.add(entity.getObservationDefinitionId());
                
            }
            // 共通を他の疾病にマージする
            Map<Integer, List<String>> diseaseMap = new LinkedHashMap<>();
            List<String> comList = map.get(0);
            if(comList!=null){
                diseaseMap.put(0, comList);
            }
            int i = 1;
            while(true) {
//                // 「6:脳卒中」の時の処理を追加（※5が追加された場合ここは修正する必要あり）
//                if (i != 5) {
//                    if (!map.containsKey(i))
//                        break;
//                    List<String> diseaseList = new ArrayList<>();
//                    List<String> mapList = map.get(i);
//                    if(comList!=null){
//                        diseaseList.addAll(comList);
//                    }
//                    diseaseList.addAll(mapList);
//                    diseaseMap.put(i, diseaseList);
//                    i++;
//                } else {
//                    i++;
//                }
                if (!map.containsKey(i))
                    break;
                List<String> diseaseList = new ArrayList<>();
                List<String> mapList = map.get(i);
                if(comList!=null){
                    diseaseList.addAll(comList);
                }
                diseaseList.addAll(mapList);
                diseaseMap.put(i, diseaseList);
                i++;
            }
            return diseaseMap;

        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
    }    
    
    
   /**
     * 保険者番号、年度にて管理項目種別を検索します。
     * @param insurerNo
     * @param viewId
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionTypeEntity> searchObservationDefinitionType(String insurerNo, int viewId) throws Throwable {
        DataAccessObject dao = null;
        try {
             dao = new DataAccessObject();

            ObservationDefinitionTypeAdapter adapter = new ObservationDefinitionTypeAdapter(dao.getConnection());
            List<ObservationDefinitionTypeEntity> list = adapter.findByInsurerNoYear(insurerNo, viewId, 1);
            return list;
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }        
    }        

    /**
     *  基準日から検査結果の直近日を検索（未来[0]過去[1]条件指定）
     * @param phrid
     * @param basedate
     * @param searchType
     * @return
     * @throws Throwable 
     */
    @Override
    public List<ObservationEventEntity> searchObsevationByFutureDay(String phrid, Timestamp basedate,int searchType) throws Throwable {
        List<ObservationEventEntity> entities = new ArrayList<>();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            entities = adapter.findByFutureDay(phrid, basedate, searchType);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        return entities;
    }

    @Override
    public List<InsurerViewDefinitionEntity> getViewIdList(int year, String phrid) throws Throwable {
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();

            InsurerViewDefinitionAdapter insureViewDefAdapter = new InsurerViewDefinitionAdapter(dao.getConnection());
            InsurerPatientAdapter insurerPatientAdapter = new InsurerPatientAdapter(dao.getConnection());
            
            InsurerPatientEntity insurerPatientEntity = insurerPatientAdapter.findByPhrId(phrid);
            List<InsurerViewDefinitionEntity> viewDefList = insureViewDefAdapter.findViewListbyInsureAndYear(insurerPatientEntity.getInsurerNo(),year);
                        
            return viewDefList;
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }       
        
        
        
    }

    @Override
    public List<ObservationEventEntity> setObservationEnumValue(List<ObservationEventEntity> observationList, int viewId) throws Throwable {
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationDefinitionEnumValueAdapter adapter = new ObservationDefinitionEnumValueAdapter((dao.getConnection()));
            List<ObservationDefinitionEnumValueEntity> enumvalueList = adapter.findByViewId(viewId);
            for(ObservationEventEntity obEntity:observationList){
                if(obEntity.getObservationValue()==null || obEntity.getObservationValue().isEmpty()){
                }else{
                    for(ObservationDefinitionEnumValueEntity enumEnt:enumvalueList ){
                        if(obEntity.getObservationDefinitionId().equals(enumEnt.getObservationDefinitionId())){
                            if(obEntity.getObservationValue()==enumEnt.getEnumValue() || obEntity.getObservationValue().equals(enumEnt.getEnumValue())){
                                obEntity.setObservationValue(enumEnt.getEnumName());
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        return observationList;
    }

    @Override
    public List<ObservationDefinitionTypeEntity> searchObservationDefinitionType(String insurerNo, String phrId, int viewId) throws Throwable {
        DataAccessObject dao = null;
        try {
             dao = new DataAccessObject();
             
             if(insurerNo==null ||insurerNo.isEmpty() ){
                InsurerPatientAdapter insurerPatientAdapter = new InsurerPatientAdapter(dao.getConnection());

                InsurerPatientEntity insurerPatientEntity = insurerPatientAdapter.findByPhrId(phrId);  
                insurerNo = insurerPatientEntity.getInsurerNo();
             }

            ObservationDefinitionTypeAdapter adapter = new ObservationDefinitionTypeAdapter(dao.getConnection());
            List<ObservationDefinitionTypeEntity> list = adapter.findByInsurerNoYear(insurerNo, viewId, 1);
            return list;
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
    }

    @Override
    public Map<String, String> getEventIdandDateList(String phrId) throws Throwable {
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            if(phrId==null){
                return null;
            }
            String insurerNo = phrId.substring(0, 7);
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            Map<String, String> maps = adapter.getEventIdandDateList(phrId, insurerNo);
            return maps;
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
    }

    /**
     * PHR_IDから全検査タブのEventIDの一覧を検索します。
     * @param phrId
     * @return
     * @throws Throwable 
     */
    @Override
    public List<ObservationEventEntity> searchObservationEventList(String phrId) throws Throwable{
        List<ObservationEventEntity> observationEventIdList = new ArrayList<ObservationEventEntity>();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            observationEventIdList = adapter.findObservationEventList(phrId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        
        return observationEventIdList;
    }

    /**
     * PHR_IDから全検査タブのEventIDの一覧を検索します。
     * @param phrId
     * @return
     * @throws Throwable 
     */
    @Override
    public List<String> searchObservationEventId(String phrId) throws Throwable{
        List<String> observationEventIdList = new ArrayList<String>();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            observationEventIdList = adapter.findObservationEventId(phrId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        
        return observationEventIdList;
    }

    /**
     * 測定・全検査結果のデータを取得（患者View）
     * @param string
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> searchAllByEventId(String observationEventId) throws Throwable {
        List<ObservationEventEntity> ObservationEntityList = new ArrayList<>();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter((dao.getConnection()));
            ObservationEntityList = adapter.searchAllByEventId(observationEventId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        
        return ObservationEntityList;
    }

    /**
     * 測定・全検査結果のデータを取得（患者View）
     * @param string
     * @return
     * @throws Throwable
     */
    public List<ObservationEventEntity> searchAllByEventIdForSelfCheck(String observationEventId) throws Throwable {
        List<ObservationEventEntity> ObservationEntityList = new ArrayList<>();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter((dao.getConnection()));
            ObservationEntityList = adapter.searchAllByEventIdForSelfCheck(observationEventId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        
        return ObservationEntityList;
    }

    /**
     * PHR_IDからObservationEventEntityリストを取得します。
     * @param phrId
     * @return
     * @throws Throwable 
     */
    public List<ObservationEventEntity> findByPhrId(String phrId) throws Throwable{
    	List<ObservationEventEntity> eList = new ArrayList<ObservationEventEntity>();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            eList = adapter.findByPhrId(phrId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        
        return eList;
    }

    /**
     * ObservationEventIdからObservationEventEntityリストを取得します。
     * @param observationEventId
     * @return
     * @throws Throwable 
     */
    public ObservationEventEntity findByObservationEventId(String observationEventId) throws Throwable{
    	ObservationEventEntity e = new ObservationEventEntity();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            e = adapter.findByPrimaryKey(observationEventId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception ex){
                logger.debug(e);
            }
        }
        
        return e;
    }

    /**
     * PHR_IDとDataInputTyepCdの値でObservationEventIdリストを取得
     * @param phr_id
     * @param dataInputTypeCd
     * @return list
     * @throws Throwable 
     */
    public List<String> findByPhridAndDatainputtypecd(String phr_id, String dataInputTypeCd) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao=null;
        List<ObservationEventEntity> observationEventList = new ArrayList<>();
        
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            // 該当のObservationEventレコードを取得
            observationEventList = adapter.findByPhridAndDatainputtypecd(phr_id, dataInputTypeCd);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception ex){
                logger.debug(ex);
            }
        }
        
        // 取得したリストからObservationEventIdを取得
        List<String> idList = new ArrayList<>();
        if (observationEventList.size() != 0) {
            for (ObservationEventEntity e : observationEventList) {
                idList.add(e.getObservationEventId());
            }
        }
        
        logger.debug("End");
        return idList;
    }

    /**
     * ObservationEventIdからDataInputTyepCdの値を取得
     * @param observationEventId
     * @return entity
     * @throws Throwable 
     */
    public ObservationEventEntity findById(String observationEventId) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao=null;
        ObservationEventEntity entity = new ObservationEventEntity();
        
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            // 該当のObservationEventレコードを取得
            entity = adapter.findById(observationEventId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception ex){
                logger.debug(ex);
            }
        }
        
        logger.debug("End");
        return entity;
    }

    /**
     * ObservationDefinitionIdとenumValueからenumNameの値を取得
     * @param observationEventId
     * @param enumValue
     * @param viewId
     * @return enumName
     * @throws Throwable 
     */
    public String findByIdAndEnum(String observationDefinitionId, String enumValue, int viewId) throws Throwable {
        logger.debug("Start");
        String enumName = null;
        
        DataAccessObject dao=null;
        ObservationDefinitionEnumValueEntity entity = new ObservationDefinitionEnumValueEntity();
        
        try {
            dao = new DataAccessObject();
            ObservationDefinitionEnumValueAdapter adapter = new ObservationDefinitionEnumValueAdapter((dao.getConnection()));
            // 該当のObservationEventレコードを取得
            entity = adapter.findByIdAndEnum(observationDefinitionId, enumValue, viewId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception ex){
                logger.debug(ex);
            }
        }
        
        if (entity.getObservationDefinitionId() != null) {
            enumName = entity.getEnumName();
        }
        
        logger.debug("End");
        return enumName;
    }
}
