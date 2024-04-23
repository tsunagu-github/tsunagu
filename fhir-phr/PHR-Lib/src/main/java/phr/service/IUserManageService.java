/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.AccountEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface IUserManageService {
    
    /**
     * ユーザ一覧の取得を行う
     * @param loginId
     * @param userName
     * @param insurerNo
     * @param validFlg
     * @return
     * @throws Throwable 
     */
    public List<AccountEntity> userSearch(String loginId, String userName, String insurerNo, Boolean validFlg) throws Throwable;
    
    /**
     * 選択行のユーザ情報を取得する
     * @param loginId
     * @return
     * @throws Throwable 
     */
    public AccountEntity userInfo(String loginId) throws Throwable;
    
    /**
     * 選択行のユーザ情報を削除する
     * @param accountId
     * @throws Throwable 
     */
    public void userDelete(String accountId) throws Throwable;
    
}
