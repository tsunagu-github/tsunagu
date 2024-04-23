/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.ssmix.maker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XPNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.PatientEntity;
import phr.enums.PrefectureCdEnum;

/**
 *
 * @author kis-note
 */
public class PV1Maker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PV1Maker.class);    
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public PV1Dto execute(){
        logger.debug("Start");
        PV1Dto pv1 = new PV1Dto();
        
        pv1.setPV100("PV1");
        pv1.setPV101("0001");
        pv1.setPV102("O");

        logger.debug("End");
        return pv1;
    }

 
}
