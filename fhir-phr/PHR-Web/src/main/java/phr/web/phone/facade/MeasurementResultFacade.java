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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.CommonReferenceRangeAdapter;
import phr.datadomain.adapter.JLAC10AnalyteCodeAdapter;
import phr.datadomain.adapter.JLAC11AnalyteCodeAdapter;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import phr.datadomain.adapter.ObservationDefinitionJlac10Adapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.CommonReferenceRangeEntity;
import phr.datadomain.entity.JLAC10AnalyteCodeEntity;
import phr.datadomain.entity.JLAC11AnalyteCodeEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ObservationDefinitionJlac10Entity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.impl.ObservationEventService;
import phr.web.dto.ObservationDto;
import phr.web.phone.dto.MeasurementTestViewDto;
import phr.web.phone.dto.RequestMeasurementTestResultDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseMeasurementTestResultDto;

/**
 *
 * @author daisuke
 */
public class MeasurementResultFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MeasurementResultFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final String EXAM_BACKGROUND_COLOR = PhrConfig.getConfigProperty(ConfigConst.EXAM_BACKGROUND_COLOR);
    private static final String SELF_BACKGROUND_COLOR = PhrConfig.getConfigProperty(ConfigConst.SELF_BACKGROUND_COLOR);
    private static final String CHECKUP_BACKGROUND_COLOR = PhrConfig.getConfigProperty(ConfigConst.CHECKUP_BACKGROUND_COLOR);
    private static final String ALERT_BACKGROUND_COLOR = PhrConfig.getConfigProperty(ConfigConst.ALERT_BACKGROUND_COLOR);

    /**
     * 処理を開始する
     *
     * @param json
     * @return
     * @throws java.lang.Throwable
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        try {
            DataAccessObject dao = null;
            dao = new DataAccessObject();
            RequestMeasurementTestResultDto requestDto = gson.fromJson(json, RequestMeasurementTestResultDto.class);

            // requestDto.getStatusの値によって処理を分岐（status=nullの時は通常処理）
            ResponseMeasurementTestResultDto responseDto = new ResponseMeasurementTestResultDto();
            ObservationEventService observationEventService = new ObservationEventService();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
            if (requestDto.getStatus() == null) {
                // 対象患者の検査結果リストを取得
                List<String> observationEventIdList = observationEventService.searchObservationEventId(requestDto.getPhrId());
                // 対象患者の検査結果リストが存在する場合
                if (!observationEventIdList.isEmpty()) {
                    // DataInputTypeCdを取得
                    int dataInputTypeCd = 0;
                    ObservationEventEntity obEvEntity = new ObservationEventEntity();
                    obEvEntity = observationEventService.findById(observationEventIdList.get(0));
                    dataInputTypeCd = obEvEntity.getDataInputTypeCd();
                    // ObservationEventEntityリストを取得(DataInputTypeCdの値によって処理を分岐)
                    List<ObservationEventEntity> entityList = new ArrayList<>();
                    if (dataInputTypeCd == 1) {
                        entityList = observationEventService.searchAllByEventId(observationEventIdList.get(0));
                    } else if (dataInputTypeCd == 2 || dataInputTypeCd == 3 || dataInputTypeCd == 4 || dataInputTypeCd == 5) {
                        entityList = observationEventService.searchAllByEventIdForSelfCheck(observationEventIdList.get(0));
                    }
//                    // ObservationEventEntityリストを取得
//                    List<ObservationEventEntity> entityList = observationEventService.searchAllByEventId(observationEventIdList.get(0));
                    // 検査結果が存在する場合
                    if (!entityList.isEmpty()) {
                        // 過去検査が存在するか確認
                        List<ObservationEventEntity> eList = observationEventService.findByPhrId(requestDto.getPhrId());
                        String pastDate = null;
                        if (eList.size() > 1) {
                            Timestamp past =eList.get(1).getExaminationDate();
                            pastDate = sdf1.format(past);
                        }
                        // 医療機関名を取得
                        String hospitalName = this.getHospitalName(entityList.get(0).getMedicalOrganizationCd());
                        // 該当年月日を取得
                        String date = null;
                        if (entityList.get(0).getExaminationDate() != null) {
                            Timestamp preDate = entityList.get(0).getExaminationDate();
                            date = sdf2.format(preDate);
                        }
                        // 検査結果入力種別CDの値によって背景色のコードを取得(「-3：:検査結果」,「-4：自己測定」,「-5：特定健診」)
                        // また、合わせて測定・全検査結果画面の表タイトルを設定
                        String backgroundColor = null;
                        String title = null;
                        if (entityList.get(0).getDataInputTypeCd() == -3) {
                            backgroundColor = EXAM_BACKGROUND_COLOR;
                            title = hospitalName + " (" + date + ")";
                        } else if (entityList.get(0).getDataInputTypeCd() == -4) {
                            backgroundColor = SELF_BACKGROUND_COLOR;
                            title = "自己測定 (" + date + ")";
                        } else if (entityList.get(0).getDataInputTypeCd() == -5) {
                            backgroundColor = CHECKUP_BACKGROUND_COLOR;
                            title = "特定健診 (" + hospitalName + ")" + " (" + date + ")";
                        }
                        
                        List<ObservationDto> dto = new ArrayList<>();
                        for (int i = 0; i < entityList.size(); i++) {
                            dto.add(new ObservationDto());
                            dto.get(i).setAlertLevel(entityList.get(i).getAlertLevelCd());
                            dto.get(i).setDataInputTypeCd(entityList.get(i).getDataInputTypeCd());
                            dto.get(i).setEventId(entityList.get(i).getObservationEventId());
                            dto.get(i).setExaminationDate(new SimpleDateFormat("yyyy/MM/dd").format(entityList.get(i).getExaminationDate()));
                            dto.get(i).setId(entityList.get(i).getObservationDefinitionId());
                            dto.get(i).setUnit(entityList.get(i).getUnitValue());
                            // 特定健診の場合
                            if (entityList.get(0).getDataInputTypeCd() == -5) {
                                String enumName = observationEventService.findByIdAndEnum(entityList.get(i).getObservationDefinitionId(), entityList.get(i).getObservationValue(), 7);
                                if (enumName != null) {
                                    dto.get(i).setValue(enumName);
                                } else {
                                    dto.get(i).setValue(entityList.get(i).getObservationValue());
                                }
                            } else {
                                dto.get(i).setValue(entityList.get(i).getObservationValue());
                            }
                            dto.get(i).setValue(entityList.get(i).getObservationValue());
                            dto.get(i).setName(entityList.get(i).getObservationDefinitionName());
                            
                            // 基準値が設定されているかを確認（優先順位：Observationテーブル→CommonReferenceRangeテーブル）
                            CommonReferenceRangeAdapter commonAdapter = new CommonReferenceRangeAdapter(dao.getConnection());
                            ObservationDefinitionJlac10Adapter jlac10Adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
                            Double min = null;
                            Double max = null;
                            String refVal = null;
                            String refUnit = null;
                            // Observationに基準値が設定されている場合
                            if (entityList.get(i).getMinReferenceValue() != null || entityList.get(i).getMaxReferenceValue() != null) {
                                if (entityList.get(i).getMinReferenceValue() != null) {
                                    refVal = String.valueOf(entityList.get(i).getMinReferenceValue());
                                    min = entityList.get(i).getMinReferenceValue();
                                }
                                if (entityList.get(i).getMaxReferenceValue() != null) {
                                    if (refVal != null) {
                                        refVal = refVal + " ～ " + String.valueOf(entityList.get(i).getMaxReferenceValue());
                                    } else {
                                        refVal = String.valueOf(entityList.get(i).getMaxReferenceValue());
                                    }
                                    max = entityList.get(i).getMaxReferenceValue();
                                }
                            // Observationに基準値が未設定かつCommonReferenceRangeに基準値が設定されている場合
                            } else {
                                // ObservationDefinitionIdからJLAC10コードを取得
                                List<ObservationDefinitionJlac10Entity> list = jlac10Adapter.findByPrimaryKeyGetAll(entityList.get(i).getObservationDefinitionId());
                                CommonReferenceRangeEntity targetEntity = null;
                                for (ObservationDefinitionJlac10Entity e : list) {
                                    String jlac10 = null;
                                    String jlac11 = null;
                                    if (e.getJLAC10() != null) {
                                        jlac10 = e.getJLAC10().substring(0, 5);
                                    }
                                    if (e.getJLAC11() != null) {
                                        jlac11 = e.getJLAC11().substring(0, 5);
                                    }
                                    CommonReferenceRangeEntity commonEntity = null;
                                    commonEntity = commonAdapter.findByJLAC10AndJLAC11(jlac10, jlac11);
                                    if (commonEntity.getJLAC10AnalyteCode() != null) {
                                        targetEntity = commonEntity;
                                        break;
                                    }
                                }
                                if (targetEntity != null) {
                                    // 男女別の基準値を取得
                                    PatientAdapter patAdapter = new PatientAdapter(dao.getConnection());
                                    PatientEntity pat = patAdapter.findByPrimaryKey(entityList.get(i).getPHR_ID());
                                    if (pat.getSexCd().equals("M")) {
                                        if (targetEntity.getMaleLowerLimit() != null || targetEntity.getMaleUpperLimit() != null) {
                                            if (targetEntity.getMaleLowerLimit() != null) {
                                                refVal = String.valueOf(targetEntity.getMaleLowerLimit());
                                                min = targetEntity.getMaleLowerLimit();
                                            }
                                            if (targetEntity.getMaleUpperLimit() != null) {
                                                if (refVal != null) {
                                                    refVal = refVal + "～" + String.valueOf(targetEntity.getMaleUpperLimit());
                                                } else {
                                                    refVal = String.valueOf(targetEntity.getMaleUpperLimit());
                                                }
                                                max = targetEntity.getMaleUpperLimit();
                                            }
                                        } else {
                                            // 男女別の基準値が未設定の場合は共通の基準値を取得
                                            if (targetEntity.getCommonLowerLimit() != null || targetEntity.getCommonUpperLimit() != null) {
                                                if (targetEntity.getCommonLowerLimit() != null) {
                                                    refVal = String.valueOf(targetEntity.getCommonLowerLimit());
                                                    min = targetEntity.getCommonLowerLimit();
                                                }
                                                if (targetEntity.getCommonUpperLimit() != null) {
                                                    if (refVal != null) {
                                                        refVal = refVal + "～" + String.valueOf(targetEntity.getCommonUpperLimit());
                                                    } else {
                                                        refVal = String.valueOf(targetEntity.getCommonUpperLimit());
                                                    }
                                                    max = targetEntity.getCommonUpperLimit();
                                                }
                                            }
                                        }
                                    } else if (pat.getSexCd().equals("F")) {
                                        if (targetEntity.getFemaleLowerLimit() != null || targetEntity.getFemaleUpperLimit() != null) {
                                            if (targetEntity.getFemaleLowerLimit() != null) {
                                                refVal = String.valueOf(targetEntity.getFemaleLowerLimit());
                                                min = targetEntity.getFemaleLowerLimit();
                                            }
                                            if (targetEntity.getFemaleUpperLimit() != null) {
                                                if (refVal != null) {
                                                    refVal = refVal + "～" + String.valueOf(targetEntity.getFemaleUpperLimit());
                                                } else {
                                                    refVal = String.valueOf(targetEntity.getFemaleUpperLimit());
                                                }
                                                max = targetEntity.getFemaleUpperLimit();
                                            }
                                        } else {
                                            // 男女別の基準値が未設定の場合は共通の基準値を取得
                                            if (targetEntity.getCommonLowerLimit() != null || targetEntity.getCommonUpperLimit() != null) {
                                                if (targetEntity.getCommonLowerLimit() != null) {
                                                    refVal = String.valueOf(targetEntity.getCommonLowerLimit());
                                                    min = targetEntity.getCommonLowerLimit();
                                                }
                                                if (targetEntity.getCommonUpperLimit() != null) {
                                                    if (refVal != null) {
                                                        refVal = refVal + "～" + String.valueOf(targetEntity.getCommonUpperLimit());
                                                    } else {
                                                        refVal = String.valueOf(targetEntity.getCommonUpperLimit());
                                                    }
                                                    max = targetEntity.getCommonUpperLimit();
                                                }
                                            }
                                        }
                                    }
                                    // ObservationレコードのUnitと共用基準範囲レコードのUnitが異なっている場合、基準値に単位を設定（※病院検査結果のみ）
                                    if (entityList.get(0).getDataInputTypeCd() == -3) {
                                        if (dto.get(i).getUnit() != null) {
                                            if (!dto.get(i).getUnit().equals(targetEntity.getUnit())) {
                                                refVal = refVal + "  " + targetEntity.getUnit();
                                                refUnit = targetEntity.getUnit();
                                            }
                                        }
                                    }
                                }
                            }
                            // ObservationレコードのUnitと共用基準範囲レコードのUnitが異なっている場合、判定は行わない
                            if (refUnit == null) {
                                // 基準値範囲外の場合はアラート色を設定
                                if (entityList.get(i).getObservationValue() != null) {
                                    Pattern pattern = Pattern.compile("^([0-9]\\d*)(\\.\\d+)?$|^(-[0-9]\\d*)(\\.\\d+)?$");
                                    if (pattern.matcher(entityList.get(i).getObservationValue()).matches()) {
                                        Double value = Double.parseDouble(entityList.get(i).getObservationValue());
                                        if (min != null && max != null) {
                                            if (value < min || value > max) {
                                                dto.get(i).setAlertColor(ALERT_BACKGROUND_COLOR);
                                            }
                                        }
                                    }
                                }
                            }
                            dto.get(i).setReferenceValue(refVal);
                        }
                        
                        // JLAC10分析物マスタもしくはJLAC11測定物マスタのHospitalLabResultTargetFlg=trueのものだけ抽出
                        List<Integer> removeList = new ArrayList<>();
                        for (int i = 0; i < dto.size(); i++) {
                            if (dto.get(i).getDataInputTypeCd() == -3) {
//                                ObservationDefinitionJlac10Adapter adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
//                                List<ObservationDefinitionJlac10Entity> dList = adapter.findByPrimaryKeyGetAll(dto.get(i).getId());
                                boolean delFlg = false;
                                // JLAC10分析物マスタに存在するか確認
                                for (int ii = 0; ii < entityList.size(); ii++) {
                                    String jlac10 = null;
                                    if (entityList.get(ii).getJlac10() != null) {
                                        jlac10 = entityList.get(ii).getJlac10().substring(0, 5);
                                    }
                                    JLAC10AnalyteCodeAdapter a10 = new JLAC10AnalyteCodeAdapter(dao.getConnection());
                                    JLAC10AnalyteCodeEntity e10 = new JLAC10AnalyteCodeEntity();
                                    e10 = a10.findRecord(jlac10);
                                    if (e10 != null) {
                                        if (e10.isHospitalLabResultTargetFlg()) {
                                            delFlg = true;
                                        }
                                    }
                                }
                                // JLAC10分析物マスタに存在しなければJLAC11測定物マスタを確認
                                if (!delFlg) {
                                    for (int ii = 0; ii < entityList.size(); ii++) {
                                        String jlac11 = null;
                                        if (entityList.get(ii).getJlac10() != null) {
                                            jlac11 = entityList.get(ii).getJlac10().substring(0, 5);
                                        }
                                        JLAC11AnalyteCodeAdapter a11 = new JLAC11AnalyteCodeAdapter(dao.getConnection());
                                        JLAC11AnalyteCodeEntity e11 = new JLAC11AnalyteCodeEntity();
                                        e11 = a11.findRecord(jlac11);
                                        if (e11 != null) {
                                            if (e11.isHospitalLabResultTargetFlg()) {
                                                delFlg = true;
                                            }
                                        }
                                    }
                                }
                                if (!delFlg) {
                                    removeList.add(i);
                                }
                            }
                        }
                        Collections.sort(removeList, Collections.reverseOrder());
                        // removeListにセットされているインデックスを削除していく
                        for (int j = 0; j < removeList.size(); j++) {
                            for (int jj = 0; jj < dto.size(); jj++) {
                                if (removeList.get(j) == jj) {
                                    int target = removeList.get(j);
                                    dto.remove(target);
                                }
                            }
                        }
                        
                        // レスポンスを作成
                        List<MeasurementTestViewDto> viewDto = new ArrayList<>();
                        MeasurementTestViewDto view = new MeasurementTestViewDto();
                        view.setResultList(dto);
                        viewDto.add(view);
                        responseDto.setPastDate(pastDate);
                        responseDto.setViewList(viewDto);
                        responseDto.setTitle(title);
                        responseDto.setBackgroundColor(backgroundColor);
                        responseDto.setObservationEventId(entityList.get(0).getObservationEventId());
                        responseDto.setStatus(ResponseBaseDto.SUCCESS);
                    } else {
                        // 検査結果が存在しない場合
                    	responseDto.setStatus(ResponseBaseDto.SUCCESS);
                    }
                } else {
                    // 対象患者の検査結果リストが存在しない場合
                    responseDto.setStatus(ResponseBaseDto.SUCCESS);
                }
                

            // 日付移動ボタンタップ時の処理
            } else {
                // ステータス
                String status = requestDto.getStatus();
                // 該当患者の全ObservationEventレコードを取得
                List<ObservationEventEntity> eList = observationEventService.findByPhrId(requestDto.getPhrId());
                // 現在表示されている結果のObservationEventId
                String obEvId = requestDto.getObservationEventId();
                // 現在表示されている結果のObservationEventIdのリスト内の番号
                int num = 0;
                for (int h = 0; h < eList.size(); h++) {
                    if (eList.get(h).getObservationEventId().equals(obEvId)) {
                        num = h;
                    }
                }
                // targetDateと一致するObservationEventレコードを特定
                int targetNum = 0;
                for (int i = 0; i < eList.size(); i++) {
                    Timestamp time =eList.get(i).getExaminationDate();
                    String timeStr = sdf1.format(time);
                    if (requestDto.getTargetDate().equals(timeStr)) {
                        if (!eList.get(i).getObservationEventId().equals(obEvId)) {
                            if (status.equals("past")) { 
                                if (num +1 == i) {
                                    targetNum = i;
                                    break;
                                }
                            } else if (status.equals("future")) {
                                if (num -1 == i) {
                                    targetNum = i;
                                    break;
                                }
                            }
                        }
                    }
                }
                // 過去日・未来日があればセット
                String pastDate = null;
                String futureDate = null;
                if (targetNum != 0) {
                    if (eList.size() - 1 != targetNum) {
                        Timestamp past =eList.get(targetNum + 1).getExaminationDate();
                        pastDate = sdf1.format(past);
                    }
                    Timestamp future =eList.get(targetNum - 1).getExaminationDate();
                    futureDate = sdf1.format(future);
                } else {
                    Timestamp past =eList.get(targetNum + 1).getExaminationDate();
                    pastDate = sdf1.format(past);
                }
                // DataInputTypeCdを取得
                int dataInputTypeCd = 0;
                ObservationEventEntity obEvEntity = new ObservationEventEntity();
                obEvEntity = observationEventService.findById(eList.get(targetNum).getObservationEventId());
                dataInputTypeCd = obEvEntity.getDataInputTypeCd();
                // ObservationEventEntityリストを取得(DataInputTypeCdの値によって処理を分岐)
                List<ObservationEventEntity> entityList = new ArrayList<>();
                if (dataInputTypeCd == 1) {
                    entityList = observationEventService.searchAllByEventId(eList.get(targetNum).getObservationEventId());
                } else if (dataInputTypeCd == 2 || dataInputTypeCd == 3 || dataInputTypeCd == 4 || dataInputTypeCd == 5) {
                    entityList = observationEventService.searchAllByEventIdForSelfCheck(eList.get(targetNum).getObservationEventId());
                }
//                // ObservationEventEntityリストを取得
//                List<ObservationEventEntity> entityList = observationEventService.searchAllByEventId(eList.get(targetNum).getObservationEventId());
                // 医療機関名を取得
                String hospitalName = this.getHospitalName(entityList.get(0).getMedicalOrganizationCd());
                // 該当年月日を取得
                String date = null;
                if (entityList.get(0).getExaminationDate() != null) {
                    Timestamp preDate = entityList.get(0).getExaminationDate();
                    date = sdf2.format(preDate);
                }
                // 検査結果入力種別CDの値によって背景色のコードを取得(「-3：:検査結果」,「-4：自己測定」,「-5：特定健診」)
                // また、合わせて測定・全検査結果画面の表タイトルを設定
                String backgroundColor = null;
                String title = null;
                if (entityList.get(0).getDataInputTypeCd() == -3) {
                    backgroundColor = EXAM_BACKGROUND_COLOR;
                    title = hospitalName + " (" + date + ")";
                } else if (entityList.get(0).getDataInputTypeCd() == -4) {
                    backgroundColor = SELF_BACKGROUND_COLOR;
                    title = "自己測定 (" + date + ")";
                } else if (entityList.get(0).getDataInputTypeCd() == -5) {
                    backgroundColor = CHECKUP_BACKGROUND_COLOR;
                    title = "特定健診 (" + hospitalName + ")" + " (" + date + ")";
                }
                List<ObservationDto> dto = new ArrayList<>();
                for (int i = 0; i < entityList.size(); i++) {
                    dto.add(new ObservationDto());
                    dto.get(i).setAlertLevel(entityList.get(i).getAlertLevelCd());
                    dto.get(i).setDataInputTypeCd(entityList.get(i).getDataInputTypeCd());
                    dto.get(i).setEventId(entityList.get(i).getObservationEventId());
                    dto.get(i).setExaminationDate(new SimpleDateFormat("yyyy/MM/dd").format(entityList.get(i).getExaminationDate()));
                    dto.get(i).setId(entityList.get(i).getObservationDefinitionId());
                    dto.get(i).setUnit(entityList.get(i).getUnitValue());
                    // 特定健診の場合
                    if (entityList.get(0).getDataInputTypeCd() == -5) {
                        String enumName = observationEventService.findByIdAndEnum(entityList.get(i).getObservationDefinitionId(), entityList.get(i).getObservationValue(), 7);
                        if (enumName != null) {
                            dto.get(i).setValue(enumName);
                        } else {
                            dto.get(i).setValue(entityList.get(i).getObservationValue());
                        }
                    } else {
                        dto.get(i).setValue(entityList.get(i).getObservationValue());
                    }
                    dto.get(i).setName(entityList.get(i).getObservationDefinitionName());
                    
                    // 基準値が設定されているかを確認（優先順位：Observationテーブル→CommonReferenceRangeテーブル）
                    CommonReferenceRangeAdapter commonAdapter = new CommonReferenceRangeAdapter(dao.getConnection());
                    ObservationDefinitionJlac10Adapter jlac10Adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
                    Double min = null;
                    Double max = null;
                    String refVal = null;
                    String refUnit = null;
                    // Observationに基準値が設定されている場合
                    if (entityList.get(i).getMinReferenceValue() != null || entityList.get(i).getMaxReferenceValue() != null) {
                        if (entityList.get(i).getMinReferenceValue() != null) {
                            refVal = String.valueOf(entityList.get(i).getMinReferenceValue());
                            min = entityList.get(i).getMinReferenceValue();
                        }
                        if (entityList.get(i).getMaxReferenceValue() != null) {
                            if (refVal != null) {
                                refVal = refVal + " ～ " + String.valueOf(entityList.get(i).getMaxReferenceValue());
                            } else {
                                refVal = String.valueOf(entityList.get(i).getMaxReferenceValue());
                            }
                            max = entityList.get(i).getMaxReferenceValue();
                        }
                    // Observationに基準値が未設定かつCommonReferenceRangeに基準値が設定されている場合
                    } else {
                        // ObservationDefinitionIdからJLAC10コードを取得
                        List<ObservationDefinitionJlac10Entity> list = jlac10Adapter.findByPrimaryKeyGetAll(entityList.get(i).getObservationDefinitionId());
                        CommonReferenceRangeEntity targetEntity = null;
                        for (ObservationDefinitionJlac10Entity e : list) {
                        	String jlac10 = null;
                            String jlac11 = null;
                            if (e.getJLAC10() != null) {
                                jlac10 = e.getJLAC10().substring(0, 5);
                            }
                            if (e.getJLAC11() != null) {
                                jlac11 = e.getJLAC11().substring(0, 5);
                            }
                            CommonReferenceRangeEntity commonEntity = new CommonReferenceRangeEntity();
                            commonEntity = commonAdapter.findByJLAC10AndJLAC11(jlac10, jlac11);
                            if (commonEntity.getJLAC10AnalyteCode() != null) {
                                targetEntity = commonEntity;
                                break;
                            }
                        }
                        if (targetEntity != null) {
                            // 男女別の基準値を取得
                            PatientAdapter patAdapter = new PatientAdapter(dao.getConnection());
                            PatientEntity pat = patAdapter.findByPrimaryKey(entityList.get(i).getPHR_ID());
                            if (pat.getSexCd().equals("M")) {
                                if (targetEntity.getMaleLowerLimit() != null || targetEntity.getMaleUpperLimit() != null) {
                                    if (targetEntity.getMaleLowerLimit() != null) {
                                        refVal = String.valueOf(targetEntity.getMaleLowerLimit());
                                        min = targetEntity.getMaleLowerLimit();
                                    }
                                    if (targetEntity.getMaleUpperLimit() != null) {
                                        if (refVal != null) {
                                            refVal = refVal + "～" + String.valueOf(targetEntity.getMaleUpperLimit());
                                        } else {
                                            refVal = String.valueOf(targetEntity.getMaleUpperLimit());
                                        }
                                        max = targetEntity.getMaleUpperLimit();
                                    }
                                } else {
                                    // 男女別の基準値が未設定の場合は共通の基準値を取得
                                    if (targetEntity.getCommonLowerLimit() != null || targetEntity.getCommonUpperLimit() != null) {
                                        if (targetEntity.getCommonLowerLimit() != null) {
                                            refVal = String.valueOf(targetEntity.getCommonLowerLimit());
                                            min = targetEntity.getCommonLowerLimit();
                                        }
                                        if (targetEntity.getCommonUpperLimit() != null) {
                                            if (refVal != null) {
                                                refVal = refVal + "～" + String.valueOf(targetEntity.getCommonUpperLimit());
                                            } else {
                                                refVal = String.valueOf(targetEntity.getCommonUpperLimit());
                                            }
                                            max = targetEntity.getCommonUpperLimit();
                                        }
                                    }
                                }
                            } else if (pat.getSexCd().equals("F")) {
                                if (targetEntity.getFemaleLowerLimit() != null || targetEntity.getFemaleUpperLimit() != null) {
                                    if (targetEntity.getFemaleLowerLimit() != null) {
                                        refVal = String.valueOf(targetEntity.getFemaleLowerLimit());
                                        min = targetEntity.getFemaleLowerLimit();
                                    }
                                    if (targetEntity.getFemaleUpperLimit() != null) {
                                        if (refVal != null) {
                                            refVal = refVal + "～" + String.valueOf(targetEntity.getFemaleUpperLimit());
                                        } else {
                                            refVal = String.valueOf(targetEntity.getFemaleUpperLimit());
                                        }
                                        max = targetEntity.getFemaleUpperLimit();
                                    }
                                } else {
                                    // 男女別の基準値が未設定の場合は共通の基準値を取得
                                    if (targetEntity.getCommonLowerLimit() != null || targetEntity.getCommonUpperLimit() != null) {
                                        if (targetEntity.getCommonLowerLimit() != null) {
                                            refVal = String.valueOf(targetEntity.getCommonLowerLimit());
                                            min = targetEntity.getCommonLowerLimit();
                                        }
                                        if (targetEntity.getCommonUpperLimit() != null) {
                                            if (refVal != null) {
                                                refVal = refVal + "～" + String.valueOf(targetEntity.getCommonUpperLimit());
                                            } else {
                                                refVal = String.valueOf(targetEntity.getCommonUpperLimit());
                                            }
                                            max = targetEntity.getCommonUpperLimit();
                                        }
                                    }
                                }
                            }
                            // ObservationレコードのUnitと共用基準範囲レコードのUnitが異なっている場合、基準値に単位を設定（※病院検査結果のみ）
                            if (entityList.get(0).getDataInputTypeCd() == -3) {
                                if (dto.get(i).getUnit() != null) {
                                    if (!dto.get(i).getUnit().equals(targetEntity.getUnit())) {
                                        refVal = refVal + "  " + targetEntity.getUnit();
                                        refUnit = targetEntity.getUnit();
                                    }
                                }
                            }
                        }
                    }
                    // ObservationレコードのUnitと共用基準範囲レコードのUnitが異なっている場合、判定は行わない
                    if (refUnit == null) {
                        // 基準値範囲外の場合はアラート色を設定
                        if (entityList.get(i).getObservationValue() != null) {
                            Pattern pattern = Pattern.compile("^([0-9]\\d*)(\\.\\d+)?$|^(-[0-9]\\d*)(\\.\\d+)?$");
                            if (pattern.matcher(entityList.get(i).getObservationValue()).matches()) {
                                Double value = Double.parseDouble(entityList.get(i).getObservationValue());
                                if (min != null && max != null) {
                                    if (value < min || value > max) {
                                        dto.get(i).setAlertColor(ALERT_BACKGROUND_COLOR);
                                    }
                                }
                            }
                        }
                    }
                    dto.get(i).setReferenceValue(refVal);
                }
                
                // JLAC10分析物マスタもしくはJLAC11測定物マスタのHospitalLabResultTargetFlg=trueのものだけ抽出
                List<Integer> removeList = new ArrayList<>();
                for (int i = 0; i < dto.size(); i++) {
                    if (dto.get(i).getDataInputTypeCd() == -3) {
//                        ObservationDefinitionJlac10Adapter adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
//                        List<ObservationDefinitionJlac10Entity> dList = adapter.findByPrimaryKeyGetAll(dto.get(i).getId());
                        boolean delFlg = false;
                        // JLAC10分析物マスタに存在するか確認
                        for (int ii = 0; ii < entityList.size(); ii++) {
                            String jlac10 = null;
                            if (entityList.get(ii).getJlac10() != null) {
                                jlac10 = entityList.get(ii).getJlac10().substring(0, 5);
                            }
                            JLAC10AnalyteCodeAdapter a10 = new JLAC10AnalyteCodeAdapter(dao.getConnection());
                            JLAC10AnalyteCodeEntity e10 = new JLAC10AnalyteCodeEntity();
                            e10 = a10.findRecord(jlac10);
                            if (e10 != null) {
                                if (e10.isHospitalLabResultTargetFlg()) {
                                    delFlg = true;
                                }
                            }
                        }
                        // JLAC10分析物マスタに存在しなければJLAC11測定物マスタを確認
                        if (!delFlg) {
                            for (int ii = 0; ii < entityList.size(); ii++) {
                                String jlac11 = null;
                                if (entityList.get(ii).getJlac10() != null) {
                                    jlac11 = entityList.get(ii).getJlac10().substring(0, 5);
                                }
                                JLAC11AnalyteCodeAdapter a11 = new JLAC11AnalyteCodeAdapter(dao.getConnection());
                                JLAC11AnalyteCodeEntity e11 = new JLAC11AnalyteCodeEntity();
                                e11 = a11.findRecord(jlac11);
                                if (e11 != null) {
                                    if (e11.isHospitalLabResultTargetFlg()) {
                                        delFlg = true;
                                    }
                                }
                            }
                        }
                        if (!delFlg) {
                            removeList.add(i);
                        }
                    }
                }
                Collections.sort(removeList, Collections.reverseOrder());
                // removeListにセットされているインデックスを削除していく
                for (int j = 0; j < removeList.size(); j++) {
                    for (int jj = 0; jj < dto.size(); jj++) {
                        if (removeList.get(j) == jj) {
                            int target = removeList.get(j);
                            dto.remove(target);
                        }
                    }
                }
                
                // レスポンスを作成
                List<MeasurementTestViewDto> viewDto = new ArrayList<>();
                MeasurementTestViewDto view = new MeasurementTestViewDto();
                view.setResultList(dto);
                viewDto.add(view);
                responseDto.setPastDate(pastDate);
                responseDto.setViewList(viewDto);
                responseDto.setTitle(title);
                responseDto.setBackgroundColor(backgroundColor);
                responseDto.setFutureDate(futureDate);
                responseDto.setObservationEventId(entityList.get(0).getObservationEventId());
                responseDto.setStatus(ResponseBaseDto.SUCCESS);
            }
            return responseDto;

        } catch (Throwable t) {
            logger.error("", t);
            throw new Exception("[" + this.getClass().getSimpleName() + "] Error", t);
        }
    }

    /**
     * 医療機関名を取得
     * @param medicalOrganizationCd
     * @return hospitalName
     * @throws Throwable 
     */
    public String getHospitalName (String medicalOrganizationCd) throws Throwable {
        DataAccessObject dao = null;
        dao = new DataAccessObject();
        String hospitalName = null;

        MedicalOrganizationAdapter medicalOrganizationAdapter = new MedicalOrganizationAdapter(dao.getConnection());
        MedicalOrganizationEntity medicalOrganizationEntity = medicalOrganizationAdapter.findByPrimaryKey(medicalOrganizationCd);
        if (medicalOrganizationEntity != null) {
            hospitalName = medicalOrganizationEntity.getMedicalOrganizationName();
        }

        return hospitalName;
    }
}
