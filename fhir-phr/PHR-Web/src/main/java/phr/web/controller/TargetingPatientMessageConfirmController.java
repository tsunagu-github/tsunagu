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
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.service.IMedicalMessageReplayService;
import phr.service.ITargetingPatientMessageService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.dto.messageDto;
import phr.web.form.MedicalMessageReplayConfirmForm;
import phr.web.form.TargetingPatientMessageConfirmForm;

/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.TARGETING_PATIENT_MESSAGE_CONFIRM_ACTION})

public class TargetingPatientMessageConfirmController extends HttpServlet{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(TargetingPatientMessageConfirmController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.TARGETING_PATIENT_MESSAGE_CONFIRM_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.TARGETING_PATIENT_MESSAGE_CONFIRM_FORM_TITLE;
    
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
    @Qualifier("TargetingPatientMessageService")
    private ITargetingPatientMessageService TargetingPatientMessageService;

    
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
   	TargetingPatientMessageConfirmForm form = new TargetingPatientMessageConfirmForm();  
        form.setStatus(false);
        this.initPage(model,form);
   	model.addAttribute(form);
        
        messageDto messageDto = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        //表示用に改行コードを<br>に直す。
        messageDto.setBodytext(messageDto.getBodytext().replaceAll(System.getProperty("line.separator"), "<br>"));
        model.addAttribute("messageDto",messageDto);

        logger.debug("End");
        return ActionConst.actionPage(ActionConst.TARGETING_PATIENT_MESSAGE_CONFIRM_ACTION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model, TargetingPatientMessageConfirmForm form) throws Throwable {
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
    public String confirmAction(@ModelAttribute @Valid TargetingPatientMessageConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {

        messageDto messageDto = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        AccountEntity account = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity medicalEntity =(MedicalOrganizationEntity) sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);

        //PHRIDから保険者情報を取得
        String insureno = TargetingPatientMessageService.getInsurerPatientInfo(messageDto.getPhrid());

        //送信者情報作成
        CommunicationEntity entity = new CommunicationEntity();
        entity.setCommunicationTypeCd(2);
        //保険者が送信者
        if(messageDto.isTypeflg()){
            entity.setSendInsurerNo(insureno);
            entity.setSenderName(messageDto.getMedicalname());
            entity.setSendAccountId(account.getAccountId());
        }else{
            //医療機関が送信者
            entity.setSendMedicalOrganizationCd(messageDto.getMedicalCd());
            entity.setSenderName(messageDto.getMedicalname());
        }
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
        pentity.setPHR_ID(messageDto.getPhrid());
        pentity.setReadFlg(false);
        pentity.setCreateDateTime(todaystamp);
        pentity.setUpdateDateTime(todaystamp);
        rList.add(pentity);
        
        if(messageDto.isInsureFlg()){
            CommunicationReceiverEntity ientity = new CommunicationReceiverEntity();
            ientity.setSeq(1);
            ientity.setReadFlg(false);
            ientity.setCreateDateTime(todaystamp);
            ientity.setUpdateDateTime(todaystamp);
                        
            ientity.setInsurerNo(insureno);
            rList.add(ientity);
            
        }
        
        List<MedicalOrganizationPatientEntity> medicalList = messageDto.getMedicalLsit();
        if(medicalList != null && medicalList.size() > 0){
            int count = 1;
            for(MedicalOrganizationPatientEntity medical : medicalList){
                CommunicationReceiverEntity ientity = new CommunicationReceiverEntity();
                ientity.setSeq(count);
                ientity.setReadFlg(false);
                ientity.setCreateDateTime(todaystamp);
                ientity.setUpdateDateTime(todaystamp);
                ientity.setMedicalOrganizationCd(medical.getMedicalOrganizationCd());
                rList.add(ientity);
            }
        }
        
        TargetingPatientMessageService.submitMessage(entity, rList);
        
        form.setStatus(true);
        this.initPage(model,form);
   	model.addAttribute(form);
        String actionPage = ActionConst.actionPage(ActionConst.TARGETING_PATIENT_MESSAGE_CONFIRM_ACTION);

        return actionPage;      
        
    }
    
     /**
     * <pre>戻るアクションメソッド</pre>
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=return")
    public String returnAction(@ModelAttribute @Valid TargetingPatientMessageConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        
        messageDto messageDto = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        //表示用<br>を改行コードに直す。
        messageDto.setBodytext(messageDto.getBodytext().replaceAll("<br>",System.getProperty("line.separator")));

        messageDto.setReturnflg(true);
        sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, messageDto);
        
        String actionPage = ActionConst.redirectActionPage(ActionConst.TARGETING_PATIENT_MESSAGE_ACTION);

        return actionPage;      
    }
}