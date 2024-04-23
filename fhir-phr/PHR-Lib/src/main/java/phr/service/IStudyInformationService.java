/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;

import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.StudyItemInformationEntity;

/**
 *
 */
public interface IStudyInformationService {

    /**
     * 研究IDで研究項目情報レコードを取得
     * @param studyId
     * @param subjectId
     * @return list
     */
    public StudyItemInformationEntity findByStudyId(String studyId, String subjectId) throws Throwable;

    /**
     * 研究情報レコードをすべて取得
     * @param studyId
     * @return entity
     */
    public List<StudyInformationEntity> findAll() throws Throwable;
}
