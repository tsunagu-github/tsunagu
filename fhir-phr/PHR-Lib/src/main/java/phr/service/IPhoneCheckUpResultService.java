/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Date;
import java.util.List;
import phr.datadomain.entity.PhoneCUItemEntity;
import phr.datadomain.entity.PhoneCUValueEntity;

/**
 *
 * @author kis-note-027_user
 */
public interface IPhoneCheckUpResultService {
    
    List<Date> getYearList(String phrId,String dataInputTypeCd)throws Throwable;
    
    List<PhoneCUItemEntity> getItemList(String dataInputTypeCd, String phrId) throws Throwable;
    
    List<PhoneCUValueEntity> getValueList(String phrId, String dataInputTypeCd, String observationDefinitionId,List<Date> showYearList)throws Throwable;
    
    /**
     * 健診日とPHR_IDから医療機関名を取得
     * @param examDate
     * @param phr_id
     * @return hospitalName
     * @throws
     */
    public String getHospitalName(Date examDate, String phrId) throws Throwable;
    
    /**
     * 対象PHR_IDの対象健診年月リストを取得
     * @param phrId
     * @return
     * @throws Throwable
     */
    public List<Date> findById(String phrId) throws Throwable;
    
    /**
     * EnumValueを取得
     * @param value
     * @param observationDefinitionId
     * @return
     * @throws Throwable
     */
    public String getEnumValue(String value, String observationDefinitionId) throws Throwable;
}
