/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関等提供情報のデータオブジェクト
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
import phr.datadomain.entity.DosageOrganProvisionInfoEntity;

/**
 * 医療機関等提供情報のデータオブジェクトです。
 */
public abstract class DosageOrganProvisionInfoEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageOrganProvisionInfoEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageOrganProvisionInfoEntityBase()
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
    /* 提供情報番号 */
    protected int provisionSeq = 0;
    /* 提供情報内容 */
    protected String provisionText = null;
    /* 提供情報タイプ */
    protected String provisionType = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageOrganProvisionInfoEntity
     */
    public static DosageOrganProvisionInfoEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageOrganProvisionInfoEntity
     */
    public static DosageOrganProvisionInfoEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageOrganProvisionInfoEntity entity = new DosageOrganProvisionInfoEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setProvisionSeq(getInt(dataRow, "ProvisionSeq"));
        entity.setProvisionText(getString(dataRow, "ProvisionText"));
        entity.setProvisionType(getString(dataRow, "ProvisionType"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("調剤番号      ：" + entity.getSeq());
            logger.debug("提供情報番号  ：" + entity.getProvisionSeq());
            logger.debug("提供情報内容  ：" + entity.getProvisionText());
            logger.debug("提供情報タイプ：" + entity.getProvisionType());
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
     * 提供情報番号を取得する
     *
     * @return
     */
    public int getProvisionSeq() {
        return this.provisionSeq;
    }
    /**
     * 提供情報番号を設定する
     *
     * @param value
     */
    public void setProvisionSeq(int value) {
        this.provisionSeq = value;
    }

    /**
     * 提供情報内容を取得する
     *
     * @return
     */
    public String getProvisionText() {
        return this.provisionText;
    }
    /**
     * 提供情報内容を設定する
     *
     * @param value
     */
    public void setProvisionText(String value) {
        this.provisionText = value;
    }

    /**
     * 提供情報タイプを取得する
     *
     * @return
     */
    public String getProvisionType() {
        return this.provisionType;
    }
    /**
     * 提供情報タイプを設定する
     *
     * @param value
     */
    public void setProvisionType(String value) {
        this.provisionType = value;
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
                ", rovisionSeq=" + provisionSeq + 
                ", rovisionText=" + provisionText + 
                ", rovisionType=" + provisionType + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
