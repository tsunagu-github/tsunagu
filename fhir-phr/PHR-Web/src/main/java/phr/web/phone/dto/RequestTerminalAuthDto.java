/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：端末認証リクエストDTO
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
public class RequestTerminalAuthDto extends RequestBaseDto {
    /**
     * トークンID
     */
    private String tokenId;

    /**
     * トークンID
     * @return the id
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * トークンID
     * @param id the id to set
     */
    public void setTokenId(String id) {
        this.tokenId = id;
    }
}
