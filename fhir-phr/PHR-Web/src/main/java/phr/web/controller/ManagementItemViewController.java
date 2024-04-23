/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
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
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.dto.ManagementItemListDto;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.dto.YearListDto;
import phr.dto.YearViewListDto;
import phr.service.IManagementItemService;
import phr.web.SessionConst;
import phr.web.ValidationMessageConst;
import phr.web.form.ManagementItemViewForm;

/**
 *
 * @author KISNOTE011
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.MANAGEMENT_ITEM_VIEW, "/"})
public class ManagementItemViewController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ManagementItemViewController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.MANAGEMENT_ITEM_VIEW_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MANAGEMENT_ITEM_VIEW_FORM_TITLE;
    
    /**
     * インジェクション：管理項目処理
     */
    @Autowired
    @Qualifier("ManagementItemService")
    private IManagementItemService managementItemService;
    
    /**
     * インジェクション：メッセージサービス
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;
    
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
    public String defaultAction(ManagementItemViewForm form, Model model, HttpServletRequest request , BindingResult result) throws Throwable {
        logger.debug("Start");

        this.initPage(model, form);
        model.addAttribute("initialDisplayFlag", "1");

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        form.setSelectYear(c.get(Calendar.YEAR));

        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MANAGEMENT_ITEM_VIEW);
    }
    
    /**
     * <pre>選択ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=search")
    public String searchAction(ManagementItemViewForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        this.initPage(model, form);
        model.addAttribute("managementItemList",
                managementItemService.getObservationReminders(Integer.valueOf(form.getSelectView()))
                .stream().map(e -> {
                    e.setReminders(escapeXml(e.getReminders()).replace("\n", "<br>"));
                    return e;
                })
                .collect(Collectors.toList())
        );
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MANAGEMENT_ITEM_VIEW);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, ManagementItemViewForm form) throws Throwable {
        ControllerUtility.InitHeader(model);
        model.addAttribute(form);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int nextYear = c.get(Calendar.YEAR) + 1;
        
        String insurerNo = null;
        AccountEntity accountEntity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity medicalEntity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        if (accountEntity == null) {
            insurerNo = medicalEntity.getMedicalOrganizationCd();
        } else {
            insurerNo = accountEntity.getInsurerNo();
        }
        
        // 年度リスト作成
        List<YearViewListDto> yearList = managementItemService.getYearViewIdList(insurerNo)
                .stream()
                .filter(e -> { return e.getYear() <= nextYear;})
                .collect(Collectors.toList());
        model.addAttribute("yearList", yearList);

	// Viewリスト作成
        JSONObject json = new JSONObject();
        yearList.stream().forEach((e) ->
            {
                json.put(e.getYear(), e.getViewList().stream().map(e1 -> {
                    JSONObject json1 = new JSONObject();
                    json1.put("id", e1.getId());
                    json1.put("label", e1.getName());
                    return json1;
                }).collect(Collectors.toList()));
            });
        model.addAttribute("viewList", json.toJSONString());
    }

    private String escapeXml(String s) {
        return s
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("'", "&#39;")
                .replace("\"", "&quot;");
    }
}
