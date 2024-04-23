/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.CommunicationEntity;
import phr.service.INoticeDetailService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.form.NoticeDetailForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.NOTICE_DETAIL_ACTION})
public class NoticeDetailController {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NoticeDetailController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.NOTICE_DETAIL_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.NOTICE_DETAIL_FORM_TITLE;
    
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
     * インジェクション：おしらせ詳細検索サービス
     */
    @Autowired
    @Qualifier("NoticeDetailService")
    private INoticeDetailService noticeDetailService;
        
    /**
     * <pre>初期アクションメソッド</pre>
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST })
    public String defaultAction(Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");
        
        HttpSession session = request.getSession(true);
        AccountEntity account = (AccountEntity) session.getAttribute(SessionConst.ACCOUNT_ENTITY);
        String accountName = account.getName();
        String insurerNo = account.getInsurerNo();

   	//画面初期化
   	NoticeDetailForm form = new NoticeDetailForm();
        form.setStatus(false);
        String comId = request.getParameter("param1");
        
        String insurerName = this.noticeDetailService.insurerSerarch(insurerNo);
        form.setAccountName(accountName);
        form.setInsurerName(insurerName);
        
        // おしらせ情報をセットする
        CommunicationEntity list = this.noticeDetailService.noticeDetailSearch(comId);
        form.setNoticeDetailList(list);
        
        this.initPage(model,form);
   	model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.NOTICE_DETAIL_ACTION);
    }
    
    /**
     * <pre>削除ボタン押下時のアクションメソッド</pre>
     * @param form
     * @param result
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = {"command=delete"})
    public String deleteAction(NoticeDetailForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");
        
        String actionPage=ActionConst.NOTICE_DETAIL_ACTION;
        //パラメータ communicationId 取得
        String comId = request.getParameter("param1");
        //データ削除
        this.noticeDetailService.noticeDelete(comId);
        
        this.initPage(model,form);
   	model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.redirectActionPage(actionPage);
    }
    
    /**
     * <pre>削除完了後アクションメソッド</pre>
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET })
    public String returnAction(Model model,HttpServletRequest request) throws Throwable {
   	//画面初期化
   	NoticeDetailForm form = new NoticeDetailForm();
        form.setStatus(true);
        this.initPage(model,form);
   	model.addAttribute(form);
        
        return ActionConst.actionPage(ActionConst.NOTICE_DETAIL_ACTION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, NoticeDetailForm form)  throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
