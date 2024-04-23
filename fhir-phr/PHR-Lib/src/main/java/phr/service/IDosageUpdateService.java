/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.DosageDoctorEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.DosageHeadEntity;
import phr.datadomain.entity.DosageMedicalOrganizationEntity;
import phr.datadomain.entity.DosageMedicineAdditionEntity;
import phr.datadomain.entity.DosageMedicineEntity;
import phr.datadomain.entity.DosagePatientInputEntity;
import phr.datadomain.entity.DosageRecipeEntity;
import phr.datadomain.entity.DosageRemarkEntity;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;

/**
 *
 * @author kis-note-027_user
 */
public interface IDosageUpdateService {
    int setDosage(DosageHeadEntity head,DosageEntity dosage,DosageMedicalOrganizationEntity pharmacy,DosageDoctorEntity pharmacist,
            DosageMedicalOrganizationEntity hospital,DosageDoctorEntity doctor, DosageRemarkEntity remark,
            List<DosageRecipeEntity> recipelist,List<DosageMedicineEntity> medicineList,List<DosageMedicineAdditionEntity> additionList,List<NonPrescriptionDrugsEntity> nonpreList)throws Throwable;

    String getNewDosageId()throws Throwable;
    
    int updatePatientInput(List<DosagePatientInputEntity> patInputList)throws Throwable;
}
