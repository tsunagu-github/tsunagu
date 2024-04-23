/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ色情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2019/05/16
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
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * グラフ色情報のデータオブジェクトです。
 */
public class ChartColorEntity extends ChartColorEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartColorEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ChartColorEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
