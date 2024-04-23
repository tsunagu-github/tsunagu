/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：PHRID採番用レスポンスDTO
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
public class ResponsePhrIdNumberingDto extends ResponseBaseDto {
    /* PHR ID */
    protected String phrId = null;
    /**
     * KeyID
     */
    private String keyId;    
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
     * KeyID
     * @return the keyId
     */
    public String getKeyId() {
        return keyId;
    }

    /**
     * KeyID
     * @param keyId the keyId to set
     */
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
    
}
