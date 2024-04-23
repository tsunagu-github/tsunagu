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
import phr.web.form.StudyItemInformationForm;
import phr.service.impl.StudyItemInformationService;
import phr.service.impl.StudyDetailedInformationService;
import phr.service.impl.StudyInformationService;

@Scope("request")
@Controller
@RequestMapping({ActionConst.STUDY_ITEM_INFORMATION, "/"})
public class StudyItemInformationController {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(StudyItemInformationController.class);
    
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
        
        List<StudyInformationEntity> entityList = new ArrayList<>();
        entityList = (List<StudyInformationEntity>)sessionUtility.getSession(SessionConst.STUDY_INFORMATION_ENTITY_LIST);
        StudyItemInformationService studyItemInformationService = new StudyItemInformationService();
        List<StudyItemInformationEntity> allList = studyItemInformationService.getAllStudyItemInformationList();
        form.setStudyItemInformationEntityList(allList);
        sessionUtility.setSession(SessionConst.STUDY_ITEM_INFORMATION_ENTITY_LIST, allList);
        
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.STUDY_ITEM_INFORMATION);
    }
    
    /**
     * <pre>新規作成アクション</pre>
     *
     * @param model
     * @param request
     * @return redirectActionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=create")
    public String createAction(@ModelAttribute StudyItemInformationForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        StudyItemInformationEntity entity = new StudyItemInformationEntity();
        List<StudyDetailedInformationEntity> entityDetailed = new ArrayList<>();
        sessionUtility.setSession(SessionConst.EDIT_STUDY_ITEM_INFORMATION_ENTITY, entity);
        sessionUtility.setSession(SessionConst.EDIT_STUDY_DETAILED_INFORMATION_ENTITY, entityDetailed);
        
        //画面初期化
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.STUDY_ITEM_INFORMATION_EDIT);
    }
    
    /**
     * <pre>修正アクション</pre>
     *
     * @param model
     * @param request
     * @return redirectActionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=edit")
    public String editAction(@ModelAttribute StudyItemInformationForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        String studyId = request.getParameter("param1");
        String subjectId = request.getParameter("param2");
        StudyItemInformationService studyItemInformationService = new StudyItemInformationService();
        StudyItemInformationEntity entity = studyItemInformationService.studyInfo(studyId, subjectId);
        StudyDetailedInformationService studyDetailedInformationService = new StudyDetailedInformationService();
        List<StudyDetailedInformationEntity> entityDetailedList = studyDetailedInformationService.studyInfo(studyId, subjectId);
        sessionUtility.setSession(SessionConst.EDIT_STUDY_ITEM_INFORMATION_ENTITY, entity);
        sessionUtility.setSession(SessionConst.EDIT_STUDY_DETAILED_INFORMATION_ENTITY, entityDetailedList);

        //画面初期化
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.STUDY_ITEM_INFORMATION_EDIT);
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
    }
}