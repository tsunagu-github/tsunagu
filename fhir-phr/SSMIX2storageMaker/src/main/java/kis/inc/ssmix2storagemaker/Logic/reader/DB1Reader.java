/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.DB1Dto;

/**
 *
 * @author kis-note
 */
public class DB1Reader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DB1Reader.class);

    /**
     * DB1セグメントの情報をDB1Dtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public DB1Dto readDB1(String[] lines , messageSplitDto splitDto){
        logger.debug("DB1セグメントの取得start");
        DB1Dto result = new DB1Dto();

        result = (DB1Dto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setDB1(lines[i], i);
        }

        logger.debug("DB1セグメントの取得End");
        return result;
    }

}
