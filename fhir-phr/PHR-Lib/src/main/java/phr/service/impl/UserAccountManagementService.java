/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.AccountAdapter;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.service.IUserAccountManagementService;

/**
 *
 * @author KISNOTE011
 */
public class UserAccountManagementService implements IUserAccountManagementService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UserAuthenticationService.class);

    /*** <pre>パスワードを変更します。</pre>
     * @param entity        更新対象Entity
     * @param newPassword   更新するパスワード文字列
     * @return true パスワード変更成功,false パスワード変更失敗
     * @throws Throwable
     */
    @Override
    public AccountEntity passwordChange(AccountEntity entity,String newPassword) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject();
        AccountEntity rtnAccountEntity = null;
    	try{
            AccountAdapter adapter = new AccountAdapter(dao.getConnection());
            dao.beginTransaction();
            AccountEntity account = adapter.findByLoginId(entity.getLoginId());

            account.setDecriptPassword(newPassword);
            account.setInitPassword(false);
            //パスワード有効期間更新(日付にはパスワード変更日（今日）をそのまま設定)
            account.setPasswordExpirationDate(new Date());

            adapter.update(account);
            dao.commitTransaction();
            rtnAccountEntity = account;
    	}catch(Exception e){
            logger.error(e);
            dao.rollbackTransaction();
            throw e;
    	}finally{
            try{
                dao.close();
            }catch(Exception e){
                logger.debug(e);
            }
    	}

        logger.trace("end");
        return rtnAccountEntity;
    }

    /**
     * 検索条件から保険者ユーザを検索する
     * @param loginIdStr　検索対象ログインID文字列
     * @param nameStr　　検索対象氏名文字列
     * @param invalidUser　true:無効ユーザを含める　false:含めない
     * @return 症例入力画面一覧表示用のリスト
     * @throws Throwable
     */
    @Override
    public List<AccountEntity> searchInsurerAccount(String loginIdStr, String nameStr, boolean invalidUser) throws Throwable {
    	logger.debug("Start");

    	String loginName = loginIdStr;
    	String name = nameStr;
    	boolean enabled = invalidUser;
//    	int offset = OFFSET;
//    	int limit = SEARCH_LIST_MAX_NUMBER;

        List<AccountEntity> list = null;
        DataAccessObject dao = new DataAccessObject();
        try{
            AccountAdapter adapter = new AccountAdapter(dao.getConnection());

            //検索
            list = adapter.findByInsurerUsers(loginName,name,enabled);
    	}catch(Exception e){
            logger.error(e);
            throw e;
    	}finally{
            try{
                dao.close();
            }catch(Exception e){
                logger.debug(e);
            }
        }

    	logger.debug("End");
    	return list;
    }
    
    /*** <pre>医療機関のパスワードを変更します。</pre>
     * @param entity        更新対象Entity
     * @param newPassword   更新するパスワード文字列
     * @return true パスワード変更成功,false パスワード変更失敗
     * @throws Throwable
     */
    @Override
    public MedicalOrganizationEntity passwordChangeMedical(MedicalOrganizationEntity entity,String newPassword) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject();
        MedicalOrganizationEntity rtnAccountEntity = null;
    	try{
            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            dao.beginTransaction();
            MedicalOrganizationEntity account = adapter.findByPrimaryKey(entity.getMedicalOrganizationCd());

            account.setDecriptPassword(newPassword);

            adapter.update(account);
            dao.commitTransaction();
            rtnAccountEntity = account;
    	}catch(Exception e){
            logger.error(e);
            dao.rollbackTransaction();
            throw e;
    	}finally{
            try{
                dao.close();
            }catch(Exception e){
                logger.debug(e);
            }
    	}

        logger.trace("end");
        return rtnAccountEntity;
    }
}
