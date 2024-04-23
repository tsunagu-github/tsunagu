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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import phr.datadomain.entity.AnswerStatusEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.StudyDetailedInformationEntity;
import phr.datadomain.entity.StudyItemInformationEntity;
import phr.datadomain.entity.UtilizationConsentEntity;
import phr.service.impl.StudyDetailedInformationService;
import phr.service.impl.StudyInformationService;
import phr.service.impl.UtilizationConsentService;
import phr.web.ISessionUtility;
import phr.web.phone.dto.CheckListViewDto;
import phr.web.phone.dto.DynamicConsentViewDto;
import phr.web.phone.dto.RequestDynamicConsentDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseDynamicConsentDto;

/**
 *
 * @author daisuke
 */
public class DynamicConsentFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DynamicConsentFacade.class);

    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

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
            UtilizationConsentService utilizationConsentService = new UtilizationConsentService();
            RequestDynamicConsentDto requestDto = gson.fromJson(json, RequestDynamicConsentDto.class);
            ResponseDynamicConsentDto responseDto = new ResponseDynamicConsentDto();
            
            // 該当患者のPHR_IDを取得
            String phr_id = requestDto.getPhrId();
            logger.debug("対象者のPHR_ID　：　" + phr_id);
            
            // 処理の分岐（リスト取得or更新or患者情報取得）
            if (requestDto.getType().equals("update")) {
                String studyId = requestDto.getStudyId();
                String subjectId = requestDto.getSubjectId();
                String responseStatus = null;
                if (requestDto.getResponseStatus().equals("同意")) {
                    responseStatus = "1";
                } else if (requestDto.getResponseStatus().equals("非同意")) {
                    responseStatus = "2";
                } else if (requestDto.getResponseStatus().equals("保留")) {
                    responseStatus = "4";
                }
                // 更新処理
                int rowCount = utilizationConsentService.updateRecord(phr_id, studyId, responseStatus, subjectId);
                
                // オプトインの場合のみ以下の処理を行う
                String consentType = requestDto.getConsentType();
                if (consentType == null) {
                    // 回答情報テーブルに該当患者の情報があるか確認
                    List<String> checks = requestDto.getChecks();
                    List<AnswerStatusEntity> answerStatusEntityList = new ArrayList<>();
                    answerStatusEntityList = utilizationConsentService.findByPhrIdAndStudyId(phr_id, studyId, subjectId);
                    // 該当のStudyIdのチェックリストを取得
                    StudyDetailedInformationService studyDetailedInformationService = new StudyDetailedInformationService();
                    List<StudyDetailedInformationEntity> studyDetailedInformationEntityList = new ArrayList<>();
                    studyDetailedInformationEntityList = studyDetailedInformationService.findByStudyId(studyId, subjectId);
                    // 更新・登録用のエンティティ
                    List<AnswerStatusEntity> updateAnswerStatusEntityList = new ArrayList<>();
                    List<AnswerStatusEntity> insertAnswerStatusEntityList = new ArrayList<>();
                    if (answerStatusEntityList.size() != 0) {
                        // 存在すれば回答情報テーブルのレコードを更新
                        List<String> checkListIdList = new ArrayList<>();
                        for (AnswerStatusEntity ent : answerStatusEntityList) {
                            checkListIdList.add(ent.getCheckListId());
                        }
                        if (checks.size() == studyDetailedInformationEntityList.size()) {
                            for (int i = 0; i < checks.size(); i ++) {
                                for (int ii = 0; ii < studyDetailedInformationEntityList.size(); ii++) {
                                    if (i == ii) {
                                        AnswerStatusEntity updateEntity = new AnswerStatusEntity();
                                        AnswerStatusEntity insertEntity = new AnswerStatusEntity();
                                        // 既に回答情報テーブルにレコードが存在するかどうか
                                        if (checks.get(i).equals("1")) {
                                            for (String id : checkListIdList) {
                                                if (id.equals(studyDetailedInformationEntityList.get(ii).getSeqId())) {
                                                    updateEntity.setStatus(true);
                                                    updateEntity.setPHR_ID(phr_id);
                                                    updateEntity.setStudyId(studyId);
                                                    updateEntity.setSubjectId(subjectId);
                                                    updateEntity.setCheckListId(studyDetailedInformationEntityList.get(ii).getSeqId());
                                                    updateAnswerStatusEntityList.add(updateEntity);
                                                } else {
//                                                    insertEntity.setStatus(true);
//                                                    insertEntity.setPHR_ID(phr_id);
//                                                    insertEntity.setStudyId(studyId);
//                                                    insertEntity.setSubjectId(subjectId);
//                                                    insertEntity.setCheckListId(studyDetailedInformationEntityList.get(ii).getSeqId());
//                                                    insertAnswerStatusEntityList.add(insertEntity);
                                                }
                                            }
                                        } else {
                                            for (String id : checkListIdList) {
                                                if (id.equals(studyDetailedInformationEntityList.get(ii).getSeqId())) {
                                                    updateEntity.setStatus(false);
                                                    updateEntity.setPHR_ID(phr_id);
                                                    updateEntity.setStudyId(studyId);
                                                    updateEntity.setSubjectId(subjectId);
                                                    updateEntity.setCheckListId(studyDetailedInformationEntityList.get(ii).getSeqId());
                                                    updateAnswerStatusEntityList.add(updateEntity);
                                                } else {
//                                                    insertEntity.setStatus(false);
//                                                    insertEntity.setPHR_ID(phr_id);
//                                                    insertEntity.setStudyId(studyId);
//                                                    insertEntity.setSubjectId(subjectId);
//                                                    insertEntity.setCheckListId(studyDetailedInformationEntityList.get(ii).getSeqId());
//                                                    insertAnswerStatusEntityList.add(insertEntity);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // 無ければ回答情報テーブルに新規でレコードを追加
                        if (checks.size() == studyDetailedInformationEntityList.size()) {
                            for (int i = 0; i < checks.size(); i++) {
                                for (int ii = 0; ii < studyDetailedInformationEntityList.size(); ii++) {
                                    if (i == ii) {
                                        AnswerStatusEntity insertEntity = new AnswerStatusEntity();
                                        if (checks.get(i).equals("1")) {
                                            insertEntity.setStatus(true);
                                            insertEntity.setPHR_ID(phr_id);
                                            insertEntity.setStudyId(studyId);
                                            insertEntity.setSubjectId(subjectId);
                                            insertEntity.setCheckListId(studyDetailedInformationEntityList.get(ii).getSeqId());
                                            insertAnswerStatusEntityList.add(insertEntity);
                                        } else {
                                            insertEntity.setStatus(false);
                                            insertEntity.setPHR_ID(phr_id);
                                            insertEntity.setStudyId(studyId);
                                            insertEntity.setSubjectId(subjectId);
                                            insertEntity.setCheckListId(studyDetailedInformationEntityList.get(ii).getSeqId());
                                            insertAnswerStatusEntityList.add(insertEntity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 作成したエンティティを使って回答情報テーブルを更新・登録
                    int rowCount2 = 0;
                    if (updateAnswerStatusEntityList.size() != 0) {
                        // 更新処理
                        for (AnswerStatusEntity en : updateAnswerStatusEntityList) {
                            rowCount2 = utilizationConsentService.updateRecord(en);
                        }
                    } else if (insertAnswerStatusEntityList.size() != 0) {
                        // 登録処理
                        for (AnswerStatusEntity en : insertAnswerStatusEntityList) {
                            rowCount2 = utilizationConsentService.insertRecord(en);
                        }
                    }
                }
                
                // レスポンス作成
                if (rowCount == 0) {
                    responseDto.setRemarks("UpdateError");
                }
                responseDto.setStatus(ResponseBaseDto.SUCCESS);
            } else if (requestDto.getType().equals("getName")) {
                StringBuilder patientName = new StringBuilder();
                PatientEntity patientEntity = new PatientEntity();
                patientEntity = utilizationConsentService.getPatient(phr_id);
                if (patientEntity.getPHR_ID() != null) {
                    patientName.append(patientEntity.getFamilyName());
                    patientName.append(" ");
                    patientName.append(patientEntity.getGivenName());
                } else {
                    responseDto.setRemarks("Error");
                }
                responseDto.setPatientName(patientName.toString());
                responseDto.setStatus(ResponseBaseDto.SUCCESS);
            } else {
                // 研究ID昇順リスト
                List<DynamicConsentViewDto> viewList = new ArrayList<>();
                // 研究ID降順リスト
                List<DynamicConsentViewDto> viewList2 = new ArrayList<>();
                // 通知日昇順リスト
                List<DynamicConsentViewDto> viewList3 = new ArrayList<>();
                // 通知日降順リスト
                List<DynamicConsentViewDto> viewList4 = new ArrayList<>();
                // 最終更新日昇順リスト
                List<DynamicConsentViewDto> viewList5 = new ArrayList<>();
                // 最終更新日降順リスト
                List<DynamicConsentViewDto> viewList6 = new ArrayList<>();
                
                // PHR_IDを使用して活用同意一覧テーブルから有効なレコードのみを抽出
                List<UtilizationConsentEntity> utilizationConsentEntityList = new ArrayList<>();
                List<UtilizationConsentEntity> utilizationConsentEntityList2 = new ArrayList<>();
                List<UtilizationConsentEntity> utilizationConsentEntityList3 = new ArrayList<>();
                List<UtilizationConsentEntity> utilizationConsentEntityList4 = new ArrayList<>();
                List<UtilizationConsentEntity> utilizationConsentEntityList5 = new ArrayList<>();
                List<UtilizationConsentEntity> utilizationConsentEntityList6 = new ArrayList<>();
                utilizationConsentEntityList = utilizationConsentService.findByPhrId(phr_id, "StudyId");
                utilizationConsentEntityList2 = utilizationConsentService.findByPhrIdDesc(phr_id, "StudyId");
                utilizationConsentEntityList3 = utilizationConsentService.findByPhrId(phr_id, "NotificationDate");
                utilizationConsentEntityList4 = utilizationConsentService.findByPhrIdDesc(phr_id, "NotificationDate");
                utilizationConsentEntityList5 = utilizationConsentService.findByPhrId(phr_id, "UpdateDate");
                utilizationConsentEntityList6 = utilizationConsentService.findByPhrIdDesc(phr_id, "UpdateDate");
                
                // viewListの作成（研究ID順リスト・通知日昇順リスト・回答更新日昇順リスト）
                viewList = this.createViewList(utilizationConsentEntityList, phr_id);
                viewList2 = this.createViewList(utilizationConsentEntityList2, phr_id);
                viewList3 = this.createViewList(utilizationConsentEntityList3, phr_id);
                viewList4 = this.createViewList(utilizationConsentEntityList4, phr_id);
                viewList5 = this.createViewList(utilizationConsentEntityList5, phr_id);
                viewList6 = this.createViewList(utilizationConsentEntityList6, phr_id);
                
                // レスポンス作成
                if (viewList.size() != 0) {
                    responseDto.setViewList(viewList);
                    responseDto.setViewList2(viewList2);
                    responseDto.setViewList3(viewList3);
                    responseDto.setViewList4(viewList4);
                    responseDto.setViewList5(viewList5);
                    responseDto.setViewList6(viewList6);
                } else {
                    responseDto.setRemarks("NoData");
                }
                responseDto.setStatus(ResponseBaseDto.SUCCESS);
            }
            logger.debug("End");
            return responseDto;
            
        } catch (Throwable t) {
            logger.error("", t);
            throw new Exception("[" + this.getClass().getSimpleName() + "] Error", t);
        }
    }
    
    /**
     * viewListの作成 
     * @param utilizationConsentEntityList
     * @param phr_id
     * @return viewList
     */
    public List<DynamicConsentViewDto> createViewList(List<UtilizationConsentEntity> utilizationConsentEntityList, String phr_id) throws Throwable {
        logger.debug("Start");
        StudyInformationService studyInformationService = new StudyInformationService();
        StudyDetailedInformationService studyDetailedInformationService = new StudyDetailedInformationService();
        UtilizationConsentService utilizationConsentService = new UtilizationConsentService();
        List<DynamicConsentViewDto> viewList = new ArrayList<>();
        
        // 研究IDごとに処理を行う
        if (utilizationConsentEntityList.size() != 0) {
            for (UtilizationConsentEntity e : utilizationConsentEntityList) {
                // まずPHR_IDを使用して回答情報が存在するか確認
                List<AnswerStatusEntity> answerStatusEntityList = new ArrayList<>();
                answerStatusEntityList = utilizationConsentService.findByPhrIdAndStudyId(phr_id, e.getStudyId(), e.getSubjectId());
                DynamicConsentViewDto dto = new DynamicConsentViewDto();
                // 研究ID
                dto.setStudyId(e.getStudyId());
                // 項目ID
                dto.setSubjectId(e.getSubjectId());
                // 同意種別
                if (e.getConsentType().equals("1")) {
                    dto.setConsentType("オプトイン方式同意");
                    dto.setIsOptInIconView("block");
                    dto.setIsOptOutIconView("none");
                    dto.setFontColor("#ff0000");
                } else if (e.getConsentType().equals("2")) {
                    dto.setConsentType("オプトアウト方式同意");
                    dto.setIsOptInIconView("none");
                    dto.setIsOptOutIconView("block");
                    dto.setFontColor("#0000ff");
                }
                // 回答ステータス
                if (e.getResponseStatus().equals("1")) {
                    dto.setResponseStatus("同意");
                    dto.setIsCheckIconView("block");
                    dto.setIsBanIconView("none");
                    dto.setIsHoldIconView("none");
                } else if (e.getResponseStatus().equals("2")) {
                    dto.setResponseStatus("非同意");
                    dto.setIsCheckIconView("none");
                    dto.setIsBanIconView("block");
                    dto.setIsHoldIconView("none");
                } else if (e.getResponseStatus().equals("3")) {
                    dto.setResponseStatus("未回答");
                    dto.setIsCheckIconView("none");
                    dto.setIsBanIconView("none");
                    dto.setIsHoldIconView("none");
                } else if (e.getResponseStatus().equals("4")) {
                    dto.setResponseStatus("保留");
                    dto.setIsCheckIconView("none");
                    dto.setIsBanIconView("none");
                    dto.setIsHoldIconView("block");
                } 
                // 新着フラグ
                if (e.isNewArrivalFlg()) {
                    dto.setIsNewArrivalView("block");
                } else {
                    dto.setIsNewArrivalView("none");
                }
                dto.setNewArrivalFlg(e.isNewArrivalFlg());
                // 通知日
                dto.setNotificationDate(sdf.format(e.getNotificationDate()));
                // 回答更新日
                if (e.getResponseDate() != null) {
                    dto.setResponseDate(sdf.format(e.getResponseDate()));
                    dto.setIsResponseDateView("block");
                } else {
                    dto.setIsResponseDateView("none");
                }
                // 研究IDと項目IDで研究情報テーブルからレコードを抽出
                String studyId = e.getStudyId();
                String subjectId = e.getSubjectId();
                StudyItemInformationEntity studyItemInformationEntity = new StudyItemInformationEntity();
                studyItemInformationEntity = studyInformationService.findByStudyId(studyId, subjectId);
                if (studyItemInformationEntity.getStudyId() != null) {
                    // 研究名称
                    dto.setStudyName(studyItemInformationEntity.getStudyName());
                    // 研究についての説明文
                    if (studyItemInformationEntity.getSubject() != null) {
                        dto.setExplanation(studyItemInformationEntity.getSubject());
                    }
                    // 詳細説明のURL
                    if (studyItemInformationEntity.getUrl() != null) {
                        dto.setUrl(studyItemInformationEntity.getUrl());
                    }
                } else {
                    // 対象の研究情報が無い時
                    logger.debug("該当の研究情報がありません。次の研究IDの処理に移ります。");
                    continue;
                }
                // 取得した研究IDで研究詳細情報テーブルからレコードを抽出
                List<StudyDetailedInformationEntity> studyDetailedInformationEntityList = new ArrayList<>();
                studyDetailedInformationEntityList = studyDetailedInformationService.findByStudyId(studyId, subjectId);
                if (studyDetailedInformationEntityList.size() != 0) {
                    // 確認項目
                    List<CheckListViewDto> dtoList = new ArrayList<>();
//                    List<String> checkList = new ArrayList<>();
//                    List<String> newCheckList = new ArrayList<>();
                    for (int i = 0; i < studyDetailedInformationEntityList.size(); i++) {
//                        newCheckList.add(i+1 + ":" + studyDetailedInformationEntityList.get(i).getCheckList());
                    	CheckListViewDto checkListViewDto = new CheckListViewDto();
                        checkListViewDto.setCheckItem(i+1 + ":" + studyDetailedInformationEntityList.get(i).getCheckList());
                        // 既に回答情報が存在する場合
                        if (answerStatusEntityList.size() != 0) {
                            // 回答情報レコードと研究詳細情報レコードを比較
                            boolean flg = false;
                            for (AnswerStatusEntity entity : answerStatusEntityList) {
                                if (entity.getCheckListId().equals(studyDetailedInformationEntityList.get(i).getSeqId())) {
                                    if (entity.isStatus()) {
                                        checkListViewDto.setSelectStatus("1");
                                        flg = false;
                                        break;
                                    } else {
                                        checkListViewDto.setSelectStatus("0");
                                        flg = false;
                                        break;
                                    }
                                } else {
                                    flg = true;
                                }
                            }
                            if (flg) {
                                checkListViewDto.setSelectStatus("0");
                            }
                        } else {
                            // 回答情報が存在しない場合
                            checkListViewDto.setSelectStatus("0");
                        }
                        dtoList.add(checkListViewDto);
                    }
//                    for (String en : newCheckList) {
//                        checkList.add(en);
//                        dtoList.add(checkListViewDto);
//                    }
//                    dto.setCheckList(checkList);
                    dto.setCheckList(dtoList);
                } else {
                    // 対象の研究詳細情報が無い時
                    if (!dto.getConsentType().equals("オプトアウト")) {
                        logger.debug("該当の研究詳細情報がありません。次の研究IDの処理に移ります。");
                        continue;
                    }
                }
                // 取得した値をviewListにセット
                viewList.add(dto);
            }
        } else {
            // 対象患者の活用同意一覧が無い時
            logger.debug("対象患者の活用同意一覧がありません。処理を終了します。");
        }
        
        logger.debug("End");
        return viewList;
    }
}
