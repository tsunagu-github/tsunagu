/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：利用者情報更新リクエストTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author daisuke
 */
public class RequestPatientDto extends ResponseBaseDto {
    /**
     * 利用者情報DTO
     */
    private ResponsePatientDto patientDto;

    /**
     * 利用者情報DTO
     * @return the patientDto
     */
    public ResponsePatientDto getPatientDto() {
        return patientDto;
    }

    /**
     * 利用者情報DTO
     * @param patientDto the patientDto to set
     */
    public void setPatientDto(ResponsePatientDto patientDto) {
        this.patientDto = patientDto;
    }
    
}
