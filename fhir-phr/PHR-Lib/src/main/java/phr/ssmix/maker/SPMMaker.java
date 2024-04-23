/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.ssmix.maker;

import java.text.SimpleDateFormat;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.SPMDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class SPMMaker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SPMMaker.class);    
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public SPMDto execute(String eventId , String cdate){
        logger.debug("Start");
        SPMDto spm = new SPMDto();
        
        spm.setSPM00("SPM");
        spm.setSPM01("0001");
        spm.setSPM02(eventId);
        spm.setSPM17(cdate);
        
        logger.debug("End");
        return spm;
    }    
}
