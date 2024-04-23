/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalMessageReplayService;
import phr.service.IMedicalMessageSelectService;
import phr.service.IMessageSelectService;
import phr.service.impl.MessageSelectService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.form.InsurerLoginForm;
import phr.web.form.MedicalMessageSelectForm;
import phr.web.form.MessageSelectForm;

/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.MEDICAL_MESSAGE_SELECT_ACTION})

public class MedicalMessageSelectController extends HttpServlet{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalMessageSelectController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MEDICAL_MESSAGE_SELECT_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MEDICAL_MESSAGE_SELECT_FORM_TITLE;
    
    @Autowired
    private MessageSource messageSource;
    
    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * インジェクション：メッセージ情報サービス
     */
    @Autowired
    @Qualifier("MedicalMessageSelectService")
    private IMedicalMessageSelectService MedicalMessageSelectService;
    
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
    @RequestMapping(method = {RequestMethod.POST})
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        HttpSession session = request.getSession(true);
        
        //画面初期化
   	MedicalMessageSelectForm form = new MedicalMessageSelectForm();
        String comId = request.getParameter("cid");
        String flg = request.getParameter("flg");
        
        MedicalOrganizationEntity mentity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        
        //メッセージ情報をセットする
        CommunicationEntity centity = this.MedicalMessageSelectService.getMessageDetail(comId,mentity.getMedicalOrganizationCd());
        if(centity.getSendInsurerNo() != null && centity.getSendInsurerNo().trim().length() > 0){
            centity.setSenderStatus(1);
        }else if(centity.getSendMedicalOrganizationCd() != null && centity.getSendMedicalOrganizationCd().trim().length() > 0){
            centity.setSenderStatus(2);
        }else{
            centity.setSenderStatus(3);
        }
        //表示用に改行コードを<br>に直す。
        centity.setBodyText(centity.getBodyText().replaceAll(System.getProperty("line.separator"), "<br>"));
        sessionUtility.setSession(SessionConst.RECIEVE_COMMUNITY_INFO, centity);
        form.setMessageDetail(centity);
        
                //患者情報取得
        String patientId = null;
        String patientName = null;
        
        if(centity.getSendPHRID() != null && centity.getSendPHRID().trim().length() > 0){
            patientId = centity.getPatientId();
        }else if(centity.getSendMedicalOrganizationCd() != null && centity.getSendMedicalOrganizationCd().trim().length() > 0){
            patientId = centity.getSendMedicalOrganizationCd();
        }else{
            patientId = centity.getInsurerNo();
        }
        patientName = centity.getSenderName();
        
//        form.setName(mentity.getMedicalOrganizationName());
        form.setPatientId(patientId);
        form.setPatientName(patientName);

        //未読を既読に変更する
        if(flg.equals("false")){
            MedicalMessageSelectService.changeReadFlg(comId, mentity.getMedicalOrganizationCd());
        }          
        this.initPage(model,form);
   	model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MEDICAL_MESSAGE_SELECT_ACTION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model, MedicalMessageSelectForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
     /**
     * <pre>ログインボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=replay")
    public String replayAction(@ModelAttribute @Valid MedicalMessageSelectForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //表示用<br>を改行コードに直す。
        CommunicationEntity centity=(CommunicationEntity)sessionUtility.getSession(SessionConst.RECIEVE_COMMUNITY_INFO);
        centity.setBodyText(centity.getBodyText().replaceAll("<br>",System.getProperty("line.separator")));
        sessionUtility.setSession(SessionConst.RECIEVE_COMMUNITY_INFO, centity);
        //次に遷移するページ
        String actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_MESSAGE_REPLAY_ACTION);


        return actionPage;
    }
}
