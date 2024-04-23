/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.dto;

/**
 *
 * @author KISNOTE011
 */
public class YearListDto {
    // 年
    private int yearValue;
    public int getYearValue(){
        return this.yearValue;
    }
    public void setYearValue(int value){
        this.yearValue=value;
    }

    // label情報
    private String yearLabel;
    public String getYearLabel(){
        return this.yearLabel;
    }
    public void setYearLabel(String value){
        this.yearLabel=value;
    }

    // 選択情報
    private String selectedOption;
    public String getSelectedOption(){
        return this.selectedOption;
    }
    public void setSelectedOption(String value){
        this.selectedOption=value;
    }
}
