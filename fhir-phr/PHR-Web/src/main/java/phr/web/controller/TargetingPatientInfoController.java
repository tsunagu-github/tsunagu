/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.datadomain.entity.PatientEntity;
import phr.enums.PrefectureCdEnum;
import phr.service.IOnetimePasswordService;
import phr.service.ITargetingPatientInfoService;
import phr.utility.TypeUtility;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.dto.DiseaseDto;
import phr.web.dto.DisplayDto;
import phr.web.dto.ObservationDto;
import phr.web.form.TargetingPatientInfoForm;

/**
 *
 * @author KISNOTE011
 */
@Controller
@RequestMapping({ActionConst.TARGETING_PATIENT_INFO_ACTION})
public class TargetingPatientInfoController {

    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(TargetingPatientInfoController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.TARGETING_PATIENT_INFO_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.TARGETING_PATIENT_INFO_FORM_TITLE;

    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;
    
       /**
     * インジェクション：ユーザ認証サービス
     */
    @Autowired
    @Qualifier("TargetingPatientInfoService")
    private ITargetingPatientInfoService targetingPatientInfoService;
    
    private List<ObservationDto> observationList;
    
    /**
     * インジェクション：ワンタイムパスワードサービス
     */
    @Autowired
    @Qualifier("OnetimePasswordService")
    private IOnetimePasswordService onetimePasswordService;
    
    /*
    * 日付処理
    */
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");    /**
     /*
    * 日付処理
    */
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");    /**

 * <pre>初期アクションメソッド</pre>
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET })
    public String defaultAction(Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //画面初期化
        TargetingPatientInfoForm form = new TargetingPatientInfoForm();
        this.observationList = new ArrayList<ObservationDto>();
//        OnetimePasswordService.OneTimePasswordResult patientInfo =(OnetimePasswordService.OneTimePasswordResult) sessionUtility.getSession(SessionConst.VIEW_PASSWORD_INFO);

//        String targetphrid = null;
//        String insurerNo = null;
//        if (patientInfo != null) {
//            targetphrid = patientInfo.getPHR_ID();
//            insurerNo = targetingPatientInfoService.getInsurerPatientInfo(targetphrid);
//            // 患者情報をセットする
//            form = setPatientInfo(form ,targetphrid);
//            patientInfo.setName(form.getName());
//            sessionUtility.setSession(SessionConst.VIEW_PASSWORD_INFO, patientInfo);
//        } else {
//            targetphrid = (String) sessionUtility.getSession(SessionConst.TARGET_PHRID);
//            insurerNo = targetingPatientInfoService.getInsurerPatientInfo(targetphrid);
//            OnetimePasswordService.OneTimePasswordResult pInfo = onetimePasswordService.searchPassword(null, insurerNo, null);
//            pInfo.setPHR_ID(targetphrid);
//            sessionUtility.setSession(SessionConst.VIEW_PASSWORD_INFO, pInfo);
//            form = setPatientInfo(form ,targetphrid);
//        }
        
        MedicalOrganizationPatientEntity patientEntiy = (MedicalOrganizationPatientEntity) sessionUtility.getSession(SessionConst.AGREE_PATIET_INFO);
        String targetphrid = patientEntiy.getPHR_ID();
//        String insurerNo = patientEntiy.getMedicalOrganizationCd();
        String insurerNo = targetingPatientInfoService.getInsurerPatientInfo(targetphrid);
        form = setPatientInfo(form ,targetphrid);
        
        //2017.1.18 テーブルが盛大に変更になったので
        //盛大に変更する。昔のものは残さないようにする
        //Viewのリストを作成する。
        List<InsurerViewDefinitionEntity> viewList = targetingPatientInfoService.getViewList(insurerNo);
        form.setViewList(viewList);
        
        //表示するViewを取得する
        InsurerViewDefinitionEntity targetEntity = null;
        for(InsurerViewDefinitionEntity target : viewList){
            if(target.getViewId() >= 100){
                targetEntity = target;
                break;
            }
            
        }
        makeView(form , targetEntity , insurerNo);
                
        //検査項目を取得する
//        form = getObservation(targetphrid,diseaseList,form);
        //表示情報を取得する
//        List<DisplayDto> displayList = makeList(this.getObservationList());
//        form.setDisplayList(displayList);
        //表示条件情報を取得する
//        List<DisplayDto> conditionList = makeConditionList(this.getObservationList(),diseaseList);
//        form.setConditionList(conditionList);

        List<ObservationDefinitionInsurerEntity> allListJlac10 = form.getObservationList().stream().sorted(new Comparator<ObservationDefinitionInsurerEntity>(){
            @Override
            public int compare(ObservationDefinitionInsurerEntity o1, ObservationDefinitionInsurerEntity o2) {
                String sto1 = o1.getDefaultJLAC10() + o1.getObservationDefinitionId();
                String sto2 = o2.getDefaultJLAC10() + o2.getObservationDefinitionId();
                return sto1.compareTo(sto2);
            }
        }).collect(Collectors.toList());
        
        form.setAllDiseaseListOrderByJlac10(allListJlac10);
        
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.TARGETING_PATIENT_INFO_ACTION);
    }

    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model,TargetingPatientInfoForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
        logger.debug("End");
    }

    /*
    * 患者情報の取得及びformへのセットを行う
    */
    private TargetingPatientInfoForm setPatientInfo(TargetingPatientInfoForm form, String targetphrid) {
        
        PatientEntity entity = new PatientEntity();
        
        try {
            entity = targetingPatientInfoService.getPatientInfo(targetphrid);
        } catch (Throwable ex) {
            Logger.getLogger(TargetingPatientInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //PHRIDの処理
        form.setPhrid(targetphrid);
        
        //氏名の処理
        String fname = entity.getFamilyName();
        String gname = entity.getGivenName();
        form.setName(fname + " " + gname);
    
        //カナ氏名の処理
        String fkname = entity.getFamilyKana();
        String gkname = entity.getGivenKana();
        form.setKananame(fkname + " " + gkname);

        //生年月日の処理
        String bithday =  sdf.format(entity.getBirthDate());
        form.setBirthday(bithday);
        
        //性別の処理
        String sexcd = entity.getSexCd();
        if(sexcd.equals("M")){
            form.setSex("男性");
        }else if(sexcd.equals("F")){
            form.setSex("女性");
        }else{
            form.setSex("不明");
        }
        
        //郵便番号の処理
        String zipcode = entity.getZipCode();
        if (!TypeUtility.isNullOrEmpty(zipcode) && zipcode.length() == 7) {
            String fcode = zipcode.substring(0, 3);
            String bcode = zipcode.substring(3, 7);
            zipcode = "〒" + fcode + "-" + bcode;
            form.setZipcode(zipcode);
        }

        //住所の処理
        String code = entity.getPrefectureCd();
        String pregecture = PrefectureCdEnum.selectName(code);
        
        String adressLine = entity.getAddressLine();
        if(TypeUtility.isNullOrEmpty(adressLine)){
            adressLine="";
        }
        String buildingname = entity.getBuildingName();
        if(TypeUtility.isNullOrEmpty(buildingname)){
            buildingname="";
        }
        form.setAddress(pregecture + "<br/> " + adressLine + "<br/> " + buildingname );
        
        //電話番号
        form.setTelnumber(entity.getTelNo());
        
        //基準日
        Date base = new Date();
        form.setBasedate(sdf.format(base));
        
        return form;
    }

    private List<DisplayDto> makeList(List<ObservationDto> observationList) {
        List<DisplayDto> displayList = new ArrayList<DisplayDto>();
        Map<Integer,String> itemmap = new TreeMap<Integer,String>();
        
        for(ObservationDto dto : observationList){
            if(dto.getDiseaseTypeCd() == 0) continue;
            int key = dto.getDiseaseTypeCd();
            String kind = dto.getKind();
            String value;
            if(itemmap.containsKey(key)){
                value = kind + "," + itemmap.get(key);
            }else{
                value = kind;
            }
            itemmap.put(key, value);
        }
        
        for(Integer key :itemmap.keySet()){
            DisplayDto dto = new DisplayDto();
            dto.setKey(String.valueOf(key));
            dto.setValue(itemmap.get(key));
       
            displayList.add(dto);
        }
        
        return displayList;
    }

    private List<DisplayDto> makeConditionList(List<ObservationDto> observationList, List<DiseaseDto> diseaseList) {
        List<DisplayDto> displayList = new ArrayList<DisplayDto>();
        Map<String,String> itemmap = new TreeMap<String,String>();
        Map<Integer,String> diseasemap = new TreeMap<Integer,String>();

        for(DiseaseDto dto : diseaseList){
            diseasemap.put(dto.getTypecd(),dto.getId());
        }

        for(ObservationDto dto : observationList){
            if(dto.getDiseaseTypeCd() == 0) continue;
            String key = dto.getId();
            String kind = String.valueOf(dto.getDiseaseTypeCd());
            String value;
            if(itemmap.containsKey(key)){
                value = kind + "," + itemmap.get(key);
            }else{
                value = kind;
            }
            itemmap.put(key, value);
        }

        for(String key :itemmap.keySet()){
            DisplayDto dto = new DisplayDto();
            dto.setKey(String.valueOf(key));
            dto.setValue(itemmap.get(key));

            displayList.add(dto);
        }

        return displayList;
    }

    private List<DiseaseDto> getDisease(int viewId) throws Throwable {
        List<DiseaseTypeEntity> dlist = targetingPatientInfoService.getDiseaseTypeList(viewId);
        List<DiseaseDto> diseaseList = new ArrayList<DiseaseDto>();
        
        for(DiseaseTypeEntity entity:dlist){
            DiseaseDto dto = new DiseaseDto();
            if(entity.getDiseaseTypeCd() == 0) continue;
            dto.setName(entity.getName());
            dto.setTypecd(entity.getDiseaseTypeCd());
            dto.setId(entity.getObservationDefinitionId());
            diseaseList.add(dto);
        }
        
        return diseaseList;
    }

    /**
     * @return the observationList
     */
    public List<ObservationDto> getObservationList() {
        return observationList;
    }

    /**
     * @param observationList the observationList to set
     */
    public void setObservationList(List<ObservationDto> observationList) {
        this.observationList = observationList;
    }

    private void makeView(TargetingPatientInfoForm form, InsurerViewDefinitionEntity targetEntity, String insurerNo) {
        int viewId = targetEntity.getViewId();
        
        form.setSelectView(viewId);
        List<DiseaseTypeEntity> diseaseList = new ArrayList<>();
        List<DiseaseTypeEntity> diseaseLabelList = new ArrayList<>();
        List<ObservationDefinitionInsurerEntity> itemList = new ArrayList<>();
        
        try {
            //管理項目を取得する
            diseaseList = targetingPatientInfoService.getDiseaseTypeList(viewId);
            //共通があるので管理項目から共通は排除する
            if(diseaseList != null && diseaseList.size() > 0){
                for(DiseaseTypeEntity d_entity : diseaseList){
                    if(d_entity.getObservationDefinitionId() != null) diseaseLabelList.add(d_entity);
                }
                form.setDiseaseList(diseaseLabelList);
            }
            
            //Viewの検査項目を取得する
            itemList = targetingPatientInfoService.getitemList(viewId);
            setObservation(form , itemList , diseaseList);
            
            form.setKateiList(targetingPatientInfoService.getitemTypeList(insurerNo, 2));
            form.setKensinOList(targetingPatientInfoService.getitemTypeList(insurerNo, 3));
            form.setKensinQList(targetingPatientInfoService.getitemTypeList(insurerNo, 4));
            form.setKensinSList(targetingPatientInfoService.getitemTypeList(insurerNo, 5));
            
        } catch (Throwable ex) {
            Logger.getLogger(TargetingPatientInfoController.class.getName()).log(Level.SEVERE, null, ex);
            logger.debug("" , ex);
        }
    }

    //表示非表示制御用リスト作成
    private void setObservation(TargetingPatientInfoForm form, List<ObservationDefinitionInsurerEntity> itemList, List<DiseaseTypeEntity> diseaseList) {

        //画面表示用のリスト
        List<ObservationDefinitionInsurerEntity> labelList = new ArrayList<>();
        
        //重複確認用のリスト
        Map<String, List<Integer>> overlapMap = new LinkedHashMap<>();
        
        Map<Integer , String > displayMap = new HashMap<>();
        Map<String , String > conditionMap = new HashMap<>();
        for(ObservationDefinitionInsurerEntity itemEntity : itemList){
            String id = itemEntity.getObservationDefinitionId();
            Integer typeCd = itemEntity.getDiseaseTypeCd();
            if(!overlapMap.containsKey(id)){
                labelList.add(itemEntity);
                //共通の場合は表示表示の対象にしないのでとばす
                if(typeCd == 0) continue;
                
                overlapMap.put(id, new ArrayList<>());
                overlapMap.get(id).add(typeCd);
            } else {
                overlapMap.get(id).add(typeCd);
            }
        }
        Map<String, List<Integer>> tempMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<Integer>> e : overlapMap.entrySet()) {
            boolean isSet = true;
            for (DiseaseTypeEntity entity : diseaseList) {
                if (entity.getDiseaseTypeCd() == 0) continue;
                
                if (!e.getValue().contains(entity.getDiseaseTypeCd())) {
                    isSet = false;
                    break;
                }
            }
            if (!isSet) {
                tempMap.put(e.getKey(), e.getValue());
            }
        }
        
        for (Map.Entry<String, List<Integer>> e : tempMap.entrySet()) {
        //for(ObservationDefinitionInsurerEntity itemEntity : itemList){
            String id = e.getKey();
            for (Integer typeCd : e.getValue()) {
                //Integer typeCd = itemEntity.getDiseaseTypeCd();
                //共通の場合は表示表示の対象にしないのでとばす
                if(typeCd == 0) continue;

                if(displayMap.containsKey(typeCd)){
                    String target = displayMap.get(typeCd);
                    target += "," + id + "_kind";
                    displayMap.put(typeCd, target);
                }else{
                    String target = id + "_kind";
                    displayMap.put(typeCd, target);
                }

                if(conditionMap.containsKey(id)){
                    String target = conditionMap.get(id);
                    target += "," + typeCd.toString();
                    conditionMap.put(id, target);
                }else{
                    String target = typeCd.toString();
                    conditionMap.put(id, target);
                }
            }
        }
        
        //ラベルリストを設定
        form.setObservationList(labelList);
        
        //表示条件の設定
        List<DisplayDto> displayList = new ArrayList<>();
        for(Integer key : displayMap.keySet()){
            DisplayDto dto = new DisplayDto();
            dto.setKey(key.toString());
            dto.setValue(displayMap.get(key));
            displayList.add(dto);
        }
        
        form.setDisplayList(displayList);
        
        //DefinitionId毎の条件設定
        List<DisplayDto> conditionList = new ArrayList<>();
        for(String c_key : conditionMap.keySet()){
            DisplayDto dto = new DisplayDto();
            dto.setKey(c_key);
            dto.setValue(conditionMap.get(c_key));
            conditionList.add(dto);
        }
        
        form.setConditionList(conditionList);

    }

    /**
     * View変更時のアクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=changeview")
    public String changeViewAction(@ModelAttribute @Valid TargetingPatientInfoForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        int viewId = form.getSelectView();
//        OnetimePasswordService.OneTimePasswordResult patientInfo =(OnetimePasswordService.OneTimePasswordResult) sessionUtility.getSession(SessionConst.VIEW_PASSWORD_INFO);
        MedicalOrganizationPatientEntity patientInfo=(MedicalOrganizationPatientEntity)sessionUtility.getSession(SessionConst.AGREE_PATIET_INFO);
        String targetphrId = patientInfo.getPHR_ID();
        String insurerNo = targetingPatientInfoService.getInsurerPatientInfo(targetphrId);
//        String insurerNo = patientInfo.getMedicalOrganizationCd();
        form = setPatientInfo(form ,targetphrId);

        InsurerViewDefinitionEntity targetEntity = new InsurerViewDefinitionEntity();
        targetEntity.setViewId(viewId);
        makeView(form , targetEntity , insurerNo);
        List<InsurerViewDefinitionEntity> viewList = targetingPatientInfoService.getViewList(insurerNo);        
        form.setViewList(viewList);
        
        this.initPage(model,form);
   	model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.TARGETING_PATIENT_INFO_ACTION);

    }
}
