/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.CTIDto;

/**
 *
 * @author kis-note
 */
public class CTIReader extends baseReader{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CTIReader.class);

    /**
     * CTIセグメントの情報をCTIDtoに詰めます
     * @param lines
     * @param splitDto
     * @return
     */
    public CTIDto readCTI(String[] lines , messageSplitDto splitDto){
        logger.debug("CTIセグメントの取得start");
        CTIDto result = new CTIDto();

        result = (CTIDto) setSeparator(result , splitDto);

        for(int i=0;i < lines.length; i++){
            result.setCTI(lines[i], i);
        }

        logger.debug("CTIセグメントの取得End");
        return result;
    }

}
