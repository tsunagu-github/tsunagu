/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目範囲情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/11/04
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;

/**
 * 管理項目範囲情報のデータオブジェクトです。
 */
public abstract class ObservationDefinitionRangeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionRangeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionRangeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 対象年度 */
    protected int year = 0;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* 疾病種別CD */
    protected int diseaseTypeCd = 0;
    /* リマインダCD */
    protected Integer reminderTypeCd = null;
    /* 範囲種別CD */
    protected int rangeTypeCd = 0;
    /* 異常値下限 */
    protected Double minInput = null;
    /* 異常値上限 */
    protected Double maxInput = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionRangeEntity
     */
    public static ObservationDefinitionRangeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionRangeEntity
     */
    public static ObservationDefinitionRangeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationDefinitionRangeEntity entity = new ObservationDefinitionRangeEntity();
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setYear(getInt(dataRow, "Year"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setDiseaseTypeCd(getInt(dataRow, "DiseaseTypeCd"));
        entity.setReminderTypeCd(getNullInt(dataRow, "ReminderTypeCd"));
        entity.setRangeTypeCd(getInt(dataRow, "RangeTypeCd"));
        entity.setMinInput(getNullDouble(dataRow, "MinInput"));
        entity.setMaxInput(getNullDouble(dataRow, "MaxInput"));

        if (logger.isDebugEnabled())
        {
            logger.debug("保険者番号  ：" + entity.getInsurerNo());
            logger.debug("対象年度    ：" + entity.getYear());
            logger.debug("項目ID      ：" + entity.getObservationDefinitionId());
            logger.debug("疾病種別CD  ：" + entity.getDiseaseTypeCd());
            logger.debug("リマインダCD：" + entity.getReminderTypeCd());
            logger.debug("範囲種別CD  ：" + entity.getRangeTypeCd());
            logger.debug("異常値下限  ：" + entity.getMinInput());
            logger.debug("異常値上限  ：" + entity.getMaxInput());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 保険者番号を取得する
     *
     * @return
     */
    public String getInsurerNo() {
        return this.insurerNo;
    }
    /**
     * 保険者番号を設定する
     *
     * @param value
     */
    public void setInsurerNo(String value) {
        this.insurerNo = value;
    }

    /**
     * 対象年度を取得する
     *
     * @return
     */
    public int getYear() {
        return this.year;
    }
    /**
     * 対象年度を設定する
     *
     * @param value
     */
    public void setYear(int value) {
        this.year = value;
    }

    /**
     * 項目IDを取得する
     *
     * @return
     */
    public String getObservationDefinitionId() {
        return this.observationDefinitionId;
    }
    /**
     * 項目IDを設定する
     *
     * @param value
     */
    public void setObservationDefinitionId(String value) {
        this.observationDefinitionId = value;
    }

    /**
     * 疾病種別CDを取得する
     *
     * @return
     */
    public int getDiseaseTypeCd() {
        return this.diseaseTypeCd;
    }
    /**
     * 疾病種別CDを設定する
     *
     * @param value
     */
    public void setDiseaseTypeCd(int value) {
        this.diseaseTypeCd = value;
    }

    /**
     * リマインダCDを取得する
     *
     * @return
     */
    public Integer getReminderTypeCd() {
        return this.reminderTypeCd;
    }
    /**
     * リマインダCDを設定する
     *
     * @param value
     */
    public void setReminderTypeCd(Integer value) {
        this.reminderTypeCd = value;
    }

    /**
     * 範囲種別CDを取得する
     *
     * @return
     */
    public int getRangeTypeCd() {
        return this.rangeTypeCd;
    }
    /**
     * 範囲種別CDを設定する
     *
     * @param value
     */
    public void setRangeTypeCd(int value) {
        this.rangeTypeCd = value;
    }

    /**
     * 異常値下限を取得する
     *
     * @return
     */
    public Double getMinInput() {
        return this.minInput;
    }
    /**
     * 異常値下限を設定する
     *
     * @param value
     */
    public void setMinInput(Double value) {
        this.minInput = value;
    }

    /**
     * 異常値上限を取得する
     *
     * @return
     */
    public Double getMaxInput() {
        return this.maxInput;
    }
    /**
     * 異常値上限を設定する
     *
     * @param value
     */
    public void setMaxInput(Double value) {
        this.maxInput = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "nsurerNo=" + insurerNo + 
                ", ear=" + year + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                ", iseaseTypeCd=" + diseaseTypeCd + 
                ", eminderTypeCd=" + reminderTypeCd + 
                ", angeTypeCd=" + rangeTypeCd + 
                ", inInput=" + minInput + 
                ", axInput=" + maxInput + 
                " }\r\n";
    }
}
