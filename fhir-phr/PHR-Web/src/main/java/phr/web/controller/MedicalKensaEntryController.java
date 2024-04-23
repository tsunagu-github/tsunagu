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
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalKensaEntryService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.dto.MedicalKensaEntryDto;
import phr.web.form.MedicalKensaEntryForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Controller
@RequestMapping({ActionConst.MEDICAL_KENSA_ENTRY_ACTION, "/"})
public class MedicalKensaEntryController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalKensaEntryController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MEDICAL_KENSA_ENTRY_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MEDICAL_KENSA_ENTRY_FORM_TITLE;
    
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
    @Qualifier("MedicalKensaEntryService")
    private IMedicalKensaEntryService medicalKensaEntryService;
    
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
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(MedicalKensaEntryForm form, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //ログインサイトを設定
//        sessionUtility.setSession("SessionConst.SITE", null);

        //画面初期化
        this.initPage(model, form);
        
        model.addAttribute(form);
        logger.debug("End");

        return ActionConst.actionPage(ActionConst.MEDICAL_KENSA_ENTRY_ACTION);
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
    public String searchAction(MedicalKensaEntryForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        HttpSession session = request.getSession(true);
        MedicalOrganizationEntity account = (MedicalOrganizationEntity) session.getAttribute(SessionConst.MEDICALACCOUNT_ENTITY);
        String medicalOrganizationCd = account.getMedicalOrganizationCd();
        
        try {
            Date birthDate = DateSet(form.getBirthDate(),form,result);
            
            if(birthDate != null){
                Date date = new Date();
                if(date.before(birthDate)){
                    ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.JAVAX_VALIDATION_CONSTRAINTS_PAST_MESSAGE,null,null));
                    result.addError(error);
                }
            }

            //カナ入力確認
            String MATCH_KATAKANA = "^[\\u30A0-\\u30FF]+$";
            String sei = form.getFamilyKana();
            String mei = form.getGivenKana();
            if (sei.length() > 0) {
                sei = Normalizer.normalize(sei, Normalizer.Form.NFKC);
                if(!sei.matches(MATCH_KATAKANA)){
                    ObjectError error = new ObjectError("search", "カナ氏名_姓はカナ文字で入力してください。");
                    result.addError(error);
                }
            }
            if (mei.length() > 0) {
                mei = Normalizer.normalize(mei, Normalizer.Form.NFKC);
                if(!mei.matches(MATCH_KATAKANA)){
                    ObjectError error = new ObjectError("search", "カナ氏名_名はカナ文字で入力してください。");
                    result.addError(error);
                }
            }
            
            List<PatientEntity> list = null;
            
            if(!result.hasErrors()){
                list = this.medicalKensaEntryService.patientSearch(form.getPatientId(), sei, mei, form.getBirthDate(), form.getSex(), medicalOrganizationCd);
                if(list.isEmpty()){
                    ObjectError error = new ObjectError("search", "検索結果が0件でした。");
                    result.addError(error);
                }
            }

            ArrayList<HashMap<String, Object>> mapList = new ArrayList<>();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    HashMap<String, Object> map = new HashMap<>();
                    String phrId = list.get(i).getPHR_ID();
                    String patientId = list.get(i).getPatientId();
                    String kana = list.get(i).getFamilyKana() + " " + list.get(i).getGivenKana();
                    String name = list.get(i).getFamilyName() + " " + list.get(i).getGivenName();
                    //String birth = list.get(i).getBirthDate().toString().substring(0,10);
                    String birth =sdf.format(list.get(i).getBirthDate());
                    String sex = "";
                    if ("M".equals(list.get(i).getSexCd())) {
                        sex = "男性";
                    } else if ("F".equals(list.get(i).getSexCd())) {
                        sex = "女性";
                    } else {
                        sex = "不明";
                    }

                    map.put("phrId",phrId);
                    map.put("patientId",patientId);
                    map.put("kana", kana);
                    map.put("name", name);
                    map.put("birth",birth);
                    map.put("sex", sex);
                    mapList.add(map);
                }
                form.setPatientList(mapList);
            }

        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        this.initPage(model, form);
        model.addAttribute(form);
        
        return ActionConst.actionPage(ActionConst.MEDICAL_KENSA_ENTRY_ACTION);
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
    public String selectAction(@ModelAttribute @Valid MedicalKensaEntryForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //次に遷移するページ
        String actionPage = "";
        
        HttpSession session = request.getSession(true);
        MedicalOrganizationEntity account = (MedicalOrganizationEntity) session.getAttribute(SessionConst.MEDICALACCOUNT_ENTITY);
        String medicalOrganizationCd = account.getMedicalOrganizationCd();

        try {
            if (form.getPatientId().equals("") ) {
                ObjectError error = new ObjectError("select", "患者IDを入力してください。");
                result.addError(error);
            }
            if (!result.hasErrors()) {
                MedicalOrganizationPatientEntity entity = new MedicalOrganizationPatientEntity();
                entity = this.medicalKensaEntryService.idSearch(form.getPatientId(), medicalOrganizationCd);

                if (entity == null) {
                    ObjectError error = new ObjectError("select", "指定した患者IDは存在しません。");
                    result.addError(error);
                }
            }
        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        
        this.initPage(model, form);
        model.addAttribute(form);
        
        logger.debug("End");
        
        if (!result.hasErrors()) {
            MedicalKensaEntryDto dto = new MedicalKensaEntryDto();
            dto.setPatientId(form.getPatientId());
            sessionUtility.setSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO, dto);
            actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_KENSA_PATIENT_ACTION);
        } else {
            actionPage = ActionConst.actionPage(ActionConst.MEDICAL_KENSA_ENTRY_ACTION);
        }
        
        return actionPage;
    }
    
    /**
     * <pre>選択ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=patient")
    public String patientAction(@ModelAttribute @Valid  MedicalKensaEntryForm form, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //次に遷移するページ
        String actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_KENSA_PATIENT_ACTION);

        MedicalKensaEntryDto dto = new MedicalKensaEntryDto();
        dto.setPatientId(form.getParam1());
        sessionUtility.setSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO, dto);
        
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
    private void initPage(Model model, MedicalKensaEntryForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    private java.util.Date DateSet(String birthDate, MedicalKensaEntryForm form, BindingResult result) {
        java.util.Date date = null;
        try {
            if(birthDate == null || birthDate.trim().length() == 0) return null;

            if(!birthDate.contains("/")){
                ObjectError error = new ObjectError("login", messageSource.getMessage(ValidationMessageConst.DATE_FORMAT_EXCEPTION,null,null));
                result.addError(error);
            }else{
                if(birthDate.length() != 10){
                    ObjectError error = new ObjectError("login", messageSource.getMessage(ValidationMessageConst.DATE_FORMAT_EXCEPTION,null,null));
                    result.addError(error);
                }else{
                    date = sdf.parse(birthDate);
                }
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(MedicalMessageListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return date;
    }
}
