/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

/**
 *
 * @author kis-note
 */
public class ReminderInfoDto {

    private int year;
    private String reminderTypeId;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getReminderTypeId() {
        return reminderTypeId;
    }

    public void setReminderTypeId(String reminderTypeId) {
        this.reminderTypeId = reminderTypeId;
    }
}
