/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ色情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2019/05/16
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
import phr.datadomain.entity.ChartColorEntity;

/**
 * グラフ色情報のデータオブジェクトです。
 */
public abstract class ChartColorEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartColorEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ChartColorEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 色ID */
    protected String colorId = null;
    /* 色CD */
    protected String colorCd = null;
    /* 色名 */
    protected String colorName = null;
    /* RGB */
    protected String rGB = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ChartColorEntity
     */
    public static ChartColorEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ChartColorEntity
     */
    public static ChartColorEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ChartColorEntity entity = new ChartColorEntity();
        entity.setColorId(getString(dataRow, "ColorId"));
        entity.setColorCd(getString(dataRow, "ColorCd"));
        entity.setColorName(getString(dataRow, "ColorName"));
        entity.setRGB(getString(dataRow, "RGB"));

        if (logger.isDebugEnabled())
        {
            logger.debug("色ID：" + entity.getColorId());
            logger.debug("色CD：" + entity.getColorCd());
            logger.debug("色名：" + entity.getColorName());
            logger.debug("RGB ：" + entity.getRGB());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 色IDを取得する
     *
     * @return
     */
    public String getColorId() {
        return this.colorId;
    }
    /**
     * 色IDを設定する
     *
     * @param value
     */
    public void setColorId(String value) {
        this.colorId = value;
    }

    /**
     * 色CDを取得する
     *
     * @return
     */
    public String getColorCd() {
        return this.colorCd;
    }
    /**
     * 色CDを設定する
     *
     * @param value
     */
    public void setColorCd(String value) {
        this.colorCd = value;
    }

    /**
     * 色名を取得する
     *
     * @return
     */
    public String getColorName() {
        return this.colorName;
    }
    /**
     * 色名を設定する
     *
     * @param value
     */
    public void setColorName(String value) {
        this.colorName = value;
    }

    /**
     * RGBを取得する
     *
     * @return
     */
    public String getRGB() {
        return this.rGB;
    }
    /**
     * RGBを設定する
     *
     * @param value
     */
    public void setRGB(String value) {
        this.rGB = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "olorId=" + colorId + 
                ", olorCd=" + colorCd + 
                ", olorName=" + colorName + 
                ", GB=" + rGB + 
                " }\r\n";
    }
}
