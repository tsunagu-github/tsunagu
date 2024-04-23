/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import phr.common.Calculate;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.service.IMedicalCreateEditService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.form.MedicalCreateEditForm;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.service.IMedicalSearchService;
import phr.web.SessionConst;
import phr.web.form.UserManageEditForm;

/**
 *
 * @author kis.o-note-002
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.MEDICAL_CREATE_EDIT_ACTION, "/"})
public class MedicalCreateEditController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalCreateEditController.class);
    /**
     * ログインエラー
     */
    private static final int LOGIN_ERROER = 0;

    /**
     * 利用可能期間外エラー
     */
    private static final int INVALID_TERM = 1;
    
    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;
    
    /**
     * インジェクション：各種マスタサービス処理
     */
    @Autowired
    @Qualifier("MedicalCreateEditService")
    private IMedicalCreateEditService medicalCreateEditService;
    
    /**
     * インジェクション：各種マスタサービス処理
     */
    @Autowired
    @Qualifier("MedicalSearchService")
    private IMedicalSearchService medicalSearchService;

    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(MedicalCreateEditForm form, Model model, HttpServletRequest request , BindingResult result) throws Throwable {

        logger.debug("Start");
        //ログインサイトを設定
        sessionUtility.setSession("SessionConst.SITE", null);

        MedicalOrganizationEntity entity = (MedicalOrganizationEntity) sessionUtility.getSession(SessionConst.MEDICAL_INFO);

        // 更新の時は登録されている内容を初期表示の段階で表示させる。
        if(entity.getMedicalOrganizationCd() == null){
            form.setEditFlg(false);
        } else {
            form.setEditFlg(true);
            form.setMedicalCd(entity.getMedicalOrganizationCd());
            form.setMedicalName(entity.getMedicalOrganizationName());
            form.setTelNo(entity.getTelNo());
            form.setZipCode(entity.getZipCode());
            form.setAddress(entity.getAddress());
        }
        //画面初期化
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MEDICAL_CREATE_EDIT_ACTION);
    }
    /**
     * <pre>確認ボタンクリック時の処理</pre>
     * @param form
     * @param model
     * @param request
     * @param result
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=confirm")
    public String confirmAction(MedicalCreateEditForm form, Model model, HttpServletRequest request,BindingResult result) throws Throwable {

        logger.debug("Start");
        if(this.formErrorCheck(form,result) ){   
            // セッションに登録内容をセットする。
            MedicalOrganizationEntity entity = new MedicalOrganizationEntity();
            if(form.isEditFlg()){
                entity = (MedicalOrganizationEntity) sessionUtility.getSession(SessionConst.MEDICAL_INFO);
            }else{
                entity.setMedicalOrganizationCd(form.getMedicalCd());
            }
            entity.setMedicalOrganizationName(form.getMedicalName());
            entity.setZipCode(form.getZipCode());
            entity.setAddress(form.getAddress());
            entity.setTelNo(form.getTelNo());
            if((form.isChangeFlg() && form.isEditFlg()) || !form.isEditFlg()){
                entity.setInitPassword(false);
                entity.setDecriptPassword(form.getPassword());
            }
            entity.setUpdateFlg(form.isEditFlg());
            sessionUtility.setSession(SessionConst.MEDICAL_INFO, entity);
            
        } else {
            logger.debug("必須項目が未入力のためエラー");
            this.initPage(model, form);
            logger.debug("End");
            return ActionConst.actionPage(ActionConst.MEDICAL_CREATE_EDIT_ACTION);
            
        }
        
        
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.MEDICAL_CREATE_CONFIRM_ACTION);
    }
       
    
     /**
     * 戻るボタンクリック時の処理
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=return")
    public String returnAction(@ModelAttribute @Valid MedicalCreateEditForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable{
        logger.debug("Start");
        
                
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.MEDICAL_SEARCH_ACTION);
    }
    /**
     * <pre>入力内容のチェック</pre>
     * @param form
     * @param result
     * @return
     * @throws Throwable 
     */
    private boolean formErrorCheck(MedicalCreateEditForm form ,BindingResult result) throws Throwable {

        // 新規登録時、エラーチェック
        MedicalOrganizationEntity entity = medicalSearchService.getMedicalOrganizationInfo(form.getMedicalCd());

        if(entity != null){
            ObjectError error = new ObjectError("search", "登録済みの医療機関CDです。");
            result.addError(error);
        }
        
        //--- 必須チェック start---//
        /**
         * 医療機関コードは新規登録時のみチェックする
         */
        if(!form.isEditFlg()){
            if(form.getMedicalCd()==null || form.getMedicalCd().length()==0){
                ObjectError error = new ObjectError("search", "医療機関名称CDを入力して下さい。");
                result.addError(error);
            }
        }
        if(form.getMedicalName()==null || form.getMedicalName().length()==0){
            ObjectError error = new ObjectError("search", "医療機関名称を入力して下さい。");
            result.addError(error);
        }
        if(form.getZipCode()==null || form.getZipCode().length()==0){
            ObjectError error = new ObjectError("search", "郵便番号を入力して下さい。");
            result.addError(error);
        }
        if(form.getAddress()==null || form.getAddress().length()==0){
            ObjectError error = new ObjectError("search", "所在地を入力して下さい。");
            result.addError(error);
        }
        if(form.getTelNo()==null || form.getTelNo().length()==0){
            ObjectError error = new ObjectError("search", "電話番号を入力して下さい。");
            result.addError(error);
        }
        if(!form.isEditFlg() || (form.isChangeFlg() && form.isEditFlg())) {
            if(form.getPassword()==null || form.getPassword().length()==0){
                ObjectError error = new ObjectError("search", "パスワードを入力して下さい。");
                result.addError(error);
            }
            if(form.getInitPassword()==null || form.getInitPassword().length()==0){
                ObjectError error = new ObjectError("search", "パスワード(確認)を入力して下さい。");
                result.addError(error);
            }
            if(passwordCheckAction(form,result)){

                if(!form.getPassword().equals(form.getInitPassword())){
                        ObjectError error = new ObjectError("search", "パスワードが一致しません。");
                        result.addError(error);
                }
            }
            
        }
        //--- 必須チェック end---//

        if(result.hasErrors()){
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * パスワードのチェック
     * @param form
     * @param result
     * @return
     * @throws Throwable
     */
    public Boolean passwordCheckAction(MedicalCreateEditForm form, BindingResult result) throws Throwable {
    	logger.debug("Start");

    	String message;
        String password = form.getPassword();
        Boolean passFlg = true;
        
        if(form.isEditFlg() == true && !form.isChangeFlg()) {
            return passFlg;
        }

    	try {
            //パスワードモード
            int passwordMode=Integer.parseInt(PhrConfig.getConfigProperty(ConfigConst.PASSWORD_MODE));
            //パスワード桁数
            int passwordDigits=Integer.parseInt(PhrConfig.getConfigProperty(ConfigConst.PASSWORD_MIN_DIGITS));
            
            // パスワード文字数チェック
            if(password.length() < passwordDigits){
                message = messageSource.getMessage("passwordChange.PassDigitsShort.message", new String[]{String.valueOf(passwordDigits)}, null);
                ObjectError error = new ObjectError("newPassword", message);
                result.addError(error);
                passFlg = false;

            // パスワード文字種チェック
            }else if(passwordMode==1){
                //eiji to suuji konnzai hissu
                if(!Calculate.isMachAlphaNum(password)){
                    message = messageSource.getMessage("passwordChange.PassNumAlfa.message", null, null);
                    ObjectError error = new ObjectError("newPassword", message);
                    result.addError(error);
                    passFlg = false;
                }
            }else if(passwordMode==2){
                //eiji to suuji to kigou konnzai hissu
                if(!Calculate.isMachAlphaNumChara(password)){
                    message = messageSource.getMessage("passwordChange.PassNumAlfaChar.message", null, null);
                    ObjectError error = new ObjectError("newPassword", message);
                    result.addError(error);
                    passFlg = false;
                }
            }else if(passwordMode==3){
                //uppereiji to lowereiji to suuji to kigou konnzai hissu
                if(!Calculate.isMachUpperLowerNumChara(password)){
                    message = messageSource.getMessage("passwordChange.PassNumUpperLowerChar.message", null, null);
                    ObjectError error = new ObjectError("newPassword", message);
                    result.addError(error);
                    passFlg = false;
                }
            }
    	} catch (Throwable t) {
            logger.error(t);
            message = messageSource.getMessage("processing.error.message", new String[]{FormConst.PASSWORD_CHANGE_FORM_TITLE}, null);
            ObjectError error = new ObjectError("currenntPassword", message);
            result.addError(error);
            passFlg = false;
    	} finally {
            logger.debug("End");
    	}
        
        return passFlg;
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, MedicalCreateEditForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        model.addAttribute(form);
    }

}
