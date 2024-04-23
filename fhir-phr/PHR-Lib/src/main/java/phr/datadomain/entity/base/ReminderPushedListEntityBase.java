/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダプッシュ完了リストのデータオブジェクト
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
import phr.datadomain.entity.ReminderPushedListEntity;

/**
 * リマインダプッシュ完了リストのデータオブジェクトです。
 */
public abstract class ReminderPushedListEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderPushedListEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ReminderPushedListEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* PHR ID */
    protected String pHR_ID = null;
    /* リマインダ種別ID */
    protected String reminderTypeId = null;
    /* リマインダレベル */
    protected int reminderLevel = 0;
    /* プッシュ日 */
    protected Date pushDate = null;
    /* プッシュ解禁日 */
    protected Date removeBanDate = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderPushedListEntity
     */
    public static ReminderPushedListEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderPushedListEntity
     */
    public static ReminderPushedListEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ReminderPushedListEntity entity = new ReminderPushedListEntity();
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setReminderTypeId(getString(dataRow, "ReminderTypeId"));
        entity.setReminderLevel(getInt(dataRow, "ReminderLevel"));
        entity.setPushDate(getDate(dataRow, "PushDate"));
        entity.setRemoveBanDate(getDate(dataRow, "RemoveBanDate"));

        if (logger.isDebugEnabled())
        {
            logger.debug("PHR ID          ：" + entity.getPHR_ID());
            logger.debug("リマインダ種別ID：" + entity.getReminderTypeId());
            logger.debug("リマインダレベル：" + entity.getReminderLevel());
            logger.debug("プッシュ日      ：" + entity.getPushDate());
            logger.debug("プッシュ解禁日  ：" + entity.getRemoveBanDate());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * プッシュ日を取得する
     *
     * @return
     */
    public Date getPushDate() {
        return this.pushDate;
    }
    /**
     * プッシュ日を設定する
     *
     * @param value
     */
    public void setPushDate(Date value) {
        this.pushDate = value;
    }

    /**
     * プッシュ解禁日を取得する
     *
     * @return
     */
    public Date getRemoveBanDate() {
        return this.removeBanDate;
    }
    /**
     * プッシュ解禁日を設定する
     *
     * @param value
     */
    public void setRemoveBanDate(Date value) {
        this.removeBanDate = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "HR_ID=" + pHR_ID + 
                ", eminderTypeId=" + reminderTypeId + 
                ", eminderLevel=" + reminderLevel + 
                ", ushDate=" + pushDate + 
                ", emoveBanDate=" + removeBanDate + 
                " }\r\n";
    }
}
