/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：スマートフォンアプリ対応健診（健診・問診・診察）結果のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.base.PhoneCUValueEntityBase;

/**
 *
 * @author kis-note-027_user
 */
public class PhoneCUValueEntity extends PhoneCUValueEntityBase{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhoneCUValueEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public PhoneCUValueEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */        
    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 最大基準値 */
    protected String maxReferenceValue = null;
    /* 最小基準値 */
    protected String minReferenceValue = null;
    /* 単位 */
    protected String unit = null;
    /* ObservationEventId */
    protected String observationEventId = null;
    /* MedicalOrganizationCd */
    protected String medicalOrganizationCd = null;
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static PhoneCUValueEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static PhoneCUValueEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        PhoneCUValueEntity entity = new PhoneCUValueEntity();
        entity.setExamdate(getDate(dataRow, "Examdate"));
        entity.setValue(getString(dataRow, "Value"));
        entity.setMaxReferenceValue(getString(dataRow, "MaxReferenceValue"));
        entity.setMinReferenceValue(getString(dataRow, "MinReferenceValue"));
        entity.setUnit(getString(dataRow, "Unit"));
        entity.setObservationEventId(getString(dataRow, "ObservationEventId"));
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));

        if (logger.isDebugEnabled())
        {

            logger.debug("健診日    ：" + entity.getExamdate());
            logger.debug("健診値       ：" + entity.getValue());
            logger.debug("最大基準値       ：" + entity.getMaxReferenceValue());
            logger.debug("最小基準値       ：" + entity.getMinReferenceValue());
            logger.debug("単位       ：" + entity.getUnit());
            logger.debug("ObservationEventId       ：" + entity.getObservationEventId());
            logger.debug("MedicalOrganizationCd    ：" + entity.getMedicalOrganizationCd());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 最大基準値を取得する
     *
     * @return
     */
    public String getMaxReferenceValue() {
        return this.maxReferenceValue;
    }

    /**
     * 最大基準値を設定する
     *
     * @param value
     */
    public void setMaxReferenceValue(String value) {
        this.maxReferenceValue = value;
    }

    /**
     * 最小基準値を取得する
     *
     * @return
     */
    public String getMinReferenceValue() {
        return this.minReferenceValue;
    }

    /**
     * 最小基準値を設定する
     *
     * @param value
     */
    public void setMinReferenceValue(String value) {
        this.minReferenceValue = value;
    }

    /**
     * 単位を取得する
     *
     * @return
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * 単位を設定する
     *
     * @param value
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * ObservationEventIdを取得する
     *
     * @return
     */
    public String getObservationEventId() {
        return this.observationEventId;
    }

    /**
     * ObservationEventIdを設定する
     *
     * @param value
     */
    public void setObservationEventId(String value) {
        this.observationEventId = value;
    }

    /**
     * MedicalOrganizationCdを取得する
     *
     * @return
     */
    public String getMedicalOrganizationCd() {
        return this.medicalOrganizationCd;
    }

    /**
     * MedicalOrganizationCdを設定する
     *
     * @param value
     */
    public void setMedicalOrganizationCd(String value) {
        this.medicalOrganizationCd = value;
    }
}
