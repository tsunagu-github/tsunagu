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
public class ResponseDynamicConsentDto extends ResponseBaseDto {

    /**
     * ビューリスト（研究ID昇順）
     */
    private List<DynamicConsentViewDto> viewList;
    
    /**
     * ビューリスト（研究ID降順）
     */
    private List<DynamicConsentViewDto> viewList2;
    
    /**
     * ビューリスト（通知日昇順）
     */
    private List<DynamicConsentViewDto> viewList3;
    
    /**
     * ビューリスト（通知日降順）
     */
    private List<DynamicConsentViewDto> viewList4;
    
    /**
     * ビューリスト（最終更新日昇順）
     */
    private List<DynamicConsentViewDto> viewList5;
    
    /**
     * ビューリスト（最終更新日降順）
     */
    private List<DynamicConsentViewDto> viewList6;
    
    /**
     * 備考
     */
    private String remarks;
    
    /**
     * 患者名
     */
    private String patientName;

    /**
     * ダイナミックコンセント利用状況フラグ
     */
    private boolean dynamicConsentFlg;

	/**
	 * @return viewList
	 */
	public List<DynamicConsentViewDto> getViewList() {
		return viewList;
	}

	/**
	 * @param viewList セットする viewList
	 */
	public void setViewList(List<DynamicConsentViewDto> viewList) {
		this.viewList = viewList;
	}

	/**
	 * @return viewList2
	 */
	public List<DynamicConsentViewDto> getViewList2() {
		return viewList2;
	}

	/**
	 * @param viewList2 セットする viewList2
	 */
	public void setViewList2(List<DynamicConsentViewDto> viewList2) {
		this.viewList2 = viewList2;
	}

	/**
	 * @return viewList3
	 */
	public List<DynamicConsentViewDto> getViewList3() {
		return viewList3;
	}

	/**
	 * @param viewList3 セットする viewList3
	 */
	public void setViewList3(List<DynamicConsentViewDto> viewList3) {
		this.viewList3 = viewList3;
	}

	/**
	 * @return viewList4
	 */
	public List<DynamicConsentViewDto> getViewList4() {
		return viewList4;
	}

	/**
	 * @param viewList4 セットする viewList4
	 */
	public void setViewList4(List<DynamicConsentViewDto> viewList4) {
		this.viewList4 = viewList4;
	}

	/**
	 * @return viewList5
	 */
	public List<DynamicConsentViewDto> getViewList5() {
		return viewList5;
	}

	/**
	 * @param viewList5 セットする viewList5
	 */
	public void setViewList5(List<DynamicConsentViewDto> viewList5) {
		this.viewList5 = viewList5;
	}

	/**
	 * @return viewList6
	 */
	public List<DynamicConsentViewDto> getViewList6() {
		return viewList6;
	}

	/**
	 * @param viewList6 セットする viewList6
	 */
	public void setViewList6(List<DynamicConsentViewDto> viewList6) {
		this.viewList6 = viewList6;
	}

	/**
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks セットする remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName セットする patientName
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return dynamicConsentFlg
	 */
	public boolean isDynamicConsentFlg() {
		return dynamicConsentFlg;
	}

	/**
	 * @param dynamicConsentFlg セットする dynamicConsentFlg
	 */
	public void setDynamicConsentFlg(boolean dynamicConsentFlg) {
		this.dynamicConsentFlg = dynamicConsentFlg;
	}

}
