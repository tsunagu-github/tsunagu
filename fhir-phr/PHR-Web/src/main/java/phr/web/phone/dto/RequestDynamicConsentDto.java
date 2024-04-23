/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果取得リクエストDTO
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
public class RequestDynamicConsentDto extends RequestBaseDto {

    /**
     * 処理のタイプ
     */
    private String type;

    /**
     * 研究ID
     */
    private String studyId;

    /**
     * 項目ID
     */
    private String subjectId;

    /**
     * 確認項目の回答情報
     */
    private List<String> checks;

    /**
     * 回答ステータス
     */
    private String responseStatus;

    /**
     * 同意種別
     */
    private String consentType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getStudyId() {
		return studyId;
	}

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

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	/**
	 * @return checks
	 */
	public List<String> getChecks() {
		return checks;
	}

	/**
	 * @param checks セットする checks
	 */
	public void setChecks(List<String> checks) {
		this.checks = checks;
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

}
