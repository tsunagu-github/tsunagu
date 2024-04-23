/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServlet;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.service.IMessageReplyService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.dto.messageDto;
import phr.web.form.MessageReplayConfirmForm;

/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.MESSAGE_REPLAY_CONFIRM_ACTION})

public class MessageReplayConfirmController extends HttpServlet{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MessageReplayConfirmController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MESSAGE_REPLAY_CONFIRM_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MESSAGE_REPLAY_CONFIRM_FORM_TITLE;
    
    @Autowired
    private MessageSource messageSource;
    
    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * インジェクション：メッセージ返信サービス
     */
    @Autowired
    @Qualifier("MessageReplyService")
    private IMessageReplyService messageReplyService;

    
    /**
     * <pre>初期アクションメソッド</pre>
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        HttpSession session = request.getSession(true);
        
        //画面初期化
   	MessageReplayConfirmForm form = new MessageReplayConfirmForm();  
        form.setStatus(false);
        this.initPage(model,form);
   	model.addAttribute(form);
        
        messageDto messageDto = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        //表示用に改行コードを<br>に直す。
        messageDto.setBodytext(messageDto.getBodytext().replaceAll(System.getProperty("line.separator"), "<br>"));
        model.addAttribute("messageDto",messageDto);

        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MESSAGE_REPLAY_CONFIRM_ACTION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model, MessageReplayConfirmForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    /**
     * <pre>確認アクションメソッド</pre>
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=entry")
    public String confirmAction(@ModelAttribute @Valid MessageReplayConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {

        messageDto messageDto = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        
        AccountEntity aentity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);

        //送信者情報作成
        CommunicationEntity entity = new CommunicationEntity();
        entity.setCommunicationTypeCd(2);
        entity.setSendInsurerNo(aentity.getInsurerNo());
        entity.setSenderName(aentity.getName());
        entity.setSubject(messageDto.getSubject());
        //表示用<br>を改行コードに直す。
        messageDto.setBodytext(messageDto.getBodytext().replaceAll("<br>",System.getProperty("line.separator")));
        entity.setBodyText(messageDto.getBodytext());
        Date today = new  Date();
        Timestamp todaystamp = new Timestamp(today.getTime());
        entity.setCreateDateTime(todaystamp);
        entity.setUpdateDateTime(todaystamp);
        
        //受信者情報作成
        List<CommunicationReceiverEntity> rList = new ArrayList<CommunicationReceiverEntity>();
        //患者
        CommunicationReceiverEntity pentity = new CommunicationReceiverEntity();
        pentity.setSeq(0);
        if (messageDto.getPhrid() != null) {
            pentity.setPHR_ID(messageDto.getPhrid());
            pentity.setMedicalOrganizationCd("");
        } else if (messageDto.getMedicalCd() != null) {
            pentity.setMedicalOrganizationCd(messageDto.getMedicalCd());
            pentity.setPHR_ID("");
        }
        pentity.setReadFlg(false);
        pentity.setCreateDateTime(todaystamp);
        pentity.setUpdateDateTime(todaystamp);
        pentity.setInsurerNo("");
        rList.add(pentity);
        
        // 返信先が患者で医療機関を選択した際のデータ生成
        if (messageDto.getMedicalLsit() != null) {
            for (int i = 0; i < messageDto.getMedicalLsit().size(); i++) {
                CommunicationReceiverEntity ientity = new CommunicationReceiverEntity();
                ientity.setSeq(i+1);
                ientity.setReadFlg(false);
                ientity.setCreateDateTime(todaystamp);
                ientity.setUpdateDateTime(todaystamp);
                ientity.setPHR_ID("");
                ientity.setMedicalOrganizationCd(messageDto.getMedicalLsit().get(i).getMedicalOrganizationCd());
                ientity.setInsurerNo("");

                rList.add(ientity);
            }
        }
        
        messageReplyService.submitMessage(entity, rList);
        
        form.setStatus(true);
        this.initPage(model,form);
   	model.addAttribute(form);
        String actionPage = ActionConst.actionPage(ActionConst.MESSAGE_REPLAY_CONFIRM_ACTION);

        return actionPage;

        
        
    }
    
    /**
     * <pre>前画面遷移アクションメソッド</pre>
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=return")
    public String returnAction(@ModelAttribute @Valid MessageReplayConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        
        messageDto messageDto = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);

        messageDto.setReturnflg(true);
        //表示用<br>を改行コードに直す。
        messageDto.setBodytext(messageDto.getBodytext().replaceAll("<br>",System.getProperty("line.separator")));
        sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, messageDto);
        
        String actionPage = ActionConst.redirectActionPage(ActionConst.MESSAGE_REPLAY_ACTION);
        
        return actionPage;
    }
}
