/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.text.SimpleDateFormat;
import phr.datadomain.DataAccessObject;
import phr.service.ISpecificMedicalCheakUpFormService;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.adapter.ObservationDefinitionJlac10Adapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.PatientEntity;
import phr.dto.ConvertPhrIdDto;
import phr.dto.ConvertResultObservationDto;
import phr.dto.ObservationEntryDto;
import phr.service.IAlertSetService;

/**
 *
 * @author kis.o-note-002
 */
public class SpecificMedicalCheakUpFormService implements ISpecificMedicalCheakUpFormService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SpecificMedicalCheakUpFormService.class);

    
    /**
     * <pre>特定健診結果登録処理</pre>
     * @param value
     * @param headerPhrId     PPHR_ID、保険者番号
     * @return
     * @throws Throwable 
     */
    @Override
    public int InsertObservationAndObservationEvent(List<ResultObservationDto> value ,ConvertPhrIdDto headerPhrId) throws Throwable{

        DataAccessObject dao = new DataAccessObject();
        int lineCount = 0;

        // 検査結果情報
        ObservationEventEntity listObservationEvent = new ObservationEventEntity(); // 検査結果情報
        int entityEvent=0;
        ObservationEventAdapter eventAdapter = new ObservationEventAdapter(dao.getConnection());

        // 検査項目情報
        ObservationEntity listObservation = new ObservationEntity();    // 検査項目情報
        int entity  =0;
        ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
        
        dao.beginTransaction();	// トランザクション開始
       
        try{
                //一度の登録で必要なObservationEventは１つのため位置変更
                String id = ObservationEventAdapter.numberingObservationEventId();  // 検査結果ID
                String insureNo = headerPhrId.getInsureNo(); // 保険者番号
                String examinationDate = headerPhrId.getExaminationDate();   // 検査日
                Timestamp time  = new Timestamp(new SimpleDateFormat("yyyyMMdd").parse(examinationDate).getTime());
                
                // 検査結果情報（CbservationEvent）登録処理
                try{
                    listObservationEvent.setObservationEventId(id);  // 検査結果ID
                    listObservationEvent.setDataInputTypeCd(3);         // データ入力種別
                    listObservationEvent.setPHR_ID(headerPhrId.getPhrId());  // PHR_ID   // ファイル名より取得処理追加
                    listObservationEvent.setExaminationDate(time);  // 検査日
                    listObservationEvent.setYear(Integer.parseInt(examinationDate.substring(0,4)));    // 対象年度 
                    listObservationEvent.setInsurerNo(insureNo);   // 保険者番号    
                    listObservationEvent.setLaboratoryCd(null); // 検査会社コード
                    listObservationEvent.setOrderNo(null);         // 検査オーダーNo
                    listObservationEvent.setMedicalOrganizationCd(null);    // 医療機関コード

                    // insert
                    entityEvent=eventAdapter.InsertObservationEvent(listObservationEvent);  
                } catch(Exception e){
                    System.out.println("observationEvent" + e.getMessage());
                }
                
            List<ConvertResultObservationDto> targetList = headerPhrId.getCresultList();
            List<ObservationEntity> oList = new ArrayList<>();
            for(ConvertResultObservationDto dto:targetList){
                lineCount++;
                
                try{
                    // 検査項目結果情報（Cbservation）登録処理  // TODO こっちの項目はほとんどTODOなので項目レベルで後で見直し
                    listObservation.setObservationEventId(id);   //
                    listObservation.setObservationDefinitionId(dto.getObservationDefinitionId());   // 項目ID   // TODO JLACコードより取得
                    listObservation.setJLAC10(dto.getCode());   // JLAC10コード
                    listObservation.setValue(dto.getValue());   // 検査結果値
                    listObservation.setOutRangeTypeCd(1);      // 範囲外種別コード // TODO とりあえず(xml1が来次第)
                    listObservation.setMinReferenceValue(0.0);     // 基準値下限   // TODO とりあえず(xml1が来次第)
                    listObservation.setMaxReferenceValue(0.0);    // 基準値上限   // TODO とりあえず(xml1が来次第)

                    
                    // insert
                    entity=adapter.InsertObservation(listObservation);  // 検査登録結果情報登録処理
                    oList.add(listObservation);
                } catch(Exception e){
                    System.out.println("observation" + e.getMessage());
                }
            }
            dao.commitTransaction();    // コミット処理

            IAlertSetService a_service = new AlertSetService();;
            a_service.checkupalertSet(listObservationEvent.getPHR_ID(), insureNo , oList, time);
            
        } catch(Exception ex){
            dao.rollbackTransaction();  // ロールバック
            throw ex;
        } finally {
            if(dao!=null){
                dao.close();
            }
        }
        return lineCount;
    }
    /**
     * 項目ID取得処理
     * @param insurerNo
     * @param year
     * @param code  // JLAC10コード
     * @return
     * @throws Throwable 
     */
    @Override
    public String getObservationDefinitionId(String insurerNo,String year,String code) throws Throwable{
        DataAccessObject dao = null;
        try{
            dao=new DataAccessObject();
            ObservationDefinitionJlac10Adapter adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
            String entity = adapter.getObservationDefinitionId(insurerNo,year,code);
            return  entity;
        } catch(NullPointerException e){
            return "";
        } catch (Throwable ex){
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
    }
    
    /*** 患者情報の有無を取得します。
     * @param phrid     取得対象のPHRID
     * @return 
     * @throws Throwable
     */
    @Override
    public PatientEntity getPatientInfo(String phrid) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject();
        PatientEntity account = null;
        
        try{
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            dao.beginTransaction();
            account = adapter.findInsurePatient(phrid);

    	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	}finally{
    	}

        logger.trace("end");
        return account;
    }

    @Override
    public Map<String, String> getjlacmap(String insurerNo) throws Throwable{
        DataAccessObject dao = new DataAccessObject();
        
        Map<String, String> resultMap = new HashMap<String,String>();
        try{
            ObservationDefinitionJlac10Adapter adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
            resultMap = adapter.getJLACMap(insurerNo);
        }catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	} finally{
            if(dao!=null){
                dao.close();
            }
        }
        
        return resultMap;
    }
    
    @Override
    public List<ObservationEventEntity> confirmCheckup(String phrid , String insurerNo, String date) throws Throwable{
        DataAccessObject dao = new DataAccessObject();
        
        List<ObservationEventEntity> resultlist = new ArrayList<ObservationEventEntity>();
        try{
            ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date targetDate = sdf.parse(date);
            resultlist = adapter.findCheckup(phrid, targetDate, insurerNo);
        }catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	} finally{
            if(dao!=null){
                dao.close();
            }
        }
        
        return resultlist; 
    }

    @Override
    public boolean deleateCheckup(List<ObservationEventEntity> eventList) throws Throwable{
        DataAccessObject dao = new DataAccessObject();
        
        for(ObservationEventEntity event : eventList){
            String id = event.getObservationEventId();
            try{
                ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
                adapter.delete(event);
                
                ObservationAdapter ob = new ObservationAdapter(dao.getConnection());
                List<ObservationEntity> list = ob.findByObservationEventId(id);
                
                for(ObservationEntity result : list){
                    ob.delete(result);
                }
                dao.commitTransaction();

            }catch(Exception e){
                dao.rollbackTransaction();
                return false;
            }
        }

        if(dao!=null){
            dao.close();
        }
        
        return true;     
        
    }
    
    /**
     * 検査結果の登録・削除
     * @param dtoList   登録リスト
     * @param eventList 削除リスト
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean deleteInsertObservationList(List<ObservationEntryDto> dtoList, List<ObservationEventEntity> eventList) throws Throwable  {

        DataAccessObject dao = null;
        boolean result = false;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter oeAdapter = new ObservationEventAdapter(dao.getConnection());
            ObservationAdapter oAdapter = new ObservationAdapter(dao.getConnection());

            dao.beginTransaction();	// トランザクション開始
            
//            //重複検査結果の削除
//            for(ObservationEventEntity event : eventList){
//                String id = event.getObservationEventId();
//                oeAdapter.delete(event);
//                List<ObservationEntity> oeList = oAdapter.findByObservationEventId(id);
//                for(ObservationEntity oeItem : oeList){
//                    oAdapter.delete(oeItem);
//                }
//            }

            List<ObservationEntity> olist = new ArrayList<>();
            //検査結果の登録
            for(ObservationEntryDto item:dtoList){
                if(!item.getSameFlg() || item.getUpdateFlg()){
                    //重複検査結果の再検索
                    Date targetDate = new Date(item.getObservationEventEntity().getExaminationDate().getTime());
                    List<ObservationEventEntity> resultlist = oeAdapter.findCheckup(
                            item.getObservationEventEntity().getPHR_ID()
                            , targetDate
                            , item.getObservationEventEntity().getInsurerNo());
                    //重複検査結果の削除
                    for(ObservationEventEntity resultItem: resultlist){
                        if (item.getObservationEventEntity().getDataInputTypeCd() == resultItem.getDataInputTypeCd()) {
                            String resultId = resultItem.getObservationEventId();
                            oeAdapter.delete(resultItem);
                            List<ObservationEntity> oeList = oAdapter.findByObservationEventId(resultId);
                            for(ObservationEntity oeItem : oeList){
                                oAdapter.delete(oeItem);
                            }
                        }
                    }

                    //最新のObservationEventIdを取得
                    String observationId = ObservationEventAdapter.numberingObservationEventId();
                    //検査結果の登録
                    ObservationEventEntity eventEntity = item.getObservationEventEntity();
                    eventEntity.setObservationEventId(observationId);
                    
                    oeAdapter.insert(eventEntity);
                    //検査結果項目の登録
                    List<String> list = new ArrayList<>();
                    for (ObservationEntity res : item.getObservationEntity()) {
                        res.setObservationEventId(observationId);
                        logger.debug(res.getObservationDefinitionId());
                        // 既に登録済みのObservationDefinitionIdじゃないかを確認
                        int num = 0;
                        if (list.size() == 0) {
                            oAdapter.insert(res);
                            olist.add(res);
                            list.add(res.getObservationDefinitionId());
                        } else {
                            for (String l : list) {
                                if (!l.equals(res.getObservationDefinitionId())) {
                                    num++;
                                }
                            }
                            if (list.size() == num) {
                                oAdapter.insert(res);
                                olist.add(res);
                                list.add(res.getObservationDefinitionId());
                            }
                        }
                    }

                    IAlertSetService a_service = new AlertSetService();
                    Timestamp cdate = new Timestamp(targetDate.getTime());
                    a_service.checkupalertSet(eventEntity.getPHR_ID(), eventEntity.getInsurerNo() , olist, cdate);//null);
                    
                }
            }
            dao.commitTransaction();    //トランザクション確定


            result = true;
        } catch (Throwable ex) {
            if(dao!=null){dao.rollbackTransaction();}
            logger.error(ex);
        }
        return result;
    }

    /**
     * ObservationEventIdを取得
     * @param phr_id
     * @param date
     * @return
     * @throws Throwable 
     */
    public List<String> getObservationEventId(String phr_id, String date) throws Throwable{
        logger.debug("Start");
        List<String> list = new ArrayList<>();
        List<ObservationEventEntity> entity = new ArrayList<>();
        
        DataAccessObject dao = null;
        try{
            dao=new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
            entity = adapter.getObservationEventId(phr_id, date);
        } catch (Throwable ex){
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
        
        if (entity.size() != 0) {
            for (ObservationEventEntity e : entity) {
                list.add(e.getObservationEventId());
            }
        }
        
        logger.debug("End");
        return  list;
    }

    /**
     * observationEventIdでレコードを削除（ObservationEvent）
     * @param observationEventId
     * @return 
     * @throws Throwable 
     */
    public void deleteById(String observationEventId) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = null;
        try{
            dao=new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
            adapter.deleteById(observationEventId);
        } catch (Throwable ex){
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
        
        logger.debug("End");
    }

    /**
     * observationEventIdでレコードを削除（Observation）
     * @param observationEventId
     * @return 
     * @throws Throwable 
     */
    public void deleteObservationById(String observationEventId) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = null;
        try{
            dao=new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            adapter.deleteObservationRecord(observationEventId);
        } catch (Throwable ex){
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
        
        logger.debug("End");
    }
}