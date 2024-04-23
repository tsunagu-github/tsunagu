/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.ssmix.maker;

import java.text.SimpleDateFormat;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class OBRMaker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OBRMaker.class);    
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public OBRDto execute(String cdate){
        logger.debug("Start");
        OBRDto obr = new OBRDto();
        
        obr.setOBR00("OBR");
        obr.setOBR01("0001");
        obr.setOBR07(cdate);
        obr.setOBR22(cdate);

        logger.debug("End");
        return obr;
    }    
}
