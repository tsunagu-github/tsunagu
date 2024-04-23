/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

import java.util.ArrayList;
import java.util.List;
import phr.datadomain.entity.ChartDefinitionEntity;
import phr.datadomain.entity.ChartObservationDefinitionEntity;

/**
 *
 * @author daisuke
 */
public class ChartDefinitionDto {
    /* グラフ定義ID */
    protected long chartDefinitionId = 0;
    /* グラフ定義名称 */
    protected String chartDefinitionName = null;
    /* 表示回数 */
    protected int viewCount = 0;
    /* 共通フラグ */
    protected int commonFlg = 0;
    /**
     * データ入力種別(1：検査  2：自己測定  3：健診)
     */
    private Integer dataInputType;
   
    /**
     * チェックボックスの選択状況
     */
    public List<Boolean> checkFlg;
    
    /**
     * グラフ項目リスト
     */
    private List<ChartObservationDefinitionDto> chartObservationList = null;
    
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
     * グラフ定義名称を取得する
     *
     * @return
     */
    public String getChartDefinitionName() {
        return this.chartDefinitionName;
    }
    /**
     * グラフ定義名称を設定する
     *
     * @param value
     */
    public void setChartDefinitionName(String value) {
        this.chartDefinitionName = value;
    }

    /**
     * 表示回数を取得する
     *
     * @return
     */
    public int getViewCount() {
        return this.viewCount;
    }
    /**
     * 表示回数を設定する
     *
     * @param value
     */
    public void setViewCount(int value) {
        this.viewCount = value;
    }

    /**
     * グラフ項目リストを取得する
     * @return the chartObservationList
     */
    public List<ChartObservationDefinitionDto> getChartObservationList() {
        return chartObservationList;
    }

    /**
     * グラフ項目リストを設定する
     * @param chartObservationList the chartObservationList to set
     */
    public void setChartObservationList(List<ChartObservationDefinitionDto> chartObservationList) {
        this.chartObservationList = chartObservationList;
    }
    /**
     * グラフ項目リストを追加する
     * @param dto
     */
    public void addChartObservationList(ChartObservationDefinitionDto dto) {
       if (chartObservationList == null) {
           chartObservationList = new ArrayList<>();
       }
       chartObservationList.add(dto);
    }
    
    /**
     * 値をDTOにコピーする
     * @param entity 
     */
    public void copyTo(ChartDefinitionEntity entity) {
        this.chartDefinitionId = entity.getChartDefinitionId();
        this.chartDefinitionName = entity.getChartDefinitionName();
        this.viewCount = entity.getViewCount();
        this.dataInputType = entity.getDataInputTypeCd();
        this.commonFlg = entity.getCommonFlg();
   }

    /**
     * DTOを元にEntityにコピーする
     * @param entity 
     */
    public void copyFrom(ChartDefinitionEntity entity) {
        entity.setChartDefinitionId(this.chartDefinitionId);
        entity.setChartDefinitionName(chartDefinitionName);
        if (this.dataInputType != null) {
            entity.setDataInputTypeCd(this.dataInputType);
        } else {
            entity.setDataInputTypeCd(9);
        }
        entity.setViewCount(this.viewCount);
        
        List<ChartObservationDefinitionEntity> list = new ArrayList<>();
        for (ChartObservationDefinitionDto obDto : this.chartObservationList) {
            if (!obDto.isSelected())
                continue;
            ChartObservationDefinitionEntity obEntity = new ChartObservationDefinitionEntity();
            obDto.copyFrom(obEntity);
            list.add(obEntity);
        }
        entity.setChartObservationDefinitionList(list);
    }
    /**
     * データ入力種別(1：検査  2：自己測定  3：健診)
     * @return the dataInputType
     */
    public Integer getDataInputType() {
        return dataInputType;
    }

    /**
     * データ入力種別(1：検査  2：自己測定  3：健診)
     * @param dataInputType the dataInputType to set
     */
    public void setDataInputType(Integer dataInputType) {
        this.dataInputType = dataInputType;
    }

    /**
     * チェックボックスの選択状況
     *
     * @return the checkFlg
     */
    public List<Boolean> getCheckFlg() {
        return checkFlg;
    }

    /**
     * チェックボックスの選択状況
     *
     * @param checkFlg the checkFlg to set
     */
    public void setCheckFlg(List<Boolean> checkFlg) {
        this.checkFlg = checkFlg;
    }
}
