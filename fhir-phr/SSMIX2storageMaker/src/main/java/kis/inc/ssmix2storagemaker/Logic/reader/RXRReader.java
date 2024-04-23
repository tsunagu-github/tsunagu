/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXRDto;

/**
 *
 * @author kis-note
 */
public class RXRReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RXRReader.class);

    /**
     * RXRセグメントの情報をRXRDtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public RXRDto readRXR(String[] lines , messageSplitDto splitDto){
        logger.debug("RXRセグメントの取得start");
        RXRDto result = new RXRDto();

        result = (RXRDto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setRXR(lines[i], i);
        }

        logger.debug("RXRセグメントの取得End");
        return result;
    }

}
