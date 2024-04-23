/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.service.impl.OnetimePasswordService.OneTimePasswordResult;
import phr.service.impl.OnetimePasswordService.OnetimePasswordResultCd;
import phr.service.IOnetimePasswordService;
import phr.utility.TypeUtility;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.form.TargetingPatientForm;
import phr.web.form.TargetingPatientListForm;

/**
 *
 * @author KISNOTE011
 */
@Controller
@RequestMapping({ActionConst.TARGETING_PATIENT_ACTION})
public class TargetingPatientController {

    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(TargetingPatientController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.TARGETING_PATIENT_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.TARGETING_PATIENT_FORM_TITLE;

    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * インジェクション：ワンタイムパスワードサービス
     */
    @Autowired
    @Qualifier("OnetimePasswordService")
    private IOnetimePasswordService onetimePasswordService;
    
    /**
     * 初期アクションメソッド
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET })
    public String defaultAction(Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");

   	//画面初期化
   	TargetingPatientForm form = new TargetingPatientForm();
   	this.initPage(model,form);
   	model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.TARGETING_PATIENT_ACTION);
    }
   
    /**
     * 対象者情報指定のワンタイムパスワード確認
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=searchOneTimePW")
    public String searchOneTimePWAction(Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");

        String actionPage=ActionConst.TARGETING_PATIENT_INFO_ACTION;
        
   	//画面初期化
   	TargetingPatientForm form = new TargetingPatientForm();
   	this.initPage(model,form);
   	model.addAttribute(form);

        //ワンタイムパスワード確認と情報取得
        String onePW = request.getParameter("_onePW");
        AccountEntity entity= (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity mentity= (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        if(TypeUtility.isNullOrEmpty(onePW) || (entity==null && mentity == null)){
            //パスワード・保険者なし（ワンタイム情報なし）
            sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
            actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
        }else{
            if(entity != null){
                OneTimePasswordResult retOTP=onetimePasswordService.searchPassword(onePW,entity.getInsurerNo(),null);
                if(retOTP==null || !retOTP.getResultCd().equals(OnetimePasswordResultCd.SUCCCESS)){
                    //ワンタイム情報なし
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
                }else if(new Date().after(retOTP.getExpirationDate())){
                    //取得情報が無効
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
                }else if(new Date().after(retOTP.getExpirationDate())){
                    //取得情報が保険者と関連なし
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
                }else{
                    //取得情報が有効か確認
                    if(new Date().after(retOTP.getExpirationDate())){
                        sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                        actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
                    }else{
                        sessionUtility.setSession(SessionConst.VIEW_PASSWORD_INFO, retOTP);
                    }
                }
            }else{
                OneTimePasswordResult retOTP=onetimePasswordService.searchPassword(onePW,null,mentity.getMedicalOrganizationCd());
                if(retOTP==null || !retOTP.getResultCd().equals(OnetimePasswordResultCd.SUCCCESS)){
                    //ワンタイム情報なし
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
                }else if(new Date().after(retOTP.getExpirationDate())){
                    //取得情報が無効
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
                }else if(new Date().after(retOTP.getExpirationDate())){
                    //取得情報が保険者と関連なし
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
                }else{
                    //取得情報が有効か確認
                    if(new Date().after(retOTP.getExpirationDate())){
                        sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                        actionPage=ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION;
                    }else{
                        sessionUtility.setSession(SessionConst.VIEW_PASSWORD_INFO, retOTP);
                    }
                }                
            }
        }
        
        logger.debug("End");
        return ActionConst.redirectActionPage(actionPage);
    }

    /**
     * 対象患者の検査結果画面へ遷移
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=select")
    public String selectPatientInfo(TargetingPatientListForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        sessionUtility.setSession(SessionConst.TARGET_PHRID, form.getParam1());
        
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.TARGETING_PATIENT_INFO_ACTION);
    }
    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model,TargetingPatientForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
