/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXADto;

/**
 *
 * @author kis-note
 */
public class RXAReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RXAReader.class);

    /**
     * RXAセグメントの情報をRXADtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public RXADto readRXA(String[] lines , messageSplitDto splitDto){
        logger.debug("RXAセグメントの取得start");
        RXADto result = new RXADto();

        result = (RXADto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setRXA(lines[i], i);
        }

        logger.debug("RXAセグメントの取得End");
        return result;
    }

}
