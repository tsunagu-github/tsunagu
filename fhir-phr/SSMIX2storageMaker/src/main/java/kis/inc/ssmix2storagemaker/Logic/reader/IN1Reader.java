/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelOML11;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IN1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.SPMDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class IN1Reader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(IN1Reader.class);
    
    /**
     * MSHセグメントの情報をMSHDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public IN1Dto readIN1(String[] lines , messageSplitDto splitDto){
        logger.debug("IN1セグメントの取得start");
        IN1Dto result = new IN1Dto();
        
        result = (IN1Dto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setIN1(lines[i], i);

        }
        
        logger.debug("IN1セグメントの取得End");
        return result;
    }
    
}
