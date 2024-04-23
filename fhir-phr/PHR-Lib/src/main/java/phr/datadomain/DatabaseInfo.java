/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データベース接続情報を管理するクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;

public class DatabaseInfo {
	/**
	 * ファクトリークラス
	 */
	private String factory = null;
	/**
	 * ファクトリークラスのPREFIXES
	 */
	private String prefixes = null;
	/**
	 * データソース名
	 */
	private String dataSourceName = null;
	/**
	 * 最大接続数
	 */
	private String maxConnection = null;
	/**
	 * ドライバークラス
	 */
	private String driver = null;
	/**
	 * Server名
	 */
	private String server = null;
	/**
	 * データベース名
	 */
	private String dbname = null;
	/**
	 * データベースポート
	 */
	private String port = null;
	/**
	 * 接続UserID
	 */
	private String user = null;
	/**
	 * 接続Userパスワード
	 */
	private String password = null;
	
	/**
	 * URLベース
	 */
	private String urlBase = null;
	
	/**
	 * ファクトリークラス
	 * @return factory
	 */
	public String getFactory() {
		return factory;
	}
	/**
	 * ファクトリークラス
	 * @param factory セットする factory
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}
	/**
	 * ファクトリークラスのPREFIXES
	 * @return prefixes
	 */
	public String getPrefixes() {
		return prefixes;
	}
	/**
	 * ファクトリークラスのPREFIXES
	 * @param prefixes セットする prefixes
	 */
	public void setPrefixes(String prefixes) {
		this.prefixes = prefixes;
	}
	/**
	 * データソース名
	 * @return dataSourceName
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}
	/**
	 * データソース名
	 * @param dataSourceName セットする dataSourceName
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	/**
	 * 最大接続数
	 * @return maxConnection
	 */
	public String getMaxConnection() {
		return maxConnection;
	}
	/**
	 * 最大接続数
	 * @param maxConnection セットする maxConnection
	 */
	public void setMaxConnection(String maxConnection) {
		this.maxConnection = maxConnection;
	}
	/**
	 * ドライバークラス
	 * @return driver
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * ドライバークラス
	 * @param driver セットする driver
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * Server名
	 * @return server
	 */
	public String getServer() {
		return server;
	}
	/**
	 * Server名
	 * @param server セットする server
	 */
	public void setServer(String server) {
		this.server = server;
	}
	/**
	 * データベース名
	 * @return dbname
	 */
	public String getDbname() {
		return dbname;
	}
	/**
	 * データベース名
	 * @param dbname セットする dbname
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	/**
	 * 接続UserID
	 * @return user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * 接続UserID
	 * @param user セットする user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * 接続Userパスワード
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 接続Userパスワード
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * データベースポート
	 * @return port
	 */
	public String getPort() {
		return port;
	}
	/**
	 * データベースポート
	 * @param port セットする port
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * URLベース
	 * @return urlBase
	 */
	public String getUrlBase() {
		return urlBase;
	}
	/**
	 * URLベース
	 * @param urlBase セットする urlBase
	 */
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}
	
	/**
	 * URLを取得する
	 * @return
	 */
	public String getUrl() {
		String url = this.urlBase.replace("{0}", this.server);
		url = url.replace("{1}", this.port);
		url = url.replace("{2}", this.dbname);
		return url;
	}
	
	/**
	 * データベース接続情報を取得する
	 * @return
	 */
	public static DatabaseInfo createDatabaseInfo() {
		DatabaseInfo databaseInfo = new DatabaseInfo();
		
		databaseInfo.setFactory(PhrConfig.getSystemConfigProperty(SystemConfigConst.JNDI_DATASOURCE_FACTORY));
		databaseInfo.setPrefixes(PhrConfig.getSystemConfigProperty(SystemConfigConst.URL_PKG_PREFIXES));
		databaseInfo.setUrlBase(PhrConfig.getSystemConfigProperty(SystemConfigConst.JDBC_URL_BASE));
		databaseInfo.setDataSourceName(PhrConfig.getSystemConfigProperty(SystemConfigConst.DATASOURCE_NAME));
		databaseInfo.setMaxConnection(PhrConfig.getSystemConfigProperty(SystemConfigConst.DATABASE_MAX_CONNECTION));
		databaseInfo.setDriver(PhrConfig.getSystemConfigProperty(SystemConfigConst.DATABASE_DRIVER));
		
		databaseInfo.setServer(PhrConfig.getConfigProperty(ConfigConst.DATABASE_HOST));
		databaseInfo.setDbname(PhrConfig.getConfigProperty(ConfigConst.DATABASE_NAME));
		databaseInfo.setPort(PhrConfig.getConfigProperty(ConfigConst.DATABASE_PORT));
		databaseInfo.setUser(PhrConfig.getConfigProperty(ConfigConst.DATABASE_USER));
		databaseInfo.setPassword(PhrConfig.getConfigProperty(ConfigConst.DATABASE_PASSWORD));
	
		return databaseInfo;
	}
}