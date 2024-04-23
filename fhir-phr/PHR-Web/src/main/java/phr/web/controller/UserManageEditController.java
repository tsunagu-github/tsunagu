/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
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
import phr.datadomain.adapter.AccountAdapter;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.service.IUserManageEditService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.dto.UserManageDto;
import phr.web.form.UserManageEditForm;

/**
 *
 * @author kis.o-note-003
 */
@Controller
@RequestMapping({ActionConst.USER_MANAGE_EDIT_ACTION})
public class UserManageEditController {
    /**ロギングコンポーネント    */
    private static final Log logger = LogFactory.getLog(TargetingPatientController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.USER_MANAGE_EDIT_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.USER_MANAGE_EDIT_FORM_TITLE;

    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * インジェクション：ユーザ管理サービス
     */
    @Autowired
    @Qualifier("UserManageEditService")
    private IUserManageEditService userManageEditService;
    
    /**
     * 初期アクションメソッド
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET })
    public String defaultAction(Model model,HttpServletRequest request) throws Throwable {
   	logger.debug("Start");

        //ログインサイトを設定
        sessionUtility.setSession("SessionConst.SITE", null);
        
   	//画面初期化
   	UserManageEditForm form = new UserManageEditForm();
        AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.USER_INFO);
        AccountEntity loginAccount = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        
        //新規登録
        if (entity.getAccountId() == null) {
            form.setEditFlg(false);
        //修正
        } else {
            form.setLoginId(entity.getLoginId());
            form.setUserName(entity.getName());
            form.setAccountType(entity.getAccoutTypeCd());
            form.setInsurerNo(entity.getInsurerNo());
            form.setInvalidFlg(entity.isInvalid());
            form.setEditFlg(true);
            form.setOneTimePassAuth(entity.getOneTimePassAuth());
        }
        
        String insurerNo = loginAccount.getInsurerNo();
        String insurerName = insurerNo +" : " + userManageEditService.getInsurer(insurerNo);

        entity.setInsurerName(insurerName);
        /**
         * 保険者によるアカウント登録は自身の保健者のみとする
         * システム管理者を実装したときは、システム管理者で保健者指定を可能とする
         */
        entity.setInsurerNo(insurerNo);
        
        sessionUtility.setSession(SessionConst.USER_INFO, entity);
        form.setInsurerName(insurerName);
        
        model.addAttribute("buttonLabel", "確認");
   	this.initPage(model,form);
   	model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.USER_MANAGE_EDIT_ACTION);
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
    @RequestMapping(method = {RequestMethod.POST }, params = "command=confirm")
    public String confirmAction(@ModelAttribute @Valid UserManageEditForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.USER_INFO);
        Date today = new Date();
        Timestamp todaystamp = new Timestamp(today.getTime());

        if(this.formErrorCheck(form, result) ){
            //ユーザ登録情報作成0
            AccountEntity loginEntity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
            
            entity.setName(form.getUserName());
            entity.setInvalid(form.getInvalidFlg());
            entity.setUpdateAccoutId(loginEntity.getAccountId());
            entity.setOneTimePassAuth(form.getOneTimePassAuth());
            if (entity.getAccountId() == null) {
                entity.setLoginId(form.getLoginId());
                entity.setAccoutTypeCd(form.getAccountType());
                entity.setInitPassword(true);
                entity.setDecriptPassword(form.getPassword());
                entity.setCreateAccoutId(loginEntity.getAccountId());
                entity.setCreateDateTime(todaystamp); 
                entity.setPasswordExpirationDate(today);
            } else {
                entity.setAccountId(entity.getAccountId());
                entity.setAccoutTypeCd(form.getAccountType());
                String pass = entity.getDecriptPassword();
                if (form.isChangeflg()) {
                    entity.setInitPassword(true);
                    entity.setDecriptPassword(form.getPassword());
                    entity.setPasswordExpirationDate(today);
                }else{
                    entity.setDecriptPassword(pass);
                }
            }
            sessionUtility.setSession(SessionConst.USER_INFO, entity);
            
        } else {
            logger.debug("必須項目に未入力があるためエラー");
            form.setInsurerName(entity.getInsurerName());
            this.initPage(model, form);
            return ActionConst.actionPage(ActionConst.USER_MANAGE_EDIT_ACTION);
        }
                
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.USER_MANAGE_CONFIRM_ACTION);
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
    public String returnAction(@ModelAttribute @Valid UserManageEditForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable{
        logger.debug("Start");
        
                
        this.initPage(model, form);
        logger.debug("End");
        return ActionConst.redirectActionPage(ActionConst.USER_MANAGE_ACTION);
    }
       
    /**
     * パスワードのチェック
     * @param form
     * @param result
     * @return
     * @throws Throwable
     */
    public Boolean passwordCheckAction(UserManageEditForm form, BindingResult result) throws Throwable {
    	logger.debug("Start");
        
    	String message;
        String password = form.getPassword();
        Boolean passFlg = true;

        if(form.getEditFlg() == true && (password == null || "".equals(password))) {
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
     * 入力内容のチェック
     * @param form
     * @param result
     * @return
     * @throws Throwable 
     */
    private  boolean formErrorCheck(UserManageEditForm form ,BindingResult result) throws Throwable {

        // 新規登録時、エラーチェック
        AccountEntity dto = (AccountEntity)sessionUtility.getSession(SessionConst.USER_INFO);
        if (dto.getAccountId() == null) {
            AccountEntity entity = this.userManageEditService.getUserInfo(form.getLoginId());
            if(entity != null){
                ObjectError error = new ObjectError("search", "入力されたログインIDはすでに使用されています。");
                result.addError(error);
            }
        }
        
        //--- 必須チェック start---// 
        if(form.getLoginId()==null || form.getLoginId().length()==0){
            ObjectError error = new ObjectError("search", "ログインIDを入力して下さい。");
            result.addError(error);
        }
        if(form.getUserName()==null || form.getUserName().length()==0){
            ObjectError error = new ObjectError("search", "氏名を入力してください。");
            result.addError(error);
        }
        /**
         * 別の保険者のアカウントは作成できなくしたのでコメントアウトにする
         * 最終的にシステム管理者を作ったときには必要
         */
/*
        if(form.getInsurerNo()==null || form.getInsurerNo().length()==0){
            ObjectError error = new ObjectError("search", "所属保険者を選択してください。");
            result.addError(error);
        }
*/
        if(form.getAccountType()!=1 && form.getAccountType()!=2){
            ObjectError error = new ObjectError("search", "アカウント種別を選択してください。");
            result.addError(error);
        }
        
        if (!form.getEditFlg() || (form.getEditFlg() && form.isChangeflg())) {
            if(form.getPassword()==null || form.getPassword().length()==0){
                ObjectError error = new ObjectError("search", "パスワードを入力して下さい。");
                result.addError(error);
            }
            if(form.getcPassword()==null || form.getcPassword().length()==0){
                ObjectError error = new ObjectError("search", "パスワード(確認)を入力して下さい。");
                result.addError(error);
            }
            
            if(this.passwordCheckAction(form, result)){
                if(!form.getPassword().equals(form.getcPassword())){
                    ObjectError error = new ObjectError("search", "パスワードが一致しません。");
                    result.addError(error);
                }
            }
        }
        
        if(form.getInvalidFlg()==null){
            ObjectError error = new ObjectError("search", "ステータスを選択してください。");
            result.addError(error);
        }
        //--- 必須チェック end---// 
        return !result.hasErrors();
    }
    
    /**
     * 登録後ユーザ管理画面に遷移するメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=redirect")
    public String redirectAction(@ModelAttribute @Valid UserManageEditForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {

        //次に遷移するページ
        String actionPage = ActionConst.redirectActionPage(ActionConst.USER_MANAGE_ACTION);

        return actionPage;
    }
    
    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model,UserManageEditForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
