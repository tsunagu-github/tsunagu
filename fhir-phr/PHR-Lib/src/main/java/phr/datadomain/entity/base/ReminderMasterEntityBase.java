/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダマスタのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/23
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
import phr.datadomain.entity.ReminderMasterEntity;

/**
 * リマインダマスタのデータオブジェクトです。
 */
public abstract class ReminderMasterEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderMasterEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ReminderMasterEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* リマインダ種別ID */
    protected String reminderTypeId = null;
    /* ビューID */
    protected int viewId = 0;
    /* リマインダ種別CD */
    protected int reminderTypeCd = 0;
    /* リマインダタイトル */
    protected String reminderTitle = null;
    /* リマインダ種類CD */
    protected String reminderKindCd = null;
    /* 通知フラグ */
    protected int notificationFlg = 0;
    /* 送信種別 */
    protected String sendType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderMasterEntity
     */
    public static ReminderMasterEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderMasterEntity
     */
    public static ReminderMasterEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ReminderMasterEntity entity = new ReminderMasterEntity();
        entity.setReminderTypeId(getString(dataRow, "ReminderTypeId"));
        entity.setViewId(getInt(dataRow, "ViewId"));
        entity.setReminderTypeCd(getInt(dataRow, "ReminderTypeCd"));
        entity.setReminderTitle(getString(dataRow, "ReminderTitle"));
        entity.setReminderKindCd(getString(dataRow, "ReminderKindCd"));
        entity.setNotificationFlg(getInt(dataRow, "NotificationFlg"));
        entity.setSendType(getString(dataRow, "SendType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("リマインダ種別ID  ：" + entity.getReminderTypeId());
            logger.debug("ビューID          ：" + entity.getViewId());
            logger.debug("リマインダ種別CD  ：" + entity.getReminderTypeCd());
            logger.debug("リマインダタイトル：" + entity.getReminderTitle());
            logger.debug("リマインダ種類CD  ：" + entity.getReminderKindCd());
            logger.debug("通知フラグ        ：" + entity.getNotificationFlg());
            logger.debug("送信種別          ：" + entity.getSendType());
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
     * リマインダ種別CDを取得する
     *
     * @return
     */
    public int getReminderTypeCd() {
        return this.reminderTypeCd;
    }
    /**
     * リマインダ種別CDを設定する
     *
     * @param value
     */
    public void setReminderTypeCd(int value) {
        this.reminderTypeCd = value;
    }

    /**
     * リマインダタイトルを取得する
     *
     * @return
     */
    public String getReminderTitle() {
        return this.reminderTitle;
    }
    /**
     * リマインダタイトルを設定する
     *
     * @param value
     */
    public void setReminderTitle(String value) {
        this.reminderTitle = value;
    }

    /**
     * リマインダ種類CDを取得する
     *
     * @return
     */
    public String getReminderKindCd() {
        return this.reminderKindCd;
    }
    /**
     * リマインダ種類CDを設定する
     *
     * @param value
     */
    public void setReminderKindCd(String value) {
        this.reminderKindCd = value;
    }

    /**
     * 通知フラグを取得する
     *
     * @return
     */
    public int getNotificationFlg() {
        return this.notificationFlg;
    }
    /**
     * 通知フラグを設定する
     *
     * @param value
     */
    public void setNotificationFlg(int value) {
        this.notificationFlg = value;
    }

    /**
     * 送信種別を取得する
     *
     * @return
     */
    public String getSendType() {
        return this.sendType;
    }
    /**
     * 送信種別を設定する
     *
     * @param value
     */
    public void setSendType(String value) {
        this.sendType = value;
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
                ", iewId=" + viewId + 
                ", eminderTypeCd=" + reminderTypeCd + 
                ", eminderTitle=" + reminderTitle + 
                ", eminderKindCd=" + reminderKindCd + 
                ", otificationFlg=" + notificationFlg + 
                ", endType=" + sendType + 
                " }\r\n";
    }
}
