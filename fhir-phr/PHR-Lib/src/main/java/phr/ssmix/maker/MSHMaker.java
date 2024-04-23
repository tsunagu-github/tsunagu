/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.ssmix.maker;

import java.text.SimpleDateFormat;
import java.util.Date;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.impl.MakeStorageService;

/**
 *
 * @author kis-note
 */
public class MSHMaker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MSHMaker.class);    
    
    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public MSHDto execute(){
        logger.debug("Start");
        MSHDto msh = new MSHDto();
        
        msh.setMSH00("MSH");
        msh.setMSH01("\\|");
        msh.setMSH02("^~\\&");
        msh.setMSH03("PHR");
        msh.setMSH04("KIS.inc");
        msh.setMSH05("GW");
        msh.setMSH06("KIS.inc");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        msh.setMSH07(sdf.format(date));

        msh.setMSH11("P");
        msh.setMSH12("2.5");
        msh.setMSH18("~ISO IR87");
        msh.setMSH20("ISO 2022-1994");
        msh.setMSH21("SS -MIX2_0.96^SS -MIX2^1.2.392.200250. 2.1.100.1.2. 96 ^ISO");
        
        logger.debug("End");
        return msh;
    }
}
