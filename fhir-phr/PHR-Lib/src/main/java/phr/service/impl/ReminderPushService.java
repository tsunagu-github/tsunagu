/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import com.mysql.jdbc.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DiseaseTypeAdapter;
import phr.datadomain.adapter.InsurerDiseaseTypeAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationDefinitionInsurerAdapter;
import phr.datadomain.adapter.ReminderMasterAdapter;
import phr.datadomain.adapter.ReminderItemAdapter;
import phr.datadomain.adapter.ReminderKindAdapter;
import phr.datadomain.adapter.ReminderPushedListAdapter;
import phr.datadomain.adapter.ReminderMessageAdapter;
import phr.datadomain.adapter.ReminderTypeAdapter;
import phr.datadomain.adapter.InsurerViewDefinitionAdapter;
import phr.datadomain.entity.InsurerDiseaseTypeEntity;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ReminderItemEntity;
import phr.datadomain.entity.ReminderKindEntity;
import phr.datadomain.entity.ReminderMasterEntity;
import phr.datadomain.entity.ReminderMessageEntity;
import phr.datadomain.entity.ReminderTargetPhrIdItemEntity;
import phr.datadomain.entity.ReminderTypeEntity;
import phr.service.IReminderPushService;

/**
 *
 * @author chiba
 */
public class ReminderPushService implements IReminderPushService{

    private static final Log logger = LogFactory.getLog(ReminderPushService.class);

    /**
     * 最終検査日から指定日数以上超過しているPHR-ID・検査情報・最終検査日を抽出する
     * @param reminderTypeId    リマインダ種別ID
     * @param reminderLevel     リマインダレベル
     * @return
     * @throws Throwable 
     */
    @Override
    public List<ReminderTargetPhrIdItemEntity> getPushTargetListPeriod(String reminderTypeId, int reminderLevel) throws Throwable {
        logger.debug("Start");
        
        List<ReminderTargetPhrIdItemEntity> entities = null;

        //  DB検索
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            entities = adapter.findExpiredData(reminderTypeId, reminderLevel);
        } catch (Throwable ex) {
            logger.error("", ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        logger.debug("End");
        return entities;
    }

    /**
     * 通知有効なリマインダメッセージを抽出する
     * @return
     * @throws Throwable 
     */
    @Override
    public List<ReminderMessageEntity> getPushMessageList() throws Throwable {
        logger.debug("Start");
        
        List<ReminderMessageEntity> entities = null;
        //  DB検索
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ReminderMessageAdapter adapter = new ReminderMessageAdapter(dao.getConnection());
            entities = adapter.getPushMessageList();
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        logger.debug("End");
        return entities;
    }

    @Override
    public List<ReminderMasterEntity> getReminderList(int year) throws Throwable {
        logger.debug("Start");
        
        List<ReminderMasterEntity> entities = null;
        //  DB検索
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ReminderMasterAdapter adapter = new ReminderMasterAdapter(dao.getConnection());
            entities = adapter.findByYear(year);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        logger.debug("End");
        return entities;
    }

    @Override
    public void deleteReminder(String reminderTypeId) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            
            ReminderMasterAdapter reminderMasterAdapter =
                    new ReminderMasterAdapter(dao.getConnection());
            ReminderItemAdapter reminderItemAdapter =
                    new ReminderItemAdapter(dao.getConnection());
            ReminderKindAdapter reminderKindAdapter =
                    new ReminderKindAdapter(dao.getConnection());
            ReminderMessageAdapter reminderMessageAdapter =
                    new ReminderMessageAdapter(dao.getConnection());
            ReminderPushedListAdapter reminderPushedListAdapter =
                    new ReminderPushedListAdapter(dao.getConnection());

            ReminderMasterEntity rm = new ReminderMasterEntity();
            rm.setReminderTypeId(reminderTypeId);

            reminderMasterAdapter.delete(rm);
            reminderItemAdapter.deleteByReminderTypeId(reminderTypeId);
            reminderKindAdapter.deleteByReminderTypeId(reminderTypeId);
            reminderMessageAdapter.deleteByReminderTypeId(reminderTypeId);
            reminderPushedListAdapter.deleteByReminderTypeId(reminderTypeId);

            dao.commitTransaction();
        } catch (Throwable ex) {
            if (dao != null)
                dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    @Override
    public List<ObservationDefinitionEntity> getReminderItemList(int viewId) throws Throwable {
        logger.debug("Start");
        
        List<ObservationDefinitionEntity> entities = null;
        //  DB検索
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationDefinitionInsurerAdapter adapter =
                    new ObservationDefinitionInsurerAdapter(dao.getConnection());
            entities = adapter.findEnabledByViewId(viewId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        logger.debug("End");
        return entities;
    }

    @Override
    public List<ReminderItemEntity> getCheckedReminderItemList(String reminderTypeId) throws Throwable {
        logger.debug("Start");
        
        List<ReminderItemEntity> entities = null;
        //  DB検索
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ReminderItemAdapter adapter = new ReminderItemAdapter(dao.getConnection());
            entities = adapter.findByReminderTypeId(reminderTypeId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        logger.debug("End");
        return entities;
    }

    @Override
    public List<ReminderKindEntity> getCheckedReminderKindList(String reminderTypeId) throws Throwable {
        logger.debug("Start");
        
        List<ReminderKindEntity> entities = null;
        //  DB検索
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ReminderKindAdapter adapter = new ReminderKindAdapter(dao.getConnection());
            entities = adapter.findByReminderTypeId(reminderTypeId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        logger.debug("End");
        return entities;
    }

    @Override
    public ReminderMasterEntity getReminder(String reminderTypeId) throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ReminderMasterAdapter adapter =
                    new ReminderMasterAdapter(dao.getConnection());
            
            return adapter.findByPrimaryKey(reminderTypeId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    @Override
    public List<ReminderMessageEntity> getReminderMessageList(String reminderTypeId) throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ReminderMessageAdapter adapter =
                    new ReminderMessageAdapter(dao.getConnection());
            
            return adapter.findByReminderTypeId(reminderTypeId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
     }

    @Override
    public List<ReminderTypeEntity> getReminderTypeList() throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ReminderTypeAdapter adapter =
                    new ReminderTypeAdapter(dao.getConnection());
            
            return adapter.findAll();
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    @Override
    public List<String> checkReminder(ReminderMasterEntity entity, List<ReminderMessageEntity> messageList, List<String> kindList, List<String> itemList) throws Throwable {

        List<String> messages = new ArrayList<>();

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerDiseaseTypeAdapter adapter =
                    new InsurerDiseaseTypeAdapter(dao.getConnection());
            List<InsurerDiseaseTypeEntity> list = adapter.findByViewId(entity.getViewId());
            if (!list.stream()
                    .map(e -> { return String.valueOf(e.getDiseaseTypeCd());})
                    .distinct()
                    .collect(Collectors.toList())
                    .containsAll(kindList)) {
                messages.add("管理項目設定画面で未チェックの疾病種別がチェックされています。");
            }
            if ("0".equals(entity.getSendType())) {
                if (!list.stream()
                        .filter(e -> { return kindList.contains(String.valueOf(e.getDiseaseTypeCd()));})
                        .map(e -> { return e.getObservationDefinitionId();})
                        .distinct()
                        .collect(Collectors.toList())
                        .containsAll(itemList)) {
                    messages.add("管理項目設定画面で結びつけた疾病種別を未チェックですが関係する項目のみをチェックしています。");
                }
            }
            return messages;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    @Override
    public String registerReminder(ReminderMasterEntity entity, List<ReminderMessageEntity> messageList, List<String> kindList, List<String> itemList) throws Throwable {

        int removeBanDays = 30; // TODO

        if (kindList == null) {
            kindList = new ArrayList<>();
        }

        if (itemList == null) {
            itemList = new ArrayList<>();
        }

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            
            InsurerViewDefinitionAdapter insurerViewDefinitionAdapter =
                    new InsurerViewDefinitionAdapter(dao.getConnection());
            ReminderTypeAdapter reminderTypeAdapter =
                    new ReminderTypeAdapter(dao.getConnection());
            DiseaseTypeAdapter diseaseTypeAdapter =
                    new DiseaseTypeAdapter(dao.getConnection());
            ObservationDefinitionInsurerAdapter observationDefinitionInsurerAdapter =
                    new ObservationDefinitionInsurerAdapter(dao.getConnection());
            ReminderMasterAdapter reminderMasterAdapter =
                    new ReminderMasterAdapter(dao.getConnection());
            ReminderMessageAdapter reminderMessageAdapter =
                    new ReminderMessageAdapter(dao.getConnection());
            ReminderItemAdapter reminderItemAdapter =
                    new ReminderItemAdapter(dao.getConnection());
            ReminderKindAdapter reminderKindAdapter =
                    new ReminderKindAdapter(dao.getConnection());
            
            if (insurerViewDefinitionAdapter.findByPrimaryKey(entity.getViewId()) == null) {
                throw new RuntimeException();
            }
            
            if (reminderTypeAdapter.findByPrimaryKey(entity.getReminderTypeCd()) == null) {
                throw new RuntimeException();
            }

            if (!diseaseTypeAdapter.findDiseaseTypeList(entity.getViewId())
                    .stream()
                    .filter(e -> { return 0 < e.getDiseaseTypeCd();})
                    .map(e -> { return String.valueOf(e.getDiseaseTypeCd());})
                    .collect(Collectors.toList())
                    .containsAll(kindList)) {
                throw new RuntimeException();
            }

            if (!observationDefinitionInsurerAdapter.findEnabledByViewId(entity.getViewId())
                    .stream()
                    .map(e -> { return e.getObservationDefinitionId();})
                    .collect(Collectors.toList())
                    .containsAll(itemList)) {
                throw new RuntimeException();
            }

            String reminderTypeId;

            if (StringUtils.isNullOrEmpty(entity.getReminderTypeId())) {

                reminderTypeId = reminderMasterAdapter.findMaxId();
                if (reminderTypeId == null) {
                    reminderTypeId = "A0000001";
                } else {
                    reminderTypeId = String.format("A%07d",
                            Integer.valueOf(reminderTypeId.substring(1)) + 1);
                }

                entity.setReminderTypeId(reminderTypeId);

                reminderMasterAdapter.insert(entity);

                for (ReminderMessageEntity message : messageList) {
                    message.setReminderTypeId(reminderTypeId);
                    message.setRemoveBanDays(removeBanDays);
                    reminderMessageAdapter.insert(message);
                }

                for (String item : itemList) {
                    ReminderItemEntity reminderItemEntity = new ReminderItemEntity();
                    reminderItemEntity.setReminderTypeId(reminderTypeId);
                    reminderItemEntity.setObservationDefinitionId(item);
                    reminderItemAdapter.insert(reminderItemEntity);
                }

                for (String kind : kindList) {
                    ReminderKindEntity reminderKindEntity = new ReminderKindEntity();
                    reminderKindEntity.setReminderTypeId(reminderTypeId);
                    reminderKindEntity.setDiseaseTypeCd(Integer.valueOf(kind));
                    reminderKindAdapter.insert(reminderKindEntity);
                }
            } else {
                reminderTypeId = entity.getReminderTypeId();

                if (reminderMessageAdapter.findByReminderTypeId(reminderTypeId).size() !=
                        messageList.size()) {
                    throw new RuntimeException();
                }

                int updated = reminderMasterAdapter.update(entity);
                if (updated != 1) {
                    throw new RuntimeException();
                }

                for (ReminderMessageEntity message : messageList) {
                    message.setReminderTypeId(reminderTypeId);
                    message.setRemoveBanDays(removeBanDays);
                    int messageUpdated = reminderMessageAdapter.update(message);
                    if (messageUpdated != 1) {
                        throw new RuntimeException();
                    }
                }

                reminderItemAdapter.deleteByReminderTypeId(reminderTypeId);
                for (String item : itemList) {
                    ReminderItemEntity reminderItemEntity = new ReminderItemEntity();
                    reminderItemEntity.setReminderTypeId(reminderTypeId);
                    reminderItemEntity.setObservationDefinitionId(item);
                    reminderItemAdapter.insert(reminderItemEntity);
                }

                reminderKindAdapter.deleteByReminderTypeId(reminderTypeId);
                for (String kind : kindList) {
                    ReminderKindEntity reminderKindEntity = new ReminderKindEntity();
                    reminderKindEntity.setReminderTypeId(reminderTypeId);
                    reminderKindEntity.setDiseaseTypeCd(Integer.valueOf(kind));
                    reminderKindAdapter.insert(reminderKindEntity);
                }
            }

            dao.commitTransaction();

            return reminderTypeId;
        } catch (Throwable ex) {
            if (dao != null)
                dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }
    
    /**
     * 同意通知メッセージを抽出する
     * @return
     * @throws Throwable 
     */
    @Override
    public ReminderMessageEntity getConsentNotificationControllerMessage() throws Throwable {
        logger.debug("Start");
        
        ReminderMessageEntity entity = null;
        //  DB検索
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ReminderMessageAdapter adapter = new ReminderMessageAdapter(dao.getConnection());
            entity = adapter.getConsentNotificationControllerMessage();
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        logger.debug("End");
        return entity;
    }
}
