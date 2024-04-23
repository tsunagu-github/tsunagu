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
public class ClinicalTestViewDto extends ResponseBaseDto {
    
    /**
     * ビューId
     */
    private Integer viewId;
    /**
     * ビュー名称
     */
    private String viewName;
//
//    /**
//     * 過去検索対象日
//     */
//    private String pastDate;
//    /**
//     * 未来検索対象日
//     */
//    private String futureDate;
    /**
     * 疾病別検査項目MAP
     */
    private Map<Integer, List<String>> diseaseDefinitionMap;

    /**
     * 疾病種別リスト
     */
    private List<DiseaseTypeDto> diseaseTypeList;

    /**
     * 検査結果リスト
     */
    private List<ObservationResultDto> resultList;
    
//    /*
//    *ビューリスト
//    */
//    private List<ClinicalTestViewDto> viewList;

//    /**
//     * 過去検索対象日
//     *
//     * @return the pastDate
//     */
//    public String getPastDate() {
//        return pastDate;
//    }
//
//    /**
//     * 過去検索対象日
//     *
//     * @param pastDate the pastDate to set
//     */
//    public void setPastDate(String pastDate) {
//        this.pastDate = pastDate;
//    }
//
//    /**
//     * 未来検索対象日
//     *
//     * @return the futureDate
//     */
//    public String getFutureDate() {
//        return futureDate;
//    }
//
//    /**
//     * 未来検索対象日
//     *
//     * @param futureDate the futureDate to set
//     */
//    public void setFutureDate(String futureDate) {
//        this.futureDate = futureDate;
//    }

    /**
     * 疾病別検査項目MAP
     *
     * @return the diseaseDefinitionMap
     */
    public Map<Integer, List<String>> getDiseaseDefinitionMap() {
        return diseaseDefinitionMap;
    }

    /**
     * 疾病別検査項目MAP
     *
     * @param diseaseDefinitionMap the diseaseDefinitionMap to set
     */
    public void setDiseaseDefinitionMap(Map<Integer, List<String>> diseaseDefinitionMap) {
        this.diseaseDefinitionMap = diseaseDefinitionMap;
    }

    /**
     * 疾病種別リスト
     *
     * @return the diseaseTypeList
     */
    public List<DiseaseTypeDto> getDiseaseTypeList() {
        return diseaseTypeList;
    }

    /**
     * 疾病種別リスト
     *
     * @param diseaseTypeList the diseaseTypeList to set
     */
    public void setDiseaseTypeList(List<DiseaseTypeDto> diseaseTypeList) {
        this.diseaseTypeList = diseaseTypeList;
    }

    /**
     * 検査結果リスト
     *
     * @return the resultList
     */
    public List<ObservationResultDto> getResultList() {
        return resultList;
    }

    /**
     * 検査結果リスト
     *
     * @param resultList the resultList to set
     */
    public void setResultList(List<ObservationResultDto> resultList) {
        this.resultList = resultList;
    }

    /**
     * @return the viewId
     */
    public Integer getViewId() {
        return viewId;
    }

    /**
     * @param viewId the viewId to set
     */
    public void setViewId(Integer viewId) {
        this.viewId = viewId;
    }

    /**
     * @return the viewName
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * @param viewName the viewName to set
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

}
