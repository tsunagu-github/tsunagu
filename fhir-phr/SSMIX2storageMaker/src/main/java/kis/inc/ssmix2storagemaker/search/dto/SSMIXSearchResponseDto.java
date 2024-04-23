/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.search.dto;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;

/**
 *
 * @author kis-note
 */
public class SSMIXSearchResponseDto {
    
    /**
     * ステータス
     */
    private boolean status;
    
    /**
     * エラーメッセージ
     */
    private List<SimpleEntry<String , ErrorDefinitionDto>> errmeaasgeList;   
    
    /**
     * 検索結果
     */
    private List<SSMIXSearchResultDto> resultList;

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the errmeaasgeList
     */
    public List<SimpleEntry<String , ErrorDefinitionDto>> getErrmeaasgeList() {
        return errmeaasgeList;
    }

    /**
     * @param errmeaasgeList the errmeaasgeList to set
     */
    public void setErrmeaasgeList(List<SimpleEntry<String , ErrorDefinitionDto>> errmeaasgeList) {
        this.errmeaasgeList = errmeaasgeList;
    }

    /**
     * @return the resultList
     */
    public List<SSMIXSearchResultDto> getResultList() {
        return resultList;
    }

    /**
     * @param resultList the resultList to set
     */
    public void setResultList(List<SSMIXSearchResultDto> resultList) {
        this.resultList = resultList;
    }
}
