/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.BackupRestoreEventAdapter;
import phr.datadomain.entity.BackupRestoreActionTypeEntity;
import phr.datadomain.entity.BackupRestoreEventEntity;
import phr.datadomain.entity.BackupRestoreStatusEntity;
import phr.service.impl.BackupRestoreService;
import phr.utility.TypeUtility;

/**
 *
 * @author daisuke
 */
public class BackupRestoreServiceTest {
        /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreServiceTest.class);
    
    /**
     * 最終バックアップ日時を取得するメソッドのテスト
     * @throws Throwable 
     */
   @Test
    public void testGetLastBackupDate() throws Throwable {
        logger.debug("Start");
        String phrId = "1234567-000001";
        BackupRestoreEventEntity entity = new BackupRestoreEventEntity();
        entity.setBackupRestoreEventId(BackupRestoreEventAdapter.numberingBackupRestoreEventId());
        entity.setRequestInsurerNo("1234567");
        entity.setRequestPHR_ID(phrId);
        entity.setBackupRestoreActionTypeCd(1);
        entity.setBackupRestoreStatusCd(3);
        entity.setDecriptPassword("Abcd_1234");
        Date date = new Date();
        entity.setStartDateTiem(new Timestamp(date.getTime()));
        entity.setEndDateTime(new Timestamp(date.getTime()));
        
        DataAccessObject dao = new DataAccessObject();
        dao.beginTransaction();
        BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
        adapter.insert(entity);
        dao.commitTransaction();
        
        BackupRestoreService service = new BackupRestoreService();
        Date lastDate = service.getLastBackupDate(phrId);
        String last = TypeUtility.format(lastDate, "yyyy/MM/dd HH:mm:ss");
        
        dao.beginTransaction();
        adapter.delete(entity);
        dao.commitTransaction();
        dao.close();
        
        logger.debug(last);
        logger.debug("End");
        
    }
    /**
     * バックアップ実行するメソッドのテスト
     * @throws Throwable 
     */
   @Test
    public void testExecuteBackup() throws Throwable {
        logger.debug("Start");
        String phrId = "1234567-000000";
        BackupRestoreEventEntity entity = new BackupRestoreEventEntity();
        entity.setBackupRestoreEventId(BackupRestoreEventAdapter.numberingBackupRestoreEventId());
        entity.setRequestInsurerNo("1234567");
        entity.setRequestPHR_ID(phrId);
        entity.setBackupRestoreActionTypeCd(1);
        entity.setBackupRestoreStatusCd(1);
        entity.setDecriptPassword("Abcd_1234");

        
        DataAccessObject dao = new DataAccessObject();
        dao.beginTransaction();
        BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
        adapter.insert(entity);
        dao.commitTransaction();
        
        BackupRestoreService service = new BackupRestoreService();
        service.executeBackup();
        
        dao.beginTransaction();
        adapter.delete(entity);
        dao.commitTransaction();
        dao.close();
        
        logger.debug("End");
        
    }
    /**
     * リストア実行するメソッドのテスト
     * @throws Throwable 
     */
   @Test
    public void testExecuteRestore() throws Throwable {
        logger.debug("Start");
        String phrId = "1234567-000016";
        BackupRestoreEventEntity entity = new BackupRestoreEventEntity();
        entity.setBackupRestoreEventId(BackupRestoreEventAdapter.numberingBackupRestoreEventId());
        entity.setRequestInsurerNo("1234567");
        entity.setRequestPHR_ID(phrId);
        entity.setRestorePhrAssociationNo("123456789");
        entity.setRestoreProjectNo("1234567");
        entity.setRestorePHR_ID("1234567-000000");
        entity.setBackupRestoreActionTypeCd(BackupRestoreActionTypeEntity.ACTION_RESTORE);
        entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_RECEPTION);
        entity.setDecriptPassword("Abcd_1234");

        
        DataAccessObject dao = new DataAccessObject();
        dao.beginTransaction();
        BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
        adapter.insert(entity);
        dao.commitTransaction();
        
        BackupRestoreService service = new BackupRestoreService();
        service.executeRestore();
        
        BackupRestoreEventEntity fillEntity = adapter.findByPrimaryKey(entity.getBackupRestoreEventId());
        
        if (fillEntity.getBackupRestoreStatusCd() == BackupRestoreStatusEntity.STATUS_INVALID) {
            logger.debug(fillEntity.getErrorMessage());
        }        
        dao.beginTransaction();
        adapter.delete(entity);
        dao.commitTransaction();
        dao.close();
        
        logger.debug("End");
        
    }
     /**
     * 削除処理を実行するメソッドのテスト
     * @throws Throwable 
     */
   @Test
    public void testExecuteDelete() throws Throwable {
        logger.debug("Start");
        String phrId = "1234567-000000";
        BackupRestoreEventEntity entity = new BackupRestoreEventEntity();
        entity.setBackupRestoreEventId(BackupRestoreEventAdapter.numberingBackupRestoreEventId());
        entity.setRequestInsurerNo("1234567");
        entity.setRequestPHR_ID(phrId);
        entity.setRestorePhrAssociationNo("123456789");
        entity.setBackupRestoreActionTypeCd(BackupRestoreActionTypeEntity.ACTION_DELETE);
        entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_RECEPTION);
        entity.setDecriptPassword("Abcd_1234");

        
        DataAccessObject dao = new DataAccessObject();
        dao.beginTransaction();
        BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
        adapter.insert(entity);
        dao.commitTransaction();
        
        BackupRestoreService service = new BackupRestoreService();
        service.executeBackupDelete();
        
        BackupRestoreEventEntity fillEntity = adapter.findByPrimaryKey(entity.getBackupRestoreEventId());
        
        if (fillEntity.getBackupRestoreStatusCd() == BackupRestoreStatusEntity.STATUS_INVALID) {
            logger.debug(fillEntity.getErrorMessage());
        }        
        dao.beginTransaction();
        adapter.delete(entity);
        dao.commitTransaction();
        dao.close();
        
        logger.debug("End");
        
    }
}
