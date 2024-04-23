/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：バックアップリストア用
 * 作成者          ：kis-inc
 * 作成日          ：2016/12/014
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
public class RequestOperationBackupRestoreDto extends RequestBaseDto {

    protected String oldPhrId = null;
    protected String restorePassword = null;
    protected String operationMode = null;
    private String phrAssociationId = null;
    private String projectId = null;
//    private String backupRestoreEventId = null;
    

    public String getOldPhrId() {
        return oldPhrId;
    }

    public void setOldPhrId(String oldPhrId) {
        this.oldPhrId = oldPhrId;
    }

    public String getRestorePassword() {
        return restorePassword;
    }

    public void setRestorePassword(String restorePassword) {
        this.restorePassword = restorePassword;
    }
    
    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
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

}
