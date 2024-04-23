/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import phr.common.Calculate;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.adapter.AccountAdapter;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.service.IUserManageEditService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.dto.UserManageDto;
import phr.web.form.UserManageConfirmForm;
import phr.web.form.UserManageConfirmForm;

/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.USER_MANAGE_CONFIRM_ACTION})
public class UserManageConfirmController {
    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(TargetingPatientController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.USER_MANAGE_CONFIRM_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.USER_MANAGE_CONFIRM_FORM_TITLE;

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
    @Qualifier("UserManageEditService")
    private IUserManageEditService userManageEditService;
    
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
   	UserManageConfirmForm form = new UserManageConfirmForm();
        AccountEntity dto = (AccountEntity)sessionUtility.getSession(SessionConst.USER_INFO);
        
        model.addAttribute("AccountEntity",dto);
   	this.initPage(model,form);
   	model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.USER_MANAGE_CONFIRM_ACTION);
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
    public String returnAction(@ModelAttribute @Valid UserManageConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable{
        logger.debug("Start");
        
                
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.USER_MANAGE_EDIT_ACTION);
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
    public String returnListAction(@ModelAttribute @Valid UserManageConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable{
        logger.debug("Start");
        
                
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.USER_MANAGE_ACTION);
    }
    /**
     * 登録アクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=regist")
    public String registAction(@ModelAttribute @Valid UserManageConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable{
        
        //ユーザ登録情報作成
        AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.USER_INFO);
        Date today = new Date();
        Timestamp todaystamp = new Timestamp(today.getTime());
        boolean flg = true;
        if (entity.getAccountId() == null) {
            //accountID 自動採番
            String accountId = AccountAdapter.numberingAccountId();
            entity.setAccountId(accountId);
            
            flg = userManageEditService.registUser(entity, false);
        } else {
            flg = userManageEditService.registUser(entity, true);
        }
        
        form.setSuccessed(flg);
        form.setFailed(!flg);
   	model.addAttribute(form);
        model.addAttribute("AccountEntity",entity);

        this.initPage(model,form);
        return ActionConst.actionPage(ActionConst.USER_MANAGE_CONFIRM_ACTION);
    }
        
    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model,UserManageConfirmForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
