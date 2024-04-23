/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.DosageEntity;

/**
 *
 * @author KISNOTE011
 */
public interface IDosageService {
    /**
     * 対象年度の検査結果から基準日の直近の値を検索する
     * @param phrid 対象のPHRID
     * @param basedate 対象の基準日
     * @return
     * @throws Throwable 
     */
    List<DosageEntity> searchDosageByPhrid(String phrid,java.sql.Date basedate) throws Throwable;
    
    /**
     * DosageId,Seq,PHRIDからおくすりの詳細リストを検索する
     * @param dosageId 対象の調剤ID
     * @param seq 対象の調剤番号
     * @param phrid 対象のPHRID
     * @return
     * @throws Throwable 
     */
    List<DosageEntity> searchDosageByDosageIdSeqPhrid(String dosageId,String seq,String phrid) throws Throwable;
    
}
