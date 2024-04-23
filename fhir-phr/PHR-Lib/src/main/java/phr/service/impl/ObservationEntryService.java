/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import phr.datadomain.DataAccessObject;
import phr.datadomain.ObservationEventInputType;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.Seq_ObservationEventIDEntity;
import phr.service.IAlertSetService;
import phr.service.IObservationEntryService;

/**
 *
 * @author kis-note-025
 */
public class ObservationEntryService implements IObservationEntryService {

//    private static final Log logger = LogFactory.getLog(ObservationEntryService.class);
    private static Logger logger = Logger.getLogger(ObservationEntryService.class);
    /**
     * 検査結果の作成
     * @param oEntity
     * @param resultList
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean insertObservationAndObservationEvent(ObservationEventEntity oEntity, List<ObservationEntity> resultList) throws Throwable  {

        Seq_ObservationEventIDEntity seq_entity = null;
        DataAccessObject dao = null;
        boolean result = false;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter oeAdapter = new ObservationEventAdapter(dao.getConnection());
            ObservationAdapter oAdapter = new ObservationAdapter(dao.getConnection());
            List<ObservationEntity> oList = new ArrayList<>();
            dao.beginTransaction();	// トランザクション開始
            //最新のObservationEventIdを取得
            //seq_entity = adapter.getMaxObservationEventId();
            
            // 使える番号でてくるまで取得する
            String observationId;
            do{
                observationId = ObservationEventAdapter.numberingObservationEventId();
            }while( oeAdapter.findByPrimaryKey( observationId ) != null );
            
            //検査結果の登録
            oEntity.setObservationEventId(observationId);
            oeAdapter.insert(oEntity);
            //検査結果項目の登録
            for (ObservationEntity res : resultList) {
                res.setObservationEventId(observationId);
                logger.debug(res.getObservationDefinitionId());
                oAdapter.insert(res);
                oList.add(res);
            }
            dao.commitTransaction();
            
            //アラート登録
            IAlertSetService a_service = new AlertSetService();
            if (oEntity.getDataInputTypeCd() == ObservationEventInputType.KENNSA.getCode()) {
                a_service.alertSet(oEntity.getPHR_ID(), oEntity.getInsurerNo() , null, oList, oEntity.getExaminationDate()); 
            } else if (oEntity.getDataInputTypeCd() == ObservationEventInputType.KENNSHIN.getCode()) {
                a_service.checkupalertSet(oEntity.getPHR_ID(), oEntity.getInsurerNo() , oList, oEntity.getExaminationDate());                
            }
            
            result = true;
        } catch (Throwable ex) {
            if( dao !=null ){
                dao.rollbackTransaction();
            }
            Logger.getLogger(ObservationEntryService.class.getName()).log(Level.WARN, null, ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return result;
    }

    /**
     * 検査依頼IDから検査結果の検索
     *
     * @param phrid
     * @param orderNo
     * @return
     * @throws Throwable
     */
    @Override
    public ObservationEventEntity searchObsevationByOrderNo(String phrid, String orderNo) throws Throwable {
        ObservationEventEntity entity = null;
        DataAccessObject dao = new DataAccessObject();
        try {
            ObservationEventAdapter adapter = new ObservationEventAdapter((dao.getConnection()));
            entity = adapter.findByIdAndOrderNo(phrid, orderNo);
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

    /**
     * 既存検査結果の検査結果項目の更新
     * @param updateList
     * @param insertList
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean updateObservationAndObservationEvent(List<ObservationEntity> updateList, List<ObservationEntity> insertList) throws Throwable {
       boolean result = false;
       
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationAdapter oAdapter = new ObservationAdapter(dao.getConnection());

            dao.beginTransaction();	// トランザクション開始

            //既存検査結果の更新
            for (ObservationEntity res : updateList) {
                oAdapter.update(res);
            }

            //新規検査項目結果の登録            
             for (ObservationEntity res : insertList) {
                oAdapter.insert(res);
            }           
            dao.commitTransaction();
            result = true;
        } catch (Throwable ex) {
            dao.rollbackTransaction();
            Logger.getLogger(ObservationEntryService.class.getName()).log(Level.WARN, null, ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
       
       return result;
    }

}
