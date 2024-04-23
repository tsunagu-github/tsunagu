/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダメッセージのデータオブジェクト
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
import phr.datadomain.entity.ReminderMessageEntity;

/**
 * リマインダメッセージのデータオブジェクトです。
 */
public abstract class ReminderMessageEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderMessageEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ReminderMessageEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* リマインダ種別ID */
    protected String reminderTypeId = null;
    /* リマインダレベル */
    protected int reminderLevel = 0;
    /* 送信メッセージ */
    protected String sendMessage = null;
    /* タイトル */
    protected String title = null;
    /* 送信期間 */
    protected Integer sendPeriod = null;
    /* 送信月 */
    protected Integer sendMonth = null;
    /* 通知制限日数 */
    protected int removeBanDays = 0;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderMessageEntity
     */
    public static ReminderMessageEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderMessageEntity
     */
    public static ReminderMessageEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ReminderMessageEntity entity = new ReminderMessageEntity();
        entity.setReminderTypeId(getString(dataRow, "ReminderTypeId"));
        entity.setReminderLevel(getInt(dataRow, "ReminderLevel"));
        entity.setSendMessage(getString(dataRow, "SendMessage"));
        entity.setTitle(getString(dataRow, "Title"));
        entity.setSendPeriod(getNullInt(dataRow, "SendPeriod"));
        entity.setSendMonth(getNullInt(dataRow, "SendMonth"));
        entity.setRemoveBanDays(getInt(dataRow, "RemoveBanDays"));

        if (logger.isDebugEnabled())
        {
            logger.debug("リマインダ種別ID：" + entity.getReminderTypeId());
            logger.debug("リマインダレベル：" + entity.getReminderLevel());
            logger.debug("送信メッセージ  ：" + entity.getSendMessage());
            logger.debug("タイトル        ：" + entity.getTitle());
            logger.debug("送信期間        ：" + entity.getSendPeriod());
            logger.debug("送信月          ：" + entity.getSendMonth());
            logger.debug("通知制限日数    ：" + entity.getRemoveBanDays());
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
     * リマインダレベルを取得する
     *
     * @return
     */
    public int getReminderLevel() {
        return this.reminderLevel;
    }
    /**
     * リマインダレベルを設定する
     *
     * @param value
     */
    public void setReminderLevel(int value) {
        this.reminderLevel = value;
    }

    /**
     * 送信メッセージを取得する
     *
     * @return
     */
    public String getSendMessage() {
        return this.sendMessage;
    }
    /**
     * 送信メッセージを設定する
     *
     * @param value
     */
    public void setSendMessage(String value) {
        this.sendMessage = value;
    }

    /**
     * タイトルを取得する
     *
     * @return
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * タイトルを設定する
     *
     * @param value
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * 送信期間を取得する
     *
     * @return
     */
    public Integer getSendPeriod() {
        return this.sendPeriod;
    }
    /**
     * 送信期間を設定する
     *
     * @param value
     */
    public void setSendPeriod(Integer value) {
        this.sendPeriod = value;
    }

    /**
     * 送信月を取得する
     *
     * @return
     */
    public Integer getSendMonth() {
        return this.sendMonth;
    }
    /**
     * 送信月を設定する
     *
     * @param value
     */
    public void setSendMonth(Integer value) {
        this.sendMonth = value;
    }

    /**
     * 通知制限日数を取得する
     *
     * @return
     */
    public int getRemoveBanDays() {
        return this.removeBanDays;
    }
    /**
     * 通知制限日数を設定する
     *
     * @param value
     */
    public void setRemoveBanDays(int value) {
        this.removeBanDays = value;
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
                ", eminderLevel=" + reminderLevel + 
                ", endMessage=" + sendMessage + 
                ", itle=" + title + 
                ", endPeriod=" + sendPeriod + 
                ", endMonth=" + sendMonth + 
                ", emoveBanDays=" + removeBanDays + 
                " }\r\n";
    }
}
