/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者連携設定操作リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
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
public class RequestOperationCooperationDto extends RequestBaseDto {
    
    protected String medicalCd = null;
    protected String patientId = null;
    protected String operationMode = null;
    protected boolean agreesToShare;

    public String getMedicalCd() {
        return medicalCd;
    }
    public void setMedicalCd( String medicalCd ) {
        this.medicalCd = medicalCd;
    }
    
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId( String patientId ) {
        this.patientId = patientId;
    }
    
    public String getOperationMode() {
        return operationMode;
    }
    public void setOperationMode( String operationMode ) {
        this.operationMode = operationMode;
    }
    
    public boolean getAgreesToShare() {
        return agreesToShare;
    }
    public void setAgreesToShare( boolean agreesToShare ) {
        this.agreesToShare = agreesToShare;
    }
}
