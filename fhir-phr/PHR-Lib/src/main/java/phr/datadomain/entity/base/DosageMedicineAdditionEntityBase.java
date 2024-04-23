/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：薬剤補足情報のデータオブジェクト
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
import phr.datadomain.entity.DosageMedicineAdditionEntity;

/**
 * 薬剤補足情報のデータオブジェクトです。
 */
public abstract class DosageMedicineAdditionEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicineAdditionEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageMedicineAdditionEntityBase()
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
    /* 処方番号 */
    protected int recipeNo = 0;
    /* 薬剤番号 */
    protected int medicineSeq = 0;
    /* 補足番号 */
    protected int additionSeq = 0;
    /* 薬品補足情報 */
    protected String additionText = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageMedicineAdditionEntity
     */
    public static DosageMedicineAdditionEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageMedicineAdditionEntity
     */
    public static DosageMedicineAdditionEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageMedicineAdditionEntity entity = new DosageMedicineAdditionEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setRecipeNo(getInt(dataRow, "RecipeNo"));
        entity.setMedicineSeq(getInt(dataRow, "MedicineSeq"));
        entity.setAdditionSeq(getInt(dataRow, "AdditionSeq"));
        entity.setAdditionText(getString(dataRow, "AdditionText"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("処方番号      ：" + entity.getRecipeNo());
            logger.debug("薬剤番号      ：" + entity.getMedicineSeq());
            logger.debug("補足番号      ：" + entity.getAdditionSeq());
            logger.debug("薬品補足情報  ：" + entity.getAdditionText());
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
     * 処方番号を取得する
     *
     * @return
     */
    public int getRecipeNo() {
        return this.recipeNo;
    }
    /**
     * 処方番号を設定する
     *
     * @param value
     */
    public void setRecipeNo(int value) {
        this.recipeNo = value;
    }

    /**
     * 薬剤番号を取得する
     *
     * @return
     */
    public int getMedicineSeq() {
        return this.medicineSeq;
    }
    /**
     * 薬剤番号を設定する
     *
     * @param value
     */
    public void setMedicineSeq(int value) {
        this.medicineSeq = value;
    }

    /**
     * 補足番号を取得する
     *
     * @return
     */
    public int getAdditionSeq() {
        return this.additionSeq;
    }
    /**
     * 補足番号を設定する
     *
     * @param value
     */
    public void setAdditionSeq(int value) {
        this.additionSeq = value;
    }

    /**
     * 薬品補足情報を取得する
     *
     * @return
     */
    public String getAdditionText() {
        return this.additionText;
    }
    /**
     * 薬品補足情報を設定する
     *
     * @param value
     */
    public void setAdditionText(String value) {
        this.additionText = value;
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
                ", ecipeNo=" + recipeNo + 
                ", edicineSeq=" + medicineSeq + 
                ", dditionSeq=" + additionSeq + 
                ", dditionText=" + additionText + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
