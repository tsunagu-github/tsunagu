package phr.web.controller;

import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

import phr.web.SessionUtility;

import java.net.URL;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import phr.config.PhrConfig;
import phr.web.SessionConst;
import phr.datadomain.entity.AccessLogsEntity;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;
import phr.web.enums.SiteTypeEnum;
import phr.service.IAccessLogsService;
import phr.service.impl.AccessLogsService.AccessLogResultCd;
import phr.service.impl.OnetimePasswordService.OneTimePasswordResult;
import phr.web.phone.dto.RequestBaseDto;

/**
 * アクセスログユーティリティー
 *
 * @author daisuke
 *
 */
public class AccesslLogBackUpUtility {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccesslLogBackUpUtility.class);
   
    /**
     * セッションユーティリティを取得する
     * @param request
     * @return
     */
    public static SessionUtility getSessionUtility(HttpServletRequest request) {
    	
    	SessionUtility sessionUtility = (SessionUtility) getComponent("SessionUtility");

    	return sessionUtility;
    }
    /**
     * コンポーネントを取得する
     * @param request
     * @param name
     * @return
     */
    private static Object getComponent(String name) {

    	HttpServletRequest request = 
    			((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	ApplicationContext applicationContext = 
                RequestContextUtils.getWebApplicationContext(request);
    	
    	Object sessionObject = applicationContext.getBean(name);

    	return sessionObject;
   	
    }

    /**
     * プロパティーの画面CDを取得する
     * @param key プロパティーKey
     * @return 値
     */
    public static String[] getDisplayNameProperty(String key) {
        String getPropStr="";
        try{
//            String configPath = PhrConfig.getSystemConfigProperty(SystemConfigConst.CONFIG_PATH);
//            if (TypeUtility.isNullOrEmpty(configPath)) {
                URL obUrl = AccesslLogBackUpUtility.class.getResource(AccesslLogBackUpUtility.class.getSimpleName()+".class");
                obUrl = new URL(obUrl,"../../../../displayName.properties");
                InputStream inputStream = obUrl.openStream();
                getPropStr=PhrConfig.getProperty(key, inputStream);
//            }else{
//                getPropStr=PhrConfig.getProperty(key, configPath+"displayName.properties");
//            }
            if(getPropStr.isEmpty()){
                return null;
            }
        }catch(Exception ex){
//            logger.error(ex);
        }
        return getPropStr.split(",");
    }

    /**
     * プロパティーのアクション名を取得する
     * @param key プロパティーKey
     * @return 値
     */
    public static String getActionNameProperty(String key) {
        String getPropStr="";
        try{
//            String configPath = PhrConfig.getSystemConfigProperty(SystemConfigConst.CONFIG_PATH);
//            if (TypeUtility.isNullOrEmpty(configPath)) {
                URL obUrl = AccesslLogBackUpUtility.class.getResource(AccesslLogBackUpUtility.class.getSimpleName()+".class");
                obUrl = new URL(obUrl,"../../../../actionName.properties");
                InputStream inputStream = obUrl.openStream();
                getPropStr=PhrConfig.getProperty(key, inputStream);
//            }else{
//                getPropStr=PhrConfig.getProperty(key, configPath+"actionName.properties");
//            }
        }catch(Exception ex){
//            logger.error(ex);
        }
        return getPropStr;
    }
    
    /**
     * アクセスログテーブルにログインログを出力
     * @param request
     * @param service
     * @param action
     * @throws Throwable 
     */
    public static void writeAccessLog(HttpServletRequest request, IAccessLogsService service,String action) throws Throwable {
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
            SessionUtility sessionUtility = AccesslLogBackUpUtility.getSessionUtility(request);
            AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
            MedicalOrganizationEntity medicalEntity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
//            OneTimePasswordResult patientInfo=null;
            MedicalOrganizationPatientEntity patientInfo=null;
            if(displayList[0].equals("I201") || displayList[0].equals("I202") ||
                     displayList[0].equals("Ic01")){
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
                accessLogEntity.setAccessUserName(medicalEntity.getAccountName());
                accessLogEntity.setSiteType(SiteTypeEnum.IRYOU.getId());   //医療機関
            }else{
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
            if(displayList.length>0){
                accessLogEntity.setScreenCd(displayList[0]);
            }
            if(displayList.length>1){
                accessLogEntity.setScreenName(displayList[1]);
            }
            accessLogEntity.setUrl(new String(request.getRequestURL()));
            accessLogEntity.setRefer(request.getHeader("Referer"));
            accessLogEntity.setRequestMethod(request.getMethod());
            accessLogEntity.setRemoteAddress(remoteAddr);
            accessLogEntity.setRemoteHost(remoteHost);
            accessLogEntity.setAgent(request.getHeader("user-agent"));
            if(patientInfo!=null){
                accessLogEntity.setReferPhrid(patientInfo.getPHR_ID());
            }
            AccessLogResultCd retCd =service.insertAccessLog(accessLogEntity);
            if(AccessLogResultCd.SUCCCESS.equals(retCd)){
                logger.debug("RequestPath = " + requestPath);
            }
        }

        logger.debug("End");
    }
    
    /**
     * アクセスログテーブルにログインログを出力(Phone用)
     * @param request
     * @param service
     * @param requestDto
     * @throws Throwable 
     */
    public static void writePhoneAccessLog(
            HttpServletRequest request, IAccessLogsService service,RequestBaseDto requestDto) throws Throwable {
        String action=requestDto.getAction();
//        String url = request.getRequestURI();
//        String contextPath = request.getContextPath() + "/";
//        int indexOf = url.indexOf(contextPath);
//        String requestPath = null;
//        if (indexOf > -1)
//            requestPath = url.substring(indexOf + contextPath.length());
//        if(requestPath!=null){
//            int index = requestPath.indexOf(";");
//            if (index > -1)
//                requestPath = requestPath.substring(0, index);
//        }
        String[] displayList = AccesslLogBackUpUtility.getDisplayNameProperty(action);
        PatientEntity entity = service.searchAccessUserName(requestDto.getPhrId());
        if(displayList != null){
            AccessLogsEntity accessLogEntity = new AccessLogsEntity();
            // リクエストBodyのJSONテキスト文字列を取得する
            accessLogEntity.setSiteType(SiteTypeEnum.KANJA.getId());   //患者
            
            // PHR-ID発行時は固定のAccessUserを設定する
            if ("CreatePatienAction".equals(requestDto.getAction())) {
                accessLogEntity.setAccessUser("NEW_PHR-ID");    // PHRID
            } else {
                accessLogEntity.setAccessUser(requestDto.getPhrId());    // PHRID
            }
            if(entity!=null){
                accessLogEntity.setAccessUserName(entity.getFamilyName()+entity.getGivenName());
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
            if(displayList.length>0){
                accessLogEntity.setScreenCd(displayList[0]);
            }
            if(displayList.length>1){
                accessLogEntity.setScreenName(displayList[1]);
            }
            accessLogEntity.setUrl(new String(request.getRequestURL()));
            accessLogEntity.setRefer(request.getHeader("Referer"));
            accessLogEntity.setRequestMethod(request.getMethod());
            accessLogEntity.setRemoteAddress(remoteAddr);
            accessLogEntity.setRemoteHost(remoteHost);
            accessLogEntity.setAgent(request.getHeader("user-agent"));
//            accessLogEntity.setReferPhrid(patientInfo.getPHR_ID());
            AccessLogResultCd retCd =service.insertAccessLog(accessLogEntity);
            if(AccessLogResultCd.SUCCCESS.equals(retCd)){
                logger.debug("action = " + action);
            }
        }

        logger.debug("End");
    }
}
