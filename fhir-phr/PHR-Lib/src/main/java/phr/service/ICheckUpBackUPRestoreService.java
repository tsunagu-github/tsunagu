/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import kis.inc.ssmix2storagemaker.dto.CHECKUP.CheckUpXMLDto;


/**
 *
 * @author iwaasa
 */
public interface ICheckUpBackUPRestoreService {
    
    /**
     * 対象の健診データを拡張ストレージへコピーする
     * @param phrId
     * @return
     * @throws Throwable 
     */
    int getBackUPCheckUpXML(String phrId, String exPath) throws Throwable;
    
    /**
     * 対象の健診データXMLをDBへ登録する
     * @param phrId
     * @param model
     * @return
     * @throws Throwable 
     */
    int setRestoreCheckUpXML(String phrId,CheckUpXMLDto model)throws Throwable;
}
