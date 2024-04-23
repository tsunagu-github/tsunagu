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
public class ReminderMessageDto {
    /**
     * メッセージ見出し
     */
    private String title;
    /**
     * 発信日：タイプ
     */
    private String sendType;
    /**
     * 発信日：期間
     */
    private String sendPeriod;
    /**
     * 発信日：特定日
     */
    private String sendMonth;
    /**
     * メッセージ
     */
    private String sendMessage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendPeriod() {
        return sendPeriod;
    }

    public void setSendPeriod(String sendPeriod) {
        this.sendPeriod = sendPeriod;
    }

    public String getSendMonth() {
        return sendMonth;
    }

    public void setSendMonth(String sendMonth) {
        this.sendMonth = sendMonth;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }
}
