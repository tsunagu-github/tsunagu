/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.dto.DosageEntitySetDto;

/**
 *
 * @author KISNOTE011
 */
public interface IImportMedicineSetService {
    
    DosageEntitySetDto getImportMedicineSet(String[] recordString,String phrId,String dosageId) throws Throwable;
}
