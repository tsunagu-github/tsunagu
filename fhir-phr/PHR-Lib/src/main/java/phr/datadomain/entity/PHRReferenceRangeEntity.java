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
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * PHR推奨設定シートのデータオブジェクトです。
 */
public class PHRReferenceRangeEntity extends PHRReferenceRangeEntityBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(PHRReferenceRangeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public PHRReferenceRangeEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */

}
