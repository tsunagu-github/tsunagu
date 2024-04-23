/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：健診結果を格納するクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author daisuke
 */
public class ObservationResultDto {
    /**
     * 項目ID
     */
    private String id;
    /**
     * 表示名
     */
    private String displayName;
    /**
     * 検査結果値
     */
    private String value;
    /**
     * 単位
     */
    private String unitValue;
    /**
     * 検査日
     */
    private String examinationDate;
    /**
     * 結果アラートレベル
     */
    private String valueAlert;
    /**
     * 日付アラート
     */
    private String dateAlert;

    /**
     * 項目ID
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 項目ID
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 表示名
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 表示名
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 検査結果値
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * 検査結果値
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 単位
     * @return the unitValue
     */
    public String getUnitValue() {
        return unitValue;
    }

    /**
     * 単位
     * @param unitValue the unitValue to set
     */
    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    /**
     * 検査日
     * @return the examinationDate
     */
    public String getExaminationDate() {
        return examinationDate;
    }

    /**
     * 検査日
     * @param examinationDate the examinationDate to set
     */
    public void setExaminationDate(String examinationDate) {
        this.examinationDate = examinationDate;
    }

    /**
     * 結果アラートレベル
     * @return the valueAlert
     */
    public String getValueAlert() {
        return valueAlert;
    }

    /**
     * 結果アラートレベル
     * @param valueAlert the valueAlert to set
     */
    public void setValueAlert(String valueAlert) {
        this.valueAlert = valueAlert;
    }

    /**
     * 日付アラート
     * @return the dateAlert
     */
    public String getDateAlert() {
        return dateAlert;
    }

    /**
     * 日付アラート
     * @param dateAlert the dateAlert to set
     */
    public void setDateAlert(String dateAlert) {
        this.dateAlert = dateAlert;
    }
    
}
