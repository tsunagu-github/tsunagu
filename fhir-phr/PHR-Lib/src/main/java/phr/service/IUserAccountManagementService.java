/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;

/**
 *
 * @author KISNOTE011
 */
public interface IUserAccountManagementService {
    /**
     * パスワード変更を行う
     * @param entity
     * @param newPassword
     * @return
     * @throws Throwable 
     */
    public AccountEntity passwordChange(AccountEntity entity, String newPassword)throws Throwable;

    /**
     * 保険者ユーザの検索を行う
     * @param loginIdStr ログインID
     * @param nameStr 氏名
     * @param invalidUser 有無効
     * @return
     * @throws Throwable 
     */
    public List<AccountEntity> searchInsurerAccount(String loginIdStr, String nameStr, boolean invalidUser) throws Throwable;
    
     /**
     * 医療機関のパスワード変更を行う
     * @param entity
     * @param newPassword
     * @return
     * @throws Throwable 
     */
    public MedicalOrganizationEntity passwordChangeMedical(MedicalOrganizationEntity entity, String newPassword)throws Throwable;

}
