/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おしらせ・メッセージ詳細取得リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/20
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
public class RequestCommunicationDetailDto extends RequestBaseDto {
    /**
     * コミュニケーションID
     */
    private String id;

    /**
     * シーケンス
     */
    private Integer seq;

    /**
     * プッシュ通知タップフラグ
     */
    private boolean onNotification;

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
     * コミュニケーションID
     * @return the seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * コミュニケーションID
     * @param seq the seq to set
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * プッシュ通知タップフラグ
     * @return the onNotification
     */
    public boolean isOnNotification() {
        return onNotification;
    }

    /**
     * プッシュ通知タップフラグ
     * @param onNotification
     */
    public void setOnNotification(boolean onNotification) {
        this.onNotification = onNotification;
    }
}
