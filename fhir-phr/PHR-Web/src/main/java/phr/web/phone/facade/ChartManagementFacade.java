/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.ChartColorEntity;
import phr.datadomain.entity.ChartDefinitionEntity;
import phr.datadomain.entity.ChartObservationDefinitionEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.service.IChartManagementService;
import phr.service.impl.ChartManagementService;
import phr.utility.TypeUtility;
import phr.web.phone.dto.ChartDefinitionDto;
import phr.web.phone.dto.ChartObservationDefinitionDto;
import phr.web.phone.dto.CodeValueDto;
import phr.web.phone.dto.RequestChartDefinitionDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseChartDefinition;
import phr.web.phone.dto.ResponseViewChartDto;
import phr.web.phone.dto.chart.ChartViewDto;
import phr.web.phone.dto.chart.DataSetDto;
import phr.web.phone.dto.chart.LabelsDto;
import phr.web.phone.dto.chart.LegendDto;
import phr.web.phone.dto.chart.OptionsDto;
import phr.web.phone.dto.chart.ScalesDto;
import phr.web.phone.dto.chart.TitleDto;

/**
 *
 * @author daisuke
 */
public class ChartManagementFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartManagementFacade.class);
    
    private static final String ACTION_LIST = "LIST";
    private static final String ACTION_NEW = "NEW";
    private static final String ACTION_EDIT = "EDIT";
    private static final String ACTION_DEL = "DEL";
    private static final String ACTION_ENTRY = "ENTRY";
    private static final String ACTION_VIEW = "VIEW";
    private static final String ACTION_CHANGE = "CHANGE";
    private static final String ACTION_CHANGE2 = "CHANGE2";
    
    private static final SimpleDateFormat SERVICE_SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static final SimpleDateFormat CHART_SDF2 = new SimpleDateFormat("M/d H:m");
    private static final SimpleDateFormat CHART_SDF1 = new SimpleDateFormat("M/d");
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
            RequestChartDefinitionDto requestDto = gson.fromJson(json, RequestChartDefinitionDto.class);
            ResponseBaseDto responseDto = null;
            
            // リスト取得
            if (ACTION_LIST.equalsIgnoreCase(requestDto.getCharttDefinitionAction())) {
                responseDto = getChartList(requestDto);
            // 新規表示
            } else if (ACTION_NEW.equalsIgnoreCase(requestDto.getCharttDefinitionAction())) {
                responseDto = getNewChartDefinition(requestDto);
            // 修正表示
            } else if (ACTION_EDIT.equalsIgnoreCase(requestDto.getCharttDefinitionAction())) {
                responseDto = getChartDefinition(requestDto);
            // 削除
            } else if (ACTION_DEL.equalsIgnoreCase(requestDto.getCharttDefinitionAction())) {
                responseDto = deleteChart(requestDto);
            // 登録
            } else if (ACTION_ENTRY.equalsIgnoreCase(requestDto.getCharttDefinitionAction())) {
                responseDto = entryChart(requestDto);
            // グラフ表示
            } else if (ACTION_VIEW.equalsIgnoreCase(requestDto.getCharttDefinitionAction())) {
               responseDto = getChartViewData(requestDto);
            // チェックボックス選択時（登録画面）
            } else if (ACTION_CHANGE.equalsIgnoreCase(requestDto.getCharttDefinitionAction())) {
               responseDto = changeChartDefinition(requestDto);
            // チェックボックス選択時（修正画面）
            } else if (ACTION_CHANGE2.equalsIgnoreCase(requestDto.getCharttDefinitionAction())) {
               responseDto = changeChartDefinition2(requestDto);
            }
            
            return responseDto;

        } catch (Throwable t) {
            logger.error("", t);
            throw new Exception("[" + this.getClass().getSimpleName() + "] Error", t);
        }       
    }
    /**
     * グラフを表示する
     * @param requestDto
     * @return
     * @throws Throwable 
     */
    private ResponseBaseDto getChartViewData(RequestChartDefinitionDto requestDto) throws Throwable {
        
        try {
            IChartManagementService service = new ChartManagementService();
            
            String phrId = requestDto.getPhrId();
            Long chartDefinitionId = requestDto.getChartDefinitionId();
            String requestDate = requestDto.getTargetDate();
            Date targetDate = null;
            if (!TypeUtility.isNullOrEmpty(requestDate)) {
                targetDate = SERVICE_SDF.parse(requestDate + " 23:59");
            }            
            
            Map<String, ChartColorEntity> colorMap = service.getColorMap();
            
            ChartDefinitionEntity chartDefinitionEntity = service.getChartDefinition(chartDefinitionId);
            
            // Chartデータを取得する
            Map<String, Map<ChartObservationDefinitionEntity, ObservationEntity>> dateMap = 
                    service.getViewChartData(phrId, chartDefinitionId, targetDate);
            
             Map<String, Map<ChartObservationDefinitionEntity, ObservationEntity>> viewMap
                     = new TreeMap<>();
            int currCount = -1;
            int viewCount = requestDto.getViewCount();
            int offSet = requestDto.getOffSet();
            int minCount = offSet * viewCount;
            int maxCount = offSet * viewCount + viewCount -1;
            int size = dateMap.size();
            int ent = size % viewCount;
            int maxOffset = size / viewCount;
            if (ent == 0) {
                maxOffset--;
            }
            
            for (Map.Entry<String, Map<ChartObservationDefinitionEntity, ObservationEntity>> e : dateMap.entrySet()) {
                currCount++;
                if (currCount < minCount)
                    continue;
                if (currCount > maxCount)
                    break;
                
                viewMap.put(e.getKey(), e.getValue());
             }
            
            // ChartデータをChartJSオブジェクトに変換する
            Map<String, DataSetDto> dataSetMap = new LinkedHashMap<>();
            ChartViewDto chartViewDto = new ChartViewDto();
            OptionsDto optionDto = new OptionsDto();
            chartViewDto.setOptions(optionDto);
            
            double maxValue = 0;
            double minValue = -1;
            Date responseDate = null;
            for (Map.Entry<String, Map<ChartObservationDefinitionEntity, ObservationEntity>> e : viewMap.entrySet()) {
                
                Date date = SERVICE_SDF.parse(e.getKey());
                if (chartDefinitionEntity.getDataInputTypeCd() == 2) {
                    chartViewDto.getData().addLabel(CHART_SDF2.format(date));
                } else {
                    chartViewDto.getData().addLabel(CHART_SDF1.format(date));
                }
                responseDate = date;
                
                for (Map.Entry<ChartObservationDefinitionEntity, ObservationEntity> entry : e.getValue().entrySet()) {
                    ChartObservationDefinitionEntity chartDataEntity = entry.getKey();
                    if (!dataSetMap.containsKey(chartDataEntity.getObservationDefinitionId())) {
                        ChartColorEntity colorEntity = colorMap.get(chartDataEntity.getLineColor());
                        DataSetDto dataSetDto = new  DataSetDto();
                        dataSetDto.setLabel(chartDataEntity.getObservationDefinitionName() + "[" + chartDataEntity.getUnitValue() + "]");
                        dataSetDto.setBorderColor(colorEntity.getRGB());
                        dataSetMap.put(chartDataEntity.getObservationDefinitionId(),dataSetDto);
                        chartViewDto.getData().addDataset(dataSetDto);
                    }
                    
                    ObservationEntity observationEntity = entry.getValue();
                    Double value = null;
                    if (observationEntity != null && !TypeUtility.isNullOrEmpty(observationEntity.getValue())) {
                        try {
                            value = Double.parseDouble(observationEntity.getValue());
                            if (maxValue < value)
                                maxValue = value;
                            if (minValue > value || minValue == -1)
                                minValue = value;
                        } catch (Exception ex) {}

                    }
                    DataSetDto dataSetDto = dataSetMap.get(chartDataEntity.getObservationDefinitionId());
                    dataSetDto.addData(value);
                }
            }
            maxValue = Math.ceil(maxValue);
            minValue = Math.floor(minValue);
            
            double maxV = Math.ceil(maxValue * 0.1);
            double minV = Math.floor(minValue * 0.1);
            int max = (int)(maxValue + maxV);
            int min = (int)(minValue - minV);
            int step = 20;
            
            int en = max % step;
            if ((max -en) >= maxV) {
                max = max - en;
            }
            
            ScalesDto scalesDto = new ScalesDto(max, min, step);
            TitleDto titleDto = new TitleDto(chartDefinitionEntity.getChartDefinitionName());
            optionDto.setScales(scalesDto);
            optionDto.setTitle(titleDto);
            
            LabelsDto labelsDto = new LabelsDto(10, 10);
            LegendDto legendDto = new LegendDto();
            legendDto.setLabels(labelsDto);
            optionDto.setLegend(legendDto);
            
            ResponseViewChartDto responseDto = new ResponseViewChartDto();
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            responseDto.setChartData(chartViewDto);
            responseDto.setChartDefinitionId(chartDefinitionId);
            if (responseDate == null) {
                responseDate = new Date();
            }
            responseDto.setTargetDate(sdfDate.format(responseDate));
            responseDto.setViewCount(viewCount);
            responseDto.setMaxOffSet(maxOffset);
            responseDto.setCommonFlg(chartDefinitionEntity.getCommonFlg());
            
            return responseDto;
        } catch (Throwable t) {
            logger.error("", t);
            throw t;
        }        
    }
    
    /**
     * グラフ定義を取得する(新規用)
     * @param requestDto
     * @return
     * @throws Throwable 
     */
    private ResponseBaseDto getNewChartDefinition(RequestChartDefinitionDto requestDto) throws Throwable {
        try {
            IChartManagementService service = new ChartManagementService();
            ChartDefinitionEntity chartDefinitionEntity  = 
                service.getNewChartDefinition(requestDto.getPhrId(), requestDto.getDataInputType());

            ChartDefinitionDto chartDefinitionDto = new ChartDefinitionDto();
            chartDefinitionDto.copyTo(chartDefinitionEntity);
            chartDefinitionDto.setDataInputType(requestDto.getDataInputType());
            
            if (requestDto.getEntryChartDefinitionDto() != null) {
                chartDefinitionDto.setChartDefinitionName(requestDto.getEntryChartDefinitionDto().getChartDefinitionName());
                chartDefinitionDto.setViewCount(requestDto.getEntryChartDefinitionDto().getViewCount());
            }
            
            for (ChartObservationDefinitionEntity entity : chartDefinitionEntity.getChartObservationDefinitionList()) {
                ChartObservationDefinitionDto dto = new ChartObservationDefinitionDto();
                dto.copyTo(entity);
                chartDefinitionDto.addChartObservationList(dto);
            }
            
            List<CodeValueDto> colorList = getColorList(service);
            
            // チェックボックスは初期状態ではすべて選択
            List<Boolean> checkFlg = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                checkFlg.add(true);
            }
            chartDefinitionDto.setCheckFlg(checkFlg);
            
            ResponseChartDefinition responseDto = new ResponseChartDefinition();
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            responseDto.setTargetChartDefinition(chartDefinitionDto);
            responseDto.setColorList(colorList);
            
            return responseDto;
        } catch (Throwable t) {
            logger.error("", t);
            throw t;
        }           
     }
    /**
     * グラフ定義を取得する
     * @param requestDto
     * @return
     * @throws Throwable 
     */
    private ResponseBaseDto getChartDefinition(RequestChartDefinitionDto requestDto) throws Throwable {
        try {
            IChartManagementService service = new ChartManagementService();
            ChartDefinitionEntity chartDefinitionEntity  = 
                service.getChartDefinition(requestDto.getChartDefinitionId(), requestDto.getDataInputType());

            ChartDefinitionDto chartDefinitionDto = new ChartDefinitionDto();
            chartDefinitionDto.copyTo(chartDefinitionEntity);
            if (requestDto.getDataInputType() != null) {
                chartDefinitionDto.setDataInputType(requestDto.getDataInputType());
            }
            for (ChartObservationDefinitionEntity entity : chartDefinitionEntity.getChartObservationDefinitionList()) {
                ChartObservationDefinitionDto dto = new ChartObservationDefinitionDto();
                dto.copyTo(entity);
                chartDefinitionDto.addChartObservationList(dto);
            }
            
            List<CodeValueDto> colorList = getColorList(service);
            
            ResponseChartDefinition responseDto = new ResponseChartDefinition();
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            responseDto.setTargetChartDefinition(chartDefinitionDto);
            responseDto.setColorList(colorList);
            if (requestDto.getChartDefinitionId() != null) {
                responseDto.setChartDefinitionId(requestDto.getChartDefinitionId());
            }
            
            return responseDto;
        } catch (Throwable t) {
            logger.error("", t);
            throw t;
        }        
    }
    /**
     * グラフ定義を登録する
     * @param requestDto
     * @return
     * @throws Throwable 
     */
    private ResponseBaseDto entryChart(RequestChartDefinitionDto requestDto) throws Throwable {
        try {
            IChartManagementService service = new ChartManagementService();

            ChartDefinitionDto cartDefDto = requestDto.getEntryChartDefinitionDto();                
            ChartDefinitionEntity chartDefinitionEntity  = new ChartDefinitionEntity();
            cartDefDto.copyFrom(chartDefinitionEntity);           
            chartDefinitionEntity.setPHR_ID(requestDto.getPhrId());
            if (requestDto.getChartDefinitionId() != null) {
                chartDefinitionEntity.setChartDefinitionId(requestDto.getChartDefinitionId());
            }

            ChartDefinitionDto chartDefinitionDto = new ChartDefinitionDto();
            chartDefinitionDto.copyTo(chartDefinitionEntity);
            for (ChartObservationDefinitionEntity entity : chartDefinitionEntity.getChartObservationDefinitionList()) {
                ChartObservationDefinitionDto dto = new ChartObservationDefinitionDto();
                dto.copyTo(entity);
                chartDefinitionDto.addChartObservationList(dto);
            }
            
            service.entryChartDefinition(chartDefinitionEntity);
            
            ResponseChartDefinition responseDto = new ResponseChartDefinition();
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            return responseDto;
            
        } catch (Throwable t) {
            logger.error("", t);
            throw t;
        }        
    }
    /**
     * グラフ一覧を取得する
     * @param requestDto
     * @return
     * @throws Throwable 
     */
    private ResponseBaseDto getChartList(RequestChartDefinitionDto requestDto) throws Throwable {
        try {
            IChartManagementService service = new ChartManagementService();
            List<ChartDefinitionEntity> list =service.getChartList(requestDto.getPhrId());
            
            List<ChartDefinitionDto> responseList = new ArrayList<>();
            for (ChartDefinitionEntity entity : list) {
                ChartDefinitionDto dto = new ChartDefinitionDto();
                dto.copyTo(entity);
                responseList.add(dto);
            }
            
            ResponseChartDefinition responseDto = new ResponseChartDefinition();
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            responseDto.setChartDefinitionList(responseList);
            
            return responseDto;
        } catch (Throwable t) {
            logger.error("", t);
            throw t;
        }
     }

    /**
     * グラフを削除する
     * @param requestDto
     * @return
     * @throws Throwable 
     */
    private ResponseBaseDto deleteChart(RequestChartDefinitionDto requestDto) throws Throwable {
        try {
            IChartManagementService service = new ChartManagementService();
            service.deleteChartDefinition(requestDto.getChartDefinitionId());
                    
            ResponseChartDefinition responseDto = new ResponseChartDefinition();
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            
            return responseDto;
        } catch (Throwable t) {
            logger.error("", t);
            throw t;
        }        
    }
    
    /**
     * 色リストを取得する
     * @param service
     * @return
     * @throws Throwable 
     */
    private List<CodeValueDto> getColorList(IChartManagementService service) throws Throwable {
        List<CodeValueDto> list = new ArrayList<>();
        List<ChartColorEntity> entityList = service.getColorList();
        
        for (ChartColorEntity entity : entityList) {
            CodeValueDto dto = new CodeValueDto(entity.getColorCd(), entity.getColorName());
            list.add(dto);
        }
        return list;
    }
        
    /**
     * グラフ定義の表示項目を変更する
     * @param service
     * @return
     * @throws Throwable 
     */
    private ResponseBaseDto changeChartDefinition(RequestChartDefinitionDto requestDto) throws Throwable {
        
        // 自己管理と健診で重複している項目リスト
        List<String> duplicateList = new ArrayList<>();
        duplicateList.add("0000000001");
        duplicateList.add("0000000002");
        duplicateList.add("0000000006");
        duplicateList.add("0000000012");
        duplicateList.add("0000000013");
        duplicateList.add("0000000018");
        duplicateList.add("0000000025");
        duplicateList.add("0000000026");
        duplicateList.add("0000000028");
        duplicateList.add("0000000039");
        duplicateList.add("1000000001");
        
        IChartManagementService service = new ChartManagementService();
        ResponseChartDefinition responseDto = new ResponseChartDefinition();
        ChartDefinitionDto chartDefinitionDto = new ChartDefinitionDto();
        List<ChartObservationDefinitionDto> list = new ArrayList<>();
        if (requestDto.getEntryChartDefinitionDto() != null) {
            list = requestDto.getEntryChartDefinitionDto().getChartObservationList();
        }
        String chartName = requestDto.getEntryChartDefinitionDto().getChartDefinitionName();
        int count = requestDto.getEntryChartDefinitionDto().getViewCount();
        
        // 線色がnullでも空でも無いときはnullに修正
        for (ChartObservationDefinitionDto dto : list) {
            if (dto.getLineColor() != null) {
                if (dto.getLineColor().isEmpty()) {
                    dto.setLineColor(null);
                }
            }
        }
        
        // 1：新規画面か2:修正画面かの判断
        String type = "1";
        for (ChartObservationDefinitionDto dto : list) {
            if (dto.getLineColor() != null) {
                type = "2";
            }
        }
        
        List<ChartObservationDefinitionDto> newList = new ArrayList<>();
        String target = requestDto.getDataInputType().toString();
//        // target=8の時は"5"に変換必要
//        if (target.equals("8")) {
//            target = "5";
//        }
        // チェックボックスの選択が外れた時
        if (!requestDto.isCheckboxFlg()) {
            // 既に登録されている項目はそのまま表示する
            List<ChartObservationDefinitionDto> copyList = new ArrayList<>();
            for (ChartObservationDefinitionDto dto : list) {
                if (dto.getLineColor() != null) {
                    newList.add(dto);
                    copyList.add(dto);
                }
            }
            // それ以外の項目は非表示にする
            if (target.equals("1")) {
                for (ChartObservationDefinitionDto dto : list) {
                    if (dto.getDataInputTypeCd() != null) {
                        if (dto.getDataInputTypeCd().equals("2")) {
                            if (dto.getLineColor() == null) {
                                newList.add(dto);
                            }
                        }
                    }
                }
                if (requestDto.getEntryChartDefinitionDto().checkFlg.get(1)) {
                    if (requestDto.getEntryChartDefinitionDto().checkFlg.get(2)) {
                        // 健診項目をセット
                        List<String> l = new ArrayList<>();
                        for (ChartObservationDefinitionDto dto : list) {
                            if (dto.getDataInputTypeCd() != null) {
                                if (dto.getDataInputTypeCd().equals("3")) {
                                    l.add(dto.getObservationDefinitionId());
                                }
                            }
                        }
                        for (String id : duplicateList) {
                            l.add(id);
                        }
                        Collections.sort(l);
                        if (type.equals("1")) {
                            for (int i = 0; i < l.size(); i++) {
                                for (int ii = 0; ii < list.size(); ii++) {
                                    if (l.get(i).equals(list.get(ii).getObservationDefinitionId())) {
                                        newList.add(list.get(ii));
                                    }
                                }
                            }
                        } else if (type.equals("2")) {
                            for (int i = 0; i < l.size(); i++) {
                                for (int ii = 0; ii < list.size(); ii++) {
                                    if (l.get(i).equals(list.get(ii).getObservationDefinitionId())) {
                                        for (int j = 0; j < copyList.size(); j++) {
                                            if (!list.get(ii).getObservationDefinitionId().equals(copyList.get(j).getObservationDefinitionId())) {
                                                if (list.get(ii).getLineColor() == null) {
                                                    newList.add(list.get(ii));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                for (ChartObservationDefinitionDto dto : list) {
                    if (dto.getDataInputTypeCd() != null) {
                        if (dto.getDataInputTypeCd().equals("8")) {
                            if (dto.getLineColor() == null) {
                                newList.add(dto);
                            }
                        }
                    }
                }
                
            } else if (target.equals("2")) {
                for (ChartObservationDefinitionDto dto : list) {
                    if (dto.getDataInputTypeCd() != null) {
                        if (!dto.getDataInputTypeCd().equals(target)) {
                            if (dto.getLineColor() == null) {
                                newList.add(dto);
                            }
                        }
                    }
                }
            } else if (target.equals("3")) {
                if (requestDto.getEntryChartDefinitionDto().checkFlg.get(1)) {
                    // 自己管理の項目をセット
                    List<String> l = new ArrayList<>();
                    for (ChartObservationDefinitionDto dto : list) {
                        if (dto.getDataInputTypeCd() != null) {
                            if (dto.getDataInputTypeCd().equals("1")) {
                                if (dto.getLineColor() == null) {
                                    newList.add(dto);
                                }
                            }
                        }
                    }
                }
                for (ChartObservationDefinitionDto dto : list) {
                    if (dto.getDataInputTypeCd() != null) {
                        if (dto.getDataInputTypeCd().equals("2")) {
                            if (dto.getLineColor() == null) {
                                newList.add(dto);
                            }
                        }
                    }
                }
                for (ChartObservationDefinitionDto dto : list) {
                    if (dto.getDataInputTypeCd() != null) {
                        if (dto.getDataInputTypeCd().equals("8")) {
                            if (dto.getLineColor() == null) {
                                newList.add(dto);
                            }
                        }
                    }
                }
                
            } else if (target.equals("8")) {
                for (ChartObservationDefinitionDto dto : list) {
                    if (dto.getDataInputTypeCd() != null) {
                        if (!dto.getDataInputTypeCd().equals(target)) {
                            if (dto.getLineColor() == null) {
                                newList.add(dto);
                            }
                        }
                    }
                }
            }
        // チェックボックスが選択された時
        } else {
            // 選択された検査項目を取得しnewListに追加
            List<ChartObservationDefinitionDto> l = new ArrayList<>();
            ChartDefinitionEntity chartDefinitionEntity = service.getNewChartDefinition(requestDto.getPhrId(), requestDto.getDataInputType());
            if (chartDefinitionEntity.getChartObservationDefinitionList() != null) {
                for (int i = 0; i < chartDefinitionEntity.getChartObservationDefinitionList().size(); i++) {
                    ChartObservationDefinitionDto d = new ChartObservationDefinitionDto();
                    d.setDataInputTypeCd(target);
                    d.setObservationDefinitionId(chartDefinitionEntity.getChartObservationDefinitionList().get(i).getObservationDefinitionId());
                    d.setObservationDefinitionName(chartDefinitionEntity.getChartObservationDefinitionList().get(i).getObservationDefinitionName());
                    l.add(d);
                }
            }
            
            // 既に登録されている項目はそのまま表示する
            int sum = list.size();
            List<ChartObservationDefinitionDto> checkList = new ArrayList<>();
            if (type.equals("2")) {
                if (list != null) {
                    for (ChartObservationDefinitionDto d : list) {
                        if (d.getLineColor() != null) {
                            newList.add(d);
                            checkList.add(d);
                        }
                    }
                }
            }
            
            List<ChartObservationDefinitionDto> copyList = new ArrayList<>();
            for (ChartObservationDefinitionDto d : list) {
                copyList.add(d);
            }
            
            if (target.equals("1")) {
                // 新規画面か修正画面かで処理を分岐
                if (type.equals("1")) {
                    // 重複分を除去
                    List<Integer> delList = new ArrayList<>();
                    for (String id : duplicateList) {
                        for (int i = 0; i < list.size(); i++) {
                            if (id.equals(list.get(i).getObservationDefinitionId())) {
                                delList.add(i);
                            }
                        }
                    }
                    Collections.sort(delList, Collections.reverseOrder());
                    for (int num : delList) {
                        list.remove(num);
                    }
                } else if (type.equals("2")) {
                    // 重複分を除去
                    List<Integer> delList = new ArrayList<>();
                    for (String id : duplicateList) {
                        for (int i = 0; i < list.size(); i++) {
                            if (id.equals(list.get(i).getObservationDefinitionId())) {
                                delList.add(i);
                            }
                        }
                    }
                    Collections.sort(delList, Collections.reverseOrder());
                    for (int num : delList) {
                        list.remove(num);
                    }
                }
                
            } else if (target.equals("3")) {
                // 新規画面か修正画面かで処理を分岐
                if (type.equals("1")) {
                    if (requestDto.getEntryChartDefinitionDto().getCheckFlg().get(1)) {
                        // 重複分を除去
                        List<Integer> delList = new ArrayList<>();
                        for (String id : duplicateList) {
                            for (int i = 0; i < l.size(); i++) {
                                if (id.equals(l.get(i).getObservationDefinitionId())) {
                                    delList.add(i);
                                }
                            }
                        }
                        Collections.sort(delList, Collections.reverseOrder());
                        for (int num : delList) {
                            l.remove(num);
                        }
                    }
                } else if (type.equals("2")) {
                    List<Integer> delList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        for (int ii = 0; ii < duplicateList.size(); ii++) {
                            if (list.get(i).getObservationDefinitionId().equals(duplicateList.get(ii))) {
                                delList.add(ii);
                            }
                        }
                    }
                    Collections.sort(delList, Collections.reverseOrder());
                    for (int num : delList) {
                        l.remove(num);
                    }
                }
            }
            
            // DataInputTypeCd=3の時、選択状態にある項目の重複を消す
            if (target.equals("3")) {
                List<Integer> delList = new ArrayList<>();
                for (ChartObservationDefinitionDto ch : checkList) {
                    for (int i = 0; i < l.size(); i++) {
                        if (ch.getObservationDefinitionId().equals(l.get(i).getObservationDefinitionId())) {
                            delList.add(i);
                        }
                    }
                }
                Collections.sort(delList, Collections.reverseOrder());
                for (int num : delList) {
                    l.remove(num);
                }
            }
            
            for (ChartObservationDefinitionDto dto : list) {
                l.add(dto);
            }
            // DataInputTypeCdの降順で並び替え
            for (ChartObservationDefinitionDto c : l) {
                if (c.getDataInputTypeCd() != null) {
                    if (c.getDataInputTypeCd().equals("1")) {
                        if (type.equals("2")) {
                            int num = 0;
                            for (ChartObservationDefinitionDto ch : checkList) {
                                if (!ch.getObservationDefinitionId().equals(c.getObservationDefinitionId())) {
                                    num++;
                                }
                                if (checkList.size() == num) {
                                    newList.add(c);
                                }
                            }
                        } else {
                            newList.add(c);
                        }
                    }
                }
            }
            for (ChartObservationDefinitionDto c : l) {
                if (c.getDataInputTypeCd() != null) {
                    if (c.getDataInputTypeCd().equals("2")) {
                        if (type.equals("2")) {
                            int num = 0;
                            for (ChartObservationDefinitionDto ch : checkList) {
                                if (!ch.getObservationDefinitionId().equals(c.getObservationDefinitionId())) {
                                    num++;
                                }
                                if (checkList.size() == num) {
                                    newList.add(c);
                                }
                            }
                        } else {
                            newList.add(c);
                        }
                    }
                }
            }
            for (ChartObservationDefinitionDto c : l) {
                if (c.getDataInputTypeCd() != null) {
                    if (c.getDataInputTypeCd().equals("3")) {
                        if (c.getLineColor() == null) {
                            newList.add(c);
                        }
                    }
                }
            }
            for (ChartObservationDefinitionDto c : l) {
                if (c.getDataInputTypeCd() != null) {
                    if (c.getDataInputTypeCd().equals("8")) {
                        if (type.equals("2")) {
                            int num = 0;
                            for (ChartObservationDefinitionDto ch : checkList) {
                                if (!ch.getObservationDefinitionId().equals(c.getObservationDefinitionId())) {
                                    num++;
                                }
                                if (checkList.size() == num) {
                                    newList.add(c);
                                }
                            }
                        } else {
                            newList.add(c);
                        }
                    }
                }
            }
        }
        
        List<Boolean> checkFlg = new ArrayList<>();
        boolean check = true;
        // チェックボックスの選択・未選択の判断
        if (!requestDto.isCheckboxFlg()) {
            check = false;
        }
        // チェックボックス全体
        for (int i = 0; i < 4; i++) {
            if (requestDto.getDataInputType() == 1) {
                if (i == 1) {
                    checkFlg.add(check);
                } else {
                    checkFlg.add(requestDto.getEntryChartDefinitionDto().getCheckFlg().get(i));
                }
            } else if (requestDto.getDataInputType() == 2) {
                if (i == 0) {
                    checkFlg.add(check);
                } else {
                    checkFlg.add(requestDto.getEntryChartDefinitionDto().getCheckFlg().get(i));
                }
            } else if (requestDto.getDataInputType() == 3) {
                if (i == 2) {
                    checkFlg.add(check);
                } else {
                    checkFlg.add(requestDto.getEntryChartDefinitionDto().getCheckFlg().get(i));
                }
            } else if (requestDto.getDataInputType() == 8) {
                if (i == 3) {
                    checkFlg.add(check);
                } else {
                    checkFlg.add(requestDto.getEntryChartDefinitionDto().getCheckFlg().get(i));
                }
            }
        }
        
        chartDefinitionDto.setCheckFlg(checkFlg);
        chartDefinitionDto.setChartObservationList(newList);
        chartDefinitionDto.setChartDefinitionName(chartName);
        chartDefinitionDto.setViewCount(count);
        List<CodeValueDto> colorList = getColorList(service);
        responseDto.setStatus(ResponseBaseDto.SUCCESS);
        responseDto.setTargetChartDefinition(chartDefinitionDto);
        responseDto.setColorList(colorList);
        if (requestDto.getChartDefinitionId() != null) {
            responseDto.setChartDefinitionId(requestDto.getChartDefinitionId());
        }
        
        return responseDto;
    }

    /**
     * グラフ定義の表示項目を変更する
     * @param service
     * @return
     * @throws Throwable 
     */
    private ResponseBaseDto changeChartDefinition2(RequestChartDefinitionDto requestDto) throws Throwable {
        
        // 自己管理と健診で重複している項目リスト
        List<String> duplicateList = new ArrayList<>();
        duplicateList.add("0000000001");
        duplicateList.add("0000000002");
        duplicateList.add("0000000006");
        duplicateList.add("0000000012");
        duplicateList.add("0000000013");
        duplicateList.add("0000000018");
        duplicateList.add("0000000025");
        duplicateList.add("0000000026");
        duplicateList.add("0000000028");
        duplicateList.add("0000000039");
        duplicateList.add("1000000001");
        
        IChartManagementService service = new ChartManagementService();
        ResponseChartDefinition responseDto = new ResponseChartDefinition();
        ChartDefinitionDto chartDefinitionDto = new ChartDefinitionDto();
        List<ChartObservationDefinitionDto> list = new ArrayList<>();
        if (requestDto.getEntryChartDefinitionDto() != null) {
            list = requestDto.getEntryChartDefinitionDto().getChartObservationList();
        }
        String chartName = requestDto.getEntryChartDefinitionDto().getChartDefinitionName();
        int count = requestDto.getEntryChartDefinitionDto().getViewCount();
        
        List<ChartObservationDefinitionDto> newList = new ArrayList<>();
        String target = requestDto.getDataInputType().toString();
//        // target=8の時は"5"に変換必要
//        if (target.equals("8")) {
//            target = "5";
//        }

        // 選択された検査項目を取得しnewListに追加
        List<ChartObservationDefinitionDto> l = new ArrayList<>();
        ChartDefinitionEntity chartDefinitionEntity = service.getNewChartDefinition(requestDto.getPhrId(), requestDto.getDataInputType());
        if (chartDefinitionEntity.getChartObservationDefinitionList() != null) {
            for (int i = 0; i < chartDefinitionEntity.getChartObservationDefinitionList().size(); i++) {
                ChartObservationDefinitionDto d = new ChartObservationDefinitionDto();
                d.setDataInputTypeCd(target);
                d.setObservationDefinitionId(chartDefinitionEntity.getChartObservationDefinitionList().get(i).getObservationDefinitionId());
                d.setObservationDefinitionName(chartDefinitionEntity.getChartObservationDefinitionList().get(i).getObservationDefinitionName());
                l.add(d);
            }
        }

        // 先にすでに登録されている項目をセット
        List<ChartObservationDefinitionDto> copyList = new ArrayList<>();
        for (ChartObservationDefinitionDto dto : list) {
            newList.add(dto);
            copyList.add(dto);
        }
        // DataInputTypeCdの降順で並び替え
        int sum = list.size();
        for (ChartObservationDefinitionDto c : l) {
            if (c.getDataInputTypeCd() != null) {
                if (c.getDataInputTypeCd().equals("1")) {
                    int num = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).getObservationDefinitionId().equals(c.getObservationDefinitionId())) {
                            num++;
                        }
                    }
                    if (sum == num) {
                        newList.add(c);
                    }
                }
            }
        }
        for (ChartObservationDefinitionDto c : l) {
            if (c.getDataInputTypeCd() != null) {
                if (c.getDataInputTypeCd().equals("2")) {
                    int num = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).getObservationDefinitionId().equals(c.getObservationDefinitionId())) {
                            num++;
                        }
                    }
                    if (sum == num) {
                        newList.add(c);
                    }
                }
            }
        }
        for (ChartObservationDefinitionDto c : l) {
            if (c.getDataInputTypeCd() != null) {
                if (c.getDataInputTypeCd().equals("3")) {
                    int num = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).getObservationDefinitionId().equals(c.getObservationDefinitionId())) {
                            num++;
                        }
                    }
                    if (sum == num) {
                        newList.add(c);
                    }
                }
            }
        }
        for (ChartObservationDefinitionDto c : l) {
            if (c.getDataInputTypeCd() != null) {
                if (c.getDataInputTypeCd().equals("8")) {
                    int num = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).getObservationDefinitionId().equals(c.getObservationDefinitionId())) {
                            num++;
                        }
                    }
                    if (sum == num) {
                        newList.add(c);
                    }
                }
            }
        }

        List<Boolean> checkFlg = new ArrayList<>();
        boolean check = true;
        // チェックボックスの選択・未選択の判断
        if (!requestDto.isCheckboxFlg()) {
            check = false;
        }
        // チェックボックス全体
        for (int i = 0; i < 4; i++) {
            if (requestDto.getDataInputType() == 1) {
                if (i == 1) {
                    checkFlg.add(check);
                } else {
                    checkFlg.add(false);
                }
            } else if (requestDto.getDataInputType() == 2) {
                if (i == 0) {
                    checkFlg.add(check);
                } else {
                    checkFlg.add(false);
                }
            } else if (requestDto.getDataInputType() == 3) {
                if (i == 2) {
                    checkFlg.add(check);
                } else {
                    checkFlg.add(false);
                }
            } else if (requestDto.getDataInputType() == 8) {
                if (i == 3) {
                    checkFlg.add(check);
                } else {
                    checkFlg.add(false);
                }
            }
        }
        
        chartDefinitionDto.setCheckFlg(checkFlg);
        chartDefinitionDto.setChartObservationList(newList);
        chartDefinitionDto.setChartDefinitionName(chartName);
        chartDefinitionDto.setViewCount(count);
        List<CodeValueDto> colorList = getColorList(service);
        responseDto.setStatus(ResponseBaseDto.SUCCESS);
        responseDto.setTargetChartDefinition(chartDefinitionDto);
        responseDto.setColorList(colorList);
        if (requestDto.getChartDefinitionId() != null) {
            responseDto.setChartDefinitionId(requestDto.getChartDefinitionId());
        }
        
        return responseDto;
    }
}
