/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：共用基準範囲のデータオブジェクト
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
 * 共用基準範囲のデータオブジェクトです。
 */
public class CommonReferenceRangeEntity extends CommonReferenceRangeEntityBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(CommonReferenceRangeEntity.class);
	private static Logger logger = Logger.getLogger(CommonReferenceRangeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public CommonReferenceRangeEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */

}
