/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IAMDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class IAMReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(IAMReader.class);
    
    /**
     * IAMセグメントの情報をIAMDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public IAMDto readIAM(String[] lines , messageSplitDto splitDto){
        logger.debug("IAMセグメントの取得start");
        IAMDto result = new IAMDto();
        
        result = (IAMDto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setIAM(lines[i], i);

        }
        
        logger.debug("IAMセグメントの取得End");
        return result;
    }
    
}
