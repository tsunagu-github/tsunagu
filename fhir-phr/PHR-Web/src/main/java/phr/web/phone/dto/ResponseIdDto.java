/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

/**
 *
 * @author daisuke
 */
public class ResponseIdDto extends ResponseBaseDto {

    /**
     * 暗号化ID
     */
    private String encId;

    /**
     * @return the encId
     */
    public String getEncId() {
        return encId;
    }

    /**
     * @param encId the encId to set
     */
    public void setEncId(String encId) {
        this.encId = encId;
    }
    
    
}
