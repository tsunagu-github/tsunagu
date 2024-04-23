/********************************************************************************
 * システム名      ：MInCS for PHR
 * コンポーネント名：PHR推奨設定シートのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/12/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.PHRReferenceRangeEntity;

/**
 * PHR推奨設定シートのデータオブジェクトです。
 */
public abstract class PHRReferenceRangeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(PHRReferenceRangeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public PHRReferenceRangeEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* 項目名称 */
    protected String observationDefinitionName = null;
    /* 単位 */
    protected String unit = null;
    /* 単位コード */
    protected String unitCode = null;
    /* 基準値下限 */
    protected Double minReferenceValue = null;
    /* 基準値上限 */
    protected Double maxReferenceValue = null;
    /* 男性基準値下限 */
    protected Double maleMinReferenceValue = null;
    /* 男性基準値上限 */
    protected Double maleMaxReferenceValue = null;
    /* 女性基準値下限 */
    protected Double femaleMinReferenceValue = null;
    /* 女性基準値上限 */
    protected Double femaleMaxReferenceValue = null;
    /* 列挙名称 */
    protected String referenceEnumName = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  PHRReferenceRangeEntity
     */
    public static PHRReferenceRangeEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  PHRReferenceRangeEntity
     */
    public static PHRReferenceRangeEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        PHRReferenceRangeEntity entity = new PHRReferenceRangeEntity();
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setObservationDefinitionName(getString(dataRow, "ObservationDefinitionName"));
        entity.setUnit(getString(dataRow, "Unit"));
        entity.setUnitCode(getString(dataRow, "UnitCode"));
        entity.setMinReferenceValue(getNullDouble(dataRow, "MinReferenceValue"));
        entity.setMaxReferenceValue(getNullDouble(dataRow, "MaxReferenceValue"));
        entity.setMaleMinReferenceValue(getNullDouble(dataRow, "MaleMinReferenceValue"));
        entity.setMaleMaxReferenceValue(getNullDouble(dataRow, "MaleMaxReferenceValue"));
        entity.setFemaleMinReferenceValue(getNullDouble(dataRow, "FemaleMinReferenceValue"));
        entity.setFemaleMaxReferenceValue(getNullDouble(dataRow, "FemaleMaxReferenceValue"));
        entity.setReferenceEnumName(getString(dataRow, "ReferenceEnumName"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("項目ID        ：" + entity.getObservationDefinitionId());
            logger.debug("項目名称      ：" + entity.getObservationDefinitionName());
            logger.debug("単位          ：" + entity.getUnit());
            logger.debug("単位コード    ：" + entity.getUnitCode());
            logger.debug("基準値下限    ：" + entity.getMinReferenceValue());
            logger.debug("基準値上限    ：" + entity.getMaxReferenceValue());
            logger.debug("男性基準値下限：" + entity.getMaleMinReferenceValue());
            logger.debug("男性基準値上限：" + entity.getMaleMaxReferenceValue());
            logger.debug("女性基準値下限：" + entity.getFemaleMinReferenceValue());
            logger.debug("女性基準値上限：" + entity.getFemaleMaxReferenceValue());
            logger.debug("列挙名称      ：" + entity.getReferenceEnumName());
            logger.debug("作成日時      ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時  ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * 項目名称を取得する
     *
     * @return
     */
    public String getObservationDefinitionName() {
        return this.observationDefinitionName;
    }
    /**
     * 項目名称を設定する
     *
     * @param value
     */
    public void setObservationDefinitionName(String value) {
        this.observationDefinitionName = value;
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
     * 単位コードを取得する
     *
     * @return
     */
    public String getUnitCode() {
        return this.unitCode;
    }
    /**
     * 単位コードを設定する
     *
     * @param value
     */
    public void setUnitCode(String value) {
        this.unitCode = value;
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
     * 男性基準値下限を取得する
     *
     * @return
     */
    public Double getMaleMinReferenceValue() {
        return this.maleMinReferenceValue;
    }
    /**
     * 男性基準値下限を設定する
     *
     * @param value
     */
    public void setMaleMinReferenceValue(Double value) {
        this.maleMinReferenceValue = value;
    }

    /**
     * 男性基準値上限を取得する
     *
     * @return
     */
    public Double getMaleMaxReferenceValue() {
        return this.maleMaxReferenceValue;
    }
    /**
     * 男性基準値上限を設定する
     *
     * @param value
     */
    public void setMaleMaxReferenceValue(Double value) {
        this.maleMaxReferenceValue = value;
    }

    /**
     * 女性基準値下限を取得する
     *
     * @return
     */
    public Double getFemaleMinReferenceValue() {
        return this.femaleMinReferenceValue;
    }
    /**
     * 女性基準値下限を設定する
     *
     * @param value
     */
    public void setFemaleMinReferenceValue(Double value) {
        this.femaleMinReferenceValue = value;
    }

    /**
     * 女性基準値上限を取得する
     *
     * @return
     */
    public Double getFemaleMaxReferenceValue() {
        return this.femaleMaxReferenceValue;
    }
    /**
     * 女性基準値上限を設定する
     *
     * @param value
     */
    public void setFemaleMaxReferenceValue(Double value) {
        this.femaleMaxReferenceValue = value;
    }

    /**
     * 列挙名称を取得する
     *
     * @return
     */
    public String getReferenceEnumName() {
        return this.referenceEnumName;
    }
    /**
     * 列挙名称を設定する
     *
     * @param value
     */
    public void setReferenceEnumName(String value) {
        this.referenceEnumName = value;
    }

    /**
     * 作成日時を取得する
     *
     * @return
     */
    public Timestamp getCreateDateTime() {
        return this.createDateTime;
    }
    /**
     * 作成日時を設定する
     *
     * @param value
     */
    public void setCreateDateTime(Timestamp value) {
        this.createDateTime = value;
    }

    /**
     * 最終更新日時を取得する
     *
     * @return
     */
    public Timestamp getUpdateDateTime() {
        return this.updateDateTime;
    }
    /**
     * 最終更新日時を設定する
     *
     * @param value
     */
    public void setUpdateDateTime(Timestamp value) {
        this.updateDateTime = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "observationDefinitionId=" + observationDefinitionId + 
                ", observationDefinitionName=" + observationDefinitionName + 
                ", unit=" + unit + 
                ", unitCode=" + unitCode + 
                ", minReferenceValue=" + minReferenceValue + 
                ", maxReferenceValue=" + maxReferenceValue + 
                ", maleMinReferenceValue=" + maleMinReferenceValue + 
                ", maleMaxReferenceValue=" + maleMaxReferenceValue + 
                ", femaleMinReferenceValue=" + femaleMinReferenceValue + 
                ", femaleMaxReferenceValue=" + femaleMaxReferenceValue + 
                ", referenceEnumName=" + referenceEnumName + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
