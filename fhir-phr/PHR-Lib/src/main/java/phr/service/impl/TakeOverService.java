/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import java.util.Random;
import java.util.UUID;
import phr.datadomain.adapter.DataTransferAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.DataTransferEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.ITakeOverService;


/**
 *
 * @author chiba
 */
public class TakeOverService implements ITakeOverService {

    private static final Log logger = LogFactory.getLog(TakeOverService.class);

    @Override
    public TakeOverServiceResult createTakeOverCode( String phrId, String takeOverPassword ) throws Throwable {

        logger.debug("Start");
        
        TakeOverServiceResult result = new TakeOverServiceResult();

        // 引継ぎコード作成「[1-9][0000000-9999999]」
        StringBuilder takeOverCode = new StringBuilder();
        takeOverCode.append( ( new Random().nextInt(8) )+1 );
        takeOverCode.append( new Random().nextInt(9999999) );
        
        //　有効期限作成
        Calendar cl = Calendar.getInstance();
        cl.add( Calendar.DAY_OF_MONTH, 3 );
        cl.set( cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cl.set( Calendar.MILLISECOND, 0 );        

        //  DB登録
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            DataTransferAdapter adapter = new DataTransferAdapter(dao.getConnection());
            
            //  古いの消す
            DataTransferEntity entitySel;
            entitySel = adapter.findByPrimaryKey(phrId);
            if( entitySel != null ){
                DataTransferEntity entityDel = new DataTransferEntity();
                entityDel.setPHR_ID(phrId);
                entityDel.setUpdateDateTime( entitySel.getUpdateDateTime() );
                dao.beginTransaction();
                adapter.delete(entityDel);
                dao.commitTransaction();
            }
            
            //  新しいの登録する
            DataTransferEntity entityAdd = new DataTransferEntity();
            entityAdd.setPHR_ID(phrId);
            entityAdd.setDataTransferCd( takeOverCode.toString() );
            entityAdd.setPassword( takeOverPassword );
            entityAdd.setExpirationDate(new Timestamp(cl.getTimeInMillis()) );
            
            //  トランザクション
            dao.beginTransaction();
            adapter.insert(entityAdd);
            dao.commitTransaction();
                    
            //  結果セット
            result.setResultCd(TakeOverResultCd.SUCCCESS );
            result.setPhrId( phrId );
            result.setTakeOverCode( takeOverCode.toString() );
            result.setExpirationDate( new java.sql.Timestamp(cl.getTimeInMillis()) );

            logger.info( "★引継ぎ：["+phrId+"] code:"+takeOverCode.toString()+" pw:"+ takeOverPassword + " limit:" + entityAdd.getExpirationDate() );
        
        } catch (Throwable ex) {
            //エラーが発生した場合はエラーを通知する。
            logger.error("", ex);

            //  結果セット
            result.setResultCd( TakeOverResultCd.SQLFAIL );
            result.setPhrId( phrId );
            result.setTakeOverCode( takeOverCode.toString() );
            result.setExpirationDate( new java.sql.Timestamp(cl.getTimeInMillis()) );

        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");

        //  結果返却
        return result;
    }

    @Override
    public TakeOverServiceResult checkTakeOverCode( String phrId, String takeOverPassword, String takeOverCode ) throws Throwable {

        logger.debug("Start");
        
        TakeOverServiceResult result = new TakeOverServiceResult();

        //  DB検索
        DataAccessObject dao = null;
        String newKeyId = UUID.randomUUID().toString().replaceAll("-", "");
        boolean checkOk = true;
        try {
            dao = new DataAccessObject();
            DataTransferAdapter adapter = new DataTransferAdapter(dao.getConnection());

            //  検索
            DataTransferEntity entitySel;
            entitySel = adapter.findDataTransferCd( takeOverCode );
            
            //  判定
            if( entitySel == null ){
                logger.debug("対象引継ぎデータ無し【" + takeOverCode + "】");
                checkOk = false;
            }
            if( entitySel !=null && entitySel.getPassword() != null ){
                if( !entitySel.getPassword().equals( takeOverPassword )){
                    logger.debug("パスワード間違い【" + takeOverPassword + "】");
                    checkOk = false;
                }
            }else{
                checkOk = false;
            }
            if( entitySel !=null && entitySel.getPHR_ID() != null ){
                if( !entitySel.getPHR_ID().equals( phrId )){
                    logger.debug("PHR ID違い【" + phrId + "】");
                    checkOk = false;
                }
            }else{
                checkOk = false;
            }            
            
            Timestamp nowDateTime = new Timestamp(System.currentTimeMillis());
            if( entitySel !=null && nowDateTime.after(entitySel.getExpirationDate() ) ){
                    logger.debug("有効期限切れ【" + entitySel.getExpirationDate() + "】");
                checkOk = false;
            }
            
            if( checkOk ){
                //  keyidを更新
                PatientAdapter adapterPatient = new PatientAdapter( dao.getConnection() );
                PatientEntity entityPatient;
                entityPatient = adapterPatient.findByPrimaryKey(phrId);
                entityPatient.setKyeId(newKeyId);
                dao.beginTransaction();
                adapterPatient.update( entityPatient );

                //  引継ぎ情報削除
                DataTransferAdapter adapterTa = new DataTransferAdapter( dao.getConnection() );
                DataTransferEntity entitySelTb;
                entitySelTb = adapter.findByPrimaryKey(phrId);
                DataTransferEntity entityUpdTb = new DataTransferEntity();
                if( entitySelTb != null ){
                    entityUpdTb.setPHR_ID(phrId);
                    entityUpdTb.setUpdateDateTime(entitySelTb.getUpdateDateTime() );
                    adapterTa.delete( entityUpdTb );
                }
                dao.commitTransaction();
            }

            
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            //エラーが発生した場合はエラーを通知する。
            logger.error("", ex);
            checkOk = false;
                
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

        //  結果セット
        if( checkOk ){
            //  OK
            result.setResultCd(TakeOverResultCd.SUCCCESS );
            result.setPhrId( phrId );
            result.setKeyId( newKeyId );
        }else{
            //  NG
            result.setResultCd(TakeOverResultCd.SQLFAIL);
            result.setResultCd(TakeOverResultCd.DATA_NOT_FOUND );
            result.setPhrId( "" );
            result.setKeyId( "" );
        }
        
        logger.debug("End");

        //  結果返却
        return result;
    }


    /**
     * 引継ぎコード用結果
     */
    public class TakeOverServiceResult {

        String phrId = null;
        TakeOverResultCd resultCd;
        String takeOverCode = null;
        Timestamp expirationDate = null;
        String takeOverPassword = null;
        String keyId = null;
        
        public String getPhrId() {
            return this.phrId;
        }
        
        public void setPhrId(String value) {
            this.phrId = value;
        }

        public TakeOverResultCd getResultCd() {
            return this.resultCd;
        }
        
        public void setResultCd(TakeOverResultCd value) {
            this.resultCd = value;
        }

        public String getTakeOverCode() {
            return this.takeOverCode;
        }
        
        public void setTakeOverCode(String value) {
            this.takeOverCode = value;
        }
        
        public Timestamp getExpirationDate() {
            return this.expirationDate;
        }

        public void setExpirationDate(Timestamp value) {
            this.expirationDate = value;
        }

        public String getTakeOverPassword() {
            return this.takeOverPassword;
        }
        
        public void setTakeOverPassword(String value) {
            this.takeOverPassword = value;
        }

        public String getKeyId() {
            return this.keyId;
        }
        
        public void setKeyId(String value) {
            this.keyId = value;
        }
    }
    
    /**
     *  結果コード 
     */
    public enum TakeOverResultCd {
        SUCCCESS,
        DATA_NOT_FOUND,
        SQLFAIL;
    }

}
