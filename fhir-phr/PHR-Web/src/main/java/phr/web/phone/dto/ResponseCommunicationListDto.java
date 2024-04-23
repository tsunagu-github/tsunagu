/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おしらせ・メッセージ情報レスポンスDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/16
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
public class ResponseCommunicationListDto extends ResponseBaseDto {
    //コミュニケーションリストセット
    private List<CommunicationDto> msgInfoList;
    //全体の未読件数
    private int allUnreadCount = 0;
    //おしらせの未読件数
    private int infoUnreadCount = 0;
    //メッセージの未読件数
    private int msgUnreadCount = 0;

    /**
     * @return the msgInfoList
     */
    public List<CommunicationDto> getMsgInfoList() {
        return msgInfoList;
    }

    /**
     * @param msgInfoList the dataList to set
     */
    public void setMsgInfoList(List<CommunicationDto> msgInfoList) {
        this.msgInfoList = msgInfoList;
    }
    
    /**
     * @return the count of all the unread communications
     */
    public int getAllUnreadCount() {
        return allUnreadCount;
    }

    /**
     * @param allUnreadCount the count of all the unread communications to set
     */
    public void setAllUnreadCount(int allUnreadCount) {
        this.allUnreadCount = allUnreadCount;
    }

    /**
     * @return the count of the unread information
     */
    public int getInfoUnreadCount() {
        return infoUnreadCount;
    }

    /**
     * @param infoUnreadCount the count of the unread information to set
     */
    public void setInfoUnreadCount(int infoUnreadCount) {
        this.infoUnreadCount = infoUnreadCount;
    }

    /**
     * @return the count of all the unread messages
     */
    public int getMsgUnreadCount() {
        return msgUnreadCount;
    }

    /**
     * @param msgUnreadCount the count of all the unread messages to set
     */
    public void setMsgUnreadCount(int msgUnreadCount) {
        this.msgUnreadCount = msgUnreadCount;
    }

}
