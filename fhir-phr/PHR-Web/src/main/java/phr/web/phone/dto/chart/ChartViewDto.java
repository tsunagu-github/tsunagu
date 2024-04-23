/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto.chart;

/**
 *
 * @author daisuke
 */
public class ChartViewDto {
    
    /**
     * グラフの種別(line)
     */
    private String type;
    
    /**
     * 線の太さ
     */
    private int borderWidth;
    
    /**
     * Chartのデータ
     */
    private DataDto data;
    
    /**
     * Option設定
     */
    private OptionsDto options;

    /**
     * デフォルトコンストラクタ
     */
    public ChartViewDto () {
        type = "line";
        borderWidth = 1;
        data = new DataDto();
        options = new OptionsDto();
    }
    /**
     * グラフの種別(line)
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * グラフの種別(line)
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 線の太さ
     * @return the borderWidth
     */
    public int getBorderWidth() {
        return borderWidth;
    }

    /**
     * 線の太さ
     * @param borderWidth the borderWidth to set
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Chartのデータ
     * @return the data
     */
    public DataDto getData() {
        return data;
    }

    /**
     * Chartのデータ
     * @param data the data to set
     */
    public void setData(DataDto data) {
        this.data = data;
    }

    /**
     * Option設定
     * @return the options
     */
    public OptionsDto getOptions() {
        return options;
    }

    /**
     * Option設定
     * @param options the options to set
     */
    public void setOptions(OptionsDto options) {
        this.options = options;
    }
    
}
