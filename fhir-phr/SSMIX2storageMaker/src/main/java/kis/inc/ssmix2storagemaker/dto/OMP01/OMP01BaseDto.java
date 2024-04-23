/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.OMP01;

import java.util.List;

import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.AL1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;

/**
 *
 * @author kis-note
 */
public class OMP01BaseDto  extends ModelDto{
    /**
     * 作成日
     */
    private String cdate;
    /**
     * MSHフィールド
     */
    private MSHDto mshDto;
    /**
     * PIDフィールド
     */
    private PIDDto pidDto;
    /**
     * AL1フィールド
     */
    private List<AL1Dto> al1List;
    /**
     * PV1フィールド
     */
    private PV1Dto pv1Dto;
    /**
     *
     */
    private List<OrderDto> orderList;

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
     * @return the al1List
     */
    public List<AL1Dto> getAl1List() {
        return al1List;
    }

    /**
     * @param al1List the al1List to set
     */
    public void setAl1List(List<AL1Dto> al1List) {
        this.al1List = al1List;
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

}
