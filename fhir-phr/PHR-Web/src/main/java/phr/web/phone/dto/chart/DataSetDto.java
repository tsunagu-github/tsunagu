/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto.chart;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daisuke
 */
public class DataSetDto {
    
    /**
     * ラベル(項目名+単位)
     */
    private String label;
    
    /**
     * 結果値
     */
    private List<Double> data;
    
    /**
     * 線曲(0:直線)
     */
    private int lineTension;
    
    /**
     * 空を無視して表示
     */
    private boolean spanGaps;
    /**
     * 線色
     */
    private String borderColor;
    /**
     * 背景色
     */
    private String backgroundColor;

    /**
     * デフォルトコンストラクタ
     */
    public DataSetDto() {
        data = new ArrayList<>();
        lineTension = 0;
        spanGaps = true;
        backgroundColor = "rgba(0,0,0,0)";
    }
    
    
    /**
     * ラベル(項目名+単位)
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * ラベル(項目名+単位)
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 結果値
     * @return the data
     */
    public List<Double> getData() {
        return data;
    }

    /**
     * 結果値
     * @param data the data to set
     */
    public void setData(List<Double> data) {
        this.data = data;
    }

    /**
     * 結果値を追加する
     * @param data 
     */
    public void addData(Double data) {
        if (this.data == null)
            this.data = new ArrayList<>();
        this.data.add(data);
    }
    /**
     * 線曲(0:直線)
     * @return the lineTension
     */
    public int getLineTension() {
        return lineTension;
    }

    /**
     * 線曲(0:直線)
     * @param lineTension the lineTension to set
     */
    public void setLineTension(int lineTension) {
        this.lineTension = lineTension;
    }

    /**
     * 空を無視して表示
     * @return the spanGaps
     */
    public boolean isSpanGaps() {
        return spanGaps;
    }

    /**
     * 空を無視して表示
     * @param spanGaps the spanGaps to set
     */
    public void setSpanGaps(boolean spanGaps) {
        this.spanGaps = spanGaps;
    }

    /**
     * 線色
     * @return the borderColor
     */
    public String getBorderColor() {
        return borderColor;
    }

    /**
     * 線色
     * @param borderColor the borderColor to set
     */
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * 背景色
     * @return the backgroundColor
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * 背景色
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    
   
}
