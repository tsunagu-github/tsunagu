/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.dto.ObservationEntryDto;

/**
 *
 * @author kis.o-note-002
 */
public class SpecificMedicalCheakUpForm extends AbstractForm {
    /**
     * serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /*
    * コンストラクタ
    */
    public SpecificMedicalCheakUpForm(){}
    
    /**
     *  ダウンロードボタン制御
     */
    private boolean downloadBtnCtl;
    public boolean getDownloadBtnCtl() {
        return downloadBtnCtl;
    }

    public void setDownloadBtnCtl(boolean downloadBtnCtl) {
        this.downloadBtnCtl = downloadBtnCtl;
    }
    
    /**
     *  csvファイル名
     */
    private String fileCsv;
    public String getFileCsv() {
        return fileCsv;
    }

    public void setFileCsv(String fileCsv) {
        this.fileCsv = fileCsv;
    }

    /**
     *  登録結果出力エリア
     */
    private List<String> insertResulrArea;
    public List<String> getInsertResulrArea() {
        return insertResulrArea;
    }

    public void setInsertResulrArea(List<String> insertResulrArea) {
        this.insertResulrArea = insertResulrArea;
    }

    /**
     *  PHR_ID
     */
    private String phrId;
    public String getPhrId() {
        return phrId;
    }

    public void setPhrId(String phrId) {
        this.phrId = phrId;
    }

    /**
     *  保険者番号
     */
    private String insureNo;
    public String getInsureNo() {
        return insureNo;
    }

    public void setInsureNo(String insureNo) {
        this.insureNo = insureNo;
    }

    /**
     *  検査日
     */
    private String examinationDate;
    public String getExaminationDate(){
        return examinationDate;
    }
    
    public void setExaminationDate(String examinationDate){
        this.examinationDate = examinationDate;
    }
    
    /*
    * 上書きflg
    */
    private boolean updateFlg;

    /**
     * @return the updateFlg
     */
    public boolean isUpdateFlg() {
        return updateFlg;
    }

    /**
     * @param updateFlg the updateFlg to set
     */
    public void setUpdateFlg(boolean updateFlg) {
        this.updateFlg = updateFlg;
    }
    
    /*
     * 登録情報
     */
    private List<ObservationEntryDto> observationEntryDto=null;
    public List<ObservationEntryDto> getObservationEntryDto() {
        return observationEntryDto;
    }
    public void setObservationEntryDto(List<ObservationEntryDto> observationEntryDto) {
        this.observationEntryDto = observationEntryDto;
    }
}
