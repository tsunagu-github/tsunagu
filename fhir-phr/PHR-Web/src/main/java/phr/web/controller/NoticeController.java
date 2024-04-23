/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import phr.datadomain.entity.CommunicationEntity;
import phr.service.INoticeService;
import phr.service.IUserAuthenticationService;
import phr.service.impl.ReminderPushService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.form.MedicalMessageListForm;
import phr.web.form.NoticeForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.NOTICE_ACTION, "/"})
public class NoticeController {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NoticeController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.CONSENT_NOTIFICATION_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.CONSENT_NOTIFICATION_TITLE;
    
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
    @Qualifier("NoticeService")
    private INoticeService noticeService;
    
    @Autowired
    private MessageSource messageSource;
    
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
    public String defaultAction(NoticeForm form, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //ログインサイトを設定
        sessionUtility.setSession("SessionConst.SITE", null);

        //画面初期化
        this.initPage(model, form);
        
        model.addAttribute(form);
        logger.debug("End");

        return ActionConst.actionPage(ActionConst.NOTICE_ACTION);
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
    public String searchAction(NoticeForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        
        logger.debug("Start");
        
        HttpSession session = request.getSession(true);
        AccountEntity account = (AccountEntity) session.getAttribute(SessionConst.ACCOUNT_ENTITY);
        String insurerNo = account.getInsurerNo();
        
        try {
            Date startDate = DateSet(form.getStartDate(), form, result);
            Date endDate = DateSet(form.getEndDate(), form, result);
            
            if(startDate != null){
                Date date = new Date();
                if(date.before(startDate)){
                    ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.JAVAX_VALIDATION_CONSTRAINTS_PAST_MESSAGE,null,null));
                    result.addError(error);
                }
            }
            
            if(!result.hasErrors()){
                if(startDate == null || endDate == null){
                    List<CommunicationEntity> list = this.noticeService.noticeSearch(startDate, endDate, insurerNo);
                    form.setNoticeList(list);
                    if(list.size() == 0){
                        ObjectError error = new ObjectError("search", "検索結果が0件でした。");
                        result.addError(error);
                    }
                }else if(startDate != null && endDate != null && !startDate.after(endDate)){
                    List<CommunicationEntity> list = this.noticeService.noticeSearch(startDate, endDate, insurerNo);
                    form.setNoticeList(list);
                    if(list.size() == 0){
                        ObjectError error = new ObjectError("search", "検索結果が0件でした。");
                        result.addError(error);
                    }
                }else{
                    ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.DATE_DIFFERANCE_EXCEPTION,null,null));
                    result.addError(error);
                }
            }

        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        this.initPage(model, form);
        model.addAttribute(form);
        
        return ActionConst.actionPage(ActionConst.NOTICE_ACTION);
    }
    
    /**
     * <pre>選択ボタン押下時のアクションメソッド</pre>
     *
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = {"command=move"})
    public String moveAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        String actionPage=ActionConst.NOTICE_DETAIL_ACTION;
        
        NoticeForm form = new NoticeForm();
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        
        return ActionConst.redirectActionPage(actionPage);
    }
    
    /**
     * <pre>新規作成ボタン押下時のアクションメソッド</pre>
     *
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = {"command=create"})
    public String createAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        String actionPage=ActionConst.NOTICE_NEWCREATE_ACTION;
        
        NoticeForm form = new NoticeForm();
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
    private void initPage (Model model, NoticeForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }

    private java.util.Date DateSet(String startDate, NoticeForm form, BindingResult result) {
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
    
}
