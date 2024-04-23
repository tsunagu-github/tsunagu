/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.dto.YearViewListDto;

/**
 *
 * @author kis
 */
public interface IManagementItemService {

    List<YearViewListDto> getYearViewIdList() throws Throwable;
    List<YearViewListDto> getYearViewIdList(String insurerNo)  throws Throwable;

    List<ObservationDefinitionEntity> getObservationDefinitionList(int viewId) throws Throwable;

    List<DiseaseTypeEntity> getDiseaseTypeList(int viewId) throws Throwable;

    List<String> getInsurerDiseaseTypeList(int viewId) throws Throwable;

    List<String> saveManagementItemSetting(
        int viewId,
        String[] observationDefinitionIdOrder,
        String[] selectedInsurerDiseaseType) throws Throwable;

    List<ObservationDefinitionInsurerEntity> getObservationReminders(int viewId) throws Throwable;

    InsurerViewDefinitionEntity getView(int viewId) throws Throwable;

}
