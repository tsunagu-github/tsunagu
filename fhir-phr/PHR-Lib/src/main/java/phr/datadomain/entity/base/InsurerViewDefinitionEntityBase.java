/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者View定義情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/16
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.sql.ResultSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;

/**
 * 保険者View定義情報のデータオブジェクトです。
 */
public abstract class InsurerViewDefinitionEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerViewDefinitionEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public InsurerViewDefinitionEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* ViewID */
    protected int viewId = 0;
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 開始年 */
    protected int startYear = 0;
    /* 終了年 */
    protected int endYear = 0;
    /* View名称 */
    protected String viewName = null;
    /* 略称 */
    protected String shortName = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  InsurerViewDefinitionEntity
     */
    public static InsurerViewDefinitionEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  InsurerViewDefinitionEntity
     */
    public static InsurerViewDefinitionEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        InsurerViewDefinitionEntity entity = new InsurerViewDefinitionEntity();
        entity.setViewId(getInt(dataRow, "ViewId"));
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setStartYear(getInt(dataRow, "StartYear"));
        entity.setEndYear(getInt(dataRow, "EndYear"));
        entity.setViewName(getString(dataRow, "ViewName"));
        entity.setShortName(getString(dataRow, "ShortName"));

        if (logger.isDebugEnabled())
        {
            logger.debug("ViewID    ：" + entity.getViewId());
            logger.debug("保険者番号：" + entity.getInsurerNo());
            logger.debug("開始年    ：" + entity.getStartYear());
            logger.debug("終了年    ：" + entity.getEndYear());
            logger.debug("View名称  ：" + entity.getViewName());
            logger.debug("略称      ：" + entity.getShortName());
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
     * 保険者番号を取得する
     *
     * @return
     */
    public String getInsurerNo() {
        return this.insurerNo;
    }
    /**
     * 保険者番号を設定する
     *
     * @param value
     */
    public void setInsurerNo(String value) {
        this.insurerNo = value;
    }

    /**
     * 開始年を取得する
     *
     * @return
     */
    public int getStartYear() {
        return this.startYear;
    }
    /**
     * 開始年を設定する
     *
     * @param value
     */
    public void setStartYear(int value) {
        this.startYear = value;
    }

    /**
     * 終了年を取得する
     *
     * @return
     */
    public int getEndYear() {
        return this.endYear;
    }
    /**
     * 終了年を設定する
     *
     * @param value
     */
    public void setEndYear(int value) {
        this.endYear = value;
    }

    /**
     * View名称を取得する
     *
     * @return
     */
    public String getViewName() {
        return this.viewName;
    }
    /**
     * View名称を設定する
     *
     * @param value
     */
    public void setViewName(String value) {
        this.viewName = value;
    }

    /**
     * 略称を取得する
     *
     * @return
     */
    public String getShortName() {
        return this.shortName;
    }
    /**
     * 略称を設定する
     *
     * @param value
     */
    public void setShortName(String value) {
        this.shortName = value;
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
                ", nsurerNo=" + insurerNo + 
                ", tartYear=" + startYear + 
                ", ndYear=" + endYear + 
                ", iewName=" + viewName + 
                ", hortName=" + shortName + 
                " }\r\n";
    }
}
