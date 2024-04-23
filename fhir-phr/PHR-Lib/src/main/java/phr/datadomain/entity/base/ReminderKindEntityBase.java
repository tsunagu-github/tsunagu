/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダ種類のデータオブジェクト
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
import phr.datadomain.entity.ReminderKindEntity;

/**
 * リマインダ種類のデータオブジェクトです。
 */
public abstract class ReminderKindEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderKindEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ReminderKindEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* リマインダ種別ID */
    protected String reminderTypeId = null;
    /* 疾病種別CD */
    protected int diseaseTypeCd = 0;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderKindEntity
     */
    public static ReminderKindEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ReminderKindEntity
     */
    public static ReminderKindEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ReminderKindEntity entity = new ReminderKindEntity();
        entity.setReminderTypeId(getString(dataRow, "ReminderTypeId"));
        entity.setDiseaseTypeCd(getInt(dataRow, "DiseaseTypeCd"));

        if (logger.isDebugEnabled())
        {
            logger.debug("リマインダ種別ID：" + entity.getReminderTypeId());
            logger.debug("疾病種別CD      ：" + entity.getDiseaseTypeCd());
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
     * 疾病種別CDを取得する
     *
     * @return
     */
    public int getDiseaseTypeCd() {
        return this.diseaseTypeCd;
    }
    /**
     * 疾病種別CDを設定する
     *
     * @param value
     */
    public void setDiseaseTypeCd(int value) {
        this.diseaseTypeCd = value;
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
                ", iseaseTypeCd=" + diseaseTypeCd + 
                " }\r\n";
    }
}
