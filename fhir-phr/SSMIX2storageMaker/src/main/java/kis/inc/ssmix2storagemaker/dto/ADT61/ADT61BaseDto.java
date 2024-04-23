/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.ADT61;

import java.util.List;

import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IAMDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;

/**
 *
 * @author kis-note
 */
public class ADT61BaseDto extends ModelDto {
    /**
     * 作成日
     */
    private String cdate;
    /**
     * MSHフィールド
     */
    private MSHDto mshDto;
    /**
     * EVNフィールド
     */
    private EVNDto evnDto;
    /**
     * PIDフィールド
     */
    private PIDDto pidDto;
    /**
     * PV1フィールド
     */
    private PV1Dto pv1Dto;
    /**
     * IAMフィールド
     */
    private List<IAMDto> iamList;

    /**
     * @return the mshDto
     */
    public MSHDto getMshDto() {
        return mshDto;
    }

    /**
     * @param mshDto the mshDto to set
     */
    public void setMshDto(MSHDto mshDto) {
        this.mshDto = mshDto;
    }

    /**
     * @return the evnDto
     */
    public EVNDto getEvnDto() {
        return evnDto;
    }

    /**
     * @param evnDto the evnDto to set
     */
    public void setEvnDto(EVNDto evnDto) {
        this.evnDto = evnDto;
    }

    /**
     * @return the pidDto
     */
    public PIDDto getPidDto() {
        return pidDto;
    }

    /**
     * @param pidDto the pidDto to set
     */
    public void setPidDto(PIDDto pidDto) {
        this.pidDto = pidDto;
    }

    /**
     * @return the pv1Dto
     */
    public PV1Dto getPv1Dto() {
        return pv1Dto;
    }

    /**
     * @param pv1Dto the pv1Dto to set
     */
    public void setPv1Dto(PV1Dto pv1Dto) {
        this.pv1Dto = pv1Dto;
    }

    /**
     * @return the iamList
     */
    public List<IAMDto> getIamList() {
        return iamList;
    }

    /**
     * @param iamList the iamList to set
     */
    public void setIamList(List<IAMDto> iamList) {
        this.iamList = iamList;
    }

    /**
     * @return the cdate
     */
    public String getCdate() {
        return cdate;
    }

    /**
     * @param cdate the cdate to set
     */
    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
    
}
