/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.*;
import phr.datadomain.entity.MedicalOrganizationEntity;

/**
 *
 * @author kis.o-note-002
 */
public interface IMedicalCreateEditService {

    /**
     * 医療機関情報登録処理
     * @param entity
     * @return
     * @throws Throwable 
     */
    boolean insertMedicalOrganization(MedicalOrganizationEntity entity) throws Throwable;

    /**
     * 医療機関コード、名称取得
     * @param userIdStr
     * @param userNameStr
     * @param limitRow
     * @param currentPage
     * @return
     * @throws Throwable 
     */
    ArrayList<MedicalOrganizationEntity> getMedicalOrganizationInfo(String userIdStr,String userNameStr,int limitRow,int currentPage) throws Throwable;
    
    /**
     * 医療機関情報更新処理
     * @param entity
     * @return
     * @throws Throwable 
     */
    boolean updateMedicalOrganization(MedicalOrganizationEntity entity) throws Throwable;
    
}
