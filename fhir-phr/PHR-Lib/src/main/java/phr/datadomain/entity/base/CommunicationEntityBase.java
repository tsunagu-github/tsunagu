/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.CommunicationEntity;

/**
 * コミュニケーション情報のデータオブジェクトです。
 */
public abstract class CommunicationEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public CommunicationEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* コミュニケーションID */
    protected String communicationId = null;
    /* コミュニケーション種別CD */
    protected int communicationTypeCd = 0;
    /* 送信者（PHRID） */
    protected String sendPHRID = null;
    /* 送信者（保険者） */
    protected String sendInsurerNo = null;
    /* アカウントID */
    protected String sendAccountId = null;
    /* 送信者（医療機関） */
    protected String sendMedicalOrganizationCd = null;
    /* 送信者名 */
    protected String senderName = null;
    /* タイトル */
    protected String subject = null;
    /* 本文 */
    protected String bodyText = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  CommunicationEntity
     */
    public static CommunicationEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  CommunicationEntity
     */
    public static CommunicationEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        CommunicationEntity entity = new CommunicationEntity();
        entity.setCommunicationId(getString(dataRow, "CommunicationId"));
        entity.setCommunicationTypeCd(getInt(dataRow, "CommunicationTypeCd"));
        entity.setSendPHRID(getString(dataRow, "SendPHRID"));
        entity.setSendInsurerNo(getString(dataRow, "SendInsurerNo"));
        entity.setSendAccountId(getString(dataRow, "SendAccountId"));
        entity.setSendMedicalOrganizationCd(getString(dataRow, "SendMedicalOrganizationCd"));
        entity.setSenderName(getString(dataRow, "SenderName"));
        entity.setSubject(getString(dataRow, "Subject"));
        entity.setBodyText(getString(dataRow, "BodyText"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("コミュニケーションID    ：" + entity.getCommunicationId());
            logger.debug("コミュニケーション種別CD：" + entity.getCommunicationTypeCd());
            logger.debug("送信者（PHRID）         ：" + entity.getSendPHRID());
            logger.debug("送信者（保険者）        ：" + entity.getSendInsurerNo());
            logger.debug("アカウントID            ：" + entity.getSendAccountId());
            logger.debug("送信者（医療機関）      ：" + entity.getSendMedicalOrganizationCd());
            logger.debug("送信者名                ：" + entity.getSenderName());
            logger.debug("タイトル                ：" + entity.getSubject());
            logger.debug("本文                    ：" + entity.getBodyText());
            logger.debug("作成日時                ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時            ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * コミュニケーションIDを取得する
     *
     * @return
     */
    public String getCommunicationId() {
        return this.communicationId;
    }
    /**
     * コミュニケーションIDを設定する
     *
     * @param value
     */
    public void setCommunicationId(String value) {
        this.communicationId = value;
    }

    /**
     * コミュニケーション種別CDを取得する
     *
     * @return
     */
    public int getCommunicationTypeCd() {
        return this.communicationTypeCd;
    }
    /**
     * コミュニケーション種別CDを設定する
     *
     * @param value
     */
    public void setCommunicationTypeCd(int value) {
        this.communicationTypeCd = value;
    }

    /**
     * 送信者（PHRID）を取得する
     *
     * @return
     */
    public String getSendPHRID() {
        return this.sendPHRID;
    }
    /**
     * 送信者（PHRID）を設定する
     *
     * @param value
     */
    public void setSendPHRID(String value) {
        this.sendPHRID = value;
    }

    /**
     * 送信者（保険者）を取得する
     *
     * @return
     */
    public String getSendInsurerNo() {
        return this.sendInsurerNo;
    }
    /**
     * 送信者（保険者）を設定する
     *
     * @param value
     */
    public void setSendInsurerNo(String value) {
        this.sendInsurerNo = value;
    }

    /**
     * アカウントIDを取得する
     *
     * @return
     */
    public String getSendAccountId() {
        return this.sendAccountId;
    }
    /**
     * アカウントIDを設定する
     *
     * @param value
     */
    public void setSendAccountId(String value) {
        this.sendAccountId = value;
    }

    /**
     * 送信者（医療機関）を取得する
     *
     * @return
     */
    public String getSendMedicalOrganizationCd() {
        return this.sendMedicalOrganizationCd;
    }
    /**
     * 送信者（医療機関）を設定する
     *
     * @param value
     */
    public void setSendMedicalOrganizationCd(String value) {
        this.sendMedicalOrganizationCd = value;
    }

    /**
     * 送信者名を取得する
     *
     * @return
     */
    public String getSenderName() {
        return this.senderName;
    }
    /**
     * 送信者名を設定する
     *
     * @param value
     */
    public void setSenderName(String value) {
        this.senderName = value;
    }

    /**
     * タイトルを取得する
     *
     * @return
     */
    public String getSubject() {
        return this.subject;
    }
    /**
     * タイトルを設定する
     *
     * @param value
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * 本文を取得する
     *
     * @return
     */
    public String getBodyText() {
        return this.bodyText;
    }
    /**
     * 本文を設定する
     *
     * @param value
     */
    public void setBodyText(String value) {
        this.bodyText = value;
    }

    /**
     * 作成日時を取得する
     *
     * @return
     */
    public Timestamp getCreateDateTime() {
        return this.createDateTime;
    }
    /**
     * 作成日時を設定する
     *
     * @param value
     */
    public void setCreateDateTime(Timestamp value) {
        this.createDateTime = value;
    }

    /**
     * 最終更新日時を取得する
     *
     * @return
     */
    public Timestamp getUpdateDateTime() {
        return this.updateDateTime;
    }
    /**
     * 最終更新日時を設定する
     *
     * @param value
     */
    public void setUpdateDateTime(Timestamp value) {
        this.updateDateTime = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "communicationId=" + communicationId + 
                ", communicationTypeCd=" + communicationTypeCd + 
                ", sendPHRID=" + sendPHRID + 
                ", sendInsurerNo=" + sendInsurerNo + 
                ", sendAccountId=" + sendAccountId + 
                ", sendMedicalOrganizationCd=" + sendMedicalOrganizationCd + 
                ", senderName=" + senderName + 
                ", subject=" + subject + 
                ", bodyText=" + bodyText + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
