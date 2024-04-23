/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 */
public class BookMemoDto {
    
    //手帳メモ情報
    private String memoInfo;
    
    //メモ入力年月日
    private String inputDate;
    
    //レコード作成者
    private String recoder;

    /**
     * @return the memoInfo
     */
    public String getMemoInfo() {
        return memoInfo;
    }

    /**
     * @param memoInfo the memoInfo to set
     */
    public void setMemoInfo(String memoInfo) {
        this.memoInfo = memoInfo;
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

    /**
     * @return the recoder
     */
    public String getRecoder() {
        return recoder;
    }

    /**
     * @param recoder the recoder to set
     */
    public void setRecoder(String recoder) {
        this.recoder = recoder;
    }
    
    public BookMemoDto setBookMemo(String line){
        BookMemoDto dto = new BookMemoDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2) dto.setMemoInfo(elements[1]);
        if(size >= 3) dto.setInputDate(elements[2]);
        if(size >= 4) dto.setRecoder(elements[3]);
        
        
        return dto;
    }
    
    
}
