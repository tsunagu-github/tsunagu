/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果ID管理のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import phr.datadomain.entity.Seq_ObservationEventIDEntity;

/**
 * 検査結果ID管理のデータオブジェクトです。
 */
public abstract class Seq_ObservationEventIDEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(Seq_ObservationEventIDEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public Seq_ObservationEventIDEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査結果ID */
    protected int observationEventId = 0;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  Seq_ObservationEventIDEntity
     */
    public static Seq_ObservationEventIDEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  Seq_ObservationEventIDEntity
     */
    public static Seq_ObservationEventIDEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        Seq_ObservationEventIDEntity entity = new Seq_ObservationEventIDEntity();
        entity.setObservationEventId(getInt(dataRow, "ObservationEventId"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果ID：" + entity.getObservationEventId());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 検査結果IDを取得する
     *
     * @return
     */
    public int getObservationEventId() {
        return this.observationEventId;
    }
    /**
     * 検査結果IDを設定する
     *
     * @param value
     */
    public void setObservationEventId(int value) {
        this.observationEventId = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "bservationEventId=" + observationEventId + 
                " }\r\n";
    }
}
