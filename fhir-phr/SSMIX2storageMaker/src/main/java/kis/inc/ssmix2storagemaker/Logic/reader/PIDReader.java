/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelOML11;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class PIDReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PIDReader.class);
    
    /**
     * MSHセグメントの情報をMSHDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public PIDDto readPID(String[] lines , messageSplitDto splitDto){
        logger.debug("PIDセグメントの取得start");
        PIDDto result = new PIDDto();
        
        result = (PIDDto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setPID(lines[i], i);

        }
        
        logger.debug("PIDセグメントの取得End");
        return result;
    }
    
}
