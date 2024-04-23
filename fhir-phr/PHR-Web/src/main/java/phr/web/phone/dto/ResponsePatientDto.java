/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：利用者情報レスポンスDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

import phr.datadomain.entity.PatientEntity;
import phr.utility.TypeUtility;

/**
 *
 * @author daisuke
 */
public class ResponsePatientDto extends ResponseBaseDto {

    /* PHR ID */
    protected String phrId = null;
    /* 氏名_姓 */
    protected String familyName = null;
    /* 氏名_名 */
    protected String givenName = null;
    /* カナ氏名_姓 */
    protected String familyKana = null;
    /* カナ氏名_名 */
    protected String givenKana = null;
    /* 生年月日 */
    protected String birthDate = null;
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
    /* 保険者No */
    protected String insurerNo = null;
    /* 疾病管理有無 */
    protected boolean diseaseManagement = false;
    /* ダイナミックコンセント利用フラグ */
    protected boolean dynamicConsentFlg = false;
    /* Push用トークン登録有無 */
    protected boolean tokenRegistered = false;


    /**
     * デフォルトコンストラクタ
     */
    public ResponsePatientDto() {
    }

    /**
     * コンストラクタ
     *
     * @param entity
     */
    public ResponsePatientDto(PatientEntity entity) {
        phrId = entity.getPHR_ID();
        familyName = entity.getFamilyName();
        givenName = entity.getGivenName();
        familyKana = entity.getFamilyKana();
        givenKana = entity.getGivenKana();
        birthDate = TypeUtility.dateToString(entity.getBirthDate());
        sexCd = entity.getSexCd();
        zipCode = entity.getZipCode();
        prefectureCd = entity.getPrefectureCd();
        addressLine = entity.getAddressLine();
        buildingName = entity.getBuildingName();
        telNo = entity.getTelNo();
        otherContactNo = entity.getOtherContactNo();
        emailAddress = entity.getEmailAddress();
        insurerNo = entity.getInsurerNo();
        diseaseManagement = entity.isDiseaseManagement();
        dynamicConsentFlg = entity.isDynamicConsentFlg();
        tokenRegistered = entity.isTokenRegistered();
    }

    /**
     * DTOの内容より利用者Entityを作成する
     *
     * @return
     */
    public PatientEntity createPatientEntity() {
        PatientEntity entity = new PatientEntity();
        entity.setPHR_ID(phrId);
        entity.setFamilyName(familyName);
        entity.setGivenName(givenName);
        entity.setFamilyKana(familyKana);
        entity.setGivenKana(givenKana);
        if (!TypeUtility.isNullOrEmpty(birthDate)) {
            entity.setBirthDate(TypeUtility.stringToDate(birthDate));
        }
        entity.setSexCd(sexCd);
        entity.setZipCode(zipCode);
        entity.setPrefectureCd(prefectureCd);
        entity.setAddressLine(addressLine);
        entity.setBuildingName(buildingName);
        entity.setTelNo(telNo);
        entity.setOtherContactNo(otherContactNo);
        entity.setEmailAddress(emailAddress);
        entity.setInsurerNo(insurerNo);
        entity.setDiseaseManagement(diseaseManagement);
        entity.setDynamicConsentFlg(dynamicConsentFlg);
        entity.setTokenRegistered(tokenRegistered);

        return entity;
    }

    /**
     * DTOの内容より利用者Entityを設定する
     *
     * @param entity
     * @return
     */
    public void copyFromPatientEntity(PatientEntity entity) {
        entity.setFamilyName(familyName);
        entity.setGivenName(givenName);
        entity.setFamilyKana(familyKana);
        entity.setGivenKana(givenKana);
        if (!TypeUtility.isNullOrEmpty(birthDate)) {
            entity.setBirthDate(TypeUtility.stringToDate(birthDate));
        } else {
            entity.setBirthDate(null);
        }
        entity.setSexCd(sexCd);
        entity.setZipCode(zipCode);
        entity.setPrefectureCd(prefectureCd);
        entity.setAddressLine(addressLine);
        entity.setBuildingName(buildingName);
        entity.setTelNo(telNo);
        entity.setOtherContactNo(otherContactNo);
        entity.setEmailAddress(emailAddress);
        entity.setInsurerNo(insurerNo);
        entity.setDiseaseManagement(diseaseManagement);
        entity.setDynamicConsentFlg(dynamicConsentFlg);
        entity.setTokenRegistered(tokenRegistered);
    }

    /**
     * PHR IDを取得する
     *
     * @return
     */
    public String getPhrId() {
        return this.phrId;
    }

    /**
     * PHR IDを設定する
     *
     * @param value
     */
    public void setPhrId(String value) {
        this.phrId = value;
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
    public String getBirthDate() {
        return this.birthDate;
    }

    /**
     * 生年月日を設定する
     *
     * @param value
     */
    public void setBirthDate(String value) {
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
     * 保険者Noを取得する
     *
     * @return
     */
    public String getInsurerNo() {
        return this.insurerNo;
    }

    /**
     * 保険者Noを設定する
     *
     * @param value
     */
    public void setInsurerNo(String value) {
        this.insurerNo = value;
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
     * Push用トークン登録有無を取得する
     *
     * @return
     */
    public boolean isTokenRegistered() {
        return this.tokenRegistered;
    }

    /**
     * Push用トークン登録有無を設定する
     *
     * @param value
     */
    public void setTokenRegistered(boolean value) {
        this.tokenRegistered = value;
    }
}
