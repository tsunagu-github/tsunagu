package phr.service;

import java.util.List;

import phr.datadomain.entity.StudyItemInformationEntity;

public interface IStudyItemInformationService {
    
    /**
     * 研究項目情報一覧を取得する
     * 
     * @param facilityId
     * @param userId
     * @param name
     * @param invalid
     * @return
     */
    public List<StudyItemInformationEntity> getAllStudyItemInformationList() throws Throwable;
    
    /**
     * 選択行のユーザ情報を取得する
     * 
     * @param facilityId
     * @param userId
     * @return
     */
    public StudyItemInformationEntity studyInfo(String studyId, String subjectId) throws Throwable;
    
    /**
     * 新規登録,修正を行う
     * 
     * @param entity
     * @param editFlg
     * @return
     */
    public boolean registAccount(StudyItemInformationEntity itemEntity, StudyItemInformationEntity newItemEntity, boolean editFlg) throws Throwable;


}
