/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.form.PasswordChangeConfirmForm;

/**
 *
 * @author KISNOTE011
 */
@Controller
@RequestMapping(ActionConst.PASSWORD_CHANGE_CONFIRM_ACTION)
public class PasswordChangeConfirmController {

    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(PasswordChangeConfirmController.class);

//    @Autowired
//    private MessageSource messageSource;

    /**画面ID */
    private final String FORM_CD = FormConst.PASSWORD_CHANGE_CONFIRM_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.PASSWORD_CHANGE_CONFIRM_FORM_TITLE;

    /**
     * インジェクション：セッションサービス
     */
//    @Autowired
//    @Qualifier("SessionUtility")
//    private ISessionUtility sessionUtility;

    /**
     * 初期アクションメソッド
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET })
    public String defaultAction(Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");

//    	AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
    	PasswordChangeConfirmForm form = new PasswordChangeConfirmForm();
    	this.initPage(model,form);
//    	form.setUserId(entity.getLoingId());
    	model.addAttribute(form);

        logger.debug("End");
        return ActionConst.actionPage(ActionConst.PASSWORD_CHANGE_CONFIRM_ACTION);
    }

    /**
     * ページの初期化処理
     * @param model
     */
    private void initPage(Model model,PasswordChangeConfirmForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
