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
import phr.datadomain.entity.SeparatorInfoEntity;

/**
 * 用法補足情報のデータオブジェクトです。
 */
public abstract class SeparatorInfoEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SeparatorInfoEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public SeparatorInfoEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 分割ID */
    protected long separatorId = 0;
    /* 調剤ID */
    protected String dosageId = null;
    /* データ連番 */
    protected int separateNo = 0;
    /* 分割数 */
    protected int separateCount = 0;
    /* 本文テキスト */
    private String separatText = null;

    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  SeparatorInfoEntity
     */
    public static SeparatorInfoEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  SeparatorInfoEntity
     */
    public static SeparatorInfoEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        SeparatorInfoEntity entity = new SeparatorInfoEntity();
        entity.setSeparatorId(getLong(dataRow, "SeparatorId"));
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeparateNo(getInt(dataRow, "SeparateNo"));
        entity.setSeparateCount(getInt(dataRow, "SeparateCount"));
        entity.setSeparatText(getString(dataRow, "SeparatText"));


        if (logger.isDebugEnabled())
        {
            logger.debug("分割ID              ：" + entity.getSeparatorId());
            logger.debug("調剤ID              ：" + entity.getDosageId());
            logger.debug("データ連番          ：" + entity.getSeparateNo());
            logger.debug("分割数              ：" + entity.getSeparateCount());
            logger.debug("本文テキスト　　    ：" + entity.getSeparatText());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 分割IDを取得する
     *
     * @return
     */
    public long getSeparatorId() {
        return this.separatorId;
    }
    /**
     * 分割IDを設定する
     *
     * @param value
     */
    public void setSeparatorId(long value) {
        this.separatorId = value;
    }

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
     * データ連番を取得する
     *
     * @return
     */
    public int getSeparateNo() {
        return this.separateNo;
    }
    /**
     * データ連番を設定する
     *
     * @param value
     */
    public void setSeparateNo(int value) {
        this.separateNo = value;
    }

    /**
     * 分割数を取得する
     *
     * @return
     */
    public int getSeparateCount() {
        return this.separateCount;
    }
    /**
     * 分割数を設定する
     *
     * @param value
     */
    public void setSeparateCount(int value) {
        this.separateCount = value;
    }
    /**
     * @return the separatText
     */
    public String getSeparatText() {
        return separatText;
    }

    /**
     * @param separatText the separatText to set
     */
    public void setSeparatText(String separatText) {
        this.separatText = separatText;
    }


    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "eparatorId=" + separatorId + 
                ", osageId=" + dosageId + 
                ", eparateNo=" + separateNo + 
                ", eparateCount=" + separateCount +
                ", eparatText=" + getSeparatText() +
                " }\r\n";
    }


}
