/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.dto;

import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;

/**
 *
 * @author kis-note
 */
public class ConvertResultObservationDto extends ResultObservationDto {
    
    /*
    * 項目ID
    */
    private String observationDefinitionId;

    /**
     * @return the observationDefinitionId
     */
    public String getObservationDefinitionId() {
        return observationDefinitionId;
    }

    /**
     * @param observationDefinitionId the observationDefinitionId to set
     */
    public void setObservationDefinitionId(String observationDefinitionId) {
        this.observationDefinitionId = observationDefinitionId;
    }
    
    
    
}
