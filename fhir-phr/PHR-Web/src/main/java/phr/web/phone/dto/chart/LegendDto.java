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
public class LegendDto {

    /**
     * ラベル要素
     */
    private LabelsDto labels;

    /**
     * ラベル要素
     *
     * @return the labels
     */
    public LabelsDto getLabels() {
        return labels;
    }

    /**
     * ラベル要素
     *
     * @param labels the labels to set
     */
    public void setLabels(LabelsDto labels) {
        this.labels = labels;
    }

}
