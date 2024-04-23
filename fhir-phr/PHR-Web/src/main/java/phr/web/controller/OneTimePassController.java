/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import phr.datadomain.entity.PatientEntity;
import phr.service.IAccessLogsService;
import phr.service.IPatientManagementService;
import phr.service.IOnetimePasswordService;
import phr.service.ITargetingPatientMessageService;
import phr.service.impl.OnetimePasswordService.OneTimePasswordResult;
import phr.service.impl.OnetimePasswordService.OnetimePasswordResultCd;
import phr.service.impl.PatientManagementService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.PhrWebConfig;
import phr.web.PhrWebConst;
import phr.web.SessionConst;
import phr.web.form.OneTimePassForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Controller
@RequestMapping({ActionConst.ONE_TIME_PASS, "/"})
public class OneTimePassController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OneTimePassController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.ONE_TIME_PASS_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.ONE_TIME_PASS_FORM_TITLE;
    
    
    @Autowired
    private MessageSource messageSource;
    
    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    
    /**
     * インジェクション：患者情報サービス
     */
    @Autowired
    @Qualifier("PatientManagementService")
   private IPatientManagementService patientManagementService;
    
    /**
     * インジェクション：ワンタイムパスワード発行サービス
     */
    @Autowired
    @Qualifier("OnetimePasswordService")
    private IOnetimePasswordService onetimePasswordService;    

    
    /**
     * インジェクション：お知らせ送信サービス
     */
    @Autowired
    @Qualifier("TargetingPatientMessageService")
    private ITargetingPatientMessageService targetingPatientMessageService;
 
    /**
     * インジェクション：アクセスログサービス
     */
    @Autowired
    @Qualifier("AccessLogsService")
    private IAccessLogsService accessLogsService;
    
    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param form
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(OneTimePassForm form, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        //画面初期化
        this.initPage(model, form);       
        model.addAttribute(form);
        logger.debug("End");

        return ActionConst.actionPage(ActionConst.ONE_TIME_PASS);
    }
    
    /**
     * <pre>患者ID指定ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=select")
    public String selectAction(@ModelAttribute @Valid OneTimePassForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
            AccountEntity ientity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
            String insurerNo = ientity.getInsurerNo();
            String phrId = form.getPatientId();
            String familyName = form.getFamilyName();
            String givenName = form.getGivenName();
            String familyKana = form.getFamilyKana();
            String givenKana = form.getGivenKana();
            List<PatientEntity> plist;
        try {           
            plist = patientManagementService.getPatientList(insurerNo, phrId, familyName, givenName, familyKana, givenKana);
            if (plist == null || plist.isEmpty()) {
                ObjectError error = new ObjectError("select", "条件に合致する患者が見つかりませんでした。");
                result.addError(error);
                form.setPatientFlg(false);
            }else{
                form.setPatientFlg(true);
                form.setPatientInfo(plist);
            }
        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.ONE_TIME_PASS);
    }
    
    /**
     * <pre>発行ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=getpassword")
    public String patientAction(@ModelAttribute @Valid  OneTimePassForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        String patientId = form.getParam1();
        boolean isPass = false;
       
        try {
            PatientEntity entity = new PatientEntity();
            if (patientId == null || patientId.isEmpty() ) {
                ObjectError error = new ObjectError("getpassword", "PHR-IDが取得できませんでした。");
                result.addError(error);
            }else{
                entity = patientManagementService.getPatient(patientId);
                if (entity == null) {
                    ObjectError error = new ObjectError("getpassword", "指定したPHR-IDから患者情報を取得できませんでした。");
                    result.addError(error);
                }                
            }
            if (!result.hasErrors()) {
             //ワンタイムパスワードの発行    
                OneTimePasswordResult passResult = this.onetimePasswordService.createOneTimePassword(patientId);
                if( passResult.getResultCd() == OnetimePasswordResultCd.SUCCCESS ){
                //　成功
                    form.setPatientId(patientId);
                    form.setOneTimePass(passResult.getPassword());
                    form.setExpirationDate( (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format( passResult.getExpirationDate() )).substring(0,16) ) ;
                    isPass = true;

                    // ワンタイムパスワード発行ログを出力する
                    passResult.setPHR_ID(patientId);
                    sessionUtility.setSession(SessionConst.VIEW_PASSWORD_INFO, passResult);
                    AccesslLogBackUpUtility.writeAccessLog(request, accessLogsService, "getpassword");
                    sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
                }else{
                //  失敗
                    ObjectError error = new ObjectError("getpassword", "ワンタイムパスワード生成エラーです。");
                    result.addError(error);
                    isPass = false;
                }
            //お知らせの発行
                if(isPass){
                   //患者向け 
                    //保険者情報
                    AccountEntity ientity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);           
                    //通知タイトルと本文
                    String subject =PhrWebConfig.getPhrWebProperty(PhrWebConst.ONETIME_PASS_SUBJECT); 
                    String text = PhrWebConfig.getPhrWebProperty(PhrWebConst.ONETIME_PASS_MESSAGE);
//                    String insurerName = noticeNewCreateService.insurerSerarch(ientity.getInsurerNo());
                    text = text.replaceAll("\\{0\\}", ientity.getName());
                    text = text.replaceAll("\\{1\\}", entity.getFamilyName() + " " + entity.getGivenName());
                    List<CommunicationReceiverEntity> rList = new ArrayList<CommunicationReceiverEntity>();
                    CommunicationReceiverEntity pent = getreciever();
                    pent.setPHR_ID(patientId);
                    rList.add(pent);
                    //Communicationにデータを登録
                    targetingPatientMessageService.submitMessage(getOshirase(ientity,subject,text), rList);
                    rList.clear();
                    
                    //自身向け
                    //通知タイトルと本文
                    String subject2 ="ワンタイムパスワード発行"; 
                    StringBuilder body = new StringBuilder();
                    body.append("━━━━━━━━━━━━━━━━━━━").append("\r\n");
                    body.append("PHR-ID").append("\r\n");
                    body.append("　").append(patientId).append("\r\n");
                    body.append("パスワード").append("\r\n");
                    body.append("　").append(passResult.getPassword()).append("\r\n");
                    body.append("有効期限").append("\r\n");
                    body.append("　").append(form.getExpirationDate()).append("\r\n");
                    body.append("━━━━━━━━━━━━━━━━━━━").append("\r\n");
                    /*
                    String text2 = entity.getFamilyName() + " " + entity.getGivenName() + "さんのワンタイムパスワードを発行しました。<br>" +
                                    "パスワード：" + passResult.getPassword() + " 有効期限：" + form.getExpirationDate();
                    */
                    pent = getreciever();
                    pent.setPHR_ID(null);
                    pent.setInsurerNo(ientity.getInsurerNo());
                    rList.add(pent);
                    //Communicationにデータを登録
                    targetingPatientMessageService.submitMessage(getOshirase(ientity,subject2,body.toString()), rList);
                    
                    
                    logger.info("保険者:" + ientity.getAccountId() + "が患者:" + patientId + " のワンタイムパスを発行しました。");
                }
                
            }
            form.setPasswordFlg(isPass);
        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }        
        
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        
        return ActionConst.actionPage(ActionConst.ONE_TIME_PASS);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, OneTimePassForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
        //権限の確認
        AccountEntity ientity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY); 
        if(ientity.getOneTimePassAuth()){
           form.setAuth(true);
        }else{
           form.setAuth(false);
        }        
    }
    
    private CommunicationEntity getOshirase(AccountEntity ientity,String subject,String text) throws Throwable{ 
        CommunicationEntity centity = new CommunicationEntity();
        
        //登録内容生成(Communication)
        //CommunicationTypeCd (2:メッセージ)
        centity.setCommunicationTypeCd(2);
        //SendPHRID
        centity.setSendPHRID(null);
        //SendInsurerNo
        centity.setSendInsurerNo(ientity.getInsurerNo());
        //SendAccountId
        centity.setSendAccountId(ientity.getAccountId());
        //SendMedcalOrganizationCd
        centity.setSendMedicalOrganizationCd(null);
        //SenderName
        centity.setSenderName(ientity.getName());
         //Subject
         centity.setSubject(subject);
         //BodyText
         centity.setBodyText(text);
         
         return centity;
    }
    
    private CommunicationReceiverEntity getreciever(){
        Date today = new  Date();
        Timestamp todaystamp = new Timestamp(today.getTime());
        
        CommunicationReceiverEntity pentity = new CommunicationReceiverEntity();
        pentity.setSeq(0);
        pentity.setReadFlg(false);
        pentity.setCreateDateTime(todaystamp);
        pentity.setUpdateDateTime(todaystamp);
        return pentity;
    } 
}
