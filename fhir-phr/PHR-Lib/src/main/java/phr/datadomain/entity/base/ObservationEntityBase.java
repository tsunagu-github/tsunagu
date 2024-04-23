/********************************************************************************
 * システム名      ：phr
 * コンポーネント名：検査項目結果情報のデータオブジェクト
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
import org.apache.log4j.Logger;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.ObservationEntity;

/**
 * 検査項目結果情報のデータオブジェクトです。
 */
public abstract class ObservationEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(ObservationEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査結果ID */
    protected String observationEventId = null;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* JLAC10コード */
    protected String jLAC10 = null;
    /* 検査結果値 */
    protected String value = null;
    /* 範囲外種別CD */
    protected Integer outRangeTypeCd = null;
    /* 基準値下限 */
    protected Double minReferenceValue = null;
    /* 基準値上限 */
    protected Double maxReferenceValue = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* 疾病管理対象フラグ */
    protected Boolean diseaseManagementTargetFlg = null;
    /* 単位（表示名） */
    protected String unit = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationEntity
     */
    public static ObservationEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationEntity
     */
    public static ObservationEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.debug("Start");
        ObservationEntity entity = new ObservationEntity();
        entity.setObservationEventId(getString(dataRow, "ObservationEventId"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setJLAC10(getString(dataRow, "JLAC10"));
        entity.setValue(getString(dataRow, "Value"));
        entity.setOutRangeTypeCd(getNullInt(dataRow, "OutRangeTypeCd"));
        entity.setMinReferenceValue(getNullDouble(dataRow, "MinReferenceValue"));
        entity.setMaxReferenceValue(getNullDouble(dataRow, "MaxReferenceValue"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));
        entity.setDiseaseManagementTargetFlg(getNullBoolean(dataRow, "DiseaseManagementTargetFlg"));
        entity.setUnit(getString(dataRow, "Unit"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果ID        ：" + entity.getObservationEventId());
            logger.debug("項目ID            ：" + entity.getObservationDefinitionId());
            logger.debug("JLAC10コード      ：" + entity.getJLAC10());
            logger.debug("検査結果値        ：" + entity.getValue());
            logger.debug("範囲外種別CD      ：" + entity.getOutRangeTypeCd());
            logger.debug("基準値下限        ：" + entity.getMinReferenceValue());
            logger.debug("基準値上限        ：" + entity.getMaxReferenceValue());
            logger.debug("作成日時          ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時      ：" + entity.getUpdateDateTime());
            logger.debug("疾病管理対象フラグ：" + entity.isDiseaseManagementTargetFlg());
            logger.debug("単位（表示名）    ：" + entity.getUnit());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 検査結果IDを取得する
     *
     * @return
     */
    public String getObservationEventId() {
        return this.observationEventId;
    }
    /**
     * 検査結果IDを設定する
     *
     * @param value
     */
    public void setObservationEventId(String value) {
        this.observationEventId = value;
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
     * JLAC10コードを取得する
     *
     * @return
     */
    public String getJLAC10() {
        return this.jLAC10;
    }
    /**
     * JLAC10コードを設定する
     *
     * @param value
     */
    public void setJLAC10(String value) {
        this.jLAC10 = value;
    }

    /**
     * 検査結果値を取得する
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }
    /**
     * 検査結果値を設定する
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 範囲外種別CDを取得する
     *
     * @return
     */
    public Integer getOutRangeTypeCd() {
        return this.outRangeTypeCd;
    }
    /**
     * 範囲外種別CDを設定する
     *
     * @param value
     */
    public void setOutRangeTypeCd(Integer value) {
        this.outRangeTypeCd = value;
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

    /**
     * 疾病管理対象フラグを取得する
     *
     * @return
     */
    public Boolean isDiseaseManagementTargetFlg() {
        return this.diseaseManagementTargetFlg;
    }
    /**
     * 疾病管理対象フラグを設定する
     *
     * @param value
     */
    public void setDiseaseManagementTargetFlg(Boolean value) {
        this.diseaseManagementTargetFlg = value;
    }

    /**
     * 単位（表示名）を取得する
     *
     * @return
     */
    public String getUnit() {
        return this.unit;
    }
    /**
     * 単位（表示名）を設定する
     *
     * @param value
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "observationEventId=" + observationEventId + 
                ", observationDefinitionId=" + observationDefinitionId + 
                ", jLAC10=" + jLAC10 + 
                ", value=" + value + 
                ", outRangeTypeCd=" + outRangeTypeCd + 
                ", minReferenceValue=" + minReferenceValue + 
                ", maxReferenceValue=" + maxReferenceValue + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                ", diseaseManagementTargetFlg=" + diseaseManagementTargetFlg + 
                ", unit=" + unit + 
                " }\r\n";
    }
}
