/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.BitSet;
import java.util.Date;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import jp.kis_inc.core.utility.TypeUtility;

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
import org.springframework.web.bind.annotation.ResponseBody;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;
import phr.datadomain.entity.HPKIAccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.OneTimeIdInfoEntity;
import phr.service.IAccessLogsService;
import phr.service.IMedicalSearchService;
import phr.service.IMedicalUserAuthenticationService;
import phr.utility.AesUtility;
import phr.web.FormConst;
import phr.web.ValidationMessageConst;
import phr.web.PhrWebConfig;
import phr.web.PhrWebConst;
import phr.web.form.MedicalLoginForm;

/**
 *
 * 保険者ログイン画面コントローラ
 *
 * @author KISNOTE011
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.MEDICAL_LOGIN_ACTION, "/"})
public class MedicalLoginController {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalLoginController.class);

    /**
     * ログインエラー
     */
    private static final int LOGIN_ERROER = 0;

    /**
     * 利用可能期間外エラー
     */
    private static final int INVALID_TERM = 1;

    /**画面CD */
    private final String FORM_CD = FormConst.MEDICAL_LOGIN_FORM_CD;
    /**画面タイトル */
    private final String FORM_NAME = FormConst.MEDICAL_LOGIN_FORM_TITLE;
    /**
     * AesUtility
     */
    private static AesUtility aesUtility = null;
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
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
    @Qualifier("MedicalUserAuthenticationService")
    private IMedicalUserAuthenticationService medicalUserAuthenticationService;

    /**
     * インジェクション：アクセスログサービス
     */
    @Autowired
    @Qualifier("AccessLogsService")
    private IAccessLogsService accessLogsService;
    
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
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        // パラメータにIDがある場合はワンタイムパスワード認証する
        String ontimeId = request.getParameter("ID");
        logger.debug("OntimeId=" + ontimeId);
        if (!TypeUtility.isNullOrEmpty(ontimeId)) {
            MedicalOrganizationEntity entity = medicalUserAuthenticationService.authenticationOneTimeId(ontimeId);
            if (entity != null) {
                //セッションに格納
                sessionUtility.setSession(SessionConst.MEDICALACCOUNT_ENTITY, entity);
                //アクセスログ
                AccesslLogBackUpUtility.writeAccessLog(request, accessLogsService, "login");
                return ActionConst.redirectActionPage("." + ActionConst.MEDICAL_KENSA_ENTRY_ACTION);
            }
        }
        
        //画面初期化
        MedicalLoginForm form = new MedicalLoginForm();
        this.initPage(model,form);
        model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MEDICAL_LOGIN_ACTION);
    }
    /**
     * <pre>HPKIアクション</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, params = "command=HPKI")
    @ResponseBody
    public String hpkiAction(@ModelAttribute @Valid MedicalLoginForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        try {
            String commonName = request.getParameter("cn");
            String serialNo = request.getParameter("sn");
            
            logger.debug("CommonName = " + commonName);
            logger.debug("SerialNo   = " + serialNo);
            if (TypeUtility.isNullOrEmpty(serialNo)) {
                serialNo = "0";
            }
            
            OneTimeIdInfoEntity entity = medicalUserAuthenticationService.authenticationHPKIUser(commonName, serialNo);
            String responseJson = createResponseJson(entity);
            
            return responseJson;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            logger.debug("End");
        }
    }
    /**
     * <pre>NoHPKIアクション</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, params = "command=nonHPKI")
    @ResponseBody
    public String nonHpkiAction(@ModelAttribute @Valid MedicalLoginForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        try {
            String medicalOrganizationCd = request.getParameter("medicalOrganizationCd");
            String password = request.getParameter("password");
            
            OneTimeIdInfoEntity entity = medicalUserAuthenticationService.authenticationNonHPKIUser(medicalOrganizationCd, password);
            String responseJson = createResponseJson(entity);
            
            return responseJson;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            logger.debug("End");
        }
    }
    /**
     * <pre>HPKIアクションメソッド</pre>
     *
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST})
    public String hpkiAction_OLD(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //クライアント証明書を検証
        if(request.isSecure()){
            X509Certificate certs[] = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
            if (certs != null){
                logger.debug("certs num = " + certs.length);
                for (X509Certificate cert : certs) {
                    //対象のクライアント証明書か判定
                    if(cert!=null){
//            		BigInteger SerialNumber = cert.getSerialNumber();	//シリアル番号
                        X500Principal IssuerDN = cert.getIssuerX500Principal(); //発行者情報
                        String issuerRFC2253 = IssuerDN.getName();  //発行者識別名
                logger.debug("IssuerDN = " + issuerRFC2253);
                        X500Principal SubjectDN = cert.getSubjectX500Principal();   //加入者(医療機関)情報
                        String subjectRFC2253 = SubjectDN.getName();    //加入者(医療機関)識別名
                logger.debug("SubjectDN = " + subjectRFC2253);
                        //取得した証明書の内容を確認
                        int matchCnt = 0;
                        String[] dnNames = issuerRFC2253.split(","); 
                        String idC = "C";
                        String cValue = "JP";
                        String idO = "O";
                        String oValue = "MEDIS";
                        String idOu = "OU";
                        String ouValue = "MEDIS HPKI CA";
                        String idCn = "CN";
//                        String cnValue = "HPKI-01-MedisSignCA2-forNonRepudiation";    //署名用
                        String cnValue = "HPKI-01-MedisSignCA2-forAuthentication";  //認証用
                        for(String dnName :dnNames){
                            String[] dnValues=dnName.split("=");
                            if(dnValues!=null && dnValues.length>=2){
                                if(dnValues[0].equals(idC) && dnValues[1].equals(cValue)){
                                    //Issuer CountryName一致
                                    matchCnt+=1;
                                }else if(dnValues[0].equals(idO) && dnValues[1].equals(oValue)){
                                    //Issuer OrganizationName一致
                                    matchCnt+=1;
                                }else if(dnValues[0].equals(idOu) && dnValues[1].equals(ouValue)){
                                    //Issuer OrganizationUnitName一致
                                    matchCnt+=1;
                                }else if(dnValues[0].equals(idCn) && dnValues[1].equals(cnValue)){
                                    //Subject CommonName一致
                                    matchCnt+=1;
                                }
                            }
                        }
                        
                        String[] subDnNames = subjectRFC2253.split(","); 
//                        String subOValue = "";  //医療福祉機関名(ローマ字あるいは英語)[管理者のみ設定]
//                        String subOuValue = "Director"; //[管理者のみ設定]
//                        String subCnValue = ""; //加入者の氏名(ローマ字)
                        for(String subDnName :subDnNames){
                            String[] subDnValues=subDnName.split("=");
                            if(subDnValues!=null && subDnValues.length>=2){
//                                if(subDnValues[0].equals(idC) && subDnValues[1].equals(cValue)){
//                                    //Subject CountryName一致
//                                    matchCnt+=1;
//                                }else if(subDnValues[0].equals(idO) && subDnValues[1].equals(subOValue)){
//                                    //Subject OrganizationName一致
//                                    matchCnt+=1;
//                                }else if(subDnValues[0].equals(idOu) && subDnValues[1].equals(subOuValue)){
//                                    //Subject OrganizationUnitName一致
//                                    matchCnt+=1;
//                                }else if(subDnValues[0].equals(idCn) && subDnValues[1].equals(subCnValue)){
//                                    //Subject CommonName一致
//                                    matchCnt+=1;
//                                }
                            }
                        }
                        
//                        Set<String> critSet=cert.getNonCriticalExtensionOIDs();
//                        for(String s:critSet){
//                            logger.debug(s);
//                        }
                        
                        //hcRoleのOIDから値を取得
                        byte[] hcRole=cert.getExtensionValue("1.2.392.100495.1.6.1.1");
                        String hcRoleStr = new String(hcRole, "UTF-8");
                        switch (hcRoleStr){
                            case "Medical Doctor":
                                //医師
                                matchCnt+=1;
                                System.out.println("医師");
                                break;
                            case "Director of Hospital":
                                //病院長
                                matchCnt+=1;
                                System.out.println("病院長");
                                break;
                            case "Director of Clinic":
                                //診療所院長
                                matchCnt+=1;
                                System.out.println("診療所院長");
                                break;
                            default:
                                //上記以外
                                break;
                        }
                        
                        //正当な内容の証明書なので、有効期限を確認(※数値は上記チェック数に合わせ設定)
                        if(matchCnt>=5){
                            try{
                                cert.checkValidity();
                            }catch(CertificateExpiredException | CertificateNotYetValidException e0){
                                //有効期限切れ・有効期限前
                                logger.error(e0);
    //                            throw new ClientCertificateException();
                                throw e0;
                            }
                        }
                    }
                }
            }else{
                logger.debug("no certs");
                throw new CertificateExpiredException();
            }
        }

        //画面初期化
        MedicalLoginForm form = new MedicalLoginForm();
        this.initPage(model,form);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MEDICAL_LOGIN_ACTION);
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
    public String loginAction(@ModelAttribute @Valid MedicalLoginForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //次に遷移するページ
        String actionPage = ActionConst.actionPage(ActionConst.MEDICAL_LOGIN_ACTION);

        //エラーメッセージ
        String message;
        try {
            BitSet validSet = new BitSet(32);
//            MedicalOrganizationEntity entity;
            HPKIAccountEntity entity;

            if (result.hasErrors()) {
                this.initPage(model,form);
                return actionPage;
            }

            //ユーザID・パスワードがDBに存在するときdto作成
            entity = medicalUserAuthenticationService.authenticationInsurerUser(form.getUserId(),form.getMedicalOrganizationCd(), form.getPassword());
//            entity = medicalUserAuthenticationService.authenticationInsurerUser(form.getUserId(), form.getPassword());

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
                //セッションに格納
                MedicalOrganizationEntity medicalOrganizationEntity = medicalSearchService.getMedicalOrganizationInfo(form.getMedicalOrganizationCd());
                sessionUtility.setSession(SessionConst.MEDICALACCOUNT_ENTITY, medicalOrganizationEntity);
                //ユーザ情報のログイン日時更新
//                userAuthenticationService.updateLastloginDateTime(entity);
                //アクセスログ
                AccesslLogBackUpUtility.writeAccessLog(request, accessLogsService, "login");

/*                if (!entity.isInitPassword()) {
                    actionPage = moveInitPage(entity);
                } else {
                    //パスワード変更フラグがtrueのとき
                    actionPage = ActionConst.redirectActionPage("." + ActionConst.PASSWORD_CHANGE_ACTION);
                }
*/
            }
        } catch (Throwable t) {
            this.initPage(model,form);
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        actionPage = ActionConst.redirectActionPage("." + ActionConst.MEDICAL_KENSA_ENTRY_ACTION);
        return actionPage;
    }

    /**
     * <pre>ログインバリデーション</pre>
     *
     * @param validSet
     * @param dto
     * @throws Throwable
     */
    private void loginValidate(BitSet validSet, HPKIAccountEntity entity) throws Throwable {
//        private void loginValidate(BitSet validSet, MedicalOrganizationEntity entity) throws Throwable {
        //ユーザ認証
        if (entity == null) {
            validSet.set(LOGIN_ERROER, true);
        }
    }

    /**
     * <pre>ログインユーザの初期画面を返します</pre>
     *
     * @param auth
     * @return initPage
     * @throws Throwable
     */
    private String moveInitPage(MedicalOrganizationEntity entity) throws Throwable {
        //認証成功：初期画面に移動
        String initPage;
        initPage = ActionConst.redirectActionPage("." + ActionConst.INSURER_ACCOUNT_MANAGEMENT_ACTION);

        logger.debug("終了時刻　:　" + new Date());
        return initPage;
    }

    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model,MedicalLoginForm form) {
        model.addAttribute("info_mailadd", PhrWebConfig.getPhrWebProperty(PhrWebConst.INFO_MAILADD));
        model.addAttribute("info_text", PhrWebConfig.getPhrWebProperty(PhrWebConst.INFO_TEXT));
        model.addAttribute("login_kind", ActionConst.MEDICAL_LOGIN_ACTION);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
    
    /**
     * レスポンスJSONを作成する
     * @param entity
     * @return
     * @throws Throwable 
     */
    private String createResponseJson(OneTimeIdInfoEntity entity) throws Throwable {
        ResponseJsonDto dto = new ResponseJsonDto();
        if (entity == null) {
            dto.setStatus(ResponseJsonDto.STATUS_NG);
        } else {
            if (aesUtility == null) {
                String key = PhrConfig.getSystemConfigProperty(SystemConfigConst.ONTIME_ID_ENCRIPT_KEY);
                aesUtility = new AesUtility(key);
            }
            dto.setStatus(ResponseJsonDto.STATUS_OK);
            //dto.setOneTimeId(aesUtility.encrypt(entity.getOneTimeId()));
            dto.setOneTimeId(entity.getOneTimeId());
        }
        String returnJson = gson.toJson(dto);
        return returnJson;
    }
    
    private class ResponseJsonDto {
        public static final String STATUS_OK = "OK";
        public static final String STATUS_NG = "NG";
        
        private String status = null;
        private String oneTimeId = null;

        /**
         * @return the status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status the status to set
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return the oneTimeId
         */
        public String getOneTimeId() {
            return oneTimeId;
        }

        /**
         * @param oneTimeId the oneTimeId to set
         */
        public void setOneTimeId(String oneTimeId) {
            this.oneTimeId = oneTimeId;
        }
        
    }
}
