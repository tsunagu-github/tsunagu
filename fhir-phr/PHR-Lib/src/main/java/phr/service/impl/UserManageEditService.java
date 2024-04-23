/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.AccountAdapter;
import phr.datadomain.adapter.InsurerAdapter;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.service.IUserManageEditService;

/**
 *
 * @author kis.o-note-003
 */
public class UserManageEditService implements IUserManageEditService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalMessageReplayService.class);
    
    /**
     * 保険者情報を全取得する
     * @return
     * @throws Throwable 
     */
    @Override
    public List<InsurerEntity> getInsurerList() throws Throwable {
        List<InsurerEntity> entityList = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerAdapter adapter = new InsurerAdapter(dao.getConnection());
            entityList = adapter.getInsurerList();
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return entityList;
    }
    
    /**
     * ユーザ情報の登録を行う
     * @param entity
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean registUser(AccountEntity entity, Boolean editFlg) throws Throwable {
        logger.trace("start");
        
        DataAccessObject dao = new DataAccessObject();
        int result = 0;
        try{
            AccountAdapter adapter = new AccountAdapter(dao.getConnection());
            
            dao.beginTransaction();
            
            if (editFlg == true) {
                //修正
                result = adapter.update(entity);
            } else {
                //新規登録
                result = adapter.insert(entity);
            }
            dao.commitTransaction();
            
        }catch(Exception e){
            logger.error("", e);
            dao.rollbackTransaction();
            return false;
            
    	}finally{
            if (dao != null) {
                dao.close();
            }
    	}
        
        if(result == 0){
            logger.trace("登録処理に失敗しました");
            return false;
        }
        logger.trace("end");
        return true;
    }
    
    /**
     * ログインIDからユーザ情報を取得する
     * @param loginId
     * @return
     * @throws Throwable 
     */
    @Override
    public AccountEntity getUserInfo(String loginId) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            AccountAdapter adapter = new AccountAdapter(dao.getConnection());
            AccountEntity entity = adapter.findByLoginId(loginId);
            
            return entity;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            System.out.println(ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
        /**
     * 保険者情報を全取得する
     * @return
     * @throws Throwable 
     */
    @Override
    public String getInsurer(String insurerNo) throws Throwable {
        DataAccessObject dao = null;
        InsurerEntity entity = null;
        try {
            dao = new DataAccessObject();
            InsurerAdapter adapter = new InsurerAdapter(dao.getConnection());
            entity = adapter.getInsurer(insurerNo);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return entity.getInsurerName();
    }
}
