/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.ViewPasswordEntity;
import phr.service.impl.OnetimePasswordService.OneTimePasswordResult;

/**
 *
 * @author kis-note-025
 */
public interface IOnetimePasswordService {
    
    /**
     * ワンタイムパスワード発行
     * @param PHRID　患者PHRID
     * @return ワンタイムパスワード結果
     * @throws java.lang.Throwable
     */
    OneTimePasswordResult createOneTimePassword(String PHRID) throws Throwable;
    
    /**
     * 期限切れパスワードの削除
     * @throws Throwable 
     */
    void deleteExpirationPassword() throws Throwable;
    
    /**
     * ワンタイムパスワードの削除
     * @param Password　削除対象パスワード
     * @return　削除結果
     * @throws Throwable 
     */
    OneTimePasswordResult deletePassword(String Password) throws Throwable;
    
    /**
     * ワンタイムパスワード検索
     * @param Password　検索対象パスワード
     * @param insurerId 検索対象保険者番号（条件としない場合null）
     * @param MedicalOrgCd 検索対象医療機関CD（条件としない場合null）
     * @return 
     * @throws Throwable 
     */
    public OneTimePasswordResult searchPassword(String Password,String insurerId,String MedicalOrgCd) throws Throwable;
    
}
