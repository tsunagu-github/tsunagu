/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リクエストDTOの規定クラス
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
public class RequestBaseDto {

    /* PHR ID */
    protected String phrId = null;
    /* アクション */
    protected String action = null;
    /**
     * タイムスタンプ
     */
    private String timestamp;
    /**
     * KeyValue
     */
    private String keyValue;
    /**
     * バージョン
     */
    private String version = "1.0";

    /**
     * ユーザーエージェント
     */
    private String userAgent;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * PHR IDを取得する
     *
     * @return
     */
    public String getPhrId() {
        return this.phrId;
    }

    /**
     * PHR IDを設定する
     *
     * @param value
     */
    public void setPhrId(String value) {
        this.phrId = value;
    }

    /**
     * アクションを取得する
     *
     * @return
     */
    public String getAction() {
        return this.action;
    }

    /**
     * アクションを設定する
     *
     * @param value
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * タイムスタンプ
     *
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * タイムスタンプ
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * KeyValue
     *
     * @return the keyValue
     */
    public String getKeyValue() {
        return keyValue;
    }

    /**
     * KeyValue
     *
     * @param keyValue the keyValue to set
     */
    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
}
