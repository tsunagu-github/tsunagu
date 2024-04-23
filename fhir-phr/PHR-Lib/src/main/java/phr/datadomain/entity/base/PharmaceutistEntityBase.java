/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：かかりつけ薬剤師情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import phr.datadomain.entity.PharmaceutistEntity;

/**
 * かかりつけ薬剤師情報のデータオブジェクトです。
 */
public abstract class PharmaceutistEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PharmaceutistEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public PharmaceutistEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤ID */
    protected String dosageId = null;
    /* 調剤番号 */
    protected int seq = 0;
    /* かかりつけ薬剤師氏名 */
    protected String pharmaceutistName = null;
    /* 勤務先薬局名称 */
    protected String pharmacy = null;
    /* 連絡先 */
    protected String contactAddress = null;
    /* 担当開始日 */
    protected Date startDate = null;
    /* 担当終了日 */
    protected Date endDate = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  PharmaceutistEntity
     */
    public static PharmaceutistEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  PharmaceutistEntity
     */
    public static PharmaceutistEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        PharmaceutistEntity entity = new PharmaceutistEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setPharmaceutistName(getString(dataRow, "PharmaceutistName"));
        entity.setPharmacy(getString(dataRow, "Pharmacy"));
        entity.setContactAddress(getString(dataRow, "ContactAddress"));
        entity.setStartDate(getDate(dataRow, "StartDate"));
        entity.setEndDate(getDate(dataRow, "EndDate"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID              ：" + entity.getDosageId());
            logger.debug("調剤番号            ：" + entity.getSeq());
            logger.debug("かかりつけ薬剤師氏名：" + entity.getPharmaceutistName());
            logger.debug("勤務先薬局名称      ：" + entity.getPharmacy());
            logger.debug("連絡先              ：" + entity.getContactAddress());
            logger.debug("担当開始日          ：" + entity.getStartDate());
            logger.debug("担当終了日          ：" + entity.getEndDate());
            logger.debug("レコード作成者      ：" + entity.getRecordCreatorType());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 調剤IDを取得する
     *
     * @return
     */
    public String getDosageId() {
        return this.dosageId;
    }
    /**
     * 調剤IDを設定する
     *
     * @param value
     */
    public void setDosageId(String value) {
        this.dosageId = value;
    }

    /**
     * 調剤番号を取得する
     *
     * @return
     */
    public int getSeq() {
        return this.seq;
    }
    /**
     * 調剤番号を設定する
     *
     * @param value
     */
    public void setSeq(int value) {
        this.seq = value;
    }

    /**
     * かかりつけ薬剤師氏名を取得する
     *
     * @return
     */
    public String getPharmaceutistName() {
        return this.pharmaceutistName;
    }
    /**
     * かかりつけ薬剤師氏名を設定する
     *
     * @param value
     */
    public void setPharmaceutistName(String value) {
        this.pharmaceutistName = value;
    }

    /**
     * 勤務先薬局名称を取得する
     *
     * @return
     */
    public String getPharmacy() {
        return this.pharmacy;
    }
    /**
     * 勤務先薬局名称を設定する
     *
     * @param value
     */
    public void setPharmacy(String value) {
        this.pharmacy = value;
    }

    /**
     * 連絡先を取得する
     *
     * @return
     */
    public String getContactAddress() {
        return this.contactAddress;
    }
    /**
     * 連絡先を設定する
     *
     * @param value
     */
    public void setContactAddress(String value) {
        this.contactAddress = value;
    }

    /**
     * 担当開始日を取得する
     *
     * @return
     */
    public Date getStartDate() {
        return this.startDate;
    }
    /**
     * 担当開始日を設定する
     *
     * @param value
     */
    public void setStartDate(Date value) {
        this.startDate = value;
    }

    /**
     * 担当終了日を取得する
     *
     * @return
     */
    public Date getEndDate() {
        return this.endDate;
    }
    /**
     * 担当終了日を設定する
     *
     * @param value
     */
    public void setEndDate(Date value) {
        this.endDate = value;
    }

    /**
     * レコード作成者を取得する
     *
     * @return
     */
    public String getRecordCreatorType() {
        return this.recordCreatorType;
    }
    /**
     * レコード作成者を設定する
     *
     * @param value
     */
    public void setRecordCreatorType(String value) {
        this.recordCreatorType = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "osageId=" + dosageId + 
                ", eq=" + seq + 
                ", harmaceutistName=" + pharmaceutistName + 
                ", harmacy=" + pharmacy + 
                ", ontactAddress=" + contactAddress + 
                ", tartDate=" + startDate + 
                ", ndDate=" + endDate + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
