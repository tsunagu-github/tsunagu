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
public class DataDto {
    
    /**
     * 縦軸のラベル
     */
    private List<String> labels;
    
    /**
     * データ
     */
    private List<DataSetDto> datasets; 

    /**
     * 縦軸のラベル
     * @return the labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * 縦軸のラベル
     * @param labels the labels to set
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /**
     * 縦軸のラベル
     * @param label 
     */
    public void addLabel(String label) {
        if (this.labels == null)
            this.labels = new ArrayList<>();
        this.labels.add(label);
        
    }
    /**
     * データ
     * @return the datasets
     */
    public List<DataSetDto> getDatasets() {
        return datasets;
    }

    /**
     * データ
     * @param datasets the datasets to set
     */
    public void setDatasets(List<DataSetDto> datasets) {
        this.datasets = datasets;
    }
    
    /**
     * データ
     * @param dataset 
     */
    public void addDataset(DataSetDto dataset) {
        if (this.datasets == null)
            this.datasets = new ArrayList<>();
        this.datasets.add(dataset);
    }
}
