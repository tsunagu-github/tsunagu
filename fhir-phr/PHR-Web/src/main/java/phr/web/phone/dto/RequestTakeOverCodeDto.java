/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：引継ぎコード発行リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author chiba
 */
public class RequestTakeOverCodeDto extends RequestBaseDto {
   
    /**
     * 引継ぎパスワードDTO
     */
    private String takeOverPassword;
    
    /**
     * 引継ぎパスワードDTO
     * @return 
     */
    public String getTakeOverPassword() {
        return takeOverPassword;
    }
    
    /**
     * 引継ぎパスワードDTO
     * @param takeOverPassword 
     */
    public void setTakeOverPassword( String takeOverPassword ) {
        this.takeOverPassword = takeOverPassword;
    }
}
