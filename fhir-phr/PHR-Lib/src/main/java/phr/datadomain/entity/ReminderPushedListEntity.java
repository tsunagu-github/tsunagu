/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダプッシュ完了リストのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/13
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
 * リマインダプッシュ完了リストのデータオブジェクトです。
 */
public class ReminderPushedListEntity extends ReminderPushedListEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderPushedListEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ReminderPushedListEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
