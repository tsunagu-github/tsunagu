/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.adapter.CommunicationReceiverAdapter;
import phr.datadomain.adapter.InsurerAdapter;
import phr.datadomain.adapter.ReminderMessageAdapter;
import phr.datadomain.adapter.ReminderPushedListAdapter;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.datadomain.entity.ReminderMessageEntity;
import phr.datadomain.entity.ReminderPushedListEntity;
import phr.service.ICommunicationService;

/**
 *
 * @author KISNOTE011
 */
public class CommunicationService implements ICommunicationService {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AlertSearchService.class);

    /**
     * 未読件数を取得する
     * @param phrid
     * @return
     * @throws Throwable 
     */
    @Override
    public int countUnreadCommunication(String phrid) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            CommunicationReceiverAdapter adapter = new CommunicationReceiverAdapter(dao.getConnection());
            int count = adapter.countUnreadMessage(phrid);

            logger.debug("End");
            return count;
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * PHRIDからおしらせ及びメッセージを検索します。（複数件直近情報）
     *
     * @param phrid
     * @param organizationCd
     * @param basedate
     * @param targetCd
     * @return
     * @Throwable
     */
    @Override
    public List<CommunicationEntity> SearchCommunicationByPhridOrganization(String phrid, String organizationCd, Timestamp basedate, int targetCd) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            List<CommunicationEntity> entity = adapter.findByPhridOrganization(phrid, organizationCd, basedate, targetCd);

            logger.debug("End");
            return entity;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * コミニュケーションID,Seqからおしらせ及びメッセージを検索します
     *
     * @param communicationId
     * @param seq
     * @return
     * @Throwable
     */
    @Override
    public List<CommunicationEntity> SearchCommunicationByCommuidAndSeq(String communicationId, int seq) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            List<CommunicationEntity> entities = adapter.findByCommuidAndSeq(communicationId, seq);

            logger.debug("End");
            return entities;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * 対象のおしらせ及びメッセージを既読に更新します
     *
     * @param entity
     * @return
     * @Throwable
     */
    @Override
    public int UpdateCommunicationByReadFlg(CommunicationReceiverEntity entity) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            CommunicationReceiverAdapter adapter = new CommunicationReceiverAdapter(dao.getConnection());
            dao.beginTransaction();
            int iRet = adapter.update(entity);
            dao.commitTransaction();

            logger.debug("End");
            return iRet;
        } catch (Throwable ex) {
            logger.error("", ex);
            if (dao != null) {
                dao.rollbackTransaction();
            }
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * （患者View向け）PHRIDから受信＆送信済みの、おしらせ及びメッセージを検索します。
     *
     * @param phrid
     * @param basedate
     * @return
     * @Throwable
     */
    @Override
    public List<CommunicationEntity> SearchCommunicationByPhridForPatient(String phrid, Timestamp basedate) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            List<CommunicationEntity> entity = adapter.findByPhridForPatient(phrid, basedate);

            logger.debug("End");
            return entity;
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * 指定された医療機関または保険者へのメッセージを登録します。
     *
     * @param entity
     * @param receiverList
     * @Throwable
     */
    @Override
    public void CreateCommunicationForPatient(CommunicationEntity entity, List<CommunicationReceiverEntity> receiverList) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.trace("Start");

            dao = new DataAccessObject();
            dao.beginTransaction();

            // コミュニケーション情報の作成
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            String communicationId = CommunicationAdapter.numberingCommunicationId();
            entity.setCommunicationId(communicationId);
            adapter.insert(entity);

            // 受信者情報の作成
            CommunicationReceiverAdapter recadapter = new CommunicationReceiverAdapter(dao.getConnection());
            if (receiverList != null) {
                int i = 0;
                for (CommunicationReceiverEntity receiver : receiverList) {
                    receiver.setCommunicationId(communicationId);
                    receiver.setSeq(i++);
                    recadapter.insert(receiver);
                }
            }

            dao.commitTransaction();
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    /**
     * PHRIDから、患者の加入している保険者情報を検索します。
     *
     * @param phrid
     * @return
     * @Throwable
     */
    @Override
    public InsurerEntity SearchInsurerByPatientPhrid(String phrid) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            InsurerAdapter adapter = new InsurerAdapter(dao.getConnection());
            InsurerEntity entity = adapter.findByPatientPhrId(phrid);

            logger.debug("End");
            return entity;
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * 指定された患者へのリマインダプッシュメッセージを登録します。
     *
     * @param phrId
     * @param reminderMessageList
     * @throws java.lang.Throwable
     */
    @Override
    public void CreateCommunicationForReminderPush(String phrId, List<ReminderMessageEntity> reminderMessageList) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.trace("Start");

            Date now = new Date();
            Timestamp timestamp = new Timestamp(now.getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date today = calendar.getTime();

            dao = new DataAccessObject();
            dao.beginTransaction();

            // 保険者の取得
            InsurerAdapter insurerAdapter = new InsurerAdapter(dao.getConnection());
            InsurerEntity insurerEntity = insurerAdapter.findByPatientPhrId(phrId);

            // リマインダメッセージの取得
            StringBuilder message = new StringBuilder();
            message.append("一定期間検査結果の記録されていない方などにお送りしています。\r\n");
            message.append("行き違いで受診や検査をされています場合はご容赦ください。\r\n");

            List<ReminderMessageEntity> cachedReminderMessage = new ArrayList<>();
            ReminderMessageAdapter reminderMessageAdapter = new ReminderMessageAdapter(dao.getConnection());
            for (ReminderMessageEntity reminderMessage : reminderMessageList) {
                ReminderMessageEntity entity = reminderMessageAdapter.findByPrimaryKey(
                        reminderMessage.getReminderTypeId(), reminderMessage.getReminderLevel());
                if (entity == null) {
                    continue;
                }
                message.append("=============================\r\n");
                message.append(entity.getTitle()).append("\r\n");
                message.append("=============================\r\n");
                message.append(entity.getSendMessage()).append("\r\n\r\n");
                cachedReminderMessage.add(entity);
            }

            // コミュニケーション情報の作成
            CommunicationAdapter communicationAdapter = new CommunicationAdapter(dao.getConnection());
            String communicationId = CommunicationAdapter.numberingCommunicationId();
            CommunicationEntity communicationEntity = new CommunicationEntity();
            communicationEntity.setCommunicationId(communicationId);
            communicationEntity.setCommunicationTypeCd(2);
            communicationEntity.setSendInsurerNo(insurerEntity.getInsurerNo());
            communicationEntity.setSendAccountId("PushService");
            communicationEntity.setSenderName(insurerEntity.getInsurerName());
            communicationEntity.setSubject("検査のおすすめ");
            communicationEntity.setBodyText(message.toString());
            communicationEntity.setCreateDateTime(timestamp);
            communicationEntity.setUpdateDateTime(timestamp);
            communicationAdapter.insert(communicationEntity);

            // 受信者情報の作成
            CommunicationReceiverAdapter communicationReceiverAdapter = new CommunicationReceiverAdapter(dao.getConnection());
            CommunicationReceiverEntity communicationReceiverEntity = new CommunicationReceiverEntity();
            communicationReceiverEntity.setCommunicationId(communicationId);
            communicationReceiverEntity.setSeq(0);
            communicationReceiverEntity.setReadFlg(false);
            communicationReceiverEntity.setPHR_ID(phrId);
            communicationReceiverEntity.setCreateDateTime(timestamp);
            communicationReceiverEntity.setUpdateDateTime(timestamp);
            communicationReceiverAdapter.insert(communicationReceiverEntity);

            // リマインダプッシュ完了リストの作成
            ReminderPushedListAdapter reminderPushedListAdapter = new ReminderPushedListAdapter(dao.getConnection());
            for (ReminderMessageEntity entity : cachedReminderMessage) {

                calendar.setTime(today);
                calendar.add(Calendar.DAY_OF_MONTH, entity.getRemoveBanDays());
                Date removeBanDate = calendar.getTime();

                ReminderPushedListEntity reminderPushedListEntity = new ReminderPushedListEntity();
                reminderPushedListEntity.setPHR_ID(phrId);
                reminderPushedListEntity.setReminderTypeId(entity.getReminderTypeId());
                reminderPushedListEntity.setReminderLevel(entity.getReminderLevel());
                reminderPushedListEntity.setPushDate(today);
                reminderPushedListEntity.setRemoveBanDate(removeBanDate);
                if (reminderPushedListAdapter.update(reminderPushedListEntity) == 0) {
                    reminderPushedListAdapter.insert(reminderPushedListEntity);
                }
                while (1 < reminderPushedListEntity.getReminderLevel()) {
                    reminderPushedListEntity.setReminderLevel(
                            reminderPushedListEntity.getReminderLevel() - 1);
                    if (reminderPushedListAdapter.updateRemoveBanDate(reminderPushedListEntity) == 0) {
                        reminderPushedListAdapter.insert(reminderPushedListEntity);
                    }
                }
            }

            // Cloud Messaging
            FCMNotificationService fcmNotificationService = new FCMNotificationService();
            int pushSuccessNumber = fcmNotificationService.sendFCMMessage(
                    communicationEntity,
                    Arrays.asList(new CommunicationReceiverEntity[]{communicationReceiverEntity}),
                    1);
            logger.info("Push success number:" + pushSuccessNumber);

            dao.commitTransaction();
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    /**
     * PhrID,コミニュケーションIDからReceiverを検索します
     *
     * @param communicationId
     * @param phrid
     * @return
     * @Throwable
     */
    @Override
    public CommunicationReceiverEntity SearchReceiverByCommuidAndPhrid(String communicationId, String phrid) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            CommunicationReceiverAdapter adapter = new CommunicationReceiverAdapter(dao.getConnection());
            CommunicationReceiverEntity entitie = adapter.findByIdPhrid(communicationId, phrid);

            logger.debug("End");
            return entitie;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }
    
    /**
     * 指定された患者への同意通知を登録、送信します。
     *
     * @param phrId
     * @param reminderMessageList
     * @throws java.lang.Throwable
     */
    @Override
    public void CreateSendCommunication(String phrId, List<ReminderMessageEntity> reminderMessageList) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.trace("Start");

            Date now = new Date();
            Timestamp timestamp = new Timestamp(now.getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date today = calendar.getTime();

            dao = new DataAccessObject();
            dao.beginTransaction();

            // 保険者の取得
            InsurerAdapter insurerAdapter = new InsurerAdapter(dao.getConnection());
            InsurerEntity insurerEntity = insurerAdapter.findByPatientPhrId(phrId);

            // リマインダメッセージの取得
            StringBuilder message = new StringBuilder();

            List<ReminderMessageEntity> cachedReminderMessage = new ArrayList<>();
            ReminderMessageAdapter reminderMessageAdapter = new ReminderMessageAdapter(dao.getConnection());
            for (ReminderMessageEntity reminderMessage : reminderMessageList) {
                ReminderMessageEntity entity = reminderMessageAdapter.findByPrimaryKey(
                        reminderMessage.getReminderTypeId(), reminderMessage.getReminderLevel());
                if (entity == null) {
                    continue;
                }
                message.append("=============================\r\n");
                message.append(entity.getTitle()).append("\r\n");
                message.append("=============================\r\n");
                message.append(entity.getSendMessage()).append("\r\n\r\n");
                cachedReminderMessage.add(entity);
            }

            // コミュニケーション情報の作成
            CommunicationAdapter communicationAdapter = new CommunicationAdapter(dao.getConnection());
            String communicationId = CommunicationAdapter.numberingCommunicationId();
            CommunicationEntity communicationEntity = new CommunicationEntity();
            communicationEntity.setCommunicationId(communicationId);
            communicationEntity.setCommunicationTypeCd(2);
            communicationEntity.setSendInsurerNo(insurerEntity.getInsurerNo());
            communicationEntity.setSendAccountId("PushService");
            communicationEntity.setSenderName(insurerEntity.getInsurerName());
            communicationEntity.setSubject("健康情報の活用同意について");
            communicationEntity.setBodyText(message.toString());
            communicationEntity.setCreateDateTime(timestamp);
            communicationEntity.setUpdateDateTime(timestamp);
            communicationAdapter.insert(communicationEntity);

            // 受信者情報の作成
            CommunicationReceiverAdapter communicationReceiverAdapter = new CommunicationReceiverAdapter(dao.getConnection());
            CommunicationReceiverEntity communicationReceiverEntity = new CommunicationReceiverEntity();
            communicationReceiverEntity.setCommunicationId(communicationId);
            communicationReceiverEntity.setSeq(0);
            communicationReceiverEntity.setReadFlg(false);
            communicationReceiverEntity.setPHR_ID(phrId);
            communicationReceiverEntity.setCreateDateTime(timestamp);
            communicationReceiverEntity.setUpdateDateTime(timestamp);
            communicationReceiverAdapter.insert(communicationReceiverEntity);

            // リマインダプッシュ完了リストの作成
            ReminderPushedListAdapter reminderPushedListAdapter = new ReminderPushedListAdapter(dao.getConnection());
            for (ReminderMessageEntity entity : cachedReminderMessage) {

                calendar.setTime(today);
                calendar.add(Calendar.DAY_OF_MONTH, entity.getRemoveBanDays());
                Date removeBanDate = calendar.getTime();

                ReminderPushedListEntity reminderPushedListEntity = new ReminderPushedListEntity();
                reminderPushedListEntity.setPHR_ID(phrId);
                reminderPushedListEntity.setReminderTypeId(entity.getReminderTypeId());
                reminderPushedListEntity.setReminderLevel(entity.getReminderLevel());
                reminderPushedListEntity.setPushDate(today);
                reminderPushedListEntity.setRemoveBanDate(removeBanDate);
                if (reminderPushedListAdapter.update(reminderPushedListEntity) == 0) {
                    reminderPushedListAdapter.insert(reminderPushedListEntity);
                }
                while (1 < reminderPushedListEntity.getReminderLevel()) {
                    reminderPushedListEntity.setReminderLevel(
                            reminderPushedListEntity.getReminderLevel() - 1);
                    if (reminderPushedListAdapter.updateRemoveBanDate(reminderPushedListEntity) == 0) {
                        reminderPushedListAdapter.insert(reminderPushedListEntity);
                    }
                }
            }

            // Cloud Messaging
            FCMNotificationService fcmNotificationService = new FCMNotificationService();
            int pushSuccessNumber = fcmNotificationService.sendFCMMessage(
                    communicationEntity,
                    Arrays.asList(new CommunicationReceiverEntity[]{communicationReceiverEntity}),
                    1);
            logger.info("Push success number:" + pushSuccessNumber);

            dao.commitTransaction();
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }
    
    /**
     * 指定された患者への同意通知を登録、送信します。
     *
     * @param phrId
     * @param reminderMessageList
     * @throws java.lang.Throwable
     */
    @Override
    public void receptionCompleted(String phrId, List<ReminderMessageEntity> reminderMessageList) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.trace("Start");

            Date now = new Date();
            Timestamp timestamp = new Timestamp(now.getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date today = calendar.getTime();

            dao = new DataAccessObject();
            dao.beginTransaction();

            // 保険者の取得
            InsurerAdapter insurerAdapter = new InsurerAdapter(dao.getConnection());
            InsurerEntity insurerEntity = insurerAdapter.findByPatientPhrId(phrId);

            // リマインダメッセージの取得
            StringBuilder message = new StringBuilder();

            List<ReminderMessageEntity> cachedReminderMessage = new ArrayList<>();
            ReminderMessageAdapter reminderMessageAdapter = new ReminderMessageAdapter(dao.getConnection());
            for (ReminderMessageEntity reminderMessage : reminderMessageList) {
                ReminderMessageEntity entity = reminderMessageAdapter.findByPrimaryKey(
                        reminderMessage.getReminderTypeId(), reminderMessage.getReminderLevel());
                if (entity == null) {
                    continue;
                }
                message.append("=============================\r\n");
                message.append(entity.getTitle()).append("\r\n");
                message.append("=============================\r\n");
                message.append(entity.getSendMessage()).append("\r\n\r\n");
                cachedReminderMessage.add(entity);
            }

            // コミュニケーション情報の作成
            CommunicationAdapter communicationAdapter = new CommunicationAdapter(dao.getConnection());
            String communicationId = CommunicationAdapter.numberingCommunicationId();
            CommunicationEntity communicationEntity = new CommunicationEntity();
            communicationEntity.setCommunicationId(communicationId);
            communicationEntity.setCommunicationTypeCd(2);
            communicationEntity.setSendInsurerNo(insurerEntity.getInsurerNo());
            communicationEntity.setSendAccountId("PushService");
            communicationEntity.setSenderName(insurerEntity.getInsurerName());
            communicationEntity.setSubject("受付完了のお知らせ");
            communicationEntity.setBodyText(message.toString());
            communicationEntity.setCreateDateTime(timestamp);
            communicationEntity.setUpdateDateTime(timestamp);
            communicationAdapter.insert(communicationEntity);

            // 受信者情報の作成
            CommunicationReceiverAdapter communicationReceiverAdapter = new CommunicationReceiverAdapter(dao.getConnection());
            CommunicationReceiverEntity communicationReceiverEntity = new CommunicationReceiverEntity();
            communicationReceiverEntity.setCommunicationId(communicationId);
            communicationReceiverEntity.setSeq(0);
            communicationReceiverEntity.setReadFlg(false);
            communicationReceiverEntity.setPHR_ID(phrId);
            communicationReceiverEntity.setCreateDateTime(timestamp);
            communicationReceiverEntity.setUpdateDateTime(timestamp);
            communicationReceiverAdapter.insert(communicationReceiverEntity);

            // リマインダプッシュ完了リストの作成
//            ReminderPushedListAdapter reminderPushedListAdapter = new ReminderPushedListAdapter(dao.getConnection());
//            for (ReminderMessageEntity entity : cachedReminderMessage) {
//
//                calendar.setTime(today);
//                calendar.add(Calendar.DAY_OF_MONTH, entity.getRemoveBanDays());
//                Date removeBanDate = calendar.getTime();
//
//                ReminderPushedListEntity reminderPushedListEntity = new ReminderPushedListEntity();
//                reminderPushedListEntity.setPHR_ID(phrId);
//                reminderPushedListEntity.setReminderTypeId(entity.getReminderTypeId());
//                reminderPushedListEntity.setReminderLevel(entity.getReminderLevel());
//                reminderPushedListEntity.setPushDate(today);
//                reminderPushedListEntity.setRemoveBanDate(removeBanDate);
//                if (reminderPushedListAdapter.update(reminderPushedListEntity) == 0) {
//                    reminderPushedListAdapter.insert(reminderPushedListEntity);
//                }
//                while (1 < reminderPushedListEntity.getReminderLevel()) {
//                    reminderPushedListEntity.setReminderLevel(
//                            reminderPushedListEntity.getReminderLevel() - 1);
//                    if (reminderPushedListAdapter.updateRemoveBanDate(reminderPushedListEntity) == 0) {
//                        reminderPushedListAdapter.insert(reminderPushedListEntity);
//                    }
//                }
//            }

            // Cloud Messaging
            FCMNotificationService fcmNotificationService = new FCMNotificationService();
            int pushSuccessNumber = fcmNotificationService.sendFCMMessage(
                    communicationEntity,
                    Arrays.asList(new CommunicationReceiverEntity[]{communicationReceiverEntity}),
                    1);
            logger.info("Push success number:" + pushSuccessNumber);

            dao.commitTransaction();
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }
}
