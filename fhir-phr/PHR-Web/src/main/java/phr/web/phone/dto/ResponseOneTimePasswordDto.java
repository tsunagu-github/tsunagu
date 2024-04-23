/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：参照用ワンタイムパスワード発行レスポンスDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

import phr.datadomain.entity.OneTimePasswordEntity;

/**
 *
 * @author chiba
 */
public class ResponseOneTimePasswordDto extends ResponseBaseDto {

    protected String oneTimePassword=null;
    protected String expirationDate=null;

    /**
     * コンストラクタ
     * @param entity
     */
    public void ResponseOneTimePasswordDto( OneTimePasswordEntity entity ) {
        oneTimePassword = entity.getOneTimePassword();
        expirationDate = entity.getExpirationDate();
    }
    
    /**
     * ワンタイムパスワード取得
     * @return 
     */
    public String getOneTimePassword() {
        return this.oneTimePassword;
    }

    /**
     * ワンタイムパスワード設定
     * @param value 
     */
    public void setOneTimePassword(String value) {
        this.oneTimePassword = value;
    }

    /**
     * 有効期限取得
     * @return 
     */
    public String getExpirationDate() {
        return this.expirationDate;
    }

    /**
     * 有効期限設定
     * @param value 
     */
    public void setExpirationDate(String value) {
        this.expirationDate= value;
    }
}
