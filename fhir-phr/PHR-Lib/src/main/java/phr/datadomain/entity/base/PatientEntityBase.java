/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/19
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
import phr.datadomain.entity.PatientEntity;

/**
 * 患者情報のデータオブジェクトです。
 */
public abstract class PatientEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public PatientEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* PHR ID */
    protected String pHR_ID = null;
    /* 氏名_姓 */
    protected String familyName = null;
    /* 氏名_名 */
    protected String givenName = null;
    /* カナ氏名_姓 */
    protected String familyKana = null;
    /* カナ氏名_名 */
    protected String givenKana = null;
    /* 生年月日 */
    protected Date birthDate = null;
    /* 性別CD */
    protected String sexCd = null;
    /* 郵便番号 */
    protected String zipCode = null;
    /* 都道府県CD */
    protected String prefectureCd = null;
    /* 住所 */
    protected String addressLine = null;
    /* ビル名等 */
    protected String buildingName = null;
    /* 電話番号 */
    protected String telNo = null;
    /* その他連絡先 */
    protected String otherContactNo = null;
    /* メールアドレス */
    protected String emailAddress = null;
    /* KeyID */
    protected String kyeId = null;
    /* トークンID */
    protected String tokenId = null;
    /* 疾病管理有無 */
    protected boolean diseaseManagement = false;
    /* ダイナミックコンセント利用フラグ */
    protected boolean dynamicConsentFlg = true;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  PatientEntity
     */
    public static PatientEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  PatientEntity
     */
    public static PatientEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        PatientEntity entity = new PatientEntity();
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setFamilyName(getString(dataRow, "FamilyName"));
        entity.setGivenName(getString(dataRow, "GivenName"));
        entity.setFamilyKana(getString(dataRow, "FamilyKana"));
        entity.setGivenKana(getString(dataRow, "GivenKana"));
        entity.setBirthDate(getDate(dataRow, "BirthDate"));
        entity.setSexCd(getString(dataRow, "SexCd"));
        entity.setZipCode(getString(dataRow, "ZipCode"));
        entity.setPrefectureCd(getString(dataRow, "PrefectureCd"));
        entity.setAddressLine(getString(dataRow, "AddressLine"));
        entity.setBuildingName(getString(dataRow, "BuildingName"));
        entity.setTelNo(getString(dataRow, "TelNo"));
        entity.setOtherContactNo(getString(dataRow, "OtherContactNo"));
        entity.setEmailAddress(getString(dataRow, "EmailAddress"));
        entity.setKyeId(getString(dataRow, "KyeId"));
        entity.setTokenId(getString(dataRow, "tokenId"));
        entity.setDiseaseManagement(getBoolean(dataRow, "DiseaseManagement"));
        entity.setDynamicConsentFlg(getBoolean(dataRow, "DynamicConsentFlg"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("PHR ID        ：" + entity.getPHR_ID());
            logger.debug("氏名_姓       ：" + entity.getFamilyName());
            logger.debug("氏名_名       ：" + entity.getGivenName());
            logger.debug("カナ氏名_姓   ：" + entity.getFamilyKana());
            logger.debug("カナ氏名_名   ：" + entity.getGivenKana());
            logger.debug("生年月日      ：" + entity.getBirthDate());
            logger.debug("性別CD        ：" + entity.getSexCd());
            logger.debug("郵便番号      ：" + entity.getZipCode());
            logger.debug("都道府県CD    ：" + entity.getPrefectureCd());
            logger.debug("住所          ：" + entity.getAddressLine());
            logger.debug("ビル名等      ：" + entity.getBuildingName());
            logger.debug("電話番号      ：" + entity.getTelNo());
            logger.debug("その他連絡先  ：" + entity.getOtherContactNo());
            logger.debug("メールアドレス：" + entity.getEmailAddress());
            logger.debug("KeyID         ：" + entity.getKyeId());
            logger.debug("トークンID    ：" + entity.getTokenId());
            logger.debug("疾病管理有無  ：" + entity.isDiseaseManagement());
            logger.debug("ダイナミックコンセント利用フラグ  ：" + entity.isDynamicConsentFlg());
            logger.debug("作成日時      ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時  ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * 氏名_姓を取得する
     *
     * @return
     */
    public String getFamilyName() {
        return this.familyName;
    }
    /**
     * 氏名_姓を設定する
     *
     * @param value
     */
    public void setFamilyName(String value) {
        this.familyName = value;
    }

    /**
     * 氏名_名を取得する
     *
     * @return
     */
    public String getGivenName() {
        return this.givenName;
    }
    /**
     * 氏名_名を設定する
     *
     * @param value
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * カナ氏名_姓を取得する
     *
     * @return
     */
    public String getFamilyKana() {
        return this.familyKana;
    }
    /**
     * カナ氏名_姓を設定する
     *
     * @param value
     */
    public void setFamilyKana(String value) {
        this.familyKana = value;
    }

    /**
     * カナ氏名_名を取得する
     *
     * @return
     */
    public String getGivenKana() {
        return this.givenKana;
    }
    /**
     * カナ氏名_名を設定する
     *
     * @param value
     */
    public void setGivenKana(String value) {
        this.givenKana = value;
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
     * 性別CDを取得する
     *
     * @return
     */
    public String getSexCd() {
        return this.sexCd;
    }
    /**
     * 性別CDを設定する
     *
     * @param value
     */
    public void setSexCd(String value) {
        this.sexCd = value;
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
     * 都道府県CDを取得する
     *
     * @return
     */
    public String getPrefectureCd() {
        return this.prefectureCd;
    }
    /**
     * 都道府県CDを設定する
     *
     * @param value
     */
    public void setPrefectureCd(String value) {
        this.prefectureCd = value;
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
     * ビル名等を取得する
     *
     * @return
     */
    public String getBuildingName() {
        return this.buildingName;
    }
    /**
     * ビル名等を設定する
     *
     * @param value
     */
    public void setBuildingName(String value) {
        this.buildingName = value;
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
     * その他連絡先を取得する
     *
     * @return
     */
    public String getOtherContactNo() {
        return this.otherContactNo;
    }
    /**
     * その他連絡先を設定する
     *
     * @param value
     */
    public void setOtherContactNo(String value) {
        this.otherContactNo = value;
    }

    /**
     * メールアドレスを取得する
     *
     * @return
     */
    public String getEmailAddress() {
        return this.emailAddress;
    }
    /**
     * メールアドレスを設定する
     *
     * @param value
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    /**
     * KeyIDを取得する
     *
     * @return
     */
    public String getKyeId() {
        return this.kyeId;
    }
    /**
     * KeyIDを設定する
     *
     * @param value
     */
    public void setKyeId(String value) {
        this.kyeId = value;
    }

    /**
     * トークンIDを取得する
     *
     * @return
     */
    public String getTokenId() {
        return this.tokenId;
    }
    /**
     * トークンIDを設定する
     *
     * @param value
     */
    public void setTokenId(String value) {
        this.tokenId = value;
    }

    /**
     * 疾病管理有無を取得する
     *
     * @return
     */
    public boolean isDiseaseManagement() {
        return this.diseaseManagement;
    }
    /**
     * 疾病管理有無を設定する
     *
     * @param value
     */
    public void setDiseaseManagement(boolean value) {
        this.diseaseManagement = value;
    }

    /**
     * ダイナミックコンセント利用フラグを取得する
     *
     * @return
     */
    public boolean isDynamicConsentFlg() {
        return this.dynamicConsentFlg;
    }
    /**
     * ダイナミックコンセント利用フラグを設定する
     *
     * @param value
     */
    public void setDynamicConsentFlg(boolean value) {
        this.dynamicConsentFlg = value;
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
                "HR_ID=" + pHR_ID + 
                ", amilyName=" + familyName + 
                ", ivenName=" + givenName + 
                ", amilyKana=" + familyKana + 
                ", ivenKana=" + givenKana + 
                ", irthDate=" + birthDate + 
                ", exCd=" + sexCd + 
                ", ipCode=" + zipCode + 
                ", refectureCd=" + prefectureCd + 
                ", ddressLine=" + addressLine + 
                ", uildingName=" + buildingName + 
                ", elNo=" + telNo + 
                ", therContactNo=" + otherContactNo + 
                ", mailAddress=" + emailAddress + 
                ", yeId=" + kyeId + 
                ", okenId=" + tokenId + 
                ", iseaseManagement=" + diseaseManagement + 
                ", ynamicConsentFlg=" + dynamicConsentFlg + 
                ", reateDateTime=" + createDateTime + 
                ", pdateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
