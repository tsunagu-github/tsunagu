/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.NK1Dto;

/**
 *
 * @author kis-note
 */
public class NK1Reader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NK1Reader.class);

    /**
     * NK1セグメントの情報をNK1Dtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public NK1Dto readNK1(String[] lines , messageSplitDto splitDto){
        logger.debug("NK1セグメントの取得start");
        NK1Dto result = new NK1Dto();

        result = (NK1Dto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setNK1(lines[i], i);
        }

        logger.debug("NK1セグメントの取得End");
        return result;
    }

}
