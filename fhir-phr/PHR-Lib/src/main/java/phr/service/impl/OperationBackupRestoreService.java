/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.BackupRestoreEventAdapter;
import phr.datadomain.entity.BackupRestoreActionTypeEntity;
import phr.datadomain.entity.BackupRestoreEventEntity;
import phr.datadomain.entity.BackupRestoreStatusEntity;
import phr.service.IOperationBackupRestoreService;

/**
 *
 * @author kis-note-027_user
 */
public class OperationBackupRestoreService implements IOperationBackupRestoreService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OperationBackupRestoreService.class);
     /**
     * 事業者ID
     */
    private static final String phrAssociationId = PhrConfig.getConfigProperty(ConfigConst.BACKUP_PHR_ASSOCIATION_NO);   
                
    @Override
    public BackupRestoreEventEntity backUpData(String phrId,String password) throws Throwable {
         DataAccessObject dao = null;
         Date now = new Date();
         BackupRestoreEventEntity entity;
        try {
            logger.debug("Start");
           
            dao = new DataAccessObject();
            BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
            List<BackupRestoreEventEntity> entityList= adapter.findByphrId(phrId);
            
            //バックアップリストアDBへの登録
            boolean update = false;
            if(entityList ==null || entityList.isEmpty()){

            }else{
                update = true;
                //updateが最新のものに上書きする
                for(BackupRestoreEventEntity compent:entityList){
                    compent.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_INVALID);
                }
            }
            entity = new BackupRestoreEventEntity();
            entity.setBackupRestoreEventId(BackupRestoreEventAdapter.numberingBackupRestoreEventId());            
            entity.setRequestInsurerNo(phrId.substring(0,7));
            entity.setRequestPHR_ID(phrId);
            entity.setBackupRestoreActionTypeCd(BackupRestoreActionTypeEntity.ACTION_BACKUP);
            entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_RECEPTION);
            entity.setDecriptPassword(password);
            entity.setRestorePhrAssociationNo(phrAssociationId);
            entity.setRestoreProjectNo(phrId.substring(0,7));
            entity.setRestorePHR_ID(phrId);
            entity.setCreateDateTime(new Timestamp(now.getTime()));

            if(update){
                adapter.insert(entity);
                for(BackupRestoreEventEntity updateEnt:entityList){
                    //adapter.update(updateEnt);
                    adapter.delete(updateEnt);
                }
            }else{
                adapter.insert(entity);
            }
            dao.commitTransaction();
            
            
        } catch (Throwable ex) {
            logger.error("", ex);
            System.out.println(ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        return entity;
    }

    @Override
    public int cancelBackup(String phrId, String restorePassword) throws Throwable {
         DataAccessObject dao = null;
         int result;
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
            
            List<BackupRestoreEventEntity> entityList= adapter.findByphrId(phrId);
            BackupRestoreEventEntity entity;
            if(entityList ==null || entityList.isEmpty()){
                //バックアップ情報が見つかりませんエラー
                return 0;
//                throw new Throwable("バックアップ情報が見つかりません");
            }else if(entityList.size()==1){
                entity = entityList.get(0);
                if(!entity.getDecriptPassword().equals(restorePassword)){
                    return 9;
                    //throw new Throwable("パスワードが一致しません");                    
                }
                if(entity.getBackupRestoreStatusCd()==BackupRestoreStatusEntity.STATUS_INVALID){
                    return 0;
//                throw new Throwable("バックアップ情報が見つかりません");                    
                }
            }else{
                List<BackupRestoreEventEntity> passList = new ArrayList();
                boolean passerror = true;
                for(BackupRestoreEventEntity compent:entityList){
                    if(compent.getDecriptPassword().equals(restorePassword)){
                        passerror = false;
                        if(compent.getBackupRestoreStatusCd()!=BackupRestoreStatusEntity.STATUS_INVALID){
                            passList.add(compent);
                        }                        
                    }
                    
                }
                
                if(passList ==null || passList.isEmpty()){
                    if(passerror){
                        return 9;
                    //throw new Throwable("パスワードが一致しません");                    
                    }else{
                        return 0;
//                throw new Throwable("バックアップ情報が見つかりません");                
                    }
                }
                
                entity=  entityList.get(0);
                for(BackupRestoreEventEntity compent:passList){
                    if(entity.getUpdateDateTime().compareTo(compent.getUpdateDateTime())<0){
                        entity = compent;
                    }
                }
                
            }           

            entity.setBackupRestoreActionTypeCd(BackupRestoreActionTypeEntity.ACTION_DELETE);
            entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_RECEPTION);
            result = adapter.update(entity);
            dao.commitTransaction();
            
        } catch (Throwable ex) {
            logger.error("", ex);
            System.out.println(ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        return result;
    }

    @Override
    public int restoreData(String phrId, String oldPhrId, String restorePassword,String phrAssociationId,String projectId) throws Throwable {

         DataAccessObject dao = null;
         Date now = new Date();
         BackupRestoreEventEntity entity;
         int result;
        try {
            logger.debug("Start");
           
            dao = new DataAccessObject();
            BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
            int restoreType = getRestoreType(phrId, oldPhrId, phrAssociationId);
//            switch (restoreType){
//                case 1://同一プロジェクト内でのリストア
//                    //httpUrl =  restoreInfoUrl;
//                    break;
//                case 2://同一事業者別プロジェクト
//                    reqestUrl = transferProjectInfoUrl;
//                    break;
//                case 3://別事業者
//                    reqestUrl = transferInfoUrl;
//                    break;
//            }            
            
            if(restoreType==3){//別事業者
                entity = new BackupRestoreEventEntity();
                entity.setBackupRestoreEventId(BackupRestoreEventAdapter.numberingBackupRestoreEventId());            
                entity.setDecriptPassword(restorePassword);
            }else{
                List<BackupRestoreEventEntity> entityList= adapter.findByphrId(oldPhrId);
                if(entityList ==null || entityList.isEmpty()){
                    //バックアップ情報が見つかりませんエラー
                    return 0;
                    //throw new Throwable("バックアップ情報が見つかりません");
                }else if(entityList.size()==1){
                    entity = entityList.get(0);
                    if(!entity.getDecriptPassword().equals(restorePassword)){
                        return 9;
                        //throw new Throwable("パスワードが一致しません");                    
                    }                
                }else{
                    List<BackupRestoreEventEntity> passList = new ArrayList();
                    for(BackupRestoreEventEntity compent:entityList){
                        if(compent.getDecriptPassword().equals(restorePassword)){
                            passList.add(compent);
                        }
                    }
                    if(passList ==null || passList.isEmpty()){
                        return 9;
                        //throw new Throwable("パスワードが一致しません"); 
                    }
                    entity=  entityList.get(0);
                    for(BackupRestoreEventEntity compent:passList){
                        if(entity.getUpdateDateTime().compareTo(compent.getUpdateDateTime())<0){
                            entity = compent;
                        }
                    }
                }
                //バックアップ時の事業所ID、プロジェクトID、PHRIDが入力されたものと一致しているか
                int errNo=0;
                if(!entity.getRestorePhrAssociationNo().equals(phrAssociationId)){
                    errNo = errNo +1;
                }
                if(!entity.getRestoreProjectNo().equals(projectId)){
                   errNo = errNo + 2;  
                }
                if(!entity.getRestorePHR_ID().equals(oldPhrId)){
                    errNo = errNo + 4;
                }
                if(errNo>0){
                    return 90+errNo;
                }
            }
            entity.setRequestInsurerNo(phrId.substring(0,7));
            entity.setRequestPHR_ID(phrId);            
            
            entity.setBackupRestoreActionTypeCd(BackupRestoreActionTypeEntity.ACTION_RESTORE);
            entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_RECEPTION);
            entity.setRestorePhrAssociationNo(phrAssociationId);
            entity.setRestoreProjectNo(projectId);
            entity.setRestorePHR_ID(oldPhrId);
            
            if(restoreType==3){
                result = adapter.insert(entity);
            }else{
                result = adapter.update(entity);
            }
            dao.commitTransaction();
            
        } catch (Throwable ex) {
            logger.error("", ex);
            System.out.println(ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }        
        

        return result;        
    }

    @Override
    public BackupRestoreEventEntity getBackUpStatus(String phrId) throws Throwable {
        //バックアップのステータスを取得する
        DataAccessObject dao = null;
        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());

            List<BackupRestoreEventEntity> entityList= adapter.findByphrId(phrId);
            BackupRestoreEventEntity entity;
            if(entityList ==null || entityList.isEmpty()){
                entity = new BackupRestoreEventEntity();
                entity.setBackupRestoreStatusCd(0);
            }else if(entityList.size()==1){
                entity = entityList.get(0);
            }else{
                //updateが最新のもの
                entity=  entityList.get(0);
                for(BackupRestoreEventEntity compent:entityList){
                    if(entity.getUpdateDateTime().compareTo(compent.getUpdateDateTime())<0){
                        entity = compent;
                    }
                }
            }
            
            return entity;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            System.out.println(ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

    }
    
    //リストアタイプ確認
    private int getRestoreType(String phrId,String oldPhrId,String oldPhrAssociationId) throws Throwable{
        int type;
        if(oldPhrAssociationId.equals(phrAssociationId)){
            String newInsurerNo = phrId.substring(0,7);
            String oldInsurerNo = oldPhrId.substring(0,7);
            if(newInsurerNo.equals(oldInsurerNo)){
                //プロジェクト移動
                    type = 2;
            }else{
                //同一リストア
                type = 1;
            }            
        }else{
            //事業者移動
            type = 3;            
        }
        return type;
    }
        
}
