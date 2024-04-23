/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

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
import phr.common.Calculate;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.service.IUserAccountManagementService;
import phr.service.IUserAuthenticationService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.form.PasswordChangeForm;

/**
 *
 * @author KISNOTE011
 */
@Controller
@RequestMapping({ActionConst.PASSWORD_CHANGE_ACTION, ActionConst.PASSWORD_CHANGE_CONFIRM_ACTION})
public class PasswordChangeController {

    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(PasswordChangeController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.PASSWORD_CHANGE_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.PASSWORD_CHANGE_FORM_TITLE;

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
    @Qualifier("UserAuthenticationService")
    private IUserAuthenticationService userAuthenticationService;

    /**
     * インジェクション：ユーザアカウントマネージメントサービス
     */
    @Autowired
    @Qualifier("UserAccountManagementService")
    private IUserAccountManagementService userAccountManagementService;
    
    /**
     * <pre>初期アクションメソッド</pre>
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET })
    public String defaultAction(Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");

        AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity mentity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
   	PasswordChangeForm form = new PasswordChangeForm();
   	//初期化
   	this.initPage(model,form);
        if(entity != null){
            form.setUserId(entity.getLoginId());
        }else{
            form.setUserId(mentity.getMedicalOrganizationCd());
        }
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.PASSWORD_CHANGE_ACTION);
    }

    /**
     * 変更ボタン押下時のアクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=passwordChange")
    public String passwordChangeAction(@ModelAttribute @Valid PasswordChangeForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");
        
    	//アクセスログ
//    	AccesslLogBackUpUtility.getAccessLog(request,Thread.currentThread().getStackTrace()[1],FORM_CD,FORM_NAME,form.getCommand());

    	//画面初期化
    	this.initPage(model,form);

    	String message;
    	String actionPage = ActionConst.actionPage(ActionConst.PASSWORD_CHANGE_ACTION);

    	AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
    	MedicalOrganizationEntity mentity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);

        if (result.hasErrors()) {
    		return actionPage;
    	}

    	try {
            // 新しいパスワード・新しいパスワード(確認)の一致チェック
            if (!form.getNewPassword().equals(form.getConfirmPassword())) {
                message = messageSource.getMessage("passwordChange.passwordNotequal.message", null, null);
                ObjectError error = new ObjectError("newPassword", message);
                result.addError(error);
            }else{
                //パスワードモード
                int passwordMode=Integer.parseInt(PhrConfig.getConfigProperty(ConfigConst.PASSWORD_MODE));

                //パスワード桁数
                int passwordDigits=Integer.parseInt(PhrConfig.getConfigProperty(ConfigConst.PASSWORD_MIN_DIGITS));
//                String passwordDigitsString =PhrConfig.getConfigProperty(ConfigConst.PASSWORD_MIN_DIGITS);

                // パスワード文字数チェック
                if(form.getNewPassword().length() < passwordDigits){
                    message = messageSource.getMessage("passwordChange.PassDigitsShort.message", new String[]{String.valueOf(passwordDigits)}, null);
                    ObjectError error = new ObjectError("newPassword", message);
                    result.addError(error);

                // パスワード文字種チェック
                }else if(passwordMode==1){
                    //eiji to suuji konnzai hissu
                    if(!Calculate.isMachAlphaNum(form.getNewPassword())){
                        message = messageSource.getMessage("passwordChange.PassNumAlfa.message", null, null);
                        ObjectError error = new ObjectError("newPassword", message);
                        result.addError(error);
                    }
                }else if(passwordMode==2){
                    //eiji to suuji to kigou konnzai hissu
                    if(!Calculate.isMachAlphaNumChara(form.getNewPassword())){
                        message = messageSource.getMessage("passwordChange.PassNumAlfaChar.message", null, null);
                        ObjectError error = new ObjectError("newPassword", message);
                        result.addError(error);
                    }
                }else if(passwordMode==3){
                    //uppereiji to lowereiji to suuji to kigou konnzai hissu
                    if(!Calculate.isMachUpperLowerNumChara(form.getNewPassword())){
                        message = messageSource.getMessage("passwordChange.PassNumUpperLowerChar.message", null, null);
                        ObjectError error = new ObjectError("newPassword", message);
                        result.addError(error);
                    }
                }
                // 現在のパスワード一致チェック
                if(entity!= null){
                    if(!userAuthenticationService.chkCurrentPass(entity,form.getCurrenntPassword())){
                        message = messageSource.getMessage("passwordChange.currenntPasswordNotMatch.message", null, null);
                        ObjectError error = new ObjectError("currenntPassword", message);
                        result.addError(error);
                    }
                }else{
                    if(!userAuthenticationService.chkCurrentPassMedical(mentity,form.getCurrenntPassword())){
                        message = messageSource.getMessage("passwordChange.currenntPasswordNotMatch.message", null, null);
                        ObjectError error = new ObjectError("currenntPassword", message);
                        result.addError(error);
                    }
                }
                //同一パスワードでの変更許可のチェック
                boolean bSamePwSet=Boolean.valueOf(PhrConfig.getConfigProperty(ConfigConst.SAME_PW_SETTINGS));
                if(!bSamePwSet && form.getNewPassword().equals(form.getCurrenntPassword())){
                    message = messageSource.getMessage("passwordChange.passwordNotsame.message", null, null);
                    ObjectError error = new ObjectError("newPassword", message);
                    result.addError(error);
                }
            }

            if (result.hasErrors()) {
                return actionPage;
            }else{
                //パスワードの変更
                if(entity != null){
                    entity.setInitPassword(false);
                    AccountEntity passwordChengedEntity;
                    passwordChengedEntity = userAccountManagementService.passwordChange(entity,form.getNewPassword());
                    if(passwordChengedEntity == null){
                        message = messageSource.getMessage("processing.error.message", new String[]{FormConst.PASSWORD_CHANGE_FORM_TITLE}, null);
                        ObjectError error = new ObjectError("currenntPassword", message);
                        result.addError(error);
                    }else{
    //                    passwordChengedEntity.setClinicalGroupCode(entity.getClinicalGroupCode());
    //                    passwordChengedEntity.setClinicalGroupName(entity.getClinicalGroupName());
                        //ユーザアカウント情報が変更になったためヘッダー読直し
                        sessionUtility.setSession(SessionConst.ACCOUNT_ENTITY,passwordChengedEntity);
                        ControllerUtility.InitHeader(model);
                        actionPage = ActionConst.actionPage(ActionConst.PASSWORD_CHANGE_CONFIRM_ACTION);
                    }
                }else{
                    MedicalOrganizationEntity passwordChengedEntity;
                    passwordChengedEntity = userAccountManagementService.passwordChangeMedical(mentity,form.getNewPassword());
                    if(passwordChengedEntity == null){
                        message = messageSource.getMessage("processing.error.message", new String[]{FormConst.PASSWORD_CHANGE_FORM_TITLE}, null);
                        ObjectError error = new ObjectError("currenntPassword", message);
                        result.addError(error);
                    }else{
    //                    passwordChengedEntity.setClinicalGroupCode(entity.getClinicalGroupCode());
    //                    passwordChengedEntity.setClinicalGroupName(entity.getClinicalGroupName());
                        //ユーザアカウント情報が変更になったためヘッダー読直し
                        sessionUtility.setSession(SessionConst.MEDICALACCOUNT_ENTITY,passwordChengedEntity);
                        ControllerUtility.InitHeader(model);
                        actionPage = ActionConst.actionPage(ActionConst.PASSWORD_CHANGE_CONFIRM_ACTION);
                    }                  
                }
            }

    	} catch (Throwable t) {
            logger.error(t);
            message = messageSource.getMessage("processing.error.message", new String[]{FormConst.PASSWORD_CHANGE_FORM_TITLE}, null);
            ObjectError error = new ObjectError("currenntPassword", message);
            result.addError(error);
    	} finally {
            logger.debug("End");
    	}
    	return actionPage;
    }
    
    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model,PasswordChangeForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
