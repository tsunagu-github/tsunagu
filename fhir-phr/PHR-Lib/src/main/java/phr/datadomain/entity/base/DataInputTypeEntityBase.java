/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ入力種別のデータオブジェクト
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
import phr.datadomain.entity.DataInputTypeEntity;

/**
 * データ入力種別のデータオブジェクトです。
 */
public abstract class DataInputTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataInputTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DataInputTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* データ入力種別CD */
    protected int dataInputTypeCd = 0;
    /* 名称 */
    protected String name = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DataInputTypeEntity
     */
    public static DataInputTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DataInputTypeEntity
     */
    public static DataInputTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DataInputTypeEntity entity = new DataInputTypeEntity();
        entity.setDataInputTypeCd(getInt(dataRow, "DataInputTypeCd"));
        entity.setName(getString(dataRow, "Name"));

        if (logger.isDebugEnabled())
        {
            logger.debug("データ入力種別CD：" + entity.getDataInputTypeCd());
            logger.debug("名称            ：" + entity.getName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * データ入力種別CDを取得する
     *
     * @return
     */
    public int getDataInputTypeCd() {
        return this.dataInputTypeCd;
    }
    /**
     * データ入力種別CDを設定する
     *
     * @param value
     */
    public void setDataInputTypeCd(int value) {
        this.dataInputTypeCd = value;
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
                "ataInputTypeCd=" + dataInputTypeCd + 
                ", ame=" + name + 
                " }\r\n";
    }
}
