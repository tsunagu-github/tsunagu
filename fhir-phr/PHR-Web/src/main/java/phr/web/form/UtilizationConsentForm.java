package phr.web.form;

import java.sql.Timestamp;
import java.util.List;

import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.UtilizationConsentEntity;

public class UtilizationConsentForm extends AbstractForm {

    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 活用同意一覧ID */
    protected String utilizationConsentId = null;
    /* PHR_ID */
    protected String pHR_ID = null;
    /* 研究ID */
    protected String studyId = null;
    /* 研究名 */
    protected String studyName = null;
    /* 同意種別 */
    protected String consentType = null;
    /* 回答ステータス */
    protected String responseStatus = null;
    /* 新着フラグ */
    protected boolean newArrivalFlg = false;
    /* 無効フラグ */
    protected String invalid = null;
    /* 通知日 */
    protected Timestamp notificationDate = null;
    /* 回答更新日 */
    protected Timestamp responseDate = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /**
     * 無効表示フラグ
     */
    private boolean responseStatusFlg;
    /**
     * チェックフラグ
     */
    private String checkFlg;
    /**
     * 回答ステータスリスト
     */
    protected List<String> responseStatusList = null;
    /**
     * 
     */
    protected List<StudyInformationEntity> studyInformationEntityList = null;
    
    public String getUtilizationConsentId() {
        return utilizationConsentId;
    }
    public void setUtilizationConsentId(String utilizationConsentId) {
        this.utilizationConsentId = utilizationConsentId;
    }
    public String getpHR_ID() {
        return pHR_ID;
    }
    public void setpHR_ID(String pHR_ID) {
        this.pHR_ID = pHR_ID;
    }
    public String getStudyId() {
        return studyId;
    }
    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }
    
    public String getStudyName() {
        return studyName;
    }
    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }
    public String getConsentType() {
        return consentType;
    }
    public void setConsentType(String consentType) {
        this.consentType = consentType;
    }
    public String getResponseStatus() {
        return responseStatus;
    }
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
    public boolean isNewArrivalFlg() {
        return newArrivalFlg;
    }
    public void setNewArrivalFlg(boolean newArrivalFlg) {
        this.newArrivalFlg = newArrivalFlg;
    }
    public String getInvalid() {
        return invalid;
    }
    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }
    public Timestamp getNotificationDate() {
        return notificationDate;
    }
    public void setNotificationDate(Timestamp notificationDate) {
        this.notificationDate = notificationDate;
    }
    public Timestamp getResponseDate() {
        return responseDate;
    }
    public void setResponseDate(Timestamp responseDate) {
        this.responseDate = responseDate;
    }
    public Timestamp getCreateDateTime() {
        return createDateTime;
    }
    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }
    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }
    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
    public boolean isResponseStatusFlg() {
        return responseStatusFlg;
    }
    public void setResponseStatusFlg(boolean responseStatusFlg) {
        this.responseStatusFlg = responseStatusFlg;
    }

    public String getCheckFlg() {
        return checkFlg;
    }
    public void setCheckFlg(String checkFlg) {
        this.checkFlg = checkFlg;
    }

    public List<String> getResponseStatusList() {
        return responseStatusList;
    }
    public void setResponseStatusList(List<String> responseStatusList) {
        this.responseStatusList = responseStatusList;
    }

    public List<StudyInformationEntity> getStudyInformationEntityList() {
        return studyInformationEntityList;
    }
    public void setStudyInformationEntityList(List<StudyInformationEntity> studyInformationEntityList) {
        this.studyInformationEntityList = studyInformationEntityList;
    }

    /*
     * 患者情報
     */
    private List<UtilizationConsentEntity> patientInfo;
    public List<UtilizationConsentEntity> getPatientInfo() 
    {
        return patientInfo;
    }

    public void setPatientInfo(List<UtilizationConsentEntity> patientInfo) 
    {
        this.patientInfo = patientInfo;
    }
    
}
