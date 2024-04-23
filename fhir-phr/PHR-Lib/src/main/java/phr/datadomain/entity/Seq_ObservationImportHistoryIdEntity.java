/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果取得履歴 IDのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/04/26
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 検査結果取得履歴 IDのデータオブジェクトです。
 */
public class Seq_ObservationImportHistoryIdEntity extends Seq_ObservationImportHistoryIdEntityBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(Seq_ObservationImportHistoryIdEntity.class);
    private static Logger logger = Logger.getLogger(Seq_ObservationImportHistoryIdEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public Seq_ObservationImportHistoryIdEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */

}
