/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.OMP12;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXADto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXRDto;

/**
 *
 * @author kis-note
 */
public class AdministrationDto {
     /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(AdministrationDto.class);

    /**
     * RXAセグメント
     */
    private List<RXADto> rxaList;

    /**
     * RXRセグメント
     */
    private RXRDto rxrDto;

    /**
     * OBXセグメント
     */
    private List<OBXDto> obxList;

    /**
     * @return the rxaList
     */
    public List<RXADto> getRxaList() {
        return rxaList;
    }

    /**
     * @param rxaList the rxaList to set
     */
    public void setRxaList(List<RXADto> rxaList) {
        this.rxaList = rxaList;
    }

    /**
     * @return the rxrDto
     */
    public RXRDto getRxrDto() {
        return rxrDto;
    }

    /**
     * @param rxrDto the rxrDto to set
     */
    public void setRxrDto(RXRDto rxrDto) {
        this.rxrDto = rxrDto;
    }

    /**
     * @return the obxList
     */
    public List<OBXDto> getObxList() {
        return obxList;
    }

    /**
     * @param obxList the obxList to set
     */
    public void setObxList(List<OBXDto> obxList) {
        this.obxList = obxList;
    }
}