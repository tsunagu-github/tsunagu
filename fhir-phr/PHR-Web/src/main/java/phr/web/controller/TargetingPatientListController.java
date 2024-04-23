package phr.web.controller;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalOrganizationPatientService;
import phr.service.IOnetimePasswordService;
import phr.service.IPatientManagementService;
import phr.service.ITargetingPatientInfoService;
import phr.service.impl.OnetimePasswordService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.form.TargetingPatientListForm;

/**
 * 
 * @author kisvdi017
 *
 */

@Controller
@RequestMapping({ActionConst.TARGETING_PATIENT_LIST_ACTION, "/"})
public class TargetingPatientListController {

    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(TargetingPatientController.class);

    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    /**画面ID */
    private final String FORM_CD = FormConst.TARGETING_PATIENT_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.TARGETING_PATIENT_FORM_TITLE;

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
     * インジェクション：
     */
    @Autowired
    @Qualifier("TargetingPatientInfoService")
    private ITargetingPatientInfoService targetingPatientInfoService;
    
    /**
     * インジェクション：
     */
    @Autowired
    @Qualifier("MedicalOrganizationPatientService")
    private IMedicalOrganizationPatientService medicalOrganizationPatientService;
    
    /**
     *  初期アクションメソッド
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET })
    public String defaultAction(TargetingPatientListForm form, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //画面初期化
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.TARGETING_PATIENT_LIST_ACTION);
    }

    /**
     * 患者検索
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=search")
    public String searchPatient(TargetingPatientListForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        MedicalOrganizationEntity entity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);

        String insurerNo = entity.getMedicalOrganizationCd();
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
        model.addAttribute("insurerNo", entity.getMedicalOrganizationCd());
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.TARGETING_PATIENT_LIST_ACTION);
    }

    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model,TargetingPatientListForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }

    /**
     * 閲覧同意の確認
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"checkAgreesToShare"}, method = {RequestMethod.POST })
    @ResponseBody
    public String checkAgreesToShareAjax(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        logger.debug("Start");
        
        String getAllStr=null;
        //リクエストJSONの取得
        try(Reader reqReader=request.getReader();
                BufferedReader reqBufReader =  new BufferedReader(reqReader);){
            StringBuilder sBuilder = new StringBuilder();
            String getStr;
            do{
                //１行読む
                getStr = reqBufReader.readLine();
                if(getStr!=null){
                    sBuilder.append(getStr);
                }
            }while (getStr != null);
            getAllStr=sBuilder.toString();
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
        String medicalOrganizationCd = null;
        String phrId = null;
        //リクエストJSONから値の取得
        try{
            String[] spStrs=getAllStr.split(",");
            for(String item:spStrs){
                String[] keyValues=item.split(":");
                String keyValue=commonReplace(keyValues[0]);
                if(keyValue.equals("value1")){
                    //必要な値を取得
                    medicalOrganizationCd=commonReplace(keyValues[1]);
                }
                if(keyValue.equals("value2")){
                    //必要な値を取得
                    phrId=commonReplace(keyValues[1]);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
        
        // 対象患者の閲覧同意の確認
        MedicalOrganizationPatientEntity entiy = medicalOrganizationPatientService.findByPrimaryKey(medicalOrganizationCd, phrId);
        String returnJson="";
        if (entiy.getAgreesToShare()) {
            returnJson= "同意";
            sessionUtility.setSession(SessionConst.AGREE_PATIET_INFO, entiy);
        }
        
        logger.debug("End");
        return gson.toJson(returnJson);
    }
    
    private String commonReplace(String item) throws Throwable {
        String ret;
        ret=item.replaceAll("\\{", "")
                .replaceAll("\\[", "")
                .replaceAll("\\}", "")
                .replaceAll("\\]", "")
                .replaceAll("\"", "");
        return ret;
    }
    
}
