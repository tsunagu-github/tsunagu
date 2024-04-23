/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import phr.datadomain.entity.ChartColorEntity;
import phr.datadomain.entity.ChartDefinitionEntity;
import phr.datadomain.entity.ChartObservationDefinitionEntity;
import phr.datadomain.entity.ObservationEntity;

/**
 *
 * @author daisuke
 */
public interface IChartManagementService {

    /**
     * グラフリストを取得する
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    List<ChartDefinitionEntity> getChartList(String phrId) throws Throwable;

    /**
     * グラフ定義を取得する
     *
     * @param chartDefinitionId
     * @return
     * @throws Throwable
     */
    ChartDefinitionEntity getChartDefinition(Long chartDefinitionId) throws Throwable;

    /**
     * グラフ定義を取得する
     *
     * @param chartDefinitionId
     * @param dataInputType
     * @return
     * @throws Throwable
     */
    ChartDefinitionEntity getChartDefinition(Long chartDefinitionId, Integer dataInputType) throws Throwable;

    /**
     * グラフ定義を取得する
     *
     * @param phrId
     * @param dataInputType
     * @return
     * @throws Throwable
     */
    ChartDefinitionEntity getNewChartDefinition(String phrId, Integer dataInputType) throws Throwable;

    /**
     * グラフ表示データを取得する
     *
     * @param phrId
     * @param chartDefinitionId
     * @param targetDate
     * @return
     * @throws Throwable
     */
    Map<String, Map<ChartObservationDefinitionEntity, ObservationEntity>> getViewChartData(String phrId, Long chartDefinitionId, Date targetDate) throws Throwable;

    /**
     * グラフ定義を登録する
     *
     * @param entity
     * @throws Throwable
     */
    void entryChartDefinition(ChartDefinitionEntity entity) throws Throwable;

    /**
     * グラフ定義を削除する
     *
     * @param chartDefinitionId
     * @throws Throwable
     */
    void deleteChartDefinition(long chartDefinitionId) throws Throwable;

    /**
     * 色リストを取得する
     *
     * @return
     * @throws Throwable
     */
    List<ChartColorEntity> getColorList() throws Throwable;

    /**
     * 色リストを取得する
     *
     * @return
     * @throws Throwable
     */
    Map<String, ChartColorEntity> getColorMap() throws Throwable;
}
