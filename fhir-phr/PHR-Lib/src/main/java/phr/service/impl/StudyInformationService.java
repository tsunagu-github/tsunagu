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
import phr.datadomain.adapter.StudyInformationAdapter;
import phr.datadomain.adapter.StudyItemInformationAdapter;
import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.StudyItemInformationEntity;
import phr.service.IStudyInformationService;

/**
 *
 */
public class StudyInformationService implements IStudyInformationService {

	private static Logger logger = Logger.getLogger(StudyInformationService.class);

	/**
	 * 研究IDで研究項目情報レコードを取得
	 * @param studyId
	 * @param subjectId
	 * @return list
	 */
	public StudyItemInformationEntity findByStudyId(String studyId, String subjectId) throws Throwable {
		logger.debug("Start");
		StudyItemInformationEntity entity = new StudyItemInformationEntity();
		
		DataAccessObject dao = null;
		try {
			dao = new DataAccessObject();
			StudyItemInformationAdapter adapter = new StudyItemInformationAdapter(dao.getConnection());
			entity = adapter.findByStudyId(studyId, subjectId);
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
     * 研究情報レコードをすべて取得
     * @param studyId
     * @return entity
     */
    public List<StudyInformationEntity> findAll() throws Throwable {
        logger.debug("Start");
        List<StudyInformationEntity> entityList = new ArrayList<>();
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            StudyInformationAdapter adapter = new StudyInformationAdapter(dao.getConnection());
            entityList = adapter.findAll();
        } catch (Throwable ex) {
            logger.error(ex.toString(), ex);
        }
        
        logger.debug("End");
        return entityList;
    }

}
