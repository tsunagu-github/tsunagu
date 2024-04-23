/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

/**
 *
 * @author kis-note-027_user
 */
public class MedicineImportDto {
    private String importText=null;
    private int shootingOrder =0;

    /**
     * @return the importText
     */
    public String getImportText() {
        return importText;
    }

    /**
     * @param importText the importText to set
     */
    public void setImportText(String importText) {
        this.importText = importText;
    }

    /**
     * @return the shootingOrder
     */
    public int getShootingOrder() {
        return shootingOrder;
    }

    /**
     * @param shootingOrder the shootingOrder to set
     */
    public void setShootingOrder(int shootingOrder) {
        this.shootingOrder = shootingOrder;
    }
    
    
}
