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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.IMedicalSearchService;

/**
 *
 * @author kis.o-note-002
 */
public class MedicalSearchService implements IMedicalSearchService {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalSearchService.class);
    
    /**
     * <pre>医療機関コード、医療期間名称取得処理searchExpireUser
     * @param userIdString
     * @param userNameStr
     * @return
     * @throws Throwable 
     */
    @Override
    public ArrayList<MedicalOrganizationEntity> getMedicalOrganizationInfo(String userIdString , String userNameStr,int limitRow, int currentPage) throws Throwable{
        DataAccessObject dao = new DataAccessObject();
        ArrayList<MedicalOrganizationEntity> list = new ArrayList<>();
        try{
            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            dao.beginTransaction();	// トランザクション開始
            list = adapter.getMedicalOrganizationInfo(userIdString ,userNameStr,limitRow,currentPage);
            return list;
        } catch (Throwable ex){
            System.out.println(ex.getMessage());
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
    }
    /**
     * 医療機関コード件数取得
     * @param userIdString
     * @param userNameStr
     * @return
     * @throws Throwable 
     */
    @Override
    public int getMedicalOrganizationInfoCount(String userIdString , String userNameStr) throws Throwable{
        DataAccessObject dao = new DataAccessObject();
        int count=0;
        try{
            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            dao.beginTransaction();	// トランザクション開始
            count=adapter.getMedicalOrganizationInfoCount(userIdString ,userNameStr);
            return count;
        } catch (Throwable ex){
            System.out.println(ex.getMessage());
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }
    }
    /**
     * <pre>医療機関CDから医療機関情報の取得
     * @param medicalCd
     * @return
     * @throws Throwable 
     */
    @Override
    public MedicalOrganizationEntity getMedicalOrganizationInfo(String medicalCd) throws Throwable{
        DataAccessObject dao = new DataAccessObject();
        MedicalOrganizationEntity entity = null;
        try{
            MedicalOrganizationAdapter adapter = new MedicalOrganizationAdapter(dao.getConnection());
            dao.beginTransaction();	// トランザクション開始
            entity = adapter.findByPrimaryKey(medicalCd);
        } catch (Throwable ex){
            logger.debug("",ex);
            throw ex;
        } finally{
            if(dao!=null){
                dao.close();
            }
        }

        return entity;
    }
   
}
