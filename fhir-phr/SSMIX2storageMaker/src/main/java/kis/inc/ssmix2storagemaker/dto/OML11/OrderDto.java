/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.OML11;

import java.util.List;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.SPMDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
     * SPMセグメント
     */
    private SPMDto spmDto;

    /**
     * OBRセグメント
     */
    private OBRDto obrDto;

    /**
     * ORCセグメント
     */
    private ORCDto orcDto;
    /**
     * OBXセグメント
     * 複数対応
     */
    private List<OBXDto> obxList;

    /**
     * @return the spmDto
     */
    public SPMDto getSpmDto() {
        return spmDto;
    }

    /**
     * @param spmDto the spmDto to set
     */
    public void setSpmDto(SPMDto spmDto) {
        this.spmDto = spmDto;
    }

    /**
     * @return the obrDto
     */
    public OBRDto getObrDto() {
        return obrDto;
    }

    /**
     * @param obrDto the obrDto to set
     */
    public void setObrDto(OBRDto obrDto) {
        this.obrDto = obrDto;
    }

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
