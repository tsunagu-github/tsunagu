/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：バックアップリストアアクションのデータオブジェクト
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
 * バックアップリストアアクションのデータオブジェクトです。
 */
public class BackupRestoreActionTypeEntity extends BackupRestoreActionTypeEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreActionTypeEntity.class);
    /**
     * アクション：バックアップ
     */
    public static final int ACTION_BACKUP = 1;
     /**
     * アクション：リストア
     */
    public static final int ACTION_RESTORE = 2;
     /**
     * アクション：削除
     */
    public static final int ACTION_DELETE = 3;
  
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public BackupRestoreActionTypeEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
