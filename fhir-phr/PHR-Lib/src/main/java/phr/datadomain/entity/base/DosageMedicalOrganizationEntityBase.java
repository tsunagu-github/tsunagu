/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import phr.datadomain.entity.DosageMedicalOrganizationEntity;

/**
 * 医療機関情報のデータオブジェクトです。
 */
public abstract class DosageMedicalOrganizationEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicalOrganizationEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageMedicalOrganizationEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤ID */
    protected String dosageId = null;
    /* 調剤番号 */
    protected int seq = 0;
    /* 処方調剤 */
    protected String dosageTypeCd = null;
    /* 医療機関等点数表 */
    protected String organizationType = null;
    /* 医療機関等コード */
    protected String medicalOrganizationCd = null;
    /* 医療機関等名称 */
    protected String medicalOrganizationName = null;
    /* 都道府県コード */
    protected String prefectureCd = null;
    /* 郵便番号 */
    protected String zipCode = null;
    /* 住所 */
    protected String addressLine = null;
    /* 電話番号 */
    protected String telNo = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageMedicalOrganizationEntity
     */
    public static DosageMedicalOrganizationEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageMedicalOrganizationEntity
     */
    public static DosageMedicalOrganizationEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setDosageTypeCd(getString(dataRow, "DosageTypeCd"));
        entity.setOrganizationType(getString(dataRow, "OrganizationType"));
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setMedicalOrganizationName(getString(dataRow, "MedicalOrganizationName"));
        entity.setPrefectureCd(getString(dataRow, "PrefectureCd"));
        entity.setZipCode(getString(dataRow, "ZipCode"));
        entity.setAddressLine(getString(dataRow, "AddressLine"));
        entity.setTelNo(getString(dataRow, "TelNo"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID          ：" + entity.getDosageId());
            logger.debug("調剤番号        ：" + entity.getSeq());
            logger.debug("処方調剤        ：" + entity.getDosageTypeCd());
            logger.debug("医療機関等点数表：" + entity.getOrganizationType());
            logger.debug("医療機関等コード：" + entity.getMedicalOrganizationCd());
            logger.debug("医療機関等名称  ：" + entity.getMedicalOrganizationName());
            logger.debug("都道府県コード  ：" + entity.getPrefectureCd());
            logger.debug("郵便番号        ：" + entity.getZipCode());
            logger.debug("住所            ：" + entity.getAddressLine());
            logger.debug("電話番号        ：" + entity.getTelNo());
            logger.debug("レコード作成者  ：" + entity.getRecordCreatorType());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 調剤IDを取得する
     *
     * @return
     */
    public String getDosageId() {
        return this.dosageId;
    }
    /**
     * 調剤IDを設定する
     *
     * @param value
     */
    public void setDosageId(String value) {
        this.dosageId = value;
    }

    /**
     * 調剤番号を取得する
     *
     * @return
     */
    public int getSeq() {
        return this.seq;
    }
    /**
     * 調剤番号を設定する
     *
     * @param value
     */
    public void setSeq(int value) {
        this.seq = value;
    }

    /**
     * 処方調剤を取得する
     *
     * @return
     */
    public String getDosageTypeCd() {
        return this.dosageTypeCd;
    }
    /**
     * 処方調剤を設定する
     *
     * @param value
     */
    public void setDosageTypeCd(String value) {
        this.dosageTypeCd = value;
    }

    /**
     * 医療機関等点数表を取得する
     *
     * @return
     */
    public String getOrganizationType() {
        return this.organizationType;
    }
    /**
     * 医療機関等点数表を設定する
     *
     * @param value
     */
    public void setOrganizationType(String value) {
        this.organizationType = value;
    }

    /**
     * 医療機関等コードを取得する
     *
     * @return
     */
    public String getMedicalOrganizationCd() {
        return this.medicalOrganizationCd;
    }
    /**
     * 医療機関等コードを設定する
     *
     * @param value
     */
    public void setMedicalOrganizationCd(String value) {
        this.medicalOrganizationCd = value;
    }

    /**
     * 医療機関等名称を取得する
     *
     * @return
     */
    public String getMedicalOrganizationName() {
        return this.medicalOrganizationName;
    }
    /**
     * 医療機関等名称を設定する
     *
     * @param value
     */
    public void setMedicalOrganizationName(String value) {
        this.medicalOrganizationName = value;
    }

    /**
     * 都道府県コードを取得する
     *
     * @return
     */
    public String getPrefectureCd() {
        return this.prefectureCd;
    }
    /**
     * 都道府県コードを設定する
     *
     * @param value
     */
    public void setPrefectureCd(String value) {
        this.prefectureCd = value;
    }

    /**
     * 郵便番号を取得する
     *
     * @return
     */
    public String getZipCode() {
        return this.zipCode;
    }
    /**
     * 郵便番号を設定する
     *
     * @param value
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    /**
     * 住所を取得する
     *
     * @return
     */
    public String getAddressLine() {
        return this.addressLine;
    }
    /**
     * 住所を設定する
     *
     * @param value
     */
    public void setAddressLine(String value) {
        this.addressLine = value;
    }

    /**
     * 電話番号を取得する
     *
     * @return
     */
    public String getTelNo() {
        return this.telNo;
    }
    /**
     * 電話番号を設定する
     *
     * @param value
     */
    public void setTelNo(String value) {
        this.telNo = value;
    }

    /**
     * レコード作成者を取得する
     *
     * @return
     */
    public String getRecordCreatorType() {
        return this.recordCreatorType;
    }
    /**
     * レコード作成者を設定する
     *
     * @param value
     */
    public void setRecordCreatorType(String value) {
        this.recordCreatorType = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "osageId=" + dosageId + 
                ", eq=" + seq + 
                ", osageTypeCd=" + dosageTypeCd + 
                ", rganizationType=" + organizationType + 
                ", edicalOrganizationCd=" + medicalOrganizationCd + 
                ", edicalOrganizationName=" + medicalOrganizationName + 
                ", refectureCd=" + prefectureCd + 
                ", ipCode=" + zipCode + 
                ", ddressLine=" + addressLine + 
                ", elNo=" + telNo + 
                ", ecordCreatorType=" + recordCreatorType + 
                " }\r\n";
    }
}
