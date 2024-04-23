/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.baseSegmentDto;

/**
 *
 * @author kis-note
 * readerの基本処理
 * readerは必ずbaseReaderを継承する
 */
public class baseReader {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(baseReader.class);

    /**
     * 区切り文字をDtoにセットする
     */
    public baseSegmentDto setSeparator(baseSegmentDto target , messageSplitDto splitDto){
        target.setFieldSp(splitDto.getFieldseparator());
        target.setElementSp(splitDto.getCompseparator());
        target.setRepeatSp(splitDto.getRepeatseparator());
        target.setSubcompSp(splitDto.getSubcomp());

        return target;
    }


}
