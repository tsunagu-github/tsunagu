/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;

import phr.datadomain.entity.StudyDetailedInformationEntity;
import phr.datadomain.entity.StudyItemInformationEntity;

/**
 *
 */
public interface IStudyDetailedInformationService {

    /**
     * 研究IDで研究詳細情報レコードを取得
     * @param studyId
     * @param subjectId
     * @return entity
     */
    public List<StudyDetailedInformationEntity> findByStudyId(String studyId, String subjectId) throws Throwable;

    /**
     * 選択行のユーザ情報を取得する
     * 
     * @param facilityId
     * @param userId
     * @return
     */
    public List<StudyDetailedInformationEntity> studyInfo(String studyId, String subjectId) throws Throwable;

    /**
     * 情報の登録,修正を行う
     * 
     * @param entity
     * @param editFlg
     * @return
     */
    public boolean insertDetalied(StudyDetailedInformationEntity detailed, StudyDetailedInformationEntity newEntityDetailed, boolean editFlg, StudyItemInformationEntity newItemEntity) throws Throwable;
}
