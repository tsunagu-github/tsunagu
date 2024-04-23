/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目列挙値情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/16
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
import phr.datadomain.entity.ObservationDefinitionEnumValueEntity;

/**
 * 管理項目列挙値情報のデータオブジェクトです。
 */
public abstract class ObservationDefinitionEnumValueEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionEnumValueEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionEnumValueEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* ViewID */
    protected int viewId = 0;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* 列挙ID */
    protected int enumId = 0;
    /* 列挙名称 */
    protected String enumName = null;
    /* 列挙値 */
    protected String enumValue = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionEnumValueEntity
     */
    public static ObservationDefinitionEnumValueEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionEnumValueEntity
     */
    public static ObservationDefinitionEnumValueEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationDefinitionEnumValueEntity entity = new ObservationDefinitionEnumValueEntity();
        entity.setViewId(getInt(dataRow, "ViewId"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setEnumId(getInt(dataRow, "EnumId"));
        entity.setEnumName(getString(dataRow, "EnumName"));
        entity.setEnumValue(getString(dataRow, "EnumValue"));

        if (logger.isDebugEnabled())
        {
            logger.debug("ViewID  ：" + entity.getViewId());
            logger.debug("項目ID  ：" + entity.getObservationDefinitionId());
            logger.debug("列挙ID  ：" + entity.getEnumId());
            logger.debug("列挙名称：" + entity.getEnumName());
            logger.debug("列挙値  ：" + entity.getEnumValue());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * ViewIDを取得する
     *
     * @return
     */
    public int getViewId() {
        return this.viewId;
    }
    /**
     * ViewIDを設定する
     *
     * @param value
     */
    public void setViewId(int value) {
        this.viewId = value;
    }

    /**
     * 項目IDを取得する
     *
     * @return
     */
    public String getObservationDefinitionId() {
        return this.observationDefinitionId;
    }
    /**
     * 項目IDを設定する
     *
     * @param value
     */
    public void setObservationDefinitionId(String value) {
        this.observationDefinitionId = value;
    }

    /**
     * 列挙IDを取得する
     *
     * @return
     */
    public int getEnumId() {
        return this.enumId;
    }
    /**
     * 列挙IDを設定する
     *
     * @param value
     */
    public void setEnumId(int value) {
        this.enumId = value;
    }

    /**
     * 列挙名称を取得する
     *
     * @return
     */
    public String getEnumName() {
        return this.enumName;
    }
    /**
     * 列挙名称を設定する
     *
     * @param value
     */
    public void setEnumName(String value) {
        this.enumName = value;
    }

    /**
     * 列挙値を取得する
     *
     * @return
     */
    public String getEnumValue() {
        return this.enumValue;
    }
    /**
     * 列挙値を設定する
     *
     * @param value
     */
    public void setEnumValue(String value) {
        this.enumValue = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "iewId=" + viewId + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                ", numId=" + enumId + 
                ", numName=" + enumName + 
                ", numValue=" + enumValue + 
                " }\r\n";
    }
}
