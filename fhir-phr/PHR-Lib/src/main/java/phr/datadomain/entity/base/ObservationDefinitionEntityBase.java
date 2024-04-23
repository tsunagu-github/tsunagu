/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/16
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
import phr.datadomain.entity.ObservationDefinitionEntity;

/**
 * 管理項目情報のデータオブジェクトです。
 */
public abstract class ObservationDefinitionEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* 項目名称 */
    protected String observationDefinitionName = null;
    /* 表示名 */
    protected String displayName = null;
    /* 順序 */
    protected int sortNo = 0;
    /* 入力最小値 */
    protected Double minInput = null;
    /* 入力最大値 */
    protected Double maxInput = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionEntity
     */
    public static ObservationDefinitionEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionEntity
     */
    public static ObservationDefinitionEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationDefinitionEntity entity = new ObservationDefinitionEntity();
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setObservationDefinitionName(getString(dataRow, "ObservationDefinitionName"));
        entity.setDisplayName(getString(dataRow, "DisplayName"));
        entity.setSortNo(getInt(dataRow, "SortNo"));
        entity.setMinInput(getNullDouble(dataRow, "MinInput"));
        entity.setMaxInput(getNullDouble(dataRow, "MaxInput"));

        if (logger.isDebugEnabled())
        {
            logger.debug("項目ID    ：" + entity.getObservationDefinitionId());
            logger.debug("項目名称  ：" + entity.getObservationDefinitionName());
            logger.debug("表示名    ：" + entity.getDisplayName());
            logger.debug("順序      ：" + entity.getSortNo());
            logger.debug("入力最小値：" + entity.getMinInput());
            logger.debug("入力最大値：" + entity.getMaxInput());
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
     * 表示名を取得する
     *
     * @return
     */
    public String getDisplayName() {
        return this.displayName;
    }
    /**
     * 表示名を設定する
     *
     * @param value
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * 順序を取得する
     *
     * @return
     */
    public int getSortNo() {
        return this.sortNo;
    }
    /**
     * 順序を設定する
     *
     * @param value
     */
    public void setSortNo(int value) {
        this.sortNo = value;
    }

    /**
     * 入力最小値を取得する
     *
     * @return
     */
    public Double getMinInput() {
        return this.minInput;
    }
    /**
     * 入力最小値を設定する
     *
     * @param value
     */
    public void setMinInput(Double value) {
        this.minInput = value;
    }

    /**
     * 入力最大値を取得する
     *
     * @return
     */
    public Double getMaxInput() {
        return this.maxInput;
    }
    /**
     * 入力最大値を設定する
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
                "bservationDefinitionId=" + observationDefinitionId + 
                ", bservationDefinitionName=" + observationDefinitionName + 
                ", isplayName=" + displayName + 
                ", ortNo=" + sortNo + 
                ", inInput=" + minInput + 
                ", axInput=" + maxInput + 
                " }\r\n";
    }
}
