/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：一般用医薬品のデータオブジェクト
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
import phr.datadomain.entity.NonPrescriptionDrugsEntity;

/**
 * 一般用医薬品のデータオブジェクトです。
 */
public abstract class NonPrescriptionDrugsEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NonPrescriptionDrugsEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public NonPrescriptionDrugsEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤ID */
    protected String dosageId = null;
    /*市販薬番号 */
    protected int seq = 0;
    /* 薬品名症 */
    protected String medicineName = null;
    /* 服用開始年月日 */
    protected Date startDate = null;
    /* 服用終了年月日 */
    protected Date endDate = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  NonPrescriptionDrugsEntity
     */
    public static NonPrescriptionDrugsEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  NonPrescriptionDrugsEntity
     */
    public static NonPrescriptionDrugsEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        NonPrescriptionDrugsEntity entity = new NonPrescriptionDrugsEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setMedicineName(getString(dataRow, "MedicineName"));
        entity.setStartDate(getDate(dataRow, "StartDate"));
        entity.setEndDate(getDate(dataRow, "EndDate"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("市販薬番号      ：" + entity.getSeq());
            logger.debug("薬品名症      ：" + entity.getMedicineName());
            logger.debug("服用開始年月日：" + entity.getStartDate());
            logger.debug("服用終了年月日：" + entity.getEndDate());
            logger.debug("レコード作成者：" + entity.getRecordCreatorType());
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
     * 薬品名症を取得する
     *
     * @return
     */
    public String getMedicineName() {
        return this.medicineName;
    }
    /**
     * 薬品名症を設定する
     *
     * @param value
     */
    public void setMedicineName(String value) {
        this.medicineName = value;
    }

    /**
     * 服用開始年月日を取得する
     *
     * @return
     */
    public Date getStartDate() {
        return this.startDate;
    }
    /**
     * 服用開始年月日を設定する
     *
     * @param value
     */
    public void setStartDate(Date value) {
        this.startDate = value;
    }

    /**
     * 服用終了年月日を取得する
     *
     * @return
     */
    public Date getEndDate() {
        return this.endDate;
    }
    /**
     * 服用終了年月日を設定する
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
                ", edicineName=" + medicineName + 
                ", tartDate=" + startDate + 
                ", ndDate=" + endDate + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
