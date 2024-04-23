/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZPDDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class ZPDReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ZPDReader.class);
    
    /**
     * ZPDセグメントの情報をZPDDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public ZPDDto readZPD(String[] lines , messageSplitDto splitDto){
        logger.debug("ZPDセグメントの取得start");
        ZPDDto result = new ZPDDto();
        
        result = (ZPDDto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setZPD(lines[i], i);

        }
        
        logger.debug("ZPDセグメントの取得End");
        return result;
    }
    
}
