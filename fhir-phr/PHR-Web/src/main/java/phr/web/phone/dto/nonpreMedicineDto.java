
package phr.web.phone.dto;

/**
 *
 * @author kis-note-027_user
 */
public class nonpreMedicineDto {
    private String medicineName = null;
    private String startDate = null;
    private String endDate = null;
    private String isdelete = null;
    private String medicineSeq = null;

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
     * @return the isdelete
     */
    public String getIsdelete() {
        return isdelete;
    }

    /**
     * @param isdelete the isdelete to set
     */
    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * @return the medicineSeq
     */
    public String getMedicineSeq() {
        return medicineSeq;
    }

    /**
     * @param medicineSeq the medicineSeq to set
     */
    public void setMedicineSeq(String medicineSeq) {
        this.medicineSeq = medicineSeq;
    }
    
}
