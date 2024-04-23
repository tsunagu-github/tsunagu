/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダ種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
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
import phr.datadomain.entity.ReminderTypeEntity;

/**
 * リマインダ種別のデータオブジェクトです。
 */
public abstract class ReminderTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ReminderTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* リマインダ種別CD */
    protected int reminderTypeCd = 0;
    /* 名称 */
    protected String name = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderTypeEntity
     */
    public static ReminderTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderTypeEntity
     */
    public static ReminderTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ReminderTypeEntity entity = new ReminderTypeEntity();
        entity.setReminderTypeCd(getInt(dataRow, "ReminderTypeCd"));
        entity.setName(getString(dataRow, "Name"));

        if (logger.isDebugEnabled())
        {
            logger.debug("リマインダ種別CD：" + entity.getReminderTypeCd());
            logger.debug("名称            ：" + entity.getName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * 名称を取得する
     *
     * @return
     */
    public String getName() {
        return this.name;
    }
    /**
     * 名称を設定する
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "eminderTypeCd=" + reminderTypeCd + 
                ", ame=" + name + 
                " }\r\n";
    }
}
