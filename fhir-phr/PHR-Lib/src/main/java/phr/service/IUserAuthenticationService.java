/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.AppAccessLogEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author daisuke
 */
public interface IUserAuthenticationService {
    /**
     * 保険者Userの認証を行う
     * @param loingId
     * @param password
     * @return 
     * @throws java.lang.Throwable 
     */
    AccountEntity authenticationInsurerUser(String loingId, String password)  throws Throwable;

    /**
     * 入力パスワードがDBのパスワードと一致するか確認
     * @param entity
     * @param currentPassword
     * @return 
     * @throws java.lang.Throwable 
     */
    public boolean chkCurrentPass(AccountEntity entity,String currentPassword) throws Throwable;

    /**
     * 前回ログイン日時を更新
     * @param entity
     * @return 
     * @throws java.lang.Throwable 
     */
    public void updateLastloginDateTime(AccountEntity entity) throws Throwable;
    
        /**
     * 利用者の認証を行う
     *
     * @param phrId
     * @param timestamp
     * @param keyValue
     * @param tokenId
     * @return
     * @throws Throwable
     */
    public PatientEntity authenticationPatient(String phrId, String timestamp, String keyValue, String tokenId) throws Throwable;

    /**
     * 入力パスワードがDBのパスワードと一致するか確認（医療機関）
     * @param entity
     * @param currentPassword
     * @return 
     * @throws java.lang.Throwable 
     */
    public boolean chkCurrentPassMedical(MedicalOrganizationEntity entity,String currentPassword) throws Throwable;

    /**
     * アプリ起動時にサーバに存在確認のリクエストを投げる
     * @param phr_id
     * @return rowCount
     */
    public int insertRecord(String phr_id) throws Throwable;

    /**
     * Appアクセスログテーブルから該当利用者のアクセス日時が最新のレコードを取得
     * @param phr_id
     * @return entity
     */
    public AppAccessLogEntity findByPhrId(String phr_id) throws Throwable;
}
