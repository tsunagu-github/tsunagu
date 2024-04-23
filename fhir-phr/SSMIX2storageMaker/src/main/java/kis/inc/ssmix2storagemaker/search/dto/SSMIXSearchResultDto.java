/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.search.dto;

import java.util.List;

import kis.inc.ssmix2storagemaker.dto.ModelDto;

/**
 * 検索結果のDto
 * @author kis-note
 */
public class SSMIXSearchResultDto {

    /**
     * 対象患者
     */
    private String patientId;
    
    /**
     * 検索結果のモデル
     */
    private List<ModelDto> modelList;

    /**
     * @return the patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return the modelList
     */
    public List<ModelDto> getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<ModelDto> modelList) {
        this.modelList = modelList;
    }
    
}
