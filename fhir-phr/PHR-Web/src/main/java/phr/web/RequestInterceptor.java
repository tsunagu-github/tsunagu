/*
 * *****************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：リクエスト時のインターセプタークラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 * *****************************************************************************
 */
package phr.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import phr.datadomain.entity.AccessLogsEntity;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.service.IAccessLogsService;
import phr.service.impl.AccessLogsService.AccessLogResultCd;
import phr.service.impl.OnetimePasswordService.OneTimePasswordResult;
import phr.web.controller.AccesslLogBackUpUtility;
import phr.web.enums.SiteTypeEnum;
import phr.web.exceptions.SessionTimeoutException;

/**
 *
 * @author 
 */
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RequestInterceptor.class);

    /**
     * インジェクション：アクセスログサービス
     */
    @Autowired
    @Qualifier("AccessLogsService")
    private IAccessLogsService accessLogsService;
    
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception 
     */
    @Override
    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response
            , Object handler) throws Exception {
        logger.debug("Start");

        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        logger.debug("URL         = " + url);
        logger.debug("ContextPath = " + contextPath);
        url = url.replaceAll(contextPath, "");
        boolean isLoginForm = false;
        if (url.contains("phoneWebInterface")) {
            //スマフォは対象外
            isLoginForm = true;
        }else if (url.contains(ActionConst.INSURER_LOGIN_ACTION)) {
            isLoginForm = true;
        } else if (url.contains(ActionConst.MEDICAL_LOGIN_ACTION)) {
            isLoginForm = true;
        } else if (url.equals("/")) {
            isLoginForm = true;
        }
        ISessionUtility sessionUtility = AccesslLogBackUpUtility.getSessionUtility(request);
        AccountEntity sessionDto = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity medicaldtoObject = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        if (sessionDto == null && medicaldtoObject == null && !isLoginForm) {
            throw new SessionTimeoutException();
        }
        try {
            String temp = handler.toString();
            String actionName = temp.substring(0,temp.indexOf("Action"));
            actionName = actionName.substring(actionName.lastIndexOf(".") +1,actionName.length());

            // AccessLog書込み
            if (!url.contains("phoneWebInterface")) {
                this.writeAccessLog(request, actionName);
            }

        } catch(Throwable th) {
            logger.error(th);
//            throw new ServletException(th.getMessage());
        }

        logger.debug("End");
        return true;
    }

    /**
     * 
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception 
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    /**
     * 
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception 
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
    
    /**
     * アクセスログテーブルにログを出力
     * @param request
     * @param action
     * @throws Throwable 
     */
    public void writeAccessLog(HttpServletRequest request, String action) throws Throwable {
        String url = request.getRequestURI();
        String contextPath = request.getContextPath() + "/";
        int indexOf = url.indexOf(contextPath);
        String requestPath = null;
        if (indexOf > -1)
            requestPath = url.substring(indexOf + contextPath.length());
        if(requestPath!=null){
            int index = requestPath.indexOf(";");
            if (index > -1)
                requestPath = requestPath.substring(0, index);
        }
        String[] displayList = AccesslLogBackUpUtility.getDisplayNameProperty(requestPath);
        if(displayList != null){
            if (displayList[0].equals("I001") || 
                displayList[0].equals("I002") ||
                displayList[0].equals("M001")){
                //アクセスログ出力対象外
                return;
            }

            SessionUtility sessionUtility = AccesslLogBackUpUtility.getSessionUtility(request);
            AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
            MedicalOrganizationEntity medicalEntity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
//            OneTimePasswordResult patientInfo=null;
            MedicalOrganizationPatientEntity patientInfo=null;
            if(displayList[0].equals("I201")){
//                patientInfo=(OneTimePasswordResult)sessionUtility.getSession(SessionConst.VIEW_PASSWORD_INFO);
                patientInfo=(MedicalOrganizationPatientEntity)sessionUtility.getSession(SessionConst.AGREE_PATIET_INFO);
            }
            AccessLogsEntity accessLogEntity = new AccessLogsEntity();
            if(entity!=null){
                accessLogEntity.setAccessUser(entity.getAccountId());
                accessLogEntity.setAccessUserName(entity.getName());
                accessLogEntity.setSiteType(SiteTypeEnum.HOKEN.getId());   //保険者

            }else if(medicalEntity!=null){
                accessLogEntity.setAccessUser(medicalEntity.getMedicalOrganizationCd());
                accessLogEntity.setAccessUserName(medicalEntity.getMedicalOrganizationName());
                accessLogEntity.setSiteType(SiteTypeEnum.IRYOU.getId());   //医療機関
            }else{
                //ログ出力せずに終了
                return;
            }

            String remoteAddr = request.getHeader("X-Forwarded-For");
            if (remoteAddr == null || remoteAddr.length() == 0) {
                remoteAddr = request.getRemoteAddr();
            }
            String remoteHost = request.getHeader("X-Forwarded-Host");
            if (remoteHost == null || remoteHost.length() == 0) {
                remoteHost = request.getRemoteHost();
            }
	    
            Long curTimeMillis=System.currentTimeMillis();
            accessLogEntity.setAccessDatetime(new Timestamp(curTimeMillis));
            accessLogEntity.setAccessDatetimeSeq(curTimeMillis);
            accessLogEntity.setActionId(action);
            accessLogEntity.setActionName(AccesslLogBackUpUtility.getActionNameProperty(action));
            accessLogEntity.setScreenCd(displayList[0]);
            accessLogEntity.setScreenName(displayList[1]);
            accessLogEntity.setUrl(new String(request.getRequestURL()));
            accessLogEntity.setRefer(request.getHeader("Referer"));
            accessLogEntity.setRequestMethod(request.getMethod());
            accessLogEntity.setRemoteAddress(remoteAddr);
            accessLogEntity.setRemoteHost(remoteHost);
            accessLogEntity.setAgent(request.getHeader("user-agent"));
            if(patientInfo!=null){
                accessLogEntity.setReferPhrid(patientInfo.getPHR_ID());
            }
//            accessLogEntity.setFreeMessage(freeMessageStr);
            AccessLogResultCd retCd =accessLogsService.insertAccessLog(accessLogEntity);
            if(AccessLogResultCd.SUCCCESS.equals(retCd)){
                logger.debug("RequestPath = " + requestPath);
            }
        }

        logger.debug("End");
    }
}
