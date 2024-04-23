/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.io.File;


/**
 * バックアップ及びリストア内部処理サービス
 * @author KISNOTE011
 */
public interface IBackupDataService {

    /**
     * バックアップデータ作成
     * @param phrid
     */
    File makeBackupData(String phrid ) throws Throwable;
}
