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
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.BackupRestoreStatusEntity;

/**
 * バックアップリストアステータスのデータオブジェクトです。
 */
public abstract class BackupRestoreStatusEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreStatusEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public BackupRestoreStatusEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* バックアップリストアステータスCD */
    protected int backupRestoreStatusCd = 0;
    /* 名称 */
    protected String backupRestoreStatusName = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  BackupRestoreStatusEntity
     */
    public static BackupRestoreStatusEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  BackupRestoreStatusEntity
     */
    public static BackupRestoreStatusEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        BackupRestoreStatusEntity entity = new BackupRestoreStatusEntity();
        entity.setBackupRestoreStatusCd(getInt(dataRow, "BackupRestoreStatusCd"));
        entity.setBackupRestoreStatusName(getString(dataRow, "BackupRestoreStatusName"));

        if (logger.isDebugEnabled())
        {
            logger.debug("バックアップリストアステータスCD：" + entity.getBackupRestoreStatusCd());
            logger.debug("名称                            ：" + entity.getBackupRestoreStatusName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * バックアップリストアステータスCDを取得する
     *
     * @return
     */
    public int getBackupRestoreStatusCd() {
        return this.backupRestoreStatusCd;
    }
    /**
     * バックアップリストアステータスCDを設定する
     *
     * @param value
     */
    public void setBackupRestoreStatusCd(int value) {
        this.backupRestoreStatusCd = value;
    }

    /**
     * 名称を取得する
     *
     * @return
     */
    public String getBackupRestoreStatusName() {
        return this.backupRestoreStatusName;
    }
    /**
     * 名称を設定する
     *
     * @param value
     */
    public void setBackupRestoreStatusName(String value) {
        this.backupRestoreStatusName = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "ackupRestoreStatusCd=" + backupRestoreStatusCd + 
                ", ackupRestoreStatusName=" + backupRestoreStatusName + 
                " }\r\n";
    }
}
