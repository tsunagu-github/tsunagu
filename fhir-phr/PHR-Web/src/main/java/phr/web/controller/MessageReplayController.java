/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.ArrayList;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMessageReplyService;
import phr.service.impl.MessageReplyService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.dto.messageDto;
import phr.web.form.MessageReplayForm;


/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.MESSAGE_REPLAY_ACTION})

public class MessageReplayController extends HttpServlet{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MessageReplayController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MESSAGE_REPLY_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MESSAGE_REPLY_FORM_TITLE;
    
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
        MessageReplayForm form = new MessageReplayForm();
                
        //メッセージ情報をセットする
        CommunicationEntity centity = (CommunicationEntity) sessionUtility.getSession(SessionConst.RECIEVE_COMMUNITY_INFO);
        form.setMessageDetail(centity);
        
        //保険者情報をセットする
        AccountEntity ientity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        String accountName = ientity.getName();
        String insurerNo = ientity.getInsurerNo();
        String insurerName = this.messageReplyService.insurerSerarch(insurerNo);
        form.setAccountName(accountName);
        form.setInsurerName(insurerName);
        
        //宛先をセットする
        if (centity.getSenderStatus() == 2) {
            form.setPhrid(centity.getRecPhrId());
            form.setPhrname(centity.getRecPhrName());
            form.setMedicalorganizationcd(centity.getSendMedicalOrganizationCd());
            form.setMedname(centity.getSenderName());
            System.out.println(centity.getRecPhrName());
        } else if(centity.getSenderStatus() == 3) {
            form.setPhrid(centity.getSendPHRID());
            form.setPhrname(centity.getSenderName());
            System.out.println(centity.getSenderName());
        }
        
        form.setSubject("Re:" + centity.getSubject());

        messageDto sendDto = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        if (sendDto == null) {
            messageDto dto = new messageDto();
            dto.setPhrid(form.getPhrid());
            dto.setName(form.getPhrname());
            dto.setMedicalname(form.getMedname());
            dto.setMedicalCd(form.getMedicalorganizationcd());
            dto.setSubject("Re:" + centity.getSubject());

            if (dto.getMedicalCd() != null) {
                MedicalOrganizationPatientEntity entity = new MedicalOrganizationPatientEntity();
                entity.setMedicalOrganizationCd(dto.getMedicalCd());
                entity.setMedicalOrganizationName(dto.getMedicalname());
                List<MedicalOrganizationPatientEntity> entList = new ArrayList<MedicalOrganizationPatientEntity>();
                entList.add(entity);
                form.setHospitallist(entList);
            }
            
            sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, dto);
        }
        if(sendDto != null && sendDto.isReturnflg()){
            form.setSubject(sendDto.getSubject());
            form.setBodytext(sendDto.getBodytext());
            form.setMedicalList(sendDto.getMedicalLsit());
            form.setHospitallist(sendDto.getMedicalLsit());
            
            sendDto.setReturnflg(false);
            sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, sendDto);
        }
        
        if(centity.getSenderStatus() != 1) {
            List<MedicalOrganizationPatientEntity> medicalList = messageReplyService.getMedicalList(form.getPhrid());
            form.setMedicalList(medicalList);
        }

        this.initPage(model,form);
   	model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MESSAGE_REPLAY_ACTION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model, MessageReplayForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
     /**
     * <pre>確認アクションメソッド</pre>
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=confirm")
    public String confirmAction(@ModelAttribute @Valid MessageReplayForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {

        //次に遷移するページ
        String actionPage = ActionConst.actionPage(ActionConst.MESSAGE_REPLAY_ACTION);
        
        if(form.getTitle() == null || form.getTitle().trim().length() == 0){
            ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.MESSAGE_NOSUBJECT,null,null));
            result.addError(error);
            InsurerEntity ientity = (InsurerEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
            form.setName(ientity.getInsurerName());
            this.initPage(model,form);
            model.addAttribute(form);
            return actionPage;
        }
        
        //確認画面用のDtoを作成
        messageDto dto =(messageDto) sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        CommunicationEntity entity = form.getMessageDetail();
        dto.setSubject(form.getTitle());
        dto.setBodytext(form.getBodytext());
        
        CommunicationEntity centity = (CommunicationEntity) sessionUtility.getSession(SessionConst.RECIEVE_COMMUNITY_INFO);
        
        if (centity.getSenderStatus() != 1) {
            String medical = form.getParam1();
            List<MedicalOrganizationPatientEntity> targetList = new ArrayList<MedicalOrganizationPatientEntity>();
            if(medical != null && medical.length() > 0){
                String[] medicals = medical.split(",");

                List<MedicalOrganizationPatientEntity> medicalList = this.messageReplyService.getMedicalList(dto.getPhrid());

                for(MedicalOrganizationPatientEntity mentity : medicalList){
                    for(int i=0 ; i<medicals.length;i++){
                        if(medicals[i].equals(mentity.getMedicalOrganizationCd())){
                            targetList.add(mentity);
                        }
                    }
                }
            }
            dto.setMedicalLsit(targetList);
        }
 
        sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, dto);

        actionPage = ActionConst.redirectActionPage(ActionConst.MESSAGE_REPLAY_CONFIRM_ACTION);

        return actionPage;
    }
}
