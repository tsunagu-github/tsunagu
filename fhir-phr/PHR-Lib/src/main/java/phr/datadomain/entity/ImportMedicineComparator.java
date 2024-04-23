/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain.entity;

import java.util.Comparator;
import phr.dto.MedicineImportUnitDto;

/**
 *
 * @author kis-note-027_user
 */
public class ImportMedicineComparator implements Comparator<MedicineImportUnitDto>{

    @Override
    public int compare(MedicineImportUnitDto sep1, MedicineImportUnitDto sep2) {
            if(sep1.getShootOrder()>sep2.getShootOrder()){
                return 1;
            }else if(sep1.getShootOrder()==sep2.getShootOrder()){
                return 0;
            }else {
                return -1;
            }            
    }
    
}
