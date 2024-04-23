/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import phr.service.IMedicalCreateEditService;
import phr.web.ActionConst;
import phr.web.ISessionUtility;
import phr.web.form.MedicalCreateConfirmForm;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.service.IMedicalSearchService;
import phr.web.FormConst;
import phr.web.SessionConst;

/**
 *
 * @author kis.o-note-002
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.MEDICAL_CREATE_CONFIRM_ACTION, "/"})
public class MedicalCreateConfirmController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalCreateConfirmController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.MEDICAL_CREATE_CONFIRM_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MEDICAL_CREATE_CONFIRM_FORM_TITLE;

    
    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;
    
    /**
     * インジェクション：各種マスタサービス処理
     */
    @Autowired
    @Qualifier("MedicalCreateEditService")
    private IMedicalCreateEditService medicalCreateEditService;
    
    /**
     * インジェクション：各種マスタサービス処理
     */
    @Autowired
    @Qualifier("MedicalSearchService")
    private IMedicalSearchService medicalSearchService;

    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param form
     * @param model
     * @param request
     * @param result
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(MedicalCreateConfirmForm form, Model model, HttpServletRequest request , BindingResult result) throws Throwable {

        logger.debug("Start");
        //ログインサイトを設定
        sessionUtility.setSession("SessionConst.SITE", null);

        MedicalOrganizationEntity entity = (MedicalOrganizationEntity) sessionUtility.getSession(SessionConst.MEDICAL_INFO);

        model.addAttribute("MedicalOrganizationEntity",entity);
        //画面初期化
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MEDICAL_CREATE_CONFIRM_ACTION);
    }
    
    /**
     * 登録ボタンクリック時
     * @param form
     * @param model
     * @param request
     * @param result
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=insert")
    public String insertAction(MedicalCreateConfirmForm form, Model model, HttpServletRequest request,BindingResult result) throws Throwable {
        logger.debug("Start");
        MedicalOrganizationEntity entity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICAL_INFO);
        // 登録処理
        if(this.medicalOrganizationInsertUpdate(entity, model)){
            form.setSucessed(true);
        } else {
            form.setSucessed(false);
        }

        model.addAttribute("MedicalOrganizationEntity",entity);
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MEDICAL_CREATE_CONFIRM_ACTION);
    }
    
    /**
     * <pre>医療機関情報登録、更新処理</pre>
     * @param form
     * @param model
     * @return
     * @throws Throwable 
     */
    private boolean medicalOrganizationInsertUpdate(MedicalOrganizationEntity entity,Model model) throws Throwable {
        
        boolean result=false;
        // 医療機関情報登録処理
        try{
            if(!entity.isUpdateFlg()){
                result =this.medicalCreateEditService.insertMedicalOrganization(entity);
           } else{
                result =this.medicalCreateEditService.updateMedicalOrganization(entity);                
           }
        } catch(Exception e){
            logger.debug(e.getMessage());
            result=false;
        }
        return result;
    }
    
     /**
     * 戻るボタンクリック時の処理
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=return")
    public String returnAction(@ModelAttribute @Valid MedicalCreateConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable{
        logger.debug("Start");
        
                
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.MEDICAL_CREATE_EDIT_ACTION);
    }
    
     /**
     * 戻るボタンクリック時の処理
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=returnlist")
    public String returnListAction(@ModelAttribute @Valid MedicalCreateConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable{
        logger.debug("Start");
        
                
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.MEDICAL_SEARCH_ACTION);
    }
        /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, MedicalCreateConfirmForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        model.addAttribute(form);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }

}
