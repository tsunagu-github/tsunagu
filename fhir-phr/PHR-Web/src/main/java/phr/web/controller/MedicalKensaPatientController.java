/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalKensaEntryService;
import phr.service.IMedicalKensaPatientService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.dto.MedicalKensaEntryDto;
import phr.web.form.MedicalKensaEntryForm;
import phr.web.form.MedicalKensaPatientForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Controller
@RequestMapping({ActionConst.MEDICAL_KENSA_PATIENT_ACTION, "/"})
public class MedicalKensaPatientController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalKensaPatientController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MEDICAL_KENSA_PATIENT_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MEDICAL_KENSA_PATIENT_FORM_TITLE;
    
    @Autowired
    private MessageSource messageSource;
    
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
     * インジェクション：検査結果登録一覧機能サービス
     */
    @Autowired
    @Qualifier("MedicalKensaPatientService")
    private IMedicalKensaPatientService medicalKensaPatientService;
    
    /*
    * 日付処理
    */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");    /**
    

    /**
     * /**<pre>初期アクションメソッド</pre>
     * @param form
     * @param result
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        //ログインサイトを設定
//        sessionUtility.setSession("SessionConst.SITE", null);
        
        MedicalKensaPatientForm form = new MedicalKensaPatientForm();
        
        HttpSession session = request.getSession(true);
        MedicalOrganizationEntity account = (MedicalOrganizationEntity) session.getAttribute(SessionConst.MEDICALACCOUNT_ENTITY);
        String medicalOrganizationCd = account.getMedicalOrganizationCd();
        
        MedicalKensaEntryDto dto = (MedicalKensaEntryDto) session.getAttribute(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        String patientId = dto.getPatientId();
        
        //患者情報取得
        PatientEntity entity = this.medicalKensaPatientService.patientSearch(patientId, medicalOrganizationCd);
        
        //dtoに患者情報をセット
        dto.setPhrid(entity.getPHR_ID());
//        dto.setPatientId(entity.getPatientId());
        dto.setName(entity.getFamilyName() + " " + entity.getGivenName());
        dto.setKana(entity.getFamilyKana() + " " + entity.getGivenKana());
        //dto.setBirth(entity.getBirthDate().toString().substring(0,10));
        dto.setBirth(sdf.format(entity.getBirthDate()));
        String sex = "";
        if ("M".equals(entity.getSexCd())) {
                        sex = "男性";
                    } else if ("F".equals(entity.getSexCd())) {
                        sex = "女性";
                    } else {
                        sex = "不明";
                    }
        dto.setSex(sex);
        dto.setMedcd(entity.getMedicalOrganizationCd());
        
        sessionUtility.setSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO, dto);
        form.setPatientList(dto);
        
        //画面初期化
        this.initPage(model, form);
        
        model.addAttribute(form);
        logger.debug("End");

        return ActionConst.actionPage(ActionConst.MEDICAL_KENSA_PATIENT_ACTION);
    }
    
    /**
     * <pre>検索ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=search")
    public String searchAction(MedicalKensaPatientForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        HttpSession session = request.getSession(true);
        MedicalOrganizationEntity account = (MedicalOrganizationEntity) session.getAttribute(SessionConst.MEDICALACCOUNT_ENTITY);
        String medicalOrganizationCd = account.getMedicalOrganizationCd();
        
        MedicalKensaEntryDto dto = (MedicalKensaEntryDto) session.getAttribute(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        
        try {
            Date startDate = DateSet(form.getStartDate(),form,result);
            Date endDate = DateSet(form.getEndDate(),form,result);
            
            if(startDate != null){
                Date date = new Date();
                if(date.before(startDate)){
                    ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.JAVAX_VALIDATION_CONSTRAINTS_PAST_MESSAGE,null,null));
                    result.addError(error);
                }
            }

            List<ObservationEventEntity> list = null;
            
            if(!result.hasErrors()){
                list = this.medicalKensaPatientService.observationEventSearch(dto.getPhrid(), startDate, endDate, medicalOrganizationCd);
                if(list.isEmpty()){
                    ObjectError error = new ObjectError("search", "検索結果が0件でした。");
                    result.addError(error);
                }
            }

            ArrayList<HashMap<String, Object>> mapList = new ArrayList<>();
            
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    HashMap<String, Object> map = new HashMap<>();
                    String observationeventid = list.get(i).getObservationEventId();
                    //String examinationdate = list.get(i).getExaminationDate().toString().substring(0,10);
                    String examinationdate = sdf.format(list.get(i).getExaminationDate());
//                    String remindertype = "";
//                    if (null != list.get(i).getReminderTypeCd()) switch (list.get(i).getReminderTypeCd()) {
//                        case 0:
//                            remindertype = "初回のみ";
//                            break;
//                        case 1:
//                            remindertype = "1ヶ月";
//                            break;
//                        case 3:
//                            remindertype = "3ヶ月";
//                            break;
//                        case 12:
//                            remindertype = "12ヶ月";
//                            break;
//                        case 99:
//                            remindertype = "なし";
//                            break;
//                        default:
//                            remindertype = "不明";
//                            break;
//                    }
                    //String creater = list.get(i).getCreateDateTime().toString().substring(0,10);
                    //String createdatetime = list.get(i).getCreateDateTime().toString().substring(0,10);
                    //String updater = list.get(i).getUpdateDateTime().toString().substring(0,10);
                    //String updatadatetime = list.get(i).getUpdateDateTime().toString().substring(0,10);
                    String createdatetime = sdf.format(list.get(i).getCreateDateTime());
                    String updatadatetime = sdf.format(list.get(i).getUpdateDateTime());
                    
                    map.put("observationeventid",observationeventid);
                    map.put("examinationdate",examinationdate);
//                    map.put("remindertype",remindertype);
//                    map.put("creater", creater);
                    map.put("createdatetime", createdatetime);
//                    map.put("updater",updater);
                    map.put("updatadatetime", updatadatetime);
                    mapList.add(map);
                }
                form.setObservationEventList(mapList);
                //sessionUtility.setSession(SessionConst.MEDICAL_KENSA_OBSERVATION_LIST, list);
                
            }
            form.setPatientList(dto);

        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        this.initPage(model, form);
        model.addAttribute(form);
        
        return ActionConst.actionPage(ActionConst.MEDICAL_KENSA_PATIENT_ACTION);
    }
    
    /**
     * <pre>新規登録ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=create")
    public String createAction(@ModelAttribute @Valid MedicalKensaPatientForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //次に遷移するページ
        String actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_KENSA_ENTRY_INPUT_ACTION);
        
        MedicalKensaEntryDto dto = (MedicalKensaEntryDto) sessionUtility.getSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        //form.setPatientList(dto);
        dto.setStartDate(null);
        dto.setKensaListOne(null);
        dto.setObservationEventId(null);
        sessionUtility.setSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO, dto);
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        
        return actionPage;
    }

    /**
     * <pre>選択ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=update")
    public String updateAction(@ModelAttribute @Valid MedicalKensaPatientForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //次に遷移するページ
        String actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_KENSA_ENTRY_INPUT_ACTION);
        
        String eventID = form.getParam1();
        String eventDate = form.getParam2();
        MedicalKensaEntryDto dto = (MedicalKensaEntryDto) sessionUtility.getSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
       
        dto.setObservationEventId(eventID);
        dto.setStartDate(eventDate);
        dto.setKensaListOne(null);
        sessionUtility.setSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO, dto);
        
        form.setPatientList(dto);
        
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        
        return actionPage;
    }    
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, MedicalKensaPatientForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    private java.util.Date DateSet(String beforeDate, MedicalKensaPatientForm form, BindingResult result) {
        java.util.Date date = null;
        try {
            if(beforeDate == null || beforeDate.trim().length() == 0) return null;

            if(!beforeDate.contains("/")){
                ObjectError error = new ObjectError("login", messageSource.getMessage(ValidationMessageConst.DATE_FORMAT_EXCEPTION,null,null));
                result.addError(error);
            }else{
                if(beforeDate.length() != 10){
                    ObjectError error = new ObjectError("login", messageSource.getMessage(ValidationMessageConst.DATE_FORMAT_EXCEPTION,null,null));
                    result.addError(error);
                }else{
                    date = sdf.parse(beforeDate);
                }
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(MedicalMessageListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return date;
    }
}
