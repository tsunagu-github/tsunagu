/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.BackupRestoreEventEntity;

/**
 *
 * @author kis-note-027_user
 */
public interface IOperationBackupRestoreService {
    /**
     * バックアップセンターへ利用者データを送信する
     * @param phrId
     * @return
     * @throws Throwable 
     */
    BackupRestoreEventEntity backUpData(String phrId,String password) throws Throwable;
    
    /**
     * バックアップセンターのバックアップデータを削除する
     * @param phrId
     * @param restorePassword
     * @return
     * @throws Throwable 
     */
    int cancelBackup(String phrId, String restorePassword) throws Throwable;
    
    /**
     * バックアップセンターから取得したデータにてリストアを実行する
     * @param phrId
     * @param restorePassword
     * @return
     * @throws Throwable 
     */
    int restoreData(String phrId, String oldPhrId, String restorePassword,String phrAssociationId,String projectId) throws Throwable;
    
    /**
     * バックアップ状況を取得する
     * @param phrId
     * @return
     * @throws Throwable 
     */
    BackupRestoreEventEntity getBackUpStatus(String phrId) throws Throwable;
}
