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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.CommunicationEntity;
import phr.service.IMessageListService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.form.MessageListForm;

/**
 *メッセージ一覧画面コントローラ
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.MESSAGE_LIST_ACTION})
public class MessageListController {
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MessageListController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MESSAGE_LIST_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MESSAGE_LIST_FORM_TITLE;
    
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
     * インジェクション：メッセージ一覧機能サービス
     */
    @Autowired
    @Qualifier("MessageListService")
    private IMessageListService messageListService;
    
    /*
    * 日付処理
    */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");    /**
    /*
    * 日付処理
    */
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");    /**

     /**<pre>初期アクションメソッド</pre>
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        MessageListForm form = new MessageListForm();
        //画面初期化
        this.initPage(model, form);
        form.readFlg = true;
        
        model.addAttribute(form);
        logger.debug("End");
        
        return ActionConst.actionPage(ActionConst.MESSAGE_LIST_ACTION);
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
    public String searchAction(MessageListForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        
        try {
            Date startDate = DateSet(form.getStartDate(),form, result);
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
                    List<CommunicationEntity> list =
                            this.messageListService.messageSearch(form.getPhrId(), startDate, endDate, !form.getReadFlg(), entity.getInsurerNo());
                    form.setMessageList(list);
                    if(list.size() == 0){
                        ObjectError error = new ObjectError("search", "検索結果が0件でした。");
                        result.addError(error);
                    }
                }else if(startDate != null && endDate != null && endDate.after(startDate)){
                    List<CommunicationEntity> list =
                            this.messageListService.messageSearch(form.getPhrId(), startDate, endDate, !form.getReadFlg(), entity.getInsurerNo());
                    form.setMessageList(list);
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
        
        return ActionConst.actionPage(ActionConst.MESSAGE_LIST_ACTION);
    }
    
    /**
     * 選択ボタン押下時のアクションメソッド
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=messageSelect")
    public String messageSelectOpen(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
   	
        //画面初期化
        MessageListForm form = new MessageListForm();
   	this.initPage(model,form);
   	model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.MESSAGE_SELECT_ACTION);
    } 
    /**
     * <pre>画面表示項目を設定</pre>
     *ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model, MessageListForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    /**
     * <pre>日付表示を設定</pre>
     * @param startDate
     * @param form
     * @param result
     * @return 
     */
    private java.util.Date DateSet(String startDate, MessageListForm form, BindingResult result) {
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
            Logger.getLogger(MessageListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return date;
    }
}
