/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おしらせ・メッセージ操作リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/21
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;
import java.util.List;

/**
 *
 * @author kikkawa
 */
public class RequestOperationCommunicationDto extends RequestBaseDto {
    /**
     * コミュニケーションID
     */
    private String id;

    /**
     * シーケンス
     */
    private Integer seq;

    /**
     * 送信者名
     */
    private String senderName;

    /**
     * タイトル
     */
    private String subject;

    /**
     * 本文
     */
    private String bodyText;

    /**
     * 受信者リスト
     */
    private List<CommunicationReceiverDto> receiver;

    /**
     * コミュニケーションID
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * コミュニケーションID
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * シーケンス
     * @return the seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * シーケンス
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
     * 受信者リスト
     * @return the receiver
     */
    public List<CommunicationReceiverDto> getReceiver() {
        return receiver;
    }

    /**
     * 受信者リスト
     * @param receiver the receiver to set
     */
    public void setReceiver(List<CommunicationReceiverDto> receiver) {
        this.receiver = receiver;
    }
}
