/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：備考情報のデータオブジェクト
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
import phr.datadomain.entity.DosageRemarkEntity;

/**
 * 備考情報のデータオブジェクトです。
 */
public abstract class DosageRemarkEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageRemarkEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageRemarkEntityBase()
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
    /* 備考番号 */
    protected int remarkSeq = 0;
    /* 備考情報 */
    protected String remarkText = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageRemarkEntity
     */
    public static DosageRemarkEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageRemarkEntity
     */
    public static DosageRemarkEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageRemarkEntity entity = new DosageRemarkEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setRemarkSeq(getInt(dataRow, "RemarkSeq"));
        entity.setRemarkText(getString(dataRow, "RemarkText"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("備考番号      ：" + entity.getRemarkSeq());
            logger.debug("備考情報      ：" + entity.getRemarkText());
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
     * 備考番号を取得する
     *
     * @return
     */
    public int getRemarkSeq() {
        return this.remarkSeq;
    }
    /**
     * 備考番号を設定する
     *
     * @param value
     */
    public void setRemarkSeq(int value) {
        this.remarkSeq = value;
    }

    /**
     * 備考情報を取得する
     *
     * @return
     */
    public String getRemarkText() {
        return this.remarkText;
    }
    /**
     * 備考情報を設定する
     *
     * @param value
     */
    public void setRemarkText(String value) {
        this.remarkText = value;
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
                ", emarkSeq=" + remarkSeq + 
                ", emarkText=" + remarkText + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
