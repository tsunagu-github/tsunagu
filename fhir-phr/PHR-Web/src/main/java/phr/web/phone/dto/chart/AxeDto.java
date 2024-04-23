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
public class AxeDto {

    /**
     * グラフ設定
     */
    private TicksDto ticks;
    
    /**
     * デフォルトコンストラクタ
     */
    public AxeDto() {
        
    }
    /**
     * コンストラクタ
     * @param suggestedMax
     * @param suggestedMin
     * @param stepSize 
     */
    public AxeDto(int suggestedMax, int suggestedMin, int stepSize) {
        ticks = new TicksDto(suggestedMax, suggestedMin, stepSize);
    }
    /**
     * グラフ設定
     *
     * @return the ticks
     */
    public TicksDto getTicks() {
        return ticks;
    }

    /**
     * グラフ設定
     *
     * @param ticks the ticks to set
     */
    public void setTicks(TicksDto ticks) {
        this.ticks = ticks;
    }

}
