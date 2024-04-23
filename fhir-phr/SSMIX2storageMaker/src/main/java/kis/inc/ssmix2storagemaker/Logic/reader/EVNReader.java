/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.AL1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class EVNReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(EVNReader.class);
    
    /**
     * MSHセグメントの情報をMSHDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public EVNDto readEVN(String[] lines , messageSplitDto splitDto){
        logger.debug("EVNセグメントの取得start");
        EVNDto result = new EVNDto();
        
        result = (EVNDto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setEVN(lines[i], i);

        }
        
        logger.debug("EVNセグメントの取得End");
        return result;
    }
    
}
