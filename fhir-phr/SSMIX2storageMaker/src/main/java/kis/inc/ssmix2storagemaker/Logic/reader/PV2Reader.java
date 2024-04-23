/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV2Dto;

/**
 *
 * @author kis-note
 */
public class PV2Reader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PV2Reader.class);

    /**
     * PV2セグメントの情報をPV2Dtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public PV2Dto readPV2(String[] lines , messageSplitDto splitDto){
        logger.debug("PV2セグメントの取得start");
        PV2Dto result = new PV2Dto();

        result = (PV2Dto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setPV2(lines[i], i);
        }

        logger.debug("PV2セグメントの取得End");
        return result;
    }

}
