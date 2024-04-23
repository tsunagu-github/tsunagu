/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.ObservationDefinitionEntity;
import phr.datadomain.entity.ReminderItemEntity;
import phr.datadomain.entity.ReminderKindEntity;
import phr.datadomain.entity.ReminderMasterEntity;
import phr.datadomain.entity.ReminderMessageEntity;
import phr.datadomain.entity.ReminderTargetPhrIdItemEntity;
import phr.datadomain.entity.ReminderTypeEntity;

/**
 *
 * @author chiba
 */
public interface IReminderPushService {

    List<ReminderMessageEntity> getPushMessageList() throws Throwable;
    List<ReminderTargetPhrIdItemEntity> getPushTargetListPeriod(String reminderTypeId, int reminderLevel) throws Throwable;

    List<ReminderMasterEntity> getReminderList(int year) throws Throwable;
    void deleteReminder(String reminderTypeId) throws Throwable;

    List<ObservationDefinitionEntity> getReminderItemList(int viewId) throws Throwable;
    List<ReminderItemEntity> getCheckedReminderItemList(String reminderTypeId) throws Throwable;
    List<ReminderKindEntity> getCheckedReminderKindList(String reminderTypeId) throws Throwable;
    ReminderMasterEntity getReminder(String reminderTypeId) throws Throwable;
    List<ReminderMessageEntity> getReminderMessageList(String reminderTypeId) throws Throwable;
    List<ReminderTypeEntity> getReminderTypeList() throws Throwable;

    List<String> checkReminder(
            ReminderMasterEntity entity,
            List<ReminderMessageEntity> messageList,
            List<String> kindList,
            List<String> itemList) throws Throwable;

    String registerReminder(
            ReminderMasterEntity entity,
            List<ReminderMessageEntity> messageList,
            List<String> kindList,
            List<String> itemList) throws Throwable;
    ReminderMessageEntity getConsentNotificationControllerMessage() throws Throwable;
}
