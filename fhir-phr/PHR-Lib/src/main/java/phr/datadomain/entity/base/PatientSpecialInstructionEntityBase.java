/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者特記事項のデータオブジェクト
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
import phr.datadomain.entity.PatientSpecialInstructionEntity;

/**
 * 患者特記事項のデータオブジェクトです。
 */
public abstract class PatientSpecialInstructionEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientSpecialInstructionEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public PatientSpecialInstructionEntityBase()
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
    /* 患者特記コード */
    protected String typeCd = null;
    /* 患者特記内容 */
    protected String noteValue = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  PatientSpecialInstructionEntity
     */
    public static PatientSpecialInstructionEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  PatientSpecialInstructionEntity
     */
    public static PatientSpecialInstructionEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        PatientSpecialInstructionEntity entity = new PatientSpecialInstructionEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setTypeCd(getString(dataRow, "TypeCd"));
        entity.setNoteValue(getString(dataRow, "NoteValue"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("患者特記コード：" + entity.getTypeCd());
            logger.debug("患者特記内容  ：" + entity.getNoteValue());
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
     * 患者特記コードを取得する
     *
     * @return
     */
    public String getTypeCd() {
        return this.typeCd;
    }
    /**
     * 患者特記コードを設定する
     *
     * @param value
     */
    public void setTypeCd(String value) {
        this.typeCd = value;
    }

    /**
     * 患者特記内容を取得する
     *
     * @return
     */
    public String getNoteValue() {
        return this.noteValue;
    }
    /**
     * 患者特記内容を設定する
     *
     * @param value
     */
    public void setNoteValue(String value) {
        this.noteValue = value;
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
                ", ypeCd=" + typeCd + 
                ", oteValue=" + noteValue + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
