/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者情報管理サービス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.InsurerNumberingAdapter;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.Seq_ObservationImportHistoryIdAdapter;
import phr.datadomain.adapter.Seq_UtilizationConsentIdAdapter;
import phr.datadomain.adapter.StudyInformationAdapter;
import phr.datadomain.adapter.UtilizationConsentAdapter;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.Seq_ObservationImportHistoryIdEntity;
import phr.datadomain.entity.Seq_UtilizationConsentIdEntity;
import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.UtilizationConsentEntity;
import phr.service.IPatientManagementService;

/**
 *
 * @author daisuke
 */
public class PatientManagementService implements IPatientManagementService {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientManagementService.class);

    /**
     * 利用者情報を取得する
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    @Override
    public PatientEntity getPatient(String phrId) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            PatientEntity entity = adapter.findByPrimaryKey(phrId);
            return entity;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }

    }
    /**
     * PHRIDを採番する
     * @param entity
     * @return 
     * @throws Throwable
     */
    @Override
    public String createPatient(PatientEntity entity) throws Throwable {
          logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            
            // PHR IDの採番
            InsurerNumberingAdapter adapter = new InsurerNumberingAdapter(dao.getConnection());
            String phrId = adapter.numberingPhrId(entity.getInsurerNo());
            if (phrId == null) {
                return null;
            }
            
            // KeyIDの作成
            String keyId = UUID.randomUUID().toString();
            keyId = keyId.replaceAll("-", "");
            
            phrId = entity.getInsurerNo() + "-" + phrId;
            // 患者情報の作成
            entity.setPHR_ID(phrId);
            entity.setKyeId(keyId);
            entity.setDynamicConsentFlg(true);
            PatientAdapter patientAdapter = new PatientAdapter(dao.getConnection());
            patientAdapter.insert(entity);
            
            // 保険者患者関連情報の作成
            InsurerPatientEntity insurerPatientEntity = new InsurerPatientEntity();
            insurerPatientEntity.setInsurerNo(entity.getInsurerNo());
            insurerPatientEntity.setPHR_ID(entity.getPHR_ID());
            InsurerPatientAdapter insurerPatientAdapter = new InsurerPatientAdapter(dao.getConnection());
            insurerPatientAdapter.insert(insurerPatientEntity);
            
            dao.commitTransaction();
            
            return phrId;
        } catch (Throwable ex) {
            if (dao != null)
                dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("end");
        }      
    }
    /**
     * 利用者情報を更新する
     *
     * @param entity
     * @throws Throwable
     */
    @Override
    public void updatePatient(PatientEntity entity) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            adapter.update(entity);
            dao.commitTransaction();
        } catch (Throwable ex) {
            if (dao != null)
                dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("end");
        }

    }

    @Override
    public List<String> getInsurerPatientList(String insurerNo) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
            List<InsurerPatientEntity> ipList = adapter.findByInsurerNo(insurerNo);
            if(ipList == null || ipList.isEmpty()){
                return null;
            }else{
                List<String> idList = new ArrayList<String>();
                for(InsurerPatientEntity ip:ipList){
                    idList.add(ip.getPHR_ID());
                }
                return idList;
            }
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }

    @Override
    public List<PatientEntity> getPatientListByIdList(List<String> phrId) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            List<PatientEntity> entitylist = adapter.findPatientListByIdList(phrId);
            return entitylist;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }

    @Override
    public List<PatientEntity> getPatientList(String insurerNo, String phrId, String familyName, String givenName, String familyKana, String givenKana) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            List<PatientEntity> entitylist = adapter.findPatientListByIdOrNames(insurerNo, phrId, familyName, givenName, familyKana, givenKana);
            return entitylist;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }
    
    @Override
    public List<PatientEntity> findPatientListByIdList(List<String> phrIdList) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            List<PatientEntity> entitylist = adapter.findPatientListByIdList(phrIdList);
            return entitylist;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }

    /**
     * 有効な研究情報レコードを取得する
     * @return list
     * @throws Throwable
     */
    public List<StudyInformationEntity> getStudyInformation() throws Throwable {
        logger.debug("Start");
        List<StudyInformationEntity> list = new ArrayList<>();
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            StudyInformationAdapter adapter = new StudyInformationAdapter(dao.getConnection());
            list = adapter.getStudyInformation();
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        }
        
        logger.debug("End");
        return list;
    }

//    /**
//     * 登録するための活用同意一覧レコードのリストを作成する
//     * @param phr_id
//     * @param studyInformationEntityList
//     * @return utilizationConsentEntityList
//     * @throws Throwable
//     */
//    public List<UtilizationConsentEntity> createList(String phr_id, List<StudyInformationEntity> studyInformationEntityList) throws Throwable {
//        logger.debug("Start");
//        List<UtilizationConsentEntity> utilizationConsentEntityList = new ArrayList<>();
//        
//        for (StudyInformationEntity e : studyInformationEntityList) {
//            UtilizationConsentEntity entity = new UtilizationConsentEntity();
//            // 活用同意一覧IDの取得
//            String utilizationConsentId = Seq_UtilizationConsentIdAdapter.numberingUtilizationConsentId();
//            // 現在日時
//            Timestamp date = new Timestamp(System.currentTimeMillis());
//            entity.setUtilizationConsentId(utilizationConsentId);
//            entity.setPHR_ID(phr_id);
//            entity.setStudyId(e.getStudyId());
//            entity.setConsentType(e.getConsentType());
//            if (e.getConsentType().equals("1")) {
//                entity.setResponseStatus("3");
//            } else if (e.getConsentType().equals("2")) {
//                entity.setResponseStatus("1");
//            }
//            entity.setNewArrivalFlg(true);
//            entity.setInvalid("0");
//            entity.setNotificationDate(date);
//            
//            // utilizationConsentId=0の時のみSeq_UtilizationConsentIdテーブルに登録
//            if (utilizationConsentId.equals("0000000000")) {
//                Seq_UtilizationConsentIdEntity en = new Seq_UtilizationConsentIdEntity();
//                en.setUtilizationConsentId(Long.valueOf(utilizationConsentId));
//                Seq_UtilizationConsentIdAdapter.insertUtilizationConsentId(en);
//            }
//            
//            utilizationConsentEntityList.add(entity);
//        }
//        
//        logger.debug("End");
//        return utilizationConsentEntityList;
//    }

    /**
     * 活用同意一覧レコードのリストをDBに登録する
     * @param list
     * @return rowCount
     * @throws Throwable
     */
    public int insertList(List<UtilizationConsentEntity> list) throws Throwable {
        logger.debug("Start");
        int rowCount = 0;
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            UtilizationConsentAdapter adapter = new UtilizationConsentAdapter(dao.getConnection());
            for (UtilizationConsentEntity entity : list) {
                rowCount = adapter.insert(entity);
            }
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        }
        
        logger.debug("End");
        return rowCount;
    }
}
