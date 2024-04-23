/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アカウント情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.AccountEntity;
import phr.utility.TypeUtility;

/**
 * アカウント情報のデータオブジェクトです。
 */
public class AccountAdapter extends AccountAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccountAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public AccountAdapter(Connection conn)
    {
        super(conn);
    }

    /**
     * ログインIDにてアカウント情報を検索します。
     * @param loginId
     * @return
     * @throws Throwable
     */
    public AccountEntity findByLoginId(String loginId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where Account.LoginId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, loginId);

        AccountEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = AccountEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }

    /**
     * 条件に一致するユーザ情報を検索する
     * @param loginId
     * @param nameStr
     * @param invalidUser
     * @return
     * @throws Throwable
     */
    public List<AccountEntity> findByInsurerUsers(String loginId, String nameStr, boolean invalidUser) throws Throwable{
        logger.trace("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        String sqlReq="";
        if(!loginId.isEmpty()){
            if(sqlReq.isEmpty()){
                sqlReq += " where";
            }else{
                sqlReq += " and";
            }
            sqlReq += " Account.LoginId = ?";
        }
        if(!nameStr.isEmpty()){
            if(sqlReq.isEmpty()){
                sqlReq += " where";
            }else{
                sqlReq += " and";
            }
            sqlReq += " Account.Name = ?";
        }
        if(!invalidUser){
            if(sqlReq.isEmpty()){
                sqlReq += " where";
            }else{
                sqlReq += " and";
            }
            sqlReq += " Account.Invalid = ?";
        }
        dao.setSql(sql+sqlReq);

        dao.clearBindParam();
        List<AccountEntity> entityList=new ArrayList<>();
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            int i=1;
            if(!loginId.isEmpty()){
                preparedStatement.setString(i, loginId);
                i+=1;
            }
            if(!nameStr.isEmpty()){
                preparedStatement.setString(i, nameStr);
                i+=1;
            }
            if(!invalidUser){
                preparedStatement.setBoolean(i, false);
            }
//            entityList = null;
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null){
                    return null;
                }
                while( dataTable.next() ) {
                    entityList.add(AccountEntity.setData(dataTable));
                }
                dao.clearBindParam();
            }
        }
        logger.trace("End");
        return entityList;
    }
    
    /**
     * 条件に一致するユーザ情報を検索する(userManage)
     * @param loginId
     * @param nameStr
     * @param insurerNo
     * @param invalidUser
     * @param passDate
     * @return
     * @throws Throwable
     */
    public List<AccountEntity> findByInsurerUsers_UserManage(String loginId, String nameStr, String insurerNo, boolean invalidUser) throws Throwable{
        logger.trace("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        // 部分一致検索対応
        String whereLoginIdStr = this.makeRequirementSentence("LoginId",loginId);
        String whereNameStr = this.makeRequirementSentence("Name",nameStr);
        
        String sql = getSelectedSql();
        String sqlReq="";
        if(!loginId.isEmpty()){
            if(sqlReq.isEmpty()){
                sqlReq += " where";
            }else{
                sqlReq += " and";
            }
            sqlReq += whereLoginIdStr;
        }
        if(!nameStr.isEmpty()){
            if(sqlReq.isEmpty()){
                sqlReq += " where";
            }else{
                sqlReq += " and";
            }
            sqlReq += whereNameStr;
        }
        if(!insurerNo.isEmpty()){
            if(sqlReq.isEmpty()){
                sqlReq += " where";
            }else{
                sqlReq += " and";
            }
            sqlReq += " Account.InsurerNo = " + insurerNo;
        }
        if(!invalidUser){
            if(sqlReq.isEmpty()){
                sqlReq += " where";
            }else{
                sqlReq += " and";
            }
            sqlReq += " Account.Invalid = ?";
        }
        
        dao.setSql(sql+sqlReq);
        
        dao.clearBindParam();
        List<AccountEntity> entityList = new ArrayList<>();
        try (PreparedStatement preparedStatement = dao.getPreparedStatement()) {
            int i=1;
            if(!loginId.isEmpty()){
                preparedStatement.setString(i, loginId);
                i+=1;
            }
            if(!nameStr.isEmpty()){
                preparedStatement.setString(i, nameStr);
                i+=1;
            }
            if(!invalidUser){
                preparedStatement.setBoolean(i, false);
            }
            
            try (ResultSet dataTable = preparedStatement.executeQuery()) {
                if (dataTable == null) {
                    return null;
                }
                while(dataTable.next()) {
                    entityList.add(AccountEntity.setData(dataTable));
                }
                dao.clearBindParam();
            }
        }
        logger.trace("End");
        return entityList;
    }
    /* -------------------------------------------------------------------------------------- */
  
    /**
     * オブジェクトの値にてアカウント情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deletea(AccountEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  Account \r\n");
        sb.append("where AccountId = ? \r\n");
//        sql.append("and UpdateDateTime = ? \r\n");
        String sql = sb.toString();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getAccountId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getAccountId());
            }
//            preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * アカウントIDを採番する
     *
     * @return
     * @throws Throwable
     */
    public static String numberingAccountId() throws Throwable {
        String accountId = "0000000000";
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            String sql = "update Seq_AccountId set AccountId=LAST_INSERT_ID(AccountId+1)";
            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            int rowCount = preparedStatement.executeUpdate();
            String sql2 = "SELECT LAST_INSERT_ID() as SeqId";
            dao.setSql(sql2);
            dao.clearBindParam();
            preparedStatement.close();
            
            PreparedStatement preparedStatement2 = dao.getPreparedStatement();
            ResultSet dataTable = preparedStatement2.executeQuery();
            if (dataTable == null) {
                return null;
            }

            while (dataTable.next()) {
                long id = AbstractEntity.getLong(dataTable, "SeqId");
                accountId = String.format("%032d", id);
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement2.close();
            dao.commitTransaction();
            return accountId;
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }
    
    /**
     * <pre>where句を作成する。
     * @param idStr
     * @param sentence
     * @return 
     */
    private String makeRequirementSentence(String idStr,String sentence){
        if(sentence==null || sentence.length()==0){
            return  null;
        }
        if(sentence.indexOf("%")==-1){
            return "  Account." + idStr + " =  ?";
        } else if(sentence.indexOf("%")>-1){
            return "  Account." + idStr + " Like  ?";
        } else{
            return null;
        }
    }
}
