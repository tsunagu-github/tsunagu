/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.OML11;

import java.util.List;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;

/**
 *
 * @author kis-note
 */
public class OML11BaseDto extends ModelDto{
    /**
     * 診療日
     */
    private String cdate;
    
    /**
     * イベントID
     */
    private String eventId;

    /**
     * MSHフィールド
     */
    private MSHDto mshDto;
    /**
     * PIDフィールド
     */
    private PIDDto pidDto;
    /**
     * 
     */
    private PV1Dto pv1Dto;
    /**
     * 
     */
    private List<OrderDto> orderList;

    /**
     * @return the msdDto
     */
    public MSHDto getMshDto() {
        return mshDto;
    }

    /**
     * @param msdDto the msdDto to set
     */
    public void setMshDto(MSHDto msdDto) {
        this.mshDto = msdDto;
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
     * @return the orderList
     */
    public List<OrderDto> getOrderList() {
        return orderList;
    }

    /**
     * @param orderList the orderList to set
     */
    public void setOrderList(List<OrderDto> orderList) {
        this.orderList = orderList;
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

    /**
     * @return the eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    
}
