/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者等記入情報のデータオブジェクト
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
import phr.datadomain.entity.DosagePatientInputEntity;

/**
 * 患者等記入情報のデータオブジェクトです。
 */
public abstract class DosagePatientInputEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosagePatientInputEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosagePatientInputEntityBase()
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
    /* 患者記入番号 */
    protected int inputSeq = 0;
    /* 患者記入情報 */
    protected String inputText = null;
    /* 入力年月日 */
    protected Date inputDate = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosagePatientInputEntity
     */
    public static DosagePatientInputEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosagePatientInputEntity
     */
    public static DosagePatientInputEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosagePatientInputEntity entity = new DosagePatientInputEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setInputSeq(getInt(dataRow, "InputSeq"));
        entity.setInputText(getString(dataRow, "InputText"));
        entity.setInputDate(getDate(dataRow, "InputDate"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID      ：" + entity.getDosageId());
            logger.debug("調剤番号    ：" + entity.getSeq());
            logger.debug("患者記入番号：" + entity.getInputSeq());
            logger.debug("患者記入情報：" + entity.getInputText());
            logger.debug("入力年月日  ：" + entity.getInputDate());
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
     * 患者記入番号を取得する
     *
     * @return
     */
    public int getInputSeq() {
        return this.inputSeq;
    }
    /**
     * 患者記入番号を設定する
     *
     * @param value
     */
    public void setInputSeq(int value) {
        this.inputSeq = value;
    }

    /**
     * 患者記入情報を取得する
     *
     * @return
     */
    public String getInputText() {
        return this.inputText;
    }
    /**
     * 患者記入情報を設定する
     *
     * @param value
     */
    public void setInputText(String value) {
        this.inputText = value;
    }

    /**
     * 入力年月日を取得する
     *
     * @return
     */
    public Date getInputDate() {
        return this.inputDate;
    }
    /**
     * 入力年月日を設定する
     *
     * @param value
     */
    public void setInputDate(Date value) {
        this.inputDate = value;
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
                ", nputSeq=" + inputSeq + 
                ", nputText=" + inputText + 
                ", nputDate=" + inputDate + 
                " }\r\n";
    }
}
