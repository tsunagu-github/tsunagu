/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.sql.Timestamp;
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

import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.enums.DataInputTypeCdEnum;
import phr.service.IMedicalKensaEntryConfirmService;
import phr.service.IMedicalKensaEntryInputService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.dto.MedicalKensaEntryDto;
import phr.web.form.MedicalKensaEntryConfirmForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Controller
@RequestMapping({ActionConst.MEDICAL_KENSA_ENTRY_CONFIRM_ACTION, "/"})
public class MedicalKensaEntryConfirmController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalKensaEntryConfirmController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.MEDICAL_KENSA_ENTRY_CONFIRM_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MEDICAL_KENSA_ENTRY_CONFIRM_FORM_TITLE;
    
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
    
    /**
     * インジェクション：検査結果登録一覧機能サービス
     */
    @Autowired
    @Qualifier("MedicalKensaEntryConfirmService")
    private IMedicalKensaEntryConfirmService medicalKensaEntryConfirmService;
    
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
    public String defaultAction(MedicalKensaEntryConfirmForm form, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //ログインサイトを設定
//        sessionUtility.setSession("SessionConst.SITE", null);
        
//        HttpSession session = request.getSession(true);
//        MedicalOrganizationEntity account = (MedicalOrganizationEntity) session.getAttribute(SessionConst.MEDICALACCOUNT_ENTITY);
//        String medicalOrganizationCd = account.getMedicalOrganizationCd();
        
        
        //患者情報をセッションから取得
        MedicalKensaEntryDto dto = (MedicalKensaEntryDto) sessionUtility.getSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        
        for (int i = 0; i < dto.getInputList().size(); i++) {
            if (dto.getInputList().get(i).get("enumid") != null) {
                
                List<HashMap> mapList = new ArrayList<>();
                
                String[] enumidList = dto.getInputList().get(i).get("enumid").toString().split(",", -1);
                String[] enumnameList = dto.getInputList().get(i).get("enumname").toString().split(",", -1);
                String[] enumvalueList = dto.getInputList().get(i).get("enumvalue").toString().split(",", -1);
                for (int j = 0; j < enumidList.length; j++) {
                    
                HashMap map = new HashMap();
                    map.put("enumId", enumidList[j]);
                    map.put("enumName", enumnameList[j]);
                    map.put("enumValue", enumvalueList[j]);
                    
                    mapList.add(map);
                }
                dto.getInputList().get(i).put("enumList", mapList);
            }
        }
        
        form.setStartDate(dto.getStartDate());
//        form.setSelectPeriod(dto.getSelectPeriod());
        form.setInputList(dto.getInputList());
        form.setPatientList(dto);
        
        //画面初期化
        this.initPage(model, form);
        
        model.addAttribute(form);
        logger.debug("End");

        return ActionConst.actionPage(ActionConst.MEDICAL_KENSA_ENTRY_CONFIRM_ACTION);
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
    public String returnAction(@ModelAttribute @Valid MedicalKensaEntryConfirmForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        
        //MedicalKensaEntryDto dto = (MedicalKensaEntryDto)sessionUtility.getSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        
        String actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_KENSA_ENTRY_INPUT_ACTION);
        
        return actionPage;
    }
    
    /**
     * 登録アクションメソッド
     * @param model
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=commit")
    public String commitAction(@ModelAttribute MedicalKensaEntryConfirmForm form,BindingResult result, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");

    	//画面初期化
    	this.initPage(model,form);
                //次に遷移するページ
        String actionPage = ActionConst.redirectActionPage(ActionConst.MEDICAL_KENSA_PATIENT_ACTION);

    	ObjectError error =null;
        MedicalOrganizationEntity account = (MedicalOrganizationEntity) sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        String medicalOrganizationCd = account.getMedicalOrganizationCd();
        
        
        MedicalKensaEntryDto dto = (MedicalKensaEntryDto)sessionUtility.getSession(SessionConst.MEDICAL_KENSA_ENTRY_DTO);
        //ObservationEventEntity
        ObservationEventEntity evEntity = setEvEntity(dto,medicalOrganizationCd);
        
        //Observationリスト
        List<ObservationEntity> observationList = new ArrayList();
        for(ObservationDefinitionRangeEntity oEnt:dto.getKensaListOne()){
            if(oEnt.getValue()==null || oEnt.getValue().isEmpty()){
            }else{
                ObservationEntity obEnt = setObEntity(oEnt, evEntity.getObservationEventId());
                obEnt.setObservationEventId(evEntity.getObservationEventId());
                observationList.add(obEnt);
            }
        }

    	try {
    		//登録処理
                int resultline = medicalKensaEntryConfirmService.updateObservationEventAndObservation(observationList, evEntity);

                    if(resultline==0){
                        error = new ObjectError("noentry", "データベースへの登録がありませんでした。");
                        result.addError(error);
                    }                
		} catch (Throwable t) {
			error = new ObjectError("noentry", "データベースへの登録中にエラーが発生しました。再度登録をお試しください");
			result.addError(error);
			logger.error(t);
		} finally {
                    model.addAttribute(form);
                    logger.debug("End");
		}


        if(result.hasErrors()){
            actionPage = ActionConst.actionPage(ActionConst.MEDICAL_KENSA_ENTRY_CONFIRM_ACTION);
            form.setStartDate(dto.getStartDate());
            form.setInputList(dto.getInputList());           
            form.setPatientList(dto);    
        }
    	logger.debug("End");
        return actionPage;
    }
    
    private ObservationEventEntity setEvEntity(MedicalKensaEntryDto origDto, String moCd) throws Throwable {
        if (origDto.getObservationEventId() != null) {
            ObservationEventEntity observationEvent = this.medicalKensaEntryConfirmService.getObservationEvent(origDto.getObservationEventId());
            String sdate = origDto.getStartDate();
            Timestamp exdate = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(sdate).getTime());
            observationEvent.setExaminationDate(exdate);
            int yearint = Integer.parseInt(sdate.substring(0, 4));
            int month = Integer.parseInt(sdate.substring(5, 7));
            if (month < 4) {
                yearint = yearint - 1;
            }
            observationEvent.setYear(yearint);
            return observationEvent;
        }
        ObservationEventEntity entity = new ObservationEventEntity();
        entity.setObservationEventId(origDto.getObservationEventId());
        entity.setDataInputTypeCd(DataInputTypeCdEnum.KENNSA.getCode());
        String sdate =  origDto.getStartDate();
        Timestamp exdate = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(sdate).getTime());
        entity.setExaminationDate(exdate);
        entity.setPHR_ID(origDto.getPhrid());
        int yearint = Integer.parseInt(sdate.substring(0, 4));
        int month = Integer.parseInt(sdate.substring(5, 7));
            if (month < 4) {
                yearint = yearint - 1; 
            }   
        entity.setYear(yearint);
//        String insurerNo = origDto.getPhrid().substring(0,7);
//        entity.setInsurerNo(insurerNo);
        InsurerPatientEntity insurerPatient = this.medicalKensaEntryInputService.insurerSearch(origDto.getPhrid());
        entity.setInsurerNo(insurerPatient.getInsurerNo());
        //entity.setLaboratoryCd();
        //entity.setOrderNo();
        entity.setMedicalOrganizationCd(moCd);
        entity.setDiseaseManagementTargetFlg(true);
        return entity;
    }

    private ObservationEntity setObEntity(ObservationDefinitionRangeEntity origDto, String observationEventId) throws Throwable {
        ObservationEntity entity = new ObservationEntity();
        if (observationEventId != null && origDto.getObservationDefinitionId() != null) {
            ObservationEntity observation = this.medicalKensaEntryConfirmService.getObservation(observationEventId, origDto.getObservationDefinitionId());
            if (observation != null) {
                entity = observation;
            }
        }
//        entity.setObservationEventId();
        entity.setObservationDefinitionId(origDto.getObservationDefinitionId());
        //entity.setJLAC10();
        entity.setValue(origDto.getValue());
        //entity.setOutRangeTypeCd();
        entity.setMinReferenceValue(origDto.getMinReferenceValue());
        entity.setMaxReferenceValue(origDto.getMaxReferenceValue());
        entity.setDiseaseManagementTargetFlg(true);
        entity.setUnit(origDto.getUnitValue());
        return entity;
    }
    
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, MedicalKensaEntryConfirmForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    private java.util.Date DateSet(String birthDate, MedicalKensaEntryConfirmForm form, BindingResult result) {
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
