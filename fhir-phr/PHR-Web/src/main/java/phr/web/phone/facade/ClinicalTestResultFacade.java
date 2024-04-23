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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IObservationEventService;
import phr.service.impl.ObservationEventService;
import phr.utility.ObservationSupportUtility;
import phr.utility.TypeUtility;
import phr.web.phone.dto.ClinicalTestViewDto;
import phr.web.phone.dto.DiseaseTypeDto;
import phr.web.phone.dto.ObservationResultDto;
import phr.web.phone.dto.RequestClinicalTestResultDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseClinicalTestResultDto;

/**
 *
 * @author daisuke
 */
public class ClinicalTestResultFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ClinicalTestResultFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private SimpleDateFormat sdfNum = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");

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
            IObservationEventService service = new ObservationEventService();
            RequestClinicalTestResultDto requestDto = gson.fromJson(json, RequestClinicalTestResultDto.class);
  
            // 対象日より年度を取得
            Date targetDate = TypeUtility.stringToDate(requestDto.getTargetDate());
            SimpleDateFormat sfY = new SimpleDateFormat("yyyy");
            SimpleDateFormat sfM = new SimpleDateFormat("MM");
            int year = Integer.parseInt(sfY.format(targetDate));
            int month = Integer.parseInt(sfM.format(targetDate));
            if (month < 4) {
                year--;
            }
            Timestamp timestamp = new Timestamp(targetDate.getTime());
                      
            // 過去データがあるか確認する
            List<ObservationEventEntity> pastList = service.searchObsevationByFutureDay(requestDto.getPhrId(), timestamp, 1);
            String pastDate = getMaxDate(pastList);
            if(pastDate != null){
                Date pastCheckDate = TypeUtility.stringToDate(pastDate);            
                int pastyear = Integer.parseInt(sfY.format(pastCheckDate));
                int pastmonth = Integer.parseInt(sfM.format(pastCheckDate));
                if (pastmonth < 4) {
                    pastyear--;
                }
                List<InsurerViewDefinitionEntity> pastviewDefList = service.getViewIdList(pastyear, requestDto.getPhrId());
                boolean viewIsNull = true;
                for(InsurerViewDefinitionEntity viewEntity:pastviewDefList){
                    if(viewEntity.getViewId()<100){
                        continue;
                    }
                    viewIsNull = false;
                }
                if(viewIsNull){
                    pastDate = null;
                }
            }
            // 未来データがあるか確認する
            List<ObservationEventEntity> futureList = service.searchObsevationByFutureDay(requestDto.getPhrId(), timestamp, 0);
            String futureDate = getMinDate(futureList);
            if (requestDto.getTargetDate().equals(futureDate)) {
                futureDate = null;
            }
            
            //viewId一覧を取得
            List<InsurerViewDefinitionEntity> viewDefList = service.getViewIdList(year, requestDto.getPhrId());
            List<ClinicalTestViewDto> viewList = new ArrayList();
            
            for(InsurerViewDefinitionEntity viewEntity:viewDefList){
                //vieｗIdが2桁以下のものはシステム予約番号のため取り扱わない
                if(viewEntity.getViewId()<100){
                    continue;
                }
                
                // 各種サービスより必要なデータを取得
                List<DiseaseTypeEntity> diseaseTypeList = service.searchDiseaseType(viewEntity.getViewId());
                Map<Integer, List<String>> diseaseDefinitionMap = service.searchObservationDefinitionDiseaseForMap(viewEntity.getViewId(), 1);
                List<ObservationEventEntity> testRsultObservationList = service.searchObsevationByOrderNo(year, requestDto.getPhrId(), timestamp, 1, viewEntity.getViewId());
                //testRsultObservationList = ObservationSupportUtility.mergeList(testRsultObservationList);
               logger.debug("検査結果List ：" + testRsultObservationList.size());
               
                List<ObservationEventEntity> checkupObservationList = service.searchObsevationByOrderNo(year, requestDto.getPhrId(), timestamp, 3, viewEntity.getViewId());
                //checkupObservationList = ObservationSupportUtility.mergeList(checkupObservationList);
               logger.debug("健診結果List ：" + checkupObservationList.size());
               
                List<ObservationEventEntity> observationList = ObservationSupportUtility.setObsevationKensiToKensa(testRsultObservationList, checkupObservationList);
               logger.debug("マージ後List1 ：" + observationList.size());

               observationList = service.setObservationEnumValue(observationList, viewEntity.getViewId());
               logger.debug("マージ後List2 ：" + observationList.size());

                // 保険者の検査項目を取得
                List<ObservationDefinitionTypeEntity> baseDefinitionTypeList = null;
                if (observationList == null) {
                    int baseYear = Integer.parseInt(sfY.format(new Date()));
                    int baseMonth = Integer.parseInt(sfM.format(new Date()));
                    if (baseMonth < 4) {
                        baseYear--;
                    }

                    baseDefinitionTypeList
                            = service.searchObservationDefinitionType(observationList.get(0).getInsurerNo(),requestDto.getPhrId() , viewEntity.getViewId());
                    if (diseaseDefinitionMap.size() == 1) {
                        diseaseDefinitionMap = service.searchObservationDefinitionDiseaseForMap(baseYear, requestDto.getPhrId(), 1);
                    }
                } else {
                    baseDefinitionTypeList = new ArrayList<>();
                }
                //long nowTime = Integer.parseInt(sdfNum.format(new Date()));
                long nowTime = Integer.parseInt(sdfNum.format(targetDate));

                // 結果のリストを設定する
                // （保険者の検査項目を基準に結果を当てはめる）
                List<ObservationResultDto> resultList = new ArrayList<>();
                if (observationList.size() > 0) {

                    // 保険者の検査項目を取得
                    List<ObservationDefinitionTypeEntity> definitionTypeList
                            = service.searchObservationDefinitionType(observationList.get(0).getInsurerNo(),requestDto.getPhrId() ,viewEntity.getViewId());
                    
                    
                    if (definitionTypeList.size() == 0)
                        definitionTypeList = baseDefinitionTypeList;

                    // 保険者の検査項目を基準に結果を当てはめる
                    for (ObservationDefinitionTypeEntity entity : definitionTypeList) {
                        ObservationResultDto dto = new ObservationResultDto();
                        dto.setId(entity.getObservationDefinitionId());
                        dto.setDisplayName(entity.getDisplayName());
                        Optional<ObservationEventEntity> optEntity
                                = observationList.stream().filter(
                                        x -> x.getObservationDefinitionId().equals(entity.getObservationDefinitionId())
                                ).filter(
                                        x -> x.getObservationValue()!= null
                                ).max(
                                    (a, b) -> a.getExaminationDate().compareTo(b.getExaminationDate())
                                );

                        // 検査項目に対して検査結果がある場合、結果を設定する
                        if (optEntity.isPresent()) {
                            ObservationEventEntity observationEventEntity = optEntity.get();
                            dto.setValue(observationEventEntity.getObservationValue());
                            dto.setUnitValue(observationEventEntity.getUnitValue());
                            if (observationEventEntity.getExaminationDate() != null) {
                                dto.setExaminationDate(sdfDate.format(observationEventEntity.getExaminationDate()));
                            }

                            if (observationEventEntity.getAlertLevelCd() != null) {
                                dto.setValueAlert(observationEventEntity.getAlertLevelCd().toString());
                            } else {
                                dto.setValueAlert("0");
                            }
                            if (observationEventEntity.getReminderTypeCd() == null
                                    || observationEventEntity.getReminderTypeCd() == 0 || observationEventEntity.getReminderTypeCd() == 99) {
                                dto.setDateAlert(Boolean.FALSE.toString());
                            } else {

                                long maxTime;
                                //if (observationEventEntity.getNewExaminationDate() == null) {
                                if (observationEventEntity.getExaminationDate() == null) {    
                                    maxTime = 0;
                                } else {
                                    Calendar calendar = Calendar.getInstance();
                                    //calendar.setTime(observationEventEntity.getNewExaminationDate());
                                    calendar.setTime(observationEventEntity.getExaminationDate());
                                    calendar.add(Calendar.MONTH, observationEventEntity.getReminderTypeCd());
                                    maxTime = Integer.parseInt(sdfNum.format(calendar.getTime()));
                                }
                                if (nowTime <= maxTime) {
                                    dto.setDateAlert(Boolean.FALSE.toString());
                                } else {
                                    dto.setDateAlert(Boolean.TRUE.toString());
                                }

                            }
                        }
                        resultList.add(dto);
                    }
                }

                // 疾病有無を設定する
                List<DiseaseTypeDto> diseaseTypeDtoList = new ArrayList<>();
                for (DiseaseTypeEntity entity : diseaseTypeList) {
                    //if (entity.getDiseaseTypeCd() == 0) {
                    //    continue;
                    //}
                    DiseaseTypeDto dto = new DiseaseTypeDto();
                    dto.setKey(entity.getDiseaseTypeCd());
                    dto.setValue(entity.getName());
                    Optional<ObservationEventEntity> optEntity
                            = observationList.stream().filter(
                                    x -> x.getObservationDefinitionId().equals(entity.getObservationDefinitionId())
                            ).findFirst();

                    dto.setChecked(false);
                    if (optEntity.isPresent()) {
                        dto.setChecked(!TypeUtility.isNullOrEmpty(optEntity.get().observationValue));
                    }


                    diseaseTypeDtoList.add(dto);
                }
                
                // どこにもマップされない項目を共通にセットする Start
                if (!diseaseDefinitionMap.containsKey(0))
                    diseaseDefinitionMap.put(0, new ArrayList<String>());
                
                for (ObservationResultDto dto : resultList) {
                    boolean isExist = false;
                    for (Map.Entry<Integer, List<String>> e : diseaseDefinitionMap.entrySet()) {
                        if (e.getValue().contains(dto.getId())) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        diseaseDefinitionMap.get(0).add(dto.getId());
                    }
                }
                // どこにもマップされない項目を共通にセットする End   
                
                // 全ての疾病にある項目を共通にセットする Start
                // 共通と共通以外のリストに分ける
                List<String> comList = diseaseDefinitionMap.get(0);
                List<List<String>> diseaseList = new ArrayList<>();
                for (Map.Entry<Integer, List<String>> e : diseaseDefinitionMap.entrySet()) {
                    if (e.getKey() == 0)
                        continue;
                    diseaseList.add(e.getValue());
                }
                // 共通以外のリストで全てにある項目を共通にセット
                for (int i = 0; i < diseaseList.size(); i ++) {
                    List<String> idList = diseaseList.get(i);
                    for (String id : idList) {
                        boolean isSet = true;
                        for (int j = 0; j < diseaseList.size(); j ++) {
                            if (i == j) continue;
                            List<String> idList2 = diseaseList.get(j);
                            if (!idList2.contains(id)) {
                                isSet = false;
                                break;
                            }
                        }
                        if (isSet) {
                            if (!comList.contains(id))
                                comList.add(id);
                        }
                    }
                }
                // 全ての疾病にある項目を共通にセットする End
                
                //ビューにセット
                ClinicalTestViewDto viewDto = new ClinicalTestViewDto();
                viewDto.setDiseaseDefinitionMap(diseaseDefinitionMap);
                viewDto.setDiseaseTypeList(diseaseTypeDtoList);
                viewDto.setResultList(resultList);
                viewDto.setViewId(viewEntity.getViewId());
                viewDto.setViewName(viewEntity.getShortName());
                   
                viewList.add(viewDto);
            }
            
            if(viewList.isEmpty()){
              pastDate = null;
              ClinicalTestViewDto viewDto = new ClinicalTestViewDto();
              viewDto.setViewId(99);
              viewDto.setViewName("noViewData");
                viewDto.setDiseaseDefinitionMap(new HashMap());
                viewDto.setDiseaseTypeList(new ArrayList());
                viewDto.setResultList(new ArrayList());              
              viewList.add(viewDto);
            }
            
            // レスポンスを作成
            ResponseClinicalTestResultDto responseDto = new ResponseClinicalTestResultDto();
//            responseDto.setDiseaseDefinitionMap(diseaseDefinitionMap);
//            responseDto.setDiseaseTypeList(diseaseTypeDtoList);
//            responseDto.setResultList(resultList);
            responseDto.setViewList(viewList);
            responseDto.setPastDate(pastDate);
            responseDto.setFutureDate(futureDate);
            responseDto.setStatus(ResponseBaseDto.SUCCESS);

            return responseDto;

        } catch (Throwable t) {
            logger.error("", t);
            throw new Exception("[" + this.getClass().getSimpleName() + "] Error", t);
        }
    }

    /**
     * リストの中から最小の日付を取得する
     *
     * @param list
     * @return
     */
    private String getMinDate(List<ObservationEventEntity> list) {
        String targetDate = null;
        if (list != null) {
            long baseNum = Long.MAX_VALUE;
            for (ObservationEventEntity pastEntity : list) {
                //if (pastEntity.getDataInputTypeCd() != 1) {
                //    continue;
                //}
                if (baseNum > pastEntity.getExaminationDate().getTime()) {
                    baseNum = pastEntity.getExaminationDate().getTime();
                }
            }
            if (baseNum < Long.MAX_VALUE) {
                Date d = new Date(baseNum);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                calendar.add(Calendar.DAY_OF_MONTH, 1);                
                
                targetDate = TypeUtility.dateToString(calendar.getTime());
//                targetDate = TypeUtility.dateToString(new Date(baseNum));
            }
        }
        return targetDate;
    }
    
    /**
     * リストの中から最大の日付を取得する
     *
     * @param list
     * @return
     */
    private String getMaxDate(List<ObservationEventEntity> list) {
        String targetDate = null;
        if (list != null) {
            long baseNum = Long.MIN_VALUE;
            for (ObservationEventEntity pastEntity : list) {
                //if (pastEntity.getDataInputTypeCd() != 1) {
                //    continue;
                //}
                if (baseNum < pastEntity.getExaminationDate().getTime()) {
                    baseNum = pastEntity.getExaminationDate().getTime();
                }
            }
            if (baseNum > Long.MIN_VALUE) {
                Date d = new Date(baseNum);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                targetDate = TypeUtility.dateToString(calendar.getTime());
            }
        }
        return targetDate;
    }
}
