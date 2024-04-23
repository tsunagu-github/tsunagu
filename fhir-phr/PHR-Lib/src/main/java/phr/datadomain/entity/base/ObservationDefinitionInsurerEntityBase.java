/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者管理項目情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/19
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
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;

/**
 * 保険者管理項目情報のデータオブジェクトです。
 */
public abstract class ObservationDefinitionInsurerEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionInsurerEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionInsurerEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* ViewID */
    protected int viewId = 0;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* リマインダ種別CD */
    protected Integer reminderTypeCd = null;
    /* 順序 */
    protected Integer sortNo = null;
    /* 単位 */
    protected String unitValue = null;
    /* データ種別CD */
    protected Integer dataTypeCd = null;
    /* 基準値下限 */
    protected Double minReferenceValue = null;
    /* 基準値上限 */
    protected Double maxReferenceValue = null;
    /* デフォルトJLAC10コード */
    protected String defaultJLAC10 = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionInsurerEntity
     */
    public static ObservationDefinitionInsurerEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionInsurerEntity
     */
    public static ObservationDefinitionInsurerEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationDefinitionInsurerEntity entity = new ObservationDefinitionInsurerEntity();
        entity.setViewId(getInt(dataRow, "ViewId"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setReminderTypeCd(getNullInt(dataRow, "ReminderTypeCd"));
        entity.setSortNo(getNullInt(dataRow, "SortNo"));
        entity.setUnitValue(getString(dataRow, "UnitValue"));
        entity.setDataTypeCd(getNullInt(dataRow, "DataTypeCd"));
        entity.setMinReferenceValue(getNullDouble(dataRow, "MinReferenceValue"));
        entity.setMaxReferenceValue(getNullDouble(dataRow, "MaxReferenceValue"));
        entity.setDefaultJLAC10(getString(dataRow, "DefaultJLAC10"));

        if (logger.isDebugEnabled())
        {
            logger.debug("ViewID                ：" + entity.getViewId());
            logger.debug("項目ID                ：" + entity.getObservationDefinitionId());
            logger.debug("リマインダ種別CD      ：" + entity.getReminderTypeCd());
            logger.debug("順序                  ：" + entity.getSortNo());
            logger.debug("単位                  ：" + entity.getUnitValue());
            logger.debug("データ種別CD          ：" + entity.getDataTypeCd());
            logger.debug("基準値下限            ：" + entity.getMinReferenceValue());
            logger.debug("基準値上限            ：" + entity.getMaxReferenceValue());
            logger.debug("デフォルトJLAC10コード：" + entity.getDefaultJLAC10());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * ViewIDを取得する
     *
     * @return
     */
    public int getViewId() {
        return this.viewId;
    }
    /**
     * ViewIDを設定する
     *
     * @param value
     */
    public void setViewId(int value) {
        this.viewId = value;
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
     * リマインダ種別CDを取得する
     *
     * @return
     */
    public Integer getReminderTypeCd() {
        return this.reminderTypeCd;
    }
    /**
     * リマインダ種別CDを設定する
     *
     * @param value
     */
    public void setReminderTypeCd(Integer value) {
        this.reminderTypeCd = value;
    }

    /**
     * 順序を取得する
     *
     * @return
     */
    public Integer getSortNo() {
        return this.sortNo;
    }
    /**
     * 順序を設定する
     *
     * @param value
     */
    public void setSortNo(Integer value) {
        this.sortNo = value;
    }

    /**
     * 単位を取得する
     *
     * @return
     */
    public String getUnitValue() {
        return this.unitValue;
    }
    /**
     * 単位を設定する
     *
     * @param value
     */
    public void setUnitValue(String value) {
        this.unitValue = value;
    }

    /**
     * データ種別CDを取得する
     *
     * @return
     */
    public Integer getDataTypeCd() {
        return this.dataTypeCd;
    }
    /**
     * データ種別CDを設定する
     *
     * @param value
     */
    public void setDataTypeCd(Integer value) {
        this.dataTypeCd = value;
    }

    /**
     * 基準値下限を取得する
     *
     * @return
     */
    public Double getMinReferenceValue() {
        return this.minReferenceValue;
    }
    /**
     * 基準値下限を設定する
     *
     * @param value
     */
    public void setMinReferenceValue(Double value) {
        this.minReferenceValue = value;
    }

    /**
     * 基準値上限を取得する
     *
     * @return
     */
    public Double getMaxReferenceValue() {
        return this.maxReferenceValue;
    }
    /**
     * 基準値上限を設定する
     *
     * @param value
     */
    public void setMaxReferenceValue(Double value) {
        this.maxReferenceValue = value;
    }

    /**
     * デフォルトJLAC10コードを取得する
     *
     * @return
     */
    public String getDefaultJLAC10() {
        return this.defaultJLAC10;
    }
    /**
     * デフォルトJLAC10コードを設定する
     *
     * @param value
     */
    public void setDefaultJLAC10(String value) {
        this.defaultJLAC10 = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "iewId=" + viewId + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                ", eminderTypeCd=" + reminderTypeCd + 
                ", ortNo=" + sortNo + 
                ", nitValue=" + unitValue + 
                ", ataTypeCd=" + dataTypeCd + 
                ", inReferenceValue=" + minReferenceValue + 
                ", axReferenceValue=" + maxReferenceValue + 
                ", efaultJLAC10=" + defaultJLAC10 + 
                " }\r\n";
    }
}
