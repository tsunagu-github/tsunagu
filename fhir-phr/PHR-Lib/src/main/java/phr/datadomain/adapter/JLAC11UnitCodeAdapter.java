/********************************************************************************
 * システム名      ：phr
 * コンポーネント名：JLAC11単位コード情報のデータオブジェクト
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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.adapter.base.*;

/**
 * JLAC11単位コード情報のデータオブジェクトです。
 */
@Repository
public class JLAC11UnitCodeAdapter extends JLAC11UnitCodeAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = Logger.getLogger(JLAC11UnitCodeAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public JLAC11UnitCodeAdapter(Connection conn) {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

}
