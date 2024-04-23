/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：調剤ヘッダ情報のデータオブジェクト
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
import phr.datadomain.entity.DosageHeadEntity;

/**
 * 調剤ヘッダ情報のデータオブジェクトです。
 */
public abstract class DosageHeadEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageHeadEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageHeadEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤ID */
    protected String dosageId = null;
    /* バージョン情報 */
    protected String qRVersion = null;
    /* 患者氏名 */
    protected String patientName = null;
    /* 性別 */
    protected String sexCd = null;
    /* 生年月日 */
    protected Date birthDate = null;
    /* 郵便番号 */
    protected String zipCode = null;
    /* 住所 */
    protected String addressLine = null;
    /* 電話番号 */
    protected String telNo = null;
    /* 緊急連絡先 */
    protected String emergencyContact = null;
    /* 血液型 */
    protected String bloodType = null;
    /* 体重 */
    protected String weight = null;
    /* カナ氏名 */
    protected String nameInKana = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageHeadEntity
     */
    public static DosageHeadEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageHeadEntity
     */
    public static DosageHeadEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageHeadEntity entity = new DosageHeadEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setQRVersion(getString(dataRow, "QRVersion"));
        entity.setPatientName(getString(dataRow, "PatientName"));
        entity.setSexCd(getString(dataRow, "SexCd"));
        entity.setBirthDate(getDate(dataRow, "BirthDate"));
        entity.setZipCode(getString(dataRow, "ZipCode"));
        entity.setAddressLine(getString(dataRow, "AddressLine"));
        entity.setTelNo(getString(dataRow, "TelNo"));
        entity.setEmergencyContact(getString(dataRow, "EmergencyContact"));
        entity.setBloodType(getString(dataRow, "BloodType"));
        entity.setWeight(getString(dataRow, "Weight"));
        entity.setNameInKana(getString(dataRow, "NameInKana"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID        ：" + entity.getDosageId());
            logger.debug("バージョン情報：" + entity.getQRVersion());
            logger.debug("患者氏名      ：" + entity.getPatientName());
            logger.debug("性別          ：" + entity.getSexCd());
            logger.debug("生年月日      ：" + entity.getBirthDate());
            logger.debug("郵便番号      ：" + entity.getZipCode());
            logger.debug("住所          ：" + entity.getAddressLine());
            logger.debug("電話番号      ：" + entity.getTelNo());
            logger.debug("緊急連絡先    ：" + entity.getEmergencyContact());
            logger.debug("血液型        ：" + entity.getBloodType());
            logger.debug("体重          ：" + entity.getWeight());
            logger.debug("カナ氏名      ：" + entity.getNameInKana());
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
     * バージョン情報を取得する
     *
     * @return
     */
    public String getQRVersion() {
        return this.qRVersion;
    }
    /**
     * バージョン情報を設定する
     *
     * @param value
     */
    public void setQRVersion(String value) {
        this.qRVersion = value;
    }

    /**
     * 患者氏名を取得する
     *
     * @return
     */
    public String getPatientName() {
        return this.patientName;
    }
    /**
     * 患者氏名を設定する
     *
     * @param value
     */
    public void setPatientName(String value) {
        this.patientName = value;
    }

    /**
     * 性別を取得する
     *
     * @return
     */
    public String getSexCd() {
        return this.sexCd;
    }
    /**
     * 性別を設定する
     *
     * @param value
     */
    public void setSexCd(String value) {
        this.sexCd = value;
    }

    /**
     * 生年月日を取得する
     *
     * @return
     */
    public Date getBirthDate() {
        return this.birthDate;
    }
    /**
     * 生年月日を設定する
     *
     * @param value
     */
    public void setBirthDate(Date value) {
        this.birthDate = value;
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
     * 緊急連絡先を取得する
     *
     * @return
     */
    public String getEmergencyContact() {
        return this.emergencyContact;
    }
    /**
     * 緊急連絡先を設定する
     *
     * @param value
     */
    public void setEmergencyContact(String value) {
        this.emergencyContact = value;
    }

    /**
     * 血液型を取得する
     *
     * @return
     */
    public String getBloodType() {
        return this.bloodType;
    }
    /**
     * 血液型を設定する
     *
     * @param value
     */
    public void setBloodType(String value) {
        this.bloodType = value;
    }

    /**
     * 体重を取得する
     *
     * @return
     */
    public String getWeight() {
        return this.weight;
    }
    /**
     * 体重を設定する
     *
     * @param value
     */
    public void setWeight(String value) {
        this.weight = value;
    }

    /**
     * カナ氏名を取得する
     *
     * @return
     */
    public String getNameInKana() {
        return this.nameInKana;
    }
    /**
     * カナ氏名を設定する
     *
     * @param value
     */
    public void setNameInKana(String value) {
        this.nameInKana = value;
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
                ", RVersion=" + qRVersion + 
                ", atientName=" + patientName + 
                ", exCd=" + sexCd + 
                ", irthDate=" + birthDate + 
                ", ipCode=" + zipCode + 
                ", ddressLine=" + addressLine + 
                ", elNo=" + telNo + 
                ", mergencyContact=" + emergencyContact + 
                ", loodType=" + bloodType + 
                ", eight=" + weight + 
                ", ameInKana=" + nameInKana + 
                " }\r\n";
    }
}
