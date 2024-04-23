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
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.adapter.CommunicationReceiverAdapter;
import phr.datadomain.adapter.InsurerAdapter;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.service.IMessageSelectService;

/**
 *
 * @author kis.o-note-003
 */
public class MessageSelectService implements IMessageSelectService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UserAuthenticationService.class);
    
    /**
     *メッセージ詳細の取得を行う
     * @param CommunicationId
     * @return
     * @throws Throwable
     */
    @Override
    public CommunicationEntity getMessageDetail(String CommunicationId) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject();
        CommunicationEntity rtnCommunicationEntity = null;
    	try{
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            dao.beginTransaction();
            CommunicationEntity account = adapter.findMessageDetail(CommunicationId);

            rtnCommunicationEntity = account;
    	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	}finally{
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

        logger.trace("end");
        return rtnCommunicationEntity;
    }
    
    
    public CommunicationEntity getHospitalInfo(String phrId) throws Throwable {
        logger.trace("Start");
        
        CommunicationEntity rtn = null;

        logger.trace("end");
        return rtn;
    }
    
    /**
     * 医療機関情報一覧用結果
     */
    public class MedInfoResult {

        String phrId = null;
        MedInfoResultCd resultCd;
        List<List<String>>medInfoList = new ArrayList<>();

        
        public String getPhrId() {
            return this.phrId;
        }
        
        public void setPhrId(String value) {
            this.phrId = value;
        }

        public MedInfoResultCd getResultCd() {
            return this.resultCd;
        }
        
        public void setResultCd(MedInfoResultCd value) {
            this.resultCd = value;
        }

        public List<List<String>> getMedInfoList() {
            return this.medInfoList;
        }
        
        public void setMedInfoList(List<List<String>> value) {
            this.medInfoList = value;
        }
    }
    
    /**
     *  結果コード 
     */
    public enum MedInfoResultCd {
        SUCCCESS,
        DATA_NOT_FOUND,
        SQLFAIL;
    }
    
    /**
     * 保険者名の取得を行う
     * @param insurerNo
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public String insurerSerarch(String insurerNo) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            InsurerAdapter adapter = new InsurerAdapter(dao.getConnection());
            InsurerEntity entity = adapter.findByPrimaryKey(insurerNo);
            String insurerName = entity.getInsurerName();
            
            return insurerName;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    /**
     * PHRIDの検索を行う
     * @param comId
     * @return
     * @throws Throwable 
     */
    @Override
    public String phrIdSerarch(String comId) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            CommunicationEntity entity = adapter.findByPrimaryKey(comId);
            String phrId = entity.getSendPHRID();
            
            return phrId;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    /**
     *未読を既読に変更する
     * @param CommunicationId
     * @param insurerNo
     * @return
     * @throws Throwable
     */
    @Override
    public boolean changeReadFlg(String CommunicationId,String insurerNo) throws Throwable {
        DataAccessObject dao = new DataAccessObject();
        CommunicationReceiverEntity entity = null;
        
        try{
            CommunicationReceiverAdapter adapter = new CommunicationReceiverAdapter(dao.getConnection());
            dao.beginTransaction();
            entity = adapter.findByIdInsurer(CommunicationId, insurerNo);
            
            entity.setReadFlg(true);
            adapter.update(entity);
            
            dao.commitTransaction();

    	}catch(Exception e){
            dao.rollbackTransaction();
            return false;
    	}finally{
            if (dao != null) {
                dao.close();
            }
    	}
        return true;
    }
    
    /**
     * 送信先医療機関の検索を行う
     * @param comId
     * @return
     * @throws Throwable 
     */
    @Override
    public List<CommunicationReceiverEntity> getMedInfo(String comId) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            CommunicationReceiverAdapter adapter = new CommunicationReceiverAdapter(dao.getConnection());
            List<CommunicationReceiverEntity> entityList = adapter.findMedInfo(comId);
            
            return entityList;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
}
