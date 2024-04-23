/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.service.ICooperationService;

/**
 *
 * @author chiba
 */
public class CooperationService implements ICooperationService {
 
    private static final Log logger = LogFactory.getLog(CooperationService.class);

    /**
     * 患者連携設定一覧を取得
     * @param phrId
     * @return
     * @throws Throwable
     */
    @Override
    public CooperationResult getCooperationList( String phrId ) throws Throwable {

        logger.debug("Start");
        
        CooperationResult result = new CooperationResult();
                
        //  DB検索
        DataAccessObject dao = new DataAccessObject();
        MedicalOrganizationPatientAdapter adapter;
        adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
        List<MedicalOrganizationPatientEntity> entity;
        entity = adapter.selectByPhrId( phrId );

        //  結果まとめ
        MedicalOrganizationPatientEntity oneEntity;
        List<List<String>> allData = new ArrayList<>();
        MedicalOrganizationEntity entityMedial;
        
        MedicalOrganizationAdapter adpMedicalSel;
        adpMedicalSel = new MedicalOrganizationAdapter(dao.getConnection());
        for( int row=0; row< entity.size(); row++ ){
            oneEntity = entity.get(row);
            List oneData = new ArrayList();
            oneData.add( oneEntity.getMedicalOrganizationCd() );
            oneData.add( oneEntity.getPatientId() );
            entityMedial = adpMedicalSel.findByPrimaryKey(oneEntity.getMedicalOrganizationCd());
            if( entityMedial != null ){
                oneData.add( "true" );
                oneData.add( entityMedial.getMedicalOrganizationName());
            }else{
                oneData.add( "false" );
                oneData.add( "不明な医療機関" );
            }
            oneData.add( oneEntity.getAgreesToShare() );
            allData.add( oneData );
        }
        
        //  返却用
        result.setPhrId(phrId);
        result.setResultCd(CooperationResultCd.SUCCCESS);
        result.setCooperationList(allData);

        dao.close();
        
        logger.debug("End");
        
        return result;
    }

    /**
     * 患者連携設定を更新
     * @param targetEntity
     * @return
     * @throws Throwable 
     */
    @Override
    public CooperationResult updateCooperation( MedicalOrganizationPatientEntity targetEntity ) throws Throwable {
        logger.debug("Start");
        
        CooperationResult result = new CooperationResult();
                
        // 更新
        DataAccessObject dao = new DataAccessObject();
        MedicalOrganizationPatientAdapter adapter;
        adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
        dao.beginTransaction();
        adapter.update( targetEntity );
        dao.commitTransaction();

        //  結果まとめ
        result.setResultCd( CooperationResultCd.SUCCCESS );
        
        dao.close();
        
        logger.debug("End");
        
        return result;
    }

    /**
     * 患者連携設定を削除
     * @param targetEntity
     * @return
     * @throws Throwable 
     */
    @Override
    public CooperationResult deleteCooperation( MedicalOrganizationPatientEntity targetEntity ) throws Throwable {
        logger.debug("Start");
        
        CooperationResult result = new CooperationResult();
                
        // 削除
        DataAccessObject dao = new DataAccessObject();
        MedicalOrganizationPatientAdapter adapter;
        adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
        dao.beginTransaction();
        adapter.delete( targetEntity );
        dao.commitTransaction();

        //  結果まとめ
        result.setResultCd( CooperationResultCd.SUCCCESS );
        
        dao.close();
        
        logger.debug("End");
        
        return result;
    }

    /**
     * 患者連携設定を追加
     * @param targetEntity
     * @return
     * @throws Throwable 
     */
    @Override
    public CooperationResult createCooperation( MedicalOrganizationPatientEntity targetEntity ) throws Throwable {

        logger.debug("Start");
        
        CooperationResult result = new CooperationResult();
                
        //  DB登録
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            MedicalOrganizationPatientAdapter adapter;
            adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
            
            //  新しいの登録する
            MedicalOrganizationPatientEntity entity = new MedicalOrganizationPatientEntity();
            
            //  トランザクション
            dao.beginTransaction();
            adapter.insert( targetEntity );
            dao.commitTransaction();
                    
            //  結果セット
            result.setPhrId( targetEntity.getPHR_ID() );
            result.setResultCd(CooperationResultCd.SUCCCESS);
            
        } catch (Throwable ex) {
            //エラーが発生した場合はエラーを通知する。
            logger.error("", ex);

            //  結果セット
            result.setPhrId(targetEntity.getPHR_ID());
            result.setResultCd(CooperationResultCd.SQLFAIL);

        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");

        return result;
    }

    /**
     * 患者連携設定情報（１件）の取得
     * @param phrId
     * @param medicalCd
     * @return
     * @throws Throwable 
     */
    @Override
    public MedicalOrganizationPatientEntity getCooperation(String phrId, String medicalCd ) throws Throwable {
        logger.debug("Start");
        
        CooperationResult result = new CooperationResult();
                
        //  DB検索
        DataAccessObject dao = new DataAccessObject();
        MedicalOrganizationPatientAdapter adapter;
        adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
        List<MedicalOrganizationPatientEntity> entity;
        entity = adapter.selectByPhrId( phrId );

        MedicalOrganizationPatientEntity resultEntity = new MedicalOrganizationPatientEntity();
        for( MedicalOrganizationPatientEntity oneEntity : entity ){
            if( phrId.equals(oneEntity.getPHR_ID() ) ){
                if( medicalCd.equals( oneEntity.getMedicalOrganizationCd() ) ){
                    resultEntity = oneEntity;
                    break;
                }
            }
        }

        dao.close();
        
        logger.debug("End");
        
        return resultEntity;
    }

    /**
     * 対象患者IDのチェック
     * @param medicalCd
     * @param phrId
     * @param patientId
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean isDuplication(String medicalCd, String phrId, String patientId) throws Throwable {
        logger.debug("Start");
        
        CooperationResult result = new CooperationResult();
        
        boolean isCheck = false;
        //  DB検索
        DataAccessObject dao = new DataAccessObject();
        MedicalOrganizationPatientAdapter adapter;
        adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
        List<MedicalOrganizationPatientEntity> list;
        list = adapter.findByPatientIdAndMedicalCdAll(patientId, medicalCd);

        for( MedicalOrganizationPatientEntity oneEntity : list ){
            if( !phrId.equals(oneEntity.getPHR_ID() ) ){
               isCheck = true;
               break;
            }
        }

        dao.close();
        
        logger.debug("End");
        
        return isCheck;
    }
    /**
     * 患者連携設定一覧用結果
     */
    public class CooperationResult {

        String phrId = null;
        CooperationResultCd resultCd;
        List<List<String>>cooperationList = new ArrayList<>();

        
        public String getPhrId() {
            return this.phrId;
        }
        
        public void setPhrId(String value) {
            this.phrId = value;
        }

        public CooperationResultCd getResultCd() {
            return this.resultCd;
        }
        
        public void setResultCd(CooperationResultCd value) {
            this.resultCd = value;
        }

        public List<List<String>> getCooperationList() {
            return this.cooperationList;
        }
        
        public void setCooperationList(List<List<String>> value) {
            this.cooperationList = value;
        }
    }
    
    /**
     *  結果コード 
     */
    public enum CooperationResultCd {
        SUCCCESS,
        DATA_NOT_FOUND,
        SQLFAIL;
    }
        
    
}
