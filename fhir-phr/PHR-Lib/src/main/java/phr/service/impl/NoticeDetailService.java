/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.adapter.CommunicationReceiverAdapter;
import phr.datadomain.adapter.InsurerAdapter;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.service.INoticeDetailService;

/**
 *
 * おしらせ画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class NoticeDetailService implements INoticeDetailService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NoticeDetailService.class);
    
    /**
     * おしらせ情報の取得を行う
     * @param communicationId
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public CommunicationEntity noticeDetailSearch(String communicationId) throws Throwable {
        
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
     * おしらせ情報の削除を行う
     * @param communicationId
     * @throws java.lang.Throwable 
     */
    @Override
    public void noticeDelete(String communicationId) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            CommunicationEntity entity = new CommunicationEntity();
            entity.setCommunicationId(communicationId);
            dao.beginTransaction();
            int delCnt = adapter.deleteByCommunicationId(entity);
            dao.commitTransaction();

            CommunicationReceiverAdapter adapterReceiver = new CommunicationReceiverAdapter(dao.getConnection());
            CommunicationReceiverEntity entityReceiver = new CommunicationReceiverEntity();
            entityReceiver.setCommunicationId(communicationId);
            dao.beginTransaction();
            int delCntRec = adapterReceiver.deleteByCommunicationId(entityReceiver);
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
}
