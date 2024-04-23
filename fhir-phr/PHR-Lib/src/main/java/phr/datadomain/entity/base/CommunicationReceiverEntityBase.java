/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション受信者のデータオブジェクト
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
import phr.datadomain.entity.CommunicationReceiverEntity;

/**
 * コミュニケーション受信者のデータオブジェクトです。
 */
public abstract class CommunicationReceiverEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationReceiverEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public CommunicationReceiverEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* コミュニケーションID */
    protected String communicationId = null;
    /* 連番 */
    protected int seq = 0;
    /* 既読フラグ */
    protected boolean readFlg = false;
    /* PHR ID */
    protected String pHR_ID = null;
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 医療機関コード */
    protected String medicalOrganizationCd = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  CommunicationReceiverEntity
     */
    public static CommunicationReceiverEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  CommunicationReceiverEntity
     */
    public static CommunicationReceiverEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        CommunicationReceiverEntity entity = new CommunicationReceiverEntity();
        entity.setCommunicationId(getString(dataRow, "CommunicationId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setReadFlg(getBoolean(dataRow, "ReadFlg"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("コミュニケーションID：" + entity.getCommunicationId());
            logger.debug("連番                ：" + entity.getSeq());
            logger.debug("既読フラグ          ：" + entity.isReadFlg());
            logger.debug("PHR ID              ：" + entity.getPHR_ID());
            logger.debug("保険者番号          ：" + entity.getInsurerNo());
            logger.debug("医療機関コード      ：" + entity.getMedicalOrganizationCd());
            logger.debug("作成日時            ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時        ：" + entity.getUpdateDateTime());
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
     * 連番を取得する
     *
     * @return
     */
    public int getSeq() {
        return this.seq;
    }
    /**
     * 連番を設定する
     *
     * @param value
     */
    public void setSeq(int value) {
        this.seq = value;
    }

    /**
     * 既読フラグを取得する
     *
     * @return
     */
    public boolean isReadFlg() {
        return this.readFlg;
    }
    /**
     * 既読フラグを設定する
     *
     * @param value
     */
    public void setReadFlg(boolean value) {
        this.readFlg = value;
    }

    /**
     * PHR IDを取得する
     *
     * @return
     */
    public String getPHR_ID() {
        return this.pHR_ID;
    }
    /**
     * PHR IDを設定する
     *
     * @param value
     */
    public void setPHR_ID(String value) {
        this.pHR_ID = value;
    }

    /**
     * 保険者番号を取得する
     *
     * @return
     */
    public String getInsurerNo() {
        return this.insurerNo;
    }
    /**
     * 保険者番号を設定する
     *
     * @param value
     */
    public void setInsurerNo(String value) {
        this.insurerNo = value;
    }

    /**
     * 医療機関コードを取得する
     *
     * @return
     */
    public String getMedicalOrganizationCd() {
        return this.medicalOrganizationCd;
    }
    /**
     * 医療機関コードを設定する
     *
     * @param value
     */
    public void setMedicalOrganizationCd(String value) {
        this.medicalOrganizationCd = value;
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
                "ommunicationId=" + communicationId + 
                ", eq=" + seq + 
                ", eadFlg=" + readFlg + 
                ", HR_ID=" + pHR_ID + 
                ", nsurerNo=" + insurerNo + 
                ", edicalOrganizationCd=" + medicalOrganizationCd + 
                ", reateDateTime=" + createDateTime + 
                ", pdateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
