/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PRBDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class PRBReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PRBReader.class);
    
    /**
     * PRBセグメントの情報をPRBDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public PRBDto readPRB(String[] lines , messageSplitDto splitDto){
        logger.debug("PRBセグメントの取得start");
        PRBDto result = new PRBDto();
        
        result = (PRBDto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setPRB(lines[i], i);

        }
        
        logger.debug("PRBセグメントの取得End");
        return result;
    }
    
}
