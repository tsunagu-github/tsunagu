/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：範囲種別のデータオブジェクト
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
import phr.datadomain.entity.RangeTypeEntity;

/**
 * 範囲種別のデータオブジェクトです。
 */
public abstract class RangeTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RangeTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public RangeTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 範囲種別CD */
    protected int rangeTypeCd = 0;
    /* 名称 */
    protected String name = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  RangeTypeEntity
     */
    public static RangeTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  RangeTypeEntity
     */
    public static RangeTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        RangeTypeEntity entity = new RangeTypeEntity();
        entity.setRangeTypeCd(getInt(dataRow, "RangeTypeCd"));
        entity.setName(getString(dataRow, "Name"));

        if (logger.isDebugEnabled())
        {
            logger.debug("範囲種別CD：" + entity.getRangeTypeCd());
            logger.debug("名称      ：" + entity.getName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 範囲種別CDを取得する
     *
     * @return
     */
    public int getRangeTypeCd() {
        return this.rangeTypeCd;
    }
    /**
     * 範囲種別CDを設定する
     *
     * @param value
     */
    public void setRangeTypeCd(int value) {
        this.rangeTypeCd = value;
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
                "angeTypeCd=" + rangeTypeCd + 
                ", ame=" + name + 
                " }\r\n";
    }
}
