/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import phr.service.ISelfCheckService;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;

/**
 *
 * @author chiba
 */
public class SelfCheckService  implements ISelfCheckService {

    private static final Log logger = LogFactory.getLog(SelfCheckService.class);

    //  収縮期
    static final String SUSTOLIC_BP_DEFINITIONID = "0000000032";
    //  拡張期	
    static final String DIASTOLIC_BP_DEFINITIONID = "0000000033";
    //  体重	
    static final String WEIGHT_DEFINITIONID = "0000000101";
    //  血糖	
    static final String BLOOD_BLUCOSE_DEFINITIONID = "0000000102";

    /**
     * PHRIDを条件に保険者番号を取得する
     * @param phrId
     * @return
     * @throws Throwable 
     */
    @Override
    public InsurerPatientEntity getInsurerPatient( String phrId )throws Throwable {

        InsurerPatientEntity result = new InsurerPatientEntity();
        
        //  DB検索
        DataAccessObject dao = new DataAccessObject();
        InsurerPatientAdapter adapter;
        adapter = new InsurerPatientAdapter(dao.getConnection());
        result = adapter.findByPhrId( phrId );
        dao.close();

        logger.debug("End");

        return result;
    }

    /**
     * 検査結果IDと項目IDを条件に検査項目結果情報を取得する
     * @param targetEntity
     * @return
     * @throws Throwable 
     */
    @Override
    public ObservationEntity getObservation( ObservationEntity targetEntity )throws Throwable {
    
        ObservationEntity result = new ObservationEntity();
        result.setObservationEventId("");
        result.setObservationDefinitionId("");
        
        List<ObservationEntity> resultList = new ArrayList();
        
        //  DB検索
        DataAccessObject dao = new DataAccessObject();
        ObservationAdapter adapter;
        adapter = new ObservationAdapter(dao.getConnection());
//        resultList = adapter.findByObservationEventId( targetEntity.getObservationEventId() );
        resultList = adapter.findByObservationEventIdForSelfCheck( targetEntity.getObservationEventId() );
        for( ObservationEntity oneData : resultList ){
            if( oneData.getObservationDefinitionId().equals( targetEntity.getObservationDefinitionId() ) ){
                result=oneData;
                break;
            }
        }
        dao.close();

        logger.debug("End");

        return result;
    }
    
    /**
     * 検査結果IDと項目IDを条件に検査項目結果情報を削除する
     * @param targetEntity
     * @return
     * @throws Throwable 
     */
    @Override
    public SelfCheckResult deleteObservation( ObservationEntity targetEntity ) throws Throwable {
                
        logger.debug("Start");
        
        SelfCheckResult result = new SelfCheckResult();
                
        // 削除
        DataAccessObject dao = new DataAccessObject();
        ObservationAdapter adapter;
        adapter = new ObservationAdapter(dao.getConnection());
        dao.beginTransaction();
        adapter.delete( targetEntity );
        dao.commitTransaction();

        //  結果まとめ
        result.setResultCd( SelfCheckResultCd.SUCCCESS );

        dao.close();
        
        logger.debug("End");
        
        return result;
    }
    
    /**
     * 自己測定の最新登録を取得
     * @param phrId
     * @return
     * @throws Throwable 
     */
    public String getLastSelfCheck( String phrId ) throws Throwable {
        DataAccessObject dao = new DataAccessObject();
        ObservationEventAdapter adapter;
        adapter = new ObservationEventAdapter(dao.getConnection());
        List<ObservationEventEntity> entities;
        entities = adapter.selectPhrIdAndObservationId( phrId, null, null );
        String maxObservationId = "";
        for( ObservationEventEntity oneEntity : entities ){
            if( oneEntity.getObservationEventId().compareTo(maxObservationId ) > 0 ){
                maxObservationId = oneEntity.getObservationEventId();
            }
        }
        try {
            if (dao != null) {dao.close();}
        } catch (Throwable ex) {
            logger.error("", ex);
        }
        return ( maxObservationId.isEmpty() )?null:maxObservationId ;
    }
    
    /**
     * 
     * @param observationEventId
     * @param addDate
     * @param phrId
     * @return
     * @throws Throwable 
     */
    public SelfCheckResult getSelfCheckListOne( String observationEventId, String addDate, String phrId ) throws Throwable {
        // PHR-IDと検査結果IDの一覧取得
        //  DB検索
        DataAccessObject dao = new DataAccessObject();
        ObservationEventAdapter adapter;
        adapter = new ObservationEventAdapter(dao.getConnection());

        List<List<String>> resultList = new ArrayList();

        //　検査ID（ObservationID）ごとに取得
        ObservationAdapter oneDataDatapeter = new ObservationAdapter(dao.getConnection());
        
        boolean validRow = false;

        //  検索
        List<ObservationEntity> oneDataEntities = oneDataDatapeter.findByObservationEventId( observationEventId );

        //  返却用まとめ
        List<String> oneResult = new ArrayList();
        for( int cnt=0; cnt < 6; cnt++ ){
            oneResult.add("");
            //  データ列は下記の順番
            //  ①日時
            //  ②家庭血圧（拡張）
            //  ③家庭血圧（収縮）
            //  ④家庭体重
            //  ⑤家庭血糖
            //  ⑥検査結果ID
        }
        
        oneResult.set(0, addDate );
        oneResult.set(5, observationEventId );

        validRow=false;
        for( ObservationEntity oneData: oneDataEntities ){
            switch( oneData.getObservationDefinitionId() ){
                case DIASTOLIC_BP_DEFINITIONID:
                    //  家庭血圧（拡張）
                    oneResult.set(1, oneData.getValue() );
                    if( !oneData.getValue().isEmpty() ){
                        validRow = true;
                    }
                    break;
                case SUSTOLIC_BP_DEFINITIONID:
                    //  家庭血圧（収縮）
                    oneResult.set(2, oneData.getValue() );
                    if( !oneData.getValue().isEmpty() ){
                        validRow = true;
                    }
                    break;
                case WEIGHT_DEFINITIONID:
                    //  家庭体重
                    oneResult.set(3, oneData.getValue() );
                    if( !oneData.getValue().isEmpty() ){
                        validRow = true;
                    }
                    break;
                case BLOOD_BLUCOSE_DEFINITIONID:
                    //  家庭血糖
                    oneResult.set(4, oneData.getValue() );
                    if( !oneData.getValue().isEmpty() ){
                        validRow = true;
                    }
                    break;
                default:
                    //  関係ないの
                    break;
            }
        }
        if( validRow ){
            resultList.add( oneResult );
        }
        
        SelfCheckResult result = new SelfCheckResult();
        result.setPhrId(phrId);
        result.setResultCd( SelfCheckResultCd.SUCCCESS );
        result.setSelfCheckList(resultList);
        
        dao.close();
        
        return result;
    }
        
    /**
     * 自己測定の一覧を取得する
     * @param phrId
     * @param startDate
     * @param endDate
     * @return
     * @throws Throwable
     */
    @Override
    public SelfCheckResult getSelfCheckList( String phrId, Date startDate, Date endDate ) throws Throwable {
    
        DataAccessObject dao = new DataAccessObject();
        
        ObservationAdapter observationAdapter = new ObservationAdapter(dao.getConnection());
        List<ObservationEntity> observationList = observationAdapter.findSelfCheckList(phrId, startDate, endDate);

        List<List<String>> resultList = new ArrayList();
        String preId = "";
        List<String> oneResult = new ArrayList();
        boolean validRow = false;
        
        for( ObservationEntity entity: observationList ) {
            // ObservationEventが変わった際にaddする
            if (!preId.equals(entity.getObservationEventId())) {
                if (validRow) {
                    resultList.add( oneResult );
                }
                oneResult = new ArrayList();
                //  返却用まとめ
                for( int cnt = 0; cnt < 6; cnt++ ){
                    oneResult.add("");
                    //  データ列は下記の順番
                    //  ①日時
                    //  ②家庭血圧（拡張）
                    //  ③家庭血圧（収縮）
                    //  ④家庭体重
                    //  ⑤家庭血糖
                    //  ⑥検査結果ID
                }                
                oneResult.set(0, entity.getExaminationDate().toString() );
                oneResult.set(5, entity.getObservationEventId() );
                validRow = false;
            }
            
            switch( entity.getObservationDefinitionId() ){
                case DIASTOLIC_BP_DEFINITIONID:
                    //  家庭血圧（拡張）
                    oneResult.set(1, entity.getValue() );
                    if(entity.getValue() != null &&  !entity.getValue().isEmpty() ){
                        validRow = true;
                    }
                    break;                
                case SUSTOLIC_BP_DEFINITIONID:
                    //  家庭血圧（収縮）
                    oneResult.set(2, entity.getValue() );
                    if(entity.getValue() != null &&  !entity.getValue().isEmpty() ){
                        validRow = true;
                    }
                    break;
                case WEIGHT_DEFINITIONID:
                    //  家庭体重
                    oneResult.set(3, entity.getValue() );
                    if(entity.getValue() != null && !entity.getValue().isEmpty() ){
                        validRow = true;
                    }
                    break;
                case BLOOD_BLUCOSE_DEFINITIONID:
                    try {
                    //  家庭血糖
                    oneResult.set(4, entity.getValue() );
                    if(entity.getValue() != null && !entity.getValue().isEmpty() ){
                        validRow = true;
                    }
                    } catch (Exception ex) {
                        logger.warn("", ex);
                    }
                    break;
                default:
                    //  関係ないの
                    break;
            }
            preId = entity.getObservationEventId();
        }
        // ループ後validRowがtrueなら追加する
        if (validRow) {
           resultList.add( oneResult );
           validRow = false;
        }
        
        SelfCheckResult result = new SelfCheckResult();
        result.setPhrId(phrId);
        result.setResultCd( SelfCheckResultCd.SUCCCESS );
        result.setSelfCheckList(resultList);
        
        dao.close();
        
        return result;
    }
    
    /**
     * 自己測定一覧用結果
     */
    public class SelfCheckResult {

        String phrId = null;
        SelfCheckResultCd resultCd;
        List<List<String>>selfCheckList = new ArrayList<>();
        
        public String getPhrId() {
            return this.phrId;
        }
        
        public void setPhrId(String value) {
            this.phrId = value;
        }

        public SelfCheckResultCd getResultCd() {
            return this.resultCd;
        }
        
        public void setResultCd(SelfCheckResultCd value) {
            this.resultCd = value;
        }

        public List<List<String>> getSelfCheckList() {
            return this.selfCheckList;
        }
        
        public void setSelfCheckList(List<List<String>> value) {
            this.selfCheckList = value;
        }
    }
    
    /**
     *  結果コード 
     */
    public enum SelfCheckResultCd {
        SUCCCESS,
        DATA_NOT_FOUND,
        SQLFAIL;
    }
    
}
