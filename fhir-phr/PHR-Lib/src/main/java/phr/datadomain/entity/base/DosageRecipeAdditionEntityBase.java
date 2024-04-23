/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：用法補足情報のデータオブジェクト
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
import phr.datadomain.entity.DosageRecipeAdditionEntity;

/**
 * 用法補足情報のデータオブジェクトです。
 */
public abstract class DosageRecipeAdditionEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageRecipeAdditionEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageRecipeAdditionEntityBase()
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
    /* 用法補足番号 */
    protected int recipeAdditionSeq = 0;
    /* 細薬品補足情報 */
    protected String additionText = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageRecipeAdditionEntity
     */
    public static DosageRecipeAdditionEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageRecipeAdditionEntity
     */
    public static DosageRecipeAdditionEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageRecipeAdditionEntity entity = new DosageRecipeAdditionEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setRecipeNo(getInt(dataRow, "RecipeNo"));
        entity.setRecipeAdditionSeq(getInt(dataRow, "RecipeAdditionSeq"));
        entity.setAdditionText(getString(dataRow, "AdditionText"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("処方番号      ：" + entity.getRecipeNo());
            logger.debug("用法補足番号  ：" + entity.getRecipeAdditionSeq());
            logger.debug("細薬品補足情報：" + entity.getAdditionText());
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
     * 用法補足番号を取得する
     *
     * @return
     */
    public int getRecipeAdditionSeq() {
        return this.recipeAdditionSeq;
    }
    /**
     * 用法補足番号を設定する
     *
     * @param value
     */
    public void setRecipeAdditionSeq(int value) {
        this.recipeAdditionSeq = value;
    }

    /**
     * 細薬品補足情報を取得する
     *
     * @return
     */
    public String getAdditionText() {
        return this.additionText;
    }
    /**
     * 細薬品補足情報を設定する
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
                ", ecipeAdditionSeq=" + recipeAdditionSeq + 
                ", dditionText=" + additionText + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
