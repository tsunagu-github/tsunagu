/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：活用同意一覧情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/04/13
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.Seq_UtilizationConsentIdEntity;

/**
 * 活用同意一覧 IDのデータオブジェクトです。
 */
public abstract class Seq_UtilizationConsentIdEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(Seq_UtilizationConsentIdEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public Seq_UtilizationConsentIdEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査結果取得履歴 ID */
    protected long utilizationConsentId = 0;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  Seq_UtilizationConsentIdEntity
     */
    public static Seq_UtilizationConsentIdEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  Seq_UtilizationConsentIdEntity
     */
    public static Seq_UtilizationConsentIdEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        Seq_UtilizationConsentIdEntity entity = new Seq_UtilizationConsentIdEntity();
        entity.setUtilizationConsentId(getLong(dataRow, "UtilizationConsentId"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果取得履歴 ID：" + entity.getUtilizationConsentId());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 検査結果取得履歴 IDを取得する
     *
     * @return
     */
    public long getUtilizationConsentId() {
        return this.utilizationConsentId;
    }
    /**
     * 検査結果取得履歴 IDを設定する
     *
     * @param value
     */
    public void setUtilizationConsentId(long value) {
        this.utilizationConsentId = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "utilizationConsentId=" + utilizationConsentId + 
                " }\r\n";
    }
}
