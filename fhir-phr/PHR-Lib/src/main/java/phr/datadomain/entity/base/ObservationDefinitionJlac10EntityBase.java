/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目JLAC10のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/10/06
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
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.ObservationDefinitionJlac10Entity;

/**
 * 管理項目JLAC10のデータオブジェクトです。
 */
public abstract class ObservationDefinitionJlac10EntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationDefinitionJlac10EntityBase.class);
    private static Logger logger = Logger.getLogger(ObservationDefinitionJlac10EntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionJlac10EntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* JLAC10コード */
    protected String jLAC10 = null;
    /* JLAC11コード */
    protected String jLAC11 = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionJlac10Entity
     */
    public static ObservationDefinitionJlac10Entity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow, true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionJlac10Entity
     */
    public static ObservationDefinitionJlac10Entity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.debug("Start");
        ObservationDefinitionJlac10Entity entity = new ObservationDefinitionJlac10Entity();
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setJLAC10(getString(dataRow, "JLAC10"));
        entity.setJLAC11(getString(dataRow, "JLAC11"));

        if (logger.isDebugEnabled())
        {
            logger.debug("項目ID      ：" + entity.getObservationDefinitionId());
            logger.debug("JLAC10コード：" + entity.getJLAC10());
            logger.debug("JLAC11コード：" + entity.getJLAC11());
        }
        logger.debug("End");
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
     * JLAC10コードを取得する
     *
     * @return
     */
    public String getJLAC10() {
        return this.jLAC10;
    }
    /**
     * JLAC10コードを設定する
     *
     * @param value
     */
    public void setJLAC10(String value) {
        this.jLAC10 = value;
    }

    /**
     * JLAC11コードを取得する
     *
     * @return
     */
    public String getJLAC11() {
        return this.jLAC11;
    }
    /**
     * JLAC11コードを設定する
     *
     * @param value
     */
    public void setJLAC11(String value) {
        this.jLAC11 = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "observationDefinitionId=" + observationDefinitionId + 
                ", jLAC10=" + jLAC10 + 
                ", jLAC11=" + jLAC11 + 
                " }\r\n";
    }
}
