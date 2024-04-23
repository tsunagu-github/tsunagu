/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

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

import jp.kis_inc.core.utility.TypeUtility;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.service.IMedicalKensaEntryInputService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.dto.MedicalKensaEntryDto;
import phr.web.form.MedicalKensaEntryInputForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Controller
@RequestMapping({ActionConst.MEDICAL_KENSA_ENTRY_INPUT_ACTION, "/"})
public class MedicalKensaEntryInputController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalKensaEntryInputController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MEDICAL_KENSA_ENTRY_INPUT_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MEDICAL_KENSA_ENTRY_INPUT_FORM_TITLE;
    
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
    @Qualifier("MedicalKensaEntryInputService")
    private IMedicalKensaEntryInputService medicalKensaEntryInputService;
    
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
    public String defaultAction(MedicalKensaEntryInputForm form, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //ログインサイトを設定
//        sessionUtility.setSession("SessionConst.SITE", null);
        
//        HttpSession session = request.getSession(true);
//        MedicalOrganizationEntity account = (MedicalOrganizationEntity) session.getAttribute(SessionConst.MEDICALACCOUNT_ENTITY);
//        String medicalOrganizationCd = account.getMedicalOrganizationCd();
        
        //患者情報をセッションから取得
        MedicalKensaEntryDto dto = (MedicalKensaEntryDto) sessionUtility.getSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        form.setPatientList(dto);
        List<ObservationEntity> entlist = null;
        if(dto.getObservationEventId()==null || dto.getObservationEventId().isEmpty()){
        }else{
            //List<ObservationEventEntity> list = (List<ObservationEventEntity>) sessionUtility.getSession(SessionConst.MEDICAL_KENSA_OBSERVATION_LIST);
            entlist = medicalKensaEntryInputService.getObservationEntryList(dto.getObservationEventId());
            //form.setStartDate(dto.getStartDate());
        }
        
        if(dto.getKensaListOne()==null || dto.getKensaListOne().isEmpty()){
            //保険者情報を取得
            InsurerPatientEntity entity = this.medicalKensaEntryInputService.insurerSearch(dto.getPhrid());
            //年度を取得
            Date now = new Date();
            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
            int year = Integer.parseInt(sdfYear.format(now));
            int month = Integer.parseInt(sdfMonth.format(now));
            if (month < 4) {
                year = year - 1; 
            } 
            //結果登録が可能な検査の一覧を取得
            //TODO:DiseaseTypeCdのSQLに固定値入力中、仕様決定後要修正
            List<ObservationDefinitionRangeEntity> list = this.medicalKensaEntryInputService.observationReminderSearch(entity.getInsurerNo(), year, dto.getSex());

    //        List<ObservationDefinitionRangeEntity> zero = new ArrayList<>();
            List<ObservationDefinitionRangeEntity> one = new ArrayList<>();
    //        List<ObservationDefinitionRangeEntity> three = new ArrayList<>();
    //        List<ObservationDefinitionRangeEntity> years = new ArrayList<>();
    //        List<ObservationDefinitionRangeEntity> none = new ArrayList<>();
            //リマインダー種別コードごとに格納リストを変更する
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    List<HashMap> mapList = new ArrayList<HashMap>();
                    int addCnt = 0;
                    if (null != list.get(i).getReminderTypeCd()) {
    //                    switch (list.get(i).getReminderTypeCd()) {
    //                    case 0:
    //                        if (list.get(i).getEnumId() != 0) {
    //                            for (int j = i; j < list.size(); j++) {
    //                                if (list.get(i).getObservationDefinitionId().equals(list.get(j).getObservationDefinitionId())) {
    //                                    HashMap map = new HashMap();
    //                                    map.put("enumId" ,list.get(j).getEnumId());
    //                                    map.put("enumName" ,list.get(j).getEnumName());
    //                                    map.put("enumValue" ,list.get(j).getEnumValue());
    //                                    mapList.add(map);
    //                                    addCnt++;
    //                                } else {
    //                                    break;
    //                                }
    //                            }
    //                            addCnt = addCnt -1;
    //                        }
    //                        list.get(i).setEnumList(mapList);
    //                        zero.add(list.get(i));
    //                        i += addCnt;
    //                        break;
    //                    case 1:
                            if (list.get(i).getEnumId() != 0) {
                                for (int j = i; j < list.size(); j++) {
                                    if (list.get(i).getObservationDefinitionId().equals(list.get(j).getObservationDefinitionId())) {
                                        HashMap map = new HashMap();
                                        map.put("enumId" ,list.get(j).getEnumId());
                                        map.put("enumName" ,list.get(j).getEnumName());
                                        map.put("enumValue" ,list.get(j).getEnumValue());
                                        mapList.add(map);
                                        addCnt++;
                                    } else {
                                        break;
                                    }
                                }
                                addCnt = addCnt -1;
                            }
                            list.get(i).setEnumList(mapList);
                            if(entlist!=null){
                                for(ObservationEntity obent:entlist){
                                    if(obent.getObservationDefinitionId().equals(list.get(i).getObservationDefinitionId())){
                                        list.get(i).setValue(obent.getValue());
                                        list.get(i).setDiseaseManagementTargetFlg(obent.isFlag());
                                        if (!(TypeUtility.isNullOrEmpty(obent.getValue()) && !TypeUtility.isNullOrEmpty(dto.getStartDate()))) {
                                            list.get(i).setMaxReferenceValue(obent.getMaxReferenceValue());
                                            list.get(i).setMinReferenceValue(obent.getMinReferenceValue());
                                        }
                                        break;
                                    }
                                }
                            }
                            one.add(list.get(i));
                            i += addCnt;
    //                        break;
    //                    case 3:
    //                        if (list.get(i).getEnumId() != 0) {
    //                            for (int j = i; j < list.size(); j++) {
    //                                if (list.get(i).getObservationDefinitionId().equals(list.get(j).getObservationDefinitionId())) {
    //                                    HashMap map = new HashMap();
    //                                    map.put("enumId" ,list.get(j).getEnumId());
    //                                    map.put("enumName" ,list.get(j).getEnumName());
    //                                    map.put("enumValue" ,list.get(j).getEnumValue());
    //                                    mapList.add(map);
    //                                    addCnt++;
    //                                } else {
    //                                    break;
    //                                }
    //                            }
    //                            addCnt = addCnt -1;
    //                        }
    //                        list.get(i).setEnumList(mapList);
    //                        three.add(list.get(i));
    //                        i += addCnt;
    //                        break;
    //                    case 12:
    //                        if (list.get(i).getEnumId() != 0) {
    //                            for (int j = i; j < list.size(); j++) {
    //                                if (list.get(i).getObservationDefinitionId().equals(list.get(j).getObservationDefinitionId())) {
    //                                    HashMap map = new HashMap();
    //                                    map.put("enumId" ,list.get(j).getEnumId());
    //                                    map.put("enumName" ,list.get(j).getEnumName());
    //                                    map.put("enumValue" ,list.get(j).getEnumValue());
    //                                    mapList.add(map);
    //                                    addCnt++;
    //                                } else {
    //                                    break;
    //                                }
    //                            }
    //                            addCnt = addCnt -1;
    //                        }
    //                        list.get(i).setEnumList(mapList);
    //                        years.add(list.get(i));
    //                        i += addCnt;
    //                        break;
    //                    case 99:
    //                        if (list.get(i).getEnumId() != 0) {
    //                            for (int j = i; j < list.size(); j++) {
    //                                if (list.get(i).getObservationDefinitionId().equals(list.get(j).getObservationDefinitionId())) {
    //                                    HashMap map = new HashMap();
    //                                    map.put("enumId" ,list.get(j).getEnumId());
    //                                    map.put("enumName" ,list.get(j).getEnumName());
    //                                    map.put("enumValue" ,list.get(j).getEnumValue());
    //                                    mapList.add(map);
    //                                    addCnt++;
    //                                } else {
    //                                    break;
    //                                }
    //                            }
    //                            addCnt = addCnt -1;
    //                        }
    //                        list.get(i).setEnumList(mapList);
    //                        none.add(list.get(i));
    //                        i += addCnt;
    //                        break;
    //                    default:
    //                        break;
                    }
                }
            }

    //        dto.setKensaListFirst(zero);
            dto.setKensaListOne(one);
    //        dto.setKensaListThree(three);
    //        dto.setKensaListYears(years);
    //        dto.setKensaListNone(none);
            sessionUtility.setSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO, dto);
        }
        //formにセット
        form.setStartDate(dto.getStartDate());
//        form.setKensaListFirst(zero);
        form.setKensaListOne(dto.getKensaListOne());
//        form.setKensaListThree(three);
//        form.setKensaListYears(years);
//        form.setKensaListNone(none);

        //画面初期化
        this.initPage(model, form);
        
        model.addAttribute(form);
        logger.debug("End");

        return ActionConst.actionPage(ActionConst.MEDICAL_KENSA_ENTRY_INPUT_ACTION);
    }
    
    /**
     * <pre>確認ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=confirm")
    public String confirmAction(@ModelAttribute @Valid MedicalKensaEntryInputForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
//        HttpSession session = request.getSession(true);
//        MedicalOrganizationEntity account = (MedicalOrganizationEntity) session.getAttribute(SessionConst.MEDICALACCOUNT_ENTITY);
//        String medicalOrganizationCd = account.getMedicalOrganizationCd();
        
        MedicalKensaEntryDto dto = (MedicalKensaEntryDto) sessionUtility.getSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        
        try 
        {
            Date startDate = DateSet(form.getStartDate(),form,result);
            
            if(startDate != null && !"".equals(startDate)){
                Date date = new Date();
                if(date.before(startDate)){
                    ObjectError error = new ObjectError("search", messageSource.getMessage(ValidationMessageConst.JAVAX_VALIDATION_CONSTRAINTS_PAST_MESSAGE,null,null));
                    result.addError(error);
                } 
            } else {
                ObjectError error = new ObjectError("search", "検査日を入力してください。");
                result.addError(error);
            }
            
//            form.setKensaListFirst(dto.getKensaListFirst());
            form.setKensaListOne(dto.getKensaListOne());
//            form.setKensaListThree(dto.getKensaListThree());
//            form.setKensaListYears(dto.getKensaListYears());

            List<ObservationDefinitionRangeEntity> entity = new ArrayList<>();
            
            //if (form.getSelectPeriod().equals("1")) {
                entity = dto.getKensaListOne();
                for (int i = 0; i < form.getDtoOne().size(); i++) {
                    for (int j = 0; j < entity.size(); j++) {
                        if (form.getDtoOne().get(i).getDefid().equals(entity.get(j).getObservationDefinitionId())) {
                            entity.get(j).setValue(form.getDtoOne().get(i).getValue());
                            entity.get(j).setMinReferenceValue(form.getDtoOne().get(i).getMin());
                            entity.get(j).setMaxReferenceValue(form.getDtoOne().get(i).getMax());
                            break;
                        }
                    }
                }
                form.setKensaListOne(entity);
//            } else if (form.getSelectPeriod().equals("3")) {
//                entity = dto.getKensaListThree();
//                for (int i = 0; i < form.getDtoThree().size(); i++) {
//                    for (int j = 0; j < entity.size(); j++) {
//                        if (form.getDtoThree().get(i).getDefid().equals(entity.get(j).getObservationDefinitionId())) {
//                            entity.get(j).setValue(form.getDtoThree().get(i).getValue());
//                            entity.get(j).setMinReferenceValue(form.getDtoThree().get(i).getMin());
//                            entity.get(j).setMaxReferenceValue(form.getDtoThree().get(i).getMax());
//                            break;
//                        }
//                    }
//                }
//                form.setKensaListThree(entity);
//            } else if (form.getSelectPeriod().equals("12")) {
//                entity = dto.getKensaListYears();
//                for (int i = 0; i < form.getDtoYears().size(); i++) {
//                    for (int j = 0; j < entity.size(); j++) {
//                        if (form.getDtoYears().get(i).getDefid().equals(entity.get(j).getObservationDefinitionId())) {
//                            entity.get(j).setValue(form.getDtoYears().get(i).getValue());
//                            entity.get(j).setMinReferenceValue(form.getDtoYears().get(i).getMin());
//                            entity.get(j).setMaxReferenceValue(form.getDtoYears().get(i).getMax());
//                            break;
//                        }
//                    }
//                }
//                form.setKensaListYears(entity);
//            } else if (form.getSelectPeriod().equals("0")) {
//                entity = dto.getKensaListFirst();
//                for (int i = 0; i < form.getDtoFirst().size(); i++) {
//                    for (int j = 0; j < entity.size(); j++) {
//                        if (form.getDtoFirst().get(i).getDefid().equals(entity.get(j).getObservationDefinitionId())) {
//                            entity.get(j).setValue(form.getDtoFirst().get(i).getValue());
//                            entity.get(j).setMinReferenceValue(form.getDtoFirst().get(i).getMin());
//                            entity.get(j).setMaxReferenceValue(form.getDtoFirst().get(i).getMax());
//                            break;
//                        }
//                    }
//                }
//                form.setKensaListFirst(entity);
//            } else {
//                entity = dto.getKensaListNone();
//                for (int i = 0; i < form.getDtoNone().size(); i++) {
//                    for (int j = 0; j < entity.size(); j++) {
//                        if (form.getDtoNone().get(i).getDefid().equals(entity.get(j).getObservationDefinitionId())) {
//                            entity.get(j).setValue(form.getDtoNone().get(i).getValue());
//                            entity.get(j).setMinReferenceValue(form.getDtoNone().get(i).getMin());
//                            entity.get(j).setMaxReferenceValue(form.getDtoNone().get(i).getMax());
//                            break;
//                        }
//                    }
//                }
//                form.setKensaListNone(entity);
//            }

                //上限下限の取得
                List<String> idList = new ArrayList();
                for(ObservationDefinitionRangeEntity getiddto:entity){
                    idList.add(getiddto.getObservationDefinitionId());
                }
                List<ObservationDefinitionEntity> checkList = medicalKensaEntryInputService.getObservationDefinition(idList);


            if (!result.hasErrors()) {
                List<MedicalKensaEntryDto> dataList = new ArrayList<>();
//                switch (form.getSelectPeriod()) {
//                    case "1":
                        dataList = form.getDtoOne();
//                        break;
//                    case "3":
//                        dataList = form.getDtoThree();
//                        break;
//                    case "12":
//                        dataList = form.getDtoYears();
//                        break;
//                    case "0":
//                        dataList = form.getDtoFirst();
//                        break;
//                    default:
//                        break;
//                }
                List<HashMap<Object, Object>> inputList = new ArrayList<HashMap<Object, Object>>();
                if (dataList.size() > 0) {
                    Double diastolicBPdouble = null;
                    Double systolicBPdouble = null;
                    for (int i = 0; i < dataList.size(); i++) {
                        HashMap<Object, Object> inputMap = new HashMap<Object, Object>();
                
                        if (!"".equals(dataList.get(i).getValue()) && dataList.get(i).getValue() != null) {
                            
                            //保険者情報を取得
//                            InsurerPatientEntity ientity = this.medicalKensaEntryInputService.insurerSearch(dto.getPhrid());
                            //年度を取得
                            String examinationDate = form.getStartDate();
                            int year = Integer.parseInt(examinationDate.substring(0, 4));
                            int month = Integer.parseInt(examinationDate.substring(5, 7));
                            if (month < 4) {
                                year = year - 1; 
                            } 
                            
                            if (dataList.get(i).getEnumid() == null) {
//                                ObservationDefinitionRangeEntity oentity = this.medicalKensaEntryInputService.inputCheck(
//                                        dataList.get(i).getValue(), ientity.getInsurerNo(), year, dataList.get(i).getDefid(), "0");
                                Double minInput=null;
                                Double maxInput=null;
                                Double valuedouble = Double.parseDouble(dataList.get(i).getValue());
                                boolean inputError = false;
                                for(ObservationDefinitionEntity checkent:checkList){
                                    if(checkent.getObservationDefinitionId().equals(dataList.get(i).getDefid())){
                                        minInput = checkent.getMinInput();
                                        maxInput = checkent.getMaxInput();
                                        break;
                                    }
                                }
                                if(minInput!=null){
                                    if(minInput>valuedouble){
                                        inputError = true;
                                    }
                                }
                                if(maxInput!=null){
                                    if(maxInput<valuedouble){
                                        inputError = true;
                                    }
                                }
                                if (inputError) {
                                    ObjectError error = new ObjectError("search", dataList.get(i).getDefname() + "の入力値が異常です。");
                                    result.addError(error);
                                }
                                //拡張期血圧＜収縮期血圧の確認の準備
                                if(dataList.get(i).getDefid().equals("0000000004")){
                                    diastolicBPdouble = Double.parseDouble(dataList.get(i).getValue());
                                }
                                if(dataList.get(i).getDefid().equals("0000000003")){
                                    systolicBPdouble = Double.parseDouble(dataList.get(i).getValue());
                                }
                                
                            }
                                                        
                            inputMap.put("id", dataList.get(i).getDefid());
                            inputMap.put("name", dataList.get(i).getDefname());
                            inputMap.put("unitvalue", dataList.get(i).getUnitvalue());
                            inputMap.put("value", dataList.get(i).getValue());
                            if (dataList.get(i).getMin() != null) {
                                inputMap.put("min", dataList.get(i).getMin().toString());
                            } else {
                                inputMap.put("min", "");
                            }
                            if (dataList.get(i).getMax() != null) {
                                inputMap.put("max", dataList.get(i).getMax().toString());
                            } else {
                                inputMap.put("max", "");
                            }
                            if (dataList.get(i).getEnumname() != null) {
                                inputMap.put("enumid", dataList.get(i).getEnumid());
                                inputMap.put("enumname", dataList.get(i).getEnumname());
                                inputMap.put("enumvalue", dataList.get(i).getEnumvalue());
                            }
                            inputList.add(inputMap);
                        }
                    }

                    //拡張期血圧＜収縮期血圧の確認
                    if(diastolicBPdouble != null && systolicBPdouble != null ){
                        boolean pressureError = false;
                        if(diastolicBPdouble > systolicBPdouble){
                            pressureError = true;
                        }
                        if (pressureError) {
                            ObjectError error = new ObjectError("search", "拡張期血圧が収縮期血圧よりも高くなっています。");
                            result.addError(error);
                        }
                    }                    
                    
                    
                    if (inputList.size() > 0) {
                        dto.setInputList(inputList);
                    } else {
                        ObjectError error = new ObjectError("search", "結果値を登録してください。");
                        result.addError(error);
                    }
                }
                dto.setStartDate(form.getStartDate());
                dto.setSelectPeriod(form.getSelectPeriod());
                sessionUtility.setSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO, dto);
            }
        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }

        this.initPage(model, form);
        model.addAttribute(form);
        
        String actionPage = "";
        if (!result.hasErrors()) {
            actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_KENSA_ENTRY_CONFIRM_ACTION);
        } else {
            form.setPatientList(dto);
            actionPage = ActionConst.actionPage(ActionConst.MEDICAL_KENSA_ENTRY_INPUT_ACTION);
        }
        
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
    public String returnAction(@ModelAttribute @Valid MedicalKensaEntryInputForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        
//        MedicalKensaEntryDto dto = (MedicalKensaEntryDto)sessionUtility.getSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        
        String actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_KENSA_PATIENT_ACTION);
        
        return actionPage;
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, MedicalKensaEntryInputForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    private java.util.Date DateSet(String startDate, MedicalKensaEntryInputForm form, BindingResult result) {
        java.util.Date date = null;
        try {
            if(startDate == null || startDate.trim().length() == 0) return null;

            if(!startDate.contains("/")){
                ObjectError error = new ObjectError("login", messageSource.getMessage(ValidationMessageConst.DATE_FORMAT_EXCEPTION,null,null));
                result.addError(error);
            }else{
                if(startDate.length() != 10){
                    ObjectError error = new ObjectError("login", messageSource.getMessage(ValidationMessageConst.DATE_FORMAT_EXCEPTION,null,null));
                    result.addError(error);
                }else{
                    date = sdf.parse(startDate);
                }
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(MedicalMessageListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return date;
    }
}
