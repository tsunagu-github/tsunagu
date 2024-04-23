/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

/**
 *
 * @author kis-note-027_user
 */
public class DosagePatientInputEntityDto {
    /* 調剤ID */
    private String dosageId = null;
    /* 調剤番号 */
    private int seq = 0;
    /* 患者記入番号 */
    private int inputSeq = 0;
    /* 患者記入情報 */
    private String inputText = null;
    /* 入力年月日 */
    private String inputDate = null;    

    /**
     * @return the dosageId
     */
    public String getDosageId() {
        return dosageId;
    }

    /**
     * @param dosageId the dosageId to set
     */
    public void setDosageId(String dosageId) {
        this.dosageId = dosageId;
    }

    /**
     * @return the seq
     */
    public int getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * @return the inputSeq
     */
    public int getInputSeq() {
        return inputSeq;
    }

    /**
     * @param inputSeq the inputSeq to set
     */
    public void setInputSeq(int inputSeq) {
        this.inputSeq = inputSeq;
    }

    /**
     * @return the inputText
     */
    public String getInputText() {
        return inputText;
    }

    /**
     * @param inputText the inputText to set
     */
    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    /**
     * @return the inputDate
     */
    public String getInputDate() {
        return inputDate;
    }

    /**
     * @param inputDate the inputDate to set
     */
    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }
}
