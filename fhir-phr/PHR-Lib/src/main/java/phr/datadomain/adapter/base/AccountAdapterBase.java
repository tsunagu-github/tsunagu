/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アカウント情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter.base;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import java.sql.PreparedStatement;
import java.sql.Types;
import phr.datadomain.DataAccessObject;
import phr.utility.TypeUtility;

import phr.datadomain.entity.AccountEntity;

/**
 * アカウント情報のデータオブジェクトです。
 */
public abstract class AccountAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccountAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public AccountAdapterBase(Connection conn)
    {
        connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    Account.AccountId As AccountId  \r\n");
        sql.append("    , Account.LoginId As LoginId  \r\n");
        sql.append("    , Account.Password As Password  \r\n");
        sql.append("    , Account.Name As Name  \r\n");
        sql.append("    , Account.AccoutTypeCd As AccoutTypeCd  \r\n");
        sql.append("    , Account.InsurerNo As InsurerNo  \r\n");
        sql.append("    , Account.InitPassword As InitPassword  \r\n");
        sql.append("    , Account.Invalid As Invalid  \r\n");
        sql.append("    , Account.PasswordExpirationDate As PasswordExpirationDate  \r\n");
        sql.append("    , Account.LastLoginDateTime As LastLoginDateTime  \r\n");
        sql.append("    , Account.CreateAccoutId As CreateAccoutId  \r\n");
        sql.append("    , Account.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , Account.UpdateAccoutId As UpdateAccoutId  \r\n");
        sql.append("    , Account.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , Account.OneTimePassAuth As OneTimePassAuth \r\n");  
        sql.append("from Account \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Account \r\n");
//        sql.append("(AccountId, LoginId, Password, Name, AccoutTypeCd, InsurerNo, InitPassword, Invalid, PasswordExpirationDate, LastLoginDateTime, CreateAccoutId, CreateDateTime, UpdateAccoutId, UpdateDateTime) \r\n");
//        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP) \r\n");
        sql.append("(AccountId, LoginId, Password, Name, AccoutTypeCd, InsurerNo, InitPassword, Invalid, PasswordExpirationDate, LastLoginDateTime, CreateAccoutId, CreateDateTime, UpdateAccoutId, UpdateDateTime, OneTimePassAuth) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?) \r\n");        
        
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update Account set \r\n");
//        sql.append("LoginId = ?, Password = ?, Name = ?, AccoutTypeCd = ?, InsurerNo = ?, InitPassword = ?, Invalid = ?, PasswordExpirationDate = ?, LastLoginDateTime = ?, CreateAccoutId = ?, UpdateAccoutId = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("LoginId = ?, Password = ?, Name = ?, AccoutTypeCd = ?, InsurerNo = ?, InitPassword = ?, Invalid = ?, PasswordExpirationDate = ?, LastLoginDateTime = ?, CreateAccoutId = ?, UpdateAccoutId = ?, UpdateDateTime = CURRENT_TIMESTAMP, OneTimePassAuth = ? \r\n");
        sql.append("where AccountId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  Account \r\n");
        sql.append("where AccountId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(AccountEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getAccountId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getAccountId());
            }
            if (entity.getLoginId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getLoginId());
            }
            if (entity.getPassword() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getPassword());
            }
            if (entity.getName() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getName());
            }
            preparedStatement.setInt(5, entity.getAccoutTypeCd());
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getInsurerNo());
            }
            preparedStatement.setBoolean(7, entity.isInitPassword());
            preparedStatement.setBoolean(8, entity.isInvalid());
            if (entity.getPasswordExpirationDate() == null ) {
                preparedStatement.setNull(9, Types.DATE );
            } else {
                preparedStatement.setDate(9, TypeUtility.convertDate(entity.getPasswordExpirationDate()));
            }
            preparedStatement.setTimestamp(10, entity.getLastLoginDateTime());
            if (entity.getCreateAccoutId() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getCreateAccoutId());
            }
            if (entity.getUpdateAccoutId() == null ) {
                preparedStatement.setNull(12, Types.VARCHAR );
            } else {
                preparedStatement.setString(12, entity.getUpdateAccoutId());
            }
            preparedStatement.setBoolean(13, entity.getOneTimePassAuth());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();

        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(AccountEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getLoginId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getLoginId());
            }
            if (entity.getPassword() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPassword());
            }
            if (entity.getName() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getName());
            }
            preparedStatement.setInt(4, entity.getAccoutTypeCd());
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getInsurerNo());
            }
            preparedStatement.setBoolean(6, entity.isInitPassword());
            preparedStatement.setBoolean(7, entity.isInvalid());
            if (entity.getPasswordExpirationDate() == null ) {
                preparedStatement.setNull(8, Types.DATE );
            } else {
                preparedStatement.setDate(8, TypeUtility.convertDate(entity.getPasswordExpirationDate()));
            }
            preparedStatement.setTimestamp(9, entity.getLastLoginDateTime());
            if (entity.getCreateAccoutId() == null ) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setString(10, entity.getCreateAccoutId());
            }
            if (entity.getUpdateAccoutId() == null ) {
                preparedStatement.setNull(11, Types.VARCHAR );
            } else {
                preparedStatement.setString(11, entity.getUpdateAccoutId());
            }
            preparedStatement.setBoolean(12, entity.getOneTimePassAuth());
            if (entity.getAccountId() == null ) {
                preparedStatement.setNull(13, Types.VARCHAR );
            } else {
                preparedStatement.setString(13, entity.getAccountId());
            }
            preparedStatement.setTimestamp(14, entity.getUpdateDateTime());
            
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてアカウント情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(AccountEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getAccountId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getAccountId());
            }
            preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてアカウント情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public AccountEntity findByPrimaryKey(String key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where Account.AccountId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

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
}
