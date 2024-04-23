/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査項目結果アラート情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/25
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
import phr.datadomain.entity.ObservationAlertEntity;

/**
 * 検査項目結果アラート情報のデータオブジェクトです。
 */
public abstract class ObservationAlertEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationAlertEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationAlertEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査結果ID */
    protected String observationEventId = null;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* ビューID */
    protected int viewId = 0;
    /* アラートレベルCD */
    protected Integer alertLevelCd = null;
    /* アラートフラグ */
    protected boolean alertFlg = false;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationAlertEntity
     */
    public static ObservationAlertEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationAlertEntity
     */
    public static ObservationAlertEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationAlertEntity entity = new ObservationAlertEntity();
        entity.setObservationEventId(getString(dataRow, "ObservationEventId"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setViewId(getInt(dataRow, "ViewId"));
        entity.setAlertLevelCd(getNullInt(dataRow, "AlertLevelCd"));
        entity.setAlertFlg(getBoolean(dataRow, "AlertFlg"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果ID      ：" + entity.getObservationEventId());
            logger.debug("項目ID          ：" + entity.getObservationDefinitionId());
            logger.debug("ビューID        ：" + entity.getViewId());
            logger.debug("アラートレベルCD：" + entity.getAlertLevelCd());
            logger.debug("アラートフラグ  ：" + entity.isAlertFlg());
            logger.debug("作成日時        ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時    ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
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
     * ビューIDを取得する
     *
     * @return
     */
    public int getViewId() {
        return this.viewId;
    }
    /**
     * ビューIDを設定する
     *
     * @param value
     */
    public void setViewId(int value) {
        this.viewId = value;
    }

    /**
     * アラートレベルCDを取得する
     *
     * @return
     */
    public Integer getAlertLevelCd() {
        return this.alertLevelCd;
    }
    /**
     * アラートレベルCDを設定する
     *
     * @param value
     */
    public void setAlertLevelCd(Integer value) {
        this.alertLevelCd = value;
    }

    /**
     * アラートフラグを取得する
     *
     * @return
     */
    public boolean isAlertFlg() {
        return this.alertFlg;
    }
    /**
     * アラートフラグを設定する
     *
     * @param value
     */
    public void setAlertFlg(boolean value) {
        this.alertFlg = value;
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
                "bservationEventId=" + observationEventId + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                ", iewId=" + viewId + 
                ", lertLevelCd=" + alertLevelCd + 
                ", lertFlg=" + alertFlg + 
                ", reateDateTime=" + createDateTime + 
                ", pdateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
