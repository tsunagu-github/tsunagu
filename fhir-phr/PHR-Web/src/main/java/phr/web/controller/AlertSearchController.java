/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ObservationAlertEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IAlertSearchService;
import phr.service.IOnetimePasswordService;
import phr.service.IUserAuthenticationService;
import phr.service.impl.OnetimePasswordService;
import phr.utility.TypeUtility;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.form.AlertSearchForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.ALERT_SEARCH_ACTION, "/"})
public class AlertSearchController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AlertSearchController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.ALERT_SEARCH_ACTION_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.ALERT_SEARCH_ACTION_TITLE;
    
    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("UserAuthenticationService")
    private IUserAuthenticationService userAuthenticationService;
    
    /**
     * インジェクション：アラート一覧機能サービス
     */
    @Autowired
    @Qualifier("AlertSearchService")
    private IAlertSearchService alertSearchService;
    
    /**
     * インジェクション：ワンタイムパスワードサービス
     */
    @Autowired
    @Qualifier("OnetimePasswordService")
    private IOnetimePasswordService onetimePasswordService;
    
    /*
    * 日付処理
    */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");    /**
    /*
    * 日付処理
    */
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");    /**
    
    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param form
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(AlertSearchForm form, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //画面初期化
        this.initPage(model, form);
        
        model.addAttribute(form);
        logger.debug("End");

        return ActionConst.actionPage(ActionConst.ALERT_SEARCH_ACTION);
    }
    
    /**
     * <pre>検索ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=search")
    public String searchAction(AlertSearchForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        String insurerNo = null;
        AccountEntity account = (AccountEntity) sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity medicalEntity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        if (account == null) {
            insurerNo = medicalEntity.getMedicalOrganizationCd();
        } else {
            insurerNo = account.getInsurerNo();
        }
        
        try {
            Date startDate = DateSet(form.getStartDate(),form,result);
            Date endDate = DateSet(form.getEndDate(), form,result);
            
            if(startDate != null){
                Date date = new Date();
                if(date.before(startDate)){
                    ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.JAVAX_VALIDATION_CONSTRAINTS_PAST_MESSAGE,null,null));
                    result.addError(error);
                }
            }
            
            List<ObservationAlertEntity> list = null;
            
            if(!result.hasErrors()){
                Timestamp t_startDate = null;
                if(startDate != null) t_startDate = new Timestamp(startDate.getTime());
                Timestamp t_endDate = null;
                if(endDate != null) t_endDate = new Timestamp(endDate.getTime());
                
                String phrid = form.getPhrId();
                if(phrid == null || phrid.trim().length() == 0) phrid = null;
                
                list = alertSearchService.alertSearch(insurerNo, phrid, t_startDate, t_endDate);
                
                
                if(list.size() == 0){
                    ObjectError error = new ObjectError("search", "検索結果が0件でした。");
                    result.addError(error);
                }else{
                    disPlayList(form , list);
                }
            }

        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        this.initPage(model, form);
        model.addAttribute(form);
        model.addAttribute("insurerNo", insurerNo);
        
        return ActionConst.actionPage(ActionConst.ALERT_SEARCH_ACTION);
    }

    /**
     * 対象者情報指定のワンタイムパスワード確認
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    /*
    @RequestMapping(method = {RequestMethod.POST }, params = "command=searchOneTimePW")
    public String searchOneTimePWAction(Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");

        String actionPage=ActionConst.TARGETING_PATIENT_INFO_ACTION;
        
   	//画面初期化
   	AlertSearchForm form = new AlertSearchForm();
   	this.initPage(model,form);
   	model.addAttribute(form);

        //ワンタイムパスワード確認と情報取得
        String onePW = request.getParameter("_onePW");
        AccountEntity entity= (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity mentity= (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        if(TypeUtility.isNullOrEmpty(onePW) || (entity==null && mentity == null)){
            //パスワード・保険者なし（ワンタイム情報なし）
            sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
            actionPage=ActionConst.ALERT_SEARCH_ACTION;
        }else{
            if(entity != null){
                OnetimePasswordService.OneTimePasswordResult retOTP=onetimePasswordService.searchPassword(onePW,entity.getInsurerNo(),null);
                if(retOTP==null || !retOTP.getResultCd().equals(OnetimePasswordService.OnetimePasswordResultCd.SUCCCESS)){
                    //ワンタイム情報なし
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.ALERT_SEARCH_ACTION;
                }else if(new Date().after(retOTP.getExpirationDate())){
                    //取得情報が無効
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.ALERT_SEARCH_ACTION;
                }else if(new Date().after(retOTP.getExpirationDate())){
                    //取得情報が保険者と関連なし
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.ALERT_SEARCH_ACTION;
                }else{
                    //取得情報が有効か確認
                    if(new Date().after(retOTP.getExpirationDate())){
                        sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                        actionPage=ActionConst.ALERT_SEARCH_ACTION;
                    }else{
                        sessionUtility.setSession(SessionConst.VIEW_PASSWORD_INFO, retOTP);
                    }
                }
            }else{
                OnetimePasswordService.OneTimePasswordResult retOTP=onetimePasswordService.searchPassword(onePW,null,mentity.getMedicalOrganizationCd());
                if(retOTP==null || !retOTP.getResultCd().equals(OnetimePasswordService.OnetimePasswordResultCd.SUCCCESS)){
                    //ワンタイム情報なし
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.ALERT_SEARCH_ACTION;
                }else if(new Date().after(retOTP.getExpirationDate())){
                    //取得情報が無効
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.ALERT_SEARCH_ACTION;
                }else if(new Date().after(retOTP.getExpirationDate())){
                    //取得情報が保険者と関連なし
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                    actionPage=ActionConst.ALERT_SEARCH_ACTION;
                }else{
                    //取得情報が有効か確認
                    if(new Date().after(retOTP.getExpirationDate())){
                        sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                        actionPage=ActionConst.ALERT_SEARCH_ACTION;
                    }else{
                        sessionUtility.setSession(SessionConst.VIEW_PASSWORD_INFO, retOTP);
                    }
                }                
            }
        }
        
        logger.debug("End");
        return ActionConst.redirectActionPage(actionPage);
    }
    */
    
    /**
     * <pre>確認ボタン押下時のアクションメソッド</pre>
     *
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=move")
    public String getPatientInfoAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        String actionPage=ActionConst.ALERT_PATIENT_INFO_ACTION;

        AlertSearchForm form = new AlertSearchForm();
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        
        return ActionConst.redirectActionPage(actionPage);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, AlertSearchForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    private java.util.Date DateSet(String startDate, AlertSearchForm form, BindingResult result) {
        java.util.Date date = null;
        try {
            if(startDate == null || startDate.trim().length() == 0) return null;

            if(!startDate.contains("/")){
                ObjectError error = new ObjectError("login", messageSource.getMessage(ValidationMessageConst.DATE_FORMAT_EXCEPTION,null,null));
                result.addError(error);
            }else{
                if(startDate.length() != 10){
                    ObjectError error = new ObjectError("login", messageSource.getMessage(ValidationMessageConst.DATE_FORMAT_EXCEPTION,null,null));
                    result.addError(error);
                }else{
                    date = sdf.parse(startDate);
                }
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(MedicalMessageListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return date;
    }

    /**
     * 表示用に検査項目などをまとめるメソッド
     */
    private void disPlayList(AlertSearchForm form, List<ObservationAlertEntity> list) {
        String b_id = null;
        List<ObservationAlertEntity> resultList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");       
         for(ObservationAlertEntity target : list){
            ObservationAlertEntity entity = new ObservationAlertEntity();
            String id = target.getObservationEventId();
            
            if(b_id != null && id.equals(b_id)){
                String disName = entity.getDisplayName() +"<br/>" + target.getObservationDefinitionName();
                entity.setDisplayName(disName);
            }else{
                if(entity.getObservationEventId() != null) resultList.add(entity);
                entity = new ObservationAlertEntity();
                
                Date targetDate = new Date(target.getExaminationDate().getTime());
                String s_targetDate = sdf.format(targetDate);
                entity.setDisplayDate(s_targetDate);
                entity.setFamilyName(target.getFamilyName());
                entity.setGivenName(target.getGivenName());
                entity.setDisplayName(target.getObservationDefinitionName());
                entity.setViewName(target.getViewName());
                entity.setpHR_ID(target.getpHR_ID());
                entity.setAgreesToShare(target.isAgreesToShare());
                b_id = target.getObservationEventId();
                resultList.add(entity);
            }
        }

        form.setAlertList(resultList);
        
    }
}
