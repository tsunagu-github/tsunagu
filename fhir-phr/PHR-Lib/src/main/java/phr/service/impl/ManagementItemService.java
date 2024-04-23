/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DiseaseTypeAdapter;
import phr.datadomain.adapter.InsurerDiseaseTypeAdapter;
import phr.datadomain.adapter.ObservationDefinitionInsurerAdapter;
import phr.datadomain.adapter.ReminderMasterAdapter;
import phr.datadomain.adapter.InsurerViewDefinitionAdapter;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerDiseaseTypeEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.datadomain.entity.ReminderMasterEntity;
import phr.dto.YearViewListDto;
import phr.service.IManagementItemService;

/**
 *
 * @author kis
 */
public class ManagementItemService implements IManagementItemService{

    private static final Log logger = LogFactory.getLog(ManagementItemService.class);

    private static final Pattern INSURER_DISEASE_TYPE_LIST_PATTERN = Pattern.compile("^[^-]+-[^-]+$");

    /**
     * 年度とビューIDの取得
     * @return
     * @throws Throwable 
     */
    @Override
    public List<YearViewListDto> getYearViewIdList() throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerViewDefinitionAdapter adapter =
                    new InsurerViewDefinitionAdapter(dao.getConnection());

            Map<Integer, List<YearViewListDto.View>> yearViewMap =
                    new TreeMap<>((Integer o1, Integer o2) -> o2 - o1);

            for (InsurerViewDefinitionEntity e : adapter.findAll()) {
                for (int i = e.getStartYear(); i <= e.getEndYear(); i++) {
                    List<YearViewListDto.View> viewList = yearViewMap.get(i);
                    if (viewList == null) {
                        viewList = new ArrayList<>();
                        yearViewMap.put(i, viewList);
                    }
                    YearViewListDto.View view = new YearViewListDto.View();
                    view.setId(e.getViewId());
                    view.setName(e.getShortName());
                    viewList.add(view);
                }
            }

            List<YearViewListDto> result = new ArrayList<>();
            for (Map.Entry<Integer, List<YearViewListDto.View>> e : yearViewMap.entrySet()) {
                Collections.sort(e.getValue(),
                        (YearViewListDto.View o1, YearViewListDto.View o2) -> o1.getId() - o2.getId());
                YearViewListDto yearView = new YearViewListDto();
                yearView.setYear(e.getKey());
                yearView.setViewList(e.getValue());
                result.add(yearView);
            }
            return result;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    /**
     * 年度とビューIDの取得
     * @param insurerNo
     * @return
     * @throws Throwable 
     */
    @Override
    public List<YearViewListDto> getYearViewIdList( String insurerNo ) throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerViewDefinitionAdapter adapter =
                    new InsurerViewDefinitionAdapter(dao.getConnection());

            Map<Integer, List<YearViewListDto.View>> yearViewMap =
                    new TreeMap<>((Integer o1, Integer o2) -> o2 - o1);

            for (InsurerViewDefinitionEntity e : adapter.findAll()) {
                if(!e.getInsurerNo().equals(insurerNo)){
                    continue;
                }
                for (int i = e.getStartYear(); i <= e.getEndYear(); i++) {
                    List<YearViewListDto.View> viewList = yearViewMap.get(i);
                    if (viewList == null) {
                        viewList = new ArrayList<>();
                        yearViewMap.put(i, viewList);
                    }
                    YearViewListDto.View view = new YearViewListDto.View();
                    view.setId(e.getViewId());
                    view.setName(e.getShortName());
                    viewList.add(view);
                }
            }

            List<YearViewListDto> result = new ArrayList<>();
            for (Map.Entry<Integer, List<YearViewListDto.View>> e : yearViewMap.entrySet()) {
                Collections.sort(e.getValue(),
                        (YearViewListDto.View o1, YearViewListDto.View o2) -> o1.getId() - o2.getId());
                YearViewListDto yearView = new YearViewListDto();
                yearView.setYear(e.getKey());
                yearView.setViewList(e.getValue());
                result.add(yearView);
            }
            return result;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    /**
     * 管理項目リストの取得
     * @param viewId
     * @return
     * @throws Throwable 
     */
    @Override
    public List<ObservationDefinitionEntity> getObservationDefinitionList(int viewId) throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationDefinitionInsurerAdapter adapter =
                    new ObservationDefinitionInsurerAdapter(dao.getConnection());
            
            return adapter.findByViewId(viewId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    /**
     * 疾病種別リストの取得
     * @param viewId
     * @return
     * @throws Throwable 
     */
    @Override
    public List<DiseaseTypeEntity> getDiseaseTypeList(int viewId) throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            DiseaseTypeAdapter adapter =
                    new DiseaseTypeAdapter(dao.getConnection());
            
            return adapter.findDiseaseTypeList(viewId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    /**
     * 保険者管理項目疾病種別の取得
     * @param viewId
     * @return
     * @throws Throwable 
     */
    @Override
    public List<String> getInsurerDiseaseTypeList(int viewId) throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerDiseaseTypeAdapter adapter =
                    new InsurerDiseaseTypeAdapter(dao.getConnection());
            
            return adapter.findByViewId(viewId).stream().map(e -> {
                return e.getObservationDefinitionId() + "-" + e.getDiseaseTypeCd();
            }).collect(Collectors.toList());
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    /**
     * ビューの設定の保存
     * @param viewId ビューID
     * @param observationDefinitionIdOrder 表示順
     * @param selectedInsurerDiseaseType 有効な項目
     * @return 
     * @throws Throwable 
     */
    @Override
    public List<String> saveManagementItemSetting(int viewId, String[] observationDefinitionIdOrder, String[] selectedInsurerDiseaseType) throws Throwable {
        List<String> result = new ArrayList<>();
        if (observationDefinitionIdOrder == null || observationDefinitionIdOrder.length == 0) {
            throw new RuntimeException();
        }
        if (selectedInsurerDiseaseType == null) {
            selectedInsurerDiseaseType = new String[0];
        }
        for (String insurerDiseaseType : selectedInsurerDiseaseType) {
            if (insurerDiseaseType == null) {
                throw new RuntimeException();
            }
            if (!INSURER_DISEASE_TYPE_LIST_PATTERN.matcher(insurerDiseaseType).matches()) {
                throw new RuntimeException();
            }
        }
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            
            List<String> dbObservationDefinition =
                    getObservationDefinitionList(viewId).stream().map(e -> {
                        return e.getObservationDefinitionId();
                    }).collect(Collectors.toList());
            if (dbObservationDefinition == null || dbObservationDefinition.isEmpty()) {
                throw new RuntimeException();
            }
            List<String> sortedDbObservationDefinition = dbObservationDefinition.stream().map(e -> {
                return e;
            }).sorted().collect(Collectors.toList());

            List<String> dbDiseaseType =
                    getDiseaseTypeList(viewId)
                            .stream()
                            .filter(e -> { return 0 < e.getDiseaseTypeCd();})
                            .map(e -> {
                                return String.valueOf(e.getDiseaseTypeCd());
                            }).collect(Collectors.toList());
            if (dbDiseaseType == null || dbDiseaseType.isEmpty()) {
                throw new RuntimeException();
            }

            List<String> sortedParameterObservationDefinition = Arrays.asList(observationDefinitionIdOrder)
                    .stream().sorted().collect(Collectors.toList());
            if (!sortedParameterObservationDefinition.equals(sortedDbObservationDefinition)) {
                throw new RuntimeException();
            }

            Set<String> observationDefinitionIdSet = new HashSet<>();
            Set<String> diseaseTypeCdSet = new HashSet<>();
            for (String insurerDiseaseType : selectedInsurerDiseaseType) {
                String[] splitted = insurerDiseaseType.split("-");
                observationDefinitionIdSet.add(splitted[0]);
                diseaseTypeCdSet.add(splitted[1]);
            }
            if (!dbObservationDefinition.containsAll(observationDefinitionIdSet)) {
                throw new RuntimeException();
            }
            if (!dbDiseaseType.containsAll(diseaseTypeCdSet)) {
                throw new RuntimeException();
            }

            Map<String, Integer> observationDefinitionIdOrderMap = new HashMap<>();
            for (int i = 0; i < observationDefinitionIdOrder.length; i++) {
                observationDefinitionIdOrderMap.put(observationDefinitionIdOrder[i], i + 1);
            }

            InsurerDiseaseTypeAdapter idtAdapter =
                    new InsurerDiseaseTypeAdapter(dao.getConnection());

            List<InsurerDiseaseTypeEntity> insurerDiseaseTypeEntityList =
                    idtAdapter.findByViewId(viewId);

            List<String> dbOnlyObservationDefinitionId = insurerDiseaseTypeEntityList
                    .stream()
                    .map(e -> { return e.getObservationDefinitionId();})
                    .filter(e -> { return !observationDefinitionIdSet.contains(e);})
                    .collect(Collectors.toList());

            if (!dbOnlyObservationDefinitionId.isEmpty()) {

                ReminderMasterAdapter rmAdapter =
                        new ReminderMasterAdapter(dao.getConnection());

                List<ReminderMasterEntity> rmEntityList =
                        rmAdapter.findByViewIdObservation(viewId, dbOnlyObservationDefinitionId);

                if (!rmEntityList.isEmpty()) {

                    result.add("error");

                    rmEntityList.forEach(e -> {
                        e.setSortNo(observationDefinitionIdOrderMap.get(e.getObservationDefinitionId()));
                    });

                    rmEntityList.stream().sorted((ReminderMasterEntity o1, ReminderMasterEntity o2) -> {
                        int compareResult;
                        compareResult = o1.getSortNo() - o2.getSortNo();
                        if (compareResult != 0) {
                            return compareResult;
                        }
                        compareResult = o1.getReminderTitle().compareTo(o2.getReminderTitle());
                        if (compareResult != 0) {
                            return compareResult;
                        }
                        return o1.getReminderTypeId().compareTo(o2.getReminderTypeId());
                    }).forEach(e -> {
                        result.add(
                                "項目「" + e.getObservationDefinitionDisplayName() + "」は既に" +
                                "リマインダ「" + e.getReminderTitle() + "」で使用されています。"
                        );
                    });

                    return result;
                }
            }

            Set<String> selectedInsurerDiseaseTypeSet =
                    new HashSet<>(Arrays.asList(selectedInsurerDiseaseType));
            List<String> dbInsurerDiseaseTypeStringList = insurerDiseaseTypeEntityList
                    .stream()
                    .map(e -> {
                        return e.getObservationDefinitionId() + "-" + e.getDiseaseTypeCd();
                    })
                    .collect(Collectors.toList());
            List<String> dbOnlyInsurerDiseaseType = dbInsurerDiseaseTypeStringList
                    .stream()
                    .filter(e -> { return !selectedInsurerDiseaseTypeSet.contains(e);})
                    .collect(Collectors.toList());

            if (!dbOnlyInsurerDiseaseType.isEmpty()) {

                List<String> parameterOnlyInsurerDiseaseType = selectedInsurerDiseaseTypeSet
                        .stream()
                        .filter(e -> { return !dbInsurerDiseaseTypeStringList.contains(e);})
                        .collect(Collectors.toList());

                ReminderMasterAdapter rmAdapter =
                        new ReminderMasterAdapter(dao.getConnection());

                List<ReminderMasterEntity> rmEntityList =
                        rmAdapter.findWarningTarget(
                                viewId,
                                dbOnlyInsurerDiseaseType,
                                parameterOnlyInsurerDiseaseType);

                if (!rmEntityList.isEmpty()) {

                    result.add("warning");

                    rmEntityList.forEach(e -> {
                        e.setSortNo(observationDefinitionIdOrderMap.get(e.getObservationDefinitionId()));
                    });

                    rmEntityList.stream().sorted((ReminderMasterEntity o1, ReminderMasterEntity o2) -> {
                        int compareResult;
                        compareResult = o1.getSortNo() - o2.getSortNo();
                        if (compareResult != 0) {
                            return compareResult;
                        }
                        compareResult = o1.getReminderTitle().compareTo(o2.getReminderTitle());
                        if (compareResult != 0) {
                            return compareResult;
                        }
                        return o1.getReminderTypeId().compareTo(o2.getReminderTypeId());
                    }).forEach(e -> {
                        result.add(
                                "項目「" + e.getObservationDefinitionDisplayName() + "」を外したことで" +
                                "リマインダ「" + e.getReminderTitle() + "」に影響がでました。"
                        );
                    });
                }
            }

            ObservationDefinitionInsurerAdapter odiAdapter =
                    new ObservationDefinitionInsurerAdapter(dao.getConnection());

            List<Map.Entry<String, Integer>> observationDefinitionIdOrderList = observationDefinitionIdOrderMap
                    .entrySet()
                    .stream()
                    .sorted((Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> {
                        return o1.getKey().compareTo(o2.getKey());
                    })
                    .collect(Collectors.toList());
            for (Map.Entry<String, Integer> order : observationDefinitionIdOrderList) {
                int count = odiAdapter.updateSortNo(viewId, order.getKey(), order.getValue());
                if (count != 1) {
                    throw new RuntimeException();
                }
            }
            idtAdapter.deleteByViewId(viewId);
            for (String insurerDiseaseType : selectedInsurerDiseaseType) {

                String[] splitted = insurerDiseaseType.split("-");

                String observationDefinitionId = splitted[0];
                String diseaseTypeCd = splitted[1];

                InsurerDiseaseTypeEntity idt = new InsurerDiseaseTypeEntity();
                idt.setViewId(viewId);
                idt.setObservationDefinitionId(observationDefinitionId);
                idt.setDiseaseTypeCd(Integer.valueOf(diseaseTypeCd));
                idtAdapter.insert(idt);
            }
            dao.commitTransaction();
        } catch (Throwable ex) {
            if (dao != null)
                dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
        return result;
    }

    @Override
    public List<ObservationDefinitionInsurerEntity> getObservationReminders(int viewId) throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationDefinitionInsurerAdapter adapter =
                    new ObservationDefinitionInsurerAdapter(dao.getConnection());
            
            return adapter.findObservationRemindersByViewId(viewId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }

    @Override
    public InsurerViewDefinitionEntity getView(int viewId) throws Throwable {
        
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerViewDefinitionAdapter adapter =
                    new InsurerViewDefinitionAdapter(dao.getConnection());
            
            return adapter.findByPrimaryKey(viewId);
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
            logger.trace("end");
        }
    }
}
