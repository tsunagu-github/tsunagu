/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.InsurerViewDefinitionEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ReminderMasterEntity;
import phr.datadomain.entity.ReminderMessageEntity;
import phr.service.IManagementItemService;
import phr.service.IReminderPushService;
import phr.web.ActionConst;
import phr.web.FormConst;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.dto.ReminderInfoDto;
import phr.web.dto.ReminderMessageDto;
import phr.web.form.ManagementItemReminderDetailForm;

/**
 *
 * @author KISNOTE011
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.MANAGEMENT_ITEM_REMINDER_DETAIL,"/"})
public class ManagementItemReminderDetailController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ManagementItemReminderDetailController.class);

    /**画面ID */
    private final String FORM_CD = FormConst.MANAGEMENT_ITEM_REMINDER_DETAIL_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.MANAGEMENT_ITEM_REMINDER_DETAIL_FORM_TITLE;
    
    /**
     * インジェクション：管理項目処理
     */
    @Autowired
    @Qualifier("ManagementItemService")
    private IManagementItemService managementItemService;
    
    /**
     * インジェクション：リマインダプッシュ処理
     */
    @Autowired
    @Qualifier("ReminderPushService")
    private IReminderPushService reminderPushService;
   
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
    public String defaultAction(ManagementItemReminderDetailForm form, Model model, HttpServletRequest request , BindingResult result) throws Throwable {
        logger.debug("Start");

        this.initPage(model, form, true);
        model.addAttribute("initialDisplayFlag", "1");

        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MANAGEMENT_ITEM_REMINDER_DETAIL);
    }
    
    /**
     * 戻るアクションメソッド
     * @param form
     * @param model
     * @param request
     * @return String
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=return")
    public String returnAction(ManagementItemReminderDetailForm form, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");
        logger.debug("End");
    	return ActionConst.redirectActionPage(ActionConst.MANAGEMENT_ITEM_REMINDER);
    }
    
    /**
     * 中止アクションメソッド
     * @param form
     * @param model
     * @param request
     * @return String
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=abort")
    public String abortAction(ManagementItemReminderDetailForm form, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");
        logger.debug("End");
    	return ActionConst.redirectActionPage(ActionConst.MANAGEMENT_ITEM_REMINDER_DETAIL);
    }
    
    /**
     * ViewID選択アクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return String
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=selectViewId")
    public String selectViewIdAction(ManagementItemReminderDetailForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        this.initPage(model, form, true);

        logger.debug("End");
        return ActionConst.actionPage(ActionConst.MANAGEMENT_ITEM_REMINDER_DETAIL);
    }
    
    /**
     * 点検アクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return String
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=check")
    public String checkAction(ManagementItemReminderDetailForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");

        if (validateForm(form, result)) {

            List<String> messages = reminderPushService.checkReminder(
                    getReminderMasterEntity(form),
                    getReminderMessageEntityList(form),
                    form.getDiseaseTypeCd(),
                    getObservationDefinitionIdList(form));

            messages.stream().forEach((message) -> {
                result.addError(new ObjectError("", message));
            });
        }

        this.initPage(model, form, false);

        logger.debug("End");
    	return ActionConst.actionPage(ActionConst.MANAGEMENT_ITEM_REMINDER_DETAIL);
    }
    
    /**
     * 更新アクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return String
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=update")
    public String updateAction(ManagementItemReminderDetailForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");

        if (validateForm(form, result)) {

            reminderPushService.registerReminder(
                    getReminderMasterEntity(form),
                    getReminderMessageEntityList(form),
                    form.getDiseaseTypeCd(),
                    getObservationDefinitionIdList(form));

            model.addAttribute("saveCompleted", true);

            this.initPage(model, form, true);

        } else {
            this.initPage(model, form, false);
        }

        logger.debug("End");
    	return ActionConst.actionPage(ActionConst.MANAGEMENT_ITEM_REMINDER_DETAIL);
    }
    
    /**
     * 登録アクションメソッド
     * @param form
     * @param result
     * @param model
     * @param request
     * @return String
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST }, params = "command=insert")
    public String insertAction(ManagementItemReminderDetailForm form, BindingResult result, Model model,HttpServletRequest request) throws Throwable {
    	logger.debug("Start");

        if (validateForm(form, result)) {

            String reminderTypeId = reminderPushService.registerReminder(
                    getReminderMasterEntity(form),
                    getReminderMessageEntityList(form),
                    form.getDiseaseTypeCd(),
                    getObservationDefinitionIdList(form));

            ((ReminderInfoDto)sessionUtility.getSession(SessionConst.REMINDER_INFO))
                .setReminderTypeId(reminderTypeId);

            model.addAttribute("saveCompleted", true);

            this.initPage(model, form, true);

        } else {
            this.initPage(model, form, false);
        }

        logger.debug("End");
    	return ActionConst.actionPage(ActionConst.MANAGEMENT_ITEM_REMINDER_DETAIL);
    }
    
    /**
     * <pre>画面表示項目を設定</pre>
     *
     * @param model
     */
    private void initPage(Model model, ManagementItemReminderDetailForm form, boolean refresh) throws Throwable {
        ControllerUtility.InitHeader(model);
        model.addAttribute(form);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);

        ReminderInfoDto reminderInfo =
                (ReminderInfoDto)sessionUtility.getSession(SessionConst.REMINDER_INFO);
        ReminderMasterEntity reminderMaster = null;
        if (!StringUtils.isEmpty(reminderInfo.getReminderTypeId())) {
            reminderMaster = reminderPushService.getReminder(
                    reminderInfo.getReminderTypeId());
            model.addAttribute("editModeFlag", "1");
        }

        Integer viewId = null;
        if (reminderMaster != null) {
            viewId = reminderMaster.getViewId();
        } else if (!StringUtils.isEmpty(form.getViewId())) {
            viewId = Integer.valueOf(form.getViewId());
        }

        if (viewId != null) {
            InsurerViewDefinitionEntity insurerViewDefinition =
                    managementItemService.getView(viewId);
            model.addAttribute("viewName", insurerViewDefinition.getViewName());
        }
//        AccountEntity accountEntity = (AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
        MedicalOrganizationEntity accountEntity = (MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
        
        // Viewリスト作成
//                model.addAttribute("viewList", managementItemService.getYearViewIdList(accountEntity.getInsurerNo())
        model.addAttribute("viewList", managementItemService.getYearViewIdList(accountEntity.getMedicalOrganizationCd())
                .stream()
                .filter(e -> { return e.getYear() == reminderInfo.getYear();})
                .findFirst()
                .get()
                .getViewList());

	// リマインダー種別リスト作成
        model.addAttribute("reminderTypeList", reminderPushService.getReminderTypeList());

        if (viewId != null) {
            
            // 疾病種別リスト作成
            model.addAttribute("diseaseTypeList",
                    managementItemService.getDiseaseTypeList(viewId)
                        .stream()
                        .filter(e -> { return 0 < e.getDiseaseTypeCd();})
                        .collect(Collectors.toList())
            );

            // 項目リスト作成
            model.addAttribute("observationDefinitionList",
                    reminderPushService.getReminderItemList(viewId));
        }

        if (!refresh) {
            return;
        }

        if (StringUtils.isEmpty(reminderInfo.getReminderTypeId())) {
            form.setReminderTitle("");
            form.setReminderTypeCd("");
            List<ReminderMessageDto> reminderMessageDtoList = new ArrayList<>();
            ReminderMessageDto reminderMessageDto;
            reminderMessageDto = new ReminderMessageDto();
            reminderMessageDto.setTitle("");
            reminderMessageDto.setSendType("0");
            reminderMessageDto.setSendPeriod("");
            reminderMessageDto.setSendMonth("");
            reminderMessageDto.setSendMessage("");
            reminderMessageDtoList.add(reminderMessageDto);
            reminderMessageDto = new ReminderMessageDto();
            reminderMessageDto.setTitle("");
            reminderMessageDto.setSendType("0");
            reminderMessageDto.setSendPeriod("");
            reminderMessageDto.setSendMonth("");
            reminderMessageDto.setSendMessage("");
            reminderMessageDtoList.add(reminderMessageDto);
            form.setReminderMessageDtoList(reminderMessageDtoList);
            form.setNotificationFlg("1");
            form.setDiseaseTypeCd(Arrays.asList(new String[]{}));
            form.setObservationDefinitionId(Arrays.asList(new String[]{}));
        } else {
            form.setViewId("");
            form.setReminderTitle(reminderMaster.getReminderTitle());
            form.setReminderTypeCd(String.valueOf(reminderMaster.getReminderTypeCd()));
            form.setNotificationFlg(String.valueOf(reminderMaster.getNotificationFlg()));
            final String sendType = reminderMaster.getSendType();

            form.setReminderMessageDtoList(
                reminderPushService.getReminderMessageList(reminderInfo.getReminderTypeId())
                    .stream()
                    .map(e -> {
                        ReminderMessageDto reminderMessageDto = new ReminderMessageDto();
                        reminderMessageDto.setTitle(e.getTitle());
                        reminderMessageDto.setSendType(sendType);
                        reminderMessageDto.setSendPeriod(
                                e.getSendPeriod() == null ? null : String.valueOf(e.getSendPeriod()));
                        reminderMessageDto.setSendMonth(
                                e.getSendMonth()== null ? null : String.valueOf(e.getSendMonth()));
                        reminderMessageDto.setSendMessage(e.getSendMessage());
                        return reminderMessageDto;
                    })
                    .collect(Collectors.toList()));

            form.setDiseaseTypeCd(
                reminderPushService.getCheckedReminderKindList(reminderInfo.getReminderTypeId())
                    .stream()
                    .map(e -> { return String.valueOf(e.getDiseaseTypeCd());})
                    .collect(Collectors.toList()));

            form.setObservationDefinitionId(
                reminderPushService.getCheckedReminderItemList(reminderInfo.getReminderTypeId())
                    .stream()
                    .map(e -> { return e.getObservationDefinitionId();})
                    .collect(Collectors.toList()));
        }
    }

    private ReminderMasterEntity getReminderMasterEntity(ManagementItemReminderDetailForm form) throws Throwable {

        ReminderInfoDto reminderInfo =
                (ReminderInfoDto)sessionUtility.getSession(SessionConst.REMINDER_INFO);

        ReminderMasterEntity reminderMaster;
        if (!StringUtils.isEmpty(reminderInfo.getReminderTypeId())) {
            reminderMaster = reminderPushService.getReminder(
                    reminderInfo.getReminderTypeId());
        } else {
            reminderMaster = new ReminderMasterEntity();
            reminderMaster.setViewId(Integer.valueOf(form.getViewId()));
        }
        reminderMaster.setReminderTitle(form.getReminderTitle());
        reminderMaster.setReminderTypeCd(Integer.valueOf(form.getReminderTypeCd()));
        reminderMaster.setNotificationFlg("1".equals(form.getNotificationFlg()) ? 1 : 0);
        reminderMaster.setReminderKindCd("");

        String sendType = form.getReminderMessageDtoList().get(0).getSendType();
        reminderMaster.setSendType(sendType);

        return reminderMaster;
    }

    private List<ReminderMessageEntity> getReminderMessageEntityList(ManagementItemReminderDetailForm form) throws Throwable {

        List<ReminderMessageEntity> list = new ArrayList<>();

        String sendType = form.getReminderMessageDtoList().get(0).getSendType();
        int i = 1;
        for (ReminderMessageDto e : form.getReminderMessageDtoList()) {
            ReminderMessageEntity entity = new ReminderMessageEntity();
            list.add(entity);
            entity.setReminderLevel(i);
            if (sendType.equals("0")) {
                entity.setSendMessage(e.getSendMessage());
                entity.setTitle(e.getTitle());
                if (!StringUtils.isEmpty(e.getSendPeriod())) {
                    entity.setSendPeriod(Integer.valueOf(e.getSendPeriod()));
                }
            } else if (i == 1) {
                entity.setSendMessage(e.getSendMessage());
                entity.setTitle(e.getTitle());
                entity.setSendMonth(Integer.valueOf(e.getSendMonth()));
            } else {
                entity.setSendMessage("");
                entity.setTitle("");
            }
            i++;
        }

        return list;
    }

    private List<String> getObservationDefinitionIdList(ManagementItemReminderDetailForm form) throws Throwable {

        String sendType = form.getReminderMessageDtoList().get(0).getSendType();

        if (sendType.equals("0")) {
            return form.getObservationDefinitionId();
        }

        return null;
    }

    private static final Pattern SEND_PERIOD_PATTERN = Pattern.compile("^\\d{1,2}$");

    private boolean validateForm(ManagementItemReminderDetailForm form, BindingResult result) throws Throwable {

        String sendType = form.getReminderMessageDtoList().get(0).getSendType();

        if (form.getReminderMessageDtoList().size() < 2) {
            throw new RuntimeException();
        }

        if (!Arrays.asList(new String[]{"0", "1"}).contains(sendType)) {
            throw new RuntimeException();
        }

        if (StringUtils.isEmpty(form.getReminderTitle())) {
            result.addError(new ObjectError("", "リマインダータイトルが未入力です。"));
        }

        int i = 0;
        for (ReminderMessageDto message : form.getReminderMessageDtoList()) {
            if (i == 0) {
                if (StringUtils.isEmpty(message.getTitle())) {
                    result.addError(new ObjectError("", "メッセージ見出しが未入力です。"));
                }
                if ("0".equals(sendType)) {
                    if (StringUtils.isEmpty(message.getSendPeriod())) {
                        result.addError(new ObjectError("", "期間が未入力です。"));
                    } else if (!SEND_PERIOD_PATTERN.matcher(message.getSendPeriod()).matches()) {
                        result.addError(new ObjectError("", "期間は数値で入力してください。"));
                    }
                } else {
                    if (StringUtils.isEmpty(message.getSendMonth())) {
                        result.addError(new ObjectError("", "特定月が未入力です。"));
                    } else if (!Arrays.asList(
                            new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"})
                            .contains(message.getSendMonth())) {
                        throw new RuntimeException();
                    }
                }
                if (StringUtils.isEmpty(message.getSendMessage())) {
                    result.addError(new ObjectError("", "メッセージが未入力です。"));
                }
            } else if ("0".equals(sendType)) {
                String indexPrefixStr = i == 1 ? "(再)" : "(再" + i + ")";
                if (!StringUtils.isEmpty(message.getTitle()) ||
                        !StringUtils.isEmpty(message.getSendPeriod()) ||
                        !StringUtils.isEmpty(message.getSendMessage())) {
                    if (StringUtils.isEmpty(message.getTitle())) {
                        result.addError(new ObjectError("", indexPrefixStr + "メッセージ見出しが未入力です。"));
                    }
                    if (StringUtils.isEmpty(message.getSendPeriod())) {
                        result.addError(new ObjectError("", indexPrefixStr + "期間が未入力です。"));
                    } else if (!SEND_PERIOD_PATTERN.matcher(message.getSendPeriod()).matches()) {
                        result.addError(new ObjectError("", indexPrefixStr + "期間は数値で入力してください。"));
                    }
                    if (StringUtils.isEmpty(message.getSendMessage())) {
                        result.addError(new ObjectError("", indexPrefixStr + "メッセージが未入力です。"));
                    }
                }
            }
            i++;
        }

        if (form.getDiseaseTypeCd() == null || form.getDiseaseTypeCd().isEmpty()) {
            result.addError(new ObjectError("", "疾病種別が未入力です。"));
        }

        if ("0".equals(sendType)) {
            if (form.getObservationDefinitionId() == null || form.getObservationDefinitionId().isEmpty()) {
                result.addError(new ObjectError("", "項目が未入力です。"));
            }
        }

        if (result.getAllErrors().isEmpty()) {
            boolean foundEmpty = false;
            for (i = 1; i < form.getReminderMessageDtoList().size(); i++) {
                ReminderMessageDto dto = form.getReminderMessageDtoList().get(i);
                if (StringUtils.isEmpty(dto.getTitle())) {
                    foundEmpty = true;
                } else if (foundEmpty) {
                    result.addError(new ObjectError("", "メッセージは上から順に入力してください。"));
                    break;
                }
            }
        }

        if (result.getAllErrors().isEmpty()) {
            if ("0".equals(sendType)) {
                int prevSendPeriod = -1;
                for (ReminderMessageDto message : form.getReminderMessageDtoList()) {
                    if (StringUtils.isEmpty(message.getTitle())) {
                        break;
                    }
                    int sendPeriod = Integer.valueOf(message.getSendPeriod());
                    if (sendPeriod <= prevSendPeriod) {
                        result.addError(new ObjectError("", "メッセージの期間は上から順に大きな値を入力してください。"));
                        break;
                    }
                    prevSendPeriod = sendPeriod;
                }
            }
        }

        return result.getAllErrors().isEmpty();
    }
}
