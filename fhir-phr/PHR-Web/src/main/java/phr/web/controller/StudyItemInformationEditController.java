package phr.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import phr.datadomain.entity.StudyItemInformationEntity;
import phr.datadomain.adapter.StudyDetailedInformationAdapter;
import phr.datadomain.adapter.StudyItemInformationAdapter;
import phr.datadomain.entity.StudyDetailedInformationEntity;
import phr.datadomain.entity.StudyInformationEntity;
import phr.service.IStudyItemInformationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.form.StudyItemInformationForm;
import phr.service.impl.StudyItemInformationService;
import phr.service.impl.StudyDetailedInformationService;
import phr.service.impl.StudyInformationService;

@Scope("request")
@Controller
@RequestMapping({ActionConst.STUDY_ITEM_INFORMATION_EDIT, "/"})
public class StudyItemInformationEditController {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(StudyItemInformationEditController.class);
    
    /**画面CD */
    private final String FORM_CD = FormConst.STUDY_ITEM_INFORMATION_CD;
 
    /**画面タイトル */
    private final String FORM_NAME = FormConst.STUDY_ITEM_INFORMATION_TITLE;
    
    @Autowired
    private MessageSource messageSource;
    
    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    
    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        //画面初期化
        StudyItemInformationForm form = new StudyItemInformationForm();
        StudyItemInformationEntity entity = (StudyItemInformationEntity)sessionUtility.getSession(SessionConst.EDIT_STUDY_ITEM_INFORMATION_ENTITY);
        List<StudyDetailedInformationEntity> entityDetailedList = (List<StudyDetailedInformationEntity>)sessionUtility.getSession(SessionConst.EDIT_STUDY_DETAILED_INFORMATION_ENTITY);
        
        //新規作成
        if (entity.getSubjectId() == null) {
            form.setEditFlg(false);
        //修正
        } else {
            form.setEditFlg(true);
        }
        form.setStudyName(entity.getStudyName());
        form.setStudyId(entity.getStudyId());
        form.setSubjectId(entity.getSubjectId());
        form.setSubject(entity.getSubject());
        form.setUrl(entity.getUrl());
        if (entity.getConsentType() == null) {
            form.setConsentType("1");
        } else {
            form.setConsentType(entity.getConsentType());
        }
        
        List<String> list = new ArrayList<>();
        for (StudyDetailedInformationEntity entityDetailed : entityDetailedList) {
            String checkList = entityDetailed.getCheckList();
            list.add(checkList);
        }
//        form.setCheckListList(list);
        StringBuilder buf = new StringBuilder();
        for (String check : list) {
            buf.append(check);
            buf.append("\n");
        }
        String s = buf.toString();
        form.setCheckList(s);
        
        
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.STUDY_ITEM_INFORMATION_EDIT);
    }

    /**
     * <pre>登録アクション</pre>
     *
     * @param model
     * @param request
     * @return redirectActionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=confirm")
    public String confirmAction(@ModelAttribute StudyItemInformationForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        List<StudyInformationEntity> studyInformationList = new ArrayList<>();
        studyInformationList = (List<StudyInformationEntity>)sessionUtility.getSession(SessionConst.STUDY_INFORMATION_ENTITY_LIST);
        StudyItemInformationEntity itemEntity = (StudyItemInformationEntity)sessionUtility.getSession(SessionConst.EDIT_STUDY_ITEM_INFORMATION_ENTITY);
        List<StudyDetailedInformationEntity> detailedList = (List<StudyDetailedInformationEntity>)sessionUtility.getSession(SessionConst.EDIT_STUDY_DETAILED_INFORMATION_ENTITY);
        StudyDetailedInformationEntity newEntityDetailed = new StudyDetailedInformationEntity();
        StudyDetailedInformationEntity detailed = new StudyDetailedInformationEntity();
        StudyItemInformationEntity newItemEntity = new StudyItemInformationEntity();
        
        if (itemEntity.getSubjectId() != null && !detailedList.isEmpty()){
            detailed = detailedList.get(0);
        }
        //formで選択されたstudyNameからstudyIdを取得
        for (StudyInformationEntity studyInformation : studyInformationList) {
            if (studyInformation.getStudyName().equals(form.getStudyName())) {
                newItemEntity.setStudyId(studyInformation.getStudyId());
                break;
            } 
        }
        StudyItemInformationService studyItemInformationService = new StudyItemInformationService();
        StudyDetailedInformationService studyDetailedInformationService = new StudyDetailedInformationService();
        
        //新規作成
        if (itemEntity.getSubjectId() == null) {
            form.setEditFlg(false);
        //修正
        } else {
            form.setEditFlg(true);
        }
        if (this.formErrorCheck(form, result)) {
            //新規作成
            if (itemEntity.getSubjectId() == null){
              //StudyItemInformationServiceテーブルの登録
                newItemEntity.setSubjectId(StudyItemInformationAdapter.numberingSubjectId());
                newItemEntity.setSubject(form.getSubject());
                newItemEntity.setConsentType(form.getConsentType());
                newItemEntity.setUrl(form.getUrl());
                newItemEntity.setCheckList(form.getCheckList());
                studyItemInformationService.registAccount(itemEntity, newItemEntity, false);
                form.setEditFlg(false);
              //StudyDetailedInformationテーブルの登録
                if (form.getConsentType().equals("1")) {
                    newEntityDetailed.setSeqId(StudyDetailedInformationAdapter.numberingSeqId());
                    newEntityDetailed.setStudyId(newItemEntity.getStudyId());
                    newEntityDetailed.setSubjectId(newItemEntity.getSubjectId());
                    newEntityDetailed.setCheckList(form.getCheckList());
                    newEntityDetailed.setStatus("0");
                    newEntityDetailed.setSortNo("1");
                    studyDetailedInformationService.insertDetalied(detailed, newEntityDetailed, false, newItemEntity);
                }
                //修正
            } else {
              //StudyItemInformationServiceテーブルの登録
                newItemEntity.setStudyId(itemEntity.getStudyId());
                newItemEntity.setStudyName(itemEntity.getStudyName());
                newItemEntity.setSubjectId(itemEntity.getSubjectId());
                newItemEntity.setSubject(itemEntity.getSubject());
                newItemEntity.setConsentType(itemEntity.getConsentType());
                newItemEntity.setUrl(form.getUrl());
//                newItemEntity.setCheckList(form.getCheckList());
                studyItemInformationService.registAccount(itemEntity, newItemEntity, true);
                form.setEditFlg(true);
              //StudyDetailedInformationテーブルの登録
                newEntityDetailed.setSeqId(StudyDetailedInformationAdapter.numberingSeqId());
                newEntityDetailed.setStudyId(itemEntity.getStudyId());
                newEntityDetailed.setSubjectId(itemEntity.getSubjectId());
                newEntityDetailed.setNewCheckList(form.getCheckList());
                newEntityDetailed.setStatus("0");
                newEntityDetailed.setSortNo("1");
                studyDetailedInformationService.insertDetalied(detailed, newEntityDetailed, true, newItemEntity);
            }
            
            model.addAttribute("StudyItemInformationEntity", newItemEntity);
        } else {
            logger.debug("必須項目に未入力があるためエラー");
            form.setStudyName(itemEntity.getStudyName());
            form.setConsentType(itemEntity.getConsentType());
            form.setSubject(itemEntity.getSubject());
            this.initPage(model,form);
            model.addAttribute(form);
            return ActionConst.actionPage(ActionConst.STUDY_ITEM_INFORMATION_EDIT);
        }

        //画面初期化
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.STUDY_ITEM_INFORMATION);
    }
    
    /**
     * <pre>入力内容のチェック</pre>
     * 
     * @param form
     * @param result
     * @return
     * @throws Throwable
     */
    private boolean formErrorCheck(StudyItemInformationForm form, BindingResult result) throws Throwable {
        //エラーメッセージ
        String message;
        StudyItemInformationEntity itemEntity = (StudyItemInformationEntity)sessionUtility.getSession(SessionConst.EDIT_STUDY_ITEM_INFORMATION_ENTITY);
        if (!form.isEditFlg()) {
            //新規作成時
            if (form.getSubject() == null || form.getSubject().length() == 0) {
                message = messageSource.getMessage(ValidationMessageConst.NOTEMPTY_SUBJECT_MESSAGE, null, null);
                ObjectError error = new ObjectError("confirm", message);
                result.addError(error);
            }
            if (form.getUrl() == null || form.getUrl().length() == 0) {
                message = messageSource.getMessage(ValidationMessageConst.NOTEMPTY_URL_MESSAGE, null, null);
                ObjectError error = new ObjectError("confirm", message);
                result.addError(error);
            }
            if (form.getConsentType().equals("1")) {        
                if (form.getCheckList() == null || form.getCheckList().length() == 0) {
                    message = messageSource.getMessage(ValidationMessageConst.NOTEMPTY_CHECKLIST_MESSAGE, null, null);
                    ObjectError error = new ObjectError("confirm", message);
                    result.addError(error);
                }
            }
            //修正時
        } else {
            if (form.getUrl() == null || form.getUrl().length() == 0) {
                message = messageSource.getMessage(ValidationMessageConst.NOTEMPTY_URL_MESSAGE, null, null);
                ObjectError error = new ObjectError("confirm", message);
                result.addError(error);
            }
            if (itemEntity.getConsentType().equals("1")) {        
                if (form.getCheckList() == null || form.getCheckList().length() == 0) {
                    message = messageSource.getMessage(ValidationMessageConst.NOTEMPTY_CHECKLIST_MESSAGE, null, null);
                    ObjectError error = new ObjectError("confirm", message);
                    result.addError(error);
                }
            }
        }
        //--- 必須チェック end---//
        return !result.hasErrors();
    }
    
    /**
     * <pre>戻るアクション</pre>
     *
     * @param model
     * @param request
     * @return redirectActionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=return")
    public String returnAction(@ModelAttribute StudyItemInformationForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        //画面初期化
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.STUDY_ITEM_INFORMATION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     * @throws Throwable 
     */
    private void initPage(Model model,StudyItemInformationForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
        
        //研究ID,研究名を取得
        StudyInformationService studyInformationService = new StudyInformationService();
        List<StudyInformationEntity> studyInformationEntityList = studyInformationService.findAll();
        
        sessionUtility.setSession(SessionConst.STUDY_INFORMATION_ENTITY_LIST, studyInformationEntityList);
        form.setStudyInformationEntityList(studyInformationEntityList);
        model.addAttribute("studyInformationEntityList", studyInformationEntityList);
    }
}