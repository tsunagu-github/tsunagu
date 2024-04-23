/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.execute;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;

import phr.datadomain.entity.MedicalOrganizationSystemEntity;
import phr.datadomain.entity.ObservationImportHistoryEntity;
import phr.service.impl.FHIRCooperationService;
import phr.service.impl.MInCSResultImportService;
import phr.service.impl.ObservationImportHistoryService;

/**
 *
 * @author kis-note-025
 */
public class FHIRCooperationExecute {
	
	 /**
	 * ロギングコンポーネント
	 */
//	private static final Log logger = LogFactory.getLog(FHIRCooperationExecute.class);
	private static Logger logger = Logger.getLogger(FHIRCooperationExecute.class);
	
		public void main(MedicalOrganizationSystemEntity param) {
		logger.debug("Start");

				// PHR-FHIR検査結果連携機能
				try{
					FHIRCooperationService fc = new FHIRCooperationService();
					fc.cooperationFunction(param);
				}
				catch(Throwable t)
				{
					logger.error(t.toString(), t);
					System.exit(-1);
				}
		
		logger.debug("End");
		}
}
