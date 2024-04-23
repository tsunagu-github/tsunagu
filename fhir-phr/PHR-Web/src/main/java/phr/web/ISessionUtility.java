/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web;

public interface ISessionUtility {

    /**
     * セッションにオブジェクトを設定する
     * @param key
     * @param object
     */
    public void setSession(String key, Object object);
    
    /**
     * セッションよりオブジェクトを取得する
     * @param key
     * @return
     */
    public Object getSession(String key);
    
    /**
     * セッションをクリアする
     */
    public void ClearSession ();
    /**
     * セッションを削除する
     */
    public void removeSession (String key);
}