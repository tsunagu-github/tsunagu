/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.AL1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class AL1Reader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AL1Reader.class);
    
    /**
     * MSHセグメントの情報をMSHDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public AL1Dto readAL1(String[] lines , messageSplitDto splitDto){
        logger.debug("AL1セグメントの取得start");
        AL1Dto result = new AL1Dto();
        
        result = (AL1Dto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setAL1(lines[i], i);

        }
        
        logger.debug("AL1セグメントの取得End");
        return result;
    }
    
}
