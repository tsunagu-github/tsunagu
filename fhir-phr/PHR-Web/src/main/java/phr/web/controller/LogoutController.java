/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.AccoutTypeCd;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.web.ActionConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;

/**
 *
 * @author KISNOTE011
 */
@Controller
@RequestMapping(ActionConst.LOGOUT_ACTION)
public class LogoutController {
    /**
     * ロギングコンポーネント    
     */
    private static final Log logger = LogFactory.getLog(LogoutController.class);
    
    /**
     * インジェクション：セッションユーティリティー
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * メッセージリソース
     */
//    @Autowired
//    private MessageSource messageSource;

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
    	
    	Object objSettion =  sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
    	Object medicalobjSettion =  sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
    	if (objSettion == null && medicalobjSettion == null) {
            sessionUtility.ClearSession();
            return ActionConst.redirectActionPage("/");
    	}
    	AccountEntity entity = (AccountEntity)objSettion;
        
    	String site;
        //アクセスログ
//        AccesslLogBackUpUtility.writeLogoutAccessLog(request, "logout");
        if(entity != null ){
            if (entity.getAccoutTypeCd()==AccoutTypeCd.INSURER.getId()) {
                site = ActionConst.INSURER_LOGIN_ACTION;
            } else {
                site = ActionConst.INSURER_LOGIN_ACTION;
            }
        }else{
                site = ActionConst.MEDICAL_LOGIN_ACTION;
        }
        sessionUtility.ClearSession();
    	logger.debug("End");
    	return ActionConst.redirectActionPage(site);
    }
}
