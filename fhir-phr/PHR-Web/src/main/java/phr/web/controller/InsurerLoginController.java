/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.BitSet;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import phr.web.ActionConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;

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
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.AccoutTypeCd;
import phr.service.IAccessLogsService;
import phr.service.IUserAuthenticationService;
import phr.web.FormConst;
import phr.web.ValidationMessageConst;
import phr.web.form.InsurerLoginForm;
import phr.web.PhrWebConfig;
import phr.web.PhrWebConst;

/**
 *
 * 保険者ログイン画面コントローラ
 *
 * @author KISNOTE011
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.INSURER_LOGIN_ACTION, "/"})
public class InsurerLoginController {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerLoginController.class);

    /**
     * ログインエラー
     */
    private static final int LOGIN_ERROER = 0;

    /**
     * 利用可能期間外エラー
     */
    private static final int INVALID_TERM = 1;

    /**画面CD */
    private final String FORM_CD = FormConst.INSURER_LOGIN_FORM_CD;
    /**画面タイトル */
    private final String FORM_NAME = FormConst.INSURER_LOGIN_FORM_TITLE;

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
     * インジェクション：アクセスログサービス
     */
    @Autowired
    @Qualifier("AccessLogsService")
    private IAccessLogsService accessLogsService;

    /**
     * <pre>初期アクションメソッド</pre>
     *
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

        //画面初期化
        InsurerLoginForm form = new InsurerLoginForm();
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.INSURER_LOGIN_ACTION);
    }

    /**
     * <pre>ログインボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=login")
    public String loginAction(@ModelAttribute @Valid InsurerLoginForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //次に遷移するページ
        String actionPage = ActionConst.actionPage(ActionConst.INSURER_LOGIN_ACTION);

        //エラーメッセージ
        String message;
        try {
            BitSet validSet = new BitSet(32);
            AccountEntity entity;

            if (result.hasErrors()) {
                this.initPage(model,form);
                return actionPage;
            }

            //ユーザID・パスワードがDBに存在するときdto作成
            entity = userAuthenticationService.authenticationInsurerUser(form.getUserId(), form.getPassword());

            //ログインエラーチェック
            loginValidate(validSet, entity);
            if (validSet.get(LOGIN_ERROER)) {
                //エラーメッセージ
//                if (validSet.get(INVALID_TERM)) {
//                    message = messageSource.getMessage(ValidationMessageConst.LOGIN_INVALID_TERM_MESSAGE, null, null);
//                } else {
                    message = messageSource.getMessage(ValidationMessageConst.LOGIN_NOTFOUND_MESSAG, null, null);
//                }
                ObjectError error = new ObjectError("login", message);
                result.addError(error);
                this.initPage(model,form);
                return actionPage;
            } else {
                //ワンタイムパスワード権限の確認
               // if(!entity.getOneTimePassAuth()){
               //     entity.setOneTimePassAuth(getOneTimePassAuth(entity.getAccountId()));
               // }
                //セッションに格納
                sessionUtility.setSession(SessionConst.ACCOUNT_ENTITY, entity);
                //ユーザ情報のログイン日時更新
                userAuthenticationService.updateLastloginDateTime(entity);
                //アクセスログ
                AccesslLogBackUpUtility.writeAccessLog(request, accessLogsService, "login");

                if (!entity.isInitPassword()) {
                    actionPage = moveInitPage(entity);
                } else {
                    //パスワード変更フラグがtrueのとき
                    actionPage = ActionConst.redirectActionPage("." + ActionConst.PASSWORD_CHANGE_ACTION);
                }
            }
        } catch (Throwable t) {
            this.initPage(model,form);
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        return actionPage;
    }

    /**
     * <pre>ログインバリデーション</pre>
     *
     * @param validSet
     * @param dto
     * @throws Throwable
     */
    private void loginValidate(BitSet validSet, AccountEntity entity) throws Throwable {
        //ユーザ認証
        if (entity == null) {
            validSet.set(LOGIN_ERROER, true);
        } else if (entity.isInvalid() == true) {
            validSet.set(LOGIN_ERROER, true);
            validSet.set(INVALID_TERM, true);
        }
    }

    /**
     * <pre>ログインユーザの初期画面を返します</pre>
     *
     * @param auth
     * @return initPage
     * @throws Throwable
     */
    private String moveInitPage(AccountEntity entity) throws Throwable {
        //認証成功：初期画面に移動
        String initPage;
        if (entity.getAccoutTypeCd() == AccoutTypeCd.INSURER.getId()) {
            //保険者サイト
//kokoda 
//initPage = ActionConst.redirectActionPage("." + ActionConst.MESSAGE_LIST_ACTION);
            initPage = ActionConst.redirectActionPage("." + ActionConst.NOTICE_ACTION);
        } else {
            //保険者（窓口）
            initPage = ActionConst.redirectActionPage("." + ActionConst.MESSAGE_LIST_ACTION);
        }

        logger.debug("終了時刻　:　" + new Date());
        return initPage;
    }

    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model,InsurerLoginForm form) {
        model.addAttribute("info_mailadd", PhrWebConfig.getPhrWebProperty(PhrWebConst.INFO_MAILADD));
        model.addAttribute("info_text", PhrWebConfig.getPhrWebProperty(PhrWebConst.INFO_TEXT));
        model.addAttribute("login_kind", ActionConst.INSURER_LOGIN_ACTION);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    private boolean getOneTimePassAuth(String id){
        String idStr = PhrWebConfig.getPhrWebProperty(PhrWebConst.ONETIME_PASS_USER);
        String[] idlist = idStr.split(",");
        for(String aId:idlist){
            if(aId.equals(id)){
                return true;
            }
        }
        return false;
    }
}
