/********************************************************************************
 * システム名      ：phr
 * コンポーネント名：残薬確認情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/28
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.UnusedDrugInfoEntity;

/**
 * 残薬確認情報のデータオブジェクトです。
 */
public abstract class UnusedDrugInfoEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(UnusedDrugInfoEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public UnusedDrugInfoEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤ID */
    protected String dosageId = null;
    /* 調剤番号 */
    protected int seq = 0;
    /* 薬品補足情報 */
    protected String unusedDrugs = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  UnusedDrugInfoEntity
     */
    public static UnusedDrugInfoEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  UnusedDrugInfoEntity
     */
    public static UnusedDrugInfoEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        UnusedDrugInfoEntity entity = new UnusedDrugInfoEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setUnusedDrugs(getString(dataRow, "UnusedDrugs"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("薬品補足情報  ：" + entity.getUnusedDrugs());
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
     * 薬品補足情報を取得する
     *
     * @return
     */
    public String getUnusedDrugs() {
        return this.unusedDrugs;
    }
    /**
     * 薬品補足情報を設定する
     *
     * @param value
     */
    public void setUnusedDrugs(String value) {
        this.unusedDrugs = value;
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
                "dosageId=" + dosageId + 
                ", seq=" + seq + 
                ", unusedDrugs=" + unusedDrugs + 
                ", recordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
