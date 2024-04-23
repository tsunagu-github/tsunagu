/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationDefinitionEnumValueAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.adapter.PhoneCUResultAdapter;
import phr.datadomain.entity.ObservationDefinitionEnumValueEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.PhoneCUItemEntity;
import phr.datadomain.entity.PhoneCUValueEntity;
import phr.service.IPhoneCheckUpResultService;

/**
 *
 * @author kis-note-027_user
 */
public class PhoneCheckUpResultService implements IPhoneCheckUpResultService{
    private static final Log logger = LogFactory.getLog(PhoneCheckUpResultService.class);
    
    @Override
    public List<Date> getYearList(String phrId, String dataInputTypeCd) throws Throwable {
       List <Date> yearList = new ArrayList();
       
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            PhoneCUResultAdapter adapter = new PhoneCUResultAdapter((dao.getConnection()));
            yearList = adapter.findyearList(phrId, dataInputTypeCd);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
       return yearList;
    }

    @Override
    public List<PhoneCUItemEntity> getItemList(String dataInputTypeCd, String phrId) throws Throwable {
       List <PhoneCUItemEntity> itemList = new ArrayList();
       
        DataAccessObject dao=null;
        PatientEntity entity = getPatientInfo(phrId);
        String insurerNo = entity.getInsurerNo();
        try {
            dao = new DataAccessObject();
            PhoneCUResultAdapter adapter = new PhoneCUResultAdapter((dao.getConnection()));
            itemList = adapter.findItemList(dataInputTypeCd, insurerNo);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
       return itemList;
    }

    @Override
    public List<PhoneCUValueEntity> getValueList(String phrId, String dataInputTypeCd, String observationDefinitionId,List<Date> showYearList) throws Throwable {
       List <PhoneCUValueEntity> valueList = new ArrayList();
       
        DataAccessObject dao=null;
        PatientEntity entity = getPatientInfo(phrId);
        String insurerNo = entity.getInsurerNo();
        try {
            dao = new DataAccessObject();
            PhoneCUResultAdapter adapter = new PhoneCUResultAdapter((dao.getConnection()));
            valueList = adapter.findValueList(phrId, dataInputTypeCd,observationDefinitionId,showYearList);
            
            if(!valueList.isEmpty()){
                ObservationDefinitionEnumValueAdapter enumAdapter = new ObservationDefinitionEnumValueAdapter((dao.getConnection()));
                List<ObservationDefinitionEnumValueEntity> enumList = new ArrayList();
                enumList = enumAdapter.findByObservationId(observationDefinitionId, insurerNo);
                if(!enumList.isEmpty()){
                    for(PhoneCUValueEntity ent:valueList){
                        if(ent!=null){
                            for(ObservationDefinitionEnumValueEntity enumEnt:enumList){
                                if(enumEnt.getEnumValue().trim().equals(ent.getValue().trim())){
                                    ent.setValueOrg(ent.getValue());
                                    ent.setValue(enumEnt.getEnumName());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
       return valueList;
    }

    /**
     * 健診日とPHR_IDから医療機関名を取得
     * @param examDate
     * @param phr_id
     * @return hospitalName
     * @throws
     */
    public String getHospitalName(Date examDate, String phrId) throws Throwable {
        logger.debug("Start");
        String hospitalName = null;
        
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            PhoneCUResultAdapter adapter = new PhoneCUResultAdapter((dao.getConnection()));
            hospitalName = adapter.getHospitalName(examDate, phrId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception e){
                logger.debug(e);
            }
        }
        
        logger.debug("End");
        return hospitalName;
    }

    /**
     * 対象PHR_IDの対象健診年月リストを取得
     * @param phrId
     * @return
     * @throws Throwable
     */
    public List<Date> findById(String phrId) throws Throwable {
        logger.debug("Start");
        List <Date> yearList = new ArrayList<>();
        
         DataAccessObject dao=null;
         try {
             dao = new DataAccessObject();
             PhoneCUResultAdapter adapter = new PhoneCUResultAdapter((dao.getConnection()));
             yearList = adapter.findById(phrId);
         } catch (Throwable ex) {
             logger.error(ex.getMessage());
             throw ex;
         } finally {
             try{
                 if(dao!=null){dao.close();}
             }catch(Exception e){
                 logger.debug(e);
             }
         }
         logger.debug("End");
         return yearList;
     }

    /**
     * EnumValueを取得
     * @param value
     * @param observationDefinitionId
     * @return
     * @throws Throwable
     */
    public String getEnumValue(String value, String observationDefinitionId) throws Throwable {
        logger.debug("Start");
        String enumValue = new String();
        
         DataAccessObject dao=null;
         try {
             dao = new DataAccessObject();
             PhoneCUResultAdapter adapter = new PhoneCUResultAdapter((dao.getConnection()));
             enumValue = adapter.getEnumValue(value, observationDefinitionId, 7);
         } catch (Throwable ex) {
             logger.error(ex.getMessage());
             throw ex;
         } finally {
             try{
                 if(dao!=null){dao.close();}
             }catch(Exception e){
                 logger.debug(e);
             }
         }
         
         logger.debug("End");
         return enumValue;
     }
    
    private PatientEntity getPatientInfo(String phrid) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = new DataAccessObject();
        PatientEntity account = null;
        
        try{
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            dao.beginTransaction();
            account = adapter.findInsurePatient(phrid);

        }catch(Exception e){
            dao.rollbackTransaction();
            return null;
        }finally{
        }

        logger.trace("end");
        return account;
    }
}
