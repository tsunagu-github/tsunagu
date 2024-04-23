package phr.web.phone.dto;


import java.util.List;

/**
 *
 * @author iwaasa
 */
public class MedicineListDto {
    //薬剤種別(市販薬：1 調剤薬：2)
    private String medicineType = null;
    //レコード作成者（医療関係者：1、患者等：2、その他：8、不明：9）
    private String recordCreatorType = null;
    //処方番号
    private int recipeNo = 0;
    //剤型コード
    private String dosageForm = null;
    //用法
    private String usageName = null;
    //調剤数量
    private Integer dosageQuantity = null;
    //調剤単位
    private String dosageUnit = null;
    //薬剤リスト
    private List<MedicineDto> medicines;
    
    //処方服用注意
    private List<String> recipeAttention;
    //用法補足
    private List<String> recipeAddition;

    /**
     * @return the medicineType
     */
    public String getMedicineType() {
        return medicineType;
    }

    /**
     * @param medicineType the medicineType to set
     */
    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    /**
     * @return the recordCreatorType
     */
    public String getRecordCreatorType() {
        return recordCreatorType;
    }

    /**
     * @param recordCreatorType the recordCreatorType to set
     */
    public void setRecordCreatorType(String recordCreatorType) {
        this.recordCreatorType = recordCreatorType;
    }

    /**
     * @return the recipeNo
     */
    public int getRecipeNo() {
        return recipeNo;
    }

    /**
     * @param recipeNo the recipeNo to set
     */
    public void setRecipeNo(int recipeNo) {
        this.recipeNo = recipeNo;
    }

    /**
     * @return the dosageForm
     */
    public String getDosageForm() {
        return dosageForm;
    }

    /**
     * @param dosageForm the dosageForm to set
     */
    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    /**
     * @return the usageName
     */
    public String getUsageName() {
        return usageName;
    }

    /**
     * @param usageName the usageName to set
     */
    public void setUsageName(String usageName) {
        this.usageName = usageName;
    }

    /**
     * @return the dosageQuantity
     */
    public Integer getDosageQuantity() {
        return dosageQuantity;
    }

    /**
     * @param dosageQuantity the dosageQuantity to set
     */
    public void setDosageQuantity(Integer dosageQuantity) {
        this.dosageQuantity = dosageQuantity;
    }

    /**
     * @return the dosageUnit
     */
    public String getDosageUnit() {
        return dosageUnit;
    }

    /**
     * @param dosageUnit the dosageUnit to set
     */
    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    /**
     * @return the medicines
     */
    public List<MedicineDto> getMedicines() {
        return medicines;
    }

    /**
     * @param medicines the medicines to set
     */
    public void setMedicines(List<MedicineDto> medicines) {
        this.medicines = medicines;
    }

    /**
     * @return the recipeAttention
     */
    public List<String> getRecipeAttention() {
        return recipeAttention;
    }

    /**
     * @param recipeAttention the recipeAttention to set
     */
    public void setRecipeAttention(List<String> recipeAttention) {
        this.recipeAttention = recipeAttention;
    }

    /**
     * @return the recipeAddition
     */
    public List<String> getRecipeAddition() {
        return recipeAddition;
    }

    /**
     * @param recipeAddition the recipeAddition to set
     */
    public void setRecipeAddition(List<String> recipeAddition) {
        this.recipeAddition = recipeAddition;
    }
    
}
