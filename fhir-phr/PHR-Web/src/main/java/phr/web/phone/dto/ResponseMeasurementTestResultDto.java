/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果を格納するDTOクラス
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
import java.util.Map;

/**
 *
 * @author daisuke
 */
public class ResponseMeasurementTestResultDto extends ResponseBaseDto {

    /**
     * 過去結果
     */
    private String pastDate;
    
    /**
     * 未来結果
     */
    private String futureDate;
    
    /**
     *ビューリスト
     */
    private List<MeasurementTestViewDto> viewList;

    /**
     * 医療機関名
     */
    private String hospitalName;

    /**
     * 該当年月日
     */
    private String date;

    /**
     * 背景色
     */
    private String backgroundColor;

    /**
     * 表タイトル
     */
    private String title;

    /**
     * ObservationEventId
     */
    private String observationEventId;

    /**
     * 過去結果
     * @return the pastDate
     */
    public String getPastDate() {
        return pastDate;
    }

    /**
     * 過去結果
     * @param pastDate the pastDate to set
     */
    public void setPastDate(String pastDate) {
        this.pastDate = pastDate;
    }

    /**
     * 未来結果
     * @return the futureDate
     */
    public String getFutureDate() {
        return futureDate;
    }

    /**
     * 未来結果
     * @param futureDate the futureDate to set
     */
    public void setFutureDate(String futureDate) {
        this.futureDate = futureDate;
    }
    
    /**
     * ビューリスト
     * @return the viewList
     */
    public List<MeasurementTestViewDto> getViewList() {
        return viewList;
    }

    /**
     * ビューリスト
     * @param viewList the viewList to set
     */
    public void setViewList(List<MeasurementTestViewDto> viewList) {
        this.viewList = viewList;
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
	 * @return backgroundColor
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor セットする backgroundColor
	 */
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title セットする title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return observationEventId
	 */
	public String getObservationEventId() {
		return observationEventId;
	}

	/**
	 * @param observationEventId セットする observationEventId
	 */
	public void setObservationEventId(String observationEventId) {
		this.observationEventId = observationEventId;
	}    

}
