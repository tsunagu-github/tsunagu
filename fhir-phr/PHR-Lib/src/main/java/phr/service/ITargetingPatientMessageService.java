/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface ITargetingPatientMessageService {
  
    public boolean submitMessage(CommunicationEntity pentity , List<CommunicationReceiverEntity> rList) throws Throwable;
    
    /*
    * 医療機関一覧を取得する
    */
    public List<MedicalOrganizationPatientEntity> getMedicalList(String phrId) throws Throwable;
    
    /*
    * PHRIDから保険者番号を取得する
    */
    public String getInsurerPatientInfo(String phrId) throws Throwable ;
}
