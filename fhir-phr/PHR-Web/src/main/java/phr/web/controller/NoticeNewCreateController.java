/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.ArrayList;
import java.util.List;
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
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.service.INoticeNewCreateService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.form.NoticeNewCreateForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.NOTICE_NEWCREATE_ACTION})
public class NoticeNewCreateController {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NoticeDetailController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.NOTICE_NEWCREATE_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.NOTICE_NEWCREATE_FORM_TITLE;
    
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
    @Qualifier("NoticeNewCreateService")
    private INoticeNewCreateService noticeNewCreateService;
        
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
   	NoticeNewCreateForm form = new NoticeNewCreateForm();
        form.setStatus(false);
        
        String insurerName = this.noticeNewCreateService.insurerSerarch(insurerNo);
        form.setAccountName(accountName);
        form.setInsurerName(insurerName);
        
        this.initPage(model,form);
   	model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.NOTICE_NEWCREATE_ACTION);
    }
    
    /**
     * <pre>送信ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = {"command=insert"})
    public String insertAction(NoticeNewCreateForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        String actionPage=ActionConst.NOTICE_NEWCREATE_ACTION;

        //アカウント情報の取得
        HttpSession session = request.getSession(true);
        AccountEntity account = (AccountEntity) session.getAttribute(SessionConst.ACCOUNT_ENTITY);
        String insurerNo = account.getInsurerNo();
        String accountId = account.getAccountId();
        String senderName = account.getName();
        //CommunicationIdの採番
        Long nextComId = Long.parseLong(this.noticeNewCreateService.maxCommunicationIdSearch()) + 1;
        String communicationId = String.format("%015d", nextComId);
//        String communicationId = this.noticeNewCreateService.maxCommunicationIdSearch();
        
        //登録内容生成(Communication)
        CommunicationEntity entityCommunication = new CommunicationEntity();
        //CommunicationId max取得
        entityCommunication.setCommunicationId(communicationId);
        //CommunicationTypeCd (1:おしらせ)
        entityCommunication.setCommunicationTypeCd(1);
        //SendPHRID
        entityCommunication.setSendPHRID(null);
        //SendInsurerNo
        entityCommunication.setSendInsurerNo(insurerNo);
        //SendAccountId
        entityCommunication.setSendAccountId(accountId);
        //SendMedcalOrganizationCd
        entityCommunication.setSendMedicalOrganizationCd(null);
        //SenderName
        entityCommunication.setSenderName(senderName);
        //Subject
        entityCommunication.setSubject(form.getSubject());
        //BodyText
        entityCommunication.setBodyText(form.getText());
        
        //Communicationにデータを登録
        this.noticeNewCreateService.insertCommunication(entityCommunication);
        
        //保険者に登録されている患者数とPHR_IDを取得
        List<InsurerPatientEntity> phrIdList = this.noticeNewCreateService.serchPhrId(insurerNo);
        //登録内容格納用リスト生成(CommunicationReceiver)
        List<CommunicationReceiverEntity> entityComRecList = new ArrayList<>();
        
        for (int i = 0; i < phrIdList.size(); i++) {
            CommunicationReceiverEntity entityComRec = new CommunicationReceiverEntity();
            //CommunicationId max取得
            entityComRec.setCommunicationId(communicationId);
            //Seq 連番1より保険者の患者数分
            entityComRec.setSeq(i);
            //ReadFlg
            entityComRec.setReadFlg(false);
            //PHR_ID
            entityComRec.setPHR_ID(phrIdList.get(i).getPHR_ID());
            //InsurerNo null?
            entityComRec.setInsurerNo("");
            //MedcalOrganizationCd null?
            entityComRec.setMedicalOrganizationCd("");
            
            entityComRecList.add(entityComRec);
        }
        
        //CommunicationReceiverにデータを登録
        this.noticeNewCreateService.insertCommunicationReceiver(entityComRecList);
        
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        
        return ActionConst.redirectActionPage(actionPage);
    }
    
    /**
     * <pre>登録完了後アクションメソッド</pre>
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET })
    public String returnAction(Model model,HttpServletRequest request) throws Throwable {
   	//画面初期化
   	NoticeNewCreateForm form = new NoticeNewCreateForm();
        form.setStatus(true);
        this.initPage(model,form);
   	model.addAttribute(form);
        
        return ActionConst.actionPage(ActionConst.NOTICE_NEWCREATE_ACTION);
    }
    
        /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, NoticeNewCreateForm form)  throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
