/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.service.impl.TakeOverService.TakeOverServiceResult;

/**
 *
 * @author chiba
 */
public interface ITakeOverService {

    /**
     * 引継ぎコードの取得
     * @param phrId
     * @param takeOverPassword
     * @return
     * @throws Throwable 
     */
    TakeOverServiceResult createTakeOverCode( String phrId, String takeOverPassword ) throws Throwable;

    TakeOverServiceResult checkTakeOverCode( String phrId, String takeOverPassword, String takeOverCode ) throws Throwable;

    
}
