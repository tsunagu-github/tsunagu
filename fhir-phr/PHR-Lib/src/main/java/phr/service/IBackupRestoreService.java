/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Date;

/**
 *
 * @author daisuke
 */
public interface IBackupRestoreService {

    /**
     * 利用者の最終バックアップ日時を返却します。 バックアップが無い場合はNULLを返却します。
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    Date getLastBackupDate(String phrId) throws Throwable;

    /**
     * バックアップ処理を実施します。
     *
     * @throws Throwable
     */
    public void executeBackup() throws Throwable;

    /**
     * リストア処理を実施します。
     *
     * @throws Throwable
     */
    public void executeRestore() throws Throwable;

    /**
     * バックアップ削除処理を実施します。
     *
     * @throws Throwable
     */
    public void executeBackupDelete() throws Throwable;
}
