/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.kis.inc.ssmix2stragemaker.search.controller;

import jp.kis.inc.ssmix2stragemaker.Facade.*;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.Facade.ModelToStorageFacade;
import kis.inc.ssmix2storagemaker.Facade.StorageToModelFacade;
import kis.inc.ssmix2storagemaker.controller.SSMIXSearchController;
import kis.inc.ssmix2storagemaker.dto.ADT00.ADT00BaseDto;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchResponseDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchResultDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author kis-note
 */
public class SearchTest {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SearchTest.class);
    
    /**
     * OML11のテスト
     */
    @Test
    public void excute(){
        logger.debug("Test Start");
 
        SSMIXSearchDto dto = new SSMIXSearchDto();
        
        dto.setPatientId("1234567-000000");
        dto.setSearchType(2);
        dto.setStdRootPath("F:/opt/phr/backup/1234567-000000/STD");
        dto.setDataType("STD");
        dto.setBaseDate("20161222");
        dto.setSearchDirection(1);
        dto.setCount(999);

        SSMIXSearchController search = new SSMIXSearchController();
        SSMIXSearchResponseDto result = search.search(dto);
        List<SSMIXSearchResultDto> list = result.getResultList();
        Gson gson = new Gson();
        logger.debug(gson.toJson(result));
        int count = 0;
        for(SSMIXSearchResultDto message : list){
            logger.debug(gson.toJson(message));
            List<ModelDto> r = message.getModelList();
            for(ModelDto model : r){
                logger.debug(gson.toJson(model));
                count ++;
            }
        }
        logger.debug("検索結果は" + count + "件");
        logger.debug("Test End");
        
    }
    
}
