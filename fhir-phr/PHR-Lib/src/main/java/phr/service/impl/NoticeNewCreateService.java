/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.adapter.CommunicationReceiverAdapter;
import phr.datadomain.adapter.InsurerAdapter;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.service.INoticeNewCreateService;

/**
 *
 * おしらせ画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class NoticeNewCreateService implements INoticeNewCreateService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NoticeNewCreateService.class);
    
    /**
     * おしらせ情報の取得を行う
     * @param communicationId
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public CommunicationEntity noticeNewInsert(String communicationId) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            CommunicationEntity entity = adapter.findByPrimaryKey(communicationId);
            
            return entity;
            
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
     * CommunicationIdのmaxの取得を行う
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public String maxCommunicationIdSearch() throws Throwable {
        return CommunicationAdapter.numberingCommunicationId();
    }
    
    /**
     * お知らせ情報(Communication)の登録を行う
     * @param entityCommunication
     * @throws java.lang.Throwable 
     */
    @Override
    public void insertCommunication(CommunicationEntity entityCommunication) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = null;

        try {
            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            
            dao.beginTransaction();
            adapter.insert(entityCommunication);
            dao.commitTransaction();
            
        } catch (Throwable ex) {
            dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    /**
     * お知らせ情報(CommunicationReceiver)の登録を行う
     * @param mapList
     * @throws java.lang.Throwable 
     */
    @Override
    public void insertCommunicationReceiver(List<CommunicationReceiverEntity> entityComRecList) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = null;

        try {
            dao = new DataAccessObject();
            CommunicationReceiverAdapter adapter = new CommunicationReceiverAdapter(dao.getConnection());
            
            dao.beginTransaction();
            for (int i = 0; i<entityComRecList.size(); i++) {
                adapter.insert(entityComRecList.get(i));
            }
            dao.commitTransaction();
            
        } catch (Throwable ex) {
            dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    /**
     * お知らせ情報(CommunicationReceiver)の登録を行う
     * @throws java.lang.Throwable 
     */
    @Override
    public List<InsurerPatientEntity> serchPhrId(String insurerNo) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = null;

        try {
            dao = new DataAccessObject();
            InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
            List<InsurerPatientEntity> entityList = adapter.findByInsurerNo(insurerNo);
            
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
