/**！！未完成！！
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：健診(健診・問診・診察)結果取得クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/07
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import phr.web.phone.dto.ResponseBaseDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.util.Date;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.PhoneCUItemEntity;
import phr.datadomain.entity.PhoneCUValueEntity;

import phr.service.IPhoneCheckUpResultService;
import phr.service.impl.AlertSetService;
import phr.service.impl.ObservationDefinitionScriptService;
import phr.service.impl.ObservationEventService;
import phr.service.impl.ObservationService;
import phr.service.impl.PatientService;
import phr.service.impl.PhoneCheckUpResultService;
import phr.web.phone.dto.CheckUpResultDataListDto;
import phr.web.phone.dto.CheckUpResultListDto;
import phr.web.phone.dto.CheckUpResultValueDto;
import phr.web.phone.dto.RequestCheckUpResultDto;
import phr.web.phone.dto.ResponseCheckUpResultDto;
/**
 *
 * @author iwaasa
 */
public class CheckUpResultFacade extends PhoneFacade{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CheckUpResultFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    private static final String xmlPath = PhrConfig.getConfigProperty(ConfigConst.XML_PATH);
    
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        logger.debug("Start");
        
        IPhoneCheckUpResultService service = new PhoneCheckUpResultService();
        RequestCheckUpResultDto requestDto = gson.fromJson(json, RequestCheckUpResultDto.class);
        ResponseCheckUpResultDto responseDto = new ResponseCheckUpResultDto();
        SimpleDateFormat sf = new SimpleDateFormat("YYYY年MM月dd日");
        ObservationEventService observationEventService = new ObservationEventService();
        ObservationService observationService = new ObservationService();
        ObservationDefinitionScriptService scriptService = new ObservationDefinitionScriptService();
        PatientService pService = new PatientService();
        String fileData = new String();
        
        // 健診結果画面に表示する医療機関名・健診実施日
        String hospitalName = null;
        String date = null;
        // 過去日・未来日が存在するかどうか
        boolean beforeFlg = false;
        boolean futureFlg = false;
        // ObservationEventId
        String medicalCheckupId = null;
        String inquiryId = null;
        String examinationId = null;
        
        String status = requestDto.getStatus();
        String targetDate = requestDto.getTargetDate();
        List<CheckUpResultListDto> resultList = new ArrayList<>();
        List<String> inspectionTypeList = requestDto.getInspectionTypeList();
        String referenceValue = null;
        
        // 結果値背景色の色分け処理
        // まず、該当患者のObservationEventIdリストを取得
        List<String> idList = new ArrayList<>();
        idList = observationEventService.findByPhridAndDatainputtypecd(requestDto.getPhrId(), "1");
        // 取得したObservationEventIdごとに管理疾患が含まれているか確認
        List<Integer> diseaseList = new ArrayList<>();
        if (idList.size() != 0) {
            for (String id : idList) {
                diseaseList = observationService.findById(id);
                if (diseaseList.size() == 0) {
                    diseaseList.add(0);
                }
            }
        }
        PatientEntity pEntity = new PatientEntity();
        pEntity = pService.findById(requestDto.getPhrId());
        
        // DBから該当患者の健診結果の年月リストを取得
        List<Date> examdateList = service.findById(requestDto.getPhrId());
        
        // 初期表示(status = nullのとき)
        if (status == null) {
            for (String inspectionType : inspectionTypeList) {
                CheckUpResultListDto resultDto = new CheckUpResultListDto();
                resultDto.setInspectionType(inspectionType);
                // DBから健診結果の年月リストを取得
//                List<Date> examdateList = service.getYearList(requestDto.getPhrId(), inspectionType);
                // 初期は最新日のものだけを表示
                if (examdateList.size() != 0) {
                    resultDto.setExamDate(sf.format(examdateList.get(0)));
                    if (date == null) {
                        date = sf.format(examdateList.get(0));
                    }
                    List<Date> tarDate = new ArrayList<>();
                    tarDate.add(examdateList.get(0));
                    // 過去日が存在すればフラグにtrueをセット
                    if (examdateList.size() != 0) {
                        if (examdateList.size() > 1) {
                            beforeFlg = true;
                        }
                    }
//                    // 健診日とPHR_IDから医療機関名を取得
//                    if (hospitalName == null) {
//                        hospitalName = service.getHospitalName(examdateList.get(0), requestDto.getPhrId());
//                    }
//                    List<String> yearlist = new ArrayList<>();
//                    if (!examdateList.isEmpty()) {
//                        for (Date edate : examdateList) {
//                            String year = sf.format(edate);
//                            yearlist.add(year);
//                        }
//                        yearlist.sort((a,b)->b.compareTo(a));
//                    }
//                    resultDto.setYearList(yearlist);
                    //DBから項目リストを取得
                    List<PhoneCUItemEntity> observationList = service.getItemList(inspectionType, requestDto.getPhrId());
                    List<CheckUpResultDataListDto> dataList = new ArrayList<>();
                    if (!observationList.isEmpty()) {
                        for (PhoneCUItemEntity itemEntity : observationList) {
                            CheckUpResultDataListDto datadto = new CheckUpResultDataListDto();
                            datadto.setTitle(itemEntity.getDisplayName());
//                            if (itemEntity.getUnitValue() == null) {
//                                datadto.setUnit("-");
//                            } else {
//                                datadto.setUnit(itemEntity.getUnitValue());
//                            }
                            if (itemEntity.getUnitValue() != null) {
                                datadto.setUnit(itemEntity.getUnitValue());
                            }
                            //DBから対象項目の検査値リストを取得 
                            List<PhoneCUValueEntity> valueList = service.getValueList(requestDto.getPhrId(), inspectionType, itemEntity.getObservationDefinitionId(), tarDate);
//                            List<PhoneCUValueEntity> valueList = service.getValueList(requestDto.getPhrId(), "3",itemEntity.getObservationDefinitionId(),examdateList);
                            if (valueList.get(0) != null) {
                                if (hospitalName == null) {
                                    hospitalName = this.getHospitalName(valueList.get(0).getMedicalOrganizationCd());
                                }
                            }
                            if (valueList.get(0) != null) {
                                if (inspectionType.equals("3") && medicalCheckupId == null) {
                                    medicalCheckupId = valueList.get(0).getObservationEventId();
                                } else if (inspectionType.equals("4") && inquiryId == null) {
                                    inquiryId = valueList.get(0).getObservationEventId();
                                } else if (inspectionType.equals("5") && examinationId == null) {
                                    examinationId = valueList.get(0).getObservationEventId();
                                }
                            }
                            List<CheckUpResultValueDto> valueDtolist = new ArrayList<>();
                            for (PhoneCUValueEntity valueEntity : valueList) {
                                CheckUpResultValueDto valuedto = new CheckUpResultValueDto();
//                                if (valueEntity==null) {
//                                    valuedto.setExamdate("-");
//                                    valuedto.setData("-");
//                                } else {
//                                    valuedto.setExamdate(sf.format(valueEntity.getExamdate()));
//                                    valuedto.setData(valueEntity.getValue());
//                                }
                                if (valueEntity != null) {
                                    valuedto.setExamdate(sf.format(valueEntity.getExamdate()));
                                    valuedto.setData(valueEntity.getValue());
                                    // マスタとObservationレコードで単位が一致しているか確認
                                    boolean unitFlg = true;
                                    if (datadto.getUnit() != null) {
                                        if (!datadto.getUnit().equals(valueEntity.getUnit())) {
                                            // 不一致の場合はObservationレコードのunitを優先
                                            unitFlg = false;
                                            datadto.setUnit(valueEntity.getUnit());
                                        }
                                    }
                                    // 基準値が含まれていればセットする
                                    if (valueEntity.getMaxReferenceValue() != null && valueEntity.getMinReferenceValue() != null) {
                                        referenceValue = valueEntity.getMinReferenceValue() + "~" + valueEntity.getMaxReferenceValue();
                                        valuedto.setReferenceValue(referenceValue);
                                        // 結果値が基準値範囲内かどうか判断する
                                        if (unitFlg) {
                                            boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                            if (isNumeric) {
                                                double val = Double.parseDouble(valueEntity.getValue());
                                                double min = Double.parseDouble(valueEntity.getMinReferenceValue());
                                                double max = Double.parseDouble(valueEntity.getMaxReferenceValue());
//                                                if (min >= val || val >= max) {
                                                if (min > val || val > max) {
                                                    valuedto.setFontColor("#ff0000");
                                                }
                                            }
                                        }
                                    } else if (valueEntity.getMaxReferenceValue() != null && valueEntity.getMinReferenceValue() == null) {
                                        referenceValue = "~" + valueEntity.getMaxReferenceValue();
                                        valuedto.setReferenceValue(referenceValue);
                                        // 結果値が基準値範囲内かどうか判断する
                                        if (unitFlg) {
                                            boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                            if (isNumeric) {
                                                double val = Double.parseDouble(valueEntity.getValue());
                                                double max = Double.parseDouble(valueEntity.getMaxReferenceValue());
//                                                if (val >= max) {
                                                if (val > max) {
                                                    valuedto.setFontColor("#ff0000");
                                                }
                                            }
                                        }
                                    } else if (valueEntity.getMaxReferenceValue() == null && valueEntity.getMinReferenceValue() != null) {
                                        referenceValue = valueEntity.getMinReferenceValue() + "~";
                                        valuedto.setReferenceValue(referenceValue);
                                        // 結果値が基準値範囲内かどうか判断する
                                        if (unitFlg) {
                                            boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                            if (isNumeric) {
                                                double val = Double.parseDouble(valueEntity.getValue());
                                                double min = Double.parseDouble(valueEntity.getMinReferenceValue());
//                                                if (min >= val) {
                                                if (min > val) {
                                                    valuedto.setFontColor("#ff0000");
                                                }
                                            }
                                        }
                                    }
                                    // 結果値の背景色を設定
                                    List<Integer> alertLevelList = new ArrayList<>();
                                    if (unitFlg) {
                                        ObservationDefinitionScriptEntity scriptEntity = new ObservationDefinitionScriptEntity();
                                        if (diseaseList.size() == 0) {
                                            // スクリプトを取得
                                            scriptEntity = scriptService.findByIdAndDiseasetypecd(itemEntity.getObservationDefinitionId(), 0);
                                            String param = null;
                                            if (itemEntity.getObservationDefinitionId().equals("0000000089")) {
                                                // 心電図所見有無の場合はvalueの値からEnumValueを取得
                                                param = service.getEnumValue(valueEntity.getValue(), "0000000089");
                                            } else {
                                                param = valueEntity.getValue();
                                            }
                                            if (scriptEntity.getAlertLevelScript() != null && param != null) {
//                                                Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), valueEntity.getValue(), pEntity);
                                                Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), param, pEntity);
                                                alertLevelList.add(alertLevel);
                                            }
                                        } else {
                                            for (Integer i : diseaseList) {
                                                // スクリプトを取得
                                                scriptEntity = scriptService.findByIdAndDiseasetypecd(itemEntity.getObservationDefinitionId(), i);
                                                String param = null;
                                                if (itemEntity.getObservationDefinitionId().equals("0000000089")) {
                                                    // 心電図所見有無の場合はvalueの値からEnumValueを取得
                                                    param = service.getEnumValue(valueEntity.getValue(), "0000000089");
                                                } else {
                                                    param = valueEntity.getValue();
                                                }
                                                if (scriptEntity.getAlertLevelScript() != null && param != null) {
//                                                    Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), valueEntity.getValue(), pEntity);
                                                    Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), param, pEntity);
                                                    alertLevelList.add(alertLevel);
                                                }
                                            }
                                        }
                                    }
                                    // アラートレベルが複数あればリスクの高い方を優先
                                    Integer level = 9;
                                    if (alertLevelList.size() != 0) {
                                        Collections.sort(alertLevelList, Collections.reverseOrder());
                                        level = alertLevelList.get(0);
                                    }
                                    // アラートレベルによって色を選択
                                    String color = this.selectColor(level);
                                    valuedto.setBackgroundColor(color);
                                }
                                valueDtolist.add(valuedto);
                            }
                            valueDtolist.sort((a,b) -> b.getExamdate().compareTo(a.getExamdate()));
//                            if (valueDtolist.get(0).getData().equals(" ")) {
                            if (valueDtolist.get(0).getData() == null || valueDtolist.get(0).getData().equals(" ")) {
                                datadto.setUnit(" ");
                            }
                            datadto.setResults(valueDtolist);
                            dataList.add(datadto);
                        }
                    }
                    resultDto.setDataList(dataList);
                    resultList.add(resultDto);
                }
            }
        } else if (status.equals("before")) {
            // 過去日ボタン押下時
            for (String inspectionType : inspectionTypeList) {
                CheckUpResultListDto resultDto = new CheckUpResultListDto();
                resultDto.setInspectionType(inspectionType);
                // DBから健診結果の年月リストを取得
//                List<Date> examdateList = service.getYearList(requestDto.getPhrId(), inspectionType);
                // targetDateより一つ過去のものを表示
                int num = 0;
                for (int i = 0; i < examdateList.size(); i++) {
                    if (targetDate.equals(sf.format(examdateList.get(i)))) {
                        num = i + 1;
                        break;
                    }
                }
                resultDto.setExamDate(sf.format(examdateList.get(num)));
                if (date == null) {
                    date = sf.format(examdateList.get(num));
                }
                List<Date> tarDate = new ArrayList<>();
                tarDate.add(examdateList.get(num));
                // 過去日が存在すればフラグにtrueをセット
                if (examdateList.size() -1 > num) {
                    if (examdateList.size() > 1) {
                        beforeFlg = true;
                    }
                }
                // 未来日は必ず存在するためtrueをセット
                futureFlg = true;
//                // 健診日とPHR_IDから医療機関名を取得
//                if (hospitalName == null) {
//                    hospitalName = service.getHospitalName(examdateList.get(0), requestDto.getPhrId());
//                }
                //DBから項目リストを取得
                List<PhoneCUItemEntity> observationList = service.getItemList(inspectionType, requestDto.getPhrId());
                List<CheckUpResultDataListDto> dataList = new ArrayList<>();
                if (!observationList.isEmpty()) {
                    for (PhoneCUItemEntity itemEntity : observationList) {
                        CheckUpResultDataListDto datadto = new CheckUpResultDataListDto();
                        datadto.setTitle(itemEntity.getDisplayName());
                        if (itemEntity.getUnitValue() != null) {
                            datadto.setUnit(itemEntity.getUnitValue());
                        }
                        //DBから対象項目の検査値リストを取得 
                        List<PhoneCUValueEntity> valueList = service.getValueList(requestDto.getPhrId(), inspectionType, itemEntity.getObservationDefinitionId(), tarDate);
                        if (valueList.get(0) != null) {
                            if (hospitalName == null) {
                                hospitalName = this.getHospitalName(valueList.get(0).getMedicalOrganizationCd());
                            }
                        }
                        if (valueList.get(0) != null) {
                            if (inspectionType.equals("3") && medicalCheckupId == null) {
                                medicalCheckupId = valueList.get(0).getObservationEventId();
                            } else if (inspectionType.equals("4") && inquiryId == null) {
                                inquiryId = valueList.get(0).getObservationEventId();
                            } else if (inspectionType.equals("5") && examinationId == null) {
                                examinationId = valueList.get(0).getObservationEventId();
                            }
                        }
                        List<CheckUpResultValueDto> valueDtolist = new ArrayList<>();
                        for (PhoneCUValueEntity valueEntity : valueList) {
                            CheckUpResultValueDto valuedto = new CheckUpResultValueDto();
                            if (valueEntity != null) {
                                valuedto.setExamdate(sf.format(valueEntity.getExamdate()));
                                valuedto.setData(valueEntity.getValue());
                                // マスタとObservationレコードで単位が一致しているか確認
                                boolean unitFlg = true;
                                if (datadto.getUnit() != null) {
                                    if (!datadto.getUnit().equals(valueEntity.getUnit())) {
                                        // 不一致の場合はObservationレコードのunitを優先
                                        unitFlg = false;
                                        datadto.setUnit(valueEntity.getUnit());
                                    }
                                }
                                // 基準値が存在すればセットする
                                if (valueEntity.getMaxReferenceValue() != null && valueEntity.getMinReferenceValue() != null) {
                                    referenceValue = valueEntity.getMinReferenceValue() + "~" + valueEntity.getMaxReferenceValue();
                                    valuedto.setReferenceValue(referenceValue);
                                    // 結果値が基準値範囲内かどうか判断する
                                    if (unitFlg) {
                                        boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                        if (isNumeric) {
                                            double val = Double.parseDouble(valueEntity.getValue());
                                            double min = Double.parseDouble(valueEntity.getMinReferenceValue());
                                            double max = Double.parseDouble(valueEntity.getMaxReferenceValue());
//                                            if (min >= val || val >= max) {
                                            if (min > val || val > max) {	
                                                valuedto.setFontColor("#ff0000");
                                            }
                                        }
                                    }
                                } else if (valueEntity.getMaxReferenceValue() != null && valueEntity.getMinReferenceValue() == null) {
                                    referenceValue = "~" + valueEntity.getMaxReferenceValue();
                                    valuedto.setReferenceValue(referenceValue);
                                    // 結果値が基準値範囲内かどうか判断する
                                    if (unitFlg) {
                                        boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                        if (isNumeric) {
                                            double val = Double.parseDouble(valueEntity.getValue());
                                            double max = Double.parseDouble(valueEntity.getMaxReferenceValue());
//                                            if (val >= max) {
                                            if (val > max) {
                                                valuedto.setFontColor("#ff0000");
                                            }
                                        }
                                    }
                                } else if (valueEntity.getMaxReferenceValue() == null && valueEntity.getMinReferenceValue() != null) {
                                    referenceValue = valueEntity.getMinReferenceValue() + "~";
                                    valuedto.setReferenceValue(referenceValue);
                                    // 結果値が基準値範囲内かどうか判断する
                                    if (unitFlg) {
                                        boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                        if (isNumeric) {
                                            double val = Double.parseDouble(valueEntity.getValue());
                                            double min = Double.parseDouble(valueEntity.getMinReferenceValue());
//                                            if (min >= val) {
                                            if (min > val) {
                                                valuedto.setFontColor("#ff0000");
                                            }
                                        }
                                    }
                                }
                                // 結果値の背景色を設定
                                List<Integer> alertLevelList = new ArrayList<>();
                                if (unitFlg) {
                                    ObservationDefinitionScriptEntity scriptEntity = new ObservationDefinitionScriptEntity();
                                    if (diseaseList.size() == 0) {
                                        // スクリプトを取得
                                        scriptEntity = scriptService.findByIdAndDiseasetypecd(itemEntity.getObservationDefinitionId(), 0);
                                        String param = null;
                                        if (itemEntity.getObservationDefinitionId().equals("0000000089")) {
                                            // 心電図所見有無の場合はvalueの値からEnumValueを取得
                                            param = service.getEnumValue(valueEntity.getValue(), "0000000089");
                                        } else {
                                            param = valueEntity.getValue();
                                        }
                                        if (scriptEntity.getAlertLevelScript() != null && param != null) {
//                                            Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), valueEntity.getValue(), pEntity);
                                            Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), param, pEntity);
                                            alertLevelList.add(alertLevel);
                                        }
                                    } else {
                                        for (Integer i : diseaseList) {
                                            // スクリプトを取得
                                            scriptEntity = scriptService.findByIdAndDiseasetypecd(itemEntity.getObservationDefinitionId(), i);
                                            String param = null;
                                            if (itemEntity.getObservationDefinitionId().equals("0000000089")) {
                                                // 心電図所見有無の場合はvalueの値からEnumValueを取得
                                                param = service.getEnumValue(valueEntity.getValue(), "0000000089");
                                            } else {
                                                param = valueEntity.getValue();
                                            }
                                            if (scriptEntity.getAlertLevelScript() != null && param != null) {
//                                                Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), valueEntity.getValue(), pEntity);
                                                Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), param, pEntity);
                                                alertLevelList.add(alertLevel);
                                            }
                                        }
                                    }
                                }
                                // アラートレベルが複数あればリスクの高い方を優先
                                Integer level = 9;
                                if (alertLevelList.size() != 0) {
                                    Collections.sort(alertLevelList, Collections.reverseOrder());
                                    level = alertLevelList.get(0);
                                }
                                // アラートレベルによって色を選択
                                String color = this.selectColor(level);
                                valuedto.setBackgroundColor(color);
                            }
                            valueDtolist.add(valuedto);
                        }
//                        if (valueDtolist.get(0).getData().equals(" ")) {
                        if (valueDtolist.get(0).getData() == null || valueDtolist.get(0).getData().equals(" ")) {
                            datadto.setUnit(" ");
                        }
                        datadto.setResults(valueDtolist);
                        dataList.add(datadto);
                    }
                }
                resultDto.setDataList(dataList);
                resultList.add(resultDto);
            }
        } else if (status.equals("future")) {
            // 未来日ボタン押下時
            for (String inspectionType : inspectionTypeList) {
                CheckUpResultListDto resultDto = new CheckUpResultListDto();
                resultDto.setInspectionType(inspectionType);
                // DBから健診結果の年月リストを取得
//                List<Date> examdateList = service.getYearList(requestDto.getPhrId(), inspectionType);
                // targetDateより一つ未来のものを表示
                int num = 0;
                for (int i = 0; i < examdateList.size(); i++) {
                    if (targetDate.equals(sf.format(examdateList.get(i)))) {
                        num = i - 1;
                        break;
                    }
                }
                resultDto.setExamDate(sf.format(examdateList.get(num)));
                if (date == null) {
                    date = sf.format(examdateList.get(num));
                }
                List<Date> tarDate = new ArrayList<>();
                tarDate.add(examdateList.get(num));
                // 過去日は必ず存在するためtrueをセット
                beforeFlg = true;
                // 未来日が存在すればフラグにtrueをセット
                if (num != 0) {
                    futureFlg = true;
                }
//                // 健診日とPHR_IDから医療機関名を取得
//                if (hospitalName == null) {
//                    hospitalName = service.getHospitalName(examdateList.get(0), requestDto.getPhrId());
//                }
                //DBから項目リストを取得
                List<PhoneCUItemEntity> observationList = service.getItemList(inspectionType, requestDto.getPhrId());
                List<CheckUpResultDataListDto> dataList = new ArrayList<>();
                if (!observationList.isEmpty()) {
                    for (PhoneCUItemEntity itemEntity : observationList) {
                        CheckUpResultDataListDto datadto = new CheckUpResultDataListDto();
                        datadto.setTitle(itemEntity.getDisplayName());
                        if (itemEntity.getUnitValue() != null) {
                            datadto.setUnit(itemEntity.getUnitValue());
                        }
                        //DBから対象項目の検査値リストを取得 
                        List<PhoneCUValueEntity> valueList = service.getValueList(requestDto.getPhrId(), inspectionType, itemEntity.getObservationDefinitionId(), tarDate);
                        if (valueList.get(0) != null) {
                            if (hospitalName == null) {
                                hospitalName = this.getHospitalName(valueList.get(0).getMedicalOrganizationCd());
                            }
                        }
                        if (valueList.get(0) != null) {
                            if (inspectionType.equals("3") && medicalCheckupId == null) {
                                medicalCheckupId = valueList.get(0).getObservationEventId();
                            } else if (inspectionType.equals("4") && inquiryId == null) {
                                inquiryId = valueList.get(0).getObservationEventId();
                            } else if (inspectionType.equals("5") && examinationId == null) {
                                examinationId = valueList.get(0).getObservationEventId();
                            }
                        }
                        List<CheckUpResultValueDto> valueDtolist = new ArrayList<>();
                        for (PhoneCUValueEntity valueEntity : valueList) {
                            CheckUpResultValueDto valuedto = new CheckUpResultValueDto();
                            if (valueEntity != null) {
                                valuedto.setExamdate(sf.format(valueEntity.getExamdate()));
                                valuedto.setData(valueEntity.getValue());
                                // マスタとObservationレコードで単位が一致しているか確認
                                boolean unitFlg = true;
                                if (datadto.getUnit() != null) {
                                    if (!datadto.getUnit().equals(valueEntity.getUnit())) {
                                        // 不一致の場合はObservationレコードのunitを優先
                                        unitFlg = false;
                                        datadto.setUnit(valueEntity.getUnit());
                                    }
                                }
                                // 基準値が存在すればセットする
                                if (valueEntity.getMaxReferenceValue() != null && valueEntity.getMinReferenceValue() != null) {
                                    referenceValue = valueEntity.getMinReferenceValue() + "~" + valueEntity.getMaxReferenceValue();
                                    valuedto.setReferenceValue(referenceValue);
                                    // 結果値が基準値範囲内かどうか判断する
                                    if (unitFlg) {
                                        boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                        if (isNumeric) {
                                            double val = Double.parseDouble(valueEntity.getValue());
                                            double min = Double.parseDouble(valueEntity.getMinReferenceValue());
                                            double max = Double.parseDouble(valueEntity.getMaxReferenceValue());
//                                            if (min >= val || val >= max) {
                                            if (min > val || val > max) {
                                                valuedto.setFontColor("#ff0000");
                                            }
                                        }
                                    }
                                } else if (valueEntity.getMaxReferenceValue() != null && valueEntity.getMinReferenceValue() == null) {
                                    referenceValue = "~" + valueEntity.getMaxReferenceValue();
                                    valuedto.setReferenceValue(referenceValue);
                                    // 結果値が基準値範囲内かどうか判断する
                                    if (unitFlg) {
                                        boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                        if (isNumeric) {
                                            double val = Double.parseDouble(valueEntity.getValue());
                                            double max = Double.parseDouble(valueEntity.getMaxReferenceValue());
//                                            if (val >= max) {
                                            if (val > max) {	
                                                valuedto.setFontColor("#ff0000");
                                            }
                                        }
                                    }
                                } else if (valueEntity.getMaxReferenceValue() == null && valueEntity.getMinReferenceValue() != null) {
                                    referenceValue = valueEntity.getMinReferenceValue() + "~";
                                    valuedto.setReferenceValue(referenceValue);
                                    // 結果値が基準値範囲内かどうか判断する
                                    if (unitFlg) {
                                        boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                        if (isNumeric) {
                                            double val = Double.parseDouble(valueEntity.getValue());
                                            double min = Double.parseDouble(valueEntity.getMinReferenceValue());
//                                            if (min >= val) {
                                            if (min > val) {
                                                valuedto.setFontColor("#ff0000");
                                            }
                                        }
                                    }
                                }
                                // 結果値の背景色を設定
                                List<Integer> alertLevelList = new ArrayList<>();
                                if (unitFlg) {
                                    ObservationDefinitionScriptEntity scriptEntity = new ObservationDefinitionScriptEntity();
                                    if (diseaseList.size() == 0) {
                                        // スクリプトを取得
                                        scriptEntity = scriptService.findByIdAndDiseasetypecd(itemEntity.getObservationDefinitionId(), 0);
                                        String param = null;
                                        if (itemEntity.getObservationDefinitionId().equals("0000000089")) {
                                            // 心電図所見有無の場合はvalueの値からEnumValueを取得
                                            param = service.getEnumValue(valueEntity.getValue(), "0000000089");
                                        } else {
                                            param = valueEntity.getValue();
                                        }
                                        if (scriptEntity.getAlertLevelScript() != null && param != null) {
//                                            Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), valueEntity.getValue(), pEntity);
                                            Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), param, pEntity);
                                            alertLevelList.add(alertLevel);
                                        }
                                    } else {
                                        for (Integer i : diseaseList) {
                                            // スクリプトを取得
                                            scriptEntity = scriptService.findByIdAndDiseasetypecd(itemEntity.getObservationDefinitionId(), i);
                                            String param = null;
                                            if (itemEntity.getObservationDefinitionId().equals("0000000089")) {
                                                // 心電図所見有無の場合はvalueの値からEnumValueを取得
                                                param = service.getEnumValue(valueEntity.getValue(), "0000000089");
                                            } else {
                                                param = valueEntity.getValue();
                                            }
                                            if (scriptEntity.getAlertLevelScript() != null && param != null) {
//                                                Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), valueEntity.getValue(), pEntity);
                                                Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), param, pEntity);
                                                alertLevelList.add(alertLevel);
                                            }
                                        }
                                    }
                                }
                                // アラートレベルが複数あればリスクの高い方を優先
                                Integer level = 9;
                                if (alertLevelList.size() != 0) {
                                    Collections.sort(alertLevelList, Collections.reverseOrder());
                                    level = alertLevelList.get(0);
                                }
                                // アラートレベルによって色を選択
                                String color = this.selectColor(level);
                                valuedto.setBackgroundColor(color);
                            }
                            valueDtolist.add(valuedto);
                        }
//                        if (valueDtolist.get(0).getData().equals(" ")) {
                        if (valueDtolist.get(0).getData() == null || valueDtolist.get(0).getData().equals(" ")) {
                            datadto.setUnit(" ");
                        }
                        datadto.setResults(valueDtolist);
                        dataList.add(datadto);
                    }
                }
                resultDto.setDataList(dataList);
                resultList.add(resultDto);
            }
        } else if (status.equals("save")) {
            // 画面に表示されている結果を端末に保存
            if (requestDto.getMedicalCheckupId() != null) {
                medicalCheckupId = requestDto.getMedicalCheckupId();
            }
            if (requestDto.getInquiryId() != null) {
                inquiryId = requestDto.getInquiryId();
            }
            if (requestDto.getExaminationId() != null) {
                examinationId = requestDto.getExaminationId();
            }
            
            // ファイル名
            String fileName = null;
            StringBuilder sb = new StringBuilder();
            if (medicalCheckupId != null) {
                sb.append(medicalCheckupId);
                sb.append("_");
            }
            if (inquiryId != null) {
                sb.append(inquiryId);
                sb.append("_");
            }
            if (examinationId != null) {
                sb.append(examinationId);
                sb.append("_");
            }
            sb.append(requestDto.getPhrId());
            sb.append("_");
            sb.append(targetDate.replaceAll("[^0-9]", ""));
            sb.append(".xml");
            fileName = sb.toString();
            
            // ファイル読み込み処理
            fileData = this.saveFileStr(fileName);
            
            // 画面表示はそのまま
            for (String inspectionType : inspectionTypeList) {
                CheckUpResultListDto resultDto = new CheckUpResultListDto();
                resultDto.setInspectionType(inspectionType);
                // 初期は最新日のものだけを表示
                if (examdateList.size() != 0) {
                    resultDto.setExamDate(sf.format(examdateList.get(0)));
                    if (date == null) {
                        date = sf.format(examdateList.get(0));
                    }
                    List<Date> tarDate = new ArrayList<>();
                    tarDate.add(examdateList.get(0));
                    // 過去日が存在すればフラグにtrueをセット
                    if (examdateList.size() != 0) {
                        if (examdateList.size() > 1) {
                            beforeFlg = true;
                        }
                    }
                    // 健診日とPHR_IDから医療機関名を取得
                    if (hospitalName == null) {
                        hospitalName = service.getHospitalName(examdateList.get(0), requestDto.getPhrId());
                    }
                    //DBから項目リストを取得
                    List<PhoneCUItemEntity> observationList = service.getItemList(inspectionType, requestDto.getPhrId());
                    List<CheckUpResultDataListDto> dataList = new ArrayList<>();
                    if (!observationList.isEmpty()) {
                        for (PhoneCUItemEntity itemEntity : observationList) {
                            CheckUpResultDataListDto datadto = new CheckUpResultDataListDto();
                            datadto.setTitle(itemEntity.getDisplayName());
                            if (itemEntity.getUnitValue() != null) {
                                datadto.setUnit(itemEntity.getUnitValue());
                            }
                            //DBから対象項目の検査値リストを取得 
                            List<PhoneCUValueEntity> valueList = service.getValueList(requestDto.getPhrId(), inspectionType, itemEntity.getObservationDefinitionId(), tarDate);
                            if (valueList.get(0) != null) {
                                if (inspectionType.equals("3") && medicalCheckupId == null) {
                                    medicalCheckupId = valueList.get(0).getObservationEventId();
                                } else if (inspectionType.equals("4") && inquiryId == null) {
                                    inquiryId = valueList.get(0).getObservationEventId();
                                } else if (inspectionType.equals("5") && examinationId == null) {
                                    examinationId = valueList.get(0).getObservationEventId();
                                }
                            }
                            List<CheckUpResultValueDto> valueDtolist = new ArrayList<>();
                            for (PhoneCUValueEntity valueEntity : valueList) {
                                CheckUpResultValueDto valuedto = new CheckUpResultValueDto();
                                if (valueEntity != null) {
                                    valuedto.setExamdate(sf.format(valueEntity.getExamdate()));
                                    valuedto.setData(valueEntity.getValue());
                                    // マスタとObservationレコードで単位が一致しているか確認
                                    boolean unitFlg = true;
                                    if (datadto.getUnit() != null) {
                                        if (!datadto.getUnit().equals(valueEntity.getUnit())) {
                                            // 不一致の場合はObservationレコードのunitを優先
                                            unitFlg = false;
                                            datadto.setUnit(valueEntity.getUnit());
                                        }
                                    }
                                    // 基準値が含まれていればセットする
                                    if (valueEntity.getMaxReferenceValue() != null && valueEntity.getMinReferenceValue() != null) {
                                        referenceValue = valueEntity.getMinReferenceValue() + "~" + valueEntity.getMaxReferenceValue();
                                        valuedto.setReferenceValue(referenceValue);
                                        // 結果値が基準値範囲内かどうか判断する
                                        if (unitFlg) {
                                            boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                            if (isNumeric) {
                                                double val = Double.parseDouble(valueEntity.getValue());
                                                double min = Double.parseDouble(valueEntity.getMinReferenceValue());
                                                double max = Double.parseDouble(valueEntity.getMaxReferenceValue());
//                                                if (min >= val || val >= max) {
                                                if (min > val || val > max) {
                                                    valuedto.setFontColor("#ff0000");
                                                }
                                            }
                                        }
                                    } else if (valueEntity.getMaxReferenceValue() != null && valueEntity.getMinReferenceValue() == null) {
                                        referenceValue = "~" + valueEntity.getMaxReferenceValue();
                                        valuedto.setReferenceValue(referenceValue);
                                        // 結果値が基準値範囲内かどうか判断する
                                        if (unitFlg) {
                                            boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                            if (isNumeric) {
                                                double val = Double.parseDouble(valueEntity.getValue());
                                                double max = Double.parseDouble(valueEntity.getMaxReferenceValue());
//                                                if (val >= max) {
                                                if (val > max) {
                                                    valuedto.setFontColor("#ff0000");
                                                }
                                            }
                                        }
                                    } else if (valueEntity.getMaxReferenceValue() == null && valueEntity.getMinReferenceValue() != null) {
                                        referenceValue = valueEntity.getMinReferenceValue() + "~";
                                        valuedto.setReferenceValue(referenceValue);
                                        // 結果値が基準値範囲内かどうか判断する
                                        if (unitFlg) {
                                            boolean isNumeric = valueEntity.getValue().matches("[+-]?\\d*(\\.\\d+)?");
                                            if (isNumeric) {
                                                double val = Double.parseDouble(valueEntity.getValue());
                                                double min = Double.parseDouble(valueEntity.getMinReferenceValue());
//                                                if (min >= val) {
                                                if (min > val) {
                                                    valuedto.setFontColor("#ff0000");
                                                }
                                            }
                                        }
                                    }
                                    // 結果値の背景色を設定
                                    List<Integer> alertLevelList = new ArrayList<>();
                                    if (unitFlg) {
                                        ObservationDefinitionScriptEntity scriptEntity = new ObservationDefinitionScriptEntity();
                                        if (diseaseList.size() == 0) {
                                            // スクリプトを取得
                                            scriptEntity = scriptService.findByIdAndDiseasetypecd(itemEntity.getObservationDefinitionId(), 0);
                                            String param = null;
                                            if (itemEntity.getObservationDefinitionId().equals("0000000089")) {
                                                // 心電図所見有無の場合はvalueの値からEnumValueを取得
                                                param = service.getEnumValue(valueEntity.getValue(), "0000000089");
                                            } else {
                                                param = valueEntity.getValue();
                                            }
                                            if (scriptEntity.getAlertLevelScript() != null && param != null) {
//                                                Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), valueEntity.getValue(), pEntity);
                                                Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), param, pEntity);
                                                alertLevelList.add(alertLevel);
                                            }
                                        } else {
                                            for (Integer i : diseaseList) {
                                                // スクリプトを取得
                                                scriptEntity = scriptService.findByIdAndDiseasetypecd(itemEntity.getObservationDefinitionId(), i);
                                                String param = null;
                                                if (itemEntity.getObservationDefinitionId().equals("0000000089")) {
                                                    // 心電図所見有無の場合はvalueの値からEnumValueを取得
                                                    param = service.getEnumValue(valueEntity.getValue(), "0000000089");
                                                } else {
                                                    param = valueEntity.getValue();
                                                }
                                                if (scriptEntity.getAlertLevelScript() != null && param != null) {
//                                                    Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), valueEntity.getValue(), pEntity);
                                                    Integer alertLevel = this.actionScript(scriptEntity.getAlertLevelScript(), param, pEntity);
                                                    alertLevelList.add(alertLevel);
                                                }
                                            }
                                        }
                                    }
                                    // アラートレベルが複数あればリスクの高い方を優先
                                    Integer level = 9;
                                    if (alertLevelList.size() != 0) {
                                        Collections.sort(alertLevelList, Collections.reverseOrder());
                                        level = alertLevelList.get(0);
                                    }
                                    // アラートレベルによって色を選択
                                    String color = this.selectColor(level);
                                    valuedto.setBackgroundColor(color);
                                }
                                valueDtolist.add(valuedto);
                            }
                            valueDtolist.sort((a,b) -> b.getExamdate().compareTo(a.getExamdate()));
//                            if (valueDtolist.get(0).getData().equals(" ")) {
                            if (valueDtolist.get(0).getData() == null || valueDtolist.get(0).getData().equals(" ")) {
                                datadto.setUnit(" ");
                            }
                            datadto.setResults(valueDtolist);
                            dataList.add(datadto);
                        }
                    }
                    resultDto.setDataList(dataList);
                    resultList.add(resultDto);
                }
            }
        }
        
        // 健診結果が１つも存在しない種別を除去
        if (resultList.size() != 0) {
            for (int i = resultList.size()-1; i >= 0; i--) {
                CheckUpResultListDto targetDto = new CheckUpResultListDto();
                targetDto = resultList.get(i);
                List<CheckUpResultDataListDto> dataList = new ArrayList<>();
                dataList = targetDto.getDataList();
                boolean deleteFlg = true;
                for (int j = 0; j < dataList.size(); j++) {
                    if (dataList.get(j).getResults().get(0).getData() != null && !dataList.get(j).getResults().get(0).getData().equals(" ")) {
                        deleteFlg = false;
                    }
                    if (!deleteFlg) {
                        break;
                    }
                }
                if (deleteFlg) {
                    for (int k = dataList.size()-1; k >= 0; k--) {
                        dataList.remove(k);
                    }
                }
            }
        }
        
        
        // レスポンスの作成
        responseDto.setFile(fileData);
        responseDto.setBeforeFlg(beforeFlg);
        responseDto.setFutureFlg(futureFlg);
        if (hospitalName != null) {
            responseDto.setHospitalName(hospitalName);
        } else {
            // マスタに該当の医療機関が存在しない場合
            responseDto.setHospitalName("その他の医療機関");
        }
        responseDto.setDate(date);
        responseDto.setMedicalCheckupId(medicalCheckupId);
        responseDto.setInquiryId(inquiryId);
        responseDto.setExaminationId(examinationId);
        responseDto.setDataList(resultList);
        responseDto.setStatus(ResponseBaseDto.SUCCESS);
        
        logger.debug("End");
        return responseDto;
    }

    /**
     * スクリプトの実行
     * @param levelScript
     * @param value
     * @param pEntity
     * @return alertLevel
     * @throws
     */
    private Integer actionScript(String levelScript, String value, PatientEntity pEntity) throws Throwable {
        logger.debug("Start");
        Integer alertLevel = 0;
        
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        
        StringBuilder sb = new StringBuilder();
        sb.append("function exec() { \r\n");
        sb.append(levelScript);
        sb.append("} \r\n");
        sb.append(" exec();");
        String script = sb.toString();
        
        Object jsValue = new Object();
        script = script.replaceAll("\\{value\\}", value);
        if(script.contains("sexCd")){
            script = script.replaceAll("\\{sexCd\\}", pEntity.getSexCd());
        }
        try {
            jsValue = scriptEngine.eval(script);
        } catch (ScriptException ex) {
            logger.debug(script);
        } 
        alertLevel = (Integer)jsValue;
        
        logger.debug("End");
        return alertLevel;
    }

    /**
     * 色選択
     * @param level
     * @return color
     * @throws
     */
    private String selectColor(Integer level) throws Throwable {
        logger.debug("Start");
        String color = null;
        
        if (level == 0) {
            color = "#98fb98";
        } else if (level == 1) {
            color = "#f0e68c";
        } else if (level == 2) {
            color = "#ffa500";
        } else if (level == 3) {
            color = "#ff7f50";
        } else if (level == 9) {
            color = "#ffffff";
        }
        
        logger.debug("End");
        return color;
    }

    /**
     * ファイル読み込み・保存
     * @param fileName
     * @return targetFilePath
     */
    public String saveFileStr(String fileName) {
        logger.debug("Start");
        
        // ファイルの内容を取得
        Path file = Paths.get(xmlPath + "/" + fileName);
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            list = Files.readAllLines(file);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        for (String s : list) {
            sb.append(s + "\n");
        }
        
        logger.debug("End");
        return sb.toString();
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
