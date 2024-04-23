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
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.BackupRestoreActionTypeEntity;

/**
 * バックアップリストアアクションのデータオブジェクトです。
 */
public abstract class BackupRestoreActionTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreActionTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public BackupRestoreActionTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* バックアップリストアアクションCD */
    protected int backupRestoreActionTypeCd = 0;
    /* 名称 */
    protected String backupRestoreActionTypeName = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  BackupRestoreActionTypeEntity
     */
    public static BackupRestoreActionTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  BackupRestoreActionTypeEntity
     */
    public static BackupRestoreActionTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        BackupRestoreActionTypeEntity entity = new BackupRestoreActionTypeEntity();
        entity.setBackupRestoreActionTypeCd(getInt(dataRow, "BackupRestoreActionTypeCd"));
        entity.setBackupRestoreActionTypeName(getString(dataRow, "BackupRestoreActionTypeName"));

        if (logger.isDebugEnabled())
        {
            logger.debug("バックアップリストアアクションCD：" + entity.getBackupRestoreActionTypeCd());
            logger.debug("名称                            ：" + entity.getBackupRestoreActionTypeName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * バックアップリストアアクションCDを取得する
     *
     * @return
     */
    public int getBackupRestoreActionTypeCd() {
        return this.backupRestoreActionTypeCd;
    }
    /**
     * バックアップリストアアクションCDを設定する
     *
     * @param value
     */
    public void setBackupRestoreActionTypeCd(int value) {
        this.backupRestoreActionTypeCd = value;
    }

    /**
     * 名称を取得する
     *
     * @return
     */
    public String getBackupRestoreActionTypeName() {
        return this.backupRestoreActionTypeName;
    }
    /**
     * 名称を設定する
     *
     * @param value
     */
    public void setBackupRestoreActionTypeName(String value) {
        this.backupRestoreActionTypeName = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "ackupRestoreActionTypeCd=" + backupRestoreActionTypeCd + 
                ", ackupRestoreActionTypeName=" + backupRestoreActionTypeName + 
                " }\r\n";
    }
}
