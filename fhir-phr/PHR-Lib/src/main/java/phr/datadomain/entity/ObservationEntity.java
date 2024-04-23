/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査項目結果情報のデータオブジェクト
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
 * 検査項目結果情報のデータオブジェクトです。
 */
public class ObservationEntity extends ObservationEntityBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationEntity.class);
    private static Logger logger = Logger.getLogger(ObservationEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    /* 項目名称 */
    private String observationDefinitionName = null;
    /* 表示名 */
    private String displayName = null;
    /* 定義上のJLAC10 */
    private String D_JLAC10 = null;
    /* 単位 */
    private String unitValue = null;
    /* 疾病管理対象フラグ */
    private boolean diseaseManagementTargetFlg;
    
    /**/
    private boolean flag;
    
    /* データ入力種別CD */
    protected int dataInputTypeCd = 0;

    /* 基準値下限1 */
    protected Double minReferenceValue1 = null;
    /* 基準値上限1 */
    protected Double maxReferenceValue1 = null;
    /* 単位1 */
    protected String unit1 = null;
    /* 基準値下限2 */
    protected Double minReferenceValue2 = null;
    /* 基準値上限2 */
    protected Double maxReferenceValue2 = null;
    /* 単位2 */
    protected String unit2 = null;
    /* アラートレベル */
    protected Integer alertLevelCd;
    /*基準値名称*/
    protected String referenceEnumName = null;
    
    /**
     * 実施日時
     */
    private Date examinationDate = null;
    
    /*病院名*/
    protected String medicalOrganizationName = null;
    
    /**
     * @return the observationDefinitionName
     */
    public String getObservationDefinitionName() {
        return observationDefinitionName;
    }

    /**
     * @param observationDefinitionName the observationDefinitionName to set
     */
    public void setObservationDefinitionName(String observationDefinitionName) {
        this.observationDefinitionName = observationDefinitionName;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the D_JLAC10
     */
    public String getD_JLAC10() {
        return D_JLAC10;
    }

    /**
     * @param D_JLAC10 the D_JLAC10 to set
     */
    public void setD_JLAC10(String D_JLAC10) {
        this.D_JLAC10 = D_JLAC10;
    }

    /**
     * @return the unitValue
     */
    public String getUnitValue() {
        return unitValue;
    }

    /**
     * @param unitValue the unitValue to set
     */
    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    /**
     * 実施日時
     * @return the examinationDate
     */
    public Date getExaminationDate() {
        return examinationDate;
    }

    /**
     * 実施日時
     * @param examinationDate the examinationDate to set
     */
    public void setExaminationDate(Date examinationDate) {
        this.examinationDate = examinationDate;
    }

	/**
	 * 疾病管理対象フラグ
	 * @return diseaseManagementTargetFlg
	 */
	public Boolean isDiseaseManagementTargetFlg() {
		return diseaseManagementTargetFlg;
	}

	/**
	 * 疾病管理対象フラグ
	 * @param diseaseManagementTargetFlg セットする diseaseManagementTargetFlg
	 */
	public void setDiseaseManagementTargetFlg(boolean diseaseManagementTargetFlg) {
		this.diseaseManagementTargetFlg = diseaseManagementTargetFlg;
	}
	
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    public int getDataInputTypeCd() {
        return dataInputTypeCd;
    }

    public void setDataInputTypeCd(int dataInputTypeCd) {
        this.dataInputTypeCd = dataInputTypeCd;
    }

    public Double getMinReferenceValue1() {
        return minReferenceValue1;
    }

    public Double getMaxReferenceValue1() {
        return maxReferenceValue1;
    }

    public String getUnit1() {
        return unit1;
    }

    public Double getMinReferenceValue2() {
        return minReferenceValue2;
    }

    public Double getMaxReferenceValue2() {
        return maxReferenceValue2;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setMinReferenceValue1(Double minReferenceValue1) {
        this.minReferenceValue1 = minReferenceValue1;
    }

    public void setMaxReferenceValue1(Double maxReferenceValue1) {
        this.maxReferenceValue1 = maxReferenceValue1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public void setMinReferenceValue2(Double minReferenceValue2) {
        this.minReferenceValue2 = minReferenceValue2;
    }

    public void setMaxReferenceValue2(Double maxReferenceValue2) {
        this.maxReferenceValue2 = maxReferenceValue2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public Integer getAlertLevelCd() {
        return alertLevelCd;
    }

    public void setAlertLevelCd(Integer alertLevelCd) {
        this.alertLevelCd = alertLevelCd;
    }

    public String getReferenceEnumName() {
        return referenceEnumName;
    }

    public void setReferenceEnumName(String referenceEnumName) {
        this.referenceEnumName = referenceEnumName;
    }

    public String getMedicalOrganizationName() {
        return medicalOrganizationName;
    }

    public void setMedicalOrganizationName(String medicalOrganizationName) {
        this.medicalOrganizationName = medicalOrganizationName;
    }

}
