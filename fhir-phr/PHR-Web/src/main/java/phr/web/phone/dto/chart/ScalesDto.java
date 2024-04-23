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
public class ScalesDto {

    /**
     * グラフ表示設定
     */
    private List<AxeDto> yAxes;

    /**
     * デフォルトコンストラクタ
     */
    public ScalesDto() {
        
    }
    /**
     * コンストラクタ
     * @param suggestedMax
     * @param suggestedMin
     * @param stepSize 
     */
    public ScalesDto(int suggestedMax, int suggestedMin, int stepSize) {
        AxeDto dto  = new AxeDto(suggestedMax, suggestedMin, stepSize);
        yAxes = new ArrayList<>();
        yAxes.add(dto);
    }
    /**
     * グラフ表示設定
     *
     * @return the yAxes
     */
    public List<AxeDto> getyAxes() {
        return yAxes;
    }

    /**
     * グラフ表示設定
     *
     * @param yAxes the yAxes to set
     */
    public void setyAxes(List<AxeDto> yAxes) {
        this.yAxes = yAxes;
    }

    /**
     * グラフ表示設定
     *
     * @param yAxe
     */
    public void addyAxes(AxeDto yAxe) {
        if (yAxes == null) {
            yAxes = new ArrayList<>();
        }
        yAxes.add(yAxe);
    }
}
