package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IObservationService;

/**
 * 
 * @author kisvdi017
 *
 */
public class ObservationService implements IObservationService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationService.class);

    /**
     * ObservationEventIdに紐づくobservationリソースをすべて取得する(自己管理)
     */
    @Override
    public List<ObservationEntity> getObservation1ByObservationEventId(String ObservationEventId) throws Throwable {
        List<ObservationEntity> list = new ArrayList<ObservationEntity>();
        DataAccessObject dao = new DataAccessObject();
        
        try {
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            list = adapter.getObservation1ByObservationEventId(ObservationEventId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        return list;
    }
    
    /**
     * ObservationEventIdに紐づくobservationリソースをすべて取得する(自己測定、特定検診)
     */
    @Override
    public List<ObservationEntity> getObservation23ByObservationEventId(String ObservationEventId) throws Throwable {
        List<ObservationEntity> list = new ArrayList<ObservationEntity>();
        DataAccessObject dao = new DataAccessObject();
        
        try {
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            list = adapter.getObservation23ByObservationEventId(ObservationEventId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        return list;
    }

    /**
     * ObservationEventIdからObservationレコードを取得し、管理疾患が含まれているか確認
     * @param id
     * @return list
     * @throws Throwable 
     */
    public List<Integer> findById(String id) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao=null;
        List<Integer> list = new ArrayList<>();
        List<ObservationEntity> observationList = new ArrayList<>();
        
        try {
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter((dao.getConnection()));
            // 該当のObservationレコードを取得
            observationList = adapter.findById(id);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception ex){
                logger.debug(ex);
            }
        }
        
        // 取得したリストに疾病管理項目があるか確認
        for (ObservationEntity e : observationList) {
            if (e.getObservationDefinitionId().equals("0000000011")) {
                list.add(1);
            }
            if (e.getObservationDefinitionId().equals("0000000015")) {
                list.add(2);
            }
            if (e.getObservationDefinitionId().equals("0000000019")) {
                list.add(3);
            }
            if (e.getObservationDefinitionId().equals("0000000021")) {
                list.add(4);
            }
            if (e.getObservationDefinitionId().equals("0000000020")) {
                list.add(5);
            }
            if (e.getObservationDefinitionId().equals("0000000103")) {
                list.add(6);
            }
        }
        
        logger.debug("End");
        return list;
    }
}
