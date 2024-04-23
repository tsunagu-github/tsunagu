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
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class PV1Reader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PV1Reader.class);
    
    /**
     * MSHセグメントの情報をMSHDtoに詰めます
     * @param lines
     * @param splitDto
     * @return 
     */
    public PV1Dto readPV1(String[] lines , messageSplitDto splitDto){
        logger.debug("PV1セグメントの取得start");
        PV1Dto result = new PV1Dto();
        
        result = (PV1Dto) setSeparator(result , splitDto);
        
        for(int i=0;i < lines.length; i++){
            result.setPV1(lines[i], i);

        }
        
        logger.debug("PV1セグメントの取得End");
        return result;
    }
    
}
