/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.dto;

import java.util.ArrayList;
import phr.datadomain.entity.ObservationDefinitionInsurerEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;

/**
 *
 * @author KISNOTE011
 */
public class ManagementItemListDto {
    /**
     * 項目リスト
     */
    private ObservationDefinitionInsurerEntity managementItem;
    public ObservationDefinitionInsurerEntity getManagementItem() {
        return managementItem;
    }
    public void setManagementItem(ObservationDefinitionInsurerEntity value) {
        this.managementItem = value;
    }

    /**
     * 項目入力種別
     */
    private ObservationDefinitionTypeEntity managementItemKind;
    public ObservationDefinitionTypeEntity getManagementItemKind() {
        return managementItemKind;
    }
    public void setManagementItemKind(ObservationDefinitionTypeEntity value) {
        this.managementItemKind = value;
    }

    /**
     * 項目リマインダ
     */
    private ObservationDefinitionRangeEntity managementItemReminder;
    public ObservationDefinitionRangeEntity getManagementItemReminder() {
        return managementItemReminder;
    }
    public void setManagementItemReminder(ObservationDefinitionRangeEntity value) {
        this.managementItemReminder = value;
    }
}
