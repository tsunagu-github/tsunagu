/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ入力種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/10/06
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
import phr.datadomain.entity.ObservationDefinitionScriptEntity;

/**
 * データ入力種別のデータオブジェクトです。
 */
public abstract class ObservationDefinitionScriptEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionScriptEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionScriptEntityBase()
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
    /* スクリプト種別ID */
    protected int scriptTypeId = 0;
    /* アラートレベルスクリプト */
    protected String alertLevelScript = null;
    /* アラートフラグスクリプト */
    protected String alertFlgScript = null;
    /* 比較日数 */
    protected Integer compareDate = null;
    /* 上限値 */
    protected Double upperLimit = null;
    /* 下限値 */
    protected Double lowerLimit = null;
    /* 疾病種別CD */
    protected Integer diseaseTypeCd = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionScriptEntity
     */
    public static ObservationDefinitionScriptEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionScriptEntity
     */
    public static ObservationDefinitionScriptEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationDefinitionScriptEntity entity = new ObservationDefinitionScriptEntity();
        entity.setViewId(getInt(dataRow, "ViewId"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setScriptTypeId(getInt(dataRow, "ScriptTypeId"));
        entity.setAlertLevelScript(getString(dataRow, "AlertLevelScript"));
        entity.setAlertFlgScript(getString(dataRow, "AlertFlgScript"));
        entity.setCompareDate(getNullInt(dataRow, "CompareDate"));
        entity.setUpperLimit(getNullDouble(dataRow, "UpperLimit"));
        entity.setLowerLimit(getNullDouble(dataRow, "LowerLimit"));
        entity.setDiseaseTypeCd(getNullInt(dataRow, "DiseaseTypeCd"));

        if (logger.isDebugEnabled())
        {
            logger.debug("ViewID                  ：" + entity.getViewId());
            logger.debug("項目ID                  ：" + entity.getObservationDefinitionId());
            logger.debug("スクリプト種別ID        ：" + entity.getScriptTypeId());
            logger.debug("アラートレベルスクリプト：" + entity.getAlertLevelScript());
            logger.debug("アラートフラグスクリプト：" + entity.getAlertFlgScript());
            logger.debug("比較日数                ：" + entity.getCompareDate());
            logger.debug("上限値                  ：" + entity.getUpperLimit());
            logger.debug("下限値                  ：" + entity.getLowerLimit());
            logger.debug("疾病種別CD              ：" + entity.getDiseaseTypeCd());
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
     * スクリプト種別IDを取得する
     *
     * @return
     */
    public int getScriptTypeId() {
        return this.scriptTypeId;
    }
    /**
     * スクリプト種別IDを設定する
     *
     * @param value
     */
    public void setScriptTypeId(int value) {
        this.scriptTypeId = value;
    }

    /**
     * アラートレベルスクリプトを取得する
     *
     * @return
     */
    public String getAlertLevelScript() {
        return this.alertLevelScript;
    }
    /**
     * アラートレベルスクリプトを設定する
     *
     * @param value
     */
    public void setAlertLevelScript(String value) {
        this.alertLevelScript = value;
    }

    /**
     * アラートフラグスクリプトを取得する
     *
     * @return
     */
    public String getAlertFlgScript() {
        return this.alertFlgScript;
    }
    /**
     * アラートフラグスクリプトを設定する
     *
     * @param value
     */
    public void setAlertFlgScript(String value) {
        this.alertFlgScript = value;
    }

    /**
     * 比較日数を取得する
     *
     * @return
     */
    public Integer getCompareDate() {
        return this.compareDate;
    }
    /**
     * 比較日数を設定する
     *
     * @param value
     */
    public void setCompareDate(Integer value) {
        this.compareDate = value;
    }

    /**
     * 上限値を取得する
     *
     * @return
     */
    public Double getUpperLimit() {
        return this.upperLimit;
    }
    /**
     * 上限値を設定する
     *
     * @param value
     */
    public void setUpperLimit(Double value) {
        this.upperLimit = value;
    }

    /**
     * 下限値を取得する
     *
     * @return
     */
    public Double getLowerLimit() {
        return this.lowerLimit;
    }
    /**
     * 下限値を設定する
     *
     * @param value
     */
    public void setLowerLimit(Double value) {
        this.lowerLimit = value;
    }

    /**
     * 疾病種別CDを取得する
     *
     * @return
     */
    public Integer getDiseaseTypeCd() {
        return this.diseaseTypeCd;
    }
    /**
     * 疾病種別CDを設定する
     *
     * @param value
     */
    public void setDiseaseTypeCd(Integer value) {
        this.diseaseTypeCd = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "viewId=" + viewId + 
                ", observationDefinitionId=" + observationDefinitionId + 
                ", scriptTypeId=" + scriptTypeId + 
                ", alertLevelScript=" + alertLevelScript + 
                ", alertFlgScript=" + alertFlgScript + 
                ", compareDate=" + compareDate + 
                ", upperLimit=" + upperLimit + 
                ", lowerLimit=" + lowerLimit + 
                ", diseaseTypeCd=" + diseaseTypeCd + 
                " }\r\n";
    }
}
