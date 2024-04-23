/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.ssmix.maker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IN1Dto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.PatientEntity;

/**
 *
 * @author kis-note
 */
public class IN1Maker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(IN1Maker.class);    
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public List<IN1Dto> execute(PatientEntity patient){
        logger.debug("Start");
        List<IN1Dto> in1List = new ArrayList<>();
        IN1Dto in1 = new IN1Dto();
        
        in1.setIN100("IN1");
        in1.setIN101("0001");
        in1.setIN110(patient.getInsurerNo());

        //被保険者記号番号は現在もたなのでいれない
        
        in1List.add(in1);
        
        logger.debug("End");
        return in1List;
    }

 
}
