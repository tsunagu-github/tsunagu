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
public class RequestEntryTakeOverCodeDto extends RequestBaseDto {
    private String takeOverPassword;
    private String takeOverCode;

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
    public void setPatientDto( String takeOverPassword ) {
        this.takeOverPassword = takeOverPassword;
    }

    /**
     * 引継ぎコード
     * @return 
     */
    public String getTakeOverCode() {
        return takeOverCode;
    }
    
    /**
     * 引継ぎコード
     * @param takeOverCode 
     */
    public void setTakeOverCode( String takeOverCode ) {
        this.takeOverCode = takeOverCode;
    }
}
