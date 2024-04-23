/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.adapter.CommunicationReceiverAdapter;
import phr.datadomain.adapter.DataTransferAdapter;
import phr.datadomain.adapter.DosageAdapter;
import phr.datadomain.adapter.DosageAttentionAdapter;
import phr.datadomain.adapter.DosageDoctorAdapter;
import phr.datadomain.adapter.DosageHeadAdapter;
import phr.datadomain.adapter.DosageMedicalOrganizationAdapter;
import phr.datadomain.adapter.DosageMedicineAdapter;
import phr.datadomain.adapter.DosageMedicineAdditionAdapter;
import phr.datadomain.adapter.DosageMedicineAttentionAdapter;
import phr.datadomain.adapter.DosageNoteAdapter;
import phr.datadomain.adapter.DosageOrganProvisionInfoAdapter;
import phr.datadomain.adapter.DosagePatientInputAdapter;
import phr.datadomain.adapter.DosageRecipeAdapter;
import phr.datadomain.adapter.DosageRecipeAdditionAdapter;
import phr.datadomain.adapter.DosageRecipeAttentionAdapter;
import phr.datadomain.adapter.DosageRemarkAdapter;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.adapter.NonPrescriptionDrugsAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationAlertAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.PatientSpecialInstructionAdapter;
import phr.datadomain.adapter.PharmaceutistAdapter;
import phr.datadomain.adapter.PhoneAccessLogsAdapter;
import phr.datadomain.adapter.ReminderPushedListAdapter;
import phr.datadomain.adapter.SeparatorInfoAdapter;
import phr.datadomain.adapter.UnusedDrugInfoAdapter;
import phr.datadomain.adapter.UtilizationConsentAdapter;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IWithDrawActionService;

/**
 *
 */
public class WithDrawActionService implements IWithDrawActionService {

	private static Logger logger = Logger.getLogger(WithDrawActionService.class);

	/**
	 * 該当のPHR_IDのダイナミックコンセント利用停止を行います。
	 * @param phr_id
	 * @return rowCount
	 */
	public int updateByPhrId(String phr_id) throws Throwable {
		logger.debug("Start");
		int rowCount = 0;
		
		DataAccessObject dao = null;
		try {
			dao = new DataAccessObject();
			PatientAdapter adapter = new PatientAdapter(dao.getConnection());
			rowCount = adapter.updateByPhrId(phr_id);
		} catch (Throwable ex) {
			logger.error(ex.toString(), ex);
		}
		
		logger.debug("End");
		return rowCount;
	}

//	/**
//	 * PHR_IDでDosageテーブルからレコードを取得
//	 * @param phr_id
//	 * @return list
//	 */
//	public List<DosageEntity> findByPhrId(String phr_id) throws Throwable {
//		logger.debug("Start");
//		List<DosageEntity> list = new ArrayList<>();
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageAdapter adapter = new DosageAdapter(dao.getConnection());
//			list = adapter.findByPhrid(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return list;
//	}
//	
//	/**
//	 * PHR_IDでObservationEventテーブルからレコードを取得
//	 * @param phr_id
//	 * @return list
//	 */
//	public List<ObservationEventEntity> getObservationEventIdList(String phr_id) throws Throwable {
//		logger.debug("Start");
//		List<ObservationEventEntity> list = new ArrayList<>();
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
//			list = adapter.getObservationEventIdList(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return list;
//	}
//	
//	/**
//	 * PHR_IDでPatientテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteByPhrId(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			PatientAdapter adapter = new PatientAdapter(dao.getConnection());
//			rowCount = adapter.deleteByPhrId(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//	
//	/**
//	 * PHR_IDでInsurerPatientテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteInsurerPatientRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
//			rowCount = adapter.deleteInsurerPatientRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//	
//	/**
//	 * PHR_IDでCommunicationテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteCommunicationRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
//			rowCount = adapter.deleteCommunicationRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * PHR_IDでCommunicationReceiverテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteCommunicationReceiverRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			CommunicationReceiverAdapter adapter = new CommunicationReceiverAdapter(dao.getConnection());
//			rowCount = adapter.deleteCommunicationReceiverRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * PHR_IDでDataTransferテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteDataTransferRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DataTransferAdapter adapter = new DataTransferAdapter(dao.getConnection());
//			rowCount = adapter.deleteDataTransferRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * PHR_IDでDosageテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteDosageRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageAdapter adapter = new DosageAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * PHR_IDでMedicalOrganizationPatientテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteMedicalOrganizationPatientRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			MedicalOrganizationPatientAdapter adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
//			rowCount = adapter.deleteMedicalOrganizationPatientRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * PHR_IDでPhoneAccessLogsテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deletePhoneAccessLogsRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			PhoneAccessLogsAdapter adapter = new PhoneAccessLogsAdapter(dao.getConnection());
//			rowCount = adapter.deletePhoneAccessLogsRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * PHR_IDでReminderPushedListテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteReminderPushedListRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			ReminderPushedListAdapter adapter = new ReminderPushedListAdapter(dao.getConnection());
//			rowCount = adapter.deleteReminderPushedListRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * PHR_IDでUtilizationConsentテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteUtilizationConsentRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			UtilizationConsentAdapter adapter = new UtilizationConsentAdapter(dao.getConnection());
//			rowCount = adapter.deleteUtilizationConsentRecord(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * PHR_IDでObservationEventテーブルからレコードを削除
//	 * @param phr_id
//	 * @return rowCount
//	 */
//	public int deleteObservationEventRecord(String phr_id) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
//			rowCount = adapter.deleteObservationEventRecordByPhrId(phr_id);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageAttentionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageAttentionRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageAttentionAdapter adapter = new DosageAttentionAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageAttentionRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageDoctorテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageDoctorRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageDoctorAdapter adapter = new DosageDoctorAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageDoctorRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageHeadテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageHeadRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageHeadAdapter adapter = new DosageHeadAdapter(dao.getConnection());
//			rowCount = adapter.deletebyDosageId(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageMedicalOrganizationテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageMedicalOrganizationRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageMedicalOrganizationAdapter adapter = new DosageMedicalOrganizationAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageMedicalOrganizationRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageNoteテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageNoteRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageNoteAdapter adapter = new DosageNoteAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageNoteRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageOrganProvisionInfoテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageOrganProvisionInfoRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageOrganProvisionInfoAdapter adapter = new DosageOrganProvisionInfoAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageOrganProvisionInfoRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosagePatientInputテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosagePatientInputRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosagePatientInputAdapter adapter = new DosagePatientInputAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosagePatientInputRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageRecipeテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageRecipeRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageRecipeAdapter adapter = new DosageRecipeAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageRecipeRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageRecipeAdditionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageRecipeAdditionRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageRecipeAdditionAdapter adapter = new DosageRecipeAdditionAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageRecipeAdditionRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageRecipeAttentionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageRecipeAttentionRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageRecipeAttentionAdapter adapter = new DosageRecipeAttentionAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageRecipeAttentionRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageRemarkテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageRemarkRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageRemarkAdapter adapter = new DosageRemarkAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageRemarkRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageMedicineテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageMedicineRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageMedicineAdapter adapter = new DosageMedicineAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageMedicineRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageMedicineAdditionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageMedicineAdditionRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageMedicineAdditionAdapter adapter = new DosageMedicineAdditionAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageMedicineAdditionRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでDosageMedicineAttentionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteDosageMedicineAttentionRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			DosageMedicineAttentionAdapter adapter = new DosageMedicineAttentionAdapter(dao.getConnection());
//			rowCount = adapter.deleteDosageMedicineAttentionRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでPatientSpecialInstructionテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deletePatientSpecialInstructionRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			PatientSpecialInstructionAdapter adapter = new PatientSpecialInstructionAdapter(dao.getConnection());
//			rowCount = adapter.deletePatientSpecialInstructionRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでNonPrescriptionDrugsテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteNonPrescriptionDrugsRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			NonPrescriptionDrugsAdapter adapter = new NonPrescriptionDrugsAdapter(dao.getConnection());
//			rowCount = adapter.deleteNonPrescriptionDrugsRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでParmaceutistテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deletePharmaceutistRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			PharmaceutistAdapter adapter = new PharmaceutistAdapter(dao.getConnection());
//			rowCount = adapter.deleteParmaceutistRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでSeparatorInfoテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteSeparatorInfoRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			SeparatorInfoAdapter adapter = new SeparatorInfoAdapter(dao.getConnection());
//			rowCount = adapter.deleteSeparatorInfoRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * DosageIdでUnusedDrugInfoテーブルからレコードを削除
//	 * @param dosageId
//	 * @return rowCount
//	 */
//	public int deleteUnusedDrugInfoRecord(String dosageId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			UnusedDrugInfoAdapter adapter = new UnusedDrugInfoAdapter(dao.getConnection());
//			rowCount = adapter.deleteUnusedDrugInfoRecord(dosageId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * ObservationEventIdでObservationテーブルからレコードを削除
//	 * @param observationEventId
//	 * @return rowCount
//	 */
//	public int deleteObservationRecord(String observationEventId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
//			rowCount = adapter.deleteObservationRecord(observationEventId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
//
//	/**
//	 * ObservationEventIdでObservationAlertテーブルからレコードを削除
//	 * @param observationEventId
//	 * @return rowCount
//	 */
//	public int deleteObservationAlertRecord(String observationEventId) throws Throwable {
//		logger.debug("Start");
//		int rowCount = 0;
//		
//		DataAccessObject dao = null;
//		try {
//			dao = new DataAccessObject();
//			ObservationAlertAdapter adapter = new ObservationAlertAdapter(dao.getConnection());
//			rowCount = adapter.deleteObservationAlertRecord(observationEventId);
//		} catch (Throwable ex) {
//			logger.error(ex.toString(), ex);
//		}
//		
//		logger.debug("End");
//		return rowCount;
//	}
}
