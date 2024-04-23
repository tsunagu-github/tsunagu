/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import java.util.*;
import phr.service.IMedicalCreateEditService;

/**
 *
 * @author kis.o-note-002
 */
public class MedicalCreateEditService implements IMedicalCreateEditService {
    
    /**
     * 医療機関名称取得処理
     * @param userIdString
     * @param userNameStr
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean insertMedicalOrganization(MedicalOrganizationEntity entity) throws Throwable{

        DataAccessObject dao = new DataAccessObject();
        boolean result = false;
        try{
            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            dao.beginTransaction();	// トランザクション開始
            int i = adapter.InsertMedicalOrganization(entity);
            dao.commitTransaction();    // コミット
            if(i==1){
                return true;
            } else {
                return false;
            }
        } catch (Throwable ex){
            dao.rollbackTransaction();  // ロールバック
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
   }

    /**
     * 医療機関コード、名称取得
     * @param userIdString
     * @param userNameStr
     * @param limitRow
     * @param currentPage
     * @return
     * @throws Throwable 
     */
    @Override
    public ArrayList<MedicalOrganizationEntity> getMedicalOrganizationInfo(String userIdString , String userNameStr,int limitRow ,int currentPage) throws Throwable{
        DataAccessObject dao = new DataAccessObject();
        ArrayList<MedicalOrganizationEntity> list = new ArrayList<>();
        
        try{
            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            dao.beginTransaction();	// トランザクション開始
            list = adapter.getMedicalOrganizationInfo(userIdString ,userNameStr,limitRow ,currentPage);
            return list;
        } catch (Throwable ex){
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
    }
    /**
     * 医療機関情報更新処理
     * @param userIdString
     * @param userNameStr
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean updateMedicalOrganization(MedicalOrganizationEntity entity) throws Throwable{

        DataAccessObject dao = new DataAccessObject();
        boolean result = false;
        try{
            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            dao.beginTransaction();	// トランザクション開始
            int i = adapter.UpdateMedicalOrganization(entity);
            dao.commitTransaction();    // コミット
            if(i==1){
                return true;
            } else {
                return false;
            }
        } catch (Throwable ex){
            dao.rollbackTransaction();  // ロールバック
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
   }

    
    
    
    
}
