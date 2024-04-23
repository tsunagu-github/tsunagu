/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.PPR01;

import java.util.List;

import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PRBDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZI1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZPDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZPRDto;

/**
 *
 * @author kis-note
 */
public class ProblemDto {
    /**
     * PRBフィールド
     */
    private PRBDto prbDto;
    /**
     * ZPRフィールド
     */
    private ZPRDto zprDto;
    /**
     * ZPDフィールド
     */
    private List<ZPDDto> zpdList;
    /**
     * ZI1フィールド
     */
    private List<ZI1Dto> zi1List;
    /**
     * ORCフィールド
     */
    private List<ORCDto> orcList;

    /**
     * @return the prbDto
     */
    public PRBDto getPrbDto() {
        return prbDto;
    }

    /**
     * @param prbDto the prbDto to set
     */
    public void setPrbDto(PRBDto prbDto) {
        this.prbDto = prbDto;
    }

    /**
     * @return the zprDto
     */
    public ZPRDto getZprDto() {
        return zprDto;
    }

    /**
     * @param zprDto the zprDto to set
     */
    public void setZprDto(ZPRDto zprDto) {
        this.zprDto = zprDto;
    }

    /**
     * @return the zpdList
     */
    public List<ZPDDto> getZpdList() {
        return zpdList;
    }

    /**
     * @param zpdList the zpdList to set
     */
    public void setZpdList(List<ZPDDto> zpdList) {
        this.zpdList = zpdList;
    }

    /**
     * @return the zi1List
     */
    public List<ZI1Dto> getZi1List() {
        return zi1List;
    }

    /**
     * @param zi1List the zi1List to set
     */
    public void setZi1List(List<ZI1Dto> zi1List) {
        this.zi1List = zi1List;
    }

    /**
     * @return the orcList
     */
    public List<ORCDto> getOrcList() {
        return orcList;
    }

    /**
     * @param orcList the orcList to set
     */
    public void setOrcList(List<ORCDto> orcList) {
        this.orcList = orcList;
    }
    
}
