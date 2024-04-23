/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目範囲情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 管理項目範囲情報のデータオブジェクトです。
 */
public class ObservationDefinitionRangeEntity extends ObservationDefinitionRangeEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionRangeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionRangeEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査名称 */
    protected String observationDefinitionName = null;
    /* 表示名称 */
    protected String displayName = null;
    /* 単位 */
    protected String unitValue = null;
    /* 基準値下限 */
    protected Double minReferenceValue = null;
    /* 基準値上限 */
    protected Double maxReferenceValue = null;
    /* 列挙ID */
    protected int enumId = 0;
    /* 列挙名称 */
    protected String enumName = null;
    /* 列挙値 */
    protected String enumValue = null;
    /* 列挙リスト */
    protected List<HashMap> enumList = null;
    /* 値 */
    protected String value = null;
    /*ソート番号*/
    protected int sortNo = 0;
    /* 疾病管理対象フラグ */
    protected boolean diseaseManagementTargetFlg;
    
    /**
     * 検査名称を取得する
     *
     * @return
     */
    public String getObservationDefinitionName() {
        return this.observationDefinitionName;
    }
    /**
     * 検査名称を設定する
     *
     * @param observationDefinitionName
     */
    public void setObservationDefinitionName(String observationDefinitionName) {
        this.observationDefinitionName = observationDefinitionName;
    }
    
    /**
     * 検査名称を取得する
     *
     * @return
     */
    public String getDisplayName() {
        return this.displayName;
    }
    /**
     * 検査名称を設定する
     *
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
     * 検査名称を設定する
     *
     * @param unitValue
     */
    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
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
     * 列挙IDを取得する
     *
     * @return
     */
    public int getEnumId() {
        return this.enumId;
    }
    /**
     * 列挙IDを設定する
     *
     * @param value
     */
    public void setEnumId(int value) {
        this.enumId = value;
    }

    /**
     * 列挙名称を取得する
     *
     * @return
     */
    public String getEnumName() {
        return this.enumName;
    }
    /**
     * 列挙名称を設定する
     *
     * @param value
     */
    public void setEnumName(String value) {
        this.enumName = value;
    }

    /**
     * 列挙値を取得する
     *
     * @return
     */
    public String getEnumValue() {
        return this.enumValue;
    }
    /**
     * 列挙値を設定する
     *
     * @param value
     */
    public void setEnumValue(String value) {
        this.enumValue = value;
    }
    
    /**
     * 列挙リストを取得する
     *
     * @return
     */
    public List<HashMap> getEnumList() {
        return this.enumList;
    }
    /**
     * 列挙リストを設定する
     *
     * @param value
     */
    public void setEnumList(List<HashMap> value) {
        this.enumList = value;
    }
    
    /**
     * 列挙リストを取得する
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }
    /**
     * 列挙リストを設定する
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
    /**
     * ソート番号を取得する
     *
     * @return
     */
    public int getSortNo() {
        return this.sortNo;
    }
    /**
     * ソート番号を設定する
     *
     * @param value
     */
    public void setSortNo(int value) {
        this.sortNo = value;
    }
    /**
     * 疾病管理対象フラグ
     * @return
     */
    public boolean isDiseaseManagementTargetFlg() {
        return diseaseManagementTargetFlg;
    }
    /**
     * 疾病管理対象フラグ
     * @param diseaseManagementTargetFlg
     */
    public void setDiseaseManagementTargetFlg(boolean diseaseManagementTargetFlg) {
        this.diseaseManagementTargetFlg = diseaseManagementTargetFlg;
    }
    
}
