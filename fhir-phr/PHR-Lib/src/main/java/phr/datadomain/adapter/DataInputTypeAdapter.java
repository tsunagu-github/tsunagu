/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ入力種別のデータオブジェクト
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
 * データ入力種別のデータオブジェクトです。
 */
public class DataInputTypeAdapter extends DataInputTypeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataInputTypeAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DataInputTypeAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

}
