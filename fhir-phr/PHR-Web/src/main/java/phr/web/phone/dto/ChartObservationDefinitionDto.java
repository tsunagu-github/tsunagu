/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

import phr.datadomain.entity.ChartObservationDefinitionEntity;

/**
 *
 * @author daisuke
 */
public class ChartObservationDefinitionDto {
    /* グラフ定義ID */
    protected long chartDefinitionId = 0;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* 項目名 */
    protected String observationDefinitionName = null;
    /* 線色 */
    protected String lineColor = null;
    /* 選択有無 */
    private boolean selected = false;
    /* データ入力種類コード */
    private String dataInputTypeCd = null;
    
    /**
     * グラフ定義IDを取得する
     *
     * @return
     */
    public long getChartDefinitionId() {
        return this.chartDefinitionId;
    }
    /**
     * グラフ定義IDを設定する
     *
     * @param value
     */
    public void setChartDefinitionId(long value) {
        this.chartDefinitionId = value;
    }

    /**
     * 項目名を取得する
     *
     * @return
     */
    public String getObservationDefinitionName() {
        return this.observationDefinitionName;
    }
    /**
     * 項目名を設定する
     *
     * @param value
     */
    public void setObservationDefinitionName(String value) {
        this.observationDefinitionName = value;
    }

    /**
     * 項目IDを取得する
     *
     * @return
     */
    public String getObservationDefinitionId() {
        return this.observationDefinitionId;
    }
    /**
     * 項目IDを設定する
     *
     * @param value
     */
    public void setObservationDefinitionId(String value) {
        this.observationDefinitionId = value;
    }

    /**
     * 線色を取得する
     *
     * @return
     */
    public String getLineColor() {
        return this.lineColor;
    }
    /**
     * 線色を設定する
     *
     * @param value
     */
    public void setLineColor(String value) {
        this.lineColor = value;
    }

    /**
     * 選択有無
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * 選択有無
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    /**
     * オブジェクトのコピーをする
     * @param entity 
     */
    public void copyTo(ChartObservationDefinitionEntity entity) {
        chartDefinitionId = entity.getChartDefinitionId();
        observationDefinitionId = entity.getObservationDefinitionId();
        observationDefinitionName = entity.getObservationDefinitionName();
        lineColor = entity.getLineColor();
        selected = entity.getLineColor() != null;
        dataInputTypeCd = entity.getDataInputTypeCd();
    }
    /**
     * オブジェクトのコピーをする
     * @param entity 
     */
    public void copyFrom(ChartObservationDefinitionEntity entity) {
         entity.setChartDefinitionId(chartDefinitionId);
         entity.setObservationDefinitionId(observationDefinitionId);
         entity.setObservationDefinitionName(observationDefinitionName);
         entity.setLineColor(lineColor);
         entity.setDataInputTypeCd(dataInputTypeCd);
    }
    /**
     * @return dataInputTypeCd
     */
    public String getDataInputTypeCd() {
        return dataInputTypeCd;
    }
    /*
     * @param dataInputTypeCd セットする dataInputTypeCd
     */
    public void setDataInputTypeCd(String dataInputTypeCd) {
        this.dataInputTypeCd = dataInputTypeCd;
    }
}
