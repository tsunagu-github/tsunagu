/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

/**
 *
 * @author KISNOTE011
 */
public class ManagementItemReminderForm extends AbstractForm {
    /**
     * 選択年
     */
    private int selectYear;
    /**
     * リマインダーID
     */
    private String reminderId;

    public int getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(int selectYear) {
        this.selectYear = selectYear;
    }

    public String getReminderId() {
        return reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }
}
