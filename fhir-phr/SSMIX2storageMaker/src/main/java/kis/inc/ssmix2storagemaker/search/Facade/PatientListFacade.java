/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.search.Facade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class PatientListFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientListFacade.class);    
    
    public List<String> getPatientList(SSMIXSearchDto dto){
        logger.debug("Start");
        List<String> patientList = new ArrayList<>();
        int count = 0;

        logger.debug("標準化ストレージのの処理");
        String stpath = dto.getStdRootPath();
        
        if(stpath != null){
            File stDir = new File(stpath);
            if(stDir.exists()){
                File[] stP1Dirs = stDir.listFiles();
                for(File stP1Dir : stP1Dirs){
                    File[] stP2Dirs = stP1Dir.listFiles();

                    for(File stP2Dir : stP2Dirs){
                        File[] patientDir = stP2Dir.listFiles();

                        for(File patient : patientDir){
                            String id = patient.getName();
                            if(!patientList.contains(id)){
                                patientList.add(id);
                                count++;
                            }
                        }
                    }

                }
            }
        }
        
        
        logger.debug("拡張ストレージのの処理");
        String expath = dto.getExRootPath();
        
        if(expath != null){
            File exDir = new File(expath);
            if(exDir.exists()){
                File[] exP1Dirs = exDir.listFiles();
                for(File exP1Dir : exP1Dirs){
                    File[] exP2Dirs = exP1Dir.listFiles();

                    for(File exP2Dir : exP2Dirs){
                        File[] patientDir = exP2Dir.listFiles();

                        for(File patient : patientDir){
                            String id = patient.getName();
                            if(!patientList.contains(id)){
                                patientList.add(id);
                                count++;
                            }
                        }
                    }

                }
            }
        }
        
        logger.info("抽出対象患者数 :" + count + "名");
        
        logger.debug("End");
        return patientList;
    }
    
}
