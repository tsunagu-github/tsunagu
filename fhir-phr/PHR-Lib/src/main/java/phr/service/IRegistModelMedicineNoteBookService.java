/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.MedicineNoteBoookDto;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author kis-note
 */
public interface IRegistModelMedicineNoteBookService {
    /**
     * 抽出を行うメインサービス
     * @param phrid
     * @param dto
     * @return 
     * @throws Throwable 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    boolean modelMNB(String phrid , MedicineNoteBoookDto dto) throws Throwable;    
}
