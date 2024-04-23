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
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.ObservationEntity;
import static phr.utility.TypeUtility.isNumber;

/**
 *
 * @author kis-note
 */
public class OBXMaker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OBXMaker.class);    
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public List<OBXDto> execute(List<ObservationEntity> entityList){
        logger.debug("Start");
        List<OBXDto> resultList = new ArrayList<>();
        int count = 1;
        for(ObservationEntity entity : entityList){
            OBXDto obx = new OBXDto();
            String st_num = String.format("%1$04d", count);
            obx.setOBX00("OBX");
            obx.setOBX01(st_num);
            
            String value = entity.getValue();
            if(isNumber(value)){
                obx.setOBX02("NM");
            }else{
                obx.setOBX02("ST");
            }

            CWEDto obx3_cwe = new CWEDto();
            if(entity.getJLAC10() != null){
                obx3_cwe.setCWE01(entity.getJLAC10());
            }else{
                obx3_cwe.setCWE01(entity.getD_JLAC10());
            }
            obx3_cwe.setCWE02(entity.getDisplayName());
            obx3_cwe.setCWE03("JC10");
            obx.setOBX03(obx3_cwe);
            
            String values[] = new String[6];
            values[0]= value;
            obx.setOBX05(values);

            CWEDto obx6_cwe = new CWEDto();
            if(entity.getUnitValue() != null){
                obx6_cwe.setCWE01(entity.getUnitValue());
                obx6_cwe.setCWE02(entity.getUnitValue());
                obx6_cwe.setCWE03("ISO+");
            }
            obx.setOBX06(obx6_cwe);

            obx.setOBX11("F");
            
            resultList.add(obx);
        }

        logger.debug("End");
        return resultList;
    }        
    
}
