/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.form.MedicalSearchForm;
import phr.service.IMedicalSearchService;
import phr.service.impl.MedicalSearchService;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.web.SessionConst;
import phr.web.dto.MedicalSearchDto;
/**
 *
 * @author kis.o-note-002
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.MEDICAL_SEARCH_ACTION, "/"})
public class MedicalSearchController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SpecificMedicalCheakUpFormController.class);
    /**
     * ログインエラー
     */
    private static final int LOGIN_ERROER = 0;


    /**画面ID */
    private final String FORM_CD = FormConst.MEDICAL_SEARCH_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MEDICAL_SEARCH_FORM_TITLE;

    /**
     * 利用可能期間外エラー
     */
    private static final int INVALID_TERM = 1;
    
    /**
     * 改ページの行数
     */
    private static final int limitRow = 20;
    
    /**
     * 最大ページ数
     */
    private static final int maxPage = 10;
    
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
    @Qualifier("MedicalSearchService")
    private IMedicalSearchService medicalSearchService;
    
    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param form
     * @param model
     * @param request
     * @param result
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(MedicalSearchForm form, Model model, HttpServletRequest request ,BindingResult result) throws Throwable {

        logger.debug("Start");
        
        //ログインサイトを設定
        sessionUtility.setSession("SessionConst.SITE", null);

        // ページ数を設定する。
        if(form.getCurrentPage()==0){
            model.addAttribute("currentPage",0);
        } else{
            model.addAttribute("currentPage",form.getCurrentPage());
        }
        model.addAttribute("totalNum", 0);
        
        //医療機関検索DTO
        MedicalSearchDto searchDto = new MedicalSearchDto();
        searchDto.setUserIdStr("");
        searchDto.setUserNameStr("");
        searchDto.setTotalNum(0);
        searchDto.setPageNo(0);
        sessionUtility.setSession(SessionConst.MEDICAL_SEARCH_DTO , searchDto);
        
        //画面初期化
        this.initPage(model, form);
        logger.debug("End");
        
        return ActionConst.actionPage(ActionConst.MEDICAL_SEARCH_ACTION);
    }
    
    /**
     * ページングアクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable
     */
	@RequestMapping(method = {RequestMethod.POST }, params = "command=clickedPage")
    public String pagingAction(MedicalSearchForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");
    	try {
    		MedicalSearchDto searchDto = 
    				(MedicalSearchDto)sessionUtility.getSession(SessionConst.MEDICAL_SEARCH_DTO);
                if (form.getClickedPage() < 1) {
                    form.setClickedPage(1);
                }
    		searchDto.setPageNo(form.getClickedPage());
	    	sessionUtility.setSession(SessionConst.MEDICAL_SEARCH_DTO , searchDto);
                displayPage(form, result, model, form.getClickedPage()-1);    //検索・リスト作成
		} catch (Throwable t) {
			logger.error(t);
		} finally {
	    	logger.debug("End");
		}
        
   	this.initPage(model, form);
        return ActionConst.actionPage(ActionConst.MEDICAL_SEARCH_ACTION);
    }
    
    /**
     * <pre>検索ボタンクリック時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=search")
    public String searchAction(MedicalSearchForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        // 検索ボタンのクリック時は１ページ目のみを表示する。
        displayPage(form, result, model, 0);
        form.setClickedPage(1);
        
        model.addAttribute(form);
        this.initPage(model, form);
        logger.debug("End");
        
        return ActionConst.actionPage(ActionConst.MEDICAL_SEARCH_ACTION);
    }
    
    /**
     * <pre>新規作成ボタンクリック時</pre>
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=insert")
    public String insertAction(MedicalSearchForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {

        // 医療機関新規、更新画面（新規登録）へ遷移
        MedicalOrganizationEntity entity = new MedicalOrganizationEntity();
        
        sessionUtility.setSession(SessionConst.MEDICAL_INFO, entity);
        this.initPage(model, form);
        return ActionConst.redirectActionPage(ActionConst.MEDICAL_CREATE_EDIT_ACTION);
    }
    
    /**
     * <pre>修正ボタンクリック時</pre>
     * @param form
     * @param result
     * @param model
     * @param request
     * @return
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=update")
    public String updateAction(MedicalSearchForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        // 医療機関CDを画面に受け渡す。
        MedicalOrganizationEntity entity = medicalSearchService.getMedicalOrganizationInfo(request.getParameter("param1"));
        
        sessionUtility.setSession(SessionConst.MEDICAL_INFO, entity);
        
        // 医療機関新規、更新画面（更新）へ遷移
        this.initPage(model, form);
        return ActionConst.redirectActionPage(ActionConst.MEDICAL_CREATE_EDIT_ACTION);
    }

    /**
     * ページ出力処理（改ページ）
     * @param form
     * @param result
     * @param model
     * @param currentPage
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=next")
    private void displayPage(MedicalSearchForm form , BindingResult result, Model model,int offset) throws Throwable {
        logger.debug("Start");
        System.out.println("page：" + offset);
        try{
            String userIdStr = form.getUserIdStr();        // 医療機関CD
            String userNameStr = form.getUserNameStr();     // 医療機関名称
            MedicalSearchDto searchDto = (MedicalSearchDto)sessionUtility.getSession(SessionConst.MEDICAL_SEARCH_DTO);      //医療機関検索DTO
            
            ArrayList<MedicalSearchForm> medicalSearchList = new ArrayList<>();
            ArrayList<MedicalOrganizationEntity> entity = new ArrayList<>();
            List<Integer> pageList = new ArrayList<Integer>();
            int entityCount = 0;
            // 医療機関コード、医療機関名称取得処理
            System.out.println(offset);
            int currentPage = offset;
            entity=this.medicalSearchService.getMedicalOrganizationInfo(userIdStr, userNameStr,limitRow, offset);
            entityCount=this.medicalSearchService.getMedicalOrganizationInfoCount(userIdStr, userNameStr);  
            if(entity.isEmpty()){
                ObjectError error = new ObjectError("search", "検索結果が0件でした。");
                result.addError(error);
            } else {
                for(MedicalOrganizationEntity list:entity){
                    medicalSearchList.add(new MedicalSearchForm(list.getMedicalOrganizationCd(),list.getMedicalOrganizationName()));
                }
                model.addAttribute("medicalSearchList",medicalSearchList);
                model.addAttribute("userIdStr",userIdStr);
                model.addAttribute("userNameStr",userNameStr);
                form.setTotalNum(entityCount);
                form.setCurrentPage(currentPage);
                
        	// ページング用のリスト作成
    		int totalPageCount = Long.valueOf((entityCount / limitRow) + ((entityCount % limitRow) == 0 ? 0 : 1)).intValue();
        	int centerNo = Long.valueOf(maxPage / 2).intValue();
        	int startpage = Long.valueOf(currentPage - centerNo < 1 ? 0 : currentPage - centerNo).intValue();
        	int pageCount = totalPageCount;
        	if (pageCount > maxPage + startpage) {
                    pageCount = Long.valueOf(maxPage + startpage).intValue();
        	} else {
                    if (pageCount - maxPage > 0)
        		startpage = Long.valueOf(pageCount - maxPage).intValue();   			
                }

                if (totalPageCount > 1) {
                    for (int i = startpage; i < pageCount; i++) {
                        pageList.add(i+1);
                    }
                    form.setPrePage(pageList.get(0) > 1);
                    form.setNextPage(pageList.get(pageList.size()-1) < totalPageCount);
                } else {
                    pageList.add(1);
                }
                if (form.isPrePage()) {
                    int page = startpage-centerNo;
                    form.setPrePageNo(page < 0 ? 1 : page);
                }
                if (form.isNextPage()) {
                    int page = pageList.get(pageList.size()-1)+centerNo;
                    form.setNextPageNo(page > totalPageCount ? totalPageCount : page);
                }
                
                form.setPage(pageList);
                
                //検索結果をセッションに格納
                searchDto.setUserIdStr(form.getUserIdStr());
                searchDto.setUserNameStr(form.getUserNameStr());
                searchDto.setPageNo(form.getCurrentPage());
                searchDto.setTotalNum(form.getTotalNum());
                sessionUtility.setSession(SessionConst.MEDICAL_SEARCH_DTO , searchDto);
            }
        } catch(Exception e){
            logger.debug(e.getMessage());
        }
        logger.debug("End");
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, MedicalSearchForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        model.addAttribute(form);
    }
    
}
