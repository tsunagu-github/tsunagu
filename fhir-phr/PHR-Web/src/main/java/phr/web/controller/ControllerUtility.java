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
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import phr.datadomain.entity.AccountEntity;
import phr.web.SessionConst;
import phr.web.SessionUtility;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.utility.TypeUtility;
import phr.web.exceptions.SessionTimeoutException;

/**
 *
 * コントローラーユーティリティー
 * @author KISNOTE011
 */
public class ControllerUtility {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ControllerUtility.class);
   
    /**
     * アプリケーションコンテキストを取得する。
     * @return
     */
    public static ApplicationContext getApplicationContext() {
    	logger.debug("Start");
    	HttpServletRequest request = 
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	ApplicationContext applicationContext = 
                RequestContextUtils.getWebApplicationContext(request);
    	logger.debug("End");
    	return applicationContext;
    }
    
    /**
     * ヘッダーを初期化する
     * 
     * @param model
     * @throws Throwable
     */
    public static void InitHeader(Model model) throws Throwable {
    	logger.debug("Start");
    	try {
            ApplicationContext applicationContext = getApplicationContext();
	    
            SessionUtility sessionUtility = (SessionUtility)applicationContext.getBean("SessionUtility");

            Object dtoObject = sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
            Object medicaldtoObject = sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
            if (dtoObject == null && medicaldtoObject == null)
                    throw new SessionTimeoutException();

            AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
            MedicalOrganizationEntity medicalOrganizationEntity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
            /**
            if (dto != null) {
                    if(dto.getSite() == null){
                            ClinicalHBaseUtility.createMcdrsDB();
                    }
                    else{
                            ClinicalHBaseUtility.createClinicalDb(dto.getClinicalGroup(), dto.getCipher(), dto.getPrivateKey());
                    }
            }
            **/
            String buildVer = PhrConfig.getSystemConfigProperty(SystemConfigConst.BUILDVERSION);
            model.addAttribute("accountEntity", entity);
            model.addAttribute("medicalOrganizationEntity", medicalOrganizationEntity);
            model.addAttribute("buildVer", buildVer);
            if(entity!=null){model.addAttribute("lastLoginStr", TypeUtility.format(entity.getLastLoginDateTime(), "yyyy-MM-dd HH:mm"));}
//	    	if(ClinicalHBaseUtility.getClinicalGroup()!=null 
//	    			&& ClinicalHBaseUtility.getClinicalGroup().isDataManagerEnabled()){
//		    	model.addAttribute("ClinicalGroupDM", true);
//	    	}
    	} catch (Throwable t) {
            logger.error(t);
            throw t;
    	} finally {
            logger.debug("End");
    	}
    }
}
