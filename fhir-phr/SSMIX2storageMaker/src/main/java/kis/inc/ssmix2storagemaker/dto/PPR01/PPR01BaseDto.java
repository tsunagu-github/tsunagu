/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.PPR01;

import java.util.List;

import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;

/**
 *
 * @author kis-note
 */
public class PPR01BaseDto extends ModelDto {
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
     * 
     */
    private List<ProblemDto> problemList;

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
     * @return the problemList
     */
    public List<ProblemDto> getProblemList() {
        return problemList;
    }

    /**
     * @param problemList the problemList to set
     */
    public void setProblemList(List<ProblemDto> problemList) {
        this.problemList = problemList;
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
