/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.ArrayList;
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
import phr.datadomain.entity.AccountEntity;
import phr.service.IUserAccountManagementService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.form.InsurerAccountManagementForm;

/**
 *
 * @author KISNOTE011
 */
@Controller
@RequestMapping({ActionConst.INSURER_ACCOUNT_MANAGEMENT_ACTION})
public class InsurerAccountManagementController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerLoginController.class);

    /**画面CD */
    private final String FORM_CD = FormConst.INSURER_ACCOUNT_MANAGEMENT_FORM_CD;
    /**画面タイトル */
    private final String FORM_NAME = FormConst.INSURER_ACCOUNT_MANAGEMENT_FORM_TITLE;

    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

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

//        AccountEntity entity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
   	InsurerAccountManagementForm form = new InsurerAccountManagementForm();
   	//初期化
   	this.initPage(model,form);
//    	form.setUserId(entity.getLoingId());
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.INSURER_ACCOUNT_MANAGEMENT_ACTION);
    }

    /**
     * 検索アクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=search")
    public String searchAction(@ModelAttribute @Valid InsurerAccountManagementForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");
    	
    	String loginIdStr;
    	String nameStr;
    	String message;
    	try {
            //画面初期化
            this.initPage(model,form);

//            List<FacilityDataDto> facilityList = facilityManagementService.searchFacilty();
//            model.addAttribute("facilityList", facilityList);
        	        	
//            String accountStr = form.getAccount();
//            String facilityCode = null;
            loginIdStr = form.getUserIdStr();
            nameStr = form.getUserNameStr();
            AccountEntity getAccountDto = (AccountEntity) sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);

//            if(accountStr.equals("1")){
//                //Todo 施設情報指定可能後に削除　
//                facilityCode = form.getFacilityCode();
//                if(facilityCode.equals("")){
//                        facilityCode = null;
//                }
//            }
    	    
            if(!result.hasErrors()){
                List<AccountEntity> list = null;
//                List<AccountEntity> list = userAccountManagementService.searchInsurerAccount(loginIdStr, nameStr, form.getShowInvalidUser());

//                //抽出
//                List<AccountEntity> formList = new ArrayList<AccountEntity>();
//                for (AccountEntity accountEntity : list) {
////                    if(accountStr.equals("0")){
////                        //グループ管理者
////                        if(accountEntity.getSite() != Site.ADMIN){
////                            continue;
////                        }
////                    }else if(accountStr.equals("1")){
////                        //利用者
////                        if(accountEntity.getSite() != Site.USER){
////                            continue;
////                        }
////
//////                    if( accountEntity.getFacilityName() == null ){
//////                        String facilityName="不明";
//////                        if( accountDto.getFacilityCode() != null && !accountDto.getFacilityCode().isEmpty() ){
//////                            Facility facilityInfo = facilityManagementService.select( accountDto.getFacilityCode() );
//////                            if( facilityInfo.getName() != null && !facilityInfo.getName().isEmpty() ){
//////                                facilityName = facilityInfo.getName();
//////                            }
//////                        }
//////                        accountEntity.setFacilityName( facilityName );
//////                    }
////                        formList.add(accountEntity);
////                    }
//                    if(formList.isEmpty()){
//                        message =  messageSource.getMessage("SearchResultZero.message", null, null);
//                        ObjectError error = new ObjectError("userManageAuth", message);
//                        result.addError(error);
//                    }
//                    //画面に設定
//                    sessionUtility.setSession(SessionConst.USER_LIST, formList);
//                    form.setAccountList(formList);
//    		}
                if(list.isEmpty()){
                    message =  messageSource.getMessage("SearchResultZero.message", null, null);
                    ObjectError error = new ObjectError("userManageAuth", message);
                    result.addError(error);
                }
                //画面に設定
                sessionUtility.setSession(SessionConst.USER_LIST, list);
                form.setAccountList(list);
    		model.addAttribute(form);
    		model.addAttribute("getAccountDto", getAccountDto);
            }
        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        return ActionConst.actionPage(ActionConst.INSURER_ACCOUNT_MANAGEMENT_ACTION);
    }
    
    /**
     * ページの初期化処理を行うメソッド
     * @param model
     */
    private void initPage(Model model,InsurerAccountManagementForm form) throws Throwable {
    	ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
    }
}
