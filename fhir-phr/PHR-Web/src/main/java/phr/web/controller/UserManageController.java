/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.service.IUserManageService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.dto.UserManageDto;
import phr.web.form.UserManageForm;

/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.USER_MANAGE_ACTION})
public class UserManageController {
    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(TargetingPatientController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.USER_MANAGE_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.USER_MANAGE_FORM_TITLE;

    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * インジェクション：ユーザ管理サービス
     */
    @Autowired
    @Qualifier("UserManageService")
    private IUserManageService userManageService;
    
    /*
    * 日付処理
    */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
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

        //ログインサイトを設定
        sessionUtility.setSession("SessionConst.SITE", null);
        
   	//画面初期化
   	UserManageForm form = new UserManageForm();
   	this.initPage(model,form);
   	model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.USER_MANAGE_ACTION);
    }
    
    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model,UserManageForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
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
    public String searchAction(UserManageForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        HttpSession session = request.getSession(true);
        AccountEntity account = (AccountEntity) session.getAttribute(SessionConst.ACCOUNT_ENTITY);
        String insurerNo = account.getInsurerNo();
        form.setLoginNo(insurerNo);
        
        try {
            if(!result.hasErrors()){
                List<AccountEntity> list = this.userManageService.userSearch(form.getLoginId(), form.getUserName(), insurerNo, form.getValidFlg());
                form.setUserList(list);
                if(list.isEmpty()){
                    ObjectError error = new ObjectError("search", "検索結果が0件でした。");
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
        
        return ActionConst.actionPage(ActionConst.USER_MANAGE_ACTION);
    }
    
    /**
     * 新規作成ボタン押下時アクション
     * @param form
     * @param result
     * @param model
     * @param request
     * @return 
     * @throws java.lang.Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=newCreate")
    public String newCreateAction(@ModelAttribute @Valid UserManageForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");
        
        AccountEntity entity = new AccountEntity();
        sessionUtility.setSession(SessionConst.USER_INFO, entity);

   	//画面初期化
   	this.initPage(model,form);
   	model.addAttribute(form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.USER_MANAGE_EDIT_ACTION);
    }
    
    
    @RequestMapping(method = {RequestMethod.POST }, params = "command=edit")
    public String editAction(@ModelAttribute @Valid UserManageForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");
        
        //パラメータ accountId 取得
        String loginId = request.getParameter("param1");
        //loginIdからユーザ情報取得
        AccountEntity entity = this.userManageService.userInfo(loginId);
        
        //Dtoに情報セット
        sessionUtility.setSession(SessionConst.USER_INFO, entity);

   	//画面初期化
   	this.initPage(model,form);
   	model.addAttribute(form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.USER_MANAGE_EDIT_ACTION);
    }
    
    /**
     * 削除ボタン押下時のアクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = {"command=delete"})
    public String deleteAction(UserManageForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        String actionPage=ActionConst.USER_MANAGE_ACTION;
        //パラメータ accountId 取得
        String accountId = request.getParameter("param1");
        //データ削除
        this.userManageService.userDelete(accountId);
        
        this.initPage(model,form);
   	model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.redirectActionPage(actionPage);
    }
}
