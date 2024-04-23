/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：健診結果取得クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.impl.WithDrawActionService;
import phr.web.ISessionUtility;
import phr.web.phone.dto.RequestWithDrawActionDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseWithDrawActionDto;

/**
 *
 * @author daisuke
 */
public class WithDrawActionFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(WithDrawActionFacade.class);

    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * 処理を開始する
     *
     * @param json
     * @return
     * @throws java.lang.Throwable
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        logger.debug("Start");
        try {
            RequestWithDrawActionDto requestDto = gson.fromJson(json, RequestWithDrawActionDto.class);
            ResponseWithDrawActionDto responseDto = new ResponseWithDrawActionDto();
            WithDrawActionService service = new WithDrawActionService();
            
            // 該当患者のPHR_IDを取得
            String phr_id = requestDto.getPhrId();
            logger.debug("対象者のPHR_ID　：　" + phr_id);
            
//            // 該当患者のDosageIdのリストを取得
//            List<String> dosageIdList = new ArrayList<>();
//            List<DosageEntity> dosageEntityList = new ArrayList<>();
//            dosageEntityList = service.findByPhrId(phr_id);
//            if (dosageEntityList.size() != 0) {
//                for (DosageEntity en : dosageEntityList) {
//                    dosageIdList.add(en.getDosageId());
//                    logger.debug("対象者のDosageId　：　" + en.getDosageId());
//                }
//            }
            
//            // 該当患者のObservationEventIdのリストを取得
//            List<String> observationEventIdList = new ArrayList<>();
//            List<ObservationEventEntity> observationEventEntityList = new ArrayList<>();
//            observationEventEntityList = service.getObservationEventIdList(phr_id);
//            if (observationEventEntityList.size() != 0) {
//                for (ObservationEventEntity e : observationEventEntityList) {
//                    observationEventIdList.add(e.getObservationEventId());
//                    logger.debug("対象者のObservationEventId　：　" + e.getObservationEventId());
//                }
//            }
            
//            // PHR_IDを使用して該当患者のレコードを削除
//            this.deleteByPhrId(phr_id);
//            
//            // DosageIdを使用して該当患者のレコードを削除
//            this.deleteByDosageId(dosageIdList);
//            
//            // ObservationEventIdを使用して該当患者のレコードを削除
//            this.deletByObservationEventId(observationEventIdList);
            
            // PatientテーブルのDynamicConsentFlgをfalseに変更
            int rowCount = service.updateByPhrId(phr_id);
            if (rowCount == 1) {
                logger.debug(phr_id + "のダイナミックコンセントの利用を停止しました。");
            } else if (rowCount == 0) {
                logger.debug(phr_id + "のダイナミックコンセントの利用停止に失敗しました。");
            }
            
            // レスポンス作成
            responseDto.setStatus(ResponseBaseDto.SUCCESS);

            logger.debug("End");
            return responseDto;
            
        } catch (Throwable t) {
            logger.error("", t);
            throw new Exception("[" + this.getClass().getSimpleName() + "] Error", t);
        }
    }

//    /**
//     * PHR_IDを使用して該当患者のレコードを削除
//     * @param phr_id
//     */
//    public void deleteByPhrId (String phr_id) throws Throwable {
//        logger.debug("Start");
//        logger.debug("PHR_ID :" + phr_id + "の退会処理開始");
//        WithDrawActionService service = new WithDrawActionService();
//        
//        // Patientテーブル
//        logger.debug("Patientテーブルの削除処理開始");
//        service.deleteByPhrId(phr_id);
//        
//        // InsurerPatientテーブル
//        logger.debug("InsurerPatientテーブルの削除処理開始");
//        service.deleteInsurerPatientRecord(phr_id);
//        
//        // Communicationテーブル
//        logger.debug("Communicationテーブルの削除処理開始");
//        service.deleteCommunicationRecord(phr_id);
//        
//        // CommunicationReceiverテーブル
//        logger.debug("CommunicationReceiverテーブルの削除処理開始");
//        service.deleteCommunicationReceiverRecord(phr_id);
//        
//        // DataTransferテーブル
//        logger.debug("DataTransferテーブルの削除処理開始");
//        service.deleteDataTransferRecord(phr_id);
//        
//        // Dosageテーブル
//        logger.debug("Dosageテーブルの削除処理開始");
//        service.deleteDosageRecord(phr_id);
//        
//        // MedicalOrganizationPatientテーブル
//        logger.debug("MedicalOrganizationPatientテーブルの削除処理開始");
//        service.deleteMedicalOrganizationPatientRecord(phr_id);
//        
//        // PhoneAccessLogsテーブル
//        logger.debug("PhoneAccessLogsテーブルの削除処理開始");
//        service.deletePhoneAccessLogsRecord(phr_id);
//        
//        // ReminderPushedListテーブル
//        logger.debug("ReminderPushedListテーブルの削除処理開始");
//        service.deleteReminderPushedListRecord(phr_id);
//        
//        // UtilizationConsentテーブル
//        logger.debug("UtilizationConsentテーブルの削除処理開始");
//        service.deleteUtilizationConsentRecord(phr_id);
//        
//        // ObservationEventテーブル
//        logger.debug("ObservationEventテーブルの削除処理開始");
//        service.deleteObservationEventRecord(phr_id);
//        
//        logger.debug("End");
//    }

//    /**
//     * DosageIdを使用して該当患者のレコードを削除
//     * @param dosageIdList
//     */
//    public void deleteByDosageId (List<String> dosageIdList) throws Throwable {
//        logger.debug("Start");
//        WithDrawActionService service = new WithDrawActionService();
//        
//        for (String dosageId : dosageIdList) {
//            logger.debug("DosageID : " + dosageId + "の退会処理開始");
//            
//            // DosageAttentionテーブル
//            logger.debug("DosageAttentionテーブルの削除処理開始");
//            service.deleteDosageAttentionRecord(dosageId);
//            
//            // DosageDoctorテーブル
//            logger.debug("DosageDoctorテーブルの削除処理開始");
//            service.deleteDosageDoctorRecord(dosageId);
//            
//            // DosageHeadテーブル
//            logger.debug("DosageHeadテーブルの削除処理開始");
//            service.deleteDosageHeadRecord(dosageId);
//            
//            // DosageMedicalOrganizationテーブル
//            logger.debug("DosageMedicalOrganizationテーブルの削除処理開始");
//            service.deleteDosageMedicalOrganizationRecord(dosageId);
//            
//            // DosageNoteテーブル
//            logger.debug("DosageNoteテーブルの削除処理開始");
//            service.deleteDosageNoteRecord(dosageId);
//            
//            // DosageOrganProvisionInfoテーブル
//            logger.debug("DosageOrganProvisionInfoテーブルの削除処理開始");
//            service.deleteDosageOrganProvisionInfoRecord(dosageId);
//            
//            // DosagePatientInputテーブル
//            logger.debug("DosagePatientInputテーブルの削除処理開始");
//            service.deleteDosagePatientInputRecord(dosageId);
//            
//            // DosageRecipeテーブル
//            logger.debug("DosageRecipeテーブルの削除処理開始");
//            service.deleteDosageRecipeRecord(dosageId);
//            
//            // DosageRecipeAdditionテーブル
//            logger.debug("DosageRecipeAdditionテーブルの削除処理開始");
//            service.deleteDosageRecipeAdditionRecord(dosageId);
//            
//            // DosageRecipeAttentionテーブル
//            logger.debug("DosageRecipeAttentionテーブルの削除処理開始");
//            service.deleteDosageRecipeAttentionRecord(dosageId);
//            
//            // DosageRemarkテーブル
//            logger.debug("DosageRemarkテーブルの削除処理開始");
//            service.deleteDosageRemarkRecord(dosageId);
//            
//            // DosageMedicineテーブル
//            logger.debug("DosageMedicineテーブルの削除処理開始");
//            service.deleteDosageMedicineRecord(dosageId);
//            
//            // DosageMedicineAdditionテーブル
//            logger.debug("DosageMedicineAdditionテーブルの削除処理開始");
//            service.deleteDosageMedicineAdditionRecord(dosageId);
//            
//            // DosageMedicineAttentionテーブル
//            logger.debug("DosageMedicineAttentionテーブルの削除処理開始");
//            service.deleteDosageMedicineAttentionRecord(dosageId);
//            
//            // PatientSpecialInstructionテーブル
//            logger.debug("PatientSpecialInstructionテーブルの削除処理開始");
//            service.deletePatientSpecialInstructionRecord(dosageId);
//            
//            // NonPrescriptionDrugsテーブル
//            logger.debug("NonPrescriptionDrugsテーブルの削除処理開始");
//            service.deleteNonPrescriptionDrugsRecord(dosageId);
//            
//            // Pharmaceutismテーブル
//            logger.debug("Pharmaceutismnテーブルの削除処理開始");
//            service.deletePharmaceutistRecord(dosageId);
//            
//            // SeparatorInfoテーブル
//            logger.debug("SeparatorInfoテーブルの削除処理開始");
//            service.deleteSeparatorInfoRecord(dosageId);
//            
//            // UnusedDrugInfoテーブル
//            logger.debug("UnusedDrugInfoテーブルの削除処理開始");
//            service.deleteUnusedDrugInfoRecord(dosageId);
//        }
//        
//        logger.debug("End");
//    }

//    /**
//     * ObservationEventIdを使用して該当患者のレコードを削除
//     * @param observationEventIdList
//     */
//    public void deletByObservationEventId (List<String> observationEventIdList) throws Throwable {
//    	logger.debug("Start");
//        WithDrawActionService service = new WithDrawActionService();
//        
//        for (String observationEventId : observationEventIdList) {
//            logger.debug("ObservationEventId : " + observationEventId + "の退会処理開始");
//            
//            // Observationテーブル
//            logger.debug("Observationテーブルの削除処理開始");
//            service.deleteObservationRecord(observationEventId);
//            
//            // ObservationAlertテーブル
//            logger.debug("ObservationAlertテーブルの削除処理開始");
//            service.deleteObservationAlertRecord(observationEventId);
//        }
//        
//        logger.debug("End");
//    }
}
