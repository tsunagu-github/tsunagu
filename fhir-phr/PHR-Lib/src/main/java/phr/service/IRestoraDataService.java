/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.io.File;


/**
 * リストア内部処理サービス
 * @author KISNOTE011
 */
public interface IRestoraDataService {

    /**
     * リストアデータ処理作成
     * @param phrid
     */
    boolean ececuteRestoraData(File zipFile  ,String old_phrid , String phrid) throws Throwable;
}
