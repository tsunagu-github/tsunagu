/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.Facade.ModelToStorageFacade;
import kis.inc.ssmix2storagemaker.dto.ADT00.ADT00BaseDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OrderDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IN1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.SPMDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.ICheckUpBackUPRestoreService;
import phr.service.IExportMedicineListService;
import phr.service.IMakeStorageService;
import phr.ssmix.maker.EVNMaker;
import phr.ssmix.maker.IN1Maker;
import phr.ssmix.maker.MSHMaker;
import phr.ssmix.maker.OBRMaker;
import phr.ssmix.maker.OBXMaker;
import phr.ssmix.maker.ORCMaker;
import phr.ssmix.maker.PIDMaker;
import phr.ssmix.maker.PV1Maker;
import phr.ssmix.maker.SPMMaker;

/**
 *
 * アラート一覧画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class MakeStorageService implements IMakeStorageService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MakeStorageService.class);
       
     private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
     /**
     * 検査結果のアラート判別を実施する
     * @param insureNo
     * @param year
     * @param observationList
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
     public boolean makeSTDStorage(String phrid , String rootPath)  throws Throwable {
         logger.debug("バックアップ用標準ストレージ作成開始");
         
        PatientEntity patient = PatientIndo(phrid);
        
        if(patient == null){
            logger.debug("対象患者いないため作成を実施できません");
            return false;
        }
        
        
        
        logger.debug("ADT-00のモデル作成");
        ADT00BaseDto adt00 = makeADT00(patient);
        logger.debug("ADT-00のモデル作成終了");
         
        logger.debug("OML-11のモデル作成");
        List<OML11BaseDto> oml11List = makeOML11(patient);
        logger.debug("OML-11のモデル作成終了");
        
        ModelToStorageFacade mts = new ModelToStorageFacade();
        mts.exChangeToStorage(adt00, rootPath, phrid, "-", "0000000000");

        for(OML11BaseDto oml11 : oml11List){
            mts.exChangeToStorage(oml11, rootPath, phrid, oml11.getCdate(), oml11.getEventId());
        }            
                    
        logger.debug("バックアップ用標準ストレージ作成終了");
        return true;    
     }

    private ADT00BaseDto makeADT00(PatientEntity patient) {
         logger.debug("Start");
         ADT00BaseDto adt00 = new ADT00BaseDto();
         
         Timestamp date = patient.getCreateDateTime();
         String cdate = sdf.format(date.getTime());
         
         adt00.setCdate(cdate);
         
         MSHMaker mshMaker = new MSHMaker();
         MSHDto msh = mshMaker.execute();
         msh.setMSH09("ADT^A08^ADT_A01");
         adt00.setMshDto(msh);

         EVNMaker evnMaker = new EVNMaker();
         EVNDto evn = evnMaker.execute();
         adt00.setEvnDto(evn);
         
         PIDMaker pidMaker = new PIDMaker();
         PIDDto pid = pidMaker.execute(patient);
         adt00.setPidDto(pid);
         
         PV1Maker pv1Maker = new PV1Maker();
         PV1Dto pv1 = pv1Maker.execute();
         adt00.setPv1Dto(pv1);
         
         //OBXに関して今回はすべてOML-11に出力するため
         //今回は作成しない
         
         //アレルギー情報が現状取得していないので
         //今回は作成しない
         
         IN1Maker in1Maker = new IN1Maker();
         List<IN1Dto> in1List = in1Maker.execute(patient);
         adt00.setIn1List(in1List);
         
         logger.debug("End");
         return adt00;
    }

    /**
     * 保険者番号も含めた患者基本情報を取得する
     * @param phrid
     * @return 
     */
    private PatientEntity PatientIndo(String phrid) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            PatientEntity entity = adapter.findInsurePatient(phrid);
            return entity;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }

    }

    /**
     * OML-11のモデル作成
     * @param patient
     * @return 
     */
    private List<OML11BaseDto> makeOML11(PatientEntity patient) {
        logger.debug("Start");
        //対象患者のObservationEventのリストを取得する
        List<ObservationEventEntity> eventList;
        List<OML11BaseDto> oml11List = new ArrayList<>();
        try {
            eventList = getEventList(patient);
        
            OML11BaseDto oml11 = new OML11BaseDto();
            MSHMaker mshMaker = new MSHMaker();
            MSHDto msh = mshMaker.execute();
            msh.setMSH09("OML^O33OML_O33");

            PIDMaker pidMaker = new PIDMaker();
            PIDDto pid = pidMaker.execute(patient);

            PV1Maker pv1Maker = new PV1Maker();
            PV1Dto pv1 = pv1Maker.execute();

            SPMMaker spmMaker = new SPMMaker();
            OBRMaker obrMaker = new OBRMaker();
            ORCMaker orcMaker = new ORCMaker();
            OBXMaker obxMaker = new OBXMaker();
            /**
             * モデルの作成はEventId単位で行う
             */
            for(ObservationEventEntity entity : eventList){
                String eventId = entity.getObservationEventId();
                String cdate = sdf.format(entity.getExaminationDate());
                String medicalCd = entity.getMedicalOrganizationCd();
                oml11 = new OML11BaseDto();
                oml11.setMshDto(msh);
                oml11.setPidDto(pid);
                oml11.setPv1Dto(pv1);
                oml11.setCdate(cdate);
                oml11.setEventId(eventId);

                OrderDto order = new OrderDto();
                SPMDto spm = spmMaker.execute(eventId, cdate);
                order.setSpmDto(spm);

                OBRDto obr = obrMaker.execute(cdate);
                order.setObrDto(obr);

                ORCDto orc = orcMaker.execute(eventId, cdate);
                order.setOrcDto(orc);

                List<ObservationEntity> observationList = getObservation(eventId , patient.getInsurerNo());
                List<OBXDto> obxList = obxMaker.execute(observationList);
                order.setObxList(obxList);
                
                List<OrderDto> orderList = new ArrayList<>();
                orderList.add(order);
                oml11.setOrderList(orderList);
                
                oml11List.add(oml11);

            }
        } catch (Throwable ex) {
            Logger.getLogger(MakeStorageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.debug("End");
        
        return oml11List;
    }

    private List<ObservationEventEntity> getEventList(PatientEntity patient) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
            List<ObservationEventEntity> entityList = adapter.getEventList(patient.getPHR_ID());
            return entityList;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }

    private List<ObservationEntity> getObservation(String eventId , String insurerNo) throws Throwable {
        logger.trace("Start");

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            List<ObservationEntity> entityList = adapter.getObservationList(eventId , insurerNo);
            return entityList;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }


    @Override
     public boolean makeEXTStorage(String phrid , String rootPath)  throws Throwable {
         logger.debug("バックアップ用拡張ストレージ作成開始");

        logger.debug("おくすり情報の作成開始");
        IExportMedicineListService medservice = new ExportMedicineListService();        
         Map<String, String> exportMedicineList = medservice.getExportMedicineList(phrid);
        if(exportMedicineList == null || exportMedicineList.isEmpty()){
            logger.debug("対象患者のおくすり情報が登録されていないため作成を実施できません");
            //return false;
        }else{        
            ModelToStorageFacade mts = new ModelToStorageFacade();
            mts.exMNBChangeToStorage(exportMedicineList, rootPath, phrid, null, null);
        }
        logger.debug("おくすり情報の作成終了");
       
      logger.debug("健診XMLのコピー開始");
       ICheckUpBackUPRestoreService cuXmlService = new CheckUpBackUPRestoreService();
       int result =  cuXmlService.getBackUPCheckUpXML(phrid, rootPath);
       if(result == 0){
           logger.debug("対象患者の健診情報が登録されていないためコピーを実施できません");
       }
       logger.debug("健診XMLのコピー終了");
         
                            
        logger.debug("バックアップ用標準ストレージ作成終了");
        return true;    
     }    
}
