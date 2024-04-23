/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：健診結果(健診・問診・診察)情報レスポンスDTO
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
public class ResponseCheckUpResultDto extends ResponseBaseDto{
    
    //検査値リストセット
    private List<CheckUpResultListDto> dataList;

    /**
     * 医療機関名
     */
    private String hospitalName;

    /**
     * 健診実施日
     */
    private String date;

    /**
     * 過去結果フラグ
     */
    private boolean beforeFlg;

    /**
     * 未来結果フラグ
     */
    private boolean futureFlg;

    /**
     * ObservationEventId(健診)
     */
    private String medicalCheckupId;

    /**
     * ObservationEventId(問診)
     */
    private String inquiryId;

    /**
     * ObservationEventId(診察)
     */
    private String examinationId;

    /**
     * ファイルデータ
     */
    private String file;

    /**
     * @return the dataList
     */
    public List<CheckUpResultListDto> getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(List<CheckUpResultListDto> dataList) {
        this.dataList = dataList;
    }

	/**
	 * @return hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}

	/**
	 * @param hospitalName セットする hospitalName
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date セットする date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return beforeFlg
	 */
	public boolean isBeforeFlg() {
		return beforeFlg;
	}

	/**
	 * @param beforeFlg セットする beforeFlg
	 */
	public void setBeforeFlg(boolean beforeFlg) {
		this.beforeFlg = beforeFlg;
	}

	/**
	 * @return futureFlg
	 */
	public boolean isFutureFlg() {
		return futureFlg;
	}

	/**
	 * @param futureFlg セットする futureFlg
	 */
	public void setFutureFlg(boolean futureFlg) {
		this.futureFlg = futureFlg;
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

	/**
	 * @return file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file セットする file
	 */
	public void setFile(String file) {
		this.file = file;
	}
}
