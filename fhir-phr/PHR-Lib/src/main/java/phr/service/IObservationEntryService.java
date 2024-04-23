/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;

/**
 *
 * @author kis-note-025
 */
public interface IObservationEntryService {
    
    
    /**
     * 検査依頼IDを使用した検査結果検索
     * @param phrid
     * @param orderNo
     * @return
     * @throws Throwable 
     */
    ObservationEventEntity searchObsevationByOrderNo(String phrid , String orderNo) throws Throwable;
    
    /**
     * 新規検査結果の登録
     * @param entity
     * @param resultList
     * @return 
     * @throws java.lang.Throwable 
     */
    boolean insertObservationAndObservationEvent(ObservationEventEntity entity , List<ObservationEntity> resultList) throws Throwable;
    
    /**
     * 既存の検査項目結果の更新
     * @param updateList　更新データ
     * @param insertList　新規登録項目
     * @return
     * @throws Throwable 
     */
    boolean updateObservationAndObservationEvent(List<ObservationEntity> updateList , List<ObservationEntity>insertList)throws Throwable;
    
}
