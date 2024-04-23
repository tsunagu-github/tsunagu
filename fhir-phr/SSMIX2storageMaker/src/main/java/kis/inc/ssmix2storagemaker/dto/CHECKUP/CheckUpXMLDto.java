/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.CHECKUP;

import java.util.List;
import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;
import kis.inc.ssmix2storagemaker.dto.ModelDto;

/**
 *
 * @author kis-note-027_user
 */
public class CheckUpXMLDto extends ModelDto{
    /*
    * 保険者番号
    */
    private String insureNo;
    
    /*
    * 検査日
    */
    private String examinationDate;    

    /*
    * 検査結果リスト
    */
    private List<ResultObservationDto> observationList;
    
    private String filePath;

    /**
     * @return the insureNo
     */
    public String getInsureNo() {
        return insureNo;
    }

    /**
     * @param insureNo the insureNo to set
     */
    public void setInsureNo(String insureNo) {
        this.insureNo = insureNo;
    }

    /**
     * @return the examinationDate
     */
    public String getExaminationDate() {
        return examinationDate;
    }

    /**
     * @param examinationDate the examinationDate to set
     */
    public void setExaminationDate(String examinationDate) {
        this.examinationDate = examinationDate;
    }    

    /**
     * @return the observationList
     */
    public List<ResultObservationDto> getObservationList() {
        return observationList;
    }

    /**
     * @param observationList the observationList to set
     */
    public void setObservationList(List<ResultObservationDto> observationList) {
        this.observationList = observationList;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
}
