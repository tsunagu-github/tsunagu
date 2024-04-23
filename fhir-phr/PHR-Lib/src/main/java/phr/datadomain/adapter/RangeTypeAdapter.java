/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：範囲種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.adapter.base.*;

/**
 * 範囲種別のデータオブジェクトです。
 */
public class RangeTypeAdapter extends RangeTypeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RangeTypeAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public RangeTypeAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

}
