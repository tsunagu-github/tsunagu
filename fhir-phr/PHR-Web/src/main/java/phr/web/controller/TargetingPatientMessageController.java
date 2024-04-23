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
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalMessageReplayService;
import phr.service.ITargetingPatientInfoService;
import phr.service.ITargetingPatientMessageService;
import phr.service.impl.OnetimePasswordService.OneTimePasswordResult;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.dto.messageDto;
import phr.web.form.MedicalMessageReplayForm;
import phr.web.form.MedicalMessageSelectForm;
import phr.web.form.TargetingPatientMessageForm;

/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.TARGETING_PATIENT_MESSAGE_ACTION})

public class TargetingPatientMessageController extends HttpServlet{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(TargetingPatientMessageController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.TARGETING_PATIENT_MESSAGE_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.TARGETING_PATIENT_MESSAGE_FORM_TITLE;
    
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
     * インジェクション：ユーザ認証サービス
     */
    @Autowired
    @Qualifier("TargetingPatientInfoService")
    private ITargetingPatientInfoService targetingPatientInfoService;

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
   	TargetingPatientMessageForm form = new TargetingPatientMessageForm();

        initial(form);
        
        messageDto messageDto = (messageDto)sessionUtility.getSession(SessionConst.SEND_MESSAGE_DTO);
        if(messageDto != null && messageDto.isReturnflg()){
            form.setSubject(messageDto.getSubject());
            form.setBodytext(messageDto.getBodytext());
            
            //送信者が保険者
            if(messageDto.isTypeflg()){
                form.setHospitallist(messageDto.getMedicalLsit());
            }else{
                //送信者が医療機関
                form.setInsureflg(messageDto.isInsureFlg());
            }
            messageDto.setReturnflg(false);
            sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, messageDto);
        }
        
        
        List<MedicalOrganizationPatientEntity> medicalList = TargetingPatientMessageService.getMedicalList(form.getPhrid());
        
        form.setMedicalList(medicalList);
        
        this.initPage(model,form);
   	model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.TARGETING_PATIENT_MESSAGE_ACTION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model, TargetingPatientMessageForm form) throws Throwable {
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
    public String confirmAction(@ModelAttribute @Valid TargetingPatientMessageForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        //次に遷移するページ
        String actionPage = ActionConst.actionPage(ActionConst.TARGETING_PATIENT_MESSAGE_ACTION);
        
        if(form.getSubject()== null || form.getSubject().trim().length() == 0){
            ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.MESSAGE_NOSUBJECT,null,null));
            result.addError(error);
            initial(form);
            this.initPage(model,form);
            model.addAttribute(form);
            return actionPage;
        }
        
        
        //確認画面用のDtoを作成
        messageDto dto = new messageDto();
        dto.setSubject(form.getSubject());
        dto.setBodytext(form.getBodytext());

        AccountEntity account =(AccountEntity) sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity medicalEntity =(MedicalOrganizationEntity) sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
//        OneTimePasswordResult patientInfo =(OneTimePasswordResult) sessionUtility.getSession(SessionConst.VIEW_PASSWORD_INFO);
        MedicalOrganizationPatientEntity patientInfo=(MedicalOrganizationPatientEntity)sessionUtility.getSession(SessionConst.AGREE_PATIET_INFO);
        String phrId = patientInfo.getPHR_ID();
        PatientEntity patientEntity = targetingPatientInfoService.getPatientInfo(phrId);
        String name = patientEntity.getFamilyName() + " " + patientEntity.getGivenName();
        
        dto.setPhrid(phrId);
        dto.setName(name);
        dto.setReturnflg(false);
        //送信者が保険者の場合
        if(account != null){
            String medical = form.getParam1();
            List<MedicalOrganizationPatientEntity> targetList = new ArrayList<MedicalOrganizationPatientEntity>();
            if(medical != null && medical.length() > 0){
                String[] medicals = medical.split(",");

                List<MedicalOrganizationPatientEntity> medicalList = TargetingPatientMessageService.getMedicalList(phrId);

                for(MedicalOrganizationPatientEntity entity : medicalList){
                    for(int i=0 ; i<medicals.length;i++){
                        if(medicals[i].equals(entity.getMedicalOrganizationCd())){
                            targetList.add(entity);
                        }
                    }
                }
            }

            dto.setMedicalLsit(targetList);
            dto.setTypeflg(true);
            dto.setMedicalname(account.getName());
        }else{
            //送信者が医療機関
            dto.setInsureFlg(form.isInsureflg());
            dto.setTypeflg(false);
            dto.setMedicalCd(medicalEntity.getMedicalOrganizationCd());
            dto.setMedicalname(medicalEntity.getMedicalOrganizationName());
            
        }
 
        sessionUtility.setSession(SessionConst.SEND_MESSAGE_DTO, dto);

        actionPage = ActionConst.redirectActionPage(ActionConst.TARGETING_PATIENT_MESSAGE_CONFIRM_ACTION);

        return actionPage;
    }

    private void initial(TargetingPatientMessageForm form) throws Throwable{
        
//        OneTimePasswordResult patientInfo =(OneTimePasswordResult) sessionUtility.getSession(SessionConst.VIEW_PASSWORD_INFO);
        MedicalOrganizationPatientEntity patientInfo=(MedicalOrganizationPatientEntity)sessionUtility.getSession(SessionConst.AGREE_PATIET_INFO);
        String phrId = patientInfo.getPHR_ID();
        PatientEntity entity = targetingPatientInfoService.getPatientInfo(phrId);
        String name = entity.getFamilyName() + " " + entity.getGivenName();
        
        form.setPhrid(phrId);
        form.setName(name);

        AccountEntity account =(AccountEntity) sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity medicalEntity =(MedicalOrganizationEntity) sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        
        //送信者フラグ
        if(account != null){
            form.setTypeflg(true);
        }else{
            form.setTypeflg(false);
        }   

    }
}
