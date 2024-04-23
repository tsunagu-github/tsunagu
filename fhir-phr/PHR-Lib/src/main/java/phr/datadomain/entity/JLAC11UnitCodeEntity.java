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
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * JLAC11単位コード情報のデータオブジェクトです。
 */
public class JLAC11UnitCodeEntity extends JLAC11UnitCodeEntityBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = Logger.getLogger(JLAC11UnitCodeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public JLAC11UnitCodeEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */

}
