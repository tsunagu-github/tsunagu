/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalMessageReplayService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.dto.messageDto;
import phr.web.form.MedicalMessageReplayForm;
import phr.web.form.MedicalMessageSelectForm;

/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.MEDICAL_MESSAGE_REPLAY_ACTION})

public class MedicalMessageReplayController extends HttpServlet{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalMessageReplayController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MEDICAL_MESSAGE_REPLAY_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MEDICAL_MESSAGE_REPLAY_FORM_TITLE;
    
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
    @Qualifier("MedicalMessageReplayService")
    private IMedicalMessageReplayService MedicalMessageReplayService;

    
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
   	MedicalMessageReplayForm form = new MedicalMessageReplayForm();
        
        //メッセージ情報をセットする
        CommunicationEntity centity = (CommunicationEntity) sessionUtility.getSession(SessionConst.RECIEVE_COMMUNITY_INFO);
        form.setMessageDetail(centity);

        MedicalOrganizationEntity mentity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        form.setName(mentity.getMedicalOrganizationName());
        
        String patientId = null;
        String phrid = null;
        
        if(centity.getSendPHRID() != null && centity.getSendPHRID().trim().length() > 0){
            patientId = centity.getPatientId();
            phrid = centity.getSendPHRID();
            form.setPhrname(centity.getSenderName());
            System.out.println(centity.getSenderName());
        }else{
            PatientEntity pentity = MedicalMessageReplayService.getPatient(centity.getCommunicationId());
            patientId = MedicalMessageReplayService.getPatientId(pentity.getPHR_ID(), mentity.getMedicalOrganizationCd());
            phrid = pentity.getPHR_ID();
            form.setPhrid(pentity.getPHR_ID());
            form.setPhrname(pentity.getFamilyName() + " " + pentity.getGivenName());
        }

        messageDto message = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        form.setInsureflg(false);
        if(message != null && message.isReturnflg()){
            form.setTitle(message.getSubject());
            form.setBodytext(message.getBodytext());
            form.setInsureflg(message.isInsureFlg());
        }


        form.setPhrid(patientId);
        
        messageDto dto = new messageDto();
        dto.setPhrid(phrid);
        dto.setPatientId(patientId);
        dto.setName(form.getPhrname());
        dto.setMedicalname(form.getName());
        dto.setMedicalCd(mentity.getMedicalOrganizationCd());
        dto.setReturnflg(false);
        sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, dto);
        
        
        
        this.initPage(model,form);
   	model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MEDICAL_MESSAGE_REPLAY_ACTION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model, MedicalMessageReplayForm form) throws Throwable {
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
    @RequestMapping(method = {RequestMethod.POST}, params = "command=confirm")
    public String confirmAction(@ModelAttribute @Valid MedicalMessageReplayForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {

        //次に遷移するページ
        String actionPage = ActionConst.actionPage(ActionConst.MEDICAL_MESSAGE_REPLAY_ACTION);
        
        if(form.getTitle() == null || form.getTitle().trim().length() == 0){
            ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.MESSAGE_NOSUBJECT,null,null));
            result.addError(error);
            MedicalOrganizationEntity mentity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
            form.setName(mentity.getMedicalOrganizationName());
            this.initPage(model,form);
            model.addAttribute(form);
            return actionPage;
        }
        
        //内容の文字数制限は必要？
        
        //確認画面用のDtoを作成
        messageDto dto =(messageDto) sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        CommunicationEntity entity = form.getMessageDetail();
        dto.setInsureFlg(form.isInsureflg());
        dto.setSubject(form.getTitle());
        dto.setBodytext(form.getBodytext());
 
        sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, dto);

        actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_MESSAGE_REPLAY_CONFIRM_ACTION);

        return actionPage;
    }
}
