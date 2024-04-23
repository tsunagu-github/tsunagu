/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.dto;
import java.util.List;
import jp.kis_inc.csvconverter.src.dto.ConvertDto;

/**
 *
 * @author kis-note
 */
public class ConvertPhrIdDto extends  ConvertDto{
    
    /*
    * PHR_ID
    */
    private String phrId;
    
    /*
    * 検査結果項目ID込み
    */
    private List<ConvertResultObservationDto> cresultList;

	/**
     * @return the PHR_ID
     */
    public String getPhrId() {
        return phrId;
    }

    /**
     * @param examinationDate the PHR_ID to set
     */
    public void setPhrId(String phrId) {
        this.phrId = phrId;
    }

    /**
     * @return the cresultList
     */
    public List<ConvertResultObservationDto> getCresultList() {
        return cresultList;
    }

    /**
     * @param cresultList the cresultList to set
     */
    public void setCresultList(List<ConvertResultObservationDto> cresultList) {
        this.cresultList = cresultList;
    }

}
