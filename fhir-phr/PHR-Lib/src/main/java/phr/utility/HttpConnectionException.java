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
public class HttpConnectionException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3775745193801059368L;

    /**
     * エラーこーお
     */
    private final String code;

    /**
     *
     */
    public HttpConnectionException() {
        super();
        code = null;
    }

    /**
     * @param message
     */
    public HttpConnectionException(String message) {
        super(message);
        code = null;
    }

    /**
     * @param message
     */
    public HttpConnectionException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * エラーコードを取得する
     *
     * @return
     */
    public String getCode() {
        return code;
    }
}
