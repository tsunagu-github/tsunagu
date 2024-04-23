/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.form.TargetingPatientMsgViewForm;

/**
 *
 * @author KISNOTE011
 */
@Controller
@RequestMapping({ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION})
public class TargetingPatientMsgViewController {

    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(TargetingPatientMsgViewController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.TARGETING_PATIENT_MSG_VIEW_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.TARGETING_PATIENT_MSG_VIEW_FORM_TITLE;

//    @Autowired
//    private MessageSource messageSource;

//    /**
//     * インジェクション：セッション保持サービス
//     */
//    @Autowired
//    @Qualifier("SessionUtility")
//    private ISessionUtility sessionUtility;
    
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
    	TargetingPatientMsgViewForm form = new TargetingPatientMsgViewForm();
    	this.initPage(form);
        form.setInformation("入力されたワンタイムパスワードは有効ではありません");
    	model.addAttribute(form);

        logger.debug("End");
        return ActionConst.actionPage(ActionConst.TARGETING_PATIENT_MSG_VIEW_ACTION);
    }

    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(TargetingPatientMsgViewForm form) throws Throwable {
//    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
