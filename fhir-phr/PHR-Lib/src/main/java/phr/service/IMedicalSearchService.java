/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.*;
import jp.kis_inc.csvconverter.src.dto.ConvertDto;
import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.dto.ConvertPhrIdDto;
import phr.service.impl.MedicalSearchService;

//import phr.datadomain.entity.ObservationDefinitionRangeEntity;

/**
 *
 * @author kis.o-note-002
 */
public interface IMedicalSearchService {

    /**
     * <pre>医療機関コード、医療機関名称取得処理
     * @param userIdStr
     * @param userNameStr
     * @param limitRow
     * @return
     * @throws Throwable 
     */
    ArrayList<MedicalOrganizationEntity> getMedicalOrganizationInfo(String userIdStr,String userNameStr ,int limitRow,int currentPage) throws Throwable;
    
    /**
     * 医療機関コード件数取得
     * @param userIdStr
     * @param userNameStr
     * @return
     * @throws Throwable 
     */
    int getMedicalOrganizationInfoCount(String userIdStr,String userNameStr)  throws Throwable;
    
    /**
     * <pre>医療機関コードから医療機関情報取得処理
     * @param medicalCd
     * @return
     * @throws Throwable 
     */
    public MedicalOrganizationEntity getMedicalOrganizationInfo(String medicalCd) throws Throwable;
}
