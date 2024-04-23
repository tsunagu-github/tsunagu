/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：バックアップリストア用
 * 作成者          ：kis-inc
 * 作成日          ：2016/12/14
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author chiba
 */
public class ResponseOperationBackupRestoreDto extends ResponseBaseDto {

    protected String progressStatus = null;
    protected String backupDate = null;
    private String phrAssociationId = null;
    private String projectId = null;
    protected String errorText = null;
//    private String backupRestoreEventId = null;

    public String getBackupDate() {
        return backupDate;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public void setBackupDate(String backupDate) {
        this.backupDate = backupDate;
    }

    /**
     * @return the phrAssociationId
     */
    public String getPhrAssociationId() {
        return phrAssociationId;
    }

    /**
     * @param phrAssociationId the phrAssociationId to set
     */
    public void setPhrAssociationId(String phrAssociationId) {
        this.phrAssociationId = phrAssociationId;
    }

    /**
     * @return the projectId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

//    /**
//     * @return the backupRestoreEventId
//     */
//    public String getBackupRestoreEventId() {
//        return backupRestoreEventId;
//    }
//
//    /**
//     * @param backupRestoreEventId the backupRestoreEventId to set
//     */
//    public void setBackupRestoreEventId(String backupRestoreEventId) {
//        this.backupRestoreEventId = backupRestoreEventId;
//    }

    /**
     * @return the errorText
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * @param errorText the errorText to set
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

}
