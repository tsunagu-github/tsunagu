/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：調剤情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/10/17
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
import phr.datadomain.entity.DosageEntity;

/**
 * 調剤情報のデータオブジェクトです。
 */
public abstract class DosageEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageEntityBase()
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
    /* 調剤等年月日 */
    protected Date dosageDate = null;
    /* PHRID */
    protected String pHR_ID = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static DosageEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static DosageEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageEntity entity = new DosageEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setDosageDate(getDate(dataRow, "DosageDate"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("調剤等年月日  ：" + entity.getDosageDate());
            logger.debug("PHRID         ：" + entity.getPHR_ID());
            logger.debug("レコード作成者：" + entity.getRecordCreatorType());
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
     * 調剤等年月日を取得する
     *
     * @return
     */
    public Date getDosageDate() {
        return this.dosageDate;
    }
    /**
     * 調剤等年月日を設定する
     *
     * @param value
     */
    public void setDosageDate(Date value) {
        this.dosageDate = value;
    }

    /**
     * PHRIDを取得する
     *
     * @return
     */
    public String getPHR_ID() {
        return this.pHR_ID;
    }
    /**
     * PHRIDを設定する
     *
     * @param value
     */
    public void setPHR_ID(String value) {
        this.pHR_ID = value;
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
                ", osageDate=" + dosageDate + 
                ", HR_ID=" + pHR_ID + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
