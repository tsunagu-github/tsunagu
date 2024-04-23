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
public class LabelsDto {

    /**
     * 凡例BOXのサイズ
     */
    private int boxWidth;

    /**
     * 凡例の文字サイズ
     */
    private int boxWidthfontSize;

    /**
     * デフォルトコンストラクタ
     */
    public LabelsDto() {

    }

    /**
     * コンストラクタ
     *
     * @param boxWidth
     * @param boxWidthfontSize
     */
    public LabelsDto(int boxWidth, int boxWidthfontSize) {
        this.boxWidth = boxWidth;
        this.boxWidthfontSize = boxWidthfontSize;
    }

    /**
     * 凡例BOXのサイズ
     *
     * @return the boxWidth
     */
    public int getBoxWidth() {
        return boxWidth;
    }

    /**
     * 凡例BOXのサイズ
     *
     * @param boxWidth the boxWidth to set
     */
    public void setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
    }

    /**
     * 凡例の文字サイズ
     *
     * @return the boxWidthfontSize
     */
    public int getBoxWidthfontSize() {
        return boxWidthfontSize;
    }

    /**
     * 凡例の文字サイズ
     *
     * @param boxWidthfontSize the boxWidthfontSize to set
     */
    public void setBoxWidthfontSize(int boxWidthfontSize) {
        this.boxWidthfontSize = boxWidthfontSize;
    }

}
