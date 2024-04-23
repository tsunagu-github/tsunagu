/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.ssmix.maker;

import java.text.SimpleDateFormat;
import java.util.Date;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.impl.MakeStorageService;

/**
 *
 * @author kis-note
 */
public class EVNMaker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(EVNMaker.class);    
    
    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public EVNDto execute(){
        logger.debug("Start");
        EVNDto evn = new EVNDto();
        
        evn.setEVN00("EVN");
        evn.setEVN04("0");

        
        logger.debug("End");
        return evn;
    }
}
