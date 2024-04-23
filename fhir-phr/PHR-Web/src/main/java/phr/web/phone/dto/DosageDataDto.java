package phr.web.phone.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author iwaasa
 */
public class DosageDataDto {
    //薬剤種別(市販薬：1 調剤薬：2)
    private String medicineType = null;
    //調剤番号
    private int seq = 0;
    //医療機関名
    private String medicalOrganization = null;
    //診療科名
    private String departmentName = null;
    //医師名
    private String doctorName = null;
    //薬品リスト
    private List<MedicineListDto> recipeList;    


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
     * @return the seq
     */
    public int getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * @return the medicalOrganization
     */
    public String getMedicalOrganization() {
        return medicalOrganization;
    }

    /**
     * @param medicalOrganization the medicalOrganization to set
     */
    public void setMedicalOrganization(String medicalOrganization) {
        this.medicalOrganization = medicalOrganization;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the doctorName
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * @param doctorName the doctorName to set
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * @return the recipeList
     */
    public List<MedicineListDto> getRecipeList() {
        return recipeList;
    }

    /**
     * @param recipeList the recipeList to set
     */
    public void setRecipeList(List<MedicineListDto> recipeList) {
        this.recipeList = recipeList;
    }
}



