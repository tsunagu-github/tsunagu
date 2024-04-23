/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おくすり更新リクエストDTO
 * 作成者          ：kis-inc
    * 作成日          ：2016/11/07
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

import phr.datadomain.entity.DosagePatientInputEntity;

/**
 *
 * @author iwaasa
 */
public class RequestMedicinePatientInputDto extends RequestBaseDto{
    private String dosageId = null;
    private DosagePatientInputEntity[] patientInputList = null;

    /**
     * @return the dosageId
     */
    public String getDosageId() {
        return dosageId;
    }

    /**
     * @param dosageId the String to set
     */
    public void setDosageId(String dosageId) {
        this.dosageId = dosageId;
    }

    /**
     * @return the patientInputList
     */
    public DosagePatientInputEntity[] getPatientInputList() {
        return patientInputList;
    }

    /**
     * @param patientInputList the patientInputList to set
     */
    public void setPatientInputList(DosagePatientInputEntity[] patientInputList) {
        this.patientInputList = patientInputList;
    }
    
}
