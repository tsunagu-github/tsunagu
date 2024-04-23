/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXEDto;

/**
 *
 * @author kis-note
 */
public class RXEReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RXEReader.class);

    /**
     * RXEセグメントの情報をRXEDtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public RXEDto readRXE(String[] lines , messageSplitDto splitDto){
        logger.debug("RXEセグメントの取得start");
        RXEDto result = new RXEDto();

        result = (RXEDto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setRXE(lines[i], i);
        }

        logger.debug("RXEセグメントの取得End");
        return result;
    }

}
