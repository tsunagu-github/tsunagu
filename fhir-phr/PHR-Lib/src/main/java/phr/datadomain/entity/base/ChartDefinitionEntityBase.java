/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ定義のデータオブジェクト
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
import phr.datadomain.entity.ChartDefinitionEntity;

/**
 * グラフ定義のデータオブジェクトです。
 */
public abstract class ChartDefinitionEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartDefinitionEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ChartDefinitionEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* グラフ定義ID */
    protected long chartDefinitionId = 0;
    /* グラフ定義名称 */
    protected String chartDefinitionName = null;
    /* 表示回数 */
    protected int viewCount = 0;
    /* データ入力Cd */
    protected int dataInputTypeCd = 0;
    /* 保険者番号 */
    protected String insurerNo = null;
    /* PHR ID */
    protected String pHR_ID = null;
    /* 共通フラグ */
    protected int commonFlg = 0;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ChartDefinitionEntity
     */
    public static ChartDefinitionEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ChartDefinitionEntity
     */
    public static ChartDefinitionEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ChartDefinitionEntity entity = new ChartDefinitionEntity();
        entity.setChartDefinitionId(getLong(dataRow, "ChartDefinitionId"));
        entity.setChartDefinitionName(getString(dataRow, "ChartDefinitionName"));
        entity.setViewCount(getInt(dataRow, "ViewCount"));
        entity.setDataInputTypeCd(getInt(dataRow, "DataInputTypeCd"));
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setCommonFlg(getInt(dataRow, "CommonFlg"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("グラフ定義ID  ：" + entity.getChartDefinitionId());
            logger.debug("グラフ定義名称：" + entity.getChartDefinitionName());
            logger.debug("表示回数      ：" + entity.getViewCount());
            logger.debug("データ入力Cd  ：" + entity.getDataInputTypeCd());
            logger.debug("保険者番号    ：" + entity.getInsurerNo());
            logger.debug("PHR ID        ：" + entity.getPHR_ID());
            logger.debug("共通フラグ    ：" + entity.getCommonFlg());
            logger.debug("作成日時      ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時  ：" + entity.getUpdateDateTime());
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
     * グラフ定義名称を取得する
     *
     * @return
     */
    public String getChartDefinitionName() {
        return this.chartDefinitionName;
    }
    /**
     * グラフ定義名称を設定する
     *
     * @param value
     */
    public void setChartDefinitionName(String value) {
        this.chartDefinitionName = value;
    }

    /**
     * 表示回数を取得する
     *
     * @return
     */
    public int getViewCount() {
        return this.viewCount;
    }
    /**
     * 表示回数を設定する
     *
     * @param value
     */
    public void setViewCount(int value) {
        this.viewCount = value;
    }

    /**
     * データ入力Cdを取得する
     *
     * @return
     */
    public int getDataInputTypeCd() {
        return this.dataInputTypeCd;
    }
    /**
     * データ入力Cdを設定する
     *
     * @param value
     */
    public void setDataInputTypeCd(int value) {
        this.dataInputTypeCd = value;
    }

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
     * PHR IDを取得する
     *
     * @return
     */
    public String getPHR_ID() {
        return this.pHR_ID;
    }
    /**
     * PHR IDを設定する
     *
     * @param value
     */
    public void setPHR_ID(String value) {
        this.pHR_ID = value;
    }

    /**
     * 共通フラグを取得する
     *
     * @return
     */
    public int getCommonFlg() {
        return this.commonFlg;
    }
    /**
     * 共通フラグを設定する
     *
     * @param value
     */
    public void setCommonFlg(int value) {
        this.commonFlg = value;
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
                ", hartDefinitionName=" + chartDefinitionName + 
                ", iewCount=" + viewCount + 
                ", ataInputTypeCd=" + dataInputTypeCd + 
                ", nsurerNo=" + insurerNo + 
                ", HR_ID=" + pHR_ID + 
                ", ommonFlg=" + commonFlg + 
                ", reateDateTime=" + createDateTime + 
                ", pdateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
