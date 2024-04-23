package phr.service;

import java.util.List;

import phr.datadomain.entity.ObservationEntity;

/**
 * 
 * @author kisvdi017
 *
 */
public interface IObservationService {

    /**
     * ObservationEventIdに紐づくobservationリソースをすべて取得する
     * @param ObservationEventId
     * @return
     * @throws Throwable
     */
    List<ObservationEntity> getObservation1ByObservationEventId (String ObservationEventId) throws Throwable;

    List<ObservationEntity> getObservation23ByObservationEventId(String observationEventId) throws Throwable;

    /**
     * ObservationEventIdからObservationレコードを取得し、管理疾患が含まれているか確認
     * @param id
     * @return list
     * @throws Throwable 
     */
    public List<Integer> findById(String id) throws Throwable;
}