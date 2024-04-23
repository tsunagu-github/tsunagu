/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.AbstractAction;
import javax.validation.Valid;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.ReminderMessageEntity;
import phr.datadomain.entity.ReminderTargetPhrIdItemEntity;
import phr.datadomain.entity.StudyDetailedInformationEntity;
import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.UtilizationConsentEntity;
import phr.dto.YearListDto;
import phr.dto.YearViewListDto;
import phr.service.ICommunicationService;
import phr.service.IManagementItemService;
import phr.service.IMedicalKensaEntryService;
import phr.service.IMedicalOrganizationPatientService;
import phr.service.IPatientManagementService;
import phr.service.IReminderPushService;
import phr.service.IStudyInformationService;
import phr.service.IUtilizationConsentService;
import phr.service.impl.CommunicationService;
import phr.service.impl.ReminderPushService;
import phr.service.impl.StudyDetailedInformationService;
import phr.service.impl.StudyInformationService;
import phr.service.impl.UtilizationConsentService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.dto.ReminderInfoDto;
import phr.web.form.ConsentNotificationForm;
import phr.web.form.ManagementItemReminderForm;
import phr.web.form.TargetingPatientListForm;
import phr.web.form.UtilizationConsentForm;

/**
 *
 * @author 
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.CONSENT_NOTIFICATION,"/"})
public class ConsentNotificationController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ConsentNotificationController.class);
    /**画面ID */
    private final String FORM_CD = FormConst.MANAGEMENT_ITEM_REMINDER_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MANAGEMENT_ITEM_REMINDER_FORM_TITLE;
    
    /**
     * インジェクション：管理項目処理
     */
    @Autowired
    @Qualifier("ManagementItemService")
    private IManagementItemService managementItemService;
    
    /**
     * インジェクション：リマインダプッシュ処理
     */
    @Autowired
    @Qualifier("ReminderPushService")
    private IReminderPushService reminderPushService;
    
    /**
     * インジェクション：メッセージサービス
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;
    
    /**
     * インジェクション
     */
    @Autowired
    @Qualifier("PatientManagementService")
    private IPatientManagementService patientManagementService;
    
    @Autowired
    @Qualifier("CommunicationService")
    private ICommunicationService communicationService;
    
//    @Autowired
//    @Qualifier("StudyInformationService")
//    private IStudyInformationService studyInformationService;
    
    /*
    * 日付処理
    */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");    /**
    
    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param form
     * @param model
     * @param request
     * @param result
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(UtilizationConsentForm form, Model model, HttpServletRequest request , BindingResult result) throws Throwable {
        logger.debug("Start");
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        form.setResponseStatusList(list);
        this.initPage(model, form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.CONSENT_NOTIFICATION);
    }
    
    /**
     * 検索ボタン押下時
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=search")
    public String searchPatient(@ModelAttribute UtilizationConsentForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
//        活用同意一覧テーブルを取得

        try {
            StudyInformationEntity entity = (StudyInformationEntity)sessionUtility.getSession(SessionConst.STUDY_INFORMATION_ENTITY);
            UtilizationConsentService utilizationConsentService = new UtilizationConsentService();
            List<UtilizationConsentEntity> utilizationConsentEntityList = new ArrayList<>();
            utilizationConsentEntityList = utilizationConsentService.findByStudyNameResponseStatus(form.getStudyName(), form.getResponseStatusList());
            for (UtilizationConsentEntity list : utilizationConsentEntityList) {
                String birthStr = sdf.format(list.getBirthDate());
                list.setBirthStr(birthStr);;
            }
            
            Date date = new Date();
            for (UtilizationConsentEntity list : utilizationConsentEntityList) {
                String notificationStr = new String();
                if(list.getNotificationDate() != null) {
                    notificationStr = sdf.format(list.getNotificationDate());
                    list.setNotificationStr(notificationStr);
                } else {
                    notificationStr = null;
                    list.setNotificationStr(notificationStr);
                }
            }
            form.setPatientInfo(utilizationConsentEntityList);
            sessionUtility.setSession(SessionConst.UTILIZATION_CONSENT_ENTITY_LIST, utilizationConsentEntityList);
        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.CONSENT_NOTIFICATION);
    }
    
    /**
     *  <pre>送信ボタンクリック時の処理</pre>
     * 
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=sendReminder")
    public String sendReminderAction(@ModelAttribute @Valid UtilizationConsentForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        int exitCode = 0;
        List<UtilizationConsentEntity> utilizationConsentEntityList = (List<UtilizationConsentEntity>)sessionUtility.getSession(SessionConst.UTILIZATION_CONSENT_ENTITY_LIST);
        try{
            MultiValuedMap<String, List<ReminderMessageEntity>> phrIdMessageMap = new ArrayListValuedHashMap<>();
            ReminderPushService service = new ReminderPushService();
            ReminderMessageEntity message = service.getConsentNotificationControllerMessage();
            List<ReminderTargetPhrIdItemEntity> phrIdList = new ArrayList<ReminderTargetPhrIdItemEntity>();
            ReminderTargetPhrIdItemEntity reminderTargetPhrIdItemEntity = new ReminderTargetPhrIdItemEntity();
            
            String[] checked = request.getParameterValues("checkFlg");
            List<String> checkedList = Arrays.asList(checked);
            for (String check : checkedList) {
                int index = check.indexOf("&");
                String id = check.substring(0, index);
                List<ReminderMessageEntity> list = new ArrayList<>();
                list.add(message);
                phrIdMessageMap.put(id, list);
            }
            //--　チェック対象者出力用
            logger.info("★プッシュ対象者★ st" );
            String concat = null;
            for(String onePhrId:phrIdMessageMap.keySet()){
                concat = "";
                for(List<ReminderMessageEntity> oneData:phrIdMessageMap.values()){
                    concat += "[" + oneData.get(0).getReminderTypeId() +","+oneData.get(0).getReminderLevel() +"]";
                }
            logger.info("[" + onePhrId + "]"+concat);
            }
            logger.info( "★プッシュ対象者★ en" );
            //-- ここまで
            
            JSONObject root = new JSONObject();
            JSONArray contents = new JSONArray();
            root.put("contents", contents);
            for (Map.Entry<String, List<ReminderMessageEntity>> e : phrIdMessageMap.entries()) {
                JSONObject content = new JSONObject();
                contents.add(content);
                content.put("phrId", e.getKey());
                JSONArray messages = new JSONArray();
                content.put("messages", messages);
                e.getValue().stream().sorted((ReminderMessageEntity o1, ReminderMessageEntity o2) -> {
                    int r;
                    r = o1.getReminderTypeId().compareTo(o2.getReminderTypeId());
                    if (r != 0) {
                        return r;
                    }
                    return o1.getReminderLevel() - o2.getReminderLevel();
                }).forEach((e1) -> {
                    JSONObject mess = new JSONObject();
                    messages.add(mess);
                    mess.put("reminderTypeId", e1.getReminderTypeId());
                    mess.put("reminderLevel", e1.getReminderLevel());
                });
            }
            File file = new File(PhrConfig.getConfigProperty(ConfigConst.REMINDER_TARGET_FILE_PATH));
            if (file.exists()) {
                file.delete();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(root.toJSONString());
            }
        }
      catch(Throwable th)
      {
          logger.error(th);
          // 戻り値(エラー)
          exitCode=1;
      }
      logger.trace("end");
        
    /**
     * 送信処理
     */
    logger.trace("Start");
    int exitcode = 0;
    try{
        CommunicationService communicationService = new CommunicationService();
        JSONParser p = new JSONParser();
        JSONObject root = (JSONObject)p.parse(readAll());
        JSONArray contents = (JSONArray)root.get("contents");
        logger.info( "number of push :" + contents.size() );
        for (Object e : contents) {
            JSONObject content = (JSONObject)e;
            String phrId = (String)content.get("phrId");
            JSONArray messages = (JSONArray)content.get("messages");
            
            List<ReminderMessageEntity> reminderMessageList = new ArrayList<>();
            for( Object e1 : messages ){
                JSONObject message = (JSONObject)e1;
                String reminderTypeId = (String)message.get("reminderTypeId");
                int reminderLevel = ((Long)message.get("reminderLevel")).intValue();
    
                //  同じリマインダでレベルの異なるものは最高レベルのみにする
                boolean pushRegist=true;
                for(ReminderMessageEntity oneData : reminderMessageList){
                    if(oneData.getReminderTypeId().equals(reminderTypeId)){
                        if(oneData.getReminderLevel() <= reminderLevel){
                            reminderMessageList.remove(oneData);
                        }else{
                            pushRegist = false;
                        }
                        break;
                    }
                }
                if( !pushRegist ){
                    continue;
                }
    
                ReminderMessageEntity entity = new ReminderMessageEntity();
                entity.setReminderTypeId(reminderTypeId);
                entity.setReminderLevel(reminderLevel);
                reminderMessageList.add(entity);
            }
    
            logger.info( "push:"+phrId );
            reminderMessageList.stream().forEach((oneData) -> {
                logger.info(" ->"+oneData.getReminderTypeId() + "," + oneData.getReminderLevel());
            });
            
            try {
                communicationService.CreateSendCommunication(
                    phrId, reminderMessageList );
            } catch(Throwable th){
                logger.error(th);
            }
        }
    }
    catch(IOException | ParseException | RuntimeException | Error th){
        logger.error(th);
        // 戻り値(エラー)
        exitcode=1;
    }
//    // 更新処理
    UtilizationConsentService utilizationConsentService = new UtilizationConsentService();
    String[] checked = request.getParameterValues("checkFlg");
    List<String> checkedList = Arrays.asList(checked);
    for (String check : checkedList) {
        String[] value = check.split("&");
        List<String> list = Arrays.asList(value);
        String phr_id = list.get(0);
        String studyId = list.get(1);
        String subjectId = list.get(2);
        
        int rowCount = utilizationConsentService.updateRecord(phr_id, studyId, subjectId);
        StudyDetailedInformationService studyDetailed = new StudyDetailedInformationService();
        studyDetailed.updateStatus(studyId, subjectId);
    }
    
    
    
    logger.trace("end");
    this.initPage(model, form);
    model.addAttribute(form);
    logger.debug("End");
    return ActionConst.actionPage(ActionConst.CONSENT_NOTIFICATION);
    }
    private static String readAll() throws IOException {
        return Files.lines(Paths.get(PhrConfig.getConfigProperty(
                ConfigConst.REMINDER_TARGET_FILE_PATH)), Charset.forName("UTF-8"))
        .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, UtilizationConsentForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        model.addAttribute(form);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
        
        //研究ID,研究名を取得
        StudyInformationService studyInformationService = new StudyInformationService();
        List<StudyInformationEntity> studyInformationEntityList = studyInformationService.findAll();
        
        sessionUtility.setSession(SessionConst.STUDY_INFORMATION_ENTITY_LIST, studyInformationEntityList);
        form.setStudyInformationEntityList(studyInformationEntityList);
        model.addAttribute("studyInformationEntityList", studyInformationEntityList);
    }
}
