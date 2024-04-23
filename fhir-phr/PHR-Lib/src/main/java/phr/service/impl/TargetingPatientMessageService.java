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
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.adapter.ObsevationLabelAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.ObservationLabelEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalMessageReplayService;
import phr.service.IMedicalMessageSelectService;
import phr.service.ITargetingPatientMessageService;

/**
 *
 * @author kis.o-note-003
 */
public class TargetingPatientMessageService implements ITargetingPatientMessageService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(TargetingPatientMessageService.class);
           
    /**
     *　メッセージの登録を行う
     * @param CommunicationId
     * @return
     * @throws Throwable
     */
    @Override
    public boolean submitMessage(CommunicationEntity pentity , List<CommunicationReceiverEntity> rList) throws Throwable{
        logger.trace("start");

        DataAccessObject dao = new DataAccessObject();
        PatientEntity account = null;
    	try{
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            dao.beginTransaction();
            String communicationId = CommunicationAdapter.numberingCommunicationId();
            //String max = adapter.findMaxCommunicationId();
            //Integer maxid = Integer.parseInt(max) + 1;
            pentity.setCommunicationId(communicationId);
            adapter.insert(pentity);
            
            CommunicationReceiverAdapter radapter = new CommunicationReceiverAdapter(dao.getConnection());
            for(CommunicationReceiverEntity rentity : rList){
                rentity.setCommunicationId(communicationId);
                radapter.insert(rentity);
            }
            
            dao.commitTransaction();

    	}catch(Exception e){
            dao.rollbackTransaction();
            return false;
    	}finally{
            if (dao != null) {
                dao.close();
            }
    	}

        logger.trace("end");

        return true;
    }
    
     /**
     *　医療機関一覧の取得を行う
     * @param phrID
     * @return
     * @throws Throwable
     */
    @Override
    public List<MedicalOrganizationPatientEntity> getMedicalList(String phrId) throws Throwable{
        DataAccessObject dao = new DataAccessObject();
        List<MedicalOrganizationPatientEntity> medicalList = null;
    	try{
            MedicalOrganizationPatientAdapter adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
            dao.beginTransaction();
            
            medicalList = adapter.getMedicalList(phrId);

     	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	}finally{
            if (dao != null) {
                dao.close();
            }
        }

        logger.trace("end");
        return medicalList;
    }

     /**
     * PHRIDから保険者患者関連情報の取得
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    @Override
    public String getInsurerPatientInfo(String phrId) throws Throwable {
        InsurerPatientEntity entity = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
            entity = adapter.findByPhrId(phrId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return entity.getInsurerNo();
    }
}
