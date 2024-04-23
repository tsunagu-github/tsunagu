/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.OMP12;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.segmentModel.CTIDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXEDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.TQ1Dto;

/**
 *
 * @author kis-note
 */
public class OrderDto {
     /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(OrderDto.class);

    /**
     * ORCセグメント
     */
    private ORCDto orcDto;

    /**
     * RXEセグメント
     */
    private RXEDto rxeDto;

    /**
     * TQ1セグメント
     */
    private TQ1Dto tq1Dto;

    /**
     * RXRセグメント
     */
    private List<RXRDto> rxrList;

    /**
     * RXCセグメント
     */
    private List<RXCDto> rxcList;

    /**
     *
     */
    private List<AdministrationDto> administrationList;

    /**
     * CTIセグメント
     */
    private List<CTIDto> ctiList;

    /**
     * @return the orcDto
     */
    public ORCDto getOrcDto() {
        return orcDto;
    }

    /**
     * @param orcDto the orcDto to set
     */
    public void setOrcDto(ORCDto orcDto) {
        this.orcDto = orcDto;
    }

    /**
     * @return the rxeDto
     */
    public RXEDto getRxeDto() {
        return rxeDto;
    }

    /**
     * @param tq1Dto the tq1Dto to set
     */
    public void setRxeDto(RXEDto rxeDto) {
        this.rxeDto = rxeDto;
    }

    /**
     * @return the tq1Dto
     */
    public TQ1Dto getTq1Dto() {
        return tq1Dto;
    }

    /**
     * @param tq1Dto the tq1Dto to set
     */
    public void setTq1Dto(TQ1Dto tq1Dto) {
        this.tq1Dto = tq1Dto;
    }

    /**
     * @return the rxrList
     */
    public List<RXRDto> getRxrList() {
        return rxrList;
    }

    /**
     * @param rxrList the rxrList to set
     */
    public void setRxrList(List<RXRDto> rxrList) {
        this.rxrList = rxrList;
    }

    /**
     * @return the rxcList
     */
    public List<RXCDto> getRxcList() {
        return rxcList;
    }

    /**
     * @param rxrList the rxrList to set
     */
    public void setRxcList(List<RXCDto> rxcList) {
        this.rxcList = rxcList;
    }

    /**
     * @return the orderList
     */
    public List<AdministrationDto> getAdministrationList() {
        return administrationList;
    }

    /**
     * @param orderList the orderList to set
     */
    public void setAdministrationList(List<AdministrationDto> administrationList) {
        this.administrationList = administrationList;
    }

    /**
     * @return the ctiList
     */
    public List<CTIDto> getCtiList() {
        return ctiList;
    }

    /**
     * @param rxrList the rxrList to set
     */
    public void setCtiList(List<CTIDto> ctiList) {
        this.ctiList = ctiList;
    }
}
