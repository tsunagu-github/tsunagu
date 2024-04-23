/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import java.util.Map;
import phr.datadomain.entity.DosageEntity;

/**
 *
 * @author KISNOTE011
 */
public interface IExportMedicineListService {
    
    /**
     * 対象PHR-IDに結びつく調剤情報のエクスポート用テキストリストを出力する。
     * KeyはPHR-ID_DosageId_調剤日(yyyyMMdd)
     * @param phrId
     * @return
     * @throws Throwable 
     */
    Map<String, String> getExportMedicineList(String phrId) throws Throwable;
    //List<String> getExportMedicineList(String phrId) throws Throwable;
}
