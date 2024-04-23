/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

/**
 * 対象患者指定の検査項目を取得するDto
 * @author kis-note
 */
public class ObservationDto {
    /*
    * 項目ID
    */
    private String id;
    
    /*
    * 項目名称
    */
    private String name;
    
    /*
    * チェックボックス用ID
    */
    private String boxid;

    /*
    * 更新日用id
    */
    private String dateid;

    /*
    * 単位
    */
    private String unit;
    
    /*
    * 分類
    */
    private String kind;

    /*
    * 対象疾患Cd
    */
    private int diseaseTypeCd;

    /*
     * 検査日
     */
    private String examinationDate;

    /*
     * 検査結果値
     */
    private String value = null;

    /*
     * アラートレベル
     */
    private int alertLevel;

    /*
     * リマインダ(true:通知対象、false:非対象)
     */
    private boolean reminderFlg;

    /*
    * 入力種別CD
    */
    private int dataInputTypeCd;

    /*
    * 健診表示No
    */
    private int dispPos;

    /*
     * タイトル
     */
    private String title;

    /*
    * ReadFlg
    */
    private boolean readFlg;

    /*
    * Seq
    */
    private int seq;

    /*
    * 項目EVENTID
    */
    private String eventId;

    // 左ボタンフラグ
    private String leftFlg;

    // 右ボタンフラグ
    private String rightFlg;

    /**
     * 基準値
     */
    private String referenceValue = null;

    /**
     * アラート色
     */
    private String alertColor;
    
    // ヘッダー色
    private String headerColor;
    
    // 病院名
    private String medicalOrganizationName;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * @param kind the kind to set
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return the boxid
     */
    public String getBoxid() {
        return boxid;
    }

    /**
     * @param boxid the boxid to set
     */
    public void setBoxid(String boxid) {
        this.boxid = boxid;
    }

    /**
     * @return the dateid
     */
    public String getDateid() {
        return dateid;
    }

    /**
     * @param dateid the dateid to set
     */
    public void setDateid(String dateid) {
        this.dateid = dateid;
    }

    /**
     * @return the diseaseTypeCd
     */
    public int getDiseaseTypeCd() {
        return diseaseTypeCd;
    }

    /**
     * @param diseaseTypeCd the diseaseTypeCd to set
     */
    public void setDiseaseTypeCd(int diseaseTypeCd) {
        this.diseaseTypeCd = diseaseTypeCd;
    }

    /**
     * @return 
     */
    public String getExaminationDate() {
        return examinationDate;
    }
    /**
     * @param examinationDate
     */
    public void setExaminationDate(String examinationDate) {
        this.examinationDate = examinationDate;
    }

    /**
     * @return 
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return 
     */
    public int getAlertLevel() {
        return alertLevel;
    }
    /**
     * @param alertLevel
     */
    public void setAlertLevel(int alertLevel) {
        this.alertLevel = alertLevel;
    }

    /**
     * @return 
     */
    public boolean getReminderFlg() {
        return reminderFlg;
    }
    /**
     * @param reminderFlg
     */
    public void setReminderFlg(boolean reminderFlg) {
        this.reminderFlg = reminderFlg;
    }

    /**
     * @return 
     */
    public int getDataInputTypeCd() {
        return dataInputTypeCd;
    }
    /**
     * @param dataInputTypeCd
     */
    public void setDataInputTypeCd(int dataInputTypeCd) {
        this.dataInputTypeCd = dataInputTypeCd;
    }

    /**
     * @return 
     */
    public int getDispPos() {
        return dispPos;
    }
    /**
     * @param dispPos
     */
    public void setDispPos(int dispPos) {
        this.dispPos = dispPos;
    }

    /**
     * @return 
     */
    public String getTitle(){
        return title;
    }
    /**
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * @return 
     */
    public boolean getReadFlg() {
        return readFlg;
    }
    /**
     * @param readFlg
     */
    public void setReadFlg(boolean readFlg) {
        this.readFlg = readFlg;
    }

    /**
     * @return 
     */
    public int getSeq() {
        return seq;
    }
    /**
     * @param seq
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * @return the eventId
     */
    public String getEventId() {
        return eventId;
    }
    /**
     * @param eventId the eventId to set
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    // 左ボタンフラグ
    public String getLeftFlg(){
        return this.leftFlg;
    }
    public void setLeftFlg(String leftFlg){
        this.leftFlg=leftFlg;
    }

    // 右ボタンフラグ
    public String getRightFlg(){
        return this.rightFlg;
    }
    public void setRightFlg(String rightFlg){
        this.rightFlg=rightFlg;
    }

    /**
     * 基準値
     * @return
     */
    public String getReferenceValue() {
        return referenceValue;
    }

    /**
     * 基準値
     * @param refVal
     */
    public void setReferenceValue(String refVal) {
        this.referenceValue = refVal;
    }

	/**
	 * @return alertColor
	 */
	public String getAlertColor() {
		return alertColor;
	}

	/**
	 * @param alertColor セットする alertColor
	 */
	public void setAlertColor(String alertColor) {
		this.alertColor = alertColor;
	}

    public String getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    public String getMedicalOrganizationName() {
        return medicalOrganizationName;
    }

    public void setMedicalOrganizationName(String medicalOrganizationName) {
        this.medicalOrganizationName = medicalOrganizationName;
    }
    
}
