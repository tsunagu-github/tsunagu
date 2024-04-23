/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZI1Dto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class ZI1Reader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ZI1Reader.class);
    
    /**
     * ZI1セグメントの情報をZI1Dtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public ZI1Dto readZI1(String[] lines , messageSplitDto splitDto){
        logger.debug("ZI1セグメントの取得start");
        ZI1Dto result = new ZI1Dto();
        
        result = (ZI1Dto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setZI1(lines[i], i);

        }
        
        logger.debug("ZI1セグメントの取得End");
        return result;
    }
    
}
