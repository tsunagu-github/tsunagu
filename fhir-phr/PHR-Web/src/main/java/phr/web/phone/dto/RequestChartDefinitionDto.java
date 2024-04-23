/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

/**
 *
 * @author daisuke
 */
public class RequestChartDefinitionDto extends RequestBaseDto {

    /**
     * グラフ定義Action LIST：一覧取得 NEW：新規作成 EDIT：修正 DEL：削除 ENTRY：登録
     */
    private String charttDefinitionAction;

    /**
     * グラフ定義ID
     */
    private Long chartDefinitionId;
    /**
     * データ入力種別(1：検査 2：自己測定 3：健診)
     */
    private Integer dataInputType;

    /**
     * 対象日
     */
    private String targetDate;
    /**
     * 表示件数
     */
    private int viewCount;
    /**
     * OFFSET
     */
    private int offSet;

    /**
     * Entryグラフ定義
     */
    private ChartDefinitionDto entryChartDefinitionDto;

    /**
     * チェックボックス
     */
    private boolean checkboxFlg;

    /**
     * グラフ定義Action
     *
     * @return the charttDefinitionAction
     */
    public String getCharttDefinitionAction() {
        return charttDefinitionAction;
    }

    /**
     * グラフ定義Action
     *
     * @param charttDefinitionAction the charttDefinitionAction to set
     */
    public void setCharttDefinitionAction(String charttDefinitionAction) {
        this.charttDefinitionAction = charttDefinitionAction;
    }

    /**
     * グラフ定義ID
     *
     * @return the chartDefinitionId
     */
    public Long getChartDefinitionId() {
        return chartDefinitionId;
    }

    /**
     * グラフ定義ID
     *
     * @param chartDefinitionId the chartDefinitionId to set
     */
    public void setChartDefinitionId(Long chartDefinitionId) {
        this.chartDefinitionId = chartDefinitionId;
    }

    /**
     * 対象日
     *
     * @return the targetDate
     */
    public String getTargetDate() {
        return targetDate;
    }

    /**
     * 対象日
     *
     * @param targetDate the targetDate to set
     */
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    /**
     * データ入力種別(1：検査 2：自己測定 3：健診)
     *
     * @return the dataInputType
     */
    public Integer getDataInputType() {
        return dataInputType;
    }

    /**
     * データ入力種別(1：検査 2：自己測定 3：健診)
     *
     * @param dataInputType the dataInputType to set
     */
    public void setDataInputType(Integer dataInputType) {
        this.dataInputType = dataInputType;
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
     * OFFSET
     *
     * @return the offSet
     */
    public int getOffSet() {
        return offSet;
    }

    /**
     * OFFSET
     *
     * @param offSet the offSet to set
     */
    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    /**
     * Entryグラフ定義
     *
     * @return the entryChartDefinitionDto
     */
    public ChartDefinitionDto getEntryChartDefinitionDto() {
        return entryChartDefinitionDto;
    }

    /**
     * Entryグラフ定義
     *
     * @param entryChartDefinitionDto the entryChartDefinitionDto to set
     */
    public void setEntryChartDefinitionDto(ChartDefinitionDto entryChartDefinitionDto) {
        this.entryChartDefinitionDto = entryChartDefinitionDto;
    }

    /**
     * @return checkboxFlg
     */
    public boolean isCheckboxFlg() {
        return checkboxFlg;
    }

    /**
    * @param checkboxFlg セットする checkboxFlg
    */
    public void setCheckboxFlg(boolean checkboxFlg) {
        this.checkboxFlg = checkboxFlg;
    }
}
