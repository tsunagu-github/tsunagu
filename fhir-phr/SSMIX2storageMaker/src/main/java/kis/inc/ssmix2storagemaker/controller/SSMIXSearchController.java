/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.controller;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import kis.inc.ssmix2storagemaker.search.Facade.SearchFacade;
import kis.inc.ssmix2storagemaker.search.dto.ErrorDefinitionDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchResponseDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchResultDto;
import kis.inc.ssmix2storagemaker.search.validate.SearchValidate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class SSMIXSearchController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SSMIXSearchController.class);    
    
    /**
     * 検索メソッド
     * @param searchDto
     * @return 
     */
    public SSMIXSearchResponseDto search(SSMIXSearchDto searchDto){
        logger.debug("Start");
        SSMIXSearchResponseDto resultDto = new SSMIXSearchResponseDto();
        SearchValidate sv = new SearchValidate();
        List<SimpleEntry<String, ErrorDefinitionDto>> messages = sv.validation(searchDto);
        
        if(messages != null && messages.size() > 0){
            resultDto.setErrmeaasgeList(messages);
            resultDto.setStatus(false);
            return resultDto;
        }
        
        //検索を実施する
        SearchFacade sf = new SearchFacade();
        List<SSMIXSearchResultDto> resultList = sf.searchExecute(searchDto);
        
        resultDto.setResultList(resultList);
        resultDto.setStatus(true);
        
        logger.debug("End");
        return resultDto;
    }

    /**
     * 検索メソッド
     * @param searchDto
     * @return 
     */
    public SSMIXSearchResponseDto search2(SSMIXSearchDto searchDto){
        logger.debug("Start");
        SSMIXSearchResponseDto resultDto = new SSMIXSearchResponseDto();
        SearchValidate sv = new SearchValidate();
        List<SimpleEntry<String, ErrorDefinitionDto>> messages = sv.validation(searchDto);
        
        if(messages != null && messages.size() > 0){
            resultDto.setErrmeaasgeList(messages);
            resultDto.setStatus(false);
            return resultDto;
        }
        
        //検索を実施する
        SearchFacade sf = new SearchFacade();
        List<SSMIXSearchResultDto> resultList = sf.searchExecute2(searchDto);
        
        resultDto.setResultList(resultList);
        resultDto.setStatus(true);
        
        logger.debug("End");
        return resultDto;
    }
}
