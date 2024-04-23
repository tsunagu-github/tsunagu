/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おしらせ・メッセージ受信者情報を格納するクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author kikkawa
 */
public class CommunicationReceiverDto {
    /**
     * 保険者No
     */
    private String insurerNo;
    /**
     * 医療機関コード
     */
    private String organizationCd;
    /**
     * 機関名称
     */
    private String name;

    /**
     * 保険者No
     * @return the communication ID
     */
    public String getInsurerNo() {
        return insurerNo;
    }

    /**
     * 保険者No
     * @param insurerNo the insurerNo to set
     */
    public void setInsurerNo(String insurerNo) {
        this.insurerNo = insurerNo;
    }

    /**
     * 医療機関コード
     * @return the organizationCd
     */
    public String getOrganizationCd() {
        return organizationCd;
    }

    /**
     * 医療機関コード
     * @param organizationCd the organizationCd to set
     */
    public void setOrganizationCd(String organizationCd) {
        this.organizationCd = organizationCd;
    }

    /**
     * 機関名称
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 機関名称
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
