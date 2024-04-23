/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

import java.util.Date;
import phr.web.phone.dto.chart.ChartViewDto;

/**
 *
 * @author daisuke
 */
public class ResponseViewChartDto extends ResponseBaseDto {

    /**
     * グラフデータ
     */
    private ChartViewDto chartData;

    /**
     * グラフ定義ID
     */
    private long chartDefinitionId;

    /**
     * 表示件数
     */
    private int viewCount;

    /**
     * 基準日
     */
    private String targetDate;
    /**
     * 最大OFFSET
     */
    private int maxOffSet;
    /**
     * 共通フラグ
     */
    private int commonFlg;

    /**
     * グラフデータ
     *
     * @return the chartData
     */
    public ChartViewDto getChartData() {
        return chartData;
    }

    /**
     * グラフデータ
     *
     * @param chartData the chartData to set
     */
    public void setChartData(ChartViewDto chartData) {
        this.chartData = chartData;
    }

    /**
     * グラフ定義ID
     *
     * @return the chartDefinitionId
     */
    public long getChartDefinitionId() {
        return chartDefinitionId;
    }

    /**
     * グラフ定義ID
     *
     * @param chartDefinitionId the chartDefinitionId to set
     */
    public void setChartDefinitionId(long chartDefinitionId) {
        this.chartDefinitionId = chartDefinitionId;
    }

    /**
     * 表示件数
     *
     * @return the viewCount
     */
    public int getViewCount() {
        return viewCount;
    }

    /**
     * 表示件数
     *
     * @param viewCount the viewCount to set
     */
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * 基準日
     *
     * @return the targetDate
     */
    public String getTargetDate() {
        return targetDate;
    }

    /**
     * 基準日
     *
     * @param targetDate the targetDate to set
     */
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    /**
     * 最大OFFSET
     *
     * @return the maxOffSet
     */
    public int getMaxOffSet() {
        return maxOffSet;
    }

    /**
     * 最大OFFSET
     *
     * @param maxOffSet the maxOffSet to set
     */
    public void setMaxOffSet(int maxOffSet) {
        this.maxOffSet = maxOffSet;
    }

    /**
     * 共通フラグ
     *
     * @return the commonFlg
     */
    public int getCommonFlg() {
        return commonFlg;
    }

    /**
     * 共通フラグ
     *
     * @param commonFlg the commonFlg to set
     */
    public void setCommonFlg(int commonFlg) {
        this.commonFlg = commonFlg;
    }

}
