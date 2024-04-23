/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

/**
 *
 * @author daisuke
 */
public class DiseaseTypeDto {
    
    /**
     * 疾病種別CD
     */
    private Integer key;
    /**
     * 疾病種別名
     */
    private String value;
    
    /**
     * 疾病有無
     */
    private boolean checked;

    /**
     * 疾病種別CD
     * @return the key
     */
    public Integer getKey() {
        return key;
    }

    /**
     * 疾病種別CD
     * @param key the key to set
     */
    public void setKey(Integer key) {
        this.key = key;
    }

    /**
     * 疾病種別名
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * 疾病種別名
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 疾病有無
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * 疾病有無
     * @param checked the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
      
    
}
