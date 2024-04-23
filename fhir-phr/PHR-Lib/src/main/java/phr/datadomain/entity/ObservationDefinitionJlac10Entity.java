/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目JLAC10のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 管理項目JLAC10のデータオブジェクトです。
 */
public class ObservationDefinitionJlac10Entity extends ObservationDefinitionJlac10EntityBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationDefinitionJlac10Entity.class);
    private static Logger logger = Logger.getLogger(ObservationDefinitionJlac10Entity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionJlac10Entity()
    {
    }
    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* JLAC11コード */
    protected String jLAC11 = null;

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

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionJlac10Entity
     */
    public static ObservationDefinitionJlac10Entity setData(ResultSet dataRow, int i) throws Throwable {
        return setData(dataRow , true, i);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionJlac10Entity
     */
    public static ObservationDefinitionJlac10Entity setData(ResultSet dataRow, boolean isChildTable, int i) throws Throwable {
        logger.debug("Start");
        ObservationDefinitionJlac10Entity entity = new ObservationDefinitionJlac10Entity();
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        if (i == 10) {
        	entity.setJLAC10(getString(dataRow, "JLAC10"));
        }
        if (i == 11) {
        	entity.setJLAC11(getString(dataRow, "JLAC11"));
        }

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
