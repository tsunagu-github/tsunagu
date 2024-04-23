/********************************************************************************
 * システム名      ：MInCS for PHR
 * コンポーネント名：PHR推奨設定シートのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/12/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import phr.datadomain.adapter.base.*;

/**
 * PHR推奨設定シートのデータオブジェクトです。
 */
@Repository
public class PHRReferenceRangeAdapter extends PHRReferenceRangeAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(PHRReferenceRangeAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PHRReferenceRangeAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

}
