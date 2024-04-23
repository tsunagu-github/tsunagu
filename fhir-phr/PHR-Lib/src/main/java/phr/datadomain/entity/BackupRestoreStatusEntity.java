/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：バックアップリストアステータスのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/12/22
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
 * バックアップリストアステータスのデータオブジェクトです。
 */
public class BackupRestoreStatusEntity extends BackupRestoreStatusEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreStatusEntity.class);

    /**
     * ステータス：受付
     */
    public static final int STATUS_RECEPTION = 1;
     /**
     * ステータス：処理中
     */
    public static final int STATUS_PROCESSING = 2;
    /**
     * ステータス：完了
     */
    public static final int STATUS_COMPLETED = 3;
    /**
     * ステータス：無効
     */
    public static final int STATUS_INVALID = 8;
     /**
     * ステータス：エラー
     */
    public static final int STATUS_ERROR = 9;
  
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public BackupRestoreStatusEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
