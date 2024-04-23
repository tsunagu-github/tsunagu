/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.TQ1Dto;

/**
 *
 * @author kis-note
 */
public class TQ1Reader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(TQ1Reader.class);

    /**
     * TQ1セグメントの情報をTQ1Dtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public TQ1Dto readTQ1(String[] lines , messageSplitDto splitDto){
        logger.debug("TQ1セグメントの取得start");
        TQ1Dto result = new TQ1Dto();

        result = (TQ1Dto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setTQ1(lines[i], i);
        }

        logger.debug("TQ1セグメントの取得End");
        return result;
    }

}
