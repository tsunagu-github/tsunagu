/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.DosageDoctorEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.DosageMedicalOrganizationEntity;
import phr.datadomain.entity.SeparatorInfoEntity;
import phr.dto.DosageEntitySetDto;

/**
 *
 * @author kis-note-027_user
 */
public interface IDosageImportService {
    int setDosage(DosageEntitySetDto dosageSet)throws Throwable;
    String getNewDosageId()throws Throwable;
    List<SeparatorInfoEntity> getSeparatorInfo(Long separatorId) throws Throwable;
    int deleteSeparatorInfo(Long separatorId,String dosageId)throws Throwable;
}
