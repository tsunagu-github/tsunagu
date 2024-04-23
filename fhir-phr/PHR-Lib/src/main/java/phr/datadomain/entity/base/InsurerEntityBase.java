/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者情報のデータオブジェクト
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
import phr.datadomain.entity.InsurerEntity;

/**
 * 保険者情報のデータオブジェクトです。
 */
public abstract class InsurerEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public InsurerEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 保険者名称 */
    protected String insurerName = null;
    /* 無効フラグ */
    protected boolean invalid = false;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  InsurerEntity
     */
    public static InsurerEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  InsurerEntity
     */
    public static InsurerEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        InsurerEntity entity = new InsurerEntity();
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setInsurerName(getString(dataRow, "InsurerName"));
        entity.setInvalid(getBoolean(dataRow, "Invalid"));

        if (logger.isDebugEnabled())
        {
            logger.debug("保険者番号：" + entity.getInsurerNo());
            logger.debug("保険者名称：" + entity.getInsurerName());
            logger.debug("無効フラグ：" + entity.isInvalid());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * 保険者名称を取得する
     *
     * @return
     */
    public String getInsurerName() {
        return this.insurerName;
    }
    /**
     * 保険者名称を設定する
     *
     * @param value
     */
    public void setInsurerName(String value) {
        this.insurerName = value;
    }

    /**
     * 無効フラグを取得する
     *
     * @return
     */
    public boolean isInvalid() {
        return this.invalid;
    }
    /**
     * 無効フラグを設定する
     *
     * @param value
     */
    public void setInvalid(boolean value) {
        this.invalid = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "nsurerNo=" + insurerNo + 
                ", nsurerName=" + insurerName + 
                ", nvalid=" + invalid + 
                " }\r\n";
    }
}
