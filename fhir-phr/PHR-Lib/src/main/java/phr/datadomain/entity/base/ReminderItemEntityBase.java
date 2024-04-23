/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダ項目のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/13
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
import phr.datadomain.entity.ReminderItemEntity;

/**
 * リマインダ項目のデータオブジェクトです。
 */
public abstract class ReminderItemEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderItemEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ReminderItemEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* リマインダ種別ID */
    protected String reminderTypeId = null;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderItemEntity
     */
    public static ReminderItemEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderItemEntity
     */
    public static ReminderItemEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ReminderItemEntity entity = new ReminderItemEntity();
        entity.setReminderTypeId(getString(dataRow, "ReminderTypeId"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));

        if (logger.isDebugEnabled())
        {
            logger.debug("リマインダ種別ID：" + entity.getReminderTypeId());
            logger.debug("項目ID          ：" + entity.getObservationDefinitionId());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * リマインダ種別IDを取得する
     *
     * @return
     */
    public String getReminderTypeId() {
        return this.reminderTypeId;
    }
    /**
     * リマインダ種別IDを設定する
     *
     * @param value
     */
    public void setReminderTypeId(String value) {
        this.reminderTypeId = value;
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

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "eminderTypeId=" + reminderTypeId + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                " }\r\n";
    }
}
