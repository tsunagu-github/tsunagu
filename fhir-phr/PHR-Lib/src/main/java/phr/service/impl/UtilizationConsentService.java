/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.AnswerStatusAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.UtilizationConsentAdapter;
import phr.datadomain.entity.AnswerStatusEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.UtilizationConsentEntity;
import phr.service.IUtilizationConsentService;

/**
 *
 */
public class UtilizationConsentService implements IUtilizationConsentService {

    private static Logger logger = Logger.getLogger(UtilizationConsentService.class);

    /**
     * PHR_IDで活用同意一覧テーブルからレコードを取得（昇順）
     * @param phr_id
     * @param type
     * @return list
     */
    public List<UtilizationConsentEntity> findByPhrId(String phr_id, String type) throws Throwable {
        logger.debug("Start");
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            UtilizationConsentAdapter adapter = new UtilizationConsentAdapter(dao.getConnection());
            if (type.equals("StudyId")) {
                list = adapter.findByPhrIdSortStudyId(phr_id);
            } else if (type.equals("NotificationDate")) {
                list = adapter.findByPhrIdSortNotification(phr_id);
            } else if (type.equals("UpdateDate")) {
                list = adapter.findByPhrIdSortUpdate(phr_id);
            }
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return list;
    }
    
    /**
     * PHR_IDで活用同意一覧テーブルからレコードを取得（降順）
     * @param phr_id
     * @param type
     * @return list
     */
    public List<UtilizationConsentEntity> findByPhrIdDesc(String phr_id, String type) throws Throwable {
        logger.debug("Start");
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            UtilizationConsentAdapter adapter = new UtilizationConsentAdapter(dao.getConnection());
            if (type.equals("StudyId")) {
                list = adapter.findByPhrIdSortStudyIdDesc(phr_id);
            } else if (type.equals("NotificationDate")) {
                list = adapter.findByPhrIdSortNotificationDesc(phr_id);
            } else if (type.equals("UpdateDate")) {
                list = adapter.findByPhrIdSortUpdateDesc(phr_id);
            }
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return list;
    }
    
    /**
     * 活用同意一覧テーブルを更新する
     * @param phr_id
     * @param studyId
     * @param responseStatus
     * @param subjectId
     * @return rowCount
     */
    public int updateRecord(String phr_id, String studyId, String responseStatus, String subjectId) throws Throwable {
        logger.debug("Start");
        int rowCount = 0;
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            UtilizationConsentAdapter adapter = new UtilizationConsentAdapter(dao.getConnection());
            rowCount = adapter.updateRecord(phr_id, studyId, responseStatus, subjectId);
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return rowCount;
    }
    
    /**
     * PHR_IDから患者情報を取得する
     * @param phr_id
     * @return entity
     */
    public PatientEntity getPatient(String phr_id) throws Throwable {
        logger.debug("Start");
        PatientEntity entity = new PatientEntity();
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            entity = adapter.findByPrimaryKey(phr_id);
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return entity;
    }

    /**
     * PHR_IDから回答情報レコードを取得する
     * @param phr_id
     * @return list
     */
    public List<AnswerStatusEntity> getAnswerStatusRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        List<AnswerStatusEntity> list = new ArrayList<>();
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            AnswerStatusAdapter adapter = new AnswerStatusAdapter(dao.getConnection());
            list = adapter.findByPhrId(phr_id);
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDとStudyIdから回答情報レコードを取得する
     * @param phr_id
     * @param studyId
     * @param subjectId
     * @return list
     */
    public List<AnswerStatusEntity> findByPhrIdAndStudyId(String phr_id, String studyId, String subjectId) throws Throwable {
        logger.debug("Start");
        List<AnswerStatusEntity> list = new ArrayList<>();
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            AnswerStatusAdapter adapter = new AnswerStatusAdapter(dao.getConnection());
            list = adapter.findByPhrIdAndStudyId(phr_id, studyId, subjectId);
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return list;
    }

    /**
     * 回答情報テーブルを更新する
     * @param entity
     * @return rowCount
     */
    public int updateRecord(AnswerStatusEntity entity) throws Throwable {
        logger.debug("Start");
        int rowCount = 0;
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            AnswerStatusAdapter adapter = new AnswerStatusAdapter(dao.getConnection());
            rowCount = adapter.updateRecord(entity);
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return rowCount;
    }

    /**
     * 回答情報テーブルに登録する
     * @param entity
     * @return rowCount
     */
    public int insertRecord(AnswerStatusEntity entity) throws Throwable {
        logger.debug("Start");
        int rowCount = 0;
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            AnswerStatusAdapter adapter = new AnswerStatusAdapter(dao.getConnection());
            rowCount = adapter.insertRecord(entity);
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return rowCount;
    }

    /**
     * 研究名と状態にて患者情報を取得
     * @param phr_id
     * @param type
     * @return list
     */
    public List<UtilizationConsentEntity> findByStudyNameResponseStatus(String studyName, List<String> responseStatusList) throws Throwable {
        logger.debug("Start");
        List<UtilizationConsentEntity> list = new ArrayList<>();

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            UtilizationConsentAdapter adapter = new UtilizationConsentAdapter(dao.getConnection());
                list = adapter.findByStudyNameResponseStatus(studyName, responseStatusList);
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        }

        logger.debug("End");
        return list;
    }

    /**
     * 活用同意一覧テーブルの通知日を更新する
     * @param phr_id
     * @param studyId
     * @param responseStatus
     * @return rowCount
     */
    public int updateRecord(String phr_id, String studyId, String SubjectId) throws Throwable {
        logger.debug("Start");
        int rowCount = 0;
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            UtilizationConsentAdapter adapter = new UtilizationConsentAdapter(dao.getConnection());
            rowCount = adapter.updateRecord(phr_id, studyId, SubjectId);
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        logger.debug("End");
        return rowCount;
    }
}
