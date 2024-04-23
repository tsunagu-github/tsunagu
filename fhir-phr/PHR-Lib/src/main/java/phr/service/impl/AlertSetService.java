/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationAlertAdapter;
import phr.datadomain.adapter.ObservationDefinitionScriptAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.InsurerViewDefinitionAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationAlertEntity;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IAlertExecuteService;
import phr.service.IAlertSetService;

/**
 *
 * アラート一覧画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class AlertSetService implements IAlertSetService{
    
    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(AlertSetService.class);
    private static Logger logger = Logger.getLogger(AlertSetService.class);
       
    /**
     * 検査結果のアラート判別を実施する
     * @param insureNo
     * @param year
     * @param observationList
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
     public List<ObservationEntity> alertSet(String phrid,String insureNo, Integer viewId , List<ObservationEntity> observationList, Timestamp date)  throws Throwable {
      
        List<ObservationAlertEntity> resultList = new ArrayList<>();
        
        //viewIdがnull の場合はアラート登録時は対象のViewすべてに対してチェックを行う
        //指定された場合は対象のViewのみチェックする。
        List<InsurerViewDefinitionEntity> viewList = getViewList(insureNo,viewId);
        
        // 過去の疾病発症チェック
        List<Integer> patientDiseaseTypeCd = this.getDiseaseType(phrid, date);
        
//        for (ObservationEntity entity : observationList) {
//            String obId= entity.getObservationDefinitionId();
//            if (obId.equals("0000000011")) {
//                // 糖尿病診断年齢
//                patientDiseaseTypeCd.add(1);
//            } else if (obId.equals("0000000015")) {
//                // 高血圧診断年齢
//                patientDiseaseTypeCd.add(2);
//            } else if (obId.equals("0000000019")) {
//                // 脂質異常症の診断年齢
//                patientDiseaseTypeCd.add(3);
//            } else if (obId.equals("0000000021")) {
//                // CKD診断年齢
//                patientDiseaseTypeCd.add(4);
//            } else if (obId.equals("0000000020")) {
//                // 冠動脈疾患の既往(あり)
//                if (entity.getValue().equals("1") || entity.getValue().equals("2")) {
//                    patientDiseaseTypeCd.add(5);
//                }
//            } else if (obId.equals("0000000103")) {
//                // 脳卒中の既往(あり)
//                if (entity.getValue().equals("1") || entity.getValue().equals("2") || entity.getValue().equals("3") || entity.getValue().equals("4")) {
//                    patientDiseaseTypeCd.add(6);
//                }
//            }
//        }
        
        if (patientDiseaseTypeCd.size() == 0) {
            patientDiseaseTypeCd.add(0);
        }
        
        PatientEntity pentity = getPatient(phrid);
        for(ObservationEntity target : observationList){
             for(InsurerViewDefinitionEntity view : viewList){
                int targetId = view.getViewId();
                String obId = target.getObservationDefinitionId();

                List<ObservationDefinitionScriptEntity> entityList = this.getScript(insureNo , targetId , obId, patientDiseaseTypeCd);
                ObservationAlertEntity rentity = null;

                for (ObservationDefinitionScriptEntity entity : entityList) {
                    ObservationAlertEntity e = null;
                    if(entity != null){
                        e = setAlert(entity,target,pentity,date,targetId);
                    }else{
                        e = new ObservationAlertEntity();
                        e.setAlertFlg(false);
                        e.setAlertLevelCd(0);
                        e.setViewId(targetId);
                        e.setObservationDefinitionId(target.getObservationDefinitionId());
                        e.setObservationEventId(target.getObservationEventId());
                    }
                    
                    if (rentity == null) {
                        rentity = e;
                    } else {
                        if (rentity.getAlertLevelCd() < e.getAlertLevelCd()) {
                            rentity = e;
                        }
                    }
                }
                resultList.add(rentity);
             }
        } 
        
        entryAlert(resultList);
        return observationList;
     }

     /**
     * 特定健診結果のアラート判別を実施する
     * @param insureNo
     * @param year
     * @param observationList
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
     public List<ObservationEntity> checkupalertSet(String phrid,String insureNo, List<ObservationEntity> observationList, Timestamp date)  throws Throwable {
        
        int checkupViewId = 1;
        PatientEntity pentity = getPatient(phrid);
        List<ObservationAlertEntity> alertList = new ArrayList<>();
        
         for(ObservationEntity target : observationList){
             String obId = target.getObservationDefinitionId();
             logger.debug(obId);
             
             //特定健診のViewIDは1で固定
             // TODO 要確認
             ObservationDefinitionScriptEntity entity = null;
             ArrayList<Integer> arr = new ArrayList<Integer>();
             arr.add(0);
             List<ObservationDefinitionScriptEntity> entityList = this.getScript(insureNo, checkupViewId, obId, arr);
             if (entityList != null & entityList.size() > 0) {
                 entity = entityList.get(0);
             }
             
             if(entity != null){
                //特定健診のViewIDは1で固定
                alertList.add(setAlert(entity,target,pentity,date,checkupViewId));
             }else{
                 ObservationAlertEntity rentity = new ObservationAlertEntity();
                 rentity.setViewId(checkupViewId);
                 rentity.setAlertFlg(false);
                 rentity.setAlertLevelCd(0);
                 rentity.setObservationDefinitionId(target.getObservationDefinitionId());
                 rentity.setObservationEventId(target.getObservationEventId());
                 alertList.add(rentity);
             }
             
         } 
         
         entryAlert(alertList);
         return observationList;
     }
     
     private boolean entryAlert(List<ObservationAlertEntity> alertList) throws Throwable{
        DataAccessObject dao = null;
        boolean result = false;

        try {
            dao = new DataAccessObject();
            ObservationAlertAdapter adapter = new ObservationAlertAdapter(dao.getConnection());
            dao.beginTransaction();	// トランザクション開始
            
            for(ObservationAlertEntity alert : alertList){
                if (alert == null) {
                    continue;
                }
                ObservationAlertEntity target = adapter.findByPrimaryKey(alert.getObservationEventId(), alert.getObservationDefinitionId(), alert.getViewId());
                
                if(target == null){
                    adapter.insert(alert);
                }else{
                    adapter.update(alert);
                }
            }
            
            dao.commitTransaction();
            result = true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlertSetService.class.getName()).log(Level.WARN, null, ex);
            logger.debug("",ex);
        } catch (SQLException ex) {
            Logger.getLogger(AlertSetService.class.getName()).log(Level.WARN, null, ex);
            logger.debug("",ex);
        } catch (Throwable ex) {
            Logger.getLogger(AlertSetService.class.getName()).log(Level.WARN, null, ex);
            logger.debug("",ex);
        }finally {
            if (dao != null) {
                dao.close();
            }
        }
         
        return result;

     }
     
    private List<ObservationDefinitionScriptEntity> getScript(String insureNo, int viewId, String obId, List<Integer> patientDiseaseTypeCd) throws Throwable {
        List<ObservationDefinitionScriptEntity> entity = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationDefinitionScriptAdapter adapter = new ObservationDefinitionScriptAdapter(dao.getConnection());
            entity = adapter.searchScriptList(viewId , obId, patientDiseaseTypeCd);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return entity;
    }

    private ObservationAlertEntity setAlert(ObservationDefinitionScriptEntity entity, ObservationEntity target,PatientEntity pentity , Timestamp date , Integer viewId) {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        String levelscript = entity.getAlertLevelScript();
        String flgscript = entity.getAlertFlgScript();
        ObservationAlertEntity result = new ObservationAlertEntity();
        /**
         * ScriotTypeIdは
         * 1は両方Javascript
         * 2はAlertはClass、LevelはJavaScript
         * 3はAlertはJavaScript、Levelはclass（未実装）
         * 4は両方class（未実装）
         */
        
        Integer typeId = entity.getScriptTypeId();
        
        logger.debug("typeID :" + typeId);
        
        String value = target.getValue();
        
        Integer level = 0;
        //Levalの処理
        if(levelscript != null ){
            if(typeId.equals(1) || typeId.equals(2)){
                StringBuilder sb = new StringBuilder();
                sb.append("function exec() { \r\n");
                sb.append(levelscript);
                sb.append("} \r\n");
                sb.append(" exec();");
                String script = sb.toString();
                Object jsValue = new Object();

                logger.debug(entity.getObservationDefinitionId() + "AlertLevelScriptの実行");
                jsValue = executeScript(script,value,scriptEngine,pentity);
                level = (Integer)jsValue;
            }else{
                //  classでの処理が未実装
            }
        }else{
            level = 9;
        }
        
        //Flgの処理
        boolean flg = false;
        if(flgscript != null ){
            if(typeId.equals(1) || typeId.equals(3)){
                StringBuilder sb2 = new StringBuilder();
                sb2.append("function exec() { \r\n");
                sb2.append(flgscript);
                sb2.append("} \r\n");
                sb2.append(" exec();");
                String script2 = sb2.toString();
                Object jsValue2 = new Object();

                logger.debug(entity.getObservationDefinitionId() + "AlertFlgScriptの実行");
                jsValue2 = executeScript(script2,value,scriptEngine,pentity);
                flg = Boolean.parseBoolean(jsValue2.toString());
            }else{
                try {
                    logger.debug(flgscript);
                    Class<?> clazz = Class.forName(flgscript);
                    IAlertExecuteService service = (IAlertExecuteService)clazz.newInstance();
                    Integer result_num = service.getAlert( entity,  target, pentity , date);
                    logger.debug(" result : " + result_num);
                    if(result_num == 0) flg = false;
                    else flg = true;
                    
                    logger.debug(" flg : " + flg);
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AlertSetService.class.getName()).log(Level.WARN, null, ex);
                    logger.error("",ex);
                } catch (Throwable ex) {
                    Logger.getLogger(AlertSetService.class.getName()).log(Level.WARN, null, ex);
                    logger.error("",ex);
                }

            }
        }
        
        result.setObservationDefinitionId(target.getObservationDefinitionId());
        result.setObservationEventId(target.getObservationEventId());
        result.setAlertLevelCd(level);
        result.setAlertFlg(flg);
        result.setViewId(viewId);
        
        return result;

    }

    private Object executeScript(String script, String value, ScriptEngine scriptEngine,PatientEntity pentity) {
        Object jsValue = new Object();
        script = script.replaceAll("\\{value\\}", value);
        
        if(script.contains("sexCd")){
            script = script.replaceAll("\\{sexCd\\}", pentity.getSexCd());
        }
        
        try {
            jsValue = scriptEngine.eval(script);
        } catch (ScriptException ex) {
            logger.debug(script);
            Logger.getLogger(AlertSetService.class.getName()).log(Level.WARN, null, ex);
        } 
        
        return jsValue;
    }

    private PatientEntity getPatient(String phrid) throws SQLException, ClassNotFoundException {
        logger.debug("Start");

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
    	} catch (Throwable ex) {
            Logger.getLogger(AlertSetService.class.getName()).log(Level.WARN, null, ex);
        }finally{
            if (dao != null) {
                try{
                    dao.close();
                }catch(Throwable e){
                    logger.error("", e);
                }
            }
        }

        logger.debug("end");
        return rtnPatientEntity;
    }

    private List<InsurerViewDefinitionEntity> getViewList(String insurerNo, Integer viewId) throws Throwable {
        List<InsurerViewDefinitionEntity> entityList = new ArrayList<>();
        List<InsurerViewDefinitionEntity> resultList = new ArrayList<>();
        DataAccessObject dao = null;
        
        try {
            dao = new DataAccessObject();
            InsurerViewDefinitionAdapter adapter = new InsurerViewDefinitionAdapter(dao.getConnection());
            resultList = adapter.findViewList(insurerNo);
            if(viewId != null){
                for(InsurerViewDefinitionEntity entity : resultList){
                    if(entity.getViewId() == viewId){
                        entityList.add(entity);
                    }
                }
            }else{
                // システム固定以外のVIEWが対象
                for(InsurerViewDefinitionEntity entity : resultList){
                    if (entity.getViewId() > 100) {
                        entityList.add(entity);
                    }
                }
                //if (entity.getViewId() > 100)
                //entityList.addAll(resultList);
            }
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        return entityList;
    }
    
    /**
     * 過去の疾病発病を取得
     */
    public List<Integer> getDiseaseType(String phrid, Timestamp examinationDate) throws Throwable {
        List<Integer> list = new ArrayList<Integer>();
        DataAccessObject dao = new DataAccessObject();
        
        try {
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            list = adapter.getDiseaseType(phrid, examinationDate);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return list;
    }
}
