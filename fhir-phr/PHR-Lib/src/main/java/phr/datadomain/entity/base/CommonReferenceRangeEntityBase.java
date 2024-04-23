/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：共用基準範囲のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.CommonReferenceRangeEntity;

/**
 * 共用基準範囲のデータオブジェクトです。
 */
public abstract class CommonReferenceRangeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(CommonReferenceRangeEntityBase.class);
    private static Logger logger = Logger.getLogger(CommonReferenceRangeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public CommonReferenceRangeEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* JLAC10分析物コード */
    protected String jLAC10AnalyteCode = null;
    /* JLAC11測定物コード */
    protected String jLAC11AnalyteCode = null;
    /* 項目名称 */
    protected String observationDefinitionName = null;
    /* 略称 */
    protected String observationDefinitionShortName = null;
    /* 単位 */
    protected String unit = null;
    /* 単位コード */
    protected String unitCode = null;
    /* 基準値区分 */
    protected String referenceRangeType = null;
    /* 共通基準範囲下限 */
    protected Double commonLowerLimit = null;
    /* 共通基準範囲上限 */
    protected Double commonUpperLimit = null;
    /* 男性基準範囲下限 */
    protected Double maleLowerLimit = null;
    /* 男性基準範囲上限 */
    protected Double maleUpperLimit = null;
    /* 女性基準範囲下限 */
    protected Double femaleLowerLimit = null;
    /* 女性基準範囲上限 */
    protected Double femaleUpperLimit = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  CommonReferenceRangeEntity
     */
    public static CommonReferenceRangeEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  CommonReferenceRangeEntity
     */
    public static CommonReferenceRangeEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.debug("Start");
        CommonReferenceRangeEntity entity = new CommonReferenceRangeEntity();
        entity.setJLAC10AnalyteCode(getString(dataRow, "JLAC10AnalyteCode"));
        entity.setJLAC11AnalyteCode(getString(dataRow, "JLAC11AnalyteCode"));
        entity.setObservationDefinitionName(getString(dataRow, "ObservationDefinitionName"));
        entity.setObservationDefinitionShortName(getString(dataRow, "ObservationDefinitionShortName"));
        entity.setUnit(getString(dataRow, "Unit"));
        entity.setUnitCode(getString(dataRow, "UnitCode"));
        entity.setReferenceRangeType(getString(dataRow, "ReferenceRangeType"));
        entity.setCommonLowerLimit(getNullDouble(dataRow, "CommonLowerLimit"));
        entity.setCommonUpperLimit(getNullDouble(dataRow, "CommonUpperLimit"));
        entity.setMaleLowerLimit(getNullDouble(dataRow, "MaleLowerLimit"));
        entity.setMaleUpperLimit(getNullDouble(dataRow, "MaleUpperLimit"));
        entity.setFemaleLowerLimit(getNullDouble(dataRow, "FemaleLowerLimit"));
        entity.setFemaleUpperLimit(getNullDouble(dataRow, "FemaleUpperLimit"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("JLAC10分析物コード：" + entity.getJLAC10AnalyteCode());
            logger.debug("JLAC11測定物コード：" + entity.getJLAC11AnalyteCode());
            logger.debug("項目名称          ：" + entity.getObservationDefinitionName());
            logger.debug("略称              ：" + entity.getObservationDefinitionShortName());
            logger.debug("単位              ：" + entity.getUnit());
            logger.debug("単位コード        ：" + entity.getUnitCode());
            logger.debug("基準値区分        ：" + entity.getReferenceRangeType());
            logger.debug("共通基準範囲下限  ：" + entity.getCommonLowerLimit());
            logger.debug("共通基準範囲上限  ：" + entity.getCommonUpperLimit());
            logger.debug("男性基準範囲下限  ：" + entity.getMaleLowerLimit());
            logger.debug("男性基準範囲上限  ：" + entity.getMaleUpperLimit());
            logger.debug("女性基準範囲下限  ：" + entity.getFemaleLowerLimit());
            logger.debug("女性基準範囲上限  ：" + entity.getFemaleUpperLimit());
            logger.debug("作成日時          ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時      ：" + entity.getUpdateDateTime());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * JLAC10分析物コードを取得する
     *
     * @return
     */
    public String getJLAC10AnalyteCode() {
        return this.jLAC10AnalyteCode;
    }
    /**
     * JLAC10分析物コードを設定する
     *
     * @param value
     */
    public void setJLAC10AnalyteCode(String value) {
        this.jLAC10AnalyteCode = value;
    }

    /**
     * JLAC11測定物コードを取得する
     *
     * @return
     */
    public String getJLAC11AnalyteCode() {
        return this.jLAC11AnalyteCode;
    }
    /**
     * JLAC11測定物コードを設定する
     *
     * @param value
     */
    public void setJLAC11AnalyteCode(String value) {
        this.jLAC11AnalyteCode = value;
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
     * 略称を取得する
     *
     * @return
     */
    public String getObservationDefinitionShortName() {
        return this.observationDefinitionShortName;
    }
    /**
     * 略称を設定する
     *
     * @param value
     */
    public void setObservationDefinitionShortName(String value) {
        this.observationDefinitionShortName = value;
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
     * 基準値区分を取得する
     *
     * @return
     */
    public String getReferenceRangeType() {
        return this.referenceRangeType;
    }
    /**
     * 基準値区分を設定する
     *
     * @param value
     */
    public void setReferenceRangeType(String value) {
        this.referenceRangeType = value;
    }

    /**
     * 共通基準範囲下限を取得する
     *
     * @return
     */
    public Double getCommonLowerLimit() {
        return this.commonLowerLimit;
    }
    /**
     * 共通基準範囲下限を設定する
     *
     * @param value
     */
    public void setCommonLowerLimit(Double value) {
        this.commonLowerLimit = value;
    }

    /**
     * 共通基準範囲上限を取得する
     *
     * @return
     */
    public Double getCommonUpperLimit() {
        return this.commonUpperLimit;
    }
    /**
     * 共通基準範囲上限を設定する
     *
     * @param value
     */
    public void setCommonUpperLimit(Double value) {
        this.commonUpperLimit = value;
    }

    /**
     * 男性基準範囲下限を取得する
     *
     * @return
     */
    public Double getMaleLowerLimit() {
        return this.maleLowerLimit;
    }
    /**
     * 男性基準範囲下限を設定する
     *
     * @param value
     */
    public void setMaleLowerLimit(Double value) {
        this.maleLowerLimit = value;
    }

    /**
     * 男性基準範囲上限を取得する
     *
     * @return
     */
    public Double getMaleUpperLimit() {
        return this.maleUpperLimit;
    }
    /**
     * 男性基準範囲上限を設定する
     *
     * @param value
     */
    public void setMaleUpperLimit(Double value) {
        this.maleUpperLimit = value;
    }

    /**
     * 女性基準範囲下限を取得する
     *
     * @return
     */
    public Double getFemaleLowerLimit() {
        return this.femaleLowerLimit;
    }
    /**
     * 女性基準範囲下限を設定する
     *
     * @param value
     */
    public void setFemaleLowerLimit(Double value) {
        this.femaleLowerLimit = value;
    }

    /**
     * 女性基準範囲上限を取得する
     *
     * @return
     */
    public Double getFemaleUpperLimit() {
        return this.femaleUpperLimit;
    }
    /**
     * 女性基準範囲上限を設定する
     *
     * @param value
     */
    public void setFemaleUpperLimit(Double value) {
        this.femaleUpperLimit = value;
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
                "jLAC10AnalyteCode=" + jLAC10AnalyteCode + 
                ", jLAC11AnalyteCode=" + jLAC11AnalyteCode + 
                ", observationDefinitionName=" + observationDefinitionName + 
                ", observationDefinitionShortName=" + observationDefinitionShortName + 
                ", unit=" + unit + 
                ", unitCode=" + unitCode + 
                ", referenceRangeType=" + referenceRangeType + 
                ", commonLowerLimit=" + commonLowerLimit + 
                ", commonUpperLimit=" + commonUpperLimit + 
                ", maleLowerLimit=" + maleLowerLimit + 
                ", maleUpperLimit=" + maleUpperLimit + 
                ", femaleLowerLimit=" + femaleLowerLimit + 
                ", femaleUpperLimit=" + femaleUpperLimit + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
