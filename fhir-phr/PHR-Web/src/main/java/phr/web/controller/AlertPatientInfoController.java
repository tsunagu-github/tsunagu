/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.PatientEntity;
import phr.enums.PrefectureCdEnum;
import phr.service.IAlertSearchService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.form.AlertPatientInfoForm;

/**
 *
 * @author KISO-NOTE-005
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.ALERT_PATIENT_INFO_ACTION, "/"})
public class AlertPatientInfoController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AlertSearchController.class);
    
    /**画面ID */
    private final String FORM_CD = FormConst.ALERT_PATIENT_INFO_ACTION_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.ALERT_PATIENT_INFO_ACTION_TITLE;
    
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
     * インジェクション：アラート患者情報検索機能サービス
     */
    @Autowired
    @Qualifier("AlertSearchService")
    private IAlertSearchService alertSearchService;
    
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
    @RequestMapping(method = {RequestMethod.POST})
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        AlertPatientInfoForm form = new AlertPatientInfoForm();
        String phrId = request.getParameter("param1");
//        StringBuilder sb = new StringBuilder(phrId);
//        sb.setCharAt(7,'-');
//        phrId = sb.toString();
        
        PatientEntity entity = this.alertSearchService.patientInfoSearch(phrId);
        
        //PHRIDの処理
        form.setPhrid(phrId);
        
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
        String fcode = zipcode.substring(0, 2);
        String bcode = zipcode.substring(3, 6);
        zipcode = "〒" + fcode + "-" + bcode;
        form.setZipcode(zipcode);

        //住所の処理
        String code = entity.getPrefectureCd();
        String pregecture = PrefectureCdEnum.selectName(code);
        
        String adressLine = entity.getAddressLine();
        String buildingname = entity.getBuildingName();
        form.setAddress(pregecture + "<br/> " + adressLine + "<br/> " + buildingname );
        
        //電話番号
        form.setTelnumber(entity.getTelNo());
        
        //画面初期化
        this.initPage(model, form);
        model.addAttribute(form);
        logger.debug("End");

        return ActionConst.actionPage(ActionConst.ALERT_PATIENT_INFO_ACTION);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, AlertPatientInfoForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
}
