/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;


/**
 *
 * @author KISNOTE011
 */
public interface IMakeStorageService {
    /**
     * DBから対象の患者のSS-MIX2形式を作成する
     * このメソッドでは標準ストレージを作成する。
     * @param phrid
     * @param rootPath
     * @return 
     * @throws Throwable 
     */
    boolean makeSTDStorage(String phrid , String rootPath) throws Throwable;
    
        /**
     * DBから対象の患者のSS-MIX2形式を作成する
     * このメソッドでは拡張ストレージを作成する。
     * @param phrid
     * @param rootPath
     * @return 
     * @throws Throwable 
     */
    boolean makeEXTStorage(String phrid , String rootPath) throws Throwable;

}
