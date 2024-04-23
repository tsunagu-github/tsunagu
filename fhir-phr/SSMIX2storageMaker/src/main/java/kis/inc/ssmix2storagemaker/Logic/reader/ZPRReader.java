/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZPRDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class ZPRReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ZPRReader.class);
    
    /**
     * ZPRセグメントの情報をZPRDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public ZPRDto readZPR(String[] lines , messageSplitDto splitDto){
        logger.debug("ZPRセグメントの取得start");
        ZPRDto result = new ZPRDto();
        
        result = (ZPRDto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setZPR(lines[i], i);

        }
        
        logger.debug("ZPRセグメントの取得End");
        return result;
    }
    
}
