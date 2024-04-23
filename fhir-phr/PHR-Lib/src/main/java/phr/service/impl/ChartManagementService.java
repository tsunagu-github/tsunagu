/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jp.kis_inc.core.utility.TypeUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ChartColorAdapter;
import phr.datadomain.adapter.ChartDefinitionAdapter;
import phr.datadomain.adapter.ChartObservationDefinitionAdapter;
import phr.datadomain.adapter.JLAC10AnalyteCodeAdapter;
import phr.datadomain.adapter.JLAC11AnalyteCodeAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationDefinitionJlac10Adapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.ChartColorEntity;
import phr.datadomain.entity.ChartDefinitionEntity;
import phr.datadomain.entity.ChartObservationDefinitionEntity;
import phr.datadomain.entity.JLAC10AnalyteCodeEntity;
import phr.datadomain.entity.JLAC11AnalyteCodeEntity;
import phr.datadomain.entity.ObservationDefinitionJlac10Entity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IChartManagementService;

/**
 *
 * @author daisuke
 */
public class ChartManagementService implements IChartManagementService {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartManagementService.class);

    /**
     * グラフリストを取得する
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    public List<ChartDefinitionEntity> getChartList(String phrId) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            PatientAdapter patientAdapter = new PatientAdapter(dao.getConnection());
            PatientEntity patientEntity = patientAdapter.findInsurePatient(phrId);

            ChartDefinitionAdapter adapter = new ChartDefinitionAdapter(dao.getConnection());
            List<ChartDefinitionEntity> entityList = adapter.getChartList(patientEntity.getInsurerNo(), phrId);

            logger.debug("End");
            return entityList;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * グラフ定義を取得する
     *
     * @param chartDefinitionId
     * @return
     * @throws Throwable
     */
    public ChartDefinitionEntity getChartDefinition(Long chartDefinitionId) throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            ChartDefinitionAdapter adapter = new ChartDefinitionAdapter(dao.getConnection());
            ChartObservationDefinitionAdapter chartObservationAdapter = new ChartObservationDefinitionAdapter(dao.getConnection());
            ChartDefinitionEntity entity = adapter.findByPrimaryKey(chartDefinitionId);

            logger.debug("End");
            return entity;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * グラフ定義を取得する
     *
     * @param chartDefinitionId
     * @param dataInputType
     * @return
     * @throws Throwable
     */
    public ChartDefinitionEntity getChartDefinition(Long chartDefinitionId, Integer dataInputType) throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            ChartDefinitionAdapter adapter = new ChartDefinitionAdapter(dao.getConnection());
            ChartObservationDefinitionAdapter chartObservationAdapter = new ChartObservationDefinitionAdapter(dao.getConnection());
            ChartDefinitionEntity entity = adapter.findByPrimaryKey(chartDefinitionId);

            Integer inputTypeCd = dataInputType;
            if (dataInputType == null) {
                inputTypeCd = entity.getDataInputTypeCd();
            }

            List<ChartObservationDefinitionEntity> list
                    = chartObservationAdapter.searchChartObservationDefinition(entity.getInsurerNo(), chartDefinitionId, inputTypeCd);
            entity.setChartObservationDefinitionList(list);

            logger.debug("End");
            return entity;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * グラフ定義を取得する
     *
     * @param phrId
     * @param dataInputType
     * @return
     * @throws Throwable
     */
    public ChartDefinitionEntity getNewChartDefinition(String phrId, Integer dataInputType) throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            ChartObservationDefinitionAdapter chartObservationAdapter = new ChartObservationDefinitionAdapter(dao.getConnection());
            PatientAdapter patientAdapter = new PatientAdapter(dao.getConnection());
            PatientEntity patientEntity = patientAdapter.findInsurePatient(phrId);

            ChartDefinitionEntity entity = new ChartDefinitionEntity();

            Integer inputTypeCd = dataInputType;
            if (dataInputType == null) {
                inputTypeCd = entity.getDataInputTypeCd();
            }

            List<ChartObservationDefinitionEntity> list = new ArrayList<>();
            if (inputTypeCd == 9 || inputTypeCd == 0) {
                // dataInputType（＝inputTypeCd）が"9"の場合、全ての検査項目を取得する
                list = chartObservationAdapter.searchAllChartObservationDefinition(patientEntity.getInsurerNo());
            } else if (inputTypeCd == 8) {
                // dataInputType（＝inputTypeCd）が"8"の場合、1，2，3以外の全ての検査項目を取得する
                list = chartObservationAdapter.searchChartObservationDefinition(patientEntity.getInsurerNo());
            } else {
                // dataInputType（＝inputTypeCd）="1":自己管理、'2':自己測定、"3":健診項目の検査項目を取得する
                list = chartObservationAdapter.searchNewChartObservationDefinition(patientEntity.getInsurerNo(), inputTypeCd);
            }
            // 取得したリストからGraphTargetFlg=trueのものだけを抽出する
            list = this.extract(list);
//            List<ChartObservationDefinitionEntity> list
//                    = chartObservationAdapter.searchNewChartObservationDefinition(patientEntity.getInsurerNo(), inputTypeCd);
            entity.setChartObservationDefinitionList(list);

            logger.debug("End");
            return entity;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * グラフ表示データを取得する
     *
     * @param phrId
     * @param chartDefinitionId
     * @param targetDate
     * @return
     * @throws Throwable
     */
    public Map<String, Map<ChartObservationDefinitionEntity, ObservationEntity>> getViewChartData(String phrId, Long chartDefinitionId, Date targetDate) throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            ChartObservationDefinitionAdapter chartObservationAdapter = new ChartObservationDefinitionAdapter(dao.getConnection());
            ObservationAdapter observationAdapter = new ObservationAdapter(dao.getConnection());

            Map<String, ChartObservationDefinitionEntity> charDefMap = new LinkedHashMap<>();
            List<ChartObservationDefinitionEntity> list
                    = chartObservationAdapter.getChartDefinition(chartDefinitionId);
            for (ChartObservationDefinitionEntity entity : list) {
                charDefMap.put(entity.getObservationDefinitionId(), entity);
            }

            Map<String, Map<ChartObservationDefinitionEntity, ObservationEntity>> dateMap = new LinkedHashMap<>();

            List<ObservationEntity> obList = observationAdapter.findByChartDefinitionId(phrId, chartDefinitionId, targetDate);
            for (ObservationEntity entity : obList) {
                String date = TypeUtility.format(entity.getExaminationDate(), "yyyy/MM/dd HH:mm");
                if (!dateMap.containsKey(date)) {
                    Map<ChartObservationDefinitionEntity, ObservationEntity> obMap = new LinkedHashMap<>();
                    for (ChartObservationDefinitionEntity defEntity : list) {
                        obMap.put(defEntity, null);
                    }
                    dateMap.put(date, obMap);
                }
                Map<ChartObservationDefinitionEntity, ObservationEntity> obMap = dateMap.get(date);
                ChartObservationDefinitionEntity defEntity = charDefMap.get(entity.getObservationDefinitionId());
                obMap.put(defEntity, entity);
            }

            logger.debug("End");
            return dateMap;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }

    /**
     * グラフ定義を登録する
     *
     * @param entity
     * @throws Throwable
     */
    public void entryChartDefinition(ChartDefinitionEntity entity) throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            dao.beginTransaction();

            ChartDefinitionAdapter adapter = new ChartDefinitionAdapter(dao.getConnection());
            ChartObservationDefinitionAdapter chartObservationAdapter = new ChartObservationDefinitionAdapter(dao.getConnection());

            long id = 0;
            if (entity.getChartDefinitionId() == 0) {
                // PHR IDから保険者Noを取得する
                PatientAdapter patientAdapter = new PatientAdapter(dao.getConnection());
                PatientEntity patientEntity = patientAdapter.findInsurePatient(entity.getPHR_ID());
                entity.setInsurerNo(patientEntity.getInsurerNo());

                // 定義の作成
                id = ChartDefinitionAdapter.numberingChartDefinitionId();
                entity.setChartDefinitionId(id);
                adapter.insert(entity);

            } else {

                // 定義の更新
                ChartDefinitionEntity dbEntity = adapter.findByPrimaryKey(entity.getChartDefinitionId());
                dbEntity.setChartDefinitionName(entity.getChartDefinitionName());
                dbEntity.setDataInputTypeCd(entity.getDataInputTypeCd());
                dbEntity.setViewCount(entity.getViewCount());
                adapter.update(dbEntity);
                id = dbEntity.getChartDefinitionId();
                // 定義項目を削除
                chartObservationAdapter.deleteByChartDefinitionId(dbEntity.getChartDefinitionId());

            }

            // 定義項目を作成
            for (ChartObservationDefinitionEntity obEntity : entity.getChartObservationDefinitionList()) {
                obEntity.setChartDefinitionId(id);
                chartObservationAdapter.insert(obEntity);
            }

            dao.commitTransaction();
            logger.debug("End");
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

    }

    /**
     * グラフ定義を削除する
     *
     * @param chartDefinitionId
     * @throws Throwable
     */
    public void deleteChartDefinition(long chartDefinitionId) throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");

            dao = new DataAccessObject();
            dao.beginTransaction();

            ChartDefinitionAdapter adapter = new ChartDefinitionAdapter(dao.getConnection());
            ChartObservationDefinitionAdapter chartObservationAdapter = new ChartObservationDefinitionAdapter(dao.getConnection());

            ChartDefinitionEntity dbEntity = adapter.findByPrimaryKey(chartDefinitionId);
            adapter.delete(dbEntity);

            chartObservationAdapter.deleteByChartDefinitionId(chartDefinitionId);

            dao.commitTransaction();
            logger.debug("End");
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

    }
    /**
     * 色リストを取得する
     * @return
     * @throws Throwable 
     */
    public List<ChartColorEntity> getColorList() throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");

            dao = new DataAccessObject();

            ChartColorAdapter adapter = new ChartColorAdapter(dao.getConnection());

            List<ChartColorEntity> list = adapter.findAll();

            logger.debug("End");

            return list;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

    }
    /**
     * 色リストを取得する
     * @return
     * @throws Throwable 
     */
    public Map<String, ChartColorEntity> getColorMap() throws Throwable {
        DataAccessObject dao = null;
        try {
            logger.debug("Start");

            dao = new DataAccessObject();

            ChartColorAdapter adapter = new ChartColorAdapter(dao.getConnection());

            Map<String, ChartColorEntity> map = adapter.findAllForMap();

            logger.debug("End");

            return map;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            try {
                if (dao != null) {
                    dao.close();
                }
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

    }

    /**
     * ObservationDefinitionIdからJLAC10コード・JLAC11コードを取得し、各マスタ内のGraphTargetFlgを確認
     * @param list
     * @return list
     */
    public List<ChartObservationDefinitionEntity> extract(List<ChartObservationDefinitionEntity> list) throws Throwable {
        
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject();
        
        ObservationDefinitionJlac10Adapter adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
        JLAC10AnalyteCodeAdapter jlac10Adapter = new JLAC10AnalyteCodeAdapter(dao.getConnection());
        JLAC11AnalyteCodeAdapter jlac11Adapter = new JLAC11AnalyteCodeAdapter(dao.getConnection());
        List<ChartObservationDefinitionEntity> newList = new ArrayList<>();
        boolean flg = false;
        for (int i = 0; i < list.size(); i++) {
            String observationDefinitionId = list.get(i).getObservationDefinitionId();
            List<ObservationDefinitionJlac10Entity> eList = new ArrayList<>();
            eList = adapter.findByPrimaryKeyGetAll(observationDefinitionId);
            if (eList != null) {
                ObservationDefinitionJlac10Entity e = eList.get(0);
                // まずJLAC10分析物マスタで確認
                String jlac10AnalyteType = null;
                if (e.getJLAC10() != null) {
                    jlac10AnalyteType = e.getJLAC10().substring(0, 5);
                }
                JLAC10AnalyteCodeEntity jlac10e = new JLAC10AnalyteCodeEntity();
                jlac10e = jlac10Adapter.findRecord(jlac10AnalyteType);
                if (jlac10e != null) {
                    if (jlac10e.isGraphTargetFllg()) {
                        if (newList.size() != 0) {
                            for (int ii = 0; ii < newList.size(); ii++) {
                                if (!newList.get(ii).getObservationDefinitionId().equals(e.getObservationDefinitionId())) {
                                    newList.add(list.get(i));
                                        flg = true;
                                        break;
                                    }
                                }
                        } else {
                            newList.add(list.get(i));
                            continue;
                        }
                    }
                }
                // JLAC10分析物マスタに存在しない場合、JLAC11測定物マスタで確認
                if (!flg) {
                    String jlac11AnalyteType = null;
                    if (e.getJLAC11() != null) {
                        jlac11AnalyteType = e.getJLAC11().substring(0, 5);
                    }
                    JLAC11AnalyteCodeEntity jlac11e = new JLAC11AnalyteCodeEntity();
                    jlac11e = jlac11Adapter.findRecord(jlac11AnalyteType);
                    if (jlac11e != null) {
                        if (jlac11e.isGraphTargetFllg()) {
                            if (newList.size() != 0) {
                                for (int ii = 0; ii < newList.size(); ii++) {
                                    if (!newList.get(ii).getObservationDefinitionId().equals(e.getObservationDefinitionId())) {
                                        newList.add(list.get(i));
                                        flg = true;
                                        break;
                                    }
                                }
                            } else {
                                newList.add(list.get(i));
                                continue;
                            }
                        }
                    }
                }
            }
        }

        // 自己管理と健診で重複している項目にフラグをセット
        List<Integer> delList = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            String observationDefinitionId = newList.get(i).getObservationDefinitionId();
            for (int ii = 1; ii < newList.size(); ii++) {
                if (i != ii) {
                    if (observationDefinitionId.equals(newList.get(ii).getObservationDefinitionId()) && !newList.get(ii).isDuplicateFlg()) {
                        newList.get(i).setDuplicateFlg(true);
                        delList.add(ii);
                    }
                }
            }
        }

        // 重複分を削除
        Collections.sort(delList, Collections.reverseOrder());
        for (int num : delList) {
            newList.remove(num);
        }

        logger.debug("End");
        return newList;
    }
}
