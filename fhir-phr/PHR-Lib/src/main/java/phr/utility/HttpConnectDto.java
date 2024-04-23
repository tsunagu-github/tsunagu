/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.utility;

/**
 *
 * @author daisuke
 */
public class HttpConnectDto {

    /**
     * クライアント証明書有無
     */
    private boolean clientAuth;
    /**
     * プロキシ有無
     */
    private boolean proxy;
    /**
     * プロキシ認証有無
     */
    private boolean proxyAuth;
    /**
     * 接続URL
     */
    private String url;
    /**
     * クライアント証明書PIN
     */
    private String clientCertPinNo;

    /**
     * クライアント証明書ファイル
     */
    private String clientCertFile;
    /**
     * クライアント証明書CAファイル
     */
    private String clientCaSertFile;
    /**
     * KeyStoreのパスワード
     */
    private String keyStorePass;
    /**
     * プロキシHOST名
     */
    private String proxyHost;
    /**
     * プロキシポート
     */
    private String proxyPort;
    /**
     * プロキシユーザ
     */
    private String proxyUser;
    /**
     * プロキシパスワード
     */
    private String proxyPassword;

    /**
     * @return 接続URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 接続URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return クライアント証明書PIN
     */
    public String getClientCertPinNo() {
        return clientCertPinNo;
    }

    /**
     * @param clientCertPinNo クライアント証明書PIN
     */
    public void setClientCertPinNo(String clientCertPinNo) {
        this.clientCertPinNo = clientCertPinNo;
    }

    /**
     * @return クライアント証明書ファイル
     */
    public String getClientCertFile() {
        return clientCertFile;
    }

    /**
     * @param clientCertFile クライアント証明書ファイル
     */
    public void setClientCertFile(String clientCertFile) {
        this.clientCertFile = clientCertFile;
    }

    /**
     * @return クライアント証明書CAファイル
     */
    public String getClientCaSertFile() {
        return clientCaSertFile;
    }

    /**
     * @param clientCaSertFile クライアント証明書CAファイル
     */
    public void setClientCaSertFile(String clientCaSertFile) {
        this.clientCaSertFile = clientCaSertFile;
    }

    /**
     * @return KeyStoreのパスワード
     */
    public String getKeyStorePass() {
        return keyStorePass;
    }

    /**
     * @param keyStorePass KeyStoreのパスワード
     */
    public void setKeyStorePass(String keyStorePass) {
        this.keyStorePass = keyStorePass;
    }

    /**
     * @return プロキシHOST名
     */
    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * @param proxyHost プロキシHOST名
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * @return プロキシポート
     */
    public String getProxyPort() {
        return proxyPort;
    }

    /**
     * @param proxyPort プロキシポート
     */
    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * @return プロキシユーザ
     */
    public String getProxyUser() {
        return proxyUser;
    }

    /**
     * @param proxyUser プロキシユーザ
     */
    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    /**
     * @return プロキシパスワード
     */
    public String getProxyPassword() {
        return proxyPassword;
    }

    /**
     * @param proxyPassword プロキシパスワード
     */
    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    /**
     * @return クライアント証明書有無
     */
    public boolean isClientAuth() {
        return clientAuth;
    }

    /**
     * @param clientAuth クライアント証明書有無
     */
    public void setClientAuth(boolean clientAuth) {
        this.clientAuth = clientAuth;
    }

    /**
     * @return プロキシ有無
     */
    public boolean isProxy() {
        return proxy;
    }

    /**
     * @param proxy プロキシ有無
     */
    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    /**
     * @return プロキシ認証有無
     */
    public boolean isProxyAuth() {
        return proxyAuth;
    }

    /**
     * @param proxyAuth プロキシ認証有無
     */
    public void setProxyAuth(boolean proxyAuth) {
        this.proxyAuth = proxyAuth;
    }

}
