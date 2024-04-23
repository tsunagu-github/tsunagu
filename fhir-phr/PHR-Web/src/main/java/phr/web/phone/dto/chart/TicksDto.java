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
public class TicksDto {

    /**
     * グラフスケールの最大値
     */
    private int suggestedMax;
    /**
     * グラフスケールの最小値
     */
    private int suggestedMin;

    /**
     * グラフスケールのStepサイズ
     */
    private int stepSize;

    /**
     * デフォルトコンストラクタ
     */
    public TicksDto() {

    }

    /**
     * コンストラクタ
     * @param suggestedMax
     * @param suggestedMin
     * @param stepSize 
     */
    public TicksDto(int suggestedMax, int suggestedMin, int stepSize) {
        this.suggestedMax = suggestedMax;
        this.suggestedMin = suggestedMin;
        this.stepSize = stepSize;
    }

    /**
     * グラフスケールの最大値
     *
     * @return the suggestedMax
     */
    public int getSuggestedMax() {
        return suggestedMax;
    }

    /**
     * グラフスケールの最大値
     *
     * @param suggestedMax the suggestedMax to set
     */
    public void setSuggestedMax(int suggestedMax) {
        this.suggestedMax = suggestedMax;
    }

    /**
     * グラフスケールの最小値
     *
     * @return the suggestedMin
     */
    public int getSuggestedMin() {
        return suggestedMin;
    }

    /**
     * グラフスケールの最小値
     *
     * @param suggestedMin the suggestedMin to set
     */
    public void setSuggestedMin(int suggestedMin) {
        this.suggestedMin = suggestedMin;
    }

    /**
     * グラフスケールのStepサイズ
     *
     * @return the stepSize
     */
    public int getStepSize() {
        return stepSize;
    }

    /**
     * グラフスケールのStepサイズ
     *
     * @param stepSize the stepSize to set
     */
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }
}
