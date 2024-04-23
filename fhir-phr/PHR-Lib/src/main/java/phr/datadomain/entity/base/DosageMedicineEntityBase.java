/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：薬剤情報のデータオブジェクト
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
import phr.datadomain.entity.DosageMedicineEntity;

/**
 * 薬剤情報のデータオブジェクトです。
 */
public abstract class DosageMedicineEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicineEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageMedicineEntityBase()
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
    /* 薬品名称 */
    protected String medicineName = null;
    /* 用量 */
    protected String amount = null;
    /* 単位名 */
    protected String unitName = null;
    /* 薬品コード種別 */
    protected String medicineCdType = null;
    /* 薬品コード */
    protected String medicineCd = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageMedicineEntity
     */
    public static DosageMedicineEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageMedicineEntity
     */
    public static DosageMedicineEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageMedicineEntity entity = new DosageMedicineEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setRecipeNo(getInt(dataRow, "RecipeNo"));
        entity.setMedicineSeq(getInt(dataRow, "MedicineSeq"));
        entity.setMedicineName(getString(dataRow, "MedicineName"));
        entity.setAmount(getString(dataRow, "Amount"));
        entity.setUnitName(getString(dataRow, "UnitName"));
        entity.setMedicineCdType(getString(dataRow, "MedicineCdType"));
        entity.setMedicineCd(getString(dataRow, "MedicineCd"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("処方番号      ：" + entity.getRecipeNo());
            logger.debug("薬剤番号      ：" + entity.getMedicineSeq());
            logger.debug("薬品名称      ：" + entity.getMedicineName());
            logger.debug("用量          ：" + entity.getAmount());
            logger.debug("単位名        ：" + entity.getUnitName());
            logger.debug("薬品コード種別：" + entity.getMedicineCdType());
            logger.debug("薬品コード    ：" + entity.getMedicineCd());
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
     * 薬品名称を取得する
     *
     * @return
     */
    public String getMedicineName() {
        return this.medicineName;
    }
    /**
     * 薬品名称を設定する
     *
     * @param value
     */
    public void setMedicineName(String value) {
        this.medicineName = value;
    }

    /**
     * 用量を取得する
     *
     * @return
     */
    public String getAmount() {
        return this.amount;
    }
    /**
     * 用量を設定する
     *
     * @param value
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * 単位名を取得する
     *
     * @return
     */
    public String getUnitName() {
        return this.unitName;
    }
    /**
     * 単位名を設定する
     *
     * @param value
     */
    public void setUnitName(String value) {
        this.unitName = value;
    }

    /**
     * 薬品コード種別を取得する
     *
     * @return
     */
    public String getMedicineCdType() {
        return this.medicineCdType;
    }
    /**
     * 薬品コード種別を設定する
     *
     * @param value
     */
    public void setMedicineCdType(String value) {
        this.medicineCdType = value;
    }

    /**
     * 薬品コードを取得する
     *
     * @return
     */
    public String getMedicineCd() {
        return this.medicineCd;
    }
    /**
     * 薬品コードを設定する
     *
     * @param value
     */
    public void setMedicineCd(String value) {
        this.medicineCd = value;
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
                ", edicineName=" + medicineName + 
                ", mount=" + amount + 
                ", nitName=" + unitName + 
                ", edicineCdType=" + medicineCdType + 
                ", edicineCd=" + medicineCd + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
