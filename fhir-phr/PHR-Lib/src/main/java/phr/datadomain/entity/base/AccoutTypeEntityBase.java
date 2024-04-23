/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アカウント種別のデータオブジェクト
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
import phr.datadomain.entity.AccoutTypeEntity;

/**
 * アカウント種別のデータオブジェクトです。
 */
public abstract class AccoutTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccoutTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public AccoutTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* アカウント種別CD */
    protected int accoutTypeCd = 0;
    /* 名称 */
    protected String name = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AccoutTypeEntity
     */
    public static AccoutTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AccoutTypeEntity
     */
    public static AccoutTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        AccoutTypeEntity entity = new AccoutTypeEntity();
        entity.setAccoutTypeCd(getInt(dataRow, "AccoutTypeCd"));
        entity.setName(getString(dataRow, "Name"));

        if (logger.isDebugEnabled())
        {
            logger.debug("アカウント種別CD：" + entity.getAccoutTypeCd());
            logger.debug("名称            ：" + entity.getName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * アカウント種別CDを取得する
     *
     * @return
     */
    public int getAccoutTypeCd() {
        return this.accoutTypeCd;
    }
    /**
     * アカウント種別CDを設定する
     *
     * @param value
     */
    public void setAccoutTypeCd(int value) {
        this.accoutTypeCd = value;
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
                "ccoutTypeCd=" + accoutTypeCd + 
                ", ame=" + name + 
                " }\r\n";
    }
}
