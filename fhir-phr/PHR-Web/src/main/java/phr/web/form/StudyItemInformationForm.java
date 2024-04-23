package phr.web.form;

import java.util.List;

import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.StudyItemInformationEntity;

public class StudyItemInformationForm extends AbstractForm {

    /**
     * 研究ID
     */
    protected String studyId = null;
    /**
     * 項目ID
     */
    protected String subjectId = null;
    /**
     * 項目説明
     */
    protected String Subject = null;
    /**
     * 同意種別
     */
    protected String consentType = null;
    /**
     * URL
     */
    protected String url = null;
    /**
     * チェックリスト
     */
    protected String checkList = null;
    /**
     * チェックリスト
     */
    protected List<String> checkListList = null;

    /**
     * 研究情報リスト
     */
    protected List<StudyInformationEntity> studyInformationEntityList= null;
    
    /**
     * 研究情報リスト
     */
    protected List<StudyItemInformationEntity> studyItemInformationEntityList= null;
    /**
     * 研究名
     */
    protected String studyName = null;
    /**
     * 新規作成or修正フラグ（修正 = true）
     */
    protected boolean editFlg;
    
    public String getStudyId() {
        return studyId;
    }
    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }
    public String getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    public String getSubject() {
        return Subject;
    }
    public void setSubject(String subject) {
        Subject = subject;
    }
    public String getConsentType() {
        return consentType;
    }
    public void setConsentType(String consentType) {
        this.consentType = consentType;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public List<StudyInformationEntity> getStudyInformationEntityList() {
        return studyInformationEntityList;
    }
    public void setStudyInformationEntityList(List<StudyInformationEntity> studyInformationEntityList) {
        this.studyInformationEntityList = studyInformationEntityList;
    }
    public String getStudyName() {
        return studyName;
    }
    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }
    public String getCheckList() {
        return checkList;
    }
    public void setCheckList(String checkList) {
        this.checkList = checkList;
    }
    public List<String> getCheckListList() {
        return checkListList;
    }
    public void setCheckListList(List<String> checkListList) {
        this.checkListList = checkListList;
    }
    public List<StudyItemInformationEntity> getStudyItemInformationEntityList() {
        return studyItemInformationEntityList;
    }
    public void setStudyItemInformationEntityList(List<StudyItemInformationEntity> studyItemInformationEntityList) {
        this.studyItemInformationEntityList = studyItemInformationEntityList;
    }
    public boolean isEditFlg() {
        return editFlg;
    }
    public void setEditFlg(boolean editFlg) {
        this.editFlg = editFlg;
    }

}
