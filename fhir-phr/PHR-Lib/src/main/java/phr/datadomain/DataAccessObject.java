/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データベースへのアクセスを行うクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.datadomain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * データベースアクセスを制御するオブジェクト
 *
 * @author daisuke
 *
 */
public class DataAccessObject {

    /**
     * JNDiからの取得有無
     */
    private static boolean jndi = false;

    /**
     * JNDiからの取得有無を設定する
     *
     * @param isJndi
     */
    public static void setJndi(boolean isJndi) {
        jndi = isJndi;
    }

    /**
     * JDBCドライバーを使ってDB接続を取得する
     *
     * @return
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        
        if (jndi) {
            return getConnectionFromJndi();
        }
        return getConnectionFromDriverClass();
    }

    /**
     * JndiからDB接続を取得する
     *
     * @return
     */
    public static Connection getConnectionFromJndi() {
        try {
            DatabaseInfo databaseInfo = DatabaseInfo.createDatabaseInfo();
            String dataSourceName = databaseInfo.getDataSourceName();
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(dataSourceName);
            Connection conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JDBCドライバーを使ってDB接続を取得する
     *
     * @return
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static Connection getConnectionFromDriverClass() throws ClassNotFoundException, SQLException {
        DatabaseInfo databaseInfo = DatabaseInfo.createDatabaseInfo();
        String url = databaseInfo.getUrl();
        String user = databaseInfo.getUser();
        String password = databaseInfo.getPassword();
        Class.forName(databaseInfo.getDriver());
        
        Connection conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataAccessObject.class);
    /**
     * SQL
     */
    private String sql = null;

    /**
     * データベースコネクション
     */
    private Connection connection = null;

    /**
     * トランザクション有無
     */
    private Boolean isTranzaction = false;

    /**
     * データベースコネクションを設定する
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public DataAccessObject() throws ClassNotFoundException, SQLException {
        logger.debug("Start");
        this.connection = DataAccessObject.createConnection();
        logger.debug("End");
    }

    /**
     * データベースコネクションを設定する
     *
     * @param conn
     */
    public DataAccessObject(Connection conn) {
        logger.debug("Start");
        this.connection = conn;
        logger.debug("End");
    }

    /**
     * 接続情報を取得する
     *
     * @return
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * データベース接続をクローズする
     *
     * @throws Throwable
     */
    public void close() throws Throwable {
        if (connection != null) {
            connection.close();
        }
        
    }
    
    public String getSql() {
        return sql;
    }
    
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * パラメータリスト
     */
    private Map<String, String> paramList = null;

    /**
     * パラメータListにパラメータを追加する。
     *
     * @param key パラメータKey
     * @param param	パラメータValue
     */
    public void addBindParam(String key, Object param) {
        if (paramList == null) {
            paramList = new HashMap<>();
        }
        paramList.put(key, param.toString());
    }

    /**
     * パラメータListにパラメータをクリアする。
     */
    public void clearBindParam() {
        if (paramList != null) {
            paramList.clear();
        }
    }

    /**
     * トランザクションを開始します。
     *
     * @return
     * @throws java.sql.SQLException
     */
    public Boolean beginTransaction() throws SQLException {
        return beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    /**
     * アイソレーションレベルを指定してトランザクションを開始します。
     *
     * @param isolationLevel
     * @return
     * @throws java.sql.SQLException
     */
    public Boolean beginTransaction(int isolationLevel) throws SQLException {
        if (this.connection != null && !this.isTranzaction) {
            this.connection.setTransactionIsolation(isolationLevel);
            this.isTranzaction = true;
            return true;
        }
        return false;
    }

    /**
     * トランザクションをCommitします。
     *
     * @throws java.sql.SQLException
     */
    public void commitTransaction() throws SQLException {
        if (connection != null
                && Connection.TRANSACTION_NONE != connection.getTransactionIsolation()) {
            connection.commit();
            this.isTranzaction = false;
        }
    }

    /**
     * トランザクションをRollBackします。
     *
     * @throws java.sql.SQLException
     */
    public void rollbackTransaction() throws SQLException {
        if (connection != null
                && Connection.TRANSACTION_NONE != connection.getTransactionIsolation()) {
            connection.rollback();
            this.isTranzaction = false;
        }
    }

    /**
     * ステートメントを実行します
     *
     * @return
     * @throws java.sql.SQLException
     */
    public PreparedStatement getPreparedStatement()
            throws SQLException {
        return connection.prepareStatement(sql);
        
    }

    /**
     * ステートメントを実行します
     *
     * @return
     * @throws java.sql.SQLException
     */
    public java.sql.CallableStatement getCallableStatement()
            throws SQLException {
        return connection.prepareCall(sql);
    }

    /**
     * ステートメントを実行します
     *
     * @return
     * @throws java.sql.SQLException
     */
    public Statement getStatement()
            throws SQLException {
        return connection.createStatement();
        
    }
    
}
