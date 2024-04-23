package phr.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.StudyItemInformationAdapter;
import phr.datadomain.entity.StudyItemInformationEntity;
import phr.service.IStudyItemInformationService;

@Service("StudyItemInformationService")
public class StudyItemInformationService implements IStudyItemInformationService{
    /**
     * ロギングコンポーネント
     */
    private static Logger logger = LoggerFactory.getLogger(StudyItemInformationService.class);
    

    /**
     * 研究項目情報一覧を取得する
     * 
     * @param facilityId
     * @param userId
     * @param name
     * @param invalid
     * @return
     */
    public List<StudyItemInformationEntity> getAllStudyItemInformationList() throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            StudyItemInformationAdapter studyItemInformationAdapter = new StudyItemInformationAdapter(dao.getConnection());
            return studyItemInformationAdapter.getAllAccountList();
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            logger.debug("end");
        }
    }
    
    /**
     * 選択行のユーザ情報を取得する
     * 
     * @param facilityId
     * @param userId
     * @return
     */
    public StudyItemInformationEntity studyInfo(String studyId, String subjectId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            StudyItemInformationAdapter studyItemInformationAdapter = new StudyItemInformationAdapter(dao.getConnection());
            return studyItemInformationAdapter.findByPrimaryKey(studyId, subjectId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            logger.debug("end");
        }
    }
    
    /**
     * 新規登録,修正を行う
     * 
     * @param entity
     * @param editFlg
     * @return
     */
    public boolean registAccount(StudyItemInformationEntity itemEntity, StudyItemInformationEntity newItemEntity, boolean editFlg) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            StudyItemInformationAdapter studyItemInformationAdapter = new StudyItemInformationAdapter(dao.getConnection());
            int result = 0;
            if (editFlg == true) {
                //修正
//                result = studyItemInformationAdapter.deleteItem(itemEntity);
//                result = studyItemInformationAdapter.insert(newItemEntity);
                result = studyItemInformationAdapter.updateItem(newItemEntity);
            } else {
                //新規作成
                result = studyItemInformationAdapter.insert(newItemEntity);
            }
            if (result == 0) {
                logger.trace("登録処理に失敗しました");
                return false;
            }
            return true;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            logger.debug("end");
        }
    }

}
