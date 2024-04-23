/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ項目定義のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2019/05/09
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
import phr.datadomain.entity.ChartObservationDefinitionEntity;

/**
 * グラフ項目定義のデータオブジェクトです。
 */
public abstract class ChartObservationDefinitionEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartObservationDefinitionEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ChartObservationDefinitionEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* グラフ定義ID */
    protected long chartDefinitionId = 0;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* 線色 */
    protected String lineColor = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ChartObservationDefinitionEntity
     */
    public static ChartObservationDefinitionEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ChartObservationDefinitionEntity
     */
    public static ChartObservationDefinitionEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ChartObservationDefinitionEntity entity = new ChartObservationDefinitionEntity();
        entity.setChartDefinitionId(getLong(dataRow, "ChartDefinitionId"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setLineColor(getString(dataRow, "LineColor"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("グラフ定義ID：" + entity.getChartDefinitionId());
            logger.debug("項目ID      ：" + entity.getObservationDefinitionId());
            logger.debug("線色        ：" + entity.getLineColor());
            logger.debug("作成日時    ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * グラフ定義IDを取得する
     *
     * @return
     */
    public long getChartDefinitionId() {
        return this.chartDefinitionId;
    }
    /**
     * グラフ定義IDを設定する
     *
     * @param value
     */
    public void setChartDefinitionId(long value) {
        this.chartDefinitionId = value;
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
     * 線色を取得する
     *
     * @return
     */
    public String getLineColor() {
        return this.lineColor;
    }
    /**
     * 線色を設定する
     *
     * @param value
     */
    public void setLineColor(String value) {
        this.lineColor = value;
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
                "hartDefinitionId=" + chartDefinitionId + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                ", ineColor=" + lineColor + 
                ", reateDateTime=" + createDateTime + 
                ", pdateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
