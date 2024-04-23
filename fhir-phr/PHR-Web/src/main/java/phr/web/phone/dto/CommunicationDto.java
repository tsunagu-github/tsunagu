/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おしらせ・メッセージを格納するクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/16
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author kikkawa
 */
public class CommunicationDto {
    /**
     * コミュニケーションID
     */
    private String id;
    /**
     * コミュニケーション受信者のシーケンス
     */
    private Integer seq;
    /**
     * 送信者名
     */
    private String senderName;
    /**
     * コミュニケーション種別コード
     */
    private Integer type;
    /**
     * 更新日
     */
    private String date;
    /**
     * タイトル
     */
    private String subject;
    /**
     * 本文
     */
    private String bodyText;
    /**
     * 既読フラグ
     */
    private boolean readFlg;
    /**
     * 送信メッセージフラグ
     */
    private boolean self;
    /**
     * 送信元保険者No
     */
    private String insurerNo;
    /**
     * 送信元医療機関コード
     */
    private String organizationCd;

    /**
     * コミュニケーションID
     * @return the communication ID
     */
    public String getId() {
        return id;
    }

    /**
     * コミュニケーションID
     * @param id the communication ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * コミュニケーション受信者のシーケンス
     * @return the seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * コミュニケーション受信者のシーケンス
     * @param seq the seq to set
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 送信者名
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * 送信者名
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * コミュニケーション種別コード
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * コミュニケーション種別コード
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 更新日
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * 更新日
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * タイトル
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * タイトル
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 本文
     * @return the bodyText
     */
    public String getBodyText() {
        return bodyText;
    }

    /**
     * 本文
     * @param bodyText the bodyText to set
     */
    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    /**
     * 既読フラグ
     * @return the readFlg
     */
    public boolean isReadFlg() {
        return readFlg;
    }

    /**
     * 既読フラグ
     * @param readFlg the readFlg to set
     */
    public void setReadFlg(boolean readFlg) {
        this.readFlg = readFlg;
    }
    
    /**
     * 送信メッセージフラグ
     * @return the self send flag
     */
    public boolean isSelf() {
        return self;
    }

    /**
     * 送信メッセージフラグ
     * @param self the self send flag to set
     */
    public void setSelf(boolean self) {
        this.self = self;
    }

    /**
     * 送信元保険者No
     * @return the insurerNo ID
     */
    public String getInsurerNo() {
        return insurerNo;
    }

    /**
     * 送信元保険者No
     * @param insurerNo the insurerNo to set
     */
    public void setInsurerNo(String insurerNo) {
        this.insurerNo = insurerNo;
    }

    /**
     * 送信元医療機関コード
     * @return the organizationCd
     */
    public String getOrganizationCd() {
        return organizationCd;
    }

    /**
     * 送信元医療機関コード
     * @param organizationCd the organizationCd to set
     */
    public void setOrganizationCd(String organizationCd) {
        this.organizationCd = organizationCd;
    }
}
