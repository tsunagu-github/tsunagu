/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.web.dto.ReminderMessageDto;

/**
 *
 * @author KISNOTE011
 */
public class ManagementItemReminderDetailForm extends AbstractForm {

    /**
     * ViewID
     */
    private String viewId;
    /**
     * リマインダータイトル
     */
    private String reminderTitle;
    /**
     * 期間
     */
    private String reminderTypeCd;
    /**
     * リマインダーメッセージ
     */
    private List<ReminderMessageDto> reminderMessageDtoList;
    /**
     * 通知
     */
    private String notificationFlg;
    /**
     * 疾病種別
     */
    private List<String> diseaseTypeCd;
    /**
     * 項目
     */
    private List<String> observationDefinitionId;

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getReminderTitle() {
        return reminderTitle;
    }

    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public String getReminderTypeCd() {
        return reminderTypeCd;
    }

    public void setReminderTypeCd(String reminderTypeCd) {
        this.reminderTypeCd = reminderTypeCd;
    }

    public List<ReminderMessageDto> getReminderMessageDtoList() {
        return reminderMessageDtoList;
    }

    public void setReminderMessageDtoList(List<ReminderMessageDto> reminderMessageDtoList) {
        this.reminderMessageDtoList = reminderMessageDtoList;
    }

    public String getNotificationFlg() {
        return notificationFlg;
    }

    public void setNotificationFlg(String notificationFlg) {
        this.notificationFlg = notificationFlg;
    }

    public List<String> getDiseaseTypeCd() {
        return diseaseTypeCd;
    }

    public void setDiseaseTypeCd(List<String> diseaseTypeCd) {
        this.diseaseTypeCd = diseaseTypeCd;
    }

    public List<String> getObservationDefinitionId() {
        return observationDefinitionId;
    }

    public void setObservationDefinitionId(List<String> observationDefinitionId) {
        this.observationDefinitionId = observationDefinitionId;
    }
}
