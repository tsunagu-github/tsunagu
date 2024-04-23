/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelOML11;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.SPMDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class OBRReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OBRReader.class);
    
    /**
     * MSHセグメントの情報をMSHDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public OBRDto readOBR(String[] lines , messageSplitDto splitDto){
        logger.debug("OBRセグメントの取得start");
        OBRDto result = new OBRDto();
        
        result = (OBRDto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setOBR(lines[i], i);

        }
        
        logger.debug("SPMセグメントの取得End");
        return result;
    }
    
}
