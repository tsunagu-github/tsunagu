/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：健診結果(健診・問診・診察)情報リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

import java.util.List;

/**
 *
 * @author iwaasa
 */
public class RequestCheckUpResultDto extends RequestBaseDto{
    /**
     * 健診の種類(健診：3 問診：4 診察：5)
     */
    private List<String> inspectionTypeList;
    
    /**
     * 表示ステータス
     */
    private String status;
    
    /**
     * 現在表示されているデータの健診日
     */
    private String targetDate;
    
    /**
     * ObservationEventId（健診）
     */
    private String medicalCheckupId;
    
    /**
     * ObservationEventId（問診）
     */
    private String inquiryId;
    
    /**
     * ObservationEventId（診察）
     */
    private String examinationId;
    
    /**
     * inspectionType取得
     *
     * @return the inspectionType(健診：3 問診：4 診察：5)
     */
    public List<String> getInspectionTypeList() {
        return inspectionTypeList;
    }

    /**
     * inspectionType設定
     *
     * @param inspectionType the inspectionType to set
     * (健診：3 問診：4 診察：5)
     */
    public void setInspectionTypeList(List<String> inspectionTypeList) {
        this.inspectionTypeList = inspectionTypeList;
    }

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status セットする status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return targetDate
	 */
	public String getTargetDate() {
		return targetDate;
	}

	/**
	 * @param targetDate セットする targetDate
	 */
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	/**
	 * @return medicalCheckupId
	 */
	public String getMedicalCheckupId() {
		return medicalCheckupId;
	}

	/**
	 * @param medicalCheckupId セットする medicalCheckupId
	 */
	public void setMedicalCheckupId(String medicalCheckupId) {
		this.medicalCheckupId = medicalCheckupId;
	}

	/**
	 * @return inquiryId
	 */
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	 * @param inquiryId セットする inquiryId
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	 * @return examinationId
	 */
	public String getExaminationId() {
		return examinationId;
	}

	/**
	 * @param examinationId セットする examinationId
	 */
	public void setExaminationId(String examinationId) {
		this.examinationId = examinationId;
	}
    
}
