package phr.web.form;

import java.util.List;

import phr.datadomain.entity.PatientEntity;


public class ConsentNotificationForm extends AbstractForm {
    /**
     * 選択年
     */
    private int selectYear;
    /**
     * リマインダーID
     */
    private String reminderId;

    public int getSelectYear() {
        return selectYear;
    }

    public String getCaseManagementNo() {
        return caseManagementNo;
    }

    public void setCaseManagementNo(String caseManagementNo) {
        this.caseManagementNo = caseManagementNo;
    }

    public boolean isWithinOneWeekFlg() {
        return withinOneWeekFlg;
    }

    public void setWithinOneWeekFlg(boolean withinOneWeekFlg) {
        this.withinOneWeekFlg = withinOneWeekFlg;
    }

    public boolean isWithinOneMonthFlg() {
        return withinOneMonthFlg;
    }

    public void setWithinOneMonthFlg(boolean withinOneMonthFlg) {
        this.withinOneMonthFlg = withinOneMonthFlg;
    }

    public boolean isOneMonthOrMoreFlg() {
        return oneMonthOrMoreFlg;
    }

    public void setOneMonthOrMoreFlg(boolean oneMonthOrMoreFlg) {
        this.oneMonthOrMoreFlg = oneMonthOrMoreFlg;
    }

//    public List<UnregisteredAnswerDto> getUnregisteredAnswerDtoList() {
//        return unregisteredAnswerDtoList;
//    }
//
//    public void setUnregisteredAnswerDtoList(List<UnregisteredAnswerDto> unregisteredAnswerDtoList) {
//        this.unregisteredAnswerDtoList = unregisteredAnswerDtoList;
//    }

    public Boolean getSuccessed() {
        return successed;
    }

    public void setSuccessed(Boolean successed) {
        this.successed = successed;
    }

    public int getReminderTotal() {
        return reminderTotal;
    }

    public void setReminderTotal(int reminderTotal) {
        this.reminderTotal = reminderTotal;
    }

    public int getReminderSuccessed() {
        return reminderSuccessed;
    }

    public void setReminderSuccessed(int reminderSuccessed) {
        this.reminderSuccessed = reminderSuccessed;
    }

    public int getReminderFailed() {
        return reminderFailed;
    }

    public void setReminderFailed(int reminderFailed) {
        this.reminderFailed = reminderFailed;
    }

    public void setSelectYear(int selectYear) {
        this.selectYear = selectYear;
    }

    public String getReminderId() {
        return reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }
    
    // 症例管理番号
    private String caseManagementNo;

    // １週間以内フラグ
    private boolean withinOneWeekFlg;

    // １ヶ月以内フラグ
    private boolean withinOneMonthFlg;

    // １ヶ月以上フラグ
    private boolean oneMonthOrMoreFlg;

    // 未登録一覧検索結果リスト（症例未回答情報リスト）
//    private List<UnregisteredAnswerDto> unregisteredAnswerDtoList;

    // リマインダ送信成功
    // true:全件成功   false:1件以上送信失敗
    private Boolean successed;

    // リマインダ送信対象件数
    private int reminderTotal;

    // リマインダ成功件数
    private int reminderSuccessed;

    // リマインダ失敗件数
    private int reminderFailed;
    
    /*
     * パラメータ-患者ID
     */
    private String patientId;
    public String getPatientId() 
    {
        return patientId;
    }

    public void setPatientId(String patientId) 
    {
        this.patientId = patientId;
    }
    
    /*
     * パラメータ-患者姓
     */
    private String familyName;
    public String getFamilyName() 
    {
        return familyName;
    }

    public void setFamilyName(String familyName) 
    {
        this.familyName = familyName;
    }
    /*
     * パラメータ-患者名
     */
    private String givenName;
    public String getGivenName() 
    {
        return givenName;
    }

    public void setGivenName(String givenName) 
    {
        this.givenName = givenName;
    }
    /*
     * パラメータ-患者カナ姓
     */
    private String familyKana;
    public String getFamilyKana() 
    {
        return familyKana;
    }

    public void setFamilyKana(String familyKana) 
    {
        this.familyKana = familyKana;
    }
    /*
     * パラメータ-患者カナ名
     */
    private String givenKana;
    public String getGivenKana() 
    {
        return givenKana;
    }

    public void setGivenKana(String givenKana) 
    {
        this.givenKana = givenKana;
    }
    
    /*
     * 患者情報
     */
    private List<PatientEntity> patientInfo;
    public List<PatientEntity> getPatientInfo() 
    {
        return patientInfo;
    }

    public void setPatientInfo(List<PatientEntity> patientInfo) 
    {
        this.patientInfo = patientInfo;
    }
    
    /*
     * 患者情報有無
     */
    private boolean patientFlg;
    public boolean getPatientFlg() 
    {
        return patientFlg;
    }

    public void setPatientFlg(boolean patientFlg) 
    {
        this.patientFlg = patientFlg;
    }  
}
