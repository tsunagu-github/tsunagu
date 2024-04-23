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
public class OptionsDto {

    /**
     * グラフのタイトル
     */
    private TitleDto title;

    /**
     * グラフスケール設定
     */
    private ScalesDto scales;
    /**
     * 凡例
     */
    private LegendDto legend;

    /**
     * グラフのタイトル
     *
     * @return the title
     */
    public TitleDto getTitle() {
        return title;
    }

    /**
     * グラフのタイトル
     *
     * @param title the title to set
     */
    public void setTitle(TitleDto title) {
        this.title = title;
    }

    /**
     * グラフスケール設定
     *
     * @return the scales
     */
    public ScalesDto getScales() {
        return scales;
    }

    /**
     * グラフスケール設定
     *
     * @param scales the scales to set
     */
    public void setScales(ScalesDto scales) {
        this.scales = scales;
    }

    /**
     * 凡例
     *
     * @return the legend
     */
    public LegendDto getLegend() {
        return legend;
    }

    /**
     * 凡例
     *
     * @param legend the legend to set
     */
    public void setLegend(LegendDto legend) {
        this.legend = legend;
    }

}
