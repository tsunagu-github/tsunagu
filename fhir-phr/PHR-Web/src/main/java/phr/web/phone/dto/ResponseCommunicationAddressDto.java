/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：メッセージ宛先リストレスポンスDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/23
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;
import java.util.List;

/**
 *
 * @author kikkawa
 */
public class ResponseCommunicationAddressDto extends ResponseBaseDto {
    //宛先リストセット
    private List<CommunicationReceiverDto> addressList;

    /**
     * @return the addressList
     */
    public List<CommunicationReceiverDto> getAddressList() {
        return addressList;
    }

    /**
     * @param addressList the addressList to set
     */
    public void setAddressList(List<CommunicationReceiverDto> addressList) {
        this.addressList = addressList;
    }
    
}
