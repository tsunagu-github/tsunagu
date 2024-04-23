/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto;

/**
 *
 * @author kis-note
 * 標準ストレージの区切り文字用のDto
 */
public class messageSplitDto {
    /**
     * フィールドセパレーター
     */
    private String fieldseparator;
    
    /**
    * 成分セパレータ
    */
    private String compseparator;
    
    /**
     * 反復セパレータ
     */
    private String repeatseparator;
    
    /**
     * エスケープ文字
     */
    private String escape;
    
    /**
     * 副成分セパレータ
     */
    private String subcomp;

    /**
     * @return the fieldseparator
     */
    public String getFieldseparator() {
        return fieldseparator;
    }

    /**
     * @param fieldseparator the fieldseparator to set
     */
    public void setFieldseparator(String fieldseparator) {
        this.fieldseparator = fieldseparator;
    }

    /**
     * @return the compseparator
     */
    public String getCompseparator() {
        return compseparator;
    }

    /**
     * @param compseparator the compseparator to set
     */
    public void setCompseparator(String compseparator) {
        this.compseparator = compseparator;
    }

    /**
     * @return the repeatseparator
     */
    public String getRepeatseparator() {
        return repeatseparator;
    }

    /**
     * @param repeatseparator the repeatseparator to set
     */
    public void setRepeatseparator(String repeatseparator) {
        this.repeatseparator = repeatseparator;
    }

    /**
     * @return the escape
     */
    public String getEscape() {
        return escape;
    }

    /**
     * @param escape the escape to set
     */
    public void setEscape(String escape) {
        this.escape = escape;
    }

    /**
     * @return the subcomp
     */
    public String getSubcomp() {
        return subcomp;
    }

    /**
     * @param subcomp the subcomp to set
     */
    public void setSubcomp(String subcomp) {
        this.subcomp = subcomp;
    }
    
}
