/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：スマートフォンアプリ対応健診（健診・問診・診察）項目のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.sql.ResultSet;
import phr.datadomain.AbstractEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.entity.PhoneCUItemEntity;


/**
 *
 * @author iwaasa
 */
public abstract class PhoneCUItemEntityBase extends AbstractEntity{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhoneCUItemEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public  PhoneCUItemEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */
    
        /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤ID */
    protected String observationDefinitionId = null;
    /* 調剤番号 */
    protected String displayName = null;
    /* PHRID */
    protected String unitValue = null;
    /* -------------------------------------------------------------------------------------- */
    
        /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static PhoneCUItemEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static PhoneCUItemEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        PhoneCUItemEntity entity = new PhoneCUItemEntity();
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setDisplayName(getString(dataRow, "DisplayName"));
        entity.setUnitValue(getString(dataRow, "UnitValue"));

        if (logger.isDebugEnabled())
        {
            logger.debug("項目ID      ：" + entity.getObservationDefinitionId());
            logger.debug("項目名    ：" + entity.getDisplayName());
            logger.debug("単位       ：" + entity.getUnitValue());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * 項目名を取得する
     *
     * @return
     */
    public String getDisplayName() {
        return this.displayName;
    }
    /**
     * 項目名を設定する
     *
     * @param value
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * 単位を取得する
     *
     * @return
     */
    public String getUnitValue() {
        return this.unitValue;
    }
    /**
     * 単位を設定する
     *
     * @param value
     */
    public void setUnitValue(String value) {
        this.unitValue = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "bservationDefinitionId=" + observationDefinitionId + 
                ", isplayName=" + displayName + 
                ", nitValue=" + unitValue + 
                " }\r\n";
    }    
}
