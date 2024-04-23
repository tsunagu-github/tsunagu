/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.ssmix.maker;

import java.text.SimpleDateFormat;
import java.util.Date;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class ORCMaker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ORCMaker.class);    
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public ORCDto execute(String eventId , String cdate){
        logger.debug("Start");
        ORCDto orc = new ORCDto();
        
        orc.setORC00("ORC");
        orc.setORC01("0001");
        orc.setORC03(eventId);
        orc.setORC05("CM");
        
        Date date = new Date();
        orc.setORC09(sdf2.format(date));
        

        logger.debug("End");
        return orc;
    }        
}
