/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.dto;

import java.util.ArrayList;
import java.util.List;
import phr.datadomain.entity.DosageAttentionEntity;
import phr.datadomain.entity.DosageDoctorEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.DosageHeadEntity;
import phr.datadomain.entity.DosageMedicalOrganizationEntity;
import phr.datadomain.entity.DosageMedicineAdditionEntity;
import phr.datadomain.entity.DosageMedicineAttentionEntity;
import phr.datadomain.entity.DosageMedicineEntity;
import phr.datadomain.entity.DosageNoteEntity;
import phr.datadomain.entity.DosageOrganProvisionInfoEntity;
import phr.datadomain.entity.DosagePatientInputEntity;
import phr.datadomain.entity.DosageRecipeAdditionEntity;
import phr.datadomain.entity.DosageRecipeAttentionEntity;
import phr.datadomain.entity.DosageRecipeEntity;
import phr.datadomain.entity.DosageRemarkEntity;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;
import phr.datadomain.entity.PatientSpecialInstructionEntity;
import phr.datadomain.entity.PharmaceutistEntity;
import phr.datadomain.entity.SeparatorInfoEntity;
import phr.datadomain.entity.UnusedDrugInfoEntity;

/**
 *
 * @author kis-note-027_user
 */
public class DosageEntitySetDto {
    /**
     * 調剤ヘッダー情報
     */
    private DosageHeadEntity dosageHead = null;
    /**
     * 調剤ヘッダー情報リスト
     */
    private List<DosageHeadEntity> dosageHeadList = new ArrayList();
        
    /**
     * 調剤ヘッダー情報(市販薬)
     */
    private DosageHeadEntity dosageHeadNonpre = null;

    /**
     * 調剤ヘッダー情報リスト
     */
    private List<DosageHeadEntity> dosageHeadNonpreList = new ArrayList();
    
    /**
     * かかりつけ薬剤師情報
     */
    private List<PharmaceutistEntity> pharmaceutistList = new ArrayList();
    /**
     * 手帳メモ情報
     */
    private List<DosageNoteEntity> dosageNoteList = new ArrayList();
    /**
     * 一般用医薬品情報
     */
    private List<NonPrescriptionDrugsEntity> nonPrescriptionDrugsList = new ArrayList();
    /**
     * 患者特記事項
     */
    private List<PatientSpecialInstructionEntity> patientSpecialInstructionList = new ArrayList();
    /**
     * 調剤情報
     */
    private List<DosageEntity> dosageList = new ArrayList();
    /**
     * 医療機関情報
     */
    private List<DosageMedicalOrganizationEntity> medicalOrganizationList = new ArrayList();
    /**
     * 医師薬剤師情報
     */
    private List<DosageDoctorEntity> dosageDoctorList = new ArrayList();
    /**
     * 医療機関等提供情報
     */
    private List<DosageOrganProvisionInfoEntity> organProvisionInfoList = new ArrayList();
    /**
     * 服用注意情報
     */
    private List<DosageAttentionEntity> dosageAttentionList = new ArrayList();
    /**
     * レシピ情報
     */
    private List<DosageRecipeEntity> dosageRecipeList = new ArrayList();
    /**
     * 用法補足情報
     */
    private List<DosageRecipeAdditionEntity> dosageRecipeAddList = new ArrayList();
    /**
     * 処方服用注意情報
     */
    private List<DosageRecipeAttentionEntity> recipeAttentionList = new ArrayList();
    /**
     * 薬剤情報
     */
    private List<DosageMedicineEntity> dosageMedicineList = new ArrayList();
    /**
     * 薬品服用注意情報
     */
    private List<DosageMedicineAttentionEntity> medicineAttentionList = new ArrayList();
    /**
     * 薬剤補足情報
     */
    private List<DosageMedicineAdditionEntity> medicineAdditionList = new ArrayList();
    /**
     * 残薬確認情報
     */
    private List<UnusedDrugInfoEntity> unusedDrugInfoList = new ArrayList();
    /**
     * 備考情報
     */
    private List<DosageRemarkEntity> dosageRemarkList = new ArrayList();
    /**
     * 患者等記入情報
     */
    private List<DosagePatientInputEntity> patientInputList = new ArrayList();

    /**
     * セパレータ情報
     */
    private List<SeparatorInfoEntity> separatorInfoList = new ArrayList();
    
    /**
     * @return the dosageHead
     */
    public DosageHeadEntity getDosageHead() {
        return dosageHead;
    }

    /**
     * @param dosageHead the dosageHead to set
     */
    public void setDosageHead(DosageHeadEntity dosageHead) {
        this.dosageHead = dosageHead;
    }

    /**
     * @return the pharmaceutist
     */
    public PharmaceutistEntity getPharmaceutist(int index) {
        return pharmaceutistList.get(index);
    }

    /**
     * @param pharmaceutist the pharmaceutist to set
     */
    public void setPharmaceutist(PharmaceutistEntity pharmaceutist) {
        pharmaceutistList.add(pharmaceutist);
    }

    /**
     * @return the dosageNote
     */
    public DosageNoteEntity getDosageNote(int index) {
        return dosageNoteList.get(index);
    }

    /**
     * @param dosageNote the dosageNote to set
     */
    public void setDosageNote(DosageNoteEntity dosageNote) {
        dosageNoteList.add(dosageNote);
    }

    /**
     * @return the nonPrescriptionDrugs
     */
    public NonPrescriptionDrugsEntity getNonPrescriptionDrugs(int index) {
        return nonPrescriptionDrugsList.get(index);
    }

    /**
     * @param nonPrescriptionDrugs the nonPrescriptionDrugs to set
     */
    public void setNonPrescriptionDrugs(NonPrescriptionDrugsEntity nonPrescriptionDrugs) {
        nonPrescriptionDrugsList.add(nonPrescriptionDrugs);
    }

    /**
     * @return the patientSpecialInstruction
     */
    public PatientSpecialInstructionEntity getPatientSpecialInstruction(int index) {
        return patientSpecialInstructionList.get(index);
    }

    /**
     * @param patientSpecialInstruction the patientSpecialInstruction to set
     */
    public void setPatientSpecialInstruction(PatientSpecialInstructionEntity patientSpecialInstruction) {
        patientSpecialInstructionList.add(patientSpecialInstruction);
    }

    /**
     * @return the dosage
     */
    public DosageEntity getDosage(int index) {
        return dosageList.get(index);
    }

    /**
     * @param dosage the dosage to set
     */
    public void setDosage(DosageEntity dosage) {
        dosageList.add(dosage);
    }

    /**
     * @return the medicalOrganization
     */
    public DosageMedicalOrganizationEntity getMedicalOrganization(int index) {
        return medicalOrganizationList.get(index);
    }

    /**
     * @param medicalOrganization the medicalOrganization to set
     */
    public void setMedicalOrganization(DosageMedicalOrganizationEntity medicalOrganization) {
        medicalOrganizationList.add(medicalOrganization);
    }

    /**
     * @return the dosageDoctor
     */
    public DosageDoctorEntity getDosageDoctor(int index) {
        return dosageDoctorList.get(index);
    }

    /**
     * @param dosageDoctor the dosageDoctor to set
     */
    public void setDosageDoctor(DosageDoctorEntity dosageDoctor) {
        dosageDoctorList.add(dosageDoctor);
    }

    /**
     * @return the organProvisionInfo
     */
    public DosageOrganProvisionInfoEntity getOrganProvisionInfo(int index) {
        return organProvisionInfoList.get(index);
    }

    /**
     * @param organProvisionInfo the organProvisionInfo to set
     */
    public void setOrganProvisionInfo(DosageOrganProvisionInfoEntity organProvisionInfo) {
        organProvisionInfoList.add(organProvisionInfo);
    }

    /**
     * @return the dosageAttention
     */
    public DosageAttentionEntity getDosageAttention(int index) {
        return dosageAttentionList.get(index);
    }

    /**
     * @param dosageAttention the dosageAttention to set
     */
    public void setDosageAttention(DosageAttentionEntity dosageAttention) {
        dosageAttentionList.add(dosageAttention);
    }

    /**
     * @return the dosageRecipe
     */
    public DosageRecipeEntity getDosageRecipe(int index) {
        return dosageRecipeList.get(index);
    }

    /**
     * @param dosageRecipe the dosageRecipe to set
     */
    public void setDosageRecipe(DosageRecipeEntity dosageRecipe) {
        dosageRecipeList.add(dosageRecipe);
    }
    
     /**
     * @return the dosageRecipeAddition
     */
    public DosageRecipeAdditionEntity getDosageRecipeAdd(int index) {
        return dosageRecipeAddList.get(index);
    }

    /**
     * @param dosageRecipeAddittion the dosageRecipeAdd to set
     */
    public void setDosageRecipeAdd(DosageRecipeAdditionEntity dosageRecipeAdd) {
        dosageRecipeAddList.add(dosageRecipeAdd);
    }

    /**
     * @return the recipeAttention
     */
    public DosageRecipeAttentionEntity getRecipeAttention(int index) {
        return recipeAttentionList.get(index);
    }

    /**
     * @param recipeAttention the recipeAttention to set
     */
    public void setRecipeAttention(DosageRecipeAttentionEntity recipeAttention) {
        recipeAttentionList.add(recipeAttention);
    }

    /**
     * @return the dosageMedicine
     */
    public DosageMedicineEntity getDosageMedicine(int index) {
        return dosageMedicineList.get(index);
    }

    /**
     * @param dosageMedicine the dosageMedicine to set
     */
    public void setDosageMedicine(DosageMedicineEntity dosageMedicine) {
        dosageMedicineList.add(dosageMedicine);
    }

    /**
     * @return the medicineAttention
     */
    public DosageMedicineAttentionEntity getMedicineAttention(int index) {
        return medicineAttentionList.get(index);
    }

    /**
     * @param medicineAttention the medicineAttention to set
     */
    public void setMedicineAttention(DosageMedicineAttentionEntity medicineAttention) {
        medicineAttentionList.add(medicineAttention);
    }

    /**
     * @return the medicineAddition
     */
    public DosageMedicineAdditionEntity getMedicineAddition(int index) {
        return medicineAdditionList.get(index);
    }

    /**
     * @param medicineAddition the medicineAddition to set
     */
    public void setMedicineAddition(DosageMedicineAdditionEntity medicineAddition) {
        medicineAdditionList.add(medicineAddition);
    }

    /**
     * @return the medicineAddition
     */
    public UnusedDrugInfoEntity getUnusedDrugInfo(int index) {
        return unusedDrugInfoList.get(index);
    }

    /**
     * @param medicineAddition the medicineAddition to set
     */
    public void setUnusedDrugInfo(UnusedDrugInfoEntity unusedDrugInfo) {
    	unusedDrugInfoList.add(unusedDrugInfo);
    }

    /**
     * @return the dosageRemark
     */
    public DosageRemarkEntity getDosageRemark(int index) {
        return dosageRemarkList.get(index);
    }

    /**
     * @param dosageRemark the dosageRemark to set
     */
    public void setDosageRemark(DosageRemarkEntity dosageRemark) {
        dosageRemarkList.add(dosageRemark);
    }

    /**
     * @return the patientInput
     */
    public DosagePatientInputEntity getPatientInput(int index) {
        return patientInputList.get(index);
    }

    /**
     * @param patientInput the patientInput to set
     */
    public void setPatientInput(DosagePatientInputEntity patientInput) {
        patientInputList.add(patientInput);
    }

    /**
     * @return the separatorInfo
     */
    public SeparatorInfoEntity getSeparatorInfo(int index) {
        return separatorInfoList.get(index);
    }

    /**
     * @param separatorInfo the separatorInfo to set
     */
    public void setSeparatorInfo(SeparatorInfoEntity separatorInfo) {
        this.separatorInfoList.add(separatorInfo);
    }
    
    /**
     * @return the pharmaceutistList
     */
    public List<PharmaceutistEntity> getPharmaceutistList() {
        return pharmaceutistList;
    }

    /**
     * @param pharmaceutistList the pharmaceutistList to set
     */
    public void setPharmaceutistList(List<PharmaceutistEntity> pharmaceutistList) {
        this.pharmaceutistList = pharmaceutistList;
    }

    /**
     * @return the dosageNoteList
     */
    public List<DosageNoteEntity> getDosageNoteList() {
        return dosageNoteList;
    }

    /**
     * @param dosageNoteList the dosageNoteList to set
     */
    public void setDosageNoteList(List<DosageNoteEntity> dosageNoteList) {
        this.dosageNoteList = dosageNoteList;
    }

    /**
     * @return the nonPrescriptionDrugsList
     */
    public List<NonPrescriptionDrugsEntity> getNonPrescriptionDrugsList() {
        return nonPrescriptionDrugsList;
    }

    /**
     * @param nonPrescriptionDrugsList the nonPrescriptionDrugsList to set
     */
    public void setNonPrescriptionDrugsList(List<NonPrescriptionDrugsEntity> nonPrescriptionDrugsList) {
        this.nonPrescriptionDrugsList = nonPrescriptionDrugsList;
    }

    /**
     * @return the patientSpecialInstructionList
     */
    public List<PatientSpecialInstructionEntity> getPatientSpecialInstructionList() {
        return patientSpecialInstructionList;
    }

    /**
     * @param patientSpecialInstructionList the patientSpecialInstructionList to set
     */
    public void setPatientSpecialInstructionList(List<PatientSpecialInstructionEntity> patientSpecialInstructionList) {
        this.patientSpecialInstructionList = patientSpecialInstructionList;
    }

    /**
     * @return the dosageList
     */
    public List<DosageEntity> getDosageList() {
        return dosageList;
    }

    /**
     * @param dosageList the dosageList to set
     */
    public void setDosageList(List<DosageEntity> dosageList) {
        this.dosageList = dosageList;
    }

    /**
     * @return the medicalOrganizationList
     */
    public List<DosageMedicalOrganizationEntity> getMedicalOrganizationList() {
        return medicalOrganizationList;
    }

    /**
     * @param medicalOrganizationList the medicalOrganizationList to set
     */
    public void setMedicalOrganizationList(List<DosageMedicalOrganizationEntity> medicalOrganizationList) {
        this.medicalOrganizationList = medicalOrganizationList;
    }

    /**
     * @return the dosageDoctorList
     */
    public List<DosageDoctorEntity> getDosageDoctorList() {
        return dosageDoctorList;
    }

    /**
     * @param dosageDoctorList the dosageDoctorList to set
     */
    public void setDosageDoctorList(List<DosageDoctorEntity> dosageDoctorList) {
        this.dosageDoctorList = dosageDoctorList;
    }

    /**
     * @return the organProvisionInfoList
     */
    public List<DosageOrganProvisionInfoEntity> getOrganProvisionInfoList() {
        return organProvisionInfoList;
    }

    /**
     * @param organProvisionInfoList the organProvisionInfoList to set
     */
    public void setOrganProvisionInfoList(List<DosageOrganProvisionInfoEntity> organProvisionInfoList) {
        this.organProvisionInfoList = organProvisionInfoList;
    }

    /**
     * @return the dosageAttentionList
     */
    public List<DosageAttentionEntity> getDosageAttentionList() {
        return dosageAttentionList;
    }

    /**
     * @param dosageAttentionList the dosageAttentionList to set
     */
    public void setDosageAttentionList(List<DosageAttentionEntity> dosageAttentionList) {
        this.dosageAttentionList = dosageAttentionList;
    }

    /**
     * @return the dosageRecipeList
     */
    public List<DosageRecipeEntity> getDosageRecipeList() {
        return dosageRecipeList;
    }

    /**
     * @param dosageRecipeList the dosageRecipeList to set
     */
    public void setDosageRecipeList(List<DosageRecipeEntity> dosageRecipeList) {
        this.dosageRecipeList = dosageRecipeList;
    }

    /**
     * @return the recipeAttentionList
     */
    public List<DosageRecipeAttentionEntity> getRecipeAttentionList() {
        return recipeAttentionList;
    }

    /**
     * @param recipeAttentionList the recipeAttentionList to set
     */
    public void setRecipeAttentionList(List<DosageRecipeAttentionEntity> recipeAttentionList) {
        this.recipeAttentionList = recipeAttentionList;
    }

    /**
     * @return the dosageMedicineList
     */
    public List<DosageMedicineEntity> getDosageMedicineList() {
        return dosageMedicineList;
    }

    /**
     * @param dosageMedicineList the dosageMedicineList to set
     */
    public void setDosageMedicineList(List<DosageMedicineEntity> dosageMedicineList) {
        this.dosageMedicineList = dosageMedicineList;
    }

    /**
     * @return the medicineAttentionList
     */
    public List<DosageMedicineAttentionEntity> getMedicineAttentionList() {
        return medicineAttentionList;
    }

    /**
     * @param medicineAttentionList the medicineAttentionList to set
     */
    public void setMedicineAttentionList(List<DosageMedicineAttentionEntity> medicineAttentionList) {
        this.medicineAttentionList = medicineAttentionList;
    }

    /**
     * @return the medicineAdditionList
     */
    public List<DosageMedicineAdditionEntity> getMedicineAdditionList() {
        return medicineAdditionList;
    }

    /**
     * @param medicineAdditionList the medicineAdditionList to set
     */
    public void setMedicineAdditionList(List<DosageMedicineAdditionEntity> medicineAdditionList) {
        this.medicineAdditionList = medicineAdditionList;
    }

    /**
     * @return the dosageRemarkList
     */
    public List<DosageRemarkEntity> getDosageRemarkList() {
        return dosageRemarkList;
    }

    /**
     * @param dosageRemarkList the dosageRemarkList to set
     */
    public void setDosageRemarkList(List<DosageRemarkEntity> dosageRemarkList) {
        this.dosageRemarkList = dosageRemarkList;
    }

    /**
     * @return the patientInputList
     */
    public List<DosagePatientInputEntity> getPatientInputList() {
        return patientInputList;
    }

    /**
     * @param patientInputList the patientInputList to set
     */
    public void setPatientInputList(List<DosagePatientInputEntity> patientInputList) {
        this.patientInputList = patientInputList;
    }

    /**
     * @return the dosageRecipeAddList
     */
    public List<DosageRecipeAdditionEntity> getDosageRecipeAddList() {
        return dosageRecipeAddList;
    }

    /**
     * @param dosageRecipeAddList the dosageRecipeAddList to set
     */
    public void setDosageRecipeAddList(List<DosageRecipeAdditionEntity> dosageRecipeAddList) {
        this.dosageRecipeAddList = dosageRecipeAddList;
    }

    /**
     * @return the separatorInfo
     */
    public List<SeparatorInfoEntity> getSeparatorInfoList() {
        return separatorInfoList;
    }

    /**
     * @param separatorInfo the separatorInfo to set
     */
    public void setSeparatorInfoList(List<SeparatorInfoEntity> separatorInfolist) {
        this.separatorInfoList=separatorInfolist;
    }
    
    /**
     * @return the dosageHeadNonpre
     */
    public DosageHeadEntity getDosageHeadNonpre() {
        return dosageHeadNonpre;
    }

    /**
     * @param dosageHeadNonpre the dosageHeadNonpre to set
     */
    public void setDosageHeadNonpre(DosageHeadEntity dosageHeadNonpre) {
        this.dosageHeadNonpre = dosageHeadNonpre;
    }

    /**
     * @return the dosageHeadList
     */
    public List<DosageHeadEntity> getDosageHeadList() {
        return dosageHeadList;
    }

    /**
     * @param dosageHeadList the dosageHeadList to set
     */
    public void setDosageHeadList(List<DosageHeadEntity> dosageHeadList) {
        this.dosageHeadList = dosageHeadList;
    }
    /**
     * @return the dosageHeadList
     */
    public DosageHeadEntity getDosageHeadListItem(int index) {
        return dosageHeadList.get(index);
    }

    /**
     * @param dosageHeadList the dosageHeadList to set
     */
    public void setDosageHeadListItem(DosageHeadEntity dosageHeadItem) {
        this.dosageHeadList.add(dosageHeadItem);
    }

    /**
     * @return the dosageHeadNonpreList
     */
    public List<DosageHeadEntity> getDosageHeadNonpreList() {
        return dosageHeadNonpreList;
    }

    /**
     * @param dosageHeadNonpreList the dosageHeadNonpreList to set
     */
    public void setDosageHeadNonpreList(List<DosageHeadEntity> dosageHeadNonpreList) {
        this.dosageHeadNonpreList = dosageHeadNonpreList;
    }
    
    /**
     * @return the dosageHeadNonpreList
     */
    public DosageHeadEntity getDosageHeadNonpreListItem(int index) {
        return dosageHeadNonpreList.get(index);
    }

    /**
     * @param dosageHeadNonpreList the dosageHeadNonpreList to set
     */
    public void setDosageHeadNonpreListItem(DosageHeadEntity dosageHeadNonpreListItem) {
        this.dosageHeadNonpreList.add(dosageHeadNonpreListItem);
    }

    /**
     * @return the unusedDrugInfo
     */
    public List<UnusedDrugInfoEntity> getUnusedDrugInfoList() {
        return unusedDrugInfoList;
    }

    /**
     * @param unusedDrugInfo the unusedDrugInfo to set
     */
    public void setUnusedDrugInfoList(List<UnusedDrugInfoEntity> unusedDrugInfolist) {
        this.unusedDrugInfoList=unusedDrugInfolist;
    }

}
