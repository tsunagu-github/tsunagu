/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：引継ぎコード発行レスポンスDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

import phr.datadomain.entity.DataTransferEntity;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chiba
 */
public class ResponseTakeOverCodeDto extends ResponseBaseDto {
    
    protected String takeOverCode=null;
    protected String expirationDate=null;
    protected String takeOverPassword=null;
    
    /**
     * デフォルトコンストラクタ
     */
    public ResponseTakeOverCodeDto() {
    }

    /**
     * コンストラクタ
     * @param entity
     */
    public ResponseTakeOverCodeDto( DataTransferEntity entity ) {
        
        takeOverCode = entity.getDataTransferCd();
        expirationDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(entity.getExpirationDate());
        takeOverPassword = entity.getPassword();
    }

    public DataTransferEntity createTakeOverCodeEntity( ) {
        DataTransferEntity entity= new DataTransferEntity();
        
        entity.setDataTransferCd( takeOverCode );
        try {
            entity.setExpirationDate( new Timestamp(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(expirationDate).getTime()) );
        } catch (ParseException ex) {
            Logger.getLogger(ResponseTakeOverCodeDto.class.getName()).log(Level.SEVERE, null, ex);
            entity.setExpirationDate( null );
        }
        
        return entity;
    }
        
    public void copyFromTakeOverCodeEntity( DataTransferEntity entity) {
        entity.setDataTransferCd(takeOverCode );
        try {
            entity.setExpirationDate( new Timestamp(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(expirationDate).getTime()) );
        } catch (ParseException ex) {
            Logger.getLogger(ResponseTakeOverCodeDto.class.getName()).log(Level.SEVERE, null, ex);
            entity.setExpirationDate( null );
        }
    }

    /**
     * 引継ぎコード取得
     * @return 
     */
    public String getTakeOverCode() {
        return this.takeOverCode;
    }

    /**
     * 引継ぎコード設定
     * @param value 
     */
    public void setTakeOverCode(String value) {
        this.takeOverCode= value;
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

    /**
     * 引継ぎパスワード取得
     * @return 
     */
    public String getTakePassword() {
        return this.takeOverPassword;
    }

    /**
     * 引継ぎパスワード設定
     * @param value 
     */
    public void setTakeOverPassword(String value) {
        this.takeOverPassword= value;
    }

}
