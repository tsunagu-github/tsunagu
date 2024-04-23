/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;

import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.ObservationEventEntity;

/**
 *
 */
public interface IWithDrawActionService {
	
	/**
	 * 該当のPHR_IDのダイナミックコンセント利用停止を行います。
	 * @param phr_id
	 * @return rowCount
	 */
	public int updateByPhrId(String phr_id) throws Throwable;
	
//	/**
//	 * PHR_IDでDosageテーブルからレコードを取得
//	 * @param phr_id
//	 * @return list
//	 */
//	public List<DosageEntity> findByPhrId(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでObservationEventテーブルからレコードを取得
//	 * @param phr_id
//	 * @return list
//	 */
//	public List<ObservationEventEntity> getObservationEventIdList(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでPatientテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteByPhrId(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでInsurerPatientテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteInsurerPatientRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでCommunicationテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteCommunicationRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでCommunicationReceiverテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteCommunicationReceiverRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでDataTransferテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteDataTransferRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでDosageテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteDosageRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでMedicalOrganizationPatientテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteMedicalOrganizationPatientRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでPhoneAccessLogsテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deletePhoneAccessLogsRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでReminderPushedListテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteReminderPushedListRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでUtilizationConsentテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteUtilizationConsentRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * PHR_IDでObservationEventテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteObservationEventRecord(String phr_id) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageAttentionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageAttentionRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageDoctorテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageDoctorRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageHeadテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageHeadRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageMedicalOrganizationテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageMedicalOrganizationRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageNoteテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageNoteRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageOrganProvisionInfoテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageOrganProvisionInfoRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosagePatientInputテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosagePatientInputRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageRecipeテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageRecipeRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageRecipeAdditionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageRecipeAdditionRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageRecipeAttentionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageRecipeAttentionRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageRemarkテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageRemarkRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageMedicineテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageMedicineRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageMedicineAdditionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageMedicineAdditionRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでDosageMedicineAttentionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageMedicineAttentionRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでPatientSpecialInstructionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deletePatientSpecialInstructionRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでNonPrescriptionDrugsテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteNonPrescriptionDrugsRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでPharmaceutistテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deletePharmaceutistRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでSeparatorInfoテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteSeparatorInfoRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * DosageIdでUnusedDrugInfoテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteUnusedDrugInfoRecord(String dosageId) throws Throwable;
//	
//	/**
//	 * ObservationEventIdでObservationテーブルからレコードを削除
//	 * @param observationEventId
//	 * @return rowCount
//	 */
//	public int deleteObservationRecord(String observationEventId) throws Throwable;
//	
//	/**
//	 * ObservationEventIdでObservationAlertテーブルからレコードを削除
//	 * @param observationEventId
//	 * @return rowCount
//	 */
//	public int deleteObservationAlertRecord(String observationEventId) throws Throwable;
}
