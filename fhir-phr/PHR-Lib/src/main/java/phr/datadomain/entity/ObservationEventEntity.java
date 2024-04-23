/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 検査結果情報のデータオブジェクトです。
 */
public class ObservationEventEntity extends ObservationEventEntityBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationEventEntity.class);
    private static Logger logger = Logger.getLogger(ObservationEventEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationEventEntity()
    {
    }

    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    
//<editor-fold defaultstate="collapsed" desc="アラート一覧検索時の他テーブルの追加必要情報">
    /* 氏名_姓 */
    protected String familyName = null;
    /* 氏名_名 */
    protected String givenName = null;
    /* カナ氏名_姓 */
    protected String familyKana = null;
    /* カナ氏名_名 */
    protected String givenKana = null;
    /* アラートフラグ */
    protected String alertFlg = null;
    /* 検査名称 */
    protected String observationDefinitionName = null;
//    /* 疾病管理対象フラグ */
//    private boolean diseaseManagementTargetFlg;
    /* 基準値下限 */
    protected Double minReferenceValue = null;
    /* 基準値上限 */
    protected Double maxReferenceValue = null;
    /* 基準列挙名称*/
    protected String refEnumName = null;;
    /* 基準値下限2 */
    protected Double minReferenceValue2 = null;
    /* 基準値上限2 */
    protected Double maxReferenceValue2 = null;

    /**
     * JLAC10
     */
    private String jlac10;

    /**
     * 氏名_姓を取得する
     *
     * @return
     */
    public String getFamilyName() {
        return this.familyName;
    }
    /**
     * 氏名_姓を設定する
     *
     * @param value
     */
    public void setFamilyName(String value) {
        this.familyName = value;
    }

    /**
     * 氏名_名を取得する
     *
     * @return
     */
    public String getGivenName() {
        return this.givenName;
    }
    /**
     * 氏名_名を設定する
     *
     * @param value
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * カナ氏名_姓を取得する
     *
     * @return
     */
    public String getFamilyKana() {
        return this.familyKana;
    }
    /**
     * カナ氏名_姓を設定する
     *
     * @param value
     */
    public void setFamilyKana(String value) {
        this.familyKana = value;
    }

    /**
     * カナ氏名_名を取得する
     *
     * @return
     */
    public String getGivenKana() {
        return this.givenKana;
    }
    /**
     * カナ氏名_名を設定する
     *
     * @param value
     */
    public void setGivenKana(String value) {
        this.givenKana = value;
    }

    /**
     * alertFlgを取得する
     *
     * @return
     */
    public String getAlertFlg() {
        return this.alertFlg;
    }
    /**
     * alertFlgを設定する
     *
     * @param value
     */
    public void setAlertFlg(String value) {
        this.alertFlg = value;
    }

    /**
     * observationDefinitionNameを取得する
     *
     * @return
     */
    public String getObservationDefinitionName() {
        return this.observationDefinitionName;
    }
    /**
     * observationDefinitionNameを設定する
     *
     * @param value
     */
    public void setObservationDefinitionName(String value) {
        this.observationDefinitionName = value;
    }
    
//	/**
//	 * diseaseManagementTargetFlgを取得する
//	 * 
//	 * @return
//	 */
//	public boolean isDiseaseManagementTargetFlg() {
//		return diseaseManagementTargetFlg;
//	}
//	/**
//	 * diseaseManagementTargetFlg セットする
//	 * 
//	 * @param diseaseManagementTargetFlg
//	 */
//	public void setDiseaseManagementTargetFlg(boolean diseaseManagementTargetFlg) {
//		this.diseaseManagementTargetFlg = diseaseManagementTargetFlg;
//	}
    
    /**
     * 基準値下限
     * @return
     */
    public Double getMinReferenceValue() {
        return minReferenceValue;
    }
    
    /**
     * 基準値下限
     * @return
     */
    public Double getMaxReferenceValue() {
        return maxReferenceValue;
    }
    
    /**
     * 基準値上限
     * @param minReferenceValue
     */
    public void setMinReferenceValue(Double minReferenceValue) {
        this.minReferenceValue = minReferenceValue;
    }
    
    /**
     * 基準値上限
     * @param maxReferenceValue
     */
    public void setMaxReferenceValue(Double maxReferenceValue) {
        this.maxReferenceValue = maxReferenceValue;
    }
    
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="直近検索時の他テーブルの追加必要情報">
    // 項目ID
    public String observationDefinitionId;
    public String getObservationDefinitionId(){
        return this.observationDefinitionId;
    }
    public void setObservationDefinitionId(String observationDefinitionId){
        this.observationDefinitionId=observationDefinitionId;
    }

    // 検査結果値
    public String observationValue;
    public String getObservationValue(){
        return this.observationValue;
    }
    public void setObservationValue(String observationValue){
        this.observationValue=observationValue;
    }

    // 範囲外種別CD
    public Integer outRangeTypeCd;
    public Integer getOutRangeTypeCd(){
        return this.outRangeTypeCd;
    }
    public void setOutRangeTypeCd(Integer outRangeTypeCd){
        this.outRangeTypeCd=outRangeTypeCd;
    }

    // 単位
    public String unitValue;
    public String getUnitValue(){
        return this.unitValue;
    }
    public void setUnitValue(String unitValue){
        this.unitValue=unitValue;
    }

    // 更新日付
    public Timestamp updateDateTimeA;
    public Timestamp getUpdateDateTimeA(){
        return this.updateDateTimeA;
    }
    public void setUpdateDateTimeA(Timestamp updateDateTimeA){
        this.updateDateTimeA=updateDateTimeA;
    }
    // アラートレベル
    public Integer alertLevelCd;
    public Integer getAlertLevelCd(){
        return this.alertLevelCd;
    }
    public void setAlertLevelCd(Integer alertLevelCd){
        this.alertLevelCd=alertLevelCd;
    }
    // 表示名
    public String ｄisplayName;
    public String getDisplayName(){
        return this.ｄisplayName;
    }
    public void setDisplayName(String ｄisplayName){
        this.ｄisplayName=ｄisplayName;
    }
    // 最新検査日
    public Timestamp newExaminationDate;
    public Timestamp getNewExaminationDate(){
        return this.newExaminationDate;
    }
    public void setNewExaminationDate(Timestamp newExaminationDate){
        this.newExaminationDate=newExaminationDate;
    }
    // リマインド
    public Integer reminderTypeCd;
    public Integer getReminderTypeCd(){
        return this.reminderTypeCd;
    }
    public void setReminderTypeCd(Integer reminderTypeCd){
        this.reminderTypeCd=reminderTypeCd;
    }
    // 表示位置（健診用）
    public Integer dispNo;
    public Integer getDispNo(){
        return this.dispNo;
    }
    public void setDispNo(Integer dispNo){
        this.dispNo=dispNo;
    }
    // ソートNo
    public Integer sortNo;
    public Integer getSortNo(){
        return this.sortNo;
    }
    public void setSortNo(Integer sortNo){
        this.sortNo = sortNo;
    }
    // jlac10AnalyteCode
    public String jlac10AnalyteCode;
    public String getjlac10AnalyteCode(){
        return this.jlac10AnalyteCode;
    }
    public void setjlac10AnalyteCode(String jlac10AnalyteCode){
        this.jlac10AnalyteCode = jlac10AnalyteCode;
    }

//</editor-fold>

    /* -------------------------------------------------------------------------------------- */
    
    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationEventEntity
     * @throws java.lang.Throwable
     */
    public static ObservationEventEntity setDataPatient(ResultSet dataRow) throws Throwable {
        ObservationEventEntity entity = new ObservationEventEntity();
        entity = setData(dataRow , true);
        return setDataPatient(entity, dataRow , true);
    }
    
    /**
     * オブジェクトにデータをセットします
     *
     * @param entity
     * @param  dataRow  結果リスト
     * @param isChildTable
     * @return  ObservationEventEntity
     * @throws java.lang.Throwable
     */
    public static ObservationEventEntity setDataPatient(ObservationEventEntity entity, ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.debug("Start");
        entity.setFamilyName(getString(dataRow, "FamilyName"));
        entity.setGivenName(getString(dataRow, "GivenName"));
        entity.setFamilyKana(getString(dataRow, "FamilyKana"));
        entity.setGivenKana(getString(dataRow, "GivenKana"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setAlertFlg(getString(dataRow, "AlertFlg"));
        entity.setObservationDefinitionName(getString(dataRow, "ObservationDefinitionName"));
        entity.setDiseaseManagementTargetFlg(getBoolean(dataRow, "diseaseManagementTargetFlg"));
        if (logger.isDebugEnabled())
        {
            logger.debug("氏名_姓       ：" + entity.getFamilyName());
            logger.debug("氏名_名       ：" + entity.getGivenName());
            logger.debug("カナ氏名_姓   ：" + entity.getFamilyKana());
            logger.debug("カナ氏名_名   ：" + entity.getGivenKana());
            logger.debug("検査コード    ：" + entity.getObservationDefinitionId());
            logger.debug("アラートフラグ ：" + entity.getAlertFlg());
            logger.debug("検査名称　　   ：" + entity.getObservationDefinitionName());
            logger.debug("疾病管理対象フラグ　　   ：" + entity.isDiseaseManagementTargetFlg());
        }
        logger.debug("End");
        return entity;
    }

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "familyName=" + familyName + 
                ", givenName=" + givenName + 
                ", familyKana=" + familyKana + 
                ", givenKana=" + givenKana + 
                ", observationDefinitionId=" + observationDefinitionId + 
                ", alertFlg=" + alertFlg + 
                ", observationDefinitionName=" + observationDefinitionName + 
                ", diseaseManagementTargetFlg=" + diseaseManagementTargetFlg + 
                " }\r\n";
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationEventEntity
     */
    public static ObservationEventEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationEventEntity
     */
    public static ObservationEventEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.debug("Start");
        ObservationEventEntity entity = new ObservationEventEntity();
        entity.setObservationEventId(getString(dataRow, "ObservationEventId"));
        entity.setDataInputTypeCd(getInt(dataRow, "DataInputTypeCd"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setExaminationDate(getDateTime(dataRow, "ExaminationDate"));
        entity.setYear(getInt(dataRow, "Year"));
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setLaboratoryCd(getString(dataRow, "LaboratoryCd"));
        entity.setOrderNo(getString(dataRow, "OrderNo"));
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));
        entity.setDiseaseManagementTargetFlg(getBoolean(dataRow, "DiseaseManagementTargetFlg"));
        
        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果ID      ：" + entity.getObservationEventId());
            logger.debug("データ入力種別CD：" + entity.getDataInputTypeCd());
            logger.debug("PHR ID          ：" + entity.getPHR_ID());
            logger.debug("検査日          ：" + entity.getExaminationDate());
            logger.debug("対象年度        ：" + entity.getYear());
            logger.debug("保険者番号      ：" + entity.getInsurerNo());
            logger.debug("検査会社CD      ：" + entity.getLaboratoryCd());
            logger.debug("検査オーダーNo  ：" + entity.getOrderNo());
            logger.debug("医療機関コード  ：" + entity.getMedicalOrganizationCd());
            logger.debug("作成日時        ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時    ：" + entity.getUpdateDateTime());
            logger.debug("疾病管理対象フラグ    ：" + entity.isDiseaseManagementTargetFlg());
        }
        logger.debug("End");
        return entity;
    }
    public String getRefEnumName() {
        return refEnumName;
    }
    public void setRefEnumName(String refEnumName) {
        this.refEnumName = refEnumName;
    }
    public Double getMinReferenceValue2() {
        return minReferenceValue2;
    }
    public Double getMaxReferenceValue2() {
        return maxReferenceValue2;
    }
    public void setMinReferenceValue2(Double minReferenceValue2) {
        this.minReferenceValue2 = minReferenceValue2;
    }
    public void setMaxReferenceValue2(Double maxReferenceValue2) {
        this.maxReferenceValue2 = maxReferenceValue2;
    }
    /**
     * @return jlac10
     */
    public String getJlac10() {
        return jlac10;
    }
    /**
     * @param jlac10 セットする jlac10
     */
    public void setJlac10(String jlac10) {
        this.jlac10 = jlac10;
    }
}
