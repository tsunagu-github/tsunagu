/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.InsurerEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface IUserManageEditService {
    
    /**
     * 保険者情報を全取得する
     * @return
     * @throws Throwable 
     */
    public List<InsurerEntity> getInsurerList() throws Throwable;

    /**
     * ユーザ情報の登録を行う
     * @param entity
     * @return
     * @throws Throwable 
     */
    public boolean registUser(AccountEntity entity, Boolean editFlg) throws Throwable;
    
    /**
     * ログインIDからユーザ情報を取得する
     * @param loginId
     * @return
     * @throws Throwable 
     */
    public AccountEntity getUserInfo(String loginId) throws Throwable;

    /**
     * 保険者情報を全取得する
     * @return
     * @throws Throwable 
     */
    public String getInsurer(String insurerNo) throws Throwable;    
}
