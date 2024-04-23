/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

import java.sql.Timestamp;

/**
 *
 * @author KISNOTE011
 */
public class CommunicationDto {
    /* コミュニケーションID */
    protected String communicationId = null;
    public String getCommunicationId() {
        return this.communicationId;
    }
    public void setCommunicationId(String value) {
        this.communicationId = value;
    }
    
    /* コミュニケーション種別CD */
    protected int communicationTypeCd = 0;
    public int getCommunicationTypeCd() {
        return this.communicationTypeCd;
    }
    public void setCommunicationTypeCd(int value) {
        this.communicationTypeCd = value;
    }

    /* 送信者（PHRID） */
    protected String sendPHRID = null;
    public String getSendPHRID() {
        return this.sendPHRID;
    }
    public void setSendPHRID(String value) {
        this.sendPHRID = value;
    }

    /* 送信者（保険者） */
    protected String sendInsurerNo = null;
    public String getSendInsurerNo() {
        return this.sendInsurerNo;
    }
    public void setSendInsurerNo(String value) {
        this.sendInsurerNo = value;
    }

    /* アカウントID */
    protected String sendAccountId = null;
    public String getSendAccountId() {
        return this.sendAccountId;
    }
    public void setSendAccountId(String value) {
        this.sendAccountId = value;
    }

    /* 送信者（医療機関） */
    protected String sendMedicalOrganizationCd = null;
    public String getSendMedicalOrganizationCd() {
        return this.sendMedicalOrganizationCd;
    }
    public void setSendMedicalOrganizationCd(String value) {
        this.sendMedicalOrganizationCd = value;
    }

    /* 送信者名 */
    protected String senderName = null;
    public String getSenderName() {
        return this.senderName;
    }
    public void setSenderName(String value) {
        this.senderName = value;
    }

    /* タイトル */
    protected String subject = null;
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String value) {
        this.subject = value;
    }

    /* 本文 */
    protected String bodyText = null;
    public String getBodyText() {
        return this.bodyText;
    }
    public void setBodyText(String value) {
        this.bodyText = value;
    }

    /* 作成日時 */
    protected Timestamp createDateTime = null;
    public Timestamp getCreateDateTime() {
        return this.createDateTime;
    }
    public void setCreateDateTime(Timestamp value) {
        this.createDateTime = value;
    }

    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    public Timestamp getUpdateDateTime() {
        return this.updateDateTime;
    }
    public void setUpdateDateTime(Timestamp value) {
        this.updateDateTime = value;
    }
    
    /* Seq */
    protected int seqNo = 0;
    public int getSeqNo() {
        return this.seqNo;
    }
    public void setSeqNo(int value) {
        this.seqNo = value;
    }

    // 既読フラグ
    protected boolean readFlg;
    public boolean getReadFlg(){
        return this.readFlg;
    }
    public void setReadFlg(boolean readFlg){
        this.readFlg=readFlg;
    }
    
    // 送信者組織名
    protected String sendOrganizationName;
    public String getSendOrganizationName(){
        return this.sendOrganizationName;
    }
    public void setSendOrganizationName(String sendOrganizationName){
        this.sendOrganizationName=sendOrganizationName;
    }

    // 左ボタンフラグ
    protected String leftFlg;
    public String getLeftFlg(){
        return this.leftFlg;
    }
    public void setLeftFlg(String leftFlg){
        this.leftFlg=leftFlg;
    }

    // 右ボタンフラグ
    protected String rightFlg;
    public String getRightFlg(){
        return this.rightFlg;
    }
    public void setRightFlg(String rightFlg){
        this.rightFlg=rightFlg;
    }
    
    // コミュニケーションタイプ名
    protected String communicationTypeName;
    public String getCommunicationTypeName(){
        return this.communicationTypeName;
    }
    public void setCommunicationTypeName(String communicationTypeName){
        this.communicationTypeName=communicationTypeName;
    }

    /* 最終更新日時 */
    protected String strUpdateDateTime = null;
    public String getStrUpdateDateTime() {
        return this.strUpdateDateTime;
    }
    public void setStrUpdateDateTime(String strUpdateDateTime) {
        this.strUpdateDateTime = strUpdateDateTime;
    }
}
