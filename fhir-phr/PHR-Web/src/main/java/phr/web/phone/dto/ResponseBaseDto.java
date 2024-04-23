/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：レスポンスDTOの規定クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author daisuke
 */
public class ResponseBaseDto {

    /**
     * ステータス：正常終了
     */
    public static String SUCCESS = "0";
    /**
     * ステータス：認証失敗
     */
    public static String TARMINAL_AUTH_ERROR = "8";
    /**
     * ステータス：異常終了
     */
    public static String ERROR = "9";
    /**
     * 処理ステータス
     */
    private String status = null;
    /**
     * エラーメッセージ
     */
    private String message = null;
    /**
     * ユーザAgent
     */
    private String userAgent;
    /**
     * ブラウザ種別
     */
    private String browserType;

    /**
     * 未読メッセージ件数
     */
    private Integer unreadMessageCount;
    /**
     * 処理ステータス
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 処理ステータス
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * エラーメッセージ
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * エラーメッセージ
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * ユーザAgent
     * @return 
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * ユーザAgent
     * @param userAgent 
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * ブラウザ種別
     * @return 
     */
    public String getBrowserType() {
        return browserType;
    }

    /**
     * ブラウザ種別
     * @param browserType 
     */
    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    /**
     * 未読メッセージ件数
     * @return the unreadMessageCount
     */
    public Integer getUnreadMessageCount() {
        return unreadMessageCount;
    }

    /**
     * 未読メッセージ件数
     * @param unreadMessageCount the unreadMessageCount to set
     */
    public void setUnreadMessageCount(Integer unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

}
