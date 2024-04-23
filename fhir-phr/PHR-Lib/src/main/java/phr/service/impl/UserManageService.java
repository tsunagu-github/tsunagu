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
import phr.datadomain.entity.AccountEntity;
import phr.service.IUserManageService;

/**
 *
 * @author kis.o-note-003
 */
public class UserManageService implements IUserManageService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UserAuthenticationService.class);
    
    /**
     * 条件に合ったユーザ一覧の取得を行う
     * @param loginId
     * @param userName
     * @param insurerNo
     * @param validFlg
     * @return
     * @throws Throwable 
     */
    @Override
    public List<AccountEntity> userSearch(String loginId, String userName, String insurerNo, Boolean validFlg) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            AccountAdapter adapter = new AccountAdapter(dao.getConnection());
            List<AccountEntity> entity = adapter.findByInsurerUsers_UserManage(loginId, userName, insurerNo, validFlg);
            
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
     * 選択行のユーザ情報を取得する
     * @param loginId
     * @return
     * @throws Throwable 
     */
    @Override
    public AccountEntity userInfo(String loginId) throws Throwable {
        
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
     * 選択行のユーザ情報を削除する
     * @param accountId
     * @throws Throwable 
     */
    @Override
    public void userDelete(String accountId) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            AccountAdapter adapter = new AccountAdapter(dao.getConnection());
            AccountEntity entity = new AccountEntity();
            entity.setAccountId(accountId);
            dao.beginTransaction();
            int delCnt = adapter.deletea(entity);
            dao.commitTransaction();
            
        } catch (Throwable ex) {
            dao.rollbackTransaction();
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
}
