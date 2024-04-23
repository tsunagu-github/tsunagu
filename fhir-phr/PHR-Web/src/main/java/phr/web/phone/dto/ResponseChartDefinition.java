/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daisuke
 */
public class ResponseChartDefinition extends ResponseBaseDto {

    /**
     * グラフ定義リスト
     */
    private List<ChartDefinitionDto> chartDefinitionList;

    /**
     * 対象グラフ定義
     */
    private ChartDefinitionDto targetChartDefinition;

    /**
     * 色リスト
     */
    private List<CodeValueDto> colorList;

    /**
     * グラフ定義ID
     */
    private Long chartDefinitionId;

    /**
     * グラフ定義リスト
     *
     * @return the chartDefinitionList
     */
    public List<ChartDefinitionDto> getChartDefinitionList() {
        return chartDefinitionList;
    }

    /**
     * グラフ定義リスト
     *
     * @param chartDefinitionList the chartDefinitionList to set
     */
    public void setChartDefinitionList(List<ChartDefinitionDto> chartDefinitionList) {
        this.chartDefinitionList = chartDefinitionList;
    }

    /**
     * 対象グラフ定義
     *
     * @return the targetChartDefinition
     */
    public ChartDefinitionDto getTargetChartDefinition() {
        return targetChartDefinition;
    }

    /**
     * 対象グラフ定義
     *
     * @param targetChartDefinition the targetChartDefinition to set
     */
    public void setTargetChartDefinition(ChartDefinitionDto targetChartDefinition) {
        this.targetChartDefinition = targetChartDefinition;
    }

    /**
     * 色リスト
     * @return the colorList
     */
    public List<CodeValueDto> getColorList() {
        return colorList;
    }

    /**
     * 色リスト
     * @param colorList the colorList to set
     */
    public void setColorList(List<CodeValueDto> colorList) {
        this.colorList = colorList;
    }

    /**
     * 色リストを追加する
     * @param dto 
     */
    public void addColor(CodeValueDto dto) {
        if (this.colorList == null)
            this.colorList = new ArrayList<>();
        this.colorList.add(dto);
    }

	/**
	 * @return chartDefinitionId
	 */
	public Long getChartDefinitionId() {
		return chartDefinitionId;
	}

	/**
	 * @param chartDefinitionId セットする chartDefinitionId
	 */
	public void setChartDefinitionId(Long chartDefinitionId) {
		this.chartDefinitionId = chartDefinitionId;
	}
}
