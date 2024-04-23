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
import phr.datadomain.adapter.StudyDetailedInformationAdapter;
import phr.datadomain.adapter.StudyItemInformationAdapter;
import phr.datadomain.entity.StudyDetailedInformationEntity;
import phr.datadomain.entity.StudyItemInformationEntity;
import phr.service.IStudyDetailedInformationService;

/**
 *
 */
public class StudyDetailedInformationService implements IStudyDetailedInformationService {

	private static Logger logger = Logger.getLogger(StudyDetailedInformationService.class);

	/**
	 * 研究IDで研究詳細情報レコードを取得
	 * @param studyId
	 * @param subjectId
	 * @return entity
	 */
	public List<StudyDetailedInformationEntity> findByStudyId(String studyId, String subjectId) throws Throwable {
		logger.debug("Start");
		List<StudyDetailedInformationEntity> list = new ArrayList<>();
		
		DataAccessObject dao = null;
		try {
			dao = new DataAccessObject();
			StudyDetailedInformationAdapter adapter = new StudyDetailedInformationAdapter(dao.getConnection());
			list = adapter.findByStudyId(studyId, subjectId);
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
     * 選択行のユーザ情報を取得する
     * 
     * @param facilityId
     * @param userId
     * @return
     */
    public List<StudyDetailedInformationEntity> studyInfo(String studyId, String subjectId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = null;
        List<StudyDetailedInformationEntity> list= new ArrayList<>();
        try {
            dao = new DataAccessObject();
            StudyDetailedInformationAdapter studyDetailedInformationAdapter = new StudyDetailedInformationAdapter(dao.getConnection());
            list = studyDetailedInformationAdapter.findByPrimaryKey(studyId, subjectId);
            if (list.isEmpty()) {
                list = studyDetailedInformationAdapter.findByPrimaryKey1(studyId, subjectId);
            }
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        }
        logger.debug("End");
        return list;
    }
	
    /**
     * 情報の登録,修正を行う
     * 
     * @param entity
     * @param editFlg
     * @return
     */
    public boolean insertDetalied(StudyDetailedInformationEntity detailed, StudyDetailedInformationEntity newEntityDetailed, boolean editFlg, StudyItemInformationEntity newItemEntity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            StudyDetailedInformationAdapter studyDetailedInformationAdapter = new StudyDetailedInformationAdapter(dao.getConnection());
            int result = 1;
            if (editFlg == true) {
                //修正
//                studyDetailedInformationAdapter.deleteDetalied(detailed);
                //statusを2にする
//                studyDetailedInformationAdapter.updateStatus(detailed);
                if (newItemEntity.getConsentType().equals("1")) {
                    studyDetailedInformationAdapter.insertDetalied(newEntityDetailed);
                }
            } else {
                //新規作成
                result = studyDetailedInformationAdapter.insertDetalied(newEntityDetailed);
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
    
    /**
     * status=0があれば0→1、1→2へ更新する
     * status=0がなければ更新しない
     * @param studyId
     * @param subjectId
     * @return entity
     */
    public void updateStatus(String studyId, String subjectId) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            StudyDetailedInformationAdapter adapter = new StudyDetailedInformationAdapter(dao.getConnection());
            List<StudyDetailedInformationEntity> detailedlist = new ArrayList<>();
            detailedlist = adapter.findByPrimaryKey(studyId, subjectId);
            if (! detailedlist.isEmpty()) {
                adapter.updateStatusOld(studyId, subjectId);
            }
            adapter.updateStatus(studyId, subjectId);
            
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        }
        
        logger.debug("End");
    }

}
