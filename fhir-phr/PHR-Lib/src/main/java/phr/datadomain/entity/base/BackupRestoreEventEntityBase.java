/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：バックアップリストアイベントのデータオブジェクト
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
import phr.datadomain.entity.BackupRestoreEventEntity;

/**
 * バックアップリストアイベントのデータオブジェクトです。
 */
public abstract class BackupRestoreEventEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreEventEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public BackupRestoreEventEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* バックアップリストアイベントID */
    protected String backupRestoreEventId = null;
    /* リクエスト保険者No */
    protected String requestInsurerNo = null;
    /* リクエストPHRID */
    protected String requestPHR_ID = null;
    /* バックアップリストアクションCD */
    protected int backupRestoreActionTypeCd = 0;
    /* バックアップリストアスターてスCD */
    protected int backupRestoreStatusCd = 0;
    /* パスワード */
    protected String password = null;
    /* リストアPHR事業者番号 */
    protected String restorePhrAssociationNo = null;
    /* リストアプロジェクトNo */
    protected String restoreProjectNo = null;
    /* リストアPHRID */
    protected String restorePHR_ID = null;
    /* 受付No */
    protected String receiptNo = null;
    /* エラーメッセージ */
    protected String errorMessage = null;
    /* 開始日時 */
    protected Timestamp startDateTiem = null;
    /* 終了日時 */
    protected Timestamp endDateTime = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  BackupRestoreEventEntity
     */
    public static BackupRestoreEventEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  BackupRestoreEventEntity
     */
    public static BackupRestoreEventEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        BackupRestoreEventEntity entity = new BackupRestoreEventEntity();
        entity.setBackupRestoreEventId(getString(dataRow, "BackupRestoreEventId"));
        entity.setRequestInsurerNo(getString(dataRow, "RequestInsurerNo"));
        entity.setRequestPHR_ID(getString(dataRow, "RequestPHR_ID"));
        entity.setBackupRestoreActionTypeCd(getInt(dataRow, "BackupRestoreActionTypeCd"));
        entity.setBackupRestoreStatusCd(getInt(dataRow, "BackupRestoreStatusCd"));
        entity.setPassword(getString(dataRow, "Password"));
        entity.setRestorePhrAssociationNo(getString(dataRow, "RestorePhrAssociationNo"));
        entity.setRestoreProjectNo(getString(dataRow, "RestoreProjectNo"));
        entity.setRestorePHR_ID(getString(dataRow, "RestorePHR_ID"));
        entity.setReceiptNo(getString(dataRow, "ReceiptNo"));
        entity.setErrorMessage(getString(dataRow, "ErrorMessage"));
        entity.setStartDateTiem(getDateTime(dataRow, "StartDateTiem"));
        entity.setEndDateTime(getDateTime(dataRow, "EndDateTime"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("バックアップリストアイベントID  ：" + entity.getBackupRestoreEventId());
            logger.debug("リクエスト保険者No              ：" + entity.getRequestInsurerNo());
            logger.debug("リクエストPHRID                 ：" + entity.getRequestPHR_ID());
            logger.debug("バックアップリストアクションCD  ：" + entity.getBackupRestoreActionTypeCd());
            logger.debug("バックアップリストアスターてスCD：" + entity.getBackupRestoreStatusCd());
            logger.debug("パスワード                      ：" + entity.getPassword());
            logger.debug("リストアPHR事業者番号           ：" + entity.getRestorePhrAssociationNo());
            logger.debug("リストアプロジェクトNo          ：" + entity.getRestoreProjectNo());
            logger.debug("リストアPHRID                   ：" + entity.getRestorePHR_ID());
            logger.debug("受付No                          ：" + entity.getReceiptNo());
            logger.debug("エラーメッセージ                ：" + entity.getErrorMessage());
            logger.debug("開始日時                        ：" + entity.getStartDateTiem());
            logger.debug("終了日時                        ：" + entity.getEndDateTime());
            logger.debug("作成日時                        ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時                    ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * バックアップリストアイベントIDを取得する
     *
     * @return
     */
    public String getBackupRestoreEventId() {
        return this.backupRestoreEventId;
    }
    /**
     * バックアップリストアイベントIDを設定する
     *
     * @param value
     */
    public void setBackupRestoreEventId(String value) {
        this.backupRestoreEventId = value;
    }

    /**
     * リクエスト保険者Noを取得する
     *
     * @return
     */
    public String getRequestInsurerNo() {
        return this.requestInsurerNo;
    }
    /**
     * リクエスト保険者Noを設定する
     *
     * @param value
     */
    public void setRequestInsurerNo(String value) {
        this.requestInsurerNo = value;
    }

    /**
     * リクエストPHRIDを取得する
     *
     * @return
     */
    public String getRequestPHR_ID() {
        return this.requestPHR_ID;
    }
    /**
     * リクエストPHRIDを設定する
     *
     * @param value
     */
    public void setRequestPHR_ID(String value) {
        this.requestPHR_ID = value;
    }

    /**
     * バックアップリストアクションCDを取得する
     *
     * @return
     */
    public int getBackupRestoreActionTypeCd() {
        return this.backupRestoreActionTypeCd;
    }
    /**
     * バックアップリストアクションCDを設定する
     *
     * @param value
     */
    public void setBackupRestoreActionTypeCd(int value) {
        this.backupRestoreActionTypeCd = value;
    }

    /**
     * バックアップリストアスターてスCDを取得する
     *
     * @return
     */
    public int getBackupRestoreStatusCd() {
        return this.backupRestoreStatusCd;
    }
    /**
     * バックアップリストアスターてスCDを設定する
     *
     * @param value
     */
    public void setBackupRestoreStatusCd(int value) {
        this.backupRestoreStatusCd = value;
    }

    /**
     * パスワードを取得する
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * パスワードを設定する
     *
     * @param value
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * リストアPHR事業者番号を取得する
     *
     * @return
     */
    public String getRestorePhrAssociationNo() {
        return this.restorePhrAssociationNo;
    }
    /**
     * リストアPHR事業者番号を設定する
     *
     * @param value
     */
    public void setRestorePhrAssociationNo(String value) {
        this.restorePhrAssociationNo = value;
    }

    /**
     * リストアプロジェクトNoを取得する
     *
     * @return
     */
    public String getRestoreProjectNo() {
        return this.restoreProjectNo;
    }
    /**
     * リストアプロジェクトNoを設定する
     *
     * @param value
     */
    public void setRestoreProjectNo(String value) {
        this.restoreProjectNo = value;
    }

    /**
     * リストアPHRIDを取得する
     *
     * @return
     */
    public String getRestorePHR_ID() {
        return this.restorePHR_ID;
    }
    /**
     * リストアPHRIDを設定する
     *
     * @param value
     */
    public void setRestorePHR_ID(String value) {
        this.restorePHR_ID = value;
    }

    /**
     * 受付Noを取得する
     *
     * @return
     */
    public String getReceiptNo() {
        return this.receiptNo;
    }
    /**
     * 受付Noを設定する
     *
     * @param value
     */
    public void setReceiptNo(String value) {
        this.receiptNo = value;
    }

    /**
     * エラーメッセージを取得する
     *
     * @return
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }
    /**
     * エラーメッセージを設定する
     *
     * @param value
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * 開始日時を取得する
     *
     * @return
     */
    public Timestamp getStartDateTiem() {
        return this.startDateTiem;
    }
    /**
     * 開始日時を設定する
     *
     * @param value
     */
    public void setStartDateTiem(Timestamp value) {
        this.startDateTiem = value;
    }

    /**
     * 終了日時を取得する
     *
     * @return
     */
    public Timestamp getEndDateTime() {
        return this.endDateTime;
    }
    /**
     * 終了日時を設定する
     *
     * @param value
     */
    public void setEndDateTime(Timestamp value) {
        this.endDateTime = value;
    }

    /**
     * 作成日時を取得する
     *
     * @return
     */
    public Timestamp getCreateDateTime() {
        return this.createDateTime;
    }
    /**
     * 作成日時を設定する
     *
     * @param value
     */
    public void setCreateDateTime(Timestamp value) {
        this.createDateTime = value;
    }

    /**
     * 最終更新日時を取得する
     *
     * @return
     */
    public Timestamp getUpdateDateTime() {
        return this.updateDateTime;
    }
    /**
     * 最終更新日時を設定する
     *
     * @param value
     */
    public void setUpdateDateTime(Timestamp value) {
        this.updateDateTime = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "ackupRestoreEventId=" + backupRestoreEventId + 
                ", equestInsurerNo=" + requestInsurerNo + 
                ", equestPHR_ID=" + requestPHR_ID + 
                ", ackupRestoreActionTypeCd=" + backupRestoreActionTypeCd + 
                ", ackupRestoreStatusCd=" + backupRestoreStatusCd + 
                ", assword=" + password + 
                ", estorePhrAssociationNo=" + restorePhrAssociationNo + 
                ", estoreProjectNo=" + restoreProjectNo + 
                ", estorePHR_ID=" + restorePHR_ID + 
                ", eceiptNo=" + receiptNo + 
                ", rrorMessage=" + errorMessage + 
                ", tartDateTiem=" + startDateTiem + 
                ", ndDateTime=" + endDateTime + 
                ", reateDateTime=" + createDateTime + 
                ", pdateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
