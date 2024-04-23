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
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;

import phr.datadomain.entity.ObservationImportHistoryEntity;
import phr.service.impl.FHIRCooperationService;
import phr.service.impl.MInCSResultImportService;
import phr.service.impl.ObservationImportHistoryService;

/**
 *
 * @author kis-note-025
 */
public class MInCSDataUploadExecute {
    
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MInCSDataUploadExecute.class);
    
        public static void main(String[] args) {
        logger.trace("Start");

                try{
                    MInCSResultImportService mi = new MInCSResultImportService();
                    mi.ImportSpecimenResult();
                }
                catch(Throwable th)
                {
                    logger.error(th);
                    System.exit(-1);
                }
                // 戻り値(正常)
        System.exit(0);
        }
}
