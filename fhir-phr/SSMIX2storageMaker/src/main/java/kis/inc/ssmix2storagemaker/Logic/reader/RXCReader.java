/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXCDto;

/**
 *
 * @author kis-note
 */
public class RXCReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RXCReader.class);

    /**
     * RXCセグメントの情報をRXCDtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public RXCDto readRXC(String[] lines , messageSplitDto splitDto){
        logger.debug("RXCセグメントの取得start");
        RXCDto result = new RXCDto();

        result = (RXCDto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setRXC(lines[i], i);
        }

        logger.debug("RXCセグメントの取得End");
        return result;
    }

}
