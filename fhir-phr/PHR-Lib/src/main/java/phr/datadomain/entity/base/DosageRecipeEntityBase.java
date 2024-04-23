/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：レシピ情報のデータオブジェクト
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
import phr.datadomain.entity.DosageRecipeEntity;

/**
 * レシピ情報のデータオブジェクトです。
 */
public abstract class DosageRecipeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageRecipeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageRecipeEntityBase()
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
    /* 用法名称 */
    protected String usageName = null;
    /* 調剤数量 */
    protected Integer dosageQuantity = null;
    /* 調剤単位 */
    protected String dosageUnit = null;
    /* 剤型コード */
    protected String dosageForms = null;
    /* 用法コード種別 */
    protected String usageCdType = null;
    /* 用法コード */
    protected String usageCd = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageRecipeEntity
     */
    public static DosageRecipeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageRecipeEntity
     */
    public static DosageRecipeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageRecipeEntity entity = new DosageRecipeEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setRecipeNo(getInt(dataRow, "RecipeNo"));
        entity.setUsageName(getString(dataRow, "UsageName"));
        entity.setDosageQuantity(getNullInt(dataRow, "DosageQuantity"));
        entity.setDosageUnit(getString(dataRow, "DosageUnit"));
        entity.setDosageForms(getString(dataRow, "DosageForms"));
        entity.setUsageCdType(getString(dataRow, "UsageCdType"));
        entity.setUsageCd(getString(dataRow, "UsageCd"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("処方番号      ：" + entity.getRecipeNo());
            logger.debug("用法名称      ：" + entity.getUsageName());
            logger.debug("調剤数量      ：" + entity.getDosageQuantity());
            logger.debug("調剤単位      ：" + entity.getDosageUnit());
            logger.debug("剤型コード    ：" + entity.getDosageForms());
            logger.debug("用法コード種別：" + entity.getUsageCdType());
            logger.debug("用法コード    ：" + entity.getUsageCd());
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
     * 用法名称を取得する
     *
     * @return
     */
    public String getUsageName() {
        return this.usageName;
    }
    /**
     * 用法名称を設定する
     *
     * @param value
     */
    public void setUsageName(String value) {
        this.usageName = value;
    }

    /**
     * 調剤数量を取得する
     *
     * @return
     */
    public Integer getDosageQuantity() {
        return this.dosageQuantity;
    }
    /**
     * 調剤数量を設定する
     *
     * @param value
     */
    public void setDosageQuantity(Integer value) {
        this.dosageQuantity = value;
    }

    /**
     * 調剤単位を取得する
     *
     * @return
     */
    public String getDosageUnit() {
        return this.dosageUnit;
    }
    /**
     * 調剤単位を設定する
     *
     * @param value
     */
    public void setDosageUnit(String value) {
        this.dosageUnit = value;
    }

    /**
     * 剤型コードを取得する
     *
     * @return
     */
    public String getDosageForms() {
        return this.dosageForms;
    }
    /**
     * 剤型コードを設定する
     *
     * @param value
     */
    public void setDosageForms(String value) {
        this.dosageForms = value;
    }

    /**
     * 用法コード種別を取得する
     *
     * @return
     */
    public String getUsageCdType() {
        return this.usageCdType;
    }
    /**
     * 用法コード種別を設定する
     *
     * @param value
     */
    public void setUsageCdType(String value) {
        this.usageCdType = value;
    }

    /**
     * 用法コードを取得する
     *
     * @return
     */
    public String getUsageCd() {
        return this.usageCd;
    }
    /**
     * 用法コードを設定する
     *
     * @param value
     */
    public void setUsageCd(String value) {
        this.usageCd = value;
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
                ", sageName=" + usageName + 
                ", osageQuantity=" + dosageQuantity + 
                ", osageUnit=" + dosageUnit + 
                ", osageForms=" + dosageForms + 
                ", sageCdType=" + usageCdType + 
                ", sageCd=" + usageCd + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
