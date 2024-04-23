/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：範囲外種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
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
import phr.datadomain.entity.OutRangeTypeEntity;

/**
 * 範囲外種別のデータオブジェクトです。
 */
public abstract class OutRangeTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OutRangeTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public OutRangeTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 範囲外種別CD */
    protected int outRangeTypeCd = 0;
    /* 名称 */
    protected String name = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  OutRangeTypeEntity
     */
    public static OutRangeTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  OutRangeTypeEntity
     */
    public static OutRangeTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        OutRangeTypeEntity entity = new OutRangeTypeEntity();
        entity.setOutRangeTypeCd(getInt(dataRow, "OutRangeTypeCd"));
        entity.setName(getString(dataRow, "Name"));

        if (logger.isDebugEnabled())
        {
            logger.debug("範囲外種別CD：" + entity.getOutRangeTypeCd());
            logger.debug("名称        ：" + entity.getName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 範囲外種別CDを取得する
     *
     * @return
     */
    public int getOutRangeTypeCd() {
        return this.outRangeTypeCd;
    }
    /**
     * 範囲外種別CDを設定する
     *
     * @param value
     */
    public void setOutRangeTypeCd(int value) {
        this.outRangeTypeCd = value;
    }

    /**
     * 名称を取得する
     *
     * @return
     */
    public String getName() {
        return this.name;
    }
    /**
     * 名称を設定する
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "utRangeTypeCd=" + outRangeTypeCd + 
                ", ame=" + name + 
                " }\r\n";
    }
}
