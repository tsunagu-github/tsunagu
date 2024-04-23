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

/**
 *
 * @author daisuke
 */
public class DynamicConsentViewDto extends ResponseBaseDto {
    
    /**
     * 研究ID
     */
    private String studyId;

    /**
     * 項目ID
     */
    private String subjectId;

    /**
     * 研究名称
     */
    private String studyName;

    /**
     * 研究説明
     */
    private String explanation;

    /**
     * 同意種別
     */
    private String consentType;

    /**
     * 回答ステータス
     */
    private String responseStatus;

    /**
     * 新着フラグ
     */
    private boolean newArrivalFlg;

    /**
     * 通知日
     */
    private String notificationDate;

    /**
     * 回答更新日
     */
    private String responseDate;

//    /**
//     * 確認項目リスト
//     */
//    private List<String> checkList;

    /**
     * 確認項目・選択状態リスト
     */
    private List<CheckListViewDto> checkList;

    /**
     * ソート番号
     */
    private int sortNo;
    
    /**
     * URL
     */
    private String url;
    
    /**
     * isNewArrivalView（新着アイコン表示フラグ）
     */
    private String isNewArrivalView;
    
    /**
     * isOptInIconView（同意種別アイコン表示フラグ）
     */
    private String isOptInIconView;
    
    /**
     * isOptOutIconView（同意種別アイコン表示フラグ）
     */
    private String isOptOutIconView;
    
    /**
     * isCheckIconView（回答ステータスアイコン表示フラグ）
     */
    private String isCheckIconView;
    
    /**
     * isBanIconView（回答ステータスアイコン表示フラグ）
     */
    private String isBanIconView;
    
    /**
     * isHoldIconView（回答ステータスアイコン表示フラグ）
     */
    private String isHoldIconView;
    
    /**
     * isResponseDateView（回答更新日表示フラグ）
     */
    private String isResponseDateView;
    
    /**
     * 文字色（回答種別）
     */
    private String fontColor;

    /**
     * @return studyId
     */
    public String getStudyId() {
        return studyId;
    }

    /**
    * @param studyId セットする studyId
    */
    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    /**
	 * @return subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId セットする subjectId
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
     * @return studyName
     */
    public String getStudyName() {
        return studyName;
    }

    /**
     * @param studyName セットする studyName
     */
    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    /**
     * @return explanation
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * @param explanation セットする explanation
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * @return consentType
     */
    public String getConsentType() {
        return consentType;
    }

    /**
     * @param consentType セットする consentType
     */
    public void setConsentType(String consentType) {
        this.consentType = consentType;
    }

    /**
     * @return responseStatus
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    /**
     * @param responseStatus セットする responseStatus
     */
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * @return newArrivalFlg
     */
    public boolean isNewArrivalFlg() {
        return newArrivalFlg;
    }

    /**
     * @param newArrivalFlg セットする newArrivalFlg
     */
    public void setNewArrivalFlg(boolean newArrivalFlg) {
        this.newArrivalFlg = newArrivalFlg;
    }

    /**
     * @return notificationDate
     */
    public String getNotificationDate() {
        return notificationDate;
    }

    /**
     * @param notificationDate セットする notificationDate
     */
    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

//    /**
//     * @return checkList
//     */
//    public List<String> getCheckList() {
//        return checkList;
//    }
//
//    /**
//     * @param checkList セットする checkList
//     */
//    public void setCheckList(List<String> checkList) {
//        this.checkList = checkList;
//    }

    /**
     * @return sortNo
     */
    public int getSortNo() {
        return sortNo;
    }

    /**
     * @param sortNo セットする sortNo
     */
    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * @return isNewArrivalView
     */
    public String getIsNewArrivalView() {
        return isNewArrivalView;
    }

    /**
     * @param isNewArrivalView セットする isNewArrivalView
     */
    public void setIsNewArrivalView(String isNewArrivalView) {
        this.isNewArrivalView = isNewArrivalView;
    }

    /**
     * @return isOptInIconView
     */
    public String getIsOptInIconView() {
        return isOptInIconView;
    }

    /**
     * @param isOptInIconView セットする isOptInIconView
     */
    public void setIsOptInIconView(String isOptInIconView) {
        this.isOptInIconView = isOptInIconView;
    }

    /**
     * @return isOptOutIconView
     */
    public String getIsOptOutIconView() {
        return isOptOutIconView;
    }

    /**
     * @param isOptOutIconView セットする isOptOutIconView
     */
    public void setIsOptOutIconView(String isOptOutIconView) {
        this.isOptOutIconView = isOptOutIconView;
    }

    /**
     * @return isCheckIconView
     */
    public String getIsCheckIconView() {
        return isCheckIconView;
    }

    /**
     * @param isCheckIconView セットする isCheckIconView
     */
    public void setIsCheckIconView(String isCheckIconView) {
        this.isCheckIconView = isCheckIconView;
    }

    /**
     * @return isBanIconView
     */
    public String getIsBanIconView() {
        return isBanIconView;
    }

    /**
     * @param isBanIconView セットする isBanIconView
     */
    public void setIsBanIconView(String isBanIconView) {
        this.isBanIconView = isBanIconView;
    }

    public String getIsHoldIconView() {
		return isHoldIconView;
	}

	public void setIsHoldIconView(String isHoldIconView) {
		this.isHoldIconView = isHoldIconView;
	}

	public String getIsResponseDateView() {
        return isResponseDateView;
    }

    public void setIsResponseDateView(String isResponseDateView) {
        this.isResponseDateView = isResponseDateView;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url セットする url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return checkList
	 */
	public List<CheckListViewDto> getCheckList() {
		return checkList;
	}

	/**
	 * @param checkList セットする checkList
	 */
	public void setCheckList(List<CheckListViewDto> checkList) {
		this.checkList = checkList;
	}

}
