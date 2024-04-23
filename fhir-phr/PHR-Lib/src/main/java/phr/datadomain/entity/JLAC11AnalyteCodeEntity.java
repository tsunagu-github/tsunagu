/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：JLAC11測定物情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * JLAC11測定物情報のデータオブジェクトです。
 */
public class JLAC11AnalyteCodeEntity extends JLAC11AnalyteCodeEntityBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(JLAC11AnalyteCodeEntity.class);
    private static Logger logger = Logger.getLogger(JLAC11AnalyteCodeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public JLAC11AnalyteCodeEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */

}
