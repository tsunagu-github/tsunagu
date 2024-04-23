/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：参照用ワンタイムパスワード発行リクエストDTO
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
public class RequestOneTimePasswordDto extends RequestBaseDto {
  
    /**
     * 参照用ワンタイムパスワードDTO
     */
    private String oneTimePassword;
    
    /**
     * 参照用ワンタイムパスワードDTO
     * @return 
     */
    public String getOneTimePassword() {
        return oneTimePassword;
    }
    
    /**
     * 参照用ワンタイムパスワードDTO
     * @param oneTimePassword 
     */
    public void setOneTimePassword( String oneTimePassword ) {
        this.oneTimePassword = oneTimePassword;
    }

}
