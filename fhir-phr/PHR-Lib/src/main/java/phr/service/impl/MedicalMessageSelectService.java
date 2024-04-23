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
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.service.IMedicalMessageSelectService;

/**
 *
 * @author kis.o-note-003
 */
public class MedicalMessageSelectService implements IMedicalMessageSelectService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalMessageSelectService.class);
    
    /**
     *メッセージ詳細の取得を行う
     * @param CommunicationId
     * @return
     * @throws Throwable
     */
    @Override
    public CommunicationEntity getMessageDetail(String medicalCd ,String CommunicationId) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject();
        CommunicationEntity rtnCommunicationEntity = null;
    	try{
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            dao.beginTransaction();
            CommunicationEntity account = adapter.findmedicalCom(CommunicationId,medicalCd);

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
        
    /**
     *メッセージ詳細の取得を行う
     * @param CommunicationId
     * @param MedicalOrganizationCd
     * @return
     * @throws Throwable
     */
    @Override
    public boolean changeReadFlg(String CommunicationId,String MedicalCd) throws Throwable {
        DataAccessObject dao = new DataAccessObject();
        CommunicationReceiverEntity entity = null;
        
        try{
            CommunicationReceiverAdapter adapter = new CommunicationReceiverAdapter(dao.getConnection());
            dao.beginTransaction();
            entity = adapter.findById(CommunicationId, MedicalCd);
            
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

}
