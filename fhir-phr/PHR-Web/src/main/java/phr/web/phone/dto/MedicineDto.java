
package phr.web.phone.dto;

import java.util.List;

/**
 *
 * @author iwaasa
 */
public class MedicineDto {
    //薬剤番号
    private int medicineSeq = 0;
    //薬剤名
    private String medicineName = null;
    //数量
    private String amount = null;
    //単位
    private String unitName = null;
    //服用開始日
//    private Date startDate = null;
    private String startDate = null;
    //服用終了日
//    private Date endDate = null;
    private String endDate = null;
    //補足
    private String additional = null;
    
    //薬剤補足
    private List<String> additionalList;
    //薬品服用注意
    private List<String> attention;

    /**
     * @return the medicineSeq
     */
    public int getMedicineSeq() {
        return medicineSeq;
    }

    /**
     * @param medicineSeq the medicineSeq to set
     */
    public void setMedicineSeq(int medicineSeq) {
        this.medicineSeq = medicineSeq;
    }

    /**
     * @return the medicineName
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * @param medicineName the medicineName to set
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the additional
     */
    public String getAdditional() {
        return additional;
    }

    /**
     * @param additional the additional to set
     */
    public void setAdditional(String additional) {
        this.additional = additional;
    }
    
    /**
     * @return the additionalList
     */
    public List<String> getAdditionalList() {
        return additionalList;
    }

    /**
     * @param additionalList the additionalList to set
     */
    public void setAdditionalList(List<String> additionalList) {
        this.additionalList = additionalList;
    }
    
    /**
     * @return the attention
     */
    public List<String> getAttention() {
        return attention;
    }

    /**
     * @param attention the attention to set
     */
    public void setAttention(List<String> attention) {
        this.attention = attention;
    }    
    
}
