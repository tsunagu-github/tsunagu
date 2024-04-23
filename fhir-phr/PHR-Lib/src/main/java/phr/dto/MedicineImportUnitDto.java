/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.dto;

import java.util.List;

/**
 *
 * @author kis-note-027_user
 */
public class MedicineImportUnitDto {
//    private int tempNo = 0;
    private int unitOrder = 0;
    private String importText= null;
    private String[] importTextSplit = null;
    private int shootOrder = 0;
    private int startRecipeNo = 0;
    private int endRecipeNo=0;
    private boolean startRecoreNO=true;
    private boolean fullSentence = true;
    private boolean separatorRecord = false;

    /**
     * @return the tempNo
     */
//    public int getTempNo() {
//        return tempNo;
//    }
//
//    /**
//     * @param tempNo the tempNo to set
//     */
//    public void setTempNo(int tempNo) {
//        this.tempNo = tempNo;
//    }

    /**
     * @return the unitOrder
     */
    public int getUnitOrder() {
        return unitOrder;
    }

    /**
     * @param unitOrder the unitOrder to set
     */
    public void setUnitOrder(int unitOrder) {
        this.unitOrder = unitOrder;
    }

    /**
     * @return the importText
     */
    public String getImportText() {
        return importText;
    }

    /**
     * @param importText the importText to set
     */
    public void setImportText(String importText) {
        this.importText = importText;
    }

    /**
     * @return the shootOrder
     */
    public int getShootOrder() {
        return shootOrder;
    }

    /**
     * @param shootOrder the shootOrder to set
     */
    public void setShootOrder(int shootOrder) {
        this.shootOrder = shootOrder;
    }

    /**
     * @return the startRecipeNo
     */
    public int getStartRecipeNo() {
        return startRecipeNo;
    }

    /**
     * @param startRecipeNo the startRecipeNo to set
     */
    public void setStartRecipeNo(int startRecipeNo) {
        this.startRecipeNo = startRecipeNo;
    }

    /**
     * @return the endRecipeNo
     */
    public int getEndRecipeNo() {
        return endRecipeNo;
    }

    /**
     * @param endRecipeNo the endRecipeNo to set
     */
    public void setEndRecipeNo(int endRecipeNo) {
        this.endRecipeNo = endRecipeNo;
    }

    /**
     * @return the startRecoreNO
     */
    public boolean isStartRecoreNO() {
        return startRecoreNO;
    }

    /**
     * @param startRecoreNO the startRecoreNO to set
     */
    public void setStartRecoreNO(boolean startRecoreNO) {
        this.startRecoreNO = startRecoreNO;
    }

    /**
     * @return the fullSentence
     */
    public boolean isFullSentence() {
        return fullSentence;
    }

    /**
     * @param fullSentence the fullSentence to set
     */
    public void setFullSentence(boolean fullSentence) {
        this.fullSentence = fullSentence;
    }
    
    /**
     * @return the importText
     */
    public String[] getImportTextSplit() {
        if(importTextSplit==null){
            setImportTextSplit();
        }
        return importTextSplit;
    }

    /**
     * @param importText the importText to set
     */
    public void setImportTextSplit() {
        this.importTextSplit = this.importText.split("\r\n");
    }

    /**
     * @return the separatorRecord
     */
    public boolean isSeparatorRecord() {
        return separatorRecord;
    }

    /**
     * @param separatorRecord the separatorRecord to set
     */
    public void setSeparatorRecord(boolean separatorRecord) {
        this.separatorRecord = separatorRecord;
    }
    
    
}
