/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;

/**
 *
 * @author kis-note
 */
public class MSHReader extends baseReader {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MSHReader.class);

    /**
     * MSHセグメントの情報をMSHDtoに詰めます
     * @param lines
     * @return
     */
    public MSHDto readMSH(String[] lines , messageSplitDto splitDto){
        logger.debug("MSHセグメントの取得start");
        MSHDto result = new MSHDto();

        result = (MSHDto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            if(i==0){
                result.setMSH(lines[i], i);
                continue;
            }
            if(i==1){
                result.setMSH(splitDto.getFieldseparator(), i);
                result.setMSH(lines[i], i+1);
                continue;
            }

            result.setMSH(lines[i], i+1);

        }

        logger.debug("MSHセグメントの取得End");
        return result;
    }

}
