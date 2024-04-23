/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.ADT00;

import java.util.List;

import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.AL1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.DB1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IN1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.NK1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;

/**
 *
 * @author kis-note
 */
public class ADT00BaseDto  extends ModelDto{
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
     * NK1フィールド
     */
    private List<NK1Dto> nk1List;
    /**
     * PV1フィールド
     */
    private PV1Dto pv1Dto;
    /**
     * DB1フィールド
     */
    private List<DB1Dto> db1List;
    /**
     * OBXフィールド
     */
    private List<OBXDto> obxList;
    /**
     * AL1フィールド
     */
    private List<AL1Dto> al1List;
    /**
     * IN1フィールド
     */
    private List<IN1Dto> in1List;

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
     * @return the nk1List
     */
    public List<NK1Dto> getNk1List() {
        return nk1List;
    }

    /**
     * @param nk1List the nk1List to set
     */
    public void setNk1List(List<NK1Dto> nk1List) {
        this.nk1List = nk1List;
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
     * @return the db1List
     */
    public List<DB1Dto> getDb1List() {
        return db1List;
    }

    /**
     * @param db1List the db1List to set
     */
    public void setDb1List(List<DB1Dto> db1List) {
        this.db1List = db1List;
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
     * @return the in1List
     */
    public List<IN1Dto> getIn1List() {
        return in1List;
    }

    /**
     * @param in1List the in1List to set
     */
    public void setIn1List(List<IN1Dto> in1List) {
        this.in1List = in1List;
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
